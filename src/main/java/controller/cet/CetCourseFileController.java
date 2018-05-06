package controller.cet;

import domain.cet.CetCourseFile;
import domain.cet.CetCourseFileExample;
import domain.cet.CetCourseFileExample.Criteria;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetCourseFileController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetCourseFile")
    public String cetCourseFile(Integer courseId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetCourseFileExample example = new CetCourseFileExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (courseId != null) {
            criteria.andCourseIdEqualTo(courseId);
        }

        long count = cetCourseFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourseFile> records = cetCourseFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cetCourseFiles", records);
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("commonList", commonList);

        return "cet/cetCourseFile/cetCourseFile_page";
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping(value = "/cetCourseFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseFile_au(int courseId,
                                   MultipartFile _file,
                                   String fileName,
                                   Boolean hasPaper,
                                   String paperNote,
                                   HttpServletRequest request) throws IOException, InterruptedException {

        CetCourseFile record = new CetCourseFile();
        record.setCourseId(courseId);
        record.setFileName(fileName);
        record.setHasPaper(BooleanUtils.isTrue(hasPaper));
        record.setPaperNote(paperNote);

        if (_file != null && !_file.isEmpty()) {

            record.setFilePath(uploadDocOrPdf(_file, "cetCourseFile"));
        }else if(!record.getHasPaper()){

            return failed("上传文件和发放纸质学习材料两项内容至少有一个。");
        }

        if (StringUtils.isBlank(record.getFileName())) {
            record.setFileName(FileUtils.getFileName(_file.getOriginalFilename()));
        }

        cetCourseFileService.insertSelective(record);

        logger.info(addLog(LogConstants.LOG_CET, "添加学习内容：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:edit")
    @RequestMapping("/cetCourseFile_au")
    public String cetCourseFile_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetCourseFile cetCourseFile = cetCourseFileMapper.selectByPrimaryKey(id);
            modelMap.put("cetCourseFile", cetCourseFile);
        }
        return "cet/cetCourseFile/cetCourseFile_au";
    }

    @RequiresPermissions("cetCourse:del")
    @RequestMapping(value = "/cetCourseFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetCourseFileService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除学习内容：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:changeOrder")
    @RequestMapping(value = "/cetCourseFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetCourseFileService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "学习内容调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetCourseFile_export(CetCourseFileExample example, HttpServletResponse response) {

        List<CetCourseFile> records = cetCourseFileMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属课程|100", "材料名称|100", "材料|100", "排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetCourseFile record = records.get(i);
            String[] values = {
                    record.getCourseId() + "",
                    record.getFileName(),
                    record.getFilePath(),
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "学习内容_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
