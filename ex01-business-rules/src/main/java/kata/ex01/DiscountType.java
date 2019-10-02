package kata.ex01;

import kata.ex01.model.HighwayDrive;

public interface DiscountType {
    boolean supports(HighwayDrive drive);

    int rate(HighwayDrive drive);
}
