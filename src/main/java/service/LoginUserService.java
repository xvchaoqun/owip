package service;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.party.BranchMemberAdminService;
import service.party.PartyMemberAdminService;
import shiro.ShiroHelper;
import shiro.ShiroUser;

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

    public List<Integer> adminPartyIdList(){
        return  new ArrayList<>(partyMemberAdminService.adminPartyIdList(ShiroHelper.getCurrentUserId()));
    }
    public List<Integer> adminBranchIdList(){
        return  new ArrayList<>(branchMemberAdminService.adminBranchIdList(ShiroHelper.getCurrentUserId()));
    }
}
