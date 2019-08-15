package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.dp.DpPartyMemberAdminService;
import service.party.BranchMemberAdminService;
import service.party.PartyMemberAdminService;
import shiro.ShiroHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fafa on 2016/1/20.
 */
@Service
public class LoginUserService {

    @Autowired
    protected PartyMemberAdminService partyMemberAdminService;
    @Autowired
    protected BranchMemberAdminService branchMemberAdminService;
    @Autowired
    protected DpPartyMemberAdminService dpPartyMemberAdminService;

    public List<Integer> adminPartyIdList(){
        return  new ArrayList<>(partyMemberAdminService.adminPartyIdList(ShiroHelper.getCurrentUserId()));
    }
    public List<Integer> adminBranchIdList(){
        return  new ArrayList<>(branchMemberAdminService.adminBranchIdList(ShiroHelper.getCurrentUserId()));
    }
    public List<Integer> adminDpPartyIdList(){
        return  new ArrayList<>(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));
    }
}
