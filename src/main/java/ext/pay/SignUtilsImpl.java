/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package ext.pay;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 *
 * 加验签工具类
 *
 * @author geng.lin
 * @version $Id: SignUtilsImpl.java, v 0.1 2012-2-24 下午02:34:17 geng.lin Exp $
 */
public class SignUtilsImpl implements SignUtils {

	
	/**
	 * 加签
	 *
	 * @param text
	 *            明文
	 * @param key
	 *            密钥
	 * @param algorithm
	 *            签名算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA,
	 *            SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
	 * @return 签名
	 * @throws Exception
	 */
	public String sign(final String text, final String key,
			final String algorithm) {
		final byte[] textBytes = text.getBytes();
		final byte[] keyBytes = Base64.decode(key);
		byte[] resultBytes = null;
		try {
			resultBytes = SignatureUtil.sign(textBytes, keyBytes, algorithm);
			return Base64.encode(resultBytes);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 验签
	 *
	 * @param text
	 *            明文
	 * @param signText
	 *            签名
	 * @param key
	 *            密钥
	 * @param algorithm
	 *            验签算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA,
	 *            SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
	 * @return 验签通过返回true，不通过返回false
	 * @throws Exception
	 */
	public boolean verify(final String text, final String signText,
			final String key, final String algorithm) {

		try {
			return SignatureUtil.verify(text.getBytes(), Base64
					.decode(signText), Base64.decode(key), algorithm);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 *      String, String)
	 */
	public String encrypt(String text, String key, String algorithm) {

		try {
			byte[] bytes = text.getBytes("UTF-8"); // 待加/解密的数据
			byte[] keyData = Base64.decode(key); // 密钥数据
			byte[] cipherBytes = SymmtricCryptoUtil.symmtricCrypto(bytes,
					keyData, algorithm, Cipher.ENCRYPT_MODE);
			return Base64.encode(cipherBytes);
		} catch (GeneralSecurityException e) {
			return "";
		} catch (UnsupportedEncodingException e1) {
			return "";
		}
	}

	/**
	 *      String, String)
	 */
	public String decrypt(String text, String key, String algorithm) {

		byte[] bytes = Base64.decode(text); // 待加/解密的数据
		byte[] keyData = Base64.decode(key); // 密钥数据

		try {
			byte[] cipherBytes = SymmtricCryptoUtil.symmtricCrypto(bytes,
					keyData, algorithm, Cipher.DECRYPT_MODE);
			return new String(cipherBytes);
		} catch (GeneralSecurityException e) {
			return "";
		}
	}

}
