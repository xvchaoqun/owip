package controller.party;

import bean.UserBean;
import controller.BaseController;
import domain.*;
import domain.MemberTransferExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer")
    public String memberTransfer() {

        return "index";
    }
    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_page")
    public String memberTransfer_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberTransferExample example = new MemberTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
  /*      if (type!=null) {
            criteria.andTypeEqualTo(type);
        }*/

        if (export == 1) {
            memberTransfer_export(example, response);
            return null;
        }

        int count = memberTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberTransfers", memberTransfers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        /*if (type!=null) {
            searchStr += "&type=" + type;
        }*/
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

        return "party/memberTransfer/memberTransfer_page";
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_deny(@CurrentUser SysUser loginUser,HttpServletRequest request,
                                     Integer id, String reason) {

        //操作人应是申请人所在分党委管理员
        int loginUserId = loginUser.getId();

        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        Member member = memberService.get(memberTransfer.getUserId());
        Integer partyId = member.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        memberTransferService.deny(memberTransfer.getUserId(), reason);
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝流出党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_check1(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应是申请人所在分党委管理员
        int loginUserId = loginUser.getId();
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        Member member = memberService.get(memberTransfer.getUserId());
        Integer partyId = member.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        memberTransferService.check1(memberTransfer.getUserId());
        logger.info(addLog(request, SystemConstants.LOG_OW, "审核流出党员申请1：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_check2(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应是申请人转入的分党委的管理员
        int loginUserId = loginUser.getId();
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        Integer partyId = memberTransfer.getToPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 转入分党委管理员
            throw new UnauthorizedException();
        }

        memberTransferService.check2(memberTransfer.getUserId(), false);
        logger.info(addLog(request, SystemConstants.LOG_OW, "通过流出党员申请2：%s", id));

        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(MemberTransfer record,
                                    String _payTime, String _fromHandleTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberTransferService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        if(member.getPartyId().byteValue() == record.getToPartyId()){
            return failed("转入不能是当前所在分党委");
        }


        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
            memberTransferService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加校内组织关系互转：%s", record.getId()));
        } else {
            record.setStatus(null); // 不改状态
            memberTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新校内组织关系互转：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping("/memberTransfer_au")
    public String memberTransfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            modelMap.put("memberTransfer", memberTransfer);

            Integer userId = memberTransfer.getUserId();
            UserBean userBean = userBeanService.get(userId);
            modelMap.put("userBean", userBean);

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);

            modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
            modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));

            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }

        }
        return "party/memberTransfer/memberTransfer_au";
    }

    @RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberTransferService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除校内组织关系互转：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberTransferService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除校内组织关系互转：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberTransfer_export(MemberTransferExample example, HttpServletResponse response) {

        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
        int rownum = memberTransferMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户",/*"人员类别","转入单位","转出单位",*/"转出办理时间","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberTransfer memberTransfer = memberTransfers.get(i);
            String[] values = {
                        memberTransfer.getUserId()+"",/*
                                            memberTransfer.getType()+"",
                                            memberTransfer.getToUnit(),
                                            memberTransfer.getFromUnit(),*/
                                            DateUtils.formatDate(memberTransfer.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                                            memberTransfer.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "校内组织关系互转_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
