package controller.cadre;

import controller.BaseController;
import domain.cadre.CadrePunish;
import domain.cadre.CadrePunishExample;
import domain.cadre.CadrePunishExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
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
public class CadrePunishController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*@RequiresPermissions("cadrePunish:list")
    @RequestMapping("/cadrePunish_page")
    public String cadrePunish_page(Integer cadreId, ModelMap modelMap) {

        return "cadre/cadrePunish/cadrePunish_page";
    }*/

    @RequiresPermissions("cadrePunish:list")
    @RequestMapping("/cadrePunish_data")
    public void cadrePunish_data(HttpServletResponse response,
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

        CadrePunishExample example = new CadrePunishExample();
        Criteria criteria = example.createCriteria()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("punish_time desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadrePunish_export(example, response);
            return;
        }

        long count = cadrePunishMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePunish> cadrePunishs = cadrePunishMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePunishs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadrePunish:edit")
    @RequestMapping(value = "/cadrePunish_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePunish_au(CadrePunish record, String _punishTime, MultipartFile _proof, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_punishTime)) {

            boolean rewardOnlyYear = CmTag.getBoolProperty("rewardOnlyYear");
            if (rewardOnlyYear) {
                record.setPunishTime(DateUtils.parseDate(_punishTime, "yyyy"));
            } else {
                record.setPunishTime(DateUtils.parseDate(_punishTime, "yyyy.MM"));
            }
        }
        
        if (_proof != null) {

            String originalFilename = _proof.getOriginalFilename();

            String savePath = upload(_proof, "cadre_punish");

            record.setProofFilename(originalFilename);
            record.setProof(savePath);
        }

        record.setListInAd(BooleanUtils.isTrue(record.getListInAd()));
        if (id == null) {

            cadrePunishService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部受处分情况：%s", record.getId()));
        } else {
            cadrePunishService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部受处分情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePunish:edit")
    @RequestMapping("/cadrePunish_au")
    public String cadrePunish_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePunish cadrePunish = cadrePunishMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePunish", cadrePunish);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadrePunish/cadrePunish_au";
    }

    @RequiresPermissions("cadrePunish:del")
    @RequestMapping(value = "/cadrePunish_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cadrePunishService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部受处分情况：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    public void cadrePunish_export(CadrePunishExample example, HttpServletResponse response) {

        List<CadrePunish> cadrePunishs = cadrePunishMapper.selectByExample(example);
        long rownum = cadrePunishMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        boolean rewardOnlyYear = CmTag.getBoolProperty("rewardOnlyYear");

        String[] titles = {"所属干部", rewardOnlyYear ? "处分年份" : "处分日期", "受何种处分", "处分单位"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            String rewardTime = "";
            CadrePunish cadrePunish = cadrePunishs.get(i);
            if (rewardOnlyYear) {
                rewardTime = DateUtils.formatDate(cadrePunish.getPunishTime(), "yyyy");
            } else {
                rewardTime = DateUtils.formatDate(cadrePunish.getPunishTime(), "yyyy.MM");
            }

            String[] values = {
                    cadrePunish.getCadreId() + "",
                    rewardTime,
                    cadrePunish.getName() + "",
                    cadrePunish.getUnit()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "干部受处分情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
