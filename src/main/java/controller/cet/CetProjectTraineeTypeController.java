package controller.cet;

import domain.cet.CetProjectTraineeType;
import domain.cet.CetProjectTraineeTypeExample;
import domain.cet.CetProjectTraineeTypeExample.Criteria;
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
import sys.constants.LogConstants;
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
public class CetProjectTraineeTypeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectTraineeType:list")
    @RequestMapping("/cetProjectTraineeType")
    public String cetProjectTraineeType() {

        return "cet/cetProjectTraineeType/cetProjectTraineeType_page";
    }

    @RequiresPermissions("cetProjectTraineeType:list")
    @RequestMapping("/cetProjectTraineeType_data")
    public void cetProjectTraineeType_data(HttpServletResponse response,
                                    Integer projectId,
                                    Integer traineeTypeId,
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

        CetProjectTraineeTypeExample example = new CetProjectTraineeTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (projectId!=null) {
            criteria.andProjectIdEqualTo(projectId);
        }
        if (traineeTypeId!=null) {
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetProjectTraineeType_export(example, response);
            return;
        }

        long count = cetProjectTraineeTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectTraineeType> records= cetProjectTraineeTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProjectTraineeType.class, cetProjectTraineeTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectTraineeType:edit")
    @RequestMapping(value = "/cetProjectTraineeType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectTraineeType_au(CetProjectTraineeType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetProjectTraineeTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加培训班关联的参训人员类型：%s", record.getId()));
        } else {

            cetProjectTraineeTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新培训班关联的参训人员类型：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectTraineeType:edit")
    @RequestMapping("/cetProjectTraineeType_au")
    public String cetProjectTraineeType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetProjectTraineeType cetProjectTraineeType = cetProjectTraineeTypeMapper.selectByPrimaryKey(id);
            modelMap.put("cetProjectTraineeType", cetProjectTraineeType);
        }
        return "cet/cetProjectTraineeType/cetProjectTraineeType_au";
    }

    @RequiresPermissions("cetProjectTraineeType:del")
    @RequestMapping(value = "/cetProjectTraineeType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectTraineeType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetProjectTraineeTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除培训班关联的参训人员类型：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectTraineeType:del")
    @RequestMapping(value = "/cetProjectTraineeType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectTraineeType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetProjectTraineeTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训班关联的参训人员类型：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetProjectTraineeType_export(CetProjectTraineeTypeExample example, HttpServletResponse response) {

        List<CetProjectTraineeType> records = cetProjectTraineeTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班|100","参训人员类型|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetProjectTraineeType record = records.get(i);
            String[] values = {
                record.getProjectId()+"",
                            record.getTraineeTypeId()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训班关联的参训人员类型_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
