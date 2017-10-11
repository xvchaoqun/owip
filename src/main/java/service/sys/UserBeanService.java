package service.sys;

import bean.UserBean;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.cadre.CadreService;
import service.member.MemberService;
import service.party.BranchService;
import service.party.PartyService;

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
    @Autowired
    private CadreService cadreService;

    // 获取接收短信的称谓。如果没有设定短信称谓，则使用姓名
    public String getMsgTitle(int userId){

        SysUserView user = sysUserService.findById(userId);
        if(user!=null)
            return StringUtils.defaultString(StringUtils.trimToNull(user.getMsgTitle()), user.getRealname());

        throw new OpException("user is not existed. id="+userId);
    }

    // 获接收短信的手机号码。
    public String getMsgMobile(int userId) {

        SysUserView user = sysUserService.findById(userId);
        if(user!=null) return user.getMobile();

        throw new OpException("user is not existed. id="+userId);
    }

    public UserBean get(int userId) {

        SysUserView sysUser = sysUserService.findById(userId);

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setUsername(sysUser.getUsername());
        userBean.setCode(sysUser.getCode());
        userBean.setAvatar(sysUser.getAvatar());
        userBean.setType(sysUser.getType());
        userBean.setMobile(sysUser.getMobile());
        userBean.setGender(sysUser.getGender());
        userBean.setRealname(sysUser.getRealname());
        userBean.setIdcard(sysUser.getIdcard());
        userBean.setNation(sysUser.getNation());
        userBean.setNativePlace(sysUser.getNativePlace());
        userBean.setBirth(sysUser.getBirth());
        userBean.setGender(sysUser.getGender());
        userBean.setIdcard(sysUser.getIdcard());
        userBean.setRealname(sysUser.getRealname());

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(userId);
        if(cadre!=null){
            userBean.setBirth(cadre.getBirth());
        }

        Member member = memberService.get(userId);
        if (member != null) { // 如果是党员
            userBean.setPoliticalStatus(member.getPoliticalStatus());
            userBean.setPartyId(member.getPartyId());
            userBean.setBranchId(member.getBranchId());
            userBean.setGrowTime(member.getGrowTime());
            userBean.setMemberStatus(member.getStatus());

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
