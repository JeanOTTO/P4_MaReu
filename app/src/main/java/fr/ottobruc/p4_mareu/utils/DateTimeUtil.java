package fr.ottobruc.p4_mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for handling date and time operations.
 */
public class DateTimeUtil {

    /**
     * Converts given date and time in string format to a Date object.
     *
     * @param date The date in string format
     * @param time The time in string format
     * @return A Date object
     * @throws ParseException if any error occurs during the conversion
     */
    public static Date stringsToDate(String date, String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateDate = dateFormat.parse(date);

        Calendar calendar = Calendar.getInstance();
        if (dateDate != null) {
            calendar.setTime(dateDate);
        }

        if (time != null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date timeDate = timeFormat.parse(time);

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(timeDate);
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        }

        return calendar.getTime();
    }

    /**
     * Checks whether two dates are the same.
     *
     * @param date1 The first date
     * @param date2 The second date
     * @return True if both dates are the same, false otherwise
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * Checks if two time intervals overlap.
     *
     * @param start1 The start of the first interval
     * @param end1 The end of the first interval
     * @param start2 The start of the second interval
     * @param end2 The end of the second interval
     * @return True if the intervals overlap, false otherwise
     */
    public static boolean isTimeConflict(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    /**
     * Compares two dates to determine if they are identical.
     *
     * @param date1 The first date
     * @param date2 The second date
     * @return True if both dates are identical, false otherwise
     * @throws ParseException if any error occurs during the conversion
     */
    public static Boolean compareDate(Date date1, Date date2) throws ParseException {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH);
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        if (year1 == year2) {
            if (month1 == month2) {
                return day1 == day2;
            }
        }
        return false;
    }

    /**
     * Compares two times to determine if they are identical.
     *
     * @param time1 The first time
     * @param time2 The second time
     * @return True if both times are identical, false otherwise
     * @throws ParseException if any error occurs during the conversion
     */
    public static Boolean compareTime(Date time1, Date time2) throws ParseException {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(time1);
        calendar2.setTime(time2);

        int hour1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int minute1 = calendar1.get(Calendar.MINUTE);

        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2 = calendar2.get(Calendar.MINUTE);

        if (hour1 == hour2) {
            return minute1 == minute2;
        }
        return false;
    }
}
