package domain.member;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberAbroadExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberAbroadExample() {
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

        public Criteria andAbroadTimeIsNull() {
            addCriterion("abroad_time is null");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeIsNotNull() {
            addCriterion("abroad_time is not null");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeEqualTo(Date value) {
            addCriterionForJDBCDate("abroad_time =", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("abroad_time <>", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("abroad_time >", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abroad_time >=", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeLessThan(Date value) {
            addCriterionForJDBCDate("abroad_time <", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abroad_time <=", value, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeIn(List<Date> values) {
            addCriterionForJDBCDate("abroad_time in", values, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("abroad_time not in", values, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abroad_time between", value1, value2, "abroadTime");
            return (Criteria) this;
        }

        public Criteria andAbroadTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abroad_time not between", value1, value2, "abroadTime");
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

        public Criteria andExpectReturnTimeIsNull() {
            addCriterion("expect_return_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeIsNotNull() {
            addCriterion("expect_return_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeEqualTo(Date value) {
            addCriterionForJDBCDate("expect_return_time =", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_return_time <>", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_return_time >", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_return_time >=", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeLessThan(Date value) {
            addCriterionForJDBCDate("expect_return_time <", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_return_time <=", value, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeIn(List<Date> values) {
            addCriterionForJDBCDate("expect_return_time in", values, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_return_time not in", values, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_return_time between", value1, value2, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andExpectReturnTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_return_time not between", value1, value2, "expectReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeIsNull() {
            addCriterion("actual_return_time is null");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeIsNotNull() {
            addCriterion("actual_return_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_time =", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_time <>", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_return_time >", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_time >=", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeLessThan(Date value) {
            addCriterionForJDBCDate("actual_return_time <", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_time <=", value, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeIn(List<Date> values) {
            addCriterionForJDBCDate("actual_return_time in", values, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_return_time not in", values, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_return_time between", value1, value2, "actualReturnTime");
            return (Criteria) this;
        }

        public Criteria andActualReturnTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_return_time not between", value1, value2, "actualReturnTime");
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
                andIdIsNull();
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