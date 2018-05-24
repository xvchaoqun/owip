package controller.sc.scGroup;

import domain.ext.ExtJzg;
import domain.sc.scGroup.ScGroupMember;
import domain.sc.scGroup.ScGroupMemberExample;
import domain.sc.scGroup.ScGroupMemberExample.Criteria;
import domain.sc.scGroup.ScGroupMemberView;
import domain.sc.scGroup.ScGroupMemberViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
public class ScGroupMemberController extends ScGroupBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroupMember:list")
    @RequestMapping("/scGroupMember")
    public String scGroupMember(@RequestParam(required = false, defaultValue = "1") Boolean isCurrent, ModelMap modelMap) {

        modelMap.put("isCurrent", isCurrent);
        return "sc/scGroup/scGroupMember/scGroupMember_page";
    }

    @RequiresPermissions("scGroupMember:list")
    @RequestMapping("/scGroupMember_data")
    public void scGroupMember_data(HttpServletResponse response,
                                    Integer userId,
                                   @RequestParam(required = false, defaultValue = "1") Boolean isCurrent,
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

        ScGroupMemberExample example = new ScGroupMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (isCurrent!=null) {
            criteria.andIsCurrentEqualTo(isCurrent);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scGroupMember_export(example, response);
            return;
        }

        long count = scGroupMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupMember> records= scGroupMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scGroupMember.class, scGroupMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scGroupMember:edit")
    @RequestMapping(value = "/scGroupMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupMember_au(ScGroupMember record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));
        record.setIsLeader(BooleanUtils.isTrue(record.getIsLeader()));

        if (id == null) {
            scGroupMemberService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "添加干部工作小组会组成名单：%s", record.getId()));
        } else {

            scGroupMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "更新干部工作小组会组成名单：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupMember:edit")
    @RequestMapping("/scGroupMember_au")
    public String scGroupMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScGroupMember scGroupMember = scGroupMemberMapper.selectByPrimaryKey(id);
            modelMap.put("scGroupMember", scGroupMember);

            modelMap.put("sysUser", sysUserService.findById(scGroupMember.getUserId()));
        }
        return "sc/scGroup/scGroupMember/scGroupMember_au";
    }

    @RequiresPermissions("scGroupMember:del")
    @RequestMapping(value = "/scGroupMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scGroupMemberService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "删除干部工作小组会组成名单：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupMember:transfer")
    @RequestMapping(value = "/scGroupMember_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupMember_transfer(HttpServletRequest request,
                                         Boolean isCurrent,
                                         @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            scGroupMemberService.transfer(ids, BooleanUtils.isTrue(isCurrent));
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量转移干部工作小组会组成名单：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupMember:del")
    @RequestMapping(value = "/scGroupMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量删除干部工作小组会组成名单：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupMember:changeOrder")
    @RequestMapping(value = "/scGroupMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scGroupMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_SC_GROUP, "干部工作小组会组成名单调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void scGroupMember_export(ScGroupMemberExample example, HttpServletResponse response) {

        List<ScGroupMember> records = scGroupMemberMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"成员|100","是否现有成员|100","是否组长|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScGroupMember record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getIsCurrent() + "",
                            record.getIsLeader() + "",
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部工作小组会组成名单_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 根据账号或姓名或学工号选择用户
    @RequestMapping("/scGroupMember_selects")
    @ResponseBody
    public Map scGroupMember_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScGroupMemberViewExample example = new ScGroupMemberViewExample();
        //example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(searchStr)) {
            ScGroupMemberViewExample.Criteria criteria = example.or().andUsernameLike("%" + searchStr + "%");
            ScGroupMemberViewExample.Criteria criteria1 = example.or().andCodeLike("%" + searchStr + "%");
            ScGroupMemberViewExample.Criteria criteria2 = example.or().andRealnameLike("%" + searchStr + "%");
        }

        long count = scGroupMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupMemberView> uvs = scGroupMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (ScGroupMemberView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(uv.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                    if (extJzg != null) {
                        option.put("unit", extJzg.getDwmc());
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
