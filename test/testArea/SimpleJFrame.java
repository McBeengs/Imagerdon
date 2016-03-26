package testArea;

import java.util.Calendar;
import java.util.Locale;

public class SimpleJFrame {
    public static void main(String[] args) {
        Calendar date = Calendar.getInstance();
        String gmt;
        String hours;
        String minutes;
        String seconds;
        String millis;
        
        if (((date.get(Calendar.ZONE_OFFSET) / (1000 * 60 * 60)) % 24) < 10) {
            gmt = "GMT -0" + ((date.get(Calendar.ZONE_OFFSET) * -1 / (1000 * 60 * 60)) % 24) + "h";
        } else {
            gmt = "GMT -" + ((date.get(Calendar.ZONE_OFFSET) * -1 / (1000 * 60 * 60)) % 24) + "h";
        }
        
        if (date.get(Calendar.HOUR_OF_DAY) < 10) {
            hours = "0" + date.get(Calendar.HOUR_OF_DAY);
        } else {
            hours = "" + date.get(Calendar.HOUR_OF_DAY);
        }
        
        if (date.get(Calendar.MINUTE) < 10) {
            minutes = "0" + date.get(Calendar.MINUTE);
        } else {
            minutes = "" + date.get(Calendar.MINUTE);
        }
        
        if (date.get(Calendar.SECOND) < 10) {
            seconds = "0" + date.get(Calendar.SECOND);
        } else {
            seconds = "" + date.get(Calendar.SECOND);
        }
        
        millis = "" + date.get(Calendar.MILLISECOND);
        
        System.out.println(hours + "h, " + minutes + "min, " + seconds + "sec & " + millis + "mil (" + gmt + ")" + ", at " +
                date.getDisplayName(2, 2, Locale.US) + " " + date.get(5) + ", " + date.get(1));
    }
}
