package ext.sso;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

public class SSOLogin {

	private static Logger logger = LoggerFactory.getLogger(SSOLogin.class);

	public  static boolean tryLogin(String username, String passwd){
		String wsdl_url = "http://cas.bnu.edu.cn/cas/services/UnifyAuthen" ;
		//String wsdl_url = "http://219.224.19.60:7011/cas/services/UnifyAuthen" ;
		Client client = null;
		try {
			org.codehaus.xfire.service.Service service1 = new ObjectServiceFactory().create(AuthenServiceImplService.class);
			AuthenServiceImplService login = (AuthenServiceImplService) new XFireProxyFactory().create(service1, wsdl_url);
			XFireProxy proxy1 = (XFireProxy) Proxy.getInvocationHandler(login);
			client = proxy1.getClient();
			logger.info("{}  {} try login by bnu sso",wsdl_url, username);
			boolean ret = login.unifyAuthen(username, passwd);
			logger.info("{} login by bnu sso, ret={}", username, ret);
			return ret;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}finally {
			if(client!=null){
				client.close();
			}
		}
		return false;
	}

	public static void main(String[] args) {

		//unifyAuthen("09901", "bnuneusoft2014")
		/*String username = "11112010114";
		String password = "11112010114";*/
		String username = "jxliaom";
		String password = "Jxkj1416";
		boolean tryLogin = SSOLogin.tryLogin(username, password);

		System.out.println(tryLogin);
	}
}
