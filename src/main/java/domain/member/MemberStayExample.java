package domain.member;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberStayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberStayExample() {
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

        public Criteria andToBranchIdIsNull() {
            addCriterion("to_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andToBranchIdIsNotNull() {
            addCriterion("to_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andToBranchIdEqualTo(Integer value) {
            addCriterion("to_branch_id =", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdNotEqualTo(Integer value) {
            addCriterion("to_branch_id <>", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdGreaterThan(Integer value) {
            addCriterion("to_branch_id >", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("to_branch_id >=", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdLessThan(Integer value) {
            addCriterion("to_branch_id <", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("to_branch_id <=", value, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdIn(List<Integer> values) {
            addCriterion("to_branch_id in", values, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdNotIn(List<Integer> values) {
            addCriterion("to_branch_id not in", values, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("to_branch_id between", value1, value2, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andToBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("to_branch_id not between", value1, value2, "toBranchId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdIsNull() {
            addCriterion("org_branch_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdIsNotNull() {
            addCriterion("org_branch_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdEqualTo(Integer value) {
            addCriterion("org_branch_admin_id =", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdNotEqualTo(Integer value) {
            addCriterion("org_branch_admin_id <>", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdGreaterThan(Integer value) {
            addCriterion("org_branch_admin_id >", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_branch_admin_id >=", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdLessThan(Integer value) {
            addCriterion("org_branch_admin_id <", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_branch_admin_id <=", value, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdIn(List<Integer> values) {
            addCriterion("org_branch_admin_id in", values, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdNotIn(List<Integer> values) {
            addCriterion("org_branch_admin_id not in", values, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdBetween(Integer value1, Integer value2) {
            addCriterion("org_branch_admin_id between", value1, value2, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_branch_admin_id not between", value1, value2, "orgBranchAdminId");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneIsNull() {
            addCriterion("org_branch_admin_phone is null");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneIsNotNull() {
            addCriterion("org_branch_admin_phone is not null");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneEqualTo(String value) {
            addCriterion("org_branch_admin_phone =", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneNotEqualTo(String value) {
            addCriterion("org_branch_admin_phone <>", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneGreaterThan(String value) {
            addCriterion("org_branch_admin_phone >", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("org_branch_admin_phone >=", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneLessThan(String value) {
            addCriterion("org_branch_admin_phone <", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneLessThanOrEqualTo(String value) {
            addCriterion("org_branch_admin_phone <=", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneLike(String value) {
            addCriterion("org_branch_admin_phone like", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneNotLike(String value) {
            addCriterion("org_branch_admin_phone not like", value, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneIn(List<String> values) {
            addCriterion("org_branch_admin_phone in", values, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneNotIn(List<String> values) {
            addCriterion("org_branch_admin_phone not in", values, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneBetween(String value1, String value2) {
            addCriterion("org_branch_admin_phone between", value1, value2, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andOrgBranchAdminPhoneNotBetween(String value1, String value2) {
            addCriterion("org_branch_admin_phone not between", value1, value2, "orgBranchAdminPhone");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andStayReasonIsNull() {
            addCriterion("stay_reason is null");
            return (Criteria) this;
        }

        public Criteria andStayReasonIsNotNull() {
            addCriterion("stay_reason is not null");
            return (Criteria) this;
        }

        public Criteria andStayReasonEqualTo(String value) {
            addCriterion("stay_reason =", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonNotEqualTo(String value) {
            addCriterion("stay_reason <>", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonGreaterThan(String value) {
            addCriterion("stay_reason >", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonGreaterThanOrEqualTo(String value) {
            addCriterion("stay_reason >=", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonLessThan(String value) {
            addCriterion("stay_reason <", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonLessThanOrEqualTo(String value) {
            addCriterion("stay_reason <=", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonLike(String value) {
            addCriterion("stay_reason like", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonNotLike(String value) {
            addCriterion("stay_reason not like", value, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonIn(List<String> values) {
            addCriterion("stay_reason in", values, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonNotIn(List<String> values) {
            addCriterion("stay_reason not in", values, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonBetween(String value1, String value2) {
            addCriterion("stay_reason between", value1, value2, "stayReason");
            return (Criteria) this;
        }

        public Criteria andStayReasonNotBetween(String value1, String value2) {
            addCriterion("stay_reason not between", value1, value2, "stayReason");
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

        public Criteria andWeixinIsNull() {
            addCriterion("weixin is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("weixin is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(String value) {
            addCriterion("weixin =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(String value) {
            addCriterion("weixin <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(String value) {
            addCriterion("weixin >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(String value) {
            addCriterion("weixin >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(String value) {
            addCriterion("weixin <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(String value) {
            addCriterion("weixin <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLike(String value) {
            addCriterion("weixin like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotLike(String value) {
            addCriterion("weixin not like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<String> values) {
            addCriterion("weixin in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<String> values) {
            addCriterion("weixin not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(String value1, String value2) {
            addCriterion("weixin between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(String value1, String value2) {
            addCriterion("weixin not between", value1, value2, "weixin");
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

        public Criteria andQqIsNull() {
            addCriterion("qq is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            addCriterion("qq is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(String value) {
            addCriterion("qq =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(String value) {
            addCriterion("qq <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(String value) {
            addCriterion("qq >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(String value) {
            addCriterion("qq >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(String value) {
            addCriterion("qq <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(String value) {
            addCriterion("qq <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLike(String value) {
            addCriterion("qq like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotLike(String value) {
            addCriterion("qq not like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(List<String> values) {
            addCriterion("qq in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(List<String> values) {
            addCriterion("qq not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(String value1, String value2) {
            addCriterion("qq between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(String value1, String value2) {
            addCriterion("qq not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andInAddressIsNull() {
            addCriterion("in_address is null");
            return (Criteria) this;
        }

        public Criteria andInAddressIsNotNull() {
            addCriterion("in_address is not null");
            return (Criteria) this;
        }

        public Criteria andInAddressEqualTo(String value) {
            addCriterion("in_address =", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressNotEqualTo(String value) {
            addCriterion("in_address <>", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressGreaterThan(String value) {
            addCriterion("in_address >", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressGreaterThanOrEqualTo(String value) {
            addCriterion("in_address >=", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressLessThan(String value) {
            addCriterion("in_address <", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressLessThanOrEqualTo(String value) {
            addCriterion("in_address <=", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressLike(String value) {
            addCriterion("in_address like", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressNotLike(String value) {
            addCriterion("in_address not like", value, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressIn(List<String> values) {
            addCriterion("in_address in", values, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressNotIn(List<String> values) {
            addCriterion("in_address not in", values, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressBetween(String value1, String value2) {
            addCriterion("in_address between", value1, value2, "inAddress");
            return (Criteria) this;
        }

        public Criteria andInAddressNotBetween(String value1, String value2) {
            addCriterion("in_address not between", value1, value2, "inAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressIsNull() {
            addCriterion("out_address is null");
            return (Criteria) this;
        }

        public Criteria andOutAddressIsNotNull() {
            addCriterion("out_address is not null");
            return (Criteria) this;
        }

        public Criteria andOutAddressEqualTo(String value) {
            addCriterion("out_address =", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressNotEqualTo(String value) {
            addCriterion("out_address <>", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressGreaterThan(String value) {
            addCriterion("out_address >", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressGreaterThanOrEqualTo(String value) {
            addCriterion("out_address >=", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressLessThan(String value) {
            addCriterion("out_address <", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressLessThanOrEqualTo(String value) {
            addCriterion("out_address <=", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressLike(String value) {
            addCriterion("out_address like", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressNotLike(String value) {
            addCriterion("out_address not like", value, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressIn(List<String> values) {
            addCriterion("out_address in", values, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressNotIn(List<String> values) {
            addCriterion("out_address not in", values, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressBetween(String value1, String value2) {
            addCriterion("out_address between", value1, value2, "outAddress");
            return (Criteria) this;
        }

        public Criteria andOutAddressNotBetween(String value1, String value2) {
            addCriterion("out_address not between", value1, value2, "outAddress");
            return (Criteria) this;
        }

        public Criteria andName1IsNull() {
            addCriterion("name1 is null");
            return (Criteria) this;
        }

        public Criteria andName1IsNotNull() {
            addCriterion("name1 is not null");
            return (Criteria) this;
        }

        public Criteria andName1EqualTo(String value) {
            addCriterion("name1 =", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1NotEqualTo(String value) {
            addCriterion("name1 <>", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1GreaterThan(String value) {
            addCriterion("name1 >", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1GreaterThanOrEqualTo(String value) {
            addCriterion("name1 >=", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1LessThan(String value) {
            addCriterion("name1 <", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1LessThanOrEqualTo(String value) {
            addCriterion("name1 <=", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1Like(String value) {
            addCriterion("name1 like", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1NotLike(String value) {
            addCriterion("name1 not like", value, "name1");
            return (Criteria) this;
        }

        public Criteria andName1In(List<String> values) {
            addCriterion("name1 in", values, "name1");
            return (Criteria) this;
        }

        public Criteria andName1NotIn(List<String> values) {
            addCriterion("name1 not in", values, "name1");
            return (Criteria) this;
        }

        public Criteria andName1Between(String value1, String value2) {
            addCriterion("name1 between", value1, value2, "name1");
            return (Criteria) this;
        }

        public Criteria andName1NotBetween(String value1, String value2) {
            addCriterion("name1 not between", value1, value2, "name1");
            return (Criteria) this;
        }

        public Criteria andRelate1IsNull() {
            addCriterion("relate1 is null");
            return (Criteria) this;
        }

        public Criteria andRelate1IsNotNull() {
            addCriterion("relate1 is not null");
            return (Criteria) this;
        }

        public Criteria andRelate1EqualTo(String value) {
            addCriterion("relate1 =", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1NotEqualTo(String value) {
            addCriterion("relate1 <>", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1GreaterThan(String value) {
            addCriterion("relate1 >", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1GreaterThanOrEqualTo(String value) {
            addCriterion("relate1 >=", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1LessThan(String value) {
            addCriterion("relate1 <", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1LessThanOrEqualTo(String value) {
            addCriterion("relate1 <=", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1Like(String value) {
            addCriterion("relate1 like", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1NotLike(String value) {
            addCriterion("relate1 not like", value, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1In(List<String> values) {
            addCriterion("relate1 in", values, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1NotIn(List<String> values) {
            addCriterion("relate1 not in", values, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1Between(String value1, String value2) {
            addCriterion("relate1 between", value1, value2, "relate1");
            return (Criteria) this;
        }

        public Criteria andRelate1NotBetween(String value1, String value2) {
            addCriterion("relate1 not between", value1, value2, "relate1");
            return (Criteria) this;
        }

        public Criteria andUnit1IsNull() {
            addCriterion("unit1 is null");
            return (Criteria) this;
        }

        public Criteria andUnit1IsNotNull() {
            addCriterion("unit1 is not null");
            return (Criteria) this;
        }

        public Criteria andUnit1EqualTo(String value) {
            addCriterion("unit1 =", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1NotEqualTo(String value) {
            addCriterion("unit1 <>", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1GreaterThan(String value) {
            addCriterion("unit1 >", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1GreaterThanOrEqualTo(String value) {
            addCriterion("unit1 >=", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1LessThan(String value) {
            addCriterion("unit1 <", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1LessThanOrEqualTo(String value) {
            addCriterion("unit1 <=", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1Like(String value) {
            addCriterion("unit1 like", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1NotLike(String value) {
            addCriterion("unit1 not like", value, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1In(List<String> values) {
            addCriterion("unit1 in", values, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1NotIn(List<String> values) {
            addCriterion("unit1 not in", values, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1Between(String value1, String value2) {
            addCriterion("unit1 between", value1, value2, "unit1");
            return (Criteria) this;
        }

        public Criteria andUnit1NotBetween(String value1, String value2) {
            addCriterion("unit1 not between", value1, value2, "unit1");
            return (Criteria) this;
        }

        public Criteria andPost1IsNull() {
            addCriterion("post1 is null");
            return (Criteria) this;
        }

        public Criteria andPost1IsNotNull() {
            addCriterion("post1 is not null");
            return (Criteria) this;
        }

        public Criteria andPost1EqualTo(String value) {
            addCriterion("post1 =", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1NotEqualTo(String value) {
            addCriterion("post1 <>", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1GreaterThan(String value) {
            addCriterion("post1 >", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1GreaterThanOrEqualTo(String value) {
            addCriterion("post1 >=", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1LessThan(String value) {
            addCriterion("post1 <", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1LessThanOrEqualTo(String value) {
            addCriterion("post1 <=", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1Like(String value) {
            addCriterion("post1 like", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1NotLike(String value) {
            addCriterion("post1 not like", value, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1In(List<String> values) {
            addCriterion("post1 in", values, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1NotIn(List<String> values) {
            addCriterion("post1 not in", values, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1Between(String value1, String value2) {
            addCriterion("post1 between", value1, value2, "post1");
            return (Criteria) this;
        }

        public Criteria andPost1NotBetween(String value1, String value2) {
            addCriterion("post1 not between", value1, value2, "post1");
            return (Criteria) this;
        }

        public Criteria andPhone1IsNull() {
            addCriterion("phone1 is null");
            return (Criteria) this;
        }

        public Criteria andPhone1IsNotNull() {
            addCriterion("phone1 is not null");
            return (Criteria) this;
        }

        public Criteria andPhone1EqualTo(String value) {
            addCriterion("phone1 =", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1NotEqualTo(String value) {
            addCriterion("phone1 <>", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1GreaterThan(String value) {
            addCriterion("phone1 >", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1GreaterThanOrEqualTo(String value) {
            addCriterion("phone1 >=", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1LessThan(String value) {
            addCriterion("phone1 <", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1LessThanOrEqualTo(String value) {
            addCriterion("phone1 <=", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1Like(String value) {
            addCriterion("phone1 like", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1NotLike(String value) {
            addCriterion("phone1 not like", value, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1In(List<String> values) {
            addCriterion("phone1 in", values, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1NotIn(List<String> values) {
            addCriterion("phone1 not in", values, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1Between(String value1, String value2) {
            addCriterion("phone1 between", value1, value2, "phone1");
            return (Criteria) this;
        }

        public Criteria andPhone1NotBetween(String value1, String value2) {
            addCriterion("phone1 not between", value1, value2, "phone1");
            return (Criteria) this;
        }

        public Criteria andMobile1IsNull() {
            addCriterion("mobile1 is null");
            return (Criteria) this;
        }

        public Criteria andMobile1IsNotNull() {
            addCriterion("mobile1 is not null");
            return (Criteria) this;
        }

        public Criteria andMobile1EqualTo(String value) {
            addCriterion("mobile1 =", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1NotEqualTo(String value) {
            addCriterion("mobile1 <>", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1GreaterThan(String value) {
            addCriterion("mobile1 >", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1GreaterThanOrEqualTo(String value) {
            addCriterion("mobile1 >=", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1LessThan(String value) {
            addCriterion("mobile1 <", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1LessThanOrEqualTo(String value) {
            addCriterion("mobile1 <=", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1Like(String value) {
            addCriterion("mobile1 like", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1NotLike(String value) {
            addCriterion("mobile1 not like", value, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1In(List<String> values) {
            addCriterion("mobile1 in", values, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1NotIn(List<String> values) {
            addCriterion("mobile1 not in", values, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1Between(String value1, String value2) {
            addCriterion("mobile1 between", value1, value2, "mobile1");
            return (Criteria) this;
        }

        public Criteria andMobile1NotBetween(String value1, String value2) {
            addCriterion("mobile1 not between", value1, value2, "mobile1");
            return (Criteria) this;
        }

        public Criteria andEmail1IsNull() {
            addCriterion("email1 is null");
            return (Criteria) this;
        }

        public Criteria andEmail1IsNotNull() {
            addCriterion("email1 is not null");
            return (Criteria) this;
        }

        public Criteria andEmail1EqualTo(String value) {
            addCriterion("email1 =", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1NotEqualTo(String value) {
            addCriterion("email1 <>", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1GreaterThan(String value) {
            addCriterion("email1 >", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1GreaterThanOrEqualTo(String value) {
            addCriterion("email1 >=", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1LessThan(String value) {
            addCriterion("email1 <", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1LessThanOrEqualTo(String value) {
            addCriterion("email1 <=", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1Like(String value) {
            addCriterion("email1 like", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1NotLike(String value) {
            addCriterion("email1 not like", value, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1In(List<String> values) {
            addCriterion("email1 in", values, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1NotIn(List<String> values) {
            addCriterion("email1 not in", values, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1Between(String value1, String value2) {
            addCriterion("email1 between", value1, value2, "email1");
            return (Criteria) this;
        }

        public Criteria andEmail1NotBetween(String value1, String value2) {
            addCriterion("email1 not between", value1, value2, "email1");
            return (Criteria) this;
        }

        public Criteria andName2IsNull() {
            addCriterion("name2 is null");
            return (Criteria) this;
        }

        public Criteria andName2IsNotNull() {
            addCriterion("name2 is not null");
            return (Criteria) this;
        }

        public Criteria andName2EqualTo(String value) {
            addCriterion("name2 =", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2NotEqualTo(String value) {
            addCriterion("name2 <>", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2GreaterThan(String value) {
            addCriterion("name2 >", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2GreaterThanOrEqualTo(String value) {
            addCriterion("name2 >=", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2LessThan(String value) {
            addCriterion("name2 <", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2LessThanOrEqualTo(String value) {
            addCriterion("name2 <=", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2Like(String value) {
            addCriterion("name2 like", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2NotLike(String value) {
            addCriterion("name2 not like", value, "name2");
            return (Criteria) this;
        }

        public Criteria andName2In(List<String> values) {
            addCriterion("name2 in", values, "name2");
            return (Criteria) this;
        }

        public Criteria andName2NotIn(List<String> values) {
            addCriterion("name2 not in", values, "name2");
            return (Criteria) this;
        }

        public Criteria andName2Between(String value1, String value2) {
            addCriterion("name2 between", value1, value2, "name2");
            return (Criteria) this;
        }

        public Criteria andName2NotBetween(String value1, String value2) {
            addCriterion("name2 not between", value1, value2, "name2");
            return (Criteria) this;
        }

        public Criteria andRelate2IsNull() {
            addCriterion("relate2 is null");
            return (Criteria) this;
        }

        public Criteria andRelate2IsNotNull() {
            addCriterion("relate2 is not null");
            return (Criteria) this;
        }

        public Criteria andRelate2EqualTo(String value) {
            addCriterion("relate2 =", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2NotEqualTo(String value) {
            addCriterion("relate2 <>", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2GreaterThan(String value) {
            addCriterion("relate2 >", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2GreaterThanOrEqualTo(String value) {
            addCriterion("relate2 >=", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2LessThan(String value) {
            addCriterion("relate2 <", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2LessThanOrEqualTo(String value) {
            addCriterion("relate2 <=", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2Like(String value) {
            addCriterion("relate2 like", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2NotLike(String value) {
            addCriterion("relate2 not like", value, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2In(List<String> values) {
            addCriterion("relate2 in", values, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2NotIn(List<String> values) {
            addCriterion("relate2 not in", values, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2Between(String value1, String value2) {
            addCriterion("relate2 between", value1, value2, "relate2");
            return (Criteria) this;
        }

        public Criteria andRelate2NotBetween(String value1, String value2) {
            addCriterion("relate2 not between", value1, value2, "relate2");
            return (Criteria) this;
        }

        public Criteria andUnit2IsNull() {
            addCriterion("unit2 is null");
            return (Criteria) this;
        }

        public Criteria andUnit2IsNotNull() {
            addCriterion("unit2 is not null");
            return (Criteria) this;
        }

        public Criteria andUnit2EqualTo(String value) {
            addCriterion("unit2 =", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2NotEqualTo(String value) {
            addCriterion("unit2 <>", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2GreaterThan(String value) {
            addCriterion("unit2 >", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2GreaterThanOrEqualTo(String value) {
            addCriterion("unit2 >=", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2LessThan(String value) {
            addCriterion("unit2 <", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2LessThanOrEqualTo(String value) {
            addCriterion("unit2 <=", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2Like(String value) {
            addCriterion("unit2 like", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2NotLike(String value) {
            addCriterion("unit2 not like", value, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2In(List<String> values) {
            addCriterion("unit2 in", values, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2NotIn(List<String> values) {
            addCriterion("unit2 not in", values, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2Between(String value1, String value2) {
            addCriterion("unit2 between", value1, value2, "unit2");
            return (Criteria) this;
        }

        public Criteria andUnit2NotBetween(String value1, String value2) {
            addCriterion("unit2 not between", value1, value2, "unit2");
            return (Criteria) this;
        }

        public Criteria andPost2IsNull() {
            addCriterion("post2 is null");
            return (Criteria) this;
        }

        public Criteria andPost2IsNotNull() {
            addCriterion("post2 is not null");
            return (Criteria) this;
        }

        public Criteria andPost2EqualTo(String value) {
            addCriterion("post2 =", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2NotEqualTo(String value) {
            addCriterion("post2 <>", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2GreaterThan(String value) {
            addCriterion("post2 >", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2GreaterThanOrEqualTo(String value) {
            addCriterion("post2 >=", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2LessThan(String value) {
            addCriterion("post2 <", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2LessThanOrEqualTo(String value) {
            addCriterion("post2 <=", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2Like(String value) {
            addCriterion("post2 like", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2NotLike(String value) {
            addCriterion("post2 not like", value, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2In(List<String> values) {
            addCriterion("post2 in", values, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2NotIn(List<String> values) {
            addCriterion("post2 not in", values, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2Between(String value1, String value2) {
            addCriterion("post2 between", value1, value2, "post2");
            return (Criteria) this;
        }

        public Criteria andPost2NotBetween(String value1, String value2) {
            addCriterion("post2 not between", value1, value2, "post2");
            return (Criteria) this;
        }

        public Criteria andPhone2IsNull() {
            addCriterion("phone2 is null");
            return (Criteria) this;
        }

        public Criteria andPhone2IsNotNull() {
            addCriterion("phone2 is not null");
            return (Criteria) this;
        }

        public Criteria andPhone2EqualTo(String value) {
            addCriterion("phone2 =", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2NotEqualTo(String value) {
            addCriterion("phone2 <>", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2GreaterThan(String value) {
            addCriterion("phone2 >", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2GreaterThanOrEqualTo(String value) {
            addCriterion("phone2 >=", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2LessThan(String value) {
            addCriterion("phone2 <", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2LessThanOrEqualTo(String value) {
            addCriterion("phone2 <=", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2Like(String value) {
            addCriterion("phone2 like", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2NotLike(String value) {
            addCriterion("phone2 not like", value, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2In(List<String> values) {
            addCriterion("phone2 in", values, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2NotIn(List<String> values) {
            addCriterion("phone2 not in", values, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2Between(String value1, String value2) {
            addCriterion("phone2 between", value1, value2, "phone2");
            return (Criteria) this;
        }

        public Criteria andPhone2NotBetween(String value1, String value2) {
            addCriterion("phone2 not between", value1, value2, "phone2");
            return (Criteria) this;
        }

        public Criteria andMobile2IsNull() {
            addCriterion("mobile2 is null");
            return (Criteria) this;
        }

        public Criteria andMobile2IsNotNull() {
            addCriterion("mobile2 is not null");
            return (Criteria) this;
        }

        public Criteria andMobile2EqualTo(String value) {
            addCriterion("mobile2 =", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2NotEqualTo(String value) {
            addCriterion("mobile2 <>", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2GreaterThan(String value) {
            addCriterion("mobile2 >", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2GreaterThanOrEqualTo(String value) {
            addCriterion("mobile2 >=", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2LessThan(String value) {
            addCriterion("mobile2 <", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2LessThanOrEqualTo(String value) {
            addCriterion("mobile2 <=", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2Like(String value) {
            addCriterion("mobile2 like", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2NotLike(String value) {
            addCriterion("mobile2 not like", value, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2In(List<String> values) {
            addCriterion("mobile2 in", values, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2NotIn(List<String> values) {
            addCriterion("mobile2 not in", values, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2Between(String value1, String value2) {
            addCriterion("mobile2 between", value1, value2, "mobile2");
            return (Criteria) this;
        }

        public Criteria andMobile2NotBetween(String value1, String value2) {
            addCriterion("mobile2 not between", value1, value2, "mobile2");
            return (Criteria) this;
        }

        public Criteria andEmail2IsNull() {
            addCriterion("email2 is null");
            return (Criteria) this;
        }

        public Criteria andEmail2IsNotNull() {
            addCriterion("email2 is not null");
            return (Criteria) this;
        }

        public Criteria andEmail2EqualTo(String value) {
            addCriterion("email2 =", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2NotEqualTo(String value) {
            addCriterion("email2 <>", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2GreaterThan(String value) {
            addCriterion("email2 >", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2GreaterThanOrEqualTo(String value) {
            addCriterion("email2 >=", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2LessThan(String value) {
            addCriterion("email2 <", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2LessThanOrEqualTo(String value) {
            addCriterion("email2 <=", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2Like(String value) {
            addCriterion("email2 like", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2NotLike(String value) {
            addCriterion("email2 not like", value, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2In(List<String> values) {
            addCriterion("email2 in", values, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2NotIn(List<String> values) {
            addCriterion("email2 not in", values, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2Between(String value1, String value2) {
            addCriterion("email2 between", value1, value2, "email2");
            return (Criteria) this;
        }

        public Criteria andEmail2NotBetween(String value1, String value2) {
            addCriterion("email2 not between", value1, value2, "email2");
            return (Criteria) this;
        }

        public Criteria andLetterIsNull() {
            addCriterion("letter is null");
            return (Criteria) this;
        }

        public Criteria andLetterIsNotNull() {
            addCriterion("letter is not null");
            return (Criteria) this;
        }

        public Criteria andLetterEqualTo(String value) {
            addCriterion("letter =", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterNotEqualTo(String value) {
            addCriterion("letter <>", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterGreaterThan(String value) {
            addCriterion("letter >", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterGreaterThanOrEqualTo(String value) {
            addCriterion("letter >=", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterLessThan(String value) {
            addCriterion("letter <", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterLessThanOrEqualTo(String value) {
            addCriterion("letter <=", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterLike(String value) {
            addCriterion("letter like", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterNotLike(String value) {
            addCriterion("letter not like", value, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterIn(List<String> values) {
            addCriterion("letter in", values, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterNotIn(List<String> values) {
            addCriterion("letter not in", values, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterBetween(String value1, String value2) {
            addCriterion("letter between", value1, value2, "letter");
            return (Criteria) this;
        }

        public Criteria andLetterNotBetween(String value1, String value2) {
            addCriterion("letter not between", value1, value2, "letter");
            return (Criteria) this;
        }

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andOverDateIsNull() {
            addCriterion("over_date is null");
            return (Criteria) this;
        }

        public Criteria andOverDateIsNotNull() {
            addCriterion("over_date is not null");
            return (Criteria) this;
        }

        public Criteria andOverDateEqualTo(Date value) {
            addCriterionForJDBCDate("over_date =", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("over_date <>", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateGreaterThan(Date value) {
            addCriterionForJDBCDate("over_date >", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("over_date >=", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateLessThan(Date value) {
            addCriterionForJDBCDate("over_date <", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("over_date <=", value, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateIn(List<Date> values) {
            addCriterionForJDBCDate("over_date in", values, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("over_date not in", values, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("over_date between", value1, value2, "overDate");
            return (Criteria) this;
        }

        public Criteria andOverDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("over_date not between", value1, value2, "overDate");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeIsNull() {
            addCriterion("abroad_type is null");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeIsNotNull() {
            addCriterion("abroad_type is not null");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeEqualTo(Byte value) {
            addCriterion("abroad_type =", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeNotEqualTo(Byte value) {
            addCriterion("abroad_type <>", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeGreaterThan(Byte value) {
            addCriterion("abroad_type >", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("abroad_type >=", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeLessThan(Byte value) {
            addCriterion("abroad_type <", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeLessThanOrEqualTo(Byte value) {
            addCriterion("abroad_type <=", value, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeIn(List<Byte> values) {
            addCriterion("abroad_type in", values, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeNotIn(List<Byte> values) {
            addCriterion("abroad_type not in", values, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeBetween(Byte value1, Byte value2) {
            addCriterion("abroad_type between", value1, value2, "abroadType");
            return (Criteria) this;
        }

        public Criteria andAbroadTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("abroad_type not between", value1, value2, "abroadType");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeIsNull() {
            addCriterion("save_start_time is null");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeIsNotNull() {
            addCriterion("save_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("save_start_time =", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("save_start_time <>", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("save_start_time >", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("save_start_time >=", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("save_start_time <", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("save_start_time <=", value, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("save_start_time in", values, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("save_start_time not in", values, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("save_start_time between", value1, value2, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("save_start_time not between", value1, value2, "saveStartTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeIsNull() {
            addCriterion("save_end_time is null");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeIsNotNull() {
            addCriterion("save_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("save_end_time =", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("save_end_time <>", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("save_end_time >", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("save_end_time >=", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("save_end_time <", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("save_end_time <=", value, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("save_end_time in", values, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("save_end_time not in", values, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("save_end_time between", value1, value2, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andSaveEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("save_end_time not between", value1, value2, "saveEndTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterionForJDBCDate("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_time not between", value1, value2, "payTime");
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

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andPrintCountIsNull() {
            addCriterion("print_count is null");
            return (Criteria) this;
        }

        public Criteria andPrintCountIsNotNull() {
            addCriterion("print_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrintCountEqualTo(Integer value) {
            addCriterion("print_count =", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotEqualTo(Integer value) {
            addCriterion("print_count <>", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountGreaterThan(Integer value) {
            addCriterion("print_count >", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("print_count >=", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountLessThan(Integer value) {
            addCriterion("print_count <", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountLessThanOrEqualTo(Integer value) {
            addCriterion("print_count <=", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountIn(List<Integer> values) {
            addCriterion("print_count in", values, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotIn(List<Integer> values) {
            addCriterion("print_count not in", values, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountBetween(Integer value1, Integer value2) {
            addCriterion("print_count between", value1, value2, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotBetween(Integer value1, Integer value2) {
            addCriterion("print_count not between", value1, value2, "printCount");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIsNull() {
            addCriterion("last_print_time is null");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIsNotNull() {
            addCriterion("last_print_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeEqualTo(Date value) {
            addCriterion("last_print_time =", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotEqualTo(Date value) {
            addCriterion("last_print_time <>", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeGreaterThan(Date value) {
            addCriterion("last_print_time >", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_print_time >=", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeLessThan(Date value) {
            addCriterion("last_print_time <", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_print_time <=", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIn(List<Date> values) {
            addCriterion("last_print_time in", values, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotIn(List<Date> values) {
            addCriterion("last_print_time not in", values, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeBetween(Date value1, Date value2) {
            addCriterion("last_print_time between", value1, value2, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_print_time not between", value1, value2, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIsNull() {
            addCriterion("last_print_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIsNotNull() {
            addCriterion("last_print_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdEqualTo(Integer value) {
            addCriterion("last_print_user_id =", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotEqualTo(Integer value) {
            addCriterion("last_print_user_id <>", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdGreaterThan(Integer value) {
            addCriterion("last_print_user_id >", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_print_user_id >=", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdLessThan(Integer value) {
            addCriterion("last_print_user_id <", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("last_print_user_id <=", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIn(List<Integer> values) {
            addCriterion("last_print_user_id in", values, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotIn(List<Integer> values) {
            addCriterion("last_print_user_id not in", values, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdBetween(Integer value1, Integer value2) {
            addCriterion("last_print_user_id between", value1, value2, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("last_print_user_id not between", value1, value2, "lastPrintUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL))
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