package service.sc.scMatter;

import domain.sc.scMatter.ScMatterCheck;
import domain.sc.scMatter.ScMatterCheckExample;
import domain.sc.scMatter.ScMatterCheckItem;
import domain.sc.scMatter.ScMatterCheckItemExample;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScMatterCheckService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    // 个人事项核查[2018]01号
    public int genNum(int year){

        int num ;
        ScMatterCheckExample example = new ScMatterCheckExample();
        example.createCriteria().andYearEqualTo(year);
        example.setOrderByClause("num desc");
        List<ScMatterCheck> scMatterChecks = scMatterCheckMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(scMatterChecks.size()>0){
            num = scMatterChecks.get(0).getNum() + 1;
        }else{
            num = 1;
        }

        return num;
    }

    @Transactional
    public void insertSelective(ScMatterCheck record, Integer[] userIds){

        record.setNum(genNum(record.getYear()));
        record.setIsDeleted(false);
        scMatterCheckMapper.insertSelective(record);

        updateUserIds(record.getId(), userIds);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterCheckExample example = new ScMatterCheckExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ScMatterCheck record = new ScMatterCheck();
        record.setIsDeleted(true);
        scMatterCheckMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScMatterCheck record, Integer[] userIds){

        scMatterCheckMapper.updateByPrimaryKeySelective(record);

        updateUserIds(record.getId(), userIds);
    }

    // 获取填报记录的所有填报对象
    public List<ScMatterCheckItem> getItemList(int id) {

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        example.createCriteria().andCheckIdEqualTo(id);
        example.setOrderByClause("id asc");

        return scMatterCheckItemMapper.selectByExample(example);
    }

    // 获取填报记录的所有填报对象
    public List<SysUserView> getItemUserList(int id) {

        List<ScMatterCheckItem> scMatterCheckItems = getItemList(id);
        List<SysUserView> userList = new ArrayList<>();
        for (ScMatterCheckItem scMatterCheckItem : scMatterCheckItems) {
            SysUserView uv = sysUserService.findById(scMatterCheckItem.getUserId());
            userList.add(uv);
        }

        return userList;
    }

    // 获取填报记录的所有填报对象userId
    public Set<Integer> getItemUserIds(int id) {

        Set<Integer> userIds = new HashSet<>();
        List<ScMatterCheckItem> scMatterCheckItems = getItemList(id);
        for (ScMatterCheckItem scMatterCheckItem : scMatterCheckItems) {
            userIds.add(scMatterCheckItem.getUserId());
        }

        return userIds;
    }

    // 更新填报记录的所有填报对象userId
    @Transactional
    public void updateUserIds(Integer id, Integer[] userIds) {

        {
            ScMatterCheckItemExample example = new ScMatterCheckItemExample();
            ScMatterCheckItemExample.Criteria criteria = example.createCriteria().andCheckIdEqualTo(id);
            if (userIds != null && userIds.length > 0) {
                // 不删除原来存在的填报对象
                criteria.andUserIdNotIn(Arrays.asList(userIds));
            }
            scMatterCheckItemMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.length == 0) return;

        Set<Integer> existUserIdSet = getItemUserIds(id);

        for (Integer userId : userIds) {

            ScMatterCheckItem record = new ScMatterCheckItem();
            record.setCheckId(id);
            record.setUserId(userId);
            if(existUserIdSet.contains(userId)){
                scMatterCheckItemMapper.updateByPrimaryKeySelective(record);
            }else{
                scMatterCheckItemMapper.insertSelective(record);
            }
        }

    }
}
