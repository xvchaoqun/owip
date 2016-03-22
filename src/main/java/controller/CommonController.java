package controller;

import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/11/27.
 */
@Controller
public class CommonController extends BaseController{

    // 根据账号或姓名或学工号选择用户
    @RequestMapping("/sysUser_selects")
    @ResponseBody
    public Map sysUser_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysUserExample example = new SysUserExample();
        example.setOrderByClause("id desc");
        if(StringUtils.isNotBlank(searchStr)){
            example.or().andUsernameLike("%" + searchStr + "%");
            example.or().andCodeLike("%" + searchStr + "%");
            example.or().andRealnameLike("%" + searchStr + "%");
        }

        int count = sysUserMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SysUser> sysUsers = sysUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != sysUsers && sysUsers.size()>0){
            for(SysUser sysUser:sysUsers){
                Map<String, Object> option = new HashMap<>();
                option.put("id", sysUser.getId() + "");
                option.put("text", sysUser.getRealname());
                option.put("user", userBeanService.get(sysUser.getId()));

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("code", extJzg.getZgh());
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("code", extBks.getXh());
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
                        if (extYjs != null) {
                            option.put("code", extYjs.getXh());
                            option.put("unit", extYjs.getYxsmc());
                        }
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    // 根据账号或姓名或学工号选择干部
    @RequestMapping("/cadre_selects")
    @ResponseBody
    public Map cadre_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        int count = commonMapper.countCadre(searchStr);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Cadre> cadres = commonMapper.selectCadreList(searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if(null != cadres && cadres.size()>0){

            for(Cadre cadre:cadres){
                Map<String, String> option = new HashMap<>();
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                option.put("id", cadre.getId() + "");
                option.put("text", sysUser.getRealname());

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                    if(extJzg!=null) {
                        option.put("code", extJzg.getZgh());
                        option.put("unit", extJzg.getDwmc());
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    // 根据账号或姓名或学工号选择非干部用户
    @RequestMapping("/notCadre_selects")
    @ResponseBody
    public Map notCadre_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        int count = commonMapper.countNotCadre(searchStr);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SysUser> sysUsers = commonMapper.selectNotCadreList(searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if(null != sysUsers && sysUsers.size()>0){

            for(SysUser sysUser:sysUsers){
                Map<String, String> option = new HashMap<>();
                option.put("id", sysUser.getId() + "");
                option.put("text", sysUser.getRealname());

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("code", extJzg.getZgh());
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("code", extBks.getXh());
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
                        if (extYjs != null) {
                            option.put("code", extYjs.getXh());
                            option.put("unit", extYjs.getYxsmc());
                        }
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    // 根据账号或姓名或学工号选择 所在单位和兼职单位 都关联该单位的干部
    @RequestMapping("/unitCadre_selects")
    @ResponseBody
    public Map unitCadre_selects(Integer pageSize, Integer pageNo, int unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        int count = commonMapper.countCadreByUnitId(searchStr, unitId);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Cadre> cadres = commonMapper.selectCadreByUnitIdList(searchStr, unitId, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if(null != cadres && cadres.size()>0){

            for(Cadre cadre:cadres){
                Map<String, String> option = new HashMap<>();
                option.put("id", cadre.getId() + "");
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                option.put("text", sysUser.getRealname());

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("code", extJzg.getZgh());
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("code", extBks.getXh());
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
                        if (extYjs != null) {
                            option.put("code", extYjs.getXh());
                            option.put("unit", extYjs.getYxsmc());
                        }
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    // 根据类别、状态、账号或姓名或学工号 查询 党员
    @RequestMapping("/member_selects")
    @ResponseBody
    public Map member_selects(Integer pageSize, Byte type, Byte status, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        Subject subject = SecurityUtils.getSubject();
        boolean addPermits = !(subject.hasRole(SystemConstants.ROLE_ADMIN)
                || subject.hasRole(SystemConstants.ROLE_ODADMIN));
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = commonMapper.countMember(type, status, searchStr, addPermits, adminPartyIdList, adminBranchIdList);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Member> members = commonMapper.selectMemberList(type, status, searchStr,
                addPermits, adminPartyIdList, adminBranchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != members && members.size()>0){

            for(Member member:members){
                Map<String, Object> option = new HashMap<>();
                SysUser sysUser = sysUserService.findById(member.getUserId());
                option.put("id", member.getUserId() + "");
                option.put("text", sysUser.getRealname());
                option.put("user", userBeanService.get(member.getUserId()));

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("code", extJzg.getZgh());
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("code", extBks.getXh());
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
                        if (extYjs != null) {
                            option.put("code", extYjs.getXh());
                            option.put("unit", extYjs.getYxsmc());
                        }
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

}
