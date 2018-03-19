package service.pmd;

import controller.global.OpException;
import domain.ext.ExtJzgSalary;
import domain.ext.ExtRetireSalary;
import domain.member.Member;
import domain.member.MemberExample;
import domain.member.MemberTeacher;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMonth;
import domain.pmd.PmdMonthExample;
import domain.pmd.PmdNorm;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import domain.pmd.PmdPayBranch;
import domain.pmd.PmdPayBranchExample;
import domain.pmd.PmdPayParty;
import domain.pmd.PmdPayPartyExample;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.common.bean.PmdReportBean;
import service.BaseMapper;
import service.party.MemberService;
import service.party.MemberTeacherService;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.PmdConstants;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

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
    private PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private PmdConfigMemberTypeService pmdConfigMemberTypeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PmdConfigResetService pmdConfigResetService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


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
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdMonthExample example = new PmdMonthExample();
        example.createCriteria().andIdEqualTo(monthId)
                .andStatusEqualTo(PmdConstants.PMD_MONTH_STATUS_START);
        pmdMonthMapper.updateByExampleSelective(record, example);
    }

    // 判断是否可以结算
    public boolean canEnd(int monthId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
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

        if (pmdMonths.size() > 1) throw new OpException("缴费系统异常，请稍后再试。");

        return pmdMonths.size() > 0 ? pmdMonths.get(0) : null;
    }

    // 启动缴费
    @Transactional
    public void start(int monthId) {

        PmdMonth currentPmdMonth = getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            throw new OpException("存在未结算月份，不可以启动缴费。");
        }

        PmdMonth currentMonth = pmdMonthMapper.selectByPrimaryKey(monthId);
        /*PmdMonth currentMonth = getMonth(new Date());
        if (currentMonth.getId() != monthId) {
            throw new OpException("只允许启动当前月份的缴费工作。");
        }*/

        Set<Integer> partyIdSet = getMonthPartyIdSet(monthId);
        if (partyIdSet.size() == 0) {
            throw new OpException("请先设置缴费分党委。");
        }
        String payMonth = DateUtils.formatDate(currentMonth.getPayMonth(), "yyyy年MM月");
        logger.info("{}党员缴费-启动", payMonth);

        long start = System.currentTimeMillis();
        for (Integer partyId : partyIdSet) {

            Party party = partyService.findAll().get(partyId);
            logger.info("{}党员缴费-同步数据-{}", payMonth, party.getName());

            PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
            if (pmdParty == null)
                throw new OpException("数据异常，党委不存在。[{0}-{1}]", monthId, partyId);
            // 直属党支部特殊处理
            if (partyService.isDirectBranch(partyId)) {
                // 同步党员（直属党支部）
                MemberExample example = new MemberExample();
                example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andPartyIdEqualTo(partyId).andBranchIdIsNull();
                List<Member> members = memberMapper.selectByExample(example);
                for (Member member : members) {
                    addOrResetMember(null, currentMonth, member);
                }
                //Integer memberCount = members.size();

                // 更新当月直属党支部信息
                PmdParty record = new PmdParty();
                record.setId(pmdParty.getId());
                // 党员总数
                //record.setMemberCount(memberCount);
                // 本月应交党费数
                //record.setDuePay(iPmdMapper.duePay(monthId, partyId, null));

                pmdPartyMapper.updateByPrimaryKeySelective(record);

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
            addOrResetMember(null, currentMonth, member);
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

    // 添加一个党员
   /* @Transactional
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
            Byte normType = PmdConstants.PMD_MEMBER_NORM_TYPE_UNMODIFY;
            // 标准名称，系统自动计算得到
            String normName = null;
            // 标准对应的额度，系统自动计算得到
            BigDecimal normDuePay = null;

            if (member.getType() == MemberConstants.MEMBER_TYPE_STUDENT) {

                type = PmdConstants.PMD_MEMBER_TYPE_STUDENT;
                // 对于学生来说，需要支部选择缴费标准
                normType = PmdConstants.PMD_MEMBER_NORM_TYPE_SELECT;
            } else {

                MemberTeacher memberTeacher = memberTeacherService.get(userId);
                record.setTalentTitle(memberTeacher.getTalentTitle());
                record.setPostClass(memberTeacher.getPostClass());
                record.setMainPostLevel(memberTeacher.getMainPostLevel());
                record.setProPostLevel(memberTeacher.getProPostLevel());
                record.setManageLevel(memberTeacher.getManageLevel());
                record.setOfficeLevel(memberTeacher.getOfficeLevel());
                record.setAuthorizedType(memberTeacher.getAuthorizedType());
                record.setStaffType(memberTeacher.getStaffType());

                type = memberTeacher.getIsRetire()?PmdConstants.PMD_MEMBER_TYPE_RETIRE
                        :PmdConstants.PMD_MEMBER_TYPE_TEACHER;

                // 如果是特殊缴费人员，则规则为支部直接编辑额度
                Map<String, PmdSpecialUser> pmdSpecialUserMap = pmdSpecialUserService.findAll();
                PmdSpecialUser pmdSpecialUser = pmdSpecialUserMap.get(uv.getCode());
                if (pmdSpecialUser != null) {
                    normType = PmdConstants.PMD_MEMBER_NORM_TYPE_MODIFY;
                    normName = pmdSpecialUser.getType();
                    normDuePay = null;
                } else {

                    if (type == PmdConstants.PMD_MEMBER_TYPE_RETIRE) {

                        BigDecimal ltxf = pmdExtService.getLtxf(memberTeacher.getCode());
                        if (ltxf == null || ltxf.compareTo(BigDecimal.ZERO) <= 0) {
                            normName = "离退休";
                        } else if (ltxf.compareTo(BigDecimal.valueOf(5000)) > 0) {
                            normName = "离退休费>5000元";
                            normDuePay = ltxf.multiply(BigDecimal.valueOf(0.01));
                        } else {
                            normName = "离退休费<=5000元";
                            normDuePay = ltxf.multiply(BigDecimal.valueOf(0.005));
                        }
                    } else {

                        int maxRCCHDuePay = pmdExtService.getMaxRCCHDuePay(memberTeacher);
                        if (maxRCCHDuePay > 0) {
                            normName = "高层次人才";
                            normDuePay = BigDecimal.valueOf(maxRCCHDuePay);
                        } else {
                            boolean syb = pmdExtService.isSYB(memberTeacher);
                            if (syb) {
                                // 事业编
                                PmdExtService.PostDuePayBean postDuePay = pmdExtService.getPostDuePay(memberTeacher);
                                int duePay = postDuePay.getDuePay();
                                if (duePay > 0) {
                                    normName = postDuePay.getPost();
                                    normDuePay = BigDecimal.valueOf(duePay);
                                } else {
                                    normName = "缺少岗位等级数据";
                                }
                            } else {
                                // 非事业编
                                if (pmdExtService.isXP(memberTeacher)) {

                                    normName = "校聘职工";
                                    int xpDuePay = pmdExtService.getXPDuePay(memberTeacher);
                                    if (xpDuePay > 0) {
                                        normDuePay = BigDecimal.valueOf(xpDuePay);
                                    }
                                } else if (pmdExtService.isXSZL(memberTeacher)) {
                                    normName = "学生助理";
                                    int xszlDuePay = pmdExtService.getXSZLDuePay(memberTeacher);
                                    if (xszlDuePay > 0) {
                                        normDuePay = BigDecimal.valueOf(xszlDuePay);
                                    }
                                } else {
                                    normName = "其他教职工";
                                }
                            }
                        }
                    }

                    if (normDuePay == null) {
                        // 对于教职工来说，没找对对应的缴费金额，则允许支部编辑额度
                        normType = PmdConstants.PMD_MEMBER_NORM_TYPE_MODIFY;
                    }
                }
            }
            record.setType(type);


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
    }*/

    // 添加或重置党员缴费记录
    @Transactional
    private PmdMember addOrResetMember(Integer pmdMemberId, PmdMonth pmdMonth, Member member) {

        int monthId = pmdMonth.getId();
        int userId = member.getUserId();
        //SysUserView uv = sysUserService.findById(userId);

        /**
         * 读取党员缴费分类
          */
        // 党员分类
        Byte configMemberType = null;
        // 标准对应的额度，系统自动计算得到
        BigDecimal duePay = null;
        // 党员分类别
        Integer configMemberTypeId = null;
        // 离退休费
        BigDecimal ltxf = null;

        // 是否需要提交工资明细，如果是A1、A2类别的党员还没提交工资明细则需要，否则不需要（辅助字段）
        Boolean needSetSalary = false;
        // 党费缴纳标准（辅助字段）
        String duePayReason = null;

        Boolean isOnlinePay = true;

        PmdConfigMember pmdConfigMember = pmdConfigMemberMapper.selectByPrimaryKey(userId);
        if (pmdConfigMember != null && pmdConfigMember.getConfigMemberType() != null) {
            configMemberType = pmdConfigMember.getConfigMemberType();
            duePay = pmdConfigMember.getDuePay();
            configMemberTypeId = pmdConfigMember.getConfigMemberTypeId();
            ltxf = pmdConfigMember.getRetireSalary();
            needSetSalary = BooleanUtils.isNotTrue(pmdConfigMember.getHasSetSalary());
            isOnlinePay = pmdConfigMember.getIsOnlinePay();

            if(pmdMemberId!=null){

                isOnlinePay = true;
                // 缴费方式更新为线上缴费（现金缴费修改为线上缴费时调用）
                PmdConfigMember record = new PmdConfigMember();
                record.setUserId(userId);
                record.setIsOnlinePay(true);
                pmdConfigMemberMapper.updateByPrimaryKeySelective(record);
            }

        } else {
            if (member.getType() == MemberConstants.MEMBER_TYPE_STUDENT) {
                configMemberType = PmdConstants.PMD_MEMBER_TYPE_STUDENT;
            } else {
                MemberTeacher memberTeacher = memberTeacherService.get(userId);
                configMemberType = memberTeacher.getIsRetire() ? PmdConstants.PMD_MEMBER_TYPE_RETIRE
                        : PmdConstants.PMD_MEMBER_TYPE_ONJOB;
                // 附属学校
                Set<String> partyCodeSet = new HashSet<>();
                partyCodeSet.add("030300");
                partyCodeSet.add("030500");
                int partyId = member.getPartyId();
                Party party = partyService.findAll().get(partyId);
                String partyCode = party.getCode();
                if (partyCodeSet.contains(partyCode)) {
                    configMemberType = PmdConstants.PMD_MEMBER_TYPE_OTHER;
                }

                Map<Byte, PmdConfigMemberType> formulaMap = pmdConfigMemberTypeService.formulaMap();
                if (configMemberType == PmdConstants.PMD_MEMBER_TYPE_RETIRE) {

                    // 设定分类别：离退休
                    PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_RETIRE);
                    if (pmdConfigMemberType != null) {
                        configMemberTypeId = pmdConfigMemberType.getId();
                    }

                    ltxf = pmdExtService.getLtxf(memberTeacher.getCode());
                    duePay = pmdExtService.getDuePayFromLtxf(ltxf);
                } else {
                    boolean syb = pmdExtService.isSYB(memberTeacher);
                    if (syb) {

                        needSetSalary = true;

                        // 设定分类别：在职在编教职工
                        PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_ONJOB);
                        if (pmdConfigMemberType != null) {
                            configMemberTypeId = pmdConfigMemberType.getId();
                        }

                    } else if (pmdExtService.isXP(memberTeacher)) {

                        needSetSalary = true;

                        // 设定分类别：校聘教职工
                        PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_EXTERNAL);
                        if (pmdConfigMemberType != null) {
                            configMemberTypeId = pmdConfigMemberType.getId();
                        }
                    }
                }
            }

            PmdConfigMember record = new PmdConfigMember();
            record.setUserId(userId);
            record.setConfigMemberType(configMemberType);
            record.setConfigMemberTypeId(configMemberTypeId);
            record.setDuePay(duePay);
            record.setRetireSalary(ltxf);
            // 默认线上缴费
            record.setIsOnlinePay(true);

            pmdConfigMemberService.insertSelective(record);
        }

        Integer _pmdMemberId = null;
        // 新建党员快照
        PmdMember _pmdMember = new PmdMember();
        {
            _pmdMember.setMonthId(monthId);
            _pmdMember.setPayMonth(pmdMonth.getPayMonth());
            _pmdMember.setUserId(userId);
            _pmdMember.setPartyId(member.getPartyId());
            _pmdMember.setBranchId(member.getBranchId());

            if(configMemberType != PmdConstants.PMD_MEMBER_TYPE_STUDENT){

                MemberTeacher memberTeacher = memberTeacherService.get(userId);
                _pmdMember.setTalentTitle(memberTeacher.getTalentTitle());
                _pmdMember.setPostClass(memberTeacher.getPostClass());
                _pmdMember.setMainPostLevel(memberTeacher.getMainPostLevel());
                _pmdMember.setProPostLevel(memberTeacher.getProPostLevel());
                _pmdMember.setManageLevel(memberTeacher.getManageLevel());
                _pmdMember.setOfficeLevel(memberTeacher.getOfficeLevel());
                _pmdMember.setAuthorizedType(memberTeacher.getAuthorizedType());
                _pmdMember.setStaffType(memberTeacher.getStaffType());
            }

            _pmdMember.setType(configMemberType);
            if(configMemberTypeId!=null){
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                if(pmdConfigMemberType!=null) {
                    PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

                    _pmdMember.setConfigMemberTypeId(configMemberTypeId);
                    _pmdMember.setConfigMemberTypeName(pmdConfigMemberType.getName());
                    _pmdMember.setConfigMemberTypeNormId(pmdConfigMemberType.getNormId());
                    _pmdMember.setConfigMemberTypeNormName(pmdNorm.getName());

                    duePayReason = pmdConfigMemberType.getName();
                    /*if(pmdNorm.getType() == PmdConstants.PMD_NORM_SET_TYPE_FORMULA){
                        switch (pmdNorm.getFormulaType()){
                            case PmdConstants.PMD_FORMULA_TYPE_ONJOB:
                            case PmdConstants.PMD_FORMULA_TYPE_EXTERNAL:
                                needSetSalary = true;
                                break;
                        }
                    }*/
                }
            }
            _pmdMember.setConfigMemberDuePay(duePay);
            _pmdMember.setSalary(ltxf);
            _pmdMember.setDuePay(duePay);

            _pmdMember.setNeedSetSalary(needSetSalary);
            _pmdMember.setDuePayReason(duePayReason);

            //record.setRealPay(new BigDecimal(0));
            _pmdMember.setIsDelay(false);
            _pmdMember.setHasPay(false);

            _pmdMember.setIsOnlinePay(true);

            if(pmdMemberId==null) {
                pmdMemberMapper.insertSelective(_pmdMember);
                _pmdMemberId = _pmdMember.getId();
            }else{
                _pmdMember.setId(pmdMemberId);
                _pmdMember.setRealPay(new BigDecimal(0));
                pmdMemberMapper.updateByPrimaryKeySelective(_pmdMember);
            }
        }

        if(pmdMemberId==null) {
            // 同步至党员账本
            PmdMemberPay record = new PmdMemberPay();
            record.setMemberId(_pmdMemberId);
            record.setHasPay(false);

            pmdMemberPayMapper.insertSelective(record);
        }else{

            commonMapper.excuteSql("update pmd_member_pay set real_pay=null, " +
                    "has_pay=0, is_online_pay=1, pay_month_id=null where member_id=" + pmdMemberId);
        }

        // 当党员默认为现金缴费时，同步添加当月的缴费记录时，需要更新为已缴费
        if(!isOnlinePay){

            if(pmdMemberId != null || duePay == null){
                // 重置缴费信息时，不可能为现金缴费
                throw new OpException("参数有误");
            }

            commonMapper.excuteSql(String.format("update pmd_member set real_pay=%s, has_pay=1, " +
                    "is_online_pay=0 where id=%s", duePay, _pmdMemberId));

            int partyId = _pmdMember.getPartyId();
            Integer branchId = _pmdMember.getBranchId();

            commonMapper.excuteSql(String.format("update pmd_member_pay set real_pay=%s, " +
                    "has_pay=1, is_online_pay=0, pay_month_id=%s, charge_party_id=%s, charge_branch_id=%s"
                    +" where member_id=%s" , duePay, monthId, partyId, branchId,  _pmdMemberId));
        }
        
        return _pmdMember;
    }

    // 添加或重置党员缴费记录
    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#userId")
    public void addOrResetPmdMember(int userId, Integer pmdMemberId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        Member member = memberService.get(userId);
        if(currentPmdMonth==null || member==null){

            throw new OpException("{1}缴费记录失败，未到缴费月份或不是党员：{0}",
                    sysUserService.findById(member.getUserId()).getRealname(),
                    pmdMemberId==null?"添加":"更新");
        }

        PmdMember pmdMember = addOrResetMember(pmdMemberId, currentPmdMonth, member);

        Date salaryMonth = pmdConfigResetService.getSalaryMonth();
        if(salaryMonth!=null) {

            String _salaryMonth = DateUtils.formatDate(salaryMonth, "yyyyMM");
            SysUserView uv = sysUserService.findById(userId);

            ExtJzgSalary ejs = iPmdMapper.getExtJzgSalary(_salaryMonth, uv.getCode());
            // 更新在职教职工工资
            pmdConfigResetService.updateDuePayByJzgSalary(ejs);

            ExtRetireSalary ers = iPmdMapper.getExtRetireSalary(_salaryMonth, uv.getCode());
            // 更新离退休费
            pmdConfigResetService.updateDuePayByRetireSalary(ers);
        }

        sysApprovalLogService.add(pmdMember.getId(), userId, SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                (pmdMemberId==null?"添加":"更新")+ "缴费记录", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
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
            record.setPartyCount(selectedPartyIdSet.size());
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
        record.setStatus(PmdConstants.PMD_MONTH_STATUS_INIT);
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

    // 修改缴费月份
    @Transactional
    public void update(int id, Date month) {

        PmdMonth pmdMonth = getMonth(month);
        if(pmdMonth!=null && pmdMonth.getId()!=id){
            throw new OpException("缴费月份重复。");
        }

        PmdMonth _pmdMonth = pmdMonthMapper.selectByPrimaryKey(id);
        if(_pmdMonth.getStatus()!=PmdConstants.PMD_MONTH_STATUS_INIT){

            throw new OpException("只能修改未启动的缴费月份。");
        }

        {
            PmdMonthExample example = new PmdMonthExample();
            example.createCriteria().andIdNotEqualTo(id)
                    .andPayMonthGreaterThanOrEqualTo(DateUtils.getFirstDateOfMonth(month));
            if(pmdMonthMapper.countByExample(example)>0){

                throw new OpException("缴费月份有误。");
            }
        }

        PmdMonth record = new PmdMonth();
        record.setId(id);
        record.setPayMonth(month);

        pmdMonthMapper.updateByPrimaryKeySelective(record);
    }
}
