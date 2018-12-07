package controller.pcs;

import domain.pcs.PcsCommitteeMember;
import domain.pcs.PcsCommitteeMemberView;
import domain.pcs.PcsCommitteeMemberViewExample;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class PcsCommitteeMemberController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsCommitteeMember:list")
    @RequestMapping("/pcsCommitteeMember")
    public String pcsCommitteeMember(
            @RequestParam(required = false, defaultValue = "0") boolean type,
            @RequestParam(required = false, defaultValue = "0") boolean isQuit,
            @RequestParam(required = false, value = "nation") String[] nation,
            ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("isQuit", isQuit);

        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        modelMap.put("teacherNations", IPropertyMapper.teacherNations());
        return "pcs/pcsCommitteeMember/pcsCommitteeMember_page";
    }

    @RequiresPermissions("pcsCommitteeMember:list")
    @RequestMapping("/pcsCommitteeMember_data")
    @ResponseBody
    public void pcsCommitteeMember_data(HttpServletResponse response,
                                  boolean type,
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

        PcsCommitteeMemberViewExample example = new PcsCommitteeMemberViewExample();
        PcsCommitteeMemberViewExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andIsQuitEqualTo(isQuit);
        example.setOrderByClause("sort_order desc");

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
            pcsCommitteeMember_export(type, example, response);
            return;
        }

        long count = pcsCommitteeMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsCommitteeMemberView> records = pcsCommitteeMemberViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsCommitteeMember.class, pcsCommitteeMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsCommitteeMember:edit")
    @RequestMapping(value = "/pcsCommitteeMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCommitteeMember_au(PcsCommitteeMember record,
                                  MultipartFile _postFilePath,
                                  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (pcsCommitteeMemberService.idDuplicate(id, record.getType(),
                record.getUserId(), record.getIsQuit())) {
            return failed("添加重复");
        }
        record.setPostFilePath(uploadPdf(_postFilePath, "pcsCommitteeMember"));

        if (id == null) {
            pcsCommitteeMemberService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "添加两委委员：%s", record.getId()));
        } else {
            record.setType(null);
            record.setIsQuit(null);
            pcsCommitteeMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "更新两委委员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCommitteeMember:edit")
    @RequestMapping("/pcsCommitteeMember_au")
    public String pcsCommitteeMember_au(Integer id, boolean type, boolean isQuit, ModelMap modelMap) {

        if (id != null) {
            PcsCommitteeMember pcsCommitteeMember = pcsCommitteeMemberMapper.selectByPrimaryKey(id);
            modelMap.put("pcsCommitteeMember", pcsCommitteeMember);

            type = pcsCommitteeMember.getType();
            isQuit = pcsCommitteeMember.getIsQuit();
        }

        modelMap.put("type", type);
        modelMap.put("isQuit", isQuit);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("currentPcsConfig", currentPcsConfig);

        modelMap.put("pcsConfigs", pcsConfigService.findAll().values());

        return "pcs/pcsCommitteeMember/pcsCommitteeMember_au";
    }

    // 离任
    @RequiresPermissions("pcsCommitteeMember:leave")
    @RequestMapping("/pcsCommitteeMember_leave")
    public String pcsCommitteeMember_leave(int id, ModelMap modelMap) {

        PcsCommitteeMember pcsCommitteeMember = pcsCommitteeMemberMapper.selectByPrimaryKey(id);
        modelMap.put("pcsCommitteeMember", pcsCommitteeMember);

        return "pcs/pcsCommitteeMember/pcsCommitteeMember_leave";
    }

    @RequiresPermissions("pcsCommitteeMember:leave")
    @RequestMapping(value = "/pcsCommitteeMember_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCommitteeMember_leave(int id, boolean type, boolean isQuit,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date quitDate,
                                           MultipartFile _quitFilePath,
                                     String quitReason,
                                     HttpServletRequest request) throws IOException, InterruptedException {

        PcsCommitteeMember record = new PcsCommitteeMember();
        record.setId(id);
        record.setIsQuit(isQuit);
        record.setQuitFilePath(uploadPdf(_quitFilePath, "pcsCommitteeMember"));
        if(isQuit){
            record.setQuitDate(quitDate);
            record.setQuitReason(quitReason);
        }else{
            commonMapper.excuteSql("update pcs_committee_member set quit_date=null, " +
                    "quit_file_path=null, quit_reason=null where id="+id);
        }
        record.setSortOrder(getNextSortOrder("pcs_committee_member", "type="+ type
                + " and is_quit="+ isQuit));

        pcsCommitteeMemberService.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_ADMIN, "离职/返回现任库：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCommitteeMember:del")
    @RequestMapping(value = "/pcsCommitteeMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCommitteeMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pcsCommitteeMemberService.del(id);
            logger.info(addLog(LogConstants.LOG_PCS, "删除两委委员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCommitteeMember:del")
    @RequestMapping(value = "/pcsCommitteeMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsCommitteeMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            pcsCommitteeMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除两委委员：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCommitteeMember:changeOrder")
    @RequestMapping(value = "/pcsCommitteeMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCommitteeMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pcsCommitteeMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PCS, "两委委员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void pcsCommitteeMember_export(boolean type, PcsCommitteeMemberViewExample example, HttpServletResponse response) {

        List<PcsCommitteeMemberView> records = pcsCommitteeMemberViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"届数|200", "职务|100", "任职日期|90", "姓名|80", "性别|50",
                 "民族|60", "职称|120","出生年月|80",
                "年龄|50", "入党时间|80","所在单位及职务|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsCommitteeMemberView record = records.get(i);
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
        String fileName = type?"现任纪委委员":"现任党委委员";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/pcsCommitteeMember_selects")
    @ResponseBody
    public Map pcsCommitteeMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsCommitteeMemberExample example = new PcsCommitteeMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = pcsCommitteeMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PcsCommitteeMember> records = pcsCommitteeMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PcsCommitteeMember record:records){

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
