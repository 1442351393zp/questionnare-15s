package com.infoac.cas.util;

import edu.thu.apm.AppResourceLoader;
import edu.thu.code.CodeUtils;
import edu.thu.global.exceptions.Exceptions;
import edu.thu.java.util.Coercions;
import edu.thu.util.FileUtils;
import edu.thu.util.StringUtils;
import edu.thu.util.StringUtilsEx;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ContentLoader {
	static boolean C = false;
	static final String A = "MVKDDF++DFLDF\"::@#~&() k =0088***^%#$";
	static final String F = "K:FD+++_(*&JN?:LDF++DFLDF\"::@#~&() k =0088***^%#$";
	static final String D = "<>LLJDF+_IM\"{}#%%BVSW!gsa452lv03(*&JNVCdecs4d--l,dgyfdnq32bd7";
	static Timestamp B;
	static SecureRandom E = new SecureRandom();

	public static String find() {
		try {
			StringBuilder localStringBuilder1 = new StringBuilder();
			Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
			while (localEnumeration.hasMoreElements()) {
				NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration.nextElement();
				byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
				if ((arrayOfByte != null) && (arrayOfByte.length == 6)) {
					StringBuilder localStringBuilder2 = new StringBuilder();
					for (int i = 0; i < 6; i++) {
						String str = String.format("%1$02X", new Object[] { Byte.valueOf(arrayOfByte[i]) });
						localStringBuilder2.append(str);
						if (i < 5) {
							localStringBuilder2.append("-");
						}
					}
					String str = localStringBuilder2.toString();
					str = CodeUtils.digestWithMD5(str);
					long l = 0L;
					for (int j = 0; j < str.length(); j += 3) {
						l += str.charAt(j);
					}
					str = Long.toHexString(l) + str.substring(0, 2);
					localStringBuilder1.append(str).append("\r\n");
				}
			}
			return localStringBuilder1.toString().trim();
		} catch (Exception localException) {
		}
		return "";
	}

	public static String kk() {
		return A("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
				"k_" + find() + "z_" + "|" + new Timestamp(System.currentTimeMillis()));
	}

	public static String uk(String paramString) {
		String str = C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$", paramString);
		if (str.indexOf('|') > 0) {
			str = str.substring(0, str.indexOf('|'));
		}
		return str.substring(2, str.length() - 2);
	}

	public static String uk2(String paramString) {
		String str1 = C("K:FD+++_(*&JN?:LDF++DFLDF\"::@#~&() k =0088***^%#$", paramString);
		String[] arrayOfString = StringUtils.split(str1, '|');
		if ((arrayOfString.length != 1) && (arrayOfString.length != 4)) {
			edu.thu.search.Query.b = false;
			System.err.println("验证失败");
		}
		if (arrayOfString.length == 4) {
			String str2 = arrayOfString[3];
			B = Coercions.toTimestamp(str2);
			if (B.getTime() < System.currentTimeMillis()) {
				edu.thu.search.Query.b = false;
				System.err.println("验证失败");
			}
		}
		str1 = arrayOfString[0];
		return str1.substring(2, str1.length() - 2);
	}

	public static String kk2(String paramString) {
		return A("K:FD+++_(*&JN?:LDF++DFLDF\"::@#~&() k =0088***^%#$", "m_" + paramString + "g_");
	}

	public static String kk2(String paramString1, String paramString2, String paramString3) {
		return A("K:FD+++_(*&JN?:LDF++DFLDF\"::@#~&() k =0088***^%#$", "m_" + paramString1 + "g_" + "|"
				+ new Timestamp(System.currentTimeMillis()) + "|" + paramString2 + "|" + paramString3);
	}

	public static synchronized boolean load() {
		C = false;
		try {
			if (C) {
				if ((B != null) && (B.getTime() < System.currentTimeMillis())) {
					edu.thu.search.Query.b = false;
					System.err.println("验证失败");
				}
				return false;
			}
			C = true;
			String str1 = System.getProperty("os.name");
			if (str1.indexOf("Windows") < 0 && str1.indexOf("Linux") < 0 ) {
				return false;
			}
			String str2 = find();
			File localFile1 = AppResourceLoader.getInstance()
					.getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
							"f3e19e5ddbd8524328c5e53d2a21c075e00310664c866b2c95baa72e8998c03919a117e14b129cbb")
									.replace('\\', '/'));
			if (!localFile1.exists()) {
				FileUtils.save(localFile1, kk());
			}
			File localFile2 = AppResourceLoader.getInstance().getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
					"f3e19e5ddbd8524328c5e53d2a21c075e00310664c866b2ce2b586f2a3fb855e"));
			if (!localFile2.exists()) {
				localFile2 = AppResourceLoader.getInstance().getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
						"92e66b228536edde749179aa0971b4b68b25959fd22ce610"));
			}
			if (!localFile2.exists()) {
				edu.thu.search.Query.b = false;
				System.err.println("验证失败");
			}
			String str3 = FileUtils.loadText(localFile2);
			str3 = StringUtils.strip(str3);
			edu.thu.search.Query.b =true;
			String str4 = uk2(str3);
			if ((!B(str2, str4)) && (!"<>LLJDF+_IM\"{}#%%BVSW!gsa452lv03(*&JNVCdecs4d--l,dgyfdnq32bd7".equals(str4))) {
				edu.thu.search.Query.b = false;
				System.err.println("验证失败");
			}if(edu.thu.search.Query.b == false) {
				System.err.println("验证失败");
			}
			else {
				edu.thu.search.Query.b = true;
				System.out.println("验证成功");
			}

		} catch (Throwable localThrowable) {
			edu.thu.search.Query.b = false;
			System.err.println("验证失败");
		}/* finally {
			System.err.println("**************************" + edu.thu.search.Query.b + "*************************");

		}*/
      return edu.thu.search.Query.b;
	}
	
	public synchronized boolean loadLicence(String licence) {
		C = false;
		System.out.println(C);
		try {
			if (C) {
				if ((B != null) && (B.getTime() < System.currentTimeMillis())) {
					edu.thu.search.Query.b = false;
					System.err.println("验证失败");
				}
				return false;
			}
			C = true;
			String str1 = System.getProperty("os.name");
			System.out.println("str1"+str1);
			if (str1.indexOf("Windows") < 0 && str1.indexOf("Linux") < 0) {
				return false;
			}
			String str2 = find();
			File localFile1 = AppResourceLoader.getInstance()
					.getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
							"f3e19e5ddbd8524328c5e53d2a21c075e00310664c866b2c95baa72e8998c03919a117e14b129cbb")
									.replace('\\', '/'));
			if (!localFile1.exists()) {
				FileUtils.save(localFile1, kk());
			}
			File localFile2 = AppResourceLoader.getInstance().getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
					"f3e19e5ddbd8524328c5e53d2a21c075e00310664c866b2ce2b586f2a3fb855e"));
			if (!localFile2.exists()) {
				localFile2 = AppResourceLoader.getInstance().getSysFile(C("MVKDDF++DFLDF\"::@#~&() k =0088***^%#$",
						"92e66b228536edde749179aa0971b4b68b25959fd22ce610"));
			}
			if (!localFile2.exists()) {
				edu.thu.search.Query.b = false;
				System.err.println("验证失败");
			}
			String str3 = FileUtils.loadText(localFile2);
			
			str3 = StringUtils.strip(str3);
			edu.thu.search.Query.b =true;
			String str4 = uk2(str3);
			if ((!B(str2, str4)) && (!"<>LLJDF+_IM\"{}#%%BVSW!gsa452lv03(*&JNVCdecs4d--l,dgyfdnq32bd7".equals(str4))) {
				edu.thu.search.Query.b = false;
				System.err.println("验证失败");
			}if(edu.thu.search.Query.b == false) {
				System.err.println("验证失败");
			}
			else {
				edu.thu.search.Query.b = true;
				System.out.println("验证成功");
			}

		} catch (Throwable localThrowable) {
			edu.thu.search.Query.b = false;
			System.err.println("验证失败");
		}/* finally {
			System.err.println("**************************" + edu.thu.search.Query.b + "*************************");

		}*/
      return edu.thu.search.Query.b;
	}

	static boolean B(String paramString1, String paramString2) {
		paramString1 = StringUtils.replace(paramString1, "\n", ",");
		paramString2 = StringUtils.replace(paramString1, "\n", ",");
		List localList1 = StringUtils.stripedSplit(paramString1, ',');
		List localList2 = StringUtils.stripedSplit(paramString2, ',');
		if ((localList2 == null) || (localList2.isEmpty())) {
			return true;
		}
		Iterator localIterator = localList1.iterator();
		while (localIterator.hasNext()) {
			Object localObject = localIterator.next();
			if (localList2.contains(localObject)) {
				return true;
			}
		}
		return false;
	}

	static String A(String paramString1, String paramString2) {
		try {
			byte[] arrayOfByte1 = paramString2.getBytes("UTF-8");
			Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec localDESKeySpec = new DESKeySpec(paramString1.getBytes("UTF-8"));
			SecretKey localSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
			IvParameterSpec localIvParameterSpec = new IvParameterSpec("12342222".getBytes());
			localCipher.init(1, localSecretKey, localIvParameterSpec);
			byte[] arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
			String str = StringUtils.toHex(arrayOfByte2);
			return str;
		} catch (Exception localException) {
			throw Exceptions.source(localException);
		}
	}

	static String C(String paramString1, String paramString2) {
		try {
			byte[] arrayOfByte1 = StringUtilsEx.fromHex(paramString2);
			Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec localDESKeySpec = new DESKeySpec(paramString1.getBytes("UTF-8"));
			SecretKey localSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
			IvParameterSpec localIvParameterSpec = new IvParameterSpec("12342222".getBytes());
			localCipher.init(2, localSecretKey, localIvParameterSpec);
			byte[] arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
			paramString2 = new String(arrayOfByte2, "UTF-8");
			return paramString2;
		} catch (Exception localException) {
			throw Exceptions.source(localException);
		}
	}

	public static void main(String[] paramArrayOfString) throws Exception {

		System.out.println(ContentLoader.load());
	}
}