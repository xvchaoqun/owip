package controller.cet.mobile.eva;

import controller.cet.CetBaseController;
import domain.cet.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.helper.CetHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/m/cet_eva")
public class MobileCetEvaLoginController extends CetBaseController {


    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(String u, String p, // 匿名测评
                        Integer trainId, // 实名测评
                        Integer id,//用于签到
                        HttpServletRequest request, ModelMap modelMap) {

        try {
            if (StringUtils.isNotBlank(u)) {
                CetTrainInspector trainInspector = cetTrainInspectorService.tryLogin(u, p);
                if (trainInspector == null) {
                    logger.info(sysLoginLogService.trainInspectorLoginlog(null, u, false, "扫码登录失败，账号或密码错误"));
                    modelMap.put("error", "扫码登录失败，账号或密码错误");
                    return "cet/mobile/eva/login";
                }
                logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), u, true, "扫码登录成功"));
                CetHelper.setTrainInspector(request, trainInspector);
            } else if (trainId != null) {

                CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
                modelMap.put("train", train);
                return "cet/mobile/eva/login";
            }
            try {
                CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
                if (cetTrainCourse != null){
                    CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(cetTrainCourse.getTrainId());
                    modelMap.put("cetTrain", cetTrain);
                    modelMap.put("cetTrainCourse", cetTrainCourse);
                    return "cet/mobile/eva/signLogin";
                }

            }catch (Exception e){
                modelMap.put("error", e.getMessage());
                return "";
            }

        } catch (Exception ex) {

            modelMap.put("error", ex.getMessage());
            return "cet/mobile/eva/login";
        }

        CetTrainInspector trainInspector = CetHelper.getTrainInspector(request);
        if (trainInspector != null) {
            return "redirect:/m/cet_eva/index";
        }
        return "cet/mobile/eva/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_login(String username, String password,
                                        Integer trainId, String mobile, // 实名测评
                                        String code,//用于签到
                                        String wxName,//用于签到
                                        String trainCourse,//用于签到
                                        HttpServletRequest request) throws IOException {

        if (StringUtils.isBlank(wxName) && StringUtils.isNotBlank(wxName)) {
            if (StringUtils.isNotBlank(username)) {
                CetTrainInspector trainInspector = cetTrainInspectorService.tryLogin(username, password);
                if (trainInspector == null) {
                    logger.info(sysLoginLogService.trainInspectorLoginlog(null, username, false, "登录失败，账号或密码错误"));
                    return failed("账号或密码错误");
                }

                CetHelper.setTrainInspector(request, trainInspector);
                logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), username, true, "登录成功"));
            } else if (trainId != null) {
                CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
                CetTrainInspector trainInspector = cetTrainInspectorService.get(trainId, mobile);
                if (trainInspector == null) {
                    logger.info(sysLoginLogService.trainInspectorLoginlog(null, mobile, false,
                            String.format("【%s】登录失败，评课账号【%s】不存在", train.getName(), mobile)));
                    return failed("评课账号不存在");
                }
                logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), mobile, true,
                        String.format("【%s】，评课账号【%s】登录成功", train.getName(), mobile)));
                CetHelper.setTrainInspector(request, trainInspector);
            }
        }else if (StringUtils.isBlank(wxName) && StringUtils.isNotBlank(code)){
            CetCodeWxSign cetCodeWxSign = cetCodeWxSignService.getByCode(code);
            if (cetCodeWxSign == null){
                logger.info("登录失败");
                return failed("首次登陆请输入微信号");
            }
        }else if (StringUtils.isNotBlank(wxName) && StringUtils.isNotBlank(code)){
            CetTraineeCourseView cetTraineeCourseView = cetCodeWxSignService.getcetTraineeCourseView(code, trainCourse);
            if (cetTraineeCourseView == null){
                logger.info("登录失败");
                return failed("签到账号不存在或未报名该课程");
            }
            CetCodeWxSign cetCodeWxSign = cetCodeWxSignService.getByCode(code);
            if (cetCodeWxSign == null){
                cetCodeWxSignService.insert(code, wxName);
                logger.info("[%s]与学工号[code]绑定成功", wxName, code);
            }else if (!cetCodeWxSign.getWxName().equals(StringUtils.trimToNull(wxName))){
                logger.info("登录失败");
                return failed("微信号输入错误");
            }
        }

        return success("登入成功");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        CetTrainInspector trainInspector = CetHelper.trainInspector_logout(request);

        logger.debug("logout success. {}", (trainInspector != null) ? trainInspector.getUsername() : "");

        return "redirect:/m/cet_eva/index";
    }

}
