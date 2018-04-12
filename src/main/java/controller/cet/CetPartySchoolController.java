package controller.cet;

import domain.cet.CetPartySchool;
import domain.cet.CetPartySchoolExample;
import domain.cet.CetPartySchoolExample.Criteria;
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
public class CetPartySchoolController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetPartySchool:list")
    @RequestMapping("/cetPartySchool")
    public String cetPartySchool() {

        return "cet/cetPartySchool/cetPartySchool_page";
    }

    @RequiresPermissions("cetPartySchool:list")
    @RequestMapping("/cetPartySchool_data")
    public void cetPartySchool_data(HttpServletResponse response,
                                    Integer partySchoolId,
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

        CetPartySchoolExample example = new CetPartySchoolExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (partySchoolId!=null) {
            criteria.andPartySchoolIdEqualTo(partySchoolId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetPartySchool_export(example, response);
            return;
        }

        long count = cetPartySchoolMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPartySchool> records= cetPartySchoolMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPartySchool.class, cetPartySchoolMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetPartySchool:edit")
    @RequestMapping(value = "/cetPartySchool_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPartySchool_au(CetPartySchool record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetPartySchoolService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加二级党校：%s", record.getId()));
        } else {

            cetPartySchoolService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetPartySchool:edit")
    @RequestMapping("/cetPartySchool_au")
    public String cetPartySchool_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetPartySchool cetPartySchool = cetPartySchoolMapper.selectByPrimaryKey(id);
            modelMap.put("cetPartySchool", cetPartySchool);
        }
        return "cet/cetPartySchool/cetPartySchool_au";
    }

    @RequiresPermissions("cetPartySchool:del")
    @RequestMapping(value = "/cetPartySchool_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPartySchool_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetPartySchoolService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除二级党校：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetPartySchool:del")
    @RequestMapping(value = "/cetPartySchool_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetPartySchool_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPartySchoolService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetPartySchool_export(CetPartySchoolExample example, HttpServletResponse response) {

        List<CetPartySchool> records = cetPartySchoolMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级党校|100","管理员|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPartySchool record = records.get(i);
            String[] values = {
                record.getPartySchoolId()+"",
                            record.getUserId()+""
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
