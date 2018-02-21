package controller.sc.scLetter;

import domain.sc.scLetter.ScLetterReplyItemView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lm on 2018/1/25.
 */
@Controller
@RequestMapping("/sc")
public class ScLetterReplyItemController extends ScLetterBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scLetterItem:list")
    @RequestMapping("/scLetterReplyItem")
    public String scLetterReplyItem(int replyId, ModelMap modelMap) {

        List<ScLetterReplyItemView> scLetterReplyItemView = scLetterReplyService.getScLetterReplyItemView(replyId);
        modelMap.put("itemList", scLetterReplyItemView);
        return "sc/scLetter/scLetterReplyItem/scLetterReplyItem_page";
    }
}
