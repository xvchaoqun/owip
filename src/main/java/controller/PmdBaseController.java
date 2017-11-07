package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdPayService;

public class PmdBaseController extends BaseController {

    @Autowired
    protected PmdPayService pmdPayService;
}
