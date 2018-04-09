package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CetTrainCourseObjResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTrainCourseObjResultExample() {
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

        public Criteria andTrainCourseObjIdIsNull() {
            addCriterion("train_course_obj_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdIsNotNull() {
            addCriterion("train_course_obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdEqualTo(Integer value) {
            addCriterion("train_course_obj_id =", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdNotEqualTo(Integer value) {
            addCriterion("train_course_obj_id <>", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdGreaterThan(Integer value) {
            addCriterion("train_course_obj_id >", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_course_obj_id >=", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdLessThan(Integer value) {
            addCriterion("train_course_obj_id <", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_course_obj_id <=", value, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdIn(List<Integer> values) {
            addCriterion("train_course_obj_id in", values, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdNotIn(List<Integer> values) {
            addCriterion("train_course_obj_id not in", values, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdBetween(Integer value1, Integer value2) {
            addCriterion("train_course_obj_id between", value1, value2, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseObjIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_course_obj_id not between", value1, value2, "trainCourseObjId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdIsNull() {
            addCriterion("course_item_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdIsNotNull() {
            addCriterion("course_item_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdEqualTo(Integer value) {
            addCriterion("course_item_id =", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdNotEqualTo(Integer value) {
            addCriterion("course_item_id <>", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdGreaterThan(Integer value) {
            addCriterion("course_item_id >", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_item_id >=", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdLessThan(Integer value) {
            addCriterion("course_item_id <", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_item_id <=", value, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdIn(List<Integer> values) {
            addCriterion("course_item_id in", values, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdNotIn(List<Integer> values) {
            addCriterion("course_item_id not in", values, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdBetween(Integer value1, Integer value2) {
            addCriterion("course_item_id between", value1, value2, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseItemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_item_id not between", value1, value2, "courseItemId");
            return (Criteria) this;
        }

        public Criteria andCourseNumIsNull() {
            addCriterion("course_num is null");
            return (Criteria) this;
        }

        public Criteria andCourseNumIsNotNull() {
            addCriterion("course_num is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNumEqualTo(Integer value) {
            addCriterion("course_num =", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotEqualTo(Integer value) {
            addCriterion("course_num <>", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumGreaterThan(Integer value) {
            addCriterion("course_num >", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_num >=", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumLessThan(Integer value) {
            addCriterion("course_num <", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumLessThanOrEqualTo(Integer value) {
            addCriterion("course_num <=", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumIn(List<Integer> values) {
            addCriterion("course_num in", values, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotIn(List<Integer> values) {
            addCriterion("course_num not in", values, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumBetween(Integer value1, Integer value2) {
            addCriterion("course_num between", value1, value2, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotBetween(Integer value1, Integer value2) {
            addCriterion("course_num not between", value1, value2, "courseNum");
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