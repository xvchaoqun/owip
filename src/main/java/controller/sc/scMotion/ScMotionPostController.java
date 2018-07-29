package controller.sc.scMotion;

import domain.sc.scMotion.ScMotionPost;
import domain.sc.scMotion.ScMotionPostExample;
import domain.sc.scMotion.ScMotionPostExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMotionPostController extends ScMotionBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMotionPost:list")
    @RequestMapping("/scMotionPost")
    public String scMotionPost(Integer motionId, ModelMap modelMap) {

        if(motionId!=null){
            modelMap.put("motion", scMotionMapper.selectByPrimaryKey(motionId));
        }

        return "sc/scMotion/scMotionPost/scMotionPost_page";
    }

    @RequiresPermissions("scMotionPost:list")
    @RequestMapping("/scMotionPost_data")
    @ResponseBody
    public void scMotionPost_data(HttpServletResponse response,
                                    Integer motionId,
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

        ScMotionPostExample example = new ScMotionPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (motionId!=null) {
            criteria.andMotionIdEqualTo(motionId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scMotionPost_export(example, response);
            return;
        }

        long count = scMotionPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMotionPost> records= scMotionPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMotionPost.class, scMotionPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMotionPost:edit")
    @RequestMapping(value = "/scMotionPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMotionPost_au(ScMotionPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            scMotionPostService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "添加动议所包含的岗位：%s", record.getId()));
        } else {

            scMotionPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "更新动议所包含的岗位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMotionPost:edit")
    @RequestMapping("/scMotionPost_au")
    public String scMotionPost_au(Integer id, Integer motionId, ModelMap modelMap) {

        if (id != null) {
            ScMotionPost scMotionPost = scMotionPostMapper.selectByPrimaryKey(id);
            modelMap.put("scMotionPost", scMotionPost);
            modelMap.put("unitPost",  scMotionPost.getUnitPost());

            motionId = scMotionPost.getMotionId();
        }
        modelMap.put("motion", scMotionMapper.selectByPrimaryKey(motionId));

        return "sc/scMotion/scMotionPost/scMotionPost_au";
    }

    @RequiresPermissions("scMotionPost:del")
    @RequestMapping(value = "/scMotionPost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMotionPost_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMotionPostService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "删除动议所包含的岗位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMotionPost:del")
    @RequestMapping(value = "/scMotionPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scMotionPost_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMotionPostService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "批量删除动议所包含的岗位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    
    public void scMotionPost_export(ScMotionPostExample example, HttpServletResponse response) {

        List<ScMotionPost> records = scMotionPostMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属动议|100","岗位|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMotionPost record = records.get(i);
            String[] values = {
                record.getMotionId()+"",
                            record.getUnitPostId()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "动议所包含的岗位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
