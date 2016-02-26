package controller.user;

import controller.BaseController;
import domain.Cadre;
import domain.PassportDraw;
import domain.PassportDrawExample;
import domain.PassportDrawExample.Criteria;
import domain.SysUser;
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
@RequestMapping("/user")
public class UserPassportDrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw")
    public String passportDraw() {

        return "index";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_page")
    public String passportDraw_page(@CurrentUser SysUser loginUser,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportDrawExample example = new PassportDrawExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

        int count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passportDraws", passportDraws);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "user/passportDraw/passportDraw_page";
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportDraw_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_au(@CurrentUser SysUser loginUser, PassportDraw record, String _applyDate,
                                  String _startDate, String _endDate,String _expectDate,
                                  String _handleDate, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_applyDate)){
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_expectDate)){
            record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleDate)){
            record.setHandleDate(DateUtils.parseDate(_handleDate, DateUtils.YYYY_MM_DD));
        }
        if (id == null) {
            int userId= loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            record.setCadreId(cadre.getId());

            record.setCreateTime(new Date());
            record.setStatus((byte)0);
            passportDrawService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加领取证件：%s", record.getId()));
        } else {
            record.setCadreId(null);

            passportDrawService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新领取证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportDraw_au")
    public String passportDraw_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            modelMap.put("passportDraw", passportDraw);
        }
        return "user/passportDraw/passportDraw_au";
    }
}
