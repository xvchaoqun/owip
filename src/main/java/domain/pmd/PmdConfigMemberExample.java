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

        public Criteria andGwgzIsNull() {
            addCriterion("gwgz is null");
            return (Criteria) this;
        }

        public Criteria andGwgzIsNotNull() {
            addCriterion("gwgz is not null");
            return (Criteria) this;
        }

        public Criteria andGwgzEqualTo(BigDecimal value) {
            addCriterion("gwgz =", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotEqualTo(BigDecimal value) {
            addCriterion("gwgz <>", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzGreaterThan(BigDecimal value) {
            addCriterion("gwgz >", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gwgz >=", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzLessThan(BigDecimal value) {
            addCriterion("gwgz <", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gwgz <=", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzIn(List<BigDecimal> values) {
            addCriterion("gwgz in", values, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotIn(List<BigDecimal> values) {
            addCriterion("gwgz not in", values, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwgz between", value1, value2, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwgz not between", value1, value2, "gwgz");
            return (Criteria) this;
        }

        public Criteria andXjgzIsNull() {
            addCriterion("xjgz is null");
            return (Criteria) this;
        }

        public Criteria andXjgzIsNotNull() {
            addCriterion("xjgz is not null");
            return (Criteria) this;
        }

        public Criteria andXjgzEqualTo(BigDecimal value) {
            addCriterion("xjgz =", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotEqualTo(BigDecimal value) {
            addCriterion("xjgz <>", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzGreaterThan(BigDecimal value) {
            addCriterion("xjgz >", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xjgz >=", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzLessThan(BigDecimal value) {
            addCriterion("xjgz <", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xjgz <=", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzIn(List<BigDecimal> values) {
            addCriterion("xjgz in", values, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotIn(List<BigDecimal> values) {
            addCriterion("xjgz not in", values, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xjgz between", value1, value2, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xjgz not between", value1, value2, "xjgz");
            return (Criteria) this;
        }

        public Criteria andGwjtIsNull() {
            addCriterion("gwjt is null");
            return (Criteria) this;
        }

        public Criteria andGwjtIsNotNull() {
            addCriterion("gwjt is not null");
            return (Criteria) this;
        }

        public Criteria andGwjtEqualTo(BigDecimal value) {
            addCriterion("gwjt =", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotEqualTo(BigDecimal value) {
            addCriterion("gwjt <>", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtGreaterThan(BigDecimal value) {
            addCriterion("gwjt >", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gwjt >=", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtLessThan(BigDecimal value) {
            addCriterion("gwjt <", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gwjt <=", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtIn(List<BigDecimal> values) {
            addCriterion("gwjt in", values, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotIn(List<BigDecimal> values) {
            addCriterion("gwjt not in", values, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwjt between", value1, value2, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwjt not between", value1, value2, "gwjt");
            return (Criteria) this;
        }

        public Criteria andZwbtIsNull() {
            addCriterion("zwbt is null");
            return (Criteria) this;
        }

        public Criteria andZwbtIsNotNull() {
            addCriterion("zwbt is not null");
            return (Criteria) this;
        }

        public Criteria andZwbtEqualTo(BigDecimal value) {
            addCriterion("zwbt =", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotEqualTo(BigDecimal value) {
            addCriterion("zwbt <>", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtGreaterThan(BigDecimal value) {
            addCriterion("zwbt >", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt >=", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtLessThan(BigDecimal value) {
            addCriterion("zwbt <", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt <=", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtIn(List<BigDecimal> values) {
            addCriterion("zwbt in", values, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotIn(List<BigDecimal> values) {
            addCriterion("zwbt not in", values, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt between", value1, value2, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt not between", value1, value2, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbt1IsNull() {
            addCriterion("zwbt1 is null");
            return (Criteria) this;
        }

        public Criteria andZwbt1IsNotNull() {
            addCriterion("zwbt1 is not null");
            return (Criteria) this;
        }

        public Criteria andZwbt1EqualTo(BigDecimal value) {
            addCriterion("zwbt1 =", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotEqualTo(BigDecimal value) {
            addCriterion("zwbt1 <>", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1GreaterThan(BigDecimal value) {
            addCriterion("zwbt1 >", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt1 >=", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1LessThan(BigDecimal value) {
            addCriterion("zwbt1 <", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt1 <=", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1In(List<BigDecimal> values) {
            addCriterion("zwbt1 in", values, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotIn(List<BigDecimal> values) {
            addCriterion("zwbt1 not in", values, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt1 between", value1, value2, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt1 not between", value1, value2, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andShbtIsNull() {
            addCriterion("shbt is null");
            return (Criteria) this;
        }

        public Criteria andShbtIsNotNull() {
            addCriterion("shbt is not null");
            return (Criteria) this;
        }

        public Criteria andShbtEqualTo(BigDecimal value) {
            addCriterion("shbt =", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotEqualTo(BigDecimal value) {
            addCriterion("shbt <>", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtGreaterThan(BigDecimal value) {
            addCriterion("shbt >", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shbt >=", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtLessThan(BigDecimal value) {
            addCriterion("shbt <", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shbt <=", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtIn(List<BigDecimal> values) {
            addCriterion("shbt in", values, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotIn(List<BigDecimal> values) {
            addCriterion("shbt not in", values, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shbt between", value1, value2, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shbt not between", value1, value2, "shbt");
            return (Criteria) this;
        }

        public Criteria andSbfIsNull() {
            addCriterion("sbf is null");
            return (Criteria) this;
        }

        public Criteria andSbfIsNotNull() {
            addCriterion("sbf is not null");
            return (Criteria) this;
        }

        public Criteria andSbfEqualTo(BigDecimal value) {
            addCriterion("sbf =", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotEqualTo(BigDecimal value) {
            addCriterion("sbf <>", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfGreaterThan(BigDecimal value) {
            addCriterion("sbf >", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sbf >=", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfLessThan(BigDecimal value) {
            addCriterion("sbf <", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sbf <=", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfIn(List<BigDecimal> values) {
            addCriterion("sbf in", values, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotIn(List<BigDecimal> values) {
            addCriterion("sbf not in", values, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sbf between", value1, value2, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sbf not between", value1, value2, "sbf");
            return (Criteria) this;
        }

        public Criteria andXlfIsNull() {
            addCriterion("xlf is null");
            return (Criteria) this;
        }

        public Criteria andXlfIsNotNull() {
            addCriterion("xlf is not null");
            return (Criteria) this;
        }

        public Criteria andXlfEqualTo(BigDecimal value) {
            addCriterion("xlf =", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotEqualTo(BigDecimal value) {
            addCriterion("xlf <>", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfGreaterThan(BigDecimal value) {
            addCriterion("xlf >", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xlf >=", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfLessThan(BigDecimal value) {
            addCriterion("xlf <", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xlf <=", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfIn(List<BigDecimal> values) {
            addCriterion("xlf in", values, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotIn(List<BigDecimal> values) {
            addCriterion("xlf not in", values, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xlf between", value1, value2, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xlf not between", value1, value2, "xlf");
            return (Criteria) this;
        }

        public Criteria andGzcxIsNull() {
            addCriterion("gzcx is null");
            return (Criteria) this;
        }

        public Criteria andGzcxIsNotNull() {
            addCriterion("gzcx is not null");
            return (Criteria) this;
        }

        public Criteria andGzcxEqualTo(BigDecimal value) {
            addCriterion("gzcx =", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotEqualTo(BigDecimal value) {
            addCriterion("gzcx <>", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxGreaterThan(BigDecimal value) {
            addCriterion("gzcx >", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gzcx >=", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxLessThan(BigDecimal value) {
            addCriterion("gzcx <", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gzcx <=", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxIn(List<BigDecimal> values) {
            addCriterion("gzcx in", values, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotIn(List<BigDecimal> values) {
            addCriterion("gzcx not in", values, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gzcx between", value1, value2, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gzcx not between", value1, value2, "gzcx");
            return (Criteria) this;
        }

        public Criteria andShiyebxIsNull() {
            addCriterion("shiyebx is null");
            return (Criteria) this;
        }

        public Criteria andShiyebxIsNotNull() {
            addCriterion("shiyebx is not null");
            return (Criteria) this;
        }

        public Criteria andShiyebxEqualTo(BigDecimal value) {
            addCriterion("shiyebx =", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxNotEqualTo(BigDecimal value) {
            addCriterion("shiyebx <>", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxGreaterThan(BigDecimal value) {
            addCriterion("shiyebx >", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shiyebx >=", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxLessThan(BigDecimal value) {
            addCriterion("shiyebx <", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shiyebx <=", value, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxIn(List<BigDecimal> values) {
            addCriterion("shiyebx in", values, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxNotIn(List<BigDecimal> values) {
            addCriterion("shiyebx not in", values, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shiyebx between", value1, value2, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andShiyebxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shiyebx not between", value1, value2, "shiyebx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxIsNull() {
            addCriterion("yanglaobx is null");
            return (Criteria) this;
        }

        public Criteria andYanglaobxIsNotNull() {
            addCriterion("yanglaobx is not null");
            return (Criteria) this;
        }

        public Criteria andYanglaobxEqualTo(BigDecimal value) {
            addCriterion("yanglaobx =", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxNotEqualTo(BigDecimal value) {
            addCriterion("yanglaobx <>", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxGreaterThan(BigDecimal value) {
            addCriterion("yanglaobx >", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yanglaobx >=", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxLessThan(BigDecimal value) {
            addCriterion("yanglaobx <", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yanglaobx <=", value, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxIn(List<BigDecimal> values) {
            addCriterion("yanglaobx in", values, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxNotIn(List<BigDecimal> values) {
            addCriterion("yanglaobx not in", values, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yanglaobx between", value1, value2, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYanglaobxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yanglaobx not between", value1, value2, "yanglaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxIsNull() {
            addCriterion("yiliaobx is null");
            return (Criteria) this;
        }

        public Criteria andYiliaobxIsNotNull() {
            addCriterion("yiliaobx is not null");
            return (Criteria) this;
        }

        public Criteria andYiliaobxEqualTo(BigDecimal value) {
            addCriterion("yiliaobx =", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxNotEqualTo(BigDecimal value) {
            addCriterion("yiliaobx <>", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxGreaterThan(BigDecimal value) {
            addCriterion("yiliaobx >", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yiliaobx >=", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxLessThan(BigDecimal value) {
            addCriterion("yiliaobx <", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yiliaobx <=", value, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxIn(List<BigDecimal> values) {
            addCriterion("yiliaobx in", values, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxNotIn(List<BigDecimal> values) {
            addCriterion("yiliaobx not in", values, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yiliaobx between", value1, value2, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andYiliaobxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yiliaobx not between", value1, value2, "yiliaobx");
            return (Criteria) this;
        }

        public Criteria andGsbxIsNull() {
            addCriterion("gsbx is null");
            return (Criteria) this;
        }

        public Criteria andGsbxIsNotNull() {
            addCriterion("gsbx is not null");
            return (Criteria) this;
        }

        public Criteria andGsbxEqualTo(BigDecimal value) {
            addCriterion("gsbx =", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxNotEqualTo(BigDecimal value) {
            addCriterion("gsbx <>", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxGreaterThan(BigDecimal value) {
            addCriterion("gsbx >", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gsbx >=", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxLessThan(BigDecimal value) {
            addCriterion("gsbx <", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gsbx <=", value, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxIn(List<BigDecimal> values) {
            addCriterion("gsbx in", values, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxNotIn(List<BigDecimal> values) {
            addCriterion("gsbx not in", values, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gsbx between", value1, value2, "gsbx");
            return (Criteria) this;
        }

        public Criteria andGsbxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gsbx not between", value1, value2, "gsbx");
            return (Criteria) this;
        }

        public Criteria andShengyubxIsNull() {
            addCriterion("shengyubx is null");
            return (Criteria) this;
        }

        public Criteria andShengyubxIsNotNull() {
            addCriterion("shengyubx is not null");
            return (Criteria) this;
        }

        public Criteria andShengyubxEqualTo(BigDecimal value) {
            addCriterion("shengyubx =", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxNotEqualTo(BigDecimal value) {
            addCriterion("shengyubx <>", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxGreaterThan(BigDecimal value) {
            addCriterion("shengyubx >", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shengyubx >=", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxLessThan(BigDecimal value) {
            addCriterion("shengyubx <", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shengyubx <=", value, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxIn(List<BigDecimal> values) {
            addCriterion("shengyubx in", values, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxNotIn(List<BigDecimal> values) {
            addCriterion("shengyubx not in", values, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shengyubx between", value1, value2, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andShengyubxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shengyubx not between", value1, value2, "shengyubx");
            return (Criteria) this;
        }

        public Criteria andQynjIsNull() {
            addCriterion("qynj is null");
            return (Criteria) this;
        }

        public Criteria andQynjIsNotNull() {
            addCriterion("qynj is not null");
            return (Criteria) this;
        }

        public Criteria andQynjEqualTo(BigDecimal value) {
            addCriterion("qynj =", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjNotEqualTo(BigDecimal value) {
            addCriterion("qynj <>", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjGreaterThan(BigDecimal value) {
            addCriterion("qynj >", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("qynj >=", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjLessThan(BigDecimal value) {
            addCriterion("qynj <", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("qynj <=", value, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjIn(List<BigDecimal> values) {
            addCriterion("qynj in", values, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjNotIn(List<BigDecimal> values) {
            addCriterion("qynj not in", values, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qynj between", value1, value2, "qynj");
            return (Criteria) this;
        }

        public Criteria andQynjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qynj not between", value1, value2, "qynj");
            return (Criteria) this;
        }

        public Criteria andZynjIsNull() {
            addCriterion("zynj is null");
            return (Criteria) this;
        }

        public Criteria andZynjIsNotNull() {
            addCriterion("zynj is not null");
            return (Criteria) this;
        }

        public Criteria andZynjEqualTo(BigDecimal value) {
            addCriterion("zynj =", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjNotEqualTo(BigDecimal value) {
            addCriterion("zynj <>", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjGreaterThan(BigDecimal value) {
            addCriterion("zynj >", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zynj >=", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjLessThan(BigDecimal value) {
            addCriterion("zynj <", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("zynj <=", value, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjIn(List<BigDecimal> values) {
            addCriterion("zynj in", values, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjNotIn(List<BigDecimal> values) {
            addCriterion("zynj not in", values, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zynj between", value1, value2, "zynj");
            return (Criteria) this;
        }

        public Criteria andZynjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zynj not between", value1, value2, "zynj");
            return (Criteria) this;
        }

        public Criteria andGjjIsNull() {
            addCriterion("gjj is null");
            return (Criteria) this;
        }

        public Criteria andGjjIsNotNull() {
            addCriterion("gjj is not null");
            return (Criteria) this;
        }

        public Criteria andGjjEqualTo(BigDecimal value) {
            addCriterion("gjj =", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjNotEqualTo(BigDecimal value) {
            addCriterion("gjj <>", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjGreaterThan(BigDecimal value) {
            addCriterion("gjj >", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gjj >=", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjLessThan(BigDecimal value) {
            addCriterion("gjj <", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gjj <=", value, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjIn(List<BigDecimal> values) {
            addCriterion("gjj in", values, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjNotIn(List<BigDecimal> values) {
            addCriterion("gjj not in", values, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gjj between", value1, value2, "gjj");
            return (Criteria) this;
        }

        public Criteria andGjjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gjj not between", value1, value2, "gjj");
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