package service.sc.scPublic;

import domain.sc.scPublic.ScPublicUser;
import domain.sc.scPublic.ScPublicUserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScPublicUserService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScPublicUser record){

        record.setSortOrder(getNextSortOrder("sc_public_user", "public_id="+ record.getPublicId()));
        scPublicUserMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scPublicUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScPublicUserExample example = new ScPublicUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scPublicUserMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScPublicUser record){
        return scPublicUserMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScPublicUser entity = scPublicUserMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer publicId = entity.getPublicId();

        ScPublicUserExample example = new ScPublicUserExample();
        if (addNum > 0) {

            example.createCriteria().andPublicIdEqualTo(publicId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andPublicIdEqualTo(publicId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScPublicUser> overEntities = scPublicUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScPublicUser targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_public_user", "public_id=" + publicId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_public_user", "public_id=" + publicId, baseSortOrder, targetEntity.getSortOrder());

            ScPublicUser record = new ScPublicUser();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scPublicUserMapper.updateByPrimaryKeySelective(record);
        }
    }
}
