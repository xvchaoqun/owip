package controller.dp;

import controller.global.OpException;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchUnit;
import domain.dp.DpParty;
import domain.dp.DpPartyMemberGroup;
import domain.dp.DpPartyMemberGroupExample;
import domain.dp.DpPartyMemberGroupExample.Criteria;
import mixin.DpPartyMemberGroupOptionMiXin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpPartyMemberGroupController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpPartyMemberGroup:list")
    @RequestMapping("/dpPartyMemberGroup_view")
    public String dpPartyMemberGroup_view(){

        return "dp/dpPartyMemberGroup/dpPartyMemberGroup_view";
    }

    @RequiresPermissions("dpPartyMemberGroup:list")
    @RequestMapping("/dpPartyMemberGroup")
    public String dpPartyMemberGroup(ModelMap modelMap,
            Integer partyId,
            Integer userId,
            @RequestParam(required = false, value = "typeIds") Integer typeIds,
            @RequestParam(required = false, defaultValue = "1") Byte status) {

        modelMap.put("status", status);

        if (null != partyId){
            modelMap.put("dpParty", dpPartyService.findAll().get(partyId));
        }
        if (null != userId){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (status == 2){
            if (null != typeIds){
                List<Integer> _typeIds = Arrays.asList(typeIds);
                modelMap.put("selectedTypeIds", _typeIds);
            }
            return "dp/dpPartyMemberGroup/dpPartyMember";
        }

        return "dp/dpPartyMemberGroup/dpPartyMemberGroup_page";
    }

    @RequiresPermissions("dpPartyMemberGroup:list")
    @RequestMapping("/dpPartyMemberGroup_data")
    @ResponseBody
    public void dpPartyMemberGroup_data(HttpServletResponse response,
                                        @RequestParam(required = false, defaultValue = "1") Byte status,
                                        Integer id,
                                        String name,
                                        Integer partyId,
                                        Boolean isPresent,
                                        String groupSession,
                                        @RequestDateRange DateRange _appointTime,
                                        @RequestDateRange DateRange _tranTime,
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

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_present desc, sort_order desc, appoint_time desc");
        //=======权限
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));
        criteria.andIsDeletedEqualTo(status == -1);

        if (id != null){
            criteria.andIdEqualTo(id);
        }
        if (null != isPresent){
            criteria.andIsPresentEqualTo(isPresent);
        }
        if (null != name){
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (null != _appointTime.getStart()){
            criteria.andAppointTimeGreaterThanOrEqualTo(_appointTime.getStart());
        }
        if (null != _appointTime.getEnd()){
            criteria.andAppointTimeLessThanOrEqualTo(_appointTime.getEnd());
        }
        if (null != _tranTime.getStart()){
            criteria.andTranTimeGreaterThanOrEqualTo(_tranTime.getStart());
        }
        if (null != _tranTime.getEnd()){
            criteria.andTranTimeLessThanOrEqualTo(_tranTime.getEnd());
        }
        if (null != groupSession){
            criteria.andGroupSessionEqualTo(groupSession);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpPartyMemberGroup_export(example, response);
            return;
        }

        long count = dpPartyMemberGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpPartyMemberGroup> records= dpPartyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(DpPartyMemberGroup.class, DpPartyMemberGroupOptionMiXin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpPartyMemberGroup:edit")
    @RequestMapping(value = "/dpPartyMemberGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMemberGroup_au(DpPartyMemberGroup record,
                                        String _tranTime,
                                        String _actualTranTime,
                                        String _appointTime,
                                        String groupSession,
                                        HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_tranTime)) {
            record.setTranTime(DateUtils.parseDate(_tranTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_actualTranTime)) {
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_appointTime)) {
            record.setAppointTime(DateUtils.parseDate(_appointTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(groupSession)){
            record.setGroupSession(groupSession);
        }

        record.setIsPresent((record.getIsPresent() == null) ? false : record.getIsPresent());

        if (id == null) {
            
            dpPartyMemberGroupService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加民主党派委员会：{0}", record.getId()));
        } else {
            if (record.getFid() != null && record.getFid().intValue() == record.getId()){
                return failed("不能选择自身为上一届委员会");
            }

            dpPartyMemberGroupService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新民主党派委员会：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMemberGroup:edit")
    @RequestMapping("/dpPartyMemberGroup_au")
    public String dpPartyMemberGroup_au(Integer id, Integer partyId, ModelMap modelMap) {

        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        modelMap.put("dpPartyMap", dpPartyMap);

        if (id != null) {
            DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("dpPartyMemberGroup", dpPartyMemberGroup);
            if (dpPartyMemberGroup.getFid() !=null ){
                modelMap.put("fDpPartyMemberGroup",dpPartyMemberGroupMapper.selectByPrimaryKey(dpPartyMemberGroup.getFid()));
            }

            DpParty dpParty = dpPartyMap.get(dpPartyMemberGroup.getPartyId());
            modelMap.put("dpParty", dpParty);
            Integer dispatchUnitId = dpPartyMemberGroup.getDispatchUnitId();
            if (dispatchUnitId != null){
                DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
                if (dispatchUnit != null){
                    modelMap.put("dispatch", dispatchUnit.getDispatch());
                }
            }
        } else if (partyId != null){
            DpParty dpParty = dpPartyMap.get(partyId);
            modelMap.put("dpParty",dpParty);
        }
        return "dp/dpPartyMemberGroup/dpPartyMemberGroup_au";
    }

    @RequiresPermissions("dpPartyMemberGroup:del")
    @RequestMapping(value = "/dpPartyMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpPartyMemberGroupService.del(id);
            logger.info(log( LogConstants.LOG_GROW, "删除基层党组织领导班子：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMemberGroup:del")
    @RequestMapping(value = "/dpPartyMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPartyMemberGroup_batchDel(HttpServletRequest request,
                                           @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                                           @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpPartyMemberGroupService.batchDel(ids,isDeleted);
            logger.info(log( LogConstants.LOG_DPPARTY, "撤销民主党派委员会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //完全删除已撤销的委员会
    @RequiresPermissions("dpPartyMemberGroup:realDel")
    @RequestMapping(value = "/dpPartyMemberGroup_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPartyMemberGroup_realDel(HttpServletRequest request,
                                          @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap){
        if (null != ids && ids.length > 0){
            dpPartyMemberGroupService.realDel(ids);
            logger.info(addLog(LogConstants.LOG_DPPARTY,"删除民主党派委员会：%s"),
                        StringUtils.join(ids, ","));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMemberGroup:changeOrder")
    @RequestMapping(value = "/dpPartyMemberGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMemberGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpPartyMemberGroupService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "基层党组织领导班子调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpPartyMemberGroup_export(DpPartyMemberGroupExample example, HttpServletResponse response) {

        List<DpPartyMemberGroup> records = dpPartyMemberGroupMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|250|left", "所属分党委|250|left", "是否现任班子|70", "委员会届数|70", "应换届时间|100", "实际换届时间|110", "任命时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpPartyMemberGroup record = records.get(i);
            Integer partyId = record.getPartyId();

            String dispatchCode = "";
            DispatchUnit dispatchUnit = CmTag.getDispatchUnit(record.getDispatchUnitId());
            if (dispatchUnit != null){
                Dispatch dispatch = dispatchUnit.getDispatch();
                if (dispatch != null){
                    dispatchCode = CmTag.getDispatchCode(dispatch.getCode(),dispatch.getDispatchTypeId(),dispatch.getYear());
                }
            }

            String[] values = {
                            record.getName(),
                            partyId == null ? "" : dpPartyService.findAll().get(partyId).getName(),
                            BooleanUtils.isTrue(record.getIsPresent()) ? "是" : "否",
                            record.getGroupSession(),
                            DateUtils.formatDate(record.getTranTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYY_MM_DD),
            };
            valuesList.add(values);
        }
        String fileName = String.format("民主党派委员会(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpPartyMemberGroup_selects")
    @ResponseBody
    public Map dpPartyMemberGroup_selects(Integer partyId,Boolean auth,
                                          Boolean del,
                                          Boolean isPresent,
                                          Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if (del != null){
            criteria.andIsDeletedEqualTo(del);
        }
        if (isPresent != null){
            criteria.andIsPresentEqualTo(isPresent);
        }
        if (null !=partyId){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (StringUtils.isNotBlank(searchStr)){
            criteria.andIdEqualTo(Integer.valueOf(searchStr.trim()));
        }

        //======权限
        if (BooleanUtils.isTrue(auth)){
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
                List<Integer> dpPartyList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());
                if (dpPartyList.size() > 0)
                    criteria.andPartyIdIn(dpPartyList);
                else
                    criteria.andPartyIdIsNull();
            }
        }

        long count = dpPartyMemberGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpPartyMemberGroup> records = dpPartyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpPartyMemberGroup record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");
                option.put("partyId", record.getPartyId());
                option.put("del", record.getIsDeleted());
                option.put("isPresent", record.getIsPresent());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("dpPartyMemberGroup:edit")
    @RequestMapping("/dpPartyMemberGroup_import")
    public String dpPartyMemberGroup_import(ModelMap modelMap){

        return "dp/dpPartyMemberGroup/dpPartyMemberGroup_import";
    }

    @RequiresPermissions("dpPartyMemberGroup:edit")
    @RequestMapping(value = "/dpPartyMemberGroup_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMemberGroup_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpPartyMemberGroup> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            DpPartyMemberGroup record = new DpPartyMemberGroup();
            row++;
            String name = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(name)){
                throw new OpException("第{0}行委员会名称为空", row);
            }
            record.setName(name);

            String dpPartyCode = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(dpPartyCode)){
                throw new OpException("第{0}行所属民主党派编码为空", row);
            }
            DpParty dpParty = dpPartyService.getByCode(dpPartyCode);
            if (dpParty == null){
                throw new OpException("第{0}行所属民主党派编码[{1}]不存在", row, dpPartyCode);
            }
            record.setPartyId(dpParty.getId());
            record.setIsPresent(StringUtils.contains(xlsRow.get(3),"是"));
            String appointTime = StringUtils.trimToNull(xlsRow.get(4));
            record.setAppointTime(DateUtils.parseStringToDate(appointTime));
            String tranTime = StringUtils.trimToNull(xlsRow.get(5));
            record.setTranTime(DateUtils.parseStringToDate(tranTime));
            if (record.getIsPresent()){
                String actualTranTime = StringUtils.trimToNull(xlsRow.get(6));
                record.setActualTranTime(DateUtils.parseStringToDate(actualTranTime));
            }
            record.setIsDeleted(false);
            records.add(record);
        }

        Collections.reverse(records);//逆序排列，保证导入的顺序正确

        int addCount = dpPartyMemberGroupService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("totalCount", totalCount);

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入民主党派委员会成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

}
