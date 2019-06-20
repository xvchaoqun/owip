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
import service.party.BranchService;
import service.party.MemberService;
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

        SysUserView uv = sysUserService.findById(userId);

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setUsername(uv.getUsername());
        userBean.setCode(uv.getCode());
        userBean.setAvatar(uv.getAvatar());
        userBean.setType(uv.getType());
        userBean.setMobile(uv.getMobile());
        userBean.setGender(uv.getGender());
        userBean.setRealname(uv.getRealname());
        userBean.setIdcard(uv.getIdcard());
        userBean.setNation(uv.getNation());
        userBean.setNativePlace(uv.getNativePlace());
        userBean.setBirth(uv.getBirth());
        userBean.setGender(uv.getGender());
        userBean.setIdcard(uv.getIdcard());
        userBean.setRealname(uv.getRealname());
        userBean.setLocked(uv.getLocked());

        userBean.setCountry(uv.getCountry());
        userBean.setUnit(uv.getUnit());

        CadreView cadre = iCadreMapper.getCadreByCode(uv.getCode());
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
