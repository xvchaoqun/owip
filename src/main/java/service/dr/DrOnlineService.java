package service.dr;

import domain.dr.*;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.DrConstants;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class DrOnlineService extends DrBaseMapper {

    @Autowired
    private DrOnlinePostService drOnlinePostService;

    public boolean idDuplicate(Integer id, Integer seq){

        Assert.isTrue(seq != null, "null");

        DrOnlineExample example = new DrOnlineExample();
        DrOnlineExample.Criteria criteria = example.createCriteria().andSeqEqualTo(seq);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlineMapper.countByExample(example) > 0;
    }

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

    public Map<Integer, SysUserView> getUser(){

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andTypeEqualTo(SystemConstants.USER_TYPE_JZG);
        List<SysUserView> userViews = sysUserViewMapper.selectByExample(example);

        Map<Integer, SysUserView> userViewMap = new HashMap<>();
        for (SysUserView sysUserView : userViews){
            userViewMap.put(sysUserView.getId(),sysUserView);
        }

        return userViewMap;
    }
}
