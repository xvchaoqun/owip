package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PmdPayViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdPayViewExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andRealOrderNoIsNull() {
            addCriterion("real_order_no is null");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoIsNotNull() {
            addCriterion("real_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoEqualTo(String value) {
            addCriterion("real_order_no =", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoNotEqualTo(String value) {
            addCriterion("real_order_no <>", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoGreaterThan(String value) {
            addCriterion("real_order_no >", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("real_order_no >=", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoLessThan(String value) {
            addCriterion("real_order_no <", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoLessThanOrEqualTo(String value) {
            addCriterion("real_order_no <=", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoLike(String value) {
            addCriterion("real_order_no like", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoNotLike(String value) {
            addCriterion("real_order_no not like", value, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoIn(List<String> values) {
            addCriterion("real_order_no in", values, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoNotIn(List<String> values) {
            addCriterion("real_order_no not in", values, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoBetween(String value1, String value2) {
            addCriterion("real_order_no between", value1, value2, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andRealOrderNoNotBetween(String value1, String value2) {
            addCriterion("real_order_no not between", value1, value2, "realOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayerIsNull() {
            addCriterion("payer is null");
            return (Criteria) this;
        }

        public Criteria andPayerIsNotNull() {
            addCriterion("payer is not null");
            return (Criteria) this;
        }

        public Criteria andPayerEqualTo(String value) {
            addCriterion("payer =", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotEqualTo(String value) {
            addCriterion("payer <>", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThan(String value) {
            addCriterion("payer >", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThanOrEqualTo(String value) {
            addCriterion("payer >=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThan(String value) {
            addCriterion("payer <", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThanOrEqualTo(String value) {
            addCriterion("payer <=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLike(String value) {
            addCriterion("payer like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotLike(String value) {
            addCriterion("payer not like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerIn(List<String> values) {
            addCriterion("payer in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotIn(List<String> values) {
            addCriterion("payer not in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerBetween(String value1, String value2) {
            addCriterion("payer between", value1, value2, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotBetween(String value1, String value2) {
            addCriterion("payer not between", value1, value2, "payer");
            return (Criteria) this;
        }

        public Criteria andPayernameIsNull() {
            addCriterion("payername is null");
            return (Criteria) this;
        }

        public Criteria andPayernameIsNotNull() {
            addCriterion("payername is not null");
            return (Criteria) this;
        }

        public Criteria andPayernameEqualTo(String value) {
            addCriterion("payername =", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotEqualTo(String value) {
            addCriterion("payername <>", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameGreaterThan(String value) {
            addCriterion("payername >", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameGreaterThanOrEqualTo(String value) {
            addCriterion("payername >=", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLessThan(String value) {
            addCriterion("payername <", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLessThanOrEqualTo(String value) {
            addCriterion("payername <=", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLike(String value) {
            addCriterion("payername like", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotLike(String value) {
            addCriterion("payername not like", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameIn(List<String> values) {
            addCriterion("payername in", values, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotIn(List<String> values) {
            addCriterion("payername not in", values, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameBetween(String value1, String value2) {
            addCriterion("payername between", value1, value2, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotBetween(String value1, String value2) {
            addCriterion("payername not between", value1, value2, "payername");
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

        public Criteria andPayMonthIsNull() {
            addCriterion("pay_month is null");
            return (Criteria) this;
        }

        public Criteria andPayMonthIsNotNull() {
            addCriterion("pay_month is not null");
            return (Criteria) this;
        }

        public Criteria andPayMonthEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month =", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <>", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_month >", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month >=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThan(Date value) {
            addCriterionForJDBCDate("pay_month <", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month not in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month not between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andIsDelayIsNull() {
            addCriterion("is_delay is null");
            return (Criteria) this;
        }

        public Criteria andIsDelayIsNotNull() {
            addCriterion("is_delay is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelayEqualTo(Boolean value) {
            addCriterion("is_delay =", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotEqualTo(Boolean value) {
            addCriterion("is_delay <>", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayGreaterThan(Boolean value) {
            addCriterion("is_delay >", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delay >=", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayLessThan(Boolean value) {
            addCriterion("is_delay <", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delay <=", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayIn(List<Boolean> values) {
            addCriterion("is_delay in", values, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotIn(List<Boolean> values) {
            addCriterion("is_delay not in", values, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delay between", value1, value2, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delay not between", value1, value2, "isDelay");
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameIsNull() {
            addCriterion("order_realname is null");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameIsNotNull() {
            addCriterion("order_realname is not null");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameEqualTo(String value) {
            addCriterion("order_realname =", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameNotEqualTo(String value) {
            addCriterion("order_realname <>", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameGreaterThan(String value) {
            addCriterion("order_realname >", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("order_realname >=", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameLessThan(String value) {
            addCriterion("order_realname <", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameLessThanOrEqualTo(String value) {
            addCriterion("order_realname <=", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameLike(String value) {
            addCriterion("order_realname like", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameNotLike(String value) {
            addCriterion("order_realname not like", value, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameIn(List<String> values) {
            addCriterion("order_realname in", values, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameNotIn(List<String> values) {
            addCriterion("order_realname not in", values, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameBetween(String value1, String value2) {
            addCriterion("order_realname between", value1, value2, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andOrderRealnameNotBetween(String value1, String value2) {
            addCriterion("order_realname not between", value1, value2, "orderRealname");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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