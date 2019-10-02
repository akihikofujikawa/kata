package kata.ex01;

import kata.ex01.model.HighwayDrive;

import java.time.LocalDateTime;

public class LateNightDiscount implements DiscountType {

    @Override
    public boolean supports(HighwayDrive drive) {
        LocalDateTime targetStart = drive.getEnteredAt();
        LocalDateTime targetEnd = drive.getExitedAt();
        LocalDateTime ruleStart = LocalDateTime.of(targetStart.getYear(), targetStart.getMonth(), targetStart.getDayOfMonth(),
                0, 0);
        LocalDateTime ruleEnd = LocalDateTime.of(targetEnd.getYear(), targetEnd.getMonth(), targetEnd.getDayOfMonth(),
                4, 0);
        return (targetStart.isBefore(ruleEnd) || targetStart.isEqual(ruleEnd)) &&
                (targetEnd.isAfter(ruleStart) || targetEnd.isEqual(ruleStart));
    }

    @Override
    public int rate(HighwayDrive drive) {
        return 30;
    }
}
