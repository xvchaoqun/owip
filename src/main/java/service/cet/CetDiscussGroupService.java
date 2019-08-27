package service.cet;

import domain.cet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.CetConstants;

import java.util.Arrays;

@Service
public class CetDiscussGroupService extends CetBaseMapper {

    @Autowired
    private CetDiscussGroupObjService cetDiscussGroupObjService;

    @Transactional
    public void insertSelective(CetDiscussGroup record){

        Integer discussId = record.getDiscussId();
        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        if(cetDiscuss.getUnitType()== CetConstants.CET_DISCUSS_UNIT_TYPE_OW){
            record.setAdminUserId(ShiroHelper.getCurrentUserId());
        }

        record.setSortOrder(getNextSortOrder("cet_discuss_group", "discuss_id="+record.getDiscussId()));
        cetDiscussGroupMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetDiscussGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussGroupExample example = new CetDiscussGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetDiscussGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetDiscussGroup record){

        return cetDiscussGroupMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        CetDiscussGroup entity = cetDiscussGroupMapper.selectByPrimaryKey(id);
        changeOrder("cet_discuss_group", "discuss_id="+entity.getDiscussId(), ORDER_BY_ASC, id, addNum);
    }

    @Transactional
    public void selectObjs(Integer[] objIds, boolean select, int discussGroupId) {

        if(objIds==null || objIds.length==0) return ;

        if(select){
            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
            int discussId = cetDiscussGroup.getDiscussId();
            for (Integer objId : objIds) {

                CetDiscussGroupObj cetDiscussGroupObj = cetDiscussGroupObjService.getByDiscussId(objId, discussId);
                if (cetDiscussGroupObj!=null) continue;

                CetDiscussGroupObj record = new CetDiscussGroupObj();
                record.setDiscussId(discussId);
                record.setDiscussGroupId(discussGroupId);
                record.setObjId(objId);
                record.setIsFinished(false);

                cetDiscussGroupObjMapper.insertSelective(record);
            }
        }else{

            CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
            example.createCriteria().andDiscussGroupIdEqualTo(discussGroupId)
                    .andObjIdIn(Arrays.asList(objIds));

            cetDiscussGroupObjMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void finish(Integer[] objIds, boolean finish, int discussGroupId) {

        if(objIds==null || objIds.length==0) return ;

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andDiscussGroupIdEqualTo(discussGroupId)
                .andObjIdIn(Arrays.asList(objIds));

        CetDiscussGroupObj record = new CetDiscussGroupObj();
        record.setIsFinished(finish);
        cetDiscussGroupObjMapper.updateByExampleSelective(record, example);
    }
}
