package controller.dp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.dp.*;
import domain.dp.DpNprExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/dp")
@Controller
public class DpNprController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpNpr:list")
    @RequestMapping("/dpNpr")
    public String dpNpr(@RequestParam(required = false, defaultValue = "1")int cls,
                        @RequestParam(required = false, value = "nation") String[] nation,
                        Integer userId,
                        ModelMap modelMap) {
        modelMap.put("cls", cls);
        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        modelMap.put("nations", iDpPropertyMapper.npmNations());

        return "dp/dpNpr/dpNpr_page";
    }

    @RequiresPermissions("dpNpr:list")
    @RequestMapping("/dpNpr_data")
    @ResponseBody
    public void dpNpr_data(HttpServletResponse response,
                                    Integer partyId,
                                    Integer userId,
                                    @RequestDateRange DateRange dpGrowTime,
                                    @RequestDateRange DateRange workTime,
                                    Integer type,
                                    Integer level,
                                    Boolean isDeleted,
                                    String unitPost,
                                    Byte gender,
                                    String unit,
                                    @RequestDateRange DateRange transferTime,
                                 @RequestParam(defaultValue = "1") int cls,
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

        DpNprViewExample example = new DpNprViewExample();
        DpNprViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));


        if (StringUtils.isNotBlank(unitPost)){
            criteria.andUnitPostLike(SqlUtils.like(unitPost));
        }
        if (cls == 1){
            isDeleted = false;
        }else {
            isDeleted = true;
        }
        criteria.andIsDeletedEqualTo(isDeleted);
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (dpGrowTime.getStart() != null){
            criteria.andDpGrowTimeGreaterThanOrEqualTo(dpGrowTime.getStart());
        }
        if (dpGrowTime.getEnd() != null) {
            criteria.andDpGrowTimeLessThanOrEqualTo(dpGrowTime.getEnd());
        }
        if (workTime.getStart() != null){
            criteria.andWorkTimeGreaterThanOrEqualTo(workTime.getStart());
        }
        if (workTime.getEnd() != null){
            criteria.andWorkTimeLessThanOrEqualTo(workTime.getEnd());
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (level!=null) {
            criteria.andLevelEqualTo(level);
        }
        if (transferTime.getStart() != null) {
            criteria.andTransferTimeGreaterThanOrEqualTo(transferTime.getStart());
        }
        if (transferTime.getEnd() != null){
            criteria.andTransferTimeLessThanOrEqualTo(transferTime.getEnd());
        }
        if (gender != null){
            criteria.andGenderEqualTo(gender);
        }
        if (unit != null){
            criteria.andUnitLike(unit);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpNpr_export(cls, example, response);
            return;
        }

        long count = dpNprViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpNprView> records= dpNprViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpNpr.class, dpNprMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpNpr:edit")
    @RequestMapping(value = "/dpNpr_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_au(DpNpr record,
                           @CurrentUser SysUserView loginUser,
                           String workTime,
                           String _transferTime,
                           HttpServletRequest request) {

        Integer id = record.getId();

        if (CmTag.getUserById(record.getUserId()).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工账号");
        }else if (dpNprService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if (StringUtils.isNotBlank(workTime)){
            record.setWorkTime(DateUtils.parseDate(workTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(_transferTime)){
            record.setTransferTime(DateUtils.parseDate(_transferTime,DateUtils.YYYYMMDD_DOT));
        }
        if (id == null) {

            record.setIsDeleted(false);
            dpNprService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加党外代表人士：{0}", record.getId()));
        } else {

            dpNprService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新党外代表人士：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpr:edit")
    @RequestMapping("/dpNpr_au")
    public String dpNpr_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DpNpr dpNpr = dpNprMapper.selectByPrimaryKey(id);
            Integer userId = dpNpr.getUserId();
            SysUserView sysUserView = CmTag.getUserById(userId);
            if (sysUserView.getType() == 1){
                modelMap.put("sysUser", sysUserView);
            }else {
                throw new OpException("非教职工账号");
            }
            modelMap.put("dpNpr", dpNpr);
        }
        return "dp/dpNpr/dpNpr_au";
    }

    @RequiresPermissions("dpNpr:del")
    @RequestMapping(value = "/dpNpr_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map dpNpr_rcover(@RequestParam(value = "ids[]") Integer[] ids,
                            HttpServletRequest request){

        if (null != ids && ids.length>0) {
            Boolean isDeleted = false;
            for (Integer id : ids) {
                DpNpr dpNpr = dpNprMapper.selectByPrimaryKey(id);
                dpNpr.setIsDeleted(isDeleted);
                dpNprService.updateByPrimaryKeySelective(dpNpr);
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpr:del")
    @RequestMapping("/dpNpr_cancel")
    public String dpNpr_cancel(){

        return "dp/dpNpr/dpNpr_cancel";
    }

    @RequiresPermissions("dpNpr:del")
    @RequestMapping(value = "/dpNpr_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_cancel(@RequestParam(value = "ids[]") Integer[] ids, String transferTime){

        if (null != ids && ids.length>0){
            DpNprExample example = new DpNprExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpNpr> dpNprs = dpNprMapper.selectByExample(example);
            for (DpNpr dpNpr : dpNprs){
                dpNpr.setIsDeleted(true);
                if (StringUtils.isNotBlank(transferTime)){
                    dpNpr.setTransferTime(DateUtils.parseDate(transferTime, DateUtils.YYYYMMDD_DOT));
                }
                dpNprService.updateByPrimaryKeySelective(dpNpr);
                logger.info(log( LogConstants.LOG_DPPARTY, "离任党外代表人士：{0}", dpNpr.getUserId()));
            }

        }

        return success(FormUtils.SUCCESS);
    }

    //单独撤销单个人员
    /*@RequiresPermissions("dpNpr:del")
    @RequestMapping("/dpNpr_cancel")
    public String dpNpr_cancel(Integer id, ModelMap modelMap){
        if (id != null){
            DpNpr dpNpr = dpNprMapper.selectByPrimaryKey(id);
            Integer userId = dpNpr.getUserId();
            modelMap.put("sysUser", dpCommonService.findById(userId));
            modelMap.put("dpNpr", dpNpr);
        }
        return "dp/dpNpr/dpNpr_cancel";
    }*/

    /*@RequiresPermissions("dpNpr:del")
    @RequestMapping(value = "/dpNpr_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_cancel(HttpServletRequest request, DpNpr record , String transferTime){

        record.setIsDeleted(true);
        if (StringUtils.isNotBlank(transferTime)){
            record.setTransferTime(DateUtils.parseDate(transferTime, DateUtils.YYYY_MM_DD));
        }
        dpNprService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DPPARTY, "撤销党外代表人士：{0}", record.getUserId()));

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("dpNpr:del")
    @RequestMapping(value = "/dpNpr_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpNprService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除党外代表人士：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpr:del")
    @RequestMapping(value = "/dpNpr_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpNpr_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpNprService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除党外代表人士：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpNpr_import")
    public String dpNpr_import(){

        return "dp/dpNpr/dpNpr_import";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpNpr_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_import(HttpServletRequest request) throws InvalidFormatException, IOException{

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpNpr> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            DpNpr record = new DpNpr();
            row++;
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行学工号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            if (uv.getType() != SystemConstants.SYNC_TYPE_JZG) {
                throw new OpException("第{0}行学工号[{1}]不是教职工", row, userCode);
            }
            record.setUserId(uv.getUserId());
            int col = 2;
            record.setWorkTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setUnitPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));
            String _type = StringUtils.trimToNull(xlsRow.get(col++));
            MetaType type = CmTag.getMetaTypeByName("mc_dp_npr_type", _type);
            if (type == null) {
                //throw new OpException("第{0}列党总支类别[{1}]不存在", col, _type);
                record.setType(null);
            } else {
                record.setType(type.getId());
            }
            String _level = StringUtils.trimToNull(xlsRow.get(col++));
            MetaType level =CmTag.getMetaTypeByName("mc_dp_npr_level",_level);
            if (level == null) {
               // throw new OpException("第{0}列党总支类别[{1}]不存在", col, _level);
                record.setLevel(null);
            }else {
                record.setLevel(level.getId());
            }
            record.setIsDeleted(false);
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));

            records.add(record);
        }
        int successCount = dpNprService.batchImportDpNpr(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入党外代表人士成功，总共{0}条记录，其中成功导入{1}条记录",
                records.size(), successCount));

        return resultMap;
    }

    @RequiresPermissions("dpNpr:changeOrder")
    @RequestMapping(value = "/dpNpr_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpr_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpNprService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "党外代表人士调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void dpNpr_export(int cls, DpNprViewExample example, HttpServletResponse response) {

        List<DpNprView> records = dpNprViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","工作证号|100","部门|250","所属单位职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","所属类别|200","所属级别|100","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","办公电话|100","手机号|100","备注|100"};
        String[] cancelTitles = {"姓名|100","工作证号|100","部门|250","所属单位职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","所属类别|200","所属级别|100","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","办公电话|100","手机号|100","备注|100","离任时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        if (cls == 1){
            for (int i = 0; i < rownum; i++) {
                DpNprView record = records.get(i);
                DpParty dpParty = new DpParty();
                if (record.getPartyId() != null){
                    dpParty = dpPartyService.findById(record.getPartyId());
                }
                SysUserView uv = sysUserService.findById(record.getUserId());
                String[] values = {
                        uv.getRealname(),//姓名
                        uv.getCode(),//工作证号
                        record.getUnit(),//部门
                        record.getUnitPost(),//所属单位及职务
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),//性别
                        uv.getNation(),//民族
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),//出生时间
                        dpParty.getName() == null ? "" : dpParty.getName(),//所属党派
                        DateUtils.formatDate(record.getDpGrowTime(), DateUtils.YYYYMMDD_DOT),//加入党派时间
                        DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYYMMDD_DOT),//参加工作时间
                        metaTypeService.getName(record.getType()),//所属类别
                        metaTypeService.getName(record.getLevel()),//所属级别
                        record.getEducation(),//最高学历
                        record.getDegree(),//最高学位
                        record.getSchool(),//毕业学校
                        record.getMajor(),//所学专业
                        record.getPhone(),//办公电话
                        record.getMobile(),//手机号
                        record.getRemark()//备注
                };
                valuesList.add(values);
            }
        }else {
            for (int i = 0; i < rownum; i++) {
                DpNprView record = records.get(i);
                DpParty dpParty = new DpParty();
                if (record.getPartyId() != null){
                    dpParty = dpPartyService.findById(record.getPartyId());
                }
                SysUserView uv = sysUserService.findById(record.getUserId());
                String[] values = {
                        uv.getRealname(),
                        uv.getCode(),
                        record.getUnit(),
                        record.getUnitPost(),
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),
                        uv.getNation(),
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),
                        dpParty.getName() == null ? "" : dpParty.getName(),
                        DateUtils.formatDate(record.getDpGrowTime(), DateUtils.YYYYMMDD_DOT),
                        DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYYMMDD_DOT),
                        metaTypeService.getName(record.getType()),//所属类别
                        metaTypeService.getName(record.getLevel()),//所属级别
                        record.getEducation(),
                        record.getDegree(),
                        record.getSchool(),
                        record.getMajor(),
                        record.getPhone(),
                        record.getMobile(),
                        record.getRemark(),
                        DateUtils.formatDate(record.getTransferTime(), DateUtils.YYYYMMDD_DOT)
                };
                valuesList.add(values);
            }
        }
        if (cls == 1){
            String fileName = String.format("党外代表人士(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(titles, valuesList, fileName, response);
        }else {
            String fileName = String.format("离任的党外代表人士(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(cancelTitles, valuesList, fileName, response);
        }
    }

    @RequestMapping("/dpNpr_selects")
    @ResponseBody
    public Map dpNpr_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpNprExample example = new DpNprExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        long count = dpNprMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpNpr> records = dpNprMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpNpr record:records){
                SysUserView uv = sysUserService.findById(record.getUserId());

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("username", uv.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", uv.getCode());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("birth", uv.getBirth());
                option.put("nation", uv.getNation());

                if (StringUtils.isNotBlank(uv.getCode())){
                    option.put("unit", extService.getUnit(uv.getId()));
                }

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
