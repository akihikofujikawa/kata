package kata.ex01.plan;

import kata.ex01.DiscountPlan;
import kata.ex01.model.HighwayDrive;
import kata.ex01.model.VehicleFamily;

import java.util.EnumSet;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.model.VehicleFamily.*;
import static kata.ex01.util.HolidayUtils.isHoliday;

public class HolidayDiscount implements DiscountPlan {
    private EnumSet<VehicleFamily> targetVehicleFamilies = EnumSet.of(STANDARD, MINI, MOTORCYCLE);

    @Override
    public boolean isValidFor(HighwayDrive drive) {
        return targetVehicleFamilies.contains(drive.getVehicleFamily()) &&
                (isHoliday(drive.getEnteredAt().toLocalDate()) || isHoliday(drive.getExitedAt().toLocalDate())) &&
                drive.getRouteType().equals(RURAL);
    }

    @Override
    public long discountPercentage(HighwayDrive drive) {
        return 30;
    }
}
