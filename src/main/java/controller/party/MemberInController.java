package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberInExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberInController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn")
    public String memberIn() {

        return "index";
    }
    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_page")
    public String memberIn_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_in") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                     Byte type,
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

        MemberInExample example = new MemberInExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            memberIn_export(example, response);
            return null;
        }

        int count = memberInMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberIn> memberIns = memberInMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberIns", memberIns);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        if (type!=null) {
            searchStr += "&type=" + type;
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
        return "party/memberIn/memberIn_page";
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping(value = "/memberIn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_au(MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                              String _growTime, String _positiveTime,
                              String _fromHandleTime, String _handleTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberInService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        record.setHasReceipt((record.getHasReceipt() == null) ? false : record.getHasReceipt());

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
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
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            enterApplyService.memberIn(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加组织关系转入：%s", record.getId()));
        } else {
            record.setStatus(null); // 更新的时候不能更新状态
            memberInService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新组织关系转入：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping("/memberIn_deny")
    public String memberIn_deny() {

        return "party/memberIn/memberIn_deny";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_deny(@CurrentUser SysUser loginUser, String reason, HttpServletRequest request, Integer id) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        Integer branchId = memberIn.getBranchId();
        Integer partyId = memberIn.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        enterApplyService.applyBack(memberIn.getUserId(), reason, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝组织关系转入：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_check2(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应该是应是申请人所在分党委、直属党支部管理员
        int loginUserId = loginUser.getId();
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        Integer partyId = memberIn.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        memberInService.checkMember(memberIn.getUserId());
        logger.info(addLog(request, SystemConstants.LOG_OW, "组织关系转入-审核1：%s", id));

        return success(FormUtils.SUCCESS);
    }

    //组织部管理员审核 正式党员
    @RequiresRoles("odAdmin")
    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_check3(int id, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        // 这里要添加权限验证?
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);

        memberInService.addMember(memberIn.getUserId(),memberIn.getPoliticalStatus());
        logger.info(addLog(request, SystemConstants.LOG_OW, "组织关系转入-审核2：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping("/memberIn_au")
    public String memberIn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
            modelMap.put("memberIn", memberIn);

            modelMap.put("userBean", userBeanService.get(memberIn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberIn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberIn.getPartyId()));
            }
            if (memberIn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberIn.getBranchId()));
            }
        }
        return "party/memberIn/memberIn_au";
    }

    @RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除组织关系转入：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberInService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除组织关系转入：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberIn_export(MemberInExample example, HttpServletResponse response) {

        List<MemberIn> memberIns = memberInMapper.selectByExample(example);
        int rownum = memberInMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","类别","转出单位","转出单位抬头","介绍信有效期天数","转出办理时间","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberIn memberIn = memberIns.get(i);
            String[] values = {
                        memberIn.getUserId()+"",
                                            memberIn.getType() +"",
                                            memberIn.getFromUnit(),
                                            memberIn.getFromTitle(),
                                            memberIn.getValidDays()+"",
                                            DateUtils.formatDate(memberIn.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                                            memberIn.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "组织关系转入_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
