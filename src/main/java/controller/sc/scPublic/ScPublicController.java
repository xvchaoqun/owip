package controller.sc.scPublic;

import controller.ScPublicBaseController;
import controller.global.OpException;
import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scCommittee.ScCommitteeVoteViewExample;
import domain.sc.scPublic.ScPublic;
import domain.sc.scPublic.ScPublicView;
import domain.sc.scPublic.ScPublicViewExample;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.common.FreemarkerService;
import service.sc.scCommittee.ScCommitteeService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
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
@RequestMapping("/sc")
public class ScPublicController extends ScPublicBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FreemarkerService freemarkerService;

    @RequiresPermissions("scPublic:list")
    @RequestMapping("/scPublic")
    public String scPublic(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "sc/scPublic/scPublic/scPublic_page";
    }

    @RequiresPermissions("scPublic:list")
    @RequestMapping("/scPublic_data")
    public void scPublic_data(HttpServletResponse response,
                              @RequestParam(defaultValue = "1") Integer cls,
                                    Integer year,
                                    Integer committeeId,
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

        ScPublicViewExample example = new ScPublicViewExample();
        ScPublicViewExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false);
        example.setOrderByClause("id desc");

        criteria.andIsFinishedEqualTo(cls==2);

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (committeeId!=null) {
            criteria.andCommitteeIdEqualTo(committeeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scPublic_export(example, response);
            return;
        }

        long count = scPublicViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPublicView> records= scPublicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scPublic.class, scPublicMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scPublic:edit")
    @RequestMapping("/scPublic_process")
    public String do_scPublic_process(ScPublic record,
                              MultipartFile _wordFilePath,
                              MultipartFile _pdfFilePath,
                              @RequestParam(required = false, value = "voteIds[]") Integer[] voteIds,
                                 Byte export, // 0:预览 1：下载  其他：提交保存
                              HttpServletResponse response, ModelMap modelMap) throws IOException, InterruptedException, TemplateException {

        if(export!=null && export==0){

            ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andIdIn(Arrays.asList(voteIds));
            example.setOrderByClause("field(id,"+ StringUtils.join(voteIds, ",") + ") asc");
            List<ScCommitteeVoteView> votes = scCommitteeVoteViewMapper.selectByExample(example);

            Map<String, Object> dataMap = scPublicService.processData(record, votes);
            modelMap.put("dataMap", dataMap);

            return "sc/scPublic/scPublic/scPublic_preview";
        }else if(export!=null && export == 1){

            //输出文件
            String filename = "干部任前公示";
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
            response.setContentType("application/msword;charset=UTF-8");

            ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andIdIn(Arrays.asList(voteIds));
            example.setOrderByClause("field(id,"+ StringUtils.join(voteIds, ",") + ") asc");
            List<ScCommitteeVoteView> votes = scCommitteeVoteViewMapper.selectByExample(example);

            Map<String, Object> dataMap = scPublicService.processData(record, votes);
            freemarkerService.process("/sc/sc_public.ftl", dataMap, response.getWriter());
            return null;
        }

        if(voteIds==null || voteIds.length==0){
            throw new OpException("请选择公示对象。");
        }

        if(record.getNum()!=null &&
                scPublicService.idDuplicate(record.getId(), record.getYear(), record.getNum())){
            throw new OpException("编号重复。");
        }

        Integer id = record.getId();
        record.setWordFilePath(upload(_wordFilePath, "scPublic-word"));
        record.setPdfFilePath(uploadPdf(_pdfFilePath, "scPublic-pdf"));
        if (id == null) {
            scPublicService.insertSelective(record, voteIds);
            logger.info(addLog( SystemConstants.LOG_SC_PUBLIC, "添加干部任前公示：%s", record.getId()));
        } else {

            scPublicService.updateByPrimaryKeySelective(record, voteIds);
            logger.info(addLog( SystemConstants.LOG_SC_PUBLIC, "更新干部任前公示：%s", record.getId()));
        }

        JSONUtils.write(response, success(FormUtils.SUCCESS));
        return null;
    }

    @RequiresPermissions("scPublic:edit")
    @RequestMapping("/scPublic_users")
    public String scPublic_users(Integer committeeId, ModelMap modelMap) {

        ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
        example.createCriteria().andCommitteeIdEqualTo(committeeId)
                .andTypeEqualTo(SystemConstants.DISPATCH_CADRE_TYPE_APPOINT);
        example.setOrderByClause("sort_order desc");
        List<ScCommitteeVoteView> scCommitteeVoteViews = scCommitteeVoteViewMapper.selectByExample(example);
        modelMap.put("scCommitteeVotes", scCommitteeVoteViews);

        return "sc/scPublic/scPublic/scPublic_users";
    }

    @RequiresPermissions("scPublic:edit")
    @RequestMapping(value = "/scPublic_selectUser", method = RequestMethod.POST)
    public void do_scPublic_selectUser(@RequestParam(value = "voteIds[]") Integer[] voteIds,
                                       HttpServletResponse response) throws IOException {

        List<ScCommitteeVoteView> votes = new ArrayList<>();
        if(voteIds!=null){

            ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andIdIn(Arrays.asList(voteIds));
            example.setOrderByClause("sort_order desc");
            votes = scCommitteeVoteViewMapper.selectByExample(example);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("votes", votes);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("scPublic:edit")
    @RequestMapping("/scPublic_au")
    public String scPublic_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScPublic scPublic = scPublicMapper.selectByPrimaryKey(id);
            modelMap.put("scPublic", scPublic);

            modelMap.put("votes", iScMapper.getScPublicVotes(id));
        }

        ScCommitteeService scCommitteeService = CmTag.getBean(ScCommitteeService.class);
        modelMap.put("scCommittees", scCommitteeService.findAll());

        return "sc/scPublic/scPublic/scPublic_au";
    }

    @RequiresPermissions("scPublic:del")
    @RequestMapping(value = "/scPublic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scPublic_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scPublicService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_PUBLIC, "批量删除干部任前公示：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPublic:finish")
    @RequestMapping(value = "/scPublic_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map scPublic_finish(HttpServletRequest request,
                               @RequestParam(value = "ids[]") Integer[] ids,
                               ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scPublicService.finish(ids, true);
            logger.info(addLog( SystemConstants.LOG_SC_PUBLIC, "批量结束干部任前公示（已确认）：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPublic:confirm")
    @RequestMapping(value = "/scPublic_confirm", method = RequestMethod.POST)
    @ResponseBody
    public Map scPublic_confirm(HttpServletRequest request,
                               @RequestParam(value = "ids[]") Integer[] ids,
                               ModelMap modelMap) {


        if (null != ids && ids.length>0){

            scPublicService.confirm(ids);
            logger.info(addLog( SystemConstants.LOG_SC_PUBLIC, "批量确认结束干部任前公示：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scPublic_export(ScPublicViewExample example, HttpServletResponse response) {

        List<ScPublicView> records = scPublicViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属党委常委会|100","年度|100","公示文件WORD版|100",
                "公示文件PDF版|100","公示时间|100","发布时间|100","是否公示结束|100","是否确认公示结束|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPublicView record = records.get(i);
            String[] values = {
                record.getCommitteeId()+"",
                            record.getYear()+"",
                            record.getWordFilePath(),
                            record.getPdfFilePath(),
                            DateUtils.formatDate(record.getPublicStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getPublishDate(), DateUtils.YYYY_MM_DD),
                            record.getIsFinished() +"",
                            record.getIsConfirmed()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部任前公示_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

  /*  @RequestMapping("/scPublic_selects")
    @ResponseBody
    public Map scPublic_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScPublicExample example = new ScPublicExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = scPublicMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScPublic> scPublics = scPublicMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scPublics && scPublics.size()>0){

            for(ScPublic scPublic:scPublics){

                Select2Option option = new Select2Option();
                option.setText(scPublic.getName());
                option.setId(scPublic.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
