package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.plan.HolidayDiscount;
import kata.ex01.plan.LateNightDiscount;
import kata.ex01.plan.WeekdayMorningEveningDiscount;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    private List<DiscountPlan> discountPlans;

    DiscountServiceImpl() {
        discountPlans = Arrays.asList(
                new WeekdayMorningEveningDiscount(),
                new HolidayDiscount(),
                new LateNightDiscount());
    }

    @Override
    public long calc(HighwayDrive drive) {
        return discountPlans.stream()
                .filter(plan -> plan.isValidFor(drive))
                .map(plan -> plan.discountPercentage(drive))
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }
}