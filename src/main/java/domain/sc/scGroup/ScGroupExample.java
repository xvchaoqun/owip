package domain.sc.scGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScGroupExample() {
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

        public Criteria andHoldDateIsNull() {
            addCriterion("hold_date is null");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNotNull() {
            addCriterion("hold_date is not null");
            return (Criteria) this;
        }

        public Criteria andHoldDateEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date =", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <>", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hold_date >", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date >=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThan(Date value) {
            addCriterionForJDBCDate("hold_date <", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date not in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date not between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andTopicNumIsNull() {
            addCriterion("topic_num is null");
            return (Criteria) this;
        }

        public Criteria andTopicNumIsNotNull() {
            addCriterion("topic_num is not null");
            return (Criteria) this;
        }

        public Criteria andTopicNumEqualTo(Integer value) {
            addCriterion("topic_num =", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotEqualTo(Integer value) {
            addCriterion("topic_num <>", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumGreaterThan(Integer value) {
            addCriterion("topic_num >", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("topic_num >=", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumLessThan(Integer value) {
            addCriterion("topic_num <", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumLessThanOrEqualTo(Integer value) {
            addCriterion("topic_num <=", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumIn(List<Integer> values) {
            addCriterion("topic_num in", values, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotIn(List<Integer> values) {
            addCriterion("topic_num not in", values, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumBetween(Integer value1, Integer value2) {
            addCriterion("topic_num between", value1, value2, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotBetween(Integer value1, Integer value2) {
            addCriterion("topic_num not between", value1, value2, "topicNum");
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

        public Criteria andLogFileIsNull() {
            addCriterion("log_file is null");
            return (Criteria) this;
        }

        public Criteria andLogFileIsNotNull() {
            addCriterion("log_file is not null");
            return (Criteria) this;
        }

        public Criteria andLogFileEqualTo(String value) {
            addCriterion("log_file =", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotEqualTo(String value) {
            addCriterion("log_file <>", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThan(String value) {
            addCriterion("log_file >", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThanOrEqualTo(String value) {
            addCriterion("log_file >=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThan(String value) {
            addCriterion("log_file <", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThanOrEqualTo(String value) {
            addCriterion("log_file <=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLike(String value) {
            addCriterion("log_file like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotLike(String value) {
            addCriterion("log_file not like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileIn(List<String> values) {
            addCriterion("log_file in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotIn(List<String> values) {
            addCriterion("log_file not in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileBetween(String value1, String value2) {
            addCriterion("log_file between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotBetween(String value1, String value2) {
            addCriterion("log_file not between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIsNull() {
            addCriterion("attend_users is null");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIsNotNull() {
            addCriterion("attend_users is not null");
            return (Criteria) this;
        }

        public Criteria andAttendUsersEqualTo(String value) {
            addCriterion("attend_users =", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotEqualTo(String value) {
            addCriterion("attend_users <>", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersGreaterThan(String value) {
            addCriterion("attend_users >", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersGreaterThanOrEqualTo(String value) {
            addCriterion("attend_users >=", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLessThan(String value) {
            addCriterion("attend_users <", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLessThanOrEqualTo(String value) {
            addCriterion("attend_users <=", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLike(String value) {
            addCriterion("attend_users like", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotLike(String value) {
            addCriterion("attend_users not like", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIn(List<String> values) {
            addCriterion("attend_users in", values, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotIn(List<String> values) {
            addCriterion("attend_users not in", values, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersBetween(String value1, String value2) {
            addCriterion("attend_users between", value1, value2, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotBetween(String value1, String value2) {
            addCriterion("attend_users not between", value1, value2, "attendUsers");
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