package controller.sc.scMatter;

import controller.sc.ScBaseController;
import domain.sc.scMatter.ScMatter;
import domain.sc.scMatter.ScMatterView;
import domain.sc.scMatter.ScMatterViewExample;
import domain.sys.SysUserView;
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
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScMatterController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatter:list")
    @RequestMapping("/scMatter")
    public String scMatter(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/sc/scMatterItem";
        }

        return "sc/scMatter/scMatter/scMatter_page";
    }

    @RequiresPermissions("scMatter:list")
    @RequestMapping("/scMatter_data")
    public void scMatter_data(HttpServletResponse response,
                                    Integer year,
                                   Boolean type,
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

        ScMatterViewExample example = new ScMatterViewExample();
        ScMatterViewExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false);
        example.setOrderByClause("draw_time desc, id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        long count = scMatterViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterView> records= scMatterViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatter.class, scMatterMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatter:edit")
    @RequestMapping(value = "/scMatter_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatter_au(ScMatter record,
                              @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                              HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            scMatterService.insertSelective(record, userIds);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "添加个人有关事项：%s", record.getId()));
        } else {

            scMatterService.updateByPrimaryKeySelective(record, userIds);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "更新个人有关事项：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatter:edit")
    @RequestMapping("/scMatter_au")
    public String scMatter_au(Integer id,
                              @RequestParam(required = false, defaultValue = "0")Boolean type,
                              ModelMap modelMap) {

        if (id != null) {
            ScMatter scMatter = scMatterMapper.selectByPrimaryKey(id);
            modelMap.put("scMatter", scMatter);
            if(scMatter!=null){
                type = scMatter.getType();
                List<SysUserView> itemUserList = scMatterService.getItemUserList(id);
                modelMap.put("itemUserList", itemUserList);
            }
        }

        modelMap.put("type", type);

        return "sc/scMatter/scMatter/scMatter_au";
    }

    @RequiresPermissions("scMatter:del")
    @RequestMapping(value = "/scMatter_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(value = "ids[]") Integer[] ids,
                        ModelMap modelMap) {

        if (null != ids && ids.length>0){
            scMatterService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "批量删除个人有关事项：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 查看集中填报下的干部
    @RequiresPermissions("scMatter:*")
    @RequestMapping("/scMatter_selectCadres_tree")
    @ResponseBody
    public Map scMatter_selectCadres_tree(Integer id) throws IOException {

        Set<Integer> selectIdSet = null;
        if(id!=null){
            selectIdSet = scMatterService.getItemUserIds(id);
        }
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

   /* // 更新集中填报下的干部
    @RequiresPermissions("scMatter:*")
    @RequestMapping(value = "/scMatter_selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatter_selectCadres(Integer id,
                                        @RequestParam(value = "userIds[]", required = false) Integer[] userIds) {

        scMatterService.updateUserIds(id, userIds);
        return success(FormUtils.SUCCESS);
    }*/

/*
    public void scMatter_export(ScMatterExample example, HttpServletResponse response) {

        List<ScMatter> records = scMatterMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","填报类型|100","领表时间|100","应交回时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatter record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getType() +"",
                            DateUtils.formatDate(record.getDrawTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            DateUtils.formatDate(record.getHandTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
*/
/*
    @RequestMapping("/scMatter_selects")
    @ResponseBody
    public Map scMatter_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterExample example = new ScMatterExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        int count = scMatterMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatter> scMatters = scMatterMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatters && scMatters.size()>0){

            for(ScMatter scMatter:scMatters){

                Select2Option option = new Select2Option();
                option.setText(scMatter.getName());
                option.setId(scMatter.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
