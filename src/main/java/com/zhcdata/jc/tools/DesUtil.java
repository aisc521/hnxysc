package com.zhcdata.jc.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {


  /**
   *
   * @param encryptString 需要加密的字符串
   * @param encryptKey    盐值 长度限制8位
   * @return
   * @throws Exception
   */
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {

		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));

		 String encodeForUrl = (new sun.misc.BASE64Encoder()).encode(encryptedData);
        //转成针对url的base64编码
        encodeForUrl = encodeForUrl.replaceAll("\\=", "!");
        encodeForUrl = encodeForUrl.replaceAll("\\+", "*");
        encodeForUrl = encodeForUrl.replaceAll("\\/", "-");
        //去除换行
        encodeForUrl = encodeForUrl.replaceAll("\\n", "");
        encodeForUrl = encodeForUrl.replaceAll("\\r", "");
        //转换*号为 -x-
        //防止有违反协议的字符
        encodeForUrl = encodeSpecialLetter(encodeForUrl);
        return encodeForUrl;
	}

	public static String decryptDES(String decryptString, String decryptKey) throws Exception {

		decryptString = decodeSpecialLetter(decryptString);
		decryptString = decryptString.replaceAll("\\!", "=");
		decryptString = decryptString.replaceAll("\\*", "+");
		decryptString = decryptString.replaceAll("\\-", "/");



		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] byteMi = decoder.decodeBuffer(decryptString);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedData = cipher.doFinal(byteMi);
		return new String(decryptedData,"UTF-8");
	}


	/**
     * 转换*号为 -x-，
                 为了防止有违反协议的字符，-x 转换为-xx
     * @param str
     * @return
     */
    private static String encodeSpecialLetter(String str){
     str = str.replaceAll("\\-x", "-xx");
     str = str.replaceAll("\\*", "-x-");
     return str;
    }

    /**
     * 转换 -x-号为*，-xx转换为-x
     * @param str
     * @return
     */
    private static String decodeSpecialLetter(String str){
     str = str.replaceAll("\\-x-", "*");
     str = str.replaceAll("\\-xx", "-x");
     return str;
    }

    public static void main(String[] args) throws Exception{
    	String str = "fUcQJhQKVpJkLpOupxqj1Xtc1CH1A-x-MoOewCEnLsdm-fUUIg1GuyMRuGaQpzBjZwkGdGp1n6uKUiexoscDloCYc6Uu-x-ZqaE8rivK7M3T8d1-U17eRApjxEhe6I9EILP8OAsNGEnxHSGS4ugbB0l799Jof6RWJu-wjXiO7edG6mnrSuyh6JDeIXOYm-q1B4Zhu-R-x-Rm4PPp2hmWKtpGsgyV0kHXn7oJt9E-x-lpm-MdPw62Fhc2yf4hBCz0Q9Og9N443zA5ev9AshFRnspPz-x-iLVFkdFjQShE4IO-x-KYLLEDcdB-xxsG-uWr6rIX7jGLe-1-x-MA3ruAhauYSH6ueMo6q-ALFpxHp2FSNzkpSzgYJ1fRLMLR7hU2qjab1HlsnNQUaqjA3MDnGT7WQwct0al-DbvfhEZwEbZ8DAs2gIqqp5fiIl2V1WHmMJpEtDArAV-x-jsdqbqywRTrfroLDXEqaWn3gC0kuW6Ce-x-s2SzGJgdQnUD9nJZxjvdl2mgceg12x2xs1l";
    	String resStr = "";
		try {
			resStr = DesUtil.decryptDES(str, "12345678");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(resStr);


    	//String str = "{\"message\":{\"head\":{\"sysType\":\"U\",\"ua\":\"iPhone Simulator -iOS8.400000\",\"src\":\"0005800001|040000\",\"messageID\":\"9999201508200955426\",\"timeStamp\":\"20150820095542\",\"transactionType\":\"10020101\",\"messengerID\":\"9999\",\"imei\":\"1\"},\"body\":{\"checkCode\":\"972643\",\"mobile\":\"13810263710\",\"password\":\"111111%\"}}}";

    	//String str="fUcQJhQKVpJkLpOupxqj1VwAi4REFZYlLKDxZGEz4dnfMDl6-0CyEVGeyk-P6ItUOxNPdFr1uG49PskICbUkkWZMpAXt2ZmXhfuMYt7-X4w7TLryEMF6f-x-ojhekbgkXHAdUJ6gWtGYqyUSph6PDI7h6WhlJWTzjsWVjx0sA6IvaWZvCVYvTOqBTVfbL3D2YLs-x-8dJVKUN75y-LmtduHDtWkBJbfOAVI3MoOBNuq-x-zC9x3rL7QW52fxVM9Txm2jxNCOpNplFuL48hLf3fm9gk8AIKh5O-GFjub46PNbi3DvQd-1MF1Pis3SLkmseRXqijn4g7oF82J1MMY8K7BZ3AQFOWwFIvoquSiNqrpZ2l5b6Yn-4uRxATY4T1kxzzfjLCkz652a3RrL03GfzcXklcE4QAZcpKStUWuUPpHrpD3ibDAmDQKwR4KMF-x-tgwKRDqrRo58Ad5YsCU7A2Kf-x-AwerTdux35OjCId";
    	//String str = "fUcQJhQKVpJkLpOupxqj1VwAi4REFZYlLKDxZGEz4dnfMDl6-0CyEVGeyk-P6ItUOxNPdFr1uG4q0M1eSNxOf2ZMpAXt2ZmXhfuMYt7-X4xvfWDjqYEe3rrwHlKWxm1UAdUJ6gWtGYqyUSph6PDI7j5pDw7632O0me2lTc6msH-x-WZvCVYvTOqHkQbRXd9YVKx4dLAeOU-x-QQ49fw-isLTq6HbSXk84bBMs56mOBYf9E1xMd-x-WmvQD7GF3-cymlPNOejN16VCg-x-pD7VkulTuhLgZDaXagb0EH7D8C8HtuGJ4Kve2fcQJ-x-HFA8dAb8Xq1o9LvzQNxOrzq-Wbymp891EzoW4yEWgOkA-x-pNrmm6aWX2FJbnVd91PCOvolXmwS1mlglzeONNOVHGIS0g4pVqmNZNzryIzdYTcc-XY4hY81yIItITJyQhC1U2RdJG8NztpmXcYISbz5IOuIihbeyGeDEq8jR5A6eOQFs9ctfV2hNFkRTWtJh3v4v-x-ghCv300v-x-V";
       // String str = "fUcQJhQKVpJkLpOupxqj1VwAi4REFZYlLKDxZGEz4dnfMDl6-0CyEVGeyk-P6ItUOxNPdFr1uG6W9vcZhr0P-GZMpAXt2ZmXhfuMYt7-X4xvfWDjqYEe3kG9jBhtbbZcAdUJ6gWtGYqyUSph6PDI7j5pDw7632O0me2lTc6msH-x-WZvCVYvTOqHkQbRXd9YVKx4dLAeOU-x-QQ49fw-isLTq6HbSXk84bBMs56mOBYf9E1xMd-x-WmvQD7GF3-cymlPNOejN16VCg-x-pD7VkulTuhLgZDaXagb0EH7D8C8HtuGJ4Kve2fcQJ-x-HFA8dAb8Xq1o9LvzQNxOrzq-Wbymp891EzoW4yEWgOkA-x-5rhUOFYg6zUhVRkTEjMEufYB-4qm8ewSy-walcN-GGB333KQ3u6vNtsew0V19unz1QoZk6tH5QV333KQ3u6vNlMT8D5kowSl1QoZk6tH5QV333KQ3u6vNmrO7EMZ-Zxqg-gXka6boUY!";
    //	String en = decryptDES(str,Constant.DES_CRYPTO_KEY);


    	//System.out.println(en);

    	/*String str = "{\"message\":{\"head\":{\"sysType\":\"M\",\"ua\":\"iPhone Simulator -iOS8.400000\",\"src\":\"0005800001|040000\",\"messageID\":\"9999201508200955426\",\"timeStamp\":\"20150820095542\",\"transactionType\":\"10070103\",\"messengerID\":\"9999\",\"imei\":\"1\"},\"body\":{\"pageNo\":\"1\",\"pageAmount\":\"20\"}}}";

    	System.out.println(DesUtil.encryptDES(str, "12345678"));*/
    }



}
