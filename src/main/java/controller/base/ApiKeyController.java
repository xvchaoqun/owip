package controller.base;

import controller.BaseController;
import domain.base.ApiKey;
import domain.base.ApiKeyExample;
import domain.base.ApiKeyExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class ApiKeyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("apiKey:list")
    @RequestMapping("/apiKey")
    public String apiKey() {

        return "base/apiKey/apiKey_page";
    }

    @RequiresPermissions("apiKey:list")
    @RequestMapping("/apiKey_data")
    @ResponseBody
    public void apiKey_data(HttpServletResponse response,
                                    String name,
                                
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

        ApiKeyExample example = new ApiKeyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            apiKey_export(example, response);
            return;
        }

        long count = apiKeyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApiKey> records= apiKeyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(apiKey.class, apiKeyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("apiKey:edit")
    @RequestMapping(value = "/apiKey_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apiKey_au(ApiKey record, HttpServletRequest request,@RequestParam("name")String name,@RequestParam("remark")String remark) {
        Integer id = record.getId();

        if (id==null) {
            ApiKey checkRecord = apiKeyService.getApiInfoByName(name);
            if (checkRecord!=null){
                return failed("添加重复");
            }
            String str = RandomStringUtils.randomNumeric(5);
            String Md5res = null;
            try {
                // 生成一个MD5加密计算摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 计算md5函数
                md.update(str.getBytes());
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                Md5res = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                logger.info("MD5加密出错，"+e.toString());
            }
            record.setApiKey(Md5res);
            record.setName(name);
            record.setRemark(remark);
            apiKeyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加API接口管理：{0}", record.getId()));
        } else {
            apiKeyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新API接口管理：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("apiKey:edit")
    @RequestMapping("/apiKey_au")
    public String apiKey_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApiKey apiKey = apiKeyMapper.selectByPrimaryKey(id);
            modelMap.put("apiKey", apiKey);
        }
        return "base/apiKey/apiKey_au";
    }

    @RequiresPermissions("apiKey:del")
    @RequestMapping(value = "/apiKey_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apiKey_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            apiKeyService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除API接口管理：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("apiKey:del")
    @RequestMapping(value = "/apiKey_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map apiKey_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            apiKeyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除API接口管理：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void apiKey_export(ApiKeyExample example, HttpServletResponse response) {

        List<ApiKey> records = apiKeyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"app名称|100","对应键值|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApiKey record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getApiKey()
            };
            valuesList.add(values);
        }
        String fileName = String.format("API接口管理(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }




}
