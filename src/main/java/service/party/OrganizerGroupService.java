package service.party;

import controller.global.OpException;
import domain.party.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrganizerGroupService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        OrganizerGroupExample example = new OrganizerGroupExample();
        OrganizerGroupExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return organizerGroupMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(OrganizerGroup record){

        record.setSortOrder(getNextSortOrder("ow_organizer_group", null));
        organizerGroupMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OrganizerGroupExample example = new OrganizerGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        organizerGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(OrganizerGroup record){
        return organizerGroupMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        OrganizerGroup entity = organizerGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        OrganizerGroupExample example = new OrganizerGroupExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<OrganizerGroup> overEntities = organizerGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            OrganizerGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ow_organizer_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_organizer_group", null, baseSortOrder, targetEntity.getSortOrder());

            OrganizerGroup record = new OrganizerGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            organizerGroupMapper.updateByPrimaryKeySelective(record);
        }
    }

    public List<OrganizerGroupUnit> getUnits(int groupId){

        OrganizerGroupUnitExample example = new OrganizerGroupUnitExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order asc");

        return organizerGroupUnitMapper.selectByExample(example);
    }

    public void updateUnits(int groupId){

        List<OrganizerGroupUnit> oguList = getUnits(groupId);
        List<String> userList = new ArrayList<>();
        for (OrganizerGroupUnit ogu : oguList) {
            Unit unit = CmTag.getUnit(ogu.getUnitId());
            userList.add(unit.getName() + "|" + unit.getId());
        }

        String units = StringUtils.join(userList, ",");
        OrganizerGroup _record = new OrganizerGroup();
        _record.setId(groupId);
        _record.setUnits(units);
        organizerGroupMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    public void addUnit(OrganizerGroupUnit record){

        int groupId = record.getGroupId();
        int unitId = record.getUnitId();

        OrganizerGroupUnitExample example = new OrganizerGroupUnitExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andUnitIdEqualTo(unitId);
        if(organizerGroupUnitMapper.countByExample(example)>0){
            throw new OpException("添加重复。");
        }

        record.setSortOrder(getNextSortOrder("ow_organizer_group_unit",
                "group_id="+ groupId));
        organizerGroupUnitMapper.insertSelective(record);

        updateUnits(groupId);
    }

    @Transactional
    public void delUnit(Integer id) {

        OrganizerGroupUnit organizerGroupUnit = organizerGroupUnitMapper.selectByPrimaryKey(id);
        Integer groupId = organizerGroupUnit.getGroupId();

        organizerGroupUnitMapper.deleteByPrimaryKey(id);

        updateUnits(groupId);
    }

    /**
     * 联系单位排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void unitChangeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        OrganizerGroupUnit entity = organizerGroupUnitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int groupId = entity.getGroupId();

        OrganizerGroupUnitExample example = new OrganizerGroupUnitExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<OrganizerGroupUnit> overEntities = organizerGroupUnitMapper
                .selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            OrganizerGroupUnit targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ow_organizer_group_unit",
                        "group_id="+ entity.getGroupId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_organizer_group_unit",
                        "group_id="+ entity.getGroupId(), baseSortOrder, targetEntity.getSortOrder());

            OrganizerGroupUnit record = new OrganizerGroupUnit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            organizerGroupUnitMapper.updateByPrimaryKeySelective(record);
        }

        updateUnits(groupId);
    }

    public List<OrganizerGroupUser> getUsers(int groupId){

        OrganizerGroupUserExample example = new OrganizerGroupUserExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order asc");

        return organizerGroupUserMapper.selectByExample(example);
    }

    public void updateOrganizers(int groupId){

        List<OrganizerGroupUser> oguList = getUsers(groupId);
        List<String> userList = new ArrayList<>();
        for (OrganizerGroupUser ogu : oguList) {
            SysUserView uv = CmTag.getUserById(ogu.getUserId());
            userList.add(uv.getRealname() + "|" + uv.getId());
        }

        String organizers = StringUtils.join(userList, ",");
        OrganizerGroup _record = new OrganizerGroup();
        _record.setId(groupId);
        _record.setOrganizers(organizers);
        organizerGroupMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    public void addUser(OrganizerGroupUser record){

        int groupId = record.getGroupId();
        int userId = record.getUserId();

        OrganizerGroupUserExample example = new OrganizerGroupUserExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
        if(organizerGroupUserMapper.countByExample(example)>0){
            throw new OpException("添加重复。");
        }

        record.setSortOrder(getNextSortOrder("ow_organizer_group_user",
                "group_id="+ groupId));
        organizerGroupUserMapper.insertSelective(record);

        updateOrganizers(groupId);
    }

    @Transactional
    public void delUser(Integer id) {

        OrganizerGroupUser organizerGroupUser = organizerGroupUserMapper.selectByPrimaryKey(id);
        Integer groupId = organizerGroupUser.getGroupId();

        organizerGroupUserMapper.deleteByPrimaryKey(id);

        updateOrganizers(groupId);
    }

    /**
     * 成员排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void userChangeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        OrganizerGroupUser entity = organizerGroupUserMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int groupId = entity.getGroupId();

        OrganizerGroupUserExample example = new OrganizerGroupUserExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<OrganizerGroupUser> overEntities = organizerGroupUserMapper
                .selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            OrganizerGroupUser targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ow_organizer_group_user",
                        "group_id="+ entity.getGroupId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_organizer_group_user",
                        "group_id="+ entity.getGroupId(), baseSortOrder, targetEntity.getSortOrder());

            OrganizerGroupUser record = new OrganizerGroupUser();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            organizerGroupUserMapper.updateByPrimaryKeySelective(record);
        }

        updateOrganizers(groupId);
    }
}
