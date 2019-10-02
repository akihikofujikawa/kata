package kata.ex01;

import kata.ex01.model.Driver;
import kata.ex01.model.HighwayDrive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.model.RouteType.URBAN;
import static kata.ex01.model.VehicleFamily.OTHER;
import static kata.ex01.model.VehicleFamily.STANDARD;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kawasima
 */
public class DiscountServiceTest {
    DiscountService discountService;

    private Driver driver(int usingCount) {
        Driver driver = new Driver();
        driver.setCountPerMonth(usingCount);
        return driver;
    }

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void test平日朝夕割引() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 3, 31, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 6, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕割引_平日朝夕で都市部の場合は割引されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 7, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test平日朝夕割引_平日朝夕で当月の利用回数が5回未満の場合は割引されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 7, 30));
        drive.setDriver(driver(3));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test平日朝夕割引_平日朝夕で当月の利用回数が5回以上9回以下の場合は3割割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 7, 30));
        drive.setDriver(driver(7));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test平日朝夕割引_平日朝夕で当月の利用回数が10回以上の場合は5割割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 7, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕割引_平日昼間は割引されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 11, 0));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test平日朝夕割引_平日朝は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 7, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 8, 0));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕割引_平日夕は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 18, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 19, 0));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕割引_休日は割引されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 2, 18, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 19, 0));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(OTHER);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引_開始と終了が同じ日で休日の場合はは休日割が適用される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 2, 7, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 15, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);
        drive.setDriver(driver(5));

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日割引_開始と終了が同じ日で平日の場合は休日割が適用されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 23, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引_開始が平日で終了が休日の場合は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 4, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);
        drive.setDriver(driver(5));

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日割引_開始が平日で終了も平日の場合は割引されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 3, 31, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 9, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引_開始が休日で終了も休日の場合は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 2, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 3, 9, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);
        drive.setDriver(driver(5));

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日割引_開始が休日で終了が平日の場合は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 3, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 4, 9, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);
        drive.setDriver(driver(5));

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日割引_都市部の場合は適用されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 3, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 3, 11, 30));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(URBAN);
        drive.setDriver(driver(5));

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引_車種がその他の場合は適用されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 3, 10, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 4, 9, 30));
        drive.setVehicleFamily(OTHER);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test深夜は深夜割引が適用される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 1, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 2, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test朝は深夜割引が適用されない() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 9, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test深夜割引の境界値チェック4時() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 4, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 5, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test深夜割引の境界値チェック0時() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 0, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test深夜割引_割引期間を含んだ場合割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 5, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test深夜割引_開始が1日目の割引期間内で終了が2日目の割引期間内は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 2, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 1, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test深夜割引_開始が1日目の割引期間内で終了が1日目の割引期間外は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 1, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 6, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test深夜割引_開始が1日目の割引期間外で終了が2日目の割引期間内は割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 9, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 2, 0));
        drive.setDriver(driver(5));
        drive.setRouteType(URBAN);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }
    @Test
    public void test平日朝夕割引_休日にかぶっている場合平日割引が優先される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 9, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 7, 0));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }
}
