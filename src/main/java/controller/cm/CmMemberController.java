package controller.cm;

import domain.cm.CmMember;
import domain.cm.CmMemberView;
import domain.cm.CmMemberViewExample;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CmConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class CmMemberController extends CmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cmMember:list")
    @RequestMapping("/cmMember")
    public String cmMember(
            @RequestParam(required = false, defaultValue = "1") byte type,
            @RequestParam(required = false, defaultValue = "0") boolean isQuit,
            @RequestParam(required = false, value = "nation") String[] nation,
            Integer userId,
            ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("isQuit", isQuit);

        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("teacherNations", iPropertyMapper.teacherNations());
        return "cm/cmMember/cmMember_page";
    }

    @RequiresPermissions("cmMember:list")
    @RequestMapping("/cmMember_data")
    @ResponseBody
    public void cmMember_data(HttpServletResponse response,
                                  Integer configId,
                                  byte type,
                                  boolean isQuit,
                                  Integer userId,
                                  Integer post,
                                  Byte gender,
                                  @RequestParam(required = false, value = "nation") String[] nation,
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

        CmMemberViewExample example = new CmMemberViewExample();
        CmMemberViewExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andIsQuitEqualTo(isQuit);
        example.setOrderByClause("sort_order desc");

        if(configId!=null){
            criteria.andConfigIdEqualTo(configId);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (post != null) {
            criteria.andPostEqualTo(post);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cmMember_export(type, example, response);
            return;
        }

        long count = cmMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CmMemberView> records = cmMemberViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cmMember.class, cmMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cmMember:edit")
    @RequestMapping(value = "/cmMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmMember_au(CmMember record,
                              MultipartFile _postFilePath,
                              HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (cmMemberService.idDuplicate(id, record.getType(),
                record.getUserId(), record.getIsQuit())) {
            return failed("添加重复");
        }
        record.setPostFilePath(uploadPdf(_postFilePath, "cmMember"));

        if (id == null) {
            cmMemberService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "添加两委委员：%s", record.getId()));
        } else {
            record.setType(null);
            record.setIsQuit(null);
            cmMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "更新两委委员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cmMember:edit")
    @RequestMapping("/cmMember_au")
    public String cmMember_au(Integer id, byte type, boolean isQuit, ModelMap modelMap) {

        if (id != null) {
            CmMember cmMember = cmMemberMapper.selectByPrimaryKey(id);
            modelMap.put("cmMember", cmMember);

            type = cmMember.getType();
            isQuit = cmMember.getIsQuit();
        }

        modelMap.put("type", type);
        modelMap.put("isQuit", isQuit);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("currentPcsConfig", currentPcsConfig);

        modelMap.put("pcsConfigs", pcsConfigService.findAll().values());

        return "cm/cmMember/cmMember_au";
    }

    // 从本届党委委员中提取党委常委
    @RequiresPermissions("cmMember:edit")
    @RequestMapping("/cmMember_draw")
    public String cmMember_draw(ModelMap modelMap) {

        int configId = -1;
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if(pcsConfig!=null){
            configId = pcsConfig.getId();
        }
        modelMap.put("configId", configId);

        return "cm/cmMember/cmMember_draw";
    }

    @RequiresPermissions("cmMember:edit")
    @RequestMapping(value = "/cmMember_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmMember_draw(HttpServletRequest request,
                                @RequestParam(value = "memberIds[]") Integer[] memberIds, ModelMap modelMap) {

        if (null != memberIds && memberIds.length > 0) {
            cmMemberService.draw(memberIds);
            logger.info(addLog(LogConstants.LOG_PCS, "从党委委员中提取党委常委：%s",
                    StringUtils.join(memberIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 离任
    @RequiresPermissions("cmMember:leave")
    @RequestMapping("/cmMember_leave")
    public String cmMember_leave(int id, ModelMap modelMap) {

        CmMember cmMember = cmMemberMapper.selectByPrimaryKey(id);
        modelMap.put("cmMember", cmMember);

        return "cm/cmMember/cmMember_leave";
    }

    @RequiresPermissions("cmMember:leave")
    @RequestMapping(value = "/cmMember_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmMember_leave(int id, byte type, boolean isQuit,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date quitDate,
                                           MultipartFile _quitFilePath,
                                     String quitReason,
                                     HttpServletRequest request) throws IOException, InterruptedException {

        CmMember record = new CmMember();
        record.setId(id);
        record.setIsQuit(isQuit);
        record.setQuitFilePath(uploadPdf(_quitFilePath, "cmMember"));
        if(isQuit){
            record.setQuitDate(quitDate);
            record.setQuitReason(quitReason);
        }else{
            commonMapper.excuteSql("update cm_member set quit_date=null, " +
                    "quit_file_path=null, quit_reason=null where id="+id);
        }
        record.setSortOrder(getNextSortOrder("cm_member", "type="+ type
                + " and is_quit="+ isQuit));

        cmMemberService.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_ADMIN, "离职/返回现任库：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cmMember:del")
    @RequestMapping(value = "/cmMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cmMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cmMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除两委委员：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cmMember:changeOrder")
    @RequestMapping(value = "/cmMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cmMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PCS, "两委委员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cmMember_export(byte type, CmMemberViewExample example, HttpServletResponse response) {

        List<CmMemberView> records = cmMemberViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"届数|200", "职务|100", "任职日期|90", "姓名|80", "性别|50",
                 "民族|60", "职称|120","出生年月|80",
                "年龄|50", "入党时间|80","所在单位及职务|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CmMemberView record = records.get(i);
            String[] values = {
                    record.getPcsName(),
                    metaTypeService.getName(record.getPost()),
                    DateUtils.formatDate(record.getPostDate(), "yyyy.MM.dd"),
                    record.getRealname(),
                    record.getGender()==null?"": SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getProPost() + "",
                    DateUtils.formatDate(record.getBirth(), "yyyy.MM"),
                    record.getBirth()==null?"":DateUtils.intervalYearsUntilNow(record.getBirth()) + "",
                    DateUtils.formatDate(record.getGrowTime(), "yyyy.MM"),
                    record.getTitle()
            };
            valuesList.add(values);
        }
        String fileName = "现任" + CmConstants.CM_MEMBER_TYPE_MAP.get(type);
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/cmMember_selects")
    @ResponseBody
    public Map cmMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CmMemberExample example = new CmMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cmMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CmMember> records = cmMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CmMember record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
