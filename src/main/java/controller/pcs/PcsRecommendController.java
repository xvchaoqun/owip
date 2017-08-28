package controller.pcs;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.member.MemberTeacher;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsCandidateView;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsRecommendController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend")
    public String pcsRecommend() {

        return "pcs/pcsRecommend/pcsRecommend_page";
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_data")
    public void pcsRecommend_data(HttpServletResponse response,
                                  byte stage,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsAdmin.getConfigId();
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iPcsMapper.countPcsBranchBeans(partyId, null);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsBranchBean> records =
                iPcsMapper.selectPcsBranchBeans(configId, stage, partyId, null,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsRecommend_au(byte stage, int partyId, Integer branchId,
                                  int expectMemberCount, int actualMemberCount, boolean isFinish,
                                  @RequestParam(required = false, value = "dwCandidateIds[]") String[] dwCandidateIds,
                                  @RequestParam(required = false, value = "jwCandidateIds[]") String[] jwCandidateIds,
                                  HttpServletRequest request) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if(pcsAdmin.getPartyId() != partyId){
            throw new UnauthorizedException();
        }

        if(partyService.isPartyContainBranch(partyId, branchId) == false){
            return failed("参数有误。");
        }

        if(pcsAdminService.hasReport(partyId, pcsAdmin.getConfigId(), stage)){
            return failed("已上报数据或已下发名单，不可修改。");
        }

        pcsRecommendService.submit(stage, partyId, branchId,
                expectMemberCount, actualMemberCount, isFinish,
                dwCandidateIds, jwCandidateIds);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "党支部推荐情况-提交推荐票：%s-%s-%s", stage, partyId, branchId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping("/pcsRecommend_au")
    public String pcsRecommend_au(byte stage, int partyId, Integer branchId, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if(pcsAdmin.getPartyId() != partyId){
            throw new UnauthorizedException();
        }

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();

        PcsBranchBean record = new PcsBranchBean();
        record.setPartyId(partyId);
        record.setBranchId(branchId);

        PcsBranchBean pcsBranchBean = pcsRecommendService.get(partyId, branchId, pcsConfig.getId(), stage);
        if (pcsBranchBean != null) {
            try {
                PropertyUtils.copyProperties(record, pcsBranchBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        record.setConfigId(pcsConfig.getId());
        modelMap.put("pcsRecommend", record);

        // 读取党委委员、纪委委员
        List<PcsCandidateView> dwCandidates = pcsCandidateService.find(record.getPartyId(), record.getBranchId(), SystemConstants.PCS_USER_TYPE_DW);
        List<PcsCandidateView> jwCandidates = pcsCandidateService.find(record.getPartyId(), record.getBranchId(), SystemConstants.PCS_USER_TYPE_JW);
        modelMap.put("dwCandidates", dwCandidates);
        modelMap.put("jwCandidates", jwCandidates);


        modelMap.put("hasReport", pcsAdminService.hasReport(partyId, pcsAdmin.getConfigId(), stage));

        return "pcs/pcsRecommend/pcsRecommend_au";
    }

    @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_selectUser", method = RequestMethod.POST)
    public void do_pcsRecommend_selectUser(int userId, HttpServletResponse response) throws IOException {

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        CadreView cv = cadreService.dbFindByUserId(userId);

        PcsCandidateView candidate = new PcsCandidateView();
        candidate.setUserId(memberTeacher.getUserId());
        candidate.setCode(memberTeacher.getCode());
        candidate.setRealname(memberTeacher.getRealname());
        candidate.setTitle(cv==null?null:cv.getTitle());
        candidate.setExtUnit(memberTeacher.getExtUnit());
        candidate.setGender(memberTeacher.getGender());
        candidate.setNation(memberTeacher.getNation());
        candidate.setBirth(memberTeacher.getBirth());
        candidate.setGrowTime(memberTeacher.getGrowTime());
        candidate.setWorkTime(memberTeacher.getWorkTime());
        candidate.setProPost(memberTeacher.getProPost());

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidate", candidate);
        JSONUtils.write(response, resultMap);
        //logger.info(addLog(SystemConstants.LOG_ADMIN, "党支部推荐情况-选择教职工委员：%s-%s", type, userId));
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_form_download")
    public String pcsRecommend_form_download(ModelMap modelMap) {

        return "pcs/pcsRecommend/pcsRecommend_form_download";
    }
}
