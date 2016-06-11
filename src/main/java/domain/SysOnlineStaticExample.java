package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysOnlineStaticExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysOnlineStaticExample() {
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

        public Criteria andOnlineCountIsNull() {
            addCriterion("online_count is null");
            return (Criteria) this;
        }

        public Criteria andOnlineCountIsNotNull() {
            addCriterion("online_count is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineCountEqualTo(Integer value) {
            addCriterion("online_count =", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountNotEqualTo(Integer value) {
            addCriterion("online_count <>", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountGreaterThan(Integer value) {
            addCriterion("online_count >", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_count >=", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountLessThan(Integer value) {
            addCriterion("online_count <", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountLessThanOrEqualTo(Integer value) {
            addCriterion("online_count <=", value, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountIn(List<Integer> values) {
            addCriterion("online_count in", values, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountNotIn(List<Integer> values) {
            addCriterion("online_count not in", values, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountBetween(Integer value1, Integer value2) {
            addCriterion("online_count between", value1, value2, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andOnlineCountNotBetween(Integer value1, Integer value2) {
            addCriterion("online_count not between", value1, value2, "onlineCount");
            return (Criteria) this;
        }

        public Criteria andBksIsNull() {
            addCriterion("bks is null");
            return (Criteria) this;
        }

        public Criteria andBksIsNotNull() {
            addCriterion("bks is not null");
            return (Criteria) this;
        }

        public Criteria andBksEqualTo(Integer value) {
            addCriterion("bks =", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotEqualTo(Integer value) {
            addCriterion("bks <>", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksGreaterThan(Integer value) {
            addCriterion("bks >", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksGreaterThanOrEqualTo(Integer value) {
            addCriterion("bks >=", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksLessThan(Integer value) {
            addCriterion("bks <", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksLessThanOrEqualTo(Integer value) {
            addCriterion("bks <=", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksIn(List<Integer> values) {
            addCriterion("bks in", values, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotIn(List<Integer> values) {
            addCriterion("bks not in", values, "bks");
            return (Criteria) this;
        }

        public Criteria andBksBetween(Integer value1, Integer value2) {
            addCriterion("bks between", value1, value2, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotBetween(Integer value1, Integer value2) {
            addCriterion("bks not between", value1, value2, "bks");
            return (Criteria) this;
        }

        public Criteria andYjsIsNull() {
            addCriterion("yjs is null");
            return (Criteria) this;
        }

        public Criteria andYjsIsNotNull() {
            addCriterion("yjs is not null");
            return (Criteria) this;
        }

        public Criteria andYjsEqualTo(Integer value) {
            addCriterion("yjs =", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsNotEqualTo(Integer value) {
            addCriterion("yjs <>", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsGreaterThan(Integer value) {
            addCriterion("yjs >", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsGreaterThanOrEqualTo(Integer value) {
            addCriterion("yjs >=", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsLessThan(Integer value) {
            addCriterion("yjs <", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsLessThanOrEqualTo(Integer value) {
            addCriterion("yjs <=", value, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsIn(List<Integer> values) {
            addCriterion("yjs in", values, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsNotIn(List<Integer> values) {
            addCriterion("yjs not in", values, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsBetween(Integer value1, Integer value2) {
            addCriterion("yjs between", value1, value2, "yjs");
            return (Criteria) this;
        }

        public Criteria andYjsNotBetween(Integer value1, Integer value2) {
            addCriterion("yjs not between", value1, value2, "yjs");
            return (Criteria) this;
        }

        public Criteria andJzgIsNull() {
            addCriterion("jzg is null");
            return (Criteria) this;
        }

        public Criteria andJzgIsNotNull() {
            addCriterion("jzg is not null");
            return (Criteria) this;
        }

        public Criteria andJzgEqualTo(Integer value) {
            addCriterion("jzg =", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgNotEqualTo(Integer value) {
            addCriterion("jzg <>", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgGreaterThan(Integer value) {
            addCriterion("jzg >", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgGreaterThanOrEqualTo(Integer value) {
            addCriterion("jzg >=", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgLessThan(Integer value) {
            addCriterion("jzg <", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgLessThanOrEqualTo(Integer value) {
            addCriterion("jzg <=", value, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgIn(List<Integer> values) {
            addCriterion("jzg in", values, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgNotIn(List<Integer> values) {
            addCriterion("jzg not in", values, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgBetween(Integer value1, Integer value2) {
            addCriterion("jzg between", value1, value2, "jzg");
            return (Criteria) this;
        }

        public Criteria andJzgNotBetween(Integer value1, Integer value2) {
            addCriterion("jzg not between", value1, value2, "jzg");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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