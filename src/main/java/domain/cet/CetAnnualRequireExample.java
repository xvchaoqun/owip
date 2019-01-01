package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetAnnualRequireExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetAnnualRequireExample() {
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

        public Criteria andAnnualIdIsNull() {
            addCriterion("annual_id is null");
            return (Criteria) this;
        }

        public Criteria andAnnualIdIsNotNull() {
            addCriterion("annual_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualIdEqualTo(Integer value) {
            addCriterion("annual_id =", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotEqualTo(Integer value) {
            addCriterion("annual_id <>", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdGreaterThan(Integer value) {
            addCriterion("annual_id >", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("annual_id >=", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdLessThan(Integer value) {
            addCriterion("annual_id <", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdLessThanOrEqualTo(Integer value) {
            addCriterion("annual_id <=", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdIn(List<Integer> values) {
            addCriterion("annual_id in", values, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotIn(List<Integer> values) {
            addCriterion("annual_id not in", values, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdBetween(Integer value1, Integer value2) {
            addCriterion("annual_id between", value1, value2, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotBetween(Integer value1, Integer value2) {
            addCriterion("annual_id not between", value1, value2, "annualId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNull() {
            addCriterion("admin_level is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNotNull() {
            addCriterion("admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelEqualTo(Integer value) {
            addCriterion("admin_level =", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotEqualTo(Integer value) {
            addCriterion("admin_level <>", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThan(Integer value) {
            addCriterion("admin_level >", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level >=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThan(Integer value) {
            addCriterion("admin_level <", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level <=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIn(List<Integer> values) {
            addCriterion("admin_level in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotIn(List<Integer> values) {
            addCriterion("admin_level not in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("admin_level between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level not between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(BigDecimal value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(BigDecimal value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(BigDecimal value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(BigDecimal value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<BigDecimal> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<BigDecimal> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodIsNull() {
            addCriterion("max_special_period is null");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodIsNotNull() {
            addCriterion("max_special_period is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodEqualTo(BigDecimal value) {
            addCriterion("max_special_period =", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodNotEqualTo(BigDecimal value) {
            addCriterion("max_special_period <>", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodGreaterThan(BigDecimal value) {
            addCriterion("max_special_period >", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_special_period >=", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodLessThan(BigDecimal value) {
            addCriterion("max_special_period <", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_special_period <=", value, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodIn(List<BigDecimal> values) {
            addCriterion("max_special_period in", values, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodNotIn(List<BigDecimal> values) {
            addCriterion("max_special_period not in", values, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_special_period between", value1, value2, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxSpecialPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_special_period not between", value1, value2, "maxSpecialPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodIsNull() {
            addCriterion("max_daily_period is null");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodIsNotNull() {
            addCriterion("max_daily_period is not null");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodEqualTo(BigDecimal value) {
            addCriterion("max_daily_period =", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodNotEqualTo(BigDecimal value) {
            addCriterion("max_daily_period <>", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodGreaterThan(BigDecimal value) {
            addCriterion("max_daily_period >", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_daily_period >=", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodLessThan(BigDecimal value) {
            addCriterion("max_daily_period <", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_daily_period <=", value, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodIn(List<BigDecimal> values) {
            addCriterion("max_daily_period in", values, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodNotIn(List<BigDecimal> values) {
            addCriterion("max_daily_period not in", values, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_daily_period between", value1, value2, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxDailyPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_daily_period not between", value1, value2, "maxDailyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodIsNull() {
            addCriterion("max_party_period is null");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodIsNotNull() {
            addCriterion("max_party_period is not null");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodEqualTo(BigDecimal value) {
            addCriterion("max_party_period =", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodNotEqualTo(BigDecimal value) {
            addCriterion("max_party_period <>", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodGreaterThan(BigDecimal value) {
            addCriterion("max_party_period >", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_party_period >=", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodLessThan(BigDecimal value) {
            addCriterion("max_party_period <", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_party_period <=", value, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodIn(List<BigDecimal> values) {
            addCriterion("max_party_period in", values, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodNotIn(List<BigDecimal> values) {
            addCriterion("max_party_period not in", values, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_party_period between", value1, value2, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxPartyPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_party_period not between", value1, value2, "maxPartyPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodIsNull() {
            addCriterion("max_unit_period is null");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodIsNotNull() {
            addCriterion("max_unit_period is not null");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodEqualTo(BigDecimal value) {
            addCriterion("max_unit_period =", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodNotEqualTo(BigDecimal value) {
            addCriterion("max_unit_period <>", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodGreaterThan(BigDecimal value) {
            addCriterion("max_unit_period >", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_unit_period >=", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodLessThan(BigDecimal value) {
            addCriterion("max_unit_period <", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_unit_period <=", value, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodIn(List<BigDecimal> values) {
            addCriterion("max_unit_period in", values, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodNotIn(List<BigDecimal> values) {
            addCriterion("max_unit_period not in", values, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_unit_period between", value1, value2, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUnitPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_unit_period not between", value1, value2, "maxUnitPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodIsNull() {
            addCriterion("max_upper_period is null");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodIsNotNull() {
            addCriterion("max_upper_period is not null");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodEqualTo(BigDecimal value) {
            addCriterion("max_upper_period =", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodNotEqualTo(BigDecimal value) {
            addCriterion("max_upper_period <>", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodGreaterThan(BigDecimal value) {
            addCriterion("max_upper_period >", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_upper_period >=", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodLessThan(BigDecimal value) {
            addCriterion("max_upper_period <", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_upper_period <=", value, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodIn(List<BigDecimal> values) {
            addCriterion("max_upper_period in", values, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodNotIn(List<BigDecimal> values) {
            addCriterion("max_upper_period not in", values, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_upper_period between", value1, value2, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andMaxUpperPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_upper_period not between", value1, value2, "maxUpperPeriod");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRelateCountIsNull() {
            addCriterion("relate_count is null");
            return (Criteria) this;
        }

        public Criteria andRelateCountIsNotNull() {
            addCriterion("relate_count is not null");
            return (Criteria) this;
        }

        public Criteria andRelateCountEqualTo(Integer value) {
            addCriterion("relate_count =", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountNotEqualTo(Integer value) {
            addCriterion("relate_count <>", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountGreaterThan(Integer value) {
            addCriterion("relate_count >", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("relate_count >=", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountLessThan(Integer value) {
            addCriterion("relate_count <", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountLessThanOrEqualTo(Integer value) {
            addCriterion("relate_count <=", value, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountIn(List<Integer> values) {
            addCriterion("relate_count in", values, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountNotIn(List<Integer> values) {
            addCriterion("relate_count not in", values, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountBetween(Integer value1, Integer value2) {
            addCriterion("relate_count between", value1, value2, "relateCount");
            return (Criteria) this;
        }

        public Criteria andRelateCountNotBetween(Integer value1, Integer value2) {
            addCriterion("relate_count not between", value1, value2, "relateCount");
            return (Criteria) this;
        }

        public Criteria andOpTimeIsNull() {
            addCriterion("op_time is null");
            return (Criteria) this;
        }

        public Criteria andOpTimeIsNotNull() {
            addCriterion("op_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpTimeEqualTo(Date value) {
            addCriterion("op_time =", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotEqualTo(Date value) {
            addCriterion("op_time <>", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThan(Date value) {
            addCriterion("op_time >", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("op_time >=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThan(Date value) {
            addCriterion("op_time <", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThanOrEqualTo(Date value) {
            addCriterion("op_time <=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeIn(List<Date> values) {
            addCriterion("op_time in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotIn(List<Date> values) {
            addCriterion("op_time not in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeBetween(Date value1, Date value2) {
            addCriterion("op_time between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotBetween(Date value1, Date value2) {
            addCriterion("op_time not between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
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