package servcie;

import domain.SysResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.sys.SysResourceService;
import sys.tool.tree.TreeNode;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SysResourceServiceTest {

    @Autowired
    SysResourceService sysResourceService;

    @Test
    public void loop(){
        TreeNode treeByLoop = sysResourceService.getTree(new HashSet<Integer>());

    }
    @Test
    public void show(){

        Map<Integer, SysResource> sortedSysResources = sysResourceService.getSortedSysResources();
        System.out.println(sortedSysResources.size());
    }
}
