package controller.sys;

import controller.BaseController;
import domain.sys.AttachFile;
import domain.sys.AttachFileExample;
import domain.sys.AttachFileExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AttachFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("attachFile:list")
    @RequestMapping("/attachFile")
    public String attachFile() {

        return "index";
    }

    @RequiresPermissions("attachFile:list")
    @RequestMapping("/attachFile_page")
    public String attachFile_page() {

        return "sys/attachFile/attachFile_page";
    }

    @RequiresPermissions("attachFile:list")
    @RequestMapping("/attachFile_data")
    public void attachFile_data(HttpServletResponse response,
                                Integer userId,
                                Byte type,
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        AttachFileExample example = new AttachFileExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        int count = attachFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<AttachFile> attachFiles = attachFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", attachFiles);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(attachFile.class, attachFileMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("attachFile:edit")
    @RequestMapping(value = "/attachFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_attachFile_au(AttachFile record,
                                MultipartFile _file, HttpServletRequest request) {

        if(_file!=null){

            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());

            String ext = FileUtils.getExtention(originalFilename);
            record.setExt(ext);

            String saveUrl = FILE_SEPARATOR
                    + "attach_file" + FILE_SEPARATOR + ymd + FILE_SEPARATOR + fileName + ext;

            FileUtils.copyFile(_file, new File(springProps.uploadPath + saveUrl));

            if(StringUtils.isBlank(record.getFilename()))
                record.setFilename(FileUtils.getFileName(originalFilename));

            record.setPath(saveUrl);
        }

        Integer id = record.getId();

        if (id == null) {

            if (StringUtils.isBlank(record.getCode()))
                record.setCode(attachFileService.genCode());

            record.setUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(new Date());

            attachFileService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加系统附件%s", record.getId()));
        } else {

            attachFileService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新系统附件%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("attachFile:edit")
    @RequestMapping("/attachFile_au")
    public String attachFile_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            AttachFile attachFile = attachFileMapper.selectByPrimaryKey(id);
            modelMap.put("attachFile", attachFile);
        }
        return "sys/attachFile/attachFile_au";
    }

    @RequiresPermissions("attachFile:del")
    @RequestMapping(value = "/attachFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_attachFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            attachFileService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除系统附件%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("attachFile:del")
    @RequestMapping(value = "/attachFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            attachFileService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除系统附件%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
