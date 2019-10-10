package kata.ex01.plan;

import java.time.LocalDateTime;
import java.time.LocalTime;

class RuleInterval {
    private final LocalTime start;
    private final LocalTime end;

    RuleInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    boolean includedIn(LocalDateTime start, LocalDateTime end) {
        int days = start.toLocalTime().isAfter(this.end) ? 1 : 0;
        LocalDateTime theStart = LocalDateTime.of(start.toLocalDate(), this.start).plusDays(days);
        LocalDateTime theEnd = LocalDateTime.of(start.toLocalDate(), this.end).plusDays(days);

        return (start.isBefore(theEnd) || start.isEqual(theEnd)) && (end.isAfter(theStart) || end.isEqual(theStart));
    }
}
