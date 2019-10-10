package kata.ex01;

import kata.ex01.model.HighwayDrive;

public interface DiscountPlan {
    boolean isValidFor(HighwayDrive drive);

    long discountPercentage(HighwayDrive drive);
}
