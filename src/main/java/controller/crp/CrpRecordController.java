package controller.crp;

import controller.BaseController;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.crp.CrpRecord;
import domain.crp.CrpRecordExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CrpConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrpRecordController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord")
    public String crpRecord(@RequestParam(required = false, defaultValue = "0") Boolean isFinished,
                                 Integer userId,
                                 ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        modelMap.put("isFinished", isFinished);
        return "crp/crpRecord/crpRecord_page";
    }

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord_data")
    public void crpRecord_data(HttpServletResponse response,

                               Integer userId,
                               String realname,
                               Boolean isPresentCadre,
                               Integer toUnitType,
                               Integer tempPostType,

                               Byte type,
                               Boolean isFinished,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
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
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameEqualTo(realname.trim());
        }
        if (isPresentCadre != null) {
            criteria.andIsPresentCadreEqualTo(isPresentCadre);
        }
        if (toUnitType != null) {
            criteria.andToUnitTypeEqualTo(toUnitType);
        }
        if (tempPostType != null) {
            criteria.andTempPostTypeEqualTo(tempPostType);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isFinished != null) {
            criteria.andIsFinishedEqualTo(isFinished);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crpRecord_export(example, response);
            return;
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
        //baseMixins.put(crpRecord.class, crpRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crpRecord:edit")
    @RequestMapping(value = "/crpRecord_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crpRecord_au(CrpRecord record,
                               Integer cadreId, // 如果是现任干部，则从现任干部中选择
                               HttpServletRequest request) {

        Integer id = record.getId();

        if (record.getStartDate().after(record.getEndDate())) {
            return failed("挂职时间有误。");
        }

        record.setIsPresentCadre(BooleanUtils.isTrue(record.getIsPresentCadre()));

        if (cadreId != null) {
            CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
            record.setUserId(cv.getUserId());
        }

        if (id == null) {
            crpRecordService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部挂职锻炼：%s", record.getId()));
        } else {

            crpRecordService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部挂职锻炼：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crpRecord:edit")
    @RequestMapping("/crpRecord_au")
    public String crpRecord_au(Integer id, Byte type, ModelMap modelMap) {

        if (id != null) {
            CrpRecord crpRecord = crpRecordMapper.selectByPrimaryKey(id);

            type = crpRecord.getType();
            modelMap.put("crpRecord", crpRecord);
            modelMap.put("sysUser", crpRecord.getUser());
            modelMap.put("cadre", crpRecord.getCadre());

        }

        modelMap.put("type", type);
        return "crp/crpRecord/crpRecord_au";
    }

    @RequiresPermissions("crpRecord:del")
    @RequestMapping(value = "/crpRecord_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crpRecord_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            crpRecordService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除干部挂职锻炼：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crpRecord:edit")
    @RequestMapping("/crpRecord_finish")
    public String crpRecord_finish(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrpRecord crpRecord = crpRecordMapper.selectByPrimaryKey(id);
            modelMap.put("crpRecord", crpRecord);
        }

        return "crp/crpRecord/crpRecord_finish";
    }

    @RequiresPermissions("crpRecord:del")
    @RequestMapping(value = "/crpRecord_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crpRecord_finish(Integer id, @DateTimeFormat(pattern = "yyyy-MM") Date realEndDate) {

        if (id != null) {

            crpRecordService.finish(id, realEndDate);
            logger.info(addLog(LogConstants.LOG_ADMIN, "干部挂职结束：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crpRecord:del")
    @RequestMapping(value = "/crpRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crpRecordService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部挂职锻炼：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void crpRecord_export(CrpRecordExample example, HttpServletResponse response) {

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType mtTemppostInUnitOther = CmTag.getMetaTypeByCode("mt_temppost_in_unit_other");
        MetaType mtTemppostInPostOther = CmTag.getMetaTypeByCode("mt_temppost_in_post_other");
        List<CrpRecord> records = crpRecordMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|50", "是否现任干部|100",
                 "时任职务|300",
                 "联系电话|100",
                "委派单位|100",
                "挂职类别|100",
                "挂职项目|120",
                "挂职单位|350",
                "所任职务|350",
                "挂职开始时间|120",
                "挂职拟结束时间|120",
                "挂职实际结束时间|120", "备注|300"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrpRecord record = records.get(i);
            SysUserView user = record.getUser();
            if(user!=null){
                record.setRealname(record.getUser().getRealname());
            }
            byte type = record.getType();
            String toUnit = "";
            if(type== CrpConstants.CRP_RECORD_TYPE_IN){
                toUnit = record.getToUnit();
            }else{
                MetaType metaType = metaTypeMap.get(record.getToUnitType());
                toUnit = metaType.getName();
                if(metaType.getId().intValue()==mtTemppostInUnitOther.getId()){
                    toUnit += ":" + record.getToUnit();
                }
            }

            MetaType metaType = metaTypeMap.get(record.getTempPostType());
            String tempPostType = metaType.getName();
            if(metaType.getId().intValue()==mtTemppostInPostOther.getId()){
                tempPostType += ":" + record.getTempPost();
            }

            String[] values = {
                    user==null?"":user.getCode(),
                    record.getRealname(),
                    record.getIsPresentCadre() ? "是" : "否",
                    record.getPresentPost(),
                    record.getPhone(),
                    toUnit,
                    tempPostType,
                    record.getProject(),
                    record.getUnit(),
                    record.getPost(),
                    DateUtils.formatDate(record.getStartDate(), "yyyy-MM"),
                    DateUtils.formatDate(record.getEndDate(), "yyyy-MM"),
                    DateUtils.formatDate(record.getRealEndDate(), "yyyy-MM"),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部挂职锻炼_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
