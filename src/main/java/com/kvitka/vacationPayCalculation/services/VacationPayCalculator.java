package com.kvitka.vacationPayCalculation.services;

import com.kvitka.vacationPayCalculation.interfaces.DayAndMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VacationPayCalculator {

    public static final List<ImmutableDayAndMonth> HOLIDAYS = List.of(
            new ImmutableDayAndMonth("01.01"), new ImmutableDayAndMonth("02.01"),
            new ImmutableDayAndMonth("03.01"), new ImmutableDayAndMonth("04.01"),
            new ImmutableDayAndMonth("05.01"), new ImmutableDayAndMonth("06.01"),
            new ImmutableDayAndMonth("07.01"), new ImmutableDayAndMonth("08.01"),
            new ImmutableDayAndMonth("23.02"), new ImmutableDayAndMonth("08.03"),
            new ImmutableDayAndMonth("01.05"), new ImmutableDayAndMonth("09.05"),
            new ImmutableDayAndMonth("12.06"), new ImmutableDayAndMonth("04.11")
    );
    public static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    public static final int MAX_VACATION_PERIOD = 28;
    public static final double AVERAGE_MONTH_LENGTH = 29.3;

    private int maxVacationPeriod;
    private double averageMonthLength;
    private final List<DayOfWeek> weekends = new ArrayList<>();
    private final List<ImmutableDayAndMonth> holidays = new ArrayList<>();

    @Autowired
    public VacationPayCalculator() {
        this(MAX_VACATION_PERIOD, HOLIDAYS, WEEKENDS, AVERAGE_MONTH_LENGTH);
    }

    public VacationPayCalculator(int maxVacationPeriod, List<ImmutableDayAndMonth> holidays) {
        this(maxVacationPeriod, holidays, WEEKENDS, AVERAGE_MONTH_LENGTH);
    }

    public VacationPayCalculator(int maxVacationPeriod, List<ImmutableDayAndMonth> holidays,
                                 List<DayOfWeek> weekends) {
        this(maxVacationPeriod, holidays, weekends, AVERAGE_MONTH_LENGTH);
    }

    public VacationPayCalculator(int maxVacationPeriod, List<ImmutableDayAndMonth> holidays,
                                 List<DayOfWeek> weekends, double averageMonthLength) {
        if (averageMonthLength <= 0)
            throw new IllegalArgumentException("Average month length must be greater than zero");
        if (maxVacationPeriod < 1)
            throw new IllegalArgumentException("Max vacation period must be at least 1 day");

        this.holidays.addAll(holidays.stream().distinct().collect(Collectors.toList()));
        this.weekends.addAll(weekends.stream().distinct().collect(Collectors.toList()));
        this.weekends.clear();
        this.weekends.addAll(weekends);
        this.maxVacationPeriod = maxVacationPeriod;
        this.averageMonthLength = averageMonthLength;
    }

    public int getMaxVacationPeriod() {
        return maxVacationPeriod;
    }

    public void setMaxVacationPeriod(int maxVacationPeriod) {
        this.maxVacationPeriod = maxVacationPeriod;
    }

    public double getAverageMonthLength() {
        return averageMonthLength;
    }

    public void setAverageMonthLength(double averageMonthLength) {
        this.averageMonthLength = averageMonthLength;
    }

    public List<DayOfWeek> getWeekends() {
        return new ArrayList<>(weekends);
    }

    public void setWeekends(List<DayOfWeek> weekends) {
        this.weekends.clear();
        this.weekends.addAll(weekends);
    }

    public List<ImmutableDayAndMonth> getHolidays() {
        return new ArrayList<>(holidays);
    }

    public void setHolidays(List<ImmutableDayAndMonth> holidays) {
        this.holidays.clear();
        this.holidays.addAll(holidays);
    }

    public double calculateVacationPay(double averageWage, int vacationDaysAmount, LocalDate vacationStart) {
        if (averageWage <= 0)
            throw new IllegalArgumentException("Average wage must be greater than zero");
        if (vacationDaysAmount < 1)
            throw new IllegalArgumentException("The amount of vacation days must be at least 1");
        if (vacationDaysAmount > maxVacationPeriod) throw new IllegalArgumentException(
                "The amount of vacation days is too big (" + vacationDaysAmount + " > " + maxVacationPeriod + ")");

        LocalDate date = LocalDate.of(vacationStart.getYear(), vacationStart.getMonth(), vacationStart.getDayOfMonth());
        double paidVacationDays = 0;

        for (int i = 0; i < vacationDaysAmount; i++) {
            if (!weekends.contains(date.getDayOfWeek()) && !holidays.contains(
                    new ImmutableDayAndMonth(date.getDayOfMonth(), date.getMonth().getValue()))) {
                paidVacationDays++;
            }
            date = date.plusDays(1);
        }

        return paidVacationDays * (averageWage / averageMonthLength);
    }

    public static final class ImmutableDayAndMonth implements DayAndMonth {

        private final LocalDate date;
        private static final int YEAR = LocalDate.MIN.getYear();
        private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH)
                .appendLiteral('.')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral('.')
                .appendValue(ChronoField.YEAR)
                .toFormatter();

        /**
         * @param day   the number of the day of the month (1-[28-31], depends on the month)
         * @param month the number of the month (1-12)
         * @throws DateTimeException if the value of any field is out of range,
         *                           or if the day is invalid for the month
         */
        public ImmutableDayAndMonth(int day, int month) throws DateTimeException {
            date = LocalDate.of(YEAR, month, day);
        }

        /**
         * @param dayAndMonth the string which consists of:
         *                                       <ul>
         *                                                          <li>the number of the day of the month (1-[28-31], depends on the month)
         *                                                          <li>a period
         *                                                          <li>the number of the month (1-12)
         *                                       </ul>
         *                    e.g. "28.09", "15.02"
         * @throws DateTimeParseException if the text cannot be parsed
         */
        public ImmutableDayAndMonth(String dayAndMonth) throws DateTimeParseException {
            date = LocalDate.parse(dayAndMonth + "." + YEAR, DATE_TIME_FORMATTER);
        }

        @Override
        public int getDay() {
            return date.getDayOfMonth();
        }

        @Override
        public int getMonth() {
            return date.getMonth().getValue();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImmutableDayAndMonth that = (ImmutableDayAndMonth) o;
            return getDay() == that.getDay() && getMonth() == that.getMonth();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getDay(), getMonth());
        }

        @Override
        public String toString() {
            int month = getMonth();
            return getDay() + "." + (month > 10 ? "" : "0") + month;
        }
    }
}
