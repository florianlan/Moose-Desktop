package trials;

import tools.TrialInfo.INFO;

public class Trial {
    private int row;
    private int col;
    private int successTrialsLeft;
    private int failTrialsLeft;

    /**
     * Constructor
     */
    public Trial(int row, int col) {
        this.row = row;
        this.col = col;
        this.successTrialsLeft = INFO.TRIAL_AMOUNT_PER_BLOCK;
        this.failTrialsLeft = INFO.MAX_TRIAL_FAILURE;
    }

    /**
     * Setter
     */

    public void setFailTrialsLeft(int failTrialsLeft) {
        this.failTrialsLeft = failTrialsLeft;
    }

    public void setSuccessTrialsLeft(int successTrialsLeft) {
        this.successTrialsLeft = successTrialsLeft;
    }

    /**
     * Getter
     */
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getFailTrialsLeft() {
        return failTrialsLeft;
    }

    public int getSuccessTrialsLeft() {
        return successTrialsLeft;
    }
}
