package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import domain.pmd.PmdNormValue;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdMemberService extends BaseMapper {

    @Autowired
    private PmdPayService pmdPayService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

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

    @Transactional
    public void del(Integer id) {

        pmdMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdMember record) {

        return pmdMemberMapper.updateByPrimaryKeySelective(record);
    }

    // 设定缴纳额度
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

            if(pmdMember.getNormType()!= SystemConstants.PMD_MEMBER_NORM_TYPE_MODIFY){
                throw new OpException("{0}不允许设定额度。", uv.getRealname());
            }
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

    }

    // 选择缴纳/减免标准
    @Transactional
    public void selectNorm(int[] ids, Boolean hasSalary, int normId, BigDecimal amount, String remark) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        Integer normValueId = null;
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        if(pmdNorm==null){
            throw new OpException("参数有误。");
        }
        if(pmdNorm.getSetType()==SystemConstants.PMD_NORM_SET_TYPE_FIXED){
            PmdNormValue pmdNormValue = pmdNorm.getPmdNormValue();
            normValueId = pmdNormValue.getId();
            amount = pmdNormValue.getAmount();
        }
        if(pmdNorm.getSetType() == SystemConstants.PMD_NORM_SET_TYPE_SET){
            if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
                throw new OpException("额度必须大于0");
            }
        }

        // 免交
        boolean isFree = (pmdNorm.getSetType()==SystemConstants.PMD_NORM_SET_TYPE_FREE);
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        for (int id : ids) {

            PmdMember pmdMember = pmdPayService.checkAdmin(id);
            SysUserView uv = pmdMember.getUser();

            if(pmdMember.getType()==SystemConstants.PMD_MEMBER_TYPE_STUDENT
                    && hasSalary ==null){
                throw new OpException("{0}是否带薪就读？", uv.getRealname());
            }

            if(pmdMember.getMonthId()!=currentMonthId){
                throw new OpException("{0}不允许对往月数据选择缴纳标准。", uv.getRealname());
            }

            if(pmdMember.getHasPay()){
                throw new OpException("{0}已缴费。", uv.getRealname());
            }
            if(pmdMember.getIsDelay()){
                throw new OpException("{0}已设置为延迟缴费。", uv.getRealname());
            }

            if(pmdNorm.getType() == SystemConstants.PMD_NORM_TYPE_PAY
                    && pmdMember.getNormType()!= SystemConstants.PMD_MEMBER_NORM_TYPE_SELECT){
                throw new OpException("{0}不允许选择缴纳标准。", uv.getRealname());
            }

            PmdMember record = new PmdMember();
            record.setNormId(normId);
            record.setNormDisplayName(pmdNorm.getName());
            record.setNormValueId(normValueId);
            record.setDuePay(amount);
            // 只针对学生党员
            record.setHasSalary(hasSalary);

            // 免交
            if(isFree){
                record.setDuePay(BigDecimal.ZERO);
                record.setRealPay(BigDecimal.ZERO);
                record.setHasPay(true);
                record.setIsOnlinePay(false);
                record.setPayTime(new Date());
                record.setChargeUserId(currentUserId);

                // 同步实缴数据
                PmdMemberPay _record = new PmdMemberPay();
                _record.setMemberId(id);
                _record.setHasPay(true);
                _record.setRealPay(BigDecimal.ZERO);
                _record.setIsOnlinePay(false);
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
                throw new OpException("{0}选择缴纳标准失败", uv.getRealname());
            }

            sysApprovalLogService.add(id, uv.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "设定" + SystemConstants.PMD_NORM_TYPE_MAP.get(pmdNorm.getType()) + "：" + pmdNorm.getName(),
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }

    }
}
