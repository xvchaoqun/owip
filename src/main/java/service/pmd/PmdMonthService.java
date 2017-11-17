package service.pmd;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberExample;
import domain.member.MemberTeacher;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMonth;
import domain.pmd.PmdMonthExample;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import domain.pmd.PmdPayBranch;
import domain.pmd.PmdPayBranchExample;
import domain.pmd.PmdPayParty;
import domain.pmd.PmdPayPartyExample;
import domain.pmd.PmdSpecialUser;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.common.bean.PmdReportBean;
import service.BaseMapper;
import service.member.MemberTeacherService;
import service.party.PartyService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.fancytree.TreeNode;
import sys.utils.DateUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PmdMonthService extends BaseMapper {

    @Autowired
    protected MemberTeacherService memberTeacherService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PmdPayPartyService pmdPayPartyService;
    @Autowired
    protected PmdPayBranchService pmdPayBranchService;
    @Autowired
    protected PmdBranchService pmdBranchService;
    @Autowired
    protected PmdPartyService pmdPartyService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private PmdSpecialUserService pmdSpecialUserService;
    @Autowired
    private SysUserService sysUserService;


    // 结算
    @Transactional
    public void end(int monthId) {

        if(!canEnd(monthId)) {
            throw new OpException("当前不允许结算。");
        }

        PmdMonth record = new PmdMonth();
        record.setStatus(SystemConstants.PMD_MONTH_STATUS_END);
        record.setEndUserId(ShiroHelper.getCurrentUserId());
        record.setEndTime(new Date());

        // 保存数据汇总
        PmdReportBean r = iPmdMapper.getOwPmdReportBean(monthId);
        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andIdEqualTo(monthId)
                .andStatusEqualTo(SystemConstants.PMD_MONTH_STATUS_START);
        pmdMonthMapper.updateByExampleSelective(record, example);
    }

    // 判断是否可以结算
    public boolean canEnd(int monthId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId) return false;

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        if(pmdMonth==null) return false;

        // 如果存在 未报送的分党委， 则不可报送
        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andHasReportEqualTo(false);

        return pmdPartyMapper.countByExample(example)==0;
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
        example.createCriteria().andStatusEqualTo(SystemConstants.PMD_MONTH_STATUS_START);
        List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExample(example);

        if(pmdMonths.size()>1) throw new OpException("缴费系统异常，请稍后再试。");

        return pmdMonths.size() > 0 ? pmdMonths.get(0) : null;
    }

    // 启动缴费
    @Transactional
    public void start(int monthId) {

        PmdMonth currentPmdMonth = getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            throw new OpException("存在未结算月份，不可以启动缴费。");
        }

        PmdMonth currentMonth = getMonth(new Date());
        if (currentMonth.getId() != monthId) {
            throw new OpException("只允许启动当前月份的缴费工作。");
        }

        Set<Integer> partyIdSet = getMonthPartyIdSet(monthId);
        if (partyIdSet.size() == 0) {
            throw new OpException("请先设置缴费分党委。");
        }

        for (Integer partyId : partyIdSet) {

            PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
            if (pmdParty == null)
                throw new OpException("数据异常，党委不存在。[{0}-{1}]", monthId, partyId);
            // 直属党支部特殊处理
            if (partyService.isDirectBranch(partyId)){
                // 同步党员（直属党支部）
                MemberExample example = new MemberExample();
                example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                        .andPartyIdEqualTo(partyId).andBranchIdIsNull();
                List<Member> members = memberMapper.selectByExample(example);
                for (Member member : members) {
                    addMember(currentMonth, member);
                }
                Integer memberCount = members.size();

                // 更新当月直属党支部信息
                PmdParty record = new PmdParty();
                record.setId(pmdParty.getId());
                // 党员总数
                record.setMemberCount(memberCount);
                // 本月应交党费数
                //record.setDuePay(iPmdMapper.duePay(monthId, partyId, null));

                pmdPartyMapper.updateByPrimaryKeySelective(record);

            }else {

                List<Integer> branchIdList = iPmdMapper.branchIdList(monthId, partyId);
                for (Integer branchId : branchIdList) {

                    // 同步党员
                    MemberExample example = new MemberExample();
                    example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                            .andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
                    List<Member> members = memberMapper.selectByExample(example);
                    for (Member member : members) {
                        addMember(currentMonth, member);
                    }

                    {
                        // 更新当月党支部信息（党员总数、本月应交党费数）
                        PmdBranch pmdBranch = pmdBranchService.get(monthId, partyId, branchId);
                        if (pmdBranch == null)
                            throw new OpException("数据异常，党支部不存在。[{0}-{1}-{2}]",
                                    monthId, partyId, branchId);

                        PmdBranch record = new PmdBranch();
                        record.setId(pmdBranch.getId());
                        // 党员总数
                        record.setMemberCount(members.size());
                        // 本月应交党费数
                        //record.setDuePay(iPmdMapper.duePay(monthId, partyId, branchId));

                        // 往月延迟缴费党员数
                        record.setHistoryDelayMemberCount(iPmdMapper.historyDelayMemberCount(monthId, partyId, branchId));
                        // 应补缴往月党费数
                        BigDecimal historyDelayPay = iPmdMapper.historyDelayPay(monthId, partyId, branchId);
                        historyDelayPay = (historyDelayPay==null)?new BigDecimal(0):historyDelayPay;
                        record.setHistoryDelayPay(historyDelayPay);

                        pmdBranchMapper.updateByPrimaryKeySelective(record);
                    }
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
        historyDelayPay = (historyDelayPay==null)?new BigDecimal(0):historyDelayPay;
        record.setHistoryDelayPay(historyDelayPay);

        record.setStatus(SystemConstants.PMD_MONTH_STATUS_START);
        pmdMonthMapper.updateByPrimaryKeySelective(record);
    }

    // 添加一个党员
    @Transactional
    private void addMember(PmdMonth pmdMonth, Member member) {

        int monthId = pmdMonth.getId();
        int userId = member.getUserId();
        SysUserView uv = sysUserService.findById(userId);

        Integer memberId = null;
        {
            // 新建党员快照
            PmdMember record = new PmdMember();
            record.setMonthId(monthId);
            record.setPayMonth(pmdMonth.getPayMonth());
            record.setUserId(userId);
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());
            Byte type = null;

            // 特殊缴费人员（年薪制） > 离退 > 高层次人才 > 事业 > 非事业
            // 标准类型
            Byte normType = SystemConstants.PMD_MEMBER_NORM_TYPE_UNMODIFY;
            // 标准名称，系统自动计算得到
            String normName = null;
            // 标准对应的额度，系统自动计算得到
            BigDecimal normDuePay = null;

            if (member.getType() == SystemConstants.MEMBER_TYPE_STUDENT) {

                type = SystemConstants.PMD_MEMBER_TYPE_STUDENT;
                // 对于学生来说，需要支部选择缴费标准
                normType = SystemConstants.PMD_MEMBER_NORM_TYPE_SELECT;
            } else {
                MemberTeacher memberTeacher = memberTeacherService.get(userId);
                record.setTalentTitle(memberTeacher.getTalentTitle());
                record.setPostClass(memberTeacher.getPostClass());
                record.setMainPostLevel(memberTeacher.getMainPostLevel());
                record.setAuthorizedType(memberTeacher.getAuthorizedType());
                record.setStaffType(memberTeacher.getStaffType());

                if (memberTeacher.getIsRetire()) {
                    type = SystemConstants.PMD_MEMBER_TYPE_RETIRE;

                    BigDecimal ltxf = pmdExtService.getLtxf(memberTeacher.getCode());
                    if(ltxf==null || ltxf.compareTo(BigDecimal.ZERO)<=0){
                        normName = "离退休";
                    }else if(ltxf.compareTo(BigDecimal.valueOf(5000))>0){
                        normName = "离退休费>5000元";
                        normDuePay = ltxf.multiply(BigDecimal.valueOf(0.01));
                    }else{
                        normName = "离退休费<=5000元";
                        normDuePay = ltxf.multiply(BigDecimal.valueOf(0.005));
                    }
                } else {
                    type = SystemConstants.PMD_MEMBER_TYPE_TEACHER;
                    boolean syb = pmdExtService.isSYB(memberTeacher);
                    if(syb){
                        // 事业编
                        int maxRCCHDuePay = pmdExtService.getMaxRCCHDuePay(memberTeacher);
                        if(maxRCCHDuePay>0){
                            normName = "高层次人才";
                            normDuePay = new BigDecimal(maxRCCHDuePay);
                        }else {
                            int mainPostDuePay = pmdExtService.getMainPostDuePay(memberTeacher);
                            if(mainPostDuePay>0){
                                normName = memberTeacher.getMainPostLevel();
                                normDuePay = new BigDecimal(mainPostDuePay);
                            }else{
                                normName = "“主岗等级”为空，支部设定党费应交额度";
                            }
                        }
                    }else{
                        // 非事业编
                        if(pmdExtService.isXP(memberTeacher)){

                            normName = "校聘职工";
                            int xpDuePay = pmdExtService.getXPDuePay(memberTeacher);
                            if(xpDuePay>0){
                                normDuePay = new BigDecimal(xpDuePay);
                            }
                        }else if(pmdExtService.isXSZL(memberTeacher)){
                            normName = "学生助理";
                            int xszlDuePay = pmdExtService.getXSZLDuePay(memberTeacher);
                            if(xszlDuePay>0){
                                normDuePay = new BigDecimal(xszlDuePay);
                            }
                        }else{
                            normName = "其他教职工";
                        }
                    }
                }

                if(normDuePay==null){
                    // 对于教职工来说，没找对对应的缴费金额，则允许支部编辑额度
                    normType = SystemConstants.PMD_MEMBER_NORM_TYPE_MODIFY;
                }
            }
            record.setType(type);

            // 如果是特殊缴费人员，则规则覆盖为支部直接编辑额度
            Map<String, PmdSpecialUser> pmdSpecialUserMap = pmdSpecialUserService.findAll();
            PmdSpecialUser pmdSpecialUser = pmdSpecialUserMap.get(uv.getCode());
            if(pmdSpecialUser!=null){
                normType = SystemConstants.PMD_MEMBER_NORM_TYPE_MODIFY;
                normName = pmdSpecialUser.getType();
                normDuePay = null;
            }

            record.setNormType(normType);
            record.setNormName(normName);
            record.setNormDuePay(normDuePay);

            record.setNormDisplayName(normName);
            record.setDuePay(normDuePay);

            //record.setRealPay(new BigDecimal(0));
            record.setIsDelay(false);
            record.setHasPay(false);

            pmdMemberMapper.insertSelective(record);
            memberId = record.getId();
        }

        {
            // 同步至党员账本
            PmdMemberPay record = new PmdMemberPay();
            record.setMemberId(memberId);
            record.setHasPay(false);

            pmdMemberPayMapper.insertSelective(record);
        }
    }

    // 得到某月的分党委ID列表
    public Set<Integer> getMonthPartyIdSet(int monthId) {

        Set<Integer> partyIdSet = new LinkedHashSet<>();
        partyIdSet.addAll(iPmdMapper.partyIdList(monthId));

        return partyIdSet;
    }

    // 设置缴费分党委（只可编辑未启动缴费）
    @Transactional
    public void updatePartyIds(int monthId, Integer[] partyIds) {

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        if(pmdMonth.getStatus()==SystemConstants.PMD_MONTH_STATUS_END){
            throw new OpException("已结算月份不允许编辑缴费分党委。");
        }else if (pmdMonth.getStatus() == SystemConstants.PMD_MONTH_STATUS_START) {
            throw new OpException("已经启动缴费，不允许重新设置缴费分党委。");
        }

        Set<Integer> selectedPartyIdSet = new HashSet<>();
        {
            // 删除从本月开始参与缴费的分党委
            PmdPayPartyExample example = new PmdPayPartyExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdPayPartyMapper.deleteByExample(example);

            selectedPartyIdSet.addAll(Arrays.asList(partyIds));

            Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet();
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
            record.setPartyCount (selectedPartyIdSet.size());
            pmdMonthMapper.updateByPrimaryKeySelective(record);
        }

        {
            // 删除从本月新设置参与缴费的党支部
            PmdPayBranchExample example = new PmdPayBranchExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            pmdPayBranchMapper.deleteByExample(example);
        }

        Set<Integer> allPayBranchIdSet = pmdPayBranchService.getAllPayBranchIdSet(null).keySet();
        for (int partyId : selectedPartyIdSet) {
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
                    record.setSortOrder(party.getSortOrder());
                    record.setHasReport(false);

                    pmdBranchMapper.insertSelective(record);

                    if(!allPayBranchIdSet.contains(branchId)){
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
                record.setBranchCount(branchList == null ? 0 : branchList.size());

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

    // 新建缴费月份（当前月）
    @Transactional
    public void create() {

        Date now = new Date();
        if (getMonth(now) != null) {
            throw new OpException("已经创建了当前缴费月份。");
        }

        Date payMonth = DateUtils.getFirstDateOfMonth(now);

        PmdMonth record = new PmdMonth();
        record.setPayMonth(payMonth);
        record.setStatus(SystemConstants.PMD_MONTH_STATUS_INIT);
        record.setCreateUserId(ShiroHelper.getCurrentUserId());
        record.setCreateTime(now);

        pmdMonthMapper.insertSelective(record);
    }

    // 树形选择分党委（状态正常的）
    public TreeNode getPartyTree(int monthId) {

        // 当月已设定
        Set<Integer> monthPartyIdSet = getMonthPartyIdSet(monthId);
        if (null == monthPartyIdSet) monthPartyIdSet = new HashSet<>();

        // 全部已设定的
        Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet();

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
            if(pmdPayParty!=null && pmdPayParty.getMonthId()!=monthId){
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

    @Transactional
    public void del(Integer id) {

        pmdMonthMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdMonthMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdMonth record) {
        if (record.getPayMonth() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getPayMonth()), "月份重复");
        return pmdMonthMapper.updateByPrimaryKeySelective(record);
    }


}
