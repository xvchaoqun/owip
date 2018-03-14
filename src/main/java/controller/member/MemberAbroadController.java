package controller.member;

import domain.member.Member;
import domain.member.MemberAbroad;
import domain.member.MemberAbroadView;
import domain.member.MemberAbroadViewExample;
import domain.member.MemberAbroadViewExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberAbroadController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad")
    public String memberAbroad(
            Integer userId, Integer partyId,
            Integer branchId, ModelMap modelMap) {

        if (userId != null) {
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

        return "member/memberAbroad/memberAbroad_page";
    }

    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad_data")
    public void memberAbroad_data(HttpServletResponse response,
                                 /*@SortParam(required = false, defaultValue = "abroad_time", tableName = "ow_member_abroad") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,*/
                                  Integer userId,
                                  @RequestDateRange DateRange _abroadTime,
                                  Integer partyId,
                                  Integer branchId,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberAbroadViewExample example = new MemberAbroadViewExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause("lsh desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (_abroadTime.getStart() != null) {
            criteria.andSjcfsjGreaterThanOrEqualTo(_abroadTime.getStart());
        }

        if (_abroadTime.getEnd() != null) {
            criteria.andSjcfsjLessThanOrEqualTo(_abroadTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberAbroad_export(example, response);
            return;
        }

        int count = memberAbroadViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberAbroadView> memberAbroads = memberAbroadViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberAbroads);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberAbroad:edit")
    @RequestMapping(value = "/memberAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberAbroad_au(@CurrentUser SysUserView loginUser, MemberAbroad record,
                                  String _abroadTime,
                                  String _expectReturnTime,
                                  String _actualReturnTime,
                                  HttpServletRequest request) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        if (StringUtils.isNotBlank(_abroadTime))
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, DateUtils.YYYY_MM_DD));
        if (StringUtils.isNotBlank(_expectReturnTime))
            record.setExpectReturnTime(DateUtils.parseDate(_expectReturnTime, DateUtils.YYYY_MM_DD));
        if (StringUtils.isNotBlank(_actualReturnTime))
            record.setActualReturnTime(DateUtils.parseDate(_actualReturnTime, DateUtils.YYYY_MM_DD));

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if (record.getId() == null) {
            memberAbroadService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_PARTY, "添加党员出国境信息：%s", record.getUserId()));
        } else {

            memberAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_PARTY, "更新党员出国境信息：%s", record.getUserId()));
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
        return "member/memberAbroad/memberAbroad_au";
    }

    /*@RequiresPermissions("memberAbroad:del")
    @RequestMapping(value = "/memberAbroad_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberAbroad_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            memberAbroadService.del(userId);
            logger.info(addLog(SystemConstants.LOG_PARTY, "删除党员出国境信息：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberAbroad:del")
    @RequestMapping(value = "/memberAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            memberAbroadService.batchDel(userIds);
            logger.info(addLog(SystemConstants.LOG_PARTY, "批量删除党员出国境信息：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    public void memberAbroad_export(MemberAbroadViewExample example, HttpServletResponse response) {

        List<MemberAbroadView> memberAbroads = memberAbroadViewMapper.selectByExample(example);
        int rownum = memberAbroadViewMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"教工号", "姓名", "所在分党委", "所在党支部", "国家", "实际出发时间", "实归时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberAbroadView record = memberAbroads.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    record.getGj(),
                    DateUtils.formatDate(record.getSjcfsj(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getSgsj(), DateUtils.YYYY_MM_DD)
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        String fileName = "教职工党员出国境信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
