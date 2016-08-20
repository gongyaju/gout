package  com.pu.gouthelper.base;

import android.text.TextUtils;
import android.util.FloatMath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private final static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    static {
        numberFormat.setMaximumIntegerDigits(9);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * URL解码
     *
     * @param str
     * @return
     */
    public static String decodeURL(String str) {
        return URLDecoder.decode(str);
    }

    public static String enCodeRUL(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception ex) {
        }
        return str;
    }

    public static boolean isEmpty(Object str) {
        return str == null || str.toString().length() == 0;
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /* 去掉时间为00:00:00 */
    public static String replaceTimeZero(String date) {
        if (date != null) {
            if (date.indexOf("00:00:00") > 0) {
                date = date.replaceAll("00:00:00", "");
            } else if (date.indexOf(":00") == 16) {
                date = date.substring(0, 16);
            }
        }
        return date;
    }

    public static boolean startWithHttp(Object str) {
        return str != null
                && str.toString().toLowerCase().startsWith("http://");
    }

    /* 字符串截取 防止出现半个汉字 */
    public static String truncate(String str, int byteLength) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        if (byteLength < 0) {
            throw new IllegalArgumentException(
                    "Parameter byteLength must be great than 0");
        }
        int i = 0;
        int len = 0;
        int leng = 0;
        char[] chs = str.toCharArray();
        try {
            leng = str.getBytes("gbk").length;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (leng <= byteLength)
            return str;
        while ((len < byteLength) && (i < leng)) {
            len = (chs[i++] > 0xff) ? (len + 2) : (len + 1);
        }

        if (len > byteLength) {
            i--;
        }
        return new String(chs, 0, i) + "...";
    }

    /**
     * 分割keyword 按最后一个出现的@分割
     *
     * @param data
     * @return keyword
     */
    public static String splitKeyWord(String data) {
        if (data == null || data.length() == 0)
            return null;
        if (data.lastIndexOf("@") == -1)
            return data;
        return data.substring(0, data.lastIndexOf("@"));
    }


    /**
     * 确定是否是时间戳
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }



    /**
     * 截取年月日 如（2013-01-08）
     *
     * @param data
     * @return yyyy-MM-dd
     */
    public static String toNYR(long data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        try {
            return dateFormat.format(data);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 抽正文标题
     *
     * @param str
     * @return
     */
    public static String setReadabilityTitle(String str) {
        String res = null;
        if (str != null)
            if (str.length() > 9)
                res = str.substring(0, 3) + "..."
                        + str.substring(str.length() - 3, str.length());
        return res == null ? str : res;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将list中的字符串用split间隔开
     *
     * @param list
     * @param split
     * @return
     */
    public static String Join(List<String> list, String split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i + 1 != list.size()) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static String toPriceStr(double price) {
        return "￥" + numberFormat.format(price);
    }

    public static String toDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


    /**
     * 流转字符串方法
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Unicode转UTF-8
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNum(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
	    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else return mobiles.matches(telRegex) && mobiles.length() == 11;
    }
}
