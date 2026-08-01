package net.feliperocha.urlshortener.util;

public class Base62Encoder {

private static final String BASE62 =
  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final int BASE = 62;

public static String encode(long value) {
  StringBuilder sb = new StringBuilder();
  while (value > 0) {
    sb.append(BASE62.charAt((int) (value % BASE)));
    value /= BASE;
  }
  return sb.reverse().toString();
}

public static long decode(String encoded) {
  long result = 0;
  for (char c : encoded.toCharArray()) {
    result = result * BASE + BASE62.indexOf(c);
  }
  return result;
}
}
