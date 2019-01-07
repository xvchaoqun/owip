package service.pmd;

import controller.global.OpException;
import domain.pmd.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pmd.common.PmdReportBean;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class PmdPartyService extends PmdBaseMapper {

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
        if(PartyHelper.isDirectBranch(partyId)){
            // 党员总数和本月应交党费数已经在启动缴费月份时保存
            /*record.setMemberCount(null);
            record.setDuePay(null);*/
            r = iPmdMapper.getBranchPmdReportBean(monthId, partyId, null);
        }else{
            r = iPmdMapper.getPartyPmdReportBean(monthId, partyId);
        }

        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andIdEqualTo(id)
                .andHasReportEqualTo(false);
        pmdPartyMapper.updateByExampleSelective(record, example);
    }
    
    // 更新报送数据（当报送数据异常时，需要先手动更新，然后更新报送统计数据）
    @Transactional
    public void updateReport(int id) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(id);
        int monthId = pmdParty.getMonthId();
        int partyId = pmdParty.getPartyId();
        
        PmdParty record = new PmdParty();
        record.setId(id);
        
        // 保存数据汇总
        PmdReportBean r = null;
        // 如果是直属党支部
        if(PartyHelper.isDirectBranch(partyId)){
            r = iPmdMapper.getBranchPmdReportBean(monthId, partyId, null);
        }else{
            r = iPmdMapper.getPartyPmdReportBean(monthId, partyId);
        }

        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        pmdPartyMapper.updateByPrimaryKeySelective(record);
    }

    // 撤销党委报送
    @Transactional
    public void unreport(int pmdPartyId) {

        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);
        int partyId = pmdParty.getPartyId();
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        int monthId = pmdParty.getMonthId();
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId){
            throw new OpException("不允许撤销报送。");
        }

        PmdParty record = new PmdParty();
        record.setId(pmdPartyId);
        record.setHasReport(false);

        pmdPartyMapper.updateByPrimaryKeySelective(record);
    }

    // 判断报送权限
    public boolean canReport(int monthId, int partyId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId) return false;

        PmdParty pmdParty = get(monthId, partyId);
        if(pmdParty==null) return false;

        // 组织部管理员、分党委管理员允许报送
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                return false;
            }
        }

        // 如果是直属党支部
        if(PartyHelper.isDirectBranch(partyId)){
            // 如果存在 没有支付且没有设置为延迟缴费， 则不可报送
            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andMonthIdEqualTo(monthId)
                    .andPartyIdEqualTo(partyId)
                    .andBranchIdIsNull()
                    .andHasPayEqualTo(false)
                    .andIsDelayEqualTo(false);
            return pmdMemberMapper.countByExample(example)==0;
        }

        // 如果存在 未报送的支部， 则不可报送
        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(partyId)
                .andHasReportEqualTo(false);

        return pmdBranchMapper.countByExample(example)==0;
    }

    public PmdParty get(int monthId, int partyId){

        PmdPartyExample example = new PmdPartyExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(partyId);
        List<PmdParty> pmdParties = pmdPartyMapper.selectByExample(example);
        return pmdParties.size()==0?null:pmdParties.get(0);
    }

    @Transactional
    public void insertSelective(PmdParty record) {

        pmdPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer pmdPartyId) {

        // 只能删除当月的缴费党委
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null){
            throw new OpException("缴费未开启");
        }
        int monthId = currentPmdMonth.getId();
        PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);
        if(pmdParty.getMonthId() != monthId){
            throw new OpException("仅允许删除当前缴费月份的党委");
        }

        if(pmdParty.getHasReport()){
            throw new OpException("不能删除已报送党委");
        }
    
        int partyId = pmdParty.getPartyId();
        // 如果是直属党支部
        if(PartyHelper.isDirectBranch(partyId)){
            PmdMemberPayExample example = new PmdMemberPayExample();
            example.createCriteria().andPayMonthIdEqualTo(monthId)
                    .andChargePartyIdEqualTo(partyId).andHasPayEqualTo(true)
                    .andIsOnlinePayEqualTo(true); // 现金缴费删除？
            if(pmdMemberPayMapper.countByExample(example)>0){
                throw new OpException("存在已缴费记录，不允许删除");
            }
        }else {
            PmdBranchExample example = new PmdBranchExample();
            example.createCriteria().andPartyIdEqualTo(partyId).andMonthIdEqualTo(monthId);
            if (pmdBranchMapper.countByExample(example) > 0) {
                throw new OpException("存在未删除党支部，不允许删除");
            }
        }
        
        pmdPartyMapper.deleteByPrimaryKey(pmdPartyId);
    
        {
            // 更新当月缴费党委数量
            PmdPartyExample example = new PmdPartyExample();
            example.createCriteria().andMonthIdEqualTo(monthId);
            int partyCount = (int) pmdPartyMapper.countByExample(example);
            
            PmdMonth record = new PmdMonth();
            record.setId(monthId);
            record.setPartyCount(partyCount);
            pmdMonthMapper.updateByPrimaryKeySelective(record);
        }
        
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
