package controller.cr;

import domain.cr.CrInfo;
import domain.cr.CrInfoExample;
import domain.cr.CrInfoExample.Criteria;
import domain.cr.CrRequire;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CrConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
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

public class CrInfoController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crInfo:list")
    @RequestMapping("/crInfo")
    public String crInfo(@RequestParam(required = false, defaultValue ="1" ) Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, CrRequire> requireMap = crRequireService.findAll();
        modelMap.put("requireMap", requireMap);

        return "cr/crInfo/crInfo_page";
    }

    @RequiresPermissions("crInfo:list")
    @RequestMapping("/crInfo_data")
    @ResponseBody
    public void crInfo_data(HttpServletResponse response,
                                    Integer year,
                                    @RequestDateRange DateRange addDate,
                                 @RequestParam(required = false, defaultValue ="1" ) Byte cls,
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

        CrInfoExample example = new CrInfoExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(cls==1){
            criteria.andStatusIn(Arrays.asList(CrConstants.CR_INFO_STATUS_INIT,CrConstants.CR_INFO_STATUS_NORMAL));
        }else if(cls==2){
            criteria.andStatusEqualTo(CrConstants.CR_INFO_STATUS_FINISH);
        }else if(cls==3){
            criteria.andStatusEqualTo(CrConstants.CR_INFO_STATUS_ABOLISH);
        }

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (addDate.getStart() != null) {
            criteria.andAddDateGreaterThanOrEqualTo(addDate.getStart());
        }

        if (addDate.getEnd() != null) {
            criteria.andAddDateLessThanOrEqualTo(addDate.getEnd());
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            crInfo_export(example, response);
            return;
        }

        long count = crInfoMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrInfo> records= crInfoMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crInfo.class, crInfoMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crInfo:edit")
    @RequestMapping(value = "/crInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crInfo_au(CrInfo record, MultipartFile _notice,  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        record.setNotice(uploadPdf(_notice, "crInfo_notice"));
        if (id == null) {

            crInfoService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CR, "添加招聘信息：{0}", record.getId()));
        } else {

            crInfoService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CR, "更新招聘信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crInfo:edit")
    @RequestMapping("/crInfo_au")
    public String crInfo_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrInfo crInfo = crInfoMapper.selectByPrimaryKey(id);
            modelMap.put("crInfo", crInfo);
        }

        Map<Integer, CrRequire> requireMap = crRequireService.findAll();
        modelMap.put("crRequires", requireMap.values());

        return "cr/crInfo/crInfo_au";
    }

    @RequiresPermissions("crInfo:del")
    @RequestMapping(value = "/crInfo_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crInfo_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crInfoService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CR, "批量删除招聘信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void crInfo_export(CrInfoExample example, HttpServletResponse response) {

        List<CrInfo> records = crInfoMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","添加日期|100","招聘通知|100","招聘人数|100","基本条件|100","基本资格|100","岗位要求|100","报名开启时间|100","报名关闭时间|100","岗位状态|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrInfo record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            DateUtils.formatDate(record.getAddDate(), DateUtils.YYYY_MM_DD),
                            record.getNotice(),
                            record.getRequireNum()+"",
                            record.getRequirement(),
                            record.getQualification(),
                            record.getRequireId()+"",
                            DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getStatus()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("招聘信息(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
