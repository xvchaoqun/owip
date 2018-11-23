package domain.sc.scBorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScBorderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScBorderExample() {
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

        public Criteria andRecordDateIsNull() {
            addCriterion("record_date is null");
            return (Criteria) this;
        }

        public Criteria andRecordDateIsNotNull() {
            addCriterion("record_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecordDateEqualTo(Date value) {
            addCriterionForJDBCDate("record_date =", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("record_date <>", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateGreaterThan(Date value) {
            addCriterionForJDBCDate("record_date >", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("record_date >=", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateLessThan(Date value) {
            addCriterionForJDBCDate("record_date <", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("record_date <=", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateIn(List<Date> values) {
            addCriterionForJDBCDate("record_date in", values, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("record_date not in", values, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("record_date between", value1, value2, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("record_date not between", value1, value2, "recordDate");
            return (Criteria) this;
        }

        public Criteria andAddFileIsNull() {
            addCriterion("add_file is null");
            return (Criteria) this;
        }

        public Criteria andAddFileIsNotNull() {
            addCriterion("add_file is not null");
            return (Criteria) this;
        }

        public Criteria andAddFileEqualTo(String value) {
            addCriterion("add_file =", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileNotEqualTo(String value) {
            addCriterion("add_file <>", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileGreaterThan(String value) {
            addCriterion("add_file >", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileGreaterThanOrEqualTo(String value) {
            addCriterion("add_file >=", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileLessThan(String value) {
            addCriterion("add_file <", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileLessThanOrEqualTo(String value) {
            addCriterion("add_file <=", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileLike(String value) {
            addCriterion("add_file like", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileNotLike(String value) {
            addCriterion("add_file not like", value, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileIn(List<String> values) {
            addCriterion("add_file in", values, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileNotIn(List<String> values) {
            addCriterion("add_file not in", values, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileBetween(String value1, String value2) {
            addCriterion("add_file between", value1, value2, "addFile");
            return (Criteria) this;
        }

        public Criteria andAddFileNotBetween(String value1, String value2) {
            addCriterion("add_file not between", value1, value2, "addFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileIsNull() {
            addCriterion("change_file is null");
            return (Criteria) this;
        }

        public Criteria andChangeFileIsNotNull() {
            addCriterion("change_file is not null");
            return (Criteria) this;
        }

        public Criteria andChangeFileEqualTo(String value) {
            addCriterion("change_file =", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileNotEqualTo(String value) {
            addCriterion("change_file <>", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileGreaterThan(String value) {
            addCriterion("change_file >", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileGreaterThanOrEqualTo(String value) {
            addCriterion("change_file >=", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileLessThan(String value) {
            addCriterion("change_file <", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileLessThanOrEqualTo(String value) {
            addCriterion("change_file <=", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileLike(String value) {
            addCriterion("change_file like", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileNotLike(String value) {
            addCriterion("change_file not like", value, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileIn(List<String> values) {
            addCriterion("change_file in", values, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileNotIn(List<String> values) {
            addCriterion("change_file not in", values, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileBetween(String value1, String value2) {
            addCriterion("change_file between", value1, value2, "changeFile");
            return (Criteria) this;
        }

        public Criteria andChangeFileNotBetween(String value1, String value2) {
            addCriterion("change_file not between", value1, value2, "changeFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileIsNull() {
            addCriterion("delete_file is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFileIsNotNull() {
            addCriterion("delete_file is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFileEqualTo(String value) {
            addCriterion("delete_file =", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileNotEqualTo(String value) {
            addCriterion("delete_file <>", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileGreaterThan(String value) {
            addCriterion("delete_file >", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileGreaterThanOrEqualTo(String value) {
            addCriterion("delete_file >=", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileLessThan(String value) {
            addCriterion("delete_file <", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileLessThanOrEqualTo(String value) {
            addCriterion("delete_file <=", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileLike(String value) {
            addCriterion("delete_file like", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileNotLike(String value) {
            addCriterion("delete_file not like", value, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileIn(List<String> values) {
            addCriterion("delete_file in", values, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileNotIn(List<String> values) {
            addCriterion("delete_file not in", values, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileBetween(String value1, String value2) {
            addCriterion("delete_file between", value1, value2, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andDeleteFileNotBetween(String value1, String value2) {
            addCriterion("delete_file not between", value1, value2, "deleteFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileIsNull() {
            addCriterion("record_file is null");
            return (Criteria) this;
        }

        public Criteria andRecordFileIsNotNull() {
            addCriterion("record_file is not null");
            return (Criteria) this;
        }

        public Criteria andRecordFileEqualTo(String value) {
            addCriterion("record_file =", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotEqualTo(String value) {
            addCriterion("record_file <>", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileGreaterThan(String value) {
            addCriterion("record_file >", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileGreaterThanOrEqualTo(String value) {
            addCriterion("record_file >=", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLessThan(String value) {
            addCriterion("record_file <", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLessThanOrEqualTo(String value) {
            addCriterion("record_file <=", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLike(String value) {
            addCriterion("record_file like", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotLike(String value) {
            addCriterion("record_file not like", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileIn(List<String> values) {
            addCriterion("record_file in", values, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotIn(List<String> values) {
            addCriterion("record_file not in", values, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileBetween(String value1, String value2) {
            addCriterion("record_file between", value1, value2, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotBetween(String value1, String value2) {
            addCriterion("record_file not between", value1, value2, "recordFile");
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