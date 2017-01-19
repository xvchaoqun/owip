package mapper;

import bean.statCadre.StatCadreBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.StatCadreMapper;

import java.util.List;

/**
 * Created by fafa on 2016/8/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CadreStatMapperTest {
    @Autowired
    StatCadreMapper cadreStatMapper;

    @Test
    public void t(){
        String unitTypeAttr = "xy";

        List<StatCadreBean> cadreStatBeans = cadreStatMapper.cadre_stat_adminLevel(unitTypeAttr);
        for (StatCadreBean cadreStatBean : cadreStatBeans) {
            System.out.println(cadreStatBean);
        }
    }
}
