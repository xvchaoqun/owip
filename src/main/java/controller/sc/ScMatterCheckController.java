package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterCheck;
import domain.sc.ScMatterCheckExample;
import domain.sc.ScMatterCheckExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterCheckController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterCheck:list")
    @RequestMapping("/scMatterCheck")
    public String scMatterCheck() {

        return "sc/scMatterCheck/scMatterCheck_page";
    }

    @RequiresPermissions("scMatterCheck:list")
    @RequestMapping("/scMatterCheck_data")
    public void scMatterCheck_data(HttpServletResponse response,
                                    Date checkDate,
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

        ScMatterCheckExample example = new ScMatterCheckExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("check_date desc, id desc");

        if (checkDate!=null) {
            criteria.andCheckDateEqualTo(checkDate);
        }

        long count = scMatterCheckMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterCheck> records= scMatterCheckMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterCheck.class, scMatterCheckMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterCheck:edit")
    @RequestMapping(value = "/scMatterCheck_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheck_au(ScMatterCheck record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterCheckService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-抽查核实：%s", record.getId()));
        } else {

            scMatterCheckService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-抽查核实：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheck:edit")
    @RequestMapping("/scMatterCheck_au")
    public String scMatterCheck_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterCheck scMatterCheck = scMatterCheckMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterCheck", scMatterCheck);
        }
        return "sc/scMatterCheck/scMatterCheck_au";
    }

    @RequiresPermissions("scMatterCheck:del")
    @RequestMapping(value = "/scMatterCheck_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheck_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterCheckService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-抽查核实：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheck:del")
    @RequestMapping(value = "/scMatterCheck_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterCheckService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-抽查核实：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scMatterCheck_selects")
    @ResponseBody
    public Map scMatterCheck_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterCheckExample example = new ScMatterCheckExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        long count = scMatterCheckMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterCheck> scMatterChecks = scMatterCheckMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterChecks && scMatterChecks.size()>0){

            for(ScMatterCheck scMatterCheck:scMatterChecks){

                Select2Option option = new Select2Option();
                option.setText(scMatterCheck.getNum()+"");
                option.setId(scMatterCheck.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
