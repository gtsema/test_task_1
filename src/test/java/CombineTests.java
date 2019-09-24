import main.Price;
import main.PriceExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CombineTests {

    private List<Price> oldPrices = new ArrayList<>();
    private List<Price> newPrices = new ArrayList<>();
    private List<Price> actual = new ArrayList<>();
    private List<Price> expected = new ArrayList<>();

    @BeforeEach
    private void clear() {
        oldPrices.clear();
        newPrices.clear();
        actual.clear();
        expected.clear();
    }

    @Test
    void differentCodesPrices_twoOldPrices() {
        Price p1 = PriceExecutor.getPrice("12065", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 5000);
        Price p2 = PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 7000);

        oldPrices.add(p1);
        newPrices.add(p2);

        expected.add(p1);
        expected.add(p2);

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void differentNumbersPrices_twoOldPrices() {
        Price p1 = PriceExecutor.getPrice("6654", 2, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 5000);
        Price p2 = PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 7000);

        oldPrices.add(p1);
        newPrices.add(p2);

        expected.add(p1);
        expected.add(p2);

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void differentDepartsPrices_twoOldPrices() {
        Price p1 = PriceExecutor.getPrice("6654", 1, 1, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 5000);
        Price p2 = PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 7000);

        oldPrices.add(p1);
        newPrices.add(p2);

        expected.add(p1);
        expected.add(p2);

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void noOverlapPrices_twoOldPrices() {
        Price p1 = PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 5000);
        Price p2 = PriceExecutor.getPrice("6654", 1, 2, "12.01.2013 00:00:00", "15.01.2013 23:59:59", 7000);

        oldPrices.add(p1);
        newPrices.add(p2);

        expected.add(p1);
        expected.add(p2);

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void newOverlapRight_twoNewPrices() {
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 5000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        expected.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "07.01.2013 23:59:59", 5000));
        expected.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void oldEquallyNew_newPrice() {
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 5000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        expected.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void newOverlapLeft_twoNewPrices() {
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "15.01.2013 23:59:59", 5000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 7000));

        expected.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59", 7000));
        expected.add(PriceExecutor.getPrice("6654", 1, 2, "11.01.2013 00:00:00", "15.01.2013 23:59:59", 5000));

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void oldInsideNew_threeNewPrices() {
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 5000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "10.01.2013 23:59:59", 7000));

        expected.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "07.01.2013 23:59:59", 5000));
        expected.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "10.01.2013 23:59:59", 7000));
        expected.add(PriceExecutor.getPrice("6654", 1, 2, "11.01.2013 00:00:00", "15.01.2013 23:59:59", 5000));

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void newInsideOld_newPrice() {
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "08.01.2013 00:00:00", "10.01.2013 23:59:59", 5000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        expected.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "15.01.2013 23:59:59", 7000));

        actual = PriceExecutor.combine(oldPrices, newPrices);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(actual, expected));
    }

    @Test
    void illegalStartDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "lol.01.2013 00:00:00", "10.01.2013 23:59:59", 5000));
        });

        Assertions.assertEquals("Illegal start date.", thrown.getMessage());
    }

    @Test
    void illegalEndDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", "l0.01.2013 23:59:59", 5000));
        });

        Assertions.assertEquals("Illegal end date.", thrown.getMessage());
    }

    @Test
    void nullStartDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, null, "l0.01.2013 23:59:59", 5000));
        });

        Assertions.assertEquals("Illegal start date.", thrown.getMessage());
    }

    @Test
    void nullEndDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00", null, 5000));
        });

        Assertions.assertEquals("Illegal end date.", thrown.getMessage());
    }

    @Test
    void emptyStartDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "", "l0.01.2013 23:59:59", 5000));
        });

        Assertions.assertEquals("Illegal start date.", thrown.getMessage());
    }

    @Test
    void emptyEndDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "02.01.2013 00:00:00","",  5000));
        });

        Assertions.assertEquals("Illegal end date.", thrown.getMessage());
    }

    @Test
    void emptyProductCode_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("", 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59",  5000));
        });

        Assertions.assertEquals("Illegal product code.", thrown.getMessage());
    }

    @Test
    void nullProductCode_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice(null, 1, 2, "02.01.2013 00:00:00", "10.01.2013 23:59:59",  5000));
        });

        Assertions.assertEquals("Illegal product code.", thrown.getMessage());
    }

    @Test
    void startDateOverEndDate_IllegalArgumentException() {
        Throwable thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "10.01.2013 00:00:00", "02.01.2013 23:59:59",  5000));
        });

        Assertions.assertEquals("Illegal date. Start date more end date.", thrown.getMessage());
    }
}
