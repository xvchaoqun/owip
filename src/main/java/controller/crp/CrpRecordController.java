package controller.crp;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.crp.CrpRecord;
import domain.crp.CrpRecordExample;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CrpRecordController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 校外挂职锻炼
    @RequiresPermissions("crpRecord:OutMenu")
    @RequestMapping("/crpRecord_out")
    public String crpRecord_out() {

        return "index";
    }

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord_out_page")
    public String crpRecord_out_page() {

        return "forward:/crpRecord_page?type=" + SystemConstants.CES_TEMP_POST_TYPE_OUT;
    }

    // 校内挂职锻炼
    @RequiresPermissions("crpRecord:InMenu")
    @RequestMapping("/crpRecord_in")
    public String crpRecord_in() {

        return "index";
    }

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord_in_page")
    public String crpRecord_in_page() {

        return "forward:/crpRecord_page?type=" + SystemConstants.CES_TEMP_POST_TYPE_IN;
    }

    // 外单位到本校挂职
    @RequiresPermissions("crpRecord:TransferMenu")
    @RequestMapping("/crpRecord_transfer")
    public String crpRecord_transfer() {

        return "index";
    }

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord_transfer_page")
    public String crpRecord_transfer_page() {

        return "forward:/crpRecord_page?type=" + SystemConstants.CES_TEMP_POST_TYPE_TRANSFER;
    }

    @RequiresPermissions("crpRecord:list")
    @RequestMapping("/crpRecord_page")
    public String crpRecord_page(@RequestParam(required = false, defaultValue = "0") Boolean isFinished,
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

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(crpRecord.class, crpRecordMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
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
            CadreView cv = cadreService.findAll().get(cadreId);
            record.setUserId(cv.getUserId());
        }

        if (id == null) {
            crpRecordService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部挂职锻炼：%s", record.getId()));
        } else {

            crpRecordService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部挂职锻炼：%s", record.getId()));
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
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部挂职锻炼：%s", id));
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
            logger.info(addLog(SystemConstants.LOG_ADMIN, "干部挂职结束：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crpRecord:del")
    @RequestMapping(value = "/crpRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crpRecordService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部挂职锻炼：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void crpRecord_export(CrpRecordExample example, HttpServletResponse response) {

        List<CrpRecord> records = crpRecordMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名", "是否现任干部", "时任职务", "委派单位", "挂职类别", "挂职单位及所任职务", "挂职开始时间", "挂职拟结束时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrpRecord record = records.get(i);
            String[] values = {
                    record.getRealname(),
                    record.getIsPresentCadre() ? "现任干部" : "非现任干部",
                    record.getPresentPost(),
                    record.getToUnitType() + "",
                    record.getTempPostType() + "",
                    record.getTitle(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "干部挂职锻炼_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
