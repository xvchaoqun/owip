package controller.sp;

import domain.sp.SpCg;
import domain.sp.SpCgExample;
import domain.sp.SpCgExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
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
@RequestMapping("/sp")
public class SpCgController extends SpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spCg")
    public String spCg(Byte type,Integer userId,ModelMap modelMap) {

        modelMap.put("sysUser",CmTag.getUserById(userId));
        modelMap.put("type",type);
        return "sp/spCg/spCg_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spCg_data")
    @ResponseBody
    public void spCg_data(HttpServletResponse response,
                                    Integer userId,
                                    String post,
                                    Boolean isCadre,
                                    Byte type,
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

        SpCgExample example = new SpCgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(post)) {
            criteria.andPostLike(SqlUtils.like(post));
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spCg_export(example, response);
            return;
        }

        long count = spCgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpCg> records= spCgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spCg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spCg_au(SpCg record, HttpServletRequest request) {

        Integer id = record.getId();

        //判断是否是干部
        spCgService.updateRecord(record);

        if (spCgService.idDuplicate(id,record.getType(),record.getUserId())){

            return failed("添加重复");
        }

        if (id == null) {
            
            spCgService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加委员会委员：{0}", record.getId()));
        } else {

            spCgService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新委员会委员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spCg_au")
    public String spCg_au(Integer id,Byte type, ModelMap modelMap) {

        if (id != null) {
            SpCg spCg = spCgMapper.selectByPrimaryKey(id);
            modelMap.put("spCg", spCg);

            type = spCg.getType();

            SysUserView sysUser = CmTag.getUserById(spCg.getUserId());
            Unit unit = CmTag.getUnit(spCg.getUnitId());

            modelMap.put("sysUser",sysUser);
            modelMap.put("unit",unit);
        }

        modelMap.put("type",type);
        return "sp/spCg/spCg_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spCg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spCg_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spCgService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除委员会委员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spCg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spCg_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            spCgService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除委员会委员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spCg_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spCg_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spCgService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "委员会委员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void spCg_export(SpCgExample example, HttpServletResponse response) {

        List<SpCg> records = spCgMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","职务|100","席位|100","所在单位|100","到校日期|100","政治面貌|100","专业技术职务|100","管理岗位等级|100","是否领导干部|100","所担任行政职务|100","联系方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpCg record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getPost(),
                            record.getSeat(),
                            record.getUnitId()+"",
                            DateUtils.formatDate(record.getArriveDate(), DateUtils.YYYY_MM_DD),
                            record.getPoliticsStatus()+"",
                            record.getProPost(),
                            record.getManageLevel(),
                            record.getIsCadre()+"",
                            record.getAdminPost(),
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("委员会委员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spCg_selects")
    @ResponseBody
    public Map spCg_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpCgExample example = new SpCgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        long count = spCgMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpCg> records = spCgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpCg record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getUnitId());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spCg_relevance")
    public String do_spCg_relevance(Integer type,ModelMap modelMap){

        modelMap.put("type",type);
        return "sp/spCg/spCg_relevance";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spCg_relevance", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spCg_relevance(SpCg record, HttpServletRequest request) {

        spCgService.spCg_relevance(record);
        return success(FormUtils.SUCCESS);
    }
}
