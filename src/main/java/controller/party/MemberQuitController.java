package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberQuitExample.Criteria;
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
public class MemberQuitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit")
    public String memberQuit() {

        return "index";
    }

    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_page")
    public String memberQuit_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_quit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte type,
                                    String _quitTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberQuitExample example = new MemberQuitExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(adminPartyIdList(), adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if(StringUtils.isNotBlank(_quitTime)) {
            String quitTimeStart = _quitTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String quitTimeEnd = _quitTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(quitTimeStart)) {
                criteria.andQuitTimeGreaterThanOrEqualTo(DateUtils.parseDate(quitTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(quitTimeEnd)) {
                criteria.andQuitTimeLessThanOrEqualTo(DateUtils.parseDate(quitTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            memberQuit_export(example, response);
            return null;
        }

        int count = memberQuitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberQuit> memberQuits = memberQuitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberQuits", memberQuits);

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

        if (StringUtils.isNotBlank(_quitTime)) {
            searchStr += "&_quitTime=" + _quitTime;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());

        return "party/memberQuit/memberQuit_page";
    }

    @RequiresPermissions("memberQuit:edit")
    @RequestMapping(value = "/memberQuit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_au(MemberQuit record, String _quitTime, HttpServletRequest request) {

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);

        record.setGrowTime(member.getGrowTime());

        if(StringUtils.isNotBlank(_quitTime))
            record.setQuitTime(DateUtils.parseDate(_quitTime, DateUtils.YYYY_MM_DD));
        if(member.getPartyId()!=null){
            Party party = partyService.findAll().get(member.getPartyId());
            record.setPartyName(party.getName());
        }
        if(member.getBranchId()!=null){
            Branch branch = branchService.findAll().get(member.getBranchId());
            record.setBranchName(branch.getName());
        }

        if (memberQuitMapper.selectByPrimaryKey(userId) == null) {

            record.setStatus(SystemConstants.RETIRE_QUIT_STATUS_CHECKED);
            record.setCreateTime(new Date());
            memberQuitService.quit(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加党员出党：%s", record.getUserId()));
        } else {

            memberQuitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新党员出党：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberQuit:edit")
    @RequestMapping("/memberQuit_au")
    public String memberQuit_au(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
            modelMap.put("memberQuit", memberQuit);

            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        return "party/memberQuit/memberQuit_au";
    }

/*
    @RequiresPermissions("memberQuit:del")
    @RequestMapping(value = "/memberQuit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            memberQuitService.del(userId);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除党员出党：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }
*/

    public void memberQuit_export(MemberQuitExample example, HttpServletResponse response) {

        List<MemberQuit> memberQuits = memberQuitMapper.selectByExample(example);
        int rownum = memberQuitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"账号ID"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberQuit memberQuit = memberQuits.get(i);
            String[] values = {
                        memberQuit.getUserId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "党员出党_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
