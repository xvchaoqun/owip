package controller.modify.user;

import controller.modify.ModifyBaseController;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseItem;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.FileUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
@RequestMapping("/user")
public class UserModifyBaseItemController extends ModifyBaseController{

    // 干部本人更新某个字段的值（待审核状态）
    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping("/modifyBaseItem_au")
    public String modifyBaseItem_au(int id, ModelMap modelMap) {

        modelMap.put("record", modifyBaseItemMapper.selectByPrimaryKey(id));
        return "modify/user/modifyBaseItem/modifyBaseItem_au";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyBaseItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyBaseItem_au(@CurrentUser SysUserView loginUser,
                                    int id, String modifyValue, MultipartFile _avatar) throws IOException {

        ModifyBaseItem record = modifyBaseItemMapper.selectByPrimaryKey(id);
        if(record==null) return failed("字段不存在");
        if(record.getStatus()!= ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY) return failed("该申请已审核，不允许再次变更");
        Integer applyId = record.getApplyId();
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(applyId);
        if(mba.getUserId().intValue()!=loginUser.getId()) return failed("您没有权限修改该字段");

        if(_avatar!=null && !_avatar.isEmpty()) {
            FileUtils.delFile(springProps.avatarFolder + record.getModifyValue()); // 把之前上传的头像删除
            modifyValue = avatarService.uploadAvatar(_avatar);
        }

        try {
            modifyBaseItemService.update(id, modifyValue);
        }catch (Exception ex){
            return failed(ex.getMessage());
        }

        return success();
    }

    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyBaseItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyBaseItem_del(@CurrentUser SysUserView loginUser, int id, String modifyValue){

        ModifyBaseItem record = modifyBaseItemMapper.selectByPrimaryKey(id);
        if(record==null) return failed("字段不存在");
        if(record.getStatus()!= ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY) return failed("该申请已审核，不允许再次变更");
        Integer applyId = record.getApplyId();
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(applyId);
        if(mba.getUserId().intValue()!=loginUser.getId()) return failed("您没有权限修改该字段");

        try {
            modifyBaseItemService.del(id);
        }catch (Exception ex){
            return failed(ex.getMessage());
        }

        return success();
    }
}
