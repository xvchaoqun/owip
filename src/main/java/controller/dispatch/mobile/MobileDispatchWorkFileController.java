package controller.dispatch.mobile;

import controller.BaseController;
import org.apache.commons.lang3.StringUtils;
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
	public String dispatchWorkFile_page() {

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
		
		/*Document document = new Document();
		document.setFile(filePath);
		float scale = 1f;
		float rotation = 0f;
		List<BufferedImage> bufferImgList = new ArrayList<BufferedImage>();

		for (int i = 0; i < document.getNumberOfPages(); i++) {
			BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX, rotation, scale);
			bufferImgList.add(image);
		}
		document.dispose();

		BufferedImage imageResult = ImageUtils.mergeImages(bufferImgList);

		ImageIO.write(imageResult, "JPEG", response.getOutputStream());*/
	}
}
