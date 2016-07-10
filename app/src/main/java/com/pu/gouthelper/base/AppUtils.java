package  com.pu.gouthelper.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 跟App相关的辅助类
 *
 * 
 */
public class AppUtils
{

	private AppUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 验证密码格式
	 */
	public static boolean isPassword(String psw) {

		String telRegex = "^\\d{6,12}$";
		if (TextUtils.isEmpty(psw))
			return false;
		else return psw.matches(telRegex);
	}

	/**
	 * 获取客户端版本
	 */
	public static String getVersion(Context context)// 获取版本号
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unknown";
		}
	}

	/**
	 * 获取设备信息
	 */

	public static String getDeviceData(Context c) {
		TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		String imsi = tm.getSubscriberId();
		WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = info.getMacAddress();
		Build bd = new Build();
		String devname = bd.MODEL + " " + bd.DEVICE + " " + bd.PRODUCT;


		return "Android|" + imsi + "|" + imei + "|" + devname + "|" + mac;
	}

	/**
	 * 获取IMEI
	 */

	public static String getImei(Context c) {
		TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
	}

	/**
	 * 获取版本号
	 *
	 * @param context
	 * @return
	 */
	public static int getVersionInt(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 图片
	 */
	public static Bitmap getImageOption(String path) {
		//String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/device.png";
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inTempStorage = new byte[100 * 1024];
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inPurgeable = true;
		opts.inSampleSize = 4;
		opts.inInputShareable = true;
		Bitmap btp =BitmapFactory.decodeStream(is,null, opts);
		return btp;
	}
}
