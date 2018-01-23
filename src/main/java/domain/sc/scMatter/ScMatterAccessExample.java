package domain.sc.scMatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScMatterAccessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScMatterAccessExample() {
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

        public Criteria andAccessDateIsNull() {
            addCriterion("access_date is null");
            return (Criteria) this;
        }

        public Criteria andAccessDateIsNotNull() {
            addCriterion("access_date is not null");
            return (Criteria) this;
        }

        public Criteria andAccessDateEqualTo(Date value) {
            addCriterionForJDBCDate("access_date =", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("access_date <>", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateGreaterThan(Date value) {
            addCriterionForJDBCDate("access_date >", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("access_date >=", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateLessThan(Date value) {
            addCriterionForJDBCDate("access_date <", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("access_date <=", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateIn(List<Date> values) {
            addCriterionForJDBCDate("access_date in", values, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("access_date not in", values, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("access_date between", value1, value2, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("access_date not between", value1, value2, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessFileIsNull() {
            addCriterion("access_file is null");
            return (Criteria) this;
        }

        public Criteria andAccessFileIsNotNull() {
            addCriterion("access_file is not null");
            return (Criteria) this;
        }

        public Criteria andAccessFileEqualTo(String value) {
            addCriterion("access_file =", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileNotEqualTo(String value) {
            addCriterion("access_file <>", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileGreaterThan(String value) {
            addCriterion("access_file >", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileGreaterThanOrEqualTo(String value) {
            addCriterion("access_file >=", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileLessThan(String value) {
            addCriterion("access_file <", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileLessThanOrEqualTo(String value) {
            addCriterion("access_file <=", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileLike(String value) {
            addCriterion("access_file like", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileNotLike(String value) {
            addCriterion("access_file not like", value, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileIn(List<String> values) {
            addCriterion("access_file in", values, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileNotIn(List<String> values) {
            addCriterion("access_file not in", values, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileBetween(String value1, String value2) {
            addCriterion("access_file between", value1, value2, "accessFile");
            return (Criteria) this;
        }

        public Criteria andAccessFileNotBetween(String value1, String value2) {
            addCriterion("access_file not between", value1, value2, "accessFile");
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

        public Criteria andIsCopyIsNull() {
            addCriterion("is_copy is null");
            return (Criteria) this;
        }

        public Criteria andIsCopyIsNotNull() {
            addCriterion("is_copy is not null");
            return (Criteria) this;
        }

        public Criteria andIsCopyEqualTo(Boolean value) {
            addCriterion("is_copy =", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotEqualTo(Boolean value) {
            addCriterion("is_copy <>", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyGreaterThan(Boolean value) {
            addCriterion("is_copy >", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_copy >=", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyLessThan(Boolean value) {
            addCriterion("is_copy <", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyLessThanOrEqualTo(Boolean value) {
            addCriterion("is_copy <=", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyIn(List<Boolean> values) {
            addCriterion("is_copy in", values, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotIn(List<Boolean> values) {
            addCriterion("is_copy not in", values, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyBetween(Boolean value1, Boolean value2) {
            addCriterion("is_copy between", value1, value2, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_copy not between", value1, value2, "isCopy");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNull() {
            addCriterion("purpose is null");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNotNull() {
            addCriterion("purpose is not null");
            return (Criteria) this;
        }

        public Criteria andPurposeEqualTo(String value) {
            addCriterion("purpose =", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotEqualTo(String value) {
            addCriterion("purpose <>", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThan(String value) {
            addCriterion("purpose >", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("purpose >=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThan(String value) {
            addCriterion("purpose <", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThanOrEqualTo(String value) {
            addCriterion("purpose <=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLike(String value) {
            addCriterion("purpose like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotLike(String value) {
            addCriterion("purpose not like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeIn(List<String> values) {
            addCriterion("purpose in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotIn(List<String> values) {
            addCriterion("purpose not in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeBetween(String value1, String value2) {
            addCriterion("purpose between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotBetween(String value1, String value2) {
            addCriterion("purpose not between", value1, value2, "purpose");
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

        public Criteria andHandleUserIdIsNull() {
            addCriterion("handle_user_id is null");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdIsNotNull() {
            addCriterion("handle_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdEqualTo(Integer value) {
            addCriterion("handle_user_id =", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotEqualTo(Integer value) {
            addCriterion("handle_user_id <>", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdGreaterThan(Integer value) {
            addCriterion("handle_user_id >", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_user_id >=", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdLessThan(Integer value) {
            addCriterion("handle_user_id <", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("handle_user_id <=", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdIn(List<Integer> values) {
            addCriterion("handle_user_id in", values, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotIn(List<Integer> values) {
            addCriterion("handle_user_id not in", values, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdBetween(Integer value1, Integer value2) {
            addCriterion("handle_user_id between", value1, value2, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_user_id not between", value1, value2, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNull() {
            addCriterion("receiver is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNotNull() {
            addCriterion("receiver is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverEqualTo(String value) {
            addCriterion("receiver =", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotEqualTo(String value) {
            addCriterion("receiver <>", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThan(String value) {
            addCriterion("receiver >", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThanOrEqualTo(String value) {
            addCriterion("receiver >=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThan(String value) {
            addCriterion("receiver <", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThanOrEqualTo(String value) {
            addCriterion("receiver <=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLike(String value) {
            addCriterion("receiver like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotLike(String value) {
            addCriterion("receiver not like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverIn(List<String> values) {
            addCriterion("receiver in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotIn(List<String> values) {
            addCriterion("receiver not in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverBetween(String value1, String value2) {
            addCriterion("receiver between", value1, value2, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotBetween(String value1, String value2) {
            addCriterion("receiver not between", value1, value2, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceivePdfIsNull() {
            addCriterion("receive_pdf is null");
            return (Criteria) this;
        }

        public Criteria andReceivePdfIsNotNull() {
            addCriterion("receive_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andReceivePdfEqualTo(String value) {
            addCriterion("receive_pdf =", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfNotEqualTo(String value) {
            addCriterion("receive_pdf <>", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfGreaterThan(String value) {
            addCriterion("receive_pdf >", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfGreaterThanOrEqualTo(String value) {
            addCriterion("receive_pdf >=", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfLessThan(String value) {
            addCriterion("receive_pdf <", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfLessThanOrEqualTo(String value) {
            addCriterion("receive_pdf <=", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfLike(String value) {
            addCriterion("receive_pdf like", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfNotLike(String value) {
            addCriterion("receive_pdf not like", value, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfIn(List<String> values) {
            addCriterion("receive_pdf in", values, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfNotIn(List<String> values) {
            addCriterion("receive_pdf not in", values, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfBetween(String value1, String value2) {
            addCriterion("receive_pdf between", value1, value2, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReceivePdfNotBetween(String value1, String value2) {
            addCriterion("receive_pdf not between", value1, value2, "receivePdf");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNull() {
            addCriterion("return_date is null");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNotNull() {
            addCriterion("return_date is not null");
            return (Criteria) this;
        }

        public Criteria andReturnDateEqualTo(Date value) {
            addCriterionForJDBCDate("return_date =", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("return_date <>", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThan(Date value) {
            addCriterionForJDBCDate("return_date >", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("return_date >=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThan(Date value) {
            addCriterionForJDBCDate("return_date <", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("return_date <=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateIn(List<Date> values) {
            addCriterionForJDBCDate("return_date in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("return_date not in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("return_date between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("return_date not between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIsNull() {
            addCriterion("return_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIsNotNull() {
            addCriterion("return_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdEqualTo(Integer value) {
            addCriterion("return_user_id =", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotEqualTo(Integer value) {
            addCriterion("return_user_id <>", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdGreaterThan(Integer value) {
            addCriterion("return_user_id >", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_user_id >=", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdLessThan(Integer value) {
            addCriterion("return_user_id <", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("return_user_id <=", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIn(List<Integer> values) {
            addCriterion("return_user_id in", values, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotIn(List<Integer> values) {
            addCriterion("return_user_id not in", values, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdBetween(Integer value1, Integer value2) {
            addCriterion("return_user_id between", value1, value2, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("return_user_id not between", value1, value2, "returnUserId");
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