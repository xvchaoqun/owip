package controller.cet;

import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
import domain.cet.CetDiscussGroupObjExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
public class CetDiscussGroupObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetDiscussGroupObj:list")
    @RequestMapping("/cetDiscussGroupObj")
    public String cetDiscussGroupObj() {

        return "cet/cetDiscussGroupObj/cetDiscussGroupObj_page";
    }

    @RequiresPermissions("cetDiscussGroupObj:list")
    @RequestMapping("/cetDiscussGroupObj_data")
    public void cetDiscussGroupObj_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "cet_discuss_group_obj") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer discussId,
                                    Integer discussGroupId,
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

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (discussId!=null) {
            criteria.andDiscussIdEqualTo(discussId);
        }
        if (discussGroupId!=null) {
            criteria.andDiscussGroupIdEqualTo(discussGroupId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetDiscussGroupObj_export(example, response);
            return;
        }

        long count = cetDiscussGroupObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetDiscussGroupObj> records= cetDiscussGroupObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetDiscussGroupObj.class, cetDiscussGroupObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetDiscussGroupObj:edit")
    @RequestMapping(value = "/cetDiscussGroupObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroupObj_au(CetDiscussGroupObj record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            cetDiscussGroupObjService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加小组成员：%s", record.getId()));
        } else {

            cetDiscussGroupObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新小组成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroupObj:edit")
    @RequestMapping("/cetDiscussGroupObj_au")
    public String cetDiscussGroupObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetDiscussGroupObj cetDiscussGroupObj = cetDiscussGroupObjMapper.selectByPrimaryKey(id);
            modelMap.put("cetDiscussGroupObj", cetDiscussGroupObj);
        }
        return "cet/cetDiscussGroupObj/cetDiscussGroupObj_au";
    }

    @RequiresPermissions("cetDiscussGroupObj:del")
    @RequestMapping(value = "/cetDiscussGroupObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroupObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetDiscussGroupObjService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除小组成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroupObj:del")
    @RequestMapping(value = "/cetDiscussGroupObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetDiscussGroupObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetDiscussGroupObjService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除小组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetDiscussGroupObj_export(CetDiscussGroupObjExample example, HttpServletResponse response) {

        List<CetDiscussGroupObj> records = cetDiscussGroupObjMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"分组讨论|100","研讨小组|100","小组成员|100","是否实际完成|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetDiscussGroupObj record = records.get(i);
            String[] values = {
                record.getDiscussId()+"",
                            record.getDiscussGroupId()+"",
                            record.getObjId()+"",
                            record.getIsFinished()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "小组成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
