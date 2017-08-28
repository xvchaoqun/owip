package controller.pcs;

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
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                                 Integer configId,
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
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (configId != null) {
            criteria.andConfigIdEqualTo(configId);
        }

        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsProposal_export(example, response);
            return;
        }*/

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

        if (pcsProposalService.idDuplicate(id, record.getUserId(), record.getType(), record.getConfigId())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setAddTime(new Date());
            pcsProposalService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加建议人选：%s", record.getId()));
        } else {

            pcsProposalService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新建议人选：%s", record.getId()));
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
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除建议人选：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposal:del")
    @RequestMapping(value = "/pcsProposal_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsProposalService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除建议人选：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
