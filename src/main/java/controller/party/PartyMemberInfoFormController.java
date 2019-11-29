package controller.party;

import bean.MemberInfoForm;
import bean.PartyMemberInfoForm;
import controller.BaseController;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.cadre.CadreInfoFormService;
import service.common.FreemarkerService;
import service.member.MemberInfoFormService;
import service.party.MemberService;
import sys.constants.MemberConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// 支部书记信息采集表
@Controller
public class PartyMemberInfoFormController extends BaseController {

    @Autowired
    private MemberInfoFormService memberInfoFormService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;
    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private MemberService memberService;

    //党员信息采集表（班子成员即党委委员、支部委员、普通党员）
    @RequiresPermissions("memberInfoForm:list")
    @RequestMapping("/memberInfoForm_page")
    public String memberInfoForm_page(@CurrentUser SysUserView loginUser, Integer cadreId, Integer userId,
                                      ModelMap modelMap) {

        Integer cls = 1;
        if (userId == null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            userId = cadre.getUserId();
        }
        Member member = memberService.get(userId);
        if (member.getType() == MemberConstants.MEMBER_TYPE_TEACHER || member.getType() == null) {
            //教职工
            cls = 1;
        } else {
            //学生
            cls = 2;
        }

        Integer loginUserId = loginUser.getId();
        if (!branchMemberService.hasAdminAuth(loginUserId, member.getPartyId(), member.getBranchId()))
            throw new UnauthorizedException();

        SysUserView uv = CmTag.getUserById(userId);
        modelMap.put("cls", cls);
        modelMap.put("userId", userId);
        modelMap.put("uv", uv);

        MemberInfoForm memberInfoForm = memberInfoFormService.getMemberInfoForm(cadreId, userId);
        modelMap.put("bean", memberInfoForm);

        return "party/partyMember/memberInfoForm_page";
    }

    @RequiresPermissions("memberInfoForm:edit")
    @RequestMapping("/memberInfoForm_au")
    public String memberInfoForm_au(Integer userId, ModelMap modelMap){

        SysUserView uv = CmTag.getUserById(userId);
        modelMap.put("uv", uv);
        CadreView cadreView = CmTag.getCadreByUserId(userId);
        modelMap.put("cadreView", cadreView);

        return "party/partyMember/memberInfoForm_au";
    }

    @RequiresPermissions("memberInfoForm:edit")
    @RequestMapping(value = "/memberInfoForm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInfoForm_au(Integer userId,
                                    String mobile,
                                    String post,
                                    ModelMap modelMap,
                                    HttpServletRequest request){

        SysUserInfo uv = sysUserInfoMapper.selectByPrimaryKey(userId);

        memberInfoFormService.update(userId, mobile, post);

        return success(FormUtils.SUCCESS);
    }


    // 党员信息采集表下载
    @RequiresPermissions("memberInfoForm:edit")
    @RequestMapping("/memberInfoForm_download")
    public void memberInfoForm_download(Integer cadreId, Integer userId, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, TemplateException {
        //if(cadreId == null) return;

        memberInfoFormService.exportPartyMember(cadreId, userId, request, response);
    }

    @RequiresPermissions("partyMemberInfoForm:list")
    @RequestMapping("/partyMemberInfoForm_page")
    public String partyMemberInfoForm_page(int cadreId, int branchId, ModelMap modelMap) {

        PartyMemberInfoForm partyMemberInfoForm = cadreInfoFormService.getPartyMemberInfoForm(cadreId, branchId);
        modelMap.put("bean", partyMemberInfoForm);

        return "party/partyMember/partyMemberInfoForm_page";
    }

    // 党委委员/支部书记信息采集表下载
    @RequiresPermissions("partyMemberInfoForm:download")
    @RequestMapping("/partyMemberInfoForm_download")
    public void partyMemberInfoForm_download(Integer cadreId, int branchId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;

        cadreInfoFormService.exportPartyMember(cadreId, branchId, request, response);
    }
}
