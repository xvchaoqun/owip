package controller.member;

import domain.member.ApplySn;
import domain.member.ApplySnExample;
import domain.member.ApplySnExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang.StringUtils;
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

public class ApplySnController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySnRange:reuse")
    @RequestMapping(value = "/applySn_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySn_reuse(int id, HttpServletRequest request) {

        applySnService.reuse(id);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySnRange:change")
    @RequestMapping(value = "/applySn_change", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySn_change(int id, int newSnId, HttpServletRequest request) {

        ApplySn applySn = applySnMapper.selectByPrimaryKey(id);
        ApplySn newApplySn = applySnMapper.selectByPrimaryKey(newSnId);
        applySnService.change(applySn, newApplySn);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySnRange:change")
    @RequestMapping("/applySn_change")
    public String applySn_change(int id, ModelMap modelMap) {

        ApplySn applySn = applySnMapper.selectByPrimaryKey(id);
        modelMap.put("applySn", applySn);
        List<ApplySn> assignApplySnList = applySnService.getAssignApplySnList(1);

        if(assignApplySnList.size()==1){
           modelMap.put("newApplySn", assignApplySnList.get(0));
        }

        return "member/applySn/applySn_change";
    }

    @RequiresPermissions("applySnRange:list")
    @RequestMapping("/applySn")
    public String applySn(@RequestParam(defaultValue = "1") int cls,
                          Integer userId,
                          ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("cls", cls);
        return "member/applySn/applySn_page";
    }

    @RequiresPermissions("applySnRange:list")
    @RequestMapping("/applySn_data")
    @ResponseBody
    public void applySn_data(HttpServletResponse response,
                             Integer year,
                             String displaySn,
                             Boolean isUsed,
                             Integer userId,
                             Boolean isAbolished,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySnExample example = new ApplySnExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, is_used asc, sn asc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }

        if (StringUtils.isNotBlank(displaySn)) {
            criteria.andDisplaySnLike("%" + displaySn + "%");
        }

        if (isUsed != null) {
            criteria.andIsUsedEqualTo(isUsed);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (isAbolished != null) {
            criteria.andIsAbolishedEqualTo(isAbolished);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            applySn_export(example, response);
            return;
        }

        long count = applySnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySn> records = applySnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(applySn.class, applySnMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void applySn_export(ApplySnExample example, HttpServletResponse response) {

        List<ApplySn> records = applySnMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属号段|100", "是否已使用|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApplySn record = records.get(i);
            String[] values = {
                    record.getRangeId() + "",
                    record.getIsUsed() + ""
            };
            valuesList.add(values);
        }
        String fileName = "入党志愿书编码_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/applySn_selects")
    @ResponseBody
    public Map applySn_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);

        ApplySnExample example = new ApplySnExample();
        Criteria criteria = example.createCriteria()
                .andYearEqualTo(DateUtils.getCurrentYear())
                .andIsUsedEqualTo(false)
                .andIsAbolishedEqualTo(false);
        if(searchStr!=null){
            criteria.andDisplaySnLike("%" + searchStr + "%");
        }
        int count = (int) applySnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySn> records = applySnMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != records && records.size() > 0) {

            for (ApplySn record : records) {
                Map<String, String> option = new HashMap<>();
                option.put("id", record.getId() + "");
                option.put("text", record.getDisplaySn());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
