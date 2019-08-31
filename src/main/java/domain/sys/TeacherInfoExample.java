package domain.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TeacherInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TeacherInfoExample() {
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

        public Criteria andExtPhoneIsNull() {
            addCriterion("ext_phone is null");
            return (Criteria) this;
        }

        public Criteria andExtPhoneIsNotNull() {
            addCriterion("ext_phone is not null");
            return (Criteria) this;
        }

        public Criteria andExtPhoneEqualTo(String value) {
            addCriterion("ext_phone =", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneNotEqualTo(String value) {
            addCriterion("ext_phone <>", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneGreaterThan(String value) {
            addCriterion("ext_phone >", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("ext_phone >=", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneLessThan(String value) {
            addCriterion("ext_phone <", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneLessThanOrEqualTo(String value) {
            addCriterion("ext_phone <=", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneLike(String value) {
            addCriterion("ext_phone like", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneNotLike(String value) {
            addCriterion("ext_phone not like", value, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneIn(List<String> values) {
            addCriterion("ext_phone in", values, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneNotIn(List<String> values) {
            addCriterion("ext_phone not in", values, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneBetween(String value1, String value2) {
            addCriterion("ext_phone between", value1, value2, "extPhone");
            return (Criteria) this;
        }

        public Criteria andExtPhoneNotBetween(String value1, String value2) {
            addCriterion("ext_phone not between", value1, value2, "extPhone");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIsNull() {
            addCriterion("degree_time is null");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIsNotNull() {
            addCriterion("degree_time is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeEqualTo(Date value) {
            addCriterionForJDBCDate("degree_time =", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("degree_time <>", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("degree_time >", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("degree_time >=", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeLessThan(Date value) {
            addCriterionForJDBCDate("degree_time <", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("degree_time <=", value, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeIn(List<Date> values) {
            addCriterionForJDBCDate("degree_time in", values, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("degree_time not in", values, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("degree_time between", value1, value2, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andDegreeTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("degree_time not between", value1, value2, "degreeTime");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNull() {
            addCriterion("school_type is null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNotNull() {
            addCriterion("school_type is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeEqualTo(String value) {
            addCriterion("school_type =", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotEqualTo(String value) {
            addCriterion("school_type <>", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThan(String value) {
            addCriterion("school_type >", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThanOrEqualTo(String value) {
            addCriterion("school_type >=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThan(String value) {
            addCriterion("school_type <", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThanOrEqualTo(String value) {
            addCriterion("school_type <=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLike(String value) {
            addCriterion("school_type like", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotLike(String value) {
            addCriterion("school_type not like", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIn(List<String> values) {
            addCriterion("school_type in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotIn(List<String> values) {
            addCriterion("school_type not in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeBetween(String value1, String value2) {
            addCriterion("school_type between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotBetween(String value1, String value2) {
            addCriterion("school_type not between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolIsNull() {
            addCriterion("degree_school is null");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolIsNotNull() {
            addCriterion("degree_school is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolEqualTo(String value) {
            addCriterion("degree_school =", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolNotEqualTo(String value) {
            addCriterion("degree_school <>", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolGreaterThan(String value) {
            addCriterion("degree_school >", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("degree_school >=", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolLessThan(String value) {
            addCriterion("degree_school <", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolLessThanOrEqualTo(String value) {
            addCriterion("degree_school <=", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolLike(String value) {
            addCriterion("degree_school like", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolNotLike(String value) {
            addCriterion("degree_school not like", value, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolIn(List<String> values) {
            addCriterion("degree_school in", values, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolNotIn(List<String> values) {
            addCriterion("degree_school not in", values, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolBetween(String value1, String value2) {
            addCriterion("degree_school between", value1, value2, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andDegreeSchoolNotBetween(String value1, String value2) {
            addCriterion("degree_school not between", value1, value2, "degreeSchool");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(Date value) {
            addCriterionForJDBCDate("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNull() {
            addCriterion("work_start_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNotNull() {
            addCriterion("work_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time =", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <>", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_start_time >", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time >=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_start_time <", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time not in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time not between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkBreakIsNull() {
            addCriterion("work_break is null");
            return (Criteria) this;
        }

        public Criteria andWorkBreakIsNotNull() {
            addCriterion("work_break is not null");
            return (Criteria) this;
        }

        public Criteria andWorkBreakEqualTo(String value) {
            addCriterion("work_break =", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakNotEqualTo(String value) {
            addCriterion("work_break <>", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakGreaterThan(String value) {
            addCriterion("work_break >", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakGreaterThanOrEqualTo(String value) {
            addCriterion("work_break >=", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakLessThan(String value) {
            addCriterion("work_break <", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakLessThanOrEqualTo(String value) {
            addCriterion("work_break <=", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakLike(String value) {
            addCriterion("work_break like", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakNotLike(String value) {
            addCriterion("work_break not like", value, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakIn(List<String> values) {
            addCriterion("work_break in", values, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakNotIn(List<String> values) {
            addCriterion("work_break not in", values, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakBetween(String value1, String value2) {
            addCriterion("work_break between", value1, value2, "workBreak");
            return (Criteria) this;
        }

        public Criteria andWorkBreakNotBetween(String value1, String value2) {
            addCriterion("work_break not between", value1, value2, "workBreak");
            return (Criteria) this;
        }

        public Criteria andRegularTimeIsNull() {
            addCriterion("regular_time is null");
            return (Criteria) this;
        }

        public Criteria andRegularTimeIsNotNull() {
            addCriterion("regular_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegularTimeEqualTo(Date value) {
            addCriterionForJDBCDate("regular_time =", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("regular_time <>", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("regular_time >", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("regular_time >=", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeLessThan(Date value) {
            addCriterionForJDBCDate("regular_time <", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("regular_time <=", value, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeIn(List<Date> values) {
            addCriterionForJDBCDate("regular_time in", values, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("regular_time not in", values, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("regular_time between", value1, value2, "regularTime");
            return (Criteria) this;
        }

        public Criteria andRegularTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("regular_time not between", value1, value2, "regularTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNull() {
            addCriterion("authorized_type is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNotNull() {
            addCriterion("authorized_type is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeEqualTo(String value) {
            addCriterion("authorized_type =", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotEqualTo(String value) {
            addCriterion("authorized_type <>", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThan(String value) {
            addCriterion("authorized_type >", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThanOrEqualTo(String value) {
            addCriterion("authorized_type >=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThan(String value) {
            addCriterion("authorized_type <", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThanOrEqualTo(String value) {
            addCriterion("authorized_type <=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLike(String value) {
            addCriterion("authorized_type like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotLike(String value) {
            addCriterion("authorized_type not like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIn(List<String> values) {
            addCriterion("authorized_type in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotIn(List<String> values) {
            addCriterion("authorized_type not in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeBetween(String value1, String value2) {
            addCriterion("authorized_type between", value1, value2, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotBetween(String value1, String value2) {
            addCriterion("authorized_type not between", value1, value2, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNull() {
            addCriterion("staff_type is null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNotNull() {
            addCriterion("staff_type is not null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeEqualTo(String value) {
            addCriterion("staff_type =", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotEqualTo(String value) {
            addCriterion("staff_type <>", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThan(String value) {
            addCriterion("staff_type >", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThanOrEqualTo(String value) {
            addCriterion("staff_type >=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThan(String value) {
            addCriterion("staff_type <", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThanOrEqualTo(String value) {
            addCriterion("staff_type <=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLike(String value) {
            addCriterion("staff_type like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotLike(String value) {
            addCriterion("staff_type not like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIn(List<String> values) {
            addCriterion("staff_type in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotIn(List<String> values) {
            addCriterion("staff_type not in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeBetween(String value1, String value2) {
            addCriterion("staff_type between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotBetween(String value1, String value2) {
            addCriterion("staff_type not between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIsNull() {
            addCriterion("staff_status is null");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIsNotNull() {
            addCriterion("staff_status is not null");
            return (Criteria) this;
        }

        public Criteria andStaffStatusEqualTo(String value) {
            addCriterion("staff_status =", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotEqualTo(String value) {
            addCriterion("staff_status <>", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusGreaterThan(String value) {
            addCriterion("staff_status >", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusGreaterThanOrEqualTo(String value) {
            addCriterion("staff_status >=", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLessThan(String value) {
            addCriterion("staff_status <", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLessThanOrEqualTo(String value) {
            addCriterion("staff_status <=", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusLike(String value) {
            addCriterion("staff_status like", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotLike(String value) {
            addCriterion("staff_status not like", value, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusIn(List<String> values) {
            addCriterion("staff_status in", values, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotIn(List<String> values) {
            addCriterion("staff_status not in", values, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusBetween(String value1, String value2) {
            addCriterion("staff_status between", value1, value2, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andStaffStatusNotBetween(String value1, String value2) {
            addCriterion("staff_status not between", value1, value2, "staffStatus");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNull() {
            addCriterion("sub_post_class is null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNotNull() {
            addCriterion("sub_post_class is not null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassEqualTo(String value) {
            addCriterion("sub_post_class =", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotEqualTo(String value) {
            addCriterion("sub_post_class <>", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThan(String value) {
            addCriterion("sub_post_class >", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("sub_post_class >=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThan(String value) {
            addCriterion("sub_post_class <", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThanOrEqualTo(String value) {
            addCriterion("sub_post_class <=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLike(String value) {
            addCriterion("sub_post_class like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotLike(String value) {
            addCriterion("sub_post_class not like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIn(List<String> values) {
            addCriterion("sub_post_class in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotIn(List<String> values) {
            addCriterion("sub_post_class not in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassBetween(String value1, String value2) {
            addCriterion("sub_post_class between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotBetween(String value1, String value2) {
            addCriterion("sub_post_class not between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNull() {
            addCriterion("main_post_level is null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNotNull() {
            addCriterion("main_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelEqualTo(String value) {
            addCriterion("main_post_level =", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotEqualTo(String value) {
            addCriterion("main_post_level <>", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThan(String value) {
            addCriterion("main_post_level >", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("main_post_level >=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThan(String value) {
            addCriterion("main_post_level <", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThanOrEqualTo(String value) {
            addCriterion("main_post_level <=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLike(String value) {
            addCriterion("main_post_level like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotLike(String value) {
            addCriterion("main_post_level not like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIn(List<String> values) {
            addCriterion("main_post_level in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotIn(List<String> values) {
            addCriterion("main_post_level not in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelBetween(String value1, String value2) {
            addCriterion("main_post_level between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotBetween(String value1, String value2) {
            addCriterion("main_post_level not between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andFromTypeIsNull() {
            addCriterion("from_type is null");
            return (Criteria) this;
        }

        public Criteria andFromTypeIsNotNull() {
            addCriterion("from_type is not null");
            return (Criteria) this;
        }

        public Criteria andFromTypeEqualTo(String value) {
            addCriterion("from_type =", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotEqualTo(String value) {
            addCriterion("from_type <>", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThan(String value) {
            addCriterion("from_type >", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThanOrEqualTo(String value) {
            addCriterion("from_type >=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThan(String value) {
            addCriterion("from_type <", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThanOrEqualTo(String value) {
            addCriterion("from_type <=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLike(String value) {
            addCriterion("from_type like", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotLike(String value) {
            addCriterion("from_type not like", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeIn(List<String> values) {
            addCriterion("from_type in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotIn(List<String> values) {
            addCriterion("from_type not in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeBetween(String value1, String value2) {
            addCriterion("from_type between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotBetween(String value1, String value2) {
            addCriterion("from_type not between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andOnJobIsNull() {
            addCriterion("on_job is null");
            return (Criteria) this;
        }

        public Criteria andOnJobIsNotNull() {
            addCriterion("on_job is not null");
            return (Criteria) this;
        }

        public Criteria andOnJobEqualTo(String value) {
            addCriterion("on_job =", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotEqualTo(String value) {
            addCriterion("on_job <>", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobGreaterThan(String value) {
            addCriterion("on_job >", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobGreaterThanOrEqualTo(String value) {
            addCriterion("on_job >=", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLessThan(String value) {
            addCriterion("on_job <", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLessThanOrEqualTo(String value) {
            addCriterion("on_job <=", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobLike(String value) {
            addCriterion("on_job like", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotLike(String value) {
            addCriterion("on_job not like", value, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobIn(List<String> values) {
            addCriterion("on_job in", values, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotIn(List<String> values) {
            addCriterion("on_job not in", values, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobBetween(String value1, String value2) {
            addCriterion("on_job between", value1, value2, "onJob");
            return (Criteria) this;
        }

        public Criteria andOnJobNotBetween(String value1, String value2) {
            addCriterion("on_job not between", value1, value2, "onJob");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusIsNull() {
            addCriterion("personnel_status is null");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusIsNotNull() {
            addCriterion("personnel_status is not null");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusEqualTo(String value) {
            addCriterion("personnel_status =", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusNotEqualTo(String value) {
            addCriterion("personnel_status <>", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusGreaterThan(String value) {
            addCriterion("personnel_status >", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusGreaterThanOrEqualTo(String value) {
            addCriterion("personnel_status >=", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusLessThan(String value) {
            addCriterion("personnel_status <", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusLessThanOrEqualTo(String value) {
            addCriterion("personnel_status <=", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusLike(String value) {
            addCriterion("personnel_status like", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusNotLike(String value) {
            addCriterion("personnel_status not like", value, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusIn(List<String> values) {
            addCriterion("personnel_status in", values, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusNotIn(List<String> values) {
            addCriterion("personnel_status not in", values, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusBetween(String value1, String value2) {
            addCriterion("personnel_status between", value1, value2, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andPersonnelStatusNotBetween(String value1, String value2) {
            addCriterion("personnel_status not between", value1, value2, "personnelStatus");
            return (Criteria) this;
        }

        public Criteria andProPostIsNull() {
            addCriterion("pro_post is null");
            return (Criteria) this;
        }

        public Criteria andProPostIsNotNull() {
            addCriterion("pro_post is not null");
            return (Criteria) this;
        }

        public Criteria andProPostEqualTo(String value) {
            addCriterion("pro_post =", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotEqualTo(String value) {
            addCriterion("pro_post <>", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThan(String value) {
            addCriterion("pro_post >", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post >=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThan(String value) {
            addCriterion("pro_post <", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThanOrEqualTo(String value) {
            addCriterion("pro_post <=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLike(String value) {
            addCriterion("pro_post like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotLike(String value) {
            addCriterion("pro_post not like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostIn(List<String> values) {
            addCriterion("pro_post in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotIn(List<String> values) {
            addCriterion("pro_post not in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostBetween(String value1, String value2) {
            addCriterion("pro_post between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotBetween(String value1, String value2) {
            addCriterion("pro_post not between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNull() {
            addCriterion("pro_post_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNotNull() {
            addCriterion("pro_post_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time =", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <>", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_time >", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time >=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_time <", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time not in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time not between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNull() {
            addCriterion("pro_post_level_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNotNull() {
            addCriterion("pro_post_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time =", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <>", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time not in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time not between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIsNull() {
            addCriterion("title_level is null");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIsNotNull() {
            addCriterion("title_level is not null");
            return (Criteria) this;
        }

        public Criteria andTitleLevelEqualTo(String value) {
            addCriterion("title_level =", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotEqualTo(String value) {
            addCriterion("title_level <>", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelGreaterThan(String value) {
            addCriterion("title_level >", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelGreaterThanOrEqualTo(String value) {
            addCriterion("title_level >=", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLessThan(String value) {
            addCriterion("title_level <", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLessThanOrEqualTo(String value) {
            addCriterion("title_level <=", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelLike(String value) {
            addCriterion("title_level like", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotLike(String value) {
            addCriterion("title_level not like", value, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelIn(List<String> values) {
            addCriterion("title_level in", values, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotIn(List<String> values) {
            addCriterion("title_level not in", values, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelBetween(String value1, String value2) {
            addCriterion("title_level between", value1, value2, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andTitleLevelNotBetween(String value1, String value2) {
            addCriterion("title_level not between", value1, value2, "titleLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNull() {
            addCriterion("manage_level_time is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNotNull() {
            addCriterion("manage_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time =", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <>", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("manage_level_time >", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time >=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("manage_level_time <", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time not in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time not between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNull() {
            addCriterion("office_level is null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNotNull() {
            addCriterion("office_level is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelEqualTo(String value) {
            addCriterion("office_level =", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotEqualTo(String value) {
            addCriterion("office_level <>", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThan(String value) {
            addCriterion("office_level >", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThanOrEqualTo(String value) {
            addCriterion("office_level >=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThan(String value) {
            addCriterion("office_level <", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThanOrEqualTo(String value) {
            addCriterion("office_level <=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLike(String value) {
            addCriterion("office_level like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotLike(String value) {
            addCriterion("office_level not like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIn(List<String> values) {
            addCriterion("office_level in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotIn(List<String> values) {
            addCriterion("office_level not in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelBetween(String value1, String value2) {
            addCriterion("office_level between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotBetween(String value1, String value2) {
            addCriterion("office_level not between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeIsNull() {
            addCriterion("office_level_time is null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeIsNotNull() {
            addCriterion("office_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("office_level_time =", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("office_level_time <>", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("office_level_time >", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("office_level_time >=", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("office_level_time <", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("office_level_time <=", value, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("office_level_time in", values, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("office_level_time not in", values, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("office_level_time between", value1, value2, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("office_level_time not between", value1, value2, "officeLevelTime");
            return (Criteria) this;
        }

        public Criteria andPostIsNull() {
            addCriterion("post is null");
            return (Criteria) this;
        }

        public Criteria andPostIsNotNull() {
            addCriterion("post is not null");
            return (Criteria) this;
        }

        public Criteria andPostEqualTo(String value) {
            addCriterion("post =", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotEqualTo(String value) {
            addCriterion("post <>", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThan(String value) {
            addCriterion("post >", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThanOrEqualTo(String value) {
            addCriterion("post >=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThan(String value) {
            addCriterion("post <", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThanOrEqualTo(String value) {
            addCriterion("post <=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLike(String value) {
            addCriterion("post like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotLike(String value) {
            addCriterion("post not like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostIn(List<String> values) {
            addCriterion("post in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotIn(List<String> values) {
            addCriterion("post not in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostBetween(String value1, String value2) {
            addCriterion("post between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotBetween(String value1, String value2) {
            addCriterion("post not between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostLevelIsNull() {
            addCriterion("post_level is null");
            return (Criteria) this;
        }

        public Criteria andPostLevelIsNotNull() {
            addCriterion("post_level is not null");
            return (Criteria) this;
        }

        public Criteria andPostLevelEqualTo(String value) {
            addCriterion("post_level =", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotEqualTo(String value) {
            addCriterion("post_level <>", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelGreaterThan(String value) {
            addCriterion("post_level >", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("post_level >=", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLessThan(String value) {
            addCriterion("post_level <", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLessThanOrEqualTo(String value) {
            addCriterion("post_level <=", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelLike(String value) {
            addCriterion("post_level like", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotLike(String value) {
            addCriterion("post_level not like", value, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelIn(List<String> values) {
            addCriterion("post_level in", values, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotIn(List<String> values) {
            addCriterion("post_level not in", values, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelBetween(String value1, String value2) {
            addCriterion("post_level between", value1, value2, "postLevel");
            return (Criteria) this;
        }

        public Criteria andPostLevelNotBetween(String value1, String value2) {
            addCriterion("post_level not between", value1, value2, "postLevel");
            return (Criteria) this;
        }

        public Criteria andTalentTypeIsNull() {
            addCriterion("talent_type is null");
            return (Criteria) this;
        }

        public Criteria andTalentTypeIsNotNull() {
            addCriterion("talent_type is not null");
            return (Criteria) this;
        }

        public Criteria andTalentTypeEqualTo(String value) {
            addCriterion("talent_type =", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeNotEqualTo(String value) {
            addCriterion("talent_type <>", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeGreaterThan(String value) {
            addCriterion("talent_type >", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("talent_type >=", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeLessThan(String value) {
            addCriterion("talent_type <", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeLessThanOrEqualTo(String value) {
            addCriterion("talent_type <=", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeLike(String value) {
            addCriterion("talent_type like", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeNotLike(String value) {
            addCriterion("talent_type not like", value, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeIn(List<String> values) {
            addCriterion("talent_type in", values, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeNotIn(List<String> values) {
            addCriterion("talent_type not in", values, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeBetween(String value1, String value2) {
            addCriterion("talent_type between", value1, value2, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTypeNotBetween(String value1, String value2) {
            addCriterion("talent_type not between", value1, value2, "talentType");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNull() {
            addCriterion("talent_title is null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNotNull() {
            addCriterion("talent_title is not null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleEqualTo(String value) {
            addCriterion("talent_title =", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotEqualTo(String value) {
            addCriterion("talent_title <>", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThan(String value) {
            addCriterion("talent_title >", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThanOrEqualTo(String value) {
            addCriterion("talent_title >=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThan(String value) {
            addCriterion("talent_title <", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThanOrEqualTo(String value) {
            addCriterion("talent_title <=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLike(String value) {
            addCriterion("talent_title like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotLike(String value) {
            addCriterion("talent_title not like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIn(List<String> values) {
            addCriterion("talent_title in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotIn(List<String> values) {
            addCriterion("talent_title not in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleBetween(String value1, String value2) {
            addCriterion("talent_title between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotBetween(String value1, String value2) {
            addCriterion("talent_title not between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNull() {
            addCriterion("marital_status is null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNotNull() {
            addCriterion("marital_status is not null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusEqualTo(String value) {
            addCriterion("marital_status =", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotEqualTo(String value) {
            addCriterion("marital_status <>", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThan(String value) {
            addCriterion("marital_status >", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("marital_status >=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThan(String value) {
            addCriterion("marital_status <", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThanOrEqualTo(String value) {
            addCriterion("marital_status <=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLike(String value) {
            addCriterion("marital_status like", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotLike(String value) {
            addCriterion("marital_status not like", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIn(List<String> values) {
            addCriterion("marital_status in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotIn(List<String> values) {
            addCriterion("marital_status not in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusBetween(String value1, String value2) {
            addCriterion("marital_status between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotBetween(String value1, String value2) {
            addCriterion("marital_status not between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNull() {
            addCriterion("is_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNotNull() {
            addCriterion("is_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsRetireEqualTo(Boolean value) {
            addCriterion("is_retire =", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotEqualTo(Boolean value) {
            addCriterion("is_retire <>", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThan(Boolean value) {
            addCriterion("is_retire >", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_retire >=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThan(Boolean value) {
            addCriterion("is_retire <", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_retire <=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireIn(List<Boolean> values) {
            addCriterion("is_retire in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotIn(List<Boolean> values) {
            addCriterion("is_retire not in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire not between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIsNull() {
            addCriterion("retire_time is null");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIsNotNull() {
            addCriterion("retire_time is not null");
            return (Criteria) this;
        }

        public Criteria andRetireTimeEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time =", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time <>", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("retire_time >", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time >=", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeLessThan(Date value) {
            addCriterionForJDBCDate("retire_time <", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("retire_time <=", value, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeIn(List<Date> values) {
            addCriterionForJDBCDate("retire_time in", values, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("retire_time not in", values, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("retire_time between", value1, value2, "retireTime");
            return (Criteria) this;
        }

        public Criteria andRetireTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("retire_time not between", value1, value2, "retireTime");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIsNull() {
            addCriterion("is_honor_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIsNotNull() {
            addCriterion("is_honor_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireEqualTo(Boolean value) {
            addCriterion("is_honor_retire =", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotEqualTo(Boolean value) {
            addCriterion("is_honor_retire <>", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireGreaterThan(Boolean value) {
            addCriterion("is_honor_retire >", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_honor_retire >=", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireLessThan(Boolean value) {
            addCriterion("is_honor_retire <", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_honor_retire <=", value, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireIn(List<Boolean> values) {
            addCriterion("is_honor_retire in", values, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotIn(List<Boolean> values) {
            addCriterion("is_honor_retire not in", values, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_honor_retire between", value1, value2, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsHonorRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_honor_retire not between", value1, value2, "isHonorRetire");
            return (Criteria) this;
        }

        public Criteria andIsTempIsNull() {
            addCriterion("is_temp is null");
            return (Criteria) this;
        }

        public Criteria andIsTempIsNotNull() {
            addCriterion("is_temp is not null");
            return (Criteria) this;
        }

        public Criteria andIsTempEqualTo(String value) {
            addCriterion("is_temp =", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempNotEqualTo(String value) {
            addCriterion("is_temp <>", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempGreaterThan(String value) {
            addCriterion("is_temp >", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempGreaterThanOrEqualTo(String value) {
            addCriterion("is_temp >=", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempLessThan(String value) {
            addCriterion("is_temp <", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempLessThanOrEqualTo(String value) {
            addCriterion("is_temp <=", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempLike(String value) {
            addCriterion("is_temp like", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempNotLike(String value) {
            addCriterion("is_temp not like", value, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempIn(List<String> values) {
            addCriterion("is_temp in", values, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempNotIn(List<String> values) {
            addCriterion("is_temp not in", values, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempBetween(String value1, String value2) {
            addCriterion("is_temp between", value1, value2, "isTemp");
            return (Criteria) this;
        }

        public Criteria andIsTempNotBetween(String value1, String value2) {
            addCriterion("is_temp not between", value1, value2, "isTemp");
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