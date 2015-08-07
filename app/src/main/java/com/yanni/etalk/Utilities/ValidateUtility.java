package com.yanni.etalk.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by macbookretina on 16/07/15.
 */
public class ValidateUtility {
    private static final String V_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    private static final String V_MOBILE = "^(1)[0-9]{10}$";

    private static final String V_NOTEMPTY = "^\\S+$";

    private static final String V_PASSWORD_LENGTH = "^\\d{6,18}$";

    private static final String V_QQ_NUMBER = "^[1-9]*[1-9][0-9]*$";

    public static boolean Email(String value) {
        return match(V_EMAIL, value);
    }

    public static boolean PhoneNum(String value) {
        return match(V_MOBILE, value);
    }

    public static boolean QQNum(String value) {
        return match(V_QQ_NUMBER, value);
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
