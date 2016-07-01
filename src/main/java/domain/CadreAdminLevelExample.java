package domain;

import java.util.ArrayList;
import java.util.List;

public class CadreAdminLevelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreAdminLevelExample() {
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

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdIsNull() {
            addCriterion("admin_level_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdIsNotNull() {
            addCriterion("admin_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdEqualTo(Integer value) {
            addCriterion("admin_level_id =", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotEqualTo(Integer value) {
            addCriterion("admin_level_id <>", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdGreaterThan(Integer value) {
            addCriterion("admin_level_id >", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level_id >=", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdLessThan(Integer value) {
            addCriterion("admin_level_id <", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level_id <=", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdIn(List<Integer> values) {
            addCriterion("admin_level_id in", values, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotIn(List<Integer> values) {
            addCriterion("admin_level_id not in", values, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_id between", value1, value2, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_id not between", value1, value2, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIsNull() {
            addCriterion("start_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIsNotNull() {
            addCriterion("start_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id =", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id <>", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("start_dispatch_cadre_id >", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id >=", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdLessThan(Integer value) {
            addCriterion("start_dispatch_cadre_id <", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id <=", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIn(List<Integer> values) {
            addCriterion("start_dispatch_cadre_id in", values, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("start_dispatch_cadre_id not in", values, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("start_dispatch_cadre_id between", value1, value2, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("start_dispatch_cadre_id not between", value1, value2, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIsNull() {
            addCriterion("end_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIsNotNull() {
            addCriterion("end_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id =", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id <>", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("end_dispatch_cadre_id >", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id >=", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdLessThan(Integer value) {
            addCriterion("end_dispatch_cadre_id <", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id <=", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIn(List<Integer> values) {
            addCriterion("end_dispatch_cadre_id in", values, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("end_dispatch_cadre_id not in", values, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("end_dispatch_cadre_id between", value1, value2, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("end_dispatch_cadre_id not between", value1, value2, "endDispatchCadreId");
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