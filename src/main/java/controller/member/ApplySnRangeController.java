package controller.member;

import domain.member.ApplySnRange;
import domain.member.ApplySnRangeExample;
import domain.member.ApplySnRangeExample.Criteria;
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

public class ApplySnRangeController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySnRange:list")
    @RequestMapping("/applySnRange")
    public String applySnRange(@RequestParam(defaultValue = "1") int cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "member/applySnRange/applySnRange_page";
    }

    @RequiresPermissions("applySnRange:list")
    @RequestMapping("/applySnRange_data")
    @ResponseBody
    public void applySnRange_data(HttpServletResponse response,
                                    Integer year,
                                     Long startSn,
                                     Long endSn,
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

        ApplySnRangeExample example = new ApplySnRangeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, sort_order desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (startSn!=null) {
            criteria.andStartSnGreaterThanOrEqualTo(startSn);
        }
        if (endSn!=null) {
            criteria.andEndSnLessThanOrEqualTo(endSn);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            applySnRange_export(example, response);
            return;
        }

        long count = applySnRangeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySnRange> records= applySnRangeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(applySnRange.class, applySnRangeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("applySnRange:edit")
    @RequestMapping(value = "/applySnRange_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySnRange_au(ApplySnRange record, HttpServletRequest request) {

        Integer id = record.getId();
        long startSn = record.getStartSn();
        long endSn = record.getEndSn();
        int year = record.getYear();

        if(startSn > endSn) return failed("起止号码有误。");

        if (id == null) {

            List<ApplySnRange> overlapApplySnRanges = iMemberMapper.getOverlapApplySnRanges(year, startSn, endSn);
            if(overlapApplySnRanges.size()>0){
                return failed("存在重叠的号码段。");
            }

            applySnRangeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加入党志愿书编码段：{0}", record.getId()));
        } else {

            List<ApplySnRange> overlapApplySnRanges = iMemberMapper.getOverlapApplySnRanges(year, startSn, endSn);
            for (ApplySnRange overlapApplySnRange : overlapApplySnRanges) {
                if(overlapApplySnRange.getId().intValue()!=id){
                    return failed("存在重叠的号码段。");
                }
            }

            applySnRangeService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新入党志愿书编码段：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySnRange:edit")
    @RequestMapping("/applySnRange_au")
    public String applySnRange_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySnRange applySnRange = applySnRangeMapper.selectByPrimaryKey(id);
            modelMap.put("applySnRange", applySnRange);
        }
        return "member/applySnRange/applySnRange_au";
    }

    @RequiresPermissions("applySnRange:del")
    @RequestMapping(value = "/applySnRange_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map applySnRange_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            applySnRangeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除入党志愿书编码段：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }



    @RequiresPermissions("applySnRange:changeOrder")
    @RequestMapping(value = "/applySnRange_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySnRange_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        applySnRangeService.changeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_MEMBER, "志愿书编码段调序：{0},{1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void applySnRange_export(ApplySnRangeExample example, HttpServletResponse response) {

        List<ApplySnRange> records = applySnRangeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属年度|100","起始编码|100","结束编码|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApplySnRange record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getStartSn() + "",
                            record.getEndSn() + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "入党志愿书编码段_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
