package domain.member;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberInflowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberInflowExample() {
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

        public Criteria andBranchNameIsNull() {
            addCriterion("branch_name is null");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNotNull() {
            addCriterion("branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNameEqualTo(String value) {
            addCriterion("branch_name =", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotEqualTo(String value) {
            addCriterion("branch_name <>", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThan(String value) {
            addCriterion("branch_name >", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("branch_name >=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThan(String value) {
            addCriterion("branch_name <", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThanOrEqualTo(String value) {
            addCriterion("branch_name <=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLike(String value) {
            addCriterion("branch_name like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotLike(String value) {
            addCriterion("branch_name not like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameIn(List<String> values) {
            addCriterion("branch_name in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotIn(List<String> values) {
            addCriterion("branch_name not in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameBetween(String value1, String value2) {
            addCriterion("branch_name between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotBetween(String value1, String value2) {
            addCriterion("branch_name not between", value1, value2, "branchName");
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

        public Criteria andOriginalJobIsNull() {
            addCriterion("original_job is null");
            return (Criteria) this;
        }

        public Criteria andOriginalJobIsNotNull() {
            addCriterion("original_job is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalJobEqualTo(Integer value) {
            addCriterion("original_job =", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobNotEqualTo(Integer value) {
            addCriterion("original_job <>", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobGreaterThan(Integer value) {
            addCriterion("original_job >", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_job >=", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobLessThan(Integer value) {
            addCriterion("original_job <", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobLessThanOrEqualTo(Integer value) {
            addCriterion("original_job <=", value, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobIn(List<Integer> values) {
            addCriterion("original_job in", values, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobNotIn(List<Integer> values) {
            addCriterion("original_job not in", values, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobBetween(Integer value1, Integer value2) {
            addCriterion("original_job between", value1, value2, "originalJob");
            return (Criteria) this;
        }

        public Criteria andOriginalJobNotBetween(Integer value1, Integer value2) {
            addCriterion("original_job not between", value1, value2, "originalJob");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(Integer value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(Integer value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(Integer value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(Integer value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(Integer value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(Integer value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<Integer> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<Integer> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(Integer value1, Integer value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(Integer value1, Integer value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andHasPapersIsNull() {
            addCriterion("has_papers is null");
            return (Criteria) this;
        }

        public Criteria andHasPapersIsNotNull() {
            addCriterion("has_papers is not null");
            return (Criteria) this;
        }

        public Criteria andHasPapersEqualTo(Boolean value) {
            addCriterion("has_papers =", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersNotEqualTo(Boolean value) {
            addCriterion("has_papers <>", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersGreaterThan(Boolean value) {
            addCriterion("has_papers >", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_papers >=", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersLessThan(Boolean value) {
            addCriterion("has_papers <", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersLessThanOrEqualTo(Boolean value) {
            addCriterion("has_papers <=", value, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersIn(List<Boolean> values) {
            addCriterion("has_papers in", values, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersNotIn(List<Boolean> values) {
            addCriterion("has_papers not in", values, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersBetween(Boolean value1, Boolean value2) {
            addCriterion("has_papers between", value1, value2, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andHasPapersNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_papers not between", value1, value2, "hasPapers");
            return (Criteria) this;
        }

        public Criteria andFlowTimeIsNull() {
            addCriterion("flow_time is null");
            return (Criteria) this;
        }

        public Criteria andFlowTimeIsNotNull() {
            addCriterion("flow_time is not null");
            return (Criteria) this;
        }

        public Criteria andFlowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("flow_time =", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("flow_time <>", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("flow_time >", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("flow_time >=", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeLessThan(Date value) {
            addCriterionForJDBCDate("flow_time <", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("flow_time <=", value, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("flow_time in", values, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("flow_time not in", values, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("flow_time between", value1, value2, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("flow_time not between", value1, value2, "flowTime");
            return (Criteria) this;
        }

        public Criteria andFlowReasonIsNull() {
            addCriterion("flow_reason is null");
            return (Criteria) this;
        }

        public Criteria andFlowReasonIsNotNull() {
            addCriterion("flow_reason is not null");
            return (Criteria) this;
        }

        public Criteria andFlowReasonEqualTo(String value) {
            addCriterion("flow_reason =", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonNotEqualTo(String value) {
            addCriterion("flow_reason <>", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonGreaterThan(String value) {
            addCriterion("flow_reason >", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonGreaterThanOrEqualTo(String value) {
            addCriterion("flow_reason >=", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonLessThan(String value) {
            addCriterion("flow_reason <", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonLessThanOrEqualTo(String value) {
            addCriterion("flow_reason <=", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonLike(String value) {
            addCriterion("flow_reason like", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonNotLike(String value) {
            addCriterion("flow_reason not like", value, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonIn(List<String> values) {
            addCriterion("flow_reason in", values, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonNotIn(List<String> values) {
            addCriterion("flow_reason not in", values, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonBetween(String value1, String value2) {
            addCriterion("flow_reason between", value1, value2, "flowReason");
            return (Criteria) this;
        }

        public Criteria andFlowReasonNotBetween(String value1, String value2) {
            addCriterion("flow_reason not between", value1, value2, "flowReason");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNull() {
            addCriterion("grow_time is null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNotNull() {
            addCriterion("grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time =", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <>", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("grow_time >", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time >=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("grow_time <", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time not in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time not between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andOrLocationIsNull() {
            addCriterion("or_location is null");
            return (Criteria) this;
        }

        public Criteria andOrLocationIsNotNull() {
            addCriterion("or_location is not null");
            return (Criteria) this;
        }

        public Criteria andOrLocationEqualTo(String value) {
            addCriterion("or_location =", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationNotEqualTo(String value) {
            addCriterion("or_location <>", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationGreaterThan(String value) {
            addCriterion("or_location >", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationGreaterThanOrEqualTo(String value) {
            addCriterion("or_location >=", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationLessThan(String value) {
            addCriterion("or_location <", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationLessThanOrEqualTo(String value) {
            addCriterion("or_location <=", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationLike(String value) {
            addCriterion("or_location like", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationNotLike(String value) {
            addCriterion("or_location not like", value, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationIn(List<String> values) {
            addCriterion("or_location in", values, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationNotIn(List<String> values) {
            addCriterion("or_location not in", values, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationBetween(String value1, String value2) {
            addCriterion("or_location between", value1, value2, "orLocation");
            return (Criteria) this;
        }

        public Criteria andOrLocationNotBetween(String value1, String value2) {
            addCriterion("or_location not between", value1, value2, "orLocation");
            return (Criteria) this;
        }

        public Criteria andInflowStatusIsNull() {
            addCriterion("inflow_status is null");
            return (Criteria) this;
        }

        public Criteria andInflowStatusIsNotNull() {
            addCriterion("inflow_status is not null");
            return (Criteria) this;
        }

        public Criteria andInflowStatusEqualTo(Byte value) {
            addCriterion("inflow_status =", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusNotEqualTo(Byte value) {
            addCriterion("inflow_status <>", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusGreaterThan(Byte value) {
            addCriterion("inflow_status >", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("inflow_status >=", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusLessThan(Byte value) {
            addCriterion("inflow_status <", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusLessThanOrEqualTo(Byte value) {
            addCriterion("inflow_status <=", value, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusIn(List<Byte> values) {
            addCriterion("inflow_status in", values, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusNotIn(List<Byte> values) {
            addCriterion("inflow_status not in", values, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusBetween(Byte value1, Byte value2) {
            addCriterion("inflow_status between", value1, value2, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andInflowStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("inflow_status not between", value1, value2, "inflowStatus");
            return (Criteria) this;
        }

        public Criteria andIsBackIsNull() {
            addCriterion("is_back is null");
            return (Criteria) this;
        }

        public Criteria andIsBackIsNotNull() {
            addCriterion("is_back is not null");
            return (Criteria) this;
        }

        public Criteria andIsBackEqualTo(Boolean value) {
            addCriterion("is_back =", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotEqualTo(Boolean value) {
            addCriterion("is_back <>", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackGreaterThan(Boolean value) {
            addCriterion("is_back >", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_back >=", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackLessThan(Boolean value) {
            addCriterion("is_back <", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackLessThanOrEqualTo(Boolean value) {
            addCriterion("is_back <=", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackIn(List<Boolean> values) {
            addCriterion("is_back in", values, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotIn(List<Boolean> values) {
            addCriterion("is_back not in", values, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackBetween(Boolean value1, Boolean value2) {
            addCriterion("is_back between", value1, value2, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_back not between", value1, value2, "isBack");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andOutUnitIsNull() {
            addCriterion("out_unit is null");
            return (Criteria) this;
        }

        public Criteria andOutUnitIsNotNull() {
            addCriterion("out_unit is not null");
            return (Criteria) this;
        }

        public Criteria andOutUnitEqualTo(String value) {
            addCriterion("out_unit =", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotEqualTo(String value) {
            addCriterion("out_unit <>", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitGreaterThan(String value) {
            addCriterion("out_unit >", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitGreaterThanOrEqualTo(String value) {
            addCriterion("out_unit >=", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLessThan(String value) {
            addCriterion("out_unit <", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLessThanOrEqualTo(String value) {
            addCriterion("out_unit <=", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLike(String value) {
            addCriterion("out_unit like", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotLike(String value) {
            addCriterion("out_unit not like", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitIn(List<String> values) {
            addCriterion("out_unit in", values, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotIn(List<String> values) {
            addCriterion("out_unit not in", values, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitBetween(String value1, String value2) {
            addCriterion("out_unit between", value1, value2, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotBetween(String value1, String value2) {
            addCriterion("out_unit not between", value1, value2, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutLocationIsNull() {
            addCriterion("out_location is null");
            return (Criteria) this;
        }

        public Criteria andOutLocationIsNotNull() {
            addCriterion("out_location is not null");
            return (Criteria) this;
        }

        public Criteria andOutLocationEqualTo(Integer value) {
            addCriterion("out_location =", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationNotEqualTo(Integer value) {
            addCriterion("out_location <>", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationGreaterThan(Integer value) {
            addCriterion("out_location >", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_location >=", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationLessThan(Integer value) {
            addCriterion("out_location <", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationLessThanOrEqualTo(Integer value) {
            addCriterion("out_location <=", value, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationIn(List<Integer> values) {
            addCriterion("out_location in", values, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationNotIn(List<Integer> values) {
            addCriterion("out_location not in", values, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationBetween(Integer value1, Integer value2) {
            addCriterion("out_location between", value1, value2, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutLocationNotBetween(Integer value1, Integer value2) {
            addCriterion("out_location not between", value1, value2, "outLocation");
            return (Criteria) this;
        }

        public Criteria andOutTimeIsNull() {
            addCriterion("out_time is null");
            return (Criteria) this;
        }

        public Criteria andOutTimeIsNotNull() {
            addCriterion("out_time is not null");
            return (Criteria) this;
        }

        public Criteria andOutTimeEqualTo(Date value) {
            addCriterionForJDBCDate("out_time =", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("out_time <>", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("out_time >", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("out_time >=", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeLessThan(Date value) {
            addCriterionForJDBCDate("out_time <", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("out_time <=", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeIn(List<Date> values) {
            addCriterionForJDBCDate("out_time in", values, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("out_time not in", values, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("out_time between", value1, value2, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("out_time not between", value1, value2, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutStatusIsNull() {
            addCriterion("out_status is null");
            return (Criteria) this;
        }

        public Criteria andOutStatusIsNotNull() {
            addCriterion("out_status is not null");
            return (Criteria) this;
        }

        public Criteria andOutStatusEqualTo(Byte value) {
            addCriterion("out_status =", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotEqualTo(Byte value) {
            addCriterion("out_status <>", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusGreaterThan(Byte value) {
            addCriterion("out_status >", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("out_status >=", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusLessThan(Byte value) {
            addCriterion("out_status <", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusLessThanOrEqualTo(Byte value) {
            addCriterion("out_status <=", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusIn(List<Byte> values) {
            addCriterion("out_status in", values, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotIn(List<Byte> values) {
            addCriterion("out_status not in", values, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusBetween(Byte value1, Byte value2) {
            addCriterion("out_status between", value1, value2, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("out_status not between", value1, value2, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutIsBackIsNull() {
            addCriterion("out_is_back is null");
            return (Criteria) this;
        }

        public Criteria andOutIsBackIsNotNull() {
            addCriterion("out_is_back is not null");
            return (Criteria) this;
        }

        public Criteria andOutIsBackEqualTo(Boolean value) {
            addCriterion("out_is_back =", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackNotEqualTo(Boolean value) {
            addCriterion("out_is_back <>", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackGreaterThan(Boolean value) {
            addCriterion("out_is_back >", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackGreaterThanOrEqualTo(Boolean value) {
            addCriterion("out_is_back >=", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackLessThan(Boolean value) {
            addCriterion("out_is_back <", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackLessThanOrEqualTo(Boolean value) {
            addCriterion("out_is_back <=", value, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackIn(List<Boolean> values) {
            addCriterion("out_is_back in", values, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackNotIn(List<Boolean> values) {
            addCriterion("out_is_back not in", values, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackBetween(Boolean value1, Boolean value2) {
            addCriterion("out_is_back between", value1, value2, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutIsBackNotBetween(Boolean value1, Boolean value2) {
            addCriterion("out_is_back not between", value1, value2, "outIsBack");
            return (Criteria) this;
        }

        public Criteria andOutReasonIsNull() {
            addCriterion("out_reason is null");
            return (Criteria) this;
        }

        public Criteria andOutReasonIsNotNull() {
            addCriterion("out_reason is not null");
            return (Criteria) this;
        }

        public Criteria andOutReasonEqualTo(String value) {
            addCriterion("out_reason =", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonNotEqualTo(String value) {
            addCriterion("out_reason <>", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonGreaterThan(String value) {
            addCriterion("out_reason >", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonGreaterThanOrEqualTo(String value) {
            addCriterion("out_reason >=", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonLessThan(String value) {
            addCriterion("out_reason <", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonLessThanOrEqualTo(String value) {
            addCriterion("out_reason <=", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonLike(String value) {
            addCriterion("out_reason like", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonNotLike(String value) {
            addCriterion("out_reason not like", value, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonIn(List<String> values) {
            addCriterion("out_reason in", values, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonNotIn(List<String> values) {
            addCriterion("out_reason not in", values, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonBetween(String value1, String value2) {
            addCriterion("out_reason between", value1, value2, "outReason");
            return (Criteria) this;
        }

        public Criteria andOutReasonNotBetween(String value1, String value2) {
            addCriterion("out_reason not between", value1, value2, "outReason");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andUserIdIsNull();
            return this;
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