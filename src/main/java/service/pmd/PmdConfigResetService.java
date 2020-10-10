package service.pmd;

import controller.global.OpException;
import ext.domain.ExtJzgSalary;
import ext.domain.ExtJzgSalaryExample;
import ext.domain.ExtRetireSalary;
import ext.domain.ExtRetireSalaryExample;
import domain.member.Member;
import domain.member.MemberExample;
import domain.pmd.*;
import domain.sys.SysUserView;
import ext.service.PmdExtService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.PmdConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PmdConfigResetService extends PmdBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdNormService pmdNormService;
    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 得到计算缴费工资的月份
    public Date getSalaryMonth() {

        PmdConfigResetExample example = new PmdConfigResetExample();
        example.setOrderByClause("id desc");
        List<PmdConfigReset> pmdConfigResets = pmdConfigResetMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return pmdConfigResets.size() > 0 ? pmdConfigResets.get(0).getSalaryMonth() : null;
    }

    // 党费重新计算-更新党费计算工资（在职教职工）
    @Transactional
    public void updateDuePayByJzgSalary(ExtJzgSalary ejs) {

        if (ejs == null) return;

        String zgh = ejs.getZgh();
        SysUserView uv = sysUserService.findByCode(zgh);
        int userId = uv.getId();

        // 先检查一下是否已经设置为别的类别，如果是则不更新工资（即非在职、校聘，比如学生助理）
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        if (pmdConfigMember != null) {
            PmdConfigMemberType pmdConfigMemberType = pmdConfigMember.getPmdConfigMemberType();
            if (pmdConfigMemberType != null) {
                PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();
                Byte formulaType = pmdNorm.getFormulaType();
                // 额度类型为公式，且是在职和校聘的，才允许计算工资，否则跳出工资计算
                if (false == (formulaType != null
                        && (formulaType == PmdConstants.PMD_FORMULA_TYPE_ONJOB
                        || formulaType == PmdConstants.PMD_FORMULA_TYPE_EXTERNAL))) {

                    if (pmdNorm.getSetType() == PmdConstants.PMD_NORM_SET_TYPE_FIXED) {
                        // 如果是固定额度的，则更新为已设置额度
                        PmdConfigMember _pmdConfigMember = new PmdConfigMember();
                        _pmdConfigMember.setUserId(userId);
                        _pmdConfigMember.setHasReset(true);
                        pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);
                    }

                    return;
                }
            }
        }


        PmdConfigMember _pmdConfigMember = new PmdConfigMember();
        _pmdConfigMember.setUserId(userId);

        _pmdConfigMember.setSalary(pmdExtService.getSalaryJSON(ejs));
        BigDecimal duePay = pmdExtService.calDuePay(userId, pmdConfigMember.getSalary());
        _pmdConfigMember.setDuePay(duePay);
        _pmdConfigMember.setHasReset(true);

        pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);

        pmdConfigMemberService.updatePmdMemberDuePay(userId, duePay, "党费重新计算-更新党费计算工资");
    }

    // 党费重新计算-更新党费计算工资（离退休）
    @Transactional
    public void updateDuePayByRetireSalary(ExtRetireSalary ers) {

        if (ers == null) return;

        BigDecimal base = ers.getBase();
        if (base != null && base.compareTo(BigDecimal.valueOf(0)) > 0) {
            String zgh = ers.getZgh();
            SysUserView uv = sysUserService.findByCode(zgh);
            int userId = uv.getId();

            // 先检查一下是否已经设置为别的类别，如果是则不更新工资（即非离退休，比如学生助理）
            PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
            if (pmdConfigMember != null) {
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMember.getPmdConfigMemberType();
                if (pmdConfigMemberType != null) {
                    PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();
                    Byte formulaType = pmdNorm.getFormulaType();

                    // 额度类型为公式，且是离退的，才允许计算工资，否则跳出工资计算
                    if (false == (formulaType != null
                            && formulaType == PmdConstants.PMD_FORMULA_TYPE_RETIRE)) {

                        if (pmdNorm.getSetType() == PmdConstants.PMD_NORM_SET_TYPE_FIXED) {
                            // 如果是固定额度的，则更新为已设置额度
                            PmdConfigMember _pmdConfigMember = new PmdConfigMember();
                            _pmdConfigMember.setUserId(userId);
                            _pmdConfigMember.setHasReset(true);
                            pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);
                        }
                        return;
                    }
                }
            }

            PmdConfigMember _pmdConfigMember = new PmdConfigMember();
            _pmdConfigMember.setUserId(userId);
            _pmdConfigMember.setRetireSalary(base);
            BigDecimal duePay = pmdExtService.getDuePayFromRetireBase(base);
            _pmdConfigMember.setDuePay(duePay);
            _pmdConfigMember.setHasReset(true);

            pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);

            pmdConfigMemberService.updatePmdMemberDuePay(userId, duePay, "党费重新计算-更新党费计算工资");

        }
    }

    // 重置党员缴费基本信息，只针对线上缴费，缴费方式已设置为现金缴费的不处理。
    @Transactional
    @CacheEvict(value = "PmdConfigMember", allEntries = true)
    public void reset(String salaryMonth, boolean reset, Integer partyId, Integer branchId, Integer userId) {

        // 影响党员范围
        Set<Integer> limitedUserIdSet = new HashSet<>();
        if(partyId!=null || branchId!=null || userId!=null) {
            MemberExample example = new MemberExample();
            MemberExample.Criteria criteria = example.createCriteria()
                    .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
            if (partyId != null) {
                criteria.andPartyIdEqualTo(partyId);
            }
            if (branchId != null) {
                criteria.andBranchIdEqualTo(branchId);
            }
            if (userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
            List<Member> members = memberMapper.selectByExample(example);
            if (members.size() > 0) {
                for (Member member : members) {
                    limitedUserIdSet.add(member.getUserId());
                }
            }
        }
        // 如果没有设定任何党员范围，则在全校范围进行更新操作，如果设定了则在指定的缴费党员中进行。
        boolean limited = (limitedUserIdSet.size()>0);

        if(!limited && (partyId!=null || branchId!=null || userId!=null)) {
            throw new OpException("所选范围不存在需要调整的缴费党员。");
        }

        // 同步月份工资到党员缴费分类列表中 （在职、校聘教职工）
        List<ExtJzgSalary> extJzgSalaries = iPmdMapper.extJzgSalaryList(salaryMonth);
        for (ExtJzgSalary ejs : extJzgSalaries) {
            if(!limited || limitedUserIdSet.contains(sysUserService.findByCode(ejs.getZgh()).getId())){
                updateDuePayByJzgSalary(ejs);
            }
        }
        // 同步离退休工资到党员缴费分类列表中 （离退休教职工）
        List<ExtRetireSalary> extRetireSalaries = iPmdMapper.extRetireSalaryList(salaryMonth);
        for (ExtRetireSalary ers : extRetireSalaries) {

            if(!limited || limitedUserIdSet.contains(sysUserService.findByCode(ers.getZgh()).getId())){
                updateDuePayByRetireSalary(ers);
            }
        }

        if (reset) {
            // 支部自行设定的额度，需要进行确认，（清除本月已设定的，但未缴费、未延迟缴费的记录的缴费额度）
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            if (currentPmdMonth != null) {

                List<Integer> configMemberTypeIdList = new ArrayList<>();
                List<PmdNorm> pmdNormList = pmdNormService.list(PmdConstants.PMD_NORM_TYPE_PAY,
                        PmdConstants.PMD_NORM_SET_TYPE_SET);
                for (PmdNorm pmdNorm : pmdNormList) {
                    int normId = pmdNorm.getId();
                    PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
                    example.createCriteria().andNormIdEqualTo(normId);
                    List<PmdConfigMemberType> pmdConfigMemberTypes = pmdConfigMemberTypeMapper.selectByExample(example);
                    for (PmdConfigMemberType pmdConfigMemberType : pmdConfigMemberTypes) {
                        configMemberTypeIdList.add(pmdConfigMemberType.getId());
                    }
                }

                PmdMemberExample example = new PmdMemberExample();
                PmdMemberExample.Criteria criteria = example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId())
                        .andConfigMemberTypeIdIn(configMemberTypeIdList)
                        .andHasPayEqualTo(false)
                        .andIsDelayEqualTo(false)
                        .andIsOnlinePayEqualTo(true);// 只针对线上缴费
                if(limited){
                    criteria.andUserIdIn(new ArrayList<>(limitedUserIdSet));
                }

                List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);
                for (PmdMember pmdMember : pmdMembers) {
                    pmdConfigMemberService.clearPmdMemberDuePay(pmdMember.getUserId());
                }
            }
        }

        PmdConfigReset record = new PmdConfigReset();
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setReset(reset);
        record.setSalaryMonth(DateUtils.parseDate(salaryMonth, "yyyyMM"));
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setLimitedUserId(userId);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());

        pmdConfigResetMapper.insertSelective(record);
    }

    // salaryMonth格式: yyyyMM
    public ExtJzgSalary getExtJzgSalary(String zgh, String salaryMonth) {

        ExtJzgSalaryExample example = new ExtJzgSalaryExample();
        example.createCriteria().andZghEqualTo(zgh).andRqEqualTo(salaryMonth);
        List<ExtJzgSalary> extJzgSalaries = extJzgSalaryMapper.selectByExample(example);
        if (extJzgSalaries.size() > 0) return extJzgSalaries.get(0);
        return null;
    }

    // salaryMonth格式: yyyyMM
    public ExtRetireSalary getExtRetireSalary(String zgh, String salaryMonth) {

        ExtRetireSalaryExample example = new ExtRetireSalaryExample();
        example.createCriteria().andZghEqualTo(zgh).andRqEqualTo(salaryMonth);
        List<ExtRetireSalary> extRetireSalaries = extRetireSalaryMapper.selectByExample(example);
        if (extRetireSalaries.size() > 0) return extRetireSalaries.get(0);
        return null;
    }

}
