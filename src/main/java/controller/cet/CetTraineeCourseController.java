package controller.cet;

import bean.XlsUpload;
import domain.cet.*;
import domain.cet.CetTraineeCourseExample.Criteria;
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
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTraineeCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTraineeCourse:list")
    @RequestMapping("/cetTraineeCourse")
    public String cetTraineeCourse(Integer userId, ModelMap modelMap) {

        if(userId!=null)
            modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetTraineeCourse/cetTraineeCourse_page";
    }

    @RequiresPermissions("cetTraineeCourse:list")
    @RequestMapping("/cetTraineeCourse_data")
    public void cetTraineeCourse_data(HttpServletResponse response,
                                    Integer traineeId,
                                    Integer trainCourseId,
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

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (traineeId!=null) {
            criteria.andTraineeIdEqualTo(traineeId);
        }
        if (trainCourseId!=null) {
            criteria.andTrainCourseIdEqualTo(trainCourseId);
        }

        long count = cetTraineeCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeCourse> records= cetTraineeCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTraineeCourse.class, cetTraineeCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTraineeCourse:edit")
    @RequestMapping(value = "/cetTraineeCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTraineeCourse_au(CetTraineeCourse record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTraineeCourseService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加参训情况：%s", record.getId()));
        } else {

            cetTraineeCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新参训情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTraineeCourse:edit")
    @RequestMapping("/cetTraineeCourse_au")
    public String cetTraineeCourse_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTraineeCourse cetTraineeCourse = cetTraineeCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTraineeCourse", cetTraineeCourse);
        }
        return "cet/cetTraineeCourse/cetTraineeCourse_au";
    }

    @RequiresPermissions("cetTraineeCourse:del")
    @RequestMapping(value = "/cetTraineeCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTraineeCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTraineeCourseService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除参训情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTraineeCourse:del")
    @RequestMapping(value = "/cetTraineeCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTraineeCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTraineeCourseService.batchDel(ids);
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
            isPermitted = ShiroHelper.isPermitted("cetTraineeCourse:sign");
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
            CetTraineeCourseViewExample example = new CetTraineeCourseViewExample();
            example.createCriteria().andTrainCourseIdEqualTo(id)
                    .andIsFinishedEqualTo(true)
                    .andSignTypeEqualTo(CetConstants.CET_TRAINEE_SIGN_TYPE_CARD);
            example.setOrderByClause("sign_time desc");
            List<CetTraineeCourseView> cetTraineeCourseViews = cetTraineeCourseViewMapper.selectByExample(example);
            modelMap.put("cetTraineeCourseViews", cetTraineeCourseViews);
        }

        return "cet/cetTraineeCourse/cetTraineeCourse_sign_page";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sign(int id, String token, String code, ModelMap modelMap) {

        if (StringUtils.isBlank(token)) {

            if(ShiroHelper.getCurrentUserId()==null){
                return failed("没有登录。");
            }
            SecurityUtils.getSubject().checkPermission("cetTraineeCourse:sign");

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

        CetTraineeCourseView cetTraineeCourseView = cetTraineeCourseService.getCetTraineeCourseView(uv.getId(), id);
        if(cetTraineeCourseView==null){
            return failed(String.format("参训学员（工号：%s）不存在。", code));
        }

        Date signTime = null;
        if(BooleanUtils.isTrue(cetTraineeCourseView.getIsFinished())){
            // 以第一次签到时间为准
            signTime = cetTraineeCourseView.getSignTime();
        }else {
            signTime = new Date();
            cetTraineeCourseService.sign(id, new Integer[]{cetTraineeCourseView.getId()}, true,
                    CetConstants.CET_TRAINEE_SIGN_TYPE_CARD, signTime);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("realname", uv.getRealname());
        resultMap.put("signTime", DateUtils.formatDate(signTime, DateUtils.YYYY_MM_DD_HH_MM_SS));

        return resultMap;
    }

    // 更新读卡签到token及有效期
    @RequiresPermissions("cetTraineeCourse:sign")
    @RequestMapping(value = "/updateSignToken", method = RequestMethod.POST)
    @ResponseBody
    public Map updateSignToken(Integer trainCourseId,
                               @RequestParam(required = false, defaultValue = "2")Integer expireInHours) {

        String signToken = StringUtil.getUUID();
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
    @RequiresPermissions("cetTraineeCourse:sign")
    @RequestMapping(value = "/cetTraineeCourse_sign", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTraineeCourse_sign(Integer trainCourseId, @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                     Boolean sign) {

        Date signTime = new Date();
        cetTraineeCourseService.sign(trainCourseId, ids, BooleanUtils.isTrue(sign),
                CetConstants.CET_TRAINEE_SIGN_TYPE_MANUAL, signTime);

        logger.info(addLog(LogConstants.LOG_CET,
                (BooleanUtils.isTrue(sign)?"签到":"还原") + "：%s",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 签到，批量导入
    @RequiresPermissions("cetTraineeCourse:sign")
    @RequestMapping(value = "/cetTraineeCourse_sign_import", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTraineeCourse_sign_import(MultipartFile xlsx, int trainCourseId) throws IOException, InvalidFormatException {

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

        Map<String, Object> retMap = cetTraineeCourseService.signImport(trainCourseId, xlsRows);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", retMap.get("success"));
        resultMap.put("failedXlsRows", retMap.get("failedXlsRows"));
        resultMap.put("total", xlsRows.size());

        return resultMap;
    }

}
