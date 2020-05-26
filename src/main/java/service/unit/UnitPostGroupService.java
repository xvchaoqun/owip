package service.unit;

import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import domain.unit.UnitPostGroup;
import domain.unit.UnitPostGroupExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.*;

@Service
public class UnitPostGroupService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        UnitPostGroupExample example = new UnitPostGroupExample();
        UnitPostGroupExample.Criteria criteria = example.createCriteria().andNameEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitPostGroupMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(UnitPostGroup record){

        Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate");
        unitPostGroupMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        unitPostGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitPostGroupExample example = new UnitPostGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitPostGroupMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(UnitPostGroup record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate");
        unitPostGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updatePostAndGroupId( int id, Integer[] ids){  // ids 当前已选岗位id

        UnitPostGroup record = unitPostGroupMapper.selectByPrimaryKey(id);
        List<Integer> idList= new ArrayList<>();     //当前已选岗位id
        List<Integer> noCheackIdList= new ArrayList<>(); //取消选中的岗位
        if(ids!=null){
            idList=Arrays.asList(ids);
        }
        if(record.getPostIds()!=null){
            String[] postIds=record.getPostIds().split(",");
            for(String postId:postIds) {
                noCheackIdList.add(Integer.valueOf(postId.trim()));
            }
        }

        if(idList.size()>0){
            if(noCheackIdList.size()>0) {
                noCheackIdList.removeAll(idList);  //应取消关联分组的岗位
            }

            UnitPost unitPost = new UnitPost();
            unitPost.setGroupId(id);
            UnitPostExample example=new UnitPostExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            unitPostMapper.updateByExampleSelective(unitPost,example);

            UnitPostGroup unitPostGroup = new UnitPostGroup();
            unitPostGroup.setId(id);
            unitPostGroup.setPostIds(StringUtils.join(ids, ","));
            unitPostGroupMapper.updateByPrimaryKeySelective(unitPostGroup);
        }else{
            commonMapper.excuteSql("update unit_post_group set post_ids=null where id="+id);
        }

        if(noCheackIdList.size()>0){
            commonMapper.excuteSql("update unit_post set group_id=null where id in("
                    + StringUtils.join(noCheackIdList, ",") + ")");
        }

    }

    public Map<Integer, UnitPostGroup> findAll() {

        UnitPostGroupExample example = new UnitPostGroupExample();
        example.setOrderByClause("sort_order desc");
        List<UnitPostGroup> records = unitPostGroupMapper.selectByExample(example);
        Map<Integer, UnitPostGroup> map = new LinkedHashMap<>();
        for (UnitPostGroup record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
