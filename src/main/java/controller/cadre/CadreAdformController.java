package controller.cadre;

import bean.CadreInfoForm;
import controller.BaseController;
import domain.cadre.CadreView;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
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
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.ZipUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/10/29.
 */
@Controller
public class CadreAdformController extends BaseController {

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
    public Map do_cadreAdform_import(HttpServletRequest request) throws IOException, DocumentException {

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

        List<String> fails = new ArrayList<>();
        for (File file : fileList) {

            try {
                cadreAdformService.importRm(file.getAbsolutePath());
            }catch (Exception e){
                fails.add(e.getMessage());
            }
        }

        FileUtils.deleteDir(new File(tmpdir));

        Map<String, Object> resultMap = success();
        resultMap.put("totalCount", fileList.size());
        resultMap.put("fails", fails);
        return resultMap;
    }

    // 干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_download")
    public void cadreAdform_download(Integer cadreId, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreAdformService.export(cadreIds, request, response);
    }

    // 中组部干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_zzb")
    public void cadreAdform_zzb(int cadreId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " 干部任免审批表 " + cadre.getRealname();
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);

        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".lrmx"));
        response.setContentType("text/xml;charset=UTF-8");

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        cadreAdformService.zzb(adform, response.getWriter());
    }
}
