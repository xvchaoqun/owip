package domain.pcs;

import java.util.ArrayList;
import java.util.List;

public class PcsPrAllocateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPrAllocateExample() {
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

        public Criteria andConfigIdIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigIdIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigIdEqualTo(Integer value) {
            addCriterion("config_id =", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotEqualTo(Integer value) {
            addCriterion("config_id <>", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThan(Integer value) {
            addCriterion("config_id >", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_id >=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThan(Integer value) {
            addCriterion("config_id <", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_id <=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdIn(List<Integer> values) {
            addCriterion("config_id in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotIn(List<Integer> values) {
            addCriterion("config_id not in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("config_id between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_id not between", value1, value2, "configId");
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

        public Criteria andCandidateCountIsNull() {
            addCriterion("candidate_count is null");
            return (Criteria) this;
        }

        public Criteria andCandidateCountIsNotNull() {
            addCriterion("candidate_count is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateCountEqualTo(Integer value) {
            addCriterion("candidate_count =", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountNotEqualTo(Integer value) {
            addCriterion("candidate_count <>", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountGreaterThan(Integer value) {
            addCriterion("candidate_count >", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("candidate_count >=", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountLessThan(Integer value) {
            addCriterion("candidate_count <", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountLessThanOrEqualTo(Integer value) {
            addCriterion("candidate_count <=", value, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountIn(List<Integer> values) {
            addCriterion("candidate_count in", values, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountNotIn(List<Integer> values) {
            addCriterion("candidate_count not in", values, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountBetween(Integer value1, Integer value2) {
            addCriterion("candidate_count between", value1, value2, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andCandidateCountNotBetween(Integer value1, Integer value2) {
            addCriterion("candidate_count not between", value1, value2, "candidateCount");
            return (Criteria) this;
        }

        public Criteria andPrCountIsNull() {
            addCriterion("pr_count is null");
            return (Criteria) this;
        }

        public Criteria andPrCountIsNotNull() {
            addCriterion("pr_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrCountEqualTo(String value) {
            addCriterion("pr_count =", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountNotEqualTo(String value) {
            addCriterion("pr_count <>", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountGreaterThan(String value) {
            addCriterion("pr_count >", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountGreaterThanOrEqualTo(String value) {
            addCriterion("pr_count >=", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountLessThan(String value) {
            addCriterion("pr_count <", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountLessThanOrEqualTo(String value) {
            addCriterion("pr_count <=", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountLike(String value) {
            addCriterion("pr_count like", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountNotLike(String value) {
            addCriterion("pr_count not like", value, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountIn(List<String> values) {
            addCriterion("pr_count in", values, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountNotIn(List<String> values) {
            addCriterion("pr_count not in", values, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountBetween(String value1, String value2) {
            addCriterion("pr_count between", value1, value2, "prCount");
            return (Criteria) this;
        }

        public Criteria andPrCountNotBetween(String value1, String value2) {
            addCriterion("pr_count not between", value1, value2, "prCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountIsNull() {
            addCriterion("female_count is null");
            return (Criteria) this;
        }

        public Criteria andFemaleCountIsNotNull() {
            addCriterion("female_count is not null");
            return (Criteria) this;
        }

        public Criteria andFemaleCountEqualTo(Integer value) {
            addCriterion("female_count =", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountNotEqualTo(Integer value) {
            addCriterion("female_count <>", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountGreaterThan(Integer value) {
            addCriterion("female_count >", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("female_count >=", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountLessThan(Integer value) {
            addCriterion("female_count <", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountLessThanOrEqualTo(Integer value) {
            addCriterion("female_count <=", value, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountIn(List<Integer> values) {
            addCriterion("female_count in", values, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountNotIn(List<Integer> values) {
            addCriterion("female_count not in", values, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountBetween(Integer value1, Integer value2) {
            addCriterion("female_count between", value1, value2, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andFemaleCountNotBetween(Integer value1, Integer value2) {
            addCriterion("female_count not between", value1, value2, "femaleCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountIsNull() {
            addCriterion("minority_count is null");
            return (Criteria) this;
        }

        public Criteria andMinorityCountIsNotNull() {
            addCriterion("minority_count is not null");
            return (Criteria) this;
        }

        public Criteria andMinorityCountEqualTo(Integer value) {
            addCriterion("minority_count =", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountNotEqualTo(Integer value) {
            addCriterion("minority_count <>", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountGreaterThan(Integer value) {
            addCriterion("minority_count >", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("minority_count >=", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountLessThan(Integer value) {
            addCriterion("minority_count <", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountLessThanOrEqualTo(Integer value) {
            addCriterion("minority_count <=", value, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountIn(List<Integer> values) {
            addCriterion("minority_count in", values, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountNotIn(List<Integer> values) {
            addCriterion("minority_count not in", values, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountBetween(Integer value1, Integer value2) {
            addCriterion("minority_count between", value1, value2, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andMinorityCountNotBetween(Integer value1, Integer value2) {
            addCriterion("minority_count not between", value1, value2, "minorityCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountIsNull() {
            addCriterion("under_fifty_count is null");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountIsNotNull() {
            addCriterion("under_fifty_count is not null");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountEqualTo(Integer value) {
            addCriterion("under_fifty_count =", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountNotEqualTo(Integer value) {
            addCriterion("under_fifty_count <>", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountGreaterThan(Integer value) {
            addCriterion("under_fifty_count >", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("under_fifty_count >=", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountLessThan(Integer value) {
            addCriterion("under_fifty_count <", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountLessThanOrEqualTo(Integer value) {
            addCriterion("under_fifty_count <=", value, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountIn(List<Integer> values) {
            addCriterion("under_fifty_count in", values, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountNotIn(List<Integer> values) {
            addCriterion("under_fifty_count not in", values, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountBetween(Integer value1, Integer value2) {
            addCriterion("under_fifty_count between", value1, value2, "underFiftyCount");
            return (Criteria) this;
        }

        public Criteria andUnderFiftyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("under_fifty_count not between", value1, value2, "underFiftyCount");
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