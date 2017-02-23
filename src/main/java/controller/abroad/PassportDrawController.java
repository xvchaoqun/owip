package controller.abroad;

import controller.BaseController;
import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.PassportDrawExample.Criteria;
import domain.base.ContentTpl;
import domain.base.Country;
import domain.cadre.Cadre;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.ApplySelfMixin;
import mixin.PassportDrawMixin;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class PassportDrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw")
    public String passportDraw() {

        return "index";
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_check")
    public String passportDraw_check(int id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        return "abroad/passportDraw/passportDraw_check";
    }

    @RequiresRoles(SystemConstants.ROLE_CADREADMIN)
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_agree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(StringUtils.trimToNull(remark));

        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "批准申请使用证件（通过）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_disagree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(StringUtils.trimToNull(remark));
        if(record.getApproveRemark()==null){
            return failed("请输入原因");
        }
        record.setStatus(SystemConstants.PASSPORT_DRAW_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "批准申请使用证件（未通过）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_page")
    public String passportDraw_page(Integer cadreId,
                                    @RequestParam(required = false, defaultValue = "1") byte type,
                                    ModelMap modelMap) {
        modelMap.put("type", type);
        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/passportDraw/passportDraw_page";
    }
    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_data")
    public void passportDraw_data(HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer passportId,
                                    Integer year,
                                    // -1:已删除
                                    @RequestParam(required = false, defaultValue = "1") byte type,
                                    String _applyDate,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportDrawExample example = new PassportDrawExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(passportId!=null){ // 查询特定证件的使用记录
            criteria.andPassportIdEqualTo(passportId);
        }else {

            if(type==-1){
                criteria.andIsDeletedEqualTo(true);
            }else {
                criteria.andIsDeletedEqualTo(false);

                if (type == SystemConstants.PASSPORT_DRAW_TYPE_SELF ||
                        type == SystemConstants.PASSPORT_DRAW_TYPE_OTHER) {
                    criteria.andTypeEqualTo(type);
                } else { // 因公赴台、长期因公出国
                    criteria.andTypeIn(Arrays.asList(SystemConstants.PASSPORT_DRAW_TYPE_TW,
                            SystemConstants.PASSPORT_DRAW_TYPE_LONG_SELF));
                }
            }
        }
        if(year!=null){
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }

        if(StringUtils.isNotBlank(_applyDate)) {
            String applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(applyDateStart)) {
                criteria.andApplyDateGreaterThanOrEqualTo(DateUtils.parseDate(applyDateStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(applyDateEnd)) {
                criteria.andApplyDateLessThanOrEqualTo(DateUtils.parseDate(applyDateEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            passportDrawService.passportDraw_export(example, response);
            return;
        }

        int count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passportDraws);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(PassportDraw.class, PassportDrawMixin.class);
        sourceMixins.put(ApplySelf.class, ApplySelfMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 管理员添加申请
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_au")
    public String passportDraw_au(Byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "abroad/passportDraw/passportDraw_au";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_draw")
    public String passportDraw_draw(Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        ContentTpl shortMsgTpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PASSPORTDRAW);
        modelMap.put("shortMsg", shortMsgTpl.getContent());
        modelMap.put("mobile", userBeanService.getMsgMobile(passport.getUser().getId()));


        return "abroad/passportDraw/passportDraw_draw";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_draw(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                    MultipartFile _drawRecord, String _returnDate, Integer id) {

        PassportDraw record = new PassportDraw();

       /* if (_drawRecord == null || _drawRecord.isEmpty()) {
            throw new RuntimeException("请选择证件拍照");
        }*/
        record.setId(id);
        record.setReturnDate(DateUtils.parseDate(_returnDate, DateUtils.YYYY_MM_DD));

        if (_drawRecord != null && !_drawRecord.isEmpty()) {
            String originalFilename = _drawRecord.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "draw" + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_drawRecord, new File(springProps.uploadPath + savePath));

            record.setDrawRecord(savePath);
        }
        record.setDrawUserId(loginUser.getId());
        record.setDrawTime(new Date());
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW);

        passportDrawService.drawPassport(record);

        logger.info(addLog(SystemConstants.LOG_ABROAD, "领取证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_return")
    public String passportDraw_return(Integer id, HttpServletRequest request, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_SELF){

            request.setAttribute("isView", true);
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_OTHER){
            return "abroad/passportDraw/passportDraw_other_return";
        }

        return "abroad/passportDraw/passportDraw_return";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_return(@CurrentUser SysUserView loginUser,
                                      HttpServletRequest request,
                                      Boolean usePassport,
                                      String  _realReturnDate,
                                    MultipartFile _attachment,
                                    MultipartFile _useRecord,
                                      String _base64,
                                      @RequestParam(required = false, defaultValue = "0") Integer _rotate,
                                      String _realStartDate,
                                    String _realEndDate,
                                      String realToCountry, String remark, Integer id) throws IOException {

        PassportDraw record = new PassportDraw();

        /*if (_useRecord == null || _useRecord.isEmpty()) {
            throw new RuntimeException("请选择证件使用记录拍照");
        }*/

        if(_attachment!=null){
            String ext = FileUtils.getExtention(_attachment.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".pdf")){
                throw new RuntimeException("附件格式错误，请上传pdf文件");
            }

            String originalFilename = _attachment.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "passportDraw" + FILE_SEPARATOR
                    + "attachment" + FILE_SEPARATOR
                    + id + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_attachment, new File(springProps.uploadPath + savePath));

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + savePath, springProps.uploadPath + swfPath);

            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setAttachmentFilename(originalFilename);
            record.setAttachment(savePath);
        }

        record.setId(id);
        record.setUsePassport(usePassport);
        record.setRealReturnDate(DateUtils.parseDate(_realReturnDate, DateUtils.YYYY_MM_DD));
        record.setRealStartDate(DateUtils.parseDate(_realStartDate, DateUtils.YYYY_MM_DD));
        record.setRealEndDate(DateUtils.parseDate(_realEndDate, DateUtils.YYYY_MM_DD));
        record.setRealToCountry(StringUtils.trimToNull(realToCountry));

        if (_useRecord != null && !_useRecord.isEmpty()) {
            String originalFilename = _useRecord.getOriginalFilename();
            String savePath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "use" + FILE_SEPARATOR
                    + UUID.randomUUID().toString() + FileUtils.getExtention(originalFilename);

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(_useRecord.getInputStream())
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);

            record.setUseRecord(savePath);
        }else if(StringUtils.isNotBlank(_base64)){

            String savePath = FILE_SEPARATOR
                    + "draw" + FILE_SEPARATOR + "use" + FILE_SEPARATOR + UUID.randomUUID().toString() + ".jpg";

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(ImageUtils.decodeBase64ToBufferedImage(_base64.split("base64,")[1]))
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);
            //ImageUtils.decodeBase64ToImage(_base64.split("base64,")[1], springProps.uploadPath + realPath, fileName);

            record.setUseRecord(savePath);
        }

        record.setReturnRemark(remark);
        if(record.getRealReturnDate()==null) // 默认当天为归还时间
            record.setRealReturnDate(new Date());
        record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_RETURN);

        passportDrawService.returnPassport(record);

        logger.info(addLog(SystemConstants.LOG_ABROAD, "归还证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 修改归还日期
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/reset_passportDraw_returnDate")
    public String reset_passportDraw_returnDate( Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);

        return "abroad/passportDraw/reset_passportDraw_returnDate";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/reset_passportDraw_returnDate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_reset_passportDraw_returnDate(Integer id, String _returnDate) throws IOException {

        PassportDraw record = new PassportDraw();
        record.setReturnDate(DateUtils.parseDate(_returnDate, DateUtils.YYYY_MM_DD));

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(SystemConstants.PASSPORT_DRAW_STATUS_PASS)
                .andDrawStatusEqualTo(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW);
        passportDrawMapper.updateByExampleSelective(record, example);

        logger.info(addLog(SystemConstants.LOG_ABROAD, "修改归还日期：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 重置归还状态
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/reset_passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_reset_passportDraw_return(@CurrentUser SysUserView loginUser, Integer id) throws IOException {

        passportDrawService.resetReturnPassport(id);

        logger.info(addLog(SystemConstants.LOG_ABROAD, "重置归还证件的归还状态为未归还：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_view")
    public String passportDraw_view( Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        Cadre cadre = cadreService.findAll().get(passportDraw.getCadreId());
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("passportDraw", passportDraw);


        if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_SELF){
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        return "user/abroad/passportDraw/passportDraw_view";
    }

    /*@RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportDrawService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除领取证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    // 删除（默认逻辑删除）
    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        Boolean isReal, // 是否真删除
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportDrawService.batchDel(ids, BooleanUtils.isTrue(isReal));
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除领取证件申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportDrawService.batchUnDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量找回领取证件申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

}
