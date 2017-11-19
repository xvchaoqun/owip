package domain.pcs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PcsVoteCandidateViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsVoteCandidateViewExample() {
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

        public Criteria andIsFromStageIsNull() {
            addCriterion("is_from_stage is null");
            return (Criteria) this;
        }

        public Criteria andIsFromStageIsNotNull() {
            addCriterion("is_from_stage is not null");
            return (Criteria) this;
        }

        public Criteria andIsFromStageEqualTo(Boolean value) {
            addCriterion("is_from_stage =", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotEqualTo(Boolean value) {
            addCriterion("is_from_stage <>", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageGreaterThan(Boolean value) {
            addCriterion("is_from_stage >", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_from_stage >=", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageLessThan(Boolean value) {
            addCriterion("is_from_stage <", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageLessThanOrEqualTo(Boolean value) {
            addCriterion("is_from_stage <=", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageIn(List<Boolean> values) {
            addCriterion("is_from_stage in", values, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotIn(List<Boolean> values) {
            addCriterion("is_from_stage not in", values, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_stage between", value1, value2, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_stage not between", value1, value2, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNull() {
            addCriterion("agree is null");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNotNull() {
            addCriterion("agree is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeEqualTo(BigDecimal value) {
            addCriterion("agree =", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotEqualTo(BigDecimal value) {
            addCriterion("agree <>", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThan(BigDecimal value) {
            addCriterion("agree >", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("agree >=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThan(BigDecimal value) {
            addCriterion("agree <", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("agree <=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeIn(List<BigDecimal> values) {
            addCriterion("agree in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotIn(List<BigDecimal> values) {
            addCriterion("agree not in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agree between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agree not between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(BigDecimal value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(BigDecimal value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(BigDecimal value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(BigDecimal value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<BigDecimal> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<BigDecimal> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNull() {
            addCriterion("abstain is null");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNotNull() {
            addCriterion("abstain is not null");
            return (Criteria) this;
        }

        public Criteria andAbstainEqualTo(BigDecimal value) {
            addCriterion("abstain =", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotEqualTo(BigDecimal value) {
            addCriterion("abstain <>", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThan(BigDecimal value) {
            addCriterion("abstain >", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("abstain >=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThan(BigDecimal value) {
            addCriterion("abstain <", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThanOrEqualTo(BigDecimal value) {
            addCriterion("abstain <=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainIn(List<BigDecimal> values) {
            addCriterion("abstain in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotIn(List<BigDecimal> values) {
            addCriterion("abstain not in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("abstain between", value1, value2, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("abstain not between", value1, value2, "abstain");
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