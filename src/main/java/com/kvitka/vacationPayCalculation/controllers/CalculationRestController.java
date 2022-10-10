package com.kvitka.vacationPayCalculation.controllers;

import com.kvitka.vacationPayCalculation.services.VacationPayCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CalculationRestController {

    private final VacationPayCalculator simpleVacationPayCalculator;

    public CalculationRestController(VacationPayCalculator simpleVacationPayCalculator) {
        this.simpleVacationPayCalculator = simpleVacationPayCalculator;
    }

    @GetMapping("/calculacte")
    public double calculateVacationPay(@RequestParam double averageWage,
                                       @RequestParam int vacationDaysAmount,
                                       @RequestParam int vacationStartDay,
                                       @RequestParam int vacationStartMonth,
                                       @RequestParam int vacationStartYear) {
        LocalDate startDate = LocalDate.of(vacationStartYear, vacationStartMonth, vacationStartDay);
        return simpleVacationPayCalculator.calculateVacationPay(averageWage, vacationDaysAmount, startDate);
    }
}
