package id.aliqornan.seefavoritemovie;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    SimpleDateFormat sdf;
    Calendar calendar;

    public DateFormatter(String format) {
        sdf = new SimpleDateFormat(format, Locale.getDefault());
        calendar = Calendar.getInstance();
    }

    public static DateFormatter format(String format) {
        return new DateFormatter(format);
    }

    public Calendar getCalendar(String source) throws Exception {
        return getCalendar(parseToDate(source));
    }

    public Calendar getCalendar(Date source) throws Exception {
        calendar.setTime(source);
        return calendar;
    }

    public Date parseToDate(String source) throws Exception {
        Date result = sdf.parse(source);
        return result;
    }

    public String parseToString(Date source) throws Exception {
        return sdf.format(source);
    }

}
