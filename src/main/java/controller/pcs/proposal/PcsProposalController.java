package controller.pcs.proposal;

import controller.BaseController;
import domain.pcs.PcsProposal;
import domain.pcs.PcsProposalExample;
import domain.pcs.PcsProposalExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
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
public class PcsProposalController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsProposal:list")
    @RequestMapping("/pcsProposal")
    public String pcsProposal() {

        return "pcs/pcsProposal/pcsProposal_page";
    }

    @RequiresPermissions("pcsProposal:list")
    @RequestMapping("/pcsProposal_data")
    public void pcsProposal_data(HttpServletResponse response,
                                 String code,
                                 Integer userId,
                                 String name,
                                 String keywords,
                                 Integer type,
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

        PcsProposalExample example = new PcsProposalExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(keywords)) {
            criteria.andKeywordsLike("%" + keywords + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsProposal_export(example, response);
            return;
        }

        long count = pcsProposalMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsProposal> records = pcsProposalMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsProposal.class, pcsProposalMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsProposal:edit")
    @RequestMapping(value = "/pcsProposal_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_au(PcsProposal record, HttpServletRequest request) {

        Integer id = record.getId();

        if (pcsProposalService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            pcsProposalService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加提案：%s", record.getId()));
        } else {

            pcsProposalService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新提案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposal:edit")
    @RequestMapping("/pcsProposal_au")
    public String pcsProposal_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(id);
            modelMap.put("pcsProposal", pcsProposal);
        }
        return "pcs/pcsProposal/pcsProposal_au";
    }

    @RequiresPermissions("pcsProposal:del")
    @RequestMapping(value = "/pcsProposal_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pcsProposalService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除提案：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposal:del")
    @RequestMapping(value = "/pcsProposal_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsProposalService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除提案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void pcsProposal_export(PcsProposalExample example, HttpServletResponse response) {

        List<PcsProposal> records = pcsProposalMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"提案编号|100", "用户|100", "标题|100", "关键字|100", "提案类型|100", "创建时间|100", "状态|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsProposal record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getUserId() + "",
                    record.getName(),
                    record.getKeywords(),
                    record.getType() + "",
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = "提案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pcsProposal_selects")
    @ResponseBody
    public Map pcsProposal_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsProposalExample example = new PcsProposalExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        long count = pcsProposalMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsProposal> pcsProposals = pcsProposalMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != pcsProposals && pcsProposals.size() > 0) {

            for (PcsProposal pcsProposal : pcsProposals) {

                Select2Option option = new Select2Option();
                option.setText(pcsProposal.getName());
                option.setId(pcsProposal.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
