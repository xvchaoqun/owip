package service;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.party.BranchMemberAdminService;
import service.party.PartyMemberAdminService;
import shiro.ShiroUser;

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
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return  partyMemberAdminService.adminPartyIdList(shiroUser.getId());
    }
    public List<Integer> adminBranchIdList(){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return  branchMemberAdminService.adminBranchIdList(shiroUser.getId());
    }
}
