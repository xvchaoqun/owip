package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.party.Organizer;
import domain.party.OrganizerExample;
import domain.party.OrganizerExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class OrganizerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer")
    public String organizer(@RequestParam(required = false, defaultValue = "1") Byte cls,
                            byte type,
                            Integer userId,
                            Integer partyId,
                            Integer unitId,
                            ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        if (cls == 10) {
            return "forward:/organizerGroup";
        }

        if(userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if(partyId!=null){
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        if(unitId!=null){
            modelMap.put("unit", unitService.findAll().get(unitId));
        }

        return "party/organizer/organizer_page";
    }

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer_data")
    @ResponseBody
    public void organizer_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               Integer year,
                               byte type,
                               Integer userId,
                               Integer partyId,
                               Integer unitId,
                               String unit,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerExample example = new OrganizerExample();
        Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type);
        if(cls==1 || cls==2) {
            example.setOrderByClause("sort_order desc");
        }else if(cls==3) {
            example.setOrderByClause("id desc");
        }

        if (cls == 1) {
            criteria.andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_NOW);
        } else if (cls == 2) {
            criteria.andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_LEAVE);
        } else if(cls==3){
            criteria.andStatusIn(Arrays.asList(OwConstants.OW_ORGANIZER_STATUS_NOW,
                    OwConstants.OW_ORGANIZER_STATUS_HISTORY));
        }else{
            criteria.andIdIsNull();
        }

        if (year != null) {
            criteria.andYearEqualTo(year);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            organizer_export(example, response);
            return;
        }

        long count = organizerMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Organizer> records = organizerMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(organizer.class, organizerMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_au(Organizer record,
                               Boolean syncBaseInfo, // 更新时是否重新同步基本信息
                               HttpServletRequest request) {

        Integer id = record.getId();

        Organizer organizer = organizerService.get(record.getType(), record.getUserId());
        if ((id==null && organizer!=null) || (id!=null && id != organizer.getId())) {
                throw new OpException(organizer.getUser().getRealname()
                        + "已经是"
                        + OwConstants.OW_ORGANIZER_STATUS_MAP.get(organizer.getStatus())
                        + OwConstants.OW_ORGANIZER_TYPE_MAP.get(organizer.getType()));
        }

        if (id == null) {

            organizerService.insertSelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "添加组织员信息：{0}", record.getId()));
        } else {

            organizerService.updateByPrimaryKeySelective(record, BooleanUtils.isTrue(syncBaseInfo));
            logger.info(log(LogConstants.LOG_PARTY, "更新组织员信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_au")
    public String organizer_au(Integer id, Byte type,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               ModelMap modelMap) {
        byte status;
        if(cls==1){
            status = OwConstants.OW_ORGANIZER_STATUS_NOW;
        }else if(cls==2){
            status = OwConstants.OW_ORGANIZER_STATUS_LEAVE;
        }else if(cls==3){
            status = OwConstants.OW_ORGANIZER_STATUS_HISTORY;
        }else{
            return null;
        }

        if (id != null) {
            Organizer organizer = organizerMapper.selectByPrimaryKey(id);
            modelMap.put("organizer", organizer);
            type = organizer.getType();
            status = organizer.getStatus();

            modelMap.put("sysUser", sysUserService.findById(organizer.getUserId()));
        }

        modelMap.put("type", type);
        modelMap.put("status", status);

        return "party/organizer/organizer_au";
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_leave(int id,
                                  Byte status,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date dismissDate,
                                  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date appointDate,
                                  HttpServletRequest request) {

        if(status==null || status==OwConstants.OW_ORGANIZER_STATUS_LEAVE) {
            organizerService.leave(id, dismissDate);
            logger.info(log(LogConstants.LOG_PARTY, "组织员离任：{0}", id));
        }else{
            organizerService.reAppoint(id, appointDate);
            logger.info(log(LogConstants.LOG_PARTY, "离任组织员重新任用：{0}", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_leave")
    public String organizer_leave(Integer id, ModelMap modelMap) {

        if (id != null) {
            Organizer organizer = organizerMapper.selectByPrimaryKey(id);
            modelMap.put("organizer", organizer);

            modelMap.put("sysUser", sysUserService.findById(organizer.getUserId()));
        }

        return "party/organizer/organizer_leave";
    }

    @RequiresPermissions("organizer:del")
    @RequestMapping(value = "/organizer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map organizer_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            organizerService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除组织员信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:changeOrder")
    @RequestMapping(value = "/organizer_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerService.changeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_PARTY, "组织员信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void organizer_export(OrganizerExample example, HttpServletResponse response) {

        List<Organizer> records = organizerMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100", "类型|100", "组织员|100", "联系党委|100",
                "联系单位|100", "任职时间|100", "离任时间|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Organizer record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getType() + "",
                    record.getUserId() + "",
                    record.getPartyId() + "",
                    record.getUnits(),
                    DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getDismissDate(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "组织员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // <userId, >
    @RequestMapping("/organizer_selects")
    @ResponseBody
    public Map organizer_selects(Integer pageSize, Integer pageNo, Byte type, Byte status, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        long count = iPartyMapper.countOrganizerList(searchStr, type, status);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Organizer> records = iPartyMapper.selectOrganizerList(searchStr, type, status,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(Organizer record:records){
                SysUserView uv = record.getUser();

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("del", record.getStatus() != OwConstants.OW_ORGANIZER_STATUS_NOW);
                option.put("username", uv.getUsername());
                option.put("code", uv.getCode());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
