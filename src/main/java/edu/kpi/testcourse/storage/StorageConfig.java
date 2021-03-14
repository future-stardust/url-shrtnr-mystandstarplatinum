package edu.kpi.testcourse.storage;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.bind.annotation.Bindable;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Storage configuration.
 */
@ConfigurationProperties("my.storage")
public interface StorageConfig {

  @Bindable(defaultValue = ".volumes/data")
  @NotBlank
  String getDataDir();

  @Min(5L)
  int getDataSyncPeriodSeconds();

  @Bindable(defaultValue = ".volumes/backup")
  @NotBlank
  String getBackupDir();

  @Min(1800L)
  int getBackupPeriodSeconds();

  @Min(1L)
  int getMaxBackups();

  /**
   * Testing configuration to generate workload.
   */
  @ConfigurationProperties("testing")
  public interface TestingConfig {
    @NotNull
    boolean getEnabled();

    /**
     * Task config to insert random data to storage.
     */
    @ConfigurationProperties("randomFill")
    public interface RandomFillConfig {
      @NotNull
      boolean getEnabled();

      List<String> getNamespaces();

      int getPeriodSeconds();

      int getInsertionCount();
    }
  }
}
