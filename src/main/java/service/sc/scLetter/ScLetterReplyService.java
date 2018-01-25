package service.sc.scLetter;

import controller.global.OpException;
import domain.sc.scLetter.ScLetter;
import domain.sc.scLetter.ScLetterExample;
import domain.sc.scLetter.ScLetterReply;
import domain.sc.scLetter.ScLetterReplyExample;
import domain.sc.scLetter.ScLetterReplyItem;
import domain.sc.scLetter.ScLetterReplyItemExample;
import domain.sc.scLetter.ScLetterReplyItemView;
import domain.sc.scLetter.ScLetterReplyItemViewExample;
import domain.sc.scLetter.ScLetterReplyView;
import domain.sc.scLetter.ScLetterReplyViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScLetterReplyService extends BaseMapper {

    // 师纪监办复[2018]01号
    public int genNum(int letterYear, int type){

        int num ;
        ScLetterReplyViewExample example = new ScLetterReplyViewExample();
        example.createCriteria().andLetterYearEqualTo(letterYear).andTypeEqualTo(type);
        example.setOrderByClause("num desc");
        List<ScLetterReplyView> scLetterReplyViews = scLetterReplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(scLetterReplyViews.size()>0){
            num = scLetterReplyViews.get(0).getNum() + 1;
        }else{
            num = 1;
        }

        return num;
    }

    public boolean idDuplicate(Integer id, int letterYear, int type, int num){

        ScLetterExample example = new ScLetterExample();
        ScLetterExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(letterYear)
                .andTypeEqualTo(type).andNumEqualTo(num);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scLetterMapper.countByExample(example) > 0;
    }

    public List<ScLetterReplyItemView> getScLetterReplyItemView(int replyId){

        ScLetterReplyItemViewExample example = new ScLetterReplyItemViewExample();
        example.createCriteria().andReplyIdEqualTo(replyId);
        example.setOrderByClause("id asc");
        List<ScLetterReplyItemView> scLetterReplyItemViews =
                scLetterReplyItemViewMapper.selectByExample(example);

        return scLetterReplyItemViews;
    }

    public ScLetterReplyItemView getScLetterReplyItemView(int replyId, int userId){

        ScLetterReplyItemViewExample example = new ScLetterReplyItemViewExample();
        example.createCriteria().andReplyIdEqualTo(replyId).andUserIdEqualTo(userId);
        List<ScLetterReplyItemView> scLetterReplyItemViews =
                scLetterReplyItemViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return scLetterReplyItemViews.size()==1?scLetterReplyItemViews.get(0):null;
    }

    @Transactional
    public void insertSelective(ScLetterReply record, List<ScLetterReplyItem> scLetterReplyItems){

        Integer letterId = record.getLetterId();
        ScLetter scLetter = scLetterMapper.selectByPrimaryKey(letterId);
        if(record.getNum()==null)
            record.setNum(genNum(scLetter.getYear(), record.getType()));
        record.setIsDeleted(false);

        if(idDuplicate(record.getId(), scLetter.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterReplyMapper.insertSelective(record);

        updateReplyItems(record.getId(), scLetterReplyItems);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScLetterReplyExample example = new ScLetterReplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScLetterReply record = new ScLetterReply();
        record.setIsDeleted(true);
        scLetterReplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScLetterReply record, List<ScLetterReplyItem> scLetterReplyItems){

        Integer letterId = record.getLetterId();
        ScLetter scLetter = scLetterMapper.selectByPrimaryKey(letterId);
        if(idDuplicate(record.getId(), scLetter.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scLetterReplyMapper.updateByPrimaryKeySelective(record);

        updateReplyItems(record.getId(), scLetterReplyItems);
    }

    // 更新回复情况
    @Transactional
    public void updateReplyItems(Integer replyId, List<ScLetterReplyItem> scLetterReplyItems) {

        {
            ScLetterReplyItemExample example = new ScLetterReplyItemExample();
            ScLetterReplyItemExample.Criteria criteria = example.createCriteria().andReplyIdEqualTo(replyId);

            scLetterReplyItemMapper.deleteByExample(example);
        }
        if (scLetterReplyItems == null || scLetterReplyItems.size() == 0) return;

        for (ScLetterReplyItem record : scLetterReplyItems) {

            record.setReplyId(replyId);
            scLetterReplyItemMapper.insertSelective(record);
        }
    }
}
