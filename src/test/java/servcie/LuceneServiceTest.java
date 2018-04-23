package servcie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.common.LuceneService;
import sys.tool.lucene.UserSearchPage;
import sys.utils.JSONUtils;

import java.text.MessageFormat;

/**
 * Created by lm on 2018/4/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class LuceneServiceTest {

    @Autowired
    LuceneService luceneService;

    @Test
    public  void index(){

        long start = System.currentTimeMillis();

        luceneService.indexAllUsers();

        long end = System.currentTimeMillis();

        System.out.println(MessageFormat.format("耗时{0}ms", (end - start)));
    }

    @Test
    public  void search(){

        UserSearchPage searchPage = luceneService.searchUsers(null, new Byte[]{1,2,3}, 1, 10);
        System.out.println(JSONUtils.toString(searchPage, false));

        searchPage = luceneService.searchUsers("廖", null, 1, 10);
        System.out.println(JSONUtils.toString(searchPage, false));

        searchPage = luceneService.searchUsers("lm", null, 1, 10);
        System.out.println(JSONUtils.toString(searchPage, false));

        searchPage = luceneService.searchUsers("廖敏", null, 1, 10);
        System.out.println(JSONUtils.toString(searchPage, false));
    }
}
