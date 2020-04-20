package service.dr;

import domain.dr.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineService extends DrBaseMapper {

    @Autowired
    private DrOnlinePostService drOnlinePostService;

    @Transactional
    public void insertSelective(DrOnline record){

        record.setStatus(DrConstants.DR_ONLINE_NOT_RELEASE);
        record.setSeq(getNextSeq(record.getRecommendDate()));
        drOnlineMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOnlineMapper.deleteByPrimaryKey(id);
    }

    //假删除
    @Transactional
    public void missDel(Integer[] ids, Integer isDeleted){

        if(ids==null || ids.length==0) return;

        //假删除
        for (Integer id : ids){
            DrOnline record = new DrOnline();
            record.setId(id);
            record.setIsDeleteed(isDeleted == 1 ? true : false);
            drOnlineMapper.updateByPrimaryKeySelective(record);
        }
    }

    /*
        删除候选人
        删除结果
        删除岗位
        删除日志
        删除参评人
        删除批次
    * */
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        //删除后相关信息全部删除
        for (Integer id : ids){
            List<DrOnlinePostView> posts = drOnlinePostService.getAllByOnlineId(id);
            if (null != posts && posts.size() > 0) {
                for (DrOnlinePostView post : posts) {
                    DrOnlineCandidateExample candidateExample = new DrOnlineCandidateExample();
                    candidateExample.createCriteria().andPostIdEqualTo(post.getId());
                    drOnlineCandidateMapper.deleteByExample(candidateExample);
                }
            }

            DrOnlineResultExample resultExample = new DrOnlineResultExample();
            resultExample.createCriteria().andOnlineIdEqualTo(id);
            drOnlineResultMapper.deleteByExample(resultExample);

            DrOnlinePostExample postExample = new DrOnlinePostExample();
            postExample.createCriteria().andOnlineIdEqualTo(id);
            drOnlinePostMapper.deleteByExample(postExample);

            DrOnlineInspectorLogExample logExample = new DrOnlineInspectorLogExample();
            logExample.createCriteria().andOnlineIdEqualTo(id);
            drOnlineInspectorLogMapper.deleteByExample(logExample);

            DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
            inspectorExample.createCriteria().andOnlineIdEqualTo(id);
            drOnlineInspectorMapper.deleteByExample(inspectorExample);

        }

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnline record){

        drOnlineMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DrOnline> findAll() {

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DrOnline> records = drOnlineMapper.selectByExample(example);
        Map<Integer, DrOnline> map = new LinkedHashMap<>();
        for (DrOnline record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public int getNextSeq(Date recommendDate) {

        DrOnlineExample example = new DrOnlineExample();
        DrOnlineExample.Criteria criteria = example.createCriteria()
                .andRecommendDateEqualTo(recommendDate);
        example.setOrderByClause("seq desc");

        List<DrOnline> records = drOnlineMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        int seq = 1;
        if(records.size()>0) {
            DrOnline record = records.get(0);
            seq = record.getSeq() + 1;
        }

        return seq;
    }

    @Transactional
    public void changeStatus(Integer[] ids, Byte status){

        if(ids==null || ids.length==0) return;

        DrOnline drOnline = new DrOnline();
        drOnline.setStatus(status);
        for (Integer id : ids){
            drOnline.setId(id);
            drOnlineMapper.updateByPrimaryKeySelective(drOnline);
        }
    }
}
