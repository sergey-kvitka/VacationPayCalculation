package com.kvitka.vacationPayCalculation;

import com.kvitka.vacationPayCalculation.services.VacationPayCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VacationPayCalculationApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    VacationPayCalculator simpleVacationPayCalculator;

    //eg http://localhost:8080/calculacte?averageWage=29300&vacationDaysAmount=14&vacationStartDay=30&vacationStartMonth=12&vacationStartYear=2022

    @Test
    void testVacationPayCalculation1() {
        double avgWage = 29300;
        int vacationDaysAmount = 10;
        int vacationStartDay = 24;
        int vacationStartMonth = 10;
        int vacationStartYear = 2022;

        assertEquals(8000, simpleVacationPayCalculator.calculateVacationPay(
                avgWage, vacationDaysAmount, LocalDate.of(vacationStartYear, vacationStartMonth, vacationStartDay)));
    }
}
