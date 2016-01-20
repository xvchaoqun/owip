package controller.party;

import controller.BaseController;
import domain.Member;
import domain.MemberAbroad;
import domain.MemberAbroadExample;
import domain.MemberAbroadExample.Criteria;
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
public class MemberAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad")
    public String memberAbroad() {

        return "index";
    }
    @RequiresPermissions("memberAbroad:list")
    @RequestMapping("/memberAbroad_page")
    public String memberAbroad_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "abroad_time", tableName = "ow_member_abroad") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    String _abroadTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

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
            modelMap.put("sysUser", sysUserService.findById(userId));
            criteria.andUserIdEqualTo(userId);
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
            return null;
        }

        int count = memberAbroadMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberAbroad> memberAbroads = memberAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberAbroads", memberAbroads);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            searchStr += "&userId=" + userId;
        }
        if (StringUtils.isNotBlank(_abroadTime)) {
            searchStr += "&_abroadTime=" + _abroadTime;
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

        return "party/memberAbroad/memberAbroad_page";
    }

    @RequiresPermissions("memberAbroad:edit")
    @RequestMapping(value = "/memberAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberAbroad_au(MemberAbroad record, HttpServletRequest request) {

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if (userId == null) {
            memberAbroadService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加党员出国境信息：%s", record.getUserId()));
        } else {

            memberAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新党员出国境信息：%s", record.getUserId()));
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
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除党员出国境信息：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberAbroad:del")
    @RequestMapping(value = "/memberAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            memberAbroadService.batchDel(userIds);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除党员出国境信息：%s", StringUtils.join(userIds, ",")));
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
