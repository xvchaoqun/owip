package controller.member;

import domain.member.ApplySn;
import domain.member.ApplySnExample;
import domain.member.ApplySnExample.Criteria;
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

public class ApplySnController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySn:list")
    @RequestMapping("/applySn")
    public String applySn() {

        return "member/applySn/applySn_page";
    }

    @RequiresPermissions("applySn:list")
    @RequestMapping("/applySn_data")
    @ResponseBody
    public void applySn_data(HttpServletResponse response,
                                    Integer rangeId,
                                    Boolean isUsed,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Long[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySnExample example = new ApplySnExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_used asc, sn asc");

        if (rangeId!=null) {
            criteria.andRangeIdEqualTo(rangeId);
        }
        if (isUsed!=null) {
            criteria.andIsUsedEqualTo(isUsed);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andSnIn(Arrays.asList(ids));
            applySn_export(example, response);
            return;
        }

        long count = applySnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySn> records= applySnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("applySn:edit")
    @RequestMapping(value = "/applySn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySn_au(ApplySn record, HttpServletRequest request) {

        Long sn = record.getSn();

        if (sn == null) {
            
            applySnService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加入党志愿书编码：{0}", record.getSn()));
        } else {

            applySnService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新入党志愿书编码：{0}", record.getSn()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySn:edit")
    @RequestMapping("/applySn_au")
    public String applySn_au(Long sn, ModelMap modelMap) {

        if (sn != null) {
            ApplySn applySn = applySnMapper.selectByPrimaryKey(sn);
            modelMap.put("applySn", applySn);
        }
        return "member/applySn/applySn_au";
    }

    @RequiresPermissions("applySn:del")
    @RequestMapping(value = "/applySn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map applySn_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Long[] sns, ModelMap modelMap) {


        if (null != sns && sns.length>0){
            applySnService.batchDel(sns);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除入党志愿书编码：{0}", StringUtils.join(sns, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void applySn_export(ApplySnExample example, HttpServletResponse response) {

        List<ApplySn> records = applySnMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属号段|100","是否已使用|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApplySn record = records.get(i);
            String[] values = {
                record.getRangeId()+"",
                            record.getIsUsed()+""
            };
            valuesList.add(values);
        }
        String fileName = "入党志愿书编码_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
