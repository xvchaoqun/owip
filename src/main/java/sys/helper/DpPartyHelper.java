package sys.helper;

import org.apache.shiro.authz.UnauthorizedException;
import service.dp.DpPartyMemberService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

public class DpPartyHelper {

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
}
