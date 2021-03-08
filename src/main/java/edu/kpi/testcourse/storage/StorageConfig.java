package edu.kpi.testcourse.storage;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.bind.annotation.Bindable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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

}
