package controller.global;

import bean.UserBean;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BaseController;
import domain.abroad.ApproverType;
import domain.base.Location;
import domain.cadre.Cadre;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.dispatch.DispatchType;
import domain.ext.ExtBks;
import domain.ext.ExtJzg;
import domain.ext.ExtYjs;
import domain.member.Member;
import domain.member.MemberInflow;
import domain.party.Branch;
import domain.party.Party;
import domain.base.MetaType;
import domain.sys.SysRole;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import domain.train.TrainEvaTable;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import mixin.PartyOptionMixin;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by fafa on 2015/11/27.
 */
@Controller
public class CommonController extends BaseController {

/*    public static void main(String[] args) throws IllegalAccessException {

        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if(StringUtils.equals(field.getType().getName(), "java.util.Map")){

                System.out.println(field.getName() + ":"+ field.get(null));
            }
        }
    }*/

    // 根据账号或姓名或学工号选择用户
    @RequestMapping("/sysUser_selects")
    @ResponseBody
    public Map sysUser_selects(@RequestParam(required = false, value = "type") Byte[] type, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysUserViewExample example = new SysUserViewExample();
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(searchStr)) {
            SysUserViewExample.Criteria criteria = example.or().andUsernameLike("%" + searchStr + "%");
            SysUserViewExample.Criteria criteria1 = example.or().andCodeLike("%" + searchStr + "%");
            SysUserViewExample.Criteria criteria2 = example.or().andRealnameLike("%" + searchStr + "%");
            if (type != null && type.length > 0) {
                criteria.andTypeIn(Arrays.asList(type));
                criteria1.andTypeIn(Arrays.asList(type));
                criteria2.andTypeIn(Arrays.asList(type));
            }
        } else if (type != null && type.length > 0) {
            example.createCriteria().andTypeIn(Arrays.asList(type));
        }

        int count = sysUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (SysUserView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(uv.getId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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

    // 根据账号或姓名或学工号选择干部（现任干部、离任干部）
    @RequestMapping("/cadre_selects")
    @ResponseBody
    public Map cadre_selects(
            // type=0 所有干部（包括后备干部、考察对象）  type=1 干部库 type=2 现任干部库  type=3 离任干部库
            @RequestParam(defaultValue = "1", required = false) Byte type,
            Byte status,
            Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        Set<Byte> cadreStatusSet = new HashSet<>();
        if (status != null) {
            cadreStatusSet.add(status);
        } else {
            if (type == 1) {
                cadreStatusSet = SystemConstants.CADRE_STATUS_SET;
            } else if (type == 2) {
                cadreStatusSet = SystemConstants.CADRE_STATUS_NOW_SET;
            } else if (type == 3) {
                cadreStatusSet = SystemConstants.CADRE_STATUS_LEAVE_SET;
            }
        }

        int count = commonMapper.countCadre(searchStr, cadreStatusSet);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Cadre> cadres = commonMapper.selectCadreList(searchStr, cadreStatusSet, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != cadres && cadres.size() > 0) {

            for (Cadre cadre : cadres) {
                Map<String, String> option = new HashMap<>();
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                option.put("id", cadre.getId() + "");
                option.put("text", sysUser.getRealname());
                if (StringUtils.isNotBlank(sysUser.getCode())) {
                    option.put("code", sysUser.getCode());
                    ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                    if (extJzg != null) {
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
    public Map notCadre_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        int count = commonMapper.countNotCadre(searchStr, SystemConstants.CADRE_STATUS_SET,
                sysUserService.buildRoleIds(SystemConstants.ROLE_REG));
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = commonMapper.selectNotCadreList(searchStr, SystemConstants.CADRE_STATUS_SET,
                sysUserService.buildRoleIds(SystemConstants.ROLE_REG), new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != uvs && uvs.size() > 0) {

            for (SysUserView uv : uvs) {
                Map<String, String> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        int count = commonMapper.countCadre(searchStr, SystemConstants.CADRE_STATUS_SET);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Cadre> cadres = commonMapper.selectCadreList(searchStr, SystemConstants.CADRE_STATUS_SET, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != cadres && cadres.size() > 0) {

            for (Cadre cadre : cadres) {
                Map<String, String> option = new HashMap<>();
                option.put("id", cadre.getId() + "");
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                option.put("text", uv.getRealname());

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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


    // 根据账号或姓名或学工号选择后备干部
    @RequestMapping("/cadreReserve_selects")
    @ResponseBody
    public Map sysUser_selects(Integer pageSize, Integer pageNo,
                               Byte reserveStatus,
                               Byte reserveType, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreReserveViewExample example = new CadreReserveViewExample();
        example.setOrderByClause("reserve_sort_order desc");
        if (StringUtils.isNotBlank(searchStr)) {
            CadreReserveViewExample.Criteria criteria1 = example.or().andUsernameLike("%" + searchStr + "%");
            CadreReserveViewExample.Criteria criteria2 = example.or().andCodeLike("%" + searchStr + "%");
            CadreReserveViewExample.Criteria criteria3 = example.or().andRealnameLike("%" + searchStr + "%");
            if (reserveStatus != null) {
                criteria1.andReserveStatusEqualTo(reserveStatus);
                criteria2.andReserveStatusEqualTo(reserveStatus);
                criteria3.andReserveStatusEqualTo(reserveStatus);
            }
            if (reserveType != null) {
                criteria1.andReserveTypeEqualTo(reserveType);
                criteria2.andReserveTypeEqualTo(reserveType);
                criteria3.andReserveTypeEqualTo(reserveType);
            }
        } else {
            CadreReserveViewExample.Criteria criteria = example.createCriteria();
            if (reserveStatus != null) criteria.andReserveStatusEqualTo(reserveStatus);
            if (reserveType != null) criteria.andReserveTypeEqualTo(reserveType);
        }

        int count = cadreReserveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveView> crvs = cadreReserveViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != crvs && crvs.size() > 0) {
            for (CadreReserveView crv : crvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", crv.getId() + "");  // cadreId
                option.put("text", crv.getRealname());
                UserBean userBean = userBeanService.get(crv.getUserId());
                option.put("user", userBean);

                if (StringUtils.isNotBlank(userBean.getCode())) {
                    option.put("code", userBean.getCode());
                    if (userBean.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(userBean.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (userBean.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(userBean.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (userBean.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(userBean.getCode());
                        if (extYjs != null) {
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
    public Map member_selects(Integer pageSize, Byte type, Byte status, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        Subject subject = SecurityUtils.getSubject();
        boolean addPermits = !(subject.hasRole(SystemConstants.ROLE_ADMIN)
                || subject.hasRole(SystemConstants.ROLE_ODADMIN));
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = commonMapper.countMember(type, status, searchStr, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Member> members = commonMapper.selectMemberList(type, status, searchStr,
                addPermits, adminPartyIdList, adminBranchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != members && members.size() > 0) {

            for (Member member : members) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(member.getUserId());
                option.put("id", member.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(member.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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

    // 根据类别、状态、账号或姓名或学工号 查询 流入党员
    @RequestMapping("/memberInflow_selects")
    @ResponseBody
    public Map memberInflow_selects(Integer pageSize, Byte type, Byte status, Boolean hasOutApply, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        Subject subject = SecurityUtils.getSubject();
        boolean addPermits = !(subject.hasRole(SystemConstants.ROLE_ADMIN)
                || subject.hasRole(SystemConstants.ROLE_ODADMIN));
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = commonMapper.countMemberInflow(type, status, hasOutApply, searchStr, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInflow> memberInflows = commonMapper.selectMemberInflowList(type, status, hasOutApply, searchStr,
                addPermits, adminPartyIdList, adminBranchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != memberInflows && memberInflows.size() > 0) {

            for (MemberInflow m : memberInflows) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(m.getUserId());
                option.put("id", m.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(m.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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

    // 根据账号或姓名或学工号选择非党员用户
    @RequestMapping("/notMember_selects")
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
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        int count = commonMapper.countNotMember(searchStr, sysUserService.buildRoleIds(SystemConstants.ROLE_REG));
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = commonMapper.selectNotMemberList(searchStr, sysUserService.buildRoleIds(SystemConstants.ROLE_REG), new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != uvs && uvs.size() > 0) {

            for (SysUserView uv : uvs) {
                Map<String, String> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(uv.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if (uv.getType() == SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(uv.getCode());
                        if (extYjs != null) {
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
