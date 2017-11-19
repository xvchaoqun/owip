package domain.pcs;

import java.util.ArrayList;
import java.util.List;

public class PcsVoteCandidateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsVoteCandidateExample() {
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

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
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

        public Criteria andAgreeIsNull() {
            addCriterion("agree is null");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNotNull() {
            addCriterion("agree is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeEqualTo(Integer value) {
            addCriterion("agree =", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotEqualTo(Integer value) {
            addCriterion("agree <>", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThan(Integer value) {
            addCriterion("agree >", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("agree >=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThan(Integer value) {
            addCriterion("agree <", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThanOrEqualTo(Integer value) {
            addCriterion("agree <=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeIn(List<Integer> values) {
            addCriterion("agree in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotIn(List<Integer> values) {
            addCriterion("agree not in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeBetween(Integer value1, Integer value2) {
            addCriterion("agree between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andDegreeEqualTo(Integer value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(Integer value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(Integer value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(Integer value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(Integer value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<Integer> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<Integer> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(Integer value1, Integer value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andAbstainEqualTo(Integer value) {
            addCriterion("abstain =", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotEqualTo(Integer value) {
            addCriterion("abstain <>", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThan(Integer value) {
            addCriterion("abstain >", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThanOrEqualTo(Integer value) {
            addCriterion("abstain >=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThan(Integer value) {
            addCriterion("abstain <", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThanOrEqualTo(Integer value) {
            addCriterion("abstain <=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainIn(List<Integer> values) {
            addCriterion("abstain in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotIn(List<Integer> values) {
            addCriterion("abstain not in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainBetween(Integer value1, Integer value2) {
            addCriterion("abstain between", value1, value2, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotBetween(Integer value1, Integer value2) {
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