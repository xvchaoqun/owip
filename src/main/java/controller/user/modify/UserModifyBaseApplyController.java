package controller.user.modify;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.ext.ExtJzg;
import domain.modify.ModifyBaseApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
@RequestMapping("/user")
public class UserModifyBaseApplyController extends BaseController{

    @RequiresRoles("cadre")
    @RequestMapping("/modifyBaseApply_au")
    public String modifyBaseApply(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        modelMap.put("uv", loginUser);

        int userId = loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        modelMap.put("cadre", cadre);

        // 人事信息
        ExtJzg extJzg = extJzgService.getByCode(loginUser.getCode());
        modelMap.put("extJzg", extJzg);

        ModifyBaseApply mba = modifyBaseApplyService.get(userId);
        if(mba!=null) {
            modelMap.put("mba", mba);
            modelMap.put("mbis", modifyBaseItemService.list(mba.getId()));
        }

        return "user/modify/modifyBaseApply/modifyBaseApply_au";
    }

    // 提交申请
    @RequiresRoles("cadre")
    @RequestMapping(value = "/modifyBaseApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyBaseApply(@CurrentUser SysUserView loginUser, MultipartFile _avatar,
                      @RequestParam(required = false, value = "codes[]")String[] codes,  // 数据库字段代码
                      @RequestParam(required = false, value = "tables[]")String[] tables, // 数据库表名
                      @RequestParam(required = false, value = "tableIdNames[]")String[] tableIdNames, // 数据库表主键名
                      @RequestParam(required = false, value = "names[]")String[] names,  // 字段中文名
                      @RequestParam(required = false, value = "originals[]")String[] originals, // 原来的值
                      @RequestParam(required = false, value = "modifys[]")String[] modifys, // 更改的值
                      @RequestParam(required = false, value = "types[]")Byte[] types, // 更改的值类型，表名不为空时有效
                      HttpServletRequest request) throws IOException {

        ModifyBaseApply mba = modifyBaseApplyService.get(loginUser.getId());
        if(mba!=null) {
            return failed("您已经提交了申请，请等待审核完成。");
        }
        try {
            modifyBaseApplyService.apply(_avatar, codes, tables, tableIdNames, names, originals, modifys, types);
        }catch (Exception ex){
            return failed(ex.getMessage());
        }

        return success();
    }

    // 撤销申请
    @RequiresRoles("cadre")
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
