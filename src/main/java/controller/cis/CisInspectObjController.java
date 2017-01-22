package controller.cis;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cis.CisInspectObj;
import domain.cis.CisInspectObjExample;
import domain.cis.CisInspectObjExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CisInspectObjController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj")
    public String cisInspectObj() {

        return "index";
    }

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj_page")
    public String cisInspectObj_page(HttpServletResponse response, ModelMap modelMap) {

        return "cis/cisInspectObj/cisInspectObj_page";
    }

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj_data")
    public void cisInspectObj_data(HttpServletResponse response,
                                   Integer cadreId,
                                   Integer typeId,
                                   Integer seq,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CisInspectObjExample example = new CisInspectObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("inspect_date desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (seq != null) {
            criteria.andSeqEqualTo(seq);
        }

        int count = cisInspectObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspectObj> cisInspectObjs = cisInspectObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisInspectObjs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisInspectObj.class, cisInspectObjMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_au(CisInspectObj record,
                                   String _inspectDate,
                                   HttpServletRequest request) {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_inspectDate)) {
            record.setInspectDate(DateUtils.parseDate(_inspectDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            if (record.getSeq() == null) {
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }
            cisInspectObjService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部考察材料：%s", record.getId()));
        } else {

            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            if (cisInspectObj.getTypeId().intValue() != record.getTypeId()
                    || cisInspectObj.getYear().intValue() != record.getYear()
                    ) { // 修改了类型或年份，要修改编号
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }

            cisInspectObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察材料：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_au")
    public String cisInspectObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            modelMap.put("cisInspectObj", cisInspectObj);
            Map<Integer, Cadre> cadreMap = cadreService.findAll();
            modelMap.put("cadre", cadreMap.get(cisInspectObj.getCadreId()));
            modelMap.put("chiefCadre", cadreMap.get(cisInspectObj.getChiefCadreId()));
        }

        return "cis/cisInspectObj/cisInspectObj_au";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_summary")
    public String cisInspectObj_summary(Integer objId, ModelMap modelMap) {

        if (objId != null) {
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
            modelMap.put("cisInspectObj", cisInspectObj);
        }

        return "cis/cisInspectObj/cisInspectObj_summary";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_summary(int objId, String summary,
                                        @RequestParam(value = "unitIds[]", required = false) Integer[] unitIds,
                                   HttpServletRequest request) {

        cisInspectObjService.updateSummary(objId, summary, unitIds);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察材料、考察单位：%s",objId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspectObj:del")
    @RequestMapping(value = "/cisInspectObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisInspectObjService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部考察材料：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
