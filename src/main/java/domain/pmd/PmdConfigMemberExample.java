package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PmdConfigMemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdConfigMemberExample() {
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIsNull() {
            addCriterion("config_member_type is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIsNotNull() {
            addCriterion("config_member_type is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeEqualTo(Byte value) {
            addCriterion("config_member_type =", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNotEqualTo(Byte value) {
            addCriterion("config_member_type <>", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeGreaterThan(Byte value) {
            addCriterion("config_member_type >", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("config_member_type >=", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeLessThan(Byte value) {
            addCriterion("config_member_type <", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeLessThanOrEqualTo(Byte value) {
            addCriterion("config_member_type <=", value, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIn(List<Byte> values) {
            addCriterion("config_member_type in", values, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNotIn(List<Byte> values) {
            addCriterion("config_member_type not in", values, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeBetween(Byte value1, Byte value2) {
            addCriterion("config_member_type between", value1, value2, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("config_member_type not between", value1, value2, "configMemberType");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdIsNull() {
            addCriterion("config_member_type_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdIsNotNull() {
            addCriterion("config_member_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdEqualTo(Integer value) {
            addCriterion("config_member_type_id =", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotEqualTo(Integer value) {
            addCriterion("config_member_type_id <>", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdGreaterThan(Integer value) {
            addCriterion("config_member_type_id >", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_id >=", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdLessThan(Integer value) {
            addCriterion("config_member_type_id <", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_id <=", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdIn(List<Integer> values) {
            addCriterion("config_member_type_id in", values, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotIn(List<Integer> values) {
            addCriterion("config_member_type_id not in", values, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_id between", value1, value2, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_id not between", value1, value2, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayIsNull() {
            addCriterion("is_online_pay is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayIsNotNull() {
            addCriterion("is_online_pay is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayEqualTo(Boolean value) {
            addCriterion("is_online_pay =", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotEqualTo(Boolean value) {
            addCriterion("is_online_pay <>", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayGreaterThan(Boolean value) {
            addCriterion("is_online_pay >", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_online_pay >=", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayLessThan(Boolean value) {
            addCriterion("is_online_pay <", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_online_pay <=", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayIn(List<Boolean> values) {
            addCriterion("is_online_pay in", values, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotIn(List<Boolean> values) {
            addCriterion("is_online_pay not in", values, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online_pay between", value1, value2, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online_pay not between", value1, value2, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNull() {
            addCriterion("due_pay is null");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNotNull() {
            addCriterion("due_pay is not null");
            return (Criteria) this;
        }

        public Criteria andDuePayEqualTo(BigDecimal value) {
            addCriterion("due_pay =", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotEqualTo(BigDecimal value) {
            addCriterion("due_pay <>", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThan(BigDecimal value) {
            addCriterion("due_pay >", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay >=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThan(BigDecimal value) {
            addCriterion("due_pay <", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay <=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayIn(List<BigDecimal> values) {
            addCriterion("due_pay in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotIn(List<BigDecimal> values) {
            addCriterion("due_pay not in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay not between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIsNull() {
            addCriterion("has_salary is null");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIsNotNull() {
            addCriterion("has_salary is not null");
            return (Criteria) this;
        }

        public Criteria andHasSalaryEqualTo(Boolean value) {
            addCriterion("has_salary =", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotEqualTo(Boolean value) {
            addCriterion("has_salary <>", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryGreaterThan(Boolean value) {
            addCriterion("has_salary >", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_salary >=", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryLessThan(Boolean value) {
            addCriterion("has_salary <", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryLessThanOrEqualTo(Boolean value) {
            addCriterion("has_salary <=", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIn(List<Boolean> values) {
            addCriterion("has_salary in", values, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotIn(List<Boolean> values) {
            addCriterion("has_salary not in", values, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryBetween(Boolean value1, Boolean value2) {
            addCriterion("has_salary between", value1, value2, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_salary not between", value1, value2, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasResetIsNull() {
            addCriterion("has_reset is null");
            return (Criteria) this;
        }

        public Criteria andHasResetIsNotNull() {
            addCriterion("has_reset is not null");
            return (Criteria) this;
        }

        public Criteria andHasResetEqualTo(Boolean value) {
            addCriterion("has_reset =", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetNotEqualTo(Boolean value) {
            addCriterion("has_reset <>", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetGreaterThan(Boolean value) {
            addCriterion("has_reset >", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_reset >=", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetLessThan(Boolean value) {
            addCriterion("has_reset <", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetLessThanOrEqualTo(Boolean value) {
            addCriterion("has_reset <=", value, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetIn(List<Boolean> values) {
            addCriterion("has_reset in", values, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetNotIn(List<Boolean> values) {
            addCriterion("has_reset not in", values, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetBetween(Boolean value1, Boolean value2) {
            addCriterion("has_reset between", value1, value2, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasResetNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_reset not between", value1, value2, "hasReset");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryIsNull() {
            addCriterion("has_set_salary is null");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryIsNotNull() {
            addCriterion("has_set_salary is not null");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryEqualTo(Boolean value) {
            addCriterion("has_set_salary =", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryNotEqualTo(Boolean value) {
            addCriterion("has_set_salary <>", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryGreaterThan(Boolean value) {
            addCriterion("has_set_salary >", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_set_salary >=", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryLessThan(Boolean value) {
            addCriterion("has_set_salary <", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryLessThanOrEqualTo(Boolean value) {
            addCriterion("has_set_salary <=", value, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryIn(List<Boolean> values) {
            addCriterion("has_set_salary in", values, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryNotIn(List<Boolean> values) {
            addCriterion("has_set_salary not in", values, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryBetween(Boolean value1, Boolean value2) {
            addCriterion("has_set_salary between", value1, value2, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSetSalaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_set_salary not between", value1, value2, "hasSetSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryIsNull() {
            addCriterion("retire_salary is null");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryIsNotNull() {
            addCriterion("retire_salary is not null");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryEqualTo(BigDecimal value) {
            addCriterion("retire_salary =", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryNotEqualTo(BigDecimal value) {
            addCriterion("retire_salary <>", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryGreaterThan(BigDecimal value) {
            addCriterion("retire_salary >", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_salary >=", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryLessThan(BigDecimal value) {
            addCriterion("retire_salary <", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryLessThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_salary <=", value, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryIn(List<BigDecimal> values) {
            addCriterion("retire_salary in", values, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryNotIn(List<BigDecimal> values) {
            addCriterion("retire_salary not in", values, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_salary between", value1, value2, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andRetireSalaryNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_salary not between", value1, value2, "retireSalary");
            return (Criteria) this;
        }

        public Criteria andSalaryIsNull() {
            addCriterion("salary is null");
            return (Criteria) this;
        }

        public Criteria andSalaryIsNotNull() {
            addCriterion("salary is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryEqualTo(String value) {
            addCriterion("salary =", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotEqualTo(String value) {
            addCriterion("salary <>", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThan(String value) {
            addCriterion("salary >", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThanOrEqualTo(String value) {
            addCriterion("salary >=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThan(String value) {
            addCriterion("salary <", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThanOrEqualTo(String value) {
            addCriterion("salary <=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLike(String value) {
            addCriterion("salary like", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotLike(String value) {
            addCriterion("salary not like", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryIn(List<String> values) {
            addCriterion("salary in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotIn(List<String> values) {
            addCriterion("salary not in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryBetween(String value1, String value2) {
            addCriterion("salary between", value1, value2, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotBetween(String value1, String value2) {
            addCriterion("salary not between", value1, value2, "salary");
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