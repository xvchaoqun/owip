package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.party.*;
import domain.party.PartyMemberExample.Criteria;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import service.party.PartyExportService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PartyExportService partyExportService;

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember")
    public String partyMember(Integer groupId,
                              Integer partyId,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              HttpServletResponse response,
                              Integer userId, ModelMap modelMap) throws IOException {

        if (export == 1) {
            partyMember_export(groupId, response);
            return null;
        }

        if (groupId != null) {
            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
            modelMap.put("partyMemberGroup", partyMemberGroup);
        }
        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }
        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }
        /*if (typeIds!=null) {
            List<Integer> _typeIds = Arrays.asList(typeIds);
            modelMap.put("selectedTypeIds", _typeIds);
        }*/

        return "party/partyMember/partyMember_page";
    }

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember_data")
    public void partyMember_data(HttpServletResponse response,
                                 Integer groupId,
                                 Integer userId,
                                 @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                 Integer postId,
                                 Integer unitId,
                                 Integer partyId,
                                 Boolean isAdmin,
                                 Boolean isDeleted,
                                 Boolean isPresent,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberViewExample example = new PartyMemberViewExample();
        PartyMemberViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_sort_order desc, sort_order desc");

        criteria.addPermits(loginUserService.adminPartyIdList());

        if (isDeleted != null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }
        if (isPresent != null) {
            criteria.andIsPresentEqualTo(isPresent);
        }

        if (groupId != null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (typeIds != null) {
            List<Integer> selectedTypeIds = Arrays.asList(typeIds);
            criteria.andTypeIdsIn(selectedTypeIds);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (isAdmin != null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            partyMember_export(example, response);
            return;
        }

        long count = partyMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMemberView> PartyMembers = partyMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", PartyMembers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_au(PartyMember record,
                                 @RequestParam(required = false, value = "_typeIds") Integer[] _typeIds,
                                 HttpServletRequest request) {


        // 权限控制
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            Integer groupId = record.getGroupId();
            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
            Integer partyId = partyMemberGroup.getPartyId();

            // 要求是分党委管理员
            if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        Integer id = record.getId();
        if (partyMemberService.idDuplicate(id, record.getGroupId(), record.getUserId(), record.getPostId())) {
            return failed("添加重复【每个领导班子的人员不可重复，并且书记只有一个】");
        }
        boolean autoAdmin = false;
        {
            Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_party_member_post");
            MetaType post = postMap.get(record.getPostId());

            if (BooleanUtils.isTrue(post.getBoolAttr())) {
                autoAdmin = true;
            }
        }
        if (_typeIds != null) {
            for (Integer typeId : _typeIds) {
                Map<Integer, MetaType> typeMap = metaTypeService.metaTypes("mc_party_member_type");
                MetaType type = typeMap.get(typeId);

                if (BooleanUtils.isTrue(type.getBoolAttr())) {
                    autoAdmin = true;
                    break;
                }
            }
            record.setTypeIds(StringUtils.join(_typeIds, ","));
        }

        if (id == null) {

            partyMemberService.insertSelective(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加基层党组织成员：%s", record.getId()));
        } else {

            partyMemberService.updateByPrimaryKey(record, autoAdmin);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新基层党组织成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping("/partyMember_au")
    public String partyMember_au(Integer groupId, Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            modelMap.put("partyMember", partyMember);
           /* if (partyMember.getTypeIds()!=null) {
                List<Integer> _typeIds = Arrays.asList();
                modelMap.put("selectedTypeIds", _typeIds);
            }*/
            SysUserView uv = sysUserService.findById(partyMember.getUserId());
            modelMap.put("uv", uv);
            groupId = partyMember.getGroupId();
        }
        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("partyMemberGroup", partyMemberGroup);

        return "party/partyMember/partyMember_au";
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map partyMember_admin(HttpServletRequest request, Integer id) {

        if (id != null) {

            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);

            // 权限控制
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

                Integer groupId = partyMember.getGroupId();
                PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
                Integer partyId = partyMemberGroup.getPartyId();

                // 要求是分党委管理员
                if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    throw new UnauthorizedException();
                }
            }

            partyMemberAdminService.toggleAdmin(partyMember);

            // test
            /*SysUser sysUser = sysUserService.findById(partyMember.getUserId());
            System.out.println(JSONUtils.toString(sysUser));*/

            String op = partyMember.getIsAdmin() ? "删除" : "添加";
            logger.info(addLog(LogConstants.LOG_PARTY, "%s基层党组织成员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map partyAdmin_del(int userId, int partyId) {

        // 权限控制
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            // 要求是分党委管理员
            if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        partyMemberService.delAdmin(userId, partyId);
        logger.info(addLog(LogConstants.LOG_PARTY, "删除基层党组织管理员权限，userId=%s, partyId=%s", userId, partyId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            // 权限控制
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

                PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
                Integer groupId = partyMember.getGroupId();
                PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
                Integer partyId = partyMemberGroup.getPartyId();

                // 要求是分党委管理员
                if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    throw new UnauthorizedException();
                }
            }

            partyMemberService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除基层党组织成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除基层党组织成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:changeOrder")
    @RequestMapping(value = "/partyMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "基层党组织成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping("/partyMember_import")
    public String partyMember_import(ModelMap modelMap) {

        return "party/partyMember/partyMember_import";
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PartyMember> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PartyMember record = new PartyMember();
            row++;

            String partyCode = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行所属分党委编码为空", row);
            }
            Party party = partyService.getByCode(partyCode);
            if (party == null) {
                throw new OpException("第{0}行所属分党委编码[{1}]不存在", row, partyCode);
            }

            PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(party.getId());
            if(presentGroup==null) continue; // 如果分党委还未设置当前班子，则忽略导入；
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
            MetaType postType = CmTag.getMetaTypeByName("mc_party_member_post", _post);
            if(postType!=null) {
                record.setPostId(postType.getId());
            }else{
                // 默认都是委员
                MetaType partyMemberType = CmTag.getMetaTypeByCode("mt_party_member");
                record.setPostId(partyMemberType.getId());
            }

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = partyMemberService.bacthImport(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    public void partyMember_export(int groupId, HttpServletResponse response) throws IOException {

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
        Party party = partyService.findAll().get(partyMemberGroup.getPartyId());

        XSSFWorkbook wb = statPartyMemberService.toXlsx(groupId);
        String fileName = party.getName() + "委员及分工统计表(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    public void partyMember_export(PartyMemberViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = partyExportService.export(example);
        String fileName = CmTag.getSysConfig().getSchoolName()
                + "分党委委员(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    @RequestMapping("/partyMember_selects")
    @ResponseBody
    public Map partyMember_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberExample example = new PartyMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }*/

        int count = partyMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMember> partyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != partyMembers && partyMembers.size() > 0) {

            for (PartyMember partyMember : partyMembers) {

                Select2Option option = new Select2Option();
                //option.setText(partyMember.getName());
                option.setId(partyMember.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
