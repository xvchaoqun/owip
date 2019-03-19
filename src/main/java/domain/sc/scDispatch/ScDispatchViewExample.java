package domain.sc.scDispatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScDispatchViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScDispatchViewExample() {
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

        public Criteria andDispatchTypeIdIsNull() {
            addCriterion("dispatch_type_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdIsNotNull() {
            addCriterion("dispatch_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdEqualTo(Integer value) {
            addCriterion("dispatch_type_id =", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotEqualTo(Integer value) {
            addCriterion("dispatch_type_id <>", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdGreaterThan(Integer value) {
            addCriterion("dispatch_type_id >", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_id >=", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdLessThan(Integer value) {
            addCriterion("dispatch_type_id <", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_id <=", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdIn(List<Integer> values) {
            addCriterion("dispatch_type_id in", values, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotIn(List<Integer> values) {
            addCriterion("dispatch_type_id not in", values, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_id between", value1, value2, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_id not between", value1, value2, "dispatchTypeId");
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNull() {
            addCriterion("meeting_time is null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNotNull() {
            addCriterion("meeting_time is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time =", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time <>", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("meeting_time >", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time >=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThan(Date value) {
            addCriterionForJDBCDate("meeting_time <", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time <=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_time in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_time not in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_time between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_time not between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNull() {
            addCriterion("pub_time is null");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNotNull() {
            addCriterion("pub_time is not null");
            return (Criteria) this;
        }

        public Criteria andPubTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time =", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time <>", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pub_time >", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time >=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThan(Date value) {
            addCriterionForJDBCDate("pub_time <", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time <=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pub_time in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pub_time not in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pub_time between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pub_time not between", value1, value2, "pubTime");
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

        public Criteria andWordFilePathIsNull() {
            addCriterion("word_file_path is null");
            return (Criteria) this;
        }

        public Criteria andWordFilePathIsNotNull() {
            addCriterion("word_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andWordFilePathEqualTo(String value) {
            addCriterion("word_file_path =", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathNotEqualTo(String value) {
            addCriterion("word_file_path <>", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathGreaterThan(String value) {
            addCriterion("word_file_path >", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("word_file_path >=", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathLessThan(String value) {
            addCriterion("word_file_path <", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathLessThanOrEqualTo(String value) {
            addCriterion("word_file_path <=", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathLike(String value) {
            addCriterion("word_file_path like", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathNotLike(String value) {
            addCriterion("word_file_path not like", value, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathIn(List<String> values) {
            addCriterion("word_file_path in", values, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathNotIn(List<String> values) {
            addCriterion("word_file_path not in", values, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathBetween(String value1, String value2) {
            addCriterion("word_file_path between", value1, value2, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andWordFilePathNotBetween(String value1, String value2) {
            addCriterion("word_file_path not between", value1, value2, "wordFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIsNull() {
            addCriterion("sign_file_path is null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIsNotNull() {
            addCriterion("sign_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathEqualTo(String value) {
            addCriterion("sign_file_path =", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotEqualTo(String value) {
            addCriterion("sign_file_path <>", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThan(String value) {
            addCriterion("sign_file_path >", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("sign_file_path >=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThan(String value) {
            addCriterion("sign_file_path <", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThanOrEqualTo(String value) {
            addCriterion("sign_file_path <=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLike(String value) {
            addCriterion("sign_file_path like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotLike(String value) {
            addCriterion("sign_file_path not like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIn(List<String> values) {
            addCriterion("sign_file_path in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotIn(List<String> values) {
            addCriterion("sign_file_path not in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathBetween(String value1, String value2) {
            addCriterion("sign_file_path between", value1, value2, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotBetween(String value1, String value2) {
            addCriterion("sign_file_path not between", value1, value2, "signFilePath");
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

        public Criteria andDispatchIdIsNull() {
            addCriterion("dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchIdIsNotNull() {
            addCriterion("dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchIdEqualTo(Integer value) {
            addCriterion("dispatch_id =", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotEqualTo(Integer value) {
            addCriterion("dispatch_id <>", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdGreaterThan(Integer value) {
            addCriterion("dispatch_id >", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_id >=", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdLessThan(Integer value) {
            addCriterion("dispatch_id <", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_id <=", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdIn(List<Integer> values) {
            addCriterion("dispatch_id in", values, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotIn(List<Integer> values) {
            addCriterion("dispatch_id not in", values, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_id between", value1, value2, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_id not between", value1, value2, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchFileIsNull() {
            addCriterion("dispatch_file is null");
            return (Criteria) this;
        }

        public Criteria andDispatchFileIsNotNull() {
            addCriterion("dispatch_file is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchFileEqualTo(String value) {
            addCriterion("dispatch_file =", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNotEqualTo(String value) {
            addCriterion("dispatch_file <>", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileGreaterThan(String value) {
            addCriterion("dispatch_file >", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileGreaterThanOrEqualTo(String value) {
            addCriterion("dispatch_file >=", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileLessThan(String value) {
            addCriterion("dispatch_file <", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileLessThanOrEqualTo(String value) {
            addCriterion("dispatch_file <=", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileLike(String value) {
            addCriterion("dispatch_file like", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNotLike(String value) {
            addCriterion("dispatch_file not like", value, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileIn(List<String> values) {
            addCriterion("dispatch_file in", values, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNotIn(List<String> values) {
            addCriterion("dispatch_file not in", values, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileBetween(String value1, String value2) {
            addCriterion("dispatch_file between", value1, value2, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNotBetween(String value1, String value2) {
            addCriterion("dispatch_file not between", value1, value2, "dispatchFile");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameIsNull() {
            addCriterion("dispatch_file_name is null");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameIsNotNull() {
            addCriterion("dispatch_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameEqualTo(String value) {
            addCriterion("dispatch_file_name =", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameNotEqualTo(String value) {
            addCriterion("dispatch_file_name <>", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameGreaterThan(String value) {
            addCriterion("dispatch_file_name >", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("dispatch_file_name >=", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameLessThan(String value) {
            addCriterion("dispatch_file_name <", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameLessThanOrEqualTo(String value) {
            addCriterion("dispatch_file_name <=", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameLike(String value) {
            addCriterion("dispatch_file_name like", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameNotLike(String value) {
            addCriterion("dispatch_file_name not like", value, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameIn(List<String> values) {
            addCriterion("dispatch_file_name in", values, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameNotIn(List<String> values) {
            addCriterion("dispatch_file_name not in", values, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameBetween(String value1, String value2) {
            addCriterion("dispatch_file_name between", value1, value2, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchFileNameNotBetween(String value1, String value2) {
            addCriterion("dispatch_file_name not between", value1, value2, "dispatchFileName");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderIsNull() {
            addCriterion("dispatch_type_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderIsNotNull() {
            addCriterion("dispatch_type_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderEqualTo(Integer value) {
            addCriterion("dispatch_type_sort_order =", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderNotEqualTo(Integer value) {
            addCriterion("dispatch_type_sort_order <>", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderGreaterThan(Integer value) {
            addCriterion("dispatch_type_sort_order >", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_sort_order >=", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderLessThan(Integer value) {
            addCriterion("dispatch_type_sort_order <", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_sort_order <=", value, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderIn(List<Integer> values) {
            addCriterion("dispatch_type_sort_order in", values, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderNotIn(List<Integer> values) {
            addCriterion("dispatch_type_sort_order not in", values, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_sort_order between", value1, value2, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_sort_order not between", value1, value2, "dispatchTypeSortOrder");
            return (Criteria) this;
        }

        public Criteria andAppointCountIsNull() {
            addCriterion("appoint_count is null");
            return (Criteria) this;
        }

        public Criteria andAppointCountIsNotNull() {
            addCriterion("appoint_count is not null");
            return (Criteria) this;
        }

        public Criteria andAppointCountEqualTo(Integer value) {
            addCriterion("appoint_count =", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotEqualTo(Integer value) {
            addCriterion("appoint_count <>", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountGreaterThan(Integer value) {
            addCriterion("appoint_count >", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("appoint_count >=", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountLessThan(Integer value) {
            addCriterion("appoint_count <", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountLessThanOrEqualTo(Integer value) {
            addCriterion("appoint_count <=", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountIn(List<Integer> values) {
            addCriterion("appoint_count in", values, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotIn(List<Integer> values) {
            addCriterion("appoint_count not in", values, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountBetween(Integer value1, Integer value2) {
            addCriterion("appoint_count between", value1, value2, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotBetween(Integer value1, Integer value2) {
            addCriterion("appoint_count not between", value1, value2, "appointCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountIsNull() {
            addCriterion("dismiss_count is null");
            return (Criteria) this;
        }

        public Criteria andDismissCountIsNotNull() {
            addCriterion("dismiss_count is not null");
            return (Criteria) this;
        }

        public Criteria andDismissCountEqualTo(Integer value) {
            addCriterion("dismiss_count =", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotEqualTo(Integer value) {
            addCriterion("dismiss_count <>", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountGreaterThan(Integer value) {
            addCriterion("dismiss_count >", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("dismiss_count >=", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountLessThan(Integer value) {
            addCriterion("dismiss_count <", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountLessThanOrEqualTo(Integer value) {
            addCriterion("dismiss_count <=", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountIn(List<Integer> values) {
            addCriterion("dismiss_count in", values, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotIn(List<Integer> values) {
            addCriterion("dismiss_count not in", values, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountBetween(Integer value1, Integer value2) {
            addCriterion("dismiss_count between", value1, value2, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotBetween(Integer value1, Integer value2) {
            addCriterion("dismiss_count not between", value1, value2, "dismissCount");
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