package controller.ps;

import domain.party.Party;
import domain.ps.PsAdminParty;
import domain.ps.PsAdminPartyExample;
import domain.ps.PsAdminPartyExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.party.PartyMapper;
import persistence.ps.PsAdminPartyMapper;
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
@RequestMapping("/ps")
public class PsAdminPartyController extends PsBaseController {
    @Autowired
    private PartyMapper partyMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("psAdminParty:list")
    @RequestMapping("/psAdminParty")
    public String psAdminParty(Integer adminId,ModelMap modelMap) {
        PsAdminPartyExample psAdminPartyExample = new PsAdminPartyExample();
        psAdminPartyExample.createCriteria()
                .andAdminIdEqualTo(adminId)
                .andIsHistoryEqualTo(false);
        List<PsAdminParty> psAdminParties = psAdminPartyMapper.selectByExample(psAdminPartyExample);
        modelMap.put("psAdminParties",psAdminParties);
        return "ps/psAdminParty/psAdminParty_page";
    }

    //@RequiresPermissions("psAdminParty:list")
    @RequestMapping("/psAdminParty_data")
    @ResponseBody
    public void psAdminParty_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ps_admin_party") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
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

        PsAdminPartyExample example = new PsAdminPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));


        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psAdminParty_export(example, response);
            return;
        }

        long count = psAdminPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsAdminParty> records= psAdminPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psAdminParty.class, psAdminPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("psAdminParty:edit")
    @RequestMapping(value = "/psAdminParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdminParty_au(PsAdminParty record, String _startDate, String _endDate, HttpServletRequest request) {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate,DateUtils.YYYYMMDD_DOT));
        }
        if (id == null) {

            if(psAdminPartyService.idDuplicate(id,record.getPartyId(),record.getAdminId())) {
                return failed("添加重复");
            }

            record.setIsHistory(false);
            psAdminPartyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PS, "添加二级党校管理员管理的单位：{0}", record.getId()));
        } else {

            psAdminPartyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PS, "更新二级党校管理员管理的单位：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("psAdminParty:edit")
    @RequestMapping("/psAdminParty_au")
    public String psAdminParty_au(Integer id,ModelMap modelMap,
                                  @RequestParam(required = false, defaultValue = "0")boolean isHistory) {

        if (id != null) {
            PsAdminParty psAdminParty = psAdminPartyMapper.selectByPrimaryKey(id);
            Party party = partyMapper.selectByPrimaryKey(psAdminParty.getPartyId());
            modelMap.put("psAdminParty", psAdminParty);
            modelMap.put("party",party);
        }
        modelMap.put("isHistory",isHistory);
        return "ps/psAdminParty/psAdminParty_au";
    }

    //@RequiresPermissions("psAdminParty:del")
    @RequestMapping(value = "/psAdminParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdminParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            psAdminPartyService.del(id);
            logger.info(log( LogConstants.LOG_PS, "删除二级党校管理员管理的单位：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("psAdminParty:del")
    @RequestMapping(value = "/psAdminParty_batchDel", method = RequestMethod.POST)
    public String psAdminParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            psAdminPartyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PS, "批量删除二级党校管理员管理的单位：{0}", StringUtils.join(ids, ",")));
        }

        return "ps/psAdminParty/psAdminParty_au";
    }

    /*@RequiresPermissions("psAdminParty:changeOrder")
    @RequestMapping(value = "/psAdminParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdminParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psAdminPartyService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PS, "二级党校管理员管理的单位调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/

    public void psAdminParty_export(PsAdminPartyExample example, HttpServletResponse response) {

        List<PsAdminParty> records = psAdminPartyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"开始管理时间|100","结束管理时间|100","现任/离任|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsAdminParty record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getIsHistory()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "二级党校管理员管理的单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psAdminParty_selects")
    @ResponseBody
    public Map psAdminParty_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsAdminPartyExample example = new PsAdminPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = psAdminPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsAdminParty> records = psAdminPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PsAdminParty record:records){

                Map<String, Object> option = new HashMap<>();
                //option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    }