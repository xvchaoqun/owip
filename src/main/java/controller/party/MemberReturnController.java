package controller.party;

import controller.BaseController;
import domain.Branch;
import domain.MemberReturn;
import domain.MemberReturnExample;
import domain.MemberReturnExample.Criteria;
import domain.Party;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberReturnController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberReturn> checkVerityAuth(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberReturn, memberReturn.getPartyId(), memberReturn.getBranchId());
    }

    private VerifyAuth<MemberReturn> checkVerityAuth2(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberReturn, memberReturn.getPartyId());
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn")
    public String memberReturn() {

        return "index";
    }
    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_page")
    public String memberReturn_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_return") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberReturnExample example = new MemberReturnExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            memberReturn_export(example, response);
            return null;
        }

        int count = memberReturnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReturn> memberReturns = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberReturns", memberReturns);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
            searchStr += "&partyId=" + partyId;
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
            searchStr += "&branchId=" + branchId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "party/memberReturn/memberReturn_page";
    }

    @RequiresPermissions("memberReturn:edit")
    @RequestMapping(value = "/memberReturn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_au(MemberReturn record, String _applyTime, String _activeTime, String _candidateTime,
                                  String _growTime, String _positiveTime,HttpServletRequest request) {

        Integer id = record.getId();

        if (memberReturnService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_candidateTime)){
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_positiveTime)){
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            enterApplyService.memberReturn(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加留学归国人员申请恢复组织生活：%s", record.getId()));
        } else {

            record.setStatus(null); // 更新的时候不能更新状态
            memberReturnService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新留学归国人员申请恢复组织生活：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_deny(HttpServletRequest request,
                                    Integer id, String remark) {

        VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth(id);
        MemberReturn memberReturn = verifyAuth.entity;

        enterApplyService.applyBack(memberReturn.getUserId(), remark, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(SystemConstants.LOG_OW, "拒绝留学归国人员申请恢复组织生活：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_check1(HttpServletRequest request, Integer id) {

        VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth(id);
        boolean isDirectBranch = verifyAuth.isDirectBranch;
        boolean isPartyAdmin = verifyAuth.isPartyAdmin;
        MemberReturn memberReturn = verifyAuth.entity;

        if(isDirectBranch && isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
            memberReturnService.addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), true);
            logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-直属党支部审核：%s", id));
        }else {
            memberReturnService.checkMember(memberReturn.getUserId());
            logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-党支部审核：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_check2(HttpServletRequest request, Integer id) {

        VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth2(id);
        MemberReturn memberReturn = verifyAuth.entity;

        memberReturnService.addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), false);
        logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-分党委、党总支审核：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:edit")
    @RequestMapping("/memberReturn_au")
    public String memberReturn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
            modelMap.put("memberReturn", memberReturn);

            modelMap.put("sysUser", sysUserService.findById(memberReturn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberReturn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberReturn.getPartyId()));
            }
            if (memberReturn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberReturn.getBranchId()));
            }
        }
        return "party/memberReturn/memberReturn_au";
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberReturnService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除留学归国人员申请恢复组织生活：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberReturnService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除留学归国人员申请恢复组织生活：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberReturn_export(MemberReturnExample example, HttpServletResponse response) {

        List<MemberReturn> memberReturns = memberReturnMapper.selectByExample(example);
        int rownum = memberReturnMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","所属分党委","所属党支部","确定为入党积极分子时间","确定为发展对象时间","入党时间","转正时间","状态","备注","创建时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberReturn memberReturn = memberReturns.get(i);
            String[] values = {
                        memberReturn.getUserId()+"",
                                            memberReturn.getPartyId()+"",
                                            memberReturn.getBranchId()+"",
                                            DateUtils.formatDate(memberReturn.getActiveTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getCandidateTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getGrowTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getPositiveTime(), DateUtils.YYYY_MM_DD),
                                            memberReturn.getStatus()+"",
                                            memberReturn.getRemark(),
                                            DateUtils.formatDate(memberReturn.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "留学归国人员申请恢复组织生活_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
