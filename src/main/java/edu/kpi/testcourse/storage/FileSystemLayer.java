package edu.kpi.testcourse.storage;

import io.micronaut.context.annotation.Prototype;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates filesystem operation, bridges data types.
 */
@Prototype
public class FileSystemLayer {
  private static final Logger logger = LoggerFactory.getLogger(FileSystemLayer.class);

  /**
   * Create directory. Fails silently.
   *
   * @param first – the path string or initial part of the path string
   * @param more – additional strings to be joined to form the path string
   */
  public void mkdir(String first, String ... more) {
    Path target = Paths.get(first, more);
    try {
      Files.createDirectories(target);
    } catch (IOException e) {
      logger.error("Exception creation directory {}: {}",
          target.toString(), e.getMessage());
    }
  }

  /**
   * Writes data to ${filename}.tmp, replaces original file, then deletes temporary file.
   * data is locked while serializing.
   *
   * @param basePath directory which contains required file
   * @param fileName file name inside ${basePath} directory
   * @param data object that needs to be serialized
   */
  public void safeWriteSerializable(
      String basePath,
      String fileName,
      LockedSerializable data
  ) {
    final Path temp = Paths.get(basePath, String.format("%s.tmp", fileName));
    final Path original = Paths.get(basePath, fileName);

    File tempFile;
    try {
      tempFile = Files.createFile(temp).toFile();
    } catch (IOException e) {
      logger.error(e.getMessage());
      return;
    }

    data.lock();
    try {
      FileOutputStream fos = new FileOutputStream(tempFile);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(data);
    } catch (IOException e) {
      logger.error("Exception writing object to file: {}", e.getMessage());
    } finally {
      data.unlock();
    }

    try {
      Files.move(
          temp,
          original,
          StandardCopyOption.ATOMIC_MOVE,
          StandardCopyOption.REPLACE_EXISTING
      );
    } catch (IOException e) {
      logger.error("Exception replacing {} (temp) with {} (original): {}",
          original.toString(),
          temp.toString(),
          e.getMessage()
      );
    } finally {
      try {
        Files.deleteIfExists(temp);
      } catch (IOException e) {
        logger.error("Could not delete temp file {}: {}", temp.toString(), e.getMessage());
      }
    }
  }
}
