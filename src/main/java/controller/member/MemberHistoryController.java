package controller.member;

import controller.global.OpException;
import domain.base.MetaType;
import domain.member.*;
import domain.member.MemberHistoryExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberHistoryController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    @RequiresPermissions("memberHistory:list")
    @RequestMapping("/memberHistory")
    public String memberHistory(@RequestParam(required = false, defaultValue = "0") Byte cls,
                                ModelMap modelMap) {

        modelMap.put("cls", cls);

        int total = (int) memberHistoryMapper.countByExample(new MemberHistoryExample());
        MemberHistoryExample example = new MemberHistoryExample();
        example.createCriteria().andStatusEqualTo((byte) 0);
        modelMap.put("total", total);
        modelMap.put("normalCount", memberHistoryMapper.countByExample(example));

        return "member/memberHistory/memberHistory_page";
    }

    @RequiresPermissions("memberHistory:list")
    @RequestMapping("/memberHistory_data")
    @ResponseBody
    public void memberHistory_data(HttpServletResponse response,
                                   String code,
                                   String realname,
                                   String idcard,
                                   Byte type,
                                   Byte gender,
                                   String partyName,
                                   String branchName,
                                   Byte politicalStatus,
                                   @RequestDateRange DateRange _growTime,
                                   @RequestDateRange DateRange _positiveTime,
                                   String detailReason,
                                   String outReason,
                                   String remark,
                                   @RequestParam(required = false, defaultValue = "0") Byte cls,
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

        MemberHistoryExample example = new MemberHistoryExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(cls);
        example.setOrderByClause("id desc");
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)){
            if (ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)){
                criteria.andAddUserIdEqualTo(ShiroHelper.getCurrentUserId());
            }else {
                throw new UnauthorizedException();
            }
        }

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.trimLike(code));
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike(SqlUtils.trimLike(realname));
        }
        if (StringUtils.isNotBlank(idcard)) {
            criteria.andIdcardLike(SqlUtils.trimLike(idcard));
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (StringUtils.isNotBlank(partyName)) {
            criteria.andPartyNameLike(SqlUtils.trimLike(partyName));
        }
        if (StringUtils.isNotBlank(branchName)) {
            criteria.andBranchNameLike(SqlUtils.trimLike(branchName));
        }
        if (politicalStatus != null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (_growTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }
        if (_growTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }
        if (_positiveTime.getStart() != null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }
        if (_positiveTime.getEnd() != null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        if (StringUtils.isNotBlank(detailReason)) {
            criteria.andDetailReasonLike(SqlUtils.trimLike(detailReason));
        }
        if (StringUtils.isNotBlank(outReason)) {
            criteria.andOutReasonEqualTo(SqlUtils.trimLike(outReason));
        }
        if (StringUtils.isNotBlank(remark)) {
            criteria.andRemarkLike(SqlUtils.trimLike(remark));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberHistory_export(cls, example, response);
            return;
        }

        long count = memberHistoryMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberHistory> records= memberHistoryMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(memberHistory.class, memberHistoryMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "/memberHistory_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberHistory_au(MemberHistory record,
                                   HttpServletRequest request) {

        Integer id = record.getId();
        String code = record.getCode();
        if (StringUtils.isNotBlank(code)){
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null){
                throw new OpException("学工号不存在");
            }
            record.setUserId(uv.getUserId());
        }

        if (id == null) {

            if (memberHistoryService.isDuplicate(record.getRealname(),record.getCode(), record.getIdcard())){
                throw new OpException("添加重复");
            }
            memberHistoryService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加历史党员：{0}", record.getId()));

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "添加", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "添加"+record.getRealname()+"至历史党员库");
        } else {

            memberHistoryService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新历史党员：{0}", record.getId()));

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "修改", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "修改历史党员"+record.getRealname()+"的信息");
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping("/memberHistory_au")
    public String memberHistory_au(Integer id,
                                   String code,
                                   ModelMap modelMap) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if (id != null) {
            MemberHistory memberHistory = memberHistoryMapper.selectByPrimaryKey(id);
            modelMap.put("memberHistory", memberHistory);
        }

        if (StringUtils.isNotBlank(code)){

            MemberHistory memberHistory = new MemberHistory();

            SysUserView uv = sysUserService.findByCode(code);
            MemberViewExample example = new MemberViewExample();
            example.createCriteria().andUserIdEqualTo(uv.getId());
            List<MemberView> memberViewList = memberViewMapper.selectByExample(example);
            if (memberViewList == null || memberViewList.size() == 0) {
                PropertyUtils.copyProperties(memberHistory, uv);
            }else {
                MemberView memberView = memberViewList.get(0);
                PropertyUtils.copyProperties(memberHistory, memberView);
                memberHistory.setType(memberView.getUserType());
                memberHistory.setPartyName(memberView.getPartyName());
                memberHistory.setBranchName(memberView.getBranchName());
            }
            memberHistory.setStatus((byte) 1);
            if (id!=null){
                memberHistory.setId(id);
            }else {
                memberHistory.setId(null);
            }
            modelMap.put("memberHistory", memberHistory);

        }
        return "member/memberHistory/memberHistory_au";
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping("/memberHistory_out")
    public String memberHistory_out(){

        return "member/memberHistory/memberHistory_out";
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "/memberHistory_out", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberHistory_out(Integer[] ids, String outReason, HttpServletRequest request){

        if (null != ids && ids.length>0){
            memberHistoryService.out(ids, outReason);
            logger.info(log( LogConstants.LOG_PARTY, "将历史党员移至已移除：%s",StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "/memberHistory_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberHistory_recover(Integer[] ids,
                             HttpServletRequest request){
        if (null != ids && ids.length>0){
            memberHistoryService.recover(ids);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping("/recoverToMember")
    public String recoverToMember(){

        return "member/memberHistory/recoverToMember";
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "/recoverToMember", method = RequestMethod.POST)
    @ResponseBody
    public Map do_recoverToMember(Integer[] ids, Integer partyId, Integer branchId, HttpServletRequest request) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if (null != ids && ids.length>0){
            memberHistoryService.recoverToMember(ids, partyId, branchId);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberHistory:del")
    @RequestMapping(value = "/memberHistory_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map memberHistory_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){

            memberHistoryService.batchDel(ids);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除历史党员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberHistory_export(Byte cls, MemberHistoryExample example, HttpServletResponse response) {

        List<MemberHistory> records = memberHistoryMapper.selectByExample(example);
        int rownum = records.size();
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"学工号|100","姓名|100","人员类别|100","性别|100","身份证号|160","民族|100","籍贯|100","出生年月|100",
                "二级党组织名称|350","党支部名称|350", "党籍状态|100","标签|200","组织关系转入时间|100","提交书面申请书时间|100","确定为入党积极分子时间|100","确定为发展对象时间|100",
                "入党介绍人|100", "入党时间|100","转正时间|100","专业技术职务|100","手机|100","邮箱|100","添加人|100","添加时间|110","转移原因|200","备注|150"}));
        List<List<String>> valuesList = new ArrayList<>();
        if (cls==1){
            titles.add(22, "移除原因|200");
        }
        for (int i = 0; i < rownum; i++) {
            MemberHistory record = records.get(i);
            String lable = "";
            if (record.getLable() != null){
                String[] lables = StringUtils.split(record.getLable(), ",");
                for (String str : lables) {
                    lable+=CmTag.getMetaType(Integer.valueOf(str)).getName();
                }
            }
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                record.getCode(),
                    record.getRealname(),
                    SystemConstants.USER_TYPE_MAP.get(record.getType()),
                    SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getIdcard(),
                    record.getNation(),
                    record.getNativePlace(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),
                    record.getPartyName(),
                    record.getBranchName(),
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    lable,
                    DateUtils.formatDate(record.getTransferTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYYMMDD_DOT),
                    record.getSponsor(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYYMMDD_DOT),
                    record.getProPost(),
                    record.getMobile(),
                    record.getEmail(),
                    record.getAddUser().getRealname(),
                    DateUtils.formatDate(record.getAddDate(), DateUtils.YYYYMMDD_DOT),
                    record.getDetailReason(),
                    record.getRemark(),
            }));
            if (cls==1){
                values.add(22, record.getOutReason());
            }
            valuesList.add(values);
        }
        String fileName = String.format("历史党员库(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping("/memberHistory_import")
    public String memberHistory_import() {

        return "/member/memberHistory/memberHistory_import";
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "memberHistory_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberHistory_import(HttpServletRequest request) throws InvalidFormatException,IOException{

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer,String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<String> lableValueList = new ArrayList<>();
        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_mh_lable");
        if (metaTypeMap!=null&&metaTypeMap.size()>0){
            for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()) {
                lableValueList.add(entry.getValue().getName());
            }
        }
        List<MemberHistory> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            int col = 0;
            MemberHistory record = new MemberHistory();
            String code = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(code)){
                throw new OpException("第{0}行学工号为空",row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                throw new OpException("第{0}行学工号为不存在",row);
            }
            record.setCode(code);
            String realname = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(realname)) {
                throw new OpException("第{0}行姓名为空",row);
            }
            record.setRealname(realname);
            String _type = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_type)){
                throw new OpException("第{0}行人员类别为空",row);
            }
            for (Map.Entry<Byte, String> entry : SystemConstants.USER_TYPE_MAP.entrySet()) {
                if (StringUtils.equals(entry.getValue(), _type)){
                    record.setType(entry.getKey());
                }
            }
            if (record.getType() == null){
                throw new OpException("第{0}行人员类别不存在", row);
            }

            record.setGender((byte) (StringUtils.equals(StringUtils.trimToNull(xlsRow.get(col++)), "男") ? 1 : 2));
            record.setIdcard(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setNation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setNativePlace(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setBirth(DateUtils.parseStringToDate(StringUtils.trimToEmpty(xlsRow.get(col++))));
            String partyName = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(partyName)) {
                throw new OpException("第{0}行二级党组织名称为空",row);
            }
            record.setPartyName(partyName);
            record.setBranchName(StringUtils.trimToNull(xlsRow.get(col++)));
            String politicalStatus = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(politicalStatus)){
                throw new OpException("第{0}行党籍状态为空",row);
            }
            for (Map.Entry<Byte, String> entry : MemberConstants.MEMBER_POLITICAL_STATUS_MAP.entrySet()) {
                if (StringUtils.equals(entry.getValue(), politicalStatus)){
                    record.setPoliticalStatus(entry.getKey());
                }
            }
            if (record.getPoliticalStatus() == null){
                throw new OpException("第{0}行人员状态别不存在", row);
            }

            String _lable = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isNotBlank(_lable)){
                List<Integer> lableList= new ArrayList<>();
                List<String> _lableList = new ArrayList<>(Arrays.asList(StringUtils.split(_lable, ",")));
                for (String str : _lableList) {
                    if (lableValueList.contains(str)){
                        lableList.add(metaTypeService.findByName("mc_mh_lable", str).getId());
                    }
                }
                record.setLable(StringUtils.join(lableList, ","));
            }
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setProPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setMobile(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDetailReason(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));

            records.add(record);
            row++;
        }
        Collections.reverse(records);//逆序排列，保证导入的顺序正确

        int addCount = memberHistoryService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("totalCount", totalCount);

        logger.info(log(LogConstants.LOG_MEMBER,
                "导入历史党员成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount-addCount));

        return resultMap;
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping(value = "memberHistory_fill", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberHistory_fill(String code){

        Map<String, Object> resultMap = success();

        int result = 0;
        if (StringUtils.isBlank(code)){
            result = 1; // 学工号为空
            resultMap.put("result", result);
            return resultMap;
        }
        SysUserView uv = sysUserService.findByCode(code);

        if (uv == null) {
            result = 2; // 学工号不存在
        }
        if (uv != null) {
            result = 3; // 该学工号存在，可以添加
        }

        resultMap.put("result", result);

        return resultMap;
    }
}
