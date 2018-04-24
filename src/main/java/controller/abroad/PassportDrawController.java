package controller.abroad;

import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.PassportDrawExample.Criteria;
import domain.base.ContentTpl;
import domain.base.Country;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
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
import sys.constants.AbroadConstants;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.ImageUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/abroad")
public class PassportDrawController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_check")
    public String passportDraw_check(int id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        return "abroad/passportDraw/passportDraw_check";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_agree", method = RequestMethod.POST)
    @ResponseBody
    public Map passportDraw_agree(@CurrentUser SysUserView loginUser, int id, String remark, HttpServletRequest request) {

        PassportDraw record = new PassportDraw();
        record.setId(id);
        record.setApproveRemark(StringUtils.trimToNull(remark));

        record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请使用证件（通过）：%s", record.getId()));

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
        record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS);
        record.setUserId(loginUser.getId());
        record.setApproveTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        passportDrawService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "批准申请使用证件（未通过）：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw")
    public String passportDraw(Integer cadreId,
                                    @RequestParam(required = false, defaultValue = "1") byte type,
                                    ModelMap modelMap) {
        modelMap.put("type", type);
        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
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
                                    @RequestDateRange DateRange _applyDate,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    // 导出类型：1：因私出国境 2： 台湾、长期 3： 处理其他事务
                                    @RequestParam(required = false, defaultValue = "1") byte exportType,
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

                if (type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF ||
                        type == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
                    criteria.andTypeEqualTo(type);
                } else { // 因公赴台、长期因公出国
                    criteria.andTypeIn(Arrays.asList(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW,
                            AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF));
                }
            }
        }
        if(year!=null){
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }
        if (_applyDate.getStart()!=null) {
            criteria.andApplyDateGreaterThanOrEqualTo(_applyDate.getStart());
        }

        if (_applyDate.getEnd()!=null) {
            criteria.andApplyDateLessThanOrEqualTo(_applyDate.getEnd());
        }

        if (export == 1) {
            passportDrawService.passportDraw_export(exportType, example, response);
            return;
        }

        long count = passportDrawMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
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
    public String passportDraw_draw(Integer id, HttpServletRequest request, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if(passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF){

            request.setAttribute("isView", true);
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        ContentTpl shortMsgTpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PASSPORTDRAW);
        Integer userId = passport.getUser().getId();
        String msgTitle = userBeanService.getMsgTitle(userId);
        String shortMsg = MessageFormat.format(shortMsgTpl.getContent(), msgTitle,
                passport.getPassportClass().getName());;
        modelMap.put("shortMsg", shortMsg);
        modelMap.put("mobile", userBeanService.getMsgMobile(userId));

        return "abroad/passportDraw/passportDraw_draw";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_draw(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                    MultipartFile _drawRecord, String _returnDate, Integer id) {

        PassportDraw record = new PassportDraw();

       /* if (_drawRecord == null || _drawRecord.isEmpty()) {
           return failed("请选择证件拍照");
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
        record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW);

        passportDrawService.drawPassport(record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "领取证件：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping("/passportDraw_return")
    public String passportDraw_return(Integer id, HttpServletRequest request, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        modelMap.put("passportDraw", passportDraw);
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        modelMap.put("passport", passport);

        if(passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF){

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

        if(passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER){
            return "abroad/passportDraw/passportDraw_other_return";
        }

        return "abroad/passportDraw/passportDraw_return";
    }

    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_return(@CurrentUser SysUserView loginUser,
                                      HttpServletRequest request,
                                      byte usePassport,
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
           return failed("请选择证件使用记录拍照");
        }*/

        if(_attachment!=null){
            String ext = FileUtils.getExtention(_attachment.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".pdf")){
                return failed("附件格式错误，请上传pdf文件");
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
                pdf2Swf(savePath, swfPath);
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

        if(record.getRealStartDate()!=null && record.getRealEndDate()!=null
                && record.getRealStartDate().after(record.getRealEndDate())){
            return failed("实际出行时间有误。");
        }

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

        if(usePassport != AbroadConstants.ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN)
            record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN);

        passportDrawService.returnPassport(record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "归还证件：%s", id));
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
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS)
                .andDrawStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW);
        passportDrawMapper.updateByExampleSelective(record, example);

        logger.info(addLog(LogConstants.LOG_ABROAD, "修改归还日期：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 重置归还状态
    @RequiresPermissions("passportDraw:edit")
    @RequestMapping(value = "/reset_passportDraw_return", method = RequestMethod.POST)
    @ResponseBody
    public Map do_reset_passportDraw_return(@CurrentUser SysUserView loginUser, Integer id) throws IOException {

        passportDrawService.resetReturnPassport(id);

        logger.info(addLog(LogConstants.LOG_ABROAD, "重置归还证件的归还状态为未归还：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("passportDraw:list")
    @RequestMapping("/passportDraw_view")
    public String passportDraw_view( Integer id, ModelMap modelMap) {

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        CadreView cadre = cadreViewMapper.selectByPrimaryKey(passportDraw.getCadreId());
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("passportDraw", passportDraw);


        if(passportDraw.getType()==AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF){
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(passportDraw.getApplyId());
            modelMap.put("applySelf", applySelf);
        }

        return "abroad/user/passportDraw/passportDraw_view";
    }

    /*@RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportDraw_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            passportDrawService.del(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除领取证件：%s", id));
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
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除领取证件申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("passportDraw:del")
    @RequestMapping(value = "/passportDraw_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            passportDrawService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量找回领取证件申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

}
