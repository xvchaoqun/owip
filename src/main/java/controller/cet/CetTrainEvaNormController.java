package controller.cet;

import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaNormExample;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetTrainEvaNormController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("cetTrainEvaNorm:list")
    @RequestMapping("/cetTrainEvaNorm")
    public String cetTrainEvaNorm(int evaTableId, ModelMap modelMap) {

        modelMap.put("cetTrainEvaTable", cetTrainEvaTableMapper.selectByPrimaryKey(evaTableId));

        return "cet/cetTrainEvaNorm/cetTrainEvaNorm_page";
    }

    @RequiresPermissions("cetTrainEvaNorm:list")
    @RequestMapping("/cetTrainEvaNorm_data")
    public void cetTrainEvaNorm_data(HttpServletResponse response,
                                   int evaTableId,
                                  Integer fid, // fid=null时，读取评估内容；fid<=0时，读取全部 fid>0 读取评估指标
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

        CetTrainEvaNormExample example = new CetTrainEvaNormExample();
        CetTrainEvaNormExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");
        if (fid != null) {
            if (fid > 0)
                criteria.andFidEqualTo(fid);
        } else {
            criteria.andFidIsNull();
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainEvaNorm_export(example, response);
            return;
        }

        int count = (int) cetTrainEvaNormMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainEvaNorm> records= cetTrainEvaNormMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainEvaNorm.class, cetTrainEvaNormMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainEvaNorm:edit")
    @RequestMapping(value = "/cetTrainEvaNorm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaNorm_au(CetTrainEvaNorm record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTrainEvaNormService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加评估指标：%s", record.getId()));
        } else {

            cetTrainEvaNormService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新评估指标：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaNorm:edit")
    @RequestMapping("/cetTrainEvaNorm_au")
    public String cetTrainEvaNorm_au(Integer id, Integer fid, Integer evaTableId, ModelMap modelMap) {

        if (id != null) {
            CetTrainEvaNorm cetTrainEvaNorm = cetTrainEvaNormMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainEvaNorm", cetTrainEvaNorm);

            evaTableId = cetTrainEvaNorm.getEvaTableId();
            fid = cetTrainEvaNorm.getFid();
        }
        modelMap.put("fid", fid);
        if(fid!=null) {
            modelMap.put("topTrainEvaNorm", cetTrainEvaNormMapper.selectByPrimaryKey(fid));
        }
        modelMap.put("cetTrainEvaTable", cetTrainEvaTableMapper.selectByPrimaryKey(evaTableId));
        return "cet/cetTrainEvaNorm/cetTrainEvaNorm_au";
    }

    @RequiresPermissions("cetTrainEvaNorm:del")
    @RequestMapping(value = "/cetTrainEvaNorm_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaNorm_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //cetTrainEvaNormService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除评估指标：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaNorm:del")
    @RequestMapping(value = "/cetTrainEvaNorm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainEvaNormService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除评估指标：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaNorm:changeOrder")
    @RequestMapping(value = "/cetTrainEvaNorm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaNorm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainEvaNormService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "评估指标调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainEvaNorm_export(CetTrainEvaNormExample example, HttpServletResponse response) {

        List<CetTrainEvaNorm> records = cetTrainEvaNormMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属评估表","父指标","指标名称","备注","排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainEvaNorm record = records.get(i);
            String[] values = {
                record.getEvaTableId()+"",
                            record.getFid()+"",
                            record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估指标_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
