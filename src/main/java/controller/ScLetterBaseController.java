package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scLetter.ScLetterItemService;
import service.sc.scLetter.ScLetterReplyService;
import service.sc.scLetter.ScLetterService;

public class ScLetterBaseController extends BaseController {

    @Autowired
    protected ScLetterService scLetterService;
    @Autowired
    protected ScLetterItemService scLetterItemService;
    @Autowired
    protected ScLetterReplyService scLetterReplyService;
}
