package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pmd.common.PmdReportBean;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdPartyService extends BaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdPayService pmdPayService;
    @Autowired
    private PmdBranchService pmdBranchService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;

    // 党委报送
    @Transactional
    public void report(int id) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        if(pmdParty==null) return;

        int monthId = pmdParty.getMonthId();
        int partyId = pmdParty.getPartyId();

        /*List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
        Set<Integer> adminPartyIdSet = new HashSet<>();
        adminPartyIdSet.addAll(adminPartyIds);
        if(!adminPartyIdSet.contains(partyId)){
            throw new UnauthorizedException();
        }*/

        if(!canReport(monthId, partyId)){
            throw new OpException("当前不允许报送。");
        }
        PmdParty record = new PmdParty();
        record.setHasReport(true);
        record.setReportUserId(ShiroHelper.getCurrentUserId());
        record.setReportTime(new Date());
        //record.setReportIp(ContextHelper.getRealIp());

        // 保存数据汇总
        PmdReportBean r = null;
        // 如果是直属党支部
        if(CmTag.isDirectBranch(partyId)){
            // 党员总数和本月应交党费数已经在启动缴费月份时保存
            /*record.setMemberCount(null);
            record.setDuePay(null);*/
            r = iPmdMapper.getBranchPmdReportBean(monthId, partyId, null);
        }else{
            r = iPmdMapper.getPartyPmdReportBean(monthId, partyId);
        }

        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andIdEqualTo(id)
                .andHasReportEqualTo(false);
        pmdPartyMapper.updateByExampleSelective(record, example);
    }

    // 判断报送权限
    public boolean canReport(int monthId, int parytId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId) return false;

        PmdParty pmdParty = get(monthId, parytId);
        if(pmdParty==null) return false;

        // 组织部管理员、分党委管理员允许报送
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), parytId)) {
                return false;
            }
        }

        // 如果是直属党支部
        if(CmTag.isDirectBranch(parytId)){
            // 如果存在 没有支付且没有设置为延迟缴费， 则不可报送
            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andMonthIdEqualTo(monthId)
                    .andPartyIdEqualTo(parytId)
                    .andBranchIdIsNull()
                    .andHasPayEqualTo(false)
                    .andIsDelayEqualTo(false);
            return pmdMemberMapper.countByExample(example)==0;
        }

        // 如果存在 未报送的支部， 则不可报送
        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(parytId)
                .andHasReportEqualTo(false);

        return pmdBranchMapper.countByExample(example)==0;
    }

    public PmdParty get(int monthId, int parytId){

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(parytId);
        List<PmdParty> pmdParties = pmdPartyMapper.selectByExample(example);
        return pmdParties.size()==0?null:pmdParties.get(0);
    }

    @Transactional
    public void insertSelective(PmdParty record) {

        pmdPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        pmdPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdPartyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdParty record) {
        return pmdPartyMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委报表
    /*public XSSFWorkbook export(int id) throws IOException {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        int montId = pmdParty.getMonthId();
        int partyId = pmdParty.getPartyId();
        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(montId);
        String month = DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy年MM月");
        Integer reportUserId = pmdParty.getReportUserId();
        String reportUser = "";
        if(reportUserId!=null){
            SysUserView uv = CmTag.getUserById(reportUserId);
            reportUser = uv.getRealname();
        }
        Party party = partyMapper.selectByPrimaryKey(partyId);
        String partyName = party.getName();
        String reportDate = DateUtils.formatDate(new Date(), "yyyy年MM月dd日");

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pmd/pmd_party_export.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        String str = null;

        row = sheet.getRow(0);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("month", month);
        cell.setCellValue(str);
        cell = row.getCell(12);
        str = cell.getStringCellValue().replace("month", month);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(2);
        str = cell.getStringCellValue().replace("party", partyName);
        cell.setCellValue(str);
        cell = row.getCell(14);
        str = cell.getStringCellValue().replace("party", partyName);
        cell.setCellValue(str);

        row = sheet.getRow(18);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("reportUser", reportUser);
        cell.setCellValue(str);
        cell = row.getCell(5);
        str = cell.getStringCellValue().replace("reportDate", reportDate);
        cell.setCellValue(str);
        cell = row.getCell(12);
        str = cell.getStringCellValue().replace("reportUser", reportUser);
        cell.setCellValue(str);
        cell = row.getCell(17);
        str = cell.getStringCellValue().replace("reportDate", reportDate);
        cell.setCellValue(str);

        int rowCount = 4; // 插入总行数
        int startRow = 5; // 起始插入行（从0开始计数）

        // 填充数据
        for (int i = 0; i < rowCount; i++) {

            PmdExcelReportBean bean = iPmdMapper.getPmdExcelReportBean(montId, partyId, i);
            int column = 1;

            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(bean.getTotal());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getZj());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getGl());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getGq());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getXp());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getXszl());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getOther());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getLt());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getBdx());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getDx());

            column += 2;
            cell = row.getCell(column++);
            cell.setCellValue(bean.getTotal());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getZj());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getGl());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getGq());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getXp());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getXszl());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getOther());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getLt());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getBdx());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getDx());
        }

        startRow = 11;
        row = sheet.getRow(startRow++);
        cell = row.getCell(3);
        cell.setCellValue(pmdParty.getDuePay().toString());
        cell = row.getCell(9);
        cell.setCellValue(pmdParty.getHistoryDelayPay().toString());
        cell = row.getCell(15);
        cell.setCellValue(pmdParty.getDuePay().toString());
        cell = row.getCell(21);
        cell.setCellValue(pmdParty.getHistoryDelayPay().toString());

        row = sheet.getRow(startRow++);
        cell = row.getCell(3);
        cell.setCellValue(pmdParty.getRealPay().toString());
        cell = row.getCell(9);
        cell.setCellValue(pmdParty.getRealDelayPay().toString());
        cell = row.getCell(15);
        cell.setCellValue(pmdParty.getRealPay().toString());
        cell = row.getCell(21);
        cell.setCellValue(pmdParty.getRealDelayPay().toString());

        row = sheet.getRow(startRow++);
        cell = row.getCell(3);
        cell.setCellValue(pmdParty.getOnlineRealPay().toString());
        cell = row.getCell(9);
        cell.setCellValue(pmdParty.getOnlineRealDelayPay().toString());
        cell = row.getCell(15);
        cell.setCellValue(pmdParty.getOnlineRealPay().toString());
        cell = row.getCell(21);
        cell.setCellValue(pmdParty.getOnlineRealDelayPay().toString());

        row = sheet.getRow(startRow++);
        cell = row.getCell(3);
        cell.setCellValue(pmdParty.getCashRealPay().toString());
        cell = row.getCell(9);
        cell.setCellValue(pmdParty.getCashRealDelayPay().toString());
        cell = row.getCell(15);
        cell.setCellValue(pmdParty.getCashRealPay().toString());
        cell = row.getCell(21);
        cell.setCellValue(pmdParty.getCashRealDelayPay().toString());

        String notPay = pmdParty.getDuePay().subtract(pmdParty.getRealPay()).toString();
        row = sheet.getRow(startRow++);
        cell = row.getCell(3);
        cell.setCellValue(notPay);
        cell = row.getCell(15);
        cell.setCellValue(notPay);

        BigDecimal totalRealPay = pmdParty.getRealPay().add(pmdParty.getRealDelayPay());
        BigDecimal totalOnlinePay = pmdParty.getOnlineRealPay().add(pmdParty.getOnlineRealDelayPay());
        BigDecimal totalCashPay = pmdParty.getCashRealPay().add(pmdParty.getCashRealDelayPay());
        row = sheet.getRow(16);
        cell = row.getCell(3);
        cell.setCellValue(totalRealPay.toString());
        cell = row.getCell(9);
        cell.setCellValue(totalOnlinePay.toString());
        cell = row.getCell(15);
        cell.setCellValue(totalRealPay.toString());
        cell = row.getCell(21);
        cell.setCellValue(totalOnlinePay.toString());

        row = sheet.getRow(17);
        cell = row.getCell(9);
        cell.setCellValue(totalCashPay.toString());
        cell = row.getCell(21);
        cell.setCellValue(totalCashPay.toString());

        return wb;
    }*/

    //获取还未设置应缴额度的党员
    public List<PmdMember> listUnsetDuepayMembers(int pmdPartyId) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(pmdParty.getMonthId())
                .andPartyIdEqualTo(pmdParty.getPartyId()).andDuePayIsNull();

        return pmdMemberMapper.selectByExample(example);
    }

    // 强制报送
    @Transactional
    public void forceReport(int pmdPartyId) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);
        if(pmdParty.getHasReport()){
            throw new OpException("重复报送。");
        }

        // 组织部管理员、分党委管理员允许报送
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), pmdParty.getPartyId())) {
                throw new UnauthorizedException();
            }
        }

        List<PmdMember> pmdMembers = listUnsetDuepayMembers(pmdPartyId);
        if(pmdMembers.size()>0){
            throw new OpException("强制报送失败：存在未设定缴费额度的党员");
        }

        pmdPayService.delayAll(pmdParty.getPartyId(), null, "系统报送");

        // 报送所有支部
        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria()
                .andMonthIdEqualTo(pmdParty.getMonthId())
                .andPartyIdEqualTo(pmdParty.getPartyId())
                .andHasReportEqualTo(false);
        List<PmdBranch> pmdBranchs = pmdBranchMapper.selectByExample(example);
        for (PmdBranch pmdBranch : pmdBranchs) {
            pmdBranchService.report(pmdBranch.getId());
        }

        //报送
        report(pmdPartyId);
    }
}
