package controller.cg;

import domain.cg.CgLeader;
import domain.cg.CgLeaderExample;
import domain.cg.CgLeaderExample.Criteria;
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
public class CgLeaderController extends CgBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgLeader:list")
    @RequestMapping("/cgLeader")
    public String cgLeader() {

        return "cg/cgLeader/cgLeader_page";
    }

    @RequiresPermissions("cgLeader:list")
    @RequestMapping("/cgLeader_data")
    @ResponseBody
    public void cgLeader_data(HttpServletResponse response,
                                    Integer teamId,
                                    Integer relatePost,
                                    Integer unitPostId,
                                    Integer userId,
                                    String phone,
                                    Date confirmDate,
                                
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

        CgLeaderExample example = new CgLeaderExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (teamId!=null) {
            criteria.andTeamIdEqualTo(teamId);
        }
        if (relatePost!=null) {
            criteria.andRelatePostEqualTo(relatePost);
        }
        if (unitPostId!=null) {
            criteria.andUnitPostIdEqualTo(unitPostId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andPhoneLike(SqlUtils.like(phone));
        }
        if (confirmDate!=null) {
        criteria.andConfirmDateGreaterThan(confirmDate);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgLeader_export(example, response);
            return;
        }

        long count = cgLeaderMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgLeader> records= cgLeaderMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgLeader.class, cgLeaderMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgLeader:edit")
    @RequestMapping(value = "/cgLeader_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgLeader_au(CgLeader record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));
        if (cgLeaderService.idDuplicate(id, record.getTeamId(), record.getIsCurrent())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            cgLeaderService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加办公室主任：{0}", record.getId()));
        } else {

            cgLeaderService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新办公室主任：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgLeader:edit")
    @RequestMapping("/cgLeader_au")
    public String cgLeader_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CgLeader cgLeader = cgLeaderMapper.selectByPrimaryKey(id);
            modelMap.put("cgLeader", cgLeader);
        }
        return "cg/cgLeader/cgLeader_au";
    }

    @RequiresPermissions("cgLeader:del")
    @RequestMapping(value = "/cgLeader_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgLeader_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgLeaderService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除办公室主任：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgLeader:del")
    @RequestMapping(value = "/cgLeader_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgLeader_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cgLeaderService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除办公室主任：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void cgLeader_export(CgLeaderExample example, HttpServletResponse response) {

        List<CgLeader> records = cgLeaderMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属委员会或领导小组|100","是否席位制|100","关联岗位|100","用户ID|100","联系方式|100","确定时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgLeader record = records.get(i);
            String[] values = {
                record.getTeamId()+"",
                            record.getRelatePost()+"",
                            record.getUnitPostId()+"",
                            record.getUserId()+"",
                            record.getPhone(),
                            DateUtils.formatDate(record.getConfirmDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("办公室主任(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/cgLeader_selects")
    @ResponseBody
    public Map cgLeader_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgLeaderExample example = new CgLeaderExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = cgLeaderMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CgLeader> records = cgLeaderMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CgLeader record:records){

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
