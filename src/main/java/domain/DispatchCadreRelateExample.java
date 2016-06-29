package domain;

import java.util.ArrayList;
import java.util.List;

public class DispatchCadreRelateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DispatchCadreRelateExample() {
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

        public Criteria andDispatchCadreIdIsNull() {
            addCriterion("dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNotNull() {
            addCriterion("dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id =", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <>", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("dispatch_cadre_id >", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id >=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThan(Integer value) {
            addCriterion("dispatch_cadre_id <", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id not in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id not between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andRelateIdIsNull() {
            addCriterion("relate_id is null");
            return (Criteria) this;
        }

        public Criteria andRelateIdIsNotNull() {
            addCriterion("relate_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelateIdEqualTo(Integer value) {
            addCriterion("relate_id =", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotEqualTo(Integer value) {
            addCriterion("relate_id <>", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdGreaterThan(Integer value) {
            addCriterion("relate_id >", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("relate_id >=", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdLessThan(Integer value) {
            addCriterion("relate_id <", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdLessThanOrEqualTo(Integer value) {
            addCriterion("relate_id <=", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdIn(List<Integer> values) {
            addCriterion("relate_id in", values, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotIn(List<Integer> values) {
            addCriterion("relate_id not in", values, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdBetween(Integer value1, Integer value2) {
            addCriterion("relate_id between", value1, value2, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("relate_id not between", value1, value2, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIsNull() {
            addCriterion("relate_type is null");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIsNotNull() {
            addCriterion("relate_type is not null");
            return (Criteria) this;
        }

        public Criteria andRelateTypeEqualTo(Byte value) {
            addCriterion("relate_type =", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotEqualTo(Byte value) {
            addCriterion("relate_type <>", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeGreaterThan(Byte value) {
            addCriterion("relate_type >", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("relate_type >=", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeLessThan(Byte value) {
            addCriterion("relate_type <", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("relate_type <=", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIn(List<Byte> values) {
            addCriterion("relate_type in", values, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotIn(List<Byte> values) {
            addCriterion("relate_type not in", values, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeBetween(Byte value1, Byte value2) {
            addCriterion("relate_type between", value1, value2, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("relate_type not between", value1, value2, "relateType");
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