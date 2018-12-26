package service.sc.scMatter;

import domain.sc.scMatter.ScMatterAccess;
import domain.sc.scMatter.ScMatterAccessExample;
import domain.sc.scMatter.ScMatterAccessItem;
import domain.sc.scMatter.ScMatterAccessItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScMatterAccessService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScMatterAccess record, Integer[] matterItemIds){

        record.setIsDeleted(false);
        scMatterAccessMapper.insertSelective(record);

        updateMatterItemIds(record.getId(), matterItemIds);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterAccessExample example = new ScMatterAccessExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ScMatterAccess record = new ScMatterAccess();
        record.setIsDeleted(true);
        scMatterAccessMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScMatterAccess record, Integer[] matterItemIds){

        scMatterAccessMapper.updateByPrimaryKeySelective(record);
        updateMatterItemIds(record.getId(), matterItemIds);
    }


    // 获取调阅记录包含的填报记录id
    public Set<Integer> getMatterItemIds(int accessId) {

        Set<Integer> matterItemIds = new HashSet<>();

        ScMatterAccessItemExample example = new ScMatterAccessItemExample();
        example.createCriteria().andAccessIdEqualTo(accessId);
        List<ScMatterAccessItem> scMatterAccessItems = scMatterAccessItemMapper.selectByExample(example);

        for (ScMatterAccessItem scMatterAccessItem : scMatterAccessItems) {
            matterItemIds.add(scMatterAccessItem.getMatterItemId());
        }

        return matterItemIds;
    }

    // 更新调阅记录包含的填报记录
    @Transactional
    public void updateMatterItemIds(int accessId, Integer[] matterItemIds) {

        {
            ScMatterAccessItemExample example = new ScMatterAccessItemExample();
            example.createCriteria().andAccessIdEqualTo(accessId);
            scMatterAccessItemMapper.deleteByExample(example);
        }

        if (matterItemIds == null || matterItemIds.length == 0) return;

        for (Integer matterItemId : matterItemIds) {

            ScMatterAccessItem record = new ScMatterAccessItem();
            record.setAccessId(accessId);
            record.setMatterItemId(matterItemId);
            scMatterAccessItemMapper.insertSelective(record);
        }

    }
}
