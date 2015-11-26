package controller;

import domain.Cadre;
import domain.CadreTeachReward;
import domain.CadreTeachRewardExample;
import domain.CadreTeachRewardExample.Criteria;
import domain.SysUser;
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
public class CadreTeachRewardController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreTeachReward:list")
    @RequestMapping("/cadreTeachReward")
    public String cadreTeachReward() {

        return "index";
    }
    @RequiresPermissions("cadreTeachReward:list")
    @RequestMapping("/cadreTeachReward_page")
    public String cadreTeachReward_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreTeachRewardExample example = new CadreTeachRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreTeachReward_export(example, response);
            return null;
        }

        int count = cadreTeachRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreTeachReward> CadreTeachRewards = cadreTeachRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreTeachRewards", CadreTeachRewards);

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
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadreTeachReward/cadreTeachReward_page";
    }

    @RequiresPermissions("cadreTeachReward:edit")
    @RequestMapping(value = "/cadreTeachReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTeachReward_au(CadreTeachReward record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreTeachRewardService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部教学奖励：%s", record.getId()));
        } else {

            cadreTeachRewardService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部教学奖励：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTeachReward:edit")
    @RequestMapping("/cadreTeachReward_au")
    public String cadreTeachReward_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreTeachReward cadreTeachReward = cadreTeachRewardMapper.selectByPrimaryKey(id);
            modelMap.put("cadreTeachReward", cadreTeachReward);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadreTeachReward/cadreTeachReward_au";
    }

    @RequiresPermissions("cadreTeachReward:del")
    @RequestMapping(value = "/cadreTeachReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTeachReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreTeachRewardService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部教学奖励：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTeachReward:del")
    @RequestMapping(value = "/cadreTeachReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreTeachRewardService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部教学奖励：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTeachReward:changeOrder")
    @RequestMapping(value = "/cadreTeachReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTeachReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreTeachRewardService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部教学奖励调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreTeachReward_export(CadreTeachRewardExample example, HttpServletResponse response) {

        List<CadreTeachReward> cadreTeachRewards = cadreTeachRewardMapper.selectByExample(example);
        int rownum = cadreTeachRewardMapper.countByExample(example);

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

            CadreTeachReward cadreTeachReward = cadreTeachRewards.get(i);
            String[] values = {
                        cadreTeachReward.getCadreId()+"",
                                            DateUtils.formatDate(cadreTeachReward.getName(), DateUtils.YYYY_MM_DD),
                                            cadreTeachReward.getType(),
                                            cadreTeachReward.getUnit(),
                                            cadreTeachReward.getRank()+""
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
