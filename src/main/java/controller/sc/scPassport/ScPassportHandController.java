package controller.sc.scPassport;

import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportHandExample;
import domain.sc.scPassport.ScPassportHandExample.Criteria;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScPassportHandController extends ScPassportBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportHand")
    public String scPassportHand() {

        return "sc/scPassport/scPassportHand/scPassportHand_page";
    }

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportHand_data")
    @ResponseBody
    public void scPassportHand_data(HttpServletResponse response,
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

        ScPassportHandExample example = new ScPassportHandExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("add_time desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scPassportHand_export(example, response);
            return;
        }

        long count = scPassportHandMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPassportHand> records= scPassportHandMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scPassportHand.class, scPassportHandMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping(value = "/scPassportHand_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_au(ScPassportHand record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scPassportHandService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "添加新提任干部交证件：%s", record.getId()));
        } else {

            scPassportHandService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "更新新提任干部交证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:edit")
    @RequestMapping("/scPassportHand_au")
    public String scPassportHand_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(id);
            modelMap.put("scPassportHand", scPassportHand);
        }
        return "sc/scPassport/scPassportHand/scPassportHand_au";
    }

    @RequiresPermissions("scPassportHand:del")
    @RequestMapping(value = "/scPassportHand_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportHand_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scPassportHandService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "删除新提任干部交证件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:del")
    @RequestMapping(value = "/scPassportHand_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scPassportHand_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scPassportHandService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "批量删除新提任干部交证件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scPassportHand_export(ScPassportHandExample example, HttpServletResponse response) {

        List<ScPassportHand> records = scPassportHandMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"关联账号|100","新提任日期|100","添加方式|100","备注|100","状态|100","证件是否已入库|100","添加时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPassportHand record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            DateUtils.formatDate(record.getAppointDate(), DateUtils.YYYY_MM_DD),
                            record.getAddType()+"",
                            record.getRemark(),
                            record.getStatus()+"",
                            record.getIsKeep()+"",
                            DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "新提任干部交证件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
