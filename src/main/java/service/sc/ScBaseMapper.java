package service.sc;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sc.IScMapper;
import persistence.sc.scAd.ScAdArchiveMapper;
import persistence.sc.scAd.ScAdArchiveViewMapper;
import persistence.sc.scAd.ScAdArchiveVoteMapper;
import persistence.sc.scAd.ScAdUseMapper;
import persistence.sc.scBorder.ScBorderItemMapper;
import persistence.sc.scBorder.ScBorderItemViewMapper;
import persistence.sc.scBorder.ScBorderMapper;
import persistence.sc.scBorder.ScBorderViewMapper;
import persistence.sc.scCommittee.*;
import persistence.sc.scDispatch.ScDispatchCommitteeMapper;
import persistence.sc.scDispatch.ScDispatchMapper;
import persistence.sc.scDispatch.ScDispatchUserMapper;
import persistence.sc.scDispatch.ScDispatchViewMapper;
import persistence.sc.scGroup.*;
import persistence.sc.scLetter.*;
import persistence.sc.scMatter.*;
import persistence.sc.scMotion.ScMotionMapper;
import persistence.sc.scMotion.ScMotionPostMapper;
import persistence.sc.scPassport.ScPassportHandMapper;
import persistence.sc.scPassport.ScPassportMapper;
import persistence.sc.scPassport.ScPassportMsgMapper;
import persistence.sc.scPublic.ScPublicMapper;
import persistence.sc.scPublic.ScPublicUserMapper;
import persistence.sc.scPublic.ScPublicViewMapper;
import persistence.sc.scSubsidy.*;
import service.CoreBaseMapper;

public class ScBaseMapper extends CoreBaseMapper {

    /**
     * 干部选拔-动议
     */
    @Autowired(required = false)
    protected ScMotionMapper scMotionMapper;
    @Autowired(required = false)
    protected ScMotionPostMapper scMotionPostMapper;

    /**
     * 干部选拔-干部津贴变动
     */
    @Autowired(required = false)
    protected ScSubsidyMapper scSubsidyMapper;
    @Autowired(required = false)
    protected ScSubsidyDispatchMapper scSubsidyDispatchMapper;
    @Autowired(required = false)
    protected ScSubsidyDispatchViewMapper scSubsidyDispatchViewMapper;
    @Autowired(required = false)
    protected ScSubsidyCadreMapper scSubsidyCadreMapper;
    @Autowired(required = false)
    protected ScSubsidyCadreViewMapper scSubsidyCadreViewMapper;
    @Autowired(required = false)
    protected ScSubsidyDcMapper scSubsidyDcMapper;

    /**
     * 干部选拔-新提任干部交证件
     */
    @Autowired(required = false)
    protected ScPassportHandMapper scPassportHandMapper;
    @Autowired(required = false)
    protected ScPassportMapper scPassportMapper;

    @Autowired(required = false)
    protected ScPassportMsgMapper scPassportMsgMapper;

    /**
     * 干部选拔-干部任免审批表
     */
    @Autowired(required = false)
    protected ScAdArchiveMapper scAdArchiveMapper;
    @Autowired(required = false)
    protected ScAdArchiveViewMapper scAdArchiveViewMapper;
    @Autowired(required = false)
    protected ScAdArchiveVoteMapper scAdArchiveVoteMapper;
    @Autowired(required = false)
    protected ScAdUseMapper scAdUseMapper;

    /**
     * 干部选拔-干部起草文件
     */
    @Autowired(required = false)
    protected ScDispatchMapper scDispatchMapper;
    @Autowired(required = false)
    protected ScDispatchViewMapper scDispatchViewMapper;
    @Autowired(required = false)
    protected ScDispatchUserMapper scDispatchUserMapper;
    @Autowired(required = false)
    protected ScDispatchCommitteeMapper scDispatchCommitteeMapper;
    /**
     * 干部选拔-干部任前公示
     */
    @Autowired(required = false)
    protected ScPublicMapper scPublicMapper;
    @Autowired(required = false)
    protected ScPublicViewMapper scPublicViewMapper;
    @Autowired(required = false)
    protected ScPublicUserMapper scPublicUserMapper;

    /**
     * 干部选拔-干部小组会议题
     */
    @Autowired(required = false)
    protected ScGroupMapper scGroupMapper;
    @Autowired(required = false)
    protected ScGroupFileMapper scGroupFileMapper;
    @Autowired(required = false)
    protected ScGroupMemberMapper scGroupMemberMapper;
    @Autowired(required = false)
    protected ScGroupMemberViewMapper scGroupMemberViewMapper;
    @Autowired(required = false)
    protected ScGroupParticipantMapper scGroupParticipantMapper;
    @Autowired(required = false)
    protected ScGroupTopicMapper scGroupTopicMapper;
    @Autowired(required = false)
    protected ScGroupTopicViewMapper scGroupTopicViewMapper;
    @Autowired(required = false)
    protected ScGroupTopicUnitMapper scGroupTopicUnitMapper;

    /**
     * 干部选拔-党委常委会议题
     */
    @Autowired(required = false)
    protected ScCommitteeMapper scCommitteeMapper;
    @Autowired(required = false)
    protected ScCommitteeViewMapper scCommitteeViewMapper;
    @Autowired(required = false)
    protected ScCommitteeMemberMapper scCommitteeMemberMapper;
    @Autowired(required = false)
    protected ScCommitteeMemberViewMapper scCommitteeMemberViewMapper;
    @Autowired(required = false)
    protected ScCommitteeTopicMapper scCommitteeTopicMapper;
    @Autowired(required = false)
    protected ScCommitteeTopicCadreMapper scCommitteeTopicCadreMapper;
    @Autowired(required = false)
    protected ScCommitteeTopicViewMapper scCommitteeTopicViewMapper;
    @Autowired(required = false)
    protected ScCommitteeVoteMapper scCommitteeVoteMapper;
    @Autowired(required = false)
    protected ScCommitteeVoteViewMapper scCommitteeVoteViewMapper;
    @Autowired(required = false)
    protected ScCommitteeOtherVoteMapper scCommitteeOtherVoteMapper;
    @Autowired(required = false)
    protected ScCommitteeOtherVoteViewMapper scCommitteeOtherVoteViewMapper;

    /**
     * 干部选拔-纪委函询管理
     */
    @Autowired(required = false)
    protected ScLetterMapper scLetterMapper;
    @Autowired(required = false)
    protected ScLetterViewMapper scLetterViewMapper;
    @Autowired(required = false)
    protected ScLetterItemMapper scLetterItemMapper;
    @Autowired(required = false)
    protected ScLetterItemViewMapper scLetterItemViewMapper;
    @Autowired(required = false)
    protected ScLetterReplyMapper scLetterReplyMapper;
    @Autowired(required = false)
    protected ScLetterReplyViewMapper scLetterReplyViewMapper;
    @Autowired(required = false)
    protected ScLetterReplyItemMapper scLetterReplyItemMapper;
    @Autowired(required = false)
    protected ScLetterReplyItemViewMapper scLetterReplyItemViewMapper;

    /**
     * 干部选拔-出入境备案
     */
    @Autowired(required = false)
    protected ScBorderMapper scBorderMapper;
    @Autowired(required = false)
    protected ScBorderViewMapper scBorderViewMapper;
    @Autowired(required = false)
    protected ScBorderItemMapper scBorderItemMapper;
    @Autowired(required = false)
    protected ScBorderItemViewMapper scBorderItemViewMapper;

    /**
     * 干部选拔-个人有关事项
     */
    @Autowired(required = false)
    protected ScMatterMapper scMatterMapper;
    @Autowired(required = false)
    protected ScMatterViewMapper scMatterViewMapper;
    @Autowired(required = false)
    protected ScMatterItemMapper scMatterItemMapper;
    @Autowired(required = false)
    protected ScMatterItemViewMapper scMatterItemViewMapper;
    @Autowired(required = false)
    protected ScMatterUserViewMapper scMatterUserViewMapper;
    @Autowired(required = false)
    protected ScMatterAccessMapper scMatterAccessMapper;
    @Autowired(required = false)
    protected ScMatterAccessItemMapper scMatterAccessItemMapper;
    @Autowired(required = false)
    protected ScMatterAccessItemViewMapper scMatterAccessItemViewMapper;
    @Autowired(required = false)
    protected ScMatterCheckMapper scMatterCheckMapper;
    @Autowired(required = false)
    protected ScMatterCheckViewMapper scMatterCheckViewMapper;
    @Autowired(required = false)
    protected ScMatterCheckItemMapper scMatterCheckItemMapper;
    @Autowired(required = false)
    protected ScMatterCheckItemViewMapper scMatterCheckItemViewMapper;
    @Autowired(required = false)
    protected ScMatterTransferMapper scMatterTransferMapper;
    @Autowired(required = false)
    protected IScMapper iScMapper;
}
