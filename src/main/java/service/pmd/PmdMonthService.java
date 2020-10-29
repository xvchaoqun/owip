package service.pmd;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberExample;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pmd.*;
import ext.service.PmdExtService;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.pmd.common.PmdReportBean;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.PmdConstants;
import sys.helper.PmdHelper;
import sys.tool.fancytree.TreeNode;
import sys.utils.DateUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PmdMonthService extends PmdBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PmdExtService pmdExtService;
    @Autowired
    protected PmdService pmdService;
    @Autowired
    protected PmdPayPartyService pmdPayPartyService;
    @Autowired
    protected PmdPayBranchService pmdPayBranchService;
    @Autowired
    protected PmdMemberService pmdMemberService;
    @Autowired
    protected PmdBranchService pmdBranchService;
    @Autowired
    protected PmdPartyService pmdPartyService;

    // 结算
    @Transactional
    public void end(int monthId) {

        if (!canEnd(monthId)) {
            throw new OpException("当前不允许结算。");
        }

        PmdMonth record = new PmdMonth();
        record.setStatus(PmdConstants.PMD_MONTH_STATUS_END);
        record.setEndUserId(ShiroHelper.getCurrentUserId());
        record.setEndTime(new Date());

        // 保存数据汇总
        PmdReportBean r = iPmdMapper.getOwPmdReportBean(monthId);
        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException e) {
            logger.error("异常", e);
        } catch (InvocationTargetException e) {
            logger.error("异常", e);
        } catch (NoSuchMethodException e) {
            logger.error("异常", e);
        }

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andIdEqualTo(monthId)
                .andStatusEqualTo(PmdConstants.PMD_MONTH_STATUS_START);
        pmdMonthMapper.updateByExampleSelective(record, example);
    }

    // 更新结算数据（当结算有误时调用）
    @Transactional
    public void updateEnd(int monthId, boolean updateEndTime) {

        PmdMonth record = new PmdMonth();
        record.setId(monthId);
        record.setEndUserId(ShiroHelper.getCurrentUserId());
        if(updateEndTime) {
            record.setEndTime(new Date());
        }

        // 保存数据汇总
        PmdReportBean r = iPmdMapper.getOwPmdReportBean(monthId);
        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException e) {
            logger.error("异常", e);
        } catch (InvocationTargetException e) {
            logger.error("异常", e);
        } catch (NoSuchMethodException e) {
            logger.error("异常", e);
        }

        pmdMonthMapper.updateByPrimaryKeySelective(record);
    }

    // 判断是否可以结算
    public boolean canEnd(int monthId) {

        PmdMonth currentPmdMonth = getCurrentPmdMonth();
        if (currentPmdMonth == null || currentPmdMonth.getId() != monthId) return false;

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        if (pmdMonth == null) return false;

        // 如果存在 未报送的分党委， 则不可报送
        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andHasReportEqualTo(false);

        return pmdPartyMapper.countByExample(example) == 0;
    }

    // 读取缴费月份记录
    public PmdMonth getMonth(Date payMonth) {

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExample(example);

        return pmdMonths.size() > 0 ? pmdMonths.get(0) : null;
    }

    // 得到当前缴费月份记录
    public PmdMonth getCurrentPmdMonth() {

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andStatusEqualTo(PmdConstants.PMD_MONTH_STATUS_START);
        List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExample(example);

        if (pmdMonths.size() > 1) throw new OpException("缴费操作错误，请稍后再试。");

        return pmdMonths.size() > 0 ? pmdMonths.get(0) : null;
    }

    // 异步启动缴费
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(int monthId, Set<Integer> partyIdSet) {

        PmdMonth currentMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        String payMonth = DateUtils.formatDate(currentMonth.getPayMonth(), "yyyy年MM月");
        logger.info("{}党员缴费-启动", payMonth);

        long start = System.currentTimeMillis();
        for (Integer partyId : partyIdSet) {

            Party party = partyService.findAll().get(partyId);
            logger.info("{}党员缴费-同步数据-{}", payMonth, party.getName());

            PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
            if (pmdParty == null)
                throw new OpException("数据错误，党委不存在。[{0}-{1}]", monthId, partyId);
            // 直属党支部特殊处理
            if (partyService.isDirectBranch(partyId)) {
                // 同步党员（直属党支部）
                MemberExample example = new MemberExample();
                example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andPartyIdEqualTo(partyId).andBranchIdIsNull();
                List<Member> members = memberMapper.selectByExample(example);
                for (Member member : members) {
                    pmdExtService.addOrResetMember(null, currentMonth, member,false);
                }
                //Integer memberCount = members.size();

                // 更新当月直属党支部信息
                //PmdParty record = new PmdParty();
                //record.setId(pmdParty.getId());
                // 党员总数
                //record.setMemberCount(memberCount);
                // 本月应交党费数
                //record.setDuePay(iPmdMapper.duePay(monthId, partyId, null));

                //pmdPartyMapper.updateByPrimaryKeySelective(record);

            } else {

                List<Integer> branchIdList = iPmdMapper.branchIdList(monthId, partyId);
                for (Integer branchId : branchIdList) {

                   addBranch(partyId, branchId, currentMonth);
                }
            }

            {
                PmdParty record = new PmdParty();
                record.setId(pmdParty.getId());
                // 往月延迟缴费党员数
                record.setHistoryDelayMemberCount(iPmdMapper.historyDelayMemberCount(monthId, partyId, null));
                // 应补缴往月党费数
                BigDecimal historyDelayPay = iPmdMapper.historyDelayPay(monthId, partyId, null);
                historyDelayPay = (historyDelayPay == null) ? new BigDecimal(0) : historyDelayPay;
                record.setHistoryDelayPay(historyDelayPay);

                pmdPartyMapper.updateByPrimaryKeySelective(record);
            }
        }

        PmdMonth record = new PmdMonth();
        record.setId(monthId);
        record.setStartTime(new Date());
        record.setStartUserId(ShiroHelper.getCurrentUserId());

        // 往月延迟缴费党员数
        record.setHistoryDelayMemberCount(iPmdMapper.historyDelayMemberCount(monthId, null, null));
        // 应补缴往月党费数
        BigDecimal historyDelayPay = iPmdMapper.historyDelayPay(monthId, null, null);
        historyDelayPay = (historyDelayPay == null) ? new BigDecimal(0) : historyDelayPay;
        record.setHistoryDelayPay(historyDelayPay);

        record.setStatus(PmdConstants.PMD_MONTH_STATUS_START);
        pmdMonthMapper.updateByPrimaryKeySelective(record);

        // 同步一次党建管理员
        pmdService.syncAdmin();

        long end = System.currentTimeMillis();
        logger.info("{}党员缴费-启动成功，耗时{}ms", payMonth, (end - start));
    }

    // 添加一个支部（非直属）
    @Transactional
    public void addBranch(int partyId, int branchId, PmdMonth currentMonth){

        int monthId = currentMonth.getId();

        // 同步党员
        MemberExample example = new MemberExample();
        example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                .andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
        List<Member> members = memberMapper.selectByExample(example);
        for (Member member : members) {

            PmdMember pmdMember = pmdMemberService.get(monthId, member.getUserId());
            if(pmdMember==null) { // 只处理当月未有缴费记录的党员
                pmdExtService.addOrResetMember(null, currentMonth, member, false);
            }
        }

        {
            // 更新当月党支部信息（党员总数、本月应交党费数）
            PmdBranch pmdBranch = pmdBranchService.get(monthId, partyId, branchId);
            if (pmdBranch == null)
                throw new OpException("数据错误，党支部不存在。[{0}-{1}-{2}]",
                        monthId, partyId, branchId);

            PmdBranch record = new PmdBranch();
            record.setId(pmdBranch.getId());
            // 党员总数
            //record.setMemberCount(members.size());
            // 本月应交党费数
            //record.setDuePay(iPmdMapper.duePay(monthId, partyId, branchId));

            // 往月延迟缴费党员数
            record.setHistoryDelayMemberCount(iPmdMapper.historyDelayMemberCount(monthId, partyId, branchId));
            // 应补缴往月党费数
            BigDecimal historyDelayPay = iPmdMapper.historyDelayPay(monthId, partyId, branchId);
            historyDelayPay = (historyDelayPay == null) ? new BigDecimal(0) : historyDelayPay;
            record.setHistoryDelayPay(historyDelayPay);

            pmdBranchMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 得到某月的分党委ID列表
    public Set<Integer> getMonthPartyIdSet(int monthId) {

        Set<Integer> partyIdSet = new LinkedHashSet<>();
        partyIdSet.addAll(iPmdMapper.partyIdList(monthId));

        return partyIdSet;
    }

    // 给当前缴费月份新增缴费党委
    @Transactional
    public void addParty(int partyId){

        PmdMonth currentPmdMonth = getCurrentPmdMonth();
        if(currentPmdMonth==null) return;
        int monthId = currentPmdMonth.getId();
        {
            PmdPayPartyExample example = new PmdPayPartyExample();
            example.createCriteria().andPartyIdEqualTo(partyId);
            if(pmdPayPartyMapper.countByExample(example)==0) {
                PmdPayParty record = new PmdPayParty();
                record.setPartyId(partyId);
                record.setMonthId(monthId);
                pmdPayPartyMapper.insertSelective(record);
            }
        }

        Party party = partyMapper.selectByPrimaryKey(partyId);
        String partyName = party.getName();
        boolean directBranch = partyService.isDirectBranch(partyId);

        List<Branch> branchList = null;
        if(!directBranch){
            // 同步支部信息
            BranchExample example = new BranchExample();
            example.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
            branchList = branchMapper.selectByExample(example);
            for (Branch branch : branchList) {

                int branchId = branch.getId();
                PmdBranch record = new PmdBranch();
                record.setMonthId(monthId);
                record.setPartyId(partyId);
                record.setBranchId(branchId);
                record.setPartyName(partyName);
                record.setBranchName(branch.getName());
                record.setPartySortOrder(party.getSortOrder());
                record.setSortOrder(branch.getSortOrder());
                record.setHasReport(false);

                pmdBranchMapper.insertSelective(record);

                PmdPayBranchExample example2 = new PmdPayBranchExample();
                example2.createCriteria().andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
                if(pmdPayBranchMapper.countByExample(example2)==0) {
                    PmdPayBranch _record = new PmdPayBranch();
                    _record.setBranchId(branchId);
                    _record.setPartyId(partyId);
                    _record.setMonthId(monthId);
                    pmdPayBranchMapper.insertSelective(_record);
                }
            }
        }

        {
            // 同步分党委信息
            PmdParty record = new PmdParty();
            record.setMonthId(monthId);
            record.setPartyId(partyId);
            record.setIsDirectBranch(false);
            record.setPartyName(partyName);
            record.setSortOrder(party.getSortOrder());
            record.setHasReport(false);
            // 党支部数
            //record.setBranchCount(branchList == null ? 0 : branchList.size());

            // 直属党支部特殊处理
            if (directBranch) {
                record.setIsDirectBranch(true);
                record.setBranchCount(1);
            }

            pmdPartyMapper.insertSelective(record);
        }

        // 直属党支部特殊处理
        if (directBranch) {
            // 同步党员（直属党支部）
            MemberExample example = new MemberExample();
            example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                    .andPartyIdEqualTo(partyId).andBranchIdIsNull();
            List<Member> members = memberMapper.selectByExample(example);
            for (Member member : members) {

                PmdMember pmdMember = pmdMemberService.get(monthId, member.getUserId());
                if(pmdMember==null) { // 只处理当月未有缴费记录的党员
                    pmdExtService.addOrResetMember(null, currentPmdMonth, member, false);
                }
            }

        } else {

            List<Integer> branchIdList = iPmdMapper.branchIdList(monthId, partyId);
            for (Integer branchId : branchIdList) {

                addBranch(partyId, branchId, currentPmdMonth);
            }
        }

        PmdMonth record = new PmdMonth();
        record.setId(monthId);
        record.setPartyCount(currentPmdMonth.getPartyCount()+1);
        pmdMonthMapper.updateByPrimaryKeySelective(record);
    }

    // 设置缴费分党委（只可编辑未启动缴费）
    @Transactional
    public void updatePartyIds(int monthId, Integer[] partyIds) {

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        if (pmdMonth.getStatus() == PmdConstants.PMD_MONTH_STATUS_END) {
            throw new OpException("已结算月份不允许编辑缴费分党委。");
        } else if (pmdMonth.getStatus() == PmdConstants.PMD_MONTH_STATUS_START) {
            throw new OpException("已经启动缴费，不允许重新设置缴费分党委。");
        }

        Set<Integer> selectedPartyIdSet = new HashSet<>();
        {
            // 删除从本月开始参与缴费的分党委
            PmdPayPartyExample example = new PmdPayPartyExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdPayPartyMapper.deleteByExample(example);

            selectedPartyIdSet.addAll(Arrays.asList(partyIds));

            Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet(true);
            Set<Integer> allPartyIdSet = allPayPartyIdSet.keySet();

            // 添加本月新加入的参与缴费的分党委
            selectedPartyIdSet.removeAll(allPartyIdSet);
            for (Integer newSelectPartyId : selectedPartyIdSet) {

                PmdPayParty record = new PmdPayParty();
                record.setPartyId(newSelectPartyId);
                record.setMonthId(monthId);
                pmdPayPartyMapper.insertSelective(record);
            }

            // 本月参与缴费的全部分党委（往月参与缴费的分党委，必须参与缴费？）
            selectedPartyIdSet.addAll(allPartyIdSet);
        }

        {
            // 删除本月设置的分党委
            PmdPartyExample example = new PmdPartyExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdPartyMapper.deleteByExample(example);
        }
        {
            // 删除本月设置的党支部
            PmdBranchExample example = new PmdBranchExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdBranchMapper.deleteByExample(example);
        }

        {
            // 设置缴费分党委数量
            PmdMonth record = new PmdMonth();
            record.setId(monthId);
            record.setPartyCount(selectedPartyIdSet.size());
            pmdMonthMapper.updateByPrimaryKeySelective(record);
        }

        {
            // 删除从本月新设置参与缴费的党支部
            PmdPayBranchExample example = new PmdPayBranchExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdPayBranchMapper.deleteByExample(example);
        }

        for (int partyId : selectedPartyIdSet) {

            Set<Integer> allPayBranchIdSet = pmdPayBranchService.getAllPayBranchIdSet(partyId).keySet();
            Party party = partyMapper.selectByPrimaryKey(partyId);
            String partyName = party.getName();
            List<Branch> branchList = null;
            {
                // 同步支部信息
                BranchExample example = new BranchExample();
                example.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
                branchList = branchMapper.selectByExample(example);
                for (Branch branch : branchList) {

                    int branchId = branch.getId();
                    PmdBranch record = new PmdBranch();
                    record.setMonthId(monthId);
                    record.setPartyId(partyId);
                    record.setBranchId(branchId);
                    record.setPartyName(partyName);
                    record.setBranchName(branch.getName());
                    record.setPartySortOrder(party.getSortOrder());
                    record.setSortOrder(branch.getSortOrder());
                    record.setHasReport(false);

                    pmdBranchMapper.insertSelective(record);

                    if (!allPayBranchIdSet.contains(branchId)) {
                        PmdPayBranch _record = new PmdPayBranch();
                        _record.setBranchId(branchId);
                        _record.setPartyId(partyId);
                        _record.setMonthId(monthId);
                        pmdPayBranchMapper.insertSelective(_record);
                    }
                }
            }

            {
                // 同步分党委信息
                PmdParty record = new PmdParty();
                record.setMonthId(monthId);
                record.setPartyId(partyId);
                record.setIsDirectBranch(false);
                record.setPartyName(partyName);
                record.setSortOrder(party.getSortOrder());
                record.setHasReport(false);
                // 党支部数
                //record.setBranchCount(branchList == null ? 0 : branchList.size());

                // 直属党支部特殊处理
                if (partyService.isDirectBranch(partyId)) {
                    record.setIsDirectBranch(true);
                    record.setBranchCount(1);
                }

                pmdPartyMapper.insertSelective(record);
            }
        }
    }


    public boolean idDuplicate(Integer id, Date payMonth) {

        PmdMonthExample example = new PmdMonthExample();
        PmdMonthExample.Criteria criteria = example.createCriteria()
                .andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        if (id != null) criteria.andIdNotEqualTo(id);

        return pmdMonthMapper.countByExample(example) > 0;
    }

    // 树形选择分党委（状态正常的）
    public TreeNode getPartyTree(int monthId) {

        // 当月已设定
        Set<Integer> monthPartyIdSet = getMonthPartyIdSet(monthId);
        if (null == monthPartyIdSet) monthPartyIdSet = new HashSet<>();

        // 全部已设定的
        Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet(true);

        TreeNode root = new TreeNode();
        root.title = "分党委列表";
        root.expanded = true;
        root.folder = true;
        root.checkbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        PartyExample example = new PartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause(" sort_order desc");
        List<Party> partyList = partyMapper.selectByExample(example);
        for (Party party : partyList) {

            int partyId = party.getId();
            TreeNode node = new TreeNode();
            node.title = party.getName();
            node.key = party.getId() + "";
            node.checkbox = true;
            if (monthPartyIdSet.contains(partyId)) {
                node.selected = true;
            }

            PmdPayParty pmdPayParty = allPayPartyIdSet.get(partyId);
            if (pmdPayParty != null && pmdPayParty.getMonthId() != monthId) {
                node.selected = true;
                node.unselectable = true;
                node.unselectableStatus = true;
                node.tooltip = "必选党委";
                //node.extraClasses = "unselectable";
            }

            rootChildren.add(node);
        }

        return root;
    }

    /*@Transactional
    public void del(Integer id) {

        pmdMonthMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdMonthMapper.deleteByExample(example);
    }*/

    @Transactional
    public int updateByPrimaryKeySelective(PmdMonth record) {
        if (record.getPayMonth() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getPayMonth()), "月份重复");
        return pmdMonthMapper.updateByPrimaryKeySelective(record);
    }

    // 新建缴费月份（当前月）
    @Transactional
    public void create() {

        Date now = new Date();
        if (getMonth(now) != null) {
            throw new OpException("缴费月份重复。");
        }

        Date payMonth = DateUtils.getFirstDateOfMonth(now);

        PmdMonth record = new PmdMonth();
        record.setPayMonth(payMonth);
        record.setStatus(PmdConstants.PMD_MONTH_STATUS_INIT);
        record.setCreateUserId(ShiroHelper.getCurrentUserId());
        record.setCreateTime(now);

        pmdMonthMapper.insertSelective(record);
    }

    // 新建/修改缴费月份
    @Transactional
    public void addOrUpdate(Integer id, Date month) {

        PmdMonth currentPmdMonth = getCurrentPmdMonth();
        if(currentPmdMonth!=null){
            throw new OpException("存在未结算月份，不可创建新的缴费月份。");
        }
        {
            PmdMonthExample example = new PmdMonthExample();
            example.createCriteria().andStatusEqualTo(PmdConstants.PMD_MONTH_STATUS_INIT);
            if(pmdMonthMapper.countByExample(example)>0){
                throw new OpException("存在未启动缴费月份，不可创建新的缴费月份。");
            }
        }

        PmdMonth pmdMonth = getMonth(month);
        if(id==null){
            if (pmdMonth != null) {
                throw new OpException("缴费月份重复。");
            }

            Date payMonth = DateUtils.getFirstDateOfMonth(month);

            PmdMonth record = new PmdMonth();
            record.setPayMonth(payMonth);
            record.setStatus(PmdConstants.PMD_MONTH_STATUS_INIT);
            record.setCreateUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(month);

            pmdMonthMapper.insertSelective(record);
        }else {

            if (pmdMonth != null && pmdMonth.getId() != id) {
                throw new OpException("缴费月份重复。");
            }

            PmdMonth _pmdMonth = pmdMonthMapper.selectByPrimaryKey(id);
            if (_pmdMonth.getStatus() != PmdConstants.PMD_MONTH_STATUS_INIT && PmdHelper.processMemberCount>=0) {

                throw new OpException("只能修改未启动的缴费月份。");
            }

            {
                PmdMonthExample example = new PmdMonthExample();
                example.createCriteria().andIdNotEqualTo(id)
                        .andPayMonthGreaterThanOrEqualTo(DateUtils.getFirstDateOfMonth(month));
                if (pmdMonthMapper.countByExample(example) > 0) {

                    throw new OpException("缴费月份有误。");
                }
            }

            PmdMonth record = new PmdMonth();
            record.setId(id);
            record.setPayMonth(month);

            pmdMonthMapper.updateByPrimaryKeySelective(record);
        }
    }
}
