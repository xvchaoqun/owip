package controller.member;

import controller.global.OpException;
import domain.member.MemberHistory;
import domain.member.MemberHistoryExample;
import domain.member.MemberHistoryExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberHistoryController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberHistory:list")
    @RequestMapping("/memberHistory")
    public String memberHistory() {

        return "member/memberHistory/memberHistory_page";
    }

    @RequiresPermissions("memberHistory:list")
    @RequestMapping("/memberHistory_data")
    @ResponseBody
    public void memberHistory_data(HttpServletResponse response,
                                   String code,
                                   String realname,
                                   Byte type,
                                   Byte gender,
                                   String partyName,
                                   String branchName,
                                   Byte politicalStatus,
                                   @RequestDateRange DateRange _growTime,
                                   @RequestDateRange DateRange _positiveTime,
                                   String remark1,
                                   String remark2,
                                   String remark3,
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
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)){
            if (ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)){
                criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
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
        if (StringUtils.isNotBlank(remark1)) {
            criteria.andRemark1Like(SqlUtils.trimLike(remark1));
        }
        if (StringUtils.isNotBlank(remark2)) {
            criteria.andRemark2Like(SqlUtils.trimLike(remark2));
        }
        if (StringUtils.isNotBlank(remark3)){
            criteria.andRemark3Like(SqlUtils.trimLike(remark3));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberHistory_export(example, response);
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
                                   String _birth,
                                   String _transferTime,
                                   String _applyTime,
                                   String _activeTime,
                                   String _candidateTime,
                                   String _growTime,
                                   String _positiveTime,
                                   HttpServletRequest request) {

        SysUserView uv = ShiroHelper.getCurrentUser();

        memberHistoryService.checkAuth(record);

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_birth)) {
            record.setBirth(DateUtils.parseStringToDate(_birth));
        }
        if (StringUtils.isNotBlank(_transferTime)) {
            record.setTransferTime(DateUtils.parseStringToDate(_transferTime));
        }
        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseStringToDate(_applyTime));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            record.setActiveTime(DateUtils.parseStringToDate(_activeTime));
        }
        if (StringUtils.isNotBlank(_candidateTime)) {
            record.setCandidateTime(DateUtils.parseStringToDate(_candidateTime));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseStringToDate(_growTime));
        }
        if (StringUtils.isNotBlank(_positiveTime)) {
            record.setPositiveTime(DateUtils.parseStringToDate(_positiveTime));
        }

        if (id == null) {

            if (memberHistoryService.isDuplicate(record.getRealname(),record.getCode())){
                throw new OpException("添加重复");
            }
            memberHistoryService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加历史党员：{0}", record.getId()));
        } else {

            memberHistoryService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新历史党员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberHistory:edit")
    @RequestMapping("/memberHistory_au")
    public String memberHistory_au(Integer id,
                                   ModelMap modelMap) {

        if (id != null) {
            MemberHistory memberHistory = memberHistoryMapper.selectByPrimaryKey(id);
            modelMap.put("memberHistory", memberHistory);
        }
        return "member/memberHistory/memberHistory_au";
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
    public void memberHistory_export(MemberHistoryExample example, HttpServletResponse response) {

        List<MemberHistory> records = memberHistoryMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100","身份证号|160","类别|100","性别|60","民族|100","籍贯|100","出生年月|100","二级党组织名称|350","党支部名称|350",
                "党籍状态|100","组织关系转入时间|100","提交书面申请书时间|100","确定为入党积极分子时间|100","确定为发展对象时间|100","入党介绍人|100",
                "入党时间|100","转正时间|100","专业技术职务|100","手机|100","邮箱|100","备注1|150","备注2|150","备注3|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberHistory record = records.get(i);
            String[] values = {
                record.getCode(),
                    record.getRealname(),
                    record.getIdCard(),
                    MemberConstants.MEMBER_TYPE_MAP.get(record.getType()),
                    SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getNativePlace(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),
                    record.getPartyName(),
                    record.getBranchName(),
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    DateUtils.formatDate(record.getTransferTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYYMMDD_DOT),
                    record.getSponsor(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYYMMDD_DOT),
                    record.getProPost(),
                    record.getPhone(),
                    record.getEmail(),
                    record.getRemark1(),
                    record.getRemark2(),
                    record.getRemark3()
            };
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

        List<MemberHistory> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            int col = 0;
            MemberHistory record = new MemberHistory();
            row++;
            String code = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(code)){
                throw new OpException("第{0}行学工号为空", row);
            }
            record.setCode(code);
            String realname = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(realname)) {
                throw new OpException("第{0}行姓名为空",row);
            }
            record.setRealname(realname);
            record.setIdCard(StringUtils.trimToNull(xlsRow.get(col++)));

            String type = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(type)){
                throw new OpException("第{0}行人员类别为空", row);
            }
            boolean hasType = false;
            for (Map.Entry<Byte, String> entry : MemberConstants.MEMBER_TYPE_MAP.entrySet()) {
                if (StringUtils.equals(entry.getValue(), type)){
                    record.setType(entry.getKey());
                    hasType = true;
                }
            }
            if (!hasType){
                throw new OpException("第{0}行人员类别有误", row);
            }

            record.setGender((byte) (StringUtils.equals(StringUtils.trimToNull(xlsRow.get(col++)), "男") ? 1 : 2));
            record.setNation(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setNativePlace(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setBirth(DateUtils.parseStringToDate(StringUtils.trimToEmpty(xlsRow.get(col++))));
            String partyName = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(partyName)) {
                throw new OpException("第{0}行二级党组织名称为空",row);
            }
            record.setPartyName(partyName);
            record.setBranchName(StringUtils.trimToNull(xlsRow.get(col++)));
            String status = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(status)){
                throw new OpException("第{0}行党籍状态为空",row);
            }
            record.setPoliticalStatus((byte) (StringUtils.equals(StringUtils.trimToNull(xlsRow.get(col++)), "正式党员") ? 2:1));
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setProPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPhone(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setRemark1(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setRemark2(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setRemark3(StringUtils.trimToNull(xlsRow.get(col++)));

            records.add(record);
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
}
