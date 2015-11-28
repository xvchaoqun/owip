package service.party;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class ApplicationContextSupport implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		System.out.println("ApplicationContext init..." + contex);
		this.context=contex; 
	}
	public static ApplicationContext getContext(){ 
	  return context; 
	} 
}
