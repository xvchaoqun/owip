package shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import sys.shiro.SaltPassword;


public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    public RetryLimitHashedCredentialsMatcher getCredentialsMatcher() {
        return credentialsMatcher;
    }

    public void setCredentialsMatcher(RetryLimitHashedCredentialsMatcher credentialsMatcher) {
        this.credentialsMatcher = credentialsMatcher;
    }

    public String encryptBySalt(String password, String salt) {

        return new SimpleHash(
                credentialsMatcher.getHashAlgorithmName(),
                password,
                ByteSource.Util.bytes(salt),
                credentialsMatcher.getHashIterations()).toHex();
    }

    public SaltPassword encryptByRandomSalt(String password) {

        SaltPassword saltPassword = new SaltPassword();
        saltPassword.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = encryptBySalt(password, saltPassword.getSalt());
        saltPassword.setPassword(newPassword);

        return saltPassword;
    }
}
