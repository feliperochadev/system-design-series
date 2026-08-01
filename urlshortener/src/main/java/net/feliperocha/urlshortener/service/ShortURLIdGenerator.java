package net.feliperocha.urlshortener.service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.feliperocha.urlshortener.util.Base62Encoder;
import net.feliperocha.urlshortener.util.NumericIdScrambler;

@Component
  public class ShortURLIdGenerator {

private static final int MAX_SEQUENCE = 999;

private final int machineId;
    private final AtomicLong sequence = new AtomicLong(0);
    private volatile long lastEpochSecond = Instant.now().getEpochSecond();

public ShortURLIdGenerator(@Value("${urlshortener.machine-id:0}") int machineId) {
  this.machineId = machineId % 10;
}

public String generate() {
  long epochSecond = Instant.now().getEpochSecond();

    if (epochSecond != lastEpochSecond) {
      lastEpochSecond = epochSecond;
      sequence.set(0);
    }

    long currentSequence = sequence.getAndIncrement();

    if (currentSequence > MAX_SEQUENCE) {
      // Sequence exhausted, wait for the clock to tick forward
  while ((epochSecond = Instant.now().getEpochSecond()) == lastEpochSecond) {
    Thread.onSpinWait();
  }
      lastEpochSecond = epochSecond;
      sequence.set(0);
      currentSequence = sequence.getAndIncrement();
    }

    long numericId = Long.parseLong(
      String.format("%d%03d%d", epochSecond, currentSequence, machineId)
      );

    // Scramble to make the output opaque, then Base62-encode
    return Base62Encoder.encode(NumericIdScrambler.scramble(numericId));
}
  }
