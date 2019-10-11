package service.cr;

import controller.global.OpException;
import domain.cr.CrApplicant;
import domain.cr.CrApplicantExample;
import domain.cr.CrInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;

import java.util.*;

@Service
public class CrApplicantService extends CrBaseMapper {

    public Map<Integer, CrInfo> hasApplyInfoMap(int userId){

        List<CrInfo> crInfos = iCrMapper.hasApplyInfos(userId);
        Map<Integer, CrInfo> crInfoMap = new HashMap<>();
        for (CrInfo crInfo : crInfos) {
            crInfoMap.put(crInfo.getId(), crInfo);
        }

        return crInfoMap;
    }

    public CrApplicant get(int userId, int infoId){

        CrApplicantExample example = new CrApplicantExample();
        example.createCriteria().andUserIdEqualTo(userId).andInfoIdEqualTo(infoId);
        List<CrApplicant> crApplicants = crApplicantMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (crApplicants.size()>0)?crApplicants.get(0):null;
    }

    @Transactional
    public void insertSelective(CrApplicant record) {

        record.setSortOrder(getNextSortOrder("cr_applicant", "info_id=" + record.getInfoId()));

        crApplicantMapper.insertSelective(record);

        if(BooleanUtils.isTrue(record.getHasSubmit())) {
            refreshInfoNum(record.getInfoId());
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrApplicant record) {

        crApplicantMapper.updateByPrimaryKeySelective(record);

        if(BooleanUtils.isTrue(record.getHasSubmit())) {
            refreshInfoNum(record.getInfoId());
        }
    }

    // 更新应聘人数
    public void refreshInfoNum(int infoId){

        CrApplicantExample example = new CrApplicantExample();
        example.createCriteria().andInfoIdEqualTo(infoId).andHasSubmitEqualTo(true);
        int num = (int) crApplicantMapper.countByExample(example);

        CrInfo record = new CrInfo();
        record.setId(infoId);
        record.setApplyNum(num);

        crInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int infoId) {

        if (ids == null || ids.length == 0) return;

        CrApplicantExample example = new CrApplicantExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crApplicantMapper.deleteByExample(example);

        refreshInfoNum(infoId);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CrApplicant entity = crApplicantMapper.selectByPrimaryKey(id);
        changeOrder("cr_applicant", "info_id=" + entity.getInfoId(), ORDER_BY_ASC, id, addNum);
    }

    @Transactional
    public void addOrUpdate(CrApplicant record) {

        int userId = record.getUserId();
        int infoId = record.getInfoId();
        CrApplicant crApplicant = get(userId, infoId);
        if(!ShiroHelper.isPermitted("crInfo:*")){
            if(crApplicant!=null && BooleanUtils.isTrue(crApplicant.getHasSubmit())){
                throw new OpException("已经提交，无法修改。");
            }
        }

        if(crApplicant!=null){
            record.setId(crApplicant.getId());
            crApplicantMapper.updateByPrimaryKeySelective(record);

            if(BooleanUtils.isTrue(record.getHasSubmit())) {
                refreshInfoNum(record.getInfoId());
            }

            if(record.getSecondPostId()==null){
                commonMapper.excuteSql("update cr_applicant set second_post_id=null where id="+ crApplicant.getId());
            }
        }else{

            insertSelective(record);
        }
    }

    @Transactional
    public void report(Integer[] ids, int infoId, boolean hasReport) {

        CrApplicantExample example = new CrApplicantExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andInfoIdEqualTo(infoId);

        CrApplicant record = new CrApplicant();
        record.setHasReport(hasReport);
        crApplicantMapper.updateByExampleSelective(record, example);
    }
}
