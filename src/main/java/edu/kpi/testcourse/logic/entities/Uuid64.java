package edu.kpi.testcourse.logic.entities;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

/**
 * Helper class with static methods that convert UUID string to Base64 string and vice versa.
 */
public class Uuid64 {

  private static final Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

  /**
   * Converts the UUID hex string to the Base64 string.
   *
   * @param uuidStr is UUID hex string
   *
   * @return Base64 string
   */
  public static String hexToBase64(String uuidStr) {
    UUID uuid = UUID.fromString(uuidStr);
    byte[] bytes = uuidToBytes(uuid);
    return BASE64_URL_ENCODER.encodeToString(bytes);
  }

  /**
   * Converts the Base64 string to the hex UUID string.
   *
   * @param uuid64 is Base64 string
   *
   * @return hex UUID string
   */
  public static String base64ToHex(String uuid64) {
    byte[] decoded = Base64.getUrlDecoder().decode(uuid64);
    UUID uuid = uuidFromBytes(decoded);
    return uuid.toString();
  }

  /**
   * Converts UUID to the bytes.
   *
   * @param uuid is UUID instance
   *
   * @return bytes
   */
  private static byte[] uuidToBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  /**
   * Converts bytes to the UUID.
   *
   * @param decoded bytes
   *
   * @return UUID instance
   */
  private static UUID uuidFromBytes(byte[] decoded) {
    ByteBuffer bb = ByteBuffer.wrap(decoded);
    long mostSigBits = bb.getLong();
    long leastSigBits = bb.getLong();
    return new UUID(mostSigBits, leastSigBits);
  }
}
