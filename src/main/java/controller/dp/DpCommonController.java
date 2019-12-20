package controller.dp;

import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.utils.FormUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dp")
public class DpCommonController extends DpBaseController {

    //不是党派成员的非学生人员
    @RequestMapping("/notDpMember_selects")
    @ResponseBody
    public Map notMember_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);

        int count = iDpMemberMapper.countNotDpMemberList(searchStr, sysUserService.buildRoleIds(RoleConstants.ROLE_REG));
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iDpMemberMapper.selectNotDpMemberList(searchStr, sysUserService.buildRoleIds(RoleConstants.ROLE_REG), new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != uvs && uvs.size() > 0) {

            for (SysUserView uv : uvs) {
                Map<String, String> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    option.put("unit", extService.getUnit(uv.getId()));
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //教职工
    @RequestMapping("/teacher_select")
    @ResponseBody
    public Map sysUser_selects(@RequestParam(required = false, value ="[1]") Byte[] types,
                               @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                               Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        searchStr = StringUtils.trimToNull(searchStr);
        long count = iDpMemberMapper.countUserList(searchStr);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iDpMemberMapper.selectUserList(searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));

        boolean isAdmin = ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_CADREADMIN, RoleConstants.ROLE_CET_ADMIN, RoleConstants.ROLE_PARTYADMIN);

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (SysUserView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());
                option.put("del", uv.getLocked());
                option.put("username", uv.getUsername());
                option.put("code", uv.getCode());
                option.put("locked", uv.getLocked());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("birth", uv.getBirth());
                option.put("nation", uv.getNation());

                if(isAdmin) {
                    option.put("mobile", uv.getMobile());
                    option.put("msgMobile", uv.getMsgMobile());
                }

                //option.put("user", userBeanService.get(uv.getId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("unit", extService.getUnit(uv.getId()));
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //同步统战中对应干部的档案表信息
    @RequestMapping("/dpSyncCadreInfo")
    @ResponseBody
    public Map do_dpSyncCadreInfo(Integer cls){

        if (cls != null){
            dpCommonService.syncCadreInfo(cls);
        }

        return success(FormUtils.SUCCESS);
    }
}
