package kata.ex01;

import kata.ex01.model.HighwayDrive;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {

        DiscountResolver resolver = new DiscountResolver();
        try {
            DiscountType discountType = resolver.resolve(drive);
            return discountType.rate(drive);
        } catch (Exception e) {
            return 0;
        }
    }
}