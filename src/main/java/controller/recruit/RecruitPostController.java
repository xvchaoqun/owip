package controller.recruit;

import controller.BaseController;
import domain.recruit.RecruitPost;
import domain.recruit.RecruitPostExample;
import domain.recruit.RecruitPostExample.Criteria;
import domain.unit.Unit;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import java.util.*;

@Controller
public class RecruitPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("recruitPost:list")
    @RequestMapping("/recruitPost")
    public String recruitPost() {

        return "index";
    }
    @RequiresPermissions("recruitPost:list")
    @RequestMapping("/recruitPost_page")
    public String recruitPost_page(@RequestParam(required = false, defaultValue = "1")Byte status,
                                   ModelMap modelMap) {

        modelMap.put("status", status);
        return "recruit/recruitPost/recruitPost_page";
    }

    @RequiresPermissions("recruitPost:list")
    @RequestMapping("/recruitPost_data")
    public void recruitPost_data(HttpServletResponse response,
                                    Integer year,
                                    String name,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
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

        RecruitPostExample example = new RecruitPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        criteria.andStatusEqualTo(status);

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            recruitPost_export(example, response);
            return;
        }

        int count = recruitPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<RecruitPost> records= recruitPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(recruitPost.class, recruitPostMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("recruitPost:edit")
    @RequestMapping(value = "/recruitPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_recruitPost_au(RecruitPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setSignStatus(SystemConstants.RECRUIT_POST_SIGN_STATUS_INIT);
            record.setMeetingStatus(false);
            record.setCommitteeStatus(false);
            record.setCreateTime(new Date());
            record.setIsPublish(false);
            record.setStatus(SystemConstants.RECRUIT_POST_STATUS_NORMAL);
            recruitPostService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加岗位：%s", record.getId()));
        } else {

            recruitPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新岗位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("recruitPost:edit")
    @RequestMapping("/recruitPost_au")
    public String recruitPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            RecruitPost recruitPost = recruitPostMapper.selectByPrimaryKey(id);
            modelMap.put("recruitPost", recruitPost);
            Unit unit = unitService.findAll().get(recruitPost.getUnitId());
            modelMap.put("unit", unit);
        }
        return "recruit/recruitPost/recruitPost_au";
    }


    @RequiresPermissions("recruitPost:del")
    @RequestMapping(value = "/recruitPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            recruitPostService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除岗位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void recruitPost_export(RecruitPostExample example, HttpServletResponse response) {

        List<RecruitPost> records = recruitPostMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度","招聘岗位","行政级别","所属单位","基本条件","任职资格","报名情况","招聘会情况","常委会情况","备注","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            RecruitPost record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getName(),
                            record.getAdminLevel()+"",
                            record.getUnitId()+"",
                            record.getRequirement(),
                            record.getQualification(),
                            record.getSignStatus() +"",
                            record.getMeetingStatus() +"",
                            record.getCommitteeStatus() +"",
                            record.getRemark(),
                            record.getStatus() +""
            };
            valuesList.add(values);
        }
        String fileName = "岗位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
