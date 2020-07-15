package controller.cadreReserve;

import controller.BaseController;
import domain.cadreReserve.CadreReserveOrigin;
import domain.cadreReserve.CadreReserveOriginExample;
import domain.cadreReserve.CadreReserveOriginExample.Criteria;
import domain.cis.CisInspectObj;
import domain.cis.CisInspectObjExample;
import mixin.MixinUtils;
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
import org.springframework.web.multipart.MultipartFile;
import persistence.cis.CisInspectObjMapper;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreReserveOriginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private CisInspectObjMapper cisInspectObjMapper;
    
    @RequiresPermissions("cadreReserveOrigin:list")
    @RequestMapping("/cadreReserveOrigin")
    public String cadreReserveOrigin() {

        return "cadreReserve/cadreReserveOrigin/cadreReserveOrigin_page";
    }

    @RequiresPermissions("cadreReserveOrigin:list")
    @RequestMapping("/cadreReserveOrigin_data")
    @ResponseBody
    public void cadreReserveOrigin_data(HttpServletResponse response,
                                    Byte way,
                                    Integer userId,
                                    Integer reserveType,
                                    String recommendUnit,
                                    Date recommendDate,
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

        CadreReserveOriginExample example = new CadreReserveOriginExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("add_time desc");

        if (way!=null) {
            criteria.andWayEqualTo(way);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (reserveType!=null) {
            criteria.andReserveTypeEqualTo(reserveType);
        }
        if (StringUtils.isNotBlank(recommendUnit)) {
            criteria.andRecommendUnitLike(SqlUtils.like(recommendUnit));
        }
        if (recommendDate!=null) {
        criteria.andRecommendDateGreaterThan(recommendDate);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cadreReserveOrigin_export(example, response);
            return;
        }

        long count = cadreReserveOriginMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveOrigin> records= cadreReserveOriginMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadreReserveOrigin.class, cadreReserveOriginMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreReserveOrigin:edit")
    @RequestMapping(value = "/cadreReserveOrigin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserveOrigin_au(CadreReserveOrigin record,
                                        MultipartFile _pdfFilePath,
                                        MultipartFile _wordFilePath,
                                        HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        record.setPdfFilePath(uploadPdf(_pdfFilePath, "cadreReserveOrigin"));
        record.setWordFilePath(upload(_wordFilePath, "cadreReserveOrigin"));

        if (id == null) {
            cadreReserveOriginService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_CADRERESERVE, "添加优秀年轻干部的产生：%s", record.getUserId()));
        } else {

            cadreReserveOriginService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CADRERESERVE, "更新优秀年轻干部的产生：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserveOrigin:edit")
    @RequestMapping("/cadreReserveOrigin_au")
    public String cadreReserveOrigin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReserveOrigin cadreReserveOrigin = cadreReserveOriginMapper.selectByPrimaryKey(id);
            modelMap.put("cadreReserveOrigin", cadreReserveOrigin);
            Integer objId = cadreReserveOrigin.getObjId();
            if(objId!=null && cisInspectObjMapper!=null){
                CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
                modelMap.put("cisInspectObj", cisInspectObj);
            }
        }
        return "cadreReserve/cadreReserveOrigin/cadreReserveOrigin_au";
    }

    @RequiresPermissions("cadreReserveOrigin:del")
    @RequestMapping(value = "/cadreReserveOrigin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserveOrigin_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreReserveOriginService.del(id);
            logger.info(addLog( LogConstants.LOG_CADRERESERVE, "删除优秀年轻干部的产生：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserveOrigin:del")
    @RequestMapping(value = "/cadreReserveOrigin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserveOrigin_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreReserveOriginService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CADRERESERVE, "批量删除优秀年轻干部的产生：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cadreReserveOrigin_export(CadreReserveOriginExample example, HttpServletResponse response) {

        List<CadreReserveOrigin> records = cadreReserveOriginMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"产生方式|100","推荐人选|100","优秀年轻干部类别|100","推荐单位|100",
                "推荐日期|100","推荐材料|100","推荐材料|100","考察材料|100","备注|100","添加日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreReserveOrigin record = records.get(i);
            String[] values = {
                record.getWay()+"",
                            record.getUserId()+"",
                            record.getReserveType()+"",
                            record.getRecommendUnit(),
                            DateUtils.formatDate(record.getRecommendDate(), DateUtils.YYYY_MM_DD),
                            record.getWordFilePath(),
                            record.getPdfFilePath(),
                            record.getObjId()+"",
                            record.getRemark(),
                            DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "优秀年轻干部的产生_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cadreReserveOrigin_selectInspectObj")
    @ResponseBody
    public Map cadreReserveOrigin_selectInspectObj(HttpServletResponse response,
                                    Integer cadreId,
                                    ModelMap modelMap) {

        CisInspectObjExample example = new CisInspectObjExample();
        CisInspectObjExample.Criteria criteria = example.createCriteria();
        if(cadreId==null){
            criteria.andIdIsNull();
        }else{
            criteria.andCadreIdEqualTo(cadreId);
        }
        long count = 0;
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();

        if(cisInspectObjMapper!=null) {
            count = cisInspectObjMapper.countByExample(example);
            List<CisInspectObj> cisInspectObjs = cisInspectObjMapper.selectByExample(example);

            if (null != cisInspectObjs && cisInspectObjs.size() > 0) {

                for (CisInspectObj cisInspectObj : cisInspectObjs) {
                    Map<String, String> option = new HashMap<>();

                    option.put("id", cisInspectObj.getId() + "");
                    option.put("sn", cisInspectObj.getSn());
                    option.put("date", DateUtils.formatDate(cisInspectObj.getInspectDate(), DateUtils.YYYY_MM_DD));

                    options.add(option);
                }
            }
        }
        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
