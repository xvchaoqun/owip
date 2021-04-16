package controller.parttime;

import domain.cla.ClaApplyModify;
import domain.cla.ClaApplyModifyExample;
import domain.parttime.ParttimeApplyModify;
import domain.parttime.ParttimeApplyModifyExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.parttime.ParttimeApplyModifyMapper;
import service.SpringProps;
import sys.constants.ClaConstants;
import sys.constants.ParttimeConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ParttimeApplyModifyController {
    @Autowired
    private ParttimeApplyModifyMapper parttimeApplyModifyMapper;
    @Autowired
    private SpringProps springProps;

    @RequiresPermissions("parttimeApply:modifyLog")
    @RequestMapping("/parttime/parttimeApplyModifyList")
    public String parttimeApplyModifyList(int applyId, ModelMap modelMap) {

        ParttimeApplyModifyExample example = new ParttimeApplyModifyExample();
        example.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(ParttimeConstants.PARTTIME_APPLY_MODIFY_TYPE_MODIFY);
        List<ParttimeApplyModify> parttimeApplyModifies = parttimeApplyModifyMapper.selectByExample(example);
        modelMap.put("modifyList", parttimeApplyModifies);
        return "parttime/parttimeApply/parttimeApplyModifyList";
    }

    @RequiresPermissions("parttimeApply:modifyLog")
    @RequestMapping("/parttimeApplyModify")
    public String parttimeApplyModify(int applyId, ModelMap modelMap) {

        // 获取第一条原始记录
        ParttimeApplyModifyExample example2 = new ParttimeApplyModifyExample();
        example2.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(ParttimeConstants.PARTTIME_APPLY_MODIFY_TYPE_ORIGINAL);
        List<ParttimeApplyModify> pApplyModifies = parttimeApplyModifyMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 1));
        if(pApplyModifies.size()>0){
            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            modelMap.put("record", JSONUtils.toString(pApplyModifies.get(0), baseMixins));
        }

        return "parttime/parttimeApply/parttimeApplyModify_page";
    }

    @RequiresPermissions("parttimeApply:modifyLog")
    @RequestMapping("/parttime/parttimeApplyModify_data")
    @ResponseBody
    public void parttimeApplyModify_data(
            int applyId,
            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ParttimeApplyModifyExample example = new ParttimeApplyModifyExample();
        ParttimeApplyModifyExample.Criteria criteria = example.createCriteria();
        criteria.andApplyIdEqualTo(applyId);
        //example.setOrderByClause("id asc");

        long count = parttimeApplyModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApplyModify> parttimeApplyModifys = parttimeApplyModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", parttimeApplyModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
