package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

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

                    return (pmdNorm!=null && pmdNorm.getSetType() == SystemConstants.PMD_NORM_SET_TYPE_FORMULA
                            && pmdNorm.getFormulaType() != SystemConstants.PMD_FORMULA_TYPE_RETIRE);
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
        if(BooleanUtils.isTrue(pmdConfigMember.getIsSelfSetSalary()) && !isSelf){
            throw new OpException("本人已经设置过了，不允许重复设置。");
        }

        record.setIsSelfSetSalary(isSelf);
        record.setUserId(userId);
        record.setConfigMemberType(null);
        record.setConfigMemberTypeId(null);
        BigDecimal duePay = calDuePay(record);
        record.setDuePay(duePay);
        record.setHasSetSalary(true);

        pmdConfigMemberMapper.updateByPrimaryKeySelective(record);

        // 更新当前缴费月份数据（未缴费前）
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
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
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        "修改应缴党费额度", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        JSONUtils.toString(pmdConfigMember, false));
            }
        }

        logger.info(logService.log(SystemConstants.LOG_PMD, MessageFormat.format("修改应缴党费额度,修改前：{0}，修改后：{1}",
                JSONUtils.toString(pmdConfigMember, false),
                JSONUtils.toString(getPmdConfigMember(userId), false))));
    }

    // 根据工资计算党费
    public BigDecimal calDuePay(PmdConfigMember record) {

        BigDecimal gwgz = NumberUtils.trimToZero(record.getGwgz());
        BigDecimal xjgz = NumberUtils.trimToZero(record.getXjgz());
        BigDecimal gwjt = NumberUtils.trimToZero(record.getGwjt());
        BigDecimal zwbt = NumberUtils.trimToZero(record.getZwbt());
        BigDecimal zwbt1 = NumberUtils.trimToZero(record.getZwbt1());
        BigDecimal shbt = NumberUtils.trimToZero(record.getShbt());
        BigDecimal sbf = NumberUtils.trimToZero(record.getSbf());
        BigDecimal xlf = NumberUtils.trimToZero(record.getXlf());
        BigDecimal gzcx = NumberUtils.trimToZero(record.getGzcx());
        BigDecimal shiyebx = NumberUtils.trimToZero(record.getShiyebx());
        BigDecimal yanglaobx = NumberUtils.trimToZero(record.getYanglaobx());
        BigDecimal yiliaobx = NumberUtils.trimToZero(record.getYiliaobx());
        BigDecimal gsbx = NumberUtils.trimToZero(record.getGsbx());
        BigDecimal shengyubx = NumberUtils.trimToZero(record.getShengyubx());
        BigDecimal qynj = NumberUtils.trimToZero(record.getQynj());
        BigDecimal zynj = NumberUtils.trimToZero(record.getZynj());
        BigDecimal gjj = NumberUtils.trimToZero(record.getGjj());

        // 前几项合计
        BigDecimal total = gwgz.add(xjgz).add(gwjt).add(zwbt).add(zwbt1).add(shbt).add(sbf).add(xlf)
                .subtract(gzcx).subtract(shiyebx).subtract(yanglaobx)
                .subtract(yiliaobx).subtract(gsbx).subtract(shengyubx).subtract(qynj)
                .subtract(zynj).subtract(gjj);
        // 扣除3500的计税基数
        BigDecimal base = total.subtract(BigDecimal.valueOf(3500));
        if (base.compareTo(BigDecimal.ZERO) < 0) {
            base = BigDecimal.ZERO;
        }
        /*=IF(AND(U3>0,U3<=1500),U3*0.03,
                IF(AND(U3>1500,U3<=4500), (U3-1500)*0.1+45,
                        IF(AND(U3>4500,U3<=9000),(U3-4500)*0.2+345,
                                IF(AND(U3>9000,U3<=35000),(U3-9000)*0.25+1245,
                                        IF(AND(U3>35000,U3<=55000),(U3-35000)*0.3+7745,
                                                IF(AND(U3>55000,U3<=80000),(U3-55000)*0.35+13745,
                                                        IF(AND(U3>80000),(U3-80000)*0.45+22495)))))))*/
        // 税金
        BigDecimal tax = BigDecimal.ZERO;
        if (base.compareTo(BigDecimal.ZERO) > 0 && base.compareTo(BigDecimal.valueOf(1500)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.03));
        } else if (base.compareTo(BigDecimal.valueOf(1500)) > 0 && base.compareTo(BigDecimal.valueOf(4500)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(1500))).multiply(BigDecimal.valueOf(0.1));
            tax = tax.add(BigDecimal.valueOf(45));
        } else if (base.compareTo(BigDecimal.valueOf(4500)) > 0 && base.compareTo(BigDecimal.valueOf(9000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(4500))).multiply(BigDecimal.valueOf(0.2));
            tax = tax.add(BigDecimal.valueOf(345));
        } else if (base.compareTo(BigDecimal.valueOf(9000)) > 0 && base.compareTo(BigDecimal.valueOf(35000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(9000))).multiply(BigDecimal.valueOf(0.25));
            tax = tax.add(BigDecimal.valueOf(1245));
        } else if (base.compareTo(BigDecimal.valueOf(35000)) > 0 && base.compareTo(BigDecimal.valueOf(55000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(35000))).multiply(BigDecimal.valueOf(0.3));
            tax = tax.add(BigDecimal.valueOf(7745));
        } else if (base.compareTo(BigDecimal.valueOf(55000)) > 0 && base.compareTo(BigDecimal.valueOf(80000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(55000))).multiply(BigDecimal.valueOf(0.35));
            tax = tax.add(BigDecimal.valueOf(13745));
        } else if (base.compareTo(BigDecimal.valueOf(80000)) > 0) {
            tax = (base.subtract(BigDecimal.valueOf(80000))).multiply(BigDecimal.valueOf(0.45));
            tax = tax.add(BigDecimal.valueOf(22495));
        }

        // 党费基数
        BigDecimal partyBase = total.subtract(tax);

        // 计算应交党费 =W3*IF(W3<=3000,0.5%,IF(W3<=5000,1%,IF(W3<=10000,1.5%,2%)))
        BigDecimal rate = BigDecimal.ZERO;
        if (partyBase.compareTo(BigDecimal.valueOf(3000)) <= 0) {
            rate = BigDecimal.valueOf(0.005);
        } else if (partyBase.compareTo(BigDecimal.valueOf(5000)) <= 0) {
            rate = BigDecimal.valueOf(0.01);
        } else if (partyBase.compareTo(BigDecimal.valueOf(10000)) <= 0) {
            rate = BigDecimal.valueOf(0.015);
        } else {
            rate = BigDecimal.valueOf(0.02);
        }

        return partyBase.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
