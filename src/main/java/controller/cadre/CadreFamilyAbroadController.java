package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.*;
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
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CadreFamilyAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreFamilyAbroad:list")
    @RequestMapping("/cadreFamilyAbroad_data")
    public void cadreFamilyAbroad_data(HttpServletRequest request, HttpServletResponse response,
                                    Integer cadreId,
                                    Integer pageSize, Integer pageNo,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    @RequestParam(required = false, defaultValue = "0") Byte status,// 干部状态,供导出使用 1: 现任处级干部 6：现任校领导 8：现任科级干部
                                    Integer[] ids
                                    ) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
        CadreFamilyAbroadExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
       // example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
//            cadreFamilyAbroad_export(example, response);

            List<Integer> cadreIds = new ArrayList<>();
            if (ids != null && ids.length > 0) {
                cadreIds.addAll(Arrays.asList(ids));
                criteria.andCadreIdIn(Arrays.asList(ids));
            } else {
                CadreViewExample cadreViewExample = new CadreViewExample();
                cadreViewExample.createCriteria().andStatusEqualTo(status);
                List<CadreView> list = cadreViewMapper.selectByExample(cadreViewExample);
                cadreIds = list.stream().map(appPermissionVo->appPermissionVo.getId()).collect(Collectors.toList());
                criteria.andCadreIdIn(cadreIds);
            }
            cadreFamilyAbroadService.cadreFamilyAbroadExport(example, cadreIds, request, response);
            return;
        }

        long count = cadreFamilyAbroadMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreFamilyAbroad> records = cadreFamilyAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreFamilyAbroad:edit")
    @RequestMapping(value = "/cadreFamilyAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamilyAbroad_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreFamilyAbroad record, String _abroadTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_abroadTime)){
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, "yyyy-MM"));
        }

        if (id == null) {

            if (!toApply) {
                cadreFamilyAbroadService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加家庭成员海外情况：%s", record.getId()));
            } else {
                cadreFamilyAbroadService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-家庭成员海外情况：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreFamilyAbroad _record = cadreFamilyAbroadMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                cadreFamilyAbroadService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新家庭成员海外情况：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreFamilyAbroadService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-家庭成员海外情况：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreFamilyAbroadService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-家庭成员海外情况：%s", record.getId()));
                }
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamilyAbroad:edit")
    @RequestMapping("/cadreFamilyAbroad_au")
    public String cadreFamilyAbroad_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreFamilyAbroad cadreFamilyAbroad = cadreFamilyAbroadMapper.selectByPrimaryKey(id);
            modelMap.put("cadreFamilyAbroad", cadreFamilyAbroad);

            Integer familyId = cadreFamilyAbroad.getFamilyId();
            CadreFamily cadreFamily = cadreFamilyMapper.selectByPrimaryKey(familyId);
            modelMap.put("cadreFamily", cadreFamily);
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreFamilyAbroad/cadreFamilyAbroad_au";
    }

    /*@RequiresPermissions("cadreFamilyAbroad:del")
    @RequestMapping(value = "/cadreFamilyAbroad_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamilyAbroad_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreFamilyAbroadService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除家庭成员海外情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreFamilyAbroad:del")
    @RequestMapping(value = "/cadreFamilyAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreFamilyAbroadService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除家庭成员海外情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreFamilyAbroad_export(CadreFamilyAbroadExample example, HttpServletResponse response) {

        List<CadreFamilyAbroad> cadreFamilyAbroads = cadreFamilyAbroadMapper.selectByExample(example);
        long rownum = cadreFamilyAbroadMapper.countByExample(example);

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

            CadreFamilyAbroad cadreFamilyAbroad = cadreFamilyAbroads.get(i);
            String[] values = {
                    cadreFamilyAbroad.getFamilyId() + "",
                    cadreFamilyAbroad.getType() + "",
                    cadreFamilyAbroad.getCountry(),
                    cadreFamilyAbroad.getCity()
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
