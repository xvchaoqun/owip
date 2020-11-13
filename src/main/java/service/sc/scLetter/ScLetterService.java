package service.sc.scLetter;

import controller.global.OpException;
import domain.sc.scLetter.ScLetter;
import domain.sc.scLetter.ScLetterExample;
import domain.sc.scLetter.ScLetterItem;
import domain.sc.scLetter.ScLetterItemExample;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

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
                .andTypeEqualTo(type).andNumEqualTo(num).andIsDeletedEqualTo(false);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scLetterMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScLetter record, List<ScLetterUser> scLetterUsers){

        if(record.getNum()==null)
            record.setNum(genNum(record.getYear(), record.getType()));
        record.setIsDeleted(false);

        if(idDuplicate(record.getId(), record.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterMapper.insertSelective(record);

        updateUsers(record.getId(), scLetterUsers);

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
    public void updateByPrimaryKeySelective(ScLetter record, List<ScLetterUser> scLetterUsers){

        if(idDuplicate(record.getId(), record.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterMapper.updateByPrimaryKeySelective(record);

        updateUsers(record.getId(), scLetterUsers);
    }

    // 获取所有函询对象
    public List<ScLetterUser> getItemList(int letterId) {

        List<ScLetterUser> scLetterUsers = new ArrayList<>();
        ScLetterItemExample example = new ScLetterItemExample();
        example.createCriteria().andLetterIdEqualTo(letterId);
        example.setOrderByClause("id asc");

        List<ScLetterItem> scLetterItems = scLetterItemMapper.selectByExample(example);
        for (ScLetterItem scLetterItem : scLetterItems) {
            SysUserView uv = CmTag.getUserById(scLetterItem.getUserId());

            ScLetterUser record = new ScLetterUser();
            try {
                PropertyUtils.copyProperties(record, scLetterItem);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            record.setRealname(uv.getRealname());
            record.setCode(uv.getCode());
            if(scLetterItem.getRecordId()!=null) {
                record.setScRecordCode(iScMapper.getScRecordView(scLetterItem.getRecordId()).getCode());
            }
            if(StringUtils.isNotBlank(scLetterItem.getRecordIds())) {
                List<String> scRecordCodes = new ArrayList<>();
                for (Integer recordId : NumberUtils.toIntSet(scLetterItem.getRecordIds(), ",")) {
                    scRecordCodes.add(iScMapper.getScRecordView(recordId).getCode());
                }
                record.setScRecordCodes(StringUtils.join(scRecordCodes, ","));
            }
            scLetterUsers.add(record);
        }

        return scLetterUsers;
    }

    // 获取所有函询对象userId
    public Set<Integer> getItemUserIds(int id) {

        Set<Integer> userIds = new HashSet<>();
        List<ScLetterUser> records = getItemList(id);
        for (ScLetterUser scLetterUser : records) {
            userIds.add(scLetterUser.getUserId());
        }

        return userIds;
    }

    // 更新函询对象列表
    @Transactional
    public void updateUsers(Integer id, List<ScLetterUser> scLetterUsers) {

        List<Integer> userIds= scLetterUsers.stream().map(ScLetterUser::getUserId).collect(Collectors.toList());
        {
            ScLetterItemExample example = new ScLetterItemExample();
            ScLetterItemExample.Criteria criteria = example.createCriteria().andLetterIdEqualTo(id);
            if (userIds != null && userIds.size() > 0) {
                // 不删除原来存在的函询对象
                criteria.andUserIdNotIn(userIds);
            }
            scLetterItemMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.size() == 0) return;

        Set<Integer> existUserIdSet = getItemUserIds(id);

        for (ScLetterUser scLetterUser : scLetterUsers) {

            int userId = scLetterUser.getUserId();
            ScLetterItem record = new ScLetterItem();
            record.setRecordId(scLetterUser.getRecordId());

            if(existUserIdSet.contains(userId)){
                if(scLetterUser.getRecordId()!=null) {
                    ScLetterItemExample example = new ScLetterItemExample();
                    example.createCriteria().andLetterIdEqualTo(id).andUserIdEqualTo(userId);
                    scLetterItemMapper.updateByExampleSelective(record, example);
                }else{
                    commonMapper.excuteSql(String.format("update sc_letter_item set record_id=null " +
                            "where letter_id=%s and user_id=%s", id, userId));
                }
            }else{
                record.setLetterId(id);
                record.setUserId(userId);
                record.setRecordUserId(ShiroHelper.getCurrentUserId());
                scLetterItemMapper.insertSelective(record);
            }
        }

    }
}
