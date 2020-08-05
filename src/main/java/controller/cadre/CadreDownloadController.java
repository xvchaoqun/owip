package controller.cadre;

import controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CadreDownloadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadre_download")
    public void cadre_download(Integer cadreId,String path, String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {

        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }
}
