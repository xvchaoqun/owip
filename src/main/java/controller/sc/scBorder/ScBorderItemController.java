package controller.sc.scBorder;

import controller.sc.ScBaseController;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.sc.scBorder.ScBorderItem;
import domain.sc.scBorder.ScBorderItemView;
import domain.sc.scBorder.ScBorderItemViewExample;
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
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sc")
public class ScBorderItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scBorder:list")
    @RequestMapping("/scBorderItem")
    public String scBorderItem(@RequestParam(defaultValue = "1") Integer cls,
                               Integer cadreId,
                               ModelMap modelMap) {
        modelMap.put("cls", cls);
        modelMap.put("cadre", cadreService.findAll().get(cadreId));

        return "sc/scBorder/scBorderItem/scBorderItem_page";
    }

    @RequiresPermissions("scBorder:list")
    @RequestMapping("/scBorderItem_data")
    @ResponseBody
    public void scBorderItem_data(HttpServletResponse response,
                                    Integer year,
                                    Integer borderId,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScBorderItemViewExample example = new ScBorderItemViewExample();
        ScBorderItemViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, record_date desc, id asc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (borderId!=null) {
            criteria.andBorderIdEqualTo(borderId);
        }
        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scBorderItem_export(example, response);
            return;
        }

        long count = scBorderItemViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScBorderItemView> records= scBorderItemViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scBorderItem.class, scBorderItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping(value = "/scBorderItem_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorderItem_add(ScBorderItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (scBorderItemService.idDuplicate(id, record.getBorderId(), record.getCadreId())) {
            return failed("添加重复");
        }

        scBorderItemService.insertSelective(record);
        logger.info(addLog( LogConstants.LOG_SC_BORDER, "添加报备干部：%s", record.getCadreId()));

        return success(FormUtils.SUCCESS);
    }

    // 从离任干部库中添加
    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorderItem_add")
    public String scBorderItem_add(ModelMap modelMap) {

        return "sc/scBorder/scBorderItem/scBorderItem_add";
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping(value = "/scBorderItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorderItem_au(ScBorderItem record, HttpServletRequest request) {

        record.setCadreId(null);
        scBorderItemService.updateByPrimaryKeySelective(record);
        logger.info(addLog( LogConstants.LOG_SC_BORDER, "更新报备干部：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorderItem_au")
    public String scBorderItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScBorderItem scBorderItem = scBorderItemMapper.selectByPrimaryKey(id);
            modelMap.put("scBorderItem", scBorderItem);
        }
        return "sc/scBorder/scBorderItem/scBorderItem_au";
    }

    @RequiresPermissions("scBorder:del")
    @RequestMapping(value = "/scBorderItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorderItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scBorderItemService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_BORDER, "删除报备干部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scBorder:del")
    @RequestMapping(value = "/scBorderItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scBorderItem_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scBorderItemService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_BORDER, "批量删除报备干部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping("/scBorderItem_addDispatchs")
    public String scBorderItem_addDispatchs(HttpServletResponse response,
                                         String type,
                                         int id, int cadreId, ModelMap modelMap) {


        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已关联的干部任免文件ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>(); // 已关联的干部任免文件

        ScBorderItem scBorderItem = scBorderItemMapper.selectByPrimaryKey(id);
        String _dispatchCadreIds = scBorderItem.getDispatchCadreIds();
        if(StringUtils.isNotBlank(_dispatchCadreIds)) {
            int[] dispatchCadreIds = Arrays.stream(_dispatchCadreIds.split(","))
                    .mapToInt(s -> Integer.parseInt(s)).toArray();

            DispatchCadreExample example = new DispatchCadreExample();
            example.createCriteria().andIdIn(Arrays.stream(dispatchCadreIds).boxed().collect(Collectors.toList()));
            List<DispatchCadre> selectedDispatchCadres = dispatchCadreMapper.selectByExample(example);
            for (DispatchCadre dispatchCadre : selectedDispatchCadres) {

                dispatchCadreIdSet.add(dispatchCadre.getId());
                relateDispatchCadres.add(dispatchCadre);
            }
        }

        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");

            List<DispatchCadre> dispatchCadres =
            iDispatchMapper.selectDispatchCadreList(cadreId, DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT);
            modelMap.put("dispatchCadres", dispatchCadres);

        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "sc/scBorder/scBorderItem/scBorderItem_addDispatchs";
    }

    @RequiresPermissions("scBorder:edit")
    @RequestMapping(value = "/scBorderItem_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scBorderItem_addDispatchs(HttpServletRequest request,
                                         int id,
                                         Integer[] ids, ModelMap modelMap) {

        if(ids==null || ids.length==0){
            commonMapper.excuteSql("update sc_border_item set dispatch_cadre_ids=null where id=" + id);
        }else {
            ScBorderItem record = new ScBorderItem();
            record.setId(id);
            record.setDispatchCadreIds(StringUtils.join(ids, ","));
            scBorderItemMapper.updateByPrimaryKeySelective(record);
        }
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改报备干部%s-关联发文：%s", id,
                StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    
    public void scBorderItem_export(ScBorderItemViewExample example, HttpServletResponse response) {

        List<ScBorderItemView> records = scBorderItemViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属干部|100","报备时所作单位及职务|100","行政级别|100","报备涉及的任免文件|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScBorderItemView record = records.get(i);
            String[] values = {
                record.getCadreId()+"",
                            record.getTitle(),
                            record.getAdminLevel()+"",
                            record.getDispatchCadreIds(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "报备干部_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
