package controller.ps;

import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.ps.PsAdmin;
import domain.ps.PsAdminExample;
import domain.ps.PsAdminExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import persistence.member.MemberViewMapper;
import sys.constants.LogConstants;
import sys.constants.PsInfoConstants;
import sys.tags.CmTag;
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
public class PsAdminController extends PsBaseController {
    @Autowired
    private MemberViewMapper memberViewMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("psAdmin:list")
    @RequestMapping("/psAdmin")
    public String psAdmin(@RequestParam(required = false, defaultValue = "0")boolean isHistory,
                          ModelMap modelMap) {

        modelMap.put("isHistory",isHistory);
        return "ps/psAdmin/psAdmin_page";
    }

    @RequiresPermissions("psAdmin:list")
    @RequestMapping("/psAdmin_data")
    @ResponseBody
    public void psAdmin_data(HttpServletResponse response,
                             @SortParam(required = false, defaultValue = "sort_order", tableName = "ps_admin") String sort,
                             @OrderParam(required = false, defaultValue = "asc") String order,
                             Integer psId,
                             Byte type,
                             Integer userId,
                             @RequestParam(required = false, defaultValue = "0")Boolean isHistory,
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

        PsAdminExample example = new PsAdminExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (psId!=null) {
            criteria.andPsIdEqualTo(psId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psAdmin_export(example, response);
            return;
        }
        if(isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        long count = psAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsAdmin> records= psAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psAdmin.class, psAdminMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psAdmin:edit")
    @RequestMapping(value = "/psAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdmin_au(PsAdmin record, String _startDate, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate,DateUtils.YYYYMMDD_DOT));
        }

        /*if (psAdminService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }*/

        if (id == null) {

            //判断管理员类型是否是'二级党校管理员'
            if(record.getType() == PsInfoConstants.PS_ADMIN_TYPE_PARTY){
                PsAdminExample psAdminExample = new PsAdminExample();
                psAdminExample.createCriteria()
                        .andTypeEqualTo(PsInfoConstants.PS_ADMIN_TYPE_PARTY)
                        .andPsIdEqualTo(record.getPsId())
                        .andIsHistoryEqualTo(false);
                List<PsAdmin> psAdmins = psAdminMapper.selectByExample(psAdminExample);
                if(psAdmins.size()>0){
                    return failed("重复添加二级党校管理员。");
                }
            }

            //新增时自动插入'所在单位'、'联系方式'；
            MemberViewExample memberViewExample = new MemberViewExample();
            memberViewExample.createCriteria().andUserIdEqualTo(record.getUserId());
            MemberView memberView = memberViewMapper.selectByExample(memberViewExample).get(0);
            record.setTitle(memberView.getUnit());
            record.setMobile(memberView.getMobile());

            record.setIsHistory(false);
            psAdminService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PS, "添加二级党校管理员：{0}", record.getId()));
        } else {

            psAdminService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PS, "更新二级党校管理员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psAdmin:edit")
    @RequestMapping("/psAdmin_au")
    public String psAdmin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(id);
            modelMap.put("psAdmin", psAdmin);
            modelMap.put("sysUser",CmTag.getUserById(psAdmin.getUserId()));
        }

        return "ps/psAdmin/psAdmin_au";
    }

    @RequiresPermissions("psAdmin:del")
    @RequestMapping(value = "/psAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdmin_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            psAdminService.del(id);
            logger.info(log( LogConstants.LOG_PS, "删除二级党校管理员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psAdmin:del")
    @RequestMapping(value = "/psAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psAdmin_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psAdminService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PS, "批量删除二级党校管理员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("psAdmin:changeOrder")
    @RequestMapping(value = "/psAdmin_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psAdmin_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psAdminService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PS, "二级党校管理员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void psAdmin_export(PsAdminExample example, HttpServletResponse response) {

        List<PsAdmin> records = psAdminMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级党校|100","类型|100","管理员|100","所在单位及职务|100","联系方式|100","任职起始时间|100","任职结束时间|100","现任/离任|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsAdmin record = records.get(i);
            String[] values = {
                record.getPsId()+"",
                            record.getType()+"",
                            record.getUserId()+"",
                            record.getTitle(),
                            record.getMobile(),
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getIsHistory()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "二级党校管理员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psAdmin_selects")
    @ResponseBody
    public Map psAdmin_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsAdminExample example = new PsAdminExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = psAdminMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsAdmin> records = psAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PsAdmin record:records){

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

    @RequiresPermissions("psAdmin:history")
    @RequestMapping("/psAdmin_history")
    public String psInfo_history() {

        return "ps/psAdmin/psAdmin_plan";
    }

    @RequiresPermissions("psAdmin:history")
    @RequestMapping(value = "/psAdmin_history", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_history(@RequestParam(value = "ids[]") Integer[] ids,String _endDate) {

        if (null != ids && ids.length>0){
            psAdminService.updateAdminStatus(ids,_endDate,true);
            logger.info(addLog(LogConstants.LOG_PS, "批量结束党校管理员职务：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
