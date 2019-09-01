package domain.dr;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DrOfflineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOfflineExample() {
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

        public Criteria andSuperviceUserIdIsNull() {
            addCriterion("supervice_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdIsNotNull() {
            addCriterion("supervice_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdEqualTo(Integer value) {
            addCriterion("supervice_user_id =", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdNotEqualTo(Integer value) {
            addCriterion("supervice_user_id <>", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdGreaterThan(Integer value) {
            addCriterion("supervice_user_id >", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("supervice_user_id >=", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdLessThan(Integer value) {
            addCriterion("supervice_user_id <", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("supervice_user_id <=", value, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdIn(List<Integer> values) {
            addCriterion("supervice_user_id in", values, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdNotIn(List<Integer> values) {
            addCriterion("supervice_user_id not in", values, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdBetween(Integer value1, Integer value2) {
            addCriterion("supervice_user_id between", value1, value2, "superviceUserId");
            return (Criteria) this;
        }

        public Criteria andSuperviceUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("supervice_user_id not between", value1, value2, "superviceUserId");
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