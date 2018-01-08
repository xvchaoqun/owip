package service.pmd;

import domain.ext.ExtJzgSalary;
import domain.ext.ExtJzgSalaryExample;
import domain.ext.ExtRetireSalary;
import domain.ext.ExtRetireSalaryExample;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberExample;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdConfigMemberTypeExample;
import domain.pmd.PmdConfigReset;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import domain.sys.SysUserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.LogService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PmdConfigResetService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdNormService pmdNormService;
    @Autowired
    private  PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private LogService logService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    @CacheEvict(value = "PmdConfigMember", allEntries = true)
    public void reset(String salaryMonth) {

        {
            PmdConfigMember record = new PmdConfigMember();
            record.setHasReset(false);
            pmdConfigMemberMapper.updateByExampleSelective(record, new PmdConfigMemberExample());
        }

        // 同步月份工资到党员缴费分类列表中 （在职、校聘教职工）
        List<ExtJzgSalary> extJzgSalaries = iPmdMapper.extJzgSalaryList(salaryMonth);
        for (ExtJzgSalary ejs : extJzgSalaries) {

            String zgh = ejs.getZgh();
            SysUserView uv = sysUserService.findByCode(zgh);
            int userId = uv.getUserId();
            PmdConfigMember _pmdConfigMember = new PmdConfigMember();
            _pmdConfigMember.setUserId(userId);

            BigDecimal gwgz = ejs.getGwgz();
            BigDecimal xpgz = ejs.getXpgz();
            if (gwgz == null || (xpgz != null && gwgz.compareTo(xpgz) < 0))
                gwgz = xpgz;
            _pmdConfigMember.setGwgz(gwgz);

            _pmdConfigMember.setXjgz(ejs.getXjgz());
            _pmdConfigMember.setGwjt(ejs.getGwjt());
            _pmdConfigMember.setZwbt(ejs.getZwbt());
            _pmdConfigMember.setZwbt1(ejs.getZwbt1());
            _pmdConfigMember.setShbt(ejs.getShbt());
            _pmdConfigMember.setSbf(ejs.getSbf());
            _pmdConfigMember.setXlf(ejs.getXlf());

            BigDecimal gzcx = ejs.getGzcx();
            if (gzcx != null) {
                gzcx = gzcx.multiply(BigDecimal.valueOf(-1));
            }
            _pmdConfigMember.setGzcx(gzcx);

            _pmdConfigMember.setShiyebx(ejs.getSygr());
            _pmdConfigMember.setYanglaobx(ejs.getYanglaogr());
            _pmdConfigMember.setYiliaobx(ejs.getYiliaogr());
            _pmdConfigMember.setZynj(ejs.getNjgr());
            _pmdConfigMember.setGjj(ejs.getZfgjj());
            BigDecimal duePay = pmdExtService.calDuePay(_pmdConfigMember);
            _pmdConfigMember.setDuePay(duePay);
            _pmdConfigMember.setHasReset(true);

            pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);

            pmdConfigMemberService.updatePmdMemberDuePay(userId, duePay, "党费重新计算-更新党费计算工资");
        }
        // 同步离退休工资到党员缴费分类列表中 （离退休教职工）
        List<ExtRetireSalary> extRetireSalaries = iPmdMapper.extRetireSalaryList(salaryMonth);
        for (ExtRetireSalary ers : extRetireSalaries) {

            BigDecimal ltxf = ers.getLtxf();
            if (ltxf != null && ltxf.compareTo(BigDecimal.valueOf(0)) > 0) {
                String zgh = ers.getZgh();
                SysUserView uv = sysUserService.findByCode(zgh);
                int userId = uv.getUserId();
                PmdConfigMember _pmdConfigMember = new PmdConfigMember();
                _pmdConfigMember.setUserId(userId);
                _pmdConfigMember.setRetireSalary(ltxf);
                BigDecimal duePay = pmdExtService.getDuePayFromLtxf(ltxf);
                _pmdConfigMember.setDuePay(duePay);
                _pmdConfigMember.setHasReset(true);

                pmdConfigMemberMapper.updateByPrimaryKeySelective(_pmdConfigMember);

                pmdConfigMemberService.updatePmdMemberDuePay(userId, duePay, "党费重新计算-更新党费计算工资");

            }
        }

        {
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();

            // 支部自行设定的额度，需要进行确认，（清除本月已设定的，但未缴费的记录的缴费额度）
            List<Integer> configMemberTypeIdList = new ArrayList<>();
            List<PmdNorm> pmdNormList = pmdNormService.list(SystemConstants.PMD_NORM_TYPE_PAY,
                    SystemConstants.PMD_NORM_SET_TYPE_SET);
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
            example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId())
                    .andConfigMemberTypeIdIn(configMemberTypeIdList)
                    .andHasPayEqualTo(false);
            List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);
            for (PmdMember pmdMember : pmdMembers) {
                pmdConfigMemberService.clearPmdMemberDuePay(pmdMember.getUserId());
            }
        }

        PmdConfigReset record = new PmdConfigReset();
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setSalaryMonth(DateUtils.parseDate(salaryMonth, "yyyyMM"));
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
