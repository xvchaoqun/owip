package controller.sc.scMatter;

import domain.sc.scMatter.ScMatterItemView;
import domain.sc.scMatter.ScMatterItemViewExample;
import domain.sc.scMatter.ScMatterTransfer;
import domain.sc.scMatter.ScMatterTransferExample;
import domain.sc.scMatter.ScMatterTransferExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterTransferController extends ScMatterBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterTransfer:list")
    @RequestMapping("/scMatterTransfer")
    public String scMatterTransfer(@RequestParam(defaultValue = "1") Integer cls,
                                   ModelMap modelMap) {

        modelMap.put("cls", cls);

        return "sc/scMatter/scMatterTransfer/scMatterTransfer_page";
    }

    @RequiresPermissions("scMatterTransfer:list")
    @RequestMapping("/scMatterTransfer_data")
    public void scMatterTransfer_data(HttpServletResponse response,
                                      Integer userId,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterTransferExample example = new ScMatterTransferExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("transfer_date desc, id desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = scMatterTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterTransfer> records = scMatterTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterTransfer.class, scMatterTransferMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterTransfer:edit")
    @RequestMapping("/scMatterTransfer_au")
    public String scMatterTransfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterTransfer scMatterTransfer = scMatterTransferMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterTransfer", scMatterTransfer);
        }
        return "sc/scMatter/scMatterTransfer/scMatterTransfer_au";
    }

    @RequiresPermissions("scMatterTransfer:edit")
    @RequestMapping(value = "/scMatterTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterTransfer_au(ScMatterTransfer record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterTransferService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_SC_MATTER, "添加个人有关事项-移交记录：%s", record.getId()));
        } else {

            scMatterTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_SC_MATTER, "更新个人有关事项-移交记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterTransfer:edit")
    @RequestMapping("/scMatterTransfer_items")
    public String scMatterTransfer_items(int transferId, ModelMap modelMap) {

        ScMatterTransfer scMatterTransfer = scMatterTransferMapper.selectByPrimaryKey(transferId);
        modelMap.put("scMatterTransfer", scMatterTransfer);

        Integer userId = scMatterTransfer.getUserId();
        ScMatterItemViewExample example = new ScMatterItemViewExample();
        example.or().andUserIdEqualTo(userId).andTransferIdIsNull();
        example.or().andUserIdEqualTo(userId).andTransferIdEqualTo(transferId);

        List<ScMatterItemView> scMatterItemViews = scMatterItemViewMapper.selectByExample(example);
        modelMap.put("scMatterItemViews", scMatterItemViews);

        return "sc/scMatter/scMatterTransfer/scMatterTransfer_items";
    }

    @RequiresPermissions("scMatterTransfer:edit")
    @RequestMapping(value = "/scMatterTransfer_items", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterTransfer_items(int transferId,
                                         @RequestParam(value = "matterItemIds[]", required = false) Integer[] matterItemIds,
                                         HttpServletRequest request) {

        scMatterTransferService.transfer(transferId, matterItemIds);
        logger.info(addLog(SystemConstants.LOG_SC_MATTER, "移交记录：%s, %s", transferId, StringUtils.join(matterItemIds)));

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("scMatterTransfer:del")
    @RequestMapping(value = "/scMatterTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            scMatterTransferService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_SC_MATTER, "批量删除个人有关事项-移交记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

  /*  public void scMatterTransfer_export(ScMatterTransferExample example, HttpServletResponse response) {

        List<ScMatterTransfer> records = scMatterTransferMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"移交对象|100","移交日期|100","移交原因|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatterTransfer record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            DateUtils.formatDate(record.getTransferDate(), DateUtils.YYYY_MM_DD),
                            record.getReason()
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项-移交记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/scMatterTransfer_selects")
    @ResponseBody
    public Map scMatterTransfer_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterTransferExample example = new ScMatterTransferExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = scMatterTransferMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterTransfer> scMatterTransfers = scMatterTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterTransfers && scMatterTransfers.size()>0){

            for(ScMatterTransfer scMatterTransfer:scMatterTransfers){

                Select2Option option = new Select2Option();
                option.setText(scMatterTransfer.getName());
                option.setId(scMatterTransfer.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
