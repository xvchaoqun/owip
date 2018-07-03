package domain.pmd;

import java.util.ArrayList;
import java.util.List;

public class PmdPayBranchViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdPayBranchViewExample() {
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

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andMonthIdIsNull() {
            addCriterion("month_id is null");
            return (Criteria) this;
        }

        public Criteria andMonthIdIsNotNull() {
            addCriterion("month_id is not null");
            return (Criteria) this;
        }

        public Criteria andMonthIdEqualTo(Integer value) {
            addCriterion("month_id =", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotEqualTo(Integer value) {
            addCriterion("month_id <>", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThan(Integer value) {
            addCriterion("month_id >", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("month_id >=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThan(Integer value) {
            addCriterion("month_id <", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThanOrEqualTo(Integer value) {
            addCriterion("month_id <=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdIn(List<Integer> values) {
            addCriterion("month_id in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotIn(List<Integer> values) {
            addCriterion("month_id not in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdBetween(Integer value1, Integer value2) {
            addCriterion("month_id between", value1, value2, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotBetween(Integer value1, Integer value2) {
            addCriterion("month_id not between", value1, value2, "monthId");
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

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andAdminCountIsNull() {
            addCriterion("admin_count is null");
            return (Criteria) this;
        }

        public Criteria andAdminCountIsNotNull() {
            addCriterion("admin_count is not null");
            return (Criteria) this;
        }

        public Criteria andAdminCountEqualTo(Integer value) {
            addCriterion("admin_count =", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountNotEqualTo(Integer value) {
            addCriterion("admin_count <>", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountGreaterThan(Integer value) {
            addCriterion("admin_count >", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_count >=", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountLessThan(Integer value) {
            addCriterion("admin_count <", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountLessThanOrEqualTo(Integer value) {
            addCriterion("admin_count <=", value, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountIn(List<Integer> values) {
            addCriterion("admin_count in", values, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountNotIn(List<Integer> values) {
            addCriterion("admin_count not in", values, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountBetween(Integer value1, Integer value2) {
            addCriterion("admin_count between", value1, value2, "adminCount");
            return (Criteria) this;
        }

        public Criteria andAdminCountNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_count not between", value1, value2, "adminCount");
            return (Criteria) this;
        }

        public Criteria andPartyNameIsNull() {
            addCriterion("party_name is null");
            return (Criteria) this;
        }

        public Criteria andPartyNameIsNotNull() {
            addCriterion("party_name is not null");
            return (Criteria) this;
        }

        public Criteria andPartyNameEqualTo(String value) {
            addCriterion("party_name =", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotEqualTo(String value) {
            addCriterion("party_name <>", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameGreaterThan(String value) {
            addCriterion("party_name >", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameGreaterThanOrEqualTo(String value) {
            addCriterion("party_name >=", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLessThan(String value) {
            addCriterion("party_name <", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLessThanOrEqualTo(String value) {
            addCriterion("party_name <=", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLike(String value) {
            addCriterion("party_name like", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotLike(String value) {
            addCriterion("party_name not like", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameIn(List<String> values) {
            addCriterion("party_name in", values, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotIn(List<String> values) {
            addCriterion("party_name not in", values, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameBetween(String value1, String value2) {
            addCriterion("party_name between", value1, value2, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotBetween(String value1, String value2) {
            addCriterion("party_name not between", value1, value2, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteIsNull() {
            addCriterion("party_is_delete is null");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteIsNotNull() {
            addCriterion("party_is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteEqualTo(Boolean value) {
            addCriterion("party_is_delete =", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteNotEqualTo(Boolean value) {
            addCriterion("party_is_delete <>", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteGreaterThan(Boolean value) {
            addCriterion("party_is_delete >", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("party_is_delete >=", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteLessThan(Boolean value) {
            addCriterion("party_is_delete <", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("party_is_delete <=", value, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteIn(List<Boolean> values) {
            addCriterion("party_is_delete in", values, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteNotIn(List<Boolean> values) {
            addCriterion("party_is_delete not in", values, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("party_is_delete between", value1, value2, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartyIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("party_is_delete not between", value1, value2, "partyIsDelete");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNull() {
            addCriterion("party_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNotNull() {
            addCriterion("party_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderEqualTo(Integer value) {
            addCriterion("party_sort_order =", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotEqualTo(Integer value) {
            addCriterion("party_sort_order <>", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThan(Integer value) {
            addCriterion("party_sort_order >", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order >=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThan(Integer value) {
            addCriterion("party_sort_order <", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order <=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIn(List<Integer> values) {
            addCriterion("party_sort_order in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotIn(List<Integer> values) {
            addCriterion("party_sort_order not in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order between", value1, value2, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order not between", value1, value2, "partySortOrder");
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