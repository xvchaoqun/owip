package controller.sc.scBorder;

import controller.sc.ScBaseController;
import domain.cadre.CadreView;
import domain.sc.scBorder.ScBorder;
import domain.sc.scBorder.ScBorderView;
import domain.sc.scBorder.ScBorderViewExample;
import mixin.MixinUtils;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
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
public class ScBorderController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scBorder:list")
    @RequestMapping("/scBorder")
    public String scBorder(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/sc/scBorderItem";
        }

        return "sc/scBorder/scBorder/scBorder_page";
    }

    @RequiresPermissions("scBorder:list")
    @RequestMapping("/scBorder_data")
    @ResponseBody
    public void scBorder_data(HttpServletResponse response,
                                    Integer year,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScBorderViewExample example = new ScBorderViewExample();
        ScBorderViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("record_date desc, id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scBorder_export(example, response);
            return;
        }

        long count = scBorderViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScBorderView> records= scBorderViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scBorder.class, scBorderMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping(value = "/scBorder_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorder_au(ScBorder record,
                              MultipartFile _addFile,
                              MultipartFile _changeFile,
                              MultipartFile _deleteFile,
                              MultipartFile _recordFile,
                              HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        record.setAddFile(uploadPdf(_addFile, "scBorder"));
        record.setChangeFile(uploadPdf(_changeFile, "scBorder"));
        record.setDeleteFile(uploadPdf(_deleteFile, "scBorder"));
        record.setRecordFile(upload(_recordFile, "scBorder"));

        if (id == null) {
            
            scBorderService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_BORDER, "添加出入境备案：%s", record.getId()));
        } else {

            scBorderService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_BORDER, "更新出入境备案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorder_au")
    public String scBorder_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScBorder scBorder = scBorderMapper.selectByPrimaryKey(id);
            modelMap.put("scBorder", scBorder);
        }
        return "sc/scBorder/scBorder/scBorder_au";
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping(value = "/scBorder_selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorder_selectCadres(Integer borderId,
                                  byte type,
                                  @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds,
                                  HttpServletRequest request) {

        scBorderService.updateCadreIds(borderId, type, cadreIds);
        logger.info(addLog( LogConstants.LOG_SC_BORDER, "更新报备干部：%s", type));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorder_selectCadres_tree")
    @ResponseBody
    public Map scBorder_selectCadres_tree() throws IOException {

        Set<Byte> cadreStatusList = new HashSet(Arrays.asList(CadreConstants.CADRE_STATUS_CJ,
                CadreConstants.CADRE_STATUS_LEADER));
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                cadreStatusList, null, null, true, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorder_selectCadres")
    public String scBorder_selectCadres(Integer borderId, ModelMap modelMap) {


        return "sc/scBorder/scBorder/scBorder_selectCadres";
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorder_cadres")
    public String scBorder_cadres(Integer borderId, ModelMap modelMap) {

        return "sc/scBorder/scBorder/scBorder_cadres";
    }

    @RequiresPermissions("scBorder:del")
    @RequestMapping(value = "/scBorder_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scBorder_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scBorderService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_BORDER, "批量删除出入境备案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scBorder_export(ScBorderViewExample example, HttpServletResponse response) {

        List<ScBorderView> records = scBorderViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","报备日期|100","报备表|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScBorderView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getRecordDate()+"",
                            record.getAddFile(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "出入境备案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
