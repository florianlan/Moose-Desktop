package trials;

import tools.TrialInfo.INFO;

public class Trial {
    private int row;
    private int col;
    private int trialsLeft;

    /**
     * Constructor
     */
    public Trial(int row, int col) {
        this.row = row;
        this.col = col;
        this.trialsLeft = INFO.TRIAL_AMOUNT_PER_BLOCK;
    }

    /**
     * Decrement trails left by amount
     * @param amount amount to decrement
     * @return amount trials left
     */
    public int decTrialsLeft(int amount) {
        return this.trialsLeft -= amount;
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

    public int getTrialsLeft() {
        return trialsLeft;
    }
}
