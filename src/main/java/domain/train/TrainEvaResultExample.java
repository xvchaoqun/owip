package domain.train;

import java.util.ArrayList;
import java.util.List;

public class TrainEvaResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TrainEvaResultExample() {
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

        public Criteria andEvaTableIdIsNull() {
            addCriterion("eva_table_id is null");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdIsNotNull() {
            addCriterion("eva_table_id is not null");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdEqualTo(Integer value) {
            addCriterion("eva_table_id =", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotEqualTo(Integer value) {
            addCriterion("eva_table_id <>", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdGreaterThan(Integer value) {
            addCriterion("eva_table_id >", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("eva_table_id >=", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdLessThan(Integer value) {
            addCriterion("eva_table_id <", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdLessThanOrEqualTo(Integer value) {
            addCriterion("eva_table_id <=", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdIn(List<Integer> values) {
            addCriterion("eva_table_id in", values, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotIn(List<Integer> values) {
            addCriterion("eva_table_id not in", values, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdBetween(Integer value1, Integer value2) {
            addCriterion("eva_table_id between", value1, value2, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("eva_table_id not between", value1, value2, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andNormIdIsNull() {
            addCriterion("norm_id is null");
            return (Criteria) this;
        }

        public Criteria andNormIdIsNotNull() {
            addCriterion("norm_id is not null");
            return (Criteria) this;
        }

        public Criteria andNormIdEqualTo(Integer value) {
            addCriterion("norm_id =", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotEqualTo(Integer value) {
            addCriterion("norm_id <>", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdGreaterThan(Integer value) {
            addCriterion("norm_id >", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("norm_id >=", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdLessThan(Integer value) {
            addCriterion("norm_id <", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdLessThanOrEqualTo(Integer value) {
            addCriterion("norm_id <=", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdIn(List<Integer> values) {
            addCriterion("norm_id in", values, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotIn(List<Integer> values) {
            addCriterion("norm_id not in", values, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdBetween(Integer value1, Integer value2) {
            addCriterion("norm_id between", value1, value2, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotBetween(Integer value1, Integer value2) {
            addCriterion("norm_id not between", value1, value2, "normId");
            return (Criteria) this;
        }

        public Criteria andRankIdIsNull() {
            addCriterion("rank_id is null");
            return (Criteria) this;
        }

        public Criteria andRankIdIsNotNull() {
            addCriterion("rank_id is not null");
            return (Criteria) this;
        }

        public Criteria andRankIdEqualTo(Integer value) {
            addCriterion("rank_id =", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdNotEqualTo(Integer value) {
            addCriterion("rank_id <>", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdGreaterThan(Integer value) {
            addCriterion("rank_id >", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank_id >=", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdLessThan(Integer value) {
            addCriterion("rank_id <", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdLessThanOrEqualTo(Integer value) {
            addCriterion("rank_id <=", value, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdIn(List<Integer> values) {
            addCriterion("rank_id in", values, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdNotIn(List<Integer> values) {
            addCriterion("rank_id not in", values, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdBetween(Integer value1, Integer value2) {
            addCriterion("rank_id between", value1, value2, "rankId");
            return (Criteria) this;
        }

        public Criteria andRankIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rank_id not between", value1, value2, "rankId");
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