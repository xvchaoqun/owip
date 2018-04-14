package controller.sc.scPublic;

import domain.sc.scPublic.ScPublicUser;
import domain.sc.scPublic.ScPublicUserExample;
import domain.sc.scPublic.ScPublicUserExample.Criteria;
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
public class ScPublicUserController extends ScPublicBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPublicUser:list")
    @RequestMapping("/scPublicUser")
    public String scPublicUser() {

        return "sc/scPublic/scPublicUser/scPublicUser_page";
    }

    @RequiresPermissions("scPublicUser:list")
    @RequestMapping("/scPublicUser_data")
    public void scPublicUser_data(HttpServletResponse response,
                                    Integer publicId,
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

        ScPublicUserExample example = new ScPublicUserExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (publicId!=null) {
            criteria.andPublicIdEqualTo(publicId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scPublicUser_export(example, response);
            return;
        }

        long count = scPublicUserMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPublicUser> records= scPublicUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scPublicUser.class, scPublicUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scPublicUser:edit")
    @RequestMapping(value = "/scPublicUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPublicUser_au(ScPublicUser record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scPublicUserService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "添加干部任前公示对象：%s", record.getId()));
        } else {

            scPublicUserService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "更新干部任前公示对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPublicUser:edit")
    @RequestMapping("/scPublicUser_au")
    public String scPublicUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScPublicUser scPublicUser = scPublicUserMapper.selectByPrimaryKey(id);
            modelMap.put("scPublicUser", scPublicUser);
        }
        return "sc/scPublic/scPublicUser/scPublicUser_au";
    }

    @RequiresPermissions("scPublicUser:del")
    @RequestMapping(value = "/scPublicUser_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPublicUser_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scPublicUserService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "删除干部任前公示对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPublicUser:del")
    @RequestMapping(value = "/scPublicUser_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scPublicUser_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scPublicUserService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "批量删除干部任前公示对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPublicUser:changeOrder")
    @RequestMapping(value = "/scPublicUser_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPublicUser_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scPublicUserService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "干部任前公示对象调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void scPublicUser_export(ScPublicUserExample example, HttpServletResponse response) {

        List<ScPublicUser> records = scPublicUserMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属公示|100","关联表决记录|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPublicUser record = records.get(i);
            String[] values = {
                record.getPublicId()+"",
                            record.getVoteId()+"",
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部任前公示对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
