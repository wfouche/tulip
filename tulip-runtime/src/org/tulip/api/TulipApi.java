package org.tulip.api;

import org.tulip.core.TulipKt;

public class TulipApi {

    public static final String VERSION_STRING = "2.0.0-beta5";

    public static final int NUM_ACTIONS = 100;

    public static void runTulip(String configFilename, TulipUserFactory userFactory) {
        TulipKt.initConfig(configFilename);
        TulipKt.runTests(userFactory);
    }
}
