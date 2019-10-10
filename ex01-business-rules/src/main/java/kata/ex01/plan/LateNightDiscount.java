package kata.ex01.plan;

import kata.ex01.DiscountPlan;
import kata.ex01.model.HighwayDrive;

import java.time.LocalTime;

public class LateNightDiscount implements DiscountPlan {
    private RuleInterval lateNightInterval = new RuleInterval(LocalTime.of(0, 0), LocalTime.of(4, 0));

    @Override
    public boolean isValidFor(HighwayDrive drive) {
        return lateNightInterval.includedIn(drive.getEnteredAt(), drive.getExitedAt());
    }

    @Override
    public long discountPercentage(HighwayDrive drive) {
        return 30;
    }
}
