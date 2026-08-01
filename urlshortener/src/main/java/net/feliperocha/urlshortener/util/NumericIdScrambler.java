package net.feliperocha.urlshortener.util;

public class NumericIdScrambler {

// Keep these secret, inject via environment variables or secrets manager
private static final long XOR_MASK = 0x5F3759DF3A2C1B4EL;
  private static final int ROTATION_BITS = 17;

public static long scramble(long value) {
  value ^= XOR_MASK;
  value = Long.rotateLeft(value, ROTATION_BITS);
  return Math.abs(value);
}

public static long unscramble(long value) {
  value = Long.rotateRight(value, ROTATION_BITS);
  value ^= XOR_MASK;
  return Math.abs(value);
}
}
