package controller.cadre;

import bean.CadreResume;
import controller.BaseController;
import controller.global.OpException;
import domain.cadre.*;
import domain.cadre.CadreWorkExample.Criteria;
import domain.crp.CrpRecord;
import domain.crp.CrpRecordExample;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.dispatch.DispatchCadreRelateService;
import sys.constants.*;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreWorkController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreWork_page")
    public String cadreWork_page(HttpServletResponse response,
                                 @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览 3 干部任免审批表简历预览
                                 @SortParam(required = false, defaultValue = "id", tableName = "cadre_work") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Integer cadreId,
                                 Integer fid,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {
        modelMap.put("type", type);
        if (type == 2) {
            List<CadreWork> cadreWorks = cadreWorkService.list(cadreId);
            modelMap.put("cadreWorks", cadreWorks);
            CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK);
            modelMap.put("cadreInfo", cadreInfo);
        } else if (type == 3) {

            List<CadreResume> cadreResumes = cadreWorkService.resume(cadreId);
            modelMap.put("cadreResumes", cadreResumes);
            CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESUME);
            modelMap.put("cadreInfo", cadreInfo);
        }

        return "cadre/cadreWork/cadreWork_page";
    }

    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreWork_data")
    public void cadreWork_data(HttpServletResponse response,
                              /* @SortParam(required = false, defaultValue = "id", tableName = "cadre_work") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,*/
                               Integer cadreId, Integer pageSize, Integer pageNo,
                                   Integer fid, // fid=null时，读取工作经历；fid<=0时，读取全部 fid>0 读取其间工作
                               Boolean isEduWork,
                               Boolean isCadre,
                               Integer unitId, // 所属内设机构
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录（exportType=0时为现任干部id)
                               @RequestParam(required = false, defaultValue = "0") int exportType,// 0: 现任干部 1：年轻干部
                               Integer reserveType // 年轻干部类别
    ) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreWorkExample example = new CadreWorkExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if(isEduWork!=null){
            criteria.andIsEduWorkEqualTo(isEduWork);
        }
        if (unitId != null) {
            criteria.andUnitIdsContain(unitId);
        }
        example.setOrderByClause("start_time asc");
        if (fid != null) {
            if (fid > 0) {
                criteria.andFidEqualTo(fid);
            }
        } else {
            criteria.andFidIsNull();
        }
        if (isCadre != null) {
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            SecurityUtils.getSubject().checkPermission("cadre:export");
            cadreWorkService.cadreWork_export(ids, exportType, reserveType, response);
            return;
        }

        long count = cadreWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        /*if(fid!=null)
            return "cadre/cadreWork/cadreWork_during_page";

        return "cadre/cadreWork/cadreWork_page";*/

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreWorks);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 干部挂职锻炼经历
    @RequiresPermissions("cadreWork:list")
    @RequestMapping("/cadreCrpRecord_data")
    public void cadreCrpRecord_data(HttpServletResponse response,
                                    Integer cadreId,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrpRecordExample example = new CrpRecordExample();
        CrpRecordExample.Criteria criteria = example.createCriteria();

        if (cadreId != null) {
            CadreView cadreView = iCadreMapper.getCadre(cadreId);
            criteria.andUserIdEqualTo(cadreView.getUserId());
        }

        long count = crpRecordMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrpRecord> records = crpRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreWork record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYYMM));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYYMM));
        }
        if(record.getStartTime()!=null && record.getEndTime()!=null
                && record.getEndTime().before(record.getStartTime())){
            return failed("起止时间有误");
        }

        record.setIsCadre(BooleanUtils.isTrue(record.getIsCadre()));
        record.setIsEduWork(BooleanUtils.isTrue(record.getIsEduWork()));
        if (id == null) {

            if (!toApply) {
                cadreWorkService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加工作经历：%s", record.getId()));
            } else {
                cadreWorkService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-工作经历：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            if (cadreWork.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                cadreWorkService.updateByPrimaryKeySelective(record);
                if (record.getEndTime() == null) {
                    commonMapper.excuteSql("update cadre_work set end_time=null where id=" + id);
                }
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新工作经历：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreWorkService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-工作经历：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreWorkService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-工作经历：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_au")
    public String cadreWork_au(Integer id, int cadreId,
                               Boolean isEduWork,
                               Integer fid, ModelMap modelMap) {

        if (id != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            isEduWork = cadreWork.getIsEduWork();
            fid = cadreWork.getFid();
            modelMap.put("cadreWork", cadreWork);
        }
        if (fid != null) {

            Date topStartTime = null;
            Date topEndTime = null;
            if (BooleanUtils.isTrue(isEduWork)) {
                CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(fid);
                topStartTime = cadreEdu.getEnrolTime();
                topEndTime = cadreEdu.getFinishTime();
            } else {
                CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(fid);
                topStartTime = cadreWork.getStartTime();
                topEndTime = cadreWork.getEndTime();
            }

            modelMap.put("topStartTime", topStartTime);
            modelMap.put("topEndTime", topEndTime);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);

        modelMap.put("fid", fid);
        modelMap.put("isEduWork", isEduWork);

        return "cadre/cadreWork/cadreWork_au";
    }

    // （在菜单【任职情况】中，对没有干部管理权限的角色隐藏）
    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_updateUnitId", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_updateUnitId(int id,
                                         Integer[] unitIds,
                                         HttpServletRequest request) {

        if (unitIds == null || unitIds.length == 0) {

            commonMapper.excuteSql("update cadre_work set unit_id=null where id=" + id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除工作经历所属内设机构：%s", id));
        } else {
            CadreWork record = new CadreWork();
            record.setId(id);
            record.setUnitIds(StringUtils.join(unitIds, ","));
            cadreWorkService.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_ADMIN, "更新工作经历所属内设机构：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_updateUnitId")
    public String cadreWork_updateUnitId(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreWork", cadreWork);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);

        return "cadre/cadreWork/cadreWork_updateUnitId";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping(value = "/cadreWork_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_transfer(HttpServletRequest request, int cadreId,
                                     Integer id) {

        if (id != null) {

            cadreWorkService.transfer(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "转移其间工作经历至主要工作经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/cadreWork_transferToSubWork")
    public String cadreWork_transferToSubWork(int id, ModelMap modelMap) {

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        modelMap.put("cadreWork", cadreWork);
        if (cadreWork.getFid() == null && cadreWork.getSubWorkCount() == 0) {

            List<CadreWork> topCadreWorks = new ArrayList<>();
            Date endTime = cadreWork.getEndTime();
            if (endTime == null) {
                topCadreWorks.addAll(iCadreMapper.findUnendCadreWorks(id, cadreWork.getCadreId()));
            } else {
                topCadreWorks.addAll(iCadreMapper.findTopCadreWorks(id, cadreWork.getCadreId(), endTime));
            }

            modelMap.put("topCadreWorks", topCadreWorks);
        }

        return "cadre/cadreWork/cadreWork_transferToSubWork";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping(value = "/cadreWork_transferToSubWork", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_transferToSubWork(int id, int fid) {

        cadreWorkService.transferToSubWork(id, fid);
        logger.info(addLog(LogConstants.LOG_ADMIN, "转移主要工作经历至其间工作经历：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:del")
    @RequestMapping(value = "/cadreWork_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreWork_batchDel(HttpServletRequest request,
                                  int cadreId, // 干部信息本人直接修改数据校验
                                  Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreWorkService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除工作经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreWork:edit")
    @RequestMapping("/cadreWork_addDispatchs")
    public String cadreWork_addDispatchs(HttpServletResponse response, int id, int cadreId, String type, ModelMap modelMap) {

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        // 已关联的发文
        Set<Integer> dispatchCadreIdSet = new HashSet<>();
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>();
        List<DispatchCadreRelate> dispatchCadreRelates = dispatchCadreRelateService.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(CmTag.getDispatchCadre(dispatchCadreId));
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, null);
            modelMap.put("dispatchCadres", dispatchCadres);

            Set<Integer> otherDispatchCadreRelateSet = dispatchCadreRelateService.findOtherDispatchCadreRelateSet(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
            modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadreWork/cadreWork_addDispatchs";
    }

    // 不提供给干部本人操作
    @RequiresPermissions("cadreWork:edit")
    @RequestMapping(value = "/cadreWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreWork_addDispatchs(HttpServletRequest request, int id, Integer[] ids, ModelMap modelMap) {

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        //if (ids != null && ids.length > 0) { // 可以删除
        dispatchCadreRelateService.updateDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK, ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改工作经历%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        //}
        return success(FormUtils.SUCCESS);
    }
}
