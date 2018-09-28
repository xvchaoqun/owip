package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.sys.LogService;
import sys.constants.LogConstants;
import sys.tags.CmTag;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TomcatListener implements ServletContextListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		LogService logService = CmTag.getBean(LogService.class);
		logger.info(logService.addlog(LogConstants.LOG_ADMIN, "关闭tomcat"));
	}
 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		LogService logService = CmTag.getBean(LogService.class);
		logger.info(logService.addlog(LogConstants.LOG_ADMIN, "启动tomcat"));
	}
}
