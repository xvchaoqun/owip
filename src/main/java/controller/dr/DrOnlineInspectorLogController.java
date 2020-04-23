package controller.dr;

import domain.dr.*;
import domain.dr.DrOnlineInspectorLogExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dr")
public class DrOnlineInspectorLogController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineInspectorLog:list")
    @RequestMapping("/drOnlineInspectorLog_menu")
    public String drOnlineInspectorLog_menu(Integer onlineId,
                                       ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);
        return "dr/drOnlineInspectorLog/menu";
    }

    @RequiresPermissions("drOnlineInspectorLog:list")
    @RequestMapping("/drOnlineInspectorLog")
    public String drOnlineInspectorLog(Integer typeId,
                                       Integer onlineId,
                                       ModelMap modelMap,
                                       String unitName,
                                       HttpServletRequest request) {
        if (typeId != null) {
            DrOnlineInspectorType inspectorType = drOnlineInspectorTypeMapper.selectByPrimaryKey(typeId);
            modelMap.put("inspectorType", inspectorType);
        }

        modelMap.put("onlineId", onlineId);

        return "dr/drOnlineInspectorLog/drOnlineInspectorLog_page";
    }

    @RequiresPermissions("drOnlineInspectorLog:list")
    @RequestMapping("/drOnlineInspectorLog_data")
    @ResponseBody
    public void drOnlineInspectorLog_data(HttpServletResponse response,
                                    Integer id,
                                    Integer onlineId,
                                    Integer typeId,
                                    String unitName,
                                
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

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        Criteria criteria = example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause("id desc");

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (StringUtils.isNotBlank(unitName)) {
            UnitExample unitExample = new UnitExample();
            unitExample.createCriteria().andNameLike(SqlUtils.like(unitName));
            List<Unit> units = unitMapper.selectByExample(unitExample);
            List<Integer> unitIds = unitService.getUnitIdsLikeUnitName(units);
            if (units.size() > 0) {

                criteria.andUnitIdIn(unitIds);
            }else {
                criteria.andUnitIdEqualTo(-1);
            }
        }


        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drOnlineInspectorLog_export(example, response);
            return;
        }

        long count = drOnlineInspectorLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlineInspectorLog> records= drOnlineInspectorLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineInspectorLog.class, drOnlineInspectorLogMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping(value = "/drOnlineInspectorLog_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspectorLog_au(@CurrentUser SysUserView user,
                                          Integer onlineId,
                                          Integer addCount,
                                          Boolean isAppended,
                                          DrOnlineInspectorLog record, HttpServletRequest request) {


        isAppended = isAppended == null ? false : isAppended;
        Integer logId = drOnlineInspectorLogService.generateInspector(record.getTypeId(), record.getUnitId(), 2, isAppended, addCount, onlineId);
        logger.info(log( LogConstants.LOG_DR, "{1}创建参评人账号个别生成：{0}", logId, user.getUsername()));


        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping("/drOnlineInspectorLog_au")
    public String drOnlineInspectorLog_au(Integer onlineId, ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);

        Map<Integer, DrOnlineInspectorType> inspectorTypeMap = drOnlineInspectorTypeService.findAll();
        modelMap.put("inspectorTypeMap", inspectorTypeMap);
        Map<Integer, Unit> unitMap = unitService.getRunAll();
        modelMap.put("unitMap", unitMap);

        return "dr/drOnlineInspectorLog/drOnlineInspectorLog_au";
    }

    @RequiresPermissions("drOnlineInspectorLog:del")
    @RequestMapping(value = "/inspectorLog_changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map inspectorLog_changeStatus(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            drOnlineInspectorLogService.changeStatus(ids);
            logger.info(log( LogConstants.LOG_DR, "发布参评人账号：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping("/selectUnitIdsAndInspectorTypeIds")
    public String selectUnitIdsAndInspectorTypeIds(Integer onlineId,
                                                   ModelMap modelMap){

        modelMap.put("onlineId", onlineId);

        return "/dr/drOnlineInspectorLog/selectUnitIdsAndInspectorTypeIds";
    }

    //生成单位树
    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping("/selectUnits_tree")
    @ResponseBody
    public Map selectUnits_tree() throws IOException{

        //得到所有单位
        TreeNode tree = unitService.getTree(null);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    //生成单位类型树
    @RequiresPermissions("drOnlineInspectorLog:list")
    @RequestMapping("/selectedInspectorTypes_tree")
    @ResponseBody
    public Map selectedInspectorTypes_tree() throws IOException {

        // 线上推荐的‘可用’参评人身份
        Map<Integer, DrOnlineInspectorType> drOnlineInspectorTypeMap = drOnlineInspectorTypeService.findAll();
        TreeNode tree = drOnlineInspectorTypeService.getTree(drOnlineInspectorTypeMap.values(),
                null);
        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping("/inspector_gen")
    public String inspector_gen_page(Integer onlineId,
                                     String unitIds,
                                     String inspectorTypeIds,
                                     ModelMap modelMap){

        Map<Integer, Unit> unitMap = unitService.getRunAll();
        Map<Integer, DrOnlineInspectorType> inspectorTypeMap = drOnlineInspectorTypeService.findAll();
        List<Unit> units = new ArrayList<>();
        List<DrOnlineInspectorType> inspectorTypes = new ArrayList<>();

        for (String unitIdStr : unitIds.split(",")){
            units.add(unitMap.get(Integer.parseInt(unitIdStr)));
        }
        for (String inspectorIdStr : inspectorTypeIds.split(",")){
            inspectorTypes.add(inspectorTypeMap.get(Integer.parseInt(inspectorIdStr)));
        }
        modelMap.put("units", units);
        modelMap.put("inspectorTypes", inspectorTypes);

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        List<DrOnlineInspectorLog> inspectorLogs = drOnlineInspectorLogMapper.selectByExample(example);
        for (DrOnlineInspectorLog inspectorLog : inspectorLogs){
            modelMap.put("total_" + inspectorLog.getUnitId() + "_" + inspectorLog.getTypeId(), inspectorLog.getTotalCount());
        }

        modelMap.put("onlineId", onlineId);

        return "/dr/drOnlineInspectorLog/inspector_gen";
    }

    @RequiresPermissions("drOnlineInspectorLog:edit")
    @RequestMapping(value = "/inspector_gen", method = RequestMethod.POST)
    @ResponseBody
    public Map do_inspector_gen(@CurrentUser SysUserView user,
                                Integer onlineId,
                                HttpServletRequest request){

        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, DrOnlineInspectorType> inspectorTypeMap = drOnlineInspectorTypeService.findAll();

        for (Map.Entry<Integer, Unit> _unit : unitMap.entrySet()) {

            for (Map.Entry<Integer, DrOnlineInspectorType> _drOnlineInspectorTypeEntry : inspectorTypeMap.entrySet()) {

                String total = request.getParameter("total_" + _unit.getKey() + "_" + _drOnlineInspectorTypeEntry.getKey());
                if (NumberUtils.isDigits(total)){
                    Integer totalCount = Integer.valueOf(total);
                    if (totalCount > 0){
                        drOnlineInspectorLogService.generateInspector(Integer.valueOf(_drOnlineInspectorTypeEntry.getKey()), Integer.valueOf(_unit.getKey()), 1,
                                false, totalCount, onlineId);
                    }
                }
            }
        }
        logger.info(user.getUsername() + "create drOnlineInspectorLog.");
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorLog:del")
    @RequestMapping(value = "/drOnlineInspectorLog_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspectorLog_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            drOnlineInspectorLogService.del(id);
            logger.info(log( LogConstants.LOG_DR, "删除参评人账号生成记录：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspectorLog:del")
    @RequestMapping(value = "/drOnlineInspectorLog_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineInspectorLog_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            drOnlineInspectorLogService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除参评人账号生成记录：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //导出参评人账号
    public void drOnlineInspectorLog_export(DrOnlineInspectorLogExample example, HttpServletResponse response) {

        List<DrOnlineInspectorLog> records = drOnlineInspectorLogMapper.selectByExample(example);
        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(records.get(0).getOnlineId());
        int rownum = records.size();
        String[] titles = {"登录账号|150","登录密码|150","推荐人身份类型|100","所属单位|130","测评状态|100","分发状态|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnlineInspectorLog record = records.get(i);
            List<DrOnlineInspector> inspectors = drOnlineInspectorService.findByLogId(record.getId());
            if (null == inspectors)
                continue;
            for(DrOnlineInspector inspector : inspectors){
                Unit unit = unitMapper.selectByPrimaryKey(record.getUnitId());
                String[] value = {
                        inspector.getUsername(),
                        inspector.getPasswd(),
                        inspector.getInspectorType().getType(),
                        unit.getName(),
                        DrConstants.INSPECTOR_STATUS_MAP.get(inspector.getStatus()),
                        DrConstants.INSPECTOR_PUB_STATUS_MAP.get(inspector.getPubStatus())
                };
                valuesList.add(value);
            }
        }
        String fileName = String.format(drOnline.getCode() + "参评人账号(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
        logger.info(log( LogConstants.LOG_DR, drOnline.getCode() + "导出参评人账号"));
    }

    @RequestMapping("/drOnlineInspectorLog_selects")
    @ResponseBody
    public Map drOnlineInspectorLog_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        long count = drOnlineInspectorLogMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DrOnlineInspectorLog> records = drOnlineInspectorLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DrOnlineInspectorLog record:records){

                Map<String, Object> option = new HashMap<>();
                //option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
