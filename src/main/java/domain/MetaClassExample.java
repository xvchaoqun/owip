package domain;

import java.util.ArrayList;
import java.util.List;

public class MetaClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MetaClassExample() {
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

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
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

        public Criteria andFirstLevelIsNull() {
            addCriterion("first_level is null");
            return (Criteria) this;
        }

        public Criteria andFirstLevelIsNotNull() {
            addCriterion("first_level is not null");
            return (Criteria) this;
        }

        public Criteria andFirstLevelEqualTo(String value) {
            addCriterion("first_level =", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelNotEqualTo(String value) {
            addCriterion("first_level <>", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelGreaterThan(String value) {
            addCriterion("first_level >", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelGreaterThanOrEqualTo(String value) {
            addCriterion("first_level >=", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLessThan(String value) {
            addCriterion("first_level <", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLessThanOrEqualTo(String value) {
            addCriterion("first_level <=", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLike(String value) {
            addCriterion("first_level like", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelNotLike(String value) {
            addCriterion("first_level not like", value, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelIn(List<String> values) {
            addCriterion("first_level in", values, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelNotIn(List<String> values) {
            addCriterion("first_level not in", values, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelBetween(String value1, String value2) {
            addCriterion("first_level between", value1, value2, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andFirstLevelNotBetween(String value1, String value2) {
            addCriterion("first_level not between", value1, value2, "firstLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelIsNull() {
            addCriterion("second_level is null");
            return (Criteria) this;
        }

        public Criteria andSecondLevelIsNotNull() {
            addCriterion("second_level is not null");
            return (Criteria) this;
        }

        public Criteria andSecondLevelEqualTo(String value) {
            addCriterion("second_level =", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelNotEqualTo(String value) {
            addCriterion("second_level <>", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelGreaterThan(String value) {
            addCriterion("second_level >", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelGreaterThanOrEqualTo(String value) {
            addCriterion("second_level >=", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelLessThan(String value) {
            addCriterion("second_level <", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelLessThanOrEqualTo(String value) {
            addCriterion("second_level <=", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelLike(String value) {
            addCriterion("second_level like", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelNotLike(String value) {
            addCriterion("second_level not like", value, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelIn(List<String> values) {
            addCriterion("second_level in", values, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelNotIn(List<String> values) {
            addCriterion("second_level not in", values, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelBetween(String value1, String value2) {
            addCriterion("second_level between", value1, value2, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andSecondLevelNotBetween(String value1, String value2) {
            addCriterion("second_level not between", value1, value2, "secondLevel");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andBoolAttrIsNull() {
            addCriterion("bool_attr is null");
            return (Criteria) this;
        }

        public Criteria andBoolAttrIsNotNull() {
            addCriterion("bool_attr is not null");
            return (Criteria) this;
        }

        public Criteria andBoolAttrEqualTo(String value) {
            addCriterion("bool_attr =", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrNotEqualTo(String value) {
            addCriterion("bool_attr <>", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrGreaterThan(String value) {
            addCriterion("bool_attr >", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrGreaterThanOrEqualTo(String value) {
            addCriterion("bool_attr >=", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrLessThan(String value) {
            addCriterion("bool_attr <", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrLessThanOrEqualTo(String value) {
            addCriterion("bool_attr <=", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrLike(String value) {
            addCriterion("bool_attr like", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrNotLike(String value) {
            addCriterion("bool_attr not like", value, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrIn(List<String> values) {
            addCriterion("bool_attr in", values, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrNotIn(List<String> values) {
            addCriterion("bool_attr not in", values, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrBetween(String value1, String value2) {
            addCriterion("bool_attr between", value1, value2, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andBoolAttrNotBetween(String value1, String value2) {
            addCriterion("bool_attr not between", value1, value2, "boolAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrIsNull() {
            addCriterion("extra_attr is null");
            return (Criteria) this;
        }

        public Criteria andExtraAttrIsNotNull() {
            addCriterion("extra_attr is not null");
            return (Criteria) this;
        }

        public Criteria andExtraAttrEqualTo(String value) {
            addCriterion("extra_attr =", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrNotEqualTo(String value) {
            addCriterion("extra_attr <>", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrGreaterThan(String value) {
            addCriterion("extra_attr >", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrGreaterThanOrEqualTo(String value) {
            addCriterion("extra_attr >=", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrLessThan(String value) {
            addCriterion("extra_attr <", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrLessThanOrEqualTo(String value) {
            addCriterion("extra_attr <=", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrLike(String value) {
            addCriterion("extra_attr like", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrNotLike(String value) {
            addCriterion("extra_attr not like", value, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrIn(List<String> values) {
            addCriterion("extra_attr in", values, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrNotIn(List<String> values) {
            addCriterion("extra_attr not in", values, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrBetween(String value1, String value2) {
            addCriterion("extra_attr between", value1, value2, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andExtraAttrNotBetween(String value1, String value2) {
            addCriterion("extra_attr not between", value1, value2, "extraAttr");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNull() {
            addCriterion("available is null");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNotNull() {
            addCriterion("available is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableEqualTo(Boolean value) {
            addCriterion("available =", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotEqualTo(Boolean value) {
            addCriterion("available <>", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThan(Boolean value) {
            addCriterion("available >", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("available >=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThan(Boolean value) {
            addCriterion("available <", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThanOrEqualTo(Boolean value) {
            addCriterion("available <=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableIn(List<Boolean> values) {
            addCriterion("available in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotIn(List<Boolean> values) {
            addCriterion("available not in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableBetween(Boolean value1, Boolean value2) {
            addCriterion("available between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("available not between", value1, value2, "available");
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