package controller.train;

import bean.XlsTrainCourse;
import bean.XlsUpload;
import controller.TrainBaseController;
import domain.train.Train;
import domain.train.TrainCourse;
import domain.train.TrainCourseExample;
import domain.train.TrainCourseExample.Criteria;
import domain.train.TrainEvaTable;
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
import sys.tool.jackson.Select2Option;
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
public class TrainCourseController extends TrainBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("trainCourse:list")
    @RequestMapping("/trainCourse")
    public String trainCourse(int trainId, ModelMap modelMap) {

        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));
        return "train/trainCourse/trainCourse_page";
    }

    @RequiresPermissions("trainCourse:list")
    @RequestMapping("/trainCourse_data")
    public void trainCourse_data(HttpServletResponse response,
                                 int trainId, String name,
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

        TrainCourseExample example = new TrainCourseExample();
        Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId)
                .andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            trainCourse_export(example, response);
            return;
        }

        int count = trainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainCourse> records = trainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(trainCourse.class, trainCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("trainCourse:edit")
    @RequestMapping(value = "/trainCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainCourse_au(TrainCourse record,
                                 String _startTime,
                                 String _endTime,
                                 Boolean isGlobal,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD_HH_MM));
        record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD_HH_MM));
        record.setIsGlobal(BooleanUtils.isTrue(isGlobal));

        if(record.getStartTime()!=null && record.getEndTime()!=null
                && record.getStartTime().after(record.getEndTime())){
            return failed("开始时间不能晚于结束时间");
        }
        if (id == null) {

            trainCourseService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加培训课程：%s", record.getId()));
        } else {

            trainCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainCourse:edit")
    @RequestMapping("/trainCourse_au")
    public String trainCourse_au(Integer id, Integer trainId, ModelMap modelMap) {

        if (id != null) {
            TrainCourse trainCourse = trainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("trainCourse", trainCourse);

            trainId = trainCourse.getTrainId();
        }
        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));

        return "train/trainCourse/trainCourse_au";
    }

    @RequiresPermissions("trainCourse:edit")
    @RequestMapping("/trainCourse_evaTable")
    public String trainCourse_evaTable(int trainId, ModelMap modelMap) {

        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));
        modelMap.put("trainEvaTableMap", trainEvaTableService.findAll());

        return "train/trainCourse/trainCourse_evaTable";
    }

    @RequiresPermissions("trainCourse:edit")
    @RequestMapping(value = "/trainCourse_evaTable", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainCourse_evaTable(int trainId, @RequestParam(value = "ids[]") Integer[] ids, int evaTableId) {

        trainCourseService.evaTable(trainId, ids, evaTableId);
        //logger.info(addLog(SystemConstants.LOG_ADMIN, "更新培训课程评估表：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainCourse:del")
    @RequestMapping(value = "/trainCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //trainCourseService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除培训课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainCourse:del")
    @RequestMapping(value = "/trainCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            trainCourseService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("trainCourse:changeOrder")
    @RequestMapping(value = "/trainCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        trainCourseService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void trainCourse_export(TrainCourseExample example, HttpServletResponse response) {

        List<TrainCourse> records = trainCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次", "课程名称", "教师名称", "开始时间", "结束时间", "评估表", "评课情况（已完成/总数）"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            TrainCourse record = records.get(i);
            Train train = trainMapper.selectByPrimaryKey(record.getTrainId());
            TrainEvaTable trainEvaTable = trainEvaTableMapper.selectByPrimaryKey(record.getEvaTableId());
            String[] values = {
                    train.getName(),
                    record.getName(),
                    record.getTeacher(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    trainEvaTable.getName(),
                    record.getFinishCount() + "/" + train.getTotalCount()
            };
            valuesList.add(values);
        }
        String fileName = "培训课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/trainCourse_selects")
    @ResponseBody
    public Map trainCourse_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        TrainCourseExample example = new TrainCourseExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        int count = trainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainCourse> trainCourses = trainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != trainCourses && trainCourses.size() > 0) {

            for (TrainCourse trainCourse : trainCourses) {

                Select2Option option = new Select2Option();
                option.setText(trainCourse.getName());
                option.setId(trainCourse.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("trainCourse:import")
    @RequestMapping("/trainCourse_import")
    public String trainCourse_import() {

        return "train/trainCourse/trainCourse_import";
    }

    @RequiresPermissions("trainCourse:import")
    @RequestMapping(value="/trainCourse_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_trainCourse_import( int trainId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsTrainCourse> records = new ArrayList<XlsTrainCourse>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        records.addAll(XlsUpload.fetchTrainCourses(sheet));

        int successCount = trainCourseService.imports(records, trainId);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }
}
