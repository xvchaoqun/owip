package controller.dispatch;

import controller.BaseController;
import domain.base.MetaClass;
import domain.base.MetaType;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lm on 2017/10/29.
 */
@Controller
public class DispatchWorkFileWorkTypeController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchWorkFile:workType")
    @RequestMapping("/dispatchWorkFile_workType")
    public String pcsProposalSetting(int type, ModelMap modelMap) {

        String classCode = null;
        if(type==1){
            classCode = "mc_dwf_work_type";
        }else if(type==2){
            classCode = "mc_dwf_work_type_ow";
        }
        modelMap.put("type", type);
        Map<Integer, MetaType> workTypes = metaTypeService.metaTypes(classCode);
        modelMap.put("workTypes", workTypes.values());

        return "dispatch/dispatchWorkFile/dispatchWorkFile_workType";
    }

    @RequiresPermissions("dispatchWorkFile:workType")
    @RequestMapping("/dispatchWorkFile_workType_au")
    public String dispatchWorkFile_workType_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            MetaType workType = metaTypeMapper.selectByPrimaryKey(id);
            modelMap.put("workType", workType);
        }

        return "dispatch/dispatchWorkFile/dispatchWorkFile_workType_au";
    }

    @RequiresPermissions("dispatchWorkFile:workType")
    @RequestMapping(value = "/dispatchWorkFile_workType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_workType_au(int type, MetaType record) {

        String classCode = null;
        if(type==1){
            classCode = "mc_dwf_work_type";
        }else if(type==2){
            classCode = "mc_dwf_work_type_ow";
        }

        MetaClass dwfWorkTypeClass = metaClassService.codeKeyMap().get(classCode);
        record.setClassId(dwfWorkTypeClass.getId());

        if(record.getId()==null) {
            record.setCode(metaTypeService.genCode());
            metaTypeService.insertSelective(record);
        }else{
            metaTypeService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:workType")
    @RequestMapping(value = "/dispatchWorkFile_workType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_workType_del(int id, int type) {

        String classCode = null;
        if(type==1){
            classCode = "mc_dwf_work_type";
        }else if(type==2){
            classCode = "mc_dwf_work_type_ow";
        }

        MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
        MetaClass dwfWorkTypeClass = metaClassService.codeKeyMap().get(classCode);
        if(metaType.getClassId().intValue() != dwfWorkTypeClass.getId()){
            throw new UnauthorizedException();
        }

        metaTypeService.del(id);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:workType")
    @RequestMapping(value = "/dispatchWorkFile_workType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_workType_changeOrder(Integer id, int type, Integer addNum, HttpServletRequest request) {

        String classCode = null;
        if(type==1){
            classCode = "mc_dwf_work_type";
        }else if(type==2){
            classCode = "mc_dwf_work_type_ow";
        }

        MetaClass dwfWorkTypeClass = metaClassService.codeKeyMap().get(classCode);
        metaTypeService.changeOrder(id, addNum, dwfWorkTypeClass.getId());
        logger.info(addLog(SystemConstants.LOG_ADMIN, "工作文件类型调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
