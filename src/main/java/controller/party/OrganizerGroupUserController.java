package controller.party;

import controller.BaseController;
import domain.party.OrganizerGroup;
import domain.party.OrganizerGroupUser;
import domain.party.OrganizerGroupUserExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class OrganizerGroupUserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizerGroup:list")
    @RequestMapping("/organizerGroupUser")
    public String organizerGroupUser(int groupId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        OrganizerGroup organizerGroup = organizerGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("organizerGroup", organizerGroup);

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerGroupUserExample example = new OrganizerGroupUserExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order asc");

        int count = (int) organizerGroupUserMapper.countByExample(example);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OrganizerGroupUser> records = organizerGroupUserMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("records", records);

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&groupId=" + groupId;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "party/organizerGroup/organizerGroupUser";
    }

    @RequiresPermissions("organizerGroup:edit")
    @RequestMapping(value = "/organizerGroupUser", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUser(int groupId, int userId, HttpServletRequest request) {

        OrganizerGroupUser record = new OrganizerGroupUser();
        record.setGroupId(groupId);
        record.setUserId(userId);
        organizerGroupService.addUser(record);

        logger.info(log(LogConstants.LOG_PARTY, "添加校级组织员分组成员：{0}, {1}", groupId, userId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:del")
    @RequestMapping(value = "/organizerGroupUser_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUser_del(HttpServletRequest request, Integer id, ModelMap modelMap) {

        organizerGroupService.delUser(id);
        logger.info(log(LogConstants.LOG_PARTY, "删除校级组织员分组成员：{0}", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:changeOrder")
    @RequestMapping(value = "/organizerGroupUser_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUser_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerGroupService.unitChangeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_PARTY, "校级组织员分组成员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
