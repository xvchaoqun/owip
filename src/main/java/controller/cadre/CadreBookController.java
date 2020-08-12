package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreBook;
import domain.cadre.CadreBookExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
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
public class CadreBookController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("cadreBook:list")
    @RequestMapping("/cadreBook_data")
    public void cadreBook_data(HttpServletResponse response,
                                 Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreBookExample example = new CadreBookExample();
        CadreBookExample.Criteria criteria = example.createCriteria()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("pub_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreBook_export(example, response);
            return;
        }

        int count = cadreBookMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreBook> cadreBooks = cadreBookMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreBooks);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreBook:edit")
    @RequestMapping(value = "/cadreBook_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreBook_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadreBook record, String _pubTime,  HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_pubTime)){
            record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            if(!toApply) {
                cadreBookService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部出版著作：%s", record.getId()));
            }else{
                cadreBookService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部出版著作：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreBook _record = cadreBookMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new OpException("数据请求错误，没有操作权限");
            }

            if(!toApply) {
                cadreBookService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部出版著作：%s", record.getId()));
            }else{
                if(_isUpdate==false) {
                    cadreBookService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部出版著作：%s", record.getId()));
                }else{
                    // 更新修改申请的内容
                    cadreBookService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部出版著作：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreBook:edit")
    @RequestMapping("/cadreBook_au")
    public String cadreBook_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreBook cadreBook = cadreBookMapper.selectByPrimaryKey(id);
            modelMap.put("cadreBook", cadreBook);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreBook/cadreBook_au";
    }

   /* @RequiresPermissions("cadreBook:del")
    @RequestMapping(value = "/cadreBook_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreBook_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreBookService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除干部出版著作：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreBook:del")
    @RequestMapping(value = "/cadreBook_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreBookService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部出版著作：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreBook_export(CadreBookExample example, HttpServletResponse response) {

        List<CadreBook> cadreBooks = cadreBookMapper.selectByExample(example);
        int rownum = cadreBookMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","出版日期","著作名称","类型", "出版社"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreBook cadreBook = cadreBooks.get(i);
            String[] values = {
                        cadreBook.getCadreId()+"",
                                            DateUtils.formatDate(cadreBook.getPubTime(), DateUtils.YYYY_MM_DD),
                                            cadreBook.getName(),
                                            CadreConstants.CADRE_BOOK_TYPE_MAP.get(cadreBook.getType()), cadreBook.getPublisher()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "干部出版著作_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
