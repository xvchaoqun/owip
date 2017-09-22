package controller.pcs.prList;

import controller.PcsBaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrFileTemplate;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrFileTemplateController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrFileTemplate:list")
    @RequestMapping("/pcsPrFileTemplate")
    public String pcsPrFileTemplate(ModelMap modelMap) {
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        List<PcsPrFileTemplate> records = pcsPrFileTemplateService.findAll(configId);
        modelMap.put("records", records);
                
        return "pcs/pcsPrFileTemplate/pcsPrFileTemplate_page";
    }
    
    @RequiresPermissions("pcsPrFileTemplate:edit")
    @RequestMapping(value = "/pcsPrFileTemplate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFileTemplate_au(PcsPrFileTemplate record, MultipartFile _file,
                                       HttpServletRequest request) throws IOException, InterruptedException {

        if(record.getId()==null && (_file==null || _file.isEmpty())){
            return failed("请选择模板。");
        }
        if(_file!=null && !_file.isEmpty()) {

            String originalFilename = _file.getOriginalFilename();
            String savePath = upload(_file, "pcsPrFileTemplate");
            record.setFilePath(savePath);
            record.setFileName(originalFilename);
        }

        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        record.setConfigId(configId);

        if(record.getId()==null) {
            pcsPrFileTemplateService.insertSelective(record);
        }else{
            pcsPrFileTemplateMapper.updateByPrimaryKeySelective(record);
        }

        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/修改党员大会材料：%s", JSONUtils.toString(record, false)));
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
