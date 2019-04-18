package controller.party;

import controller.BaseController;
import domain.party.Organizer;
import domain.party.OrganizerExample;
import domain.party.OrganizerExample.Criteria;
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
import sys.constants.OwConstants;
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
public class OrganizerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer")
    public String organizer() {

        return "party/organizer/organizer_page";
    }

    @RequiresPermissions("organizer:list")
    @RequestMapping("/organizer_data")
    @ResponseBody
    public void organizer_data(HttpServletResponse response,
                                    Integer year,
                                    byte type,
                                    byte status,
                                    Integer userId,
                                    Integer partyId,
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

        OrganizerExample example = new OrganizerExample();
        Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type)
                .andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            organizer_export(example, response);
            return;
        }

        long count = organizerMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Organizer> records= organizerMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(organizer.class, organizerMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping(value = "/organizer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_au(Organizer record, HttpServletRequest request) {

        Integer id = record.getId();

        if (organizerService.idDuplicate(id, record.getType(), record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setStatus(OwConstants.OW_ORGANIZER_STATUS_NOW);
            organizerService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加组织员信息：{0}", record.getId()));
        } else {

            organizerService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新组织员信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:edit")
    @RequestMapping("/organizer_au")
    public String organizer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Organizer organizer = organizerMapper.selectByPrimaryKey(id);
            modelMap.put("organizer", organizer);
        }
        return "party/organizer/organizer_au";
    }

    @RequiresPermissions("organizer:del")
    @RequestMapping(value = "/organizer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map organizer_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            organizerService.batchDel(ids);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除组织员信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("organizer:changeOrder")
    @RequestMapping(value = "/organizer_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_organizer_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        organizerService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_MEMBER, "组织员信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void organizer_export(OrganizerExample example, HttpServletResponse response) {

        List<Organizer> records = organizerMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","类型|100","组织员|100","联系党委|100","联系单位|100","任职时间|100","离任时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Organizer record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getType()+"",
                            record.getUserId()+"",
                            record.getPartyId()+"",
                            record.getUnits(),
                            DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getDismissDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "组织员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/organizer_selects")
    @ResponseBody
    public Map organizer_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrganizerExample example = new OrganizerExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = organizerMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Organizer> records = organizerMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(Organizer record:records){

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
