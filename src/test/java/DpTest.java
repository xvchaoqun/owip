import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.cadre.CadreEduService;
import service.pmd.PmdOrderLogService;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DpTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String path = "d:/pmddoc";
    public static final String openFileStyle = "r";
    @Autowired
    private PmdOrderLogService pmdOrderLogService;
    @Autowired
    private CadreEduService cadreEduService;
    /*
        批量计算文件夹中.txt中的党费收缴情况
        遇到相同订单号的，先删除，后添加
    * */
    @Test
    public void excute() {
        File file = new File(path);
        int count = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File txt : files) {
                try {
                    String name = txt.getName();
                    count += pmdOrderLogService.loadFile(path + "/" + name, openFileStyle, name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("总计：" + count + "条记录");
    }

    //根据code更新领导干部的学习经历中，最高学历最高学位（可以去掉code,直接全部更新）
    @Test
    public void updateEdu(){
        String code = 99038 + "";
        cadreEduService.updateHighEdu(code);
    }
}
