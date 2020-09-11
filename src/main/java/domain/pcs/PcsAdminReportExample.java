package domain.pcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcsAdminReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsAdminReportExample() {
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
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