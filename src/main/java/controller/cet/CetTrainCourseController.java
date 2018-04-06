package controller.cet;

import bean.XlsTrainCourse;
import bean.XlsUpload;
import domain.cet.CetCourse;
import domain.cet.CetCourseType;
import domain.cet.CetExpert;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseView;
import domain.cet.CetTrainCourseViewExample;
import domain.cet.CetTrainEvaTable;
import domain.cet.CetTraineeCourseCadreView;
import domain.cet.CetTraineeCourseCadreViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
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
@RequestMapping("/cet")
public class CetTrainCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse")
    public String cetTrainCourse(@RequestParam(defaultValue = "1") Integer cls,
                                 @RequestParam(defaultValue = "1")Boolean isOnCampus,
                                 int trainId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        if(isOnCampus) {

            modelMap.put("cls", cls);
            Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
            modelMap.put("courseTypeMap", courseTypeMap);
        }

        return isOnCampus?"cet/cetTrainCourse/cetTrainCourse_page":"cet/cetTrainCourse/cetTrainCourse_off_page";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_data")
    public void cetTrainCourse_data(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1")Boolean isOnCampus,
                                 int trainId,
                                    String name,
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

        CetTrainCourseViewExample example = new CetTrainCourseViewExample();
        CetTrainCourseViewExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            if(isOnCampus)
                cetTrainCourse_export(example, response);
            else
                cetTrainCourse_off_export(example, response);
            return;
        }

        long count = cetTrainCourseViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourseView> records = cetTrainCourseViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainCourse.class, cetTrainCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_selectCourses", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_selectCourses(int trainId,
                                               @RequestParam(value = "courseIds[]") Integer[] courseIds,
                                               HttpServletRequest request) {

        cetTrainCourseService.selectCourses(trainId, courseIds);
        logger.info(addLog(SystemConstants.LOG_CET, "添加培训班课程：%s, %s",trainId, StringUtils.join(courseIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectCourses")
    public String cetTrainCourse_selectCourses(int trainId, Integer expertId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        if(expertId!=null)
            modelMap.put("cetExpert", cetExpertMapper.selectByPrimaryKey(expertId));

        Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
        modelMap.put("courseTypeMap", courseTypeMap);

        return "cet/cetTrainCourse/cetTrainCourse_selectCourses";
    }

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetTrainCourse_selectCourses_data")
    public void cetTrainCourse_selectCourses_data(HttpServletResponse response,
                                                  int trainId, Integer expertId, String name,
                                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iCetMapper.cetTrainCourse_countCourses(trainId, expertId, name);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourse> records= iCetMapper.cetTrainCourse_selectCourses(trainId, expertId, name,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetCourse.class, cetCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 管理员给培训课程设置必选课的参训人
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectObjs")
    public String cetTrainCourse_selectObjs(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));

        return "cet/cetTrainCourse/cetTrainCourse_selectObjs";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectObjs_tree")
    @ResponseBody
    public Map cetTrainCourse_selectObjs_tree(int trainCourseId) throws IOException {

        TreeNode tree = cetTrainCourseService.selectObjs_tree(trainCourseId);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_selectObjs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_selectObjs(int trainCourseId,
                                                @RequestParam(required = false, value = "userIds[]") Integer[] userIds,
                                                HttpServletRequest request) {

        cetTrainCourseService.selectObjs(trainCourseId, userIds);
        logger.info(addLog(SystemConstants.LOG_CET, "设置参训人员：%s, %s",trainCourseId, StringUtils.join(userIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 参训人列表（签到列表）
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_trainee")
    public String cetTrainCourse_trainee(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));

        return "cet/cetTrainCourse/cetTrainCourse_trainee_page";
    }

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetTrainCourse_trainee_data")
    public void cetTrainCourse_trainee_data(HttpServletResponse response,
                                                  int trainCourseId, String realname,
                                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeCourseCadreViewExample example = new CetTraineeCourseCadreViewExample();
        CetTraineeCourseCadreViewExample.Criteria criteria = example.createCriteria()
                .andTrainCourseIdEqualTo(trainCourseId);

        if(StringUtils.isNotBlank(realname)){
            criteria.andRealnameLike("%" + realname + "%");
        }

        long count = cetTraineeCourseCadreViewMapper.countByExample(example);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeCourseCadreView> records= cetTraineeCourseCadreViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetCourse.class, cetCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_info", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_info(CetTrainCourse record, HttpServletRequest request) {

        cetTrainCourseService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班课程信息：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_info")
    public String cetTrainCourse_info(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));
        return "cet/cetTrainCourse/cetTrainCourse_info";
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetTrainCourseService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_CET, "批量删除培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:changeOrder")
    @RequestMapping(value = "/cetTrainCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainCourseService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_CET, "培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourse_export(CetTrainCourseViewExample example, HttpServletResponse response) {

        List<CetTrainCourseView> records = cetTrainCourseViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次|200", "课程名称|300", "教师名称", "开始时间|200", "结束时间|200", "选课情况"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourseView record = records.get(i);
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(record.getTrainId());
            CetCourse cetCourse = record.getCetCourse();
            CetExpert cetExpert = cetCourse.getCetExpert();
            String[] values = {
                    cetTrain.getName(),
                    cetCourse.getName(),
                    cetExpert.getRealname(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    record.getSelectedCount()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训班课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void cetTrainCourse_off_export(CetTrainCourseViewExample example, HttpServletResponse response) {

        List<CetTrainCourseView> records = cetTrainCourseViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次|200", "课程名称|300", "教师名称|80", "开始时间|200", "结束时间|200", "评估表|200", "评课情况（已完成/总数）|200"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourseView record = records.get(i);
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(record.getTrainId());
            CetTrainEvaTable trainEvaTable = cetTrainEvaTableMapper.selectByPrimaryKey(record.getEvaTableId());
            String[] values = {
                    cetTrain.getName(),
                    record.getName(),
                    record.getTeacher(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    trainEvaTable.getName(),
                    (record.getFinishCount()==null?0:record.getFinishCount())
                            + "/" + (cetTrain.getEvaCount()==null?0:cetTrain.getEvaCount())
            };
            valuesList.add(values);
        }
        String fileName = "培训课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_au(CetTrainCourse record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setIsGlobal(BooleanUtils.isTrue(record.getIsGlobal()));

        if(record.getStartTime()!=null && record.getEndTime()!=null
                && record.getStartTime().after(record.getEndTime())){
            return failed("开始时间不能晚于结束时间");
        }

        if (id == null) {

            cetTrainCourseService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加培训课程：%s", record.getId()));
        } else {

            cetTrainCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_au")
    public String cetTrainCourse_au(Integer id, Integer trainId, ModelMap modelMap) {

        if (id != null) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            trainId = cetTrainCourse.getTrainId();
        }
        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));

        return "cet/cetTrainCourse/cetTrainCourse_au";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_evaTable")
    public String cetTrainCourse_evaTable(int trainId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        modelMap.put("cetTrainEvaTableMap", cetTrainEvaTableService.findAll());

        return "cet/cetTrainCourse/cetTrainCourse_evaTable";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_evaTable", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_evaTable(int trainId, @RequestParam(value = "ids[]") Integer[] ids, int evaTableId) {

        cetTrainCourseService.evaTable(trainId, ids, evaTableId);
        //logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程评估表：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:import")
    @RequestMapping("/cetTrainCourse_import")
    public String cetTrainCourse_import() {

        return "cet/cetTrainCourse/cetTrainCourse_import";
    }

    @RequiresPermissions("cetTrainCourse:import")
    @RequestMapping(value="/cetTrainCourse_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_import( int trainId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsTrainCourse> records = new ArrayList<XlsTrainCourse>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        records.addAll(XlsUpload.fetchTrainCourses(sheet));

        int successCount = cetTrainCourseService.imports(records, trainId);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }
}
