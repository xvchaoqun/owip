package controller.sc.scGroup;

import controller.ScGroupBaseController;
import domain.sc.scGroup.ScGroupTopicUnit;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScGroupTopicUnitController extends ScGroupBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroupTopicUnit:list")
    @RequestMapping("/scGroupTopicUnit")
    public String scGroupTopicUnit(int topicId, ModelMap modelMap) {

        modelMap.put("units", scGroupTopicService.getUnits(topicId));
        return "sc/scGroup/scGroupTopicUnit/scGroupTopicUnit_page";
    }

    /*@RequiresPermissions("scGroupTopicUnit:list")
    @RequestMapping("/scGroupTopicUnit_data")
    public void scGroupTopicUnit_data(HttpServletResponse response,
                                    Integer topicId,
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

        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (topicId!=null) {
            criteria.andTopicIdEqualTo(topicId);
        }

        long count = scGroupTopicUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupTopicUnit> records= scGroupTopicUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scGroupTopicUnit.class, scGroupTopicUnitMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }*/

    @RequiresPermissions("scGroupTopicUnit:edit")
    @RequestMapping(value = "/scGroupTopicUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupTopicUnit_au(ScGroupTopicUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scGroupTopicUnitService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "添加干部小组会议题涉及单位：%s", record.getId()));
        } else {

            scGroupTopicUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "更新干部小组会议题涉及单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupTopicUnit:edit")
    @RequestMapping("/scGroupTopicUnit_au")
    public String scGroupTopicUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScGroupTopicUnit scGroupTopicUnit = scGroupTopicUnitMapper.selectByPrimaryKey(id);
            modelMap.put("scGroupTopicUnit", scGroupTopicUnit);
        }
        return "sc/scGroup/scGroupTopicUnit/scGroupTopicUnit_au";
    }

    @RequiresPermissions("scGroupTopicUnit:del")
    @RequestMapping(value = "/scGroupTopicUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupTopicUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scGroupTopicUnitService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "删除干部小组会议题涉及单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupTopicUnit:del")
    @RequestMapping(value = "/scGroupTopicUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupTopicUnitService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "批量删除干部小组会议题涉及单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
