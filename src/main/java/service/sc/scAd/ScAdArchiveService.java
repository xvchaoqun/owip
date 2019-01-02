package service.sc.scAd;

import bean.CadreInfoForm;
import controller.global.OpException;
import domain.cis.CisInspectObj;
import domain.sc.scAd.*;
import domain.sc.scCommittee.*;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cis.CisInspectObjMapper;
import service.cadre.CadreAdformService;
import service.cis.CisInspectObjService;
import service.sc.ScBaseMapper;
import service.sc.scCommittee.ScCommitteeTopicService;
import service.sys.SysConfigService;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.XmlSerializeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ScAdArchiveService extends ScBaseMapper {

    @Autowired
    private CadreAdformService cadreAdformService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected ScCommitteeTopicService scCommitteeTopicService;
    @Autowired(required = false)
    protected CisInspectObjMapper cisInspectObjMapper;

 /*   public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScAdArchiveExample example = new ScAdArchiveExample();
        ScAdArchiveExample.Criteria criteria = example.createCriteria().and(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scAdArchiveMapper.countByExample(example) > 0;
    }
*/
    @Transactional
    public void insertSelective(ScAdArchiveWithBLOBs record){

        scAdArchiveMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scAdArchiveMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScAdArchiveExample example = new ScAdArchiveExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scAdArchiveMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScAdArchiveWithBLOBs record){
        return scAdArchiveMapper.updateByPrimaryKeySelective(record);
    }

    // 添加任免审批表
    @Transactional
    public void add(int committeeId, Integer[] cadreIds) {

        for (Integer cadreId : cadreIds) {

            ScAdArchiveWithBLOBs record = new ScAdArchiveWithBLOBs();
            record.setCommitteeId(committeeId);
            record.setCadreId(cadreId);
            record.setHasAppoint(false);
            record.setIsAdformSaved(false);

            scAdArchiveMapper.insertSelective(record);
        }
    }

    public List<ScCommitteeVote> checkVotes(Integer archiveId, Integer[] voteIds) {

        ScAdArchive scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
        int cadreId = scAdArchive.getCadreId();

        if(voteIds==null){
            throw new OpException("请选择任免信息。");
        }else if(voteIds.length>2){
            throw new OpException("任命和免职分别允许最多选择一条。");
        }
        {
            ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
            example.createCriteria().andArchiveIdNotEqualTo(archiveId)
                    .andVoteIdIn(Arrays.asList(voteIds));
            if (scAdArchiveVoteMapper.countByExample(example) > 0) {
                throw new OpException("任免信息重复使用。");
            }
        }

        ScCommitteeVoteExample example = new ScCommitteeVoteExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(voteIds));
        List<ScCommitteeVote> scCommitteeVotes = scCommitteeVoteMapper.selectByExample(example);

        if(scCommitteeVotes.size() == 0){
            throw new OpException("任免信息不存在。");
        }

        if(scCommitteeVotes.size()==2){
            ScCommitteeVote scCommitteeVote1 = scCommitteeVotes.get(0);
            ScCommitteeVote scCommitteeVote2 = scCommitteeVotes.get(1);
            if(scCommitteeVote1.getType().byteValue() == scCommitteeVote2.getType()){
                throw new OpException("任命和免职分别允许最多选择一条。");
            }
        }

        return scCommitteeVotes;
    }

    public CadreInfoForm getCadreAdForm(Integer archiveId, Integer[] voteIds) {

        List<ScCommitteeVote> votes = checkVotes(archiveId, voteIds);

        return getCadreAdForm(votes);
    }

    public CadreInfoForm getCadreAdForm(List<ScCommitteeVote> scCommitteeVotes){

        if(scCommitteeVotes==null || scCommitteeVotes.size()==0) return null;

        Integer cadreId = null;
        Integer topicId = null;
        ScCommitteeVote appointVote = null;
        ScCommitteeVote dismissVote = null;
        for (ScCommitteeVote scCommitteeVote : scCommitteeVotes) {

            cadreId = scCommitteeVote.getCadreId();
            topicId = scCommitteeVote.getTopicId();

            if(scCommitteeVote.getType() == DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT){
                appointVote = scCommitteeVote;
            }else{
                dismissVote = scCommitteeVote;
            }
        }

        ScCommitteeTopicCadre topicCadre = scCommitteeTopicService.getTopicCadre(topicId, cadreId);
        //CadreView cadre = iCadreMapper.getCadre(cadreId);
        CadreInfoForm bean = cadreAdformService.getCadreAdform(cadreId);
        bean.setPost(topicCadre.getOriginalPost());
        bean.setInPost(null);
        bean.setPrePost(null);
        if(appointVote!=null){
            // 拟任职务
            bean.setInPost(appointVote.getPost());
        }
        if(dismissVote!=null){
            // 拟免职务
            bean.setPrePost(dismissVote.getPost());
        }

        return bean;
    }

    @Transactional
    public void save(Integer archiveId, Integer[] voteIds) {

        CadreInfoForm cadreAdForm = getCadreAdForm(archiveId, voteIds);

        ScAdArchiveWithBLOBs record = new ScAdArchiveWithBLOBs();
        record.setId(archiveId);
        record.setAdform(XmlSerializeUtils.serialize(cadreAdForm));
        record.setIsAdformSaved(true);
        record.setHasAppoint(StringUtils.isNoneBlank(cadreAdForm.getInPost()));
        record.setAdformSaveTime(new Date());
        scAdArchiveMapper.updateByPrimaryKeySelective(record);

        {
            ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
            example.createCriteria().andArchiveIdEqualTo(archiveId);
            scAdArchiveVoteMapper.deleteByExample(example);
        }
        for (Integer voteId : voteIds) {
            ScAdArchiveVote vote = new ScAdArchiveVote();
            vote.setArchiveId(archiveId);
            vote.setVoteId(voteId);
            scAdArchiveVoteMapper.insertSelective(vote);
        }
    }

    // 读取已选择的干部考察报告
    public CisInspectObj getCisInspectObj(int archiveId, int objId){

        {
            ScAdArchiveExample example = new ScAdArchiveExample();
            example.createCriteria().andIdNotEqualTo(archiveId).andObjIdEqualTo(objId);
            if(scAdArchiveMapper.countByExample(example)>0){
                throw new OpException("干部考察报告重复使用。");
            }
        }

        Date holdDate = null;
        ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
        example.createCriteria().andArchiveIdEqualTo(archiveId);
        List<ScAdArchiveVote> scAdArchiveVotes = scAdArchiveVoteMapper.selectByExample(example);
        for (ScAdArchiveVote scAdArchiveVote : scAdArchiveVotes) {

            ScCommitteeVoteViewExample example1 = new ScCommitteeVoteViewExample();
            example1.createCriteria().andIdEqualTo(scAdArchiveVote.getVoteId()).andTypeEqualTo(DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT);
            List<ScCommitteeVoteView> scCommitteeVoteViews = scCommitteeVoteViewMapper.selectByExampleWithRowbounds(example1, new RowBounds(0, 1));

            if(scCommitteeVoteViews.size()==1){
                ScCommittee scCommittee = scCommitteeMapper.selectByPrimaryKey(scCommitteeVoteViews.get(0).getCommitteeId());
                holdDate = scCommittee.getHoldDate();
            }
        }

        CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
        ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
        if(cisInspectObj.getCadreId().intValue() != scAdArchive.getCadreId()){
            throw new OpException("干部考察报告有误。");
        }

        if(holdDate!=null) {
            cisInspectObj.setRemark(DateUtils.formatDate(holdDate, DateUtils.YYYY_MM_DD_CHINA)+"党委常委会通过任命");
        }

        return cisInspectObj;
    }

    @Transactional
    public void cisSave(Integer archiveId, int objId) throws IOException, TemplateException {

        CisInspectObj cisInspectObj = getCisInspectObj(archiveId, objId);
        Map<String, Object> dataMap = getCisInspectObjService().getDataMap(cisInspectObj);
        ScAdArchiveWithBLOBs record = new ScAdArchiveWithBLOBs();
        record.setId(archiveId);
        record.setCis(XmlSerializeUtils.serialize(dataMap));
        record.setObjId(objId);
        record.setCisSaveTime(new Date());
        scAdArchiveMapper.updateByPrimaryKeySelective(record);
    }

    public CisInspectObjService getCisInspectObjService(){

        return CmTag.getBean(CisInspectObjService.class);
    }
}
