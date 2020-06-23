package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.party.BranchAdminService;
import service.party.PartyAdminService;
import shiro.ShiroHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fafa on 2016/1/20.
 */
@Service
public class LoginUserService {

    @Autowired
    protected PartyAdminService partyAdminService;
    @Autowired
    protected BranchAdminService branchAdminService;

    public List<Integer> adminPartyIdList(){
        return  new ArrayList<>(partyAdminService.adminPartyIdList(ShiroHelper.getCurrentUserId()));
    }
    public List<Integer> adminBranchIdList(){
        return  new ArrayList<>(branchAdminService.adminBranchIdList(ShiroHelper.getCurrentUserId()));
    }
}
