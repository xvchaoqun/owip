package domain.sc.scLetter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScLetterReplyItemViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScLetterReplyItemViewExample() {
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

        public Criteria andReplyIdIsNull() {
            addCriterion("reply_id is null");
            return (Criteria) this;
        }

        public Criteria andReplyIdIsNotNull() {
            addCriterion("reply_id is not null");
            return (Criteria) this;
        }

        public Criteria andReplyIdEqualTo(Integer value) {
            addCriterion("reply_id =", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdNotEqualTo(Integer value) {
            addCriterion("reply_id <>", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdGreaterThan(Integer value) {
            addCriterion("reply_id >", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_id >=", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdLessThan(Integer value) {
            addCriterion("reply_id <", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("reply_id <=", value, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdIn(List<Integer> values) {
            addCriterion("reply_id in", values, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdNotIn(List<Integer> values) {
            addCriterion("reply_id not in", values, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdBetween(Integer value1, Integer value2) {
            addCriterion("reply_id between", value1, value2, "replyId");
            return (Criteria) this;
        }

        public Criteria andReplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_id not between", value1, value2, "replyId");
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andReplyTypeIsNull() {
            addCriterion("reply_type is null");
            return (Criteria) this;
        }

        public Criteria andReplyTypeIsNotNull() {
            addCriterion("reply_type is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTypeEqualTo(Integer value) {
            addCriterion("reply_type =", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeNotEqualTo(Integer value) {
            addCriterion("reply_type <>", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeGreaterThan(Integer value) {
            addCriterion("reply_type >", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_type >=", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeLessThan(Integer value) {
            addCriterion("reply_type <", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("reply_type <=", value, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeIn(List<Integer> values) {
            addCriterion("reply_type in", values, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeNotIn(List<Integer> values) {
            addCriterion("reply_type not in", values, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeBetween(Integer value1, Integer value2) {
            addCriterion("reply_type between", value1, value2, "replyType");
            return (Criteria) this;
        }

        public Criteria andReplyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_type not between", value1, value2, "replyType");
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

        public Criteria andReplyNumIsNull() {
            addCriterion("reply_num is null");
            return (Criteria) this;
        }

        public Criteria andReplyNumIsNotNull() {
            addCriterion("reply_num is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNumEqualTo(Integer value) {
            addCriterion("reply_num =", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotEqualTo(Integer value) {
            addCriterion("reply_num <>", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThan(Integer value) {
            addCriterion("reply_num >", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_num >=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThan(Integer value) {
            addCriterion("reply_num <", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThanOrEqualTo(Integer value) {
            addCriterion("reply_num <=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumIn(List<Integer> values) {
            addCriterion("reply_num in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotIn(List<Integer> values) {
            addCriterion("reply_num not in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumBetween(Integer value1, Integer value2) {
            addCriterion("reply_num between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_num not between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathIsNull() {
            addCriterion("reply_file_path is null");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathIsNotNull() {
            addCriterion("reply_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathEqualTo(String value) {
            addCriterion("reply_file_path =", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathNotEqualTo(String value) {
            addCriterion("reply_file_path <>", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathGreaterThan(String value) {
            addCriterion("reply_file_path >", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("reply_file_path >=", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathLessThan(String value) {
            addCriterion("reply_file_path <", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathLessThanOrEqualTo(String value) {
            addCriterion("reply_file_path <=", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathLike(String value) {
            addCriterion("reply_file_path like", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathNotLike(String value) {
            addCriterion("reply_file_path not like", value, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathIn(List<String> values) {
            addCriterion("reply_file_path in", values, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathNotIn(List<String> values) {
            addCriterion("reply_file_path not in", values, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathBetween(String value1, String value2) {
            addCriterion("reply_file_path between", value1, value2, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFilePathNotBetween(String value1, String value2) {
            addCriterion("reply_file_path not between", value1, value2, "replyFilePath");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameIsNull() {
            addCriterion("reply_file_name is null");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameIsNotNull() {
            addCriterion("reply_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameEqualTo(String value) {
            addCriterion("reply_file_name =", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameNotEqualTo(String value) {
            addCriterion("reply_file_name <>", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameGreaterThan(String value) {
            addCriterion("reply_file_name >", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("reply_file_name >=", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameLessThan(String value) {
            addCriterion("reply_file_name <", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameLessThanOrEqualTo(String value) {
            addCriterion("reply_file_name <=", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameLike(String value) {
            addCriterion("reply_file_name like", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameNotLike(String value) {
            addCriterion("reply_file_name not like", value, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameIn(List<String> values) {
            addCriterion("reply_file_name in", values, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameNotIn(List<String> values) {
            addCriterion("reply_file_name not in", values, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameBetween(String value1, String value2) {
            addCriterion("reply_file_name between", value1, value2, "replyFileName");
            return (Criteria) this;
        }

        public Criteria andReplyFileNameNotBetween(String value1, String value2) {
            addCriterion("reply_file_name not between", value1, value2, "replyFileName");
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

        public Criteria andLetterFilePathIsNull() {
            addCriterion("letter_file_path is null");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathIsNotNull() {
            addCriterion("letter_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathEqualTo(String value) {
            addCriterion("letter_file_path =", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathNotEqualTo(String value) {
            addCriterion("letter_file_path <>", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathGreaterThan(String value) {
            addCriterion("letter_file_path >", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("letter_file_path >=", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathLessThan(String value) {
            addCriterion("letter_file_path <", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathLessThanOrEqualTo(String value) {
            addCriterion("letter_file_path <=", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathLike(String value) {
            addCriterion("letter_file_path like", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathNotLike(String value) {
            addCriterion("letter_file_path not like", value, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathIn(List<String> values) {
            addCriterion("letter_file_path in", values, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathNotIn(List<String> values) {
            addCriterion("letter_file_path not in", values, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathBetween(String value1, String value2) {
            addCriterion("letter_file_path between", value1, value2, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFilePathNotBetween(String value1, String value2) {
            addCriterion("letter_file_path not between", value1, value2, "letterFilePath");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameIsNull() {
            addCriterion("letter_file_name is null");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameIsNotNull() {
            addCriterion("letter_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameEqualTo(String value) {
            addCriterion("letter_file_name =", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameNotEqualTo(String value) {
            addCriterion("letter_file_name <>", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameGreaterThan(String value) {
            addCriterion("letter_file_name >", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("letter_file_name >=", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameLessThan(String value) {
            addCriterion("letter_file_name <", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameLessThanOrEqualTo(String value) {
            addCriterion("letter_file_name <=", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameLike(String value) {
            addCriterion("letter_file_name like", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameNotLike(String value) {
            addCriterion("letter_file_name not like", value, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameIn(List<String> values) {
            addCriterion("letter_file_name in", values, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameNotIn(List<String> values) {
            addCriterion("letter_file_name not in", values, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameBetween(String value1, String value2) {
            addCriterion("letter_file_name between", value1, value2, "letterFileName");
            return (Criteria) this;
        }

        public Criteria andLetterFileNameNotBetween(String value1, String value2) {
            addCriterion("letter_file_name not between", value1, value2, "letterFileName");
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