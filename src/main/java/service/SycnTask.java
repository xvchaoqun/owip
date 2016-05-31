package service;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.abroad.PassportService;
import service.sys.SysUserSyncService;
import sys.utils.PropertiesUtils;

@Component
public class SycnTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserSyncService sysUserSyncService;
	@Autowired
	private PassportService passportService;
	@Autowired
	private SpringProps springProps;

	//@Scheduled(cron = "0/5 * * * * ?")
	@Scheduled(cron = "0 30 1 * * ?")
	public void passportExpire(){

		logger.info("证件过期扫描...");
		try {
			passportService.expire();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 23 * * ?")  // 每天23:00执行
	public void syncAbroad() {

		if(BooleanUtils.isFalse(springProps.sycnAbroad)){
			return;
		}

		logger.info("同步教职工党员出国境信息...");
		try {
			sysUserSyncService.syncAbroad(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	//@Scheduled(cron = "0/5 * * * * ?")
	//@Scheduled(cron = "0 0/5 12-20 * * ?") // 每天18~20点，每隔半小时
	@Scheduled(cron = "0 30 23 * * ?")  // 每天23:30执行
	public void syncJZG() {

		if(BooleanUtils.isFalse(springProps.sycnJZG)){
			return;
		}

		logger.info("同步教师库...");
		try {
			sysUserSyncService.syncJZG(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void syncYJS() {

		if(BooleanUtils.isFalse(springProps.sycnYJS)){
			return;
		}

		logger.info("同步研究生库...");
		try {
			sysUserSyncService.syncYJS(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 3 * * ?")
	public void syncBks() {

		if(BooleanUtils.isFalse(springProps.sycnBKS)){
			return;
		}

		logger.info("同步本科生库...");
		try {
			sysUserSyncService.syncBks(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
