package domain.pcs;

import java.util.ArrayList;
import java.util.List;

public class PcsBranchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsBranchExample() {
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

        public Criteria andIsDirectBranchIsNull() {
            addCriterion("is_direct_branch is null");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchIsNotNull() {
            addCriterion("is_direct_branch is not null");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchEqualTo(Boolean value) {
            addCriterion("is_direct_branch =", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotEqualTo(Boolean value) {
            addCriterion("is_direct_branch <>", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchGreaterThan(Boolean value) {
            addCriterion("is_direct_branch >", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_direct_branch >=", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchLessThan(Boolean value) {
            addCriterion("is_direct_branch <", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchLessThanOrEqualTo(Boolean value) {
            addCriterion("is_direct_branch <=", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchIn(List<Boolean> values) {
            addCriterion("is_direct_branch in", values, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotIn(List<Boolean> values) {
            addCriterion("is_direct_branch not in", values, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchBetween(Boolean value1, Boolean value2) {
            addCriterion("is_direct_branch between", value1, value2, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_direct_branch not between", value1, value2, "isDirectBranch");
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

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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