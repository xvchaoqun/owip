package controller.crs;

import controller.global.OpException;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import domain.crs.CrsPostExample.Criteria;
import domain.crs.CrsPostExpert;
import domain.crs.CrsPostExpertExample;
import domain.crs.CrsPostWithBLOBs;
import domain.crs.CrsTemplate;
import mixin.MixinUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.ContentTypeUtils;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CrsPostController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost")
    public String crsPost(@RequestParam(required = false, defaultValue = "1") Byte status,
                          Integer expertUserId,
                          ModelMap modelMap) {

        if (expertUserId != null) {
            modelMap.put("sysUser", sysUserService.findById(expertUserId));
        }
        modelMap.put("status", status);

        return "crs/crsPost/crsPost_page";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_data")
    public void crsPost_data(HttpServletResponse response,
                             Integer year,
                             String name,
                             Integer expertUserId, // 专家
                             @RequestDateRange DateRange meetingTime,
                             @RequestParam(required = false, defaultValue = "1") Byte status,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostExample example = new CrsPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (status != -1) criteria.andStatusEqualTo(status);

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (meetingTime.getStart() != null) {
            criteria.andMeetingTimeGreaterThanOrEqualTo(meetingTime.getStart());
        }

        if (meetingTime.getEnd() != null) {
            criteria.andMeetingTimeLessThanOrEqualTo(meetingTime.getEnd());
        }

        // 查询专家参与的招聘
        if (expertUserId != null) {

            List<Integer> crsPostIds = new ArrayList<>();
            CrsPostExpertExample example2 = new CrsPostExpertExample();
            example2.createCriteria().andUserIdEqualTo(expertUserId);
            List<CrsPostExpert> crsPostExperts = crsPostExpertMapper.selectByExample(example2);
            for (CrsPostExpert crsPostExpert : crsPostExperts) {
                crsPostIds.add(crsPostExpert.getPostId());
            }
            if (crsPostIds.size() > 0) {
                criteria.andIdIn(crsPostIds);
            } else {
                criteria.andIdIsNull();
            }

            // 不看已删除的
            criteria.andStatusIn(Arrays.asList(CrsConstants.CRS_POST_STATUS_NORMAL,
                    CrsConstants.CRS_POST_STATUS_FINISH));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsPost_export(example, response);
            return;
        }

        long count = crsPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsPost.class, crsPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_au(CrsPostWithBLOBs record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            crsPostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加岗位：%s", record.getId()));
        } else {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            if (crsPost.getType().intValue() != record.getType()
                    || crsPost.getYear().intValue() != record.getYear()) { // 修改了类型或年份，要修改编号
                record.setSeq(crsPostService.genSeq(record.getType(), record.getYear()));
            }

            crsPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新岗位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_au")
    public String crsPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_au";
    }

    private String uploadFile(MultipartFile _file) {

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }

        String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");

        String fileName = UUID.randomUUID().toString();
        String realPath = FILE_SEPARATOR
                + "crs_post_notice" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                + "file" + FILE_SEPARATOR
                + fileName;
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

        try {
            String swfPath = realPath + ".swf";
            pdf2Swf(savePath, swfPath);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }

        return savePath;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_uploadNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_uploadNotice(Integer id, MultipartFile file) {

        String savePath = uploadFile(file);

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setNotice(savePath);
        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "上传招聘公告：%s", id));

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", savePath);

        return resultMap;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_templateContent")
    public String crsPost_templateContent(Integer id, byte type, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
            Map<Integer, CrsTemplate> templateMap
                    = crsTemplateService.findAll(type);
            modelMap.put("templateMap", templateMap);
        }
        return "crs/crsPost/crsPost_templateContent";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_templateContent", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_templateContent(Integer id, byte type, String content) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        if (type == CrsConstants.CRS_TEMPLATE_TYPE_BASE) {
            record.setRequirement(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_POST) {
            record.setQualification(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_MEETINGNOTICE) {
            record.setMeetingNotice(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_POST_DUTY) {
            record.setPostDuty(content);
        }

        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位%s：%s",
                CrsConstants.CRS_TEMPLATE_TYPE_MAP.get(type), id));

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_meetingSummary")
    public String crsPost_meetingSummary(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_meetingSummary";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_meetingSummary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_meetingSummary(Integer id, String meetingSummary) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setMeetingSummary(meetingSummary);

        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位会议备忘：%s", id));

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_publish", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_publish(HttpServletRequest request, Integer id, Boolean publish, ModelMap modelMap) {

        if (id != null) {

            publish = BooleanUtils.isTrue(publish);
            CrsPostWithBLOBs record = new CrsPostWithBLOBs();
            record.setId(id);
            record.setPubStatus(publish ? CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED
                    : CrsConstants.CRS_POST_PUB_STATUS_CANCEL);
            /*if (!publish) {
                record.setStatus(CrsConstants.CRS_POST_STATUS_DELETE);
            }*/

            crsPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, (BooleanUtils.isTrue(publish) ? "发布" : "取消发布") + "岗位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_pptUploadClosed", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_pptUploadClosed(HttpServletRequest request, Integer id, Boolean closed, ModelMap modelMap) {

        if (id != null) {

            closed = BooleanUtils.isTrue(closed);
            CrsPostWithBLOBs record = new CrsPostWithBLOBs();
            record.setId(id);
            record.setPptUploadClosed(closed);

            crsPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, (BooleanUtils.isTrue(closed) ? "关闭" : "开启") + "ppt上传，岗位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:del")
    @RequestMapping(value = "/crsPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_batchDel(HttpServletRequest request,
                                boolean isAbolish,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            crsPostService.batchDel(ids, isAbolish?CrsConstants.CRS_POST_STATUS_ABOLISH:CrsConstants.CRS_POST_STATUS_DELETE);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除岗位[%s]：%s", isAbolish?"作废":"假删除",
                    StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:del")
    @RequestMapping(value = "/crsPost_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_realDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            crsPostService.realDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除岗位：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }


    public void crsPost_export(CrsPostExample example, HttpServletResponse response) {

        List<CrsPostWithBLOBs> records = crsPostMapper.selectByExampleWithBLOBs(example);
        int rownum = records.size();
        String[] titles = {"年度", "招聘岗位", "行政级别", "所属单位", "基本条件", "任职资格", "报名情况", "常委会情况", "备注", "状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsPostWithBLOBs record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getName(),
                    record.getAdminLevel() + "",
                    record.getUnitId() + "",
                    record.getRequirement(),
                    record.getQualification(),
                    record.getEnrollStatus() + "",
                    record.getCommitteeStatus() + "",
                    record.getRemark(),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = "岗位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/crsPost_selects")
    @ResponseBody
    public Map crsPost_selects(Byte status, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostExample example = new CrsPostExample();
        if(StringUtils.isNotBlank(searchStr))
            example.createCriteria().andNameLike("%" + searchStr + "%");
        example.setOrderByClause("create_time desc");

        long count = crsPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPost> crsPosts = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != crsPosts && crsPosts.size() > 0) {

            for (CrsPost crsPost : crsPosts) {

                Map<String, String> option = new HashMap<>();
                option.put("id", crsPost.getId() + "");
                option.put("text", crsPost.getName());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
