package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdMemberPayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdMemberPayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdIsNull() {
            addCriterion("order_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdIsNotNull() {
            addCriterion("order_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdEqualTo(Integer value) {
            addCriterion("order_user_id =", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdNotEqualTo(Integer value) {
            addCriterion("order_user_id <>", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdGreaterThan(Integer value) {
            addCriterion("order_user_id >", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_user_id >=", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdLessThan(Integer value) {
            addCriterion("order_user_id <", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_user_id <=", value, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdIn(List<Integer> values) {
            addCriterion("order_user_id in", values, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdNotIn(List<Integer> values) {
            addCriterion("order_user_id not in", values, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdBetween(Integer value1, Integer value2) {
            addCriterion("order_user_id between", value1, value2, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andOrderUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_user_id not between", value1, value2, "orderUserId");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNull() {
            addCriterion("real_pay is null");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNotNull() {
            addCriterion("real_pay is not null");
            return (Criteria) this;
        }

        public Criteria andRealPayEqualTo(BigDecimal value) {
            addCriterion("real_pay =", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotEqualTo(BigDecimal value) {
            addCriterion("real_pay <>", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThan(BigDecimal value) {
            addCriterion("real_pay >", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay >=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThan(BigDecimal value) {
            addCriterion("real_pay <", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay <=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayIn(List<BigDecimal> values) {
            addCriterion("real_pay in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotIn(List<BigDecimal> values) {
            addCriterion("real_pay not in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay not between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andHasPayIsNull() {
            addCriterion("has_pay is null");
            return (Criteria) this;
        }

        public Criteria andHasPayIsNotNull() {
            addCriterion("has_pay is not null");
            return (Criteria) this;
        }

        public Criteria andHasPayEqualTo(Boolean value) {
            addCriterion("has_pay =", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotEqualTo(Boolean value) {
            addCriterion("has_pay <>", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayGreaterThan(Boolean value) {
            addCriterion("has_pay >", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_pay >=", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayLessThan(Boolean value) {
            addCriterion("has_pay <", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayLessThanOrEqualTo(Boolean value) {
            addCriterion("has_pay <=", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayIn(List<Boolean> values) {
            addCriterion("has_pay in", values, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotIn(List<Boolean> values) {
            addCriterion("has_pay not in", values, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayBetween(Boolean value1, Boolean value2) {
            addCriterion("has_pay between", value1, value2, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_pay not between", value1, value2, "hasPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIsNull() {
            addCriterion("is_self_pay is null");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIsNotNull() {
            addCriterion("is_self_pay is not null");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayEqualTo(Boolean value) {
            addCriterion("is_self_pay =", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotEqualTo(Boolean value) {
            addCriterion("is_self_pay <>", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayGreaterThan(Boolean value) {
            addCriterion("is_self_pay >", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_self_pay >=", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayLessThan(Boolean value) {
            addCriterion("is_self_pay <", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_self_pay <=", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIn(List<Boolean> values) {
            addCriterion("is_self_pay in", values, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotIn(List<Boolean> values) {
            addCriterion("is_self_pay not in", values, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_self_pay between", value1, value2, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_self_pay not between", value1, value2, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdIsNull() {
            addCriterion("pay_month_id is null");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdIsNotNull() {
            addCriterion("pay_month_id is not null");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdEqualTo(Integer value) {
            addCriterion("pay_month_id =", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdNotEqualTo(Integer value) {
            addCriterion("pay_month_id <>", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdGreaterThan(Integer value) {
            addCriterion("pay_month_id >", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_month_id >=", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdLessThan(Integer value) {
            addCriterion("pay_month_id <", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdLessThanOrEqualTo(Integer value) {
            addCriterion("pay_month_id <=", value, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdIn(List<Integer> values) {
            addCriterion("pay_month_id in", values, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdNotIn(List<Integer> values) {
            addCriterion("pay_month_id not in", values, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdBetween(Integer value1, Integer value2) {
            addCriterion("pay_month_id between", value1, value2, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayMonthIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_month_id not between", value1, value2, "payMonthId");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIsNull() {
            addCriterion("charge_user_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIsNotNull() {
            addCriterion("charge_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdEqualTo(Integer value) {
            addCriterion("charge_user_id =", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotEqualTo(Integer value) {
            addCriterion("charge_user_id <>", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdGreaterThan(Integer value) {
            addCriterion("charge_user_id >", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_user_id >=", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdLessThan(Integer value) {
            addCriterion("charge_user_id <", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_user_id <=", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIn(List<Integer> values) {
            addCriterion("charge_user_id in", values, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotIn(List<Integer> values) {
            addCriterion("charge_user_id not in", values, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_user_id between", value1, value2, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_user_id not between", value1, value2, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdIsNull() {
            addCriterion("charge_party_id is null");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdIsNotNull() {
            addCriterion("charge_party_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdEqualTo(Integer value) {
            addCriterion("charge_party_id =", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdNotEqualTo(Integer value) {
            addCriterion("charge_party_id <>", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdGreaterThan(Integer value) {
            addCriterion("charge_party_id >", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_party_id >=", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdLessThan(Integer value) {
            addCriterion("charge_party_id <", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_party_id <=", value, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdIn(List<Integer> values) {
            addCriterion("charge_party_id in", values, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdNotIn(List<Integer> values) {
            addCriterion("charge_party_id not in", values, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_party_id between", value1, value2, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargePartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_party_id not between", value1, value2, "chargePartyId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdIsNull() {
            addCriterion("charge_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdIsNotNull() {
            addCriterion("charge_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdEqualTo(Integer value) {
            addCriterion("charge_branch_id =", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdNotEqualTo(Integer value) {
            addCriterion("charge_branch_id <>", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdGreaterThan(Integer value) {
            addCriterion("charge_branch_id >", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_branch_id >=", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdLessThan(Integer value) {
            addCriterion("charge_branch_id <", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_branch_id <=", value, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdIn(List<Integer> values) {
            addCriterion("charge_branch_id in", values, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdNotIn(List<Integer> values) {
            addCriterion("charge_branch_id not in", values, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_branch_id between", value1, value2, "chargeBranchId");
            return (Criteria) this;
        }

        public Criteria andChargeBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_branch_id not between", value1, value2, "chargeBranchId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}