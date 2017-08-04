package domain.crs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrsApplicantCheckExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrsApplicantCheckExample() {
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

        public Criteria andApplicantIdIsNull() {
            addCriterion("applicant_id is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIdIsNotNull() {
            addCriterion("applicant_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantIdEqualTo(Integer value) {
            addCriterion("applicant_id =", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotEqualTo(Integer value) {
            addCriterion("applicant_id <>", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdGreaterThan(Integer value) {
            addCriterion("applicant_id >", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("applicant_id >=", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdLessThan(Integer value) {
            addCriterion("applicant_id <", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdLessThanOrEqualTo(Integer value) {
            addCriterion("applicant_id <=", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdIn(List<Integer> values) {
            addCriterion("applicant_id in", values, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotIn(List<Integer> values) {
            addCriterion("applicant_id not in", values, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdBetween(Integer value1, Integer value2) {
            addCriterion("applicant_id between", value1, value2, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("applicant_id not between", value1, value2, "applicantId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdIsNull() {
            addCriterion("require_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdIsNotNull() {
            addCriterion("require_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdEqualTo(Integer value) {
            addCriterion("require_rule_id =", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdNotEqualTo(Integer value) {
            addCriterion("require_rule_id <>", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdGreaterThan(Integer value) {
            addCriterion("require_rule_id >", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("require_rule_id >=", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdLessThan(Integer value) {
            addCriterion("require_rule_id <", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("require_rule_id <=", value, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdIn(List<Integer> values) {
            addCriterion("require_rule_id in", values, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdNotIn(List<Integer> values) {
            addCriterion("require_rule_id not in", values, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("require_rule_id between", value1, value2, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andRequireRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("require_rule_id not between", value1, value2, "requireRuleId");
            return (Criteria) this;
        }

        public Criteria andPassIsNull() {
            addCriterion("pass is null");
            return (Criteria) this;
        }

        public Criteria andPassIsNotNull() {
            addCriterion("pass is not null");
            return (Criteria) this;
        }

        public Criteria andPassEqualTo(Boolean value) {
            addCriterion("pass =", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassNotEqualTo(Boolean value) {
            addCriterion("pass <>", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassGreaterThan(Boolean value) {
            addCriterion("pass >", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassGreaterThanOrEqualTo(Boolean value) {
            addCriterion("pass >=", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassLessThan(Boolean value) {
            addCriterion("pass <", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassLessThanOrEqualTo(Boolean value) {
            addCriterion("pass <=", value, "pass");
            return (Criteria) this;
        }

        public Criteria andPassIn(List<Boolean> values) {
            addCriterion("pass in", values, "pass");
            return (Criteria) this;
        }

        public Criteria andPassNotIn(List<Boolean> values) {
            addCriterion("pass not in", values, "pass");
            return (Criteria) this;
        }

        public Criteria andPassBetween(Boolean value1, Boolean value2) {
            addCriterion("pass between", value1, value2, "pass");
            return (Criteria) this;
        }

        public Criteria andPassNotBetween(Boolean value1, Boolean value2) {
            addCriterion("pass not between", value1, value2, "pass");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNull() {
            addCriterion("check_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNotNull() {
            addCriterion("check_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdEqualTo(Integer value) {
            addCriterion("check_user_id =", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotEqualTo(Integer value) {
            addCriterion("check_user_id <>", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThan(Integer value) {
            addCriterion("check_user_id >", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_user_id >=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThan(Integer value) {
            addCriterion("check_user_id <", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_user_id <=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIn(List<Integer> values) {
            addCriterion("check_user_id in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotIn(List<Integer> values) {
            addCriterion("check_user_id not in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id not between", value1, value2, "checkUserId");
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