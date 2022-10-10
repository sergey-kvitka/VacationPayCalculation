package com.kvitka.vacationPayCalculation;

import com.kvitka.vacationPayCalculation.services.VacationPayCalculator;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VacationPayCalculationApplicationTests {

    @Test
    void contextLoads() {
    }

    @AllArgsConstructor
    static class VacationCalcArgs {
        final double averageWage;
        final int vacationDaysAmount;
        final int vacationStartDay, vacationStartMonth, vacationStartYear;
        final double expectedResult;
    }

    @Autowired
    VacationPayCalculator simpleVacationPayCalculator;

    double calculateVacationPay(VacationCalcArgs args) {
        return simpleVacationPayCalculator.calculateVacationPay(args.averageWage, args.vacationDaysAmount,
                LocalDate.of(args.vacationStartYear, args.vacationStartMonth, args.vacationStartDay));
    }

    static Map<String, VacationCalcArgs> argsMap = new HashMap<>();
    static {
        argsMap.put("test1", new VacationCalcArgs(29300, 10,
                24, 10, 2022, 8000));

        argsMap.put("test2", new VacationCalcArgs(58600, 10,
                24, 10, 2022, 16000));

        argsMap.put("test3", new VacationCalcArgs(29300, 14,
                29, 12, 2022, 5000));

        argsMap.put("test4", new VacationCalcArgs(43950, 14,
                29, 12, 2022, 7500));

        argsMap.put("test5", new VacationCalcArgs(58600, 18,
                22, 2, 2023, 22000));

        argsMap.put("test6", new VacationCalcArgs(43950, 8,
                5, 12, 2022, 9000));

        argsMap.put("test7", new VacationCalcArgs(29300, 10,
                3, 11, 2022, 6000));

        argsMap.put("test8", new VacationCalcArgs(70320, 7,
                6, 3, 2023, 9600));

        argsMap.put("test9", new VacationCalcArgs(29300, 21,
                28, 12, 2026, 9000));

        argsMap.put("test10", new VacationCalcArgs(29300, 12,
                30, 4, 2030, 7000));
    }

    @Test
    void testVacationPayCalculation1() {
        VacationCalcArgs args = argsMap.get("test1");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation2() {
        VacationCalcArgs args = argsMap.get("test2");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation3() {
        VacationCalcArgs args = argsMap.get("test3");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation4() {
        VacationCalcArgs args = argsMap.get("test4");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation5() {
        VacationCalcArgs args = argsMap.get("test5");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation6() {
        VacationCalcArgs args = argsMap.get("test6");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation7() {
        VacationCalcArgs args = argsMap.get("test7");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation8() {
        VacationCalcArgs args = argsMap.get("test8");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation9() {
        VacationCalcArgs args = argsMap.get("test9");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }

    @Test
    void testVacationPayCalculation10() {
        VacationCalcArgs args = argsMap.get("test10");
        assertEquals(args.expectedResult, calculateVacationPay(args));
    }


}
