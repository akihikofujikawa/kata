package kata.ex01;

import kata.ex01.model.HighwayDrive;

import java.util.ArrayList;

public class DiscountResolver {

    public DiscountResolver() {
        this.discountTypes.add(new WeekdayMorningEveningDiscount());
        this.discountTypes.add(new LateNightDiscount());
        this.discountTypes.add(new HolidayDiscount());
    }

    ArrayList<DiscountType> discountTypes = new ArrayList<>();


    DiscountType resolve(HighwayDrive drive) throws Exception {
        for (DiscountType discountType : discountTypes) {
            if (discountType.supports(drive)) {
                return discountType;
            }
        }
        throw new Exception();
    }
}
