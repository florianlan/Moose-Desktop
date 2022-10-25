package tools;

public class TrialInfo {
    public static class INFO {
        public final static String PARTICIPANT_ID = "01";
        public final static String CSV_FILE_NAME = "csv/participant" + INFO.PARTICIPANT_ID + ".csv";
        public final static int BLOCK_AMOUNT = 2;
        public final static int TRIAL_AMOUNT_PER_BLOCK = 2; // 2 times each symbol
        public final static int MAX_TRIAL_FAILURE = 5;
        public static int BLOCK_NR = 1;
        public static int TRIAL_NR = 0;
        public static int TASK_ID = 0;
        public final static int BREAK_TIME = 30; // seconds
        public static boolean SYMBOLS = false;

    }
}
