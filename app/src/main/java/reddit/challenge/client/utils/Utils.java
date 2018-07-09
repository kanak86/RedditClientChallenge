package reddit.challenge.client.utils;

/**
 * Created by apoorvakanaksiwach on 2/4/18.
 */

public class Utils {

    public static final String BASE_URL = "https://www.reddit.com/";

    public static String convertMillisToHours(Double millis) {
        double hours;
        double seconds;

        seconds = millis/1000;
        hours = seconds/(60*60*60);

        return Double.toString(hours);
    }
}
