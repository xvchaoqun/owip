package controller.crs;

import domain.crs.CrsPostFile;
import org.apache.commons.lang3.StringUtils;
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
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CrsPostFileController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPostFile:list")
    @RequestMapping("/crsPostFile")
    public String crsPostFile(int postId, ModelMap modelMap) {

        modelMap.put("crsPost", crsPostMapper.selectByPrimaryKey(postId));

        List<CrsPostFile> images = crsPostFileService.getPostFiles(postId, CrsConstants.CRS_POST_FILE_TYPE_PIC);
        List<CrsPostFile> audios = crsPostFileService.getPostFiles(postId, CrsConstants.CRS_POST_FILE_TYPE_AUDIO);

        modelMap.put("images", images);
        modelMap.put("audios", audios);

        return "crs/crsPostFile/crsPostFile_page";
    }

   /* @RequiresPermissions("crsPostFile:list")
    @RequestMapping("/crsPostFile_data")
    public void crsPostFile_data(HttpServletResponse response,
                                 Integer postId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostFileExample example = new CrsPostFileExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }

        long count = crsPostFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPostFile> records = crsPostFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsPostFile.class, crsPostFileMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }*/

    @RequiresPermissions("crsPostFile:edit")
    @RequestMapping(value = "/crsPostFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostFile_au(CrsPostFile record, MultipartFile _file, HttpServletRequest request) {

        Integer id = record.getId();

        if(_file!=null){

            String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");
            String realPath = FILE_SEPARATOR
                    + "crs_post_file" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + UUID.randomUUID().toString();
            String originalFilename = _file.getOriginalFilename();
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            record.setFile(savePath);

            if(StringUtils.isBlank(record.getFileName())){
                record.setFileName(FileUtils.getFileName(originalFilename));
            }
        }

        if (id == null) {
            record.setCreateTime(new Date());
            crsPostFileService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘会记录文件：%s", record.getId()));
        } else {

            crsPostFileService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘会记录文件：%s", record.getId()));
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("record", record);
        return resultMap;
    }

    // 批量上传图片或音频
    @RequiresPermissions("crsPostFile:edit")
    @RequestMapping(value = "/crsPostFile_batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostFile_batchUpload(byte type, int postId, MultipartFile[] _files, HttpServletRequest request) throws IOException, InterruptedException {

        List<CrsPostFile> records = new ArrayList<>();
        if(_files!=null){
            for (MultipartFile _file : _files) {


                if(_file==null || _file.isEmpty()) continue;

                CrsPostFile record = new CrsPostFile();
                record.setType(type);
                record.setPostId(postId);
                record.setCreateTime(new Date());

                /*String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");
                String realPath = FILE_SEPARATOR
                        + "crs_post_file" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                        + "file" + FILE_SEPARATOR
                        + UUID.randomUUID().toString();
                String originalFilename = _file.getOriginalFilename();
                String savePath = realPath + FileUtils.getExtention(originalFilename);
                FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));*/

                String savePath = null;
                if(type==CrsConstants.CRS_POST_FILE_TYPE_PIC) {
                    savePath = uploadPic(_file, "crs_post_file", 300, 100);
                }else if(type==CrsConstants.CRS_POST_FILE_TYPE_AUDIO) {
                    savePath = upload(_file, "crs_post_file");
                }
                record.setFile(savePath);

                if(StringUtils.isBlank(record.getFileName())){
                    record.setFileName(_file.getOriginalFilename());
                }

                records.add(record);
            }
        }

        crsPostFileService.batchAdd(records);
        logger.info(addLog(LogConstants.LOG_CRS, "批量添加招聘会记录文件：%s", JSONUtils.toString(records, false)));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("records", records);
        return resultMap;
    }

    @RequiresPermissions("crsPostFile:edit")
    @RequestMapping("/crsPostFile_au")
    public String crsPostFile_au(Integer id, Integer postId, ModelMap modelMap) {

        if (id != null) {
            CrsPostFile crsPostFile = crsPostFileMapper.selectByPrimaryKey(id);
            modelMap.put("crsPostFile", crsPostFile);

            if (crsPostFile != null) {
                postId = crsPostFile.getPostId();
            }
        }

        modelMap.put("postId", postId);

        return "crs/crsPostFile/crsPostFile_au";
    }

    @RequiresPermissions("crsPostFile:del")
    @RequestMapping(value = "/crsPostFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            crsPostFileService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘会记录文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPostFile:del")
    @RequestMapping(value = "/crsPostFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crsPostFileService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘会记录文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
