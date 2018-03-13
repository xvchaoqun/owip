package controller.cet;

import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import domain.cet.CetTraineeExample.Criteria;
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
@RequestMapping("/cet")
public class CetTraineeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee")
    public String cetTrainee() {

        return "cet/cetTrainee/cetTrainee_page";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_data")
    public void cetTrainee_data(HttpServletResponse response,
                                    Integer trainId,
                                    Integer userId,
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

        CetTraineeExample example = new CetTraineeExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (trainId!=null) {
            criteria.andTrainIdEqualTo(trainId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = cetTraineeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainee> records= cetTraineeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainee.class, cetTraineeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainee:edit")
    @RequestMapping(value = "/cetTrainee_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_au(CetTrainee record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTraineeService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加可选课人员：%s", record.getId()));
        } else {

            cetTraineeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新可选课人员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainee:edit")
    @RequestMapping("/cetTrainee_au")
    public String cetTrainee_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainee", cetTrainee);
        }
        return "cet/cetTrainee/cetTrainee_au";
    }

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTraineeService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除可选课人员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainee_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTraineeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除可选课人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
