package controller.dp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.dp.*;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import domain.unit.Unit;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.dp.DpPartyMemberAdminService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpPartyMemberController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DpPartyMemberAdminService dpPartyMemberAdminService;

    @RequiresPermissions("dpPartyMember:list")
    @RequestMapping("/dpPartyMember")
    public String dpPartyMember(Integer groupId,
                                Integer partyId,
                                @RequestParam(required = false, defaultValue = "1") Byte cls,
                                @RequestParam(required = false,defaultValue = "0") int export,
                                HttpServletResponse response,
                                Integer userId,ModelMap modelMap) throws IOException {

        modelMap.put("cls", cls);

        if (export == 1) {
            dpPartyMember_export(groupId, response);
            return null;
        }

        if (groupId != null) {
            DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
            modelMap.put("dpPartyMemberGroup", dpPartyMemberGroup);
        }
        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }
        if (partyId != null) {
            modelMap.put("dpParty", dpPartyService.findAll().get(partyId));
        }

        return "dp/dpPartyMember/dpPartyMember_page";
    }

    @RequiresPermissions("dpPartyMember:list")
    @RequestMapping("/dpPartyMember_menu")
    public String dpPartyMember_show_menu(ModelMap modelMap,Integer groupId){

        modelMap.put("groupId", groupId);
        return "dp/dpPartyMember/menu";
    }

    @RequiresPermissions("dpPartyMember:list")
    @RequestMapping("/dpPartyMember_data")
    public void dpPartyMember_data(HttpServletResponse response,
                                   @RequestParam(required = false, defaultValue = "1") Byte cls,
                                   @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                   Integer userId,
                                   Integer groupId,
                                   Integer postId,
                                   Integer groupPartyId,
                                   Integer unitId,
                                   Boolean isAdmin,
                                   Boolean isDeleted,
                                   Boolean isPresent,
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

        DpPartyMemberViewExample example = new DpPartyMemberViewExample();
        DpPartyMemberViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        //权限管理
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

        criteria.andPresentMemberEqualTo(cls==1);

        if (isDeleted != null){
            criteria.andIsDeletedEqualTo(isDeleted);
        }
        if (isPresent != null){
            criteria.andIsPresentEqualTo(isPresent);
        }
        if (groupId!=null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (groupPartyId != null){
            criteria.andGroupPartyIdEqualTo(groupPartyId);
        }
        if (unitId != null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if (typeIds != null){
            List<Integer> selectedTypeIds = Arrays.asList(typeIds);
            criteria.andTypeIdsIn(selectedTypeIds);
        }
        if (isAdmin!=null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpPartyMember_export(example, response);
            return;
        }

        long count = dpPartyMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpPartyMemberView> records= dpPartyMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();

        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpPartyMember.class, dpPartyMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping(value = "/dpPartyMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMember_au(DpPartyMember record,
                                   @RequestParam(required = false, value = "_typeIds") Integer[] _typeIds,
                                   HttpServletRequest request) {

        Integer id = record.getId();
        if (CmTag.getUserById(record.getUserId()).getType() != SystemConstants.USER_TYPE_JZG){
            return failed("非教职工账号");
        }else if (dpPartyMemberService.idDuplicate(id, record.getUserId(),record.getGroupId(),record.getPostId())){
            return failed("该成员已是无党派人士");
        }

        //权限控制
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            Integer groupId = record.getGroupId();
            DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
            Integer partyId = dpPartyMemberGroup.getPartyId();
            //要求是党派管理员
            if (!dpPartyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(),partyId)){
                throw new UnauthorizedException();
            }
        }

        if (dpPartyMemberService.idDuplicate(id, record.getUserId(),record.getGroupId(), record.getPostId())){
            return failed("添加重复【该委员已添加，并且主委只能有一个】");
        }
        boolean autoAdmin = false;
        {
            Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_dp_party_member_post");
            MetaType post = postMap.get(record.getPostId());

            if (BooleanUtils.isTrue(post.getBoolAttr())){
                autoAdmin = true;
            }
        }
        if (_typeIds != null){
            for (Integer typeId : _typeIds){
                Map<Integer, MetaType> typeMap = metaTypeService.metaTypes("mc_dp_party_member_type");
                MetaType type = typeMap.get(typeId);

                if (BooleanUtils.isTrue(type.getBoolAttr())){
                    autoAdmin = true;
                    break;
                }
            }
            record.setTypeIds(StringUtils.join(_typeIds,","));
        }
        record.setPresentMember(true);

        if (id == null) {
            dpPartyMemberService.insertSelective(record, autoAdmin);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加党派委员：{0}", record.getId()));
        } else {
            dpPartyMemberService.updateByPrimaryKeySelective(record,autoAdmin);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新党派委员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping("/dpPartyMember_au")
    public String dpPartyMember_au(Integer id, Integer groupId, ModelMap modelMap) {

        if (id != null) {
            DpPartyMember dpPartyMember = dpPartyMemberMapper.selectByPrimaryKey(id);
            modelMap.put("dpPartyMember", dpPartyMember);
            SysUserView uv = sysUserService.findById(dpPartyMember.getUserId());
            modelMap.put("uv", uv);
            groupId = dpPartyMember.getGroupId();
        }

        DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("dpPartyMemberGroup", dpPartyMemberGroup);

        return "dp/dpPartyMember/dpPartyMember_au";
    }

    @RequiresPermissions("dpPartyMember:del")
    @RequestMapping("/dpPartyMember_cancel")
    public String dpPartyMember_cancel(){

        return "dp/dpPartyMember/dpPartyMember_cancel";
    }

    @RequiresPermissions("dpPartyMember:del")
    @RequestMapping(value = "/dpPartyMember_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMember_cancel(@RequestParam(value = "ids[]") Integer[] ids,
                              String deleteTime){

        if (null != ids && ids.length>0){
            for (Integer id : ids){
                DpPartyMember dpPartyMember = dpPartyMemberMapper.selectByPrimaryKey(id);
                if (dpPartyMember.getUserId().intValue() == ShiroHelper.getCurrentUserId()){
                    return failed("不能撤销自己");
                }
            }
            DpPartyMemberExample example = new DpPartyMemberExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
            for (DpPartyMember dpPartyMember : dpPartyMembers){
                if (StringUtils.isNotBlank(deleteTime)){
                    dpPartyMember.setDeleteTime(DateUtils.parseDate(deleteTime, DateUtils.YYYYMMDD_DOT));
                }
                dpPartyMemberMapper.updateByPrimaryKeySelective(dpPartyMember);
            }
            dpPartyMemberService.batchDel(ids);
            logger.info(log( LogConstants.LOG_GROW, "批量撤销分党委委员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMember:del")
    @RequestMapping(value = "/dpPartyMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMember_del(HttpServletRequest request,
                                    @RequestParam(value = "ids[]") Integer[] ids) {

        if (ids != null) {

            dpPartyMemberService.del(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除民主党派委员：{0}", ids));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMember:del")
    @RequestMapping(value = "/dpPartyMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPartyMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            for (Integer id : ids){
                DpPartyMember dpPartyMember = dpPartyMemberMapper.selectByPrimaryKey(id);
                if (dpPartyMember.getUserId().intValue() == ShiroHelper.getCurrentUserId()){
                    return failed("不能撤销自己");
                }
            }
            dpPartyMemberService.batchDel(ids);
            logger.info(log( LogConstants.LOG_GROW, "批量撤销分党委委员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping(value = "/dpPartyMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpPartyMemberService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DPPARTY, "民主党派委员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpPartyMember_export(int groupId, HttpServletResponse response) throws IOException {

        DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
        DpParty dpParty = dpPartyService.findAll().get(dpPartyMemberGroup.getPartyId());
        XSSFWorkbook wb = statDpPartyMemberService.toXlsx(groupId);
        String fileName = dpParty.getName() + "的委员及分工统计表(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    public void dpPartyMember_export(DpPartyMemberViewExample example, HttpServletResponse response) {

        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();

        List<DpPartyMemberView> records = dpPartyMemberViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|80", "所在单位|200", "所属党派|270", "职务|100",
                "分工|100", "任职时间|100", "性别|50", "民族|50", "身份证号|150",
                "出生时间|80", "到校时间|100", "岗位类别|100",
                "主岗等级|100", "专业技术职务|120", "专技职务等级|120", "管理岗位等级|120", "办公电话|100",
                "手机号|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpPartyMemberView record = records.get(i);
            SysUserView sysUserView = record.getUser();
            DpMember dpMember = dpMemberMapper.selectByPrimaryKey(record.getUserId());
            DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(record.getGroupId());
            Unit unit = unitMap.get(sysUserView.getUnit());
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(record.getUserId());

            List<String> typeNames = new ArrayList();
            String[] _typeIds = StringUtils.split(record.getTypeIds(), ",");
            if (_typeIds != null && _typeIds.length > 0) {
                for (String typeId : _typeIds) {
                    MetaType metaType = metaTypeMap.get(Integer.valueOf(typeId));
                    if (metaType != null) typeNames.add(metaType.getName());
                }
            }

            String[] values = {
                    sysUserView.getCode(),
                    sysUserView.getRealname(),
                    unit == null ? "" : unit.getName(),
                    dpPartyMap.get(dpPartyMemberGroup.getPartyId()).getName(),
                    metaTypeService.getName(record.getPostId()),

                    StringUtils.join(typeNames, ","),
                    DateUtils.formatDate(record.getAssignDate(), DateUtils.YYYYMM),
                    sysUserView.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(sysUserView.getGender()),
                    sysUserView.getNation(),
                    sysUserView.getIdcard(),
                    DateUtils.formatDate(sysUserView.getBirth(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(teacherInfo.getArriveTime(), DateUtils.YYYYMMDD_DOT),
                    teacherInfo.getPostClass(),

                    teacherInfo.getMainPostLevel(),
                    teacherInfo.getProPost(),
                    teacherInfo.getProPostLevel(),
                    teacherInfo.getManageLevel(),
                    record.getOfficePhone(),

                    record.getMobile(),
            };
            valuesList.add(values);
        }
        String fileName = String.format("民主党派委员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpPartyMember_selects")
    @ResponseBody
    public Map dpPartyMember_selects(Integer pageSize,
                                     Integer pageNo,
                                     Integer userId,
                                     Integer partyId,
                                     @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                     Boolean isPresent,
                                     Boolean isDeleted) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpPartyMemberViewExample example = new DpPartyMemberViewExample();
        DpPartyMemberViewExample.Criteria criteria = example.createCriteria();
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

        if (partyId != null){
            criteria.andGroupPartyIdEqualTo(partyId);
        }
        if (userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (typeIds != null){
            List<Integer> selectedTypeIds = Arrays.asList(typeIds);
            criteria.andTypeIdsIn(selectedTypeIds);
        }
        if (isPresent != null){
            criteria.andIsPresentEqualTo(true);
        }
        if (isDeleted != null){
            criteria.andIsDeletedEqualTo(false);
        }


        long count = dpPartyMemberViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpPartyMemberView> records = dpPartyMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String,Object>> options = new ArrayList<Map<String,Object>>();
        if(null != records && records.size()>0){

            for(DpPartyMemberView record:records){

                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(record.getUserId());
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("code", uv.getCode());
                option.put("realname", uv.getRealname());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("dpPartyMember:del")
    @RequestMapping(value = "/dpPartyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPartyAdmin_del(int userId, int partyId){

        //权限控制
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            //要求是现任党派管理员
            if (!dpPartyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(),partyId)){
                throw new UnauthorizedException();
            }
            if (userId == ShiroHelper.getCurrentUserId()){
                throw new OpException("不能删自己！");
            }
        }

        dpPartyMemberService.delAdmin(userId,partyId);
        logger.info(addLog(LogConstants.LOG_DPPARTY,"删除民主党派管理员权限，userId=%s，partyId=%s"), userId, partyId);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping("/dpPartyMember_import")
    public String dpPartyMember_import(){

        return "dp/dpPartyMember/dpPartyMember_import";
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping(value = "/dpPartyMember_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpPartyMember_import(HttpServletRequest request) throws InvalidFormatException,IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<DpPartyMember> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            DpPartyMember record = new DpPartyMember();
            row++;

            String dpPartyCode = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(dpPartyCode)) {
                throw new OpException("第{0}行所属民主党派编码为空", row);
            }
            DpParty dpParty = dpPartyService.getByCode(dpPartyCode);
            if (dpParty == null) {
                throw new OpException("第{0}行所属民主党派编码[{1}]不存在", row, dpPartyCode);
            }

            DpPartyMemberGroup presentGroup = dpPartyMemberGroupService.getPresentGroup(dpParty.getId());
            if(presentGroup==null) continue; // 如果党派还未设置当前委员会，则忽略导入；
            record.setGroupId(presentGroup.getId());

            String userCode = StringUtils.trim(xlsRow.get(3));
            if (StringUtils.isBlank(userCode)) {
                continue; // 学工号为空则忽略该行
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            String _post = StringUtils.trim(xlsRow.get(4));
            MetaType postType = CmTag.getMetaTypeByName("mc_dp_party_member_post", _post);
            if(postType!=null) {
                record.setPostId(postType.getId());
            }else{
                // 默认都是委员
                MetaType dpPartyMemberType = CmTag.getMetaTypeByCode("mt_dp_wy");
                record.setPostId(dpPartyMemberType.getId());
            }
            record.setPresentMember(true);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = dpPartyMemberService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_DPPARTY,
                "导入党派委员会委员成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("dpPartyMember:edit")
    @RequestMapping(value = "/dpPartyMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map dpPartyMember_admin(HttpServletRequest request, Integer id) {

        if (id != null) {

            DpPartyMember dpPartyMember = dpPartyMemberMapper.selectByPrimaryKey(id);

            // 权限控制
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)) {
                if (dpPartyMember.getUserId().intValue() == ShiroHelper.getCurrentUserId()){
                    return failed("不能删除自己");
                }

                Integer groupId = dpPartyMember.getGroupId();
                DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
                Integer partyId = dpPartyMemberGroup.getPartyId();

                // 要求是民主党派管理员
                if (!dpPartyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    throw new UnauthorizedException();
                }
            }

            dpPartyMemberAdminService.toggleAdmin(dpPartyMember);

            String op = dpPartyMember.getIsAdmin() ? "删除" : "添加";
            logger.info(addLog(LogConstants.LOG_DPPARTY, "%s民主党派成员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

}

