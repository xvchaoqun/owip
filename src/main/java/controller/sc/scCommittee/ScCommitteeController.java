package controller.sc.scCommittee;

import controller.ScCommitteeBaseController;
import domain.sc.scCommittee.ScCommittee;
import domain.sc.scCommittee.ScCommitteeMember;
import domain.sc.scCommittee.ScCommitteeMemberView;
import domain.sc.scCommittee.ScCommitteeView;
import domain.sc.scCommittee.ScCommitteeViewExample;
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
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
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
public class ScCommitteeController extends ScCommitteeBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommittee:list")
    @RequestMapping("/scCommittee")
    public String scCommittee(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("committeeMemberCount", cadreService.countCommitteeMember());

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/sc/scCommitteeTopic";
        }else if (cls == 3) {
            return "forward:/sc/scCommitteeVote";
        }else if (cls == 4) {
            return "forward:/sc/scCommitteeOtherVote";
        }

        return "sc/scCommittee/scCommittee/scCommittee_page";
    }

    @RequiresPermissions("scCommittee:list")
    @RequestMapping("/scCommittee_data")
    public void scCommittee_data(HttpServletResponse response,
                                    Integer year,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date holdDate,
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

        ScCommitteeViewExample example = new ScCommitteeViewExample();
        ScCommitteeViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (holdDate!=null) {
            criteria.andHoldDateEqualTo(holdDate);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scCommittee_export(example, response);
            return;
        }

        long count = scCommitteeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeView> records= scCommitteeViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommittee.class, scCommitteeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scCommittee:edit")
    @RequestMapping(value = "/scCommittee_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommittee_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        /*if (!StringUtils.equalsIgnoreCase(ext, ".ppt")
                && !ContentTypeUtils.isFormat(file, "ppt")) {
            throw new OpException("文件格式错误，请上传ppt文件");
        }*/
        if (!StringUtils.equalsIgnoreCase(ext, ".ppt") && !StringUtils.equalsIgnoreCase(ext, ".pptx")) {
            return failed("文件格式错误，请上传ppt文件");
        }

        String savePath = uploadDoc(file, "scCommittee");

        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("filePath", savePath);

        return resultMap;
    }

    @RequiresPermissions("scCommittee:edit")
    @RequestMapping(value = "/scCommittee_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommittee_au(ScCommittee record,
                                 String items,
                                 MultipartFile _logFile,
                                 HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        List<ScCommitteeMember> scCommitteeMembers = GsonUtils.toBeans(items, ScCommitteeMember.class);
        record.setLogFile(uploadPdf(_logFile, "scCommittee-log"));
        if (id == null) {
            scCommitteeService.insertSelective(record, scCommitteeMembers);
            logger.info(addLog( SystemConstants.LOG_SC_COMMITTEE, "添加党委常委会：%s", record.getId()));
        } else {

            scCommitteeService.updateByPrimaryKeySelective(record, scCommitteeMembers);
            logger.info(addLog( SystemConstants.LOG_SC_COMMITTEE, "更新党委常委会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommittee:edit")
    @RequestMapping("/scCommittee_au")
    public String scCommittee_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScCommittee scCommittee = scCommitteeMapper.selectByPrimaryKey(id);
            modelMap.put("scCommittee", scCommittee);

            if(scCommittee!=null){

                List<ScCommitteeMemberView> memberUserList = scCommitteeService.getMemberList(id, null);
                modelMap.put("memberUserList", memberUserList);
            }
        }

        return "sc/scCommittee/scCommittee/scCommittee_au";
    }

    @RequiresPermissions("scCommittee:del")
    @RequestMapping(value = "/scCommittee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scCommitteeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_COMMITTEE, "批量删除党委常委会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scCommittee_export(ScCommitteeViewExample example, HttpServletResponse response) {

        List<ScCommitteeView> records = scCommitteeViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"文件|100","年份|100","党委常委会日期|100","议题数量|100","会议记录|100","列席人|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScCommitteeView record = records.get(i);
            String[] values = {
                record.getFilePath(),
                            record.getYear()+"",
                            DateUtils.formatDate(record.getHoldDate(), DateUtils.YYYY_MM_DD),
                            record.getTopicNum()+"",
                            record.getLogFile(),
                            record.getAttendUsers(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "党委常委会_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
