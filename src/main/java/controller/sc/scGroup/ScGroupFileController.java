package controller.sc.scGroup;

import controller.ScGroupBaseController;
import domain.sc.scGroup.ScGroupFile;
import domain.sc.scGroup.ScGroupFileExample;
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
import sys.constants.SystemConstants;
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
@RequestMapping("/sc")
public class ScGroupFileController extends ScGroupBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroupFile:list")
    @RequestMapping("/scGroupFile_page")
    public String scGroupFile_page(HttpServletResponse response,Integer pageSize, Integer pageNo, ModelMap modelMap)  throws IOException{

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScGroupFileExample example = new ScGroupFileExample();
        example.setOrderByClause("sort_order desc");

        long count = scGroupFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupFile> records= scGroupFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("scGroupFiles", records);
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("commonList", commonList);

        return "sc/scGroup/scGroupFile/scGroupFile_page";
    }

    @RequiresPermissions("scGroupFile:edit")
    @RequestMapping(value = "/scGroupFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupFile_au( MultipartFile[] _files, HttpServletRequest request) throws IOException, InterruptedException {

        List<ScGroupFile> records = new ArrayList<>();
        if(_files!=null) {
            for (MultipartFile _file : _files) {

                if (_file == null || _file.isEmpty()) continue;

                ScGroupFile record = new ScGroupFile();
                String originalFilename = _file.getOriginalFilename();
                String savePath = uploadPdf(_file, "scGroupFile");
                record.setFilePath(savePath);
                if (StringUtils.isBlank(record.getFileName())) {
                    record.setFileName(FileUtils.getFileName(originalFilename));
                }
                record.setIsCurrent(false);
                records.add(record);
            }
        }

        scGroupFileService.batchAdd(records);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "批量添加小组会成立文件：%s",  JSONUtils.toString(records, false)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupFile:del")
    @RequestMapping(value = "/scGroupFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            scGroupFileService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除小组会成立文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupFile:edit")
    @RequestMapping(value = "/scGroupFile_setCurrent", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupFile_setCurrent(HttpServletRequest request, Integer id) {

        if (id != null) {
            scGroupFileService.setCurrent(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "应用小组会成立文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupFile:changeOrder")
    @RequestMapping(value = "/scGroupFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scGroupFileService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_SC_GROUP, "干部工作小组会成立文件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
