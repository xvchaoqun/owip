package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PassportDrawExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PassportDrawExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andPassportIdIsNull() {
            addCriterion("passport_id is null");
            return (Criteria) this;
        }

        public Criteria andPassportIdIsNotNull() {
            addCriterion("passport_id is not null");
            return (Criteria) this;
        }

        public Criteria andPassportIdEqualTo(Integer value) {
            addCriterion("passport_id =", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdNotEqualTo(Integer value) {
            addCriterion("passport_id <>", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdGreaterThan(Integer value) {
            addCriterion("passport_id >", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("passport_id >=", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdLessThan(Integer value) {
            addCriterion("passport_id <", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdLessThanOrEqualTo(Integer value) {
            addCriterion("passport_id <=", value, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdIn(List<Integer> values) {
            addCriterion("passport_id in", values, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdNotIn(List<Integer> values) {
            addCriterion("passport_id not in", values, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdBetween(Integer value1, Integer value2) {
            addCriterion("passport_id between", value1, value2, "passportId");
            return (Criteria) this;
        }

        public Criteria andPassportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("passport_id not between", value1, value2, "passportId");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("apply_date is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("apply_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(Date value) {
            addCriterionForJDBCDate("apply_date >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(Date value) {
            addCriterionForJDBCDate("apply_date <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
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

        public Criteria andNeedSignIsNull() {
            addCriterion("need_sign is null");
            return (Criteria) this;
        }

        public Criteria andNeedSignIsNotNull() {
            addCriterion("need_sign is not null");
            return (Criteria) this;
        }

        public Criteria andNeedSignEqualTo(Boolean value) {
            addCriterion("need_sign =", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignNotEqualTo(Boolean value) {
            addCriterion("need_sign <>", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignGreaterThan(Boolean value) {
            addCriterion("need_sign >", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_sign >=", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignLessThan(Boolean value) {
            addCriterion("need_sign <", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignLessThanOrEqualTo(Boolean value) {
            addCriterion("need_sign <=", value, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignIn(List<Boolean> values) {
            addCriterion("need_sign in", values, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignNotIn(List<Boolean> values) {
            addCriterion("need_sign not in", values, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignBetween(Boolean value1, Boolean value2) {
            addCriterion("need_sign between", value1, value2, "needSign");
            return (Criteria) this;
        }

        public Criteria andNeedSignNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_sign not between", value1, value2, "needSign");
            return (Criteria) this;
        }

        public Criteria andFilesIsNull() {
            addCriterion("files is null");
            return (Criteria) this;
        }

        public Criteria andFilesIsNotNull() {
            addCriterion("files is not null");
            return (Criteria) this;
        }

        public Criteria andFilesEqualTo(String value) {
            addCriterion("files =", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotEqualTo(String value) {
            addCriterion("files <>", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThan(String value) {
            addCriterion("files >", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThanOrEqualTo(String value) {
            addCriterion("files >=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThan(String value) {
            addCriterion("files <", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThanOrEqualTo(String value) {
            addCriterion("files <=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLike(String value) {
            addCriterion("files like", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotLike(String value) {
            addCriterion("files not like", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesIn(List<String> values) {
            addCriterion("files in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotIn(List<String> values) {
            addCriterion("files not in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesBetween(String value1, String value2) {
            addCriterion("files between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotBetween(String value1, String value2) {
            addCriterion("files not between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andExpectDateIsNull() {
            addCriterion("expect_date is null");
            return (Criteria) this;
        }

        public Criteria andExpectDateIsNotNull() {
            addCriterion("expect_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpectDateEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date =", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date <>", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_date >", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date >=", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateLessThan(Date value) {
            addCriterionForJDBCDate("expect_date <", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date <=", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateIn(List<Date> values) {
            addCriterionForJDBCDate("expect_date in", values, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_date not in", values, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_date between", value1, value2, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_date not between", value1, value2, "expectDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNull() {
            addCriterion("handle_date is null");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNotNull() {
            addCriterion("handle_date is not null");
            return (Criteria) this;
        }

        public Criteria andHandleDateEqualTo(Date value) {
            addCriterionForJDBCDate("handle_date =", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("handle_date <>", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThan(Date value) {
            addCriterionForJDBCDate("handle_date >", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("handle_date >=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThan(Date value) {
            addCriterionForJDBCDate("handle_date <", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("handle_date <=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateIn(List<Date> values) {
            addCriterionForJDBCDate("handle_date in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("handle_date not in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("handle_date between", value1, value2, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("handle_date not between", value1, value2, "handleDate");
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

        public Criteria andApproveRemarkIsNull() {
            addCriterion("approve_remark is null");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkIsNotNull() {
            addCriterion("approve_remark is not null");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkEqualTo(String value) {
            addCriterion("approve_remark =", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotEqualTo(String value) {
            addCriterion("approve_remark <>", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkGreaterThan(String value) {
            addCriterion("approve_remark >", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("approve_remark >=", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLessThan(String value) {
            addCriterion("approve_remark <", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLessThanOrEqualTo(String value) {
            addCriterion("approve_remark <=", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLike(String value) {
            addCriterion("approve_remark like", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotLike(String value) {
            addCriterion("approve_remark not like", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkIn(List<String> values) {
            addCriterion("approve_remark in", values, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotIn(List<String> values) {
            addCriterion("approve_remark not in", values, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkBetween(String value1, String value2) {
            addCriterion("approve_remark between", value1, value2, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotBetween(String value1, String value2) {
            addCriterion("approve_remark not between", value1, value2, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNull() {
            addCriterion("approve_time is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("approve_time is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("approve_time =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("approve_time <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("approve_time >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approve_time >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("approve_time <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("approve_time <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("approve_time in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("approve_time not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("approve_time between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("approve_time not between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveIpIsNull() {
            addCriterion("approve_ip is null");
            return (Criteria) this;
        }

        public Criteria andApproveIpIsNotNull() {
            addCriterion("approve_ip is not null");
            return (Criteria) this;
        }

        public Criteria andApproveIpEqualTo(String value) {
            addCriterion("approve_ip =", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpNotEqualTo(String value) {
            addCriterion("approve_ip <>", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpGreaterThan(String value) {
            addCriterion("approve_ip >", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpGreaterThanOrEqualTo(String value) {
            addCriterion("approve_ip >=", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpLessThan(String value) {
            addCriterion("approve_ip <", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpLessThanOrEqualTo(String value) {
            addCriterion("approve_ip <=", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpLike(String value) {
            addCriterion("approve_ip like", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpNotLike(String value) {
            addCriterion("approve_ip not like", value, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpIn(List<String> values) {
            addCriterion("approve_ip in", values, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpNotIn(List<String> values) {
            addCriterion("approve_ip not in", values, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpBetween(String value1, String value2) {
            addCriterion("approve_ip between", value1, value2, "approveIp");
            return (Criteria) this;
        }

        public Criteria andApproveIpNotBetween(String value1, String value2) {
            addCriterion("approve_ip not between", value1, value2, "approveIp");
            return (Criteria) this;
        }

        public Criteria andRealStartDateIsNull() {
            addCriterion("real_start_date is null");
            return (Criteria) this;
        }

        public Criteria andRealStartDateIsNotNull() {
            addCriterion("real_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andRealStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("real_start_date =", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("real_start_date <>", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("real_start_date >", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_start_date >=", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateLessThan(Date value) {
            addCriterionForJDBCDate("real_start_date <", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_start_date <=", value, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("real_start_date in", values, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("real_start_date not in", values, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_start_date between", value1, value2, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_start_date not between", value1, value2, "realStartDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateIsNull() {
            addCriterion("real_return_date is null");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateIsNotNull() {
            addCriterion("real_return_date is not null");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateEqualTo(Date value) {
            addCriterionForJDBCDate("real_return_date =", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("real_return_date <>", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateGreaterThan(Date value) {
            addCriterionForJDBCDate("real_return_date >", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_return_date >=", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateLessThan(Date value) {
            addCriterionForJDBCDate("real_return_date <", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_return_date <=", value, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateIn(List<Date> values) {
            addCriterionForJDBCDate("real_return_date in", values, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("real_return_date not in", values, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_return_date between", value1, value2, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealReturnDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_return_date not between", value1, value2, "realReturnDate");
            return (Criteria) this;
        }

        public Criteria andRealToCountryIsNull() {
            addCriterion("real_to_country is null");
            return (Criteria) this;
        }

        public Criteria andRealToCountryIsNotNull() {
            addCriterion("real_to_country is not null");
            return (Criteria) this;
        }

        public Criteria andRealToCountryEqualTo(String value) {
            addCriterion("real_to_country =", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryNotEqualTo(String value) {
            addCriterion("real_to_country <>", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryGreaterThan(String value) {
            addCriterion("real_to_country >", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryGreaterThanOrEqualTo(String value) {
            addCriterion("real_to_country >=", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryLessThan(String value) {
            addCriterion("real_to_country <", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryLessThanOrEqualTo(String value) {
            addCriterion("real_to_country <=", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryLike(String value) {
            addCriterion("real_to_country like", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryNotLike(String value) {
            addCriterion("real_to_country not like", value, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryIn(List<String> values) {
            addCriterion("real_to_country in", values, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryNotIn(List<String> values) {
            addCriterion("real_to_country not in", values, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryBetween(String value1, String value2) {
            addCriterion("real_to_country between", value1, value2, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andRealToCountryNotBetween(String value1, String value2) {
            addCriterion("real_to_country not between", value1, value2, "realToCountry");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNull() {
            addCriterion("return_time is null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNotNull() {
            addCriterion("return_time is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeEqualTo(Date value) {
            addCriterionForJDBCDate("return_time =", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("return_time <>", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("return_time >", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("return_time >=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThan(Date value) {
            addCriterionForJDBCDate("return_time <", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("return_time <=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIn(List<Date> values) {
            addCriterionForJDBCDate("return_time in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("return_time not in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("return_time between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("return_time not between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnStatusIsNull() {
            addCriterion("return_status is null");
            return (Criteria) this;
        }

        public Criteria andReturnStatusIsNotNull() {
            addCriterion("return_status is not null");
            return (Criteria) this;
        }

        public Criteria andReturnStatusEqualTo(Byte value) {
            addCriterion("return_status =", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotEqualTo(Byte value) {
            addCriterion("return_status <>", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThan(Byte value) {
            addCriterion("return_status >", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("return_status >=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThan(Byte value) {
            addCriterion("return_status <", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanOrEqualTo(Byte value) {
            addCriterion("return_status <=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusIn(List<Byte> values) {
            addCriterion("return_status in", values, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotIn(List<Byte> values) {
            addCriterion("return_status not in", values, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusBetween(Byte value1, Byte value2) {
            addCriterion("return_status between", value1, value2, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("return_status not between", value1, value2, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkIsNull() {
            addCriterion("return_remark is null");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkIsNotNull() {
            addCriterion("return_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkEqualTo(String value) {
            addCriterion("return_remark =", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkNotEqualTo(String value) {
            addCriterion("return_remark <>", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkGreaterThan(String value) {
            addCriterion("return_remark >", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("return_remark >=", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkLessThan(String value) {
            addCriterion("return_remark <", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkLessThanOrEqualTo(String value) {
            addCriterion("return_remark <=", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkLike(String value) {
            addCriterion("return_remark like", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkNotLike(String value) {
            addCriterion("return_remark not like", value, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkIn(List<String> values) {
            addCriterion("return_remark in", values, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkNotIn(List<String> values) {
            addCriterion("return_remark not in", values, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkBetween(String value1, String value2) {
            addCriterion("return_remark between", value1, value2, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andReturnRemarkNotBetween(String value1, String value2) {
            addCriterion("return_remark not between", value1, value2, "returnRemark");
            return (Criteria) this;
        }

        public Criteria andJobCertifyIsNull() {
            addCriterion("job_certify is null");
            return (Criteria) this;
        }

        public Criteria andJobCertifyIsNotNull() {
            addCriterion("job_certify is not null");
            return (Criteria) this;
        }

        public Criteria andJobCertifyEqualTo(Boolean value) {
            addCriterion("job_certify =", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyNotEqualTo(Boolean value) {
            addCriterion("job_certify <>", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyGreaterThan(Boolean value) {
            addCriterion("job_certify >", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("job_certify >=", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyLessThan(Boolean value) {
            addCriterion("job_certify <", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyLessThanOrEqualTo(Boolean value) {
            addCriterion("job_certify <=", value, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyIn(List<Boolean> values) {
            addCriterion("job_certify in", values, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyNotIn(List<Boolean> values) {
            addCriterion("job_certify not in", values, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyBetween(Boolean value1, Boolean value2) {
            addCriterion("job_certify between", value1, value2, "jobCertify");
            return (Criteria) this;
        }

        public Criteria andJobCertifyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("job_certify not between", value1, value2, "jobCertify");
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