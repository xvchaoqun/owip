package controller.cet.mobile;

import controller.cet.CetBaseController;
import domain.cet.CetTrain;
import domain.cet.CetTrainInspector;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/m/cet")
public class MobileTrainLoginController extends CetBaseController {


    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(String u, String p, // 匿名测评
                        Integer trainId, // 实名测评
                        HttpServletRequest request, ModelMap modelMap) {

        try {
            if (StringUtils.isNotBlank(u)) {
                CetTrainInspector trainInspector = cetTrainInspectorService.tryLogin(u, p);
                if (trainInspector == null) {
                    logger.info(sysLoginLogService.trainInspectorLoginlog(null, u, false, "扫码登录失败，账号或密码错误"));
                    modelMap.put("error", "扫码登录失败，账号或密码错误");
                    return "cet/mobile/login";
                }
                logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), u, true, "扫码登录成功"));
                SessionUtils.setTrainInspector(request, trainInspector);
            } else if (trainId != null) {

                CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
                modelMap.put("train", train);
                return "cet/mobile/login";
            }

        } catch (Exception ex) {

            modelMap.put("error", ex.getMessage());
            return "cet/mobile/login";
        }

        CetTrainInspector trainInspector = SessionUtils.getTrainInspector(request);
        if (trainInspector != null) {
            return "redirect:/m/cet/index";
        }
        return "cet/mobile/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_login(String username, String password,
                                        Integer trainId, String mobile, // 实名测评
                                        HttpServletRequest request) throws IOException {

        if (StringUtils.isNotBlank(username)) {
            CetTrainInspector trainInspector = cetTrainInspectorService.tryLogin(username, password);
            if (trainInspector == null) {
                logger.info(sysLoginLogService.trainInspectorLoginlog(null, username, false, "登录失败，账号或密码错误"));
                return failed("账号或密码错误");
            }

            SessionUtils.setTrainInspector(request, trainInspector);
            logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), username, true, "登录成功"));
        }else if (trainId != null) {
            CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
            CetTrainInspector trainInspector = cetTrainInspectorService.tryLogin(trainId, mobile);
            if (trainInspector == null) {
                logger.info(sysLoginLogService.trainInspectorLoginlog(null, mobile, false,
                        String.format("【%s】登录失败，评课账号【%s】不存在", train.getName(), mobile)));
                return failed("评课账号不存在");
            }
            logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), mobile, true,
                    String.format("【%s】，评课账号【%s】登录成功", train.getName(), mobile)));
            SessionUtils.setTrainInspector(request, trainInspector);
        }

        return success("登入成功");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        CetTrainInspector trainInspector = SessionUtils.trainInspector_logout(request);

        logger.debug("logout success. {}", (trainInspector != null) ? trainInspector.getUsername() : "");

        return "redirect:/m/cet/index";
    }

}
