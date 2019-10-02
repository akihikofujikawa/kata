package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;

import static kata.ex01.util.HolidayUtils.isHoliday;

public class HolidayDiscount implements DiscountType {

    @Override
    public boolean supports(HighwayDrive drive) {
        return (drive.getVehicleFamily().equals(VehicleFamily.STANDARD) ||
                drive.getVehicleFamily().equals(VehicleFamily.MINI) ||
                drive.getVehicleFamily().equals(VehicleFamily.MOTORCYCLE)
        ) &&
                drive.getRouteType().equals(RouteType.RURAL)
                &&
                (isHoliday(drive.getEnteredAt().toLocalDate()) || isHoliday(drive.getExitedAt().toLocalDate()));
    }

    @Override
    public int rate(HighwayDrive drive) {
        return 30;
    }
}
