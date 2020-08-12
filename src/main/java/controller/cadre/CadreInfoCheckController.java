package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.sys.SysUserInfo;
import domain.sys.SysUserInfoExample;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreInfoCheckController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInfo:check")
    @RequestMapping("/cadreInfoCheck_table")
    public String cadreInfoCheck_table(int cadreId, ModelMap modelMap) {

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        modelMap.put("sysUser", cadre.getUser());

        return "cadre/cadreInfoCheck/cadreInfoCheck_table";
    }

    // 批量更新“无此类情况”为“是”
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadreInfoCheck_batchUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfoCheck_batchUpdate(@RequestParam(required = false, value = "ids" ) Integer[] cadreIds,
                                             byte status, HttpServletRequest request) {

        CadreExample example = new CadreExample();
        CadreExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        if(cadreIds!=null && cadreIds.length>0){
            criteria.andIdIn(Arrays.asList(cadreIds));
        }
        List<Cadre> cadres = cadreMapper.selectByExample(example);

        for (Cadre cadre : cadres) {
            cadreInfoCheckService.batchUpdateInfoCheck(cadre.getId());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInfo:check")
    @RequestMapping(value = "/cadreInfoCheck_update", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfoCheck_update(int cadreId, String name, boolean isChecked, HttpServletRequest request) {

        if(StringUtils.equals(name, "avatar")){

            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            SysUserInfo record = new SysUserInfo();
            record.setAvatarUploadTime(new Date());
            SysUserInfoExample example = new SysUserInfoExample();
            example.createCriteria().andUserIdEqualTo(cadre.getUserId()).andAvatarIsNotNull();
            sysUserInfoMapper.updateByExampleSelective(record, example);

        }else {
            if (isChecked && !cadreInfoCheckService.canUpdateInfoCheck(cadreId, name)) {

                return failed("存在相关数据，无法进行此操作");
            }
            cadreInfoCheckService.update(cadreId, name, isChecked);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加/更新干部信息检查：%s, %s, %s", cadreId, name, isChecked));
        }
        return success(FormUtils.SUCCESS);
    }
}
