package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreEduExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreEduExample() {
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

        public Criteria andSubWorkCountIsNull() {
            addCriterion("sub_work_count is null");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountIsNotNull() {
            addCriterion("sub_work_count is not null");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountEqualTo(Integer value) {
            addCriterion("sub_work_count =", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotEqualTo(Integer value) {
            addCriterion("sub_work_count <>", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountGreaterThan(Integer value) {
            addCriterion("sub_work_count >", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_work_count >=", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountLessThan(Integer value) {
            addCriterion("sub_work_count <", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountLessThanOrEqualTo(Integer value) {
            addCriterion("sub_work_count <=", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountIn(List<Integer> values) {
            addCriterion("sub_work_count in", values, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotIn(List<Integer> values) {
            addCriterion("sub_work_count not in", values, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountBetween(Integer value1, Integer value2) {
            addCriterion("sub_work_count between", value1, value2, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_work_count not between", value1, value2, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNull() {
            addCriterion("edu_id is null");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNotNull() {
            addCriterion("edu_id is not null");
            return (Criteria) this;
        }

        public Criteria andEduIdEqualTo(Integer value) {
            addCriterion("edu_id =", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotEqualTo(Integer value) {
            addCriterion("edu_id <>", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThan(Integer value) {
            addCriterion("edu_id >", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("edu_id >=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThan(Integer value) {
            addCriterion("edu_id <", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThanOrEqualTo(Integer value) {
            addCriterion("edu_id <=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdIn(List<Integer> values) {
            addCriterion("edu_id in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotIn(List<Integer> values) {
            addCriterion("edu_id not in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdBetween(Integer value1, Integer value2) {
            addCriterion("edu_id between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotBetween(Integer value1, Integer value2) {
            addCriterion("edu_id not between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedIsNull() {
            addCriterion("is_graduated is null");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedIsNotNull() {
            addCriterion("is_graduated is not null");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedEqualTo(Boolean value) {
            addCriterion("is_graduated =", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedNotEqualTo(Boolean value) {
            addCriterion("is_graduated <>", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedGreaterThan(Boolean value) {
            addCriterion("is_graduated >", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_graduated >=", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedLessThan(Boolean value) {
            addCriterion("is_graduated <", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_graduated <=", value, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedIn(List<Boolean> values) {
            addCriterion("is_graduated in", values, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedNotIn(List<Boolean> values) {
            addCriterion("is_graduated not in", values, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graduated between", value1, value2, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsGraduatedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graduated not between", value1, value2, "isGraduated");
            return (Criteria) this;
        }

        public Criteria andIsHighEduIsNull() {
            addCriterion("is_high_edu is null");
            return (Criteria) this;
        }

        public Criteria andIsHighEduIsNotNull() {
            addCriterion("is_high_edu is not null");
            return (Criteria) this;
        }

        public Criteria andIsHighEduEqualTo(Boolean value) {
            addCriterion("is_high_edu =", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduNotEqualTo(Boolean value) {
            addCriterion("is_high_edu <>", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduGreaterThan(Boolean value) {
            addCriterion("is_high_edu >", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_high_edu >=", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduLessThan(Boolean value) {
            addCriterion("is_high_edu <", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduLessThanOrEqualTo(Boolean value) {
            addCriterion("is_high_edu <=", value, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduIn(List<Boolean> values) {
            addCriterion("is_high_edu in", values, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduNotIn(List<Boolean> values) {
            addCriterion("is_high_edu not in", values, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduBetween(Boolean value1, Boolean value2) {
            addCriterion("is_high_edu between", value1, value2, "isHighEdu");
            return (Criteria) this;
        }

        public Criteria andIsHighEduNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_high_edu not between", value1, value2, "isHighEdu");
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

        public Criteria andDepIsNull() {
            addCriterion("dep is null");
            return (Criteria) this;
        }

        public Criteria andDepIsNotNull() {
            addCriterion("dep is not null");
            return (Criteria) this;
        }

        public Criteria andDepEqualTo(String value) {
            addCriterion("dep =", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotEqualTo(String value) {
            addCriterion("dep <>", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThan(String value) {
            addCriterion("dep >", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThanOrEqualTo(String value) {
            addCriterion("dep >=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThan(String value) {
            addCriterion("dep <", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThanOrEqualTo(String value) {
            addCriterion("dep <=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLike(String value) {
            addCriterion("dep like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotLike(String value) {
            addCriterion("dep not like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepIn(List<String> values) {
            addCriterion("dep in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotIn(List<String> values) {
            addCriterion("dep not in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepBetween(String value1, String value2) {
            addCriterion("dep between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotBetween(String value1, String value2) {
            addCriterion("dep not between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(Integer value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(Integer value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(Integer value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(Integer value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(Integer value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(Integer value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<Integer> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<Integer> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(Integer value1, Integer value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(Integer value1, Integer value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectIsNull() {
            addCriterion("first_subject is null");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectIsNotNull() {
            addCriterion("first_subject is not null");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectEqualTo(Integer value) {
            addCriterion("first_subject =", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectNotEqualTo(Integer value) {
            addCriterion("first_subject <>", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectGreaterThan(Integer value) {
            addCriterion("first_subject >", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_subject >=", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectLessThan(Integer value) {
            addCriterion("first_subject <", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectLessThanOrEqualTo(Integer value) {
            addCriterion("first_subject <=", value, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectIn(List<Integer> values) {
            addCriterion("first_subject in", values, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectNotIn(List<Integer> values) {
            addCriterion("first_subject not in", values, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectBetween(Integer value1, Integer value2) {
            addCriterion("first_subject between", value1, value2, "firstSubject");
            return (Criteria) this;
        }

        public Criteria andFirstSubjectNotBetween(Integer value1, Integer value2) {
            addCriterion("first_subject not between", value1, value2, "firstSubject");
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

        public Criteria andSchoolTypeIsNull() {
            addCriterion("school_type is null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNotNull() {
            addCriterion("school_type is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeEqualTo(Byte value) {
            addCriterion("school_type =", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotEqualTo(Byte value) {
            addCriterion("school_type <>", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThan(Byte value) {
            addCriterion("school_type >", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("school_type >=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThan(Byte value) {
            addCriterion("school_type <", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThanOrEqualTo(Byte value) {
            addCriterion("school_type <=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIn(List<Byte> values) {
            addCriterion("school_type in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotIn(List<Byte> values) {
            addCriterion("school_type not in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeBetween(Byte value1, Byte value2) {
            addCriterion("school_type between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("school_type not between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIsNull() {
            addCriterion("enrol_time is null");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIsNotNull() {
            addCriterion("enrol_time is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeEqualTo(Date value) {
            addCriterionForJDBCDate("enrol_time =", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("enrol_time <>", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("enrol_time >", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enrol_time >=", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeLessThan(Date value) {
            addCriterionForJDBCDate("enrol_time <", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enrol_time <=", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIn(List<Date> values) {
            addCriterionForJDBCDate("enrol_time in", values, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("enrol_time not in", values, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enrol_time between", value1, value2, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enrol_time not between", value1, value2, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterionForJDBCDate("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNull() {
            addCriterion("learn_style is null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNotNull() {
            addCriterion("learn_style is not null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleEqualTo(Integer value) {
            addCriterion("learn_style =", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotEqualTo(Integer value) {
            addCriterion("learn_style <>", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThan(Integer value) {
            addCriterion("learn_style >", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("learn_style >=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThan(Integer value) {
            addCriterion("learn_style <", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThanOrEqualTo(Integer value) {
            addCriterion("learn_style <=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIn(List<Integer> values) {
            addCriterion("learn_style in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotIn(List<Integer> values) {
            addCriterion("learn_style not in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleBetween(Integer value1, Integer value2) {
            addCriterion("learn_style between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("learn_style not between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andHasDegreeIsNull() {
            addCriterion("has_degree is null");
            return (Criteria) this;
        }

        public Criteria andHasDegreeIsNotNull() {
            addCriterion("has_degree is not null");
            return (Criteria) this;
        }

        public Criteria andHasDegreeEqualTo(Boolean value) {
            addCriterion("has_degree =", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeNotEqualTo(Boolean value) {
            addCriterion("has_degree <>", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeGreaterThan(Boolean value) {
            addCriterion("has_degree >", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_degree >=", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeLessThan(Boolean value) {
            addCriterion("has_degree <", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeLessThanOrEqualTo(Boolean value) {
            addCriterion("has_degree <=", value, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeIn(List<Boolean> values) {
            addCriterion("has_degree in", values, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeNotIn(List<Boolean> values) {
            addCriterion("has_degree not in", values, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeBetween(Boolean value1, Boolean value2) {
            addCriterion("has_degree between", value1, value2, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andHasDegreeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_degree not between", value1, value2, "hasDegree");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeIsNull() {
            addCriterion("degree_type is null");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeIsNotNull() {
            addCriterion("degree_type is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeEqualTo(Byte value) {
            addCriterion("degree_type =", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeNotEqualTo(Byte value) {
            addCriterion("degree_type <>", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeGreaterThan(Byte value) {
            addCriterion("degree_type >", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("degree_type >=", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeLessThan(Byte value) {
            addCriterion("degree_type <", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("degree_type <=", value, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeIn(List<Byte> values) {
            addCriterion("degree_type in", values, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeNotIn(List<Byte> values) {
            addCriterion("degree_type not in", values, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeBetween(Byte value1, Byte value2) {
            addCriterion("degree_type between", value1, value2, "degreeType");
            return (Criteria) this;
        }

        public Criteria andDegreeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("degree_type not between", value1, value2, "degreeType");
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

        public Criteria andIsHighDegreeIsNull() {
            addCriterion("is_high_degree is null");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeIsNotNull() {
            addCriterion("is_high_degree is not null");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeEqualTo(Boolean value) {
            addCriterion("is_high_degree =", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeNotEqualTo(Boolean value) {
            addCriterion("is_high_degree <>", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeGreaterThan(Boolean value) {
            addCriterion("is_high_degree >", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_high_degree >=", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeLessThan(Boolean value) {
            addCriterion("is_high_degree <", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_high_degree <=", value, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeIn(List<Boolean> values) {
            addCriterion("is_high_degree in", values, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeNotIn(List<Boolean> values) {
            addCriterion("is_high_degree not in", values, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_high_degree between", value1, value2, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsHighDegreeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_high_degree not between", value1, value2, "isHighDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeIsNull() {
            addCriterion("is_second_degree is null");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeIsNotNull() {
            addCriterion("is_second_degree is not null");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeEqualTo(Boolean value) {
            addCriterion("is_second_degree =", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeNotEqualTo(Boolean value) {
            addCriterion("is_second_degree <>", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeGreaterThan(Boolean value) {
            addCriterion("is_second_degree >", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_second_degree >=", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeLessThan(Boolean value) {
            addCriterion("is_second_degree <", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_second_degree <=", value, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeIn(List<Boolean> values) {
            addCriterion("is_second_degree in", values, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeNotIn(List<Boolean> values) {
            addCriterion("is_second_degree not in", values, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_second_degree between", value1, value2, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andIsSecondDegreeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_second_degree not between", value1, value2, "isSecondDegree");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryIsNull() {
            addCriterion("degree_country is null");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryIsNotNull() {
            addCriterion("degree_country is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryEqualTo(String value) {
            addCriterion("degree_country =", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryNotEqualTo(String value) {
            addCriterion("degree_country <>", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryGreaterThan(String value) {
            addCriterion("degree_country >", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryGreaterThanOrEqualTo(String value) {
            addCriterion("degree_country >=", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryLessThan(String value) {
            addCriterion("degree_country <", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryLessThanOrEqualTo(String value) {
            addCriterion("degree_country <=", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryLike(String value) {
            addCriterion("degree_country like", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryNotLike(String value) {
            addCriterion("degree_country not like", value, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryIn(List<String> values) {
            addCriterion("degree_country in", values, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryNotIn(List<String> values) {
            addCriterion("degree_country not in", values, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryBetween(String value1, String value2) {
            addCriterion("degree_country between", value1, value2, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeCountryNotBetween(String value1, String value2) {
            addCriterion("degree_country not between", value1, value2, "degreeCountry");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitIsNull() {
            addCriterion("degree_unit is null");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitIsNotNull() {
            addCriterion("degree_unit is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitEqualTo(String value) {
            addCriterion("degree_unit =", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitNotEqualTo(String value) {
            addCriterion("degree_unit <>", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitGreaterThan(String value) {
            addCriterion("degree_unit >", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitGreaterThanOrEqualTo(String value) {
            addCriterion("degree_unit >=", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitLessThan(String value) {
            addCriterion("degree_unit <", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitLessThanOrEqualTo(String value) {
            addCriterion("degree_unit <=", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitLike(String value) {
            addCriterion("degree_unit like", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitNotLike(String value) {
            addCriterion("degree_unit not like", value, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitIn(List<String> values) {
            addCriterion("degree_unit in", values, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitNotIn(List<String> values) {
            addCriterion("degree_unit not in", values, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitBetween(String value1, String value2) {
            addCriterion("degree_unit between", value1, value2, "degreeUnit");
            return (Criteria) this;
        }

        public Criteria andDegreeUnitNotBetween(String value1, String value2) {
            addCriterion("degree_unit not between", value1, value2, "degreeUnit");
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

        public Criteria andTutorNameIsNull() {
            addCriterion("tutor_name is null");
            return (Criteria) this;
        }

        public Criteria andTutorNameIsNotNull() {
            addCriterion("tutor_name is not null");
            return (Criteria) this;
        }

        public Criteria andTutorNameEqualTo(String value) {
            addCriterion("tutor_name =", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameNotEqualTo(String value) {
            addCriterion("tutor_name <>", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameGreaterThan(String value) {
            addCriterion("tutor_name >", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameGreaterThanOrEqualTo(String value) {
            addCriterion("tutor_name >=", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameLessThan(String value) {
            addCriterion("tutor_name <", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameLessThanOrEqualTo(String value) {
            addCriterion("tutor_name <=", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameLike(String value) {
            addCriterion("tutor_name like", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameNotLike(String value) {
            addCriterion("tutor_name not like", value, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameIn(List<String> values) {
            addCriterion("tutor_name in", values, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameNotIn(List<String> values) {
            addCriterion("tutor_name not in", values, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameBetween(String value1, String value2) {
            addCriterion("tutor_name between", value1, value2, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorNameNotBetween(String value1, String value2) {
            addCriterion("tutor_name not between", value1, value2, "tutorName");
            return (Criteria) this;
        }

        public Criteria andTutorTitleIsNull() {
            addCriterion("tutor_title is null");
            return (Criteria) this;
        }

        public Criteria andTutorTitleIsNotNull() {
            addCriterion("tutor_title is not null");
            return (Criteria) this;
        }

        public Criteria andTutorTitleEqualTo(String value) {
            addCriterion("tutor_title =", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleNotEqualTo(String value) {
            addCriterion("tutor_title <>", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleGreaterThan(String value) {
            addCriterion("tutor_title >", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleGreaterThanOrEqualTo(String value) {
            addCriterion("tutor_title >=", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleLessThan(String value) {
            addCriterion("tutor_title <", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleLessThanOrEqualTo(String value) {
            addCriterion("tutor_title <=", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleLike(String value) {
            addCriterion("tutor_title like", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleNotLike(String value) {
            addCriterion("tutor_title not like", value, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleIn(List<String> values) {
            addCriterion("tutor_title in", values, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleNotIn(List<String> values) {
            addCriterion("tutor_title not in", values, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleBetween(String value1, String value2) {
            addCriterion("tutor_title between", value1, value2, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andTutorTitleNotBetween(String value1, String value2) {
            addCriterion("tutor_title not between", value1, value2, "tutorTitle");
            return (Criteria) this;
        }

        public Criteria andCertificateIsNull() {
            addCriterion("certificate is null");
            return (Criteria) this;
        }

        public Criteria andCertificateIsNotNull() {
            addCriterion("certificate is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateEqualTo(String value) {
            addCriterion("certificate =", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotEqualTo(String value) {
            addCriterion("certificate <>", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateGreaterThan(String value) {
            addCriterion("certificate >", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateGreaterThanOrEqualTo(String value) {
            addCriterion("certificate >=", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLessThan(String value) {
            addCriterion("certificate <", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLessThanOrEqualTo(String value) {
            addCriterion("certificate <=", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLike(String value) {
            addCriterion("certificate like", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotLike(String value) {
            addCriterion("certificate not like", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateIn(List<String> values) {
            addCriterion("certificate in", values, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotIn(List<String> values) {
            addCriterion("certificate not in", values, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateBetween(String value1, String value2) {
            addCriterion("certificate between", value1, value2, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotBetween(String value1, String value2) {
            addCriterion("certificate not between", value1, value2, "certificate");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeIsNull() {
            addCriterion("note_brackets_exclude is null");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeIsNotNull() {
            addCriterion("note_brackets_exclude is not null");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeEqualTo(Boolean value) {
            addCriterion("note_brackets_exclude =", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeNotEqualTo(Boolean value) {
            addCriterion("note_brackets_exclude <>", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeGreaterThan(Boolean value) {
            addCriterion("note_brackets_exclude >", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("note_brackets_exclude >=", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeLessThan(Boolean value) {
            addCriterion("note_brackets_exclude <", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeLessThanOrEqualTo(Boolean value) {
            addCriterion("note_brackets_exclude <=", value, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeIn(List<Boolean> values) {
            addCriterion("note_brackets_exclude in", values, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeNotIn(List<Boolean> values) {
            addCriterion("note_brackets_exclude not in", values, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeBetween(Boolean value1, Boolean value2) {
            addCriterion("note_brackets_exclude between", value1, value2, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andNoteBracketsExcludeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("note_brackets_exclude not between", value1, value2, "noteBracketsExclude");
            return (Criteria) this;
        }

        public Criteria andResumeIsNull() {
            addCriterion("resume is null");
            return (Criteria) this;
        }

        public Criteria andResumeIsNotNull() {
            addCriterion("resume is not null");
            return (Criteria) this;
        }

        public Criteria andResumeEqualTo(String value) {
            addCriterion("resume =", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeNotEqualTo(String value) {
            addCriterion("resume <>", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeGreaterThan(String value) {
            addCriterion("resume >", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeGreaterThanOrEqualTo(String value) {
            addCriterion("resume >=", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeLessThan(String value) {
            addCriterion("resume <", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeLessThanOrEqualTo(String value) {
            addCriterion("resume <=", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeLike(String value) {
            addCriterion("resume like", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeNotLike(String value) {
            addCriterion("resume not like", value, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeIn(List<String> values) {
            addCriterion("resume in", values, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeNotIn(List<String> values) {
            addCriterion("resume not in", values, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeBetween(String value1, String value2) {
            addCriterion("resume between", value1, value2, "resume");
            return (Criteria) this;
        }

        public Criteria andResumeNotBetween(String value1, String value2) {
            addCriterion("resume not between", value1, value2, "resume");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeIsNull() {
            addCriterion("adform_edu_exclude is null");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeIsNotNull() {
            addCriterion("adform_edu_exclude is not null");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeEqualTo(Boolean value) {
            addCriterion("adform_edu_exclude =", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeNotEqualTo(Boolean value) {
            addCriterion("adform_edu_exclude <>", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeGreaterThan(Boolean value) {
            addCriterion("adform_edu_exclude >", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("adform_edu_exclude >=", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeLessThan(Boolean value) {
            addCriterion("adform_edu_exclude <", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeLessThanOrEqualTo(Boolean value) {
            addCriterion("adform_edu_exclude <=", value, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeIn(List<Boolean> values) {
            addCriterion("adform_edu_exclude in", values, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeNotIn(List<Boolean> values) {
            addCriterion("adform_edu_exclude not in", values, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_edu_exclude between", value1, value2, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformEduExcludeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_edu_exclude not between", value1, value2, "adformEduExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeIsNull() {
            addCriterion("adform_resume_exclude is null");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeIsNotNull() {
            addCriterion("adform_resume_exclude is not null");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeEqualTo(Boolean value) {
            addCriterion("adform_resume_exclude =", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeNotEqualTo(Boolean value) {
            addCriterion("adform_resume_exclude <>", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeGreaterThan(Boolean value) {
            addCriterion("adform_resume_exclude >", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("adform_resume_exclude >=", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeLessThan(Boolean value) {
            addCriterion("adform_resume_exclude <", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeLessThanOrEqualTo(Boolean value) {
            addCriterion("adform_resume_exclude <=", value, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeIn(List<Boolean> values) {
            addCriterion("adform_resume_exclude in", values, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeNotIn(List<Boolean> values) {
            addCriterion("adform_resume_exclude not in", values, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_resume_exclude between", value1, value2, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformResumeExcludeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_resume_exclude not between", value1, value2, "adformResumeExclude");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeIsNull() {
            addCriterion("adform_display_as_fulltime is null");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeIsNotNull() {
            addCriterion("adform_display_as_fulltime is not null");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeEqualTo(Boolean value) {
            addCriterion("adform_display_as_fulltime =", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeNotEqualTo(Boolean value) {
            addCriterion("adform_display_as_fulltime <>", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeGreaterThan(Boolean value) {
            addCriterion("adform_display_as_fulltime >", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("adform_display_as_fulltime >=", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeLessThan(Boolean value) {
            addCriterion("adform_display_as_fulltime <", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeLessThanOrEqualTo(Boolean value) {
            addCriterion("adform_display_as_fulltime <=", value, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeIn(List<Boolean> values) {
            addCriterion("adform_display_as_fulltime in", values, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeNotIn(List<Boolean> values) {
            addCriterion("adform_display_as_fulltime not in", values, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_display_as_fulltime between", value1, value2, "adformDisplayAsFulltime");
            return (Criteria) this;
        }

        public Criteria andAdformDisplayAsFulltimeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("adform_display_as_fulltime not between", value1, value2, "adformDisplayAsFulltime");
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

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
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