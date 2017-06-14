package ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by lm on 2017/6/14.
 */
public class CacheTest {


    @Test
    public void tes11() throws FileNotFoundException {


        CacheManager cacheManager = new CacheManager();
        //CacheManager cacheManager = new CacheManager(this.getClass().getResource("/ehcache-test.xml"));

        Ehcache copyCache=cacheManager.addCacheIfAbsent("copyCache");

        System.out.println(Arrays.asList(cacheManager.getCacheNames()));

        //cacheManager.add
    }

}
