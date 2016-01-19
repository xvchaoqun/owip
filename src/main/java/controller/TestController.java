package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestServcie;
import sys.utils.FormUtils;

/**
 * Created by fafa on 2016/1/18.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestServcie testServcie;

    @RequestMapping("/toMember")
    @ResponseBody
    public String toMember(int userId) {

        testServcie.toMember(userId);
        return FormUtils.SUCCESS;
    }

    @RequestMapping("/toGuest")
    @ResponseBody
    public String toGuest(int userId) {

        testServcie.toGuest(userId);
        return FormUtils.SUCCESS;
    }
}
