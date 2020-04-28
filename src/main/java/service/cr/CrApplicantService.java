package service.cr;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreEva;
import domain.cr.CrApplicant;
import domain.cr.CrApplicantExample;
import domain.cr.CrInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.cadre.CadreEvaService;
import service.cadre.CadreService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrApplicantService extends CrBaseMapper {

    @Autowired
    protected CadreEvaService cadreEvaService;
    @Autowired
    protected CadreService cadreService;

    public Map<Integer, CrInfo> hasApplyInfoMap(int userId) {

        List<CrInfo> crInfos = iCrMapper.hasApplyInfos(userId);
        Map<Integer, CrInfo> crInfoMap = new HashMap<>();
        for (CrInfo crInfo : crInfos) {
            crInfoMap.put(crInfo.getId(), crInfo);
        }

        return crInfoMap;
    }

    public CrApplicant get(int userId, int infoId) {

        CrApplicantExample example = new CrApplicantExample();
        example.createCriteria().andUserIdEqualTo(userId).andInfoIdEqualTo(infoId);
        List<CrApplicant> crApplicants = crApplicantMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (crApplicants.size() > 0) ? crApplicants.get(0) : null;
    }

    public String getEva(int infoId, int userId) {

        CrInfo crInfo = crInfoMapper.selectByPrimaryKey(infoId);
        CrApplicant crApplicant = get(userId, infoId);
        Cadre cadre = cadreService.getByUserId(crApplicant.getUserId());

        return getEva(crInfo.getYear(), cadre, crApplicant);
    }

    public String getEva(int year, Cadre cadre, CrApplicant crApplicant) {

        String eva = "";
        if (cadre != null && cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE) {
            int cadreId = cadre.getId();
            CadreEva cadreEva_3 = cadreEvaService.get(cadreId, year - 3);
            CadreEva cadreEva_2 = cadreEvaService.get(cadreId, year - 2);
            CadreEva cadreEva_1 = cadreEvaService.get(cadreId, year - 1);
            eva = String.format("%s,%s,%s", cadreEva_3 == null ? "" : cadreEva_3.getType()
                    , cadreEva_2 == null ? "" : cadreEva_2.getType()
                    , cadreEva_1 == null ? "" : cadreEva_1.getType());
        } else if(crApplicant!=null){
            eva = crApplicant.getEva();
        }

        return eva;
    }

    @Transactional
    public void insertSelective(CrApplicant record) {

        record.setSortOrder(getNextSortOrder("cr_applicant", "info_id=" + record.getInfoId()));

        crApplicantMapper.insertSelective(record);

        if (BooleanUtils.isTrue(record.getHasSubmit())) {
            refreshInfoNum(record.getInfoId());
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrApplicant record) {

        crApplicantMapper.updateByPrimaryKeySelective(record);

        if (BooleanUtils.isTrue(record.getHasSubmit())) {
            refreshInfoNum(record.getInfoId());
        }
    }

    // 更新应聘人数
    public void refreshInfoNum(int infoId) {

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
        if (!ShiroHelper.isPermitted("crInfo:*")) {
            if (crApplicant != null && BooleanUtils.isTrue(crApplicant.getHasSubmit())) {
                throw new OpException("已经提交，无法修改。");
            }
        }

        if (crApplicant != null) {
            record.setId(crApplicant.getId());
            crApplicantMapper.updateByPrimaryKeySelective(record);

            if (BooleanUtils.isTrue(record.getHasSubmit())) {
                refreshInfoNum(record.getInfoId());
            }

            if (record.getSecondPostId() == null) {
                commonMapper.excuteSql("update cr_applicant set second_post_id=null where id=" + crApplicant.getId());
            }
        } else {

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
