package sys.helper;

import domain.dp.DpParty;
import org.apache.shiro.authz.UnauthorizedException;
import service.dp.DpPartyMemberService;
import service.dp.DpPrCmService;
import service.dp.dpCommon.DpCommonService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

public class DpPartyHelper {

    private static DpPrCmService dpPrCmService = CmTag.getBean(DpPrCmService.class);
    private static DpCommonService dpCommonService = CmTag.getBean(DpCommonService.class);
    private static DpPartyMemberService dpPartyMemberService = CmTag.getBean(DpPartyMemberService.class);

    public static void checkAuth(int partyId) {

        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)) {

            boolean isAdmin = isPresentDpPartyAdmin(loginUserId, partyId);

            if (!isAdmin) throw new UnauthorizedException();
    }
    }

    public static Boolean isPresentDpPartyAdmin(Integer userId, Integer partyId) {

        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)) return true;

        return dpPartyMemberService.isPresentAdmin(userId, partyId);
    }

    public static DpParty getDpPartyByPartyId(Integer partyId) {

        if (partyId == null) return null;

        return dpCommonService.getDpPartyByPartyId(partyId);
    }

    public static DpParty getDpPartyByGroupId(Integer groupId){
        if (groupId == null) return null;
        return dpCommonService.getDpPartyByGroupId(groupId);
    }

    public static String getTypes(Integer userId){
        if (userId == null) return null;
        return dpPrCmService.getTypesByUserId(userId);
    }

}
