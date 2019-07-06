package controller.cet;

import domain.cet.*;
import domain.cet.CetPlanCourseObjResultExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetPlanCourseObjResultController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourseObjResult")
    public String cetPlanCourseObjResult() {

        return "cet/cetPlanCourseObjResult/cetPlanCourseObjResult_page";
    }

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourseObjResult_data")
    public void cetPlanCourseObjResult_data(HttpServletResponse response,
                                    Integer trainCourseObjId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (trainCourseObjId!=null) {
            criteria.andPlanCourseObjIdEqualTo(trainCourseObjId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetPlanCourseObjResult_export(example, response);
            return;
        }

        long count = cetPlanCourseObjResultMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPlanCourseObjResult> records= cetPlanCourseObjResultMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPlanCourseObjResult.class, cetPlanCourseObjResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourseObjResult_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObjResult_au(CetPlanCourseObj cetPlanCourseObj, HttpServletRequest request) {

        cetPlanCourseObjResultService.add(cetPlanCourseObj, request);

        logger.info(addLog(LogConstants.LOG_CET, "添加上级网上专题班完成结果, %s",
                JSONUtils.toString(cetPlanCourseObj, false)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourseObjResult_au")
    public String cetPlanCourseObjResult_au(int planCourseId,
                                            int objId,
                                            Boolean view,
                                            ModelMap modelMap) {

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        int courseId = cetPlanCourse.getCourseId();
        Map<Integer, CetCourseItem> cetCourseItemMap = cetCourseItemService.findAll(courseId);
        modelMap.put("cetCourseItemMap", cetCourseItemMap);

        CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.get(objId, planCourseId);
        modelMap.put("cetPlanCourseObj", cetPlanCourseObj);

        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(objId);
        int userId = cetProjectObj.getUserId();
        modelMap.put("sysUser", CmTag.getUserById(userId));

        Map<Integer, CetPlanCourseObjResult> resultMap = new HashMap<>();
        int planCourseObjId = cetPlanCourseObj.getId();
        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        example.createCriteria().andPlanCourseObjIdEqualTo(planCourseObjId);
        List<CetPlanCourseObjResult> cetPlanCourseObjResults = cetPlanCourseObjResultMapper.selectByExample(example);
        for (CetPlanCourseObjResult cetPlanCourseObjResult : cetPlanCourseObjResults) {
            resultMap.put(cetPlanCourseObjResult.getCourseItemId(), cetPlanCourseObjResult);
        }
        modelMap.put("resultMap", resultMap);

        if(BooleanUtils.isTrue(view)) return "cet/cetPlanCourseObjResult/cetPlanCourseObjResult_detail";
        return "cet/cetPlanCourseObjResult/cetPlanCourseObjResult_au";
    }

    @RequiresPermissions("cetProjectPlan:clear")
    @RequestMapping(value = "/cetPlanCourseObjResult_clear", method = RequestMethod.POST)
    @ResponseBody
    public Map cetPlanCourseObjResult_clear(HttpServletRequest request,
                                            int planCourseId,
                                            // objIds
                                            @RequestParam(value = "ids[]") Integer[] ids,
                                            ModelMap modelMap) {

        if (null != ids && ids.length>0){
            cetPlanCourseObjResultService.clear(planCourseId, ids);
            logger.info(addLog(LogConstants.LOG_CET, "清除结果，上级网上专题班完成情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:import")
    @RequestMapping("/cetPlanCourseObjResult_import")
    public String cetPlanCourseObjResult_import() {

        return "cet/cetPlanCourseObjResult/cetPlanCourseObjResult_import";
    }

    @RequiresPermissions("cetProjectPlan:import")
    @RequestMapping(value="/cetPlanCourseObjResult_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObjResult_import( int planCourseId,
                                                 HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        int successCount = cetPlanCourseObjResultService.imports(xlsRows, planCourseId);
        int totalCount = xlsRows.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入培训结果成功，总共{0}条记录，其中成功导入{1}条记录，{2}条失败",
                totalCount, successCount, totalCount - successCount));

        return resultMap;
    }
    
    public void cetPlanCourseObjResult_export(CetPlanCourseObjResultExample example, HttpServletResponse response) {

        List<CetPlanCourseObjResult> records = cetPlanCourseObjResultMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属培训课程|100","所属课程专题班|100","完成课程数|100","完成学时数|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPlanCourseObjResult record = records.get(i);
            String[] values = {
                record.getPlanCourseObjId()+"",
                            record.getCourseItemId()+"",
                            record.getCourseNum()+"",
                            record.getPeriod() + ""
            };
            valuesList.add(values);
        }
        String fileName = "上级网上专题班完成情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
