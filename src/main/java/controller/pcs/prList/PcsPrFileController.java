package controller.pcs.prList;

import controller.BaseController;
import controller.global.OpException;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrFile;
import domain.pcs.PcsPrFileExample;
import domain.pcs.PcsPrFileTemplate;
import domain.pcs.PcsPrFileTemplateExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContentTypeUtils;
import sys.utils.ContextHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrFile:list")
    @RequestMapping("/pcsPrFile")
    public String pcsPrFile(ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        {
            PcsPrFileTemplateExample example = new PcsPrFileTemplateExample();
            example.createCriteria().andConfigIdEqualTo(configId);
            example.setOrderByClause("sort_order asc");
            List<PcsPrFileTemplate> templates = pcsPrFileTemplateMapper.selectByExample(example);
            modelMap.put("templates", templates);
        }

        {
            // <templateId, file>
            Map<Integer, PcsPrFile> fileMap = new HashMap<>();
            PcsPrFileExample example = new PcsPrFileExample();
            example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
            List<PcsPrFile> files = pcsPrFileMapper.selectByExample(example);
            for (PcsPrFile file : files) {
                fileMap.put(file.getTemplateId(), file);
            }

            modelMap.put("fileMap", fileMap);
        }

        return "pcs/pcsPrFile/pcsPrFile_page";
    }

    @RequiresPermissions("pcsPrFile:edit")
    @RequestMapping(value = "/pcsPrFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFile_au(PcsPrFile record, MultipartFile _file,
                                      HttpServletRequest request) throws IOException, InterruptedException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }

        record.setPartyId(pcsAdmin.getPartyId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        record.setConfigId(configId);

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }
        String savePath = uploadPdf(_file, "pcsPrFile");
        record.setFilePath(savePath);
        record.setFileName(originalFilename);

        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setIp(ContextHelper.getRealIp());
        record.setCreateTime(new Date());

        if (record.getId() == null) {
            pcsPrFileMapper.insertSelective(record);
        } else {
            pcsPrFileMapper.updateByPrimaryKeySelective(record);
        }

        logger.info(addLog(SystemConstants.LOG_ADMIN, "上传党员大会材料：%s-%s", savePath, originalFilename));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrFile:edit")
    @RequestMapping("/pcsPrFile_au")
    public String pcsPrFile_au(Integer id, Integer templateId, ModelMap modelMap) {

        if (id != null) {
            PcsPrFile pcsPrFile = pcsPrFileMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPrFile", pcsPrFile);
            if(pcsPrFile!=null){
                templateId = pcsPrFile.getTemplateId();
            }
        }

        PcsPrFileTemplate pcsPrFileTemplate = pcsPrFileTemplateMapper.selectByPrimaryKey(templateId);
        modelMap.put("pcsPrFileTemplate", pcsPrFileTemplate);

        return "pcs/pcsPrFile/pcsPrFile_au";
    }
}
