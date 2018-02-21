package controller.pcs.vote;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsVoteMember;
import domain.pcs.PcsVoteMemberExample;
import domain.pcs.PcsVoteMemberExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsVoteMemberController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsVoteMember:list")
    @RequestMapping("/pcsVoteMemberList")
    public String pcsVoteMemberList() {

        return "pcs/pcsVoteMember/pcsVoteMemberList_page";
    }

    @RequiresPermissions("pcsVoteMember:list")
    @RequestMapping("/pcsVoteMember")
    public String pcsVoteMember() {


        return "pcs/pcsVoteMember/pcsVoteMember_page";
    }

    @RequiresPermissions("pcsVoteMember:list")
    @RequestMapping("/pcsVoteMember_data")
    public void pcsVoteMember_data(HttpServletResponse response,
                               String name,
                                   byte type,
                                   Byte orderType,
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

        PcsVoteMemberExample example = new PcsVoteMemberExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);

        String orderStr = null;
        if(orderType==null || orderType==0) {
            orderStr = "sort_order asc"; // 笔画排序
        }

        if(orderType!=null&&orderType==1){ // 按得票排序
            orderStr = " agree desc, " + orderStr;
        }
        example.setOrderByClause(orderStr);

       /* if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsVoteMember_export(example, response);
            return;
        }*/

        long count = pcsVoteMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsVoteMember> records = pcsVoteMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsVoteMember.class, pcsVoteMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsVoteMember:export")
    @RequestMapping("/pcsVoteMember_export")
    public String pcsVoteMember_export(HttpServletResponse response) throws IOException {

        XSSFWorkbook wb  = pcsVoteExportService.member();
        if (wb != null) {
            ExportHelper.output(wb, "两委委员当选名单.xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pcsVoteMember:edit")
    @RequestMapping(value = "/pcsVoteMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteMember_au(PcsVoteMember record, HttpServletRequest request) {

        Integer id = record.getId();
        
        if (id == null) {
            pcsVoteMemberService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_PCS, "添加当选人：%s", record.getId()));
        } else {

            pcsVoteMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_PCS, "更新当选人：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsVoteMember:edit")
    @RequestMapping("/pcsVoteMember_au")
    public String pcsVoteMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsVoteMember pcsVoteMember = pcsVoteMemberMapper.selectByPrimaryKey(id);
            modelMap.put("pcsVoteMember", pcsVoteMember);
        }
        return "pcs/pcsVoteMember/pcsVoteMember_au";
    }

    @RequiresPermissions("pcsVoteMember:del")
    @RequestMapping(value = "/pcsVoteMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsVoteMemberService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_PCS, "批量删除当选人：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
