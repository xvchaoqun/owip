package controller.party;

import controller.BaseController;
import domain.Member;
import domain.MemberOut;
import domain.MemberOutExample;
import domain.MemberOutExample.Criteria;
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
import org.apache.shiro.authz.annotation.RequiresRoles;
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
public class MemberOutController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberOut> checkVerityAuth(int id){
        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberOut, memberOut.getPartyId(), memberOut.getBranchId());
    }

    private VerifyAuth<MemberOut> checkVerityAuth2(int id){
        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberOut, memberOut.getPartyId());
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut/printPreview")
    public String memberOut_printPreview(int userId, ModelMap modelMap) {

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        return "party/memberOut/print_preview";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_view")
    public String memberOut_view(int userId, ModelMap modelMap) {

        modelMap.put("userBean", userBeanService.get(userId));

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        return "party/memberOut/memberOut_view";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut")
    public String memberOut() {

        return "index";
    }
    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_page")
    public String memberOut_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_out") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte type,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberOutExample example = new MemberOutExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            memberOut_export(example, response);
            return null;
        }

        int count = memberOutMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOut> memberOuts = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberOuts", memberOuts);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        if (type!=null) {
            searchStr += "&type=" + type;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());

        return "party/memberOut/memberOut_page";
    }

    @RequiresPermissions("memberOut:update")
    @RequestMapping("/memberOut_deny")
    public String memberOut_deny() {

        return "party/memberOut/memberOut_deny";
    }

    @RequiresPermissions("memberOut:update")
    @RequestMapping(value = "/memberOut_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_deny(HttpServletRequest request,
                                     Integer id, String reason) {

        VerifyAuth<MemberOut> verifyAuth = checkVerityAuth(id);
        MemberOut memberOut = verifyAuth.entity;

        memberOutService.deny(memberOut.getUserId(), reason);
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝转出党员申请：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:update")
    @RequestMapping(value = "/memberOut_check1", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_check1(HttpServletRequest request, Integer id) {

        VerifyAuth<MemberOut> verifyAuth = checkVerityAuth2(id);
        MemberOut memberOut = verifyAuth.entity;
        boolean isParty = verifyAuth.isParty;

        if(isParty){ // 分党委审核，需要跳过下一步的组织部审核
            memberOutService.checkByParty(memberOut.getUserId(), false);
            logger.info(addLog(request, SystemConstants.LOG_OW, "转出党员申请-分党委审核：%s", id));
        }else {
            memberOutService.check1(memberOut.getUserId());
            logger.info(addLog(request, SystemConstants.LOG_OW, "转出党员申请-党总支、直属党支部审核：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("odAdmin")
    @RequiresPermissions("memberOut:update")
    @RequestMapping(value = "/memberOut_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_check2(HttpServletRequest request, Integer id) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);

        memberOutService.check2(memberOut.getUserId(), false);
        logger.info(addLog(request, SystemConstants.LOG_OW, "转出党员申请-组织部审核：%s", id));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("memberOut:edit")
    @RequestMapping(value = "/memberOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_au(MemberOut record, String _payTime, String _handleTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberOutService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_OUT_STATUS_APPLY);
            memberOutService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加组织关系转出：%s", record.getId()));
        } else {

            memberOutService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新组织关系转出：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping("/memberOut_au")
    public String memberOut_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
            modelMap.put("memberOut", memberOut);

            modelMap.put("userBean", userBeanService.get(memberOut.getUserId()));
        }
        return "party/memberOut/memberOut_au";
    }

    @RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberOutService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除组织关系转出：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberOutService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除组织关系转出：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberOut_export(MemberOutExample example, HttpServletResponse response) {

        List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);
        int rownum = memberOutMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","类别","转入单位抬头","转入单位","转出单位","介绍信有效期天数","办理时间","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberOut memberOut = memberOuts.get(i);
            String[] values = {
                        memberOut.getUserId()+"",
                                            memberOut.getType()+"",
                                            memberOut.getToTitle(),
                                            memberOut.getToUnit(),
                                            memberOut.getFromUnit(),
                                            memberOut.getValidDays()+"",
                                            DateUtils.formatDate(memberOut.getHandleTime(), DateUtils.YYYY_MM_DD),
                                            memberOut.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "组织关系转出_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
