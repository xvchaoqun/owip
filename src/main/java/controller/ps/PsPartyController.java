package controller.ps;

import controller.global.OpException;
import domain.party.Party;
import domain.party.PartyExample;
import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import domain.ps.PsPartyExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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
import sys.constants.PsInfoConstants;
import sys.constants.SystemConstants;
import sys.spring.RequestDateRange;
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
public class PsPartyController extends PsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("psParty:list")
    @RequestMapping("/psParty")
    public String psParty(Integer psId, ModelMap modelMap) {

        modelMap.put("isHaveHostUnit",psPartyService.isHaveHostUnit(psId));
        return "ps/psParty/psParty_page";
    }

    @RequiresPermissions("psParty:list")
    @RequestMapping("/psParty_data")
    @ResponseBody
    public void psParty_data(HttpServletResponse response,
                                    Boolean isHost,//是否主建单位
                                    Boolean isFinish,//是否结束
                                    Integer pageSize,
                                    Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsPartyExample example = new PsPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_finish asc,sort_order desc");

        if (isHost!=null) {
            criteria.andIsHostEqualTo(isHost);
        }
        if (isFinish!=null) {
            criteria.andIsFinishEqualTo(isFinish);
        }

        long count = psPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsParty> records= psPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psParty:edit")
    @RequestMapping(value = "/psParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psParty_au(PsParty psParty,
                             String _startDate,
                             HttpServletRequest request) {

        Integer id = psParty.getId();
        if (StringUtils.isNotBlank(_startDate)){
            psParty.setStartDate(DateUtils.parseDate(_startDate,DateUtils.YYYYMM));
        }

        //判断选择单位是否已经组建二级党校
        PsPartyExample psPartyExample = new PsPartyExample();
        psPartyExample.createCriteria()
                .andIsFinishEqualTo(false)
                .andPartyIdEqualTo(psParty.getPartyId());
        List<PsParty> psParties = psPartyMapper.selectByExample(psPartyExample);
        if (psParties.size()>0) {
            throw new OpException("该单位已经组建二级党校。");
        }

        if (id == null) {

            //默认添加的建设单位未结束
            psParty.setIsFinish(false);
            psPartyService.insertSelective(psParty);
            logger.info(log( LogConstants.LOG_PS, "添加二级党校建设单位：{0}", psParty.getId()));
        } else {

            psPartyService.updateByPrimaryKeySelective(psParty);
            logger.info(log( LogConstants.LOG_PS, "更新二级党校建设单位：{0}", psParty.getId()));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psParty:edit")
    @RequestMapping("/psParty_au")
    public String psParty_au(HttpServletResponse response,
                             Integer id,
                             Integer psId,
                             @RequestParam(required = false, defaultValue = "0") boolean isHost,//是否是主建单位
                             ModelMap modelMap) {

        if (id != null) {
            PsParty psParty = psPartyMapper.selectByPrimaryKey(id);
            Party party = partyMapper.selectByPrimaryKey(psParty.getPartyId());
            modelMap.put("psParty", psParty);
            modelMap.put("party", party);
        }

        if(id == null){
            if (isHost){
                if(psPartyService.isHaveHostUnit(psId)){
                    throw new OpException("已经拥有主建单位。");
                }
            }
        }

        modelMap.put("isHost",isHost);
        return "ps/psParty/psParty_au";
    }

    @RequiresPermissions("psParty:del")
    @RequestMapping(value = "/psParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            psPartyService.del(id);
            logger.info(log( LogConstants.LOG_PS, "删除二级党校建设单位：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psParty:del")
    @RequestMapping(value = "/psParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            psPartyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PS, "批量删除二级党校建设单位：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/psParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psPartyService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_ADMIN, "二级党校建设单位调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void psParty_export(PsPartyExample example, HttpServletResponse response) {

        List<PsParty> records = psPartyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级党校|100","建设单位|100","主建设单位/联合建设单位|100","是否结束|100","开始时间|100","结束时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsParty record = records.get(i);
            String[] values = {
                record.getPsId()+"",
                            record.getPartyId()+"",
                            record.getIsHost()+"",
                            record.getIsFinish()+"",
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "二级党校建设单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psParty_selects")
    @ResponseBody
    public Map psParty_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsPartyExample example = new PsPartyExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = psPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsParty> records = psPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PsParty record:records){

                String psPartyName = partyMapper.selectByPrimaryKey(record.getPartyId()).getName();
                Map<String, Object> option = new HashMap<>();
                option.put("text", psPartyName);
                option.put("id", record.getPartyId());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("psParty:history")
    @RequestMapping("/psParty_history")
    public String psParty_history() {

        return "ps/psParty/psParty_plan";
    }

    @RequiresPermissions("psParty:history")
    @RequestMapping(value = "/psParty_history", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psParty_history(@RequestParam(value = "ids[]") Integer[] ids,
                                  @RequestParam(required = false, defaultValue = "0") Boolean isHost,
                                  String _endDate) {

        if (null != ids && ids.length>0){
            psPartyService.updatePartyStatus(ids,_endDate,true,isHost);
            logger.info(addLog(LogConstants.LOG_PS, "批量结束建设单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

}
