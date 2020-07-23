package domain.dr;

import java.util.ArrayList;
import java.util.List;

public class DrOnlinePostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOnlinePostExample() {
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

        public Criteria andUnitPostIdIsNull() {
            addCriterion("unit_post_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNotNull() {
            addCriterion("unit_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdEqualTo(Integer value) {
            addCriterion("unit_post_id =", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotEqualTo(Integer value) {
            addCriterion("unit_post_id <>", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThan(Integer value) {
            addCriterion("unit_post_id >", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id >=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThan(Integer value) {
            addCriterion("unit_post_id <", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id <=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIn(List<Integer> values) {
            addCriterion("unit_post_id in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotIn(List<Integer> values) {
            addCriterion("unit_post_id not in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id not between", value1, value2, "unitPostId");
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

        public Criteria andOnlineIdIsNull() {
            addCriterion("online_id is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIsNotNull() {
            addCriterion("online_id is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdEqualTo(Integer value) {
            addCriterion("online_id =", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotEqualTo(Integer value) {
            addCriterion("online_id <>", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThan(Integer value) {
            addCriterion("online_id >", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_id >=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThan(Integer value) {
            addCriterion("online_id <", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThanOrEqualTo(Integer value) {
            addCriterion("online_id <=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIn(List<Integer> values) {
            addCriterion("online_id in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotIn(List<Integer> values) {
            addCriterion("online_id not in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdBetween(Integer value1, Integer value2) {
            addCriterion("online_id between", value1, value2, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("online_id not between", value1, value2, "onlineId");
            return (Criteria) this;
        }

        public Criteria andHasCandidateIsNull() {
            addCriterion("has_candidate is null");
            return (Criteria) this;
        }

        public Criteria andHasCandidateIsNotNull() {
            addCriterion("has_candidate is not null");
            return (Criteria) this;
        }

        public Criteria andHasCandidateEqualTo(Boolean value) {
            addCriterion("has_candidate =", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateNotEqualTo(Boolean value) {
            addCriterion("has_candidate <>", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateGreaterThan(Boolean value) {
            addCriterion("has_candidate >", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_candidate >=", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateLessThan(Boolean value) {
            addCriterion("has_candidate <", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateLessThanOrEqualTo(Boolean value) {
            addCriterion("has_candidate <=", value, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateIn(List<Boolean> values) {
            addCriterion("has_candidate in", values, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateNotIn(List<Boolean> values) {
            addCriterion("has_candidate not in", values, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateBetween(Boolean value1, Boolean value2) {
            addCriterion("has_candidate between", value1, value2, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andHasCandidateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_candidate not between", value1, value2, "hasCandidate");
            return (Criteria) this;
        }

        public Criteria andCandidatesIsNull() {
            addCriterion("candidates is null");
            return (Criteria) this;
        }

        public Criteria andCandidatesIsNotNull() {
            addCriterion("candidates is not null");
            return (Criteria) this;
        }

        public Criteria andCandidatesEqualTo(String value) {
            addCriterion("candidates =", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotEqualTo(String value) {
            addCriterion("candidates <>", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesGreaterThan(String value) {
            addCriterion("candidates >", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesGreaterThanOrEqualTo(String value) {
            addCriterion("candidates >=", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLessThan(String value) {
            addCriterion("candidates <", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLessThanOrEqualTo(String value) {
            addCriterion("candidates <=", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLike(String value) {
            addCriterion("candidates like", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotLike(String value) {
            addCriterion("candidates not like", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesIn(List<String> values) {
            addCriterion("candidates in", values, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotIn(List<String> values) {
            addCriterion("candidates not in", values, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesBetween(String value1, String value2) {
            addCriterion("candidates between", value1, value2, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotBetween(String value1, String value2) {
            addCriterion("candidates not between", value1, value2, "candidates");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveIsNull() {
            addCriterion("has_competitive is null");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveIsNotNull() {
            addCriterion("has_competitive is not null");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveEqualTo(Boolean value) {
            addCriterion("has_competitive =", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveNotEqualTo(Boolean value) {
            addCriterion("has_competitive <>", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveGreaterThan(Boolean value) {
            addCriterion("has_competitive >", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_competitive >=", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveLessThan(Boolean value) {
            addCriterion("has_competitive <", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveLessThanOrEqualTo(Boolean value) {
            addCriterion("has_competitive <=", value, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveIn(List<Boolean> values) {
            addCriterion("has_competitive in", values, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveNotIn(List<Boolean> values) {
            addCriterion("has_competitive not in", values, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveBetween(Boolean value1, Boolean value2) {
            addCriterion("has_competitive between", value1, value2, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andHasCompetitiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_competitive not between", value1, value2, "hasCompetitive");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumIsNull() {
            addCriterion("competitive_num is null");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumIsNotNull() {
            addCriterion("competitive_num is not null");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumEqualTo(Integer value) {
            addCriterion("competitive_num =", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumNotEqualTo(Integer value) {
            addCriterion("competitive_num <>", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumGreaterThan(Integer value) {
            addCriterion("competitive_num >", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("competitive_num >=", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumLessThan(Integer value) {
            addCriterion("competitive_num <", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumLessThanOrEqualTo(Integer value) {
            addCriterion("competitive_num <=", value, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumIn(List<Integer> values) {
            addCriterion("competitive_num in", values, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumNotIn(List<Integer> values) {
            addCriterion("competitive_num not in", values, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumBetween(Integer value1, Integer value2) {
            addCriterion("competitive_num between", value1, value2, "competitiveNum");
            return (Criteria) this;
        }

        public Criteria andCompetitiveNumNotBetween(Integer value1, Integer value2) {
            addCriterion("competitive_num not between", value1, value2, "competitiveNum");
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