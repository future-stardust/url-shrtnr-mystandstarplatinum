package edu.kpi.testcourse.storage.dummy;

/**
 * Testing workload to run emulate storage usage.
 */
abstract class DummyWorkload implements Runnable {

  public abstract boolean shouldBeScheduled();

  public abstract int getPeriod();

}
