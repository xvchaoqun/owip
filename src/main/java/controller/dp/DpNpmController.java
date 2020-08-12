package controller.dp;

import controller.global.OpException;
import domain.dp.DpNpm;
import domain.dp.DpNpmExample;
import domain.dp.DpNpmView;
import domain.dp.DpNpmViewExample;
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
import sys.constants.DpConstants;
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

@RequestMapping("/dp")
@Controller
public class DpNpmController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpNpm:list")
    @RequestMapping("/dpNpm")
    public String dpNpm(@RequestParam(required = false, defaultValue = "1")Byte cls,
                        Integer userId,
                        String[] nation,
                        String[] nativePlace,
                        ModelMap modelMap,
                        HttpServletResponse response) {

        modelMap.put("cls", cls);

        if (userId != null){
            modelMap.put("dpNpm", dpNpmService.get(userId));
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            modelMap.put("selectNativePlaces", selectNativePlaces);
        }
        modelMap.put("nations", iDpPropertyMapper.npmNations());
        modelMap.put("nativePlaces", iDpPropertyMapper.npmNativePlaces());

        return "dp/dpNpm/dpNpm_page";
    }

    @RequiresPermissions("dpNpm:list")
    @RequestMapping("/dpNpm_data")
    @ResponseBody
    public void dpNpm_data(HttpServletResponse response,
                           Integer id,
                           Integer userId,
                           String post,
                           Byte gender,
                           String unit,
                           String education,
                           String degree,
                           Byte status,
                           @RequestDateRange DateRange _addAime,
                           String[] nation,
                           String[] nativePlace,
                           @RequestParam(required = false, defaultValue = "1") int cls,
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


            DpNpmViewExample example = new DpNpmViewExample();
            DpNpmViewExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause("sort_order desc");

        if (cls == 1) {
            status = DpConstants.DP_NPM_NORMAL;
            criteria.andStatusEqualTo(status);
        }else if (cls == 2){
            status = DpConstants.DP_NPM_OUT;
            criteria.andStatusEqualTo(status);
        }else {
            status = DpConstants.DP_NPM_TRANSFER;
            criteria.andStatusEqualTo(status);
        }
        if (id != null){
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(post)) {
            criteria.andPostLike(SqlUtils.like(post));
        }
        if (StringUtils.isNotBlank(education)) {
            criteria.andEducationLike(SqlUtils.like(education));
        }
        if (StringUtils.isNotBlank(degree)) {
            criteria.andDegreeLike(SqlUtils.like(degree));
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }
        if (userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }
        if (_addAime.getStart() != null){
            criteria.andAddTimeGreaterThanOrEqualTo(_addAime.getStart());
        }
        if (_addAime.getEnd() != null){
            criteria.andAddTimeLessThanOrEqualTo(_addAime.getEnd());
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpNpm_export(cls, example, response);
            return;
        }

        long count = dpNpmViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpNpmView> records= dpNpmViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpNpm.class, dpNpmMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping(value = "/dpNpm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_au(DpNpm record,
                           String addTime,
                           String post,
                           String education,
                           String degree,
                           String authorizedType,
                           String proPost,
                           String unit,
                           String remark,
                           Byte status,
                           HttpServletRequest request) {

        Integer id = record.getId();
        Integer userId = record.getUserId();

        if (CmTag.getUserById(userId).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工账号");
        }else if (dpMemberService.get(userId) != null){
            return failed("该成员已是民主党派成员");
        }else if (dpNpmService.idDuplicate(id, userId)) {
            return failed("添加重复");
        }

        if (StringUtils.isNotBlank(addTime)){
            record.setAddTime(DateUtils.parseDate(addTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(post)){
            record.setPost(post);
        }
        if (StringUtils.isNotBlank(education)){
            record.setEducation(education);
        }
        if (StringUtils.isNotBlank(degree)){
            record.setDegree(degree);
        }
        if (StringUtils.isNotBlank(authorizedType)){
            record.setAuthorizedType(authorizedType);
        }
        if (StringUtils.isNotBlank(proPost)){
            record.setProPost(proPost);
        }

        if (StringUtils.isNotBlank(unit)){
            record.setUnit(unit);
        }
        if (StringUtils.isNotBlank(remark)){
            record.setEducation(remark);
        }

        record.setStatus(status);
        SysUserView sysUserView = sysUserService.findById(record.getUserId());
        DpNpm dpNpm = dpNpmService.get(userId);
        if (dpNpm == null) {

            DpNpm dpNpmAdd = dpNpmService.get(userId);
            if (dpNpmAdd != null){
                return failed(sysUserService.findById(userId).getRealname() + "用户已是无党派人士");
            }
            status = 1;
            record.setStatus(status);
            dpNpmService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加无党派人士信息：%s %s", sysUserView.getId(),sysUserView.getRealname()));
        } else {

            dpNpmService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新无党派人士信息：%s %s", sysUserView.getId(),sysUserView.getRealname()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping("/dpNpm_au")
    public String dpNpm_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DpNpm dpNpm = dpNpmMapper.selectByPrimaryKey(id);
            Integer userId = dpNpm.getUserId();
            modelMap.put("sysUser", CmTag.getUserById(userId));
            DpNpmExample example = new DpNpmExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<DpNpm> dpNpms = dpNpmMapper.selectByExample(example);
            if (dpNpms.size() == 1){
                for (DpNpm dpNpmAdd : dpNpms){
                    modelMap.put("dpNpm", dpNpmAdd);
                }
            }
        }
        return "dp/dpNpm/dpNpm_au";
    }

    @RequiresPermissions("dpNpm:del")
    @RequestMapping(value = "/dpNpm_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpNpmService.del(id);
            logger.info(log( LogConstants.LOG_GROW, "删除无党派和退出人士：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpm:del")
    @RequestMapping(value = "/dpNpm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpNpm_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpNpmService.batchDel(ids);
            logger.info(log( LogConstants.LOG_GROW, "批量删除无党派和退出人士：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping("/dpNpm_out")
    public String dpNpm_out(){

        return "dp/dpNpm/dpNpm_out";
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping(value = "/dpNpm_out", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_out( Integer[] ids,
                             String outTime){

        if (null != ids && ids.length>0){
            DpNpmExample example = new DpNpmExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpNpm> dpNpms = dpNpmMapper.selectByExample(example);
            for (DpNpm dpNpm : dpNpms){
                dpNpm.setStatus(DpConstants.DP_NPM_OUT);
                if (StringUtils.isNotBlank(outTime)){
                    dpNpm.setOutTime(DateUtils.parseDate(outTime, DateUtils.YYYYMMDD_DOT));
                }
                dpNpmService.updateByPrimaryKeySelective(dpNpm);
                logger.info(log( LogConstants.LOG_DPPARTY, "无党派人士移除信息：%s", dpNpm.getUserId()));
            }

        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping(value = "/dpNpm_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map dpNpm_recover(Integer[] ids,
                             HttpServletRequest request){
        if (null != ids && ids.length>0){
            for (Integer id :ids){
                DpNpm dpNpm = dpNpmMapper.selectByPrimaryKey(id);
                dpNpm.setStatus(DpConstants.DP_NPM_NORMAL);
                dpNpmMapper.updateByPrimaryKeySelective(dpNpm);
            }
        }
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("dpNpm:edit")
    @RequestMapping("/dpNpm_transfer")
    public String dpNpm_transfer(){

        return "dp/dpNpm/dpNpm_transfer";
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping(value = "/dpNpm_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_transfer(Integer[] ids,
                                 String transferTime){
        if (null != ids && ids.length>0){
            DpNpmExample example = new DpNpmExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpNpm> dpNpms = dpNpmMapper.selectByExample(example);
            for (DpNpm dpNpm : dpNpms){
                dpNpm.setStatus(DpConstants.DP_NPM_TRANSFER);
                if (StringUtils.isNotBlank(transferTime)){
                    dpNpm.setTransferTime(DateUtils.parseDate(transferTime, DateUtils.YYYYMMDD_DOT));
                }
                dpNpmService.updateByPrimaryKeySelective(dpNpm);
                logger.info(log( LogConstants.LOG_DPPARTY, "转出无党派人士信息：%s", dpNpm.getUserId()));
            }

        }
        return success(FormUtils.SUCCESS);
    }*/

    //单独撤销单个人员
    /*@RequiresPermissions("dpNpm:edit")
    @RequestMapping("/dpNpm_transfer")
    public String dpNpm_transfer(Integer id, ModelMap modelMap){

        if (id != null) {
            DpNpm dpNpm = dpNpmMapper.selectByPrimaryKey(id);
            Integer userId = dpNpm.getUserId();
            modelMap.put("sysUser", dpCommonService.findById(userId));
            modelMap.put("dpNpm", dpNpm);
        }

        return "dp/dpNpm/dpNpm_transfer";
    }

    @RequiresPermissions("dpNpm:edit")
    @RequestMapping(value = "/dpNpm_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_transfer(DpNpm record,
                                    Byte status,
                                    String transferTime,
                                    ModelMap modelMap){
        Integer userId = record.getUserId();
        if (record != null) {
            SysUserView sysUserView = sysUserService.findById(userId);
            record.setStatus(DpConstants.DP_NPM_TRANSFER);
            if(StringUtils.isNotBlank(transferTime)) {
                record.setTransferTime(DateUtils.parseDate(transferTime, DateUtils.YYYYMMDD));
            }
            dpNpmService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "转出无党派人士信息：%s %s", sysUserView.getId(),sysUserView.getRealname()));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpNpm_import")
    public String dpNpm_import(){

        return "dp/dpNpm/dpNpm_import";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpNpm_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multiPartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpNpm> records = new ArrayList<>();
        int row = 1;
        Byte status = 1;
        for (Map<Integer,String> xlsRow : xlsRows){

            DpNpm record = new DpNpm();
            row++;
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行学工号为空", row);
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
            record.setAddTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setUnit(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setStatus(status);
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));

            records.add(record);
        }
        int successCount = dpNpmService.batchImportDpDpm(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入无党派人士成功，总共{0}条记录，其中成功导入{1}条记录",
                records.size(), successCount));

        return resultMap;
    }

    @RequiresPermissions("dpNpm:changeOrder")
    @RequestMapping(value = "/dpNpm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpNpm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpNpmService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "无党派和退出人士调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void dpNpm_export(int cls, DpNpmViewExample example, HttpServletResponse response) {

        List<DpNpmView> records = dpNpmViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] noPartyTitles = {"姓名|100","工作证号|100","性别|100","民族|100","籍贯|100","出生时间|100","年龄|100",
                "认定时间|100","部门|250","现任职务|100","最高学历|100",
                "最高学位|100","备注|200"};
        String[] outTitles = {"姓名|100","工作证号|100","移除时间|100","性别|100","民族|100","籍贯|100","出生时间|100","年龄|100",
                "认定时间|100","部门|250","现任职务|100","最高学历|100",
                "最高学位|100","备注|200"};
        /*String[] transferTitles = {"姓名|100","工作证号|100","转出时间|100","性别|100","民族|100","籍贯|100","出生时间|100","年龄|100",
                "认定时间|100","部门|250","现任职务|100","最高学历|100",
                "最高学位|100","备注|200"};*/
        List<String[]> valuesList = new ArrayList<>();
        if (cls == 3){
            for (int i = 0; i < rownum; i++) {
                DpNpmView record = records.get(i);
                Integer userId = record.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                String[] values = {
                        uv.getRealname(),//姓名
                        uv.getCode(),//工作证号
                        DateUtils.formatDate(record.getTransferTime(), DateUtils.YYYYMMDD_DOT),
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),//性别
                        uv.getNation(),//民族
                        uv.getNativePlace(),
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),
                        uv.getBirth() != null ? DateUtils.intervalYearsUntilNow(uv.getBirth()) + "" : "",//年龄
                        DateUtils.formatDate(record.getAddTime(), DateUtils.YYYYMMDD_DOT),
                        record.getUnit(),
                        record.getPost(),
                        record.getEducation(),
                        record.getDegree(),
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }else if (cls == 1){
            for (int i = 0; i < rownum; i++) {
                DpNpmView record = records.get(i);
                Integer userId = record.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                String[] values = {
                        uv.getRealname(),//姓名
                        uv.getCode(),//工作证号
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),//性别
                        uv.getNation(),//民族
                        uv.getNativePlace(),
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),
                        uv.getBirth() != null ? DateUtils.intervalYearsUntilNow(uv.getBirth()) + "" : "",//年龄
                        DateUtils.formatDate(record.getAddTime(), DateUtils.YYYYMMDD_DOT),
                        record.getUnit(),
                        record.getPost(),
                        record.getEducation(),
                        record.getDegree(),
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }else if (cls == 2){
            for (int i = 0; i < rownum; i++) {
                DpNpmView record = records.get(i);
                Integer userId = record.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                String[] values = {
                        uv.getRealname(),//姓名
                        uv.getCode(),//工作证号
                        DateUtils.formatDate(record.getOutTime(), DateUtils.YYYYMMDD_DOT),
                        uv.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(uv.getGender()),//性别
                        uv.getNation(),//民族
                        uv.getNativePlace(),
                        DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMMDD_DOT),
                        uv.getBirth() != null ? DateUtils.intervalYearsUntilNow(uv.getBirth()) + "" : "",//年龄
                        DateUtils.formatDate(record.getAddTime(), DateUtils.YYYYMMDD_DOT),
                        record.getUnit(),
                        record.getPost(),
                        record.getEducation(),
                        record.getDegree(),
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }

        if (cls == 1){
            String fileName = String.format("无党派人士(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(noPartyTitles, valuesList, fileName, response);
        }else if (cls == 2){
            String fileName = String.format("已移除的无党派人士(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(outTitles, valuesList, fileName, response);
        }/*else {
            String fileName = String.format("已转出的无党派人士(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            ExportHelper.export(transferTitles, valuesList, fileName, response);
        }*/

    }

    @RequestMapping("/dpNpm_selects")
    @ResponseBody
    public Map dpNpm_selects(Integer pageSize,
                             Integer pageNo,
                             Byte status,
                             String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpNpmExample example = new DpNpmExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        long count = dpNpmMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpNpm> records = dpNpmMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpNpm record:records){
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
