package controller.crs;

import domain.crs.CrsApplicantStatView;
import domain.crs.CrsApplicantStatViewExample;
import domain.crs.CrsCandidateView;
import domain.crs.CrsCandidateViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.crs.common.CrsStatApplicantBean;
import sys.constants.CadreConstants;
import sys.constants.CrsConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CrsStatController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsStat:*")
    @RequestMapping("/crsStat")
    public String crsStat(@RequestParam(required = false, defaultValue = "1") Byte cls,
                          @RequestParam(required = false, value = "dpTypes") Long[] dpTypes, // 党派
                          @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus, // 最高学历
                          ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        if (maxEdus != null) {
            modelMap.put("selectMaxEdus", Arrays.asList(maxEdus));
        }
        if(cls==2){ // 应聘人报名次数统计
            return "crs/crsStat/crsStatApplicant_page";
        }
        // 历次招聘会前两名汇总
        return "crs/crsStat/crsStatCandidate_page";
    }

    @RequiresPermissions("crsStat:list")
    @RequestMapping("/crsStatCandidate_data")
    public void crsStatCandidate_data(HttpServletResponse response,
                                    Integer year,
                                    Boolean isFirst,
                                  Integer startAge,
                                  Integer endAge,
                                  @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes, // 党派
                                  @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus, // 最高学历
                                  Boolean isMiddle, // 是否现任干部
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsCandidateViewExample example = new CrsCandidateViewExample();
        CrsCandidateViewExample.Criteria criteria = example.createCriteria()
                .andCrsPostStatusEqualTo(CrsConstants.CRS_POST_STATUS_FINISH); // 招聘会完成后
        example.setOrderByClause("crs_post_year desc, crs_post_id desc, is_first desc");

        if(year!=null){
            criteria.andCrsPostYearEqualTo(year);
        }
        if(isFirst!=null){
            criteria.andIsFirstEqualTo(isFirst);
        }
        if (endAge != null) {
            criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endAge+1)));
        }
        if (startAge != null) {
            criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge));
        }
        if (dpTypes != null) {
            criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
        }
        if (maxEdus != null) {
            criteria.andEduIdIn(Arrays.asList(maxEdus));
        }
        if(isMiddle!=null){
            if(isMiddle){
                criteria.andStatusEqualTo(CadreConstants.CADRE_STATUS_CJ);
            }
        }

        long count = crsCandidateViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsCandidateView> records = crsCandidateViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsCandidateView.class, crsCandidateViewMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsStat:list")
    @RequestMapping("/crsStatApplicant_data")
    public void crsStatApplicant_data(HttpServletResponse response,
                                    Integer year,
                                    Boolean isFirst,
                                  Integer startAge,
                                  Integer endAge,
                                  @RequestParam(required = false, value = "dpTypes") Long[] dpTypes, // 党派
                                  @RequestParam(required = false, value = "maxEdus") Integer[] maxEdus, // 最高学历
                                  Boolean isMiddle,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        Byte applicantStatus = CrsConstants.CRS_APPLICANT_STATUS_SUBMIT;
        Byte postStatus = CrsConstants.CRS_POST_STATUS_FINISH;
        long count = iCrsMapper.countStatApplicantList(applicantStatus, postStatus);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsStatApplicantBean> records = iCrsMapper.selectStatApplicantList(applicantStatus, postStatus, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsCandidateView.class, crsCandidateViewMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsStat:*")
    @RequestMapping("/crsStatApplicant_detail")
    public String crsStatApplicant_detail(int userId, ModelMap modelMap) {

        return "crs/crsStat/crsStatApplicant_detail";
    }

    @RequiresPermissions("crsStat:list")
    @RequestMapping("/crsStatApplicant_detail_data")
    public void crsStatApplicant_detail_data(HttpServletResponse response,
                                             int userId, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsApplicantStatViewExample example = new CrsApplicantStatViewExample();
        CrsApplicantStatViewExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        example.setOrderByClause("enroll_time asc");


        long count = crsApplicantStatViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsApplicantStatView> records = crsApplicantStatViewMapper.selectByExampleWithRowbounds(example,
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
}
