package servcie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.cadre.common.CadreSearchBean;
import service.analysis.StatCadreService;
import sys.constants.CadreConstants;

import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CadreStatServiceTest {

    @Autowired
    StatCadreService cadreStatService;

    @Test
    public void stat(){

        String unitTypeGroup = "xy";
        //String unitTypeGroup = null;

        Map<String, List> result = cadreStatService.stat(unitTypeGroup, CadreSearchBean.getInstance(CadreConstants.CADRE_TYPE_CJ));

        for (Map.Entry<String, List> entry : result.entrySet()) {
            System.out.println(entry.getKey());
            for (Object column : entry.getValue()) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
    }

}
