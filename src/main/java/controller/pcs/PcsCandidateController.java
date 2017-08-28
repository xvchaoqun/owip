package controller.pcs;

import controller.BaseController;
import domain.pcs.PcsCandidate;
import domain.pcs.PcsCandidateExample;
import domain.pcs.PcsCandidateExample.Criteria;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsCandidateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsCandidate:list")
    @RequestMapping("/pcsCandidate")
    public String pcsCandidate() {

        return "pcs/pcsCandidate/pcsCandidate_page";
    }

    @RequiresPermissions("pcsCandidate:list")
    @RequestMapping("/pcsCandidate_data")
    public void pcsCandidate_data(HttpServletResponse response,
                                  Integer recommendId,
                                  Byte type,
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

        PcsCandidateExample example = new PcsCandidateExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (recommendId != null) {
            criteria.andRecommendIdEqualTo(recommendId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsCandidate_export(example, response);
            return;
        }*/

        long count = pcsCandidateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsCandidate> records = pcsCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsCandidate.class, pcsCandidateMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsCandidate:edit")
    @RequestMapping(value = "/pcsCandidate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCandidate_au(PcsCandidate record, HttpServletRequest request) {

        if (pcsCandidateService.idDuplicate(null, record.getRecommendId(), record.getUserId(), record.getType())) {
            return failed("添加重复");
        }
        record.setAddTime(new Date());
        pcsCandidateService.insertSelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加两委委员人选：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCandidate:edit")
    @RequestMapping("/pcsCandidate_au")
    public String pcsCandidate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsCandidate pcsCandidate = pcsCandidateMapper.selectByPrimaryKey(id);
            modelMap.put("pcsCandidate", pcsCandidate);
        }
        return "pcs/pcsCandidate/pcsCandidate_au";
    }

    @RequiresPermissions("pcsCandidate:changeOrder")
    @RequestMapping(value = "/pcsCandidate_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsCandidate_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pcsCandidateService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "党代会委员调序：%s, %s", id, addNum));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsCandidate:del")
    @RequestMapping(value = "/pcsCandidate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsCandidateService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除两委委员人选：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
