package controller.cr;

import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cr.CrApplicant;
import domain.cr.CrInfo;
import domain.cr.CrInfoExample;
import domain.cr.CrPost;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DownloadUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserCrInfoController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCrInfo:*")
    @RequestMapping("/crPost")
    public String crPost(int infoId, ModelMap modelMap) {

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        modelMap.put("crInfo", crInfo);

        List<CrPost> crPosts = crPostService.getPosts(infoId);
        modelMap.put("crPosts", crPosts);

        return "cr/user/crPost";
    }

    @RequiresPermissions("userCrInfo:edit")
    @RequestMapping(value = "/crInfo_start", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crInfo_start(HttpServletRequest request) {

        int cadreId = crsApplicantService.start(ShiroHelper.getCurrentUserId());
        logger.info(addLog(LogConstants.LOG_USER, "干部招聘-开始采集信息"));

        boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadreId);

        Map<String, Object> result = success(FormUtils.SUCCESS);
        result.put("cadreId", cadreId);
        result.put("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);

        return result;
    }

    @RequiresPermissions("userCrInfo:*")
    @RequestMapping("/crInfo_apply")
    public String crInfo_apply(int infoId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        modelMap.put("crInfo", crInfo);
        int year = crInfo.getYear();
        CrApplicant crApplicant = crApplicantService.get(userId, infoId);
        modelMap.put("crApplicant", crApplicant);

        Cadre cadre = cadreService.getByUserId(userId);
        if(cadre!=null && cadre.getStatus()== CadreConstants.CADRE_STATUS_CJ){
            modelMap.put("cadre", cadre);
        }
        String eva = crApplicantService.getEva(year, cadre, crApplicant);
        modelMap.put("evas", Arrays.asList(eva.split(",")));

        List<CrPost> crPosts = crPostService.getPosts(infoId);
        modelMap.put("crPosts", crPosts);

        CadreView cv = CmTag.getCadreByUserId(userId);
        if(cv!=null) {
            modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cv.getId()));
        }

        return "cr/user/crInfo_apply";
    }

    @RequiresPermissions("userCrInfo:*")
    @RequestMapping(value = "/crInfo_apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crInfo_apply(CrApplicant record, HttpServletRequest request) {

        boolean hasSubmit = BooleanUtils.isTrue(record.getHasSubmit());

        if(record.getId()==null){
            record.setEnrollTime(new Date());
            record.setHasReport(false);
        }
        record.setUserId(ShiroHelper.getCurrentUserId());
        if(hasSubmit){
            record.setSubmitTime(new Date());
        }
        crApplicantService.addOrUpdate(record);

        logger.info(addLog(LogConstants.LOG_USER, "干部应聘报名，%s应聘材料", hasSubmit ? "提交" : "保存"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCrInfo:*")
    @RequestMapping("/crInfo")
    public String crInfo_page(ModelMap modelMap) {

        return "cr/user/crInfo_page";
    }

    @RequiresPermissions("userCrInfo:list")
    @RequestMapping("/crInfo_data")
    public void crInfo_data(HttpServletResponse response, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrInfoExample example = new CrInfoExample();
        CrInfoExample.Criteria criteria = example.createCriteria()
                .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL); // 读取正在招聘的招聘信息
        example.setOrderByClause("year desc, add_date desc");

        long count = crInfoMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrInfo> records = crInfoMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        int userId = ShiroHelper.getCurrentUserId();
        // 已报名的招聘信息
        Map<Integer, CrInfo> hasApplyInfoMap = crApplicantService.hasApplyInfoMap(userId);
        resultMap.put("hasApplyInfoMap", hasApplyInfoMap);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 导出应聘人报名表
    @RequiresPermissions("userCrInfo:export")
    @RequestMapping("/crInfo_export")
    public void crInfo_export(int infoId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        //输出文件
        String filename = CmTag.getSysConfig().getSchoolName() + "干部应聘报名表";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        CrApplicant crApplicant = crApplicantService.get(ShiroHelper.getCurrentUserId(), infoId);
        if(crApplicant==null){
            throw new UnauthorizedException();
        }

        crExportService.process(infoId, new Integer[]{crApplicant.getId()}, response.getWriter());
    }
}
