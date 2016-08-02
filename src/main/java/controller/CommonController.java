package controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.abroad.ApproverType;
import domain.base.Location;
import domain.cadre.Cadre;
import domain.dispatch.DispatchType;
import domain.ext.ExtBks;
import domain.ext.ExtJzg;
import domain.ext.ExtYjs;
import domain.member.Member;
import domain.member.MemberInflow;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.*;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/11/27.
 */
@Controller
public class CommonController extends BaseController{

    @RequestMapping("/cache/clear")
    @ResponseBody
    public Map clearCache(){
        /*CacheManager manager = CacheManager.getInstance();
        String[] names = manager.getCacheNames();
        for (String name : names)
        {
            Cache cache = manager.getCache(name);
            cache.removeAll();
        }*/
        CacheManager.create().clearAll();
        /*CacheManager cacheManager = CacheManager.create();
        Ehcache cache = cacheManager.getEhcache(cacheConfiguration.getName());
        cache.removeAll();*/

        return success();
    }

/*    public static void main(String[] args) throws IllegalAccessException {

        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if(StringUtils.equals(field.getType().getName(), "java.util.Map")){

                System.out.println(field.getName() + ":"+ field.get(null));
            }
        }
    }*/

    @RequestMapping("/metaMap_JSON")
    public String metaMap_JSON(ModelMap modelMap) throws JsonProcessingException {

        /*Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MetaType.class, MetaTypeMixin.class);
        sourceMixins.put(SafeBox.class, MetaSafeBoxMixin.class);
        sourceMixins.put(Unit.class, MetaUnitMixin.class);
        sourceMixins.put(DispatchType.class, MetaDispatchTypeMixin.class);

        Map metaMap = getMetaMap();
        metaMap.remove("cadreMap");*/

        Map map = new HashMap();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        for (MetaType metaType : metaTypeMap.values()) {
            map.put(metaType.getId(), metaType.getName());
        }
        modelMap.put("metaTypeMap", JSONUtils.toString(map));

        Map cMap = new HashMap();

        Map metaMap = getMetaMap();
        Map constantMap = new HashMap();

        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if(StringUtils.equals(field.getType().getName(), "java.util.Map")){
                try {
                    constantMap.put(field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        cMap.putAll(metaMap);
        cMap.putAll(constantMap);

        ObjectMapper mapper = JSONUtils.buildObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
        sourceMixins.put(MetaType.class, MetaTypeOptionMixin.class);
        sourceMixins.put(Party.class, OptionMixin.class);
        sourceMixins.put(Branch.class, OptionMixin.class);
        //sourceMixins.put(Dispatch.class, OptionMixin.class);
        //sourceMixins.put(DispatchUnit.class, OptionMixin.class);
        sourceMixins.put(Unit.class, OptionMixin.class);
        //sourceMixins.put(Cadre.class, OptionMixin.class);
        sourceMixins.put(DispatchType.class, OptionMixin.class);
        //sourceMixins.put(SafeBox.class, OptionMixin.class);
        sourceMixins.put(ApproverType.class, OptionMixin.class);
        sourceMixins.put(Location.class, OptionMixin.class);
        //sourceMixins.put(Country.class, OptionMixin.class);

        sourceMixins.put(SysRole.class, OptionMixin.class);

        mapper.setMixInAnnotations(sourceMixins);

        modelMap.put("cMap", mapper.writeValueAsString(cMap));

        return "common/metaMap_JSON";
    }

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
        example.setOrderByClause("create_time desc");
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
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
                    option.put("code", sysUser.getCode());
                    ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                    if(extJzg!=null) {
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
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
    public Map memberInflow_selects(Integer pageSize, Byte type, Byte status, Boolean hasOutApply, Integer pageNo,String searchStr) throws IOException {

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

        int count = commonMapper.countMemberInflow(type, status, hasOutApply, searchStr, addPermits, adminPartyIdList, adminBranchIdList);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<MemberInflow> memberInflows = commonMapper.selectMemberInflowList(type, status, hasOutApply, searchStr,
                addPermits, adminPartyIdList, adminBranchIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != memberInflows && memberInflows.size()>0){

            for(MemberInflow m:memberInflows){
                Map<String, Object> option = new HashMap<>();
                SysUser sysUser = sysUserService.findById(m.getUserId());
                option.put("id", m.getUserId() + "");
                option.put("text", sysUser.getRealname());
                option.put("user", userBeanService.get(m.getUserId()));

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
    public Map notMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if(searchStr!= null) searchStr = "%"+searchStr+"%";

        int count = commonMapper.countNotMember(searchStr);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SysUser> sysUsers = commonMapper.selectNotMemberList(searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if(null != sysUsers && sysUsers.size()>0){

            for(SysUser sysUser:sysUsers){
                Map<String, String> option = new HashMap<>();
                option.put("id", sysUser.getId() + "");
                option.put("text", sysUser.getRealname());

                if(StringUtils.isNotBlank(sysUser.getCode())) {
                    option.put("code", sysUser.getCode());
                    if(sysUser.getType()== SystemConstants.USER_TYPE_JZG) {
                        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
                        if (extJzg != null) {
                            option.put("unit", extJzg.getDwmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_BKS) {
                        ExtBks extBks = extBksService.getByCode(sysUser.getCode());
                        if (extBks != null) {
                            option.put("unit", extBks.getYxmc());
                        }
                    }
                    if(sysUser.getType()== SystemConstants.USER_TYPE_YJS) {
                        ExtYjs extYjs = extYjsService.getByCode(sysUser.getCode());
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
