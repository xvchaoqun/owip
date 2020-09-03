package job.member;

import domain.member.MemberView;
import domain.member.MemberViewExample;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.MemberViewMapper;
import persistence.member.common.IMemberMapper;
import persistence.party.common.IPartyMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateRetireStatus implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPartyMapper iPartyMapper;
    @Autowired
    private IMemberMapper iMemberMapper;
    @Autowired
    private MemberViewMapper memberViewMapper;

    /*
        根据分党委和党支部名称中是否包含的name1和name2，来更新其中状态为name的人员，设置其状态为“退休”
    * */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String nameRegExps = "离休|退休";

        List<Integer> partyIdList = iPartyMapper.findBranchIdList(nameRegExps);
        List<Integer> branchIdList = iPartyMapper.findPartyIdList(nameRegExps);
        if(partyIdList==null) partyIdList = new ArrayList<>();
        if(branchIdList==null) branchIdList = new ArrayList<>();

        if (partyIdList.isEmpty() && branchIdList.isEmpty()){
            logger.debug("没有退休分党委和党支部");
            return;
        }else {
            MemberViewExample example = new MemberViewExample();
            MemberViewExample.Criteria criteria = example.createCriteria().andStaffStatusEqualTo("在职");
            criteria.in(partyIdList, branchIdList);
            List<MemberView> memberViewList = memberViewMapper.selectByExample(example);
            List<Integer> userIdList = new ArrayList<>();
            if (memberViewList.size() > 0){
                userIdList = memberViewList.stream().map(MemberView::getUserId).collect(Collectors.toList());
            }
            if (userIdList.size() > 0){
                String userIds = StringUtils.join(userIdList, ",");
                iMemberMapper.updateRetireMemberStatus(userIds);
                logger.info("更新退休分党委和党支部中，状态为'在职'的党员，改为'退休'状态：" + userIds);
            }else {
                logger.debug("没有需要修改退休状态的党员");
            }
        }
    }

}
