package kata.ex01;

import kata.ex01.model.HighwayDrive;

import java.time.LocalDateTime;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.util.HolidayUtils.isHoliday;

public class WeekdayMorningEveningDiscount implements DiscountType {

    @Override
    public boolean supports(HighwayDrive drive) {
        LocalDateTime targetStart = drive.getEnteredAt();
        LocalDateTime targetEnd = drive.getExitedAt();
        LocalDateTime ruleMorningStart = LocalDateTime.of(targetStart.getYear(), targetStart.getMonth(), targetStart.getDayOfMonth(),
                6, 0);
        LocalDateTime ruleMorningEnd = LocalDateTime.of(targetEnd.getYear(), targetEnd.getMonth(), targetEnd.getDayOfMonth(),
                9, 0);
        LocalDateTime ruleEveningStart = LocalDateTime.of(targetStart.getYear(), targetStart.getMonth(), targetStart.getDayOfMonth(),
                17, 0);
        LocalDateTime ruleEveningEnd = LocalDateTime.of(targetEnd.getYear(), targetEnd.getMonth(), targetEnd.getDayOfMonth(),
                20, 0);

        return drive.getRouteType().equals(RURAL) &&
                drive.getDriver().getCountPerMonth() >= 5 &&
                ((targetStart.isBefore(ruleMorningEnd) || targetStart.isEqual(ruleMorningEnd)) &&
                        (targetEnd.isAfter(ruleMorningStart) || targetEnd.isEqual(ruleMorningStart)) ||
                        (targetStart.isBefore(ruleEveningEnd) || targetStart.isEqual(ruleEveningEnd)) &&
                                (targetEnd.isAfter(ruleEveningStart) || targetEnd.isEqual(ruleEveningStart))) &&
                (!isHoliday(targetStart.toLocalDate()) || !isHoliday(targetEnd.toLocalDate()));

    }

    @Override
    public int rate(HighwayDrive drive) {
        if (drive.getDriver().getCountPerMonth() >= 10) {
            return 50;
        }
        return 30;
    }
}
