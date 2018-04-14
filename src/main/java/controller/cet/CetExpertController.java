package controller.cet;

import domain.cet.CetExpert;
import domain.cet.CetExpertExample;
import domain.cet.CetExpertExample.Criteria;
import domain.cet.CetExpertView;
import domain.cet.CetExpertViewExample;
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
public class CetExpertController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetExpert:list")
    @RequestMapping("/cetExpert")
    public String cetExpert() {

        return "cet/cetExpert/cetExpert_page";
    }

    @RequiresPermissions("cetExpert:list")
    @RequestMapping("/cetExpert_data")
    public void cetExpert_data(HttpServletResponse response,
                                    String realname,
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

        CetExpertViewExample example = new CetExpertViewExample();
        CetExpertViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike("%" + realname + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetExpert_export(example, response);
            return;
        }

        long count = cetExpertViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetExpertView> records= cetExpertViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetExpert.class, cetExpertMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetExpert:edit")
    @RequestMapping(value = "/cetExpert_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_au(CetExpert record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetExpertService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加专家信息：%s", record.getId()));
        } else {

            cetExpertService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新专家信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:edit")
    @RequestMapping("/cetExpert_au")
    public String cetExpert_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetExpert cetExpert = cetExpertMapper.selectByPrimaryKey(id);
            modelMap.put("cetExpert", cetExpert);
        }
        return "cet/cetExpert/cetExpert_au";
    }

    @RequiresPermissions("cetExpert:del")
    @RequestMapping(value = "/cetExpert_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetExpertService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除专家信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:del")
    @RequestMapping(value = "/cetExpert_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetExpert_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetExpertService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除专家信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:changeOrder")
    @RequestMapping(value = "/cetExpert_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetExpertService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "专家信息调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetExpert_export(CetExpertViewExample example, HttpServletResponse response) {

        List<CetExpertView> records = cetExpertViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","所在单位|100","职务和职称|100","联系方式|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetExpertView record = records.get(i);
            String[] values = {
                record.getRealname(),
                            record.getUnit(),
                            record.getPost(),
                            record.getContact(),
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "专家信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetExpert_selects")
    @ResponseBody
    public Map cetExpert_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetExpertExample example = new CetExpertExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andRealnameLike("%" + searchStr + "%");
        }

        long count = cetExpertMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetExpert> cetExperts = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != cetExperts && cetExperts.size()>0){

            for(CetExpert cetExpert:cetExperts){

                Map<String, Object> option = new HashMap<>();
                option.put("id", cetExpert.getId());
                option.put("text", cetExpert.getRealname());
                option.put("unit", cetExpert.getUnit());
                option.put("post", cetExpert.getPost());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
