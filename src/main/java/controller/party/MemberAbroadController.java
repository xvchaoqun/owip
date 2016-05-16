package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberAbroadExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberAbroadMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad")
    public String memberAbroad() {

        return "index";
    }
    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad_page")
    public String memberAbroad_page(
                                    Integer userId,Integer partyId,
                                    Integer branchId, ModelMap modelMap) {

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        return "party/memberAbroad/memberAbroad_page";
    }
    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad_data")
    public void memberAbroad_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "abroad_time", tableName = "ow_member_abroad") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    String _abroadTime,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberAbroadExample example = new MemberAbroadExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(StringUtils.isNotBlank(_abroadTime)) {
            String abroadTimeStart = _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String abroadTimeEnd= _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(abroadTimeStart)) {
                criteria.andAbroadTimeGreaterThanOrEqualTo(DateUtils.parseDate(abroadTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(abroadTimeEnd)) {
                criteria.andAbroadTimeLessThanOrEqualTo(DateUtils.parseDate(abroadTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            memberAbroad_export(example, response);
            return;
        }

        int count = memberAbroadMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberAbroad> memberAbroads = memberAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberAbroads);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberAbroad.class, MemberAbroadMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("memberAbroad:edit")
    @RequestMapping(value = "/memberAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberAbroad_au(@CurrentUser SysUser loginUser, MemberAbroad record,
                                  String _abroadTime,
                                  String _expectReturnTime,
                                  String _actualReturnTime,
                                  HttpServletRequest request) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin && branchId!=null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
            }
            if(!isAdmin) throw new UnauthorizedException();
        }

        if(StringUtils.isNotBlank(_abroadTime))
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, DateUtils.YYYY_MM_DD));
        if(StringUtils.isNotBlank(_expectReturnTime))
            record.setExpectReturnTime(DateUtils.parseDate(_expectReturnTime, DateUtils.YYYY_MM_DD));
        if(StringUtils.isNotBlank(_actualReturnTime))
            record.setActualReturnTime(DateUtils.parseDate(_actualReturnTime, DateUtils.YYYY_MM_DD));

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if (record.getId() == null) {
            memberAbroadService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加党员出国境信息：%s", record.getUserId()));
        } else {

            memberAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新党员出国境信息：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberAbroad:edit")
    @RequestMapping("/memberAbroad_au")
    public String memberAbroad_au(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            MemberAbroad memberAbroad = memberAbroadMapper.selectByPrimaryKey(userId);
            modelMap.put("memberAbroad", memberAbroad);
        }
        return "party/memberAbroad/memberAbroad_au";
    }

    @RequiresPermissions("memberAbroad:del")
    @RequestMapping(value = "/memberAbroad_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberAbroad_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            memberAbroadService.del(userId);
            logger.info(addLog(SystemConstants.LOG_OW, "删除党员出国境信息：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberAbroad:del")
    @RequestMapping(value = "/memberAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            memberAbroadService.batchDel(userIds);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除党员出国境信息：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberAbroad_export(MemberAbroadExample example, HttpServletResponse response) {

        List<MemberAbroad> memberAbroads = memberAbroadMapper.selectByExample(example);
        int rownum = memberAbroadMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"党员",/*"分党委名称","党支部名称","入党时间",*/"出国时间","出国缘由","预计归国时间","实际归国时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberAbroad memberAbroad = memberAbroads.get(i);
            String[] values = {
                        memberAbroad.getUserId()+"",
                                           /* memberAbroad.getPartyName(),
                                            memberAbroad.getBranchName(),
                                            DateUtils.formatDate(memberAbroad.getGrowTime(), DateUtils.YYYY_MM_DD),*/
                                            memberAbroad.getAbroadTime()+"",
                                            memberAbroad.getReason()+"",
                                            DateUtils.formatDate(memberAbroad.getExpectReturnTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberAbroad.getActualReturnTime(), DateUtils.YYYY_MM_DD)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "党员出国境信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
