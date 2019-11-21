package controller.dp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.dp.*;
import domain.dp.DpOmExample.Criteria;
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
public class DpOmController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpOm:list")
    @RequestMapping("/dpOm")
    public String dpOm(ModelMap modelMap,
                       Integer userId,
                       @RequestParam(required = false, value = "nation") String[] nation,
                       @RequestParam(required = false, defaultValue = "1") int cls) {

        modelMap.put("cls", cls);
        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        modelMap.put("nations", iDpPropertyMapper.npmNations());

        return "dp/dpOm/dpOm_page";
    }

    @RequiresPermissions("dpOm:list")
    @RequestMapping("/dpOm_data")
    @ResponseBody
    public void dpOm_data(HttpServletResponse response,
                                    Integer partyId,
                                    Integer userId,
                                    Integer type,
                                    String unitPost,
                                    Boolean isDeleted,
                                    String nation,
                                    Byte gender,
                                    @RequestDateRange DateRange growTime,
                                    @RequestDateRange DateRange workTime,
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

        DpOmViewExample example = new DpOmViewExample();
        DpOmViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));


        if (cls == 1){
            isDeleted = false;
        }else if (cls == 2){
            isDeleted = true;
        }
        criteria.andIsDeletedEqualTo(isDeleted);

        if (gender != null){
            criteria.andGenderEqualTo(gender);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
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
        if (growTime.getStart()!=null){
            criteria.andGrowTimeGreaterThanOrEqualTo(growTime.getStart());
        }
        if (growTime.getEnd()!=null){
            criteria.andBirthLessThanOrEqualTo(growTime.getEnd());
        }
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (StringUtils.isNotBlank(unitPost)){
            criteria.andUnitPostLike(SqlUtils.like(unitPost));
        }
        if (StringUtils.isNotBlank(nation)){
            criteria.andNationEqualTo(nation);
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

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpOm_export(cls, example, response);
            return;
        }

        long count = dpOmViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpOmView> records= dpOmViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpOm.class, dpOmMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpOm:edit")
    @RequestMapping(value = "/dpOm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_au(DpOm record,
                          @CurrentUser SysUserView loginUser,
                          String workTime,
                          String transferTime,
                          HttpServletRequest request) {

        Integer id = record.getId();

        if (CmTag.getUserById(record.getUserId()).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工成员");
        }else if (dpOmService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if (StringUtils.isNotBlank(workTime)){
            record.setWorkTime(DateUtils.parseDate(workTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(transferTime)){
            record.setTransferTime(DateUtils.parseDate(transferTime,DateUtils.YYYYMMDD_DOT));
        }
        if (id == null) {
            record.setIsDeleted(false);
            dpOmService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加其他统战人员：{0}", record.getId()));
        } else {

            dpOmService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新其他统战人员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpOm:edit")
    @RequestMapping("/dpOm_au")
    public String dpOm_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DpOm dpOm = dpOmMapper.selectByPrimaryKey(id);
            Integer userId = dpOm.getUserId();
            modelMap.put("sysUser", CmTag.getUserById(userId));
            modelMap.put("dpOm", dpOm);
        }
        return "dp/dpOm/dpOm_au";
    }

    @RequiresPermissions("dpOm:del")
    @RequestMapping(value = "/dpOm_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map dpOm_recover(@RequestParam(value = "ids[]") Integer[] ids,
                            HttpServletRequest request){
        if (null != ids && ids.length>0){
            Boolean isDeleted = false;
            for (Integer id : ids){
                DpOm dpOm = dpOmMapper.selectByPrimaryKey(id);
                dpOm.setIsDeleted(isDeleted);
                dpOmService.updateByPrimaryKeySelective(dpOm);
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpOm:del")
    @RequestMapping("/dpOm_cancel")
    public String dpOm_cancel(){

        return "dp/dpOm/dpOm_cancel";
    }

    @RequiresPermissions("dpOm:del")
    @RequestMapping(value = "/dpOm_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_cancel(@RequestParam(value = "ids[]") Integer[] ids,
                              String transferTime){

        if (null != ids && ids.length>0){
            DpOmExample example = new DpOmExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpOm> dpOms = dpOmMapper.selectByExample(example);
            for (DpOm dpOm : dpOms){
                dpOm.setIsDeleted(true);
                if (StringUtils.isNotBlank(transferTime)){
                    dpOm.setTransferTime(DateUtils.parseDate(transferTime, DateUtils.YYYYMMDD_DOT));
                }
                dpOmService.updateByPrimaryKeySelective(dpOm);
                logger.info(log( LogConstants.LOG_DPPARTY, "撤销其他统战人员：{0}", dpOm.getUserId()));
            }

        }

        return success(FormUtils.SUCCESS);
    }

    //撤销单个人员
    /*@RequiresPermissions("dpOm:del")
    @RequestMapping("/dpOm_cancel")
    public String dpOm_cancel(Integer id, ModelMap modelMap){

        if (id != null){
            DpOm dpOm = dpOmMapper.selectByPrimaryKey(id);
            Integer userId = dpOm.getUserId();
            modelMap.put("sysUser", dpCommonService.findById(userId));
            modelMap.put("dpOm", dpOm);
        }
        return "dp/dpOm/dpOm_cancel";
    }

    @RequiresPermissions("dpOm:del")
    @RequestMapping(value = "/dpOm_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_cancel(HttpServletRequest request,
                              DpOm record,
                              String transferTime){

        record.setIsDeleted(true);
        if (StringUtils.isNotBlank(transferTime)){
            record.setTransferTime(DateUtils.parseDate(transferTime,DateUtils.YYYY_MM_DD));
        }
        dpOmService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DPPARTY, "撤销其他统战人员：{0}", record.getUserId()));

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("dpOm:del")
    @RequestMapping(value = "/dpOm_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpOmService.del(id);
            logger.info(log( LogConstants.LOG_GROW, "删除其他统战人员。华侨、归侨及侨眷、欧美同学会会员、知联会员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpOm:del")
    @RequestMapping(value = "/dpOm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpOm_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpOmService.batchDel(ids);
            logger.info(log( LogConstants.LOG_GROW, "批量删除其他统战人员。华侨、归侨及侨眷、欧美同学会会员、知联会员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpOm_import")
    public String dpOm_import(){

        return "dp/dpOm/dpOm_import";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpOm_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_import(HttpServletRequest request) throws InvalidFormatException, IOException{

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpOm> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            DpOm record = new DpOm();
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
            record.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));
            String _type = StringUtils.trimToNull(xlsRow.get(col++));
            MetaType type = CmTag.getMetaTypeByName("mc_dp_other_type",_type);
            if (type == null) throw new OpException("第{0}列党总支类别[{1}]不存在", col, _type);
            record.setType(type.getId());
            record.setIsDeleted(StringUtils.contains(xlsRow.get(col++),"否"));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            records.add(record);
        }
        int successCount = dpOmService.batchImportDpOm(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入其他统战人员成功，总共{0}条记录，其中成功导入{1}条记录",
                records.size(), successCount));

        return resultMap;
    }

    @RequiresPermissions("dpOm:changeOrder")
    @RequestMapping(value = "/dpOm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpOmService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "其他统战人员。华侨、归侨及侨眷、欧美同学会会员、知联会员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void dpOm_export(int cls, DpOmViewExample example, HttpServletResponse response) {

        List<DpOmView> records = dpOmViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","工作证号|100","所属单位|250","所属单位及职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","所属类别|200","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","办公电话|100","手机号|100","备注|100"};
        String[] cancelTitles = {"姓名|100","工作证号|100","所属单位|250","所属单位及职务|100","性别|100","民族|100","出生时间|100",
                "所属党派|270","加入党派时间|100","参加工作时间|100","所属类别|200","最高学历|100",
                "最高学位|100","毕业学校|100","所学专业|100","办公电话|100","手机号|100","备注|100","撤销时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        if (cls == 1){
            for (int i = 0; i < rownum; i++) {
                DpOmView record = records.get(i);
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
                        metaTypeService.getName(record.getType()),//所属类别
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
                DpOmView record = records.get(i);
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
                        metaTypeService.getName(record.getType()),//所属类别
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
            String fileName = String.format("其他统战人员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(titles, valuesList, fileName, response);
        }else {
            String fileName = String.format("离任的其他统战人员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(cancelTitles, valuesList, fileName, response);
        }
    }

    @RequestMapping("/dpOm_selects")
    @ResponseBody
    public Map dpOm_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpOmExample example = new DpOmExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        long count = dpOmMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpOm> records = dpOmMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpOm record:records){
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
