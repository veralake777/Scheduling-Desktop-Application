package utils.DateTime;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static Calendar stringToCalendar(String stringDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse(stringDate);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String[] getDaysOfWeek(int startDay) {

        String[] dayNames = new DateFormatSymbols().getShortWeekdays();
        String[] daysList = new String[7];
        int index;

        // VAR j = Sunday = 1
        int j = 1;
        // first day is today
        daysList[0] = dayNames[startDay + 1];
        daysList[1] = dayNames[startDay + 2];

        for(int i=0; i < 7; i++) {

            // IF startDay + i > dayNames
            // startDay + i = 0
            // increment until i = 7
            if(startDay + i >= dayNames.length) {
                index = j;
                j++;
                daysList[i] = dayNames[index];
            }else{
                index = startDay + i;
                daysList[i] = dayNames[index];
            }
            System.out.println(dayNames[i]);
        }
        return daysList;
    }
}
