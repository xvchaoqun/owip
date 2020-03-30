package domain.dr;

import java.util.ArrayList;
import java.util.List;

public class DrOnlineResultViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOnlineResultViewExample() {
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

        public Criteria andOnlineIdIsNull() {
            addCriterion("online_id is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIsNotNull() {
            addCriterion("online_id is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdEqualTo(Integer value) {
            addCriterion("online_id =", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotEqualTo(Integer value) {
            addCriterion("online_id <>", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThan(Integer value) {
            addCriterion("online_id >", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_id >=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThan(Integer value) {
            addCriterion("online_id <", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThanOrEqualTo(Integer value) {
            addCriterion("online_id <=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIn(List<Integer> values) {
            addCriterion("online_id in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotIn(List<Integer> values) {
            addCriterion("online_id not in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdBetween(Integer value1, Integer value2) {
            addCriterion("online_id between", value1, value2, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("online_id not between", value1, value2, "onlineId");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNull() {
            addCriterion("post_id is null");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNotNull() {
            addCriterion("post_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdEqualTo(Integer value) {
            addCriterion("post_id =", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotEqualTo(Integer value) {
            addCriterion("post_id <>", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThan(Integer value) {
            addCriterion("post_id >", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_id >=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThan(Integer value) {
            addCriterion("post_id <", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_id <=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdIn(List<Integer> values) {
            addCriterion("post_id in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotIn(List<Integer> values) {
            addCriterion("post_id not in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdBetween(Integer value1, Integer value2) {
            addCriterion("post_id between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_id not between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdIsNull() {
            addCriterion("candidate_id is null");
            return (Criteria) this;
        }

        public Criteria andCandidateIdIsNotNull() {
            addCriterion("candidate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateIdEqualTo(Integer value) {
            addCriterion("candidate_id =", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotEqualTo(Integer value) {
            addCriterion("candidate_id <>", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdGreaterThan(Integer value) {
            addCriterion("candidate_id >", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("candidate_id >=", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdLessThan(Integer value) {
            addCriterion("candidate_id <", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdLessThanOrEqualTo(Integer value) {
            addCriterion("candidate_id <=", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdIn(List<Integer> values) {
            addCriterion("candidate_id in", values, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotIn(List<Integer> values) {
            addCriterion("candidate_id not in", values, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdBetween(Integer value1, Integer value2) {
            addCriterion("candidate_id between", value1, value2, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("candidate_id not between", value1, value2, "candidateId");
            return (Criteria) this;
        }

        public Criteria andOptionSumIsNull() {
            addCriterion("option_sum is null");
            return (Criteria) this;
        }

        public Criteria andOptionSumIsNotNull() {
            addCriterion("option_sum is not null");
            return (Criteria) this;
        }

        public Criteria andOptionSumEqualTo(Integer value) {
            addCriterion("option_sum =", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumNotEqualTo(Integer value) {
            addCriterion("option_sum <>", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumGreaterThan(Integer value) {
            addCriterion("option_sum >", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("option_sum >=", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumLessThan(Integer value) {
            addCriterion("option_sum <", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumLessThanOrEqualTo(Integer value) {
            addCriterion("option_sum <=", value, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumIn(List<Integer> values) {
            addCriterion("option_sum in", values, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumNotIn(List<Integer> values) {
            addCriterion("option_sum not in", values, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumBetween(Integer value1, Integer value2) {
            addCriterion("option_sum between", value1, value2, "optionSum");
            return (Criteria) this;
        }

        public Criteria andOptionSumNotBetween(Integer value1, Integer value2) {
            addCriterion("option_sum not between", value1, value2, "optionSum");
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

        public Criteria andPubCountsIsNull() {
            addCriterion("pub_counts is null");
            return (Criteria) this;
        }

        public Criteria andPubCountsIsNotNull() {
            addCriterion("pub_counts is not null");
            return (Criteria) this;
        }

        public Criteria andPubCountsEqualTo(Integer value) {
            addCriterion("pub_counts =", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsNotEqualTo(Integer value) {
            addCriterion("pub_counts <>", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsGreaterThan(Integer value) {
            addCriterion("pub_counts >", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_counts >=", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsLessThan(Integer value) {
            addCriterion("pub_counts <", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsLessThanOrEqualTo(Integer value) {
            addCriterion("pub_counts <=", value, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsIn(List<Integer> values) {
            addCriterion("pub_counts in", values, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsNotIn(List<Integer> values) {
            addCriterion("pub_counts not in", values, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsBetween(Integer value1, Integer value2) {
            addCriterion("pub_counts between", value1, value2, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andPubCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_counts not between", value1, value2, "pubCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsIsNull() {
            addCriterion("finish_counts is null");
            return (Criteria) this;
        }

        public Criteria andFinishCountsIsNotNull() {
            addCriterion("finish_counts is not null");
            return (Criteria) this;
        }

        public Criteria andFinishCountsEqualTo(Integer value) {
            addCriterion("finish_counts =", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsNotEqualTo(Integer value) {
            addCriterion("finish_counts <>", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsGreaterThan(Integer value) {
            addCriterion("finish_counts >", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_counts >=", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsLessThan(Integer value) {
            addCriterion("finish_counts <", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsLessThanOrEqualTo(Integer value) {
            addCriterion("finish_counts <=", value, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsIn(List<Integer> values) {
            addCriterion("finish_counts in", values, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsNotIn(List<Integer> values) {
            addCriterion("finish_counts not in", values, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsBetween(Integer value1, Integer value2) {
            addCriterion("finish_counts between", value1, value2, "finishCounts");
            return (Criteria) this;
        }

        public Criteria andFinishCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_counts not between", value1, value2, "finishCounts");
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