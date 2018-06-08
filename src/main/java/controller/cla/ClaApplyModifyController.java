package controller.cla;

import domain.cla.ClaApplyModify;
import domain.cla.ClaApplyModifyExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.ClaConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cla")
public class ClaApplyModifyController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("claApply:modifyLog")
    @RequestMapping("/claApplyModifyList")
    public String claApplyModifyList(int applyId, ModelMap modelMap) {

        /*List<ClaApplyModifyBean> claApplyModifyList = iClaMapper.getClaApplyModifyList(applyId);
        modelMap.put("modifyList", claApplyModifyList);*/
        ClaApplyModifyExample example = new ClaApplyModifyExample();
        example.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(ClaConstants.CLA_APPLY_MODIFY_TYPE_MODIFY);
        List<ClaApplyModify> claApplyModifies = claApplyModifyMapper.selectByExample(example);
        modelMap.put("modifyList", claApplyModifies);
        return "cla/claApply/claApplyModifyList";
    }


    @RequiresPermissions("claApply:modifyLog")
    @RequestMapping("/claApplyModify")
    public String claApplyModify(int applyId, ModelMap modelMap) {

        // 获取第一条原始记录
        ClaApplyModifyExample example2 = new ClaApplyModifyExample();
        example2.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(ClaConstants.CLA_APPLY_MODIFY_TYPE_ORIGINAL);
        List<ClaApplyModify> claApplyModifies = claApplyModifyMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 1));
        if(claApplyModifies.size()>0){
            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            modelMap.put("record", JSONUtils.toString(claApplyModifies.get(0), baseMixins));
        }

        return "cla/claApply/claApplyModify_page";
    }

    @RequiresPermissions("claApply:modifyLog")
    @RequestMapping("/claApplyModify_data")
    @ResponseBody
    public void claApplyModify_data(
            int applyId,
            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ClaApplyModifyExample example = new ClaApplyModifyExample();
        ClaApplyModifyExample.Criteria criteria = example.createCriteria();
        criteria.andApplyIdEqualTo(applyId);
        //example.setOrderByClause("id asc");

        long count = claApplyModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApplyModify> claApplyModifys = claApplyModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", claApplyModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
