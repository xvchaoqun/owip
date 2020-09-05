package ext.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RSASign {

	static String URL;
	static String KEY_TYPE = "SHA1withRSA";
	// static String KEY_CODE =
	// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALrAn/beCwoSGghJXqPZyLQlhkffIspHVPAJ8oB3tbrwFLrvtvVJ37iRrdRwVJMyeUekrhnmBxRzcNgePo1a15Xmfoa0PNc/gAdIE+neWekta7nICrRVSREZEcPPwMkz4C5gxY4MFmHfDF7jiIABiEQ2tE5gTM73TSLeHxbLeVFtAgMBAAECgYAuisUTFEFyEBLWz9o7QZKIevWdQdn8yYw8im4aYDs1TJIOCKHzPab4662DzT72tFSPa1ZMdAx9p0uPQPJb7jDd6CJ4fDEJP2RQuKXeZqOm5lTXB5LXg+TVh+rnX8TGxSOiflpRJTEvnHhmiOelbYxbsU/aIEL7Yd3/u8vAI4MrDQJBAPZM65jhPGGAsAcU6JtmwvRh84UXH6fLsa2aWs3AHcRBS33IXFotVhKNR1sFqLAnEMbofBYm1wilhwR+5KFFCtsCQQDCG17ovS7eOUT2kVWsLsuY4zITNg1KqTipkyn1EcLmqB+3g06S4v/ESIACi1zG879Ah/p5t3FH9T9QtScgWzNXAkEAvEFW4mdl63CdGwyULhOBd6FWrKdb/rjmeba9KP5qwhLWTLHUROjaHRv3Kk9M/Dcfz6jrD1NokKdUZ89FmS5YeQJAWuEtwpIwy2LM4xSQpBkUwfQ9kbkQ6A/qs5pvXop+UjgNAYK82xDk5yV6qbZOLegZd0EzCKHWq/YrOsJWG1QX6QJBAIOy3JWO5+Pj/rkGFwqC6oaAwcrRVq+BSTNBlvNiGi/NP3mwRywz3xgLBtKvMxM/5KLXXictnejLvzi1V8h+5G0=";
	static String KEY_CODE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDil3PZhXSwuPZZ9M4oRARVCyvNjV6Ogw2ndE+1uIzt4SAsI5SlWwp3EL37es++FWd73lLX74h5NYxrzUo+yLPc1E0eqIw6MANPtd+mAFY+SsA2FdjJN91iNorqL+HKzYGzMn3yQ8BbiNWtX3L692smTRcVsimeeNm2/EdOAddrpQIDAQAB";

	static String privateKeyUp = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOKXc9mFdLC49ln0zihEBFULK82NXo6DDad0T7W4jO3hICwjlKVbCncQvft6z74VZ3veUtfviHk1jGvNSj7Is9zUTR6ojDowA0+136YAVj5KwDYV2Mk33WI2iuov4crNgbMyffJDwFuI1a1fcvr3ayZNFxWyKZ542bb8R04B12ulAgMBAAECgYAwiEnq/DerJmK1j8acPz1CTds68p2fHpjNFg+Al5+vz7lJWvGanS5XpEFc3MgkKYd5s3vA/nAXrg1+hYDyg6BqM+lI6OHRZxPynaeQQeOiMDdTvR+pZ3b8uSx386riD1DM5d1oGRg9tMvJxQVCQU0bLrxViv2+KJf44dT3yqLJ3QJBAOwmsjpZi1BT+13IREjmdwEuTZRf7Ksffa1SvNyI+EoIpHZrM6V1w6vhRmJsENWe75qvJYy3hhem3i77O6z84oMCQQD1ow/fPTNxtM5qQKxMux3BEixp/j23ib+MIkoswmd+ARGtUEKqwnjXJWoXneXfWhMQTTJhBb5REwOEjOmv8IC3AkB09dlyMuVgHKgz07uWS6cHS7Ka2UOzoX4yePcXVzN6H3utNv02ZvRJzeJ5XsKbuwM7HqI/ZqogTsJejIoK7JkXAkAODxoudctG+8lArZju/1qxnT+rhWC0645qD+Bc9XeE77y6Rbi7G0xdTAfpeCEbCoXCzhhPE0wUSdlOsd4CMuq7AkBUD8WlEup0Ypa7oXTPGIdCOPZ+gxIWfEKBMAvi1paik+HIRfBdeaIPG1PyXrYDg8QWcZ3IrzMqLd9+jZ7XdYUF";

	static SignUtilsImpl SignUtils = new SignUtilsImpl();

	String _url = "";

	Map<String, String> _params;

	public RSASign(String url, Map<String, String> keyValueParams) {
		this._url = url;
		this._params = keyValueParams;
	}

	public static String makeRSAsign(HashMap<String, String> createMap) {
		String sourcestr = "";
		String sign = "";
		Object[] key_arr = createMap.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			sourcestr += (sourcestr.equals("") ? "" : "&") + key + "=" + createMap.get(key);
		}

		System.out.println("sourcestr=" + sourcestr);
		sign = SignUtils.sign(sourcestr, KEY_CODE, KEY_TYPE);

		sign = removeAndStr(sign);

		System.out.println("sign=" + sign);
		return sign;
	}

	private static String removeAndStr(String str) {
		String sendBuf = str;

		try {
			sendBuf = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendBuf;
	}

	public static String makeRSAsignUp(HashMap<String, String> createMap) {
		String sourcestr = "";
		String sign = "";
		Object[] key_arr = createMap.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			try {
				if( key.equals("request") || key.equals("timestamp") )
					sourcestr += key + URLDecoder.decode(createMap.get(key), "UTF-8");
				else
					sourcestr += key + createMap.get(key);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// sourcestr =
		// "acqId=04&amount=1&bizId=2016081917555948288869&chanId=1000003&merchId=99999201&sendTime=20160819175559&txnTp=20&userPhone=15601959939";
		System.out.println("sourcestr=" + sourcestr);
		sign = SignUtils.sign(sourcestr, privateKeyUp, KEY_TYPE);
		System.out.println("sign=" + sign);
		return sign;
	}
	
	public static String makeMD5Sign(HashMap<String, String> createMap) {
		String sourcestr = "";
		String sign = "";
		Object[] key_arr = createMap.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			sourcestr += (sourcestr.equals("") ? "" : "&") + key + "=" + createMap.get(key);
		}
		
		sourcestr += "&app_credential=065e792b7c0b43d08838fff7a38bc868";

		System.out.println("sourcestr=" + sourcestr);
		try {
			sign = binary2Hex(MessageDigest.getInstance("MD5").digest((sourcestr.getBytes("UTF-8"))));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println("sign=" + sign);
		return sign;
	}
	
	public static final String binary2Hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}