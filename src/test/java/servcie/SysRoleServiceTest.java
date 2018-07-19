package servcie;

import domain.sys.SysUserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.sys.SysRoleService;
import service.sys.SysUserService;
import sys.constants.RoleConstants;

import java.util.List;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SysRoleServiceTest {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserService sysUserService;

    @Test
    public void delRole(){

        String role = RoleConstants.ROLE_PMD_PARTY;
        List<SysUserView> sysUserViews = sysUserService.findByRole(role);

        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getId(), role);
        }

        role = RoleConstants.ROLE_PMD_BRANCH;
        sysUserViews = sysUserService.findByRole(role);

        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getId(), role);
        }
    }

}
