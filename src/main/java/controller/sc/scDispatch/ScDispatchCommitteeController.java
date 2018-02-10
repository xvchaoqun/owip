package controller.sc.scDispatch;

import controller.ScDispatchBaseController;
import domain.sc.scDispatch.ScDispatchCommittee;
import domain.sc.scDispatch.ScDispatchCommitteeExample;
import domain.sc.scDispatch.ScDispatchCommitteeExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScDispatchCommitteeController extends ScDispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatchCommittee")
    public String scDispatchCommittee() {

        return "sc/scDispatch/scDispatchCommittee/scDispatchCommittee_page";
    }

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatchCommittee_data")
    public void scDispatchCommittee_data(HttpServletResponse response,
                                    Integer dispatchId,
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

        ScDispatchCommitteeExample example = new ScDispatchCommitteeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (dispatchId!=null) {
            criteria.andDispatchIdEqualTo(dispatchId);
        }

        long count = scDispatchCommitteeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScDispatchCommittee> records= scDispatchCommitteeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scDispatchCommittee.class, scDispatchCommitteeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatchCommittee_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatchCommittee_au(ScDispatchCommittee record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            scDispatchCommitteeService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "添加任免对象：%s", record.getId()));
        } else {

            scDispatchCommitteeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "更新任免对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping("/scDispatchCommittee_au")
    public String scDispatchCommittee_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScDispatchCommittee scDispatchCommittee = scDispatchCommitteeMapper.selectByPrimaryKey(id);
            modelMap.put("scDispatchCommittee", scDispatchCommittee);
        }
        return "sc/scDispatch/scDispatchCommittee/scDispatchCommittee_au";
    }

    @RequiresPermissions("scDispatch:del")
    @RequestMapping(value = "/scDispatchCommittee_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatchCommittee_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scDispatchCommitteeService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "删除任免对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:del")
    @RequestMapping(value = "/scDispatchCommittee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scDispatchCommittee_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scDispatchCommitteeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "批量删除任免对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
