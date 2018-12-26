package service.pmd;

import controller.global.OpException;
import domain.pmd.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.LogService;
import service.sys.SysApprovalLogService;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;

@Service
public class PmdConfigMemberService extends PmdBaseMapper {

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

    // 判断是否允许设置离退休费
    public boolean canSetLtxf(int userId){

        PmdConfigMember pmdConfigMember = getPmdConfigMember(userId);
        if(pmdConfigMember!=null) {

            if(pmdConfigMember.getConfigMemberType()!=PmdConstants.PMD_MEMBER_TYPE_RETIRE){
                return false;
            }

            Integer configMemberTypeId = pmdConfigMember.getConfigMemberTypeId();

            if (configMemberTypeId != null) {

                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                if(pmdConfigMemberType!=null) {

                    PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

                    return (pmdNorm!=null && pmdNorm.getFormulaType() == PmdConstants.PMD_FORMULA_TYPE_RETIRE);
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

        logger.info(logService.log(LogConstants.LOG_PMD, MessageFormat.format("修改党费计算工资,修改前：{0}，修改后：{1}",
                JSONUtils.toString(pmdConfigMember, false),
                JSONUtils.toString(getPmdConfigMember(userId), false))));
    }

    // 修改离退休费
    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#userId")
    public void setLtxf(int userId, BigDecimal retireSalary) {

        if(!canSetLtxf(userId)){
            throw new OpException("不允许设置离退休费");
        }

        PmdConfigMember pmdConfigMember = getPmdConfigMember(userId);

        PmdConfigMember record = new PmdConfigMember();
        record.setUserId(userId);
        record.setMobile(pmdConfigMember.getMobile());
        record.setConfigMemberType(pmdConfigMember.getConfigMemberType());
        record.setConfigMemberTypeId(pmdConfigMember.getConfigMemberTypeId());
        record.setIsOnlinePay(true);
        record.setRetireSalary(retireSalary);
        BigDecimal duePay = pmdExtService.getDuePayFromLtxf(retireSalary);
        record.setDuePay(duePay);
        record.setHasReset(true);

        // 覆盖
        pmdConfigMemberMapper.updateByPrimaryKey(record);

        // 更新当前缴费月份数据（未缴费前）
        updatePmdMemberDuePay(userId, duePay, MessageFormat.format("修改离退休费,{0}->{1}",
                pmdConfigMember.getRetireSalary(), retireSalary));

        logger.info(logService.log(LogConstants.LOG_PMD, MessageFormat.format("修改离退休费, 修改前：{0}，修改后：{1}",
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
                        "更新缴费额度", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        remark);
            }
        }
    }

    // 重置支部设定的额度，需要支部再次确认
    public void clearPmdMemberDuePay(int userId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null) {
            PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), userId);
            if (pmdMember != null && pmdMember.getConfigMemberTypeId()!=null && !pmdMember.getHasPay()) {

                commonMapper.excuteSql("update pmd_member set due_pay=null where id="+ pmdMember.getId());

                commonMapper.excuteSql("update pmd_config_member set has_reset=0 where user_id="+ pmdMember.getUserId());

                sysApprovalLogService.add(pmdMember.getId(), userId,
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        "待支部确认额度", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        null);
            }
        }
    }
}
