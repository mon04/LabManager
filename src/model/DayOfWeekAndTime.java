package model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DayOfWeekAndTime {

    private DayOfWeek day;
    private LocalTime time;

    public DayOfWeekAndTime(DayOfWeek day, LocalTime time) {
        this.day = day;
        this.time = time;
    }

    public DayOfWeekAndTime(int dayNumber, LocalTime time) {
        this.day = DayOfWeek.of(dayNumber);
        this.time = time;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public int getDayAsInt() {
        return day.getValue();
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
