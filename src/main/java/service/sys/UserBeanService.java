package service.sys;

import bean.UserBean;
import domain.member.Member;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import sys.constants.SystemConstants;

import java.util.Map;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class UserBeanService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PartyService partyService;

    @Autowired
    private MemberService memberService;

    public UserBean get(int userId) {

        SysUserView sysUser = sysUserService.findById(userId);

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setUsername(sysUser.getUsername());
        userBean.setCode(sysUser.getCode());
        userBean.setType(sysUser.getType());
        userBean.setMobile(sysUser.getMobile());
        userBean.setGender(sysUser.getGender());
        userBean.setRealname(sysUser.getRealname());
        userBean.setIdcard(sysUser.getIdcard());

        if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
            userBean.setBirth(sysUser.getBirth());
            if (sysUser.getGender() != null)
                userBean.setGender(sysUser.getGender());
            if (sysUser.getIdcard() != null) userBean.setIdcard(sysUser.getIdcard());
            userBean.setNation(sysUser.getNation());
            userBean.setNativePlace(sysUser.getNativePlace());
            if (sysUser.getRealname() != null) userBean.setRealname(sysUser.getRealname());
        } else {
            userBean.setBirth(sysUser.getBirth());
            if (sysUser.getGender() != null)
                userBean.setGender(sysUser.getGender());
            if (sysUser.getIdcard() != null) userBean.setIdcard(sysUser.getIdcard());
            userBean.setNation(sysUser.getNation());
            userBean.setNativePlace(sysUser.getNativePlace());
            if (sysUser.getRealname() != null) userBean.setRealname(sysUser.getRealname());
        }

        Member member = memberService.get(userId);
        if (member != null) { // 如果是党员
            userBean.setPoliticalStatus(member.getPoliticalStatus());
            userBean.setPartyId(member.getPartyId());
            userBean.setBranchId(member.getBranchId());
            userBean.setGrowTime(member.getGrowTime());

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            Integer partyId = member.getPartyId();
            Integer branchId = member.getBranchId();
            if (partyId != null) {
                userBean.setParty(partyMap.get(partyId));
            }
            if (branchId != null) {
                userBean.setBranch(branchMap.get(branchId));
            }
        }

        return userBean;
    }
}
