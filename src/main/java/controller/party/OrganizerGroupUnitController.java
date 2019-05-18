package controller.party;

import controller.BaseController;
import domain.party.OrganizerGroup;
import domain.party.OrganizerGroupUnit;
import domain.party.OrganizerGroupUnitExample;
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
public class OrganizerGroupUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizerGroup:list")
    @RequestMapping("/organizerGroupUnit")
    public String organizerGroupUnit(int groupId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        OrganizerGroup organizerGroup = organizerGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("organizerGroup", organizerGroup);

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerGroupUnitExample example = new OrganizerGroupUnitExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order asc");

        int count = (int) organizerGroupUnitMapper.countByExample(example);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OrganizerGroupUnit> records = organizerGroupUnitMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("records", records);

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&groupId=" + groupId;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "party/organizerGroup/organizerGroupUnit";
    }

    @RequiresPermissions("organizerGroup:edit")
    @RequestMapping(value = "/organizerGroupUnit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUnit(int groupId, int unitId, HttpServletRequest request) {

        OrganizerGroupUnit record = new OrganizerGroupUnit();
        record.setGroupId(groupId);
        record.setUnitId(unitId);
        organizerGroupService.addUnit(record);

        logger.info(log(LogConstants.LOG_PARTY, "添加校级组织员分组联系单位：{0}, {1}", groupId, unitId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:del")
    @RequestMapping(value = "/organizerGroupUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUnit_del(HttpServletRequest request, Integer id, ModelMap modelMap) {

        organizerGroupService.delUnit(id);
        logger.info(log(LogConstants.LOG_PARTY, "删除校级组织员分组联系单位：{0}", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:changeOrder")
    @RequestMapping(value = "/organizerGroupUnit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroupUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerGroupService.unitChangeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_PARTY, "校级组织员分组联系单位调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
