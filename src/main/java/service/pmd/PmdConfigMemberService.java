package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.LogService;
import service.sys.SysApprovalLogService;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;

@Service
public class PmdConfigMemberService extends BaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdConfigMemberTypeService pmdConfigMemberTypeService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private LogService logService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#record.userId")
    public void insertSelective(PmdConfigMember record) {

        pmdConfigMemberMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#userId")
    public void del(int userId) {

        pmdConfigMemberMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#record.userId")
    public int updateByPrimaryKeySelective(PmdConfigMember record) {

        return pmdConfigMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "PmdConfigMember", key = "#userId")
    public PmdConfigMember getPmdConfigMember(int userId) {

        return pmdConfigMemberMapper.selectByPrimaryKey(userId);
    }

    // 判断是否允许设置工资
    public boolean canSetSalary(int userId){

        PmdConfigMember pmdConfigMember = getPmdConfigMember(userId);
        if(pmdConfigMember!=null) {
            Integer configMemberTypeId = pmdConfigMember.getConfigMemberTypeId();
            if (configMemberTypeId != null) {

                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                if(pmdConfigMemberType!=null) {

                    PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

                    return (pmdNorm!=null && pmdNorm.getSetType() == PmdConstants.PMD_NORM_SET_TYPE_FORMULA
                            && pmdNorm.getFormulaType() != PmdConstants.PMD_FORMULA_TYPE_RETIRE);
                }
            }
        }

        return false;
    }

    // 计算应缴党费额度
    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#record.userId")
    public void setSalary(PmdConfigMember record, boolean isSelf) {

        int userId = record.getUserId();
        if(!canSetSalary(userId)){
            throw new OpException("不允许设置工资");
        }

        PmdConfigMember pmdConfigMember = getPmdConfigMember(userId);
        /*if(BooleanUtils.isTrue(pmdConfigMember.getIsSelfSetSalary()) && !isSelf){
            throw new OpException("本人已经设置过了，不允许重复设置。");
        }*/

        //record.setIsSelfSetSalary(isSelf);
        record.setUserId(userId);
        record.setConfigMemberType(null);
        record.setConfigMemberTypeId(null);
        BigDecimal duePay = pmdExtService.calDuePay(record);
        record.setDuePay(duePay);
        //record.setHasSetSalary(true);
        record.setHasReset(true);

        pmdConfigMemberMapper.updateByPrimaryKeySelective(record);

        // 更新当前缴费月份数据（未缴费前）
        updatePmdMemberDuePay(userId, duePay, "修改党费计算工资");
        /*PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), userId);
            if (pmdMember != null && !pmdMember.getHasPay()) {

                PmdMember _pmdMember = new PmdMember();
                _pmdMember.setId(pmdMember.getId());
                _pmdMember.setConfigMemberDuePay(duePay);
                _pmdMember.setNeedSetSalary(false);
                _pmdMember.setDuePay(duePay);

                pmdMemberMapper.updateByPrimaryKeySelective(_pmdMember);

                sysApprovalLogService.add(pmdMember.getId(), userId,
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        "修改党费计算工资", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        JSONUtils.toString(pmdConfigMember, false));
            }
        }*/

        logger.info(logService.log(SystemConstants.LOG_PMD, MessageFormat.format("修改党费计算工资,修改前：{0}，修改后：{1}",
                JSONUtils.toString(pmdConfigMember, false),
                JSONUtils.toString(getPmdConfigMember(userId), false))));
    }

    // 更新当前缴费月份应缴党费（未缴费前且已经分类）
    public void updatePmdMemberDuePay(int userId, BigDecimal duePay, String remark) {

        if (duePay == null) return;

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), userId);
            if (pmdMember != null && pmdMember.getConfigMemberTypeId()!=null && !pmdMember.getHasPay()) {

                PmdMember _pmdMember = new PmdMember();
                _pmdMember.setId(pmdMember.getId());
                _pmdMember.setConfigMemberDuePay(duePay);
                _pmdMember.setNeedSetSalary(false);
                _pmdMember.setDuePay(duePay);

                pmdMemberMapper.updateByPrimaryKeySelective(_pmdMember);

                sysApprovalLogService.add(pmdMember.getId(), userId,
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        remark, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        null);
            }
        }
    }

    // 重置支部设定的额度，需要支部再次确认
    public void clearPmdMemberDuePay(int userId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), userId);
            if (pmdMember != null && pmdMember.getConfigMemberTypeId()!=null && !pmdMember.getHasPay()) {

                commonMapper.excuteSql("update pmd_member set due_pay =null where id="+ pmdMember.getId());

                sysApprovalLogService.add(pmdMember.getId(), userId,
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        "待支部确认额度", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        null);
            }
        }
    }
}
