package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CadrePostInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadrePostInfo_page")
    public String cadrePostInfo_page(int cadreId) {

        return "cadrePostInfo/cadrePostInfo_page";
    }
}
