package controller.party;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_au(Member record,  String _applyTime, String _activeTime, String _candidateTime,
                            String _growTime, String _positiveTime, String _transferTime, HttpServletRequest request) {

        Integer userId = record.getUserId();

        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_candidateTime)){
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_positiveTime)){
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_transferTime)){
            record.setTransferTime(DateUtils.parseDate(_transferTime, DateUtils.YYYY_MM_DD));
        }

        Member member = memberService.get(userId);
        if (member == null) {
            record.setCreateTime(new Date());
            record.setSource(SystemConstants.MEMBER_SOURCE_ADMIN); // 后台添加的党员
            memberService.add(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "添加党员信息表：%s", record.getUserId()));
        } else {
            memberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "更新党员信息表：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:edit")
    @RequestMapping("/member_au")
    public String member_au(Integer userId, Integer partyId, Integer branchId, ModelMap modelMap) {

        Member member = null;
        if (userId != null) {
            member = memberMapper.selectByPrimaryKey(userId);
            modelMap.put("op", "编辑");

            partyId = member.getPartyId();
            branchId = member.getBranchId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        }else{
            modelMap.put("op", "添加");
        }

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        modelMap.put("member", member);

        return "party/member/member_au";
    }

    @RequiresPermissions("member:del")
    @RequestMapping(value = "/member_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            memberService.del(userId);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "删除党员信息表：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:list")
    @RequestMapping("/member")
    public String member() {

        return "index";
    }
    @RequiresPermissions("member:list")
    @RequestMapping("/member_page")
    public String member_page(HttpServletResponse response,@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(cls==1) { // => member.type=3 member.status=1
            return "forward:/memberStudent_page";
        }
        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                4应退休   =>  member.type=2 member.status=1
                5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
         */
        return "forward:/memberTeacher_page";
    }

 /*   @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_view(HttpServletResponse response, int userId, ModelMap modelMap) {

        return "index";
    }*/

    @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_show_page(HttpServletResponse response, int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        if(sysUser.getType() == SystemConstants.MEMBER_TYPE_TEACHER)  // 这个地方的判断可能有问题，应该用党员信息里的类别++++++++++++
            return "party/member/teacher_view";
        return "party/member/student_view";
    }
}
