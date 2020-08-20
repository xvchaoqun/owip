package domain.pcs;

import java.util.ArrayList;
import java.util.List;

public class PcsPollReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPollReportExample() {
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

        public Criteria andPollIdIsNull() {
            addCriterion("poll_id is null");
            return (Criteria) this;
        }

        public Criteria andPollIdIsNotNull() {
            addCriterion("poll_id is not null");
            return (Criteria) this;
        }

        public Criteria andPollIdEqualTo(Integer value) {
            addCriterion("poll_id =", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdNotEqualTo(Integer value) {
            addCriterion("poll_id <>", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdGreaterThan(Integer value) {
            addCriterion("poll_id >", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("poll_id >=", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdLessThan(Integer value) {
            addCriterion("poll_id <", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdLessThanOrEqualTo(Integer value) {
            addCriterion("poll_id <=", value, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdIn(List<Integer> values) {
            addCriterion("poll_id in", values, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdNotIn(List<Integer> values) {
            addCriterion("poll_id not in", values, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdBetween(Integer value1, Integer value2) {
            addCriterion("poll_id between", value1, value2, "pollId");
            return (Criteria) this;
        }

        public Criteria andPollIdNotBetween(Integer value1, Integer value2) {
            addCriterion("poll_id not between", value1, value2, "pollId");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andPositiveBallotIsNull() {
            addCriterion("positive_ballot is null");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotIsNotNull() {
            addCriterion("positive_ballot is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotEqualTo(Integer value) {
            addCriterion("positive_ballot =", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotNotEqualTo(Integer value) {
            addCriterion("positive_ballot <>", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotGreaterThan(Integer value) {
            addCriterion("positive_ballot >", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotGreaterThanOrEqualTo(Integer value) {
            addCriterion("positive_ballot >=", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotLessThan(Integer value) {
            addCriterion("positive_ballot <", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotLessThanOrEqualTo(Integer value) {
            addCriterion("positive_ballot <=", value, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotIn(List<Integer> values) {
            addCriterion("positive_ballot in", values, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotNotIn(List<Integer> values) {
            addCriterion("positive_ballot not in", values, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotBetween(Integer value1, Integer value2) {
            addCriterion("positive_ballot between", value1, value2, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andPositiveBallotNotBetween(Integer value1, Integer value2) {
            addCriterion("positive_ballot not between", value1, value2, "positiveBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotIsNull() {
            addCriterion("grow_ballot is null");
            return (Criteria) this;
        }

        public Criteria andGrowBallotIsNotNull() {
            addCriterion("grow_ballot is not null");
            return (Criteria) this;
        }

        public Criteria andGrowBallotEqualTo(Integer value) {
            addCriterion("grow_ballot =", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotNotEqualTo(Integer value) {
            addCriterion("grow_ballot <>", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotGreaterThan(Integer value) {
            addCriterion("grow_ballot >", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotGreaterThanOrEqualTo(Integer value) {
            addCriterion("grow_ballot >=", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotLessThan(Integer value) {
            addCriterion("grow_ballot <", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotLessThanOrEqualTo(Integer value) {
            addCriterion("grow_ballot <=", value, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotIn(List<Integer> values) {
            addCriterion("grow_ballot in", values, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotNotIn(List<Integer> values) {
            addCriterion("grow_ballot not in", values, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotBetween(Integer value1, Integer value2) {
            addCriterion("grow_ballot between", value1, value2, "growBallot");
            return (Criteria) this;
        }

        public Criteria andGrowBallotNotBetween(Integer value1, Integer value2) {
            addCriterion("grow_ballot not between", value1, value2, "growBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotIsNull() {
            addCriterion("disagree_ballot is null");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotIsNotNull() {
            addCriterion("disagree_ballot is not null");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotEqualTo(Integer value) {
            addCriterion("disagree_ballot =", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotNotEqualTo(Integer value) {
            addCriterion("disagree_ballot <>", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotGreaterThan(Integer value) {
            addCriterion("disagree_ballot >", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotGreaterThanOrEqualTo(Integer value) {
            addCriterion("disagree_ballot >=", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotLessThan(Integer value) {
            addCriterion("disagree_ballot <", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotLessThanOrEqualTo(Integer value) {
            addCriterion("disagree_ballot <=", value, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotIn(List<Integer> values) {
            addCriterion("disagree_ballot in", values, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotNotIn(List<Integer> values) {
            addCriterion("disagree_ballot not in", values, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotBetween(Integer value1, Integer value2) {
            addCriterion("disagree_ballot between", value1, value2, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andDisagreeBallotNotBetween(Integer value1, Integer value2) {
            addCriterion("disagree_ballot not between", value1, value2, "disagreeBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotIsNull() {
            addCriterion("abstain_ballot is null");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotIsNotNull() {
            addCriterion("abstain_ballot is not null");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotEqualTo(Integer value) {
            addCriterion("abstain_ballot =", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotNotEqualTo(Integer value) {
            addCriterion("abstain_ballot <>", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotGreaterThan(Integer value) {
            addCriterion("abstain_ballot >", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotGreaterThanOrEqualTo(Integer value) {
            addCriterion("abstain_ballot >=", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotLessThan(Integer value) {
            addCriterion("abstain_ballot <", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotLessThanOrEqualTo(Integer value) {
            addCriterion("abstain_ballot <=", value, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotIn(List<Integer> values) {
            addCriterion("abstain_ballot in", values, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotNotIn(List<Integer> values) {
            addCriterion("abstain_ballot not in", values, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotBetween(Integer value1, Integer value2) {
            addCriterion("abstain_ballot between", value1, value2, "abstainBallot");
            return (Criteria) this;
        }

        public Criteria andAbstainBallotNotBetween(Integer value1, Integer value2) {
            addCriterion("abstain_ballot not between", value1, value2, "abstainBallot");
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