package fr.ottobruc.p4_mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static Date stringsToDate(String date, String time) throws ParseException {

        // Formater la chaîne de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateDate = dateFormat.parse(date);

        // Combinez la date et l'heure
        Calendar calendar = Calendar.getInstance();
        if (dateDate != null) {
            calendar.setTime(dateDate);
        }

        if (time != null) {
            // Formater la chaîne de temps
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date timeDate = timeFormat.parse(time);

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(timeDate);
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        }

        // Obtenez la date finale
        return calendar.getTime();
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    public static boolean isTimeConflict(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }


    public static Boolean compareDate(Date date1, Date date2) throws ParseException {

        // Création de deux instances de Calendar
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        // Attribution des valeurs des dates aux instances de Calendar
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        // Comparaison des jours, mois et années
        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH);
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        // Comparaison des années
        if (year1 == year2) {
            // Les années sont les mêmes, comparer les mois
            if (month1 == month2) {
                // Les mois sont les mêmes, comparer les jours
                return day1 == day2;
            }
        }
        return false;
    }

    public static Boolean compareTime(Date time1, Date time2) throws ParseException {

        // Création de deux instances de Calendar
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        // Attribution des valeurs des dates aux instances de Calendar
        calendar1.setTime(time1);
        calendar2.setTime(time2);

        // Comparaison des jours, mois et années
        int hour1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int minute1 = calendar1.get(Calendar.MINUTE);


        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2 = calendar2.get(Calendar.MINUTE);

        // Comparaison des années
        if (hour1 == hour2) {
            // Les années sont les mêmes, comparer les mois
            return minute1 == minute2;
            }
        return false;
    }
}
