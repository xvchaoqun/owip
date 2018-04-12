package controller.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyExample;
import domain.cet.CetPartyExample.Criteria;
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
@RequestMapping("/cet")
public class CetPartyController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty")
    public String cetParty() {

        return "cet/cetParty/cetParty_page";
    }

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty_data")
    public void cetParty_data(HttpServletResponse response,

                                    Integer partyId,
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

        CetPartyExample example = new CetPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetParty_export(example, response);
            return;
        }

        long count = cetPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetParty> records= cetPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetParty.class, cetPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping(value = "/cetParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_au(CetParty record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            cetPartyService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加院系级党委：%s", record.getId()));
        } else {

            cetPartyService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新院系级党委：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/cetParty_au")
    public String cetParty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetParty cetParty = cetPartyMapper.selectByPrimaryKey(id);
            modelMap.put("cetParty", cetParty);
        }
        return "cet/cetParty/cetParty_au";
    }

    @RequiresPermissions("cetParty:del")
    @RequestMapping(value = "/cetParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetPartyService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除院系级党委：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:del")
    @RequestMapping(value = "/cetParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPartyService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除院系级党委：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetParty_export(CetPartyExample example, HttpServletResponse response) {

        List<CetParty> records = cetPartyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属基层党组织|100","管理员|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetParty record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                            record.getUserId()+""
            };
            valuesList.add(values);
        }
        String fileName = "院系级党委_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
