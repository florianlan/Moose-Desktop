package tools;

public class TrialInfo {
    public static class INFO {
        // Settings
        public final static String PARTICIPANT_ID = "01";
        public final static String PARTICIPANT_NAME = "Lanz";
        public final static int BLOCK_AMOUNT = 2;
        public final static int TRIAL_AMOUNT_PER_BLOCK = 2; // 2 times each symbol
        public final static int BREAK_TIME = 30; // seconds

        // Do not touch
        public static String TEST_ID = "1";
        public static String CSV_FILE_NAME = "csv/participant_" + INFO.PARTICIPANT_ID + "_" + INFO.PARTICIPANT_NAME + "_test_" + TEST_ID;
        public static int BLOCK_NR = 1;
        public static int TRIAL_NR = 0;
        public static String TRIAL_ID = "";
        public static int ACTIVE_ROW_1 = 0;
        public static int ACTIVE_ROW_2 = 0;
        public static int DOT_SIZE_PX = 0;
        public static boolean SHOW_SYMBOLS = false;
        public static boolean SHOW_LINES = false;
        public static boolean SHOW_FAILS = false;
        public static int NR_ROWS = 0;
        public static int NR_COLS = 0;
        public static int SIZE_X_TOTAL = 0;
        public static int SIZE_Y_TOTAL = 0;

    }
}
