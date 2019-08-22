package controller.pm;

import domain.member.Member;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;
import domain.pm.PmMeetingFileExample;
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
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.PmConstants.*;
import static sys.constants.RoleConstants.ROLE_ADMIN;
import static sys.constants.RoleConstants.ROLE_ODADMIN;


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
