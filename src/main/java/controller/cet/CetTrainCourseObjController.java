package controller.cet;

import domain.cet.CetTrainCourseObj;
import domain.cet.CetTrainCourseObjExample;
import domain.cet.CetTrainCourseObjExample.Criteria;
import mixin.MixinUtils;
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
public class CetTrainCourseObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourseObj:list")
    @RequestMapping("/cetTrainCourseObj")
    public String cetTrainCourseObj() {

        return "cet/cetTrainCourseObj/cetTrainCourseObj_page";
    }

    @RequiresPermissions("cetTrainCourseObj:list")
    @RequestMapping("/cetTrainCourseObj_data")
    public void cetTrainCourseObj_data(HttpServletResponse response,
                                    Integer trainCourseId,
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

        CetTrainCourseObjExample example = new CetTrainCourseObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (trainCourseId!=null) {
            criteria.andTrainCourseIdEqualTo(trainCourseId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainCourseObj_export(example, response);
            return;
        }

        long count = cetTrainCourseObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourseObj> records= cetTrainCourseObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainCourseObj.class, cetTrainCourseObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainCourseObj:edit")
    @RequestMapping(value = "/cetTrainCourseObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourseObj_au(CetTrainCourseObj record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cetTrainCourseObjService.idDuplicate(id, record.getTrainCourseId(), record.getObjId())) {
            return failed("添加重复");
        }
        if (id == null) {
            cetTrainCourseObjService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加选课学员：%s", record.getId()));
        } else {

            cetTrainCourseObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新选课学员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourseObj:edit")
    @RequestMapping("/cetTrainCourseObj_au")
    public String cetTrainCourseObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainCourseObj cetTrainCourseObj = cetTrainCourseObjMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourseObj", cetTrainCourseObj);
        }
        return "cet/cetTrainCourseObj/cetTrainCourseObj_au";
    }

    @RequiresPermissions("cetTrainCourseObj:del")
    @RequestMapping(value = "/cetTrainCourseObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourseObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTrainCourseObjService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除选课学员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourseObj_export(CetTrainCourseObjExample example, HttpServletResponse response) {

        List<CetTrainCourseObj> records = cetTrainCourseObjMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属培训课程|100","培训对象|100","提交学习心得数|100","是否结业|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourseObj record = records.get(i);
            String[] values = {
                record.getTrainCourseId()+"",
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
