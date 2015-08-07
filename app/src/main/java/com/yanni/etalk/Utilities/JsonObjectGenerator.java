package com.yanni.etalk.Utilities;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by macbookretina on 27/06/15.
 */
public class JsonObjectGenerator {
    public static JSONObject createLoginJsonObject(String username, String password) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", username);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createLogOutJsonObject(String userId) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createRegisterJsonObject(String userName, String password, String email,
                                                      String phoneNum, String inviteCode) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", userName);
            obj.put("password", password);
            obj.put("email", email);
            obj.put("mobile", phoneNum);
            obj.put("invite_code", inviteCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createChangePasswordJsonObject(String userId, String password, String newPassword) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", userId);
            obj.put("password", password);
            obj.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createChangePhoneNumJsonObject(String prefUserId, String originalPhoneNum, String newPhoneNum) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("mobile", originalPhoneNum);
            obj.put("new_mobile", newPhoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;

    }

    public static JSONObject createChangeQQNumJsonObject(String prefUserId, String newQQNum) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("qq", newQQNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFindPasswordJsonObject(String email) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFetchBookCourseJsonObject(String prefUserId, String page, String pageSize) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("page", page);
            obj.put("page_size", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFetchToCommentJsonObject(String prefUserId, String page, String pageSize) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("page", page);
            obj.put("page_size", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createCommentJsonObject(String prefUserId, String courseId, String commentContent, float score) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("course_id", courseId);
            obj.put("comment", commentContent);
            obj.put("score", Float.toString(score));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFetchMonthRecordJsonObject(String prefUserId, String month) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", prefUserId);
            obj.put("bespeak_month", month);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFetchDailyRecordJsonObject(String userName, String courseDate) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", userName);
            obj.put("bespeak_date", courseDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createCancelCourseJsonObject(String prefUserId, String courseId) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("course_id", courseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createFetchBookClassTimeJsonObject(String userID, String productId, String date) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", userID);
            obj.put("product_id", productId);
            obj.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject createBookClassJsonObject(String prefUserId, String packageId, String time, String lm) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", prefUserId);
            obj.put("package_id", packageId);
            obj.put("time", time);
            obj.put("way", "1");
            obj.put("lm", lm);
            obj.put("count", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
