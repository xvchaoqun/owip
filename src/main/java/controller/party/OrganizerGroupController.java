package controller.party;

import controller.BaseController;
import domain.party.Organizer;
import domain.party.OrganizerGroup;
import domain.party.OrganizerGroupExample;
import domain.party.OrganizerGroupExample.Criteria;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class OrganizerGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizerGroup:list")
    @RequestMapping("/organizerGroup")
    public String organizerGroup(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                 Integer userId, Integer unitId,
                                 byte type, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        if (userId != null) {
            Organizer organizer = organizerService.get(type, userId);
            modelMap.put("organizer", organizer);
        }
        if (unitId != null) {
            modelMap.put("unit", unitService.findAll().get(unitId));
        }

        return "party/organizerGroup/organizerGroup_page";
    }

    @RequiresPermissions("organizerGroup:list")
    @RequestMapping("/organizerGroup_data")
    @ResponseBody
    public void organizerGroup_data(HttpServletResponse response,
                                    String name,
                                    Integer userId,
                                    Integer unitId,
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

        OrganizerGroupExample example = new OrganizerGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (userId != null) {
            List<Integer> organizerGroupIds = iPartyMapper.getOrganizerGroupIds(userId);
            if (organizerGroupIds.size() > 0) {
                criteria.andIdIn(organizerGroupIds);
            }else{
                criteria.andIdIsNull();
            }
        }
        if (unitId != null) {
            List<Integer> unitGroupIds = iPartyMapper.getUnitGroupIds(unitId);
            if (unitGroupIds.size() > 0) {
                criteria.andIdIn(unitGroupIds);
            }else{
                criteria.andIdIsNull();
            }
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            organizerGroup_export(example, response);
            return;
        }

        long count = organizerGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OrganizerGroup> records = organizerGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(organizerGroup.class, organizerGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("organizerGroup:edit")
    @RequestMapping(value = "/organizerGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroup_au(OrganizerGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            organizerGroupService.insertSelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "添加校级组织员分组：{0}", record.getId()));
        } else {

            organizerGroupService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_PARTY, "更新校级组织员分组：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:edit")
    @RequestMapping("/organizerGroup_au")
    public String organizerGroup_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OrganizerGroup organizerGroup = organizerGroupMapper.selectByPrimaryKey(id);
            modelMap.put("organizerGroup", organizerGroup);
        }
        return "party/organizerGroup/organizerGroup_au";
    }

    @RequiresPermissions("organizerGroup:del")
    @RequestMapping(value = "/organizerGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map organizerGroup_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            organizerGroupService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除校级组织员分组：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizerGroup:changeOrder")
    @RequestMapping(value = "/organizerGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizerGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerGroupService.changeOrder(id, addNum);
        logger.info(log(LogConstants.LOG_PARTY, "校级组织员分组调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void organizerGroup_export(OrganizerGroupExample example, HttpServletResponse response) {

        List<OrganizerGroup> records = organizerGroupMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            OrganizerGroup record = records.get(i);
            String[] values = {
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "校级组织员分组_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/organizerGroup_selects")
    @ResponseBody
    public Map organizerGroup_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerGroupExample example = new OrganizerGroupExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = organizerGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OrganizerGroup> records = organizerGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(OrganizerGroup record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
