package servcie;

import domain.sys.SysResource;
import domain.sys.SysUserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.sys.SysResourceService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.tool.tree.TreeNode;

import java.util.*;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SysResourceServiceTest {

    @Autowired
    SysResourceService sysResourceService;
    @Autowired
    SysUserService sysUserService;

    @Test
    public void loop(){
        TreeNode treeByLoop = sysResourceService.getTree(new HashSet<Integer>());

    }
    @Test
    public void findAll(){

        Map<Integer, SysResource> sortedSysResources = sysResourceService.getSortedSysResources();
        Collection<SysResource> sysResources = sortedSysResources.values();
        for (SysResource sysResource : sysResources) {
            System.out.println(sysResource.getPermission());
        }
    }
    @Test
    public void userMenu(){

        int userId = 672;//李晓兵
        SysUserView uv = sysUserService.findById(userId);
        ShiroUser shiroUser = new ShiroUser(userId, uv.getUsername(), uv.getCode(), uv.getRealname(), uv.getType());
        Set<String> ownPermissions = shiroUser.getPermissions();
       /* for (String permission : ownPermissions) {
            System.out.println(permission);
        }*/

        List<SysResource> userMenus = sysResourceService.makeMenus(ownPermissions);
        for (SysResource userMenu : userMenus) {
            System.out.println(userMenu.getPermission());
        }
    }
}
