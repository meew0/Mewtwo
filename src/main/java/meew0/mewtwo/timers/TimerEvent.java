package meew0.mewtwo.timers;

import java.time.Instant;

public record Timer(int id, Instant instant, String name) {
}
