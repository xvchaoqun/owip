package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterAccess;
import domain.sc.ScMatterAccessExample;
import domain.sc.ScMatterAccessExample.Criteria;
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
public class ScMatterAccessController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterAccess:list")
    @RequestMapping("/scMatterAccess")
    public String scMatterAccess() {

        return "sc/scMatterAccess/scMatterAccess_page";
    }

    @RequiresPermissions("scMatterAccess:list")
    @RequestMapping("/scMatterAccess_data")
    public void scMatterAccess_data(HttpServletResponse response,
                                    Date accessDate,
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

        ScMatterAccessExample example = new ScMatterAccessExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("access_date desc, id desc");

        if (accessDate!=null) {
            criteria.andAccessDateEqualTo(accessDate);
        }

        long count = scMatterAccessMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterAccess> records= scMatterAccessMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterAccess.class, scMatterAccessMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping(value = "/scMatterAccess_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_au(ScMatterAccess record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterAccessService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-调阅记录：%s", record.getId()));
        } else {

            scMatterAccessService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-调阅记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_au")
    public String scMatterAccess_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterAccess scMatterAccess = scMatterAccessMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterAccess", scMatterAccess);
        }
        return "sc/scMatterAccess/scMatterAccess_au";
    }

    @RequiresPermissions("scMatterAccess:del")
    @RequestMapping(value = "/scMatterAccess_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterAccessService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-调阅记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccess:del")
    @RequestMapping(value = "/scMatterAccess_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterAccessService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-调阅记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scMatterAccess_selects")
    @ResponseBody
    public Map scMatterAccess_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterAccessExample example = new ScMatterAccessExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        long count = scMatterAccessMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterAccess> scMatterAccesss = scMatterAccessMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterAccesss && scMatterAccesss.size()>0){

            for(ScMatterAccess scMatterAccess:scMatterAccesss){

                Select2Option option = new Select2Option();
                option.setText(scMatterAccess.getReceiver());
                option.setId(scMatterAccess.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
