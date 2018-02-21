package controller.pmd;

import domain.pmd.PmdNormValueLog;
import domain.pmd.PmdNormValueLogExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pmd")
public class PmdNormValueLogController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdNorm:list")
    @RequestMapping("/pmdNormValueLog")
    public String pmdNormValueLog(int pmdNormValueId, ModelMap modelMap) {

        PmdNormValueLogExample example = new PmdNormValueLogExample();
        example.createCriteria().andNormValueIdEqualTo(pmdNormValueId);
        example.setOrderByClause("start_time desc");
        List<PmdNormValueLog> pmdNormValueLogs = pmdNormValueLogMapper.selectByExample(example);
        modelMap.put("pmdNormValueLogs", pmdNormValueLogs);
        return "pmd/pmdNormValueLog/pmdNormValueLog_page";
    }

    /*@RequiresPermissions("pmdNorm:list")
    @RequestMapping("/pmdNormValueLog_data")
    public void pmdNormValueLog_data(HttpServletResponse response,
                                     Integer normValueId,
                                     Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdNormValueLogExample example = new PmdNormValueLogExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (normValueId != null) {
            criteria.andNormValueIdEqualTo(normValueId);
        }

        long count = pmdNormValueLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdNormValueLog> records = pmdNormValueLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdNormValueLog.class, pmdNormValueLogMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }*/
}
