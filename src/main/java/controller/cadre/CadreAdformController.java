package controller.cadre;

import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.cadre.CadreAdformService;
import sys.constants.LogConstants;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.ZipUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/10/29.
 */
@Controller
public class CadreAdformController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CadreAdformService cadreAdformService;

    @RequiresPermissions("cadreAdform:list")
    @RequestMapping("/cadreAdform_page")
    public String cadreAdform_page(
            @RequestParam(defaultValue = 1 + "") Byte type,
            int cadreId, ModelMap modelMap) {

        modelMap.put("type", type);

        modelMap.put("bean", cadreAdformService.getCadreAdform(cadreId));

        return "cadre/cadreAdform/cadreAdform_page";
    }

    @RequiresPermissions("cadreAdform:import")
    @RequestMapping("/cadreAdform_import")
    public String cadreAdform_import() {

        return "cadre/cadreAdform/cadreAdform_import";
    }

    @RequiresPermissions("cadreAdform:import")
    @RequestMapping(value = "/cadreAdform_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdform_import(Boolean importResume, HttpServletRequest request) throws IOException, DocumentException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        List<File> fileList = new ArrayList<>();
        String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "lrmx";;
        String filepath = null;

        MultipartFile lrmx = multipartRequest.getFile("lrmx");
        if(lrmx!=null) {

            FileUtils.mkdirs(tmpdir, false);

            filepath = tmpdir + FILE_SEPARATOR + lrmx.getOriginalFilename();
            FileUtils.copyFile(lrmx, new File(filepath));
            fileList.add(new File(filepath));
        }

        MultipartFile zip = multipartRequest.getFile("zip");

        if(zip!=null) {

            String zipDir = tmpdir + FILE_SEPARATOR + "zip";
            FileUtils.mkdirs(tmpdir, false);

            filepath = zipDir + FILE_SEPARATOR + zip.getOriginalFilename();
            FileUtils.copyFile(zip, new File(filepath));

            String destDir = tmpdir + FILE_SEPARATOR + "unzip";

            //FileUtils.mkdirs(destDir, false);
            ZipUtils.unzipFile(filepath, destDir, Charset.forName("gbk"));

            // 遍历 destDir
            FileUtils.listFiles(fileList, destDir, "^.*\\.lrmx$");
        }

        importResume = BooleanUtils.isTrue(importResume);

        List<String> fails = new ArrayList<>();
        for (File file : fileList) {

            try {
                cadreAdformService.importRm(file.getAbsolutePath(), importResume);
            }catch (Exception e){
                logger.error(file.getName(), e);
                fails.add(e.getMessage());
            }
        }

        FileUtils.deleteDir(new File(tmpdir));

        int totalCount = fileList.size();
        Map<String, Object> resultMap = success();
        resultMap.put("totalCount", totalCount);
        resultMap.put("fails", fails);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部任免审批表成功，总共{0}份，其中成功导入{1}份",
                totalCount, totalCount - fails.size()));

        return resultMap;
    }

    // 干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_download")
    public void cadreAdform_download(Integer cadreId,
                                     Boolean isWord, // 否： 中组部格式
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, TemplateException, DocumentException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreAdformService.export(cadreIds, BooleanUtils.isTrue(isWord), request, response);
    }
}
