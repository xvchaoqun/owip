package service.pmd;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.*;
import domain.sys.SysUserView;
import ext.service.PmdExtService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PmdMemberService extends PmdBaseMapper {

    @Autowired
    private PmdBranchService pmdBranchService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PmdPartyService pmdPartyService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private PmdConfigMemberTypeService pmdConfigMemberTypeService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private  PmdMemberPayService pmdMemberPayService;

    /**
     * 批量同步更新所有当月参与缴费党员
     *
     * 以当前党员库数据为基准进行更新，如果党员的所在支部在当月的缴费列表：
     *  1如果还没有缴费党员则添加
     *  2如果存在则更新所属党组织（仅针对还未缴费且为设置为延迟缴费的）
     *  3如果党员库中不存在了则删除（仅删除还未缴费、未设置为延迟缴费的缴费记录）
     */
    public void syncCurrentMonthPmdMembers(){

        PmdMonth pmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(pmdMonth==null) return;
        int pmdMonthId = pmdMonth.getId();
        Set<Integer> partyIdSet = new HashSet<>(); // 当月参与缴费的分党委和党支部
        Set<Integer> branchIdSet = new HashSet<>();
        {
            PmdPartyExample example = new PmdPartyExample();
            example.createCriteria().andMonthIdEqualTo(pmdMonthId);
            List<PmdParty> pmdPartyList = pmdPartyMapper.selectByExample(example);
            partyIdSet = pmdPartyList.stream().map(PmdParty::getPartyId).collect(Collectors.toSet());
        }
        {
            PmdBranchExample example = new PmdBranchExample();
            example.createCriteria().andMonthIdEqualTo(pmdMonthId);
            List<PmdBranch> pmdBranchList = pmdBranchMapper.selectByExample(example);
            branchIdSet = pmdBranchList.stream().map(PmdBranch::getBranchId).collect(Collectors.toSet());
        }

        List<Integer> needPayUserIdList = new ArrayList<>(); // 应参与缴费的党员USERID
        {
            MemberViewExample example = new MemberViewExample();
            MemberViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
            criteria.addPermits(new ArrayList<>(partyIdSet), new ArrayList<>(branchIdSet));
            List<MemberView> memberViews = memberViewMapper.selectByExample(example); // 应参与缴费的党员

            for (MemberView memberView : memberViews) {

                int userId = memberView.getUserId();
                needPayUserIdList.add(userId);
                Member member = memberMapper.selectByPrimaryKey(userId);
                PmdMember pmdMember = get(pmdMonthId, userId);
                if(pmdMember==null){ // 不存在则添加
                    pmdExtService.addOrResetMember(null, pmdMonth, member,false);
                }else{
                    if(!pmdMember.getHasPay() && !pmdMember.getIsDelay()) {
                        // 针对还未缴费且为设置为延迟缴费的，更新所在党组织
                        Integer partyId = member.getPartyId();
                        Integer branchId = member.getBranchId();
                        commonMapper.excuteSql("update pmd_member set party_id=" + partyId + ", branch_id=" + branchId
                                + " where user_id=" + userId + " and month_id=" + pmdMonthId);

                        if(!StringUtils.equals(partyId+"_"+branchId, pmdMember.getPartyId()+"_"+pmdMember.getBranchId())) {

                            Party party = CmTag.getParty(pmdMember.getPartyId());
                            Branch branch = CmTag.getBranch(pmdMember.getBranchId());
                            String oldPartyName = party.getName() + ((branch==null)?"":("-"+branch.getName()));
                            sysApprovalLogService.add(pmdMember.getId(), userId, SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                                    "同步更新所在党组织", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, oldPartyName);
                        }
                    }
                }
            }
        }
        {
            // 删除还未缴费、未设置为延迟缴费的缴费记录
            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andUserIdNotIn(needPayUserIdList)
                    .andHasPayEqualTo(false).andIsDelayEqualTo(false);
            pmdMemberMapper.deleteByExample(example);
        }
    }

    // 检测支部或直属党支部的操作权限（允许上级分党委或组织部管理员操作）
    public PmdMember checkAdmin(int pmdMemberId) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);

        Integer partyId = pmdMember.getPartyId();
        Integer branchId = pmdMember.getBranchId();

        // 当前所在的党支部快照，如果存在则应该由当前所在党支部操作
        PmdMember _pmdMember = get(currentMonthId, pmdMember.getUserId());
        if(_pmdMember!=null) {

            partyId = _pmdMember.getPartyId();
            branchId = _pmdMember.getBranchId();
        }

        List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
        Set<Integer> adminPartyIdSet = new HashSet<>();
        adminPartyIdSet.addAll(adminPartyIds);

        List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
        Set<Integer> adminBranchIdSet = new HashSet<>();
        adminBranchIdSet.addAll(adminBranchIds);

        // 检测党支部或直属党支部是否已经报送了
        if (partyService.isDirectBranch(partyId)) {

            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMDVIEWALL)
                    && !adminPartyIdSet.contains(partyId)) {
                throw new UnauthorizedException();
            }

            PmdParty pmdParty = pmdPartyService.get(currentMonthId, partyId);
            if (pmdParty == null || pmdParty.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        } else {

            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMDVIEWALL)
                    && !adminPartyIdSet.contains(partyId)
                    && !adminBranchIdSet.contains(branchId)) {
                throw new UnauthorizedException();
            }

            PmdBranch pmdBranch = pmdBranchService.get(currentMonthId, partyId, branchId);
            if (pmdBranch == null || pmdBranch.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        }
        return pmdMember;
    }

    public PmdMember get(int monthId, int userId){

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andUserIdEqualTo(userId);
        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);
        return pmdMembers.size()==0?null:pmdMembers.get(0);
    }

    @Transactional
    public void insertSelective(PmdMember record) {

        pmdMemberMapper.insertSelective(record);
    }

    // 删除未缴费记录（同时删除党员列表中的记录）
    @Transactional
    public void del(Integer id) {

        //PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);
        PmdMember pmdMember = checkAdmin(id);

        if(pmdMember.getHasPay()){
            throw  new OpException("操作失败，只能删除未缴费的记录。");
        }

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(pmdMember.getMonthId()!=currentPmdMonth.getId()){

            throw new OpException("操作失败，只能删除当前缴费月份的未缴费记录。");
        }
        if(pmdMemberPayService.branchHasReport(pmdMember.getPartyId(), pmdMember.getBranchId(),  currentPmdMonth)){

            throw  new OpException("操作失败，支部已报送。");
        }

        pmdMemberMapper.deleteByPrimaryKey(id);
        pmdMemberPayMapper.deleteByPrimaryKey(id);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andUserIdEqualTo(pmdMember.getUserId());
        if(pmdMemberMapper.countByExample(example)==0)
            pmdConfigMemberService.del(pmdMember.getUserId());
    }

    // 批量删除未缴费记录（同时删除党员列表中的记录，如果是往月缴费数据，则更新往月缴纳报表）
    @Transactional
    public void batchDel(Integer[] ids){

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andHasPayEqualTo(true);
        PmdMemberPayViewExample example2 = new PmdMemberPayViewExample();
        example2.createCriteria().andIdIn(Arrays.asList(ids)).andHasPayEqualTo(true); // 补缴已确认数据
        if (pmdMemberMapper.countByExample(example) >0
                || pmdMemberPayViewMapper.countByExample(example2) > 0) {
            throw new OpException("操作失败，存在已缴费的记录。");
        }

        example = new PmdMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andHasPayEqualTo(false);
        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);

        Set<Integer> updatePmdBranchIdSet = new HashSet<>();
        Set<Integer> updatePmdPartyIdSet = new HashSet<>();
        Set<Integer> updatePmdMonthIdSet = new HashSet<>();
        Set<Integer> updateUserIdSet = new HashSet<>();
        List<Integer> deletePmdMemberIds = new ArrayList<>();

        for (PmdMember pmdMember : pmdMembers) {

            deletePmdMemberIds.add(pmdMember.getId());
            updateUserIdSet.add(pmdMember.getUserId());

            int monthId = pmdMember.getMonthId();
            int partyId = pmdMember.getPartyId();
            Integer branchId = pmdMember.getBranchId();

            List<PmdMonth> pmdMonths = new ArrayList<>(); // 待更新汇总数据的月份（本月延迟党员数和缴费金额、往月应补缴金额）
            {
                PmdMonthExample _example = new PmdMonthExample();
                _example.createCriteria().andIdGreaterThanOrEqualTo(monthId);
                pmdMonths = pmdMonthMapper.selectByExample(_example);
            }

            for (PmdMonth pmdMonth : pmdMonths) {

                // 当前缴费月份不需要更新结算
                if(pmdMonth.getStatus()!=PmdConstants.PMD_MONTH_STATUS_END) continue;

                updatePmdMonthIdSet.add(pmdMonth.getId());

                PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
                if(pmdParty!=null){
                    updatePmdPartyIdSet.add(pmdParty.getId());
                }

                if(branchId!=null) {
                    PmdBranch pmdBranch = pmdBranchService.get(monthId, partyId, branchId);

                    if(pmdBranch!=null){
                        updatePmdBranchIdSet.add(pmdBranch.getId());
                    }
                }
            }
        }

        // 先删除记录
        {
            example = new PmdMemberExample();
            example.createCriteria().andIdIn(deletePmdMemberIds);
            pmdMemberMapper.deleteByExample(example);

            PmdMemberPayExample _example = new PmdMemberPayExample();
            _example.createCriteria().andMemberIdIn(deletePmdMemberIds);
            pmdMemberPayMapper.deleteByExample(_example);
        }

        // 更新相关汇总数据
        for (Integer pmdBranchId : updatePmdBranchIdSet) {

            pmdBranchService.updateReport(pmdBranchId);
        }

        for (Integer pmdPartyId : updatePmdPartyIdSet) {

            pmdPartyService.updateReport(pmdPartyId);
        }

        for (Integer pmdMonthId : updatePmdMonthIdSet) {
            pmdMonthService.updateEnd(pmdMonthId, false);
        }

        // 清除缴费人员，如果需要
        for (Integer userId : updateUserIdSet) {

            example = new PmdMemberExample();
            example.createCriteria().andUserIdEqualTo(userId);
            if(pmdMemberMapper.countByExample(example)==0)
                pmdConfigMemberService.del(userId);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdMember record) {

        return pmdMemberMapper.updateByPrimaryKeySelective(record);
    }

    // 修改延迟缴费的应交金额
    @Transactional
    public void changeDuePay(Integer[] ids, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        for (int id : ids) {

            PmdMember pmdMember = checkAdmin(id);
            SysUserView uv = pmdMember.getUser();

            if(pmdMember.getHasPay()){
                throw new OpException("{0}已缴费。", uv.getRealname());
            }

            if(pmdMember.getMonthId()==currentMonthId || !pmdMember.getIsDelay()){
                throw new OpException("{0}仅允许修改往月应交金额。", uv.getRealname());
            }

            PmdMember record = new PmdMember();
            record.setId(id);
            record.setDuePay(amount);

            pmdMemberMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(id, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "修改往月应交金额：" +pmdMember.getDuePay() + "->" + amount, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }

    }

    // 选择党员分类别
    @Transactional
    public void selectMemberType(Integer[] ids, Boolean isUser, Boolean hasSalary, byte configMemberType,
                                 int configMemberTypeId, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        List<Integer> pmMemberList = new ArrayList<>();
        if(BooleanUtils.isTrue(isUser) && currentPmdMonth != null){
            for(Integer userId : ids){
                PmdMember pmdMember = get(currentMonthId, userId);
                pmMemberList.add(pmdMember.getId());   // userId -> pmdMemberId
            }
        }else {
            pmMemberList.addAll(Arrays.asList(ids));
        }

        PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
        if(pmdConfigMemberType==null || pmdConfigMemberType.getType()!=configMemberType){
            throw new OpException("参数有误（党员分类异常）。");
        }

        PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();
        byte setType = pmdNorm.getSetType();
        if(setType== PmdConstants.PMD_NORM_SET_TYPE_FIXED){
            PmdNormValue pmdNormValue = pmdNorm.getPmdNormValue();
            amount = pmdNormValue.getAmount();
        }else if(setType == PmdConstants.PMD_NORM_SET_TYPE_SET){
            if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
                throw new OpException("额度必须大于0");
            }
        }else {
            // 公式
            Assert.isTrue(setType == PmdConstants.PMD_NORM_SET_TYPE_FORMULA, "参数错误");
            amount = null;
        }

        for (Integer id : pmMemberList) {

            PmdMember pmdMember = checkAdmin(id);
            SysUserView uv = pmdMember.getUser();
            int userId = uv.getId();

            if(pmdMember.getType()==PmdConstants.PMD_MEMBER_TYPE_STUDENT
                    && hasSalary ==null){
                throw new OpException("{0}是否带薪？", uv.getRealname());
            }

            if(pmdMember.getMonthId()!=currentMonthId){
                throw new OpException("{0}不允许对往月数据修改党员分类。", uv.getRealname());
            }

            if(pmdMember.getHasPay()){
                throw new OpException("{0}已缴费。", uv.getRealname());
            }
            if(pmdMember.getIsDelay()){
                throw new OpException("{0}已设置为延迟缴费。", uv.getRealname());
            }

            PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);

            // 同步公式对应的党费
            BigDecimal retireBase = null;
            Boolean isSalary = false; // 是否由工资计算党费
            Boolean isRetire = false; // 是否由离退休计算党费
            Boolean needSetSalary = false;
            if(setType==PmdConstants.PMD_NORM_SET_TYPE_FORMULA) {
                if(pmdConfigMember.getConfigMemberTypeId().equals(configMemberTypeId)){
                    //如果党员分类型的缴费类型为公式，且党员分类型未改变
                    return;
                }
                if (pmdNorm.getFormulaType() == PmdConstants.PMD_FORMULA_TYPE_RETIRE) {
                    isRetire = true;
                    retireBase = pmdExtService.getRetireBase(uv.getCode());
                    amount = pmdExtService.getDuePayFromRetireBase(retireBase);
                } else if (pmdNorm.getFormulaType() == PmdConstants.PMD_FORMULA_TYPE_ONJOB ||
                        pmdNorm.getFormulaType() == PmdConstants.PMD_FORMULA_TYPE_EXTERNAL) {

                    isSalary = true;
                    needSetSalary = BooleanUtils.isNotTrue(pmdConfigMember.getHasSetSalary());
                    if (!needSetSalary) {
                        // 已经提交了工资，则同步计算出来的党费
                        //amount = pmdConfigMemberService.calDuePay(pmdConfigMember);
                        amount = pmdConfigMember.getDuePay();
                    }
                }
            }
            if(!isRetire){
                // 清空离退休人员党费计算基数
                commonMapper.excuteSql(String.format("update pmd_config_member " +
                        "set retire_salary=null where user_id=%s", userId));
            }
            if(!isSalary){
                // 其他类别：清空工资项
                commonMapper.excuteSql(String.format("update pmd_config_member " +
                                "set salary=null where user_id=%s", userId));
            }

            {
                // 除了组织部管理员，其他人员不允许修改党员一级类别
                if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PMDVIEWALL)
                        && configMemberType!=pmdConfigMember.getConfigMemberType()){
                    throw new OpException("{0}不允许修改党员类别。", uv.getRealname());
                }

                PmdConfigMember record = new PmdConfigMember();
                record.setUserId(userId);
                record.setConfigMemberType(configMemberType);
                record.setConfigMemberTypeId(configMemberTypeId);
                record.setDuePay(amount);
                record.setRetireSalary(retireBase);
                record.setHasSalary(hasSalary);
                record.setHasReset(true);

                pmdConfigMemberService.updateByPrimaryKeySelective(record);
            }


            PmdMember record = new PmdMember();
            record.setConfigMemberTypeId(configMemberTypeId);
            record.setConfigMemberTypeName(pmdConfigMemberType.getName());
            record.setConfigMemberTypeNormId(pmdConfigMemberType.getNormId());
            record.setConfigMemberTypeNormId(pmdConfigMemberType.getNormId());
            record.setConfigMemberTypeNormName(pmdConfigMemberType.getPmdNorm().getName());
            record.setSalary(retireBase);
            record.setDuePayReason(pmdConfigMemberType.getName());
            record.setDuePay(amount);
            record.setConfigMemberDuePay(amount);

            // 只针对学生党员
            record.setHasSalary(hasSalary);
            record.setNeedSetSalary(needSetSalary);

            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andIdEqualTo(id)
                    .andHasPayEqualTo(false);
            if(pmdMemberMapper.updateByExampleSelective(record, example)==0){
                throw new OpException("{0}选择党员分类别操作失败", uv.getRealname());
            }

            if(amount==null){
                // 离退休工资没读取到 或者 没有设定工资 的情况
                commonMapper.excuteSql(String.format("update pmd_config_member " +
                                "set due_pay=%s, retire_salary=%s  where user_id=%s",
                        null, null, userId));

                commonMapper.excuteSql(String.format("update pmd_member set due_pay=%s, config_member_due_pay=%s where id=%s",
                        null, null, id));
            }

            sysApprovalLogService.add(id, uv.getId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "选择党员分类别：" + pmdConfigMemberType.getName() ,
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
            sysApprovalLogService.add(userId, uv.getId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_USER,
                    "选择党员分类别：" + pmdConfigMemberType.getName() ,
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "更新后缴费额度："+(amount==null?"":amount));
        }
    }

    // 选择减免标准
    @Transactional
    public void selectReduceNorm(Integer[] ids, int normId, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        Integer normValueId = null;
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        if(pmdNorm==null && pmdNorm.getType()!=PmdConstants.PMD_NORM_TYPE_REDUCE){
            throw new OpException("参数有误（选择减免标准异常）。");
        }
        if(pmdNorm.getSetType()==PmdConstants.PMD_NORM_SET_TYPE_FIXED){
            PmdNormValue pmdNormValue = pmdNorm.getPmdNormValue();
            normValueId = pmdNormValue.getId();
            amount = pmdNormValue.getAmount();
        }
        if(pmdNorm.getSetType() == PmdConstants.PMD_NORM_SET_TYPE_SET){
            if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
                throw new OpException("额度必须大于0");
            }
        }

        // 免交
        boolean isFree = (pmdNorm.getSetType()==PmdConstants.PMD_NORM_SET_TYPE_FREE);
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        for (int id : ids) {

            PmdMember pmdMember = checkAdmin(id);
            SysUserView uv = pmdMember.getUser();

            if(pmdMember.getMonthId()!=currentMonthId){
                throw new OpException("{0}不允许对往月数据选择缴纳标准。", uv.getRealname());
            }

            if(pmdMember.getHasPay()){
                throw new OpException("{0}已缴费。", uv.getRealname());
            }
            if(pmdMember.getIsDelay()){
                throw new OpException("{0}已设置为延迟缴费。", uv.getRealname());
            }

            /*if(pmdNorm.getType() == PmdConstants.PMD_NORM_TYPE_PAY
                    && pmdMember.getNormType()!= PmdConstants.PMD_MEMBER_NORM_TYPE_SELECT){
                throw new OpException("{0}不允许选择缴纳标准。", uv.getRealname());
            }*/

            PmdMember record = new PmdMember();
            record.setNormId(normId);
            record.setDuePayReason(pmdNorm.getName());
            record.setNormValueId(normValueId);
            record.setDuePay(amount);

            // 免交
            if(isFree){
                record.setDuePay(BigDecimal.ZERO);
                record.setRealPay(BigDecimal.ZERO);
                record.setHasPay(true);
                record.setIsSelfPay(false);
                record.setPayTime(new Date());
                record.setChargeUserId(currentUserId);

                // 同步实缴数据
                PmdMemberPay _record = new PmdMemberPay();
                _record.setMemberId(id);
                _record.setHasPay(true);
                _record.setRealPay(BigDecimal.ZERO);
                _record.setIsSelfPay(false);
                _record.setPayMonthId(currentMonthId);
                _record.setChargePartyId(pmdMember.getPartyId());
                _record.setChargeBranchId(pmdMember.getBranchId());
                _record.setPayTime(new Date());
                _record.setChargeUserId(currentUserId);

                PmdMemberPayExample example = new PmdMemberPayExample();
                example.createCriteria().andMemberIdEqualTo(id)
                        .andHasPayEqualTo(false);
                if(pmdMemberPayMapper.updateByExampleSelective(_record, example)==0){
                    throw new OpException("{0}更新账单失败");
                }
            }

            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andIdEqualTo(id)
                    .andHasPayEqualTo(false);
            if(pmdMemberMapper.updateByExampleSelective(record, example)==0){
                throw new OpException("{0}选择减免标准操作失败", uv.getRealname());
            }

            sysApprovalLogService.add(id, uv.getId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "设定" + PmdConstants.PMD_NORM_TYPE_MAP.get(pmdNorm.getType()) + "：" + pmdNorm.getName(),
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }

    }

    // 当月修改缴费方式 或 往月延迟缴费进行现金缴费
    @Transactional
    public void setIsOnlinePay(Integer[] ids, boolean isOnlinePay, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        for (int id : ids) {

            //PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);
            PmdMember pmdMember = checkAdmin(id);
            int pmdMemberId = pmdMember.getId();
            String realname = pmdMember.getUser().getRealname();
            BigDecimal duePay = pmdMember.getDuePay();
            int userId = pmdMember.getUserId();

            if(pmdMember.getHasPay()){
                PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(pmdMemberId);
                if(pmdMemberPayView.getPayMonthId()!=currentMonthId){
                    throw  new OpException("操作失败，{0}是往月已缴费记录。", realname);
                }

                if(pmdMember.getIsOnlinePay()){
                    throw new OpException("操作失败，{0}已线上缴费。", realname);
                }
            }

            PmdConfigMember pmdConfigMember = pmdMember.getPmdConfigMember();
            if(BooleanUtils.isFalse(pmdConfigMember.getHasReset())){
                throw  new OpException("操作失败，{0}未确认缴费额度。", realname); // 未确认缴费额度
            }

            Integer partyId = pmdMember.getPartyId();
            Integer branchId = pmdMember.getBranchId();

            // 当前所在的党支部快照，如果存在则应该由当前所在党支部操作
            PmdMember _pmdMember = get(currentMonthId, userId);
            if(_pmdMember!=null) {

                partyId = _pmdMember.getPartyId();
                branchId = _pmdMember.getBranchId();
            }

            // 检测党支部或直属党支部是否已经报送了
            if(pmdMemberPayService.branchHasReport(partyId, branchId, currentPmdMonth)){
                throw  new OpException("操作失败，{0}所在支部已报送。", realname);
            }

            boolean _isOnlinePay = pmdMember.getIsOnlinePay(); // 当前缴费方式
            if(_isOnlinePay==isOnlinePay) continue;

            String opStr = "现金缴费 -> 线上缴费";
            if(isOnlinePay && !_isOnlinePay){

                if(pmdMember.getMonthId() == currentMonthId && pmdMember.getIsDelay()==false) {
                    // 现金缴费 -> 线上缴费
                    pmdExtService.addOrResetPmdMember(pmdMember.getUserId(), pmdMemberId);

                }else if(pmdMember.getMonthId() != currentMonthId && pmdMember.getIsDelay()){
                    // 现金缴费 -> 延迟缴费
                    opStr = "现金缴费 -> 延迟缴费";
                    commonMapper.excuteSql(String.format("update pmd_member set real_pay=null, has_pay=0, " +
                            "is_online_pay=1 where id=%s", pmdMemberId));

                    commonMapper.excuteSql(String.format("update pmd_member_pay set real_pay=null, " +
                            "has_pay=0, is_online_pay=1, pay_month_id=null, charge_party_id=null, charge_branch_id=null"
                            +" where member_id=%s", pmdMemberId));

                    PmdConfigMember record = new PmdConfigMember();
                    record.setUserId(userId);
                    record.setIsOnlinePay(true);
                    pmdConfigMemberMapper.updateByPrimaryKeySelective(record);
                }

            }else if(!isOnlinePay && _isOnlinePay){

                Assert.isTrue(pmdMember.getMonthId() == currentMonthId || pmdMember.getIsDelay());

                opStr = "线上缴费 -> 现金缴费";
                if(pmdMember.getMonthId() != currentMonthId){
                    opStr = "现金补缴";
                }else if(pmdMember.getIsDelay()){
                    opStr = "延迟缴费 -> 现金缴费";
                }

                // 线上缴费 -> 现金缴费
                if(duePay==null){
                    throw  new OpException("操作失败，{0}没有设置缴费额度。", realname);
                }

                commonMapper.excuteSql(String.format("update pmd_member set real_pay=%s, has_pay=1, " +
                        "is_online_pay=0 where id=%s", duePay, pmdMemberId));

                commonMapper.excuteSql(String.format("update pmd_member_pay set real_pay=%s, " +
                        "has_pay=1, is_online_pay=0, pay_month_id=%s, charge_party_id=%s, charge_branch_id=%s"
                        +" where member_id=%s" , duePay, currentMonthId, partyId, branchId,  pmdMemberId));

                PmdConfigMember record = new PmdConfigMember();
                record.setUserId(userId);
                record.setIsOnlinePay(false);
                pmdConfigMemberMapper.updateByPrimaryKeySelective(record);
            }

            sysApprovalLogService.add(id, userId, SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    opStr, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }
    }

    // 清除订单号生成人
    /*@Transactional
    public void clearOrderUser(int pmdMemberId) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if(pmdMember.getHasPay()){
            throw new OpException("该订单已缴费成功，无法清除订单号生成人。");
        }

        PmdMemberPay pmdMemberPay = pmdMemberPayMapper.selectByPrimaryKey(pmdMemberId);
        Integer orderUserId = pmdMemberPay.getOrderUserId();
        SysUserView uv = sysUserService.findById(orderUserId);
        commonMapper.excuteSql("update pmd_member_pay set order_user_id=null where member_id="+ pmdMemberId);

        sysApprovalLogService.add(pmdMemberId, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                String.format("清除订单号生成人：%s(%s)", uv.getRealname(), uv.getCode()),
                SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }*/
}
