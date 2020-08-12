package controller.party;

import controller.BaseController;
import domain.party.Party;
import domain.party.PartyPost;
import domain.party.PartyPostView;
import domain.party.PartyPostViewExample;
import domain.sys.SysUserView;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class PartyPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyPost:menu")
    @RequestMapping("/party/partyPostList_page")
    public String partyPostList_page(@RequestParam(defaultValue = "1") Integer list,
                                     Integer partyId,
                                     ModelMap modelMap){

        Party party = new Party();
        if (partyId != null){
            party = partyMapper.selectByPrimaryKey(partyId);
        }
        modelMap.put("party", party);
        modelMap.put("list", list);

        return "/party/partyPost/partyPostList_page";
    }

    @RequiresPermissions("partyPost:list")
    @RequestMapping("/party/partyPost")
    public String partyPost(Integer userId,
                            HttpServletResponse response,
                            ModelMap modelMap) {
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        return "party/partyPost/partyPost_page";
    }

    @RequiresPermissions("partyPost:list")
    @RequestMapping("/party/partyPost_data")
    @ResponseBody
    public void partyPost_data(HttpServletResponse response,
                               Integer id,
                               Integer userId,
                               Date startDate,
                               Date endDate,
                               Integer partyId,
                               String detail,

                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyPostViewExample example = new PartyPostViewExample();
        PartyPostViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_sort_order desc, branch_sort_order desc, id asc");

        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (startDate != null) {
            criteria.andStartDateGreaterThan(startDate);
        }
        if (endDate != null) {
            criteria.andEndDateGreaterThan(endDate);
        }
        if (detail != null){
            criteria.andDetailLike(SqlUtils.like(detail));
        }

        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            partyPost_export(example, response);
            return;
        }*/

        long count = partyPostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyPostView> records = partyPostViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyPost.class, partyPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping(value = "/party/partyPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPost_au(PartyPost record,
                               Integer userId,
                               String startDate,
                               String endDate,
                               HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(startDate)) {
            record.setStartDate(DateUtils.parseDate(startDate, DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(endDate)) {
            record.setEndDate(DateUtils.parseDate(endDate, DateUtils.YYYYMMDD_DOT));
        }
        record.setUserId(userId);
        if (id == null) {

            partyPostService.insertSelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "添加党内任职经历：{0}", record.getId()));
        } else {

            partyPostService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "更新党内任职经历：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping("/party/partyPost_au")
    public String partyPost_au(Integer id,
                               Integer list,
                               Integer userId,
                               ModelMap modelMap) {

        modelMap.put("list", list);
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
            modelMap.put("user", user);
        }
        if (id != null) {
            PartyPost partyPost = partyPostMapper.selectByPrimaryKey(id);
            userId = partyPost.getUserId();
            user = sysUserService.findById(userId);
            modelMap.put("user", user);
            modelMap.put("partyPost", partyPost);
        }

        return "party/partyPost/partyPost_au";
    }

    @RequiresPermissions("partyPost:edit")
    @RequestMapping(value = "/party/partyPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyPost_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyPostService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除党内任职经历：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}