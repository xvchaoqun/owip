package service.cet;

import bean.XlsUpload;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CetConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.util.*;

@Service
public class CetTrainInspectorService extends CetBaseMapper {

    @Autowired
    private CetTrainService cetTrainService;

    @Transactional
    public void delAbolished(Integer id) {

        {
            CetTrainInspectorCourseExample example = new CetTrainInspectorCourseExample();
            example.createCriteria().andInspectorIdEqualTo(id);
            cetTrainInspectorCourseMapper.deleteByExample(example);
        }

        CetTrainInspectorExample example = new CetTrainInspectorExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH);

        cetTrainInspectorMapper.deleteByPrimaryKey(id);
    }

    // 彻底删除
    @Transactional
    public void batchDel(int trainId, Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainInspectorExample example = new CetTrainInspectorExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andIdIn(Arrays.asList(ids));
        cetTrainInspectorMapper.deleteByExample(example);

        CetTrainInspectorExample _example = new CetTrainInspectorExample();
        _example.createCriteria().andTrainIdEqualTo(trainId);
        int count = (int) cetTrainInspectorMapper.countByExample(_example);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEvaCount(count);
        cetTrainMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @param trainId
     * @param type       1列表生成 2个别生成
     * @param evaAnonymous 是否匿名测评
     * @param totalCount isAnonymous=true, 匿名测评账号数量
     * @param xlsx isAnonymous=false, 实名测评名单
     */
    @Transactional
    public void generateInspector(int trainId, byte type,
                                  boolean evaAnonymous,
                                  Integer totalCount, MultipartFile xlsx) throws IOException, InvalidFormatException {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        int evaCount = cetTrain.getEvaCount()==null?0:cetTrain.getEvaCount();
        if(evaCount>0) // 第一次生成时，已经确定了是匿名还是实名测评，后面不允许改变
            evaAnonymous = cetTrain.getEvaAnonymous();

        int closed = cetTrainService.evaIsClosed(trainId);
        if (closed == 1) {
            throw new OpException("评课已关闭。");
        } else if (closed == 3) {
            throw new OpException("评课已结束于" + DateUtils.formatDate(cetTrain.getEvaCloseTime(), DateUtils.YYYY_MM_DD_HH_MM));
        }

        Date now = new Date();

        if(evaAnonymous) {
            int newCount = totalCount;

            if (type == 1) { // 列表生成，目前都是默认此类别
                newCount = totalCount - evaCount > 0 ? totalCount - evaCount : 0;
            }

            if (newCount > 0) {
                for (int i = 0; i < newCount; i++) {

                    CetTrainInspector record = new CetTrainInspector();
                    record.setTrainId(trainId);
                    record.setUsername(buildUsername());
                    record.setPasswd(RandomStringUtils.randomNumeric(6));
                    record.setType(type);
                    record.setStatus(CetConstants.CET_TRAIN_INSPECTOR_STATUS_INIT);
                    record.setCreateTime(now);

                    cetTrainInspectorMapper.insert(record);
                }
            }
        }else{

            OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

            for (Map<Integer, String> xlsRow : xlsRows) {

                String mobile = StringUtils.trimToNull(xlsRow.get(0));
                if(StringUtils.isBlank(mobile)) continue;

                String realname = StringUtils.trimToNull(xlsRow.get(1));
                if(StringUtils.isBlank(realname)) continue;

                CetTrainInspector cetTrainInspector = get(trainId, mobile);
                if(cetTrainInspector!=null){
                    // 手机号存在则进行更新操作
                    CetTrainInspector _record = new CetTrainInspector();
                    _record.setId(cetTrainInspector.getId());
                    _record.setRealname(realname);
                    cetTrainInspectorMapper.updateByPrimaryKeySelective(_record);
                }else{

                    CetTrainInspector _record = new CetTrainInspector();
                    _record.setTrainId(trainId);
                    _record.setUsername(buildUsername());
                    _record.setPasswd(RandomStringUtils.randomNumeric(6));
                    _record.setMobile(mobile);
                    _record.setRealname(realname);
                    _record.setType(type);
                    _record.setStatus(CetConstants.CET_TRAIN_INSPECTOR_STATUS_INIT);
                    _record.setCreateTime(now);

                    cetTrainInspectorMapper.insert(_record);
                }
            }
        }

        CetTrainInspectorExample _example = new CetTrainInspectorExample();
        _example.createCriteria().andTrainIdEqualTo(trainId).andStatusNotEqualTo(CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH);
        int count = (int) cetTrainInspectorMapper.countByExample(_example);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEvaCount(count);
        record.setEvaAnonymous(evaAnonymous);
        cetTrainMapper.updateByPrimaryKeySelective(record);
    }

    // 校内培训，生成评课账号
    @Transactional
    public void generateInspectorOnCampus(int trainId) throws IOException, InvalidFormatException {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        int closed = cetTrainService.evaIsClosed(trainId);
        if (closed == 1) {
            throw new OpException("评课已关闭。");
        } else if (closed == 3) {
            throw new OpException("评课已结束于" + DateUtils.formatDate(cetTrain.getEvaCloseTime(), DateUtils.YYYY_MM_DD_HH_MM));
        }

        // 读取已选课人员
        CetTraineeViewExample example = new CetTraineeViewExample();
        example.createCriteria().andTrainIdEqualTo(trainId)
                .andCourseCountGreaterThan(0);
        List<CetTraineeView> cetTraineeViews = cetTraineeViewMapper.selectByExample(example);

        Date now = new Date();
        for (CetTraineeView ctee : cetTraineeViews) {

            Integer userId = ctee.getUserId();
            CadreView cv = CmTag.getCadreByUserId(userId);
            // 使用工作证号当做账号
            String mobile = cv.getCode();
            String realname = cv.getRealname();
            CetTrainInspector cetTrainInspector = get(trainId, mobile);
            if (cetTrainInspector != null) {
                // 存在则进行更新操作
                CetTrainInspector record = new CetTrainInspector();
                record.setId(cetTrainInspector.getId());
                record.setRealname(realname);
                cetTrainInspectorMapper.updateByPrimaryKeySelective(record);
            } else {

                CetTrainInspector record = new CetTrainInspector();
                record.setTrainId(trainId);
                record.setUsername(buildUsername());
                record.setPasswd(RandomStringUtils.randomNumeric(6));
                record.setMobile(mobile);
                record.setRealname(realname);
                record.setType((byte)1);
                record.setStatus(CetConstants.CET_TRAIN_INSPECTOR_STATUS_INIT);
                record.setCreateTime(now);

                cetTrainInspectorMapper.insert(record);
            }
        }

        CetTrainInspectorExample _example = new CetTrainInspectorExample();
        _example.createCriteria().andTrainIdEqualTo(trainId);
        int count = (int) cetTrainInspectorMapper.countByExample(_example);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEvaCount(count);
        record.setEvaAnonymous(false);
        cetTrainMapper.updateByPrimaryKeySelective(record);
    }

    public String buildUsername() {

        //String username = RandomStringUtils.randomAlphabetic(6).toLowerCase() + RandomStringUtils.randomNumeric(2);
        String username = RandomStringUtils.random(6, "abcdefghijkmnpqrstuvwxy")
                + RandomStringUtils.random(2, "23456789");
        CetTrainInspectorExample example = new CetTrainInspectorExample();
        example.createCriteria().andUsernameEqualTo(username);
        while (cetTrainInspectorMapper.countByExample(example) > 0) {

            //username = RandomStringUtils.randomAlphabetic(6).toLowerCase() + RandomStringUtils.randomNumeric(2);
            username = RandomStringUtils.random(6, "abcdefghijkmnpqrstuvwxy")
                    + RandomStringUtils.random(2, "23456789");
        }

        return username;
    }

    @Transactional
    public void abolish(int inspectorId) {

        CetTrainInspector inspector = cetTrainInspectorMapper.selectByPrimaryKey(inspectorId);

        CetTrainInspector _inspector = new CetTrainInspector();
        _inspector.setId(inspectorId);
        _inspector.setStatus(CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH);

        if (cetTrainInspectorMapper.updateByPrimaryKeySelective(_inspector) != 1)
            throw new OpException("abolish error1.");

        Integer trainId = inspector.getTrainId();
        CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
        CetTrain _train = new CetTrain();
        _train.setId(trainId);
        _train.setEvaCount(Math.max(0, train.getEvaCount() - 1));
        if (cetTrainMapper.updateByPrimaryKeySelective(_train) != 1)
            throw new OpException("abolish error2.");

        {
            CetTrainEvaResultExample example = new CetTrainEvaResultExample();
            example.createCriteria().andInspectorIdEqualTo(inspectorId);
            cetTrainEvaResultMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void changepasswd(int inspectorId, String passwd) {

        CetTrainInspector record = new CetTrainInspector();
        record.setId(inspectorId);
        record.setPasswd(passwd);
        record.setPasswdChangeType(CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF);

        cetTrainInspectorMapper.updateByPrimaryKeySelective(record);
    }

    // 匿名测评 登录
    public CetTrainInspector tryLogin(String username, String password) {

        username = StringUtils.trimToNull(username);
        password = StringUtils.trimToNull(password);
        if(username==null || password==null) return null;

        CetTrainInspectorExample example = new CetTrainInspectorExample();
        example.createCriteria().andUsernameEqualTo(username)
                .andPasswdEqualTo(password);

        return get(example);
    }

    // 实名测评 登录
    public CetTrainInspector get(int trainId, String mobile) {

        mobile = StringUtils.trimToNull(mobile);
        if( mobile==null) return null;

        CetTrainInspectorExample example = new CetTrainInspectorExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andMobileEqualTo(mobile);

        return get(example);
    }

    private CetTrainInspector get(CetTrainInspectorExample example){

        List<CetTrainInspector> trainInspectors = cetTrainInspectorMapper.selectByExample(example);
        if(trainInspectors.size()==1){
            CetTrainInspector trainInspector = trainInspectors.get(0);
            if(trainInspector.getStatus()==CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH){
                throw new OpException("该账号已经作废");
            }
            if(cetTrainService.evaIsClosed(trainInspector.getTrainId())!=0){
                throw new OpException("测评已经结束");
            }

            return trainInspector;
        }

        return null;
    }
}
