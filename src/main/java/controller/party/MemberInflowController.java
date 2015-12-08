package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberInflowExample.Criteria;
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
public class MemberInflowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow")
    public String memberInflow() {

        return "index";
    }
    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_page")
    public String memberInflow_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
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

        MemberInflowExample example = new MemberInflowExample();
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
            memberInflow_export(example, response);
            return null;
        }

        int count = memberInflowMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInflow> memberInflows = memberInflowMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberInflows", memberInflows);

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

        modelMap.put("locationMap", locationService.codeMap());
        modelMap.put("jobMap", metaTypeService.metaTypes("mc_job"));

        return "party/memberInflow/memberInflow_page";
    }

    @RequiresPermissions("memberInflow:edit")
    @RequestMapping(value = "/memberInflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_au(MemberInflow record,
                                  String _flowTime, String _growTime, String _outflowTime,
                                  HttpServletRequest request) {

        Integer id = record.getId();

        if (memberInflowService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_outflowTime)) {
            record.setOutflowTime(DateUtils.parseDate(_outflowTime, DateUtils.YYYY_MM_DD));
        }

        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        if (id == null) {
            enterApplyService.memberInflow(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加流入党员：%s", record.getId()));
        } else {

            memberInflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新流入党员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_deny(@CurrentUser SysUser loginUser,HttpServletRequest request,
                                    Integer id, String remark) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        Integer branchId = memberInflow.getBranchId();
        Integer partyId = memberInflow.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        enterApplyService.applyBack(memberInflow.getUserId(), remark, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝流入党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_check1(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        Integer branchId = memberInflow.getBranchId();
        Integer partyId = memberInflow.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过党支部审核
            memberInflowService.addMember(memberInflow.getUserId(), true);
            logger.info(addLog(request, SystemConstants.LOG_OW, "通过流入党员申请：%s", id));
        }else {
            memberInflowService.checkMember(memberInflow.getUserId());
            logger.info(addLog(request, SystemConstants.LOG_OW, "审核流入党员申请：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_check2(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应是申请人所在分党委管理员
        int loginUserId = loginUser.getId();
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        Integer partyId = memberInflow.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        memberInflowService.addMember(memberInflow.getUserId(), false);
        logger.info(addLog(request, SystemConstants.LOG_OW, "通过流入党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:edit")
    @RequestMapping("/memberInflow_au")
    public String memberInflow_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
            modelMap.put("memberInflow", memberInflow);

            modelMap.put("sysUser", sysUserService.findById(memberInflow.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberInflow.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberInflow.getPartyId()));
            }
            if (memberInflow.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberInflow.getBranchId()));
            }
        }
        return "party/memberInflow/memberInflow_au";
    }

    @RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInflowService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除流入党员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberInflowService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除流入党员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberInflow_export(MemberInflowExample example, HttpServletResponse response) {

        List<MemberInflow> memberInflows = memberInflowMapper.selectByExample(example);
        int rownum = memberInflowMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","分党委名称","党支部名称","原职业","流入前所在省份","是否持有《中国共产党流动党员活动证》","流入时间","流入原因","入党时间","组织关系所在地"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberInflow memberInflow = memberInflows.get(i);
            String[] values = {
                        memberInflow.getUserId()+"",
                                            memberInflow.getPartyName(),
                                            memberInflow.getBranchName(),
                                            memberInflow.getOriginalJob()+"",
                                            memberInflow.getProvince()+"",
                                            memberInflow.getHasPapers() +"",
                                            DateUtils.formatDate(memberInflow.getFlowTime(), DateUtils.YYYY_MM_DD),
                                            memberInflow.getReason(),
                                            DateUtils.formatDate(memberInflow.getGrowTime(), DateUtils.YYYY_MM_DD),
                                            memberInflow.getOrLocation()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "流入党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
