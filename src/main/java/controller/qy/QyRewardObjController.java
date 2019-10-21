package controller.qy;

import domain.qy.*;
import domain.qy.QyRewardObjExample.Criteria;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class QyRewardObjController extends QyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyRewardObj")
    public String qyRewardObj(Integer recordId,Byte type) {

        return "qy/qyRewardObj/qyRewardObj_page";
    }

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyRewardObj_data")
    @ResponseBody
    public void qyRewardObj_data(HttpServletResponse response,
                                    Integer recordId,
                                    Byte type,   // type=1 党委   2 党支部  3 党员  4 党日活动
                                    Integer partyId,
                                    Integer branchId,
                                    Integer userId,
                                    String meetingName,
                                     Byte exportType,      // exportType=1 通过记录导出  2 直接导出
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

        QyRewardObjViewExample example = new QyRewardObjViewExample();
        QyRewardObjViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");
        if (recordId!=null) {
            criteria.andRecordIdEqualTo(recordId);
        }
        if (type!=null) {
            criteria.andRewardTypeEqualTo(type);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(meetingName)) {
            criteria.andMeetingNameLike(SqlUtils.like(meetingName));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0){
                if(exportType!=null &&exportType==1)
                criteria.andRecordIdIn(Arrays.asList(ids));
                if(exportType!=null &&exportType==2)
                    criteria.andIdIn(Arrays.asList(ids));
            }
            qyRewardObj_export(example,type, response);
            return;
        }

        long count = qyRewardObjViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<QyRewardObjView> records= qyRewardObjViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(qyRewardObj.class, qyRewardObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyRewardObj_au(QyRewardObj record,Byte type, HttpServletRequest request) {

        Integer id = record.getId();

       /* if (qyYearRewardService.idDuplicate(id, yearId,rewardId)) {
            return failed("添加重复");
        }*/
        if (id == null) {
            if(record.getPartyId()!=null)
                record.setPartyName(CmTag.getParty(record.getPartyId()).getName());
           if(record.getBranchId()!=null)
                 record.setBranchName(CmTag.getBranch(record.getBranchId()).getName());

            qyRewardObjService.insertSelective(record);
            logger.info(log( LogConstants.LOG_QY, "添加七一表彰_奖励对象：{0}", record.getId()));
        } else {

            qyRewardObjService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_QY, "更新七一表彰_奖励对象：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyRewardObj_au")
    public String qyRewardObj_au(Integer id, Integer recordId,Byte type,ModelMap modelMap) {

        if (id != null) {
            QyRewardObjViewExample example = new QyRewardObjViewExample();
            example.createCriteria().andIdEqualTo(id);
            List<QyRewardObjView> QyRewardObjViews = qyRewardObjViewMapper.selectByExample(example);
            if(QyRewardObjViews.size()>0){
                QyRewardObjView qyRewardObjView=QyRewardObjViews.get(0);
                modelMap.put("qyRewardObjView", qyRewardObjView);
            }
        }
        return "qy/qyRewardObj/qyRewardObj_au";
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyRewardObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            qyRewardObjService.del(id);
            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_奖励对象：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map qyRewardObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            qyRewardObjService.batchDel(ids);
            logger.info(log( LogConstants.LOG_QY, "批量删除七一表彰_奖励对象：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyRewardObj_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyRewardObj_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        qyRewardObjService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_QY, "七一表彰_奖励对象调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void qyRewardObj_export(QyRewardObjViewExample example,Byte type, HttpServletResponse response) {

        List<QyRewardObjView> records = qyRewardObjViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles=null;

        if(type!=null){
            if(type==1)
                 titles = new String[]{ "年度","奖项|150","院系级党委编号|150","获表彰党委名称|250"};
            if(type==2)
                titles = new String[]{ "年度","奖项|150", "院系级党委编号|150","所属党委名称|250","党支部编号|150","获表彰党支部名称|200"};
            if(type==3)
                titles = new String[]{ "年度","奖项|150","院系级党委编号|150","所属党委名称|250","党员工作证号|150", "获表彰党员姓名|150",};
            if(type==4)
                titles = new String[]{ "年度","奖项|150","院系级党委编号|150","获表彰党委名称|250", "党支部编号|150","获表彰党支部名称|200","获表彰党日活动名称|250"};
        }
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            QyRewardObjView record = records.get(i);
            String[] values=null;
            if(type==1)
                values =new String[] {
                            record.getYear()+"",
                            record.getRewardName(),
                            record.getParty().getCode(),
                            record.getPartyName()
            };
            if(type==2)
                values =new String[] {
                        record.getYear()+"",
                        record.getRewardName(),
                        record.getParty().getCode(),
                        record.getPartyName(),
                        record.getBranch()==null?"":record.getBranch().getCode()+"",
                        record.getBranchName()
                };
            if(type==3)
                values =new String[] {
                        record.getYear()+"",
                        record.getRewardName(),
                        record.getParty().getCode(),
                        record.getPartyName(),
                        record.getUser().getCode()+"",
                        record.getUser().getRealname()
                };
            if(type==4)
                values =new String[] {
                        record.getYear()+"",
                        record.getRewardName(),
                        record.getParty().getCode(),
                        record.getPartyName(),
                        record.getBranch().getCode()+"",
                        record.getBranchName(),
                        record.getMeetingName(),
                };

            valuesList.add(values);
        }
        String fileName = String.format("七一表彰获奖情况(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/qyRewardObj_selects")
    @ResponseBody
    public Map qyRewardObj_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyRewardObjExample example = new QyRewardObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = qyRewardObjMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<QyRewardObj> records = qyRewardObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(QyRewardObj record:records){

                Map<String, Object> option = new HashMap<>();
          //      option.put("text", record.getName());
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
