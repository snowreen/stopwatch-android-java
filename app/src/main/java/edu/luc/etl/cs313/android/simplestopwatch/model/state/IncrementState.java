package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import static edu.luc.etl.cs313.android.simplestopwatch.R.string.INCREMENT;

/**
 * Created by snowreen on 11/19/16.
 */

public class IncrementState implements StopwatchState {

    public IncrementState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    private final StopwatchSMStateView sm;

    @Override
    public void updateView() {
        sm.updateUICounter();
    }

    @Override
    public int getId() {
        return INCREMENT;
    }

    @Override
    public void onStartStop() {

    }

    @Override
    public void onLapReset() {

    }

    @Override
    public void onIncrementStop() {
        sm.actionNegativeCounterStop();
        sm.actionIncrement();
        sm.actionNegativeCounterStart();
        sm.toIncrementState();
    }

    @Override
    public void onTick() {
        sm.actionStart();
        sm.toDecrementState();
    }
}
