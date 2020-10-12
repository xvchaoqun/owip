package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.party.Branch;
import domain.party.OrgAdmin;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import persistence.party.common.OwAdmin;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class OrgAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 弹出框编辑其他管理员
    //@RequiresPermissions("orgAdmin:list")
    @RequestMapping("/org_admin")
    public String org_admin(Integer partyId, Integer branchId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (partyId != null || branchId != null) {
            if (null == pageSize) {
                pageSize = 5;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            if (partyId != null) {
                modelMap.put("party", partyService.findAll().get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchService.findAll().get(branchId));
            }

            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

            OwAdmin search = new OwAdmin();
            search.setPartyId(partyId);
            search.setBranchId(branchId);
            search.setNormal(true); // 其他管理员
            if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                search.setAddPermits(true);
                search.setAdminPartyIdList(adminPartyIdList);
                search.setAdminBranchIdList(adminBranchIdList);
            }

            List<OwAdmin> records = null;
            long count = 0;
            if (partyId != null) {

                records = iPartyMapper.selectPartyAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
                count = iPartyMapper.countPartyAdminList(search);
            } else {
                records = iPartyMapper.selectBranchAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
                count = iPartyMapper.countBranchAdminList(search);
            }

            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            modelMap.put("owAdmins", records);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (partyId != null) {
                searchStr += "&partyId=" + partyId;
            }
            if (branchId != null) {
                searchStr += "&branchId=" + branchId;
            }
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "party/org_admin";
    }

    //@RequiresPermissions("orgAdmin:list")
    @RequestMapping("/orgAdmin")
    public String orgAdmin(ModelMap modelMap,
                           byte type,
                           Integer userId,
                           Integer partyId,
                           Integer branchId,
                           @RequestParam(required = false, defaultValue = "1") Byte cls) throws IOException {

        modelMap.put("type", type);
        modelMap.put("cls", cls);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchService.findAll().get(branchId));
        }

        return "party/orgAdmin/orgAdmin_page";
    }

    //@RequiresPermissions("orgAdmin:list")
    @RequestMapping("/orgAdmin_data")
    public void orgAdmin_data(HttpServletResponse response,
                              byte type,
                              Integer userId,
                              Integer classId,
                              Integer partyId,
                              Integer branchId,
                              @RequestParam(required = false, defaultValue = "1") Byte cls,
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

        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        OwAdmin search = new OwAdmin();
        search.setPartyId(partyId);
        search.setBranchId(branchId);
        search.setUserId(userId);
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            search.setAddPermits(true);
            search.setAdminPartyIdList(adminPartyIdList);
            search.setAdminBranchIdList(adminBranchIdList);
        }

        List records = null;
        long count = 0;
        if (type == OwConstants.OW_ORG_ADMIN_PARTY) {
            search.setPartyClassId(classId);
            records = iPartyMapper.selectPartyAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
            count = iPartyMapper.countPartyAdminList(search);
        } else {
            records = iPartyMapper.selectBranchAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
            count = iPartyMapper.countBranchAdminList(search);
        }

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("orgAdmin:edit")
    @RequestMapping(value = "/orgAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_orgAdmin_au(OrgAdmin record, HttpServletRequest request) {

        SysUserView uv = sysUserService.findById(record.getUserId());

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if (partyId != null) {

            PartyHelper.checkAuth(partyId);
            Party party = partyService.findAll().get(partyId);
            orgAdminService.addPartyAdmin(record.getUserId(), partyId);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加分党委管理员：%s， %s", uv.getCode(), party.getName()));
        } else if (branchId != null) {

            Branch branch = branchService.findAll().get(branchId);
            PartyHelper.checkAuth(branch.getPartyId(), branchId);

            orgAdminService.addBranchAdmin(record.getUserId(), branchId);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加党支部管理员：%s， %s", uv.getCode(), branch.getName()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("orgAdmin:edit")
    @RequestMapping("/orgAdmin_au")
    public String orgAdmin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OrgAdmin orgAdmin = orgAdminMapper.selectByPrimaryKey(id);
            modelMap.put("orgAdmin", orgAdmin);
        }
        return "party/orgAdmin/orgAdmin_au";
    }

    //@param isPartyAdmin  区分分党委管理员和党支部管理员
    //@RequiresPermissions("orgAdmin:del")
    @RequestMapping(value = "/orgAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_orgAdmin_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, String id, boolean isPartyAdmin) {

        if (id != null) {
            OrgAdmin orgAdmin = (OrgAdmin) orgAdminService.getAdmin(id, isPartyAdmin);
            if (orgAdmin != null) {
                if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                    if (orgAdmin.getUserId().intValue() == loginUser.getId()) {
                        return failed("不能删除自己");
                    }
                }
                SysUserView uv = sysUserService.findById(orgAdmin.getUserId());
                Party party = null;
                Integer partyId = orgAdmin.getPartyId();
                if (partyId != null) {

                    party = partyService.findAll().get(partyId);
                    PartyHelper.checkAuth(partyId);
                }
                Branch branch = null;
                Integer branchId = orgAdmin.getBranchId();
                if (branchId != null) {

                    branch = branchService.findAll().get(branchId);
                    PartyHelper.checkAuth(branch.getPartyId(), branchId);
                }

                orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
                logger.info(addLog(LogConstants.LOG_PARTY, "删除党组织管理员：%s, %s%s"
                        , uv.getCode(), party == null ? "" : party.getName(), branch == null ? "" : branch.getName()));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("orgAdmin:list")
    @RequestMapping("/orgAdmin_selects")
    @ResponseBody
    public Map orgAdmin_selects(Integer pageSize, Integer pageNo,
                                @RequestParam(required = false, defaultValue = OwConstants.OW_ORG_ADMIN_PARTY+"") byte type,
                                String searchStr) throws IOException {

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
            return null;

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OwAdmin search = new OwAdmin();
        search.setQuery(searchStr);
        search.setAddPermits(false);

        List<OwAdmin> records = null;
        long count = 0;
        if (type == OwConstants.OW_ORG_ADMIN_PARTY) {

            records = iPartyMapper.selectPartyAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
            count = iPartyMapper.countPartyAdminList(search);
        } else {
            records = iPartyMapper.selectBranchAdminList(search, new RowBounds((pageNo - 1) * pageSize, pageSize));
            count = iPartyMapper.countBranchAdminList(search);
        }

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (OwAdmin record : records) {

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getUserId() + "");
                option.put("text", record.getRealname());
                option.put("username", record.getUsername());
                option.put("code", record.getCode());
                option.put("unit", extService.getUnit(record.getUserId()));

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequestMapping(value = "/org/orgAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map orgAdmin_batchDel(@CurrentUser SysUserView loginUser, HttpServletRequest request, String[] ids, byte type, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            boolean isPartyAdmin = type==OwConstants.OW_ORG_ADMIN_PARTY?true:false;
            orgAdminService.batchDel(ids, isPartyAdmin, loginUser);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除管理员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/org/orgAdmin_import")
    public String orgAdmin_import(ModelMap modelMap) {

        return "party/orgAdmin/orgAdmin_import";
    }

    @RequestMapping(value = "/org/orgAdmin_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_orgAdmin_import(HttpServletRequest request, byte type) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<OrgAdmin> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            OrgAdmin record = new OrgAdmin();
            row++;
            String code = StringUtils.trimToNull(xlsRow.get(0));
            if(StringUtils.isBlank(code)){
                throw new OpException("第{0}行编号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null){
                throw new OpException("第{0}行账号不存在", row);
            }
            record.setUserId(uv.getUserId());

            String owCode = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(owCode)) {
                throw new OpException("第{0}行{1}编码为空", row, type==OwConstants.OW_ORG_ADMIN_PARTY?"基层党组织":"党支部");
            }
            if (type == OwConstants.OW_ORG_ADMIN_PARTY) {

                Party party = partyService.getByCode(owCode);
                if (party == null) {
                    throw new OpException("第{0}行基层党组织不存在", row);
                }
                record.setPartyId(party.getId());
            }else if (type == OwConstants.OW_ORG_ADMIN_BRANCH){

                Branch branch = branchService.getByCode(owCode);
                if (branch == null){
                    throw new OpException("第{0}行党支部不存在", row);
                }
                record.setBranchId(branch.getId());
            }

            record.setType(type);
            record.setCreateTime(new Date());
            records.add(record);
        }

        int addCount = orgAdminService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
