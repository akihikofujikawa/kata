package kata.ex01;

import kata.ex01.model.HighwayDrive;

import java.time.LocalDateTime;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        LocalDateTime targetEnd = drive.getExitedAt();
        LocalDateTime ruleEnd = LocalDateTime.of(targetEnd.getYear(), targetEnd.getMonth(), targetEnd.getDayOfMonth(),
                4, 0);
            if ((drive.getEnteredAt().isBefore(drive.getExitedAt())
                    drive.getEnteredAt().isEqual(LocalDateTime.of(2016, 4, 1, 4, 0))) &&
                    (drive.getExitedAt().isAfter(LocalDateTime.of(2016, 4, 1, 0, 0)) ||
                            drive.getExitedAt().isEqual(LocalDateTime.of(2016, 4, 1, 0, 0)))
            ) {
            return 30;
        }
    }
}
