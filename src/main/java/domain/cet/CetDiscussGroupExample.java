package domain.cet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetDiscussGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetDiscussGroupExample() {
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

        public Criteria andDiscussIdIsNull() {
            addCriterion("discuss_id is null");
            return (Criteria) this;
        }

        public Criteria andDiscussIdIsNotNull() {
            addCriterion("discuss_id is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussIdEqualTo(Integer value) {
            addCriterion("discuss_id =", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotEqualTo(Integer value) {
            addCriterion("discuss_id <>", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdGreaterThan(Integer value) {
            addCriterion("discuss_id >", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("discuss_id >=", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdLessThan(Integer value) {
            addCriterion("discuss_id <", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdLessThanOrEqualTo(Integer value) {
            addCriterion("discuss_id <=", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdIn(List<Integer> values) {
            addCriterion("discuss_id in", values, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotIn(List<Integer> values) {
            addCriterion("discuss_id not in", values, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdBetween(Integer value1, Integer value2) {
            addCriterion("discuss_id between", value1, value2, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotBetween(Integer value1, Integer value2) {
            addCriterion("discuss_id not between", value1, value2, "discussId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdIsNull() {
            addCriterion("hold_user_id is null");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdIsNotNull() {
            addCriterion("hold_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdEqualTo(Integer value) {
            addCriterion("hold_user_id =", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdNotEqualTo(Integer value) {
            addCriterion("hold_user_id <>", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdGreaterThan(Integer value) {
            addCriterion("hold_user_id >", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hold_user_id >=", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdLessThan(Integer value) {
            addCriterion("hold_user_id <", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("hold_user_id <=", value, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdIn(List<Integer> values) {
            addCriterion("hold_user_id in", values, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdNotIn(List<Integer> values) {
            addCriterion("hold_user_id not in", values, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdBetween(Integer value1, Integer value2) {
            addCriterion("hold_user_id between", value1, value2, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andHoldUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hold_user_id not between", value1, value2, "holdUserId");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyIsNull() {
            addCriterion("subject_can_modify is null");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyIsNotNull() {
            addCriterion("subject_can_modify is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyEqualTo(Boolean value) {
            addCriterion("subject_can_modify =", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyNotEqualTo(Boolean value) {
            addCriterion("subject_can_modify <>", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyGreaterThan(Boolean value) {
            addCriterion("subject_can_modify >", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("subject_can_modify >=", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyLessThan(Boolean value) {
            addCriterion("subject_can_modify <", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyLessThanOrEqualTo(Boolean value) {
            addCriterion("subject_can_modify <=", value, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyIn(List<Boolean> values) {
            addCriterion("subject_can_modify in", values, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyNotIn(List<Boolean> values) {
            addCriterion("subject_can_modify not in", values, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyBetween(Boolean value1, Boolean value2) {
            addCriterion("subject_can_modify between", value1, value2, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andSubjectCanModifyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("subject_can_modify not between", value1, value2, "subjectCanModify");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeIsNull() {
            addCriterion("discuss_time is null");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeIsNotNull() {
            addCriterion("discuss_time is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeEqualTo(Date value) {
            addCriterion("discuss_time =", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeNotEqualTo(Date value) {
            addCriterion("discuss_time <>", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeGreaterThan(Date value) {
            addCriterion("discuss_time >", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("discuss_time >=", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeLessThan(Date value) {
            addCriterion("discuss_time <", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeLessThanOrEqualTo(Date value) {
            addCriterion("discuss_time <=", value, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeIn(List<Date> values) {
            addCriterion("discuss_time in", values, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeNotIn(List<Date> values) {
            addCriterion("discuss_time not in", values, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeBetween(Date value1, Date value2) {
            addCriterion("discuss_time between", value1, value2, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussTimeNotBetween(Date value1, Date value2) {
            addCriterion("discuss_time not between", value1, value2, "discussTime");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressIsNull() {
            addCriterion("discuss_address is null");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressIsNotNull() {
            addCriterion("discuss_address is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressEqualTo(String value) {
            addCriterion("discuss_address =", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressNotEqualTo(String value) {
            addCriterion("discuss_address <>", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressGreaterThan(String value) {
            addCriterion("discuss_address >", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressGreaterThanOrEqualTo(String value) {
            addCriterion("discuss_address >=", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressLessThan(String value) {
            addCriterion("discuss_address <", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressLessThanOrEqualTo(String value) {
            addCriterion("discuss_address <=", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressLike(String value) {
            addCriterion("discuss_address like", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressNotLike(String value) {
            addCriterion("discuss_address not like", value, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressIn(List<String> values) {
            addCriterion("discuss_address in", values, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressNotIn(List<String> values) {
            addCriterion("discuss_address not in", values, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressBetween(String value1, String value2) {
            addCriterion("discuss_address between", value1, value2, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andDiscussAddressNotBetween(String value1, String value2) {
            addCriterion("discuss_address not between", value1, value2, "discussAddress");
            return (Criteria) this;
        }

        public Criteria andUntiIdIsNull() {
            addCriterion("unti_id is null");
            return (Criteria) this;
        }

        public Criteria andUntiIdIsNotNull() {
            addCriterion("unti_id is not null");
            return (Criteria) this;
        }

        public Criteria andUntiIdEqualTo(Integer value) {
            addCriterion("unti_id =", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdNotEqualTo(Integer value) {
            addCriterion("unti_id <>", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdGreaterThan(Integer value) {
            addCriterion("unti_id >", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unti_id >=", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdLessThan(Integer value) {
            addCriterion("unti_id <", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdLessThanOrEqualTo(Integer value) {
            addCriterion("unti_id <=", value, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdIn(List<Integer> values) {
            addCriterion("unti_id in", values, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdNotIn(List<Integer> values) {
            addCriterion("unti_id not in", values, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdBetween(Integer value1, Integer value2) {
            addCriterion("unti_id between", value1, value2, "untiId");
            return (Criteria) this;
        }

        public Criteria andUntiIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unti_id not between", value1, value2, "untiId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(Integer value) {
            addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(Integer value) {
            addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(Integer value) {
            addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(Integer value) {
            addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(List<Integer> values) {
            addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(List<Integer> values) {
            addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id not between", value1, value2, "adminUserId");
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