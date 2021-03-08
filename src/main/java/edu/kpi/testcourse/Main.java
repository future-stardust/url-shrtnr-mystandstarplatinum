package edu.kpi.testcourse;

import com.google.gson.Gson;
import edu.kpi.testcourse.storage.Storage;
import edu.kpi.testcourse.storage.namespace.Namespace;
import io.micronaut.context.BeanContext;
import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a main entry point to the URL shortener.
 *
 * <p>It creates, connects and starts all system parts.
 */
public class Main {
  private static final Gson gson = new Gson();
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    initStorages();
    Micronaut.run(Main.class, args);
  }

  public static Gson getGson() {
    return gson;
  }

  /**
   * Method purpose is to create all storages on application startup.
   * do not forget to add your newly-declared namespaces here.
   */
  public static void initStorages() {
    getStorage();
    getDefaultStorage();
    getAuthStorage();
    getUrlStorage();
  }

  /**
   * Get storage.
   * Consider adding new `getMyDomainRelatedStorage` method to avoid manual instantiating
   * of Namespace objects in the code if You need new namespace.
   *
   * @return Storage
   */
  public static Storage getStorage() {
    BeanContext ctx = BeanContext.run();
    return ctx.getBean(Storage.class);
  }

  /**
   * Get default namespace. Better not to store data here.
   *
   * @return Namespace
   */
  public static Namespace getDefaultStorage() {
    return Main.getStorage().getOrCreateNamespace("default");
  }

  /**
   * Get namespace to store auth-related data.
   *
   * @return Namespace
   */
  public static Namespace getAuthStorage() {
    return Main.getStorage().getOrCreateNamespace("auth");
  }

  /**
   * Get namespace to store shortened urls.
   *
   * @return Namespace
   */
  public static Namespace getUrlStorage() {
    return Main.getStorage().getOrCreateNamespace("url");
  }
}
