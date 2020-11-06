package controller.global;

import controller.BaseController;
import domain.abroad.Passport;
import domain.cadre.CadreView;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.member.Member;
import domain.member.MemberInflow;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.abroad.PassportService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2015/11/27.
 */
@Controller
public class CommonController extends BaseController {

    // 根据账号或姓名或学工号选择用户
    @RequestMapping("/sysUser_selects")
    @ResponseBody
    public Map sysUser_selects(Byte[] types,
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
        long count = iSysMapper.countUserList(searchStr, types);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iSysMapper.selectUserList(searchStr, types, new RowBounds((pageNo - 1) * pageSize, pageSize));

        boolean isAdmin = ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_OA_ADMIN,
                RoleConstants.ROLE_CADREADMIN,
                RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_CET_ADMIN,
                RoleConstants.ROLE_PARTYADMIN);

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

                if(needPrivate) {
                    Member member = memberService.get(uv.getId());
                    if (member != null) {
                        option.put("politicalStatus", member.getPoliticalStatus());
                        option.put("idcard", uv.getIdcard());
                    }
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

    // 根据账号或姓名或学工号选择干部（现任干部、离任干部）
    @RequestMapping("/cadre_selects")
    @ResponseBody
    public Map cadre_selects(
            Byte[] types, // 指定多个干部类别
            // type=0 所有干部（包括优秀年轻干部、考察对象）  type=1 干部库 type=2 现任干部库  type=3 离任干部库  （优先级最低）
            @RequestParam(defaultValue = "1", required = false) Byte type,
            Byte[] status, // 特定干部类别 (优先级最高)
            Integer[] unitIds, // 所属单位
            Integer pageSize,
            // key=0，选项value=cadreId key=1 ，选项value=userId
            @RequestParam(defaultValue = "0", required = false) Byte key,
            // 是否常委
            Boolean isCommittee,
            // 出国境带出台湾证件号码
            @RequestParam(defaultValue = "0", required = false) boolean abroad,
            // 任现职时间
            @RequestParam(defaultValue = "0", required = false) boolean lpWorkTime,
            Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        Set<Byte> cadreStatusSet = new HashSet<>();
        if (status != null) {
            cadreStatusSet.addAll(new ArrayList<>(Arrays.asList(status)));
        } else {
            if (types == null) {
                if (type == 1) {
                    cadreStatusSet = CadreConstants.CADRE_STATUS_SET;
                } else if (type == 2) {
                    cadreStatusSet = CadreConstants.CADRE_STATUS_NOW_SET;
                } else if (type == 3) {
                    cadreStatusSet = CadreConstants.CADRE_STATUS_LEAVE_SET;
                }else{
                    cadreStatusSet.addAll(CadreConstants.CADRE_STATUS_SET);
                    cadreStatusSet.add(CadreConstants.CADRE_STATUS_RESERVE);
                    cadreStatusSet.add(CadreConstants.CADRE_STATUS_INSPECT);
                }
            } else {
                cadreStatusSet = new HashSet<>(Arrays.asList(types));
            }
        }

        int count = iCadreMapper.countCadreList(searchStr, cadreStatusSet, unitIds, isCommittee);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> cadres = iCadreMapper.selectCadreList(searchStr, cadreStatusSet, unitIds, isCommittee,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != cadres && cadres.size() > 0) {

            for (CadreView cadre : cadres) {
                Map<String, String> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                option.put("id", (key == 0) ? (cadre.getId() + "") : (cadre.getUserId() + ""));
                if(key == 0) {
                    option.put("userId", cadre.getUserId() + "");
                }else{
                    option.put("cadreId", cadre.getId() + "");
                }
                option.put("text", uv.getRealname());
                option.put("mobile", uv.getMobile());
                option.put("postType", NumberUtils.trimToEmpty(cadre.getPostType()));
                option.put("title", cadre.getTitle());
                option.put("status", cadre.getStatus() + "");
                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    option.put("unit", uv.getUnit());
                }
                if (abroad) {

                    PassportService passportService = ApplicationContextSupport.getContext().getBean(PassportService.class);

                    Passport twPassport = passportService.findTwPassport(cadre.getId());
                    if (twPassport != null)
                        option.put("twPassportCode", twPassport.getCode());
                }
                if(lpWorkTime){
                    CadreView cv = cadreService.get(cadre.getId());
                    option.put("lpWorkTime", DateUtils.formatDate(cv.getLpWorkTime(), DateUtils.YYYY_MM_DD));
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
        int count = iCadreMapper.countNotCadreList(searchStr, CadreConstants.CADRE_STATUS_SET,
                sysUserService.buildRoleIds(RoleConstants.ROLE_REG));
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iCadreMapper.selectNotCadreList(searchStr, CadreConstants.CADRE_STATUS_SET,
                sysUserService.buildRoleIds(RoleConstants.ROLE_REG), new RowBounds((pageNo - 1) * pageSize, pageSize));

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
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        int count = iCadreMapper.countCadreList(searchStr, CadreConstants.CADRE_STATUS_SET, null,null);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> cadres = iCadreMapper.selectCadreList(searchStr, CadreConstants.CADRE_STATUS_SET, null, null,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != cadres && cadres.size() > 0) {

            for (CadreView cadre : cadres) {
                Map<String, String> option = new HashMap<>();
                option.put("id", cadre.getId() + "");
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                option.put("text", uv.getRealname());
                option.put("mobile", uv.getMobile());

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


    // 根据账号或姓名或学工号选择优秀年轻干部
    @RequestMapping("/cadreReserve_selects")
    @ResponseBody
    public Map cadreReserve_selects(Integer pageSize, Integer pageNo,
                                    Byte reserveStatus,
                                    Integer reserveType, String searchStr) throws IOException {

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
            CadreReserveViewExample.Criteria criteria1 = example.or().andUsernameLike( searchStr.trim() + "%");
            CadreReserveViewExample.Criteria criteria2 = example.or().andCodeLike(searchStr.trim() + "%");
            CadreReserveViewExample.Criteria criteria3 = example.or().andRealnameLike( searchStr.trim() + "%");
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

        long count = cadreReserveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveView> crvs = cadreReserveViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != crvs && crvs.size() > 0) {
            for (CadreReserveView crv : crvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", crv.getId() + "");  // 此id就是cadreId
                option.put("text", crv.getRealname());
                //UserBean userBean = userBeanService.get(crv.getUserId());
                //option.put("user", userBean);

                if (StringUtils.isNotBlank(crv.getCode())) {
                    option.put("code", crv.getCode());
                    option.put("unit", extService.getUnit(crv.getUserId()));
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
    public Map member_selects(Integer pageSize,
                              Integer partyId,
                              Integer branchId,
                              Byte type, // 党员类别
                              Boolean isRetire,
                              Byte politicalStatus,
                              Byte[] status, // 党员状态
                              Boolean noAuth, // 默认需要读取权限
                              Integer[] excludeUserIds, // 排除用户
                              @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                              Integer pageNo,
                              String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        example.setOrderByClause("sort_order desc, convert(realname using gbk) asc");

        List<Integer> adminPartyIdList = null;
        List<Integer> adminBranchIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)){
            adminPartyIdList = loginUserService.adminPartyIdList();
            adminBranchIdList = loginUserService.adminBranchIdList();

            criteria.addPermits(adminPartyIdList, adminBranchIdList);
        }

        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        if(isRetire!=null){
            criteria.andIsRetireEqualTo(isRetire);
        }

        if(politicalStatus!=null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }

        if(status!=null && status.length>0){
            criteria.andStatusIn(Arrays.asList(status));
        }

        if(excludeUserIds!=null && excludeUserIds.length>0){
            criteria.andUserIdNotIn(Arrays.asList(excludeUserIds));
        }

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) {
            criteria.andUserLike(searchStr);
        }

        int count = (int) memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> members = memberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != members && members.size() > 0) {

            for (MemberView member : members) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(member.getUserId());
                option.put("id", member.getUserId() + "");
                option.put("text", member.getRealname());

                option.put("username", member.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", member.getCode());
                option.put("realname", member.getRealname());
                option.put("gender", member.getGender());
                option.put("birth", member.getBirth());
                option.put("nation", member.getNation());

                if(needPrivate) {
                    option.put("idcard", member.getIdcard());
                    option.put("politicalStatus", member.getPoliticalStatus());
                    option.put("mobile", member.getMobile());
                }
                //option.put("user", userBeanService.get(member.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("unit", extService.getUnit(uv.getId()));
                }

                /*String branchName = StringUtils.defaultIfBlank(member.getBranchName(), member.getPartyName());
                String schoolName = CmTag.getSysConfig().getSchoolName();
                String schoolShortName = CmTag.getSysConfig().getSchoolShortName();
                branchName = RegExUtils.replaceFirst(branchName,
                        "[中共|中国共产党]"+ "["+ schoolShortName +"|"+ schoolName +"]", "");
                option.put("unit", branchName);*/

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
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = iMemberMapper.countMemberInflowList(type, status, hasOutApply, searchStr, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInflow> memberInflows = iMemberMapper.selectMemberInflowList(type, status, hasOutApply, searchStr,
                addPermits, adminPartyIdList, adminBranchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != memberInflows && memberInflows.size() > 0) {

            for (MemberInflow m : memberInflows) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(m.getUserId());
                option.put("id", m.getUserId() + "");
                option.put("text", uv.getRealname());
                //option.put("user", userBeanService.get(m.getUserId()));

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

        int count = iMemberMapper.countNotMemberList(searchStr, sysUserService.buildRoleIds(RoleConstants.ROLE_REG));
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iMemberMapper.selectNotMemberList(searchStr, sysUserService.buildRoleIds(RoleConstants.ROLE_REG), new RowBounds((pageNo - 1) * pageSize, pageSize));

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

}
