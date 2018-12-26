package service.sc.scLetter;

import controller.global.OpException;
import domain.sc.scLetter.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScLetterService extends ScBaseMapper {

    // 个人事项核查[2018]01号
    public int genNum(int year, int type){

        int num ;
        ScLetterExample example = new ScLetterExample();
        example.createCriteria().andYearEqualTo(year).andTypeEqualTo(type);
        example.setOrderByClause("num desc");
        List<ScLetter> scLetters = scLetterMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(scLetters.size()>0){
            num = scLetters.get(0).getNum() + 1;
        }else{
            num = 1;
        }

        return num;
    }

    public boolean idDuplicate(Integer id, int year, int type, int num){

        ScLetterExample example = new ScLetterExample();
        ScLetterExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year)
                .andTypeEqualTo(type).andNumEqualTo(num);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scLetterMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScLetter record, Integer[] userIds){

        if(record.getNum()==null)
            record.setNum(genNum(record.getYear(), record.getType()));
        record.setIsDeleted(false);

        if(idDuplicate(record.getId(), record.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterMapper.insertSelective(record);

        updateUserIds(record.getId(), userIds);

    }

    @Transactional
    public void del(Integer id){

        scLetterMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScLetterExample example = new ScLetterExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScLetter record = new ScLetter();
        record.setIsDeleted(true);
        scLetterMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScLetter record, Integer[] userIds){

        if(idDuplicate(record.getId(), record.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterMapper.updateByPrimaryKeySelective(record);

        updateUserIds(record.getId(), userIds);
    }

    // 获取所有函询对象
    public List<ScLetterItemView> getItemList(int id) {

        ScLetterItemViewExample example = new ScLetterItemViewExample();
        example.createCriteria().andLetterIdEqualTo(id);
        example.setOrderByClause("id asc");

        return scLetterItemViewMapper.selectByExample(example);
    }

    // 获取所有函询对象userId
    public Set<Integer> getItemUserIds(int id) {

        Set<Integer> userIds = new HashSet<>();
        List<ScLetterItemView> scLetterItems = getItemList(id);
        for (ScLetterItemView scLetterItem : scLetterItems) {
            userIds.add(scLetterItem.getUserId());
        }

        return userIds;
    }

    // 更新函询对象列表userId
    @Transactional
    public void updateUserIds(Integer id, Integer[] userIds) {

        {
            ScLetterItemExample example = new ScLetterItemExample();
            ScLetterItemExample.Criteria criteria = example.createCriteria().andLetterIdEqualTo(id);
            if (userIds != null && userIds.length > 0) {
                // 不删除原来存在的函询对象
                criteria.andUserIdNotIn(Arrays.asList(userIds));
            }
            scLetterItemMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.length == 0) return;

        Set<Integer> existUserIdSet = getItemUserIds(id);

        for (Integer userId : userIds) {

            ScLetterItem record = new ScLetterItem();
            record.setLetterId(id);
            record.setUserId(userId);
            if(existUserIdSet.contains(userId)){
                scLetterItemMapper.updateByPrimaryKeySelective(record);
            }else{
                scLetterItemMapper.insertSelective(record);
            }
        }

    }
}
