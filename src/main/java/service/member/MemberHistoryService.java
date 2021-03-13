package service.member;

import controller.global.OpException;
import domain.member.MemberHistory;
import domain.member.MemberHistoryExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberHistoryService extends MemberBaseMapper {

    public boolean isDuplicate(String realname, String code){

        MemberHistoryExample example = new MemberHistoryExample();
        example.createCriteria().andRealnameEqualTo(realname).andCodeEqualTo(code);

        return memberHistoryMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(MemberHistory record){
        record.setUserId(ShiroHelper.getCurrentUserId());
        memberHistoryMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            MemberHistory record = memberHistoryMapper.selectByPrimaryKey(id);
            if ((ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&record.getUserId().equals(ShiroHelper.getCurrentUserId()))
                    ||ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)){
                memberHistoryMapper.deleteByPrimaryKey(id);
            }else {
                throw new OpException("没有权限删除"+record.getRealname()+"("+record.getCode()+")");
            }
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberHistory record){
        if (!record.getUserId().equals(ShiroHelper.getCurrentUserId())&&!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL))
            throw new OpException("没有权限修改其他分党委下的历史党员"+record.getRealname()+"("+record.getCode()+")");
        memberHistoryMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, MemberHistory> findAll() {

        MemberHistoryExample example = new MemberHistoryExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<MemberHistory> records = memberHistoryMapper.selectByExample(example);
        Map<Integer, MemberHistory> map = new LinkedHashMap<>();
        for (MemberHistory record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public int bacthImport(List<MemberHistory> records) {

        int count = 0;
        if (records==null||records.size()==0) return 0;
        //检查权限
        checkAuth(records.get(0));

        for (MemberHistory record : records) {

            if (isDuplicate(record.getRealname(),record.getCode())){

                MemberHistoryExample example = new MemberHistoryExample();
                example.createCriteria().andRealnameEqualTo(record.getRealname()).andCodeEqualTo(record.getCode());
                MemberHistory memberHistory = memberHistoryMapper.selectByExample(example).get(0);
                record.setId(memberHistory.getId());
                record.setUserId(memberHistory.getUserId());
                updateByPrimaryKeySelective(record);
            }else {
                insertSelective(record);
                count++;
            }
        }
        return count;
    }

    public void checkAuth(MemberHistory record){
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)&&!ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)){
           throw new UnauthorizedException();
        }
    }
}
