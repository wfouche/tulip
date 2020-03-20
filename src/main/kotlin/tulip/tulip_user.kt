/*-------------------------------------------------------------------------*/

package tulip

/*-------------------------------------------------------------------------*/

//
// Open base class for a Virtual User.
//
open class User(val userId: Int) {

    private val map = arrayOf(
        ::init,
        ::action01,
        ::action02,
        ::action03,
        ::action04,
        ::action05,
        ::action06,
        ::action07,
        ::action08,
        ::action09,
        ::action10,
        ::action11,
        ::action12,
        ::action13,
        ::action14,
        ::action15,
        ::action16,
        ::action17,
        ::action18,
        ::action19,
        ::action20,
        ::action21,
        ::action22,
        ::action23,
        ::action24,
        ::action25,
        ::action26,
        ::action27,
        ::action28,
        ::action29,
        ::action30,
        ::action31,
        ::action32,
        ::action33,
        ::action34,
        ::action35,
        ::action36,
        ::action37,
        ::action38,
        ::action39,
        ::action40,
        ::action41,
        ::action42,
        ::action43,
        ::action44,
        ::action45,
        ::action46,
        ::action47,
        ::action48,
        ::action49,
        ::action50,
        ::action51,
        ::action52,
        ::action53,
        ::action54,
        ::action55,
        ::action56,
        ::action57,
        ::action58,
        ::action59,
        ::action60,
        ::action61,
        ::action62,
        ::action63,
        ::action64,
        ::action65,
        ::action66,
        ::action67,
        ::action68,
        ::action69,
        ::action70,
        ::action71,
        ::action72,
        ::action73,
        ::action74,
        ::action75,
        ::action76,
        ::action77,
        ::action78,
        ::action79,
        ::action80,
        ::action81,
        ::action82,
        ::action83,
        ::action84,
        ::action85,
        ::action86,
        ::action87,
        ::action88,
        ::action89,
        ::action90,
        ::action91,
        ::action92,
        ::action93,
        ::action94,
        ::action95,
        ::action96,
        ::action97,
        ::action98,
        ::action99,
        ::done
    )

    open fun init(): Boolean = false
    open fun action01(): Boolean = false
    open fun action02(): Boolean = false
    open fun action03(): Boolean = false
    open fun action04(): Boolean = false
    open fun action05(): Boolean = false
    open fun action06(): Boolean = false
    open fun action07(): Boolean = false
    open fun action08(): Boolean = false
    open fun action09(): Boolean = false
    open fun action10(): Boolean = false
    open fun action11(): Boolean = false
    open fun action12(): Boolean = false
    open fun action13(): Boolean = false
    open fun action14(): Boolean = false
    open fun action15(): Boolean = false
    open fun action16(): Boolean = false
    open fun action17(): Boolean = false
    open fun action18(): Boolean = false
    open fun action19(): Boolean = false
    open fun action20(): Boolean = false
    open fun action21(): Boolean = false
    open fun action22(): Boolean = false
    open fun action23(): Boolean = false
    open fun action24(): Boolean = false
    open fun action25(): Boolean = false
    open fun action26(): Boolean = false
    open fun action27(): Boolean = false
    open fun action28(): Boolean = false
    open fun action29(): Boolean = false
    open fun action30(): Boolean = false
    open fun action31(): Boolean = false
    open fun action32(): Boolean = false
    open fun action33(): Boolean = false
    open fun action34(): Boolean = false
    open fun action35(): Boolean = false
    open fun action36(): Boolean = false
    open fun action37(): Boolean = false
    open fun action38(): Boolean = false
    open fun action39(): Boolean = false
    open fun action40(): Boolean = false
    open fun action41(): Boolean = false
    open fun action42(): Boolean = false
    open fun action43(): Boolean = false
    open fun action44(): Boolean = false
    open fun action45(): Boolean = false
    open fun action46(): Boolean = false
    open fun action47(): Boolean = false
    open fun action48(): Boolean = false
    open fun action49(): Boolean = false
    open fun action50(): Boolean = false
    open fun action51(): Boolean = false
    open fun action52(): Boolean = false
    open fun action53(): Boolean = false
    open fun action54(): Boolean = false
    open fun action55(): Boolean = false
    open fun action56(): Boolean = false
    open fun action57(): Boolean = false
    open fun action58(): Boolean = false
    open fun action59(): Boolean = false
    open fun action60(): Boolean = false
    open fun action61(): Boolean = false
    open fun action62(): Boolean = false
    open fun action63(): Boolean = false
    open fun action64(): Boolean = false
    open fun action65(): Boolean = false
    open fun action66(): Boolean = false
    open fun action67(): Boolean = false
    open fun action68(): Boolean = false
    open fun action69(): Boolean = false
    open fun action70(): Boolean = false
    open fun action71(): Boolean = false
    open fun action72(): Boolean = false
    open fun action73(): Boolean = false
    open fun action74(): Boolean = false
    open fun action75(): Boolean = false
    open fun action76(): Boolean = false
    open fun action77(): Boolean = false
    open fun action78(): Boolean = false
    open fun action79(): Boolean = false
    open fun action80(): Boolean = false
    open fun action81(): Boolean = false
    open fun action82(): Boolean = false
    open fun action83(): Boolean = false
    open fun action84(): Boolean = false
    open fun action85(): Boolean = false
    open fun action86(): Boolean = false
    open fun action87(): Boolean = false
    open fun action88(): Boolean = false
    open fun action89(): Boolean = false
    open fun action90(): Boolean = false
    open fun action91(): Boolean = false
    open fun action92(): Boolean = false
    open fun action93(): Boolean = false
    open fun action94(): Boolean = false
    open fun action95(): Boolean = false
    open fun action96(): Boolean = false
    open fun action97(): Boolean = false
    open fun action98(): Boolean = false
    open fun action99(): Boolean = false
    open fun done(): Boolean = false

    open fun processAction(actionId: Int): Boolean {
        return map[actionId].invoke()
    }

}

/*-------------------------------------------------------------------------*/