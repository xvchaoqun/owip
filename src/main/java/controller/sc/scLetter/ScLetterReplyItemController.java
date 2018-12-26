package controller.sc.scLetter;

import controller.sc.ScBaseController;
import domain.sc.scLetter.ScLetterReplyItemView;
import domain.sc.scLetter.ScLetterReplyItemViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/1/25.
 */
@Controller
@RequestMapping("/sc")
public class ScLetterReplyItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scLetterReply:list")
    @RequestMapping("/scLetterReplyItems")
    public String scLetterReplyItems(int replyId, ModelMap modelMap) {

        List<ScLetterReplyItemView> scLetterReplyItemView = scLetterReplyService.getScLetterReplyItemView(replyId);
        modelMap.put("itemList", scLetterReplyItemView);
        return "sc/scLetter/scLetterReplyItem/scLetterReplyItems";
    }


    @RequiresPermissions("scLetterReply:list")
    @RequestMapping("/scLetterReplyItem")
    public String scLetterReplyItem(@RequestParam(defaultValue = "1") Integer cls,
                                    Integer userId,
                                    ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "sc/scLetter/scLetterReplyItem/scLetterReplyItem_page";
    }

    @RequiresPermissions("scLetterReply:list")
    @RequestMapping("/scLetterReplyItem_data")
    public void scLetterReplyItem_data(HttpServletResponse response,
                                       Integer userId,
                                   Integer letterYear,
                                   Integer letterType,
                                   Integer letterNum,
                                       Integer replyNum,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo)  throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScLetterReplyItemViewExample example = new ScLetterReplyItemViewExample();
        ScLetterReplyItemViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("reply_id desc, id desc");

        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }

        if (letterYear!=null) {
            criteria.andLetterYearEqualTo(letterYear);
        }
        if (letterType!=null) {
            criteria.andLetterTypeEqualTo(letterType);
        }
        if (letterNum!=null) {
            criteria.andLetterNumEqualTo(letterNum);
        }

        if (replyNum!=null) {
            criteria.andReplyNumEqualTo(replyNum);
        }

        long count = scLetterReplyItemViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScLetterReplyItemView> records= scLetterReplyItemViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scLetterReplyItem.class, scLetterReplyItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
