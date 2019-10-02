package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;

import java.time.LocalDateTime;

import static kata.ex01.util.HolidayUtils.isHoliday;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        LocalDateTime targetStart = drive.getEnteredAt();
        LocalDateTime ruleStart = LocalDateTime.of(targetStart.getYear(), targetStart.getMonth(), targetStart.getDayOfMonth(),
                0, 0);
        LocalDateTime targetEnd = drive.getExitedAt();
        LocalDateTime ruleEnd = LocalDateTime.of(targetEnd.getYear(), targetEnd.getMonth(), targetEnd.getDayOfMonth(),
                4, 0);
        if ((targetStart.isBefore(ruleEnd) || targetStart.isEqual(ruleEnd)) &&
                (targetEnd.isAfter(ruleStart) || targetEnd.isEqual(ruleStart))) {
            return 30;
        }

        if((drive.getVehicleFamily().equals(VehicleFamily.STANDARD) ||
                drive.getVehicleFamily().equals(VehicleFamily.MINI) ||
                drive.getVehicleFamily().equals(VehicleFamily.MOTORCYCLE)
        ) &&
                drive.getRouteType().equals(RouteType.RURAL)
                &&
                (isHoliday(targetStart.toLocalDate()) || isHoliday(targetEnd.toLocalDate()))
        ) {
            return 30;
        }



        return 0;
    }
}