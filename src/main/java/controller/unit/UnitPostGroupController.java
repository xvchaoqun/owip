package controller.unit;

import controller.BaseController;
import domain.unit.UnitPostGroup;
import domain.unit.UnitPostGroupExample;
import domain.unit.UnitPostGroupExample.Criteria;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class UnitPostGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPostGroup")
    public String unitPostGroup() {

        return "unit/unitPostGroup/unitPostGroup_page";
    }

   /* @RequiresPermissions("unitPostGroup:list")*/
    @RequestMapping("/unitPostGroup_data")
    @ResponseBody
    public void unitPostGroup_data(HttpServletResponse response, String name,
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

        UnitPostGroupExample example = new UnitPostGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            unitPostGroup_export(example, response);
            return;
        }

        long count = unitPostGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitPostGroup> records= unitPostGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(unitPostGroup.class, unitPostGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPostGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPostGroup_au(UnitPostGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (unitPostGroupService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            unitPostGroupService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加岗位分组：{0}", record.getId()));
        } else {

            unitPostGroupService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新岗位分组：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPostGroup_au")
    public String unitPostGroup_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            UnitPostGroup unitPostGroup = unitPostGroupMapper.selectByPrimaryKey(id);
            modelMap.put("unitPostGroup", unitPostGroup);
        }
        return "unit/unitPostGroup/unitPostGroup_au";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPostGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPostGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            unitPostGroupService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除岗位分组：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPostGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unitPostGroup_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitPostGroupService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除岗位分组：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void unitPostGroup_export(UnitPostGroupExample example, HttpServletResponse response) {

        List<UnitPostGroup> records = unitPostGroupMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"分组名称|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitPostGroup record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("岗位分组(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
    //岗位分组关联岗位
     @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPostGroup_addPost")
    public String unitPostGroup_addPost(HttpServletResponse response,
                                      String type, int id, String code,
                                        String name,
                                      Integer pageSize, Integer pageNo,ModelMap modelMap) {
        if (null == pageSize) {
            pageSize = 8;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        int count =0;
        UnitPostGroup unitPostGroup=unitPostGroupMapper.selectByPrimaryKey(id);

        modelMap.put("ids", unitPostGroup.getPostIds());
        /*  modelMap.put("relateUnitPosts", relateUnitPosts);*/

        if (StringUtils.equalsIgnoreCase(type, "edit")) {

            count = iUnitMapper.countUnitPostByGroup(id,name,code);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<UnitPostView> unitPosts=iUnitMapper.getUnitPostByGroup(id,name,code,new RowBounds((pageNo - 1) * pageSize, pageSize));

            modelMap.put("type", "edit");
            modelMap.put("unitPosts", unitPosts);

        } else {
            List<UnitPostView> relateUnitPosts = new ArrayList<>(); // 已关联的岗位
            List<Integer> postIdList= new ArrayList<>();
            if(unitPostGroup.getPostIds()!=null){

                String[] postIds = unitPostGroup.getPostIds().split(",");
                for (String postId : postIds) {
                    postIdList.add(Integer.valueOf(postId.trim()));
                }

                UnitPostViewExample example1=new UnitPostViewExample();
                example1.createCriteria().andIdIn(postIdList);

                count = (int) unitPostViewMapper.countByExample(example1);

                relateUnitPosts=unitPostViewMapper.selectByExampleWithRowbounds(example1,new RowBounds((pageNo - 1) * pageSize, pageSize));
                if ((pageNo - 1) * pageSize >= count) {

                    pageNo = Math.max(1, pageNo - 1);
                }
                modelMap.put("type", "add");
                modelMap.put("unitPosts", relateUnitPosts);
            }
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize+ "&id=" + id;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "unit/unitPostGroup/unitPostGroup_addPost";
    }

    //岗位分组关联岗位
    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPostGroup_table")
    public String unitPostGroup_table(HttpServletResponse response,
                                         String type, int id, String code,
                                      String name,
                                        Integer pageSize, Integer pageNo,ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = 8;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        int count =0;
        UnitPostGroup unitPostGroup=unitPostGroupMapper.selectByPrimaryKey(id);

        modelMap.put("ids", unitPostGroup.getPostIds());
        /*  modelMap.put("relateUnitPosts", relateUnitPosts);*/

        if (StringUtils.equalsIgnoreCase(type, "edit")) {

            count = iUnitMapper.countUnitPostByGroup(id,name,code);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<UnitPostView> unitPosts=iUnitMapper.getUnitPostByGroup(id,name,code,new RowBounds((pageNo - 1) * pageSize, pageSize));

            modelMap.put("type", "edit");
            modelMap.put("unitPosts", unitPosts);

        } else {
            List<UnitPostView> relateUnitPosts = new ArrayList<>(); // 已关联的岗位
            List<Integer> postIdList= new ArrayList<>();
            if(unitPostGroup.getPostIds()!=null){

                String[] postIds = unitPostGroup.getPostIds().split(",");
                for (String postId : postIds) {
                    postIdList.add(Integer.valueOf(postId.trim()));
                }

                UnitPostViewExample example1=new UnitPostViewExample();
                example1.createCriteria().andIdIn(postIdList);

                count = (int) unitPostViewMapper.countByExample(example1);

                relateUnitPosts=unitPostViewMapper.selectByExampleWithRowbounds(example1,new RowBounds((pageNo - 1) * pageSize, pageSize));
                if ((pageNo - 1) * pageSize >= count) {

                    pageNo = Math.max(1, pageNo - 1);
                }
                modelMap.put("type", "add");
                modelMap.put("unitPosts", relateUnitPosts);
            }
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize+ "&id=" + id;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "unit/unitPostGroup/unitPost_table";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPostGroup_addPost", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPostGroup_addPost(HttpServletRequest request,
                                         int id,
                                         @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        unitPostGroupService.updatePostAndGroupId(id,ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改岗位分组%s-关联岗位：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }
   /* @RequestMapping("/unitPostGroup_selects")
    @ResponseBody
    public Map unitPostGroup_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostGroupExample example = new UnitPostGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = unitPostGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<UnitPostGroup> records = unitPostGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(UnitPostGroup record:records){

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
    }*/
}
