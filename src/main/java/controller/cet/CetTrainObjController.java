package controller.cet;

import domain.cet.*;
import domain.cet.CetTrainObjExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainObj:list")
    @RequestMapping("/cetTrainObj")
    public String cetTrainObj(Integer userId, ModelMap modelMap) {

        if(userId!=null)
            modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetTrainObj/cetTrainObj_page";
    }

    @RequiresPermissions("cetTrainObj:list")
    @RequestMapping("/cetTrainObj_data")
    public void cetTrainObj_data(HttpServletResponse response,
                                    Integer trainCourseId,
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

        CetTrainObjExample example = new CetTrainObjExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (trainCourseId!=null) {
            criteria.andTrainCourseIdEqualTo(trainCourseId);
        }

        long count = cetTrainObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainObj> records= cetTrainObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainObj.class, cetTrainObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainObj:edit")
    @RequestMapping(value = "/cetTrainObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainObj_au(CetTrainObj record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTrainObjService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加参训情况：%s", record.getId()));
        } else {

            cetTrainObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新参训情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainObj:edit")
    @RequestMapping("/cetTrainObj_au")
    public String cetTrainObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainObj cetTrainObj = cetTrainObjMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainObj", cetTrainObj);
        }
        return "cet/cetTrainObj/cetTrainObj_au";
    }

    @RequiresPermissions("cetTrainObj:del")
    @RequestMapping(value = "/cetTrainObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTrainObjService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除参训情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainObj:del")
    @RequestMapping(value = "/cetTrainObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainObj_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainObjService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除参训情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 刷卡签到页面
    @RequestMapping("/sign")
    public String sign(int id, String token, ModelMap modelMap) {

        boolean isPermitted = false;
        if (StringUtils.isBlank(token)) {

            if(ShiroHelper.getCurrentUserId()==null){
                return "redirect:/login";
            }
            isPermitted = ShiroHelper.isPermitted("cetTrainObj:sign");
        }else{
            String signToken = cetTrainCourseService.getSignToken(id);

            if(signToken!=null){

                String _token =  signToken.split("_")[0];
                long _expire =  Long.valueOf(signToken.split("_")[1]);

                boolean tokenIsValid = StringUtils.equals(token, _token);
                boolean tokenIsExpired = System.currentTimeMillis() >= _expire;
                isPermitted = (tokenIsValid && !tokenIsExpired);
            }
        }

        if(isPermitted) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            // 已刷卡签到的学员
            CetTrainObjViewExample example = new CetTrainObjViewExample();
            example.createCriteria().andTrainCourseIdEqualTo(id)
                    .andIsFinishedEqualTo(true)
                    .andSignTypeEqualTo(CetConstants.CET_TRAINEE_SIGN_TYPE_CARD);
            example.setOrderByClause("sign_time desc");
            List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper.selectByExample(example);
            modelMap.put("cetTrainObjViews", cetTrainObjViews);
        }

        return "cet/cetTrainObj/cetTrainObj_sign_page";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sign(int id, String token, String code, ModelMap modelMap) {

        if (StringUtils.isBlank(token)) {

            if(ShiroHelper.getCurrentUserId()==null){
                return failed("没有登录。");
            }
            ShiroHelper.checkPermission("cetTrainObj:sign");

        }else{
            String signToken = cetTrainCourseService.getSignToken(id);

            if(signToken!=null){

                String _token =  signToken.split("_")[0];
                long _expire =  Long.valueOf(signToken.split("_")[1]);

                if(!StringUtils.equals(token, _token) || System.currentTimeMillis() >= _expire){

                    return failed("签到地址已过期，请重新生成。");
                }
            }
        }

        SysUserView uv = CmTag.getUserByCode(code);
        if(uv==null){
            return failed("学工号"+ code +"不存在。");
        }

        CetTrainObjView cetTrainObjView = cetTrainObjService.get(uv.getId(), id);
        if(cetTrainObjView==null){
            return failed(String.format("参训学员（工号：%s）不存在。", code));
        }

        Date signTime = null;
        if(BooleanUtils.isTrue(cetTrainObjView.getIsFinished())){
            // 以第一次签到时间为准
            signTime = cetTrainObjView.getSignTime();
        }else {
            signTime = new Date();
            cetTrainObjService.sign(id, new Integer[]{cetTrainObjView.getId()}, true,
                    CetConstants.CET_TRAINEE_SIGN_TYPE_CARD, signTime);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("realname", uv.getRealname());
        resultMap.put("signTime", DateUtils.formatDate(signTime, DateUtils.YYYY_MM_DD_HH_MM_SS));

        return resultMap;
    }

    // 更新读卡签到token及有效期
    @RequiresPermissions("cetTrainObj:sign")
    @RequestMapping(value = "/updateSignToken", method = RequestMethod.POST)
    @ResponseBody
    public Map updateSignToken(Integer trainCourseId,
                               @RequestParam(required = false, defaultValue = "2")Integer expireInHours) {

        String signToken = ContentUtils.getUUID();
        long signTokenExpire = System.currentTimeMillis() + expireInHours*60*60*1000;
        cetTrainCourseService.updateSignToken(trainCourseId, signToken,
                signTokenExpire);

        logger.info(addLog(LogConstants.LOG_CET, "更新签到token，%s", trainCourseId));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("signToken", signToken);
        resultMap.put("signTokenExpire", signTokenExpire);

        return resultMap;
    }


    // 手动签到
    @RequiresPermissions("cetTrainObj:sign")
    @RequestMapping(value = "/cetTrainObj_sign", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainObj_sign(Integer trainCourseId, Integer[] ids,
                                     Boolean sign) {

        Date signTime = new Date();
        cetTrainObjService.sign(trainCourseId, ids, BooleanUtils.isTrue(sign),
                CetConstants.CET_TRAINEE_SIGN_TYPE_MANUAL, signTime);

        logger.info(addLog(LogConstants.LOG_CET,
                (BooleanUtils.isTrue(sign)?"签到":"还原") + "：%s",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 签到，批量导入
    @RequiresPermissions("cetTrainObj:sign")
    @RequestMapping(value = "/cetTrainObj_sign_import", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainObj_sign_import(MultipartFile xlsx, int trainCourseId) throws IOException, InvalidFormatException {

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> retMap = cetTrainObjService.signImport(trainCourseId, xlsRows);

        int totalCount = xlsRows.size();
        int successCount = (int) retMap.get("success");
        List<Map<Integer, String>> failedXlsRows = (List<Map<Integer, String>>)retMap.get("failedXlsRows");

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("failedXlsRows", failedXlsRows);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入培训签到结果成功，总共{0}条记录，其中成功导入{1}条记录，{2}条失败",
                totalCount, successCount, failedXlsRows.size()));

        return resultMap;
    }

    // 二维码签到签退
    @RequestMapping("/codeSign")
    public String codeSign(int id,int cls, String token, ModelMap modelMap) {

        boolean isPermitted = false;
        if (StringUtils.isBlank(token)) {

            if(ShiroHelper.getCurrentUserId()==null){
                return "redirect:/login";
            }
            isPermitted = ShiroHelper.isPermitted("cetTrainObj:sign");
        }else{
            String signToken = cetTrainCourseService.getSignToken(id);

            if(signToken!=null){

                String _token =  signToken.split("_")[0];
                long _expire =  Long.valueOf(signToken.split("_")[1]);

                boolean tokenIsValid = StringUtils.equals(token, _token);
                boolean tokenIsExpired = System.currentTimeMillis() >= _expire;
                isPermitted = (tokenIsValid && !tokenIsExpired);
            }
        }

        if(isPermitted) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            // 已签到的学员
            CetTrainObjViewExample example = new CetTrainObjViewExample();
            example.createCriteria().andTrainCourseIdEqualTo(id)
                    .andIsFinishedEqualTo(true)
                    .andSignTypeEqualTo(CetConstants.CET_TRAINEE_SIGN_TYPE_CARD);
            example.setOrderByClause("sign_time desc");
            List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper.selectByExample(example);
            modelMap.put("cetTrainObjViews", cetTrainObjViews);
        }

        modelMap.put("cls", cls);

        return "cet/cetCodeSign/cetCode_sign_page";
    }

    //二维码签到签退（单人）
    /*@RequestMapping(value = "/codeSign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_codeSign(String codeSignIn, String codeSignOut, String trainCourse, ModelMap modelMap) {

        Date signTime = null;

        String code = cetCodeWxSignService.signInOrOut(codeSignIn, codeSignOut);
        SysUserView uv = CmTag.getUserByCode(code);
        if(uv==null){
            return failed("学工号"+ code + "不存在。");
        }
        CetTrainObjView cetTrainObjView = cetTrainObjService.getCetTrainObjView(uv.getId(), Integer.parseInt(trainCourse));
        if(cetTrainObjView == null){
            return failed("您不是本次课程的学员。");
        }

        if (codeSignIn != null && codeSignOut == null) {
            if (cetTrainObjView.getSignTime() != null) {
                // 以第一次签到时间为准
                signTime = cetTrainObjView.getSignTime();
            } else {
                signTime = new Date();
                cetCodeWxSignService.sign(cetTrainObjView, false,
                        CetConstants.CET_TRAINEE_SIGN_TYPE_QRCODE, signTime);
            }
        }else if (codeSignIn == null && codeSignOut != null){
            if (cetTrainObjView.getSignTime() == null){
                return failed(String.format("参训学员（工号：%s）签退前未进行签到。", code));
            }
            if (cetTrainObjView.getSignOutTime() != null) {
                // 以第一次签到时间为准
                signTime = cetTrainObjView.getSignOutTime();
            } else {
                signTime = new Date();
                cetCodeWxSignService.sign(cetTrainObjView, true,
                        CetConstants.CET_TRAINEE_SIGN_TYPE_QRCODE, signTime);
            }
        }
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("uv", uv);
        resultMap.put("signTime", DateUtils.formatDate(signTime, DateUtils.YYYY_MM_DD_HH_MM_SS));

        return resultMap;
    }*/
}
