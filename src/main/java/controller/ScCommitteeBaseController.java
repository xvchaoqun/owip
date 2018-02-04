package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scCommittee.ScCommitteeMemberService;
import service.sc.scCommittee.ScCommitteeOtherVoteService;
import service.sc.scCommittee.ScCommitteeService;
import service.sc.scCommittee.ScCommitteeTopicService;
import service.sc.scCommittee.ScCommitteeVoteService;

public class ScCommitteeBaseController extends BaseController {

    @Autowired
    protected ScCommitteeService scCommitteeService;
    @Autowired
    protected ScCommitteeMemberService scCommitteeMemberService;
    @Autowired
    protected ScCommitteeTopicService scCommitteeTopicService;
    @Autowired
    protected ScCommitteeVoteService scCommitteeVoteService;
    @Autowired
    protected ScCommitteeOtherVoteService scCommitteeOtherVoteService;
}
