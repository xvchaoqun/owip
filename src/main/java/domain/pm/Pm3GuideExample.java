package domain.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Pm3GuideExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Pm3GuideExample() {
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

        public Criteria andMeetingMonthIsNull() {
            addCriterion("meeting_month is null");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthIsNotNull() {
            addCriterion("meeting_month is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_month =", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthNotEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_month <>", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthGreaterThan(Date value) {
            addCriterionForJDBCDate("meeting_month >", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_month >=", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthLessThan(Date value) {
            addCriterionForJDBCDate("meeting_month <", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_month <=", value, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_month in", values, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthNotIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_month not in", values, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_month between", value1, value2, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andMeetingMonthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_month not between", value1, value2, "meetingMonth");
            return (Criteria) this;
        }

        public Criteria andGuideFilesIsNull() {
            addCriterion("guide_files is null");
            return (Criteria) this;
        }

        public Criteria andGuideFilesIsNotNull() {
            addCriterion("guide_files is not null");
            return (Criteria) this;
        }

        public Criteria andGuideFilesEqualTo(String value) {
            addCriterion("guide_files =", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesNotEqualTo(String value) {
            addCriterion("guide_files <>", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesGreaterThan(String value) {
            addCriterion("guide_files >", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesGreaterThanOrEqualTo(String value) {
            addCriterion("guide_files >=", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesLessThan(String value) {
            addCriterion("guide_files <", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesLessThanOrEqualTo(String value) {
            addCriterion("guide_files <=", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesLike(String value) {
            addCriterion("guide_files like", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesNotLike(String value) {
            addCriterion("guide_files not like", value, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesIn(List<String> values) {
            addCriterion("guide_files in", values, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesNotIn(List<String> values) {
            addCriterion("guide_files not in", values, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesBetween(String value1, String value2) {
            addCriterion("guide_files between", value1, value2, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilesNotBetween(String value1, String value2) {
            addCriterion("guide_files not between", value1, value2, "guideFiles");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesIsNull() {
            addCriterion("guide_filenames is null");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesIsNotNull() {
            addCriterion("guide_filenames is not null");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesEqualTo(String value) {
            addCriterion("guide_filenames =", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesNotEqualTo(String value) {
            addCriterion("guide_filenames <>", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesGreaterThan(String value) {
            addCriterion("guide_filenames >", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesGreaterThanOrEqualTo(String value) {
            addCriterion("guide_filenames >=", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesLessThan(String value) {
            addCriterion("guide_filenames <", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesLessThanOrEqualTo(String value) {
            addCriterion("guide_filenames <=", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesLike(String value) {
            addCriterion("guide_filenames like", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesNotLike(String value) {
            addCriterion("guide_filenames not like", value, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesIn(List<String> values) {
            addCriterion("guide_filenames in", values, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesNotIn(List<String> values) {
            addCriterion("guide_filenames not in", values, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesBetween(String value1, String value2) {
            addCriterion("guide_filenames between", value1, value2, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andGuideFilenamesNotBetween(String value1, String value2) {
            addCriterion("guide_filenames not between", value1, value2, "guideFilenames");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNull() {
            addCriterion("report_time is null");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNotNull() {
            addCriterion("report_time is not null");
            return (Criteria) this;
        }

        public Criteria andReportTimeEqualTo(Date value) {
            addCriterion("report_time =", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotEqualTo(Date value) {
            addCriterion("report_time <>", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThan(Date value) {
            addCriterion("report_time >", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("report_time >=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThan(Date value) {
            addCriterion("report_time <", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThanOrEqualTo(Date value) {
            addCriterion("report_time <=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeIn(List<Date> values) {
            addCriterion("report_time in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotIn(List<Date> values) {
            addCriterion("report_time not in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeBetween(Date value1, Date value2) {
            addCriterion("report_time between", value1, value2, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotBetween(Date value1, Date value2) {
            addCriterion("report_time not between", value1, value2, "reportTime");
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