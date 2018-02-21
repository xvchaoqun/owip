package controller.sc.scGroup;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scGroup.ScGroupFileService;
import service.sc.scGroup.ScGroupMemberService;
import service.sc.scGroup.ScGroupParticipantService;
import service.sc.scGroup.ScGroupService;
import service.sc.scGroup.ScGroupTopicService;
import service.sc.scGroup.ScGroupTopicUnitService;

public class ScGroupBaseController extends BaseController {

    @Autowired
    protected ScGroupService scGroupService;
    @Autowired
    protected ScGroupFileService scGroupFileService;
    @Autowired
    protected ScGroupMemberService scGroupMemberService;
    @Autowired
    protected ScGroupParticipantService scGroupParticipantService;
    @Autowired
    protected ScGroupTopicService scGroupTopicService;
    @Autowired
    protected ScGroupTopicUnitService scGroupTopicUnitService;
}
