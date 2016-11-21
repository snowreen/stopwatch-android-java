package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import static edu.luc.etl.cs313.android.simplestopwatch.R.string.DECREMENT;
/**
 * Created by snowreen on 11/19/16.
 */

public class DecrementState implements StopwatchState {

    public DecrementState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    private final StopwatchSMStateView sm;

    @Override
    public void updateView() {
        sm.updateUICounter();
    }

    @Override
    public int getId() {
        return DECREMENT;
    }

    @Override
    public void onStartStop() {

    }

    @Override
    public void onLapReset() {

    }

    @Override
    public void onIncrementStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        sm.actionDecrement();
        sm.toDecrementState();
    }
}
