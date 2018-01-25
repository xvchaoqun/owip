package domain.sc.scLetter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScLetterReplyViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScLetterReplyViewExample() {
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

        public Criteria andLetterIdIsNull() {
            addCriterion("letter_id is null");
            return (Criteria) this;
        }

        public Criteria andLetterIdIsNotNull() {
            addCriterion("letter_id is not null");
            return (Criteria) this;
        }

        public Criteria andLetterIdEqualTo(Integer value) {
            addCriterion("letter_id =", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdNotEqualTo(Integer value) {
            addCriterion("letter_id <>", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdGreaterThan(Integer value) {
            addCriterion("letter_id >", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("letter_id >=", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdLessThan(Integer value) {
            addCriterion("letter_id <", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdLessThanOrEqualTo(Integer value) {
            addCriterion("letter_id <=", value, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdIn(List<Integer> values) {
            addCriterion("letter_id in", values, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdNotIn(List<Integer> values) {
            addCriterion("letter_id not in", values, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdBetween(Integer value1, Integer value2) {
            addCriterion("letter_id between", value1, value2, "letterId");
            return (Criteria) this;
        }

        public Criteria andLetterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("letter_id not between", value1, value2, "letterId");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andReplyDateIsNull() {
            addCriterion("reply_date is null");
            return (Criteria) this;
        }

        public Criteria andReplyDateIsNotNull() {
            addCriterion("reply_date is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDateEqualTo(Date value) {
            addCriterionForJDBCDate("reply_date =", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("reply_date <>", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateGreaterThan(Date value) {
            addCriterionForJDBCDate("reply_date >", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reply_date >=", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateLessThan(Date value) {
            addCriterionForJDBCDate("reply_date <", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reply_date <=", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateIn(List<Date> values) {
            addCriterionForJDBCDate("reply_date in", values, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("reply_date not in", values, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reply_date between", value1, value2, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reply_date not between", value1, value2, "replyDate");
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

        public Criteria andLetterYearIsNull() {
            addCriterion("letter_year is null");
            return (Criteria) this;
        }

        public Criteria andLetterYearIsNotNull() {
            addCriterion("letter_year is not null");
            return (Criteria) this;
        }

        public Criteria andLetterYearEqualTo(Integer value) {
            addCriterion("letter_year =", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearNotEqualTo(Integer value) {
            addCriterion("letter_year <>", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearGreaterThan(Integer value) {
            addCriterion("letter_year >", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("letter_year >=", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearLessThan(Integer value) {
            addCriterion("letter_year <", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearLessThanOrEqualTo(Integer value) {
            addCriterion("letter_year <=", value, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearIn(List<Integer> values) {
            addCriterion("letter_year in", values, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearNotIn(List<Integer> values) {
            addCriterion("letter_year not in", values, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearBetween(Integer value1, Integer value2) {
            addCriterion("letter_year between", value1, value2, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterYearNotBetween(Integer value1, Integer value2) {
            addCriterion("letter_year not between", value1, value2, "letterYear");
            return (Criteria) this;
        }

        public Criteria andLetterNumIsNull() {
            addCriterion("letter_num is null");
            return (Criteria) this;
        }

        public Criteria andLetterNumIsNotNull() {
            addCriterion("letter_num is not null");
            return (Criteria) this;
        }

        public Criteria andLetterNumEqualTo(Integer value) {
            addCriterion("letter_num =", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumNotEqualTo(Integer value) {
            addCriterion("letter_num <>", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumGreaterThan(Integer value) {
            addCriterion("letter_num >", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("letter_num >=", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumLessThan(Integer value) {
            addCriterion("letter_num <", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumLessThanOrEqualTo(Integer value) {
            addCriterion("letter_num <=", value, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumIn(List<Integer> values) {
            addCriterion("letter_num in", values, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumNotIn(List<Integer> values) {
            addCriterion("letter_num not in", values, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumBetween(Integer value1, Integer value2) {
            addCriterion("letter_num between", value1, value2, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterNumNotBetween(Integer value1, Integer value2) {
            addCriterion("letter_num not between", value1, value2, "letterNum");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateIsNull() {
            addCriterion("letter_query_date is null");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateIsNotNull() {
            addCriterion("letter_query_date is not null");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateEqualTo(Date value) {
            addCriterionForJDBCDate("letter_query_date =", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("letter_query_date <>", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("letter_query_date >", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("letter_query_date >=", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateLessThan(Date value) {
            addCriterionForJDBCDate("letter_query_date <", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("letter_query_date <=", value, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateIn(List<Date> values) {
            addCriterionForJDBCDate("letter_query_date in", values, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("letter_query_date not in", values, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("letter_query_date between", value1, value2, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterQueryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("letter_query_date not between", value1, value2, "letterQueryDate");
            return (Criteria) this;
        }

        public Criteria andLetterTypeIsNull() {
            addCriterion("letter_type is null");
            return (Criteria) this;
        }

        public Criteria andLetterTypeIsNotNull() {
            addCriterion("letter_type is not null");
            return (Criteria) this;
        }

        public Criteria andLetterTypeEqualTo(Integer value) {
            addCriterion("letter_type =", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeNotEqualTo(Integer value) {
            addCriterion("letter_type <>", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeGreaterThan(Integer value) {
            addCriterion("letter_type >", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("letter_type >=", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeLessThan(Integer value) {
            addCriterion("letter_type <", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeLessThanOrEqualTo(Integer value) {
            addCriterion("letter_type <=", value, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeIn(List<Integer> values) {
            addCriterion("letter_type in", values, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeNotIn(List<Integer> values) {
            addCriterion("letter_type not in", values, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeBetween(Integer value1, Integer value2) {
            addCriterion("letter_type between", value1, value2, "letterType");
            return (Criteria) this;
        }

        public Criteria andLetterTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("letter_type not between", value1, value2, "letterType");
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