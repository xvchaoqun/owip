package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdMonthService;

public class ScBaseController extends BaseController {

    @Autowired
    protected PmdMonthService pmdMonthService;

}
