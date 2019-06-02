package controller.dispatch.mobile;

import controller.BaseController;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;
import sys.utils.PdfUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/m")
public class MobileDispatchWorkFileController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:dispatchWorkFile:list")
	@RequestMapping("/dispatchWorkFile")
	public String dispatchWorkFile(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:dispatchWorkFile:list")
	@RequestMapping("/dispatchWorkFile_page")
	public String dispatchWorkFile_page(ModelMap modelMap) {

		DispatchWorkFileExample example = new DispatchWorkFileExample();
		example.createCriteria().andStatusEqualTo(true);
		List<DispatchWorkFile> dispatchWorkFiles =
				dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 10));
		modelMap.put("dispatchWorkFiles", dispatchWorkFiles);

		return "dispatch/mobile/dispatchWorkFile_page";
	}

	@RequiresPermissions("m:dispatchWorkFile:list")
	@RequestMapping("/dispatchWorkFile_preview")
	public void dispatchWorkFile_preview(String path, HttpServletResponse response) throws Exception {

		String ext = FileUtils.getExtention(path); // linux区分文件名大小写
		path = FileUtils.getFileName(path) + (StringUtils.equalsIgnoreCase(ext, ".pdf")?ext:".pdf");
		String pdfFilePath = springProps.uploadPath + path;
		
		if(!FileUtils.exists(pdfFilePath)) return;
		
		String imgPath = pdfFilePath+".jpg";
		if(!FileUtils.exists(imgPath)){
			
			PdfUtils.pdf2jpg(pdfFilePath, PropertiesUtils.getString("gs.command"));
		}
		
		ImageUtils.displayImage(FileUtils.getBytes(imgPath), response);
	}
}
