package domain.pcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcsPrRecommendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPrRecommendExample() {
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

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Byte value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Byte value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Byte value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Byte value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Byte value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Byte value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Byte> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Byte> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Byte value1, Byte value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Byte value1, Byte value2) {
            addCriterion("stage not between", value1, value2, "stage");
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

        public Criteria andExpectMemberCountIsNull() {
            addCriterion("expect_member_count is null");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountIsNotNull() {
            addCriterion("expect_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountEqualTo(Integer value) {
            addCriterion("expect_member_count =", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotEqualTo(Integer value) {
            addCriterion("expect_member_count <>", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountGreaterThan(Integer value) {
            addCriterion("expect_member_count >", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("expect_member_count >=", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountLessThan(Integer value) {
            addCriterion("expect_member_count <", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("expect_member_count <=", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountIn(List<Integer> values) {
            addCriterion("expect_member_count in", values, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotIn(List<Integer> values) {
            addCriterion("expect_member_count not in", values, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("expect_member_count between", value1, value2, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("expect_member_count not between", value1, value2, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIsNull() {
            addCriterion("actual_member_count is null");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIsNotNull() {
            addCriterion("actual_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountEqualTo(Integer value) {
            addCriterion("actual_member_count =", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotEqualTo(Integer value) {
            addCriterion("actual_member_count <>", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountGreaterThan(Integer value) {
            addCriterion("actual_member_count >", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_member_count >=", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountLessThan(Integer value) {
            addCriterion("actual_member_count <", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("actual_member_count <=", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIn(List<Integer> values) {
            addCriterion("actual_member_count in", values, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotIn(List<Integer> values) {
            addCriterion("actual_member_count not in", values, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("actual_member_count between", value1, value2, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_member_count not between", value1, value2, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountIsNull() {
            addCriterion("expect_positive_member_count is null");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountIsNotNull() {
            addCriterion("expect_positive_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountEqualTo(Integer value) {
            addCriterion("expect_positive_member_count =", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountNotEqualTo(Integer value) {
            addCriterion("expect_positive_member_count <>", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountGreaterThan(Integer value) {
            addCriterion("expect_positive_member_count >", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("expect_positive_member_count >=", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountLessThan(Integer value) {
            addCriterion("expect_positive_member_count <", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("expect_positive_member_count <=", value, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountIn(List<Integer> values) {
            addCriterion("expect_positive_member_count in", values, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountNotIn(List<Integer> values) {
            addCriterion("expect_positive_member_count not in", values, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("expect_positive_member_count between", value1, value2, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectPositiveMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("expect_positive_member_count not between", value1, value2, "expectPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountIsNull() {
            addCriterion("actual_positive_member_count is null");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountIsNotNull() {
            addCriterion("actual_positive_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountEqualTo(Integer value) {
            addCriterion("actual_positive_member_count =", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountNotEqualTo(Integer value) {
            addCriterion("actual_positive_member_count <>", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountGreaterThan(Integer value) {
            addCriterion("actual_positive_member_count >", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_positive_member_count >=", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountLessThan(Integer value) {
            addCriterion("actual_positive_member_count <", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("actual_positive_member_count <=", value, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountIn(List<Integer> values) {
            addCriterion("actual_positive_member_count in", values, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountNotIn(List<Integer> values) {
            addCriterion("actual_positive_member_count not in", values, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("actual_positive_member_count between", value1, value2, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualPositiveMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_positive_member_count not between", value1, value2, "actualPositiveMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountIsNull() {
            addCriterion("vote_member_count is null");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountIsNotNull() {
            addCriterion("vote_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountEqualTo(Integer value) {
            addCriterion("vote_member_count =", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountNotEqualTo(Integer value) {
            addCriterion("vote_member_count <>", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountGreaterThan(Integer value) {
            addCriterion("vote_member_count >", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_member_count >=", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountLessThan(Integer value) {
            addCriterion("vote_member_count <", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("vote_member_count <=", value, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountIn(List<Integer> values) {
            addCriterion("vote_member_count in", values, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountNotIn(List<Integer> values) {
            addCriterion("vote_member_count not in", values, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("vote_member_count between", value1, value2, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andVoteMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_member_count not between", value1, value2, "voteMemberCount");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeIsNull() {
            addCriterion("meeting_type is null");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeIsNotNull() {
            addCriterion("meeting_type is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeEqualTo(Byte value) {
            addCriterion("meeting_type =", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeNotEqualTo(Byte value) {
            addCriterion("meeting_type <>", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeGreaterThan(Byte value) {
            addCriterion("meeting_type >", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("meeting_type >=", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeLessThan(Byte value) {
            addCriterion("meeting_type <", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeLessThanOrEqualTo(Byte value) {
            addCriterion("meeting_type <=", value, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeIn(List<Byte> values) {
            addCriterion("meeting_type in", values, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeNotIn(List<Byte> values) {
            addCriterion("meeting_type not in", values, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeBetween(Byte value1, Byte value2) {
            addCriterion("meeting_type between", value1, value2, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("meeting_type not between", value1, value2, "meetingType");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNull() {
            addCriterion("meeting_time is null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNotNull() {
            addCriterion("meeting_time is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeEqualTo(Date value) {
            addCriterion("meeting_time =", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotEqualTo(Date value) {
            addCriterion("meeting_time <>", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThan(Date value) {
            addCriterion("meeting_time >", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("meeting_time >=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThan(Date value) {
            addCriterion("meeting_time <", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThanOrEqualTo(Date value) {
            addCriterion("meeting_time <=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIn(List<Date> values) {
            addCriterion("meeting_time in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotIn(List<Date> values) {
            addCriterion("meeting_time not in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeBetween(Date value1, Date value2) {
            addCriterion("meeting_time between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotBetween(Date value1, Date value2) {
            addCriterion("meeting_time not between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIsNull() {
            addCriterion("meeting_address is null");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIsNotNull() {
            addCriterion("meeting_address is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressEqualTo(String value) {
            addCriterion("meeting_address =", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotEqualTo(String value) {
            addCriterion("meeting_address <>", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressGreaterThan(String value) {
            addCriterion("meeting_address >", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("meeting_address >=", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLessThan(String value) {
            addCriterion("meeting_address <", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLessThanOrEqualTo(String value) {
            addCriterion("meeting_address <=", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLike(String value) {
            addCriterion("meeting_address like", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotLike(String value) {
            addCriterion("meeting_address not like", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIn(List<String> values) {
            addCriterion("meeting_address in", values, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotIn(List<String> values) {
            addCriterion("meeting_address not in", values, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressBetween(String value1, String value2) {
            addCriterion("meeting_address between", value1, value2, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotBetween(String value1, String value2) {
            addCriterion("meeting_address not between", value1, value2, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andReportFilePathIsNull() {
            addCriterion("report_file_path is null");
            return (Criteria) this;
        }

        public Criteria andReportFilePathIsNotNull() {
            addCriterion("report_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andReportFilePathEqualTo(String value) {
            addCriterion("report_file_path =", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathNotEqualTo(String value) {
            addCriterion("report_file_path <>", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathGreaterThan(String value) {
            addCriterion("report_file_path >", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("report_file_path >=", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathLessThan(String value) {
            addCriterion("report_file_path <", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathLessThanOrEqualTo(String value) {
            addCriterion("report_file_path <=", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathLike(String value) {
            addCriterion("report_file_path like", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathNotLike(String value) {
            addCriterion("report_file_path not like", value, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathIn(List<String> values) {
            addCriterion("report_file_path in", values, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathNotIn(List<String> values) {
            addCriterion("report_file_path not in", values, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathBetween(String value1, String value2) {
            addCriterion("report_file_path between", value1, value2, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andReportFilePathNotBetween(String value1, String value2) {
            addCriterion("report_file_path not between", value1, value2, "reportFilePath");
            return (Criteria) this;
        }

        public Criteria andHasReportIsNull() {
            addCriterion("has_report is null");
            return (Criteria) this;
        }

        public Criteria andHasReportIsNotNull() {
            addCriterion("has_report is not null");
            return (Criteria) this;
        }

        public Criteria andHasReportEqualTo(Boolean value) {
            addCriterion("has_report =", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotEqualTo(Boolean value) {
            addCriterion("has_report <>", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThan(Boolean value) {
            addCriterion("has_report >", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_report >=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThan(Boolean value) {
            addCriterion("has_report <", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThanOrEqualTo(Boolean value) {
            addCriterion("has_report <=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportIn(List<Boolean> values) {
            addCriterion("has_report in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotIn(List<Boolean> values) {
            addCriterion("has_report not in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report between", value1, value2, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report not between", value1, value2, "hasReport");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNull() {
            addCriterion("report_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNotNull() {
            addCriterion("report_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdEqualTo(Integer value) {
            addCriterion("report_user_id =", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotEqualTo(Integer value) {
            addCriterion("report_user_id <>", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThan(Integer value) {
            addCriterion("report_user_id >", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("report_user_id >=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThan(Integer value) {
            addCriterion("report_user_id <", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("report_user_id <=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIn(List<Integer> values) {
            addCriterion("report_user_id in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotIn(List<Integer> values) {
            addCriterion("report_user_id not in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdBetween(Integer value1, Integer value2) {
            addCriterion("report_user_id between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("report_user_id not between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNull() {
            addCriterion("report_time is null");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNotNull() {
            addCriterion("report_time is not null");
            return (Criteria) this;
        }

        public Criteria andReportTimeEqualTo(Date value) {
            addCriterion("report_time =", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotEqualTo(Date value) {
            addCriterion("report_time <>", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThan(Date value) {
            addCriterion("report_time >", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("report_time >=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThan(Date value) {
            addCriterion("report_time <", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThanOrEqualTo(Date value) {
            addCriterion("report_time <=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeIn(List<Date> values) {
            addCriterion("report_time in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotIn(List<Date> values) {
            addCriterion("report_time not in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeBetween(Date value1, Date value2) {
            addCriterion("report_time between", value1, value2, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotBetween(Date value1, Date value2) {
            addCriterion("report_time not between", value1, value2, "reportTime");
            return (Criteria) this;
        }

        public Criteria andBranchCountIsNull() {
            addCriterion("branch_count is null");
            return (Criteria) this;
        }

        public Criteria andBranchCountIsNotNull() {
            addCriterion("branch_count is not null");
            return (Criteria) this;
        }

        public Criteria andBranchCountEqualTo(Integer value) {
            addCriterion("branch_count =", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotEqualTo(Integer value) {
            addCriterion("branch_count <>", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThan(Integer value) {
            addCriterion("branch_count >", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_count >=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThan(Integer value) {
            addCriterion("branch_count <", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThanOrEqualTo(Integer value) {
            addCriterion("branch_count <=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountIn(List<Integer> values) {
            addCriterion("branch_count in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotIn(List<Integer> values) {
            addCriterion("branch_count not in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountBetween(Integer value1, Integer value2) {
            addCriterion("branch_count between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_count not between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNull() {
            addCriterion("member_count is null");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNotNull() {
            addCriterion("member_count is not null");
            return (Criteria) this;
        }

        public Criteria andMemberCountEqualTo(Integer value) {
            addCriterion("member_count =", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotEqualTo(Integer value) {
            addCriterion("member_count <>", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThan(Integer value) {
            addCriterion("member_count >", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_count >=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThan(Integer value) {
            addCriterion("member_count <", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("member_count <=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIn(List<Integer> values) {
            addCriterion("member_count in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotIn(List<Integer> values) {
            addCriterion("member_count not in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("member_count between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("member_count not between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNull() {
            addCriterion("positive_count is null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNotNull() {
            addCriterion("positive_count is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountEqualTo(Integer value) {
            addCriterion("positive_count =", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotEqualTo(Integer value) {
            addCriterion("positive_count <>", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThan(Integer value) {
            addCriterion("positive_count >", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("positive_count >=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThan(Integer value) {
            addCriterion("positive_count <", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThanOrEqualTo(Integer value) {
            addCriterion("positive_count <=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIn(List<Integer> values) {
            addCriterion("positive_count in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotIn(List<Integer> values) {
            addCriterion("positive_count not in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountBetween(Integer value1, Integer value2) {
            addCriterion("positive_count between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotBetween(Integer value1, Integer value2) {
            addCriterion("positive_count not between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNull() {
            addCriterion("student_member_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNotNull() {
            addCriterion("student_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountEqualTo(Integer value) {
            addCriterion("student_member_count =", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotEqualTo(Integer value) {
            addCriterion("student_member_count <>", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThan(Integer value) {
            addCriterion("student_member_count >", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("student_member_count >=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThan(Integer value) {
            addCriterion("student_member_count <", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("student_member_count <=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIn(List<Integer> values) {
            addCriterion("student_member_count in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotIn(List<Integer> values) {
            addCriterion("student_member_count not in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("student_member_count between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("student_member_count not between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNull() {
            addCriterion("teacher_member_count is null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNotNull() {
            addCriterion("teacher_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountEqualTo(Integer value) {
            addCriterion("teacher_member_count =", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotEqualTo(Integer value) {
            addCriterion("teacher_member_count <>", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThan(Integer value) {
            addCriterion("teacher_member_count >", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("teacher_member_count >=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThan(Integer value) {
            addCriterion("teacher_member_count <", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("teacher_member_count <=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIn(List<Integer> values) {
            addCriterion("teacher_member_count in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotIn(List<Integer> values) {
            addCriterion("teacher_member_count not in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("teacher_member_count between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("teacher_member_count not between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNull() {
            addCriterion("retire_member_count is null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNotNull() {
            addCriterion("retire_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountEqualTo(Integer value) {
            addCriterion("retire_member_count =", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotEqualTo(Integer value) {
            addCriterion("retire_member_count <>", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThan(Integer value) {
            addCriterion("retire_member_count >", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("retire_member_count >=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThan(Integer value) {
            addCriterion("retire_member_count <", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("retire_member_count <=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIn(List<Integer> values) {
            addCriterion("retire_member_count in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotIn(List<Integer> values) {
            addCriterion("retire_member_count not in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("retire_member_count between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("retire_member_count not between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIsNull() {
            addCriterion("check_remark is null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIsNotNull() {
            addCriterion("check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkEqualTo(String value) {
            addCriterion("check_remark =", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotEqualTo(String value) {
            addCriterion("check_remark <>", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThan(String value) {
            addCriterion("check_remark >", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("check_remark >=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThan(String value) {
            addCriterion("check_remark <", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("check_remark <=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLike(String value) {
            addCriterion("check_remark like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotLike(String value) {
            addCriterion("check_remark not like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIn(List<String> values) {
            addCriterion("check_remark in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotIn(List<String> values) {
            addCriterion("check_remark not in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkBetween(String value1, String value2) {
            addCriterion("check_remark between", value1, value2, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("check_remark not between", value1, value2, "checkRemark");
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