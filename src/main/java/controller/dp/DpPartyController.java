package controller.dp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.dispatch.DispatchUnit;
import domain.dp.*;
import domain.dp.DpPartyExample.Criteria;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
public class DpPartyController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty")
    public String dpParty(HttpServletResponse response,
                            Integer id,
                            ModelMap modelMap,
                            @RequestParam(required = false, defaultValue = "1") Byte cls) throws Exception{

        DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
        modelMap.put("dpParty", dpParty);
        modelMap.put("cls",cls);

        return "dp/dpParty/dpParty_page";
    }

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty_data")
    @ResponseBody
    public void dpParty_data(HttpServletResponse response,
                             @RequestParam(required = false, defaultValue = "1") byte cls,//正在运转
                             Integer id,
                             String code,
                             String name,
                             Integer unitId,
                             Integer classId,
                             String phone,
                             Long presentGroupCount,
                             @RequestDateRange DateRange _foundTime,
                             @RequestDateRange DateRange deleteTime,
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

        DpPartyViewExample example = new DpPartyViewExample();
        DpPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("sort_order desc"));
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

        criteria.andIsDeletedEqualTo(cls == 2);

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andPhoneLike(SqlUtils.like(phone));
        }
        if (_foundTime.getStart()!=null) {
            criteria.andFoundTimeGreaterThanOrEqualTo(_foundTime.getStart());
        }
        if (_foundTime.getEnd()!=null) {
            criteria.andFoundTimeLessThanOrEqualTo(_foundTime.getEnd());
        }
        if (deleteTime.getStart()!=null) {
            criteria.andDeleteTimeGreaterThanOrEqualTo(deleteTime.getStart());
        }
        if (deleteTime.getEnd()!=null) {
            criteria.andDeleteTimeGreaterThanOrEqualTo(deleteTime.getEnd());
        }
        if (presentGroupCount != null){
            criteria.andPresentGroupCountEqualTo(presentGroupCount);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpParty_export(cls, example, response);
            return;
        }

        long count = dpPartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpPartyView> records= dpPartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dp.class, dpPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping(value = "/dpParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map dpParty_au(Integer id, DpParty record, String deleteTime, String _foundTime, HttpServletRequest request) {

        Integer partyId = record.getId();

        if (dpPartyService.idDuplicate(partyId, record.getCode())) {
            return failed("添加重复");
        }
        if (StringUtils.isNotBlank(_foundTime)){
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(deleteTime)){
            record.setDeleteTime(DateUtils.parseDate(deleteTime, DateUtils.YYYYMMDD_DOT));
        }
        if (partyId == null) {
            SecurityUtils.getSubject().checkPermission("dpParty:add");

            record.setCreateTime(new Date());
            dpPartyService.insertSelective(record);
            DpParty dpParty = dpPartyService.getByCode(record.getCode());
            sysApprovalLogService.add(dpParty.getId(), dpParty.getId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_DP_LOG_TYPE_PARTY,
                    "添加党派", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "添加民主党派" + dpParty.getName());
            logger.info(log( LogConstants.LOG_DPPARTY, "添加民主党派：{0}", record.getId()));
        } else {
            dpPartyService.updateByPrimaryKeySelective(record);
            sysApprovalLogService.add(id, record.getId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_DP_LOG_TYPE_PARTY,
                    "修改党派", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "修改名民主党派" + record.getName() + "的信息");
            logger.info(log( LogConstants.LOG_DPPARTY, "更新民主党派：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping("/dpParty_au")
    public String dpParty_au(int cls,Integer id, ModelMap modelMap) {

        if (id != null) {
            DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
            modelMap.put("cls", cls);
            modelMap.put("dpParty", dpParty);
        }
        return "dp/dpParty/dpParty_au";
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping("/dpParty_cancel")
    public String dpParty_cancel(){

        return "dp/dpParty/dpParty_cancel";
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping(value = "/dpParty_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_cancel(@RequestParam(value = "ids[]") Integer[] ids,
                              String deleteTime){

        if (null != ids && ids.length>0){
            DpPartyExample example = new DpPartyExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpParty> dpParties = dpPartyMapper.selectByExample(example);
            for (DpParty dpParty : dpParties){
                dpParty.setIsDeleted(true);
                if (StringUtils.isNotBlank(deleteTime)){
                    dpParty.setDeleteTime(DateUtils.parseDate(deleteTime, DateUtils.YYYYMMDD_DOT));
                }
                dpPartyService.updateByPrimaryKeySelective(dpParty);
                logger.info(log( LogConstants.LOG_DPPARTY, "撤销民主党派：{0}", dpParty.getName()));
            }

        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping(value = "/dpParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpPartyService.del(id);
            logger.info(log( LogConstants.LOG_GROW, "删除基层党组织：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping(value = "/dpParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpParty_batchDel(HttpServletRequest request,
                                @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpPartyService.batchDel(ids,isDeleted);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量撤销/恢复民主党派：{0}", StringUtils.join(ids, ",")));
        }
        cacheHelper.clearSysBaseCache();
        cacheService.flushMetadata();
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping(value = "/dpParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpPartyService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "基层党组织调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpParty_export(int cls, DpPartyViewExample example, HttpServletResponse response) {

        List<DpPartyView> records = dpPartyViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100","名称|250","简称|100","所属单位|250","民主党派类别|250","联系电话|100","传真|100","邮箱|100","成立时间|100"};
        String[] deleteTitles = {"编号|100","名称|250","撤销时间|100","简称|100","所属单位|250","民主党派类别|250","联系电话|100","传真|100","邮箱|100","成立时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        if (cls == 1) {
            for (int i = 0; i < rownum; i++) {
                DpPartyView record = records.get(i);
                String[] values = {
                        record.getCode(),//编号
                        record.getName(),//名称
                        record.getShortName(),//简称
                        record.getUnitId() == null ? "" : unitService.findAll().get(record.getUnitId()).getName(),
                        metaTypeService.getName(record.getClassId()),//民主党派类别
                        record.getPhone(),//联系电话
                        record.getFax(),
                        record.getEmail(),
                        DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMMDD_DOT),
                };
                valuesList.add(values);
            }
            String fileName = String.format("民主党派(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(titles, valuesList, fileName, response);
        }else if (cls == 2){
            for (int i = 0; i < rownum; i++) {
                DpPartyView record = records.get(i);
                String[] values = {
                        record.getCode(),//编号
                        record.getName(),//名称
                        DateUtils.formatDate(record.getDeleteTime(), DateUtils.YYYYMMDD_DOT),
                        record.getShortName(),//简称
                        record.getUnitId() == null ? "" : unitService.findAll().get(record.getUnitId()).getName(),
                        metaTypeService.getName(record.getClassId()),//民主党派类别
                        record.getPhone(),//联系电话
                        record.getFax(),
                        record.getEmail(),
                        DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMMDD_DOT),
                };
                valuesList.add(values);
            }
            String fileName = String.format("已撤销的民主党派(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(deleteTitles, valuesList, fileName, response);
        }
    }

    @RequestMapping("/dpParty_selects")
    @ResponseBody
    public Map dpParty_selects(Integer pageSize, Boolean auth,
                               Integer partyId,
                               Boolean del,
                               Integer pageNo, Integer classId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpPartyExample example = new DpPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if (del!=null){
            criteria.andIsDeletedEqualTo(del);
        }
        if (classId!=null){
            criteria.andClassIdEqualTo(classId);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        //======权限
        if (BooleanUtils.isTrue(auth)){
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
                List<Integer> dpPartyList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());
                if (dpPartyList.size() > 0)
                    criteria.andIdIn(dpPartyList);
                else
                    criteria.andIdIsNull();
            }
        }

        long count = dpPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpParty> records = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpParty record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");
                option.put("name",record.getName());
                option.put("class",record.getClassId());
                option.put("del",record.getIsDeleted());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //民主党派基本信息
    @RequestMapping("/dpParty_base")
    public String dpParty_base(Integer id, ModelMap modelMap){

        DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
        modelMap.put("dpParty",dpParty);
        DpPartyMemberGroup presentGroup = dpPartyMemberGroupService.getPresentGroup(id);
        modelMap.put("presentGroup",presentGroup);
        modelMap.put("adminIds", iDpPartyMapper.findDpPartyAdmin(id));

        if (presentGroup!=null){
            DpPartyMemberExample example = new DpPartyMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
            modelMap.put("dpPartyMembers", dpPartyMembers);
        }

        return "dp/dpParty/dpParty_base";
    }

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty_view")
    public String dpParty_show_view(Integer id,
                                    ModelMap modelMap){

        DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
        modelMap.put("dpParty", dpParty);

        return "dp/dpParty/dpParty_view";
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping("/dpParty_import")
    public String dpParty_import(){

        return "dp/dpParty/dpParty_import";
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping(value = "dpParty_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_import(HttpServletRequest request) throws InvalidFormatException,IOException{

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer,String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpParty> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            DpParty record = new DpParty();
            row++;
            String code = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(code)){
                throw new OpException("第{0}行编号为空", row);
            }
            record.setCode(code);

            String name = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(name)){
                throw new OpException("第{0}行名称为空", row);
            }
            record.setName(name);

            String shortName = StringUtils.trimToNull(xlsRow.get(2));
            record.setShortName(shortName);

            String foundTime = StringUtils.trimToNull(xlsRow.get(3));
            record.setFoundTime(DateUtils.parseStringToDate(foundTime));

            String unitCode = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(unitCode)){
                throw new OpException("第{0}行的单位编码为空", row);
            }
            Unit unit = unitService.findUnitByCode(unitCode);
            if (unit == null){
                throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
            }
            record.setUnitId(unit.getId());

            String _partyClass = StringUtils.trimToNull(xlsRow.get(5));
            MetaType partyClass = CmTag.getMetaTypeByName("mc_dp_party_class",_partyClass);
            if (partyClass == null)throw new OpException("第{0}行党总支类别[{1}]不存在", row, _partyClass);
            record.setClassId(partyClass.getId());

            /*String _partyUnitType = StringUtils.trimToNull(xlsRow.get(7));
            MetaType partyUnitType = CmTag.getMetaTypeByName("mc_party_unit_type", _partyUnitType);
            if (partyUnitType == null) throw new OpException("第{0}行所在单位属性[{1}]不存在", row, _partyUnitType);
            record.setUnitTypeId(partyUnitType.getId());*/

            record.setPhone(StringUtils.trimToNull(xlsRow.get(6)));
            record.setFax(StringUtils.trimToNull(xlsRow.get(7)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(8)));
            record.setCreateTime(new Date());
            records.add(record);
        }
        Collections.reverse(records);//逆序排列，保证导入的顺序正确

        int addCount = dpPartyService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("totalCount", totalCount);

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入民主党派成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("unit:view")
    @RequestMapping("/dp_unit_view")
    public String dp_unit_view(HttpServletResponse response, int id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);

        return "dp/dpParty/dp_unit_view";
    }

    // 基本信息
    @RequiresPermissions("unit:view")
    @RequestMapping("/dp_unit_base")
    public String dp_unit_base(Integer id, ModelMap modelMap) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        modelMap.put("unit", unit);

        Integer dispatchUnitId = unit.getDispatchUnitId();
        if (dispatchUnitId != null) {
            DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
            if (dispatchUnit != null)
                modelMap.put("dispatch", dispatchUnit.getDispatch());
        }

        // 正在运转单位
        //modelMap.put("runUnits", unitService.findRunUnits(id));
        // 历史单位
        modelMap.put("historyUnits", unitService.findHistoryUnits(id));

        return "unit/unit_base";
    }

}
