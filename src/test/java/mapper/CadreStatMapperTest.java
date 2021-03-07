package mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.cadre.common.CadreSearchBean;
import persistence.cadre.common.StatCadreBean;
import persistence.cadre.common.StatCadreMapper;
import sys.constants.CadreConstants;

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
        String unitTypeGroup = "xy";

        List<StatCadreBean> cadreStatBeans = cadreStatMapper.cadre_stat_adminLevel(CadreSearchBean.getInstance(unitTypeGroup, CadreConstants.CADRE_CATEGORY_CJ));
        for (StatCadreBean cadreStatBean : cadreStatBeans) {
            System.out.println(cadreStatBean);
        }
    }
}
