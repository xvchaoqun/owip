package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringProps {

	@Value("${page.pageSize}")
	public int pageSize;
	
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
}
