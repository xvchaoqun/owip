package controller.sp;

import domain.sp.SpTeach;
import domain.sp.SpTeachExample;
import domain.sp.SpTeachExample.Criteria;
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
public class SpTeachController extends SpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spTeach")
    public String spTeach() {

        return "sp/spTeach/spTeach_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spTeach_data")
    @ResponseBody
    public void spTeach_data(HttpServletResponse response,
                                    String post,
                                    Integer userId,
                                    Boolean isCadre,
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

        SpTeachExample example = new SpTeachExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(post)) {
            criteria.andPostLike(SqlUtils.like(post));
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spTeach_export(example, response);
            return;
        }

        long count = spTeachMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpTeach> records= spTeachMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
    @RequestMapping(value = "/spTeach_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTeach_au(SpTeach record, HttpServletRequest request) {

        Integer id = record.getId();

        if (spTeachService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        spTeachService.updateRecord(record);

        if (id == null) {
            
            spTeachService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加教代会执委会：{0}", record.getId()));
        } else {

            spTeachService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新教代会执委会：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spTeach_au")
    public String spTeach_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SpTeach spTeach = spTeachMapper.selectByPrimaryKey(id);
            modelMap.put("spTeach", spTeach);
            modelMap.put("sysUser", CmTag.getUserById(spTeach.getUserId()));
            modelMap.put("unit",CmTag.getUnit(spTeach.getUnitId()));
        }
        return "sp/spTeach/spTeach_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTeach_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTeach_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spTeachService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除教代会执委会：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTeach_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spTeach_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            spTeachService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除教代会执委会：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTeach_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTeach_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spTeachService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "教代会执委会调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void spTeach_export(SpTeachExample example, HttpServletResponse response) {

        List<SpTeach> records = spTeachMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"界数|100","职务|100","姓名|100","所在单位|100","专业技术职务|100","是否领导干部|100","所担任行政职务|100","联系方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpTeach record = records.get(i);
            String[] values = {
                record.getTh(),
                            record.getPost(),
                            record.getUserId()+"",
                            record.getUnitId()+"",
                            record.getProPost(),
                            record.getIsCadre()+"",
                            record.getAdminPost(),
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("教代会执委会(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spTeach_selects")
    @ResponseBody
    public Map spTeach_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpTeachExample example = new SpTeachExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        long count = spTeachMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpTeach> records = spTeachMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpTeach record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getUserId());
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
