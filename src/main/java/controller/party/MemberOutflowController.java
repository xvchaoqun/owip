package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberOutflowExample.Criteria;
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
public class MemberOutflowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_view")
    public String memberOutflow_view(int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        MemberOutflow memberOutflow = memberOutflowService.get(sysUser.getId());
        modelMap.put("memberOutflow", memberOutflow);

        modelMap.put("locationMap", locationService.codeMap());
        modelMap.put("jobMap", metaTypeService.metaTypes("mc_job"));
        modelMap.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

        if(memberOutflow!=null) {
            Integer partyId = memberOutflow.getPartyId();
            Integer branchId = memberOutflow.getBranchId();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
        }

        return "party/memberOutflow/memberOutflow_view";
    }

    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow")
    public String memberOutflow() {

        return "index";
    }
    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_page")
    public String memberOutflow_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_outflow") String sort,
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

        MemberOutflowExample example = new MemberOutflowExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type );
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            memberOutflow_export(example, response);
            return null;
        }

        int count = memberOutflowMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberOutflows", memberOutflows);

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
        modelMap.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));

        return "party/memberOutflow/memberOutflow_page";
    }

    @RequiresPermissions("memberOutflow:update")
    @RequestMapping(value = "/memberOutflow_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_deny(@CurrentUser SysUser loginUser,HttpServletRequest request,
                                    Integer id, String remark) {

        //操作人应是申请人所在党支部或直属党支部管理员
        int loginUserId = loginUser.getId();
        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        Integer branchId = memberOutflow.getBranchId();
        Integer partyId = memberOutflow.getPartyId();
        boolean branchAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectBranch(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        memberOutflowService.deny(memberOutflow.getUserId());
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝流出党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:update")
    @RequestMapping(value = "/memberOutflow_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_check1(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应是申请人所在党支部或直属党支部管理员
        int loginUserId = loginUser.getId();
        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        Integer branchId = memberOutflow.getBranchId();
        Integer partyId = memberOutflow.getPartyId();
        boolean branchAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectBranch(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过党支部审核
            memberOutflowService.check2(memberOutflow.getUserId(), true);
            logger.info(addLog(request, SystemConstants.LOG_OW, "通过流出党员申请：%s", id));
        }else {
            memberOutflowService.check1(memberOutflow.getUserId());
            logger.info(addLog(request, SystemConstants.LOG_OW, "审核流出党员申请：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:update")
    @RequestMapping(value = "/memberOutflow_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_check2(@CurrentUser SysUser loginUser,HttpServletRequest request, Integer id) {

        //操作人应是申请人所在分党委管理员
        int loginUserId = loginUser.getId();
        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        Integer partyId = memberOutflow.getPartyId();
        if(!partyMemberService.isPresentAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        memberOutflowService.check2(memberOutflow.getUserId(), false);
        logger.info(addLog(request, SystemConstants.LOG_OW, "通过流出党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:edit")
    @RequestMapping(value = "/memberOutflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_au(MemberOutflow record, String _flowTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberOutflowService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }
        Integer userId = record.getUserId();
        SysUser sysUser = sysUserService.findById(userId);
        if(sysUser.getType()==SystemConstants.USER_TYPE_JZG)
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER);
        else
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT);

        if (id == null) {
            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);

            memberOutflowService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加流出党员：%s", record.getId()));
        } else {
            record.setStatus(null);// 不修改状态

            memberOutflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新流出党员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:edit")
    @RequestMapping("/memberOutflow_au")
    public String memberOutflow_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
            modelMap.put("memberOutflow", memberOutflow);

            modelMap.put("sysUser", sysUserService.findById(memberOutflow.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberOutflow.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberOutflow.getPartyId()));
            }
            if (memberOutflow.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberOutflow.getBranchId()));
            }
        }
        return "party/memberOutflow/memberOutflow_au";
    }

    @RequiresPermissions("memberOutflow:del")
    @RequestMapping(value = "/memberOutflow_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberOutflowService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除流出党员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:del")
    @RequestMapping(value = "/memberOutflow_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberOutflowService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除流出党员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberOutflow_export(MemberOutflowExample example, HttpServletResponse response) {

        List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExample(example);
        int rownum = memberOutflowMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","分党委名称","党支部名称","原职业","外出流向","流出时间","流出省份","流出原因","是否持有《中国共产党流动党员活动证》","组织关系状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberOutflow memberOutflow = memberOutflows.get(i);
            String[] values = {
                        memberOutflow.getUserId()+"",
                                            memberOutflow.getPartyName(),
                                            memberOutflow.getBranchName(),
                                            memberOutflow.getOriginalJob()+"",
                                            memberOutflow.getDirection()+"",
                                            DateUtils.formatDate(memberOutflow.getFlowTime(), DateUtils.YYYY_MM_DD),
                                            memberOutflow.getProvince()+"",
                                            memberOutflow.getReason(),
                                            memberOutflow.getHasPapers() +"",
                                            memberOutflow.getOrStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "流出党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
