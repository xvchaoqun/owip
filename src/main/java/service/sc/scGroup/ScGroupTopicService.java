package service.sc.scGroup;

import controller.sc.scGroup.TopicUser;
import domain.base.MetaType;
import domain.sc.scGroup.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;
import service.unit.UnitService;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ScGroupTopicService extends ScBaseMapper {

    @Autowired
    private UnitService unitService;

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupTopicExample example = new ScGroupTopicExample();
        ScGroupTopicExample.Criteria criteria = example.createCriteria();
        if (id != null) criteria.andIdNotEqualTo(id);

        return scGroupTopicMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroupTopic record, Integer[] unitIds, List<TopicUser> topicUsers) {

        record.setIsDeleted(false);
        scGroupTopicMapper.insertSelective(record);

        if(topicUsers!=null){
            for (TopicUser topicUser : topicUsers) {

                ScGroupTopicUser _record = new ScGroupTopicUser();
                _record.setUserId(topicUser.getUserId());
                _record.setTitle(topicUser.getTitle());
                scGroupTopicUserMapper.insertSelective(_record);
            }
        }

        updateUnitIds(record.getId(), unitIds);

        clearByType(record.getId(), record.getType());
    }

    @Transactional
    public void del(Integer id) {

        scGroupTopicMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScGroupTopicExample example = new ScGroupTopicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScGroupTopic record = new ScGroupTopic();
        record.setIsDeleted(true);
        scGroupTopicMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScGroupTopic record, Integer[] unitIds, List<TopicUser> topicUsers) {

        int topicId = record.getId();
        scGroupTopicMapper.updateByPrimaryKeySelective(record);
        updateUnitIds(topicId, unitIds);

        if(topicUsers!=null){

            commonMapper.excuteSql("delete from sc_group_topic_user where topic_id="+ topicId);
            for (TopicUser topicUser : topicUsers) {

                ScGroupTopicUser _record = new ScGroupTopicUser();
                _record.setTopicId(topicId);
                _record.setUserId(topicUser.getUserId());
                _record.setTitle(topicUser.getTitle());
                scGroupTopicUserMapper.insertSelective(_record);
            }
        }

        clearByType(record.getId(), record.getType());
    }

    // 清除多余的字段值
    public void clearByType(int topicId, int type){

        MetaType metaType = CmTag.getMetaType(type);
        if(metaType!=null){
            String code = metaType.getCode();
            if(code.equals("mt_sgt_motion")){

                commonMapper.excuteSql("update sc_group_topic set record_id=null, candidate_user_id=null where id="+topicId);
                commonMapper.excuteSql("delete from sc_group_topic_user where topic_id="+topicId);

            }else if(code.equals("mt_sgt_candidate")){

                commonMapper.excuteSql("update sc_group_topic set sc_type=null, candidate_user_id=null, content=null where id="+topicId);

            }else if(code.equals("mt_sgt_recommend")){

                commonMapper.excuteSql("update sc_group_topic set sc_type=null, content=null where id="+topicId);
                commonMapper.excuteSql("delete from sc_group_topic_user where topic_id="+topicId);

            }else if(code.equals("mt_sgt_other")){

                commonMapper.excuteSql("update sc_group_topic set unit_post_id=null, sc_type=null, " +
                        "record_id=null, candidate_user_id=null where id="+topicId);
                commonMapper.excuteSql("delete from sc_group_topic_user where topic_id="+topicId);
            }
        }
    }

    public List<Unit> getUnits(Integer topicId) {

        Map<Integer, Unit> unitMap = unitService.findAll();

        List<Unit> units = new ArrayList<>();
        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        example.createCriteria().andTopicIdEqualTo(topicId);
        example.setOrderByClause("id asc");
        List<ScGroupTopicUnit> scGroupTopicUnits = scGroupTopicUnitMapper.selectByExample(example);
        for (ScGroupTopicUnit scGroupTopicUnit : scGroupTopicUnits) {
            Integer unitId = scGroupTopicUnit.getUnitId();
            Unit unit = unitMap.get(unitId);
            units.add(unit);
        }

        return units;
    }

    public void updateUnitIds(int topicId, Integer[] unitIds) {

        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        example.createCriteria().andTopicIdEqualTo(topicId);
        scGroupTopicUnitMapper.deleteByExample(example);

        if (unitIds != null && unitIds.length > 0) {
            for (Integer unitId : unitIds) {

                ScGroupTopicUnit _record = new ScGroupTopicUnit();
                _record.setTopicId(topicId);
                _record.setUnitId(unitId);
                scGroupTopicUnitMapper.insertSelective(_record);
            }
        }
    }

    // 根据纪实ID和议题类型，获取议题
    public ScGroupTopic getTopic(int recordId, int type){

        ScGroupTopicExample example = new ScGroupTopicExample();
        example.createCriteria().andRecordIdEqualTo(recordId).andTypeEqualTo(type);
        List<ScGroupTopic> scGroupTopics = scGroupTopicMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return scGroupTopics.size()==0?null:scGroupTopics.get(0);
    }

    // 确定的考察对象列表
    public List<TopicUser> getTopicUsers(Integer topicId) {

         List<TopicUser> topicUsers = new ArrayList<>();

        if(topicId==null) return topicUsers;
        ScGroupTopicUserExample example = new ScGroupTopicUserExample();
        ScGroupTopicUserExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);

        List<ScGroupTopicUser> records = scGroupTopicUserMapper.selectByExample(example);
        for (ScGroupTopicUser record : records) {

            SysUserView uv = CmTag.getUserById(record.getUserId());
            TopicUser topicUser = new TopicUser();
            topicUser.setUserId(record.getUserId());
            topicUser.setCode(uv.getCode());
            topicUser.setRealname(uv.getRealname());
            topicUser.setTitle(record.getTitle());

            topicUsers.add(topicUser);
        }
        return topicUsers;
    }
}
