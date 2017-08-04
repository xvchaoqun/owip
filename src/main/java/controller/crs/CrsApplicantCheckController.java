package controller.crs;

import controller.BaseController;
import controller.global.OpException;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantCheck;
import domain.crs.CrsPost;
import domain.crs.CrsPostRequire;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.utils.ContentTypeUtils;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class CrsApplicantCheckController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping(value = "/crsApplicant_infoCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_infoCheck(int id, boolean status, String remark) {

        crsApplicantCheckService.infoCheck(id, status, remark);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping("/crsApplicant_infoCheck")
    public String crsApplicant_infoCheck(int applicantId, ModelMap modelMap) {

        CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
        modelMap.put("crsApplicant", crsApplicant);

        return "crs/crsApplicant/crsApplicant_infoCheck";
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping("/crsApplicant_requireCheck")
    public String crsApplicant_requireCheck(int applicantId, ModelMap modelMap) {

        CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
        modelMap.put("crsApplicant", crsApplicant);

        if(crsApplicant!=null) {

            Map<Byte, String> realValMap = crsPostRequireService.getRealValMap(crsApplicant.getUserId());
            modelMap.put("realValMap", realValMap);

            Map<Integer, CrsApplicantCheck> checkMap = crsApplicantCheckService.getRuleCheckMap(crsApplicant.getId());
            modelMap.put("checkMap", checkMap);

            CrsPost crsPost = crsPostService.get(crsApplicant.getPostId());
            if (crsPost != null) {
                CrsPostRequire crsPostRequire = crsPostRequireMapper.selectByPrimaryKey(crsPost.getPostRequireId());
                modelMap.put("crsPostRequire", crsPostRequire);
            }
        }

        return "crs/crsApplicant/crsApplicant_requireCheck";
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping(value = "/crsApplicant_requireCheckItem", method = RequestMethod.POST)
    @ResponseBody
    public Map crsApplicant_requireCheckItem(int applicantId, int ruleId, boolean status) {

        crsApplicantCheckService.ruleCheck(applicantId, ruleId, status);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping(value = "/crsApplicant_requireCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_requireCheck(int id, boolean status, String remark) {

        crsApplicantCheckService.requireCheck(id, status, remark);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping("/crsApplicant_special")
    public String crsApplicant_special(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            modelMap.put("crsApplicant", crsApplicant);
        }
        return "crs/crsApplicant/crsApplicant_special";
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping(value = "/crsApplicant_special", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_special(CrsApplicant record,
                                         String file,
                                         HttpServletRequest request) {

        boolean specialStatus = BooleanUtils.isTrue(record.getSpecialStatus());
        crsApplicantCheckService.special(record.getId(), specialStatus,
                file, record.getSpecialRemark());

        logger.info(addLog(SystemConstants.LOG_ADMIN, (specialStatus ? "" : "取消") + "破格操作：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    private String uploadFile(MultipartFile _file) {

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");

        String fileName = UUID.randomUUID().toString();
        String realPath = FILE_SEPARATOR
                + "crs_applicant_special" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                + "file" + FILE_SEPARATOR
                + fileName;
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

        try {
            String swfPath = realPath + ".swf";
            pdf2Swf(savePath, swfPath);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }

        return savePath;
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping(value = "/crsApplicant_special_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_special_upload(MultipartFile file) throws InterruptedException {

        String savePath = uploadFile(file);
        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", savePath);

        return resultMap;
    }
}
