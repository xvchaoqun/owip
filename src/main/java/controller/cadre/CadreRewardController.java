package controller.cadre;

import controller.BaseController;
import domain.Cadre;
import domain.CadreReward;
import domain.CadreRewardExample;
import domain.CadreRewardExample.Criteria;
import domain.SysUser;
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
import org.springframework.util.Assert;
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
public class CadreRewardController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreReward:list")
    @RequestMapping("/cadreReward")
    public String cadreReward() {

        return "index";
    }
    @RequiresPermissions("cadreReward:list")
    @RequestMapping("/cadreReward_page")
    public String cadreReward_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_cadre_reward") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    byte type, //  1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreRewardExample example = new CadreRewardExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreReward_export(example, response);
            return null;
        }

        int count = cadreRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReward> CadreRewards = cadreRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreRewards", CadreRewards);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        searchStr += "&type=" + type;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadre/cadreReward/cadreReward_page";
    }

    @RequiresPermissions("cadreReward:edit")
    @RequestMapping(value = "/cadreReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReward_au(CadreReward record,
                                 String _rewardTime, HttpServletRequest request) {

        Integer id = record.getId();

        Assert.isTrue(record.getType()!=null);

        if(StringUtils.isNotBlank(_rewardTime)){
            record.setRewardTime(DateUtils.parseDate(_rewardTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreRewardService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部教学奖励：%s", record.getId()));
        } else {

            cadreRewardService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部教学奖励：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReward:edit")
    @RequestMapping("/cadreReward_au")
    public String cadreReward_au(Integer id, byte type, //  1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
                                 int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreReward cadreReward = cadreRewardMapper.selectByPrimaryKey(id);
            modelMap.put("cadreReward", cadreReward);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreReward/cadreReward_au";
    }

    @RequiresPermissions("cadreReward:del")
    @RequestMapping(value = "/cadreReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreRewardService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部教学奖励：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReward:del")
    @RequestMapping(value = "/cadreReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreRewardService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部教学奖励：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReward:changeOrder")
    @RequestMapping(value = "/cadreReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreRewardService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部教学奖励调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreReward_export(CadreRewardExample example, HttpServletResponse response) {

        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
        int rownum = cadreRewardMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","日期","获得奖项","颁奖单位","排名"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreReward cadreReward = cadreRewards.get(i);
            String[] values = {
                        cadreReward.getCadreId()+"",
                                            DateUtils.formatDate(cadreReward.getRewardTime(), DateUtils.YYYY_MM_DD),
                                            cadreReward.getName()+"",
                                            cadreReward.getUnit(),
                                            cadreReward.getRank()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部教学奖励_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
