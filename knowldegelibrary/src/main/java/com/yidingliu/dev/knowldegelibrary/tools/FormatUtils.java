package com.yidingliu.dev.knowldegelibrary.tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/4.
 */
public class FormatUtils {

    private static FormatUtils instance;
    private static final String formatDefault="yyyy-MM-dd HH:mm:ss";
    private static final String formatDefault2="yyyyMMddHHmmss";

    public static FormatUtils getInstance(){
        if(instance==null){
            instance=new FormatUtils();
        }
        return instance;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public String getTrim(double value, String rules) {
        DecimalFormat df = new DecimalFormat(rules);
        return df.format(value);
    }

    public String getFormatTime(String currtimeStyle, String time, String timeStyle) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currtimeStyle);
            Date date = format.parse(time);
            return new SimpleDateFormat(timeStyle, Locale.getDefault()).format(date);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 修剪浮点类型
     * @param value value
     * @param rules 规则(如:0.00保留2位小数)
     * @return string or "" or value
     */
    public String getTrim(String value, String rules)
    {
        if(value == null || value.length() == 0 || rules == null || rules.length() == 0)
        {
            return "";
        }
        try
        {
            return getTrim(Double.parseDouble(value), rules);
        }
        catch(Exception e)
        {
            return value;
        }
    }

    public String getFormatDate(long l){
        SimpleDateFormat sdf = new SimpleDateFormat(formatDefault);
        String dateStr = sdf.format(new Date(l * getFormatDateLength(l)));
        return dateStr;
    }

    /**
     * 获取时间戳长度
     * @param l
     * @return
     */
    public long getFormatDateLength(long l){
        long dateLength=l+"".length();
        long result=1;
        for(int i=0;i<13-dateLength;i++){
            result*=10;
        }
        return result;
    }

    public String getFormatDate(long l,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(new Date(l * 1000));
        return dateStr;
    }

    public String getFormatDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat(formatDefault);
        String dateStr=sdf.format(date);
        return dateStr;
    }

    public String getFormatDate(Date date,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String dateStr=sdf.format(date);
        return dateStr;
    }


    public Date getFormatDateStr(long l){
        SimpleDateFormat sdf = new SimpleDateFormat(formatDefault);
        Date date=null;
        try {
            date=sdf.parse(getFormatDate(l));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getFormatDateStr(long l,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date=null;
        try {
            date=sdf.parse(getFormatDate(l,format));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getFormatDateStr(String dateStr){
        SimpleDateFormat sdf=new SimpleDateFormat(formatDefault);
        Date date=new Date();
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public Date getFormatDateStr(String dateStr,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Date date=new Date();
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取两点间距离,单位：px
     *
     * @param x1 第一个点
     * @param x2 第二个点
     * @return
     * @formula |AB| = sqrt((X1-X2)^2 + (Y1-Y2)^2)
     */
    public static double getFormatDistance(Point x1, Point x2) {
        return getFormatDistance(x1.x,x2.x,x1.y,x2.y);
    }


    public static double getFormatDistance(float x1,float x2, float y1,float y2) {
        float x=Math.abs(x2-x1);
        float y=Math.abs(y2-y1);
        return Math.sqrt(x * x + y * y);
    }

    /**
     * 获得字体高度
     */
    public static int getFontHeight(Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds("正", 0, 1, rect);
        return rect.height();
    }

    /**
     * 获得字体宽
     */
    public static int getFontWidth(Paint paint, String str) {
        if (str == null || str.equals(""))
            return 0;
        Rect rect = new Rect();
        int length = str.length();
        paint.getTextBounds(str, 0, length, rect);
        return rect.width();
    }

}
