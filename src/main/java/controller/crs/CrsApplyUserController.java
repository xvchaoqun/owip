package controller.crs;

import domain.crs.CrsApplyUser;
import domain.crs.CrsApplyUserExample;
import domain.crs.CrsApplyUserExample.Criteria;
import domain.crs.CrsPost;
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
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class CrsApplyUserController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsApplyUser:list")
    @RequestMapping("/crsApplyUser")
    public String crsApplyUser() {

        return "crs/crsApplyUser/crsApplyUser_page";
    }

    @RequiresPermissions("crsApplyUser:list")
    @RequestMapping("/crsApplyUser_data")
    @ResponseBody
    public void crsApplyUser_data(HttpServletResponse response,
                                    Integer postId,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsApplyUserExample example = new CrsApplyUserExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }

        long count = crsApplyUserMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsApplyUser> records= crsApplyUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsApplyUser.class, crsApplyUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsApplyUser:edit")
    @RequestMapping(value = "/crsApplyUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplyUser_au(CrsApplyUser record,
                                  @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                  HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            crsApplyUserService.insertSelective(record, userIds);
            logger.info(addLog( LogConstants.LOG_CRS, "添加补报人员：%s", record.getId()));
        } else {

            CrsApplyUser crsApplyUser = crsApplyUserMapper.selectByPrimaryKey(id);
            if(crsApplyUser.getStatus()==CrsConstants.CRS_APPLY_USER_STATUS_FINISH){
                return failed("已完成补报，不允许修改。");
            }

            crsApplyUserService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CRS, "更新补报人员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplyUser:edit")
    @RequestMapping("/crsApplyUser_au")
    public String crsApplyUser_au(Integer id, Integer postId, ModelMap modelMap) {

        if (id != null) {
            CrsApplyUser crsApplyUser = crsApplyUserMapper.selectByPrimaryKey(id);
            modelMap.put("crsApplyUser", crsApplyUser);

            postId = crsApplyUser.getPostId();
        }

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crsPost", crsPost);

        return "crs/crsApplyUser/crsApplyUser_au";
    }

    @RequiresPermissions("crsApplyUser:edit")
    @RequestMapping(value = "/crsApplyUser_status", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplyUser_status(HttpServletRequest request, byte status,
                                      @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length>0) {

            CrsApplyUser record = new CrsApplyUser();
            record.setStatus(status);
            CrsApplyUserExample example = new CrsApplyUserExample();
            Criteria criteria = example.createCriteria().andIdIn(Arrays.asList(ids))
                    .andStatusNotEqualTo(CrsConstants.CRS_APPLY_USER_STATUS_FINISH)
                    .andEndTimeLessThan(new Date());

            crsApplyUserMapper.updateByExampleSelective(record, example);

            logger.info(addLog( LogConstants.LOG_CRS, "补报人员改变状态：%s %s", StringUtils.join(ids, ","), status));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplyUser:del")
    @RequestMapping(value = "/crsApplyUser_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crsApplyUser_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crsApplyUserService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CRS, "批量删除补报人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsApplyUser:changeOrder")
    @RequestMapping(value = "/crsApplyUser_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsApplyUser_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsApplyUserService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_CRS, "补报人员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
