package domain;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberTeacherExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberTeacherExample() {
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

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterionForJDBCDate("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterionForJDBCDate("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Byte value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Byte value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Byte value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Byte value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Byte value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Byte> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Byte> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Byte value1, Byte value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIsNull() {
            addCriterion("positive_time is null");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIsNotNull() {
            addCriterion("positive_time is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time =", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time <>", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("positive_time >", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time >=", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeLessThan(Date value) {
            addCriterionForJDBCDate("positive_time <", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time <=", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("positive_time in", values, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("positive_time not in", values, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("positive_time between", value1, value2, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("positive_time not between", value1, value2, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNull() {
            addCriterion("active_time is null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNotNull() {
            addCriterion("active_time is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("active_time =", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("active_time <>", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("active_time >", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_time >=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThan(Date value) {
            addCriterionForJDBCDate("active_time <", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_time <=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("active_time in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("active_time not in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_time between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_time not between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusIsNull() {
            addCriterion("political_status is null");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusIsNotNull() {
            addCriterion("political_status is not null");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusEqualTo(Byte value) {
            addCriterion("political_status =", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusNotEqualTo(Byte value) {
            addCriterion("political_status <>", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusGreaterThan(Byte value) {
            addCriterion("political_status >", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("political_status >=", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusLessThan(Byte value) {
            addCriterion("political_status <", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusLessThanOrEqualTo(Byte value) {
            addCriterion("political_status <=", value, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusIn(List<Byte> values) {
            addCriterion("political_status in", values, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusNotIn(List<Byte> values) {
            addCriterion("political_status not in", values, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusBetween(Byte value1, Byte value2) {
            addCriterion("political_status between", value1, value2, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticalStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("political_status not between", value1, value2, "politicalStatus");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNull() {
            addCriterion("transfer_time is null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNotNull() {
            addCriterion("transfer_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeEqualTo(Date value) {
            addCriterion("transfer_time =", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotEqualTo(Date value) {
            addCriterion("transfer_time <>", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThan(Date value) {
            addCriterion("transfer_time >", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("transfer_time >=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThan(Date value) {
            addCriterion("transfer_time <", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThanOrEqualTo(Date value) {
            addCriterion("transfer_time <=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIn(List<Date> values) {
            addCriterion("transfer_time in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotIn(List<Date> values) {
            addCriterion("transfer_time not in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeBetween(Date value1, Date value2) {
            addCriterion("transfer_time between", value1, value2, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotBetween(Date value1, Date value2) {
            addCriterion("transfer_time not between", value1, value2, "transferTime");
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

        public Criteria andCandidateTimeIsNull() {
            addCriterion("candidate_time is null");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeIsNotNull() {
            addCriterion("candidate_time is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time =", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time <>", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("candidate_time >", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time >=", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeLessThan(Date value) {
            addCriterionForJDBCDate("candidate_time <", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time <=", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_time in", values, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_time not in", values, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_time between", value1, value2, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_time not between", value1, value2, "candidateTime");
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

        public Criteria andPartyPostIsNull() {
            addCriterion("party_post is null");
            return (Criteria) this;
        }

        public Criteria andPartyPostIsNotNull() {
            addCriterion("party_post is not null");
            return (Criteria) this;
        }

        public Criteria andPartyPostEqualTo(String value) {
            addCriterion("party_post =", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostNotEqualTo(String value) {
            addCriterion("party_post <>", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostGreaterThan(String value) {
            addCriterion("party_post >", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostGreaterThanOrEqualTo(String value) {
            addCriterion("party_post >=", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostLessThan(String value) {
            addCriterion("party_post <", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostLessThanOrEqualTo(String value) {
            addCriterion("party_post <=", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostLike(String value) {
            addCriterion("party_post like", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostNotLike(String value) {
            addCriterion("party_post not like", value, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostIn(List<String> values) {
            addCriterion("party_post in", values, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostNotIn(List<String> values) {
            addCriterion("party_post not in", values, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostBetween(String value1, String value2) {
            addCriterion("party_post between", value1, value2, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyPostNotBetween(String value1, String value2) {
            addCriterion("party_post not between", value1, value2, "partyPost");
            return (Criteria) this;
        }

        public Criteria andPartyRewardIsNull() {
            addCriterion("party_reward is null");
            return (Criteria) this;
        }

        public Criteria andPartyRewardIsNotNull() {
            addCriterion("party_reward is not null");
            return (Criteria) this;
        }

        public Criteria andPartyRewardEqualTo(String value) {
            addCriterion("party_reward =", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardNotEqualTo(String value) {
            addCriterion("party_reward <>", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardGreaterThan(String value) {
            addCriterion("party_reward >", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardGreaterThanOrEqualTo(String value) {
            addCriterion("party_reward >=", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardLessThan(String value) {
            addCriterion("party_reward <", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardLessThanOrEqualTo(String value) {
            addCriterion("party_reward <=", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardLike(String value) {
            addCriterion("party_reward like", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardNotLike(String value) {
            addCriterion("party_reward not like", value, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardIn(List<String> values) {
            addCriterion("party_reward in", values, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardNotIn(List<String> values) {
            addCriterion("party_reward not in", values, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardBetween(String value1, String value2) {
            addCriterion("party_reward between", value1, value2, "partyReward");
            return (Criteria) this;
        }

        public Criteria andPartyRewardNotBetween(String value1, String value2) {
            addCriterion("party_reward not between", value1, value2, "partyReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardIsNull() {
            addCriterion("other_reward is null");
            return (Criteria) this;
        }

        public Criteria andOtherRewardIsNotNull() {
            addCriterion("other_reward is not null");
            return (Criteria) this;
        }

        public Criteria andOtherRewardEqualTo(String value) {
            addCriterion("other_reward =", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardNotEqualTo(String value) {
            addCriterion("other_reward <>", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardGreaterThan(String value) {
            addCriterion("other_reward >", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardGreaterThanOrEqualTo(String value) {
            addCriterion("other_reward >=", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardLessThan(String value) {
            addCriterion("other_reward <", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardLessThanOrEqualTo(String value) {
            addCriterion("other_reward <=", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardLike(String value) {
            addCriterion("other_reward like", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardNotLike(String value) {
            addCriterion("other_reward not like", value, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardIn(List<String> values) {
            addCriterion("other_reward in", values, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardNotIn(List<String> values) {
            addCriterion("other_reward not in", values, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardBetween(String value1, String value2) {
            addCriterion("other_reward between", value1, value2, "otherReward");
            return (Criteria) this;
        }

        public Criteria andOtherRewardNotBetween(String value1, String value2) {
            addCriterion("other_reward not between", value1, value2, "otherReward");
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

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNull() {
            addCriterion("school_type is null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNotNull() {
            addCriterion("school_type is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeEqualTo(String value) {
            addCriterion("school_type =", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotEqualTo(String value) {
            addCriterion("school_type <>", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThan(String value) {
            addCriterion("school_type >", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThanOrEqualTo(String value) {
            addCriterion("school_type >=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThan(String value) {
            addCriterion("school_type <", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThanOrEqualTo(String value) {
            addCriterion("school_type <=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLike(String value) {
            addCriterion("school_type like", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotLike(String value) {
            addCriterion("school_type not like", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIn(List<String> values) {
            addCriterion("school_type in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotIn(List<String> values) {
            addCriterion("school_type not in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeBetween(String value1, String value2) {
            addCriterion("school_type between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotBetween(String value1, String value2) {
            addCriterion("school_type not between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIsNull() {
            addCriterion("title_level is null");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIsNotNull() {
            addCriterion("title_level is not null");
            return (Criteria) this;
        }

        public Criteria andTitleLevelEqualTo(String value) {
            addCriterion("title_level =", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotEqualTo(String value) {
            addCriterion("title_level <>", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelGreaterThan(String value) {
            addCriterion("title_level >", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelGreaterThanOrEqualTo(String value) {
            addCriterion("title_level >=", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLessThan(String value) {
            addCriterion("title_level <", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLessThanOrEqualTo(String value) {
            addCriterion("title_level <=", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLike(String value) {
            addCriterion("title_level like", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotLike(String value) {
            addCriterion("title_level not like", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIn(List<String> values) {
            addCriterion("title_level in", values, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotIn(List<String> values) {
            addCriterion("title_level not in", values, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelBetween(String value1, String value2) {
            addCriterion("title_level between", value1, value2, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotBetween(String value1, String value2) {
            addCriterion("title_level not between", value1, value2, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIsNull() {
            addCriterion("staff_status is null");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIsNotNull() {
            addCriterion("staff_status is not null");
            return (Criteria) this;
        }

        public Criteria andStaffStatusEqualTo(String value) {
            addCriterion("staff_status =", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotEqualTo(String value) {
            addCriterion("staff_status <>", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusGreaterThan(String value) {
            addCriterion("staff_status >", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusGreaterThanOrEqualTo(String value) {
            addCriterion("staff_status >=", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLessThan(String value) {
            addCriterion("staff_status <", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLessThanOrEqualTo(String value) {
            addCriterion("staff_status <=", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLike(String value) {
            addCriterion("staff_status like", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotLike(String value) {
            addCriterion("staff_status not like", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIn(List<String> values) {
            addCriterion("staff_status in", values, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotIn(List<String> values) {
            addCriterion("staff_status not in", values, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusBetween(String value1, String value2) {
            addCriterion("staff_status between", value1, value2, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotBetween(String value1, String value2) {
            addCriterion("staff_status not between", value1, value2, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIsNull() {
            addCriterion("retire_time is null");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIsNotNull() {
            addCriterion("retire_time is not null");
            return (Criteria) this;
        }

        public Criteria andRetireTimeEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time =", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time <>", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("retire_time >", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time >=", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeLessThan(Date value) {
            addCriterionForJDBCDate("retire_time <", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time <=", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIn(List<Date> values) {
            addCriterionForJDBCDate("retire_time in", values, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("retire_time not in", values, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("retire_time between", value1, value2, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("retire_time not between", value1, value2, "retireTime");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andProPostIsNull() {
            addCriterion("pro_post is null");
            return (Criteria) this;
        }

        public Criteria andProPostIsNotNull() {
            addCriterion("pro_post is not null");
            return (Criteria) this;
        }

        public Criteria andProPostEqualTo(String value) {
            addCriterion("pro_post =", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotEqualTo(String value) {
            addCriterion("pro_post <>", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThan(String value) {
            addCriterion("pro_post >", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post >=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThan(String value) {
            addCriterion("pro_post <", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThanOrEqualTo(String value) {
            addCriterion("pro_post <=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLike(String value) {
            addCriterion("pro_post like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotLike(String value) {
            addCriterion("pro_post not like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostIn(List<String> values) {
            addCriterion("pro_post in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotIn(List<String> values) {
            addCriterion("pro_post not in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostBetween(String value1, String value2) {
            addCriterion("pro_post between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotBetween(String value1, String value2) {
            addCriterion("pro_post not between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andPostIsNull() {
            addCriterion("post is null");
            return (Criteria) this;
        }

        public Criteria andPostIsNotNull() {
            addCriterion("post is not null");
            return (Criteria) this;
        }

        public Criteria andPostEqualTo(String value) {
            addCriterion("post =", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotEqualTo(String value) {
            addCriterion("post <>", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThan(String value) {
            addCriterion("post >", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThanOrEqualTo(String value) {
            addCriterion("post >=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThan(String value) {
            addCriterion("post <", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThanOrEqualTo(String value) {
            addCriterion("post <=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLike(String value) {
            addCriterion("post like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotLike(String value) {
            addCriterion("post not like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostIn(List<String> values) {
            addCriterion("post in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotIn(List<String> values) {
            addCriterion("post not in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostBetween(String value1, String value2) {
            addCriterion("post between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotBetween(String value1, String value2) {
            addCriterion("post not between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNull() {
            addCriterion("is_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNotNull() {
            addCriterion("is_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsRetireEqualTo(Boolean value) {
            addCriterion("is_retire =", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotEqualTo(Boolean value) {
            addCriterion("is_retire <>", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThan(Boolean value) {
            addCriterion("is_retire >", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_retire >=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThan(Boolean value) {
            addCriterion("is_retire <", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_retire <=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireIn(List<Boolean> values) {
            addCriterion("is_retire in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotIn(List<Boolean> values) {
            addCriterion("is_retire not in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire not between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIsNull() {
            addCriterion("is_honor_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIsNotNull() {
            addCriterion("is_honor_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireEqualTo(Boolean value) {
            addCriterion("is_honor_retire =", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotEqualTo(Boolean value) {
            addCriterion("is_honor_retire <>", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireGreaterThan(Boolean value) {
            addCriterion("is_honor_retire >", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_honor_retire >=", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireLessThan(Boolean value) {
            addCriterion("is_honor_retire <", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_honor_retire <=", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIn(List<Boolean> values) {
            addCriterion("is_honor_retire in", values, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotIn(List<Boolean> values) {
            addCriterion("is_honor_retire not in", values, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_honor_retire between", value1, value2, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_honor_retire not between", value1, value2, "isHonorRetire");
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

        public Criteria andPostTypeEqualTo(String value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(String value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(String value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(String value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(String value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(String value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLike(String value) {
            addCriterion("post_type like", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotLike(String value) {
            addCriterion("post_type not like", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<String> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<String> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(String value1, String value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(String value1, String value2) {
            addCriterion("post_type not between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIsNull() {
            addCriterion("degree_time is null");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIsNotNull() {
            addCriterion("degree_time is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeEqualTo(String value) {
            addCriterion("degree_time =", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotEqualTo(String value) {
            addCriterion("degree_time <>", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeGreaterThan(String value) {
            addCriterion("degree_time >", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeGreaterThanOrEqualTo(String value) {
            addCriterion("degree_time >=", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeLessThan(String value) {
            addCriterion("degree_time <", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeLessThanOrEqualTo(String value) {
            addCriterion("degree_time <=", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeLike(String value) {
            addCriterion("degree_time like", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotLike(String value) {
            addCriterion("degree_time not like", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIn(List<String> values) {
            addCriterion("degree_time in", values, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotIn(List<String> values) {
            addCriterion("degree_time not in", values, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeBetween(String value1, String value2) {
            addCriterion("degree_time between", value1, value2, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotBetween(String value1, String value2) {
            addCriterion("degree_time not between", value1, value2, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPostLevelIsNull() {
            addCriterion("post_level is null");
            return (Criteria) this;
        }

        public Criteria andPostLevelIsNotNull() {
            addCriterion("post_level is not null");
            return (Criteria) this;
        }

        public Criteria andPostLevelEqualTo(String value) {
            addCriterion("post_level =", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotEqualTo(String value) {
            addCriterion("post_level <>", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelGreaterThan(String value) {
            addCriterion("post_level >", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("post_level >=", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLessThan(String value) {
            addCriterion("post_level <", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLessThanOrEqualTo(String value) {
            addCriterion("post_level <=", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLike(String value) {
            addCriterion("post_level like", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotLike(String value) {
            addCriterion("post_level not like", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelIn(List<String> values) {
            addCriterion("post_level in", values, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotIn(List<String> values) {
            addCriterion("post_level not in", values, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelBetween(String value1, String value2) {
            addCriterion("post_level between", value1, value2, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotBetween(String value1, String value2) {
            addCriterion("post_level not between", value1, value2, "postLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNull() {
            addCriterion("office_level is null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNotNull() {
            addCriterion("office_level is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelEqualTo(String value) {
            addCriterion("office_level =", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotEqualTo(String value) {
            addCriterion("office_level <>", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThan(String value) {
            addCriterion("office_level >", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThanOrEqualTo(String value) {
            addCriterion("office_level >=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThan(String value) {
            addCriterion("office_level <", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThanOrEqualTo(String value) {
            addCriterion("office_level <=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLike(String value) {
            addCriterion("office_level like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotLike(String value) {
            addCriterion("office_level not like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIn(List<String> values) {
            addCriterion("office_level in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotIn(List<String> values) {
            addCriterion("office_level not in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelBetween(String value1, String value2) {
            addCriterion("office_level between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotBetween(String value1, String value2) {
            addCriterion("office_level not between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNull() {
            addCriterion("talent_title is null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNotNull() {
            addCriterion("talent_title is not null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleEqualTo(String value) {
            addCriterion("talent_title =", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotEqualTo(String value) {
            addCriterion("talent_title <>", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThan(String value) {
            addCriterion("talent_title >", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThanOrEqualTo(String value) {
            addCriterion("talent_title >=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThan(String value) {
            addCriterion("talent_title <", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThanOrEqualTo(String value) {
            addCriterion("talent_title <=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLike(String value) {
            addCriterion("talent_title like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotLike(String value) {
            addCriterion("talent_title not like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIn(List<String> values) {
            addCriterion("talent_title in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotIn(List<String> values) {
            addCriterion("talent_title not in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleBetween(String value1, String value2) {
            addCriterion("talent_title between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotBetween(String value1, String value2) {
            addCriterion("talent_title not between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNull() {
            addCriterion("authorized_type is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNotNull() {
            addCriterion("authorized_type is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeEqualTo(String value) {
            addCriterion("authorized_type =", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotEqualTo(String value) {
            addCriterion("authorized_type <>", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThan(String value) {
            addCriterion("authorized_type >", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThanOrEqualTo(String value) {
            addCriterion("authorized_type >=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThan(String value) {
            addCriterion("authorized_type <", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThanOrEqualTo(String value) {
            addCriterion("authorized_type <=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLike(String value) {
            addCriterion("authorized_type like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotLike(String value) {
            addCriterion("authorized_type not like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIn(List<String> values) {
            addCriterion("authorized_type in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotIn(List<String> values) {
            addCriterion("authorized_type not in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeBetween(String value1, String value2) {
            addCriterion("authorized_type between", value1, value2, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotBetween(String value1, String value2) {
            addCriterion("authorized_type not between", value1, value2, "authorizedType");
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

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(String value) {
            addCriterion("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(String value) {
            addCriterion("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(String value) {
            addCriterion("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(String value) {
            addCriterion("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(String value) {
            addCriterion("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(String value) {
            addCriterion("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLike(String value) {
            addCriterion("arrive_time like", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotLike(String value) {
            addCriterion("arrive_time not like", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<String> values) {
            addCriterion("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<String> values) {
            addCriterion("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(String value1, String value2) {
            addCriterion("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(String value1, String value2) {
            addCriterion("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNull() {
            addCriterion("marital_status is null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNotNull() {
            addCriterion("marital_status is not null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusEqualTo(String value) {
            addCriterion("marital_status =", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotEqualTo(String value) {
            addCriterion("marital_status <>", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThan(String value) {
            addCriterion("marital_status >", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("marital_status >=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThan(String value) {
            addCriterion("marital_status <", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThanOrEqualTo(String value) {
            addCriterion("marital_status <=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLike(String value) {
            addCriterion("marital_status like", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotLike(String value) {
            addCriterion("marital_status not like", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIn(List<String> values) {
            addCriterion("marital_status in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotIn(List<String> values) {
            addCriterion("marital_status not in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusBetween(String value1, String value2) {
            addCriterion("marital_status between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotBetween(String value1, String value2) {
            addCriterion("marital_status not between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNull() {
            addCriterion("staff_type is null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNotNull() {
            addCriterion("staff_type is not null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeEqualTo(String value) {
            addCriterion("staff_type =", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotEqualTo(String value) {
            addCriterion("staff_type <>", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThan(String value) {
            addCriterion("staff_type >", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThanOrEqualTo(String value) {
            addCriterion("staff_type >=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThan(String value) {
            addCriterion("staff_type <", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThanOrEqualTo(String value) {
            addCriterion("staff_type <=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLike(String value) {
            addCriterion("staff_type like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotLike(String value) {
            addCriterion("staff_type not like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIn(List<String> values) {
            addCriterion("staff_type in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotIn(List<String> values) {
            addCriterion("staff_type not in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeBetween(String value1, String value2) {
            addCriterion("staff_type between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotBetween(String value1, String value2) {
            addCriterion("staff_type not between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andOnJobIsNull() {
            addCriterion("on_job is null");
            return (Criteria) this;
        }

        public Criteria andOnJobIsNotNull() {
            addCriterion("on_job is not null");
            return (Criteria) this;
        }

        public Criteria andOnJobEqualTo(String value) {
            addCriterion("on_job =", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotEqualTo(String value) {
            addCriterion("on_job <>", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobGreaterThan(String value) {
            addCriterion("on_job >", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobGreaterThanOrEqualTo(String value) {
            addCriterion("on_job >=", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLessThan(String value) {
            addCriterion("on_job <", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLessThanOrEqualTo(String value) {
            addCriterion("on_job <=", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLike(String value) {
            addCriterion("on_job like", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotLike(String value) {
            addCriterion("on_job not like", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobIn(List<String> values) {
            addCriterion("on_job in", values, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotIn(List<String> values) {
            addCriterion("on_job not in", values, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobBetween(String value1, String value2) {
            addCriterion("on_job between", value1, value2, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotBetween(String value1, String value2) {
            addCriterion("on_job not between", value1, value2, "onJob");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole(SystemConstants.ROLE_ADMIN)
                    || subject.hasRole(SystemConstants.ROLE_ODADMIN))
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