package service.sc.scPublic;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.cadre.CadreView;
import domain.sc.scCommittee.ScCommittee;
import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scPublic.ScPublic;
import domain.sc.scPublic.ScPublicExample;
import domain.sc.scPublic.ScPublicUser;
import domain.sc.scPublic.ScPublicUserExample;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreService;
import service.sc.ScBaseMapper;
import service.sys.UserBeanService;
import sys.constants.CadreConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ScPublicService extends ScBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScPublicUserService scPublicUserService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected MetaTypeService metaTypeService;

    public boolean idDuplicate(Integer id, int year, int num){

        ScPublicExample example = new ScPublicExample();
        ScPublicExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year).andNumEqualTo(num);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scPublicMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScPublic record, Integer[] voteIds){

        if(record.getYear()!=null && record.getNum()!=null){
            Assert.isTrue(!idDuplicate(null, record.getYear(), record.getNum()), "编号重复");
        }
        record.setIsFinished(false);
        record.setIsConfirmed(false);
        record.setIsDeleted(false);
        scPublicMapper.insertSelective(record);
        updateUsers(record.getId(), voteIds);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScPublicExample example = new ScPublicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScPublic record = new ScPublic();
        record.setIsDeleted(true);
        scPublicMapper.updateByExampleSelective(record, example);
    }

    // 结束公示（未确认）
    @Transactional
    public void finish(Integer[] ids, Boolean confirm) {

        if(ids==null || ids.length==0) return;

        ScPublicExample example = new ScPublicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScPublic record = new ScPublic();
        record.setIsFinished(true);
        record.setIsConfirmed(BooleanUtils.isTrue(confirm));

        scPublicMapper.updateByExampleSelective(record, example);
    }

    // 确认结束公示
    @Transactional
    public void confirm(Integer[] ids) {

        if(ids==null || ids.length==0) return;

        ScPublicExample example = new ScPublicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScPublic record = new ScPublic();
        record.setIsConfirmed(true);
        scPublicMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScPublic record, Integer[] voteIds){

        if(record.getYear()!=null && record.getNum()!=null){

            Assert.isTrue(!idDuplicate(record.getId(), record.getYear(), record.getNum()), "编号重复");
        }
        scPublicMapper.updateByPrimaryKeySelective(record);

        updateUsers(record.getId(), voteIds);
    }

    @Transactional
    public void updateUsers(int publicId, Integer[] voteIds){

        ScPublicUserExample example = new ScPublicUserExample();
        example.createCriteria().andPublicIdEqualTo(publicId);
        scPublicUserMapper.deleteByExample(example);

        for (Integer voteId : voteIds) {

            ScPublicUser record = new ScPublicUser();
            record.setPublicId(publicId);
            record.setVoteId(voteId);
            scPublicUserService.insertSelective(record);
        }

    }

    // 系统自动扫描公示结束
    @Transactional
    public void autoFinish() {

        ScPublic record = new ScPublic();
        record.setIsFinished(true);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date todayStart = cal.getTime();

        ScPublicExample example = new ScPublicExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andIsFinishedEqualTo(false)
                // 扫描从当天0点到运行之间结束的
                .andPublicEndDateBetween(todayStart, now);

        scPublicMapper.updateByExampleSelective(record, example);

        // 发送短信
        autoFinishMsg();
    }

    // 公示结束没有“确认”之前， 每天上午8:30， 下午2:30反复发短信提醒。
    @Transactional
    public void autoFinishMsg() {

        ScPublicExample example = new ScPublicExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andIsFinishedEqualTo(true)
                .andIsConfirmedEqualTo(false);

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_SC_PUBLIC_FINISH_CONFIRM);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());
        List<ScPublic> scPublics = scPublicMapper.selectByExample(example);
        for (ScPublic scPublic : scPublics) {

            String endDate = DateUtils.formatDate(scPublic.getPublicEndDate(), DateUtils.YYYY_MM_DD_CHINA);
            for (SysUserView uv : receivers) {
                try {
                    int userId = uv.getId();
                    String mobile = userBeanService.getMsgMobile(userId);
                    String msgTitle = userBeanService.getMsgTitle(userId);
                    String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                            scPublic.getCode(), endDate);
                    ShortMsgBean bean = new ShortMsgBean();
                    bean.setSender(null);
                    bean.setReceiver(userId);
                    bean.setMobile(mobile);
                    bean.setContent(msg);
                    bean.setRelateId(tpl.getId());
                    bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                    bean.setType(tpl.getName());

                    shortMsgService.send(bean, null);
                }catch (Exception ex){
                    ex.printStackTrace();
                    logger.error("干部任前公示结束确认提醒失败。公示编号：{}， 接收人：{}, {}",
                            scPublic.getCode(), uv.getRealname(), uv.getCode());
                }
            }
        }
    }

    /**
     * 干部任前公示.doc
     */
    public Map<String, Object> processData(ScPublic scPublic, List<ScCommitteeVoteView> scPublicVotes) throws IOException, TemplateException {

        ScCommittee scCommittee = scCommitteeMapper.selectByPrimaryKey(scPublic.getCommitteeId());

        Date publicStartDate = scPublic.getPublicStartDate();
        Date publicEndDate = scPublic.getPublicEndDate();
        String _publicDate = DateUtils.formatDate(publicStartDate, DateUtils.YYYY_MM_DD_HH_MM_CHINA)
                + "－" + DateUtils.formatDate(publicEndDate, DateUtils.YYYY_MM_DD_HH_MM_CHINA);

        Date publishDate = scPublic.getPublishDate();
        String _publishDate = DateUtils.formatDate(publishDate, DateUtils.YYYY_MM_DD_CHINA);

        Date holdDate = scCommittee.getHoldDate();
        String _holdDate = DateUtils.formatDate(holdDate, DateUtils.YYYY_MM_DD_CHINA);

        //List<ScCommitteeVoteView> scPublicVotes = iScMapper.getScPublicVotes(publicId);

        List votes = new ArrayList<>();
        for (ScCommitteeVoteView scPublicVote : scPublicVotes) {

            SysUserView uv = scPublicVote.getUser();
            Map<String, Object> vote = new HashMap<>();
            vote.put("realname", uv.getRealname());
            vote.put("post", scPublicVote.getPost());

            // 男，1983年10月生，汉族，农工党党员，博士，教授 。现为天文系教师。
            Byte gender = uv.getGender();
            String _gender = SystemConstants.GENDER_MAP.get(gender);
            String _birth = DateUtils.formatDate(uv.getBirth(), "yyyy年MM月");
            String nation = uv.getNation();

            String partyName = null;
            String edu = null;
            String post = null;
            CadreView cv = cadreService.dbFindByUserId(uv.getId());
            if(cv!=null) {
                partyName = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(),
                        "中共党员", cv.getDpTypeId(), cv.getDpGrowTime(), true).get("partyName");
                edu = metaTypeService.getName(cv.getEduId());
                post = StringUtils.defaultIfBlank(cv.getProPostLevel(), cv.getMainPostLevel());
            }

            String originalPost = scPublicVote.getOriginalPost();

            vote.put("detail", MessageFormat.format("{0}，{1}生，{2}，{3}{4}，{5}。{6}{7}。",
                    _gender, _birth, nation, StringUtils.isBlank(partyName)?"":(partyName+"，"), StringUtils.trimToEmpty(edu)
                    , StringUtils.trimToEmpty(post),
                    (cv==null||!CadreConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus()))?"现为":"现任", originalPost));

            votes.add(vote);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("publicDate", _publicDate);
        dataMap.put("publishDate", _publishDate);
        dataMap.put("holdDate", _holdDate);
        dataMap.put("votes", votes);

        return dataMap;
    }
}
