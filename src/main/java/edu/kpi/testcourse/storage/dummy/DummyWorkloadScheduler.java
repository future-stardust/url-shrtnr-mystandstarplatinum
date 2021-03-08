package edu.kpi.testcourse.storage.dummy;

import edu.kpi.testcourse.storage.StorageConfig.TestingConfig;
import io.micronaut.context.ApplicationContext;
import io.micronaut.scheduling.TaskScheduler;
import java.time.Duration;

/**
 * Schedule dummy workloads if enabled.
 */
public class DummyWorkloadScheduler {

  /**
   * schedule all know dummies.
   */
  public static void schedule() {
    ApplicationContext appContext = ApplicationContext.run();

    TestingConfig config = appContext.getBean(TestingConfig.class);
    if (!config.getEnabled()) {
      return;
    }

    TaskScheduler scheduler = appContext.getBean(TaskScheduler.class);

    var rf = new RandomFill();
    if (rf.shouldBeScheduled()) {
      scheduler.scheduleAtFixedRate(
          Duration.ofSeconds(rf.getPeriod()),
          Duration.ofSeconds(rf.getPeriod()),
          rf
      );
    }
  }
}
