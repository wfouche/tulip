package tulip

import java.util.*
import java.util.concurrent.ArrayBlockingQueue as Queue
import java.util.concurrent.LinkedBlockingQueue

object DataCollector : Thread() {

    init {
        isDaemon = true
        start()
    }

    // Input queue for the Data Collector thread.
    private var dcq = Queue<Task>(10000)

    // Queue used to block the main thread on while the DataCollector thread
    // prints the results of a TestCase to the console.
    private var handshakeQueue = LinkedBlockingQueue<Int>()

    fun clearStats() {
        dcq.put(Task(userId = -1, numUsers = 0, numThreads = 0, actionId = 0))
    }

    fun printStats(num_actions: Int, duration_millis: Int, printMap: Boolean) {
        val numUsers = if (printMap) 1 else 0

        dcq.put(Task(userId = -2, numUsers = numUsers, numThreads = num_actions, actionId = duration_millis))

        handshakeQueue.take()
    }

    fun put(task: Task) {
        dcq.put(task)
    }

    override fun run() {
        // frequency map: count the number of times a given (key) response time occurred.
        // key = response time in microseconds (NOT milliseconds).
        // value = the number of times a given (key) response time occurred.
        val latencyMap = mutableMapOf<Long, Long>()
        var latencyMap_min_rt: Long = Long.MAX_VALUE
        var latencyMap_max_rt: Long = Long.MIN_VALUE
        var latencyMap_max_ts = ""

        fun saveStats(num_actions: Int, duration_millis: Int, printMap: Boolean) {
            val duration_seconds: Double = duration_millis.toDouble() / 1000.0

            // actions per second (aps)
            val aps: Double = num_actions / duration_seconds

            // average response time (art) in milliseconds
            val art: Double = latencyMap.map { it.value * it.key }.sum() / 1000.0 / num_actions

            // standard deviation
            // HOWTO: https://www.statcan.gc.ca/edu/power-pouvoir/ch12/5214891-eng.htm#a2
            val sdev: Double = Math.sqrt(latencyMap.map { it.value * Math.pow((it.key / 1000.0 - art), 2.0) }.sum() / num_actions)

            // 95th percentile value
            // HOWTO 1: https://www.youtube.com/watch?v=9QhU2grGU_E
            // HOWTO 2: https://www.youtube.com/watch?v=8U__c22VOVA
            //
            // https://harding.edu/sbreezeel/460%20files/statbook/chapter5.pdf
            //
            // Formula for finding Percentiles (Grouped Frequency Distribution)
            ///
            fun percentile(k: Double, min_value: Double = 0.0, max_value: Double = 0.0): Double {
                val P: Double = (k / 100.0) * num_actions

                var CFb: Double = 0.0
                var F: Double = 0.0

                val keys = latencyMap.keys.sorted()
                var tk: Long = 0
                for (key in keys) {
                    tk = key
                    F = latencyMap[key]!!.toDouble()
                    if (100.0 * (CFb + F) / (1.0 * num_actions) >= k) {
                        break
                    }
                    CFb += F
                }
                var L = tk
                var U = tk

                while (llq(L) == tk) {
                    L -= 1
                }
                L += 1

                while (llq(U) == tk) {
                    U += 1
                }
                U -= 1

                var p: Double = (L + ((P - CFb) / F) * (U - L)) / 1000.0
                if (p < 0.0) {
                    p = 0.0
                }

                if (p < min_value) {
                    p = min_value
                }
                if (p > max_value) {
                    p = max_value
                }

                return p
            }

            // min rt
            val min_rt = latencyMap_min_rt / 1000.0

            // max rt
            val max_rt = latencyMap_max_rt / 1000.0

            val output = mutableListOf("")
            if (printMap) {
                output.add("latencyMap = " + latencyMap.toString())
                output.add("")
            }

            output.add("  average number of actions completed per second = ${"%.3f".format(Locale.US, aps)}")
            output.add("  average duration/response time in milliseconds = ${"%.3f".format(Locale.US, art)}")
            output.add("  standard deviation  (response time)  (millis)  = ${"%.3f".format(Locale.US, sdev)}")
            output.add("")
            output.add("  duration of benchmark (in seconds) = ${duration_seconds}")
            output.add("  number of actions completed = ${num_actions}")

            output.add("")

            val percentiles = mainTestCase.percentiles
            for (kk in percentiles) {
                val px = percentile(kk, min_rt, max_rt)
                output.add("  ${kk}th percentile (response time) (millis) = ${"%.3f".format(Locale.US, px)}")
            }

            output.add("")
            output.add("  minimum response time (millis) = ${"%.3f".format(Locale.US, min_rt)}")
            output.add("  maximum response time (millis) = ${"%.3f".format(Locale.US, max_rt)} at ${latencyMap_max_ts}")

            output.add("")
            var cpu_load: Double = 0.0
            var i = 0.0
            while (!CpuLoadMetrics.processCpuStats.isEmpty())
            {
                cpu_load += CpuLoadMetrics.processCpuStats.take()
                i += 1.0
            }
            output.add("  average cpu load (process) = ${"%.3f".format(Locale.US, if (i == 0.0) 0.0 else cpu_load / i)}")

            cpu_load = 0.0
            i = 0.0
            while (!CpuLoadMetrics.systemCpuStats.isEmpty())
            {
                cpu_load += CpuLoadMetrics.systemCpuStats.take()
                i += 1.0
            }
            output.add("  average cpu load (system)  = ${"%.3f".format(Locale.US, if (i == 0.0) 0.0 else cpu_load / i)}")

            Console.put(output)

            handshakeQueue.put(0)
        }

        while (true) {

            var shouldPrintStats = false

            var num_actions: Int = 0
            var duration_millis: Int = 0
            var print_map: Boolean = false

            while (true) {

                val task: Task = dcq.take()
                if (task.userId < 0) {
                    if (task.userId == -2) {
                        shouldPrintStats = true
                        num_actions = task.numThreads
                        duration_millis = task.actionId
                        print_map = task.numUsers == 1
                    }
                    break
                }
                val durationMicros = task.durationMicros

                //
                // Limit the number of active users.
                //
                task.rspQueue?.put(if (task.success) 1 else 0)

                val key = llq(durationMicros)

                // Attempt 1:
                //
                // if (key in latencyMap.keys) {
                //    latencyMap[key] = (latencyMap[key])!! + 1
                //} else {
                //    latencyMap[key] = 1
                //}

                // Attempt 2:
                //
                //latencyMap[key] = latencyMap.getOrDefault(key, 0) + 1

                // Attempt 3:
                //
                // Java 8:  map.merge(key, 1, (a, b) -> a + b);
                //
                // Kotlin: map.merge(key, 1, {a, b -> a+b})
                //
                latencyMap.merge(key, 1, { a, b -> a + b })

                if (durationMicros < latencyMap_min_rt) {
                    latencyMap_min_rt = durationMicros
                }
                if (durationMicros > latencyMap_max_rt) {
                    latencyMap_max_rt = durationMicros
                    latencyMap_max_ts = java.time.LocalDateTime.now().toString()
                }
            }
            if (shouldPrintStats) {
                saveStats(num_actions, duration_millis, print_map)
            }
            latencyMap.clear()
            latencyMap_min_rt = Long.MAX_VALUE
            latencyMap_max_rt = Long.MIN_VALUE
            latencyMap_max_ts = ""
        }
    }
}

/*-------------------------------------------------------------------------*/