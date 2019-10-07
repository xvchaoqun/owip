package sys.helper;

import domain.cr.CrPost;
import domain.cr.CrRequireRule;
import service.cr.CrPostService;
import service.cr.CrRequireRuleService;
import sys.tags.CmTag;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class CrHelper {

    public static Map<Integer, CrRequireRule> getCrRequireRules(Integer postRequireId) {

        CrRequireRuleService crRequireRuleService = CmTag.getBean(CrRequireRuleService.class);

        return crRequireRuleService.findAll(postRequireId);
    }

    public static CrPost getCrPost(Integer id) {

        if(id==null) return null;

        CrPostService crPostService = CmTag.getBean(CrPostService.class);

        return crPostService.get(id);
    }

    public static List<CrPost> getCrPost(List<Integer> ids) {

        CrPostService crPostService = CmTag.getBean(CrPostService.class);
        return crPostService.get(ids);
    }
}
