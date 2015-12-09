package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.sys.SysUserSyncService;

@Component
public class SycnUserTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserSyncService sysUserSyncService;

	//@Scheduled(cron = "0/5 * * * * ?")
	//@Scheduled(cron = "0 0/5 12-20 * * ?") // 每天18~20点，每隔半小时
	@Scheduled(cron = "0 30 23 * * ?")  // 每天23:30执行
	public void syncJZG() {

		logger.info("同步教师库...");
		try {
			sysUserSyncService.syncJZG(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void syncYJS() {

		logger.info("同步研究生库...");
		try {
			sysUserSyncService.syncYJS(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 3 * * ?")
	public void syncBks() {

		logger.info("同步本科生库...");
		try {
			sysUserSyncService.syncBks(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
