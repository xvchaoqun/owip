package domain.dr;

import java.util.ArrayList;
import java.util.List;

public class DrOnlineResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOnlineResultExample() {
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

        public Criteria andInspectorIdIsNull() {
            addCriterion("inspector_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectorIdIsNotNull() {
            addCriterion("inspector_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorIdEqualTo(Integer value) {
            addCriterion("inspector_id =", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdNotEqualTo(Integer value) {
            addCriterion("inspector_id <>", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdGreaterThan(Integer value) {
            addCriterion("inspector_id >", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspector_id >=", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdLessThan(Integer value) {
            addCriterion("inspector_id <", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspector_id <=", value, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdIn(List<Integer> values) {
            addCriterion("inspector_id in", values, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdNotIn(List<Integer> values) {
            addCriterion("inspector_id not in", values, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdBetween(Integer value1, Integer value2) {
            addCriterion("inspector_id between", value1, value2, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspector_id not between", value1, value2, "inspectorId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdIsNull() {
            addCriterion("inspector_type_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdIsNotNull() {
            addCriterion("inspector_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdEqualTo(Integer value) {
            addCriterion("inspector_type_id =", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdNotEqualTo(Integer value) {
            addCriterion("inspector_type_id <>", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdGreaterThan(Integer value) {
            addCriterion("inspector_type_id >", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspector_type_id >=", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdLessThan(Integer value) {
            addCriterion("inspector_type_id <", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspector_type_id <=", value, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdIn(List<Integer> values) {
            addCriterion("inspector_type_id in", values, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdNotIn(List<Integer> values) {
            addCriterion("inspector_type_id not in", values, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("inspector_type_id between", value1, value2, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspector_type_id not between", value1, value2, "inspectorTypeId");
            return (Criteria) this;
        }

        public Criteria andInsOptionIsNull() {
            addCriterion("ins_option is null");
            return (Criteria) this;
        }

        public Criteria andInsOptionIsNotNull() {
            addCriterion("ins_option is not null");
            return (Criteria) this;
        }

        public Criteria andInsOptionEqualTo(Boolean value) {
            addCriterion("ins_option =", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionNotEqualTo(Boolean value) {
            addCriterion("ins_option <>", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionGreaterThan(Boolean value) {
            addCriterion("ins_option >", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ins_option >=", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionLessThan(Boolean value) {
            addCriterion("ins_option <", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionLessThanOrEqualTo(Boolean value) {
            addCriterion("ins_option <=", value, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionIn(List<Boolean> values) {
            addCriterion("ins_option in", values, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionNotIn(List<Boolean> values) {
            addCriterion("ins_option not in", values, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionBetween(Boolean value1, Boolean value2) {
            addCriterion("ins_option between", value1, value2, "insOption");
            return (Criteria) this;
        }

        public Criteria andInsOptionNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ins_option not between", value1, value2, "insOption");
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