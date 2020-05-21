package controller.sys;

import controller.BaseController;
import domain.sys.SysConfig;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import java.io.IOException;
import java.util.Map;

@Controller
public class SysConfigController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfig")
    public String sysConfig(@RequestParam(required = false, defaultValue = "1")Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==2){
            return "forward:/sysProperty";
        }

        modelMap.put("sysConfig", CmTag.getSysConfig());

        return "sys/sysConfig/sysConfig_page";
    }

    @RequiresPermissions("sysConfig:edit")
    @RequestMapping(value = "/sysConfig_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfig_au(SysConfig record,
                               MultipartFile _favicon,
                               MultipartFile _appleIcon,
                               MultipartFile _screenIcon,
                               MultipartFile _logo,
                               MultipartFile _logoWhite,
                               MultipartFile _loginTop,
                               MultipartFile _loginBg,
                               MultipartFile _drLoginBg,
                               MultipartFile _qrLogo
                                ) throws IOException, InterruptedException {

        String folder = "sysConfig";
        String logo = uploadPic(_logo, folder, 100, 50);
        String logoWhite = uploadPic(_logoWhite, folder, 100, 50);
        String loginTop = uploadPic(_loginTop, folder, 500, 50);
        String loginBg = uploadPic(_loginBg, folder, 400, 200);
        String drLoginBg = uploadPic(_drLoginBg, folder, 400, 200);
        record.setLogo(logo);
        record.setLogoWhite(logoWhite);
        record.setLoginTop(loginTop);
        record.setLoginBg(loginBg);
        record.setDrLoginBg(drLoginBg);

        record.setFavicon(upload(_favicon, folder));
        record.setAppleIcon(upload(_appleIcon, folder));
        record.setScreenIcon(upload(_screenIcon, folder));
        record.setQrLogo(upload(_qrLogo, folder));

        record.setDisplayLoginMsg(BooleanUtils.isTrue(record.getDisplayLoginMsg()));

        sysConfigService.insertOrUpdate(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新系统配置：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }
}
