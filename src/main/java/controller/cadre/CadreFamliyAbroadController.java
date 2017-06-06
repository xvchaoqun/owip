package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyAbroad;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreFamliyAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreFamliyAbroad:list")
    @RequestMapping("/cadreFamliyAbroad_data")
    public void cadreFamliyAbroad_data(HttpServletResponse response,
                                    Integer cadreId,
                                    Integer pageSize, Integer pageNo,
                                 @RequestParam(required = false, defaultValue = "0") int export) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
        CadreFamliyAbroadExample.Criteria criteria = example.createCriteria();
       // example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreFamliyAbroad_export(example, response);
            return;
        }

        int count = cadreFamliyAbroadMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreFamliyAbroad> records = cadreFamliyAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreFamliyAbroad:edit")
    @RequestMapping(value = "/cadreFamliyAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliyAbroad_au(CadreFamliyAbroad record, String _abroadTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_abroadTime)){
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, "yyyy-MM"));
        }

        if (id == null) {
            cadreFamliyAbroadService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加家庭成员海外情况：%s", record.getId()));
        } else {
            // 干部信息本人直接修改数据校验
            CadreFamliyAbroad _record = cadreFamliyAbroadMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }
            cadreFamliyAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新家庭成员海外情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamliyAbroad:edit")
    @RequestMapping("/cadreFamliyAbroad_au")
    public String cadreFamliyAbroad_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreFamliyAbroad cadreFamliyAbroad = cadreFamliyAbroadMapper.selectByPrimaryKey(id);
            modelMap.put("cadreFamliyAbroad", cadreFamliyAbroad);

            Integer famliyId = cadreFamliyAbroad.getFamliyId();
            CadreFamliy cadreFamliy = cadreFamliyMapper.selectByPrimaryKey(famliyId);
            modelMap.put("cadreFamliy", cadreFamliy);
        }

        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreFamliyAbroad/cadreFamliyAbroad_au";
    }

    /*@RequiresPermissions("cadreFamliyAbroad:del")
    @RequestMapping(value = "/cadreFamliyAbroad_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliyAbroad_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreFamliyAbroadService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除家庭成员海外情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreFamliyAbroad:del")
    @RequestMapping(value = "/cadreFamliyAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreFamliyAbroadService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除家庭成员海外情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreFamliyAbroad_export(CadreFamliyAbroadExample example, HttpServletResponse response) {

        List<CadreFamliyAbroad> cadreFamliyAbroads = cadreFamliyAbroadMapper.selectByExample(example);
        int rownum = cadreFamliyAbroadMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所选家庭成员", "移居类别", "移居国家", "现居住城市"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreFamliyAbroad cadreFamliyAbroad = cadreFamliyAbroads.get(i);
            String[] values = {
                    cadreFamliyAbroad.getFamliyId() + "",
                    cadreFamliyAbroad.getType() + "",
                    cadreFamliyAbroad.getCountry(),
                    cadreFamliyAbroad.getCity()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "家庭成员海外情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
