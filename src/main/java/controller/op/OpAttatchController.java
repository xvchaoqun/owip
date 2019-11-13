package controller.op;

import domain.op.OpAttatch;
import domain.op.OpRecord;
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
import sys.tags.CmTag;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/op")
public class OpAttatchController extends OpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("opAttatch:edit")
    @RequestMapping("/opAttatch")
    public String opFiles(Integer recordId, ModelMap modelMap) {

        OpRecord opRecord = opRecordMapper.selectByPrimaryKey(recordId);
        modelMap.put("sysUser", CmTag.getUserById(opRecord.getUserId()));
        modelMap.put("opRecord", opRecord);
        modelMap.put("opAttatchs", opAttatchService.getByRecordId(recordId));

        return "op/opAttatch/opAttatch";
    }

    @RequiresPermissions("opAttatch:edit")
    @RequestMapping(value = "/opAttatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskFile_au(int recordId, MultipartFile[] files, HttpServletRequest request) throws IOException, InterruptedException {

        if (files == null || files.length == 0) {
            return failed("附件不能为空。");
        }

        //psTaskService.checkAuth(taskId, null);

        for (MultipartFile file : files) {

            OpAttatch record = new OpAttatch();
            record.setRecordId(recordId);
            record.setFileName(file.getOriginalFilename());
            record.setFilePath(upload(file, "op_attatch_file"));

            opAttatchMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_OP, "添加组织处理附件：%s", record.getFileName()));

        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("opAttatch:edit")
    @RequestMapping(value = "/opAttatch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_opAttatch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            opAttatchService.del(id);
            logger.info(log( LogConstants.LOG_OP, "删除组织处理附件：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
