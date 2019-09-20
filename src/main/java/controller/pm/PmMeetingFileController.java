package controller.pm;

import domain.pm.PmMeetingFile;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller

public class PmMeetingFileController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeetingFile")
    public String pmMeetingFile(Integer id,ModelMap modelMap) {

        modelMap.put("pmMeeting", pmMeetingMapper.selectByPrimaryKey(id));
        modelMap.put("pmMeetingFiles", pmMeetingFileService.getMeetingFiles(id));

        return "pm/pmMeeting/pmMeetingFile";
    }

    @RequestMapping(value = "/pmMeetingFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskFile_au(int id, MultipartFile[] files, HttpServletRequest request) throws IOException, InterruptedException {

        if (files == null || files.length == 0) {
            return failed("附件不能为空。");
        }

        pmMeetingFileService.insertMeetingFiles(id,files);
        logger.info(addLog(LogConstants.LOG_PM, "添加三会一课附件"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping(value = "/pmMeetingFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmMeetingFile_del(HttpServletRequest request, Integer id) {

        PmMeetingFile pmMeetingFile = pmMeetingFileMapper.selectByPrimaryKey(id);

        pmMeetingFileMapper.deleteByPrimaryKey(id);
        logger.info(addLog(LogConstants.LOG_OA, "删除协同办公任务附件：%s", pmMeetingFile.getFileName()));

        return success(FormUtils.SUCCESS);
    }
}
