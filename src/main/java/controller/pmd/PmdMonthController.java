package controller.pmd;

import controller.global.OpException;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdMonthExample;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.helper.PmdHelper;
import sys.tool.fancytree.TreeNode;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pmd")
public class PmdMonthController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdMonth:list")
    @RequestMapping("/pmdMonth")
    public String pmdMonth(ModelMap modelMap) {

        //PmdMonth realThisMonth = pmdMonthService.getMonth(new Date());
        //modelMap.put("realThisMonth", realThisMonth);

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andStatusEqualTo(PmdConstants.PMD_MONTH_STATUS_INIT);
        List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        if(pmdMonths.size()>0){
            // 如果存在新建的缴费月份ID，可能需要读取进度
            modelMap.put("initMonthId", pmdMonths.get(0).getId());
            modelMap.put("processMemberCount", PmdHelper.processMemberCount);
        }

        return "pmd/pmdMonth/pmdMonth_page";
    }

    @RequiresPermissions("pmdMonth:list")
    @RequestMapping("/pmdMonth_data")
    public void pmdMonth_data(HttpServletResponse response,
                               @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdMonthExample example = new PmdMonthExample();
        PmdMonthExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (payMonth != null) {
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }

        long count = pmdMonthMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdMonth> records = pmdMonthMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMonth.class, pmdMonthMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    /*@RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_create", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_create(HttpServletRequest request) {

        pmdMonthService.create();

        logger.info(addLog(LogConstants.LOG_PMD, "新建缴费月份"));
        return success(FormUtils.SUCCESS);
    }*/

    // 更新未启动的月份
    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_au")
    public String pmdMonth_au(Integer id, ModelMap modelMap) {

        if(id!=null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(id);
            modelMap.put("pmdMonth", pmdMonth);
        }

        return "pmd/pmdMonth/pmdMonth_au";
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_au(Integer id, @DateTimeFormat(pattern = "yyyy-MM") Date month, HttpServletRequest request) {

        pmdMonthService.addOrUpdate(id, month);

        logger.info(addLog(LogConstants.LOG_PMD, "修改缴费月份"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_selectParties", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_selectParties(int monthId, Integer[] partyIds,
                                          HttpServletRequest request) {

        pmdMonthService.updatePartyIds(monthId, partyIds);

        logger.info(addLog(LogConstants.LOG_PMD, "设置/更新缴费月份的分党委"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_addParty")
    public String pmdMonth_addParty(ModelMap modelMap) {
    
        List<Integer> hasSelectedPartyIds = new ArrayList<>();
        {
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            PmdPartyExample example = new PmdPartyExample();
            example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId());
            List<PmdParty> pmdParties = pmdPartyMapper.selectByExample(example);
            for (PmdParty pmdParty : pmdParties) {
                hasSelectedPartyIds.add(pmdParty.getPartyId());
            }
        }
    
        /*{
            // 全部已设定的
            Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet(null);
        
        }*/

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        if(hasSelectedPartyIds.size()>0) {
            criteria.andIdNotIn(hasSelectedPartyIds);
        }
        example.setOrderByClause(" sort_order desc");
        List<Party> partyList = partyMapper.selectByExample(example);
        modelMap.put("partyList", partyList);

        return "pmd/pmdMonth/pmdMonth_addParty";
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_addParty", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_addParty(int partyId, HttpServletRequest request) {

        pmdMonthService.addParty(partyId);

        logger.info(addLog(LogConstants.LOG_PMD, "新增缴费党委， %s", partyId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_selectParties")
    public String pmdMonth_selectParties(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);
        }
        return "pmd/pmdMonth/pmdMonth_selectParties";
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_selectParties_tree")
    @ResponseBody
    public TreeNode pmdMonth_selectParties_tree(int monthId) throws IOException {

        TreeNode tree = pmdMonthService.getPartyTree(monthId);

        return tree;
        /*Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;*/
    }

    // 启动
    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping(value = "/pmdMonth_start", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_start(int monthId, HttpServletRequest request) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            throw new OpException("存在未结算月份，不可以启动新的缴费月份。");
        }
        Set<Integer> partyIdSet = pmdMonthService.getMonthPartyIdSet(monthId);
        if (partyIdSet.size() == 0) {
            throw new OpException("请先设置缴费分党委。");
        }

        PmdHelper.processMemberCount = 0;
        pmdService.asyncStart(monthId, partyIdSet);

        logger.info(addLog(LogConstants.LOG_PMD, "启动缴费， %s", monthId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_start")
    public String pmdMonth_start(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);

            Set<Integer> partyIdSet = pmdMonthService.getMonthPartyIdSet(monthId);
            modelMap.put("partyIdSet", partyIdSet);
        }
        return "pmd/pmdMonth/pmdMonth_start";
    }

    @RequiresPermissions("pmdMonth:edit")
    @RequestMapping("/pmdMonth_start_status")
    @ResponseBody
    public Map pmdMonth_start_status(Integer monthId, ModelMap modelMap) {

        int totalMemberCount = iPmdMapper.getTotalMemberCount(monthId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalMemberCount", totalMemberCount);
        resultMap.put("processMemberCount", PmdHelper.processMemberCount);

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        boolean isStarted = pmdMonth.getStatus() == PmdConstants.PMD_MONTH_STATUS_START;
        resultMap.put("isStarted", isStarted);
        if(isStarted){
            PmdHelper.processMemberCount = -1;
        }
        return resultMap;
    }

    // 结算
    @RequiresPermissions("pmdMonth:end")
    @RequestMapping(value = "/pmdMonth_end", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_end(int monthId, Boolean update, HttpServletRequest request) {

        if(BooleanUtils.isTrue(update)){
            
            ShiroHelper.checkPermission(SystemConstants.PERMISSION_PMDVIEWALL);
            pmdMonthService.updateEnd(monthId, true);
            logger.info(addLog(LogConstants.LOG_PMD, "更新结算缴费， %s", monthId));
            
        }else {
            pmdMonthService.end(monthId);
            logger.info(addLog(LogConstants.LOG_PMD, "结算缴费， %s", monthId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:end")
    @RequestMapping("/pmdMonth_end")
    public String pmdMonth_end(Integer monthId, ModelMap modelMap) {

        if (monthId != null) {
            PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
            modelMap.put("pmdMonth", pmdMonth);
        }

        return "pmd/pmdMonth/pmdMonth_end";
    }

    /*@RequiresPermissions("pmdMonth:del")
    @RequestMapping(value = "/pmdMonth_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMonth_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdMonthService.del(id);
            logger.info(addLog(LogConstants.LOG_PMD, "删除每月党费缴纳配置：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMonth:del")
    @RequestMapping(value = "/pmdMonth_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdMonthService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PMD, "批量删除每月党费缴纳配置：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
