package kata.ex01.plan;

import kata.ex01.DiscountPlan;
import kata.ex01.model.HighwayDrive;

import java.time.LocalDate;
import java.time.LocalTime;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.util.HolidayUtils.isHoliday;

public class WeekdayMorningEveningDiscount implements DiscountPlan {
    private RuleInterval morningInterval = new RuleInterval(LocalTime.of(6, 0), LocalTime.of(9, 0));
    private RuleInterval eveningInterval = new RuleInterval(LocalTime.of(17, 0), LocalTime.of(20, 0));

    @Override
    public boolean isValidFor(HighwayDrive drive) {
        return (isWeekday(drive.getEnteredAt().toLocalDate()) || isWeekday(drive.getExitedAt().toLocalDate())) &&
                (morningInterval.includedIn(drive.getEnteredAt(), drive.getExitedAt()) ||
                        eveningInterval.includedIn(drive.getEnteredAt(), drive.getExitedAt())) &&
                drive.getRouteType().equals(RURAL) &&
                drive.getDriver().getCountPerMonth() >= 5;
    }

    private boolean isWeekday(LocalDate date) {
        return !isHoliday(date);
    }

    @Override
    public long discountPercentage(HighwayDrive drive) {
        if (drive.getDriver().getCountPerMonth() >= 10) {
            return 50;
        }
        return 30;
    }
}
