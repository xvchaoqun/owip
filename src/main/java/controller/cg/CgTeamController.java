package controller.cg;

import domain.cg.CgTeam;
import domain.cg.CgTeamExample;
import domain.cg.CgTeamExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cg")
public class CgTeamController extends CgBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgTeam:list")
    @RequestMapping("/cgTeam")
    public String cgTeam() {

        return "cg/cgTeam/cgTeam_page";
    }

    @RequiresPermissions("cgTeam:list")
    @RequestMapping("/cgTeam_data")
    @ResponseBody
    public void cgTeam_data(HttpServletResponse response,
                                    String name,
                                    Byte type,
                                    Integer category,
                                
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

        CgTeamExample example = new CgTeamExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (category!=null) {
            criteria.andCategoryEqualTo(category);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgTeam_export(example, response);
            return;
        }

        long count = cgTeamMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgTeam> records= cgTeamMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgTeam.class, cgTeamMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgTeam:edit")
    @RequestMapping(value = "/cgTeam_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_au(CgTeam record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (id == null) {
            
            cgTeamService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加委员会和领导小组：{0}", record.getId()));
        } else {

            cgTeamService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新委员会和领导小组：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgTeam:edit")
    @RequestMapping("/cgTeam_au")
    public String cgTeam_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(id);
            modelMap.put("cgTeam", cgTeam);
        }
        return "cg/cgTeam/cgTeam_au";
    }

    @RequiresPermissions("cgTeam:del")
    @RequestMapping(value = "/cgTeam_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgTeamService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除委员会和领导小组：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgTeam:del")
    @RequestMapping(value = "/cgTeam_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgTeam_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cgTeamService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除委员会和领导小组：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cgTeam:changeOrder")
    @RequestMapping(value = "/cgTeam_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cgTeamService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CG, "委员会和领导小组调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void cgTeam_export(CgTeamExample example, HttpServletResponse response) {

        List<CgTeam> records = cgTeamMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|100","类型|100","类别|100","是否需要调整|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgTeam record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getType()+"",
                            record.getCategory()+"",
                            record.getNeedAdjust()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("委员会和领导小组(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cgTeam_selects")
    @ResponseBody
    public Map cgTeam_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgTeamExample example = new CgTeamExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = cgTeamMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CgTeam> records = cgTeamMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CgTeam record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
