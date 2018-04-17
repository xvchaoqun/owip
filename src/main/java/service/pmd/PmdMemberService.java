package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdBranch;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import domain.pmd.PmdNormValue;
import domain.pmd.PmdParty;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PmdMemberService extends BaseMapper {

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

    // 检测支部或直属党支部的操作权限（允许上级分党委或组织部管理员操作）
    public PmdMember checkAdmin(int pmdMemberId) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 当前所在的单位快照
        PmdMember _pmdMember = get(currentMonthId, pmdMember.getUserId());

        // 检测党支部或直属党支部是否已经报送了
        Integer partyId = _pmdMember.getPartyId();
        Integer branchId = _pmdMember.getBranchId();

        List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
        Set<Integer> adminPartyIdSet = new HashSet<>();
        adminPartyIdSet.addAll(adminPartyIds);

        List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
        Set<Integer> adminBranchIdSet = new HashSet<>();
        adminBranchIdSet.addAll(adminBranchIds);

        if (partyService.isDirectBranch(partyId)) {

            if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)
                    && !adminPartyIdSet.contains(partyId)) {
                throw new UnauthorizedException();
            }

            PmdParty pmdParty = pmdPartyService.get(currentMonthId, partyId);
            if (pmdParty == null || pmdParty.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        } else {

            if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)
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
        if(pmdMemberPayService.branchHasReport(pmdMember, currentPmdMonth)){

            throw  new OpException("操作失败，支部已报送。");
        }

        pmdMemberMapper.deleteByPrimaryKey(id);
        pmdMemberPayMapper.deleteByPrimaryKey(id);

        pmdConfigMemberService.del(pmdMember.getUserId());
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdMember record) {

        return pmdMemberMapper.updateByPrimaryKeySelective(record);
    }

   /* // 设定缴纳额度
    @Transactional
    public void setDuePay(int[] ids, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        for (int id : ids) {

            PmdMember pmdMember = pmdPayService.checkAdmin(id);
            SysUserView uv = pmdMember.getUser();

            if(pmdMember.getMonthId()!=currentMonthId){
                throw new OpException("{0}不允许设定往月额度。", uv.getRealname());
            }

            *//*if(pmdMember.getNormType()!= PmdConstants.PMD_MEMBER_NORM_TYPE_MODIFY){
                throw new OpException("{0}不允许设定额度。", uv.getRealname());
            }*//*
            if(pmdMember.getHasPay()){
                throw new OpException("{0}已缴费。", uv.getRealname());
            }
            if(pmdMember.getIsDelay()){
                throw new OpException("{0}已设置为延迟缴费。", uv.getRealname());
            }

            PmdMember record = new PmdMember();
            record.setId(id);
            record.setDuePay(amount);

            pmdMemberMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(id, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "设定缴纳额度：" + amount, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }

    }*/

    // 选择党员分类别
    @Transactional
    public void selectMemberType(int[] ids, Boolean hasSalary, byte configMemberType,
                                 int configMemberTypeId, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
        if(pmdConfigMemberType==null && pmdConfigMemberType.getType()!=configMemberType){
            throw new OpException("参数有误。");
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

        for (int id : ids) {

            PmdMember pmdMember = checkAdmin(id);
            SysUserView uv = pmdMember.getUser();
            int userId = uv.getUserId();

            if(pmdMember.getType()==PmdConstants.PMD_MEMBER_TYPE_STUDENT
                    && hasSalary ==null){
                throw new OpException("{0}是否带薪就读？", uv.getRealname());
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
            BigDecimal ltxf = null;
            Boolean isSalary = false; // 是否由工资计算党费
            Boolean isRetire = false; // 是否由离退休计算党费
            Boolean needSetSalary = false;
            if(setType==PmdConstants.PMD_NORM_SET_TYPE_FORMULA) {
                if (pmdNorm.getFormulaType() == PmdConstants.PMD_FORMULA_TYPE_RETIRE) {
                    isRetire = true;
                    ltxf = pmdExtService.getLtxf(uv.getCode());
                    amount = pmdExtService.getDuePayFromLtxf(ltxf);
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
                // 清空离退休费
                commonMapper.excuteSql(String.format("update pmd_config_member " +
                        "set retire_salary=null where user_id=%s", userId));
            }
            if(!isSalary){
                // 其他类别：清空工资项
                commonMapper.excuteSql(String.format("update pmd_config_member " +
                                "set gwgz=null,xjgz=null,gwjt=null,zwbt=null," +
                        "zwbt1=null,shbt=null,sbf=null,xlf=null, gzcx=null, " +
                        "shiyebx=null,yanglaobx=null,yiliaobx=null,gsbx=null,shengyubx=null, " +
                        "qynj=null,zynj=null,gjj=null where user_id=%s", userId));
            }

            {
                // 除了组织部管理员，其他人员不允许修改党员一级类别
                if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)
                        && configMemberType!=pmdConfigMember.getConfigMemberType()){
                    throw new OpException("{0}不允许修改党员类别。", uv.getRealname());
                }

                PmdConfigMember record = new PmdConfigMember();
                record.setUserId(userId);
                record.setConfigMemberType(configMemberType);
                record.setConfigMemberTypeId(configMemberTypeId);
                record.setDuePay(amount);
                record.setRetireSalary(ltxf);
                record.setHasSalary(hasSalary);
                record.setHasReset(true);

                pmdConfigMemberService.updateByPrimaryKeySelective(record);
            }


            PmdMember record = new PmdMember();
            record.setConfigMemberTypeId(configMemberTypeId);
            record.setConfigMemberTypeName(pmdConfigMemberType.getName());
            record.setConfigMemberTypeNormId(pmdConfigMemberType.getNormId());
            record.setConfigMemberTypeNormName(pmdConfigMemberType.getPmdNorm().getName());
            record.setSalary(ltxf);
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

            sysApprovalLogService.add(id, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "选择党员分类别：" + pmdConfigMemberType.getName() ,
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }
    }

    // 选择减免标准
    @Transactional
    public void selectReduceNorm(int[] ids, int normId, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        Integer normValueId = null;
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        if(pmdNorm==null && pmdNorm.getType()!=PmdConstants.PMD_NORM_TYPE_REDUCE){
            throw new OpException("参数有误。");
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

            sysApprovalLogService.add(id, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "设定" + PmdConstants.PMD_NORM_TYPE_MAP.get(pmdNorm.getType()) + "：" + pmdNorm.getName(),
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }

    }

    // 当月修改缴费方式 或 往月延迟缴费进行现金缴费
    @Transactional
    public void setIsOnlinePay(int[] ids, boolean isOnlinePay, String remark) {

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

            PmdMember _pmdMember = get(currentMonthId, userId);
            if(pmdMemberPayService.branchHasReport(_pmdMember, currentPmdMonth)){
                throw  new OpException("操作失败，{0}所在支部已报送。", realname);
            }

            boolean _isOnlinePay = pmdMember.getIsOnlinePay(); // 当前缴费方式
            if(_isOnlinePay==isOnlinePay) continue;

            String opStr = "现金缴费 -> 线上缴费";
            if(isOnlinePay && !_isOnlinePay){

                if(pmdMember.getMonthId() == currentMonthId && pmdMember.getIsDelay()==false) {
                    // 现金缴费 -> 线上缴费
                    pmdMonthService.addOrResetPmdMember(pmdMember.getUserId(), pmdMemberId);

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

                //PmdMember _pmdMember = get(currentMonthId, userId);
                int partyId = _pmdMember.getPartyId();
                Integer branchId = _pmdMember.getBranchId();

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
