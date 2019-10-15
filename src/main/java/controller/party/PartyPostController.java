package controller.party;

import controller.BaseController;
import domain.party.PartyPost;
import domain.party.PartyPostExample;
import domain.party.PartyPostExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class PartyPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyPost:list")
    @RequestMapping("/party/partyPost")
    public String partyPost(Integer userId,
                            HttpServletResponse response,
                            ModelMap modelMap) {
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        return "party/partyPost/partyPost_page";
    }

    @RequiresPermissions("partyPost:list")
    @RequestMapping("/party/partyPost_data")
    @ResponseBody
    public void partyPost_data(HttpServletResponse response,
                                    Integer id,
                                    Integer userId,
                                    Date startDate,
                                    Date endDate,
                                
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

        PartyPostExample example = new PartyPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (startDate != null){
            criteria.andStartDateGreaterThan(startDate);
        }
        if (endDate != null){
            criteria.andEndDateGreaterThan(endDate);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyPost_export(example, response);
            return;
        }

        long count = partyPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyPost> records= partyPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyPost.class, partyPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping(value = "/party/partyPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPost_au(PartyPost record,
                               Integer userId,
                               String startDate,
                               String endDate,
                               HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(startDate)){
            record.setStartDate(DateUtils.parseDate(startDate,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(endDate)){
            record.setEndDate(DateUtils.parseDate(endDate,DateUtils.YYYYMMDD_DOT));
        }
        record.setUserId(userId);
        if (id == null) {
            
            partyPostService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "添加党内任职经历：{0}", record.getId()));
        } else {

            partyPostService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党内任职经历：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping("/party/partyPost_au")
    public String partyPost_au(Integer id,
                               Integer userId,
                               ModelMap modelMap) {

        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        if (id != null) {
            PartyPost partyPost = partyPostMapper.selectByPrimaryKey(id);
            modelMap.put("partyPost", partyPost);
        }
        return "party/partyPost/partyPost_au";
    }


    @RequestMapping(value = "/party/partyPost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPost_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyPostService.del(id);
            logger.info(log( LogConstants.LOG_PARTY, "删除党内任职经历：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping(value = "/party/partyPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyPost_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyPostService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PARTY, "批量删除党内任职经历：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void partyPost_export(PartyPostExample example, HttpServletResponse response) {

        List<PartyPost> records = partyPostMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属党员|100","工作单位及担任职务|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyPost record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getDetail(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党内任职经历(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/partyPost_selects")
    @ResponseBody
    public Map partyPost_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyPostExample example = new PartyPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = partyPostMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyPost> records = partyPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PartyPost record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getUserId());
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
