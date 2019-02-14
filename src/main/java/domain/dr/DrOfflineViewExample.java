package domain.dr;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DrOfflineViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOfflineViewExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Short value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Short value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Short value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Short value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Short value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Short value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Short> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Short> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Short value1, Short value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Short value1, Short value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIsNull() {
            addCriterion("recommend_date is null");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIsNotNull() {
            addCriterion("recommend_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendDateEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date =", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date <>", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateGreaterThan(Date value) {
            addCriterionForJDBCDate("recommend_date >", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date >=", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateLessThan(Date value) {
            addCriterionForJDBCDate("recommend_date <", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date <=", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIn(List<Date> values) {
            addCriterionForJDBCDate("recommend_date in", values, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("recommend_date not in", values, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("recommend_date between", value1, value2, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("recommend_date not between", value1, value2, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(Integer value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(Integer value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(Integer value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(Integer value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<Integer> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<Integer> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdIsNull() {
            addCriterion("chief_member_id is null");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdIsNotNull() {
            addCriterion("chief_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdEqualTo(Integer value) {
            addCriterion("chief_member_id =", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdNotEqualTo(Integer value) {
            addCriterion("chief_member_id <>", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdGreaterThan(Integer value) {
            addCriterion("chief_member_id >", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("chief_member_id >=", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdLessThan(Integer value) {
            addCriterion("chief_member_id <", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("chief_member_id <=", value, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdIn(List<Integer> values) {
            addCriterion("chief_member_id in", values, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdNotIn(List<Integer> values) {
            addCriterion("chief_member_id not in", values, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("chief_member_id between", value1, value2, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andChiefMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("chief_member_id not between", value1, value2, "chiefMemberId");
            return (Criteria) this;
        }

        public Criteria andMembersIsNull() {
            addCriterion("members is null");
            return (Criteria) this;
        }

        public Criteria andMembersIsNotNull() {
            addCriterion("members is not null");
            return (Criteria) this;
        }

        public Criteria andMembersEqualTo(String value) {
            addCriterion("members =", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersNotEqualTo(String value) {
            addCriterion("members <>", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersGreaterThan(String value) {
            addCriterion("members >", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersGreaterThanOrEqualTo(String value) {
            addCriterion("members >=", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersLessThan(String value) {
            addCriterion("members <", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersLessThanOrEqualTo(String value) {
            addCriterion("members <=", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersLike(String value) {
            addCriterion("members like", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersNotLike(String value) {
            addCriterion("members not like", value, "members");
            return (Criteria) this;
        }

        public Criteria andMembersIn(List<String> values) {
            addCriterion("members in", values, "members");
            return (Criteria) this;
        }

        public Criteria andMembersNotIn(List<String> values) {
            addCriterion("members not in", values, "members");
            return (Criteria) this;
        }

        public Criteria andMembersBetween(String value1, String value2) {
            addCriterion("members between", value1, value2, "members");
            return (Criteria) this;
        }

        public Criteria andMembersNotBetween(String value1, String value2) {
            addCriterion("members not between", value1, value2, "members");
            return (Criteria) this;
        }

        public Criteria andBallotSampleIsNull() {
            addCriterion("ballot_sample is null");
            return (Criteria) this;
        }

        public Criteria andBallotSampleIsNotNull() {
            addCriterion("ballot_sample is not null");
            return (Criteria) this;
        }

        public Criteria andBallotSampleEqualTo(String value) {
            addCriterion("ballot_sample =", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleNotEqualTo(String value) {
            addCriterion("ballot_sample <>", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleGreaterThan(String value) {
            addCriterion("ballot_sample >", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleGreaterThanOrEqualTo(String value) {
            addCriterion("ballot_sample >=", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleLessThan(String value) {
            addCriterion("ballot_sample <", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleLessThanOrEqualTo(String value) {
            addCriterion("ballot_sample <=", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleLike(String value) {
            addCriterion("ballot_sample like", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleNotLike(String value) {
            addCriterion("ballot_sample not like", value, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleIn(List<String> values) {
            addCriterion("ballot_sample in", values, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleNotIn(List<String> values) {
            addCriterion("ballot_sample not in", values, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleBetween(String value1, String value2) {
            addCriterion("ballot_sample between", value1, value2, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andBallotSampleNotBetween(String value1, String value2) {
            addCriterion("ballot_sample not between", value1, value2, "ballotSample");
            return (Criteria) this;
        }

        public Criteria andHeadcountIsNull() {
            addCriterion("headcount is null");
            return (Criteria) this;
        }

        public Criteria andHeadcountIsNotNull() {
            addCriterion("headcount is not null");
            return (Criteria) this;
        }

        public Criteria andHeadcountEqualTo(Integer value) {
            addCriterion("headcount =", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountNotEqualTo(Integer value) {
            addCriterion("headcount <>", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountGreaterThan(Integer value) {
            addCriterion("headcount >", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("headcount >=", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountLessThan(Integer value) {
            addCriterion("headcount <", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountLessThanOrEqualTo(Integer value) {
            addCriterion("headcount <=", value, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountIn(List<Integer> values) {
            addCriterion("headcount in", values, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountNotIn(List<Integer> values) {
            addCriterion("headcount not in", values, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountBetween(Integer value1, Integer value2) {
            addCriterion("headcount between", value1, value2, "headcount");
            return (Criteria) this;
        }

        public Criteria andHeadcountNotBetween(Integer value1, Integer value2) {
            addCriterion("headcount not between", value1, value2, "headcount");
            return (Criteria) this;
        }

        public Criteria andScopeIsNull() {
            addCriterion("scope is null");
            return (Criteria) this;
        }

        public Criteria andScopeIsNotNull() {
            addCriterion("scope is not null");
            return (Criteria) this;
        }

        public Criteria andScopeEqualTo(String value) {
            addCriterion("scope =", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotEqualTo(String value) {
            addCriterion("scope <>", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeGreaterThan(String value) {
            addCriterion("scope >", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeGreaterThanOrEqualTo(String value) {
            addCriterion("scope >=", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLessThan(String value) {
            addCriterion("scope <", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLessThanOrEqualTo(String value) {
            addCriterion("scope <=", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLike(String value) {
            addCriterion("scope like", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotLike(String value) {
            addCriterion("scope not like", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeIn(List<String> values) {
            addCriterion("scope in", values, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotIn(List<String> values) {
            addCriterion("scope not in", values, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeBetween(String value1, String value2) {
            addCriterion("scope between", value1, value2, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotBetween(String value1, String value2) {
            addCriterion("scope not between", value1, value2, "scope");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumIsNull() {
            addCriterion("expect_voter_num is null");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumIsNotNull() {
            addCriterion("expect_voter_num is not null");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumEqualTo(Integer value) {
            addCriterion("expect_voter_num =", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumNotEqualTo(Integer value) {
            addCriterion("expect_voter_num <>", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumGreaterThan(Integer value) {
            addCriterion("expect_voter_num >", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("expect_voter_num >=", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumLessThan(Integer value) {
            addCriterion("expect_voter_num <", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumLessThanOrEqualTo(Integer value) {
            addCriterion("expect_voter_num <=", value, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumIn(List<Integer> values) {
            addCriterion("expect_voter_num in", values, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumNotIn(List<Integer> values) {
            addCriterion("expect_voter_num not in", values, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumBetween(Integer value1, Integer value2) {
            addCriterion("expect_voter_num between", value1, value2, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andExpectVoterNumNotBetween(Integer value1, Integer value2) {
            addCriterion("expect_voter_num not between", value1, value2, "expectVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumIsNull() {
            addCriterion("actual_voter_num is null");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumIsNotNull() {
            addCriterion("actual_voter_num is not null");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumEqualTo(Integer value) {
            addCriterion("actual_voter_num =", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumNotEqualTo(Integer value) {
            addCriterion("actual_voter_num <>", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumGreaterThan(Integer value) {
            addCriterion("actual_voter_num >", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_voter_num >=", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumLessThan(Integer value) {
            addCriterion("actual_voter_num <", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumLessThanOrEqualTo(Integer value) {
            addCriterion("actual_voter_num <=", value, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumIn(List<Integer> values) {
            addCriterion("actual_voter_num in", values, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumNotIn(List<Integer> values) {
            addCriterion("actual_voter_num not in", values, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumBetween(Integer value1, Integer value2) {
            addCriterion("actual_voter_num between", value1, value2, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andActualVoterNumNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_voter_num not between", value1, value2, "actualVoterNum");
            return (Criteria) this;
        }

        public Criteria andBallotIsNull() {
            addCriterion("ballot is null");
            return (Criteria) this;
        }

        public Criteria andBallotIsNotNull() {
            addCriterion("ballot is not null");
            return (Criteria) this;
        }

        public Criteria andBallotEqualTo(Integer value) {
            addCriterion("ballot =", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotNotEqualTo(Integer value) {
            addCriterion("ballot <>", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotGreaterThan(Integer value) {
            addCriterion("ballot >", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotGreaterThanOrEqualTo(Integer value) {
            addCriterion("ballot >=", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotLessThan(Integer value) {
            addCriterion("ballot <", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotLessThanOrEqualTo(Integer value) {
            addCriterion("ballot <=", value, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotIn(List<Integer> values) {
            addCriterion("ballot in", values, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotNotIn(List<Integer> values) {
            addCriterion("ballot not in", values, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotBetween(Integer value1, Integer value2) {
            addCriterion("ballot between", value1, value2, "ballot");
            return (Criteria) this;
        }

        public Criteria andBallotNotBetween(Integer value1, Integer value2) {
            addCriterion("ballot not between", value1, value2, "ballot");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNull() {
            addCriterion("abstain is null");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNotNull() {
            addCriterion("abstain is not null");
            return (Criteria) this;
        }

        public Criteria andAbstainEqualTo(Integer value) {
            addCriterion("abstain =", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotEqualTo(Integer value) {
            addCriterion("abstain <>", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThan(Integer value) {
            addCriterion("abstain >", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThanOrEqualTo(Integer value) {
            addCriterion("abstain >=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThan(Integer value) {
            addCriterion("abstain <", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThanOrEqualTo(Integer value) {
            addCriterion("abstain <=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainIn(List<Integer> values) {
            addCriterion("abstain in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotIn(List<Integer> values) {
            addCriterion("abstain not in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainBetween(Integer value1, Integer value2) {
            addCriterion("abstain between", value1, value2, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotBetween(Integer value1, Integer value2) {
            addCriterion("abstain not between", value1, value2, "abstain");
            return (Criteria) this;
        }

        public Criteria andInvalidIsNull() {
            addCriterion("invalid is null");
            return (Criteria) this;
        }

        public Criteria andInvalidIsNotNull() {
            addCriterion("invalid is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidEqualTo(Integer value) {
            addCriterion("invalid =", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotEqualTo(Integer value) {
            addCriterion("invalid <>", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidGreaterThan(Integer value) {
            addCriterion("invalid >", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidGreaterThanOrEqualTo(Integer value) {
            addCriterion("invalid >=", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidLessThan(Integer value) {
            addCriterion("invalid <", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidLessThanOrEqualTo(Integer value) {
            addCriterion("invalid <=", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidIn(List<Integer> values) {
            addCriterion("invalid in", values, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotIn(List<Integer> values) {
            addCriterion("invalid not in", values, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidBetween(Integer value1, Integer value2) {
            addCriterion("invalid between", value1, value2, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotBetween(Integer value1, Integer value2) {
            addCriterion("invalid not between", value1, value2, "invalid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeIsNull() {
            addCriterion("need_voter_type is null");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeIsNotNull() {
            addCriterion("need_voter_type is not null");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeEqualTo(Boolean value) {
            addCriterion("need_voter_type =", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeNotEqualTo(Boolean value) {
            addCriterion("need_voter_type <>", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeGreaterThan(Boolean value) {
            addCriterion("need_voter_type >", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_voter_type >=", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeLessThan(Boolean value) {
            addCriterion("need_voter_type <", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("need_voter_type <=", value, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeIn(List<Boolean> values) {
            addCriterion("need_voter_type in", values, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeNotIn(List<Boolean> values) {
            addCriterion("need_voter_type not in", values, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("need_voter_type between", value1, value2, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andNeedVoterTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_voter_type not between", value1, value2, "needVoterType");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdIsNull() {
            addCriterion("voter_type_tpl_id is null");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdIsNotNull() {
            addCriterion("voter_type_tpl_id is not null");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdEqualTo(Integer value) {
            addCriterion("voter_type_tpl_id =", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdNotEqualTo(Integer value) {
            addCriterion("voter_type_tpl_id <>", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdGreaterThan(Integer value) {
            addCriterion("voter_type_tpl_id >", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("voter_type_tpl_id >=", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdLessThan(Integer value) {
            addCriterion("voter_type_tpl_id <", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdLessThanOrEqualTo(Integer value) {
            addCriterion("voter_type_tpl_id <=", value, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdIn(List<Integer> values) {
            addCriterion("voter_type_tpl_id in", values, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdNotIn(List<Integer> values) {
            addCriterion("voter_type_tpl_id not in", values, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdBetween(Integer value1, Integer value2) {
            addCriterion("voter_type_tpl_id between", value1, value2, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVoterTypeTplIdNotBetween(Integer value1, Integer value2) {
            addCriterion("voter_type_tpl_id not between", value1, value2, "voterTypeTplId");
            return (Criteria) this;
        }

        public Criteria andVotersIsNull() {
            addCriterion("voters is null");
            return (Criteria) this;
        }

        public Criteria andVotersIsNotNull() {
            addCriterion("voters is not null");
            return (Criteria) this;
        }

        public Criteria andVotersEqualTo(String value) {
            addCriterion("voters =", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersNotEqualTo(String value) {
            addCriterion("voters <>", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersGreaterThan(String value) {
            addCriterion("voters >", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersGreaterThanOrEqualTo(String value) {
            addCriterion("voters >=", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersLessThan(String value) {
            addCriterion("voters <", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersLessThanOrEqualTo(String value) {
            addCriterion("voters <=", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersLike(String value) {
            addCriterion("voters like", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersNotLike(String value) {
            addCriterion("voters not like", value, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersIn(List<String> values) {
            addCriterion("voters in", values, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersNotIn(List<String> values) {
            addCriterion("voters not in", values, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersBetween(String value1, String value2) {
            addCriterion("voters between", value1, value2, "voters");
            return (Criteria) this;
        }

        public Criteria andVotersNotBetween(String value1, String value2) {
            addCriterion("voters not between", value1, value2, "voters");
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

        public Criteria andSrSeqIsNull() {
            addCriterion("sr_seq is null");
            return (Criteria) this;
        }

        public Criteria andSrSeqIsNotNull() {
            addCriterion("sr_seq is not null");
            return (Criteria) this;
        }

        public Criteria andSrSeqEqualTo(String value) {
            addCriterion("sr_seq =", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqNotEqualTo(String value) {
            addCriterion("sr_seq <>", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqGreaterThan(String value) {
            addCriterion("sr_seq >", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqGreaterThanOrEqualTo(String value) {
            addCriterion("sr_seq >=", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqLessThan(String value) {
            addCriterion("sr_seq <", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqLessThanOrEqualTo(String value) {
            addCriterion("sr_seq <=", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqLike(String value) {
            addCriterion("sr_seq like", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqNotLike(String value) {
            addCriterion("sr_seq not like", value, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqIn(List<String> values) {
            addCriterion("sr_seq in", values, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqNotIn(List<String> values) {
            addCriterion("sr_seq not in", values, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqBetween(String value1, String value2) {
            addCriterion("sr_seq between", value1, value2, "srSeq");
            return (Criteria) this;
        }

        public Criteria andSrSeqNotBetween(String value1, String value2) {
            addCriterion("sr_seq not between", value1, value2, "srSeq");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNull() {
            addCriterion("hold_date is null");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNotNull() {
            addCriterion("hold_date is not null");
            return (Criteria) this;
        }

        public Criteria andHoldDateEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date =", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <>", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hold_date >", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date >=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThan(Date value) {
            addCriterionForJDBCDate("hold_date <", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date not in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date not between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andScTypeIsNull() {
            addCriterion("sc_type is null");
            return (Criteria) this;
        }

        public Criteria andScTypeIsNotNull() {
            addCriterion("sc_type is not null");
            return (Criteria) this;
        }

        public Criteria andScTypeEqualTo(Integer value) {
            addCriterion("sc_type =", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotEqualTo(Integer value) {
            addCriterion("sc_type <>", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeGreaterThan(Integer value) {
            addCriterion("sc_type >", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sc_type >=", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeLessThan(Integer value) {
            addCriterion("sc_type <", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeLessThanOrEqualTo(Integer value) {
            addCriterion("sc_type <=", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeIn(List<Integer> values) {
            addCriterion("sc_type in", values, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotIn(List<Integer> values) {
            addCriterion("sc_type not in", values, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeBetween(Integer value1, Integer value2) {
            addCriterion("sc_type between", value1, value2, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("sc_type not between", value1, value2, "scType");
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

        public Criteria andPostNameIsNull() {
            addCriterion("post_name is null");
            return (Criteria) this;
        }

        public Criteria andPostNameIsNotNull() {
            addCriterion("post_name is not null");
            return (Criteria) this;
        }

        public Criteria andPostNameEqualTo(String value) {
            addCriterion("post_name =", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotEqualTo(String value) {
            addCriterion("post_name <>", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameGreaterThan(String value) {
            addCriterion("post_name >", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameGreaterThanOrEqualTo(String value) {
            addCriterion("post_name >=", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLessThan(String value) {
            addCriterion("post_name <", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLessThanOrEqualTo(String value) {
            addCriterion("post_name <=", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLike(String value) {
            addCriterion("post_name like", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotLike(String value) {
            addCriterion("post_name not like", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameIn(List<String> values) {
            addCriterion("post_name in", values, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotIn(List<String> values) {
            addCriterion("post_name not in", values, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameBetween(String value1, String value2) {
            addCriterion("post_name between", value1, value2, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotBetween(String value1, String value2) {
            addCriterion("post_name not between", value1, value2, "postName");
            return (Criteria) this;
        }

        public Criteria andJobIsNull() {
            addCriterion("job is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {
            addCriterion("job is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {
            addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {
            addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {
            addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {
            addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {
            addCriterion("job like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {
            addCriterion("job not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {
            addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {
            addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("job not between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNull() {
            addCriterion("admin_level is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNotNull() {
            addCriterion("admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelEqualTo(Integer value) {
            addCriterion("admin_level =", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotEqualTo(Integer value) {
            addCriterion("admin_level <>", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThan(Integer value) {
            addCriterion("admin_level >", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level >=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThan(Integer value) {
            addCriterion("admin_level <", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level <=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIn(List<Integer> values) {
            addCriterion("admin_level in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotIn(List<Integer> values) {
            addCriterion("admin_level not in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("admin_level between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level not between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNull() {
            addCriterion("post_type is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNotNull() {
            addCriterion("post_type is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeEqualTo(Integer value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(Integer value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(Integer value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(Integer value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<Integer> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<Integer> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type not between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIsNull() {
            addCriterion("unit_type is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIsNotNull() {
            addCriterion("unit_type is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeEqualTo(Integer value) {
            addCriterion("unit_type =", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNotEqualTo(Integer value) {
            addCriterion("unit_type <>", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeGreaterThan(Integer value) {
            addCriterion("unit_type >", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type >=", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeLessThan(Integer value) {
            addCriterion("unit_type <", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type <=", value, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIn(List<Integer> values) {
            addCriterion("unit_type in", values, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNotIn(List<Integer> values) {
            addCriterion("unit_type not in", values, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeBetween(Integer value1, Integer value2) {
            addCriterion("unit_type between", value1, value2, "unitType");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type not between", value1, value2, "unitType");
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