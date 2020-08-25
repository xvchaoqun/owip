package controller.sc.scPassport;

import controller.sc.ScBaseController;
import domain.abroad.Passport;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportHandExample;
import domain.sc.scPassport.ScPassportHandExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
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
import persistence.sc.IScDispatchCadre;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.ScConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScPassportHandController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportHand")
    public String scPassportHand(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                 Integer cadreId,
                                 ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(cadreId!=null){
            CadreView cadreView = cadreService.get(cadreId);
            modelMap.put("cadre", cadreView);
        }

        return "sc/scPassport/scPassportHand/scPassportHand_page";
    }

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportHand_data")
    @ResponseBody
    public void scPassportHand_data(HttpServletResponse response,
                                    @RequestParam(required = false, defaultValue = "1") Byte cls,
                                    Integer cadreId,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer[] ids, // 导出的记录
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScPassportHandExample example = new ScPassportHandExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("add_time desc");

        switch (cls) {
            case 1:
                criteria.andStatusEqualTo(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);
                break;
            case 2:
                criteria.andStatusEqualTo(ScConstants.SC_PASSPORTHAND_STATUS_HAND);
                break;
            case 3:
                criteria.andStatusEqualTo(ScConstants.SC_PASSPORTHAND_STATUS_ABOLISH);
                break;
        }

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            scPassportHand_export(example, response);
            return;
        }

        long count = scPassportHandMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPassportHand> records = scPassportHandMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scPassportHand.class, scPassportHandMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_au(ScPassportHand record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scPassportHandService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "添加新提任干部交证件：%s", record.getId()));
        } else {

            scPassportHandService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "更新新提任干部交证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_au")
    public String scPassportHand_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(id);
            modelMap.put("scPassportHand", scPassportHand);
        }
        return "sc/scPassport/scPassportHand/scPassportHand_au";
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_unabolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_unabolish(int id, HttpServletRequest request) {

        scPassportHandService.unabolish(id);
        logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "取消撤销新提任干部交证件：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_unhand", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_unhand(int id, HttpServletRequest request) {

        scPassportHandService.unhand(id);
        logger.info(addLog(LogConstants.LOG_SC_PASSPORT,
                "转移至未交证件：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_abolish(int id, String remark, HttpServletRequest request) {

        scPassportHandService.abolish(id, remark);
        logger.info(addLog(LogConstants.LOG_SC_PASSPORT,
                "撤销新提任干部交证件：%s，%s", id, remark));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_abolish")
    public String scPassportHand_abolish(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(id);
            modelMap.put("scPassportHand", scPassportHand);
        }
        return "sc/scPassport/scPassportHand/scPassportHand_abolish";
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_import(HttpServletRequest request, Integer handId) {

        if (handId != null) {

            scPassportHandService.importPassport(handId);
            logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "新提任干部证件入库：%s", handId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:del")
    @RequestMapping(value = "/scPassportHand_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scPassportHand_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            scPassportHandService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "批量删除新提任干部交证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 从任免文件中提取新提任干部
    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_dispatch_draw")
    @ResponseBody
    public void scPassportHand_dispatch_draw(int dispatchId, HttpServletResponse response) throws IOException {

        List<DispatchCadre> dispatchCadres = scPassportHandService.draw(dispatchId);
        // 已经添加的任免记录
        List<Integer> hasDispatchCadreIds = iScMapper.getScPassportHandList(dispatchId);
        Set<Integer> hasDispatchCadreIdSet = new HashSet<>(hasDispatchCadreIds);

        List<IScDispatchCadre> records = new ArrayList<>();
        for (DispatchCadre dispatchCadre : dispatchCadres) {

            IScDispatchCadre record = new IScDispatchCadre();
            //BeanUtils.copyProperties(record, dispatchCadre);
            try {
                PropertyUtils.copyProperties(record, dispatchCadre);
            }catch (Exception e) {
               logger.error("异常", e);
            }

            record.setHasImport(hasDispatchCadreIdSet.contains(dispatchCadre.getId()));

            if(passportService!=null) {
                Map<Integer, Passport> passportMap = passportService.findByCadreId(dispatchCadre.getCadreId());
                record.setPassports(new ArrayList<>(passportMap.values()));
            }
            records.add(record);
        }

        JSONUtils.write(response, records, "id",
                "user", "user.code", "user.realname",
                "dispatch","dispatch.dispatchCode", "hasImport", "passports", "passports.code", "passports.classId");
    }
    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_dispatch")
    public String scPassportHand_dispatch(ModelMap modelMap) throws IOException {

        return "sc/scPassport/scPassportHand/scPassportHand_dispatch";
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_dispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_dispatch(Integer[] dispatchCadreIds) {

        scPassportHandService.addDispatchCadres(dispatchCadreIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_selectCadres")
    public String scPassportHand_selectCadres(ModelMap modelMap) throws IOException {

        return "sc/scPassport/scPassportHand/scPassportHand_selectCadres";
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_selectCadres(Integer[] cadreIds) {

        scPassportHandService.addCadres(cadreIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_selectCadres_tree")
    @ResponseBody
    public Map scPassportHand_selectCadres_tree() throws IOException {

        Set<Byte> cadreStatusList = new HashSet(Arrays.asList(CadreConstants.CADRE_STATUS_CJ,
                CadreConstants.CADRE_STATUS_LEADER));
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<Cadre>(cadreService.getCadres()),
                cadreStatusList, null, null, true, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    public void scPassportHand_export(ScPassportHandExample example, HttpServletResponse response) {

        List<ScPassportHand> records = scPassportHandMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"关联账号|100", "新提任日期|100", "添加方式|100", "备注|100", "状态|100", "证件是否已入库|100", "添加时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPassportHand record = records.get(i);
            String[] values = {
                    record.getCadreId() + "",
                    DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                    record.getAddType() + "",
                    record.getRemark(),
                    record.getStatus() + "",
                    record.getIsKeep() + "",
                    DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "新提任干部交证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
