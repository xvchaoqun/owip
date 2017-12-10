package servcie;

import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CommonMapper;
import persistence.common.IPmdMapper;
import persistence.pmd.PmdBranchMapper;
import persistence.pmd.PmdMemberMapper;
import persistence.pmd.PmdNormMapper;
import persistence.pmd.PmdPartyMapper;
import service.pmd.PmdBranchService;
import service.pmd.PmdMonthService;
import service.pmd.PmdPartyService;
import service.pmd.PmdPayService;
import service.pmd.PmdPayWszfService;
import sys.utils.DateUtils;

import java.util.List;

/**
 * Created by lm on 2018/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PmdTest {

    @Autowired
    PmdMonthService pmdMonthService;
    @Autowired
    PmdBranchService pmdBranchService;
    @Autowired
    PmdBranchMapper pmdBranchMapper;
    @Autowired
    PmdPartyService pmdPartyService;
    @Autowired
    PmdPartyMapper pmdPartyMapper;
    @Autowired
    PmdPayService pmdPayService;
    @Autowired
    PmdPayWszfService pmdWszfService;
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    PmdMemberMapper pmdMemberMapper;
    @Autowired
    PmdNormMapper pmdNormMapper;
    @Autowired
    IPmdMapper iPmdMapper;

    public long notPayCount(int monthId) {

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andHasPayEqualTo(false).andIsDelayEqualTo(false);
        return pmdMemberMapper.countByExample(example);
    }

   /* @Test
    public void tt(){

        PmdExcelReportBean pmdExcelReportBean = iPmdMapper.getPmdExcelReportBean(2, 17, 0);
        System.out.println(pmdExcelReportBean);
    }*/
    /*@Test
    public void pmdExport() throws IOException {

        FileOutputStream output = new FileOutputStream(new File("D:/tmp/党费报表.xlsx"));
        XSSFWorkbook wb = pmdPartyService.export(1);
        wb.write(output);
        output.close();
    }*/

    // 设定额度
    @Test
    public void step0(){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int normId = 1;
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        // 选择标准
        String setNorm = "update pmd_member pm set pm.due_pay= format(rand()*100,2 ), norm_id="+normId
                + ",norm_display_name='" + pmdNorm.getName() + "', has_salary=floor((rand()*100)<10) " +
                "where pm.has_pay=0 and pm.is_delay=0 and pm.norm_type=2 and " +
                "(pm.due_pay is null or pm.due_pay<=0.01) and pm.month_id=" + currentPmdMonth.getId();

        commonMapper.excuteSql(setNorm);

        // 可直接编辑额度
        String setDuePay = "update pmd_member pm set pm.due_pay= format(rand()*100,2 )" +
                "where pm.has_pay=0 and pm.is_delay=0 and pm.norm_type=1 and " +
                "(pm.due_pay is null or pm.due_pay<=0.01) and pm.month_id=" + currentPmdMonth.getId();

        commonMapper.excuteSql(setDuePay);
    }

    // 缴费
    @Test
    public void step1() {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        String orderNoPrefix = DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMMdd");
        // 延迟缴费
        String delaySql = "update pmd_member pm set pm.is_delay=1, pm.delay_reason='测试延迟缴费' " +
                "where pm.has_pay=0 and pm.is_delay=0 and pm.month_id=" + currentPmdMonth.getId() + " order by rand() limit 50";

        // 线上缴费
        String onlineSql = "update pmd_member pm, pmd_member_pay pmp set " +
                "pmp.order_no=concat('" + orderNoPrefix + "', pm.id+" + pmdPayService.orderNoOffset + "), " +
                "pm.has_pay=1 , pm.real_pay=pm.due_pay, pm.is_online_pay=1, " +
                "pmp.has_pay=1 , pmp.real_pay=pm.due_pay, pmp.is_online_pay=1, " +
                "pmp.charge_party_id=pm.party_id , pmp.charge_branch_id=pm.branch_id, " +
                "pmp.pay_month_id=pm.month_id, pm.pay_time=now(), pmp.pay_time=now() " +
                "where pmp.member_id=pm.id and pm.has_pay=0 and pm.is_delay=0 and pm.month_id=" + currentPmdMonth.getId()
                + " and pm.id in(select id from (select id from pmd_member order by rand() limit 200) t)";

        // 现金缴费
        String cashSql = "update pmd_member pm, pmd_member_pay pmp set " +
                "pm.has_pay=1, pm.real_pay=pm.due_pay, pm.is_online_pay=0, " +
                "pmp.has_pay=1 , pmp.real_pay=pm.due_pay, pmp.is_online_pay=0, " +
                "pmp.pay_month_id=pm.month_id, pm.pay_time=now(), pmp.pay_time=now() " +
                "where pmp.member_id=pm.id and pm.has_pay=0 " +
                "and pm.is_delay=0 and pm.month_id=" + currentPmdMonth.getId()
                + " and pm.id in(select id from (select id from pmd_member order by rand() limit 50) t)";


        /*// 线上补缴
        String onlineDelaySql = "update pmd_member set " +
                "order_no=concat('"+orderNoPrefix+"', id+"+ pmdPayService.orderNoOffset +"), " +
                "has_pay=1 , real_pay=due_pay , is_online_pay=1, pay_month_id="+currentPmdMonth.getId() + ", pay_time=now() " +
                "where has_pay=0 and is_delay=1 and month_id<"+currentPmdMonth.getId() + " order by rand() limit 10";

        // 现金补缴
        String cashDelaySql = "update pmd_member set has_pay=1, real_pay=due_pay, is_online_pay=0, " +
                "pay_month_id="+currentPmdMonth.getId()+", pay_time=now() " +
                "where has_pay=0 and is_delay=1 and month_id<"+currentPmdMonth.getId() + " order by rand() limit 10";*/

        while (notPayCount(currentPmdMonth.getId()) > 0) {

            int rand = RandomUtils.nextInt(20);
            if (rand < 5) {
                commonMapper.excuteSql(delaySql);
            } else if (rand < 15) {
                commonMapper.excuteSql(onlineSql);
            } else
                commonMapper.excuteSql(cashSql);
        }

        /*int payDelayCount = 200;
        for (int i = 0; i <=payDelayCount; i++) {

            int rand = RandomUtils.nextInt(20);
            if(rand<10) {
                commonMapper.excuteSql(onlineDelaySql);
                i += 10;
            }else{
                commonMapper.excuteSql(cashDelaySql);
                i += 10;
            }
        }*/
    }

    // 报送所有支部
    @Test
    public void step2() {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId()).andHasReportEqualTo(false);
        List<PmdBranch> pmdBranches = pmdBranchMapper.selectByExample(example);

        for (PmdBranch pmdBranch : pmdBranches) {
            pmdBranchService.report(pmdBranch.getId());
        }

    }

    // 报送所有分党委
    @Test
    public void step3() {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId()).andHasReportEqualTo(false);
        List<PmdParty> pmdParties = pmdPartyMapper.selectByExample(example);

        for (PmdParty pmdParty : pmdParties) {
            pmdPartyService.report(pmdParty.getId());
        }

    }
}
