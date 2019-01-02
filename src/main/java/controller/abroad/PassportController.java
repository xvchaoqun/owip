package controller.abroad;

import bean.XlsPassport;
import bean.XlsUpload;
import controller.global.OpException;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportExample;
import domain.abroad.TaiwanRecord;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
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
import persistence.abroad.common.PassportSearchBean;
import persistence.abroad.common.PassportStatByClassBean;
import persistence.abroad.common.PassportStatByLentBean;
import persistence.abroad.common.PassportStatByPostBean;
import sys.constants.AbroadConstants;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.ImageUtils;
import sys.utils.JSONUtils;
import sys.utils.PatternUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/abroad")
public class PassportController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passport:list")
    @RequestMapping("/passport")
    public String passport(// 1:集中管理证件 2:取消集中保管证件 3:丢失证件  5：保险柜管理
                           @RequestParam(required = false, defaultValue = "1") byte status,
                           HttpServletResponse response,
                           ModelMap modelMap) {

        modelMap.put("status", status);
        if (status == 0) {
            return "forward:/abroad/passport_stat";
        } else if (status == 5) {
            return "forward:/abroad/safeBox";
        } else {
            return "forward:/abroad/passportList_page";
        }
    }

    @RequiresPermissions("passport:list")
    @RequestMapping("/passport_stat")
    public String passport_stat(ModelMap modelMap) {

        List<PassportStatByClassBean> classBeans = iAbroadMapper.passportStatByClass();
        List<PassportStatByLentBean> lentBeans = iAbroadMapper.passportStatByLent();

        int selfPassportTypeId = CmTag.getMetaTypeByCode("mt_passport_normal").getId();
        int twPassportTypeId = CmTag.getMetaTypeByCode("mt_passport_tw").getId();
        List<PassportStatByPostBean> postBeans = iAbroadMapper.passportStatByPost(selfPassportTypeId, twPassportTypeId);

        modelMap.put("totalCount", iAbroadMapper.passportCount());
        modelMap.put("classBeans", JSONUtils.toString(classBeans));
        modelMap.put("lentBeans", JSONUtils.toString(lentBeans));
        modelMap.put("postBeans", JSONUtils.toString(postBeans));

        return "abroad/passport/passport_stat";
    }

    @RequiresPermissions("passport:list")
    @RequestMapping("/passportList_page")
    public String passportList_page(
            @CurrentUser SysUserView loginUser,
            //Integer unitId,
            Integer cadreId,
            // 1:集中管理证件 2:取消集中保管证件 3:丢失证件 4：作废证件 5 保险柜管理
            @RequestParam(required = false, defaultValue = "1") byte status,
            Integer userId, ModelMap modelMap) {

        // 判断下是否上传了签名 和联系电话
        String sign = loginUser.getSign();
        if(StringUtils.isBlank(sign)
                || FileUtils.exists(springProps.uploadPath + sign)==false
                || StringUtils.isBlank(loginUser.getMobile())) {
            return "abroad/passportApply/passportApply_sign";
        }

        modelMap.put("status", status);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        /*if(unitId!=null){

            modelMap.put("unit", unitService.findAll().get(unitId));
        }*/
        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "abroad/passport/passport_page";
    }

    @RequiresPermissions("passport:list")
    @RequestMapping("/passport_data")
    public void passport_data(HttpServletResponse response,
                              @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport") String sort,
                              @OrderParam(required = false, defaultValue = "desc") String order,
                              Integer unitId,
                              Integer cadreId,
                              Integer classId,
                              String code,
                              Integer safeBoxId,
                              Boolean isLent,
                              // 1:集中管理证件 2:取消集中保管证件（未确认） 3:丢失证件  4:取消集中保管证件（已确认）
                              @RequestParam(required = false, defaultValue = "1") byte status,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Byte type = null;
        if (status < 4) {
            type = status;
        }
        Boolean cancelConfirm = null;
        if (status == 2) {
            cancelConfirm = false;
        }
        if (status == 4) {
            cancelConfirm = true;
            type = 2;
        }

        code = StringUtils.trimToNull(code);

        PassportSearchBean bean = new PassportSearchBean(unitId, cadreId, classId, code,
                type, safeBoxId, cancelConfirm, isLent);

        if (export == 1) {
            passport_export(bean, status, response);
            return;
        }

        int count = iAbroadMapper.countPassport(bean);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Passport> passports = iAbroadMapper.selectPassportList
                (bean, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passports);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void passport_export(PassportSearchBean bean, Byte status,
                                HttpServletResponse response) {

        List<Passport> records = iAbroadMapper.selectPassportList(bean, new RowBounds());
        int rownum = records.size();
        if(status==AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP) {
            String[] titles = {"工作证号|100", "姓名|50", "所在单位及职务|300|left", "职务属性|100", "干部类型|100",
                    "证件名称|160", "证件号码|100", "发证机关|150", "发证日期|100",
                    "有效期|100", "集中管理日期|100", "所在保险柜|100", "是否借出|80"};
            List<String[]> valuesList = new ArrayList<>();
            for (int i = 0; i < rownum; i++) {
                Passport record = records.get(i);
                SysUserView sysUser = record.getUser();
                CadreView cadre = record.getCadre();
                String[] values = {
                        sysUser.getCode(),
                        sysUser.getRealname(),
                        cadre.getTitle(),
                        cadre.getPostType()!=null?cadre.getPostType().getName():"",
                        CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()),
                        record.getPassportClass().getName(),
                        record.getCode(),
                        record.getAuthority(),
                        record.getIssueDate()!=null?DateUtils.formatDate(record.getIssueDate(), DateUtils.YYYY_MM_DD):"",
                        record.getExpiryDate()!=null?DateUtils.formatDate(record.getExpiryDate(), DateUtils.YYYY_MM_DD):"",
                        record.getKeepDate()!=null?DateUtils.formatDate(record.getKeepDate(), DateUtils.YYYY_MM_DD):"",
                        record.getSafeBox().getCode(),
                        record.getIsLent()?"借出":"-"
                };
                valuesList.add(values);
            }
            String fileName = "集中管理证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles, valuesList, fileName, response);
        }

        if(status==AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL) {
            String[] titles = {"工作证号", "姓名", "所在单位及职务", "职务属性", "干部类型",
                    "证件名称", "证件号码", "发证机关", "发证日期", "有效期", "集中管理日期", "所在保险柜", "是否借出"
                    , "取消集中保管原因", "状态"};
            List<String[]> valuesList = new ArrayList<>();
            for (int i = 0; i < rownum; i++) {
                Passport record = records.get(i);
                SysUserView uv = record.getUser();
                CadreView cadre = record.getCadre();
                String[] values = {
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        cadre.getPostType()!=null?cadre.getPostType().getName():"",
                        CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()),
                        record.getPassportClass().getName(),
                        record.getCode(),
                        record.getAuthority(),
                        record.getIssueDate()!=null?DateUtils.formatDate(record.getIssueDate(), DateUtils.YYYY_MM_DD):"",
                        record.getExpiryDate()!=null?DateUtils.formatDate(record.getExpiryDate(), DateUtils.YYYY_MM_DD):"",
                        record.getKeepDate()!=null?DateUtils.formatDate(record.getKeepDate(), DateUtils.YYYY_MM_DD):"",
                        record.getSafeBox().getCode(),
                        record.getIsLent()?"借出":"-",
                        AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_MAP.get(record.getCancelType()),
                        BooleanUtils.isTrue(record.getCancelConfirm())?"已确认":"未确认"
                };
                valuesList.add(values);
            }
            String fileName = "取消集中管理证件（未确认）_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles, valuesList, fileName, response);
        }

        if(status==4) { // 取消集中管理证件（已确认）
            String[] titles = {"工作证号", "姓名", "所在单位及职务", "职务属性", "干部类型",
                    "证件名称", "证件号码", "发证机关", "发证日期", "有效期", "集中管理日期","取消集中保管日期", "取消集中保管原因", "状态"};
            List<String[]> valuesList = new ArrayList<>();
            for (int i = 0; i < rownum; i++) {
                Passport record = records.get(i);
                SysUserView uv = record.getUser();
                CadreView cadre = record.getCadre();
                String[] values = {
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        cadre.getPostType()!=null?cadre.getPostType().getName():"",
                        CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()),
                        record.getPassportClass().getName(),
                        record.getCode(),
                        record.getAuthority(),
                        record.getIssueDate()!=null?DateUtils.formatDate(record.getIssueDate(), DateUtils.YYYY_MM_DD):"",
                        record.getExpiryDate()!=null?DateUtils.formatDate(record.getExpiryDate(), DateUtils.YYYY_MM_DD):"",
                        record.getKeepDate()!=null?DateUtils.formatDate(record.getKeepDate(), DateUtils.YYYY_MM_DD):"",
                        record.getCancelTime()!=null?DateUtils.formatDate(record.getCancelTime(), DateUtils.YYYY_MM_DD):"",
                        AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_MAP.get(record.getCancelType()),
                        BooleanUtils.isTrue(record.getCancelConfirm())?"已确认":"未确认"
                };
                valuesList.add(values);
            }
            String fileName = "取消集中管理证件（已确认）_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles, valuesList, fileName, response);
        }

        if(status==AbroadConstants.ABROAD_PASSPORT_TYPE_LOST) {
            String[] titles = {"工作证号", "姓名", "所在单位及职务", "职务属性", "干部类型",
                    "证件名称", "证件号码", "发证机关", "发证日期", "有效期", "集中管理日期","登记丢失日期"};
            List<String[]> valuesList = new ArrayList<>();
            for (int i = 0; i < rownum; i++) {
                Passport record = records.get(i);
                SysUserView uv = record.getUser();
                CadreView cadre = record.getCadre();
                String keepDate = "";
                if(!record.getKeepDate().after(record.getLostTime())){
                    keepDate=record.getKeepDate()!=null?DateUtils.formatDate(record.getKeepDate(), DateUtils.YYYY_MM_DD):"";
                }
                String[] values = {
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        cadre.getPostType()!=null?cadre.getPostType().getName():"",
                        CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()),
                        record.getPassportClass().getName(),
                        record.getCode(),
                        record.getAuthority(),
                        record.getIssueDate()!=null?DateUtils.formatDate(record.getIssueDate(), DateUtils.YYYY_MM_DD):"",
                        record.getExpiryDate()!=null?DateUtils.formatDate(record.getExpiryDate(), DateUtils.YYYY_MM_DD):"",
                        keepDate,
                        record.getLostTime()!=null?DateUtils.formatDate(record.getLostTime(), DateUtils.YYYY_MM_DD):""
                };
                valuesList.add(values);
            }
            String fileName = "丢失的证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ExportHelper.export(titles, valuesList, fileName, response);
        }
    }

    @RequiresPermissions("passport:list")
    @RequestMapping("/passport_useLogs")
    public String passport_useLogs(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);
        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "abroad/passport/passport_useLogs";
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_cancel( @CurrentUser SysUserView loginUser, int id,
                                   String _base64,
                                   @RequestParam(required = false, defaultValue = "0") Integer _rotate,
                                   MultipartFile _cancelPic, HttpServletRequest request) throws IOException {

        if ((_cancelPic == null || _cancelPic.isEmpty()) && StringUtils.isBlank(_base64))
            return failed("请选择签字图片或进行拍照");

        Passport record = new Passport();
        record.setId(id);

        record.setCancelTime(new Date());
        record.setCancelConfirm(true);
        record.setCancelUserId(loginUser.getId());

        if (_cancelPic != null && !_cancelPic.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String savePath = FILE_SEPARATOR
                    + "passport_cancel" + FILE_SEPARATOR
                    + fileName + FileUtils.getExtention(_cancelPic.getOriginalFilename());

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(_cancelPic.getInputStream())
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);

            record.setCancelPic(savePath);
        }else if(StringUtils.isNotBlank(_base64)){

            String savePath = FILE_SEPARATOR
                    + "passport_cancel" + FILE_SEPARATOR + UUID.randomUUID().toString() + ".jpg";

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(ImageUtils.decodeBase64ToBufferedImage(_base64.split("base64,")[1]))
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);
            //ImageUtils.decodeBase64ToImage(_base64.split("base64,")[1], springProps.uploadPath + realPath, fileName);

            record.setCancelPic(savePath);
        }

        passportService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "确认取消集中管理证件：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping("/passport_cancel")
    public String passport_cancel(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/passport_cancel";
    }

    @RequiresPermissions("passport:view")
    @RequestMapping("/passport_remark")
    public String passport_remark(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/passport_remark";
    }

    @RequiresPermissions("passport:view")
    @RequestMapping("/passport_lost_view")
    public String passport_lost_view(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/passport_lost_view";
    }

    // 批量上传证件首页
    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/passport_uploadPic_batch")
    @ResponseBody
    public Map passport_uploadPic_batch(String folder, String type) { // folder是具体的系统文件夹路径，下面都是图片。

        // type= mt_passport_normal, mt_passport_hk, mt_passport_tw
        MetaType passportType = CmTag.getMetaTypeByCode(type);
        File[] files = new File(folder).listFiles();
        int successCount = 0;
        for (File file : files) {
            if(file.isFile()){
                String filename = file.getName();
                try {
                    if (PatternUtils.match("^.*\\.(jpg|JPG)$", filename)) {
                        String _filename = filename.split("\\.")[0];

                        CadreView cv = null;
                        {
                            CadreViewExample example = new CadreViewExample();
                            example.createCriteria().andRealnameEqualTo(_filename);
                            List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);
                            if (cadreViews.size() == 1) {
                                cv = cadreViews.get(0);
                            }
                        }
                        if(cv!=null){

                            String realPath = FILE_SEPARATOR
                                    + "passport_pic" + FILE_SEPARATOR  // passport_cancel -> passport_lost 20160620
                                    + UUID.randomUUID().toString()+ ".jpg";
                            FileUtils.copyFile(file, new File(springProps.uploadPath + realPath));

                            Passport record = new Passport();
                            record.setPic(realPath);

                            PassportExample example = new PassportExample();
                            example.createCriteria().andCadreIdEqualTo(cv.getId()).andClassIdEqualTo(passportType.getId());
                            successCount += passportMapper.updateByExampleSelective(record, example);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        Map<String, Object> resultMap = success();
        resultMap.put("successCount", successCount);
        resultMap.put("totalCount", files.length);

        return resultMap;
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/passport_uploadPic")
    public String passport_uploadPic() {

        return "abroad/passport/passport_uploadPic";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/passport_uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_uploadPic(int id, String _base64,
                                     @RequestParam(required = false, defaultValue = "0") Integer _rotate,
                                     MultipartFile _pic) throws IOException {

        Passport record = new Passport();
        record.setId(id);

        String realPath = FILE_SEPARATOR
                + "passport_pic" + FILE_SEPARATOR  // passport_cancel -> passport_lost 20160620
                + UUID.randomUUID().toString();
        if (_pic != null && !_pic.isEmpty()) {

            String ext = FileUtils.getExtention(_pic.getOriginalFilename());
            String savePath = realPath + ext;
            FileUtils.copyFile(_pic, new File(springProps.uploadPath + savePath));
            record.setPic(savePath);

        }else if(StringUtils.isNotBlank(_base64)) {

            String savePath =realPath + ".jpg";
            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(ImageUtils.decodeBase64ToBufferedImage(_base64.split("base64,")[1]))
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);
            record.setPic(savePath);
        }

        if(record.getPic()!=null){

            passportService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "上传证件首页：%s", record.getId()));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/updateLostProof")
    public String updateLostProof(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/updateLostProof";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/updateLostProof", method = RequestMethod.POST)
    @ResponseBody
    public Map do_updateLostProof(
            @CurrentUser SysUserView loginUser,
            int id,
            String _lostTime,
            MultipartFile _lostProof,
            HttpServletRequest request) {

        Passport record = new Passport();
        record.setId(id);
        if (StringUtils.isNotBlank(_lostTime)) {
            record.setLostTime(DateUtils.parseDate(_lostTime, DateUtils.YYYY_MM_DD));
        }
        if (_lostProof != null && !_lostProof.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "passport_lost" + FILE_SEPARATOR  // passport_cancel -> passport_lost 20160620
                    + fileName;
            String ext = FileUtils.getExtention(_lostProof.getOriginalFilename());
            String savePath = realPath + ext;
            FileUtils.copyFile(_lostProof, new File(springProps.uploadPath + savePath));
            record.setLostProof(savePath);
        }
        record.setLostUserId(loginUser.getId());
        if(record.getLostTime()!=null || record.getLostProof()!=null)
            passportService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "更新证件丢失证明：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:view")
    @RequestMapping("/passport_cancel_view")
    public String passport_cancel_view(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/passport_cancel_view";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/updateCancelPic")
    public String updateCancelProof(int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        return "abroad/passport/updateCancelPic";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/updateCancelPic", method = RequestMethod.POST)
    @ResponseBody
    public Map do_updateCancelProof(
            int id,
            String _cancelTime,
            MultipartFile _cancelPic,
            HttpServletRequest request) {

        Passport record = new Passport();
        record.setId(id);
        if (StringUtils.isNotBlank(_cancelTime)) {
            record.setCancelTime(DateUtils.parseDate(_cancelTime, DateUtils.YYYY_MM_DD));
        }
        if (_cancelPic != null && !_cancelPic.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "passport_cancel" + FILE_SEPARATOR
                    + fileName;
            String ext = FileUtils.getExtention(_cancelPic.getOriginalFilename());
            String savePath = realPath + ext;
            FileUtils.copyFile(_cancelPic, new File(springProps.uploadPath + savePath));
            record.setCancelPic(savePath);
        }
        //record.setCancelConfirm(true);
        if(record.getCancelTime()!=null || record.getCancelPic()!=null)
            passportService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "更新取消集中管理证明：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_check", method = RequestMethod.POST)
    @ResponseBody
    public Map passport_check(int cadreId, int classId) {

        Map<String, Object> resultMap = success();

        int result = 0;
        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);
        if(passportMap.get(classId)!=null){
            result = 1; // 已经拥有该证件
        }
        if(passportApplyService.checkApplyPassButNotHandle(cadreId, classId)){
            result = 2; // 申请办理已通过，还未交证件
        }

        if(passportApplyService.checkNewApply(cadreId, classId)){
            result = 3; // 新申请
        }

        resultMap.put("result", result);

        return resultMap;
    }
    @RequiresPermissions("passport:edit")
    @RequestMapping(value = "/passport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_au(String op, Passport record,
                              Integer applyId, Integer taiwanRecordId,
                              String _issueDate, String _expiryDate,
                              String _keepDate,
                              Byte type,
                              String _lostTime,
                              MultipartFile _lostProof,
                              HttpServletRequest request) {

        Integer id = record.getId();

        MetaType passportType = CmTag.getMetaType(record.getClassId());
        int idDuplicate = passportService.idDuplicate(id, record.getType(), record.getCadreId(), record.getClassId(), record.getCode());
        if(idDuplicate == 1){
            return failed("证件号码重复");
        }
        if (idDuplicate == 2) {
            return failed(passportType.getName() + "重复，请先作废现有的" + passportType.getName());
        }

        if (StringUtils.isNotBlank(_issueDate)) {
            record.setIssueDate(DateUtils.parseDate(_issueDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_expiryDate)) {
            record.setExpiryDate(DateUtils.parseDate(_expiryDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_keepDate)) {
            record.setKeepDate(DateUtils.parseDate(_keepDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_lostTime)) {
            record.setLostTime(DateUtils.parseDate(_lostTime, DateUtils.YYYY_MM_DD));
        }

        if (type != null && type == AbroadConstants.ABROAD_PASSPORT_TYPE_LOST) {

            //if (id == null && (_lostProof == null || _lostProof.isEmpty())) throw new OpException("请选择丢失证明文件");
            if (_lostProof != null && !_lostProof.isEmpty()) {
                String fileName = UUID.randomUUID().toString();
                String realPath = FILE_SEPARATOR
                        + "passport_cancel" + FILE_SEPARATOR
                        + fileName;
                String ext = FileUtils.getExtention(_lostProof.getOriginalFilename());
                String savePath = realPath + ext;
                FileUtils.copyFile(_lostProof, new File(springProps.uploadPath + savePath));
                record.setLostProof(savePath);
            }
            if (id == null)
                record.setLostType(AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_ADD);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        if (id == null) {
            if (type == null)
                record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
            else
                record.setType(type);

            record.setIsLent(false);
            record.setCancelConfirm(false);
            record.setCreateTime(new Date());
            passportService.add(record, applyId, taiwanRecordId);
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加证件：%s", record.getId()));

            // 添加证件后需要短信提醒
            resultMap.put("id", record.getId());

        } else {

            Passport passport = passportMapper.selectByPrimaryKey(id);

            if(!StringUtils.equals(op, "back")) {
                if (!(passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP
                        || passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL
                        || (passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_LOST
                        && passport.getLostType() == AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_ADD))) {
                    // 只有集中管理证件 或 取消集中管理证件 或 从 后台添加的 丢失证件，可以更新
                    return failed("该证件不可以进行更新操作");
                }
                record.setType(null);
                passportService.updateByPrimaryKeySelective(record);
            }else{
                // 证件找回
                passportService.back(record);
            }

            logger.info(addLog(LogConstants.LOG_ABROAD, "更新证件：%s", record.getId()));
        }

        return resultMap;
    }

    // 下载取消集中管理证明
    @RequiresPermissions("passport:download")
    @RequestMapping("/passport_cancelPic_download")
    public void passport_cancelPic_download(Integer id, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        String cancelPic = passport.getCancelPic();
        String filePath = springProps.uploadPath + cancelPic;

        MetaType passportType = CmTag.getMetaType(passport.getClassId());
        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        String fileName = URLEncoder.encode(uv.getRealname() + "-" + passportType.getName()
                + "（取消集中管理证明）" + FileUtils.getExtention(cancelPic), "UTF-8");

        DownloadUtils.download(request, response, filePath, fileName);
    }

    @RequiresPermissions("passport:download")
    @RequestMapping("/passport_lostProof_download")
    public void passport_lostProof_download(Integer id, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        String lostProof = passport.getLostProof();
        String filePath = springProps.uploadPath + lostProof;

        MetaType passportType = CmTag.getMetaType(passport.getClassId());
        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        String fileName = URLEncoder.encode(uv.getRealname() + "-" + passportType.getName()
                + "（丢失证明）" + FileUtils.getExtention(lostProof), "UTF-8");

        DownloadUtils.download(request, response, filePath, fileName);
    }

    @RequiresPermissions("passport:edit")
    @RequestMapping("/passport_au")
    public String passport_au(String op, Integer id, Integer type,
                              Integer applyId, Integer taiwanRecordId, ModelMap modelMap) {

        modelMap.put("type", type);
        Passport passport = null;
        if (id != null) {
            passport = passportMapper.selectByPrimaryKey(id);

            modelMap.put("type", passport.getType());

            if(!StringUtils.equals(op, "back")) {
                if (!(passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP
                        || passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL
                        || (passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_LOST
                        && passport.getLostType() == AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_ADD))) {
                    // 只有集中管理证件 或 取消集中管理证件 或 从 后台添加的 丢失证件，可以更新
                    throw new OpException("该证件不可以进行更新操作");
                }
            }
        } else if (applyId != null) {

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            passport = new Passport();
            passport.setCadreId(passportApply.getCadreId());
            passport.setClassId(passportApply.getClassId());

            // 已经存在该类证件
            modelMap.put("isDuplicate", passportService.idDuplicate(id, AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP,
                    passportApply.getCadreId(), passportApply.getClassId(), null)==2);

        }else if(taiwanRecordId!=null){

            TaiwanRecord taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(taiwanRecordId);
            MetaType passportTwType = CmTag.getMetaTypeByCode("mt_passport_tw");
            passport = new Passport();
            passport.setCadreId(taiwanRecord.getCadreId());
            passport.setClassId(passportTwType.getId());
        }

        modelMap.put("passport", passport);

        return "abroad/passport/passport_au";
    }

    @RequiresPermissions("passport:lost")
    @RequestMapping("/passport_lost")
    public String passport_lost(Integer id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "abroad/passport/passport_lost";
    }

    // 从集中管理证件库 -> 丢失证件库
    @RequiresPermissions("passport:lost")
    @RequestMapping(value = "/passport_lost", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_lost(@CurrentUser SysUserView loginUser, Integer id, String _lostTime,
                                MultipartFile _lostProof) {

        Passport record = new Passport();
        record.setId(id);
        if (StringUtils.isNotBlank(_lostTime)) {
            record.setLostTime(DateUtils.parseDate(_lostTime, DateUtils.YYYY_MM_DD));
        }
        if (_lostProof != null && !_lostProof.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "passport_cancel" + FILE_SEPARATOR
                    + fileName;
            String ext = FileUtils.getExtention(_lostProof.getOriginalFilename());
            String savePath = realPath + ext;
            FileUtils.copyFile(_lostProof, new File(springProps.uploadPath + savePath));
            record.setLostProof(savePath);
        }

        record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_LOST);
        record.setLostType(AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_TRANSFER);
        record.setLostUserId(loginUser.getId());

        // 在“借出”状态, 加备注
        Passport passport = passportMapper.selectByPrimaryKey(id);
        if(BooleanUtils.isTrue(passport.getIsLent())){

            record.setCancelRemark("在证件借出的情况下证件丢失");
        }

        passportService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "丢失证件：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:abolish")
    @RequestMapping("/passport_abolish")
    public String passport_abolish(Integer id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        modelMap.put("passport", passport);

        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "abroad/passport/passport_abolish";
    }

    @RequiresPermissions("passport:abolish")
    @RequestMapping(value = "/passport_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_abolish(HttpServletRequest request,
                                   Integer id, Byte cancelType,
                                   String cancelTypeOther) {

        passportService.abolish(id,cancelType, cancelTypeOther);
        logger.info(addLog(LogConstants.LOG_ABROAD,
                "取消证件集中管理：%s, %s", id, cancelType, cancelTypeOther));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:abolish")
    @RequestMapping(value = "/passport_unabolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_unabolish(HttpServletRequest request, Integer id) {

        Passport passport = passportService.unabolish(id);
        if(passport!=null) {
            MetaType mcPassportType = CmTag.getMetaType(passport.getClassId());
            logger.info(addLog(LogConstants.LOG_ABROAD, "已取消集中管理证件返回集中管理：%s, %s",
                    passport.getUser().getRealname(), mcPassportType.getName()));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportService.del(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("passport:del")
    @RequestMapping(value = "/passport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passport:import")
    @RequestMapping("/passport_import")
    public String passport_import(ModelMap modelMap) {

        return "abroad/passport/passport_import";
    }

    @RequiresPermissions("passport:import")
    @RequestMapping(value = "/passport_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passport_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsPassport> passports = new ArrayList<XlsPassport>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);

            String sheetName = sheet.getSheetName();

            if (StringUtils.equals(sheetName, "证件")) {

                passports.addAll(XlsUpload.fetchPassports(sheet));
            }
        }

        int successCount = passportService.importPassports(passports, AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", passports.size());

        return resultMap;
    }

    /*@RequestMapping("/passport_selects")
    @ResponseBody
    public Map passport_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportExample example = new PassportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = passportMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Passport> passports = passportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != passports && passports.size()>0){

            for(Passport passport:passports){

                Select2Option option = new Select2Option();
                option.setText(passport.getName());
                option.setId(passport.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
