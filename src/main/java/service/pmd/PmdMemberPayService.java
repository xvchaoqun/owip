package service.pmd;

import domain.pmd.PmdBranch;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMemberPayViewExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PmdMemberPayService extends BaseMapper {

    @Autowired
    private  PmdMemberService pmdMemberService;
    @Autowired
    private  PmdBranchService pmdBranchService;
    @Autowired
    private  PmdPartyService pmdPartyService;
    @Autowired
    private  PmdMonthService pmdMonthService;

    // 根据订单号，查找党员账单
    public PmdMemberPayView get(String orderNo){

        PmdMemberPayViewExample example = new PmdMemberPayViewExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<PmdMemberPayView> PmdMemberPayViews = pmdMemberPayViewMapper.selectByExample(example);
        return PmdMemberPayViews.size()==0?null:PmdMemberPayViews.get(0);
    }

    // 根据订单号，查找党员账单视图
    public PmdMemberPayView get(int pmdMemberId){

        PmdMemberPayViewExample example = new PmdMemberPayViewExample();
        example.createCriteria().andMemberIdEqualTo(pmdMemberId);
        List<PmdMemberPayView> PmdMemberPayViews = pmdMemberPayViewMapper.selectByExample(example);
        return PmdMemberPayViews.size()==0?null:PmdMemberPayViews.get(0);
    }

    /**
     * 判断能否缴费以及缴费类型
     * <p>
     * 能缴费的情况：已设置额度>0 && 已开启缴费 && 党支部未报送  && 还未缴费  && 当月的没有设置为延迟缴费
     *
     * return 0 不可缴费  1 缴费 2 补缴
     */
    public int getPayStatus(int pmdMemberId) {

        int notAllowed = 0; // 不可缴费
        int normalPay = 1; // 缴费
        int delayPay = 2; // 补缴

        PmdMemberPayView pmdMemberPayView = get(pmdMemberId);
        if(pmdMemberPayView==null
                || pmdMemberPayView.getDuePay()==null
                || pmdMemberPayView.getDuePay().compareTo(BigDecimal.ZERO)<=0) return notAllowed;

        if (pmdMemberPayView.getHasPay()) return notAllowed; // 已缴费

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return notAllowed; // 已关闭缴费

        int monthId = pmdMemberPayView.getMonthId();
        Integer currentMonthId = currentPmdMonth.getId();

        if (currentMonthId == monthId) {
            // 正常缴费，不需要判断支部是否报送，因为支部报送前，肯定已经确认了所有党员已经缴费或延迟缴费
            if (pmdMemberPayView.getIsDelay()) {
                // 当月已设置延迟
                return notAllowed;
            } else {
                return normalPay;
            }
        } else {
            /**
             * 往月未缴费，肯定已经设置了延迟缴费，
             * 只需要判断支部（党员当前所在的支部，不一定是快照生成的支部）是否报送
              */
            int userId = pmdMemberPayView.getUserId();
            PmdMember pmdMember = pmdMemberService.get(currentMonthId, userId);
            int partyId = pmdMember.getPartyId();
            Integer branchId = pmdMember.getBranchId();

            if (branchId != null) {

                PmdBranch pmdBranch = pmdBranchService.get(currentPmdMonth.getId(), partyId, branchId);
                // 支部已报送
                if (pmdBranch==null || pmdBranch.getHasReport()) return notAllowed;
            } else {

                PmdParty pmdParty = pmdPartyService.get(currentPmdMonth.getId(), partyId);
                // 直属党支部已报送
                if (pmdParty==null || pmdParty.getHasReport()) return notAllowed;
            }

            return delayPay;
        }
    }
}
