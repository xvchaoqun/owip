package controller.sc.scPublic;

import controller.sc.ScBaseController;
import domain.sc.scPublic.ScPublicUser;
import domain.sc.scPublic.ScPublicUserView;
import domain.sc.scPublic.ScPublicUserViewExample;
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
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScPublicUserController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPublicUser:list")
    @RequestMapping("/scPublicUser")
    public String scPublicUser(Integer committeeId, ModelMap modelMap) {

        modelMap.put("scCommittee", scCommitteeMapper.selectByPrimaryKey(committeeId));

        return "sc/scPublic/scPublicUser/scPublicUser_page";
    }

    @RequiresPermissions("scPublicUser:list")
    @RequestMapping("/scPublicUser_data")
    public void scPublicUser_data(HttpServletResponse response,
                                    Integer publicId,
                                 Integer committeeId,
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

        ScPublicUserViewExample example = new ScPublicUserViewExample();
        ScPublicUserViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (publicId!=null) {
            criteria.andPublicIdEqualTo(publicId);
        }
        if (committeeId!=null) {
            criteria.andCommitteeIdEqualTo(committeeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scPublicUser_export(example, response);
            return;
        }

        long count = scPublicUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPublicUserView> records= scPublicUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("scPublicUser:edit")
    @RequestMapping("/scPublicUser_selectScRecord")
    public String scPublicUser_selectScRecord(int id,
                                      ModelMap modelMap) {

        modelMap.put("scPublicUser", iScMapper.getScPublicUserView(id));

        return "sc/scPublic/scPublicUser/scPublicUser_selectScRecord";
    }

    @RequiresPermissions("scPublicUser:edit")
    @RequestMapping(value = "/scPublicUser_selectScRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPublicUser_selectScRecord(Integer id, Integer recordId, HttpServletRequest request) {

        if(recordId==null){
            commonMapper.excuteSql("update sc_public_user set record_id=null where id="+id);
        }else {
            ScPublicUser record = new ScPublicUser();
            record.setId(id);
            record.setRecordId(recordId);
            scPublicUserService.updateByPrimaryKeySelective(record);
        }
        logger.info(addLog(LogConstants.LOG_SC_PUBLIC, "干部任前公示对应选任纪实：%s, %s", id, recordId));
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

    public void scPublicUser_export(ScPublicUserViewExample example, HttpServletResponse response) {

        List<ScPublicUserView> records = scPublicUserViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属公示|100","关联表决记录|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPublicUserView record = records.get(i);
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
