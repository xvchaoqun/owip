package controller.dispatch;

import controller.global.OpException;
import domain.dispatch.*;
import domain.dispatch.DispatchExample.Criteria;
import domain.sc.scDispatch.ScDispatchView;
import mixin.DispatchMixin;
import mixin.MixinUtils;
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
import org.springframework.web.multipart.MultipartFile;
import persistence.sc.IScMapper;
import service.sc.scDispatch.ScDispatchService;
import shiro.ShiroHelper;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class DispatchController extends DispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch")
    public String dispatch(@RequestParam(defaultValue = "1") Integer cls,
                                Integer dispatchTypeId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/dispatchCadre";
        }else if (cls == 3) {
            return "forward:/sc/scDispatch";
        }else if (cls == 4) {
            return "forward:/dispatchUnit";
        }

        if (dispatchTypeId != null) {
            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatchTypeId));
        }

        return "dispatch/dispatch_page";
    }

    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch_data")
    public void dispatch_data(HttpServletResponse response,
                                 /*@SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,*/
                              Integer year,
                              Integer dispatchTypeId,
                              String code,
                              @RequestDateRange DateRange _pubTime,
                              @RequestDateRange DateRange _workTime,
                              @RequestDateRange DateRange _meetingTime,
                              Integer[] ids, // 导出的记录
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchViewExample example = new DispatchViewExample();
        DispatchViewExample.Criteria criteria = example.createCriteria();

        example.setOrderByClause("work_time desc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (dispatchTypeId != null) {
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code);
        }
        if (_pubTime.getStart() != null) {
            criteria.andPubTimeGreaterThanOrEqualTo(_pubTime.getStart());
        }

        if (_pubTime.getEnd() != null) {
            criteria.andPubTimeLessThanOrEqualTo(_pubTime.getEnd());
        }
        if (_workTime.getStart() != null) {
            criteria.andWorkTimeGreaterThanOrEqualTo(_workTime.getStart());
        }

        if (_workTime.getEnd() != null) {
            criteria.andWorkTimeLessThanOrEqualTo(_workTime.getEnd());
        }
        if (_meetingTime.getStart() != null) {
            criteria.andMeetingTimeGreaterThanOrEqualTo(_meetingTime.getStart());
        }

        if (_meetingTime.getEnd() != null) {
            criteria.andMeetingTimeLessThanOrEqualTo(_meetingTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            dispatch_export(example, response);
            return;
        }

        long count = dispatchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchView> Dispatchs = dispatchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Dispatchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(DispatchView.class, DispatchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping("/dispatch_au_page")
    public String dispatch_cadres(Integer id, ModelMap modelMap) {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
        modelMap.put("dispatch", dispatch);

        return "dispatch/dispatch_au_page";
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping("/dispatch_au")
    public String dispatch_au(Integer id, Integer scDispatchId, ModelMap modelMap) {

        Integer year = null;
        if (id != null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            scDispatchId = dispatch.getScDispatchId();

            modelMap.put("dispatch", dispatch);
            year = dispatch.getYear();

            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatch.getDispatchTypeId()));
        }else if(scDispatchId!=null){

            IScMapper iScMapper = CmTag.getBean(IScMapper.class);
            if(iScMapper!=null){
                ScDispatchView scDispatch = iScMapper.getScDispatchView(scDispatchId);
                if(scDispatch!=null){

                    Dispatch dispatch = new Dispatch();
                    dispatch.setDispatchTypeId(scDispatch.getDispatchTypeId());
                    dispatch.setCode(scDispatch.getCode());
                    dispatch.setMeetingTime(scDispatch.getMeetingTime());
                    dispatch.setAppointCount(scDispatch.getAppointCount());
                    dispatch.setDismissCount(scDispatch.getDismissCount());
                    dispatch.setCategory(DispatchConstants.DISPATCH_CATEGORY_CADER+"");

                    modelMap.put("dispatch", dispatch);

                    Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
                    modelMap.put("dispatchType", dispatchTypeMap.get(dispatch.getDispatchTypeId()));
                }
            }
        }
        modelMap.put("scDispatchId", scDispatchId);

        if (year == null) {
            year = DateUtils.getCurrentYear();
        }
        modelMap.put("year", year);

        return "dispatch/dispatch_au";
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping(value = "/dispatch_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isAnyFormat(file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "dispatch");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", UserResUtils.sign(savePath));

        return resultMap;
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping(value = "/dispatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_au(Dispatch record, String _pubTime,
                              String _workTime,
                              String _meetingTime,
                              /*MultipartFile _file,*/
                              Integer[] _category,
                              String file,
                              String fileName,
                              MultipartFile _ppt,
                              HttpServletRequest request) throws InterruptedException, IOException {

        Integer id = record.getId();

        if(record.getScDispatchId()!=null){
            ScDispatchService scDispatchService = CmTag.getBean(ScDispatchService.class);
            ScDispatchView sd = scDispatchService.get(record.getScDispatchId());
            record.setYear(sd.getYear());
            if(StringUtils.isNotBlank(sd.getCode()))
                record.setCode(sd.getCode());
            record.setDispatchTypeId(sd.getDispatchTypeId());
            record.setMeetingTime(sd.getMeetingTime());
            record.setAppointCount(sd.getAppointCount());
            record.setDismissCount(sd.getDismissCount());
            record.setRecordUserId(sd.getRecordUserId());
        }else{
            record.setMeetingTime(DateUtils.parseDate(_meetingTime, DateUtils.YYYY_MM_DD));
        }

        if (record.getCode() == null){

            return failed("发文号不能为空");
        }else if (record.getCode() != null) {

            Dispatch dispatch = dispatchService.get(record.getDispatchTypeId(), record.getYear(), record.getCode());
            if(dispatch!=null) {
                id = dispatch.getId();
                record.setId(id);
            }
            //return failed("发文号重复");
        }

        record.setFileName(StringUtils.trimToNull(fileName));
        if(record.getFile()!=null) {
            UserRes resBean = UserResUtils.decode(record.getFile());
            record.setFile(resBean.getRes());
        }
        
        record.setCategory(StringUtils.join(_category, ","));
        
        if (_ppt != null) {

            String originalFilename = _ppt.getOriginalFilename();
            String savePath = uploadDoc(_ppt, "dispatch_ppt");

            record.setPptName(FileUtils.getFileName(originalFilename));
            record.setPpt(savePath);
        }

        record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));

        if (id == null) {
            /*if (record.getCode() == null)
                record.setCode(dispatchService.genCode(record.getDispatchTypeId(), record.getYear()));*/
            if(record.getScDispatchId()==null) {
                record.setRecordUserId(ShiroHelper.getCurrentUserId());
            }

            dispatchService.insertSelective(record);
            id = record.getId(); // 新ID
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加发文：%s", id));
        } else {

            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            if (dispatch != null && dispatch.getHasChecked()) {
                return failed("已经复核，不可修改。");
            }

            if (StringUtils.isNotBlank(file) && StringUtils.isNotBlank(record.getFile())) {
                FileUtils.delFile(springProps.uploadPath + dispatch.getFile()); // 删除原文件
            }
            if (_ppt != null && StringUtils.isNotBlank(record.getPpt())) {
                FileUtils.delFile(springProps.uploadPath + dispatch.getPpt()); // 删除原ppt
            }

            /*if (dispatch.getDispatchTypeId().intValue() != record.getDispatchTypeId()) { // 修改了类型，要修改发文号
                record.setCode(dispatchService.genCode(record.getDispatchTypeId(), record.getYear()));
            }*/
            dispatchService.updateByPrimaryKeySelective(record, true);
            if(record.getMeetingTime()==null){
                commonMapper.excuteSql("update dispatch set meeting_time=null where id=" + id);
            }
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新发文：%s", id));
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("id", id);
        return resultMap;
    }

    // 复核
    @RequiresPermissions("dispatch:check")
    @RequestMapping(value = "/dispatch_check", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_check(Integer id) {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);

        int realAppointCount = dispatch.getRealAppointCount() == null ? 0 : dispatch.getRealAppointCount();
        int realDismissCount = dispatch.getRealDismissCount() == null ? 0 : dispatch.getRealDismissCount();
        int appointCount = dispatch.getAppointCount() == null ? 0 : dispatch.getAppointCount();
        int dismissCount = dispatch.getDismissCount() == null ? 0 : dispatch.getDismissCount();

        if ((realAppointCount + realDismissCount) > 0
                && appointCount == realAppointCount
                && dismissCount == realDismissCount) {
            Dispatch record = new Dispatch();
            record.setId(id);
            record.setHasChecked(true);
            dispatchService.updateByPrimaryKeySelective(record, false);
        } else {
           return failed("还未全部录入，不能进行复核操作");
        }

        return success(FormUtils.SUCCESS);
    }

    // 重新复核
    @RequiresPermissions("dispatch:check")
    @RequestMapping(value = "/dispatch_reset_check", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_reset_check(Integer id) {

        Dispatch record = new Dispatch();
        record.setId(id);
        record.setHasChecked(false);
        dispatchService.updateByPrimaryKeySelective(record, false);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_del_file", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_del_file(Integer id, @RequestParam String type) {

        if (StringUtils.equalsIgnoreCase(type, "file")) {
            dispatchService.delFile(id);
        } else if (StringUtils.equalsIgnoreCase(type, "ppt")) {
            dispatchService.delPpt(id);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:changeOrder")
    @RequestMapping(value = "/dispatch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatch_export(DispatchViewExample example, HttpServletResponse response) {

        List<DispatchView> records = dispatchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份", "发文类型", "发文号|200", "党委常委会日期|120", "发文日期|120", "任免日期|120", "任命人数", "录入任命人数",
                "免职人数", "录入免职人数", "是否全部录入", "是否复核", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchView record = records.get(i);
            int realAppointCount = record.getRealAppointCount() == null ? 0 : record.getRealAppointCount();
            int realDismissCount = record.getRealDismissCount() == null ? 0 : record.getRealDismissCount();
            int appointCount = record.getAppointCount() == null ? 0 : record.getAppointCount();
            int dismissCount = record.getDismissCount() == null ? 0 : record.getDismissCount();
            String[] values = {
                    record.getYear() + "",
                    record.getDispatchTypeId() == null ? "" : dispatchTypeService.findAll().get(record.getDispatchTypeId()).getName(),
                    CmTag.getDispatchCode(record.getCode(), record.getDispatchTypeId(), record.getYear()),
                    DateUtils.formatDate(record.getMeetingTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPubTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYY_MM_DD),
                    appointCount + "",
                    realAppointCount + "",
                    dismissCount + "",
                    realDismissCount + "",
                    ((realAppointCount + realDismissCount) > 0
                            && appointCount == realAppointCount
                            && dismissCount == realDismissCount) ? "是" : "否",
                    record.getHasChecked() ? "已复核" : "否",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "任免文件信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dispatch_selects")
    @ResponseBody
    public Map dispatch_selects(Integer pageSize, Integer pageNo,
                                Boolean isCadre, // 是否干部任免文件
                                Integer dispatchTypeId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchExample example = new DispatchExample();
        Criteria criteria = example.createCriteria();
        if (dispatchTypeId != null) {
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);
        }
        if(isCadre!=null){
            criteria.andCategoryContain(DispatchConstants.DISPATCH_CATEGORY_CADER, isCadre);
        }

        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andCodeEqualTo(searchStr);
        }

        long count = dispatchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Dispatch> dispatchs = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        List<Map<String, Object>> options = new ArrayList<>();
        if (null != dispatchs && dispatchs.size() > 0) {

            for (Dispatch dispatch : dispatchs) {
                Map<String, Object> option = new HashMap<>();
                option.put("text", CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()));
                option.put("id", dispatch.getId());
                option.put("year", dispatch.getYear());
                option.put("type", dispatchTypeMap.get(dispatch.getDispatchTypeId()).getName());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
