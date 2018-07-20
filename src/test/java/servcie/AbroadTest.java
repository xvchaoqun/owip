package servcie;

import domain.abroad.ApproverType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.abroad.AbroadService;
import service.abroad.ApproverTypeService;
import service.cadre.CadreService;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AbroadTest {

    @Autowired
    CadreService cadreService;
    @Autowired
    AbroadService abroadService;
    @Autowired
    ApproverTypeService approverTypeService;

    @Test
    public void list(){

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        Map<Integer,  Map<Integer, List<SysUserView>>> cadreApproverListMap
                = abroadService.getCadreApproverListMap();
        Map<Integer, CadreView> cadreMap = cadreService.findAll();

        for (Map.Entry<Integer, Map<Integer, List<SysUserView>>> entry : cadreApproverListMap.entrySet()) {

            int cadreId = entry.getKey();
            CadreView cv = cadreMap.get(cadreId);
            Map<Integer, List<SysUserView>> _approverListMap = entry.getValue();
            System.out.print(cv.getRealname());
            for (Map.Entry<Integer, List<SysUserView>> listEntry : _approverListMap.entrySet()) {

                ApproverType approverType = approverTypeMap.get(listEntry.getKey());
                System.out.print("  " + approverType.getName() + " ");
                List<SysUserView> uvs = listEntry.getValue();
                if(uvs!=null) {
                    for (SysUserView uv : uvs) {
                        System.out.print(uv.getRealname() + " ");
                    }
                }else{
                    System.out.print("--");
                }
            }
            System.out.println();
        }
    }
}
