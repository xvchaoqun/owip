package domain.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PartyRewardViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PartyRewardViewExample() {
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

        public Criteria andRewardTimeIsNull() {
            addCriterion("reward_time is null");
            return (Criteria) this;
        }

        public Criteria andRewardTimeIsNotNull() {
            addCriterion("reward_time is not null");
            return (Criteria) this;
        }

        public Criteria andRewardTimeEqualTo(Date value) {
            addCriterionForJDBCDate("reward_time =", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("reward_time <>", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("reward_time >", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reward_time >=", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeLessThan(Date value) {
            addCriterionForJDBCDate("reward_time <", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reward_time <=", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeIn(List<Date> values) {
            addCriterionForJDBCDate("reward_time in", values, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("reward_time not in", values, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reward_time between", value1, value2, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reward_time not between", value1, value2, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardLevelIsNull() {
            addCriterion("reward_level is null");
            return (Criteria) this;
        }

        public Criteria andRewardLevelIsNotNull() {
            addCriterion("reward_level is not null");
            return (Criteria) this;
        }

        public Criteria andRewardLevelEqualTo(Integer value) {
            addCriterion("reward_level =", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelNotEqualTo(Integer value) {
            addCriterion("reward_level <>", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelGreaterThan(Integer value) {
            addCriterion("reward_level >", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_level >=", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelLessThan(Integer value) {
            addCriterion("reward_level <", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelLessThanOrEqualTo(Integer value) {
            addCriterion("reward_level <=", value, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelIn(List<Integer> values) {
            addCriterion("reward_level in", values, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelNotIn(List<Integer> values) {
            addCriterion("reward_level not in", values, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelBetween(Integer value1, Integer value2) {
            addCriterion("reward_level between", value1, value2, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_level not between", value1, value2, "rewardLevel");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIsNull() {
            addCriterion("reward_type is null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIsNotNull() {
            addCriterion("reward_type is not null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeEqualTo(Integer value) {
            addCriterion("reward_type =", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotEqualTo(Integer value) {
            addCriterion("reward_type <>", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThan(Integer value) {
            addCriterion("reward_type >", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_type >=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThan(Integer value) {
            addCriterion("reward_type <", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("reward_type <=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIn(List<Integer> values) {
            addCriterion("reward_type in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotIn(List<Integer> values) {
            addCriterion("reward_type not in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeBetween(Integer value1, Integer value2) {
            addCriterion("reward_type between", value1, value2, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_type not between", value1, value2, "rewardType");
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

        public Criteria andProofIsNull() {
            addCriterion("proof is null");
            return (Criteria) this;
        }

        public Criteria andProofIsNotNull() {
            addCriterion("proof is not null");
            return (Criteria) this;
        }

        public Criteria andProofEqualTo(String value) {
            addCriterion("proof =", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofNotEqualTo(String value) {
            addCriterion("proof <>", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofGreaterThan(String value) {
            addCriterion("proof >", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofGreaterThanOrEqualTo(String value) {
            addCriterion("proof >=", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofLessThan(String value) {
            addCriterion("proof <", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofLessThanOrEqualTo(String value) {
            addCriterion("proof <=", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofLike(String value) {
            addCriterion("proof like", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofNotLike(String value) {
            addCriterion("proof not like", value, "proof");
            return (Criteria) this;
        }

        public Criteria andProofIn(List<String> values) {
            addCriterion("proof in", values, "proof");
            return (Criteria) this;
        }

        public Criteria andProofNotIn(List<String> values) {
            addCriterion("proof not in", values, "proof");
            return (Criteria) this;
        }

        public Criteria andProofBetween(String value1, String value2) {
            addCriterion("proof between", value1, value2, "proof");
            return (Criteria) this;
        }

        public Criteria andProofNotBetween(String value1, String value2) {
            addCriterion("proof not between", value1, value2, "proof");
            return (Criteria) this;
        }

        public Criteria andProofFilenameIsNull() {
            addCriterion("proof_filename is null");
            return (Criteria) this;
        }

        public Criteria andProofFilenameIsNotNull() {
            addCriterion("proof_filename is not null");
            return (Criteria) this;
        }

        public Criteria andProofFilenameEqualTo(String value) {
            addCriterion("proof_filename =", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameNotEqualTo(String value) {
            addCriterion("proof_filename <>", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameGreaterThan(String value) {
            addCriterion("proof_filename >", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("proof_filename >=", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameLessThan(String value) {
            addCriterion("proof_filename <", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameLessThanOrEqualTo(String value) {
            addCriterion("proof_filename <=", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameLike(String value) {
            addCriterion("proof_filename like", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameNotLike(String value) {
            addCriterion("proof_filename not like", value, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameIn(List<String> values) {
            addCriterion("proof_filename in", values, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameNotIn(List<String> values) {
            addCriterion("proof_filename not in", values, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameBetween(String value1, String value2) {
            addCriterion("proof_filename between", value1, value2, "proofFilename");
            return (Criteria) this;
        }

        public Criteria andProofFilenameNotBetween(String value1, String value2) {
            addCriterion("proof_filename not between", value1, value2, "proofFilename");
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

        public Criteria andBranchSortOrderIsNull() {
            addCriterion("branch_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIsNotNull() {
            addCriterion("branch_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderEqualTo(Integer value) {
            addCriterion("branch_sort_order =", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotEqualTo(Integer value) {
            addCriterion("branch_sort_order <>", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThan(Integer value) {
            addCriterion("branch_sort_order >", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order >=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThan(Integer value) {
            addCriterion("branch_sort_order <", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order <=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIn(List<Integer> values) {
            addCriterion("branch_sort_order in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotIn(List<Integer> values) {
            addCriterion("branch_sort_order not in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order not between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdIsNull() {
            addCriterion("branch_party_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdIsNotNull() {
            addCriterion("branch_party_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdEqualTo(Integer value) {
            addCriterion("branch_party_id =", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdNotEqualTo(Integer value) {
            addCriterion("branch_party_id <>", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdGreaterThan(Integer value) {
            addCriterion("branch_party_id >", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_party_id >=", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdLessThan(Integer value) {
            addCriterion("branch_party_id <", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_party_id <=", value, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdIn(List<Integer> values) {
            addCriterion("branch_party_id in", values, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdNotIn(List<Integer> values) {
            addCriterion("branch_party_id not in", values, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_party_id between", value1, value2, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andBranchPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_party_id not between", value1, value2, "branchPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdIsNull() {
            addCriterion("user_party_id is null");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdIsNotNull() {
            addCriterion("user_party_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdEqualTo(Integer value) {
            addCriterion("user_party_id =", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdNotEqualTo(Integer value) {
            addCriterion("user_party_id <>", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdGreaterThan(Integer value) {
            addCriterion("user_party_id >", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_party_id >=", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdLessThan(Integer value) {
            addCriterion("user_party_id <", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_party_id <=", value, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdIn(List<Integer> values) {
            addCriterion("user_party_id in", values, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdNotIn(List<Integer> values) {
            addCriterion("user_party_id not in", values, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("user_party_id between", value1, value2, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_party_id not between", value1, value2, "userPartyId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdIsNull() {
            addCriterion("user_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdIsNotNull() {
            addCriterion("user_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdEqualTo(Integer value) {
            addCriterion("user_branch_id =", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdNotEqualTo(Integer value) {
            addCriterion("user_branch_id <>", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdGreaterThan(Integer value) {
            addCriterion("user_branch_id >", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_branch_id >=", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdLessThan(Integer value) {
            addCriterion("user_branch_id <", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_branch_id <=", value, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdIn(List<Integer> values) {
            addCriterion("user_branch_id in", values, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdNotIn(List<Integer> values) {
            addCriterion("user_branch_id not in", values, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("user_branch_id between", value1, value2, "userBranchId");
            return (Criteria) this;
        }

        public Criteria andUserBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_branch_id not between", value1, value2, "userBranchId");
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