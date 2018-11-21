package domain.sc.scMatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScMatterCheckExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScMatterCheckExample() {
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

        public Criteria andCheckFileNameIsNull() {
            addCriterion("check_file_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIsNotNull() {
            addCriterion("check_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameEqualTo(String value) {
            addCriterion("check_file_name =", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotEqualTo(String value) {
            addCriterion("check_file_name <>", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThan(String value) {
            addCriterion("check_file_name >", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_name >=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThan(String value) {
            addCriterion("check_file_name <", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThanOrEqualTo(String value) {
            addCriterion("check_file_name <=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLike(String value) {
            addCriterion("check_file_name like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotLike(String value) {
            addCriterion("check_file_name not like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIn(List<String> values) {
            addCriterion("check_file_name in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotIn(List<String> values) {
            addCriterion("check_file_name not in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameBetween(String value1, String value2) {
            addCriterion("check_file_name between", value1, value2, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotBetween(String value1, String value2) {
            addCriterion("check_file_name not between", value1, value2, "checkFileName");
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