package service;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.abroad.ApplySelfService;
import service.abroad.PassportDrawService;
import service.abroad.PassportService;
import service.cadre.CadreStatHistoryService;
import service.sys.SysOnlineStaticService;
import service.sys.SysUserSyncService;

@Component
public class SycnTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserSyncService sysUserSyncService;
	@Autowired
	private SysOnlineStaticService sysOnlineStaticService;
	@Autowired
	private PassportService passportService;
	@Autowired
	private PassportDrawService passportDrawService;
	@Autowired
	private ApplySelfService applySelfService;
	@Autowired
	private SpringProps springProps;
	@Autowired
	private CadreStatHistoryService cadreStatHistoryService;

	@Scheduled(cron = "${cron.sync.cadreStatHistory}")
	public void cadreStatHistory() {

		if(BooleanUtils.isNotTrue(springProps.cadreStatHistory)){
			return;
		}

		logger.info("备份干部历史数据文件...");
		try {
			cadreStatHistoryService.saveCadreExport();

			cadreStatHistoryService.saveStatCadreExport();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 因私审批自动通知审批人
	 */
	@Scheduled(cron = "${cron.applyself.approval}")
	public void applySelfSendApprovalMsg(){

		if(springProps.applySelfSendApprovalMsg) {
			applySelfService.sendApprovalMsg();
		}
	}

	@Scheduled(cron = "${cron.online.static}")
	public void onlineStatic(){

		if(springProps.onlineStatic) {
			logger.debug("在线用户数量统计...");
			sysOnlineStaticService.stat();
		}
	}

	/*
	自动发送，发送时间为上午10点，每三天发一次，直到将证件交回。
	比如，应交组织部日期为2016年9月1日，那么从第二天9月2日开始发，每三天发一次，直到交回证件为止。
	 */
	@Scheduled(cron = "${cron.passport.draw.return}")
	public void passportDrawReturnMsg(){

		if(springProps.passportDrawReturnMsg) {
			logger.debug("领取证件之后催交证件短信通知...");
			passportDrawService.sendReturnMsg();
		}
	}

	@Scheduled(cron = "${cron.passport.expire}")
	public void passportExpire(){

		logger.info("证件过期扫描...");
		try {
			passportService.expire();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "${cron.sync.abroad}")
	public void syncAbroad() {

		if(BooleanUtils.isNotTrue(springProps.sycnAbroad)){
			return;
		}

		logger.info("同步教职工党员出国境信息...");
		try {
			sysUserSyncService.syncAllAbroad(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	//@Scheduled(cron = "0/5 * * * * ?")
	//@Scheduled(cron = "0 0/5 12-20 * * ?") // 每天18~20点，每隔半小时
	@Scheduled(cron = "${cron.sync.JZG}")
	public void syncJZG() {

		if(BooleanUtils.isNotTrue(springProps.sycnJZG)){
			return;
		}

		logger.info("同步教师库...");
		try {
			sysUserSyncService.syncAllJZG(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "${cron.sync.YJS}")
	public void syncYJS() {

		if(BooleanUtils.isNotTrue(springProps.sycnYJS)){
			return;
		}

		logger.info("同步研究生库...");
		try {
			sysUserSyncService.syncAllYJS(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "${cron.sync.BKS}")
	public void syncBks() {

		if(BooleanUtils.isNotTrue(springProps.sycnBKS)){
			return;
		}

		logger.info("同步本科生库...");
		try {
			sysUserSyncService.syncAllBks(true);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
