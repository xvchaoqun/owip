package service.sc.scMatter;

import domain.cadre.Cadre;
import domain.sc.scMatter.ScMatter;
import domain.sc.scMatter.ScMatterExample;
import domain.sc.scMatter.ScMatterItem;
import domain.sc.scMatter.ScMatterItemExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import service.sys.SysUserService;
import sys.tags.CmTag;

import java.util.*;

@Service
public class ScMatterService extends ScBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Transactional
    public void insertSelective(ScMatter record, Integer[] userIds) {

        record.setIsDeleted(false);
        scMatterMapper.insertSelective(record);

        updateUserIds(record.getId(), userIds);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScMatterExample example = new ScMatterExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ScMatter record = new ScMatter();
        record.setIsDeleted(true);

        scMatterMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScMatter record, Integer[] userIds) {

        scMatterMapper.updateByPrimaryKeySelective(record);

        updateUserIds(record.getId(), userIds);
    }

    // 获取填报记录的所有填报对象
    public List<ScMatterItem> getItemList(int id) {

        ScMatterItemExample example = new ScMatterItemExample();
        example.createCriteria().andMatterIdEqualTo(id);
        example.setOrderByClause("id asc");

        return scMatterItemMapper.selectByExample(example);
    }

    // 获取填报记录的所有填报对象
    public List<SysUserView> getItemUserList(int id) {

        List<ScMatterItem> scMatterItems = getItemList(id);
        List<SysUserView> userList = new ArrayList<>();
        for (ScMatterItem scMatterItem : scMatterItems) {
            SysUserView uv = sysUserService.findById(scMatterItem.getUserId());
            userList.add(uv);
        }

        return userList;
    }

    // 获取填报记录的所有填报对象userId
    public Set<Integer> getItemUserIds(int id) {

        Set<Integer> userIds = new HashSet<>();
        List<ScMatterItem> scMatterItems = getItemList(id);
        for (ScMatterItem scMatterItem : scMatterItems) {
            userIds.add(scMatterItem.getUserId());
        }

        return userIds;
    }

    // 更新填报记录的所有填报对象userId
    @Transactional
    public void updateUserIds(Integer id, Integer[] userIds) {

        {
            ScMatterItemExample example = new ScMatterItemExample();
            ScMatterItemExample.Criteria criteria = example.createCriteria().andMatterIdEqualTo(id);
            if (userIds != null && userIds.length > 0) {
                // 不删除原来存在的填报对象
                criteria.andUserIdNotIn(Arrays.asList(userIds));
            }
            scMatterItemMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.length == 0) return;

        Set<Integer> existUserIdSet = getItemUserIds(id);

        for (Integer userId : userIds) {

            ScMatterItem record = new ScMatterItem();
            record.setMatterId(id);
            record.setUserId(userId);
            Cadre cadre = CmTag.getCadre(userId);
            if(cadre!=null) {
                record.setTitle(cadre.getTitle());
            }
            if(existUserIdSet.contains(userId)){
                scMatterItemMapper.updateByPrimaryKeySelective(record);
            }else{
                scMatterItemMapper.insertSelective(record);
            }
        }

    }
}
