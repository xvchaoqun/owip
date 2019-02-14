package controller.dr;

import domain.dr.DrMember;
import domain.dr.DrMemberExample;
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
import sys.constants.DrConstants;
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

public class DrMemberController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drMember:list")
    @RequestMapping("/drMember")
    public String drMember(HttpServletResponse response,
                               Integer userId,
                               @RequestParam(required = false,
                                       defaultValue = DrConstants.DR_MEMBER_STATUS_NOW + "") Byte status,
                               ModelMap modelMap) {

        modelMap.put("status", status);
        if(userId!= null) modelMap.put("sysUser", sysUserService.findById(userId));
        return "dr/drMember/drMember_page";
    }

    @RequiresPermissions("drMember:list")
    @RequestMapping("/drMember_data")
    public void drMember_data(HttpServletResponse response,
                                  Integer userId,
                                  @RequestParam(required = false,
                                          defaultValue = DrConstants.DR_MEMBER_STATUS_NOW + "") Byte status,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrMemberExample example = new DrMemberExample();
        DrMemberExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            drMember_export(example, response);
            return;
        }

        int count = (int) drMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrMember> records = drMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drMember.class, drMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drMember:edit")
    @RequestMapping(value = "/drMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drMember_au(DrMember record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(DrConstants.DR_MEMBER_STATUS_NOW);
            drMemberService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加推荐组成员：%s", record.getId()));
        } else {

            drMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新推荐组成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drMember:edit")
    @RequestMapping("/drMember_au")
    public String drMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrMember drMember = drMemberMapper.selectByPrimaryKey(id);
            modelMap.put("drMember", drMember);
        }
        return "dr/drMember/drMember_au";
    }

    @RequiresPermissions("drMember:edit")
    @RequestMapping(value = "/drMember_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drMember_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            drMemberService.abolish(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "撤销推荐组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drMember:edit")
    @RequestMapping(value = "/drMember_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drMember_reuse(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            drMemberService.reuse(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "重新任用推荐组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 逻辑删除
    @RequiresPermissions("drMember:del")
    @RequestMapping(value = "/drMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {

            drMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除推荐组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drMember:changeOrder")
    @RequestMapping(value = "/drMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        drMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "推荐组成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void drMember_export(DrMemberExample example, HttpServletResponse response) {

        List<DrMember> records = drMemberMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"推荐组成员", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrMember record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "推荐组成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // <drMember.id, realname>
    @RequestMapping("/drMember_selects")
    @ResponseBody
    public Map drMember_selects(Byte status, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = "%" + searchStr + "%";

        int count = iDrMapper.countMemberList(status, searchStr);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrMember> drMembers = iDrMapper.selectMemberList(status, searchStr,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map> options = new ArrayList<Map>();
        if (null != drMembers && drMembers.size() > 0) {

            for (DrMember drMember : drMembers) {

                Map option = new HashMap();
                option.put("text", drMember.getUser().getRealname());
                option.put("id", drMember.getId() + "");
                option.put("del", drMember.getStatus()!= DrConstants.DR_MEMBER_STATUS_NOW);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
