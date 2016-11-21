package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface StopwatchSMStateView {

    // transitions
    void toRunningState();
    //void toStoppedState();
    void toLapRunningState();
    void toLapStoppedState();

    void toStoppedState();
    void toIncrementState();
    void toDecrementState();
    void toAlarmState();

    // actions
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionLap();
    void actionInc();
    void actionUpdateView();

    void actionIncrement();
    void actionDecrement();
    void actionNegativeCounterStart();
    void actionNegativeCounterStop();



    // state-dependent UI updates
    void updateUIRuntime();
    void updateUILaptime();

    void updateUICounter();

}
