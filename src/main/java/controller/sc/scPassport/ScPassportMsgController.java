package controller.sc.scPassport;

import domain.base.ContentTpl;
import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportMsg;
import domain.sc.scPassport.ScPassportMsgExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScPassportMsgController extends ScPassportBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportMsg")
    public String scPassportMsg(int handId, ModelMap modelMap) {

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_SC_PASSPORT_MSG);
        modelMap.put("msg", tpl.getContent());

        ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(handId);
        modelMap.put("scPassportHand", scPassportHand);

        return "sc/scPassport/scPassportMsg/scPassportMsg";
    }

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping(value = "/scPassportMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scPassportMsg(Integer handId, String msg) {

        if (handId != null) {

            scPassportMsgService.send(handId, msg);
            logger.info(addLog(LogConstants.LOG_SC_PASSPORT, "新提任干部交证件短信通知：%s", handId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportMsgList")
    public String scPassportMsgList(ModelMap modelMap) {

        return "sc/scPassport/scPassportMsg/scPassportMsgList_page";
    }

    @RequiresPermissions("scPassportHand:list")
    @RequestMapping("/scPassportMsgList_data")
    public void scPassportMsgList_data(HttpServletResponse response,
                                   Integer handId,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScPassportMsgExample example = new ScPassportMsgExample();
        example.createCriteria().andHandIdEqualTo(handId);

        example.setOrderByClause("send_time desc");

        long count = scPassportMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScPassportMsg> records = scPassportMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
