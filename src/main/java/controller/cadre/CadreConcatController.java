package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.sys.SysUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CadreConcatController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreConcat:list")
    @RequestMapping("/cadreConcat_page")
    public String cadreConcat_page(int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export, ModelMap modelMap) {

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        modelMap.put("cadre", cadre);

        return "cadre/cadreConcat/cadreConcat_page";
    }

    @RequiresPermissions("cadreConcat:edit")
    @RequestMapping(value = "/cadreConcat_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreConcat_au(int userId, String mobile, String phone, String homePhone,
                                 String msgTitle, String email, HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile)) {
            if (!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)) {
               return failed("手机号码有误");
            }
        }

        SysUserInfo record = new SysUserInfo();
        record.setUserId(userId);
        record.setMobile(mobile);
        record.setPhone(phone);
        record.setHomePhone(homePhone);
        record.setMsgTitle(msgTitle);
        record.setEmail(email);

        sysUserService.insertOrUpdateUserInfoSelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "添加/更新干部联系方式：userId=%s", userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreConcat:edit")
    @RequestMapping("/cadreConcat_au")
    public String cadreConcat_au(Integer cadreId, ModelMap modelMap) {

        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
        }
        return "cadre/cadreConcat/cadreConcat_au";
    }
}
