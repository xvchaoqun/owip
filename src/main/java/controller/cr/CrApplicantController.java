package controller.cr;

import domain.cadre.CadreView;
import domain.cr.CrApplicant;
import domain.cr.CrApplicantExample;
import domain.cr.CrApplicantExample.Criteria;
import domain.cr.CrInfo;
import domain.cr.CrPost;
import freemarker.template.TemplateException;
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
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class CrApplicantController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crApplicant:list")
    @RequestMapping("/crApplicant")
    public String crApplicant(Integer infoId, Integer userId,
                              @RequestParam(required = false, value = "postId") Integer[] postId,
                              ModelMap modelMap) {

        modelMap.put("crInfo", crInfoMapper.selectByPrimaryKey(infoId));
        modelMap.put("postMap", crPostService.getPostMap(infoId));

        if (postId != null) {
            List<Integer> selectPostIds = Arrays.asList(postId);
            modelMap.put("selectPostIds", selectPostIds);
        }

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "cr/crApplicant/crApplicant_page";
    }

    @RequiresPermissions("crApplicant:list")
    @RequestMapping("/crApplicant_data")
    @ResponseBody
    public void crApplicant_data(HttpServletResponse response,
                                 Integer infoId,
                                 Integer userId,
                                 @RequestDateRange DateRange submitTime,
                                 Integer firstPostId,
                                 Integer secondPostId,
                                 Boolean hasReport,
                                 @RequestParam(required = false, value = "postId") Integer[] postId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException, TemplateException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrApplicantExample example = new CrApplicantExample();
        Criteria criteria = example.createCriteria().andHasSubmitEqualTo(true);
        example.setOrderByClause("submit_time asc");

        if (infoId != null) {
            criteria.andInfoIdEqualTo(infoId);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (submitTime.getStart() != null) {
            criteria.andSubmitTimeGreaterThanOrEqualTo(submitTime.getStart());
        }

        if (submitTime.getEnd() != null) {
            criteria.andSubmitTimeLessThanOrEqualTo(submitTime.getEnd());
        }

        if (postId != null) {
            List<Integer> selectPostIds = Arrays.asList(postId);
            criteria.andPostIdIn(selectPostIds);
        }

        if(firstPostId!=null){
            criteria.andFirstPostIdEqualTo(firstPostId);
        }
        if(secondPostId!=null){
            criteria.andSecondPostIdEqualTo(secondPostId);
        }

        if(hasReport!=null){
            criteria.andHasReportEqualTo(hasReport);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crExportService.exportApplicants(infoId, example, response);
            return;
        }

        long count = crApplicantMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrApplicant> records = crApplicantMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crApplicant.class, crApplicantMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crApplicant:edit")
    @RequestMapping(value = "/crApplicant_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crApplicant_au(CrApplicant record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            CadreView cv = CmTag.getCadreByUserId(record.getUserId());
            if(cv==null) {
               crsApplicantService.start(record.getUserId());
            }

            record.setHasSubmit(true);
            record.setEnrollTime(new Date());
            record.setSubmitTime(new Date());
            record.setHasReport(false);

            CrApplicant crApplicant = crApplicantService.get(record.getUserId(), record.getInfoId());
            if(crApplicant==null) {
                crApplicantService.insertSelective(record);
            }else{
                // 可能本人已提交
                record.setId(crApplicant.getId());
                crApplicantService.updateByPrimaryKeySelective(record);
            }
            logger.info(log(LogConstants.LOG_CR, "添加报名人员：{0}", record.getId()));
        } else {

            crApplicantService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_CR, "更新报名人员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crApplicant:edit")
    @RequestMapping("/crApplicant_au")
    public String crApplicant_au(Integer id, Integer infoId, ModelMap modelMap) {

        if (id != null) {
            CrApplicant crApplicant = crApplicantMapper.selectByPrimaryKey(id);
            modelMap.put("crApplicant", crApplicant);
            infoId = crApplicant.getInfoId();

            modelMap.put("evas", Arrays.asList(crApplicantService.getEva(infoId, crApplicant.getUserId()).split(",")));
        }

        modelMap.put("infoId", infoId);

        List<CrPost> crPosts = crPostService.getPosts(infoId);
        modelMap.put("crPosts", crPosts);

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        modelMap.put("crInfo", crInfo);

        return "cr/crApplicant/crApplicant_au";
    }

    @RequiresPermissions("crApplicant:del")
    @RequestMapping(value = "/crApplicant_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crApplicant_batchDel(HttpServletRequest request, int infoId, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crApplicantService.batchDel(ids, infoId);
            logger.info(log(LogConstants.LOG_CR, "批量删除报名人员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crApplicant:report")
    @RequestMapping(value = "/crApplicant_report", method = RequestMethod.POST)
    @ResponseBody
    public Map crApplicant_report(HttpServletRequest request, int infoId,
                                  @RequestParam(value = "ids[]") Integer[] ids,
                                  Boolean hasReport,
                                  ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            crApplicantService.report(ids, infoId, BooleanUtils.isTrue(hasReport));
            logger.info(log(LogConstants.LOG_CR, "提交纸质表：{0}, {1}",
                    StringUtils.join(ids, ","), BooleanUtils.isTrue(hasReport)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crApplicant:changeOrder")
    @RequestMapping(value = "/crApplicant_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crApplicant_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crApplicantService.changeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_CR, "报名人员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    
    // 导出应聘人报名表
    @RequiresPermissions("crApplicant:export")
    @RequestMapping("/crApplicant_export")
    public void crApplicant_export(int infoId, @RequestParam(required = false, value = "ids[]") Integer[] ids,
                                    HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        //输出文件
        String filename = String.format("应聘报名表(%s).doc", DateUtils.formatDate(crInfo.getAddDate(), DateUtils.YYYYMMDD));
        if(ids!=null && ids.length==1){
            Integer id = ids[0];
            CrApplicant crApplicant = crApplicantMapper.selectByPrimaryKey(id);
            String realname = crApplicant.getUser().getRealname();
            filename = realname + " " + filename;
        }

        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
        response.setContentType("application/msword;charset=UTF-8");

        crExportService.process(infoId, ids, response.getWriter());
    }
}
