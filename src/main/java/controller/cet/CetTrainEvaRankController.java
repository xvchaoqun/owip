package controller.cet;

import domain.cet.CetTrainEvaRank;
import domain.cet.CetTrainEvaRankExample;
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
@RequestMapping("/cet")
public class CetTrainEvaRankController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainEvaRank:list")
    @RequestMapping("/cetTrainEvaRank")
    public String cetTrainEvaRank(int evaTableId, ModelMap modelMap) {

        modelMap.put("cetTrainEvaTable", cetTrainEvaTableMapper.selectByPrimaryKey(evaTableId));
        return "cet/cetTrainEvaRank/cetTrainEvaRank_page";
    }

    @RequiresPermissions("cetTrainEvaRank:list")
    @RequestMapping("/cetTrainEvaRank_data")
    public void cetTrainEvaRank_data(HttpServletResponse response,
                                  int evaTableId, String name,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainEvaRankExample example = new CetTrainEvaRankExample();
        CetTrainEvaRankExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainEvaRank_export(example, response);
            return;
        }

        int count = (int) cetTrainEvaRankMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainEvaRank> records= cetTrainEvaRankMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainEvaRank.class, cetTrainEvaRankMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainEvaRank:edit")
    @RequestMapping(value = "/cetTrainEvaRank_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaRank_au(CetTrainEvaRank record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            cetTrainEvaRankService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加评估等级：%s", record.getId()));
        } else {

            cetTrainEvaRankService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新评估等级：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaRank:edit")
    @RequestMapping("/cetTrainEvaRank_au")
    public String cetTrainEvaRank_au(Integer id, Integer evaTableId, ModelMap modelMap) {

        if (id != null) {
            CetTrainEvaRank cetTrainEvaRank = cetTrainEvaRankMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainEvaRank", cetTrainEvaRank);

            evaTableId = cetTrainEvaRank.getEvaTableId();
        }

        modelMap.put("cetTrainEvaTable", cetTrainEvaTableMapper.selectByPrimaryKey(evaTableId));

        return "cet/cetTrainEvaRank/cetTrainEvaRank_au";
    }

    @RequiresPermissions("cetTrainEvaRank:del")
    @RequestMapping(value = "/cetTrainEvaRank_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaRank_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTrainEvaRankService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除评估等级：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaRank:del")
    @RequestMapping(value = "/cetTrainEvaRank_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainEvaRankService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除评估等级：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaRank:changeOrder")
    @RequestMapping(value = "/cetTrainEvaRank_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaRank_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainEvaRankService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "评估等级调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainEvaRank_export(CetTrainEvaRankExample example, HttpServletResponse response) {

        List<CetTrainEvaRank> records = cetTrainEvaRankMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属评估表","名称","得分","得分显示内容","备注","排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainEvaRank record = records.get(i);
            String[] values = {
                record.getEvaTableId()+"",
                            record.getName(),
                            record.getScore()+"",
                            record.getScoreShow(),
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估等级_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
