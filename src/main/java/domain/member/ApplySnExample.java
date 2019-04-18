package domain.member;

import java.util.ArrayList;
import java.util.List;

public class ApplySnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApplySnExample() {
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

        public Criteria andSnIsNull() {
            addCriterion("sn is null");
            return (Criteria) this;
        }

        public Criteria andSnIsNotNull() {
            addCriterion("sn is not null");
            return (Criteria) this;
        }

        public Criteria andSnEqualTo(Long value) {
            addCriterion("sn =", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotEqualTo(Long value) {
            addCriterion("sn <>", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThan(Long value) {
            addCriterion("sn >", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThanOrEqualTo(Long value) {
            addCriterion("sn >=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThan(Long value) {
            addCriterion("sn <", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThanOrEqualTo(Long value) {
            addCriterion("sn <=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnIn(List<Long> values) {
            addCriterion("sn in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotIn(List<Long> values) {
            addCriterion("sn not in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnBetween(Long value1, Long value2) {
            addCriterion("sn between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotBetween(Long value1, Long value2) {
            addCriterion("sn not between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andRangeIdIsNull() {
            addCriterion("range_id is null");
            return (Criteria) this;
        }

        public Criteria andRangeIdIsNotNull() {
            addCriterion("range_id is not null");
            return (Criteria) this;
        }

        public Criteria andRangeIdEqualTo(Integer value) {
            addCriterion("range_id =", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdNotEqualTo(Integer value) {
            addCriterion("range_id <>", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdGreaterThan(Integer value) {
            addCriterion("range_id >", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("range_id >=", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdLessThan(Integer value) {
            addCriterion("range_id <", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdLessThanOrEqualTo(Integer value) {
            addCriterion("range_id <=", value, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdIn(List<Integer> values) {
            addCriterion("range_id in", values, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdNotIn(List<Integer> values) {
            addCriterion("range_id not in", values, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdBetween(Integer value1, Integer value2) {
            addCriterion("range_id between", value1, value2, "rangeId");
            return (Criteria) this;
        }

        public Criteria andRangeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("range_id not between", value1, value2, "rangeId");
            return (Criteria) this;
        }

        public Criteria andIsUsedIsNull() {
            addCriterion("is_used is null");
            return (Criteria) this;
        }

        public Criteria andIsUsedIsNotNull() {
            addCriterion("is_used is not null");
            return (Criteria) this;
        }

        public Criteria andIsUsedEqualTo(Boolean value) {
            addCriterion("is_used =", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedNotEqualTo(Boolean value) {
            addCriterion("is_used <>", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedGreaterThan(Boolean value) {
            addCriterion("is_used >", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_used >=", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedLessThan(Boolean value) {
            addCriterion("is_used <", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_used <=", value, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedIn(List<Boolean> values) {
            addCriterion("is_used in", values, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedNotIn(List<Boolean> values) {
            addCriterion("is_used not in", values, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used between", value1, value2, "isUsed");
            return (Criteria) this;
        }

        public Criteria andIsUsedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used not between", value1, value2, "isUsed");
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