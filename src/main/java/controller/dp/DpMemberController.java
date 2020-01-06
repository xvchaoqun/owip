package controller.dp;

import controller.global.OpException;
import domain.dp.*;
import domain.member.Member;
import domain.sys.SysUserView;
import domain.unit.Unit;
import interceptor.OrderParam;
import mixin.MemberMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
import service.dp.DpPartyService;
import shiro.ShiroHelper;
import sys.constants.DpConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.helper.DpPartyHelper;
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

@Controller
@RequestMapping("/dp")
public class DpMemberController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DpPartyService dpPartyService;

    @RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember")
    public String dpMember(HttpServletResponse response,
                            Integer userId,
                           Integer partyId,
                           Integer unitId,
                           //2 教职工 7 已转出教职工 10 全部
                           @RequestParam(defaultValue = "2")Integer cls,
                           @RequestParam(required = false, value = "post") String[] post,
                           @RequestParam(required = false, value = "adminLevel") Integer[] adminLevel,
                           @RequestParam(required = false, value = "nation") String[] nation,
                           @RequestParam(required = false, value = "nativePlace") String[] nativePlace,
                           ModelMap modelMap) {

        if (unitId != null){
            modelMap.put("unit", unitMapper.selectByPrimaryKey(unitId));
        }
        //查询登录用户管理的民主党派
        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL);
        List<Integer> adminDpPartyIdList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());

        //统计学生、教职工各个状态人数
        Map dpMemberTeacherCount = iDpMemberMapper.selectDpMemberTeacherCount(addPermits,adminDpPartyIdList);
        if (dpMemberTeacherCount != null){
            modelMap.putAll(dpMemberTeacherCount);
        }

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        if (partyId != null){
            modelMap.put("dpParty", dpPartyMap.get(partyId));
        }
        if (post != null){
            List<String> selectPosts = Arrays.asList(post);
            modelMap.put("selectPosts", selectPosts);
        }
        if (adminLevel != null){
            List<Integer> selectAdminLevels = Arrays.asList(adminLevel);
            modelMap.put("selectAdminLevels", selectAdminLevels);
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            modelMap.put("selectNativePlaces", selectNativePlaces);
        }

        modelMap.put("cls", cls);

        //导出的名字
        modelMap.put("metaAdminLevel", metaTypeService.metaTypes("mc_admin_level").values());
        modelMap.put("metaPost", metaTypeService.metaTypes("mc_post").values());

        List<String> titles = new ArrayList<>();
        if (cls == 2 || cls == 3 || cls == 7){ //教师党派成员
            titles = getTeacherExportTitles();
            if (cls == 3){
                titles.add(6, "离退休时间|80");
            }
            if (iDpPropertyMapper.teacherPosts() != null) {
                modelMap.put("posts", iDpPropertyMapper.teacherPosts());
            }
            if (iDpPropertyMapper.teacherAdminLevels() != null) {
                modelMap.put("adminLevels", iDpPropertyMapper.teacherAdminLevels());
            }
            modelMap.put("nations", iDpPropertyMapper.teacherNations());
            modelMap.put("nativePlaces", iDpPropertyMapper.teacherNativePlaces());
        }  else if (cls == 10){
            if (iDpPropertyMapper.teacherPosts() != null) {
                modelMap.put("posts", iDpPropertyMapper.teacherPosts());
            }
            if (iDpPropertyMapper.teacherAdminLevels() != null) {
                modelMap.put("adminLevels", iDpPropertyMapper.teacherAdminLevels());
            }
            modelMap.put("nations", iDpPropertyMapper.nations());
            modelMap.put("nativePlaces", iDpPropertyMapper.nativePlaces());
        }

        modelMap.put("titles", titles);

        return "/dp/dpMember/dpMember_page";
    }

    @RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember_data")
    public void dpMember_data(HttpServletResponse response,
                                 @RequestParam(defaultValue = "dpParty") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    @RequestParam(defaultValue = "2")int cls,
                                    Integer userId,
                                    Integer partyId,
                                    String unit,
                                    Byte gender,
                                    Byte age,
                                    String edu,
                                    String degree,
                                    @RequestParam(required = false, value = "post") String[] post,
                                    @RequestParam(required = false, value = "adminLevel") Integer[] adminLevel,
                                    @RequestParam(required = false, value = "nation") String[] nation,//
                                    @RequestParam(required = false, value = "nativePlace") String[] nativePlace,//
                                    @RequestDateRange DateRange _dpGrowTime,
                                    Byte userSource, //账号来源

                                    /**教职工党员**/
                                    @RequestDateRange DateRange _retireTime,
                                    Boolean isPartyMember,
                                    Boolean isHonorRetire,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 @RequestParam(required = false) Integer[] cols, // 选择导出的列
                                 Integer pageSize, Integer pageNo)  throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpMemberViewExample example = new DpMemberViewExample();
        DpMemberViewExample.Criteria criteria = example.createCriteria();

        if (StringUtils.equalsIgnoreCase(sort, "dpParty")) {
            example.setOrderByClause(String.format("party_id, dp_grow_time desc"));
        } else if (StringUtils.equalsIgnoreCase(sort, "dpGrowTime")) {
            example.setOrderByClause(String.format("dp_grow_time %s", order));
        }
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));


        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }
        if (StringUtils.isNotBlank(edu)){
            criteria.andEduLike(SqlUtils.like(edu));
        }
        if (StringUtils.isNotBlank(degree)){
            criteria.andDegreeLike(SqlUtils.like(degree));
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (post != null){
            List<String> selectPosts = Arrays.asList(post);
            criteria.andPostIn(selectPosts);
        }
        if (adminLevel != null){
            List<Integer> selectAdminLevels = Arrays.asList(adminLevel);
            criteria.andAdminLevelIn(selectAdminLevels);
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }

            if (age != null) {
                switch (age) {
                    case DpConstants.DP_MEMBER_AGE_20: // 20及以下
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                        break;
                    case DpConstants.DP_MEMBER_AGE_21_30:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -31))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                        break;
                    case DpConstants.DP_MEMBER_AGE_31_40:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -41))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                        break;
                    case DpConstants.DP_MEMBER_AGE_41_50:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -51))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                        break;
                    case DpConstants.DP_MEMBER_AGE_51: // 51及以上
                        criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
                        break;
                    case DpConstants.DP_MEMBER_AGE_0:
                        criteria.andBirthIsNull();
                        break;
                }
            }


        if (_retireTime.getStart() != null) {
            criteria.andRetireTimeGreaterThanOrEqualTo(_retireTime.getStart());
        }

        if (_retireTime.getEnd() != null) {
            criteria.andRetireTimeLessThanOrEqualTo(_retireTime.getEnd());
        }

        if (isHonorRetire != null) {
            criteria.andIsHonorRetireEqualTo(isHonorRetire);
        }

        if (_dpGrowTime.getStart() != null) {
            criteria.andDpGrowTimeGreaterThanOrEqualTo(_dpGrowTime.getStart());
        }

        if (_dpGrowTime.getEnd() != null) {
            criteria.andDpGrowTimeLessThanOrEqualTo(_dpGrowTime.getEnd());
        }
        if (isPartyMember != null){
            criteria.andIsPartyMemberEqualTo(isPartyMember);
        }
        /*
           2教职工 3离退休 6已转出学生 7 已转出教职工 10全部
         */
        switch (cls) {
            case 2:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL)
                        .andIsRetireNotEqualTo(true);
                break;
            case 3:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 6:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_STUDENT)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_TRANSFER);
                break;
            case 7:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_TRANSFER);
                break;
            case 10:
                criteria.andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL);
                break;
            default:
                criteria.andUserIdIsNull();
                break;
        }


        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            if (cls == 2 || cls == 3 || cls == 7 || cls == 10){
                teacher_export(cls, example, cols, response);
            }
            return;
        }

        long count = dpMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpMemberView> records= dpMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(DpMemberView.class, MemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpMember:edit")
    @RequestMapping(value = "/dpMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_au(DpMember record,
                              @CurrentUser SysUserView loginUser,
                              String _dpGrowTime,
                              String reason,
                              HttpServletRequest request) {

        Integer partyId = record.getPartyId();

        if (CmTag.getUserById(record.getUserId()).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工账号");
        }else if (dpNpmService.get(record.getUserId()) != null){
            return failed("该成员已是无党派人士");
        }

        //权限
        Integer loginUserId = loginUser.getUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            boolean isAdmin = dpPartyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin) throw new UnauthorizedException();
        }

        Integer userId = record.getUserId();

        if (StringUtils.isNotBlank(_dpGrowTime)) {
            record.setDpGrowTime(DateUtils.parseDate(_dpGrowTime, DateUtils.YYYYMMDD_DOT));
        }
        SysUserView sysUser = sysUserService.findById(userId);
        DpMember dpMember = dpMemberService.get(userId);

        if (dpMember == null){
            SecurityUtils.getSubject().checkPermission("dpMember:edit");
            DpMember dpMemberAdd = dpMemberMapper.selectByPrimaryKey(userId);
            if (dpMemberAdd != null ){
                return failed(sysUserService.findById(userId).getRealname() + "用户已是党派成员");
            }

            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);//正常
            record.setCreateTime(new Date());
            record.setSource(DpConstants.DP_MEMBER_SOURCE_ADMIN);//后台添加
            dpMemberService.add(record);
            //dpMemberService.addModify(record.getUserId(), "后台添加");//向民主党派成员信息表添加信息

            logger.info(addLog(LogConstants.LOG_DPMEMBER, "添加党派成员信息表： %s %s %s, 添加原因：%s", sysUser.getId(), sysUser.getRealname(),
                    dpPartyService.findAll().get(partyId).getName(), reason));
        } else {
            SecurityUtils.getSubject().checkPermission("dpMember:edit");

            //是否是共产党员
            Member member = memberService.get(userId);
            record.setIsPartyMember(member != null && (member.getType()==1 || member.getType() == 4));

            dpMemberService.updateByPrimaryKeySelective(record,reason);

            logger.info(addLog(LogConstants.LOG_DPMEMBER,"更新党派成员信息表： %s %s %s, 更新原因：%s", sysUser.getId(), sysUser.getRealname(),
                    dpPartyService.findAll().get(partyId).getName(), reason));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:edit")
    @RequestMapping("/dpMember_au")
    public String dpMember_au(Integer userId, Integer partyId, ModelMap modelMap) {

        DpMember dpMember = null;
        if (userId != null) {
            SecurityUtils.getSubject().checkPermission("dpMember:edit");

            dpMember = dpMemberMapper.selectByPrimaryKey(userId);
            partyId = dpMember.getPartyId();
            modelMap.put("sysUser", CmTag.getUserById(userId));
        } else {
           SecurityUtils.getSubject().checkPermission("dpMember:edit");
        }

        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        if (partyId != null){
            modelMap.put("dpParty", dpPartyMap.get(partyId));
        }
        modelMap.put("dpMember", dpMember);

        return "dp/dpMember/dpMember_au";
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_recover", method = RequestMethod.POST)
    @ResponseBody
    public Map dpMember_recover(@RequestParam(value = "ids[]") Integer[] ids,
                                Byte status){

        if(ids != null){
            for (Integer userId :ids){
                status = DpConstants.DP_MEMBER_STATUS_NORMAL;
                DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);
                dpMember.setStatus(status);
                dpMemberService.updateByPrimaryKeySelective(dpMember);
            }

        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping("/dpMember_out")
    public String dpMember_out(){

        return "dp/dpMember/dpMember_out";
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_out", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_out(@RequestParam(value = "ids[]") Integer[] userIds,
                              String outTime){

        if (null != userIds && userIds.length>0){
            DpMemberExample example = new DpMemberExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<DpMember> dpMembers = dpMemberMapper.selectByExample(example);
            Byte status = DpConstants.DP_MEMBER_STATUS_TRANSFER;
            for (DpMember dpMember : dpMembers){
                dpMember.setStatus(status);
                if (StringUtils.isNotBlank(outTime)){
                    dpMember.setOutTime(DateUtils.parseDate(outTime, DateUtils.YYYYMMDD_DOT));
                }
                dpMemberService.updateByPrimaryKeySelective(dpMember);
                logger.info(log( LogConstants.LOG_DPPARTY, "撤销民主党派：{0}", dpMember.getUserId()));
            }

        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            dpMemberService.del(userId);
            logger.info(log( LogConstants.LOG_GROW, "删除党员信息表：{0}", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            List<DpMember> dpMembers = new ArrayList<>();
            for (Integer userId : userIds){
                dpMembers.add(dpMemberService.get(userId));
            }

            dpMemberService.batchDel(userIds);
            logger.info(log( LogConstants.LOG_DPMEMBER, "批量删除党派成员信息表：{0}", StringUtils.join(dpMembers, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:changeOrder")
    @RequestMapping(value = "/dpMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_changeOrder(Integer userId, Integer addNum, HttpServletRequest request) {

        logger.info(log( LogConstants.LOG_GROW, "党员信息表调序：{0}, {1}", userId, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:modifyStatus")
    @RequestMapping("/dp_member_modify_status")
    public String dpMember_modify_status(int userId, ModelMap modelMap){

        DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);
        modelMap.put("dpMember", dpMember);

        return "dp/dpMember/dp_member_modify_status";
    }

    @RequiresPermissions("dpMember:modifyStatus")
    @RequestMapping(value = "/dp_member_modify_status",method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_modify_status(int userId, byte politicalStatus, String remark){

        DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/dpMember_selects")
    @ResponseBody
    public Map dpMember_selects(Integer pageSize,
                                Integer pageNo,
                                Integer partyId,
                                Byte type,//党派成员类别
                                Boolean isRetire,
                                Byte politicalStatus,
                                @RequestParam(required = false, value = "status") Byte[] status,//党派成员状态
                                Boolean noAuth,//默认需要读取权限
                                @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                                String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        boolean addPermits = false;
        List<Integer> adminDpPartyIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)){
            addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL);
            adminDpPartyIdList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());
        }

        List<Byte> statusList = null;
        if (status != null && status.length > 0){
            statusList = Arrays.asList(status);
        }

        int count = iDpMemberMapper.countDpMemberList(partyId, type, isRetire, politicalStatus, statusList,
                searchStr, addPermits, adminDpPartyIdList);

        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpMember> records = iDpMemberMapper.selectDpMemberList(partyId, type, isRetire, politicalStatus, statusList,
                searchStr, addPermits, adminDpPartyIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String,Object>> options = new ArrayList<Map<String,Object>>();
        if(null != records && records.size()>0){

            for(DpMember record:records){
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(record.getUserId());
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("username", uv.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", uv.getCode());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("birth", uv.getBirth());
                option.put("nation", uv.getNation());

                if (needPrivate){
                    option.put("idcard",uv.getIdcard());
                    option.put("mobile", uv.getMobile());
                }

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

    @RequestMapping("/dpMember_view")
    public String member_show_page(@CurrentUser SysUserView loginUser,
            HttpServletResponse response,
            int userId,
            ModelMap modelMap){

        DpMember dpMember = dpMemberService.get(userId);
        SysUserView uv = CmTag.getUserById(userId);
        modelMap.put("uv", uv);

        //党派管理员才可以操作
        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            Integer loginUserId = loginUser.getId();
            boolean isDpPartyAdmin = DpPartyHelper.isPresentDpPartyAdmin(loginUserId,null);
            if (!isDpPartyAdmin){
                throw new UnauthorizedException();
            }
        }
        return "dp/dpMember/dpTeacher_view";
    }

    /*@RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember/search")
    public String dpMember_search(){

        return "dp/dpMember/dpMember_search";
    }

    //todo js
    @RequiresPermissions("dpMember:list")
    @RequestMapping(value = "/dpMember/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_search(int userId){

        String realname = "";
        String unit = "";
        String msg = "";
        String code = "";
        Byte userType = null;
        String status = "";
        SysUserView sysUser = sysUserService.findById(userId);
        if (sysUser == null){
            msg = "该用户不存在！";
        } else {
            code = sysUser.getCode();
            userType = sysUser.getType();
            realname = sysUser.getRealname();
            DpMember dpMember = dpMemberService.get(userId);
            unit = dpMember.getUnit();
            if (dpMember == null){
                msg = "该用户不是党派成员！";
            } else {
                Integer partyId = dpMember.getPartyId();
                DpParty dpParty = dpPartyService.findAll().get(partyId);
                msg = dpParty.getName();

                //查询状态
                if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_NORMAL){
                    status = "正常";
                } else if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_TRANSFER){
                    status = "已转出";
                } else if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_QUIT){
                    status = "已出党";
                }

                if (dpMember.getType() == DpConstants.DP_MEMBER_TYPE_TEACHER){
                    DpMemberView dpMemberView = iDpMemberMapper.getDpMemberView(userId);
                    if (dpMemberView.getIsRetire())
                        status = "已退休";
                }
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("msg", msg);
        resultMap.put("userType", userType);
        resultMap.put("code", code);
        resultMap.put("realname", realname);
        resultMap.put("unit", unit);
        resultMap.put("status", status);
        return resultMap;
    }
*/
    //党派成员基本信息
    @RequestMapping("/dpMember_base")
    public String dpMember_base(Integer userId, ModelMap modelMap){

        DpMemberView dpMemberView = iDpMemberMapper.getDpMemberView(userId);
        Integer partyId = null;
        if (dpMemberView != null) {
            partyId = dpMemberView.getPartyId();
            modelMap.put("dpParty", dpPartyService.findById(partyId));
            modelMap.put("dpMember", dpMemberView);
        }
        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);
        if(uv.getType() == DpConstants.DP_MEMBER_TYPE_TEACHER){
            modelMap.put("teacherInfo", teacherInfoService.get(userId));
        }
        return "dp/dpMember/dpTeacher_base";

    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpMember_import")
    public String dpMember_import(boolean inSchool,
                                  ModelMap modelMap){

        modelMap.put("inSchool", inSchool);

        return "dp/dpMember/dpMember_import";
    }

    //导入校内账号的党派成员信息
    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpMember_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        Map<String, DpParty> runPartyMap = new HashMap<>();
        for (DpParty dpParty : dpPartyMap.values()){
            if (BooleanUtils.isNotTrue(dpParty.getIsDeleted())){
                runPartyMap.put(dpParty.getCode(), dpParty);
            }
        }

        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multiPartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);

        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;

        resultMap = importInSchoolDpMember(xlsRows,runPartyMap);
        /*if (inSchool){

        } else {
            resultMap = importOutSchoolDpMember(xlsRows,runPartyMap,politicalStatusMap);
        }*/

        int successCount = (int)resultMap.get("successCount");
        int total = (int)resultMap.get("total");

        logger.info(log(LogConstants.LOG_ADMIN,"导入党派成员成功，总共{0}条记录，其中成功导入{1}条记录", total, successCount));
        return resultMap;
    }

/*    public Map<String, Object> importOutSchoolDpMember(List<Map<Integer, String>> xlsRows,
                                                      Map<String, DpParty> runPartyMap,
                                                      Map<String, Byte> politicalStatusMap) {

        Date now = new Date();
        List<DpMember> records = new ArrayList<>();
        List<TeacherInfo> teacherInfos = new ArrayList<>();
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            row++;
            DpMember record = new DpMember();
            TeacherInfo teacherInfo = new TeacherInfo();
            SysUserInfo sysUserInfo = new SysUserInfo();

            int col = 0;
            *//*for (int i = 0; i<xlsRow.size();i ++){
                System.out.println(StringUtils.trim(xlsRow.get(i)) + "," + i);
            }*//*
            String userCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            int userId = uv.getId();
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            SysUserView sysUser = sysUserService.findById(userId);
            Byte source = sysUser.getSource();
            if (source == SystemConstants.USER_TYPE_BKS || source == SystemConstants.USER_SOURCE_YJS
                    || source == SystemConstants.USER_TYPE_JZG){
                throw new OpException("第{0}行学工号[{1}]是学校账号", row, userCode);
            }
            record.setUserId(userId);
            teacherInfo.setUserId(userId);
            sysUserInfo.setUserId(userId);

            String realname = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(realname)){
                throw new OpException("第{0}行姓名为空", row);
            }
            sysUserInfo.setRealname(realname);

            String gender = StringUtils.trim(xlsRow.get(col++));
            if(StringUtils.contains(gender, "男")){
                sysUserInfo.setGender(SystemConstants.GENDER_MALE);
            } else if (StringUtils.contains(gender, "女")){
                sysUserInfo.setGender(SystemConstants.GENDER_FEMALE);
            }
            sysUserInfo.setBirth(DateUtils.parseStringToDate(StringUtils.trim(xlsRow.get(col++))));
            sysUserInfo.setNativePlace(StringUtils.trimToNull(xlsRow.get(col++)));

            sysUserInfo.setNation(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setIdcard(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setMobile(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setCountry(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setFromType(StringUtils.trimToNull(xlsRow.get(col++))); // 学员结构，本校、境内、京外等

            sysUserInfo.setUnit(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegreeTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setArriveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setAuthorizedType(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setStaffType(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setStaffStatus(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setPostClass(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setProPost(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setTitleLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));

            sysUserInfo.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setIsRetire(StringUtils.equals(xlsRow.get(col++), "是"));
            teacherInfo.setRetireTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            teacherInfo.setIsHonorRetire(StringUtils.equals(xlsRow.get(col++), "是"));
            //判断xlsx文件和系统内是否包含所要导入的信息
            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_politicalStatus)){
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null){
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            //record.setPoliticalStatus(politicalStatus);

            String partyCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(partyCode)){
                throw new OpException("第{0}行民主党派编码为空", row);
            }
            DpParty dpParty = runPartyMap.get(partyCode);
            if (dpParty == null){
                throw new OpException("第{0}行民主党派编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(dpParty.getId());


            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setDpPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setCreateTime(now);

            //record.setType();
            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);
            // 默认为原有党员导入
            record.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_old").getId());
            record.setSource(DpConstants.DP_MEMBER_SOURCE_IMPORT);

            records.add(record);
            teacherInfos.add(teacherInfo);
            sysUserInfos.add(sysUserInfo);
        }

        int successCount = dpMemberService.batchImportOutSchool(records, teacherInfos, sysUserInfos);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }*/
        //导入校内账号的党派成员
    public Map<String, Object> importInSchoolDpMember(List<Map<Integer, String>> xlsRows,
                                                      Map<String, DpParty> runPartyMap){

        Date now = new Date();
        List<DpMember> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            row++;
            DpMember record = new DpMember();
            String dpPartyCode = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(dpPartyCode)){
                throw new OpException("第{0}行民主党派编号为空", row);
            }
            DpParty dpParty = dpPartyService.getByCode(dpPartyCode);
            if (dpParty == null){
                throw new OpException("第{0}行民主党派不存在", row);
            }
            record.setPartyId(Integer.valueOf(dpParty.getId()));
            String userCode = StringUtils.trimToNull(xlsRow.get(2));
            String realname = StringUtils.trimToNull(xlsRow.get(3));
            String idCard = StringUtils.trimToNull(xlsRow.get(4));
            SysUserView uv = null;
            if (StringUtils.isBlank(userCode)){
                if (idCard != null) {
                    uv = dpMemberService.getByIdCard(idCard);
                }else if (idCard == null && StringUtils.isNotBlank(realname)) {
                    uv = dpMemberService.addDpMember(realname);
                }else {
                    throw new OpException("第{0}行工号、姓名和身份证号同时为空", row);
                }
            }else {
                uv = CmTag.getUserByCode(userCode);
                if (uv == null){
                    throw new OpException("第{0}行工号不存在或工号有误", row);
                }
            }

            record.setUserId(uv.getUserId());
            int col = 5;
            String unit = StringUtils.trimToNull(xlsRow.get(col++));
            record.setUnit(unit);
            String dpPost = StringUtils.trimToNull(xlsRow.get(col++));
            record.setDpPost(dpPost);
            record.setDpGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setEdu(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartTimeJob(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setTrainState(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPoliticalAct(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setMobile(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);
            record.setType(DpConstants.DP_MEMBER_TYPE_TEACHER);
            // 默认为原有党员导入
            record.setSource(DpConstants.DP_MEMBER_SOURCE_IMPORT);

            records.add(record);
        }
        int successCount = dpMemberService.batchImportInSchool(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    private List<String> getTeacherExportTitles() {

        return new ArrayList<>(Arrays.asList(new String[]{"民主党派编号|100","党派名称|200","工号|100", "姓名|120","性别|50",
                "出生日期|80","民族|50",
                "部门|200", "是否退休|50", "党派内职务|100", "加入党派时间|80","职称|80","学历|120","学位|100","行政职务|100",
                "兼职（其他校外职务）|100","是否是中共党员|80","培训情况|200","政治表现|200","党内奖励|200","其他奖励|200","通讯地址|200",
                "手机|100","邮箱|100","备注|200"}));
    }

    public void teacher_export(int cls, DpMemberViewExample example, Integer[] cols, HttpServletResponse response) {

        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        List<DpMemberView> records = dpMemberViewMapper.selectByExample(example);

        List<String> exportTitles = getTeacherExportTitles();
        if (cls == 3 || cls == 10) {
            exportTitles.add(9, "离退休时间|80");
        }else if (cls == 7){
            exportTitles.add(9, "转出时间|80");
        }

        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }

        List<List<String>> valuesList = new ArrayList<>();
        List<Integer> dpPartyIds = new ArrayList<>();
        List<DpParty> dpParties = dpPartyMapper.selectByExample(new DpPartyExample());
        for (DpParty dpParty : dpParties){
            dpPartyIds.add(dpParty.getId());
        }
        for (DpMemberView record : records) {
            SysUserView sysUserView = record.getUser();
            Integer partyId = record.getPartyId();
            if (!dpPartyIds.contains(partyId)){
                partyId = null;
            }
            SysUserView uv = sysUserService.findById(record.getUserId());
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{

                    partyId == null ? "--" : dpPartyService.findAll().get(partyId).getCode(),//民主党派编号
                    partyId == null ? "--" : dpPartyService.findAll().get(partyId).getName(),//党派名称
                    sysUserView.getCode(),//工号
                    sysUserView.getRealname(),//姓名
                    sysUserView.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(sysUserView.getGender()),//性别
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),//出生日期
                    sysUserView.getNation(),//民族
                    record.getUnit(),//部门
                    record.getIsRetire() == true ? "是" : "否",
                    record.getDpPost(),//党派内职务
                    DateUtils.formatDate(record.getDpGrowTime(), DateUtils.YYYYMMDD_DOT),//加入党派时间
                    record.getProPost(),//职称
                    record.getEdu(),//学历
                    record.getDegree(),//学位
                    record.getPost(),//行政职务
                    record.getPartTimeJob(),//兼职（其他校外职务）
                    record.getIsPartyMember() == false ? "否" : "是",
                    record.getTrainState(),
                    record.getPoliticalAct(),//政治表现
                    record.getPartyReward(),//党内奖励
                    record.getOtherReward(),//其他奖励
                    record.getAddress(),//通讯地址
                    record.getMobile(),//手机
                    record.getEmail(),//email
                    record.getRemark()//备注

            }));

            if (cls == 3 || cls == 10) {
                values.add(9, DateUtils.formatDate(record.getRetireTime(), DateUtils.YYYYMMDD_DOT));
            }else if (cls == 7){
                values.add(9, DateUtils.formatDate(record.getOutTime(), DateUtils.YYYYMMDD_DOT));
            }

            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }

            valuesList.add(values);
        }
        String fileName = (cls == 7 ? "已移除" : (cls == 3 ? "离退休" : (cls == 10 ? "全部" : "在职"))) + "教职工党派成员信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";

        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }
}
