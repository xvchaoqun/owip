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
import org.springframework.util.Assert;
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
public class CadreResearchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch_page")
    public String cadreResearch_page(
            @RequestParam(defaultValue = CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY + "") Byte type,
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);

            /*CadreResearchExample example = new CadreResearchExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andResearchTypeEqualTo(CadreConstants.CADRE_RESEARCH_TYPE_DIRECT);
            example.setOrderByClause("start_time asc");
            List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
            modelMap.put("cadreResearchs", cadreResearchs);*/

        CadreInfo cadreInfo = cadreInfoService.get(cadreId, type);
        modelMap.put("cadreInfo", cadreInfo);

        /*if (type == CadreConstants.CADRE_INFO_TYPE_RESEARCH) {
            Map<String, HtmlFragment> htmlFragmentMap = htmlFragmentService.codeKeyMap();
            modelMap.put("researchInInfo", cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY));
            modelMap.put("researchDirectInfo", cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY));
            modelMap.put("bookInfo", cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY));
            modelMap.put("paperInfo", cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY));
            //modelMap.put("researchRewardInfo", cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD));


        } else {
            String key = null;
            switch (type) {
                case CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY: {
                    key = "hf_cadre_research_in_summary";
                    CadreResearchExample example = new CadreResearchExample();
                    example.createCriteria().andCadreIdEqualTo(cadreId)
                            .andResearchTypeEqualTo(CadreConstants.CADRE_RESEARCH_TYPE_IN)
                            .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                    modelMap.put("count", cadreResearchMapper.countByExample(example));
                }
                break;
                case CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY: {
                    key = "hf_cadre_research_direct_summary";
                    CadreResearchExample example = new CadreResearchExample();
                    example.createCriteria().andCadreIdEqualTo(cadreId)
                            .andResearchTypeEqualTo(CadreConstants.CADRE_RESEARCH_TYPE_DIRECT)
                            .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                    modelMap.put("count", cadreResearchMapper.countByExample(example));
                }
                break;
                case CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY: {
                    key = "hf_cadre_book_summary";
                    CadreBookExample example = new CadreBookExample();
                    example.createCriteria().andCadreIdEqualTo(cadreId)
                            .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                    modelMap.put("count", cadreBookMapper.countByExample(example));
                }
                break;
                case CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY: {
                    key = "hf_cadre_paper_summary";
                    CadrePaperExample example = new CadrePaperExample();
                    example.createCriteria().andCadreIdEqualTo(cadreId)
                            .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                    modelMap.put("count", cadrePaperMapper.countByExample(example));
                }
                break;
                *//*case CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD:
                    key = "hf_cadre_research_reward";
                    break;*//*
            }
            if (key != null) {

                Map<String, HtmlFragment> htmlFragmentMap = htmlFragmentService.codeKeyMap();
                modelMap.put("htmlFragment", htmlFragmentMap.get(key));
            }
        }*/

        String name = null;
        switch (type) {
            case CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY: {
                name = "research_direct";
            }
            break;
            case CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY: {
                name = "research_in";
            }
            break;
            case CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY: {
                name = "book";
            }
            break;
            case CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY: {
                name = "paper";
            }
            break;
            case CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD: {
                name = "research_reward";
            }
            break;
        }
        modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
        modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));

        modelMap.put("cadreResearchDirects", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_DIRECT));
        modelMap.put("cadreResearchIns", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_IN));
        modelMap.put("cadreBooks", cadreBookService.list(cadreId));
        modelMap.put("cadrePapers", cadrePaperService.list(cadreId));
        modelMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_RESEARCH));

        return "cadre/cadreResearch/cadreResearch_page";
    }

    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreReward_fragment")
    public String cadreReward_fragment(Integer cadreId, ModelMap modelMap) {

        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andRewardTypeEqualTo(CadreConstants.CADRE_REWARD_TYPE_RESEARCH)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("reward_time asc");
        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
        modelMap.put("cadreRewards", cadreRewards);

        return "cadre/cadreResearch/cadreReward_fragment";
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
        CadreResearchExample.Criteria criteria = example.createCriteria().andResearchTypeEqualTo(researchType)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping(value = "/cadreResearch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadreResearch record, String _startTime, String _endTime, HttpServletRequest request) {

        Assert.isTrue(record.getResearchType() != null, " researchType is null");
        Integer id = record.getId();

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYYMM));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYYMM));
        }

        if (id == null) {

            if (!toApply) {
                cadreResearchService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部科研项目：%s", record.getId()));
            } else {
                cadreResearchService.modifyApply(record, null, record.getResearchType(), false);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部科研项目：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreResearch _record = cadreResearchMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                cadreResearchService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部科研项目：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreResearchService.modifyApply(record, id, record.getResearchType(), false);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部科研项目：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreResearchService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部科研项目：%s", record.getId()));
                }
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping("/cadreResearch_au")
    public String cadreResearch_au(Integer id, int cadreId, Byte researchType, ModelMap modelMap) {

        if (id != null) {
            CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
            modelMap.put("cadreResearch", cadreResearch);
            if(cadreResearch!=null)
                researchType = cadreResearch.getResearchType();
        }
        modelMap.put("researchType", researchType);

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreResearch/cadreResearch_au";
    }

    /*@RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreResearchService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除干部科研项目：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreResearchService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部科研项目：%s", StringUtils.join(ids, ",")));
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

        String fileName = "干部科研项目_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
