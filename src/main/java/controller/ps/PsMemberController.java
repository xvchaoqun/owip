package controller.ps;

import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import domain.ps.PsMemberExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
@RequestMapping("/ps")
public class PsMemberController extends PsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("psMember:list")
    @RequestMapping("/psMember")
    public String psMember() {

        return "ps/psMember/psMember_page";
    }

    @RequiresPermissions("psMember:list")
    @RequestMapping("/psMember_data")
    @ResponseBody
    public void psMember_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ps_member") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer psId,
                                    String seq,
                                    Byte type,
                                    Integer userId,
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

        PsMemberExample example = new PsMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (psId!=null) {
            criteria.andPsIdEqualTo(psId);
        }
        if (StringUtils.isNotBlank(seq)) {
            criteria.andSeqLike(SqlUtils.like(seq));
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
            psMember_export(example, response);
            return;
        }

        long count = psMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsMember> records= psMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psMember.class, psMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psMember:edit")
    @RequestMapping(value = "/psMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psMember_au(PsMember record, HttpServletRequest request) {

        Integer id = record.getId();

        if (psMemberService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            psMemberService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PS, "添加二级党校班子成员：{0}", record.getId()));
        } else {

            psMemberService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PS, "更新二级党校班子成员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psMember:edit")
    @RequestMapping("/psMember_au")
    public String psMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsMember psMember = psMemberMapper.selectByPrimaryKey(id);
            modelMap.put("psMember", psMember);
        }
        return "ps/psMember/psMember_au";
    }

    @RequiresPermissions("psMember:del")
    @RequestMapping(value = "/psMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            psMemberService.del(id);
            logger.info(log( LogConstants.LOG_PS, "删除二级党校班子成员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psMember:del")
    @RequestMapping(value = "/psMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psMemberService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PS, "批量删除二级党校班子成员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("psMember:changeOrder")
    @RequestMapping(value = "/psMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psMemberService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PS, "二级党校班子成员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/

    public void psMember_export(PsMemberExample example, HttpServletResponse response) {

        List<PsMember> records = psMemberMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级党校|100","届数|100","党校职务|100","班子成员|100","所在单位及职务|100","联系方式|100","任职起始时间|100","任职结束时间|100","现任/离任|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsMember record = records.get(i);
            String[] values = {
                record.getPsId()+"",
                            record.getSeq(),
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
        String fileName = "二级党校班子成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psMember_selects")
    @ResponseBody
    public Map psMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsMemberExample example = new PsMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = psMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsMember> records = psMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PsMember record:records){

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
