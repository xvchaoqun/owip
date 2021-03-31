package controller.pcs.prList;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import domain.pcs.*;
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
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.utils.ContentTypeUtils;
import sys.utils.ContextHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsPrFileController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("pcsPrFile:list")
    @RequestMapping("/pcsPrFile")
    public String pcsPrFile(Integer partyId, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsPrListOw:admin")) {

            ShiroHelper.checkPermission("pcsPrFile:list");
        }

        if(partyId==null){ // 党代会管理员同时也可以是某个二级党组织管理员

            PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin == null) {
                throw new UnauthorizedException();
            }
            partyId = pcsAdmin.getPartyId();
        }

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        modelMap.put("templates", pcsPrFileTemplateService.findAll(configId));
        modelMap.put("fileMap", pcsPrFileService.fileMap(configId, partyId));

        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId, configId,
                PcsConstants.PCS_STAGE_THIRD));

        List<PcsPrCandidate> candidates = pcsPrListService.getList(configId, partyId, null);
        modelMap.put("candidates", candidates);

        return "pcs/pcsPrFile/pcsPrFile_page";
    }

    //@RequiresPermissions("pcsPrFile:edit")
    @RequestMapping(value = "/pcsPrList_sort", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrList_sort(Integer partyId, Integer[] userIds) throws IOException, InterruptedException {


        if(!ShiroHelper.isPermitted("pcsPrListOw:admin")) {

            ShiroHelper.checkPermission("pcsPrFile:edit");
        }

        if(partyId==null){ // 党代会管理员同时也可以是某个二级党组织管理员

            PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin == null) {
                throw new UnauthorizedException();
            }
            partyId = pcsAdmin.getPartyId();
        }

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        pcsPrListService.sort(configId, partyId, userIds);

        logger.info(addLog(LogConstants.LOG_PCS, "按姓氏笔画排序"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrFile:edit")
    @RequestMapping(value = "/pcsPrFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrFile_au(PcsPrFile record, MultipartFile _file,
                                      HttpServletRequest request) throws IOException, InterruptedException {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        if(!pcsPrPartyService.allowModify(partyId, configId, PcsConstants.PCS_STAGE_THIRD)){
            return failed("已报送数据，不可修改。");
        }

        record.setPartyId(partyId);

        record.setConfigId(configId);

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isAnyFormat(_file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }
        String savePath = uploadPdf(_file, "pcsPrFile");
        record.setFilePath(savePath);
        record.setFileName(FileUtils.getFileName(originalFilename));

        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setIp(ContextHelper.getRealIp());
        record.setCreateTime(new Date());

        if (record.getId() == null) {
            pcsPrFileMapper.insertSelective(record);
        } else {
            pcsPrFileMapper.updateByPrimaryKeySelective(record);
        }

        logger.info(addLog(LogConstants.LOG_PCS, "上传党员大会材料：%s-%s", savePath, originalFilename));
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
