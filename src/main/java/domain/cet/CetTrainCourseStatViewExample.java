package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CetTrainCourseStatViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTrainCourseStatViewExample() {
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

        public Criteria andTrainCourseIdIsNull() {
            addCriterion("train_course_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdIsNotNull() {
            addCriterion("train_course_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdEqualTo(Integer value) {
            addCriterion("train_course_id =", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotEqualTo(Integer value) {
            addCriterion("train_course_id <>", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdGreaterThan(Integer value) {
            addCriterion("train_course_id >", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_course_id >=", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdLessThan(Integer value) {
            addCriterion("train_course_id <", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_course_id <=", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdIn(List<Integer> values) {
            addCriterion("train_course_id in", values, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotIn(List<Integer> values) {
            addCriterion("train_course_id not in", values, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("train_course_id between", value1, value2, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_course_id not between", value1, value2, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andTrainIdIsNull() {
            addCriterion("train_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainIdIsNotNull() {
            addCriterion("train_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainIdEqualTo(Integer value) {
            addCriterion("train_id =", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotEqualTo(Integer value) {
            addCriterion("train_id <>", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThan(Integer value) {
            addCriterion("train_id >", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_id >=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThan(Integer value) {
            addCriterion("train_id <", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_id <=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdIn(List<Integer> values) {
            addCriterion("train_id in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotIn(List<Integer> values) {
            addCriterion("train_id not in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdBetween(Integer value1, Integer value2) {
            addCriterion("train_id between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_id not between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSelectedCountIsNull() {
            addCriterion("selected_count is null");
            return (Criteria) this;
        }

        public Criteria andSelectedCountIsNotNull() {
            addCriterion("selected_count is not null");
            return (Criteria) this;
        }

        public Criteria andSelectedCountEqualTo(Integer value) {
            addCriterion("selected_count =", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotEqualTo(Integer value) {
            addCriterion("selected_count <>", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountGreaterThan(Integer value) {
            addCriterion("selected_count >", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("selected_count >=", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountLessThan(Integer value) {
            addCriterion("selected_count <", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountLessThanOrEqualTo(Integer value) {
            addCriterion("selected_count <=", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountIn(List<Integer> values) {
            addCriterion("selected_count in", values, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotIn(List<Integer> values) {
            addCriterion("selected_count not in", values, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountBetween(Integer value1, Integer value2) {
            addCriterion("selected_count between", value1, value2, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("selected_count not between", value1, value2, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNull() {
            addCriterion("finish_count is null");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNotNull() {
            addCriterion("finish_count is not null");
            return (Criteria) this;
        }

        public Criteria andFinishCountEqualTo(Integer value) {
            addCriterion("finish_count =", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotEqualTo(Integer value) {
            addCriterion("finish_count <>", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThan(Integer value) {
            addCriterion("finish_count >", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_count >=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThan(Integer value) {
            addCriterion("finish_count <", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThanOrEqualTo(Integer value) {
            addCriterion("finish_count <=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIn(List<Integer> values) {
            addCriterion("finish_count in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotIn(List<Integer> values) {
            addCriterion("finish_count not in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountBetween(Integer value1, Integer value2) {
            addCriterion("finish_count between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_count not between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIsNull() {
            addCriterion("eva_finish_count is null");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIsNotNull() {
            addCriterion("eva_finish_count is not null");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountEqualTo(Integer value) {
            addCriterion("eva_finish_count =", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotEqualTo(Integer value) {
            addCriterion("eva_finish_count <>", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountGreaterThan(Integer value) {
            addCriterion("eva_finish_count >", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("eva_finish_count >=", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountLessThan(Integer value) {
            addCriterion("eva_finish_count <", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountLessThanOrEqualTo(Integer value) {
            addCriterion("eva_finish_count <=", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIn(List<Integer> values) {
            addCriterion("eva_finish_count in", values, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotIn(List<Integer> values) {
            addCriterion("eva_finish_count not in", values, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountBetween(Integer value1, Integer value2) {
            addCriterion("eva_finish_count between", value1, value2, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotBetween(Integer value1, Integer value2) {
            addCriterion("eva_finish_count not between", value1, value2, "evaFinishCount");
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