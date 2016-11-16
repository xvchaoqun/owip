package controller.cadre;

import bean.XlsCadre;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.*;
import domain.ext.ExtJzg;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre/search")
    public String search(){

        return "cadre/cadre_search";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping(value = "/cadre/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_search(String code) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        SysUserView sysUser = sysUserService.findByCode(code);
        if(sysUser==null){
            msg = "该用户不存在";
        }else {
            resultMap.put("realname", sysUser.getRealname());
            Cadre cadre = cadreService.findByUserId(sysUser.getId());
            if(cadre==null){
                msg = "该用户不是干部";
            }else{
                resultMap.put("cadreId", cadre.getId());
                resultMap.put("status", cadre.getStatus());
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }

    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre")
    public String cadre() {

        return "index";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_page")
    public String cadre_page(@RequestParam(required = false, defaultValue = "1")Byte status,
                             Integer cadreId,ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "cadre/cadre_page";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_data")
    public void cadre_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order",tableName = "cadre") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
                                    Integer cadreId,
                                    Integer typeId,
                                    Integer postId,
                                    String title,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cadre_export(example, response);
            return;
        }

        int count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }


    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_view")
    public String cadre_show_page(HttpServletResponse response,
                                  @RequestParam(defaultValue = "cadre_base")String to, // 默认跳转到基本信息
                                  ModelMap modelMap) {
        modelMap.put("to", to);

        return "cadre/cadre_view";
    }

    // 基本信息
    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_base")
    public String cadre_base(Integer id, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        modelMap.put("cadre", cadre);

        SysUserView uv = sysUserService.findById(cadre.getUserId());
        modelMap.put("uv", uv);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        modelMap.put("member", memberService.get(uv.getId()));

        // 人事信息
        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
        modelMap.put("extJzg", extJzg);

        CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(id);
        // 主职,现任职务
        modelMap.put("mainCadrePost", mainCadrePost);

        // 任现职级
        modelMap.put("cadreAdminLevel",cadreAdminLevelService.getPresentByCadreId(id,
                mainCadrePost!=null?mainCadrePost.getAdminLevelId():null));

        // 兼职单位
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(id);
        if(subCadrePosts.size()>=1){
            modelMap.put("subCadrePost1", subCadrePosts.get(0));
        }
        if(subCadrePosts.size()>=2){
            modelMap.put("subCadrePost2", subCadrePosts.get(1));
        }

        // 最高学历
        modelMap.put("highEdu", cadreEduService.getHighEdu(id));
        //最高学位
        modelMap.put("highDegree", cadreEduService.getHighDegree(id));

        return "cadre/cadre_base";
    }

    // 人事信息
    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_personnel")
    public String cadre_personnel(Integer id, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        //modelMap.put("cadre", cadre);
        return "cadre/cadre_personnel";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_temp_pass_au")
    public String cadre_temp_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            modelMap.put("cadre", cadre);
            modelMap.put("sysUser", cadre.getUser());
        }
        return "cadre/cadre_temp_pass_au";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_temp_pass_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_temp_pass_au(Cadre record, HttpServletRequest request) {

        int id = record.getId();
        record.setStatus(SystemConstants.CADRE_STATUS_NOW);
        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(SystemConstants.CADRE_STATUS_TEMP);

        cadreService.updateByExampleSelective(record, example);

        Cadre cadre = cadreMapper.selectByPrimaryKey(id);
        SysUserView user = cadre.getUser();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部通过常委会任命：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_leave")
    public String cadre_leave(int id, HttpServletResponse response,  ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);

        TreeNode dispatchCadreTree = cadreService.getDispatchCadreTree(id, SystemConstants.DISPATCH_CADRE_TYPE_DISMISS);
        modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));

        return "cadre/cadre_leave";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_leave(int id, Byte status, String title, Integer dispatchCadreId, HttpServletRequest request) {

        if(status==null) status=SystemConstants.CADRE_STATUS_LEAVE;

        cadreService.leave(id, status, StringUtils.trimToNull(title), dispatchCadreId);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部离任：%s，%s", id, SystemConstants.CADRE_STATUS_MAP.get(status)));
        return success(FormUtils.SUCCESS);
    }

    // 在“离任中层干部库”和“离任校领导干部库”中加一个按钮“重新任用”，点击这个按钮，可以转移到“考察对象”中去。
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_assign(@RequestParam(value = "ids[]") Integer[] ids) {

        cadreService.assign(ids);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部任用：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    // for test 给所有的干部加上干部身份
    @RequiresRoles("admin")
    @RequestMapping(value = "/cadre_addAllCadreRole")
    @ResponseBody
    public Map do_cadre_addAllCadreRole() {

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        for (Cadre cadre : cadreMap.values()) {
            SysUserView sysUser = cadre.getUser();
            // 添加干部身份
            sysUserService.addRole(sysUser.getId(), SystemConstants.ROLE_CADRE, sysUser.getUsername(), sysUser.getCode());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_au(Cadre record, HttpServletRequest request) {

        Integer id = record.getId();
        if(cadreService.idDuplicate(id, record.getUserId())){
            return failed("添加重复");
        }

        if (id == null) {
            cadreService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部：%s", record.getId()));
        } else {

            cadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_au")
    public String cadre_au(Integer id, Byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        if (id != null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            modelMap.put("cadre", cadre);
            modelMap.put("status", cadre.getStatus());

            modelMap.put("sysUser", sysUserService.findById(cadre.getUserId()));
        }
        if(id!=null && (status==SystemConstants.CADRE_STATUS_LEAVE || status==SystemConstants.CADRE_STATUS_LEADER_LEAVE)){
            TreeNode dispatchCadreTree = cadreService.getDispatchCadreTree(id, SystemConstants.DISPATCH_CADRE_TYPE_DISMISS);
            modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));
        }

        if(status==SystemConstants.CADRE_STATUS_TEMP)
            return "cadre/cadre_temp_au";
        return "cadre/cadre_au";
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            cadreService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:changeOrder")
    @RequestMapping(value = "/cadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        cadreService.changeOrder(id, status, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部调序：%s, %s", id ,addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadre_export(CadreViewExample example, HttpServletResponse response) {

        List<CadreView> records = cadreViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工号","姓名","行政级别","职务属性","职务","所在单位及职务","手机号","办公电话","家庭电话","电子邮箱","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreView record = records.get(i);
            SysUserView sysUser =  record.getUser();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getPostId()),
                    record.getPost(),
                    record.getTitle(),
                    record.getMobile(),
                    record.getPhone(),
                    record.getHomePhone(),
                    record.getEmail(),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部库_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping("/cadre_import")
    public String cadre_import(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_import";
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping(value="/cadre_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_import( HttpServletRequest request, Byte status) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadre> cadres = new ArrayList<XlsCadre>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);

            String sheetName = sheet.getSheetName();
            if(StringUtils.equals(sheetName, "干部")){

                cadres.addAll(XlsUpload.fetchCadres(sheet));
            }
        }

        int successCount = cadreService.importCadres(cadres, status);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadres.size());

        return resultMap;
    }
}
