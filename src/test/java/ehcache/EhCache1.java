package ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
 * 使用API来动态的添加缓存(将缓存的配置信息通过java代码来实现而非写在配置文件)
 * @author Administrator
 *
 */
public class EhCache1 {
	
	public static void main(String[] args) {
        //创建一个缓存管理器
        CacheManager singletonManager = CacheManager.create();
        //建立一个缓存实例
        Cache memoryOnlyCache = new Cache("testCache", 5000, false, false, 5, 2);
        //在内存管理器中添加缓存实例
        singletonManager.addCache(memoryOnlyCache);
        //在缓存管理器中获取一个缓存实例
        Cache cache = singletonManager.getCache("testCache");
        //使用获取到的缓存实例
        Element element = new Element("key1", "value1");
        cache.put(element);//添加缓存值
        cache.put(new Element("key2", "value2"));//添加缓存值

        int elementsInMemory = cache.getSize();//获取缓存个数
        System.out.println("缓存个数======="+elementsInMemory);

//        Object obj = element.getObjectValue();//获取对象值
//        cache.remove("key1");//删除缓存
        
        Cache cache2 = singletonManager.getCache("testCache");//获取缓存实例
        Element element2 = cache2.get("key1");
        System.out.println("value====="+element2.getValue());//获取缓存值
//        singletonManager.shutdown();
	}

}
