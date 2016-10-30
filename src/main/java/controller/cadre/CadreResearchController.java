package controller.cadre;

import controller.BaseController;
import domain.base.ContentTpl;
import domain.cadre.*;
import domain.sys.HtmlFragment;
import domain.sys.SysUser;
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
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreResearchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch")
    public String cadreResearch() {

        return "index";
    }

    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch_page")
    public String cadreResearch_page(
            @RequestParam(defaultValue = SystemConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY + "") Byte type,
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);

            /*CadreResearchExample example = new CadreResearchExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andResearchTypeEqualTo(SystemConstants.CADRE_RESEARCH_TYPE_DIRECT);
            example.setOrderByClause("start_time asc");
            List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
            modelMap.put("cadreResearchs", cadreResearchs);*/

        CadreInfo cadreInfo = cadreInfoService.get(cadreId, type);
        modelMap.put("cadreInfo", cadreInfo);

        if (type == SystemConstants.CADRE_INFO_TYPE_RESEARCH) {
            Map<String, HtmlFragment> htmlFragmentMap = htmlFragmentService.codeKeyMap();
            modelMap.put("researchInInfo", cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY));
            modelMap.put("researchDirectInfo", cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY));
            modelMap.put("bookInfo", cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_BOOK_SUMMARY));
            modelMap.put("paperInfo", cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_PAPER_SUMMARY));
            modelMap.put("researchRewardInfo", cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_RESEARCH_REWARD));
        } else {
            String key = null;
            switch (type) {
                case SystemConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY:
                    key = "hf_cadre_research_in_summary";
                    break;
                case SystemConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY:
                    key = "hf_cadre_research_direct_summary";
                    break;
                case SystemConstants.CADRE_INFO_TYPE_BOOK_SUMMARY:
                    key = "hf_cadre_book_summary";
                    break;
                case SystemConstants.CADRE_INFO_TYPE_PAPER_SUMMARY:
                    key = "hf_cadre_paper_summary";
                    break;
                /*case SystemConstants.CADRE_INFO_TYPE_RESEARCH_REWARD:
                    key = "hf_cadre_research_reward";
                    break;*/
            }
            if (key != null) {

                Map<String, HtmlFragment> htmlFragmentMap = htmlFragmentService.codeKeyMap();
                modelMap.put("htmlFragment", htmlFragmentMap.get(key));
            }
        }

        {
            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_RESEARCH);
            example.setOrderByClause("reward_time asc");
            List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
            modelMap.put("cadreRewards", cadreRewards);
        }
        return "cadre/cadreResearch/cadreResearch_page";
    }

    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch_data")
    public void cadreResearch_data(HttpServletResponse response,
                                   Integer cadreId,
                                   byte researchType, //  1,主持 2 参与
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreResearchExample example = new CadreResearchExample();
        CadreResearchExample.Criteria criteria = example.createCriteria().andResearchTypeEqualTo(researchType);
        example.setOrderByClause("start_time desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreResearch_export(example, response);
            return;
        }

        int count = cadreResearchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreResearchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping(value = "/cadreResearch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_au(CadreResearch record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreResearchService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部科研项目：%s", record.getId()));
        } else {

            cadreResearchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部科研项目：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping("/cadreResearch_au")
    public String cadreResearch_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
            modelMap.put("cadreResearch", cadreResearch);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreResearch/cadreResearch_au";
    }

    @RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreResearchService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部科研项目：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreResearchService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部科研项目：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreResearch_export(CadreResearchExample example, HttpServletResponse response) {

        List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
        int rownum = cadreResearchMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部", "项目起始时间", "项目结题时间", "项目名称", "项目类型", "委托单位"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreResearch cadreResearch = cadreResearchs.get(i);
            String[] values = {
                    cadreResearch.getCadreId() + "",
                    DateUtils.formatDate(cadreResearch.getStartTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(cadreResearch.getEndTime(), DateUtils.YYYY_MM_DD),
                    cadreResearch.getName(),
                    cadreResearch.getType(), cadreResearch.getUnit()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部科研项目_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
