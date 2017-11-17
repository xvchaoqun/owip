package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdNotifyLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdNotifyLogExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNull() {
            addCriterion("order_date is null");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNotNull() {
            addCriterion("order_date is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDateEqualTo(Date value) {
            addCriterion("order_date =", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotEqualTo(Date value) {
            addCriterion("order_date <>", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThan(Date value) {
            addCriterion("order_date >", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThanOrEqualTo(Date value) {
            addCriterion("order_date >=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThan(Date value) {
            addCriterion("order_date <", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThanOrEqualTo(Date value) {
            addCriterion("order_date <=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateIn(List<Date> values) {
            addCriterion("order_date in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotIn(List<Date> values) {
            addCriterion("order_date not in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateBetween(Date value1, Date value2) {
            addCriterion("order_date between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotBetween(Date value1, Date value2) {
            addCriterion("order_date not between", value1, value2, "orderDate");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andJylshIsNull() {
            addCriterion("jylsh is null");
            return (Criteria) this;
        }

        public Criteria andJylshIsNotNull() {
            addCriterion("jylsh is not null");
            return (Criteria) this;
        }

        public Criteria andJylshEqualTo(String value) {
            addCriterion("jylsh =", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshNotEqualTo(String value) {
            addCriterion("jylsh <>", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshGreaterThan(String value) {
            addCriterion("jylsh >", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshGreaterThanOrEqualTo(String value) {
            addCriterion("jylsh >=", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshLessThan(String value) {
            addCriterion("jylsh <", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshLessThanOrEqualTo(String value) {
            addCriterion("jylsh <=", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshLike(String value) {
            addCriterion("jylsh like", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshNotLike(String value) {
            addCriterion("jylsh not like", value, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshIn(List<String> values) {
            addCriterion("jylsh in", values, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshNotIn(List<String> values) {
            addCriterion("jylsh not in", values, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshBetween(String value1, String value2) {
            addCriterion("jylsh between", value1, value2, "jylsh");
            return (Criteria) this;
        }

        public Criteria andJylshNotBetween(String value1, String value2) {
            addCriterion("jylsh not between", value1, value2, "jylsh");
            return (Criteria) this;
        }

        public Criteria andTranStatIsNull() {
            addCriterion("tran_stat is null");
            return (Criteria) this;
        }

        public Criteria andTranStatIsNotNull() {
            addCriterion("tran_stat is not null");
            return (Criteria) this;
        }

        public Criteria andTranStatEqualTo(Byte value) {
            addCriterion("tran_stat =", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatNotEqualTo(Byte value) {
            addCriterion("tran_stat <>", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatGreaterThan(Byte value) {
            addCriterion("tran_stat >", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatGreaterThanOrEqualTo(Byte value) {
            addCriterion("tran_stat >=", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatLessThan(Byte value) {
            addCriterion("tran_stat <", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatLessThanOrEqualTo(Byte value) {
            addCriterion("tran_stat <=", value, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatIn(List<Byte> values) {
            addCriterion("tran_stat in", values, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatNotIn(List<Byte> values) {
            addCriterion("tran_stat not in", values, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatBetween(Byte value1, Byte value2) {
            addCriterion("tran_stat between", value1, value2, "tranStat");
            return (Criteria) this;
        }

        public Criteria andTranStatNotBetween(Byte value1, Byte value2) {
            addCriterion("tran_stat not between", value1, value2, "tranStat");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIsNull() {
            addCriterion("return_type is null");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIsNotNull() {
            addCriterion("return_type is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTypeEqualTo(Byte value) {
            addCriterion("return_type =", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotEqualTo(Byte value) {
            addCriterion("return_type <>", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeGreaterThan(Byte value) {
            addCriterion("return_type >", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("return_type >=", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeLessThan(Byte value) {
            addCriterion("return_type <", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeLessThanOrEqualTo(Byte value) {
            addCriterion("return_type <=", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIn(List<Byte> values) {
            addCriterion("return_type in", values, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotIn(List<Byte> values) {
            addCriterion("return_type not in", values, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeBetween(Byte value1, Byte value2) {
            addCriterion("return_type between", value1, value2, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("return_type not between", value1, value2, "returnType");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andVerifySignIsNull() {
            addCriterion("verify_sign is null");
            return (Criteria) this;
        }

        public Criteria andVerifySignIsNotNull() {
            addCriterion("verify_sign is not null");
            return (Criteria) this;
        }

        public Criteria andVerifySignEqualTo(Boolean value) {
            addCriterion("verify_sign =", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotEqualTo(Boolean value) {
            addCriterion("verify_sign <>", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignGreaterThan(Boolean value) {
            addCriterion("verify_sign >", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignGreaterThanOrEqualTo(Boolean value) {
            addCriterion("verify_sign >=", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignLessThan(Boolean value) {
            addCriterion("verify_sign <", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignLessThanOrEqualTo(Boolean value) {
            addCriterion("verify_sign <=", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignIn(List<Boolean> values) {
            addCriterion("verify_sign in", values, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotIn(List<Boolean> values) {
            addCriterion("verify_sign not in", values, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignBetween(Boolean value1, Boolean value2) {
            addCriterion("verify_sign between", value1, value2, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotBetween(Boolean value1, Boolean value2) {
            addCriterion("verify_sign not between", value1, value2, "verifySign");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNull() {
            addCriterion("ret_time is null");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNotNull() {
            addCriterion("ret_time is not null");
            return (Criteria) this;
        }

        public Criteria andRetTimeEqualTo(Date value) {
            addCriterion("ret_time =", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotEqualTo(Date value) {
            addCriterion("ret_time <>", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThan(Date value) {
            addCriterion("ret_time >", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ret_time >=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThan(Date value) {
            addCriterion("ret_time <", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThanOrEqualTo(Date value) {
            addCriterion("ret_time <=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeIn(List<Date> values) {
            addCriterion("ret_time in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotIn(List<Date> values) {
            addCriterion("ret_time not in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeBetween(Date value1, Date value2) {
            addCriterion("ret_time between", value1, value2, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotBetween(Date value1, Date value2) {
            addCriterion("ret_time not between", value1, value2, "retTime");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
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