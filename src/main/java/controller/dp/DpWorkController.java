package controller.dp;

import domain.dp.DpEdu;
import domain.dp.DpWork;
import domain.dp.DpWorkExample;
import domain.dp.DpWorkExample.Criteria;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpWorkController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpWork:list")
    @RequestMapping("/dpWork")
    public String dpWork(Integer userId,
                         ModelMap modelMap) {

        SysUserView sysUser = CmTag.getUserById(userId);
        modelMap.put("sysUser", sysUser);

        return "dp/dpWork/dpWork_page";
    }

    @RequiresPermissions("dpWork:list")
    @RequestMapping("/dpWork_data")
    @ResponseBody
    public void dpWork_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer fid,
                                    Integer unitId,
                                    Date endTime,
                                    String workTypes,
                                    Boolean isCadre,
                                Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpWorkExample example = new DpWorkExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_time asc");

        if (endTime!=null) {
        criteria.andEndTimeGreaterThan(endTime);
        }
        if (StringUtils.isNotBlank(workTypes)) {
            criteria.andWorkTypesLike(SqlUtils.like(workTypes));
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }
        if (userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId != null) {
            criteria.andUnitIdsContain(unitId);
        }
        if (fid != null) {
            if (fid > 0) {
                criteria.andFidEqualTo(fid);
            }
        } else {
            criteria.andFidIsNull();
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpWork_export(example, response);
            return;
        }

        long count = dpWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpWork> records= dpWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpWork.class, dpWorkMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpWork:edit")
    @RequestMapping(value = "/dpWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpWork_au(DpWork record,
                            String _startTime,
                            String _endTime,
                            HttpServletRequest request) {

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

            //record.setStatus(true);
            dpWorkService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加工作经历：{0}", record.getId()));
        } else {

            dpWorkService.updateByPrimaryKeySelective(record);
            if (record.getEndTime() == null) {
                commonMapper.excuteSql("update cadre_work set end_time=null where id=" + id);
            }
            logger.info(log( LogConstants.LOG_DPPARTY, "更新工作经历：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpWork:edit")
    @RequestMapping("/dpWork_au")
    public String dpWork_au(Integer id, Integer userId,
                            Boolean isEduWork,
                            Integer fid, ModelMap modelMap) {

        if (id != null) {
            DpWork dpWork = dpWorkMapper.selectByPrimaryKey(id);
            isEduWork = dpWork.getIsEduWork();
            fid = dpWork.getFid();
            modelMap.put("dpWork", dpWork);
        }
        if (fid != null){
            Date topStartTime = null;
            Date topEndTime = null;
            if (BooleanUtils.isTrue(isEduWork)) {
                DpEdu dpEdu = dpEduMapper.selectByPrimaryKey(fid);
                topStartTime = dpEdu.getEnrolTime();
                topEndTime = dpEdu.getFinishTime();
            } else {
                DpWork dpWork = dpWorkMapper.selectByPrimaryKey(fid);
                topStartTime = dpWork.getStartTime();
                topEndTime = dpWork.getEndTime();
            }

            modelMap.put("topStartTime", topStartTime);
            modelMap.put("topEndTime", topEndTime);
        }
        SysUserView sysUser = CmTag.getUserById(userId);
        modelMap.put("sysUser", sysUser);

        modelMap.put("fid", fid);
        modelMap.put("isEduWork", isEduWork);
        return "dp/dpWork/dpWork_au";
    }

    @RequiresPermissions("dpWork:del")
    @RequestMapping(value = "/dpWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpWorkService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除工作经历：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpWork:del")
    @RequestMapping(value = "/dpWork_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpWork_batchDel(HttpServletRequest request,
                               Integer userId,
                               Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpWorkService.batchDel(ids, userId);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除工作经历：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void dpWork_export(DpWorkExample example, HttpServletResponse response) {

        List<DpWork> records = dpWorkMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"结束日期|100","工作单位及担任职务（或专技职务）|100","所属内设机构|100","院系/机关工作经历|100","是否干部任职|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpWork record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD),
                            record.getDetail(),
                            record.getUnitIds(),
                            record.getWorkTypes(),
                            record.getIsCadre()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("工作经历(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
