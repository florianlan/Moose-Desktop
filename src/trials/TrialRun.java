package trials;

import data.Data;

import java.util.*;

public class TrialRun {
    private static TrialRun INSTANCE;
    private List<Trial> trialList;
    private Trial actualTrial;

    /**
     * private Constructor
     */
    private TrialRun() {
        trialList = new ArrayList<>();
        for (int i = 1; i <= Data.getInstance().getRows(); i++) {
            for (int j = 1; j <= Data.getInstance().getCols(); j++) {
                trialList.add(new Trial(i, j));
            }
        }
    }

    /**
     * Singleton of TrialRun
     */
    public static TrialRun getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrialRun();
        }
        return INSTANCE;
    }

    /**
     * start new block after brake
     * @return
     */
    public TrialRun startNewBlock() {
        INSTANCE = new TrialRun();
        return INSTANCE;
    }

    public void removeTrial(Trial trial) {
        trialList.remove(trial);
    }

    public Trial getRandomTrial() {
        if (!trialList.isEmpty()) {
            Collections.shuffle(trialList);
            actualTrial = trialList.get(0);
        } else actualTrial = null;
        return actualTrial;
    }

    /**
     * Getter
     */
    public Trial getActualTrial() {
        return actualTrial;
    }

    /**
     * Setter
     */
}
