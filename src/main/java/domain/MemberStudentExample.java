package domain;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberStudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberStudentExample() {
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

        public Criteria andDelayYearIsNull() {
            addCriterion("delay_year is null");
            return (Criteria) this;
        }

        public Criteria andDelayYearIsNotNull() {
            addCriterion("delay_year is not null");
            return (Criteria) this;
        }

        public Criteria andDelayYearEqualTo(Float value) {
            addCriterion("delay_year =", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearNotEqualTo(Float value) {
            addCriterion("delay_year <>", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearGreaterThan(Float value) {
            addCriterion("delay_year >", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearGreaterThanOrEqualTo(Float value) {
            addCriterion("delay_year >=", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearLessThan(Float value) {
            addCriterion("delay_year <", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearLessThanOrEqualTo(Float value) {
            addCriterion("delay_year <=", value, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearIn(List<Float> values) {
            addCriterion("delay_year in", values, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearNotIn(List<Float> values) {
            addCriterion("delay_year not in", values, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearBetween(Float value1, Float value2) {
            addCriterion("delay_year between", value1, value2, "delayYear");
            return (Criteria) this;
        }

        public Criteria andDelayYearNotBetween(Float value1, Float value2) {
            addCriterion("delay_year not between", value1, value2, "delayYear");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(String value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(String value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(String value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(String value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(String value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLike(String value) {
            addCriterion("period like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotLike(String value) {
            addCriterion("period not like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<String> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<String> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(String value1, String value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(String value1, String value2) {
            addCriterion("period not between", value1, value2, "period");
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

        public Criteria andEduCategoryIsNull() {
            addCriterion("edu_category is null");
            return (Criteria) this;
        }

        public Criteria andEduCategoryIsNotNull() {
            addCriterion("edu_category is not null");
            return (Criteria) this;
        }

        public Criteria andEduCategoryEqualTo(String value) {
            addCriterion("edu_category =", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryNotEqualTo(String value) {
            addCriterion("edu_category <>", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryGreaterThan(String value) {
            addCriterion("edu_category >", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("edu_category >=", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryLessThan(String value) {
            addCriterion("edu_category <", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryLessThanOrEqualTo(String value) {
            addCriterion("edu_category <=", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryLike(String value) {
            addCriterion("edu_category like", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryNotLike(String value) {
            addCriterion("edu_category not like", value, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryIn(List<String> values) {
            addCriterion("edu_category in", values, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryNotIn(List<String> values) {
            addCriterion("edu_category not in", values, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryBetween(String value1, String value2) {
            addCriterion("edu_category between", value1, value2, "eduCategory");
            return (Criteria) this;
        }

        public Criteria andEduCategoryNotBetween(String value1, String value2) {
            addCriterion("edu_category not between", value1, value2, "eduCategory");
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

        public Criteria andActualGraduateTimeIsNull() {
            addCriterion("actual_graduate_time is null");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeIsNotNull() {
            addCriterion("actual_graduate_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("actual_graduate_time =", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_graduate_time <>", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_graduate_time >", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_graduate_time >=", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeLessThan(Date value) {
            addCriterionForJDBCDate("actual_graduate_time <", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_graduate_time <=", value, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("actual_graduate_time in", values, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_graduate_time not in", values, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_graduate_time between", value1, value2, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualGraduateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_graduate_time not between", value1, value2, "actualGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeIsNull() {
            addCriterion("expect_graduate_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeIsNotNull() {
            addCriterion("expect_graduate_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("expect_graduate_time =", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_graduate_time <>", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_graduate_time >", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_graduate_time >=", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeLessThan(Date value) {
            addCriterionForJDBCDate("expect_graduate_time <", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_graduate_time <=", value, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("expect_graduate_time in", values, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_graduate_time not in", values, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_graduate_time between", value1, value2, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andExpectGraduateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_graduate_time not between", value1, value2, "expectGraduateTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeIsNull() {
            addCriterion("actual_enrol_time is null");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeIsNotNull() {
            addCriterion("actual_enrol_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeEqualTo(Date value) {
            addCriterionForJDBCDate("actual_enrol_time =", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_enrol_time <>", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_enrol_time >", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_enrol_time >=", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeLessThan(Date value) {
            addCriterionForJDBCDate("actual_enrol_time <", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_enrol_time <=", value, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeIn(List<Date> values) {
            addCriterionForJDBCDate("actual_enrol_time in", values, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_enrol_time not in", values, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_enrol_time between", value1, value2, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andActualEnrolTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_enrol_time not between", value1, value2, "actualEnrolTime");
            return (Criteria) this;
        }

        public Criteria andSyncSourceIsNull() {
            addCriterion("sync_source is null");
            return (Criteria) this;
        }

        public Criteria andSyncSourceIsNotNull() {
            addCriterion("sync_source is not null");
            return (Criteria) this;
        }

        public Criteria andSyncSourceEqualTo(Byte value) {
            addCriterion("sync_source =", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceNotEqualTo(Byte value) {
            addCriterion("sync_source <>", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceGreaterThan(Byte value) {
            addCriterion("sync_source >", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("sync_source >=", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceLessThan(Byte value) {
            addCriterion("sync_source <", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceLessThanOrEqualTo(Byte value) {
            addCriterion("sync_source <=", value, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceIn(List<Byte> values) {
            addCriterion("sync_source in", values, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceNotIn(List<Byte> values) {
            addCriterion("sync_source not in", values, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceBetween(Byte value1, Byte value2) {
            addCriterion("sync_source between", value1, value2, "syncSource");
            return (Criteria) this;
        }

        public Criteria andSyncSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("sync_source not between", value1, value2, "syncSource");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeIsNull() {
            addCriterion("is_full_time is null");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeIsNotNull() {
            addCriterion("is_full_time is not null");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeEqualTo(Boolean value) {
            addCriterion("is_full_time =", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeNotEqualTo(Boolean value) {
            addCriterion("is_full_time <>", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeGreaterThan(Boolean value) {
            addCriterion("is_full_time >", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_full_time >=", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeLessThan(Boolean value) {
            addCriterion("is_full_time <", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_full_time <=", value, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeIn(List<Boolean> values) {
            addCriterion("is_full_time in", values, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeNotIn(List<Boolean> values) {
            addCriterion("is_full_time not in", values, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_full_time between", value1, value2, "isFullTime");
            return (Criteria) this;
        }

        public Criteria andIsFullTimeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_full_time not between", value1, value2, "isFullTime");
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

        public Criteria andEnrolYearIsNull() {
            addCriterion("enrol_year is null");
            return (Criteria) this;
        }

        public Criteria andEnrolYearIsNotNull() {
            addCriterion("enrol_year is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolYearEqualTo(String value) {
            addCriterion("enrol_year =", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearNotEqualTo(String value) {
            addCriterion("enrol_year <>", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearGreaterThan(String value) {
            addCriterion("enrol_year >", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearGreaterThanOrEqualTo(String value) {
            addCriterion("enrol_year >=", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearLessThan(String value) {
            addCriterion("enrol_year <", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearLessThanOrEqualTo(String value) {
            addCriterion("enrol_year <=", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearLike(String value) {
            addCriterion("enrol_year like", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearNotLike(String value) {
            addCriterion("enrol_year not like", value, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearIn(List<String> values) {
            addCriterion("enrol_year in", values, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearNotIn(List<String> values) {
            addCriterion("enrol_year not in", values, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearBetween(String value1, String value2) {
            addCriterion("enrol_year between", value1, value2, "enrolYear");
            return (Criteria) this;
        }

        public Criteria andEnrolYearNotBetween(String value1, String value2) {
            addCriterion("enrol_year not between", value1, value2, "enrolYear");
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

        public Criteria andEduWayIsNull() {
            addCriterion("edu_way is null");
            return (Criteria) this;
        }

        public Criteria andEduWayIsNotNull() {
            addCriterion("edu_way is not null");
            return (Criteria) this;
        }

        public Criteria andEduWayEqualTo(String value) {
            addCriterion("edu_way =", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayNotEqualTo(String value) {
            addCriterion("edu_way <>", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayGreaterThan(String value) {
            addCriterion("edu_way >", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayGreaterThanOrEqualTo(String value) {
            addCriterion("edu_way >=", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayLessThan(String value) {
            addCriterion("edu_way <", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayLessThanOrEqualTo(String value) {
            addCriterion("edu_way <=", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayLike(String value) {
            addCriterion("edu_way like", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayNotLike(String value) {
            addCriterion("edu_way not like", value, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayIn(List<String> values) {
            addCriterion("edu_way in", values, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayNotIn(List<String> values) {
            addCriterion("edu_way not in", values, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayBetween(String value1, String value2) {
            addCriterion("edu_way between", value1, value2, "eduWay");
            return (Criteria) this;
        }

        public Criteria andEduWayNotBetween(String value1, String value2) {
            addCriterion("edu_way not between", value1, value2, "eduWay");
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

        public Criteria andEduLevelIsNull() {
            addCriterion("edu_level is null");
            return (Criteria) this;
        }

        public Criteria andEduLevelIsNotNull() {
            addCriterion("edu_level is not null");
            return (Criteria) this;
        }

        public Criteria andEduLevelEqualTo(String value) {
            addCriterion("edu_level =", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotEqualTo(String value) {
            addCriterion("edu_level <>", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThan(String value) {
            addCriterion("edu_level >", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThanOrEqualTo(String value) {
            addCriterion("edu_level >=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThan(String value) {
            addCriterion("edu_level <", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThanOrEqualTo(String value) {
            addCriterion("edu_level <=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLike(String value) {
            addCriterion("edu_level like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotLike(String value) {
            addCriterion("edu_level not like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelIn(List<String> values) {
            addCriterion("edu_level in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotIn(List<String> values) {
            addCriterion("edu_level not in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelBetween(String value1, String value2) {
            addCriterion("edu_level between", value1, value2, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotBetween(String value1, String value2) {
            addCriterion("edu_level not between", value1, value2, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andGradeIsNull() {
            addCriterion("grade is null");
            return (Criteria) this;
        }

        public Criteria andGradeIsNotNull() {
            addCriterion("grade is not null");
            return (Criteria) this;
        }

        public Criteria andGradeEqualTo(String value) {
            addCriterion("grade =", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotEqualTo(String value) {
            addCriterion("grade <>", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThan(String value) {
            addCriterion("grade >", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThanOrEqualTo(String value) {
            addCriterion("grade >=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThan(String value) {
            addCriterion("grade <", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThanOrEqualTo(String value) {
            addCriterion("grade <=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLike(String value) {
            addCriterion("grade like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotLike(String value) {
            addCriterion("grade not like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeIn(List<String> values) {
            addCriterion("grade in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotIn(List<String> values) {
            addCriterion("grade not in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeBetween(String value1, String value2) {
            addCriterion("grade between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotBetween(String value1, String value2) {
            addCriterion("grade not between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andEduTypeIsNull() {
            addCriterion("edu_type is null");
            return (Criteria) this;
        }

        public Criteria andEduTypeIsNotNull() {
            addCriterion("edu_type is not null");
            return (Criteria) this;
        }

        public Criteria andEduTypeEqualTo(String value) {
            addCriterion("edu_type =", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeNotEqualTo(String value) {
            addCriterion("edu_type <>", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeGreaterThan(String value) {
            addCriterion("edu_type >", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeGreaterThanOrEqualTo(String value) {
            addCriterion("edu_type >=", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeLessThan(String value) {
            addCriterion("edu_type <", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeLessThanOrEqualTo(String value) {
            addCriterion("edu_type <=", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeLike(String value) {
            addCriterion("edu_type like", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeNotLike(String value) {
            addCriterion("edu_type not like", value, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeIn(List<String> values) {
            addCriterion("edu_type in", values, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeNotIn(List<String> values) {
            addCriterion("edu_type not in", values, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeBetween(String value1, String value2) {
            addCriterion("edu_type between", value1, value2, "eduType");
            return (Criteria) this;
        }

        public Criteria andEduTypeNotBetween(String value1, String value2) {
            addCriterion("edu_type not between", value1, value2, "eduType");
            return (Criteria) this;
        }

        public Criteria andXjStatusIsNull() {
            addCriterion("xj_status is null");
            return (Criteria) this;
        }

        public Criteria andXjStatusIsNotNull() {
            addCriterion("xj_status is not null");
            return (Criteria) this;
        }

        public Criteria andXjStatusEqualTo(String value) {
            addCriterion("xj_status =", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusNotEqualTo(String value) {
            addCriterion("xj_status <>", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusGreaterThan(String value) {
            addCriterion("xj_status >", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusGreaterThanOrEqualTo(String value) {
            addCriterion("xj_status >=", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusLessThan(String value) {
            addCriterion("xj_status <", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusLessThanOrEqualTo(String value) {
            addCriterion("xj_status <=", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusLike(String value) {
            addCriterion("xj_status like", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusNotLike(String value) {
            addCriterion("xj_status not like", value, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusIn(List<String> values) {
            addCriterion("xj_status in", values, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusNotIn(List<String> values) {
            addCriterion("xj_status not in", values, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusBetween(String value1, String value2) {
            addCriterion("xj_status between", value1, value2, "xjStatus");
            return (Criteria) this;
        }

        public Criteria andXjStatusNotBetween(String value1, String value2) {
            addCriterion("xj_status not between", value1, value2, "xjStatus");
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