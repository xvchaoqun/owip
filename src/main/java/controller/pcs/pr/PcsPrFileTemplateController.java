package controller.pcs.pr;

import controller.BaseController;
import controller.global.OpException;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrFileTemplate;
import domain.pcs.PcsPrFileTemplateExample;
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
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrFileTemplateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrFileTemplate:list")
    @RequestMapping("/pcsPrFileTemplate")
    public String pcsPrFileTemplate(ModelMap modelMap) {
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPrFileTemplateExample example = new PcsPrFileTemplateExample();
        example.createCriteria().andConfigIdEqualTo(configId);
        List<PcsPrFileTemplate> records = pcsPrFileTemplateMapper.selectByExample(example);
        modelMap.put("records", records);
                
        return "pcs/pcsPrFileTemplate/pcsPrFileTemplate_page";
    }
    
    @RequiresPermissions("pcsPrFileTemplate:edit")
    @RequestMapping(value = "/pcsPrFileTemplate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFileTemplate_au(PcsPrFileTemplate record, MultipartFile _file,
                                       HttpServletRequest request) throws IOException, InterruptedException {

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }
        String savePath = uploadPdf(_file, "pcsPrFileTemplate");
        record.setFilePath(savePath);
        record.setFileName(originalFilename);
        if(record.getId()==null) {
            record.setIsDeleted(false);
            pcsPrFileTemplateMapper.insertSelective(record);
        }else{
            pcsPrFileTemplateMapper.updateByPrimaryKeySelective(record);
        }

        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/修改党员大会材料：%s-%s", savePath, originalFilename));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrFileTemplate:edit")
    @RequestMapping("/pcsPrFileTemplate_au")
    public String pcsPrFileTemplate_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            PcsPrFileTemplate pcsPrFileTemplate = pcsPrFileTemplateMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPrFileTemplate", pcsPrFileTemplate);
        }

        return "pcs/pcsPrFileTemplate/pcsPrFileTemplate_au";
    }

    @RequiresPermissions("pcsPrFileTemplate:del")
    @RequestMapping(value = "/pcsPrFileTemplate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFileTemplate_del(HttpServletRequest request, Integer id, ModelMap modelMap) {

        PcsPrFileTemplate pcsPrFileTemplate = pcsPrFileTemplateMapper.selectByPrimaryKey(id);
        pcsPrFileTemplateService.del(id);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "删除党员大会材料：%s", JSONUtils.toString(pcsPrFileTemplate, false)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrFileTemplate:changeOrder")
    @RequestMapping(value = "/pcsPrFileTemplate_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFileTemplate_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pcsPrFileTemplateService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "党员大会材料调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
