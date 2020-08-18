package controller.pcs;

import domain.pcs.PcsConfig;
import domain.pcs.PcsConfigExample;
import domain.pcs.PcsConfigExample.Criteria;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsConfigController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsConfig:list")
    @RequestMapping("/pcsConfig")
    public String pcsConfig() {

        return "pcs/pcsConfig/pcsConfig_page";
    }

    @RequiresPermissions("pcsConfig:list")
    @RequestMapping("/pcsConfig_data")
    public void pcsConfig_data(HttpServletResponse response,
                               String name,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfigExample example = new PcsConfigExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);

        example.setOrderByClause("create_time desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        long count = pcsConfigMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsConfig> records = pcsConfigMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsConfig.class, pcsConfigMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsConfig:edit")
    @RequestMapping(value = "/pcsConfig_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsConfig_au(PcsConfig record, HttpServletRequest request) {

        Integer id = record.getId();
        if (pcsConfigService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));
        if (id == null) {
            record.setCreateTime(new Date());
            pcsConfigService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "添加党代会：%s", record.getName()));
        } else {

            pcsConfigService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "更新党代会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsConfig:edit")
    @RequestMapping("/pcsConfig_au")
    public String pcsConfig_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsConfig pcsConfig = pcsConfigMapper.selectByPrimaryKey(id);
            modelMap.put("pcsConfig", pcsConfig);
        }
        return "pcs/pcsConfig/pcsConfig_au";
    }

    @RequiresPermissions("pcsConfig:del")
    @RequestMapping(value = "/pcsConfig_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsConfigService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除党代会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
