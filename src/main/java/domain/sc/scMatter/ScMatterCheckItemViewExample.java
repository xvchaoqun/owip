package domain.sc.scMatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScMatterCheckItemViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScMatterCheckItemViewExample() {
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

        public Criteria andCheckIdIsNull() {
            addCriterion("check_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckIdIsNotNull() {
            addCriterion("check_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckIdEqualTo(Integer value) {
            addCriterion("check_id =", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdNotEqualTo(Integer value) {
            addCriterion("check_id <>", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdGreaterThan(Integer value) {
            addCriterion("check_id >", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_id >=", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdLessThan(Integer value) {
            addCriterion("check_id <", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_id <=", value, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdIn(List<Integer> values) {
            addCriterion("check_id in", values, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdNotIn(List<Integer> values) {
            addCriterion("check_id not in", values, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdBetween(Integer value1, Integer value2) {
            addCriterion("check_id between", value1, value2, "checkId");
            return (Criteria) this;
        }

        public Criteria andCheckIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_id not between", value1, value2, "checkId");
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

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(Integer value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(Integer value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(Integer value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(Integer value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<Integer> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<Integer> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIsNull() {
            addCriterion("record_ids is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIsNotNull() {
            addCriterion("record_ids is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdsEqualTo(String value) {
            addCriterion("record_ids =", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotEqualTo(String value) {
            addCriterion("record_ids <>", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsGreaterThan(String value) {
            addCriterion("record_ids >", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsGreaterThanOrEqualTo(String value) {
            addCriterion("record_ids >=", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLessThan(String value) {
            addCriterion("record_ids <", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLessThanOrEqualTo(String value) {
            addCriterion("record_ids <=", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLike(String value) {
            addCriterion("record_ids like", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotLike(String value) {
            addCriterion("record_ids not like", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIn(List<String> values) {
            addCriterion("record_ids in", values, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotIn(List<String> values) {
            addCriterion("record_ids not in", values, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsBetween(String value1, String value2) {
            addCriterion("record_ids between", value1, value2, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotBetween(String value1, String value2) {
            addCriterion("record_ids not between", value1, value2, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNull() {
            addCriterion("record_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNotNull() {
            addCriterion("record_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdEqualTo(Integer value) {
            addCriterion("record_user_id =", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotEqualTo(Integer value) {
            addCriterion("record_user_id <>", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThan(Integer value) {
            addCriterion("record_user_id >", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_user_id >=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThan(Integer value) {
            addCriterion("record_user_id <", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_user_id <=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIn(List<Integer> values) {
            addCriterion("record_user_id in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotIn(List<Integer> values) {
            addCriterion("record_user_id not in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id not between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andCompareDateIsNull() {
            addCriterion("compare_date is null");
            return (Criteria) this;
        }

        public Criteria andCompareDateIsNotNull() {
            addCriterion("compare_date is not null");
            return (Criteria) this;
        }

        public Criteria andCompareDateEqualTo(Date value) {
            addCriterionForJDBCDate("compare_date =", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("compare_date <>", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateGreaterThan(Date value) {
            addCriterionForJDBCDate("compare_date >", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("compare_date >=", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateLessThan(Date value) {
            addCriterionForJDBCDate("compare_date <", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("compare_date <=", value, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateIn(List<Date> values) {
            addCriterionForJDBCDate("compare_date in", values, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("compare_date not in", values, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("compare_date between", value1, value2, "compareDate");
            return (Criteria) this;
        }

        public Criteria andCompareDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("compare_date not between", value1, value2, "compareDate");
            return (Criteria) this;
        }

        public Criteria andResultTypeIsNull() {
            addCriterion("result_type is null");
            return (Criteria) this;
        }

        public Criteria andResultTypeIsNotNull() {
            addCriterion("result_type is not null");
            return (Criteria) this;
        }

        public Criteria andResultTypeEqualTo(String value) {
            addCriterion("result_type =", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeNotEqualTo(String value) {
            addCriterion("result_type <>", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeGreaterThan(String value) {
            addCriterion("result_type >", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeGreaterThanOrEqualTo(String value) {
            addCriterion("result_type >=", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeLessThan(String value) {
            addCriterion("result_type <", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeLessThanOrEqualTo(String value) {
            addCriterion("result_type <=", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeLike(String value) {
            addCriterion("result_type like", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeNotLike(String value) {
            addCriterion("result_type not like", value, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeIn(List<String> values) {
            addCriterion("result_type in", values, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeNotIn(List<String> values) {
            addCriterion("result_type not in", values, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeBetween(String value1, String value2) {
            addCriterion("result_type between", value1, value2, "resultType");
            return (Criteria) this;
        }

        public Criteria andResultTypeNotBetween(String value1, String value2) {
            addCriterion("result_type not between", value1, value2, "resultType");
            return (Criteria) this;
        }

        public Criteria andSelfFileIsNull() {
            addCriterion("self_file is null");
            return (Criteria) this;
        }

        public Criteria andSelfFileIsNotNull() {
            addCriterion("self_file is not null");
            return (Criteria) this;
        }

        public Criteria andSelfFileEqualTo(String value) {
            addCriterion("self_file =", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileNotEqualTo(String value) {
            addCriterion("self_file <>", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileGreaterThan(String value) {
            addCriterion("self_file >", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileGreaterThanOrEqualTo(String value) {
            addCriterion("self_file >=", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileLessThan(String value) {
            addCriterion("self_file <", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileLessThanOrEqualTo(String value) {
            addCriterion("self_file <=", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileLike(String value) {
            addCriterion("self_file like", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileNotLike(String value) {
            addCriterion("self_file not like", value, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileIn(List<String> values) {
            addCriterion("self_file in", values, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileNotIn(List<String> values) {
            addCriterion("self_file not in", values, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileBetween(String value1, String value2) {
            addCriterion("self_file between", value1, value2, "selfFile");
            return (Criteria) this;
        }

        public Criteria andSelfFileNotBetween(String value1, String value2) {
            addCriterion("self_file not between", value1, value2, "selfFile");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeIsNull() {
            addCriterion("confirm_type is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeIsNotNull() {
            addCriterion("confirm_type is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeEqualTo(Byte value) {
            addCriterion("confirm_type =", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeNotEqualTo(Byte value) {
            addCriterion("confirm_type <>", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeGreaterThan(Byte value) {
            addCriterion("confirm_type >", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("confirm_type >=", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeLessThan(Byte value) {
            addCriterion("confirm_type <", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeLessThanOrEqualTo(Byte value) {
            addCriterion("confirm_type <=", value, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeIn(List<Byte> values) {
            addCriterion("confirm_type in", values, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeNotIn(List<Byte> values) {
            addCriterion("confirm_type not in", values, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeBetween(Byte value1, Byte value2) {
            addCriterion("confirm_type between", value1, value2, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("confirm_type not between", value1, value2, "confirmType");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNull() {
            addCriterion("confirm_date is null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNotNull() {
            addCriterion("confirm_date is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date =", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date <>", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThan(Date value) {
            addCriterionForJDBCDate("confirm_date >", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date >=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThan(Date value) {
            addCriterionForJDBCDate("confirm_date <", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date <=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_date in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_date not in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_date between", value1, value2, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_date not between", value1, value2, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIsNull() {
            addCriterion("check_reason is null");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIsNotNull() {
            addCriterion("check_reason is not null");
            return (Criteria) this;
        }

        public Criteria andCheckReasonEqualTo(String value) {
            addCriterion("check_reason =", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotEqualTo(String value) {
            addCriterion("check_reason <>", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonGreaterThan(String value) {
            addCriterion("check_reason >", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonGreaterThanOrEqualTo(String value) {
            addCriterion("check_reason >=", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLessThan(String value) {
            addCriterion("check_reason <", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLessThanOrEqualTo(String value) {
            addCriterion("check_reason <=", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLike(String value) {
            addCriterion("check_reason like", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotLike(String value) {
            addCriterion("check_reason not like", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIn(List<String> values) {
            addCriterion("check_reason in", values, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotIn(List<String> values) {
            addCriterion("check_reason not in", values, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonBetween(String value1, String value2) {
            addCriterion("check_reason between", value1, value2, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotBetween(String value1, String value2) {
            addCriterion("check_reason not between", value1, value2, "checkReason");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNull() {
            addCriterion("handle_type is null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNotNull() {
            addCriterion("handle_type is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeEqualTo(String value) {
            addCriterion("handle_type =", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotEqualTo(String value) {
            addCriterion("handle_type <>", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThan(String value) {
            addCriterion("handle_type >", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("handle_type >=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThan(String value) {
            addCriterion("handle_type <", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThanOrEqualTo(String value) {
            addCriterion("handle_type <=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLike(String value) {
            addCriterion("handle_type like", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotLike(String value) {
            addCriterion("handle_type not like", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIn(List<String> values) {
            addCriterion("handle_type in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotIn(List<String> values) {
            addCriterion("handle_type not in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeBetween(String value1, String value2) {
            addCriterion("handle_type between", value1, value2, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotBetween(String value1, String value2) {
            addCriterion("handle_type not between", value1, value2, "handleType");
            return (Criteria) this;
        }

        public Criteria andCheckFileIsNull() {
            addCriterion("check_file is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileIsNotNull() {
            addCriterion("check_file is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileEqualTo(String value) {
            addCriterion("check_file =", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileNotEqualTo(String value) {
            addCriterion("check_file <>", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileGreaterThan(String value) {
            addCriterion("check_file >", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileGreaterThanOrEqualTo(String value) {
            addCriterion("check_file >=", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileLessThan(String value) {
            addCriterion("check_file <", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileLessThanOrEqualTo(String value) {
            addCriterion("check_file <=", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileLike(String value) {
            addCriterion("check_file like", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileNotLike(String value) {
            addCriterion("check_file not like", value, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileIn(List<String> values) {
            addCriterion("check_file in", values, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileNotIn(List<String> values) {
            addCriterion("check_file not in", values, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileBetween(String value1, String value2) {
            addCriterion("check_file between", value1, value2, "checkFile");
            return (Criteria) this;
        }

        public Criteria andCheckFileNotBetween(String value1, String value2) {
            addCriterion("check_file not between", value1, value2, "checkFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeIsNull() {
            addCriterion("ow_handle_type is null");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeIsNotNull() {
            addCriterion("ow_handle_type is not null");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeEqualTo(String value) {
            addCriterion("ow_handle_type =", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeNotEqualTo(String value) {
            addCriterion("ow_handle_type <>", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeGreaterThan(String value) {
            addCriterion("ow_handle_type >", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ow_handle_type >=", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeLessThan(String value) {
            addCriterion("ow_handle_type <", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeLessThanOrEqualTo(String value) {
            addCriterion("ow_handle_type <=", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeLike(String value) {
            addCriterion("ow_handle_type like", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeNotLike(String value) {
            addCriterion("ow_handle_type not like", value, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeIn(List<String> values) {
            addCriterion("ow_handle_type in", values, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeNotIn(List<String> values) {
            addCriterion("ow_handle_type not in", values, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeBetween(String value1, String value2) {
            addCriterion("ow_handle_type between", value1, value2, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleTypeNotBetween(String value1, String value2) {
            addCriterion("ow_handle_type not between", value1, value2, "owHandleType");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateIsNull() {
            addCriterion("ow_handle_date is null");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateIsNotNull() {
            addCriterion("ow_handle_date is not null");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateEqualTo(Date value) {
            addCriterionForJDBCDate("ow_handle_date =", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ow_handle_date <>", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ow_handle_date >", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_handle_date >=", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateLessThan(Date value) {
            addCriterionForJDBCDate("ow_handle_date <", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_handle_date <=", value, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateIn(List<Date> values) {
            addCriterionForJDBCDate("ow_handle_date in", values, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("ow_handle_date not in", values, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_handle_date between", value1, value2, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_handle_date not between", value1, value2, "owHandleDate");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileIsNull() {
            addCriterion("ow_handle_file is null");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileIsNotNull() {
            addCriterion("ow_handle_file is not null");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileEqualTo(String value) {
            addCriterion("ow_handle_file =", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileNotEqualTo(String value) {
            addCriterion("ow_handle_file <>", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileGreaterThan(String value) {
            addCriterion("ow_handle_file >", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileGreaterThanOrEqualTo(String value) {
            addCriterion("ow_handle_file >=", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileLessThan(String value) {
            addCriterion("ow_handle_file <", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileLessThanOrEqualTo(String value) {
            addCriterion("ow_handle_file <=", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileLike(String value) {
            addCriterion("ow_handle_file like", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileNotLike(String value) {
            addCriterion("ow_handle_file not like", value, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileIn(List<String> values) {
            addCriterion("ow_handle_file in", values, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileNotIn(List<String> values) {
            addCriterion("ow_handle_file not in", values, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileBetween(String value1, String value2) {
            addCriterion("ow_handle_file between", value1, value2, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwHandleFileNotBetween(String value1, String value2) {
            addCriterion("ow_handle_file not between", value1, value2, "owHandleFile");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateIsNull() {
            addCriterion("ow_affect_date is null");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateIsNotNull() {
            addCriterion("ow_affect_date is not null");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateEqualTo(Date value) {
            addCriterionForJDBCDate("ow_affect_date =", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ow_affect_date <>", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ow_affect_date >", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_affect_date >=", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateLessThan(Date value) {
            addCriterionForJDBCDate("ow_affect_date <", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_affect_date <=", value, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateIn(List<Date> values) {
            addCriterionForJDBCDate("ow_affect_date in", values, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("ow_affect_date not in", values, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_affect_date between", value1, value2, "owAffectDate");
            return (Criteria) this;
        }

        public Criteria andOwAffectDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_affect_date not between", value1, value2, "owAffectDate");
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

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andIsRandomIsNull() {
            addCriterion("is_random is null");
            return (Criteria) this;
        }

        public Criteria andIsRandomIsNotNull() {
            addCriterion("is_random is not null");
            return (Criteria) this;
        }

        public Criteria andIsRandomEqualTo(Boolean value) {
            addCriterion("is_random =", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomNotEqualTo(Boolean value) {
            addCriterion("is_random <>", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomGreaterThan(Boolean value) {
            addCriterion("is_random >", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_random >=", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomLessThan(Boolean value) {
            addCriterion("is_random <", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomLessThanOrEqualTo(Boolean value) {
            addCriterion("is_random <=", value, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomIn(List<Boolean> values) {
            addCriterion("is_random in", values, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomNotIn(List<Boolean> values) {
            addCriterion("is_random not in", values, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomBetween(Boolean value1, Boolean value2) {
            addCriterion("is_random between", value1, value2, "isRandom");
            return (Criteria) this;
        }

        public Criteria andIsRandomNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_random not between", value1, value2, "isRandom");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterionForJDBCDate("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterionForJDBCDate("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterionForJDBCDate("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterionForJDBCDate("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
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