package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.member.Member;
import domain.sys.SysUserInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class CadreBaseInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadreModifyHelp")
    public String cadreModifyHelp(){

        return "cadre/cadreModifyHelp";
    }

    // 修改干部基本信息
    @RequiresPermissions("cadreBaseInfo:edit")
    @RequestMapping(value = "/cadreBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreBaseInfo(int cadreId,
                                  MultipartFile _avatar,
                                  Integer dpTypeId,
                                  String _dpAddTime,
                                  String nativePlace,
                                  String homeplace,
                                  String household,
                                  String health,
                                  String specialty,
                                  String mobile,
                                  String phone,
                                  String email,
                                  HttpServletRequest request) throws IOException {

        Cadre cadre = cadreService.findAll().get(cadreId);
        Member member = memberService.get(cadre.getUserId());
        if(BooleanUtils.isFalse(cadre.getIsDp()) && member==null) {
            if(dpTypeId!=null && _dpAddTime!=null)
                cadreService.addDemocraticParty(cadreId, dpTypeId, _dpAddTime, null, "干部本人添加");
        }

        if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            throw new RuntimeException("手机号码有误："+ mobile);
        }

        String avatar = avatarService.uploadAvatar(_avatar);
        SysUserInfo record = new SysUserInfo();
        record.setAvatar(avatar);
        record.setUserId(cadre.getUserId());
        record.setNativePlace(nativePlace);
        record.setHomeplace(homeplace);
        record.setHousehold(household);
        record.setHealth(health);
        record.setSpecialty(specialty);
        record.setMobile(mobile);
        record.setPhone(phone);
        record.setEmail(email);
        sysUserService.insertOrUpdateUserInfoSelective(record);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部基本信息：%s ", cadreId));
        return success(FormUtils.SUCCESS);
    }
}
