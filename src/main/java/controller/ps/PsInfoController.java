package controller.ps;

import domain.base.MetaType;
import domain.ps.*;
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
import persistence.ps.common.IPsMapper;
import sys.constants.LogConstants;
import sys.constants.PsInfoConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/ps")
public class PsInfoController extends PsBaseController {
    @Autowired
    protected IPsMapper iPsMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 基本信息
    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_base")
    public String psInfo_base(Integer id, ModelMap modelMap) {

        //党校信息
        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        //建设单位信息
        PsParty hostParty = new PsParty();
        List<PsParty> psParties = getPsPsParty(true,id);
        if(psParties.size()>0) hostParty = psParties.get(0);//主建单位信息
        List<PsParty> jointPartyList = getPsPsParty(false,id);//联合建设单位信息
        modelMap.put("hostParty",hostParty);
        modelMap.put("jointPartyList",jointPartyList);

        //组织架构信息
        PsMember principal = new PsMember();
        List<PsMember> psMembers = getPsMember("ps_principal",id);
        if(psMembers.size()>0) principal = psMembers.get(0);//二级党校校长信息
        List<PsMember> viceprincipalList = getPsMember("ps_viceprincipal",id);//二级党校副校长信息
        modelMap.put("principal",principal);
        modelMap.put("viceprincipalList",viceprincipalList);

        //党员人数信息
        Map allPartyNubmerCount = iPsMapper.count(getPartyIdList(null,id));//全部建设单位
        Map hostPartyNumberCount = iPsMapper.count(getPartyIdList(true,id));//主建设单位
        Map notHostPartyNumberCount = iPsMapper.count(getPartyIdList(false,id));//联合建设单位
        modelMap.put("allPartyNubmerCount",allPartyNubmerCount);
        modelMap.put("hostPartyNumberCount",hostPartyNumberCount);
        modelMap.put("notHostPartyNumberCount",notHostPartyNumberCount);

        return "ps/psInfo/psInfo_base";
    }

    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_view")
    public String psInfo_view(int id, ModelMap modelMap) {

        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        return "ps/psInfo/psInfo_view";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo")
    public String psInfo( @RequestParam(required = false, defaultValue = "0") boolean isHistory, ModelMap modelMap) {

        modelMap.put("isHistory", isHistory);

        return "ps/psInfo/psInfo_page";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo_data")
    public void psInfo_data(HttpServletResponse response,
                                 String name,
                                 @RequestParam(required = false, defaultValue = "0") boolean isHistory,
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

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria = example.createCriteria().andIsHistoryEqualTo(isHistory);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psInfo_export(example, response);
            return;
        }

        long count = psInfoMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsInfo> records= psInfoMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psInfo.class, psInfoMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psInfo:edit")
    @RequestMapping(value = "/psInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_au(PsInfo record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setIsHistory(false);
            psInfoService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "添加二级党校：%s", record.getId()));
        } else {

            psInfoService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:edit")
    @RequestMapping("/psInfo_au")
    public String psInfo_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
            modelMap.put("psInfo", psInfo);
        }
        return "ps/psInfo/psInfo_au";
    }

    @RequiresPermissions("psInfo:history")
    @RequestMapping(value = "/psInfo_history", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_history(@RequestParam(value = "ids[]") Integer[] ids,String _abolishDate) {

        if (null != ids && ids.length>0){
            psInfoService.history(ids,_abolishDate);
            logger.info(addLog(LogConstants.LOG_PS, "批量转移二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:history")
    @RequestMapping("/psInfo_history")
    public String psInfo_history() {

        return "ps/psInfo/psInfo_plan";
    }

    @RequiresPermissions("psInfo:del")
    @RequestMapping(value = "/psInfo_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psInfo_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psInfoService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PS, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:changeOrder")
    @RequestMapping(value = "/psInfo_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psInfoService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PS, "二级党校调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void psInfo_export(PsInfoExample example, HttpServletResponse response) {

        List<PsInfo> records = psInfoMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"二级党校名称|100","设立日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsInfo record = records.get(i);
            String[] values = {
                record.getName(),
                            DateUtils.formatDate(record.getFoundDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psInfo_selects")
    @ResponseBody
    public Map psInfo_selects(Integer pageSize,
                                   Boolean isHistory,
                                   Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria = example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("is_history asc, sort_order desc");

        if(isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = psInfoMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsInfo> psInfos = psInfoMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != psInfos && psInfos.size()>0){

            for(PsInfo psInfo:psInfos){

                Map<String, Object> option = new HashMap<>();
                option.put("text", psInfo.getName());
                option.put("id", psInfo.getId() + "");
                option.put("del", psInfo.getIsHistory());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    /**
     * 获取未结束的二级党校建设单位
     * @param isHost 是否主建单位
     * @param psId 二级党校id
     * @return 建设单位信息
     */
    public List<PsParty> getPsPsParty(Boolean isHost,Integer psId){
        PsPartyExample psPartyExample = new PsPartyExample();
        psPartyExample.createCriteria()
                .andPsIdEqualTo(psId)
                .andIsFinishEqualTo(PsInfoConstants.PS_STATUS_NOT_HISTORY);

        List<PsParty> allPsParties = psPartyMapper.selectByExample(psPartyExample);
        if(isHost == null) return allPsParties;//全部建设单位

        List<PsParty> psParties = new ArrayList<>();
        for(PsParty psParty : allPsParties){
            if (psParty.getIsHost() == isHost){
                psParties.add(psParty);
            }
        }
        return psParties;
    }
    //获取现任的组织人员
    public List<PsMember> getPsMember(String code, Integer id){
        MetaType metaType = CmTag.getMetaTypeByCode(code);
        PsMemberExample psMemberExample = new PsMemberExample();
        psMemberExample.createCriteria().andPsIdEqualTo(id)
                .andIsHistoryEqualTo(false).andTypeEqualTo(metaType.getId());
        return psMemberMapper.selectByExample(psMemberExample);
    }
    //获取未结束的建设单位ID
    public List<Integer> getPartyIdList(Boolean isHost, Integer psId){
        List<PsParty> psPartyList = getPsPsParty(null,psId);
        List<Integer> psPartyIdList = new ArrayList<Integer>();
        for (PsParty psParty : psPartyList){
            if(isHost == null){//全部未结束的建设单位Id
                psPartyIdList.add(psParty.getPartyId());
                continue;
            }
            if (isHost){//未结束的主建设单位ID
                if(psParty.getIsHost()){
                    psPartyIdList.add(psParty.getPartyId());
                }
            }else {//未结束的联合建设单位ID
                if(!psParty.getIsHost()){
                    psPartyIdList.add(psParty.getPartyId());
                }
            }
        }
        if(psPartyIdList.size() == 0) psPartyIdList.add(-1);
        return psPartyIdList;
    }

    public Integer getPsIdbyUserId(Integer userId){
        PsAdminExample psAdminExample = new PsAdminExample();
        psAdminExample.createCriteria()
                .andUserIdEqualTo(userId).andIsHistoryEqualTo(false)
                .andTypeEqualTo(PsInfoConstants.PS_ADMIN_TYPE_PARTY);
        List<PsAdmin> psAdmins = psAdminMapper.selectByExample(psAdminExample);
        if (psAdmins.size() == 0){
            return -1;
        }
        return psAdmins.get(0).getPsId();
    }
}
