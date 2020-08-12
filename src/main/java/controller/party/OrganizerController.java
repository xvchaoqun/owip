package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.party.Branch;
import domain.party.Organizer;
import domain.party.OrganizerExample;
import domain.party.OrganizerExample.Criteria;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.format.annotation.DateTimeFormat;
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
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.OwConstants.*;

@Controller
public class OrganizerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer")
    public String organizer(@RequestParam(required = false, defaultValue = "1") Byte cls,
                            byte type,
                            Integer userId,
                            Integer partyId,
                            Integer branchId,
                            Integer unitId,
                            ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        if (cls == 10) {
            return "forward:/organizerGroup";
        }

        if(userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if(partyId!=null){
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        if(branchId!=null){
            modelMap.put("branch", branchService.findAll().get(branchId));
        }

        if(unitId!=null){
            modelMap.put("unit", unitService.findAll().get(unitId));
        }

        return "party/organizer/organizer_page";
    }

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer_data")
    @ResponseBody
    public void organizer_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               Integer year,
                               byte type,
                               Integer userId,
                               Integer partyId,
                               Integer branchId,
                               Integer unitId,
                               String unit,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerExample example = new OrganizerExample();
        Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type);
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        if(cls==1 || cls==2) {
            example.setOrderByClause("sort_order desc");
        }else if(cls==3) {
            example.setOrderByClause("id desc");
        }

        if (cls == 1) {
            criteria.andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_NOW);
        } else if (cls == 2) {
            criteria.andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_LEAVE);
        } else if(cls==3){
            criteria.andStatusIn(Arrays.asList(OwConstants.OW_ORGANIZER_STATUS_NOW,
                    OwConstants.OW_ORGANIZER_STATUS_HISTORY));
        }else{
            criteria.andIdIsNull();
        }

        if (year != null) {
            criteria.andYearEqualTo(year);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            organizer_export(type,cls,example, response);
            return;
        }

        long count = organizerMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Organizer> records = organizerMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(organizer.class, organizerMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_au(@CurrentUser SysUserView loginUser,
                               Organizer record,
                               Boolean syncBaseInfo, // 更新时是否重新同步基本信息
                               HttpServletRequest request) {
        Integer id = record.getId();

        Integer loginUserId = loginUser.getId();
        if (!branchMemberService.hasAdminAuth(loginUserId, record.getPartyId(), record.getBranchId()))
            throw new UnauthorizedException();

        Organizer organizer = organizerService.get(record.getType(), record.getUserId());
        if ((id==null && organizer!=null) || (id!=null &&organizer!=null&& id != organizer.getId())) {
                throw new OpException(organizer.getUser().getRealname()
                        + "已经是"
                        + OwConstants.OW_ORGANIZER_STATUS_MAP.get(organizer.getStatus())
                        + OW_ORGANIZER_TYPE_MAP.get(organizer.getType()));
        }

        if (id == null) {

            organizerService.insertSelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "添加组织员信息：{0}", JSONUtils.toString(record, false)));
        } else {

            organizerService.updateByPrimaryKeySelective(record, BooleanUtils.isTrue(syncBaseInfo));
            logger.info(log(LogConstants.LOG_PARTY, "更新组织员信息：{0}", JSONUtils.toString(record, false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_au")
    public String organizer_au(Integer id, Byte type,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               ModelMap modelMap) {
        byte status;
        if(cls==1){
            status = OwConstants.OW_ORGANIZER_STATUS_NOW;
        }else if(cls==2){
            status = OwConstants.OW_ORGANIZER_STATUS_LEAVE;
        }else if(cls==3){
            status = OwConstants.OW_ORGANIZER_STATUS_HISTORY;
        }else{
            return null;
        }

        if (id != null) {
            Organizer organizer = organizerMapper.selectByPrimaryKey(id);
            modelMap.put("organizer", organizer);
            type = organizer.getType();
            status = organizer.getStatus();

            modelMap.put("sysUser", sysUserService.findById(organizer.getUserId()));
            modelMap.put("party", partyService.findAll().get(organizer.getPartyId()));
            modelMap.put("branch", branchService.findAll().get(organizer.getBranchId()));

        }

        modelMap.put("type", type);
        modelMap.put("status", status);

        return "party/organizer/organizer_au";
    }
    // 导入会议
    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_import(Byte type, Byte cls,HttpServletRequest request) throws IOException, InvalidFormatException, InterruptedException {

        byte status;
        if(cls==1){
            status = OwConstants.OW_ORGANIZER_STATUS_NOW;
        }else if(cls==2){
            status = OwConstants.OW_ORGANIZER_STATUS_LEAVE;
        }else if(cls==3){
            status = OwConstants.OW_ORGANIZER_STATUS_HISTORY;
        }else{
            return null;
        }

        byte organizerType;
        if(type==1){
            organizerType = OW_ORGANIZER_TYPE_SCHOOL;
        }else if(type==2){
            organizerType = OW_ORGANIZER_TYPE_UNIT;
        }else if(type==3){
            organizerType = OW_ORGANIZER_TYPE_BRANCH;
        }else{
            return null;
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;

        resultMap = importOrganizer_data(xlsRows,organizerType, status);

        int successCount = (int) resultMap.get("successCount");
        int totalCount = (int) resultMap.get("total");

        logger.info(log(LogConstants.LOG_PARTY,
                "导入组织员信息成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_import")
    public String organizer_import(Byte type,
                                   @RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

            modelMap.put("type", type);
            modelMap.put("cls", cls);

        return "party/organizer/organizer_import";
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_leave(int id,
                                  Byte status,
                                  @CurrentUser SysUserView loginUser,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date dismissDate,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date appointDate,
                                  HttpServletRequest request) {

        Organizer organizer = organizerMapper.selectByPrimaryKey(id);
        Integer loginUserId = loginUser.getId();
        if (!branchMemberService.hasAdminAuth(loginUserId, organizer.getPartyId(), organizer.getBranchId()))
            throw new UnauthorizedException();

        if(status==null || status==OwConstants.OW_ORGANIZER_STATUS_LEAVE) {

            organizerService.leave(id, dismissDate);
            logger.info(log(LogConstants.LOG_PARTY, "组织员离任：{0}", JSONUtils.toString(organizer, false)));
        }else{
            organizerService.reAppoint(id, appointDate);
            logger.info(log(LogConstants.LOG_PARTY, "离任组织员重新任用：{0}", JSONUtils.toString(organizer, false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_leave")
    public String organizer_leave(Integer id, ModelMap modelMap) {

        if (id != null) {
            Organizer organizer = organizerMapper.selectByPrimaryKey(id);
            modelMap.put("organizer", organizer);

            modelMap.put("sysUser", sysUserService.findById(organizer.getUserId()));
        }

        return "party/organizer/organizer_leave";
    }

    @RequiresPermissions("organizer:del")
    @RequestMapping(value = "/organizer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map organizer_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            organizerService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除组织员信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:changeOrder")
    @RequestMapping(value = "/organizer_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerService.changeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_PARTY, "组织员信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void organizer_export(byte type,Byte cls,OrganizerExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<Organizer> records = organizerMapper.selectByExample(example);
        int rownum = records.size();

        String[] titles=null;
        if(type==OW_ORGANIZER_TYPE_SCHOOL){
            titles = new String[]{"年度|100", "工作证号|200","姓名|100","联系方式|200","排序|100", "所在单位|250","行政职务|250","性别|50","出生日期|200","入党时间|200","编制类别|100","人员类别|100", "专业技术职务|250","任职时间|200","离任时间|200", "备注|300"};
        } else if(type==OW_ORGANIZER_TYPE_UNIT){
            titles = new String[]{"年度|100", "工作证号|200","姓名|100","联系方式|200","排序|100","所在单位|250" ,"行政职务|250","性别|50","出生日期|200","入党时间|200","编制类别|100","人员类别|100", "专业技术职务|250","联系党委|250","任职时间|200", "离任时间|200", "备注|300"};
        }else if(type==OW_ORGANIZER_TYPE_BRANCH){
            titles = new String[]{"年度|100", "工作证号|200","姓名|100","联系方式|200","排序|100","所在单位|250","行政职务|250","性别|50","出生日期|200","入党时间|200","编制类别|100","人员类别|100", "专业技术职务|250",  "联系党委|250","联系党支部|250","任职时间|200", "离任时间|200", "备注|300"};
        }

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Organizer record = records.get(i);
            String[] values=null;
            if(type==OW_ORGANIZER_TYPE_SCHOOL){
                values =new String[]{
                    record.getYear() + "",
                    record.getUser().getCode() + "",
                    record.getUser().getRealname() + "",
                    record.getPhone(),
                    record.getSortOrder() + "",
                    record.getUnit(),
                    record.getPost(),
                    record.getUser().getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getUser().getGender()),
                    DateUtils.formatDate(record.getUser().getBirth(), DateUtils.YYYYMMDD_DOT),//出生时间
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getAuthorizedType(),
                    record.getStaffType(),
                    record.getProPost(),
                    DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getDismissDate(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
                };
           }else if(type==OW_ORGANIZER_TYPE_UNIT){
               values =new String[]{
                       record.getYear() + "",
                       record.getUser().getCode() + "",
                       record.getUser().getRealname() + "",
                       record.getPhone(),
                       record.getSortOrder()+ "",
                       record.getUnit(),
                       record.getPost(),
                       record.getUser().getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getUser().getGender()),
                       DateUtils.formatDate(record.getUser().getBirth(),DateUtils.YYYYMMDD_DOT),//出生时间
                       DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                       record.getAuthorizedType(),
                       record.getStaffType(),
                       record.getProPost(),
                       record.getPartyId()==null?"":partyMap.get(record.getPartyId()).getName(),
                       DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                       DateUtils.formatDate(record.getDismissDate(), DateUtils.YYYY_MM_DD),
                       record.getRemark()
                };
            }else if(type==OW_ORGANIZER_TYPE_BRANCH){
                values =new String[]{
                        record.getYear() + "",
                        record.getUser().getCode() + "",
                        record.getUser().getRealname() + "",
                        record.getPhone(),
                        record.getSortOrder()+ "",
                        record.getUnit(),
                        record.getPost(),
                        record.getUser().getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getUser().getGender()),
                        DateUtils.formatDate(record.getUser().getBirth(),DateUtils.YYYYMMDD_DOT),//出生时间
                        DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                        record.getAuthorizedType(),
                        record.getStaffType(),
                        record.getProPost(),
                        record.getPartyId()==null?"":partyMap.get(record.getPartyId()).getName(),
                        record.getBranchId()==null?"":branchMap.get(record.getBranchId()).getName(),
                        DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(record.getDismissDate(), DateUtils.YYYY_MM_DD),
                        record.getRemark()
                };
            }
            valuesList.add(values);
        }
        String fileName = OW_ORGANIZER_STATUS_MAP.get(cls)+OW_ORGANIZER_TYPE_MAP.get(type)+"信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // <userId, >
    @RequestMapping("/organizer_selects")
    @ResponseBody
    public Map organizer_selects(Integer pageSize, Integer pageNo, Byte type, Byte status, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        long count = iPartyMapper.countOrganizerList(searchStr, type, status);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Organizer> records = iPartyMapper.selectOrganizerList(searchStr, type, status,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(Organizer record:records){
                SysUserView uv = record.getUser();

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("del", record.getStatus() != OwConstants.OW_ORGANIZER_STATUS_NOW);
                option.put("username", uv.getUsername());
                option.put("code", uv.getCode());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    private Map<String, Object> importOrganizer_data(List<Map<Integer, String>> xlsRows,
                                              byte type, byte status) throws InterruptedException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if (BooleanUtils.isNotTrue(branch.getIsDeleted())) {
                runBranchMap.put(branch.getCode(), branch);
            }
        }
        List<Organizer> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            Organizer record = new Organizer();

            record.setType(type);
            record.setStatus(status);

            String year = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(year)) {
                throw new OpException("第{0}行年度为空", row);
            }
            record.setYear(Integer.valueOf(year));

            String code = StringUtils.trim(xlsRow.get(1));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView user = sysUserService.findByCode(code);
            record.setUserId(user.getId());

            record.setPhone(StringUtils.trimToNull(xlsRow.get(3)));
            if(type!=OW_ORGANIZER_TYPE_SCHOOL){
                String partyCode = StringUtils.trim(xlsRow.get(4));
                if (StringUtils.isBlank(partyCode)) {
                    throw new OpException("第{0}行联系党委编码为空", row);
                }
                Party party = runPartyMap.get(partyCode);
                if (party == null) {
                    throw new OpException("第{0}行联系党委编码[{1}]不存在", row, partyCode);
                }
                if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),party.getId())){
                    throw new OpException("您没有权限导入第{0}行联系党委数据", row);
                }
                record.setPartyId(party.getId());
                if(type==OW_ORGANIZER_TYPE_BRANCH){
                    String branchCode = StringUtils.trim(xlsRow.get(6));
                    Branch branch = runBranchMap.get(branchCode);
                    if (!partyService.isDirectBranch(party.getId())) {

                        if (StringUtils.isBlank(branchCode)) {
                            throw new OpException("第{0}行联系党支部编码为空", row);
                        }
                        if (branch == null) {
                            throw new OpException("第{0}行联系党支部编码[{1}]不存在", row, partyCode);
                        }
                        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),party.getId(),branch.getId())){
                            throw new OpException("您没有权限导入第{0}行联系党支部数据", row);
                        }
                        record.setBranchId(branch.getId());
                    }
                }
            }

            int col = 4;
            if(type==OW_ORGANIZER_TYPE_UNIT){
                 col = 6;
            }else if(type==OW_ORGANIZER_TYPE_BRANCH){
                col = 8;
            }


            record.setAppointDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            if(status!=OW_ORGANIZER_STATUS_NOW) {
                record.setDismissDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            }
            records.add(record);
        }

        int successCount = organizerService.organizerImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }
}
