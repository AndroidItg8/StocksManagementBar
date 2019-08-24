package itg8.com.stockmanagement.common;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class CommonMethod {
    public static final String BASE_URL = "Base URL";

    public static String getDateFromDateTime(Date time) {
        SimpleDateFormat formatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return formatServer.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
