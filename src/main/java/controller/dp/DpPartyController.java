package controller.dp;

import domain.dp.*;
import domain.dp.DpPartyExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpPartyController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty")
    public String dpParty(HttpServletResponse response,
                            ModelMap modelMap,
                            @RequestParam(required = false, defaultValue = "1") Byte cls) throws Exception{
        modelMap.put("cls",cls);

        return "dp/dpParty/dpParty_page";
    }

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty_data")
    @ResponseBody
    public void dpParty_data(HttpServletResponse response,
                             @RequestParam(required = false, defaultValue = "1") byte cls,//正在运转
                             Integer id,
                             String code,
                             String name,
                             Integer unitId,
                             Integer classId,
                             String phone,
                             @RequestDateRange DateRange _foundTime,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                             Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpPartyViewExample example = new DpPartyViewExample();
        DpPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("sort_order desc"));
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

        criteria.andIsDeletedEqualTo(cls == 2);

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andPhoneLike(SqlUtils.like(phone));
        }
        if (_foundTime.getStart()!=null) {
            criteria.andFoundTimeGreaterThanOrEqualTo(_foundTime.getStart());
        }
        if (_foundTime.getEnd()!=null) {
            criteria.andFoundTimeLessThanOrEqualTo(_foundTime.getEnd());
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpParty_export(example, response);
            return;
        }

        long count = dpPartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpPartyView> records= dpPartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dp.class, dpPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping(value = "/dpParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map dpParty_au(DpParty record, String _foundTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (dpPartyService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (StringUtils.isNotBlank(_foundTime)){
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }
        if (id == null) {

            record.setCreateTime(new Date());
            dpPartyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加民主党派：{0}", record.getId()));
        } else {
            dpPartyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新民主党派：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping("/dpParty_au")
    public String dpParty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
            modelMap.put("dpParty", dpParty);
        }
        return "dp/dpParty/dpParty_au";
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping(value = "/dpParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpPartyService.del(id);
            logger.info(log( LogConstants.LOG_GROW, "删除基层党组织：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:del")
    @RequestMapping(value = "/dpParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpParty_batchDel(HttpServletRequest request,
                                @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpPartyService.batchDel(ids,isDeleted);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除民主党派：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpParty:edit")
    @RequestMapping(value = "/dpParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpPartyService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_GROW, "基层党组织调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpParty_export(DpPartyViewExample example, HttpServletResponse response) {

        List<DpPartyView> records = dpPartyViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100","名称|100","简称|100","所属单位|250","民主党派类别|100","联系电话|100","传真|100","邮箱|100","成立时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpPartyView record = records.get(i);
            String[] values = {
                            record.getCode(),//编号
                            record.getName(),//名称
                            record.getShortName(),//简称
                            record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),
                            metaTypeService.getName(record.getClassId()),//民主党派类别
                            record.getPhone(),//联系电话
                            record.getFax(),
                            record.getEmail(),
                            DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMMDD),
            };
            valuesList.add(values);
        }
        String fileName = String.format("民主党派(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpParty_selects")
    @ResponseBody
    public Map dpParty_selects(Integer pageSize, Boolean auth,
                               Boolean del,
                               Integer pageNo, Integer classId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpPartyExample example = new DpPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if (del!=null){
            criteria.andIsDeletedEqualTo(del);
        }
        if (classId!=null){
            criteria.andClassIdEqualTo(classId);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

       /* //======权限
        if (BooleanUtils.isTrue(auth)){
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
                List<Integer> dpPartyList = loginUserService.adminDpPartyIdList();
                if (dpPartyList.size() > 0)
                    criteria.andIdIn(dpPartyList);
                else
                    criteria.andIdIsNull();
            }
        }*/

        long count = dpPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpParty> records = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpParty record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");
                option.put("class",record.getClassId());
                option.put("del",record.getIsDeleted());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //民主党派基本信息
    @RequestMapping("/dpParty_base")
    public String dpParty_base(Integer id, ModelMap modelMap){

        DpParty dpParty = dpPartyMapper.selectByPrimaryKey(id);
        modelMap.put("dpParty",dpParty);
        DpPartyMemberGroup presentGroup = dpPartyMemberGroupService.getPresentGroup(id);
        modelMap.put("presentGroup",presentGroup);

        if (presentGroup!=null){
            DpPartyMemberExample example = new DpPartyMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
            modelMap.put("dpPartyMembers", dpPartyMembers);
            modelMap.put("adminIds", iDpPartyMapper.findDpPartyAdmin(id));
        }

        return "dp/dpParty/dpParty_base";
    }

    @RequiresPermissions("dp:list")
    @RequestMapping("/dpParty_view")
    public String dpParty_show_view(){

        return "dp/dpParty/dpParty_view";
    }

    @RequiresPermissions("dp:edit")
    @RequestMapping("/dpParty_import")
    public String dpParty_import(){

        return "dp/dpParty/dpParty_import";
    }

}
