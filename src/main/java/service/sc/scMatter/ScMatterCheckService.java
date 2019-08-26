package service.sc.scMatter;

import controller.global.OpException;
import domain.sc.scMatter.ScMatterCheck;
import domain.sc.scMatter.ScMatterCheckExample;
import domain.sc.scMatter.ScMatterCheckItem;
import domain.sc.scMatter.ScMatterCheckItemExample;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.utils.NumberUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScMatterCheckService extends ScBaseMapper {

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

    public boolean idDuplicate(Integer id, int year, int num){

        ScMatterCheckExample example = new ScMatterCheckExample();
        ScMatterCheckExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year)
                .andNumEqualTo(num);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scMatterCheckMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScMatterCheck record, List<ScMatterCheckUser> scMatterCheckUsers){

        if(record.getNum()==null)
            record.setNum(genNum(record.getYear()));

        record.setIsDeleted(false);

        if(idDuplicate(record.getId(), record.getYear(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scMatterCheckMapper.insertSelective(record);

        updateUserIds(record.getId(), scMatterCheckUsers);
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
    public void updateByPrimaryKeySelective(ScMatterCheck record, List<ScMatterCheckUser> scMatterCheckUsers){

        if(idDuplicate(record.getId(), record.getYear(), record.getNum())){
            throw new OpException("编号重复。");
        }

        scMatterCheckMapper.updateByPrimaryKeySelective(record);

        updateUserIds(record.getId(), scMatterCheckUsers);
    }

    // 获取填报记录的所有填报对象
    public List<ScMatterCheckItem> getItemList(int checkId) {

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        example.createCriteria().andCheckIdEqualTo(checkId);
        example.setOrderByClause("id asc");

        return scMatterCheckItemMapper.selectByExample(example);
    }

    // 获取填报记录的所有填报对象
    public List<ScMatterCheckUser> getItemUserList(int checkId) {

        List<ScMatterCheckItem> scMatterCheckItems = getItemList(checkId);
        List<ScMatterCheckUser> scMatterCheckUsers = new ArrayList<>();

        for (ScMatterCheckItem scMatterCheckItem : scMatterCheckItems) {

            SysUserView uv = sysUserService.findById(scMatterCheckItem.getUserId());

            ScMatterCheckUser record = new ScMatterCheckUser();
            try {
                PropertyUtils.copyProperties(record, scMatterCheckItem);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            record.setRealname(uv.getRealname());
            record.setCode(uv.getCode());
            if(scMatterCheckItem.getRecordId()!=null) {
                record.setScRecordCode(iScMapper.getScRecordView(scMatterCheckItem.getRecordId()).getCode());
            }
            if(StringUtils.isNotBlank(scMatterCheckItem.getRecordIds())) {
                List<String> scRecordCodes = new ArrayList<>();
                for (Integer recordId : NumberUtils.toIntSet(scMatterCheckItem.getRecordIds(), ",")) {
                    scRecordCodes.add(iScMapper.getScRecordView(recordId).getCode());
                }
                record.setScRecordCodes(StringUtils.join(scRecordCodes, ","));
            }
            scMatterCheckUsers.add(record);
        }

        return scMatterCheckUsers;
    }

    // 获取填报记录的所有填报对象userId
    public Set<Integer> getItemUserIds(int checkId) {

        Set<Integer> userIds = new HashSet<>();
        List<ScMatterCheckItem> scMatterCheckItems = getItemList(checkId);
        for (ScMatterCheckItem scMatterCheckItem : scMatterCheckItems) {
            userIds.add(scMatterCheckItem.getUserId());
        }

        return userIds;
    }

    // 更新填报记录的所有填报对象userId
    @Transactional
    public void updateUserIds(Integer checkId, List<ScMatterCheckUser> scMatterCheckUsers) {

        List<Integer> userIds= scMatterCheckUsers.stream().map(ScMatterCheckUser::getUserId).collect(Collectors.toList());
        {
            ScMatterCheckItemExample example = new ScMatterCheckItemExample();
            ScMatterCheckItemExample.Criteria criteria = example.createCriteria().andCheckIdEqualTo(checkId);
            if (userIds != null && userIds.size() > 0) {
                // 不删除原来存在的填报对象
                criteria.andUserIdNotIn(userIds);
            }
            scMatterCheckItemMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.size() == 0) return;

        Set<Integer> existUserIdSet = getItemUserIds(checkId);

        for (ScMatterCheckUser scMatterCheckUser : scMatterCheckUsers) {

            int userId = scMatterCheckUser.getUserId();
            ScMatterCheckItem record = new ScMatterCheckItem();
            record.setRecordId(scMatterCheckUser.getRecordId());

            if(existUserIdSet.contains(userId)){

                if(scMatterCheckUser.getRecordId()!=null) {

                    ScMatterCheckItemExample example = new ScMatterCheckItemExample();
                    example.createCriteria().andCheckIdEqualTo(checkId).andUserIdEqualTo(userId);
                    scMatterCheckItemMapper.updateByExampleSelective(record, example);
                }else{
                    commonMapper.excuteSql(String.format("update sc_matter_check_item set record_id=null " +
                            "where check_id=%s and user_id=%s", checkId, userId));
                }
            }else{
                record.setCheckId(checkId);
                record.setUserId(userId);
                record.setRecordUserId(ShiroHelper.getCurrentUserId());
                scMatterCheckItemMapper.insertSelective(record);
            }
        }

    }
}
