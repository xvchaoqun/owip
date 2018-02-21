package controller.sc.scGroup;

import controller.global.OpException;
import domain.sc.scGroup.ScGroup;
import domain.sc.scGroup.ScGroupExample;
import domain.sc.scGroup.ScGroupExample.Criteria;
import domain.sys.SysUserView;
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
import sys.tool.paging.CommonList;
import sys.utils.ContentTypeUtils;
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
public class ScGroupController extends ScGroupBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroup:list")
    @RequestMapping("/scGroup")
    public String scGroup(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 1) {

            modelMap.put("scGroupFile", scGroupFileService.getCurrent());
            return "/sc/scGroup/scGroup/scGroup_info";
        }

        if (cls == 3) {
            return "forward:/sc/scGroupTopic";
        }

        return "sc/scGroup/scGroup/scGroup_page";
    }

    @RequiresPermissions("scGroup:list")
    @RequestMapping("/scGroup_data")
    public void scGroup_data(HttpServletResponse response,
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

        ScGroupExample example = new ScGroupExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
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
            scGroup_export(example, response);
            return;
        }

        long count = scGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroup> records= scGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scGroup.class, scGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scGroup:edit")
    @RequestMapping(value = "/scGroup_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroup_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scGroup");

        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("filePath", savePath);

        return resultMap;
    }

    @RequiresPermissions("scGroup:edit")
    @RequestMapping(value = "/scGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroup_au(ScGroup record,
                             MultipartFile _wordFilePath,
                             MultipartFile _logFile,
                             @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                             HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        record.setWordFilePath(upload(_wordFilePath, "scGroup-word"));
        record.setLogFile(uploadPdf(_logFile, "scGroup-log"));

        if (id == null) {
            record.setIsDeleted(false);
            scGroupService.insertSelective(record, userIds);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "添加干部小组会：%s", record.getId()));
        } else {

            scGroupService.updateByPrimaryKeySelective(record, userIds);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "更新干部小组会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroup:edit")
    @RequestMapping("/scGroup_au")
    public String scGroup_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScGroup scGroup = scGroupMapper.selectByPrimaryKey(id);
            modelMap.put("scGroup", scGroup);

            if(scGroup!=null){

                List<SysUserView> memberUserList = scGroupService.getMemberUserList(id);
                modelMap.put("memberUserList", memberUserList);
            }
        }
        return "sc/scGroup/scGroup/scGroup_au";
    }

    @RequiresPermissions("scGroup:del")
    @RequestMapping(value = "/scGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_GROUP, "批量删除干部小组会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scGroup_export(ScGroupExample example, HttpServletResponse response) {

        List<ScGroup> records = scGroupMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"干部小组会议题|100","年份|100","干部小组会日期|100","议题数量|100","议题word版|100","会议记录|100","列席人|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScGroup record = records.get(i);
            String[] values = {
                record.getFilePath(),
                            record.getYear()+"",
                            DateUtils.formatDate(record.getHoldDate(), DateUtils.YYYY_MM_DD),
                            record.getTopicNum()+"",
                            record.getWordFilePath(),
                            record.getLogFile(),
                            record.getAttendUsers()
            };
            valuesList.add(values);
        }
        String fileName = "干部小组会_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
