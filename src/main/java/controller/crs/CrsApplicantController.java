package controller.crs;

import controller.global.OpException;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsPost;
import freemarker.template.TemplateException;
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
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.ContentTypeUtils;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CrsApplicantController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping("/crsApplicant")
    public String crsApplicant( @RequestParam(required = false, defaultValue = "1") int cls,
                                int postId, Integer userId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("crsPost", crsPostService.get(postId));
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "crs/crsApplicant/crsApplicant_page";
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping("/crsApplicant_data")
    public void crsApplicant_data(HttpServletResponse response,
                                  @RequestParam(required = false, defaultValue = "1") int cls,
                                  int postId,
                                  Integer userId,
                                  @RequestDateRange DateRange enrollTime,
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

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        CrsApplicantViewExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        example.setOrderByClause("sort_order desc, enroll_time asc");

        switch (cls) {
            case 1:
                criteria.andRequireCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT)
                        .andIsQuitEqualTo(false).andIsRequireCheckPassEqualTo(false);
                break;
            case 2: // 资格审核通过 或 破格
                criteria.andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false);
                break;
            case 3:
                criteria.andIsRequireCheckPassEqualTo(false).andIsQuitEqualTo(false)
                        .andRequireCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS);
                break;
            case 4:
                criteria.andIsQuitEqualTo(true);
                break;
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (enrollTime.getStart() != null) {
            criteria.andEnrollTimeGreaterThanOrEqualTo(enrollTime.getStart());
        }

        if (enrollTime.getEnd() != null) {
            criteria.andEnrollTimeLessThanOrEqualTo(enrollTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsExportService.exportApplicants(postId, example, response);
            return;
        }

        long count = crsApplicantViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsApplicantView> records = crsApplicantViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsApplicant.class, crsApplicantMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping("/crsApplicant_search")
    public String crsApplicant_search() {

        return "crs/crsApplicant/crsApplicant_search";
    }

    @RequiresPermissions("crsApplicant:list")
    @RequestMapping(value = "/crsApplicant_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_search(int userId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);

        List<Map> hasApplyPosts = iCrsMapper.hasApplyPosts(userId, CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        for (Map hasApplyPost : hasApplyPosts) {
            int postId = ((Long) hasApplyPost.get("postId")).intValue();
            //boolean isQuit = (boolean) hasApplyPost.get("isQuit");
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
            hasApplyPost.put("postName", crsPost.getName());
        }

        resultMap.put("hasApplyPosts", hasApplyPosts);
        return resultMap;
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping(value = "/crsApplicant_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_au(Integer id, int postId, int userId, String career,
                                  String report, HttpServletRequest request) {

        crsApplicantService.apply(id, postId,
                CrsConstants.CRS_APPLICANT_STATUS_SUBMIT, career, report, userId);
        logger.info(addLog(LogConstants.LOG_CRS, "添加报名人员：%s, %s", postId, userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping("/crsApplicant_au")
    public String crsApplicant_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            modelMap.put("crsApplicant", crsApplicant);
        }
        return "crs/crsApplicant/crsApplicant_au";
    }

    // 导出应聘人报名表
    @RequiresPermissions("crsApplicant:export")
    @RequestMapping("/crsApplicant_export")
    public void crsApplicant_export(int postId, @RequestParam(required = false, value = "ids[]") Integer[] ids,
                                    HttpServletResponse response) throws IOException, TemplateException {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        //输出文件
        String filename = crsPost.getName();
        if(ids!=null && ids.length==1){
            Integer id = ids[0];
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            String realname = crsApplicant.getCadre().getRealname();
            filename += "—" + realname;
        }

        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        crsExportService.process(postId, ids, response.getWriter());
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping("/crsApplicant_recommend")
    public String crsApplicant_recommend(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            modelMap.put("crsApplicant", crsApplicant);
        }
        return "crs/crsApplicant/crsApplicant_recommend";
    }

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping(value = "/crsApplicant_recommend", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_recommend(CrsApplicant record,
                                         String filePath,
                                         HttpServletRequest request) {

        record.setRecommendPdf(filePath);
        crsApplicantCheckService.recommend(record);

        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位报名自荐/推荐：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    private String uploadFile(MultipartFile _file) {

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");

        String fileName = UUID.randomUUID().toString();
        String realPath = FILE_SEPARATOR
                + "crs_applicant_recommend" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
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

    @RequiresPermissions("crsApplicant:edit")
    @RequestMapping(value = "/crsApplicant_recommend_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_recommend_upload(MultipartFile file) throws InterruptedException {

        String savePath = uploadFile(file);
        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", savePath);

        return resultMap;
    }

    @RequiresPermissions("crsApplicant:changeOrder")
    @RequestMapping(value = "/crsApplicant_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsApplicantService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_CRS, "资格审核（通过）调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("crsApplicant:del")
    @RequestMapping(value = "/crsApplicant_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplicant_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            crsApplicantService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除报名人员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplicant:del")
    @RequestMapping(value = "/crsApplicant_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crsApplicantService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除报名人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
