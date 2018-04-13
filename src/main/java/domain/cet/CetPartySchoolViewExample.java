package domain.cet;

import java.util.ArrayList;
import java.util.List;

public class CetPartySchoolViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetPartySchoolViewExample() {
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

        public Criteria andPartySchoolIdIsNull() {
            addCriterion("party_school_id is null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdIsNotNull() {
            addCriterion("party_school_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdEqualTo(Integer value) {
            addCriterion("party_school_id =", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdNotEqualTo(Integer value) {
            addCriterion("party_school_id <>", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdGreaterThan(Integer value) {
            addCriterion("party_school_id >", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_school_id >=", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdLessThan(Integer value) {
            addCriterion("party_school_id <", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_school_id <=", value, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdIn(List<Integer> values) {
            addCriterion("party_school_id in", values, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdNotIn(List<Integer> values) {
            addCriterion("party_school_id not in", values, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("party_school_id between", value1, value2, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_school_id not between", value1, value2, "partySchoolId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameIsNull() {
            addCriterion("party_school_name is null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameIsNotNull() {
            addCriterion("party_school_name is not null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameEqualTo(String value) {
            addCriterion("party_school_name =", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameNotEqualTo(String value) {
            addCriterion("party_school_name <>", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameGreaterThan(String value) {
            addCriterion("party_school_name >", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("party_school_name >=", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameLessThan(String value) {
            addCriterion("party_school_name <", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameLessThanOrEqualTo(String value) {
            addCriterion("party_school_name <=", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameLike(String value) {
            addCriterion("party_school_name like", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameNotLike(String value) {
            addCriterion("party_school_name not like", value, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameIn(List<String> values) {
            addCriterion("party_school_name in", values, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameNotIn(List<String> values) {
            addCriterion("party_school_name not in", values, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameBetween(String value1, String value2) {
            addCriterion("party_school_name between", value1, value2, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolNameNotBetween(String value1, String value2) {
            addCriterion("party_school_name not between", value1, value2, "partySchoolName");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryIsNull() {
            addCriterion("party_school_is_history is null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryIsNotNull() {
            addCriterion("party_school_is_history is not null");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryEqualTo(Boolean value) {
            addCriterion("party_school_is_history =", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryNotEqualTo(Boolean value) {
            addCriterion("party_school_is_history <>", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryGreaterThan(Boolean value) {
            addCriterion("party_school_is_history >", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("party_school_is_history >=", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryLessThan(Boolean value) {
            addCriterion("party_school_is_history <", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryLessThanOrEqualTo(Boolean value) {
            addCriterion("party_school_is_history <=", value, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryIn(List<Boolean> values) {
            addCriterion("party_school_is_history in", values, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryNotIn(List<Boolean> values) {
            addCriterion("party_school_is_history not in", values, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryBetween(Boolean value1, Boolean value2) {
            addCriterion("party_school_is_history between", value1, value2, "partySchoolIsHistory");
            return (Criteria) this;
        }

        public Criteria andPartySchoolIsHistoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("party_school_is_history not between", value1, value2, "partySchoolIsHistory");
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