package controller.cet;

import domain.cet.CetTrainCourseObjResult;
import domain.cet.CetTrainCourseObjResultExample;
import domain.cet.CetTrainCourseObjResultExample.Criteria;
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
public class CetTrainCourseObjResultController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourseObjResult:list")
    @RequestMapping("/cetTrainCourseObjResult")
    public String cetTrainCourseObjResult() {

        return "cet/cetTrainCourseObjResult/cetTrainCourseObjResult_page";
    }

    @RequiresPermissions("cetTrainCourseObjResult:list")
    @RequestMapping("/cetTrainCourseObjResult_data")
    public void cetTrainCourseObjResult_data(HttpServletResponse response,
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

        CetTrainCourseObjResultExample example = new CetTrainCourseObjResultExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (trainCourseObjId!=null) {
            criteria.andTrainCourseObjIdEqualTo(trainCourseObjId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainCourseObjResult_export(example, response);
            return;
        }

        long count = cetTrainCourseObjResultMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourseObjResult> records= cetTrainCourseObjResultMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainCourseObjResult.class, cetTrainCourseObjResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourseObjResult:edit")
    @RequestMapping(value = "/cetTrainCourseObjResult_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourseObjResult_au(CetTrainCourseObjResult record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cetTrainCourseObjResultService.idDuplicate(id, record.getTrainCourseObjId(), record.getCourseItemId())) {
            return failed("添加重复");
        }
        if (id == null) {
            cetTrainCourseObjResultService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加上级网上专题班完成情况：%s", record.getId()));
        } else {

            cetTrainCourseObjResultService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新上级网上专题班完成情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourseObjResult:edit")
    @RequestMapping("/cetTrainCourseObjResult_au")
    public String cetTrainCourseObjResult_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainCourseObjResult cetTrainCourseObjResult = cetTrainCourseObjResultMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourseObjResult", cetTrainCourseObjResult);
        }
        return "cet/cetTrainCourseObjResult/cetTrainCourseObjResult_au";
    }

    @RequiresPermissions("cetTrainCourseObjResult:del")
    @RequestMapping(value = "/cetTrainCourseObjResult_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainCourseObjResult_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainCourseObjResultService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除上级网上专题班完成情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourseObjResult_export(CetTrainCourseObjResultExample example, HttpServletResponse response) {

        List<CetTrainCourseObjResult> records = cetTrainCourseObjResultMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属培训课程|100","所属课程专题班|100","完成课程数|100","完成学时数|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourseObjResult record = records.get(i);
            String[] values = {
                record.getTrainCourseObjId()+"",
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
