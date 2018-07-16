package service.sc.scPassport;

import domain.abroad.Passport;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.sc.scPassport.ScPassport;
import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportHandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.abroad.PassportService;
import service.cadre.CadreService;
import sys.constants.AbroadConstants;
import sys.constants.DispatchConstants;
import sys.constants.ScConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ScPassportHandService extends BaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired(required = false)
    private PassportService passportService;

    @Transactional
    public void insertSelective(ScPassportHand record){

        scPassportHandMapper.insertSelective(record);
    }

    // 证件入库
    @Transactional
    public void importPassport(int handId){

        ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(handId);
        List<ScPassport> scPassports = scPassportHand.getScPassports();
        int cadreId = scPassportHand.getCadreId();
        Map<Integer, Passport> passportMap = passportService.findByCadreId(cadreId);

        if(scPassportHand.getStatus()==ScConstants.SC_PASSPORTHAND_STATUS_UNHAND && scPassports.size() ==3){

            for (ScPassport scPassport : scPassports) {

                if(scPassport.getIsExist()){
                    int classId = scPassport.getClassId();
                    Passport passport = passportMap.get(classId);
                    if(passport==null){

                        passport = new Passport();
                        passport.setCadreId(cadreId);
                        passport.setClassId(classId);
                        passport.setCode(scPassport.getCode());
                        passport.setAuthority(scPassport.getAuthority());
                        passport.setIssueDate(scPassport.getIssueDate());
                        passport.setExpiryDate(scPassport.getExpiryDate());
                        passport.setKeepDate(scPassport.getKeepDate());
                        passport.setSafeBoxId(scPassport.getSafeBoxId());
                        passport.setPic(scPassport.getPic());
                        passport.setIsLent(false);
                        passport.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
                        passport.setCreateTime(new Date());

                        passportService.add(passport, null, null);

                    }else{

                        Passport record = new Passport();
                        record.setId(passport.getId());
                        record.setCadreId(cadreId);
                        record.setClassId(classId);
                        record.setCode(scPassport.getCode());
                        record.setAuthority(scPassport.getAuthority());
                        record.setIssueDate(scPassport.getIssueDate());
                        record.setExpiryDate(scPassport.getExpiryDate());
                        record.setKeepDate(scPassport.getKeepDate());
                        record.setSafeBoxId(scPassport.getSafeBoxId());
                        record.setPic(scPassport.getPic());
                        record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);

                        passportService.updateByPrimaryKeySelective(record);
                    }
                }
            }

            ScPassportHand record = new ScPassportHand();
            record.setId(handId);
            record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_HAND);
            scPassportHandMapper.updateByPrimaryKeySelective(record);
        }
    }
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScPassportHandExample example = new ScPassportHandExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scPassportHandMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScPassportHand record){

        return scPassportHandMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void addCadres(Integer[] cadreIds) {

        Map<Integer, CadreView> cadreMap = cadreService.findAll();

        Date now = new Date();
        for (Integer cadreId : cadreIds) {

            CadreView cv = cadreMap.get(cadreId);

            ScPassportHand record = new ScPassportHand();
            record.setCadreId(cadreId);
            record.setPost(cv.getPost());
            record.setPostId(cv.getPostId());
            record.setTypeId(cv.getTypeId());
            record.setUnitId(cv.getUnitId());

            record.setAddType(ScConstants.SC_PASSPORTHAND_ADDTYPE_CADRE);
            record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);
            record.setAddTime(now);

            insertSelective(record);
        }
    }

    public ScPassportHand getByDispatchCadreId(int dispatchCadreId){

        ScPassportHandExample example = new ScPassportHandExample();
        example.createCriteria().andDispatchCadreIdEqualTo(dispatchCadreId);
        List<ScPassportHand> scPassportHands = scPassportHandMapper.selectByExample(example);

        return (scPassportHands.size()>0)?scPassportHands.get(0):null;
    }

    @Transactional
    public void addDispatchCadres(Integer[] dispatchCadreIds) {

        MetaType dispatchCadreWayPromote = CmTag.getMetaTypeByCode("mt_dispatch_cadre_way_promote");
        Date now = new Date();

        for (Integer dispatchCadreId : dispatchCadreIds) {

            DispatchCadre dispatchCadre = dispatchCadreMapper.selectByPrimaryKey(dispatchCadreId);
            Integer wayId = dispatchCadre.getWayId();
            if(wayId.intValue() != dispatchCadreWayPromote.getId()) continue;

            CadreView cv = dispatchCadre.getCadre();
            ScPassportHand record = new ScPassportHand();
            record.setCadreId(cv.getId());

            record.setAppointDate(dispatchCadre.getDispatch().getWorkTime());
            record.setPost(dispatchCadre.getPost());
            record.setPostId(dispatchCadre.getPostId());
            record.setTypeId(dispatchCadre.getAdminLevelId());
            record.setUnitId(dispatchCadre.getUnitId());

            record.setDispatchCadreId(dispatchCadreId);

            record.setAddType(ScConstants.SC_PASSPORTHAND_ADDTYPE_DISPATCH);
            record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);
            record.setAddTime(now);

            ScPassportHand scPassportHand = getByDispatchCadreId(dispatchCadreId);
            if(scPassportHand==null) {
                insertSelective(record);
            }else{
                record.setId(scPassportHand.getId());
                updateByPrimaryKeySelective(record);
            }
        }
    }

    // 提取
    public List<DispatchCadre> draw(int dispatchId) {

        MetaType dispatchCadreWayPromote = CmTag.getMetaTypeByCode("mt_dispatch_cadre_way_promote");

        DispatchCadreExample example = new DispatchCadreExample();
        example.createCriteria().andDispatchIdEqualTo(dispatchId)
                .andTypeEqualTo(DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT)
                .andWayIdEqualTo(dispatchCadreWayPromote.getId());

        return dispatchCadreMapper.selectByExample(example);
    }

    // 撤销
    @Transactional
    public void abolish(int id, String remark) {

        ScPassportHand record = new ScPassportHand();
        record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_ABOLISH);
        record.setRemark(remark);
        record.setAbolishTime(new Date());

        ScPassportHandExample example = new ScPassportHandExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);
        scPassportHandMapper.updateByExampleSelective(record, example);
    }

    // 取消撤销
    @Transactional
    public void unabolish(int id) {

        ScPassportHand record = new ScPassportHand();
        record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);

        ScPassportHandExample example = new ScPassportHandExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(ScConstants.SC_PASSPORTHAND_STATUS_ABOLISH);
        scPassportHandMapper.updateByExampleSelective(record, example);

        commonMapper.excuteSql("update sc_passport_hand set abolish_time=null, remark=null where id="+ id);
    }

    // 转移至未交证件
    @Transactional
    public void unhand(int id) {

        ScPassportHand record = new ScPassportHand();
        record.setId(id);
        record.setStatus(ScConstants.SC_PASSPORTHAND_STATUS_UNHAND);
        scPassportHandMapper.updateByPrimaryKeySelective(record);
    }
}
