package controller.sys;

import controller.BaseController;
import domain.sys.AttachFile;
import domain.sys.AttachFileExample;
import domain.sys.AttachFileExample.Criteria;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Controller
public class AttachFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("attachFile:list")
    @RequestMapping("/attachFile")
    public String attachFile(@RequestParam(required = false, defaultValue = "0") Boolean isDeleted,
                             ModelMap modelMap) {

        modelMap.put("isDeleted",isDeleted);
        return "sys/attachFile/attachFile_page";
    }

    @RequiresPermissions("attachFile:list")
    @RequestMapping("/attachFile_data")
    public void attachFile_data(HttpServletResponse response,
                                Integer userId,
                                String filename,
                                String code,
                                Byte type,
                                @RequestParam(required = false, defaultValue = "0") Boolean isDeleted,
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
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(filename)) {
            criteria.andFilenameLike(SqlUtils.like(filename));
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isDeleted != null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }
        int count = (int) attachFileMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(attachFile.class, attachFileMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("attachFile:edit")
    @RequestMapping(value = "/attachFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_attachFile_au(AttachFile record,
                                MultipartFile _file, HttpServletRequest request) throws IOException, InterruptedException {

        if (_file != null) {

            String savePath = null;
            if (record.getType() == SystemConstants.ATTACH_FILE_TYPE_PDF) {
                savePath = uploadPdf(_file, "attach_file");
            } else {
                savePath = upload(_file, "attach_file");
            }
            record.setPath(savePath);

            String originalFilename = _file.getOriginalFilename();
            String ext = FileUtils.getExtention(originalFilename);
            record.setExt(ext);

            if (StringUtils.isBlank(record.getFilename())) {
                record.setFilename(FileUtils.getFileName(originalFilename));
            }
        }

        Integer id = record.getId();

        if (id == null) {

            if (StringUtils.isBlank(record.getCode()))
                record.setCode(attachFileService.genCode());

            record.setUserId(ShiroHelper.getCurrentUserId());
            record.setCreateTime(new Date());

            attachFileService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加系统附件%s", record.getFilename()));
        } else {

            attachFileService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新系统附件%s", record.getId()));
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
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除系统附件%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    //删除系统附件
    @RequiresPermissions("attachFile:del")
    @RequestMapping(value = "/attachFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            attachFileService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除系统附件%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //彻底删除系统附件
    @RequiresPermissions("attachFile:del")
    @RequestMapping(value = "/attachFile_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map doBatchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            attachFileService.doBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "彻底删除系统附件%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //返回列表
    @RequiresPermissions("attachFile:del")
    @RequestMapping(value = "/attachFile_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            attachFileService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "返回系统附件%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("attachFile:edit")
    @RequestMapping(value = "/attachFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_attachFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        attachFileService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "系统附件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("attachFile:import")
    @RequestMapping("/attachFile_import")
    public String attachFile_import(ModelMap modelMap) {

        return "sys/attachFile/attachFile_import";
    }

    @RequiresPermissions("attachFile:import")
    @RequestMapping(value = "/attachFile_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_attachFile_import(HttpServletRequest request) throws IOException {

        int addCount = 0;
        int updateCount = 0;
        int totalCount = 1;

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xls = multipartRequest.getFile("xls");

        if (xls != null && !xls.isEmpty()) {
            String templateName = xls.getOriginalFilename();
            String code = PatternUtils.withdraw(".*\\[\\s*(\\S*)\\s*\\].*", templateName);
            String fileName = PatternUtils.withdraw(".*\\[.*](.*)\\..*", templateName);
            if (StringUtils.isNotBlank(code)) {

                String ext = FileUtils.getExtention(templateName);
                String savePath = FILE_SEPARATOR + "attach_file" +
                        FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                        FILE_SEPARATOR + UUID.randomUUID().toString() + ext;
                FileUtils.saveFile(xls.getInputStream(), springProps.uploadPath + savePath);

                AttachFile record = new AttachFile();
                record.setCode(code);
                record.setFilename(fileName);
                record.setPath(savePath);
                record.setExt(ext);
                record.setCreateTime(new Date());

                AttachFile attachFile = attachFileService.get(code);
                if (attachFile != null) {
                    record.setId(attachFile.getId());
                    attachFileMapper.updateByPrimaryKeySelective(record);
                    updateCount++;
                } else {

                    attachFileService.insertSelective(record);
                    addCount++;
                }
            }
        } else {

            MultipartFile zip = multipartRequest.getFile("zip");

            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "attachFiles";
            FileUtils.mkdirs(tmpdir, false);

            String filepath = tmpdir + FILE_SEPARATOR + zip.getOriginalFilename();
            FileUtils.copyFile(zip, new File(filepath));
            ZipFile zipFile = new ZipFile(filepath, Charset.forName("gbk"));


            for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {

                ZipEntry zipEntry = (ZipEntry) e.nextElement();
                if (!zipEntry.isDirectory()) {

                    String templateName = zipEntry.getName();
                    String code = PatternUtils.withdraw(".*\\[\\s*(\\S*)\\s*\\].*", templateName);
                    String fileName = PatternUtils.withdraw(".*\\[.*](.*)\\..*", templateName);
                    if (StringUtils.isNotBlank(code)) {

                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        String ext = FileUtils.getExtention(templateName);
                        String savePath = FILE_SEPARATOR + "attach_file" +
                                FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                                FILE_SEPARATOR + UUID.randomUUID().toString() + ext;
                        FileUtils.saveFile(inputStream, springProps.uploadPath + savePath);

                        AttachFile record = new AttachFile();
                        record.setCode(code);
                        record.setFilename(fileName);
                        record.setPath(savePath);
                        record.setExt(ext);
                        record.setCreateTime(new Date());

                        AttachFile attachFile = attachFileService.get(code);
                        if (attachFile != null) {
                            record.setId(attachFile.getId());
                            attachFileMapper.updateByPrimaryKeySelective(record);
                            updateCount++;
                        } else {

                            attachFileService.insertSelective(record);
                            addCount++;
                        }
                    }
                }
            }

            FileUtils.deleteDir(new File(tmpdir));

            totalCount = zipFile.size();
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("updateCount", updateCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入附件成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
