package controller.cet;

import domain.cet.CetTrainTraineeType;
import domain.cet.CetTrainTraineeTypeExample;
import domain.cet.CetTrainTraineeTypeExample.Criteria;
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
public class CetTrainTraineeTypeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainTraineeType:list")
    @RequestMapping("/cetTrainTraineeType")
    public String cetTrainTraineeType() {

        return "cet/cetTrainTraineeType/cetTrainTraineeType_page";
    }

    @RequiresPermissions("cetTrainTraineeType:list")
    @RequestMapping("/cetTrainTraineeType_data")
    public void cetTrainTraineeType_data(HttpServletResponse response,
                                    Integer trainId,
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

        CetTrainTraineeTypeExample example = new CetTrainTraineeTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (trainId!=null) {
            criteria.andTrainIdEqualTo(trainId);
        }
        if (traineeTypeId!=null) {
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainTraineeType_export(example, response);
            return;
        }

        long count = cetTrainTraineeTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainTraineeType> records= cetTrainTraineeTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainTraineeType.class, cetTrainTraineeTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainTraineeType:edit")
    @RequestMapping(value = "/cetTrainTraineeType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainTraineeType_au(CetTrainTraineeType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTrainTraineeTypeService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加培训班关联的参训人员类型：%s", record.getId()));
        } else {

            cetTrainTraineeTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新培训班关联的参训人员类型：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainTraineeType:edit")
    @RequestMapping("/cetTrainTraineeType_au")
    public String cetTrainTraineeType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainTraineeType cetTrainTraineeType = cetTrainTraineeTypeMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainTraineeType", cetTrainTraineeType);
        }
        return "cet/cetTrainTraineeType/cetTrainTraineeType_au";
    }

    @RequiresPermissions("cetTrainTraineeType:del")
    @RequestMapping(value = "/cetTrainTraineeType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainTraineeType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTrainTraineeTypeService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除培训班关联的参训人员类型：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainTraineeType:del")
    @RequestMapping(value = "/cetTrainTraineeType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainTraineeType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainTraineeTypeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训班关联的参训人员类型：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetTrainTraineeType_export(CetTrainTraineeTypeExample example, HttpServletResponse response) {

        List<CetTrainTraineeType> records = cetTrainTraineeTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班|100","参训人员类型|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainTraineeType record = records.get(i);
            String[] values = {
                record.getTrainId()+"",
                            record.getTraineeTypeId()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训班关联的参训人员类型_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
