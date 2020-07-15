package controller.sc.scPassport;

import controller.sc.ScBaseController;
import domain.sc.scPassport.ScPassport;
import domain.sc.scPassport.ScPassportExample;
import domain.sc.scPassport.ScPassportExample.Criteria;
import domain.sc.scPassport.ScPassportHand;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import service.abroad.SafeBoxService;
import sys.constants.LogConstants;
import sys.tags.CmTag;
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
@RequestMapping("/sc")
public class ScPassportController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPassport:list")
    @RequestMapping("/scPassport")
    public String scPassport(int handId, ModelMap modelMap) {

        ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(handId);
        modelMap.put("scPassportHand", scPassportHand);

        return "sc/scPassport/scPassport/scPassport_page";
    }

    @RequiresPermissions("scPassport:list")
    @RequestMapping("/scPassport_data")
    @ResponseBody
    public void scPassport_data(HttpServletResponse response,
                                    Integer handId,
                                    Integer classId,
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

        ScPassportExample example = new ScPassportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (handId!=null) {
            criteria.andHandIdEqualTo(handId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scPassport_export(example, response);
            return;
        }

        long count = scPassportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPassport> records= scPassportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scPassport.class, scPassportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scPassport:edit")
    @RequestMapping(value = "/scPassport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassport_au(ScPassport record,
                                MultipartFile _pic,
                                HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        record.setIsExist(BooleanUtils.isTrue(record.getIsExist()));

        if(scPassportService.idDuplicate(record.getId(), record.getHandId(), record.getClassId())){
            return failed("添加重复。");
        }
        if (_pic != null && !_pic.isEmpty()) {
            record.setPic(uploadPic(_pic, "scPassport", 100, 200));
        }

        if (id == null) {
            
            scPassportService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "添加上交证件信息"));
        } else {

            scPassportService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "更新上交证件信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassport:edit")
    @RequestMapping("/scPassport_au")
    public String scPassport_au(Integer id, Integer handId, ModelMap modelMap) {

        if (id != null) {
            ScPassport scPassport = scPassportMapper.selectByPrimaryKey(id);
            modelMap.put("scPassport", scPassport);
            handId = scPassport.getHandId();
        }
        modelMap.put("handId", handId);

        SafeBoxService safeBoxService = CmTag.getBean(SafeBoxService.class);
        if(safeBoxService!=null)
            modelMap.put("safeBoxMap", safeBoxService.findAll());

        return "sc/scPassport/scPassport/scPassport_au";
    }

    @RequiresPermissions("scPassport:del")
    @RequestMapping(value = "/scPassport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scPassport_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scPassportService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_PASSPORT, "批量删除上交证件信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void scPassport_export(ScPassportExample example, HttpServletResponse response) {

        List<ScPassport> records = scPassportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"关联上交证件记录|100","证件名称|100","干部是否拥有该证件|100","集中保管日期|100","证件号码|100","上传证件首页|100","发证机关|100","发证日期|100","有效期|100","存放保险柜|100","交证件时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScPassport record = records.get(i);
            String[] values = {
                record.getHandId()+"",
                            record.getClassId()+"",
                            record.getIsExist()+"",
                            DateUtils.formatDate(record.getKeepDate(), DateUtils.YYYY_MM_DD),
                            record.getCode(),
                            record.getPic(),
                            record.getAuthority(),
                            DateUtils.formatDate(record.getIssueDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getExpiryDate(), DateUtils.YYYY_MM_DD),
                            record.getSafeBoxId()+"",
                            DateUtils.formatDate(record.getHandTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "上交证件信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
