package domain.ext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExtRetireSalaryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtRetireSalaryExample() {
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

        public Criteria andZghIsNull() {
            addCriterion("zgh is null");
            return (Criteria) this;
        }

        public Criteria andZghIsNotNull() {
            addCriterion("zgh is not null");
            return (Criteria) this;
        }

        public Criteria andZghEqualTo(String value) {
            addCriterion("zgh =", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotEqualTo(String value) {
            addCriterion("zgh <>", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghGreaterThan(String value) {
            addCriterion("zgh >", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghGreaterThanOrEqualTo(String value) {
            addCriterion("zgh >=", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLessThan(String value) {
            addCriterion("zgh <", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLessThanOrEqualTo(String value) {
            addCriterion("zgh <=", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLike(String value) {
            addCriterion("zgh like", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotLike(String value) {
            addCriterion("zgh not like", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghIn(List<String> values) {
            addCriterion("zgh in", values, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotIn(List<String> values) {
            addCriterion("zgh not in", values, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghBetween(String value1, String value2) {
            addCriterion("zgh between", value1, value2, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotBetween(String value1, String value2) {
            addCriterion("zgh not between", value1, value2, "zgh");
            return (Criteria) this;
        }

        public Criteria andLtxfIsNull() {
            addCriterion("ltxf is null");
            return (Criteria) this;
        }

        public Criteria andLtxfIsNotNull() {
            addCriterion("ltxf is not null");
            return (Criteria) this;
        }

        public Criteria andLtxfEqualTo(BigDecimal value) {
            addCriterion("ltxf =", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfNotEqualTo(BigDecimal value) {
            addCriterion("ltxf <>", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfGreaterThan(BigDecimal value) {
            addCriterion("ltxf >", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ltxf >=", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfLessThan(BigDecimal value) {
            addCriterion("ltxf <", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ltxf <=", value, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfIn(List<BigDecimal> values) {
            addCriterion("ltxf in", values, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfNotIn(List<BigDecimal> values) {
            addCriterion("ltxf not in", values, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ltxf between", value1, value2, "ltxf");
            return (Criteria) this;
        }

        public Criteria andLtxfNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ltxf not between", value1, value2, "ltxf");
            return (Criteria) this;
        }

        public Criteria andRqIsNull() {
            addCriterion("rq is null");
            return (Criteria) this;
        }

        public Criteria andRqIsNotNull() {
            addCriterion("rq is not null");
            return (Criteria) this;
        }

        public Criteria andRqEqualTo(String value) {
            addCriterion("rq =", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotEqualTo(String value) {
            addCriterion("rq <>", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqGreaterThan(String value) {
            addCriterion("rq >", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqGreaterThanOrEqualTo(String value) {
            addCriterion("rq >=", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLessThan(String value) {
            addCriterion("rq <", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLessThanOrEqualTo(String value) {
            addCriterion("rq <=", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLike(String value) {
            addCriterion("rq like", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotLike(String value) {
            addCriterion("rq not like", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqIn(List<String> values) {
            addCriterion("rq in", values, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotIn(List<String> values) {
            addCriterion("rq not in", values, "rq");
            return (Criteria) this;
        }

        public Criteria andRqBetween(String value1, String value2) {
            addCriterion("rq between", value1, value2, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotBetween(String value1, String value2) {
            addCriterion("rq not between", value1, value2, "rq");
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