package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.counter.CounterModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

    public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel, final CounterModel counterModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
        this.counterModel = counterModel;
    }

    private final TimeModel timeModel;

    private final ClockModel clockModel;

    private final CounterModel counterModel;

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private StopwatchState state;

    protected void setState(final StopwatchState state) {
        this.state = state;
        uiUpdateListener.updateState(state.getId());
    }

    private StopwatchUIUpdateListener uiUpdateListener;

    @Override
    public void setUIUpdateListener(final StopwatchUIUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onStartStop() { state.onStartStop(); }
    @Override public synchronized void onLapReset()  { state.onLapReset(); }

    @Override
    public void onIncrementStop() {
        state.onIncrementStop();
    }

    @Override public synchronized void onTick() {
        state.onTick();
    }

    @Override public void updateUIRuntime() { uiUpdateListener.updateTime(timeModel.getRuntime()); }
    @Override public void updateUILaptime() { uiUpdateListener.updateTime(timeModel.getLaptime()); }

    @Override
    public void updateUICounter() {
        uiUpdateListener.updateCounter(counterModel.get());
    }

    // known states
    private final StopwatchState STOPPED     = new StoppedState(this);
    private final StopwatchState RUNNING     = new RunningState(this);
    private final StopwatchState LAP_RUNNING = new LapRunningState(this);
    private final StopwatchState LAP_STOPPED = new LapStoppedState(this);

    private final StopwatchState INCREMENT     = new IncrementState(this);
    private final StopwatchState DECREMENT     = new DecrementState(this);

    // transitions
    @Override public void toRunningState()    { setState(RUNNING); }
    @Override public void toStoppedState()    { setState(STOPPED); }

    @Override
    public void toIncrementState() {
        setState(INCREMENT);
    }

    @Override
    public void toDecrementState() {
        setState(DECREMENT);
    }

    @Override
    public void toAlarmState() {

    }

    @Override public void toLapRunningState() { setState(LAP_RUNNING); }
    @Override public void toLapStoppedState() { setState(LAP_STOPPED); }

    // actions
    @Override public void actionInit()       { toStoppedState(); actionReset(); }
    @Override public void actionReset()      { counterModel.reset(); actionUpdateView(); }
    @Override public void actionStart()      { clockModel.start(); }
    @Override public void actionStop()       { clockModel.stop(); clockModel.negativeCountingStop(); }
    @Override public void actionLap()        { timeModel.setLaptime(); }
    @Override public void actionInc()        { timeModel.incRuntime(); actionUpdateView(); }
    @Override public void actionUpdateView() { state.updateView(); }

    @Override
    public void actionIncrement() {
        counterModel.increment();
        actionUpdateView();
    }

    @Override
    public void actionDecrement() {
        counterModel.decrement();
        actionUpdateView();
    }

    @Override
    public void actionNegativeCounterStart() {
        clockModel.negativeCountingStart();
    }

    @Override
    public void actionNegativeCounterStop() {
        clockModel.negativeCountingStop();
    }
}
