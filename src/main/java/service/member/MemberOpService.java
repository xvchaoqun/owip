package service.member;

import domain.member.MemberOut;
import domain.member.MemberTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.constants.SystemConstants;

@Service
public class MemberOpService extends BaseMapper{

    @Autowired
    private MemberOutService memberOutService;
    @Autowired
    private MemberTransferService memberTransferService;

    /**
     * 填写了党员流出，其他3项都可以填写，不做限定；
     * 填写了组织关系转出，另外3项就都不能填写了；
     * 填写校内组织关系转接之后另外3项同样不能填写；
     * 填写公派留学生申请组织关系暂留之后原则上另外3项都可以填写。
     * 但是党员流出和公派留学生组织关系暂留的党员转出之后在党员流出和组织关系暂留的状态栏目相应显示“已转出”
     * @param userId
     * @return
     */
    public int findOpAuth(int userId){

        MemberOut memberOut = memberOutService.get(userId);
        if(memberOut!=null && memberOut.getStatus()>=SystemConstants.MEMBER_OUT_STATUS_APPLY){
            return 1; // 已经申请了组织关系转出
        }
        MemberTransfer memberTransfer = memberTransferService.get(userId);
        if(memberTransfer!=null
                && memberTransfer.getStatus()>=SystemConstants.MEMBER_TRANSFER_STATUS_APPLY
                && memberTransfer.getStatus()< SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY // 完成了校内转接，可以其他申请
                ){
            return 2; // 已经申请了校内组织关系转接
        }
        return 0; // 可以
    }

    public void checkOpAuth(int userId){

        int opAuth = findOpAuth(userId);
        if(opAuth==1){
            throw new RuntimeException("已经申请了组织关系转出，请撤销后再进行当前申请");
        }
        if(opAuth==2){
            throw new RuntimeException("已经申请了校内组织关系转接，请撤销后再进行当前申请");
        }
    }

}
