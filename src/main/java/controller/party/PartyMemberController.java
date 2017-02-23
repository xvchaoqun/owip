package controller.party;

import controller.BaseController;
import domain.base.MetaType;
import domain.party.*;
import domain.party.PartyMemberExample.Criteria;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember_page")
    public String partyMember_page(Integer groupId,
                                   //@RequestParam(required = false, value = "typeIds")Integer[] typeIds,
                                   Integer userId, ModelMap modelMap) {

        if(groupId!=null) {
            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
            modelMap.put("partyMemberGroup", partyMemberGroup);
        }
        if(userId!=null){
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }
        /*if (typeIds!=null) {
            List<Integer> _typeIds = Arrays.asList(typeIds);
            modelMap.put("selectedTypeIds", _typeIds);
        }*/

        return "party/partyMember/partyMember_page";
    }

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember_data")
    public void partyMember_data(HttpServletResponse response,
                                    Integer groupId,
                                    Integer userId,
                                 @RequestParam(required = false, value = "typeIds")Integer[] typeIds,
                                    Integer postId,
                                 Integer unitId,
                                 Integer partyId,
                                 Boolean isAdmin,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberViewExample example = new PartyMemberViewExample();
        PartyMemberViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_sort_order desc, sort_order desc");

        if (groupId!=null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (typeIds!=null) {
            List<Integer> selectedTypeIds = Arrays.asList(typeIds);
            criteria.andTypeIdsIn(selectedTypeIds);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (isAdmin!=null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyMember_export(example, response);
            return;
        }

        int count = partyMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMemberView> PartyMembers = partyMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", PartyMembers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_au(PartyMember record,
                                 @RequestParam(required = false, value = "_typeIds")Integer[] _typeIds,
                                 String _assignDate,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        record.setAssignDate(DateUtils.parseDate(_assignDate, "yyyy.MM"));
        if (partyMemberService.idDuplicate(id, record.getGroupId(), record.getUserId(), record.getPostId())) {
            return failed("添加重复【每个领导班子的人员不可重复，并且书记只有一个】");
        }
        boolean autoAdmin = false;
        {
            Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_party_member_post");
            MetaType post = postMap.get(record.getPostId());
            Boolean boolAttr = post.getBoolAttr();
            if (boolAttr != null && boolAttr) {
                autoAdmin = true;
            }
        }
        if(_typeIds!=null) {
            for (Integer typeId : _typeIds) {
                Map<Integer, MetaType> typeMap = metaTypeService.metaTypes("mc_party_member_type");
                MetaType type = typeMap.get(typeId);
                Boolean boolAttr = type.getBoolAttr();
                if (boolAttr != null && boolAttr) {
                    autoAdmin = true;
                    break;
                }
            }
            record.setTypeIds(StringUtils.join(_typeIds, ","));
        }

        if (id == null) {

            partyMemberService.insertSelective(record, autoAdmin);
            logger.info(addLog(SystemConstants.LOG_OW, "添加基层党组织成员：%s", record.getId()));
        } else {

            partyMemberService.updateByPrimaryKey(record, autoAdmin);
            logger.info(addLog(SystemConstants.LOG_OW, "更新基层党组织成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping("/partyMember_au")
    public String partyMember_au(Integer groupId, Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            modelMap.put("partyMember", partyMember);
           /* if (partyMember.getTypeIds()!=null) {
                List<Integer> _typeIds = Arrays.asList();
                modelMap.put("selectedTypeIds", _typeIds);
            }*/
            SysUserView uv = sysUserService.findById(partyMember.getUserId());
            modelMap.put("uv", uv);
            groupId = partyMember.getGroupId();
        }
        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("partyMemberGroup", partyMemberGroup);

        return "party/partyMember/partyMember_au";
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map partyMember_admin(HttpServletRequest request, Integer id) {

        if (id != null) {

            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            partyMemberAdminService.toggleAdmin(partyMember);

            // test
            /*SysUser sysUser = sysUserService.findById(partyMember.getUserId());
            System.out.println(JSONUtils.toString(sysUser));*/

            String op = partyMember.getIsAdmin()?"删除":"添加";
            logger.info(addLog(SystemConstants.LOG_OW, "%s基层党组织成员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map partyAdmin_del(Integer userId, Integer partyId) {

        partyMemberService.delAdmin(userId, partyId);
        logger.info(addLog(SystemConstants.LOG_OW, "删除基层党组织管理员权限，userId=%s, partyId=%s", userId, partyId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyMemberService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除基层党组织成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyMemberService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除基层党组织成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:changeOrder")
    @RequestMapping(value = "/partyMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyMemberService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_OW, "基层党组织成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void partyMember_export(PartyMemberViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = partyMemberService.export(example);
        String fileName = PropertiesUtils.getString("site.school")  +"分党委委员(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/partyMember_selects")
    @ResponseBody
    public Map partyMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberExample example = new PartyMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        int count = partyMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyMember> partyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != partyMembers && partyMembers.size()>0){

            for(PartyMember partyMember:partyMembers){

                Select2Option option = new Select2Option();
                //option.setText(partyMember.getName());
                option.setId(partyMember.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
