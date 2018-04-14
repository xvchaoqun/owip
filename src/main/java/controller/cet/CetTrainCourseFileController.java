package controller.cet;

import domain.cet.CetTrainCourseFile;
import domain.cet.CetTrainCourseFileExample;
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
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainCourseFileController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourseFile")
    public String cetTrainCourseFile(Integer trainCourseId,
                                     HttpServletResponse response,
                                     Integer pageSize, Integer pageNo, ModelMap modelMap)  throws IOException{

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainCourseFileExample example = new CetTrainCourseFileExample();
        if(trainCourseId!=null)
            example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);
        example.setOrderByClause("id desc");

        long count = cetTrainCourseFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourseFile> records= cetTrainCourseFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cetTrainCourseFiles", records);
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("commonList", commonList);

        return "cet/cetTrainCourse/cetTrainCourseFile";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourseFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourseFile_au( MultipartFile[] _files,
                                         int trainCourseId,
                                         HttpServletRequest request) throws IOException, InterruptedException {

        List<CetTrainCourseFile> records = new ArrayList<>();
        if(_files!=null) {
            for (MultipartFile _file : _files) {

                if (_file == null || _file.isEmpty()) continue;

                CetTrainCourseFile record = new CetTrainCourseFile();
                record.setTrainCourseId(trainCourseId);
                String originalFilename = _file.getOriginalFilename();
                String savePath = uploadPdf(_file, "cetTrainCourseFile");
                record.setFilePath(savePath);
                if (StringUtils.isBlank(record.getFileName())) {
                    record.setFileName(FileUtils.getFileName(originalFilename));
                }
                records.add(record);
            }
        }

        cetTrainCourseService.batchAddFile(records);

        logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量添加培训课程文件：%s",  JSONUtils.toString(records, false)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourseFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourseFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cetTrainCourseService.delFile(id);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "删除培训课程文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
