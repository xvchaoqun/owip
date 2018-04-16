package controller.cadre;

import controller.BaseController;
import domain.cadre.CadrePostWork;
import domain.cadre.CadrePostWorkExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePostWorkController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostWork_data")
    @ResponseBody
    public void cadrePostWork_data(HttpServletResponse response,
                                   Integer cadreId,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostWorkExample example = new CadrePostWorkExample();
        CadrePostWorkExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        long count = cadrePostWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostWork> cadrePostWorks = cadrePostWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePostWorks);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping(value = "/cadrePostWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePostWork_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadrePostWork record, String _gradeTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_gradeTime)){
            record.setGradeTime(DateUtils.parseDate(_gradeTime, "yyyy.MM"));
        }
        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (id == null) {

            if (!toApply) {
                cadrePostWorkService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加工勤岗位过程信息：%s", record.getId()));
            } else {
                cadrePostWorkService.modifyApply(record, null, false);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-工勤岗位过程信息：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadrePostWork _record = cadrePostWorkMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new IllegalArgumentException("数据异常");
            }

            if (!toApply) {
                cadrePostWorkService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新工勤岗位过程信息：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadrePostWorkService.modifyApply(record, id, false);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-工勤岗位过程信息：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadrePostWorkService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-工勤岗位过程信息：%s", record.getId()));
                }
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping("/cadrePostWork_au")
    public String cadrePostWork_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePostWork cadrePostWork = cadrePostWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePostWork", cadrePostWork);
        }

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePostWork/cadrePostWork_au";
    }

    @RequiresPermissions("cadrePostInfo:del")
    @RequestMapping(value = "/cadrePostWork_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadrePostWorkService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除工勤岗位过程信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
