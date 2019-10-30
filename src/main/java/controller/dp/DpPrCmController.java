package controller.dp;

import controller.global.OpException;
import domain.dp.*;
import domain.dp.DpPrCmExample.Criteria;
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
import sys.constants.DpConstants;
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
public class DpPrCmController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpPrCm:list")
    @RequestMapping("/dpPrCm")
    public String dpPrCm(Integer type,
                         Integer userId,
                         @RequestParam(required = false, value = "nation") String[] nation,
                         @RequestParam(defaultValue = "1") int cls,
                         ModelMap modelMap) {

        //modelMap.put("type", type);
        modelMap.put("cls", cls);
        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        modelMap.put("nations", iDpPropertyMapper.npmNations());

        return "dp/dpPrCm/dpPrCm_page";
    }

    @RequiresPermissions("dpPrCm:list")
    @RequestMapping("/dpPrCm_data")
    @ResponseBody
    public void dpPrCm_data(HttpServletResponse response,
                            Integer userId,
                            Integer partyId,
                            String unitPost,
                            Integer unitId,
                            String nation,
                            Byte gender,
                            @RequestDateRange DateRange growTime,
                            @RequestDateRange DateRange workTime,
                            @RequestParam(required = false, defaultValue = "1") Byte type,
                            @RequestParam(defaultValue = "1") int cls,
                            @RequestDateRange DateRange electTime,
                            @RequestDateRange DateRange endTime,
                            Boolean status,
                            Integer electSession,
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

        DpPrCmViewExample example = new DpPrCmViewExample();
        if (cls == 1){
            status = true;
        }else if (cls == 2){
            status = false;
        }
        DpPrCmViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));


        if (gender != null){
            criteria.andGenderEqualTo(gender);
        }
        if (StringUtils.isNotBlank(nation)){
            criteria.andNationEqualTo(nation);
        }
        if (unitId != null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (workTime.getStart()!=null) {
            criteria.andWorkTimeGreaterThanOrEqualTo(workTime.getStart());
        }
        if (workTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(workTime.getEnd());
        }
        if (growTime.getStart()!=null){
            criteria.andGrowTimeGreaterThanOrEqualTo(growTime.getStart());
        }
        if (growTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(growTime.getEnd());
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (workTime.getStart()!=null) {
            criteria.andWorkTimeGreaterThanOrEqualTo(workTime.getStart());
        }
        if (workTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(workTime.getEnd());
        }
        if (electTime.getStart()!=null){
            criteria.andGrowTimeGreaterThanOrEqualTo(electTime.getStart());
        }
        if (electTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(electTime.getEnd());
        }
        if (endTime.getStart()!=null){
            criteria.andGrowTimeGreaterThanOrEqualTo(endTime.getStart());
        }
        if (endTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(endTime.getEnd());
        }
        if (electSession!=null) {
            criteria.andElectSessionEqualTo(electSession);
        }
        if (StringUtils.isNotBlank(unitPost)){
            criteria.andUnitPostLike(SqlUtils.like(unitPost));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpPrCm_export(cls, type, example, response);
            return;
        }

        long count = dpPrCmViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpPrCmView> records= dpPrCmViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpPrCm.class, dpPrCmMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpPrCm:edit")
    @RequestMapping(value = "/dpPrCm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_au(DpPrCm record,
                            String workTime,
                            String electTime,
                            String endTime,
                            @CurrentUser SysUserView loginUser,
                            HttpServletRequest request) {

        Integer id = record.getId();

        if (CmTag.getUserById(record.getUserId()).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工账号");
        }else if (dpPrCmService.idDuplicate(id, record.getUserId(), record.getType(), record.getElectSession())) {
            return failed("添加重复");
        }
        if (StringUtils.isNotBlank(workTime)){
            record.setWorkTime(DateUtils.parseDate(workTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(electTime)){
            record.setElectTime(DateUtils.parseDate(electTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(endTime)){
            record.setEndTime(DateUtils.parseDate(endTime,DateUtils.YYYYMMDD_DOT));
        }
        if (id == null) {
            record.setStatus(true);
            dpPrCmService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加人大代表、政协委员信息：{0},{1}", record.getId(), record.getUserId()));
        } else {

            dpPrCmService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新人大代表、政协委员信息：{0},{1}", record.getId(), record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPrCm:edit")
    @RequestMapping("/dpPrCm_au")
    public String dpPrCm_au(Integer id, ModelMap modelMap) {

        if (id != null) {

            DpPrCm dpPrCm = dpPrCmMapper.selectByPrimaryKey(id);
            Integer userId = dpPrCm.getUserId();
            SysUserView sysUserView = CmTag.getUserById(userId);
            if (sysUserView.getType() == SystemConstants.USER_TYPE_JZG){
                modelMap.put("sysUser", sysUserView);
            }

            modelMap.put("dpPrCm", dpPrCm);
        }
        return "dp/dpPrCm/dpPrCm_au";
    }

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping(value = "/dpPrCm_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPrCm_recover(@RequestParam(value = "ids[]") Integer[] ids,
                              HttpServletRequest request){

        if (null != ids && ids.length>0){
            Boolean status = true;
            for (Integer id : ids){
                DpPrCm dpPrCm = dpPrCmMapper.selectByPrimaryKey(id);
                dpPrCm.setStatus(status);
                dpPrCmService.updateByPrimaryKeySelective(dpPrCm);
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping("/dpPrCm_cancel")
    public String dpPrCm_cancel(Byte type,
                                ModelMap modelMap){
        modelMap.put("type", type);

        return "dp/dpPrCm/dpPrCm_cancel";
    }

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping(value = "/dpPrCm_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_cancel(@RequestParam(value = "ids[]") Integer[] ids,
                                String endTime,
                                Integer electSession){

        if (null != ids && ids.length>0){
            DpPrCmExample example = new DpPrCmExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpPrCm> dpPrCms = dpPrCmMapper.selectByExample(example);
            for (DpPrCm dpPrCm : dpPrCms){
                dpPrCm.setStatus(false);
                if (StringUtils.isNotBlank(endTime)){
                    dpPrCm.setEndTime(DateUtils.parseDate(endTime, DateUtils.YYYYMMDD_DOT));
                }
                dpPrCmService.updateByPrimaryKeySelective(dpPrCm);
                logger.info(log( LogConstants.LOG_DPPARTY, "人大代表、政协委员离任：{0}", dpPrCm.getUserId()));
            }

        }
        return success(FormUtils.SUCCESS);
    }

    //单个人员到届
    /*@RequiresPermissions("dpPrCm:del")
    @RequestMapping("/dpPrCm_cancel")
    public String dpPrCm_cancel(Integer id, ModelMap modelMap){

        if (id != null){
            DpPrCm dpPrCm = dpPrCmMapper.selectByPrimaryKey(id);
            Integer userId = dpPrCm.getUserId();
            modelMap.put("sysUser", dpCommonService.findById(userId));
            modelMap.put("dpPrCm", dpPrCm);
        }
        return "dp/dpPrCm/dpPrCm_cancel";
    }

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping(value = "/dpPrCm_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_cancel(HttpServletRequest request,
                                DpPrCm record,
                                String endTime,
                                Integer electSession){

        record.setStatus(false);
        if (StringUtils.isNotBlank(endTime)){
            record.setEndTime(DateUtils.parseDate(endTime,DateUtils.YYYY_MM_DD));
        }
        dpPrCmService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DPPARTY, "人大代表、政协委员离任：{0}，{1}", record.getUserId(), record.getType()));

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping(value = "/dpPrCm_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpPrCmService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除人大代表、政协委员信息。同一个人在一个库中可以出现多次：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPrCm:del")
    @RequestMapping(value = "/dpPrCm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPrCm_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpPrCmService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除人大代表、政协委员信息。同一个人在一个库中可以出现多次：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("dpPrCm:changeOrder")
    @RequestMapping(value = "/dpPrCm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpPrCmService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DPPARTY, "人大代表、政协委员信息。同一个人在一个库中可以出现多次调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpPrCm_import")
    public String dpPrCm_import(){

        return "dp/dpPrCm/dpPrCm_import";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpPrCm_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPrCm_import(HttpServletRequest request) throws InvalidFormatException, IOException{

        Map<String, Byte> typeMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : DpConstants.DP_PR_CM_MAP.entrySet()){
            typeMap.put(entry.getValue(),entry.getKey());
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpPrCm> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            DpPrCm record = new DpPrCm();
            row++;
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            if (uv.getType() != SystemConstants.SYNC_TYPE_JZG){
                throw new OpException("第{0}行学工号[{1}]不是教职工", row, userCode);
            }
            record.setUserId(uv.getUserId());
            int col = 2;
            record.setWorkTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setUnitPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setExecutiveLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setElectPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setElectSession(Integer.valueOf(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setElectTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setEndTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));
            String _type = StringUtils.trimToNull(xlsRow.get(col++));
            Byte type = typeMap.get(_type);
            if (type != null){
                record.setType(Integer.valueOf(type));
            }
            record.setStatus(StringUtils.contains(xlsRow.get(col++),"是"));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));


            records.add(record);
        }
        int successCount = dpPrCmService.batchImportDpPrCm(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入人大代表、政协委员成功，总共{0}条记录，其中成功导入{1}条记录",
                records.size(), successCount));

        return resultMap;
    }

    public void dpPrCm_export(int cls, Byte type, DpPrCmViewExample example, HttpServletResponse response) {

        List<DpPrCmView> records = dpPrCmViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","工作证号|100","所属单位|250","所属单位及职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","行政级别|100","所属类别|200","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","备注|100"};
        String[] cancelTitles = {"姓名|100","工作证号|100","所属单位|250","所属单位及职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","行政级别|100","所属类别|200","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","离任时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        if (cls == 1){
            for (int i = 0; i < rownum; i++) {
                DpPrCmView record = records.get(i);
                DpParty dpParty = new DpParty();
                if (record.getPartyId() != null){
                    dpParty = dpPartyService.findById(record.getPartyId());
                }
                SysUserView uv = sysUserService.findById(record.getUserId());
                String[] values = {
                        uv.getRealname(),//姓名
                        uv.getCode(),//工作证号
                        record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),//所属单位
                        record.getUnitPost(),//所属单位及职务
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),//性别
                        uv.getNation(),//民族
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),//出生时间
                        dpParty.getName() == null ? "" : dpParty.getName(),//所属党派
                        DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),//加入党派时间
                        DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYYMMDD_DOT),//参加工作时间
                        record.getExecutiveLevel(),//行政级别
                        DpConstants.DP_PR_CM_MAP.get(type),//所属类别
                        record.getEducation(),//最高学历
                        record.getDegree(),//最高学位
                        record.getSchool(),//毕业学校
                        record.getMajor(),//所学专业
                        record.getRemark()//备注
                };
                valuesList.add(values);
            }
        }else {
            for (int i = 0; i < rownum; i++) {
                DpPrCmView record = records.get(i);
                DpParty dpParty = new DpParty();
                if (record.getPartyId() != null){
                    dpParty = dpPartyService.findById(record.getPartyId());
                }
                SysUserView uv = sysUserService.findById(record.getUserId());
                String[] values = {
                        uv.getRealname(),
                        uv.getCode(),
                        record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),
                        record.getUnitPost(),
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),
                        uv.getNation(),
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),
                        dpParty.getName() == null ? "" : dpParty.getName(),
                        DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),
                        DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYYMMDD_DOT),
                        record.getExecutiveLevel(),
                        DpConstants.DP_PR_CM_MAP.get(type),//所属类别
                        record.getEducation(),
                        record.getDegree(),
                        record.getSchool(),
                        record.getMajor(),
                        DateUtils.formatDate(record.getEndTime(), DateUtils.YYYYMMDD_DOT),
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }
        if (cls == 1){
            String fileName = String.format(DpConstants.DP_PR_CM_MAP.get(type) + "(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(titles, valuesList, fileName, response);
        }else if (cls == 2){
            String fileName = String.format("往届" + DpConstants.DP_PR_CM_MAP.get(type) + "(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(cancelTitles, valuesList, fileName, response);
        }

    }

    @RequestMapping("/dpPrCm_selects")
    @ResponseBody
    public Map dpPrCm_selects(Integer pageSize, Integer type,int cls, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Boolean status = false;
        if (cls == 1){
            status = true;
        }
        DpPrCmExample example = new DpPrCmExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        long count = dpPrCmMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpPrCm> records = dpPrCmMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        Set<DpPrCm> dpPrCms = new HashSet<>();
        for (DpPrCm dpPrCm : records){
            Set<Integer> userIds = new HashSet<>();
            int userId = dpPrCm.getUserId();
            if (!userIds.contains(userId)){
                userIds.add(userId);
                dpPrCms.add(dpPrCm);
            }
        }

        List options = new ArrayList<>();
        if(null != dpPrCms && dpPrCms.size()>0){

            for(DpPrCm record:dpPrCms){
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

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
