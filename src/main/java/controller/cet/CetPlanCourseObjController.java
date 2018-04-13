package controller.cet;

import domain.cet.CetPlanCourseObj;
import domain.cet.CetPlanCourseObjExample;
import domain.cet.CetPlanCourseObjExample.Criteria;
import domain.cet.CetProjectObj;
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
import sys.constants.SystemConstants;
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
@RequestMapping("/cet")
public class CetPlanCourseObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourseObj")
    public String cetPlanCourseObj() {

        return "cet/cetPlanCourseObj/cetPlanCourseObj_page";
    }

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourseObj_data")
    public void cetPlanCourseObj_data(HttpServletResponse response,
                                    Integer planCourseId,
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

        CetPlanCourseObjExample example = new CetPlanCourseObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (planCourseId!=null) {
            criteria.andPlanCourseIdEqualTo(planCourseId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetPlanCourseObj_export(example, response);
            return;
        }

        long count = cetPlanCourseObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPlanCourseObj> records= cetPlanCourseObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPlanCourseObj.class, cetPlanCourseObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourseObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObj_au(CetPlanCourseObj record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cetPlanCourseObjService.idDuplicate(id, record.getPlanCourseId(), record.getObjId())) {
            return failed("添加重复");
        }
        if (id == null) {
            cetPlanCourseObjService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加选课学员：%s", record.getId()));
        } else {

            cetPlanCourseObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新选课学员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourseObj_au")
    public String cetPlanCourseObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjMapper.selectByPrimaryKey(id);
            modelMap.put("cetPlanCourseObj", cetPlanCourseObj);
        }
        return "cet/cetPlanCourseObj/cetPlanCourseObj_au";
    }

    // 上传学习心得
    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourseObj_uploadNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObj_uploadNote(int id, int planCourseId, MultipartFile _file,
                                            HttpServletRequest request) throws IOException, InterruptedException {

        if(_file!=null && !_file.isEmpty()) {

            CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.get(id, planCourseId);

            CetPlanCourseObj record = new CetPlanCourseObj();
            record.setId(cetPlanCourseObj.getId());
            record.setNote(uploadDocOrPdf(_file, "cetPlanCourseObj_note"));

            cetPlanCourseObjService.updateByPrimaryKeySelective(record);

            logger.info(addLog(SystemConstants.LOG_CET, "上传心得体会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourseObj_uploadNote")
    public String cetPlanCourseObj_uploadNote(int id, ModelMap modelMap) {

        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(id);
        Integer userId = cetProjectObj.getUserId();

        modelMap.put("cetProjectObj", cetProjectObj);
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "cet/cetPlanCourseObj/cetPlanCourseObj_uploadNote";
    }

    // 完成自学/未完成自学
    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourseObj_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObj_finish(boolean finish, int planCourseId,
                                         @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                         HttpServletRequest request) {

        cetPlanCourseObjService.finish(ids, finish, planCourseId);
        logger.info(addLog(SystemConstants.LOG_CET, "完成自学/未完成自学： %s, %s, %s",
                StringUtils.join(ids, ","), finish, planCourseId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:del")
    @RequestMapping(value = "/cetPlanCourseObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourseObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetPlanCourseObjService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除选课学员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    public void cetPlanCourseObj_export(CetPlanCourseObjExample example, HttpServletResponse response) {

        List<CetPlanCourseObj> records = cetPlanCourseObjMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属培训课程|100","培训对象|100","提交学习心得数|100","是否结业|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPlanCourseObj record = records.get(i);
            String[] values = {
                record.getPlanCourseId()+"",
                            record.getObjId()+"",
                            record.getNum()+"",
                            record.getIsFinished() +""
            };
            valuesList.add(values);
        }
        String fileName = "选课学员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
