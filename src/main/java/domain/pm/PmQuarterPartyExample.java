package domain.pm;

import java.util.ArrayList;
import java.util.List;

public class PmQuarterPartyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmQuarterPartyExample() {
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

        public Criteria andQuarterIdIsNull() {
            addCriterion("quarter_id is null");
            return (Criteria) this;
        }

        public Criteria andQuarterIdIsNotNull() {
            addCriterion("quarter_id is not null");
            return (Criteria) this;
        }

        public Criteria andQuarterIdEqualTo(Integer value) {
            addCriterion("quarter_id =", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdNotEqualTo(Integer value) {
            addCriterion("quarter_id <>", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdGreaterThan(Integer value) {
            addCriterion("quarter_id >", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("quarter_id >=", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdLessThan(Integer value) {
            addCriterion("quarter_id <", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdLessThanOrEqualTo(Integer value) {
            addCriterion("quarter_id <=", value, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdIn(List<Integer> values) {
            addCriterion("quarter_id in", values, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdNotIn(List<Integer> values) {
            addCriterion("quarter_id not in", values, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdBetween(Integer value1, Integer value2) {
            addCriterion("quarter_id between", value1, value2, "quarterId");
            return (Criteria) this;
        }

        public Criteria andQuarterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("quarter_id not between", value1, value2, "quarterId");
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

        public Criteria andBranchNumIsNull() {
            addCriterion("branch_num is null");
            return (Criteria) this;
        }

        public Criteria andBranchNumIsNotNull() {
            addCriterion("branch_num is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNumEqualTo(Integer value) {
            addCriterion("branch_num =", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumNotEqualTo(Integer value) {
            addCriterion("branch_num <>", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumGreaterThan(Integer value) {
            addCriterion("branch_num >", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_num >=", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumLessThan(Integer value) {
            addCriterion("branch_num <", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumLessThanOrEqualTo(Integer value) {
            addCriterion("branch_num <=", value, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumIn(List<Integer> values) {
            addCriterion("branch_num in", values, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumNotIn(List<Integer> values) {
            addCriterion("branch_num not in", values, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumBetween(Integer value1, Integer value2) {
            addCriterion("branch_num between", value1, value2, "branchNum");
            return (Criteria) this;
        }

        public Criteria andBranchNumNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_num not between", value1, value2, "branchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumIsNull() {
            addCriterion("exculde_branch_num is null");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumIsNotNull() {
            addCriterion("exculde_branch_num is not null");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumEqualTo(Integer value) {
            addCriterion("exculde_branch_num =", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumNotEqualTo(Integer value) {
            addCriterion("exculde_branch_num <>", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumGreaterThan(Integer value) {
            addCriterion("exculde_branch_num >", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("exculde_branch_num >=", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumLessThan(Integer value) {
            addCriterion("exculde_branch_num <", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumLessThanOrEqualTo(Integer value) {
            addCriterion("exculde_branch_num <=", value, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumIn(List<Integer> values) {
            addCriterion("exculde_branch_num in", values, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumNotIn(List<Integer> values) {
            addCriterion("exculde_branch_num not in", values, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumBetween(Integer value1, Integer value2) {
            addCriterion("exculde_branch_num between", value1, value2, "exculdeBranchNum");
            return (Criteria) this;
        }

        public Criteria andExculdeBranchNumNotBetween(Integer value1, Integer value2) {
            addCriterion("exculde_branch_num not between", value1, value2, "exculdeBranchNum");
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