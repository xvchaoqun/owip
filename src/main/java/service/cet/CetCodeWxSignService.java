package service.cet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.cet.CetCodeWxSignMapper;
import service.sys.SysApprovalLogService;

@Service
public class CetCodeWxSignService extends CetBaseMapper{

    @Autowired(required = false)
    protected CetCodeWxSignMapper cetCodeWxSignMapper;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;

/*
    public CetCodeWxSign getByCode(String code){

        CetCodeWxSignExample example = new CetCodeWxSignExample();
        example.createCriteria().andCodeEqualTo(code);
        List<CetCodeWxSign> cetCodeWxSigns = cetCodeWxSignMapper.selectByExample(example);

        return cetCodeWxSigns.size() > 0 ? cetCodeWxSigns.get(0) : null;
    }

    //插入绑定信息
    @Transactional
    public void insert(String code, String wxName){

        CetCodeWxSign record = new CetCodeWxSign();
        record.setCode(code);
        record.setWxName(wxName);

        cetCodeWxSignMapper.insert(record);
    }

    //根据trainCourseId和userId得到参训情况
    public CetTrainObjView getcetTrainObjView(String code, String trainCourse){
        SysUserView uv = CmTag.getUserByCode(code);

        if (uv != null) {
            CetTrainObjViewExample example = new CetTrainObjViewExample();
            example.createCriteria().andTrainCourseIdEqualTo(Integer.valueOf(trainCourse)).andUserIdEqualTo(uv.getUserId());
            List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper.selectByExample(example);


            return cetTrainObjViews.size() > 0 ? cetTrainObjViews.get(0) : null;
        }
        return null;
    }

    //判断签到签退并去除*
    public String signInOrOut(String codeSignIn, String codeSignOut){

        String code = null;
        if (codeSignIn != null){
            code = codeSignIn;
        }
        if (codeSignOut != null){
            code = codeSignOut;
        }
        if (code != null){
            code = code.substring(0, code.length() - 1);
        }

        return code;
    }

    // 签到签退
    @Transactional
    public void sign(CetTrainObjView record, boolean sign, byte signType, Date signTime) {

            Integer traineeCourseId = record.getId();
            CetTrainObj cetTrainObj = cetTrainObjMapper.selectByPrimaryKey(traineeCourseId);
            int traineeId = cetTrainObj.getTraineeId();
            CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
            CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
            int userId = cetProjectObj.getUserId();
            String courseName = cetTrainObj.getCetTrainCourse().getCetCourse().getName();

            CetTrainObj _record = new CetTrainObj();
            _record.setIsFinished(sign);
            _record.setSignType(signType);
            if (sign){
                _record.setSignOutTime(signTime);
            }else {
                _record.setSignTime(signTime);
            }

            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andIdEqualTo(traineeCourseId);

            cetTrainObjMapper.updateByExampleSelective(_record, example);

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    sign ? "签到" : "还原", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
    }*/

}
