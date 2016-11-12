package service.sys;

import bean.UserBean;
import domain.cadre.Cadre;
import domain.cadre.CadreConcat;
import domain.member.Member;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.cadre.CadreService;
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
    private CadreService cadreService;

    @Autowired
    private MemberService memberService;

    // 获取接收短信的称谓。如果设定了干部的短信称谓，优先使用该称谓
    public String getMsgTitle(int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre!=null) {
            CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadre.getId());
            if(cadreConcat!=null  && StringUtils.isNotBlank(cadreConcat.getMsgTitle()))
                return cadreConcat.getMsgTitle();
        }

        SysUserView user = sysUserService.findById(userId);
        if(user!=null) return user.getRealname();

        throw new RuntimeException("user is not existed. id="+userId);
    }

    // 获接收短信的手机号码。 如果是干部，优先使用干部联系方式中的手机号码
    public String getMsgMobile(int userId) {

        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre!= null) {
            CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadre.getId());
            if(cadreConcat!=null  && StringUtils.isNotBlank(cadreConcat.getMobile())) return cadreConcat.getMobile();
        }
        SysUserView user = sysUserService.findById(userId);
        if(user!=null) return user.getMobile();

        throw new RuntimeException("user is not existed. id="+userId);
    }

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
        userBean.setNation(sysUser.getNation());
        userBean.setNativePlace(sysUser.getNativePlace());
        userBean.setBirth(sysUser.getBirth());
        userBean.setGender(sysUser.getGender());
        userBean.setIdcard(sysUser.getIdcard());
        userBean.setRealname(sysUser.getRealname());

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
