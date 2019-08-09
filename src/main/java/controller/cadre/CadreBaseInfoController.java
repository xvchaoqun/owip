package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreParty;
import domain.cadre.CadreView;
import domain.cadreReserve.CadreReserve;
import domain.member.Member;
import domain.sys.SysUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
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
                                  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date _dpAddTime,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMM) Date _workTime,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMM) Date _postTime, // 后备干部任职时间
                                  String nativePlace,
                                  String homeplace,
                                  String household,
                                  Integer health,
                                  String specialty,
                                  String mobile,
                                  String phone,
                                  String email,
                                    String title,
                                  HttpServletRequest request) throws IOException {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        int userId = cadre.getUserId();
        Member member = memberService.get(userId);
        if(member==null) {
            if(dpTypeId!=null && dpTypeId>=0/* && _dpAddTime!=null*/){

                CadreParty record = new CadreParty();
                record.setUserId(userId);
                if(dpTypeId>0) {
                    // 直接修改为民主党派时，先删除中共党员
                    cadrePartyService.del(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                    CadreParty cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                    if(cadreParty!=null){
                        record.setId(cadreParty.getId());
                        logger.info(addLog(LogConstants.LOG_ADMIN, "更新为民主党派：%s ", cadreId));
                    }

                    record.setType(CadreConstants.CADRE_PARTY_TYPE_DP);
                    record.setClassId(dpTypeId);

                    record.setIsFirst(true); // 默认是第一民主党派

                }else if(dpTypeId==0){
                    // 直接修改为中共党员时，先删除民主党派（删除所有的民主党派）
                    cadrePartyService.del(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                    CadreParty cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                    if(cadreParty!=null){
                        record.setId(cadreParty.getId());
                        logger.info(addLog(LogConstants.LOG_ADMIN, "更新为中共党员：%s ", cadreId));
                    }

                    record.setType(CadreConstants.CADRE_PARTY_TYPE_OW);
                }
                record.setGrowTime(_dpAddTime);
                record.setRemark(ShiroHelper.getCurrentUserId()==userId?"干部本人添加":"");

                cadrePartyService.addOrUPdateCadreParty(record);
            }else{
                // 非党员且dpTypeId不传时，删除在民主党派库中的“中共党员”记录（保留民主党派记录）
                //cadrePartyService.del(userId, CadreConstants.CADRE_PARTY_TYPE_OW); ???
            }
        }

        {
            if(_workTime!=null)
                cadreService.updateWorkTime(userId, _workTime);
        }
        if(_postTime!=null){
            CadreReserve normalRecord = cadreReserveService.getNormalRecord(cadreId);
            if(normalRecord!=null) {
                CadreReserve record = new CadreReserve();
                record.setId(normalRecord.getId());
                record.setPostTime(_postTime);
                cadreReserveMapper.updateByPrimaryKeySelective(record);
            }
        }

        {
            if(StringUtils.isNotBlank(title) /*&& ShiroHelper.hasRole(RoleConstants.ROLE_CADRERECRUIT)*/){

                cadreService.updateTitle(cadreId, title);
            }
        }

        if(!CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }

        String avatar = avatarService.uploadAvatar(_avatar);
        SysUserInfo record = new SysUserInfo();
        record.setAvatar(avatar);
        record.setAvatarUploadTime(new Date());
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

        logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部基本信息：%s ", cadreId));
        return success(FormUtils.SUCCESS);
    }
}
