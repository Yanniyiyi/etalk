package com.yanni.etalk.Utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by macbookretina on 28/06/15.
 */
public class ErrorHelper {
    public static void displayErrorInfo(Context context, String errorCode) {
        switch (errorCode) {
            case "1001":
                Toast.makeText(context, "必填字段不能为空", Toast.LENGTH_SHORT).show();
                break;
            case "1002":
                Toast.makeText(context, "邮箱地址无效", Toast.LENGTH_SHORT).show();
                break;
            case "1003":
                Toast.makeText(context, "手机号码无效", Toast.LENGTH_SHORT).show();
                break;
            case "1004":
                Toast.makeText(context, "保存注册信息失败", Toast.LENGTH_SHORT).show();
                break;
            case "1005":
                Toast.makeText(context, "用户已存在", Toast.LENGTH_SHORT).show();
                break;
            case "1006":
                Toast.makeText(context, "用户不存在", Toast.LENGTH_SHORT).show();
                break;
            case "1101":
                Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                break;
            case "1102":
                Toast.makeText(context, "拒绝访问，无效的token", Toast.LENGTH_SHORT).show();
                break;
            case "1103":
                Toast.makeText(context, "token丢失", Toast.LENGTH_SHORT).show();
                break;
            case "1104":
                Toast.makeText(context, "获取Token失败，请重新登录", Toast.LENGTH_SHORT).show();
                break;
            case "1105":
                Toast.makeText(context, "邮箱不存在", Toast.LENGTH_SHORT).show();
                break;
            case "1106":
                Toast.makeText(context, "找回密码邮件发送失败", Toast.LENGTH_SHORT).show();
                break;
            case "1201":
                Toast.makeText(context, "修改密码失败", Toast.LENGTH_SHORT).show();
                break;
            case "1202":
                Toast.makeText(context, "新密码与原密码相同", Toast.LENGTH_SHORT).show();
                break;
            case "1203":
                Toast.makeText(context, "生成新的token失败", Toast.LENGTH_SHORT).show();
                break;
            case "1204":
                Toast.makeText(context, "修改手机失败", Toast.LENGTH_SHORT).show();
                break;
            case "1205":
                Toast.makeText(context, "新手机号与原手机号相同", Toast.LENGTH_SHORT).show();
                break;
            case "1206":
                Toast.makeText(context, "修改QQ失败", Toast.LENGTH_SHORT).show();
                break;
            case "1301":
                Toast.makeText(context, "课程已预约满", Toast.LENGTH_SHORT).show();
                break;
            case "1302":
                Toast.makeText(context, "未找到相应上课套餐", Toast.LENGTH_SHORT).show();
                break;
            case "1303":
                Toast.makeText(context, "此套餐课程已使用完", Toast.LENGTH_SHORT).show();
                break;
            case "1304":
                Toast.makeText(context, "此套餐课程已过期", Toast.LENGTH_SHORT).show();
                break;
            case "1305":
                Toast.makeText(context, "预约课程数超出当天限制", Toast.LENGTH_SHORT).show();
                break;
            case "1306":
                Toast.makeText(context, "尚未设置课程，或您已完成所有课程", Toast.LENGTH_SHORT).show();
                break;
            case "1307":
                Toast.makeText(context, "课程预约失败", Toast.LENGTH_SHORT).show();
                break;
            case "1501":
                Toast.makeText(context, "提交评论失败", Toast.LENGTH_SHORT).show();
                break;
            case "1601":
                Toast.makeText(context, "距离上课不足一个小时或课程时间已过，不可以取消课程", Toast.LENGTH_SHORT).show();
                break;
            case "1602":
                Toast.makeText(context, "取消课程失败", Toast.LENGTH_SHORT).show();
                break;
            case "1603":
                Toast.makeText(context, "课程不存在", Toast.LENGTH_SHORT).show();
                break;
            case "1701":
                Toast.makeText(context, "订购套餐不存在", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
