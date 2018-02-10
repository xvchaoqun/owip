package controller.pcs.cm;

import controller.PcsBaseController;
import domain.cadre.CadreView;
import domain.member.MemberTeacher;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsCandidateView;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
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
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsRecommendController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend")
    public String pcsRecommend(Integer branchId, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        modelMap.put("partyId", partyId);
        modelMap.put("isDirectBranch", partyService.isDirectBranch(partyId));

        if(branchId!=null){
            modelMap.put("branch", branchService.findAll().get(branchId));
        }
        return "pcs/pcsRecommend/pcsRecommend_page";
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_data")
    public void pcsRecommend_data(HttpServletResponse response,
                                  byte stage,
                                  Integer branchId,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iPcsMapper.countPcsBranchBeans(configId, stage, partyId, branchId, null);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsBranchBean> records =
                iPcsMapper.selectPcsBranchBeans(configId, stage, partyId, branchId, null,
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

    //@RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsRecommend_au(byte stage, int partyId, Integer branchId,
                                  int expectMemberCount, int actualMemberCount, Boolean isFinish,
                                  @RequestParam(required = false, value = "dwCandidateIds[]") String[] dwCandidateIds,
                                  @RequestParam(required = false, value = "jwCandidateIds[]") String[] jwCandidateIds,
                                  HttpServletRequest request) {

        if(ShiroHelper.isPermitted("pcsOw:admin")){
            // 管理员可以修改，但不改变状态
            isFinish = null;

        }else {
            SecurityUtils.getSubject().checkPermission("pcsRecommend:edit");

            PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin.getPartyId() != partyId) {
                throw new UnauthorizedException();
            }

            if (!pcsPartyService.allowModify(partyId,
                    pcsConfigService.getCurrentPcsConfig().getId(), stage)) {
                return failed("已报送数据或已下发名单，不可修改。");
            }
        }

        if (partyService.isPartyContainBranch(partyId, branchId) == false) {
            return failed("参数有误。");
        }

        pcsRecommendService.submit(stage, partyId, branchId,
                expectMemberCount, actualMemberCount, isFinish,
                dwCandidateIds, jwCandidateIds);

        logger.info(addLog(SystemConstants.LOG_PCS, "党支部推荐情况-提交推荐票：%s-%s-%s", stage, partyId, branchId));

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
        int configId = pcsConfig.getId();

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

        record.setConfigId(configId);
        modelMap.put("pcsRecommend", record);

        // 读取党委委员、纪委委员
        List<PcsCandidateView> dwCandidates =
                pcsCandidateService.find(record.getPartyId(),
                        record.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_DW);
        List<PcsCandidateView> jwCandidates =
                pcsCandidateService.find(record.getPartyId(),
                        record.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("dwCandidates", dwCandidates);
        modelMap.put("jwCandidates", jwCandidates);


        modelMap.put("allowModify", pcsPartyService.allowModify(partyId,
                pcsConfigService.getCurrentPcsConfig().getId(), stage));

        return "pcs/pcsRecommend/pcsRecommend_au";
    }


    //@RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_candidates")
    public String pcsRecommend_candidates(byte stage, byte type, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsOw:admin")){
            SecurityUtils.getSubject().checkPermission("pcsRecommend:list");
        }

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        List<IPcsCandidateView> candidates =
                iPcsMapper.selectPartyCandidates(null, true, configId, stage, type, new RowBounds());

        modelMap.put("candidates", candidates);
        return "pcs/pcsRecommend/pcsRecommend_candidates";
    }

    //@RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_selectUser", method = RequestMethod.POST)
    public void do_pcsRecommend_selectUser(@RequestParam(value = "userIds[]") Integer[] userIds,
                                           HttpServletResponse response) throws IOException {

        if(!ShiroHelper.isPermitted("pcsOw:admin")){
            SecurityUtils.getSubject().checkPermission("pcsRecommend:edit");
        }

        List<PcsCandidateView> candidates = new ArrayList<>();
        if(userIds!=null){
            for (Integer userId : userIds) {

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

                candidates.add(candidate);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
        //logger.info(addLog(SystemConstants.LOG_PCS, "党支部推荐情况-选择教职工委员：%s-%s", type, userId));
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_form_download")
    public String pcsRecommend_form_download(ModelMap modelMap) {

        return "pcs/pcsRecommend/pcsRecommend_form_download";
    }
}
