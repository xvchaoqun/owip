package controller.dr;

import bean.TempResult;
import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlinePostView;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sys.SysUserViewMapper;
import sys.constants.DrConstants;
import sys.constants.SystemConstants;
import sys.helper.DrHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/dr/drOnline")
@Controller
public class DrOnlineLoginController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserViewMapper sysUserViewMapper;

    @RequestMapping("/iLogin")
    public String mobileLogin(String username, String passwd,
                              HttpServletRequest request, ModelMap modelMap){

        if (StringUtils.isNotBlank(username)) {
            DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            if (inspector == null) {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                modelMap.put("error", "账号或密码错误！");
            }
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，用户已完成测评！"));
                modelMap.put("error", "该账号已测评完成！");
            }
        }

        return "dr/drOnline/mobile/login";
    }

    @RequestMapping("/login")
    public String drOnlineLogin(HttpServletRequest request, ModelMap modelMap){

        return "dr/drOnline/user/drOnlineLogin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_drOnlineLogin(String username, String passwd,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        if (StringUtils.isNotBlank(username)){

            DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            if (inspector == null){
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                return failed("账号或密码错误！");
            }
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH){
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，用户已完成测评！"));
                return failed("该账号已测评完成！");
            }
            logger.info(sysLoginLogService.log(null, username,
                    SystemConstants.LOGIN_TYPE_NET, false, "登陆成功！"));
            DrHelper.setDrInspector(request, inspector);
        }

        String successUrl=request.getContextPath() + "/dr/drOnline/drOnlineIndex";

        Map<String, Object> resultMap = success("登入成功");
        resultMap.put("url", successUrl);
        return resultMap;
    }

    @RequestMapping("/logout")
    public String logout(Byte isMobile, HttpServletRequest request) {

        DrOnlineInspector inspector =DrHelper.drInspector_logout(request);

        logger.debug("logout success. {}", (inspector != null) ? inspector.getUsername() : "");

        if (isMobile != null && isMobile == 1){
            return "redirect:/dr/drOnline/iLogin";
        }
        return "redirect:/dr/drOnline/login";
    }

    @RequestMapping("/drOnlineIndex")
    public String drIndex(Byte isMobile, ModelMap modelMap, HttpServletRequest request){

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);

        if (inspector != null) {
            modelMap.put("inspector", inspector);
            modelMap.put("drOnline", drOnlineMapper.selectByPrimaryKey(inspector.getOnlineId()));
            List<DrOnlinePostView> postViews = drOnlinePostService.getAllByOnlineId(inspector.getOnlineId());
            modelMap.put("postViews", postViews);
            Map<Integer, List<DrOnlineCandidate>> candidateMap =  drOnlineCandidateService.findAll(inspector.getOnlineId());
            modelMap.put("candidateMap", candidateMap);
            TempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
            modelMap.put("tempResult", tempResult);
            try {
                modelMap.put("sysUser", JSONUtils.toString(sysUser_selects(SystemConstants.USER_TYPE_JZG)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isMobile != null && isMobile == 1){
                return "dr/drOnline/mobile/index";
            }
        }else {
            if (isMobile != null && isMobile == 1)
                return "dr/drOnline/mobile/iLogin";

            return "dr/drOnline/user/drOnlineLogin";
        }
        return "dr/drOnline/user/drOnlineIndex";
    }

    @RequestMapping("/user/changePasswd")
    @ResponseBody
    public Map drOnline_changePasswd(String oldPasswd,
                                     String passwd,
                                     HttpServletRequest request){

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);

        if (inspector == null){
            return failed(FormUtils.ILLEGAL);
        }
        if (!StringUtils.equalsIgnoreCase(inspector.getPasswd(), oldPasswd)){
            return failed(FormUtils.WRONG);
        }

        drOnlineInspectorService.changePasswd(inspector.getId(), passwd, DrConstants.INSPECTOR_PASSWD_CHANGE_TYPE_SELF);

        return success(FormUtils.SUCCESS);
    }

    public List sysUser_selects(Byte type) throws IOException {

        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();

        if (type != null){
            criteria.andTypeEqualTo(type);
        }

        long count = sysUserViewMapper.countByExample(example);

        List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);
        List<String> options = new ArrayList<String>();
        String nameCode = null;
        for (SysUserView uv : uvs){
            nameCode = uv.getRealname() + "(" + uv.getCode() + ")";
            options.add(nameCode);
        }

        return options;
    }

}
