package controller.modify.user;

import controller.modify.ModifyBaseController;
import domain.cadre.CadreView;
import domain.modify.ModifyBaseApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.security.Base64Utils;
import sys.shiro.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
@RequestMapping("/user")
public class UserModifyBaseApplyController extends ModifyBaseController {

    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping("/modifyBaseApply_au")
    public String modifyBaseApply_au(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        modelMap.put("uv", loginUser);

        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);

        ModifyBaseApply mba = modifyBaseApplyService.get(userId);
        if(mba!=null) {
            modelMap.put("mba", mba);
            modelMap.put("mbis", modifyBaseItemService.list(mba.getId()));
        }

        if(cadre!=null) {
            // 是否已认定了参加工作时间，没认定前可修改
            modelMap.put("hasVerifyWorkTime", cadre.getVerifyWorkTime()!=null);
        }

        return "modify/user/modifyBaseApply/modifyBaseApply_au";
    }

    // 提交申请
    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyBaseApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyBaseApply_au(@CurrentUser SysUserView loginUser, MultipartFile _avatar,
                      @RequestParam(required = false, value = "codes[]")String[] codes,  // 数据库字段代码
                      @RequestParam(required = false, value = "tables[]")String[] tables, // 数据库表名
                      @RequestParam(required = false, value = "tableIdNames[]")String[] tableIdNames, // 数据库表主键名
                      @RequestParam(required = false, value = "names[]")String[] names,  // 字段中文名
                      @RequestParam(required = false, value = "originals[]")String[] originals, // 原来的值
                      @RequestParam(required = false, value = "modifys[]")String[] modifys, // 更改的值
                      @RequestParam(required = false, value = "types[]")Byte[] types, // 更改的值类型，表名不为空时有效
                      HttpServletRequest request) throws Exception {

        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)
                || ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMINSELF)){
            return failed("您有直接修改[干部基本信息-干部信息]的权限，请勿在此提交申请。");
        }

        ModifyBaseApply mba = modifyBaseApplyService.get(loginUser.getId());
        if(mba!=null) {
            return failed("您已经提交了申请，请等待审核完成。");
        }
        if(codes==null || codes.length==0
                || originals==null || originals.length==0
                || modifys==null || modifys.length==0
                || originals.length!= codes.length
                || originals.length!= modifys.length){
            return failed("数据异常，请重试。");
        }

        for (int i = 0; i < originals.length; i++) {
            originals[i] = new String(Base64Utils.decode(originals[i]), "utf-8");
        }
        for (int i = 0; i < modifys.length; i++) {
            modifys[i] = new String(Base64Utils.decode(modifys[i]), "utf-8");
        }
        try {
            modifyBaseApplyService.apply(_avatar, codes, tables, tableIdNames, names, originals, modifys, types);
        }catch (Exception ex){
            return failed(ex.getMessage());
        }

        return success();
    }

    // 撤销申请
    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyBaseApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map back(@RequestParam(required = false, value = "ids[]")Integer[] ids,
                      HttpServletRequest request){

        try {
            modifyBaseApplyService.back(ids);
        }catch (Exception ex){
            return failed(ex.getMessage());
        }

        return success();
    }
}
