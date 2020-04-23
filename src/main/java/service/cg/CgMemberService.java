package service.cg;

import domain.cadre.CadreView;
import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import domain.unit.UnitPostView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CgMemberService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, int teamId, int userId, boolean isCurrent){

        if(!isCurrent) return false;

        CgMemberExample example = new CgMemberExample();
        CgMemberExample.Criteria criteria = example.createCriteria().
                andTeamIdEqualTo(teamId).andUserIdEqualTo(userId)
                .andIsCurrentEqualTo(isCurrent);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgMember record){

        record.setSortOrder(getNextSortOrder("cg_member",
                String.format("is_current=%s and team_id=%s",record.getIsCurrent(),record.getTeamId())));
        cgMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgMemberExample example = new CgMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgMemberMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgMember record){

        //Assert.isTrue(!idDuplicate(record.getId(), record.getTeamId(), record.getUserId(), record.getIsCurrent()), "duplicate");
        cgMemberMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        CgMember cgMember = cgMemberMapper.selectByPrimaryKey(id);
        changeOrder("cg_member", String.format("is_current=%s and team_id=%s",cgMember.getIsCurrent(),cgMember.getTeamId()), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateMemberState(Integer[] ids, boolean isCurrent) {
        for (Integer id : ids){

            CgMember cgMember = cgMemberMapper.selectByPrimaryKey(id);

            CgMember record = new CgMember();
            record.setId(id);
            record.setIsCurrent(isCurrent);
            record.setNeedAdjust(false);
            record.setSortOrder(getNextSortOrder("cg_member",
                    String.format("is_current=%s and team_id=%s",record.getIsCurrent(),cgMember.getTeamId())));
            cgMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void updateNeedAdjust(){
        List<Integer> cgMemberIds = iCgMapper.getNeedAdjustMember(null);

        if (cgMemberIds.size() == 0) return;

        for (Integer cgMemberId : cgMemberIds){

            CgMember cgMember = new CgMember();
            cgMember.setId(cgMemberId);
            cgMember.setNeedAdjust(true);

            updateByPrimaryKeySelective(cgMember);
        }
    }

    //更新席位制变动人员
    @Transactional
    public void updateUser(Integer[] ids){

        for (Integer id : ids) {

            CgMember cgMember = cgMemberMapper.selectByPrimaryKey(id);

            CadreView cadreView = getCadreByUnitPostId(cgMember.getUnitPostId());

            //插入最新现任干部信息
            if (cadreView == null) {//没有现任干部
                cgMember.setUserId(null);
            }else {
                cgMember.setUserId(cadreView.getUserId());
            }
            cgMember.setId(null);
            cgMember.setNeedAdjust(false);
            insertSelective(cgMember);
        }
        //将需要调整的更新为已撤销。
        updateMemberState(ids,false);
    }

    //得到岗位现任干部
    public CadreView getCadreByUnitPostId(Integer unitPostId){

        if (unitPostId==null) return null;

        UnitPostView unitPostView = iUnitMapper.getUnitPost(unitPostId);
        return unitPostView.getCadre();
    }

    //获取原席位制干部和现任干部（用于更新席位制时的页面显示）
    public List<Map> getOldAndNewUser(List<Integer> ids){

        List<Map> userList = new ArrayList<>();
        for (Integer id : ids) {

            Map map = new HashMap();
            CgMember cgMember = cgMemberMapper.selectByPrimaryKey(id);
            CadreView cadreView = getCadreByUnitPostId(cgMember.getUnitPostId());

            map.put("id",cgMember.getId());
            map.put("seat",cgMember.getSeat());

            if (cgMember.getCgTeam()!=null){
                map.put("cgTeamName",cgMember.getCgTeam().getName());
            }
            if (cgMember.getUnitPost()!=null){
                map.put("unitPostName",cgMember.getUnitPost().getName());
            }
            if (cgMember.getUser()!=null){
                map.put("oldRealname",cgMember.getUser().getRealname());
            }
            if (cadreView!=null && cadreView.getUser()!=null){
                map.put("newRealname",cadreView.getUser().getRealname());
            }
            userList.add(map);
        }
        return userList;
    }
}
