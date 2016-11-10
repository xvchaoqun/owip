package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringProps {

	@Value("${page.pageSize}")
	public int pageSize;

	@Value("${m.page.pageSize}")
	public int mPageSize;
	
	@Value("${upload.path}")
	public String uploadPath;

	@Value("${avatar.folder}")
	public String avatarFolder;

	@Value("${avatar.default}")
	public String defaultAvatar;
	
	@Value("${swfTools.command}")
	public String swfToolsCommand;

	@Value("${username.regex}")
	public String usernameRegex;

	@Value("${login.useSSOLogin}")
	public Boolean useSSOLogin;

	@Value("${login.useCaptcha}")
	public Boolean useCaptcha;

	@Value("${shortMsg.send}")
	public Boolean shortMsgSend;

	@Value("${shortMsg.url}")
	public String shortMsgUrl;

	@Value("${switch.sycn.ABROAD}")
	public Boolean sycnAbroad;

	@Value("${switch.sycn.JZG}")
	public Boolean sycnJZG;

	@Value("${switch.sycn.BKS}")
	public Boolean sycnBKS;

	@Value("${switch.sycn.YJS}")
	public Boolean sycnYJS;

	@Value("${switch.online.static}")
	public Boolean onlineStatic;

	@Value("${switch.passport.draw.return}")
	public Boolean passportDrawReturnMsg;

	@Value("${switch.applyself.approval}")
	public Boolean applySelfSendApprovalMsg;
	@Value("${switch.applyself.approval.next}")
	public Boolean applySelfSendNextApprovalMsg;
}
