package sys.helper;

import domain.crs.CrsPost;
import domain.crs.CrsRequireRule;
import service.crs.CrsPostService;
import service.crs.CrsRequireRuleService;
import sys.tags.CmTag;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class CrsHelper {

    public static Map<Integer, CrsRequireRule> getCrsRequireRules(Integer postRequireId) {

        CrsRequireRuleService crsRequireRuleService = CmTag.getBean(CrsRequireRuleService.class);

        return crsRequireRuleService.findAll(postRequireId);
    }

    public static CrsPost getCrsPost(Integer id) {

        if(id==null) return null;

        CrsPostService crsPostService = CmTag.getBean(CrsPostService.class);

        return crsPostService.get(id);
    }

    public static List<CrsPost> getCrsPost(List<Integer> ids) {

        CrsPostService crsPostService = CmTag.getBean(CrsPostService.class);
        return crsPostService.get(ids);
    }
}
