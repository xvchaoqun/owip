package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringProps {

	@Value("${devMode}")
	public Boolean devMode;

	@Value("${db.schema}")
	public String schema;

	@Value("${page.pageSize}")
	public int pageSize;

	@Value("${m.page.pageSize}")
	public int mPageSize;
	
	@Value("${upload.path}")
	public String uploadPath;

	@Value("${avatar.folder}")
	public String avatarFolder;

	@Value("${avatar.folder.ext}")
	public String avatarFolderExt;

	@Value("${swfTools.command}")
	public String swfToolsCommand;

	@Value("${swfTools.languagedir}")
	public String swfToolsLanguagedir;

	@Value("${login.useSSO}")
	public Boolean useSSO;

	@Value("${login.useCAS}")
	public Boolean useCAS;

	@Value("${login.useCaptcha}")
	public Boolean useCaptcha;

	@Value("${shortMsg.send}")
	public Boolean shortMsgSend;
}
