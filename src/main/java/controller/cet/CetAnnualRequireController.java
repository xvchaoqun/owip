package controller.cet;

import domain.cet.CetAnnualRequire;
import domain.cet.CetAnnualRequireExample;
import domain.cet.CetAnnualRequireExample.Criteria;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetAnnualRequireController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetAnnualRequire:list")
    @RequestMapping("/cetAnnualRequire")
    public String cetAnnualRequire() {

        return "cet/cetAnnualRequire/cetAnnualRequire_page";
    }

    @RequiresPermissions("cetAnnualRequire:list")
    @RequestMapping("/cetAnnualRequire_data")
    @ResponseBody
    public void cetAnnualRequire_data(HttpServletResponse response,
                                    int annualId,
                                    Integer adminLevel,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetAnnualRequireExample example = new CetAnnualRequireExample();
        Criteria criteria = example.createCriteria().andAnnualIdEqualTo(annualId);
        example.setOrderByClause("id desc");

        if (adminLevel!=null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }

        long count = cetAnnualRequireMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetAnnualRequire> records= cetAnnualRequireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetAnnualRequire.class, cetAnnualRequireMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
