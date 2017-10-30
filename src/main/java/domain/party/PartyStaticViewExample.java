package domain.party;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PartyStaticViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PartyStaticViewExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andBksIsNull() {
            addCriterion("bks is null");
            return (Criteria) this;
        }

        public Criteria andBksIsNotNull() {
            addCriterion("bks is not null");
            return (Criteria) this;
        }

        public Criteria andBksEqualTo(BigDecimal value) {
            addCriterion("bks =", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotEqualTo(BigDecimal value) {
            addCriterion("bks <>", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksGreaterThan(BigDecimal value) {
            addCriterion("bks >", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bks >=", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksLessThan(BigDecimal value) {
            addCriterion("bks <", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bks <=", value, "bks");
            return (Criteria) this;
        }

        public Criteria andBksIn(List<BigDecimal> values) {
            addCriterion("bks in", values, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotIn(List<BigDecimal> values) {
            addCriterion("bks not in", values, "bks");
            return (Criteria) this;
        }

        public Criteria andBksBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bks between", value1, value2, "bks");
            return (Criteria) this;
        }

        public Criteria andBksNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bks not between", value1, value2, "bks");
            return (Criteria) this;
        }

        public Criteria andSsIsNull() {
            addCriterion("ss is null");
            return (Criteria) this;
        }

        public Criteria andSsIsNotNull() {
            addCriterion("ss is not null");
            return (Criteria) this;
        }

        public Criteria andSsEqualTo(BigDecimal value) {
            addCriterion("ss =", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsNotEqualTo(BigDecimal value) {
            addCriterion("ss <>", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsGreaterThan(BigDecimal value) {
            addCriterion("ss >", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ss >=", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsLessThan(BigDecimal value) {
            addCriterion("ss <", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ss <=", value, "ss");
            return (Criteria) this;
        }

        public Criteria andSsIn(List<BigDecimal> values) {
            addCriterion("ss in", values, "ss");
            return (Criteria) this;
        }

        public Criteria andSsNotIn(List<BigDecimal> values) {
            addCriterion("ss not in", values, "ss");
            return (Criteria) this;
        }

        public Criteria andSsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ss between", value1, value2, "ss");
            return (Criteria) this;
        }

        public Criteria andSsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ss not between", value1, value2, "ss");
            return (Criteria) this;
        }

        public Criteria andBsIsNull() {
            addCriterion("bs is null");
            return (Criteria) this;
        }

        public Criteria andBsIsNotNull() {
            addCriterion("bs is not null");
            return (Criteria) this;
        }

        public Criteria andBsEqualTo(BigDecimal value) {
            addCriterion("bs =", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotEqualTo(BigDecimal value) {
            addCriterion("bs <>", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsGreaterThan(BigDecimal value) {
            addCriterion("bs >", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bs >=", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsLessThan(BigDecimal value) {
            addCriterion("bs <", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bs <=", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsIn(List<BigDecimal> values) {
            addCriterion("bs in", values, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotIn(List<BigDecimal> values) {
            addCriterion("bs not in", values, "bs");
            return (Criteria) this;
        }

        public Criteria andBsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bs between", value1, value2, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bs not between", value1, value2, "bs");
            return (Criteria) this;
        }

        public Criteria andStudentIsNull() {
            addCriterion("student is null");
            return (Criteria) this;
        }

        public Criteria andStudentIsNotNull() {
            addCriterion("student is not null");
            return (Criteria) this;
        }

        public Criteria andStudentEqualTo(BigDecimal value) {
            addCriterion("student =", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentNotEqualTo(BigDecimal value) {
            addCriterion("student <>", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentGreaterThan(BigDecimal value) {
            addCriterion("student >", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("student >=", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentLessThan(BigDecimal value) {
            addCriterion("student <", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("student <=", value, "student");
            return (Criteria) this;
        }

        public Criteria andStudentIn(List<BigDecimal> values) {
            addCriterion("student in", values, "student");
            return (Criteria) this;
        }

        public Criteria andStudentNotIn(List<BigDecimal> values) {
            addCriterion("student not in", values, "student");
            return (Criteria) this;
        }

        public Criteria andStudentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student between", value1, value2, "student");
            return (Criteria) this;
        }

        public Criteria andStudentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student not between", value1, value2, "student");
            return (Criteria) this;
        }

        public Criteria andPositiveBksIsNull() {
            addCriterion("positive_bks is null");
            return (Criteria) this;
        }

        public Criteria andPositiveBksIsNotNull() {
            addCriterion("positive_bks is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveBksEqualTo(BigDecimal value) {
            addCriterion("positive_bks =", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksNotEqualTo(BigDecimal value) {
            addCriterion("positive_bks <>", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksGreaterThan(BigDecimal value) {
            addCriterion("positive_bks >", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_bks >=", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksLessThan(BigDecimal value) {
            addCriterion("positive_bks <", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_bks <=", value, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksIn(List<BigDecimal> values) {
            addCriterion("positive_bks in", values, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksNotIn(List<BigDecimal> values) {
            addCriterion("positive_bks not in", values, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_bks between", value1, value2, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveBksNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_bks not between", value1, value2, "positiveBks");
            return (Criteria) this;
        }

        public Criteria andPositiveSsIsNull() {
            addCriterion("positive_ss is null");
            return (Criteria) this;
        }

        public Criteria andPositiveSsIsNotNull() {
            addCriterion("positive_ss is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveSsEqualTo(BigDecimal value) {
            addCriterion("positive_ss =", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsNotEqualTo(BigDecimal value) {
            addCriterion("positive_ss <>", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsGreaterThan(BigDecimal value) {
            addCriterion("positive_ss >", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_ss >=", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsLessThan(BigDecimal value) {
            addCriterion("positive_ss <", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_ss <=", value, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsIn(List<BigDecimal> values) {
            addCriterion("positive_ss in", values, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsNotIn(List<BigDecimal> values) {
            addCriterion("positive_ss not in", values, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_ss between", value1, value2, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveSsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_ss not between", value1, value2, "positiveSs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsIsNull() {
            addCriterion("positive_bs is null");
            return (Criteria) this;
        }

        public Criteria andPositiveBsIsNotNull() {
            addCriterion("positive_bs is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveBsEqualTo(BigDecimal value) {
            addCriterion("positive_bs =", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsNotEqualTo(BigDecimal value) {
            addCriterion("positive_bs <>", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsGreaterThan(BigDecimal value) {
            addCriterion("positive_bs >", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_bs >=", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsLessThan(BigDecimal value) {
            addCriterion("positive_bs <", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_bs <=", value, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsIn(List<BigDecimal> values) {
            addCriterion("positive_bs in", values, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsNotIn(List<BigDecimal> values) {
            addCriterion("positive_bs not in", values, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_bs between", value1, value2, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveBsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_bs not between", value1, value2, "positiveBs");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentIsNull() {
            addCriterion("positive_student is null");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentIsNotNull() {
            addCriterion("positive_student is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentEqualTo(BigDecimal value) {
            addCriterion("positive_student =", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentNotEqualTo(BigDecimal value) {
            addCriterion("positive_student <>", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentGreaterThan(BigDecimal value) {
            addCriterion("positive_student >", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_student >=", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentLessThan(BigDecimal value) {
            addCriterion("positive_student <", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_student <=", value, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentIn(List<BigDecimal> values) {
            addCriterion("positive_student in", values, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentNotIn(List<BigDecimal> values) {
            addCriterion("positive_student not in", values, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_student between", value1, value2, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andPositiveStudentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_student not between", value1, value2, "positiveStudent");
            return (Criteria) this;
        }

        public Criteria andTeacherIsNull() {
            addCriterion("teacher is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIsNotNull() {
            addCriterion("teacher is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherEqualTo(BigDecimal value) {
            addCriterion("teacher =", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotEqualTo(BigDecimal value) {
            addCriterion("teacher <>", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThan(BigDecimal value) {
            addCriterion("teacher >", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher >=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThan(BigDecimal value) {
            addCriterion("teacher <", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher <=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherIn(List<BigDecimal> values) {
            addCriterion("teacher in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotIn(List<BigDecimal> values) {
            addCriterion("teacher not in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher not between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireIsNull() {
            addCriterion("teacher_retire is null");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireIsNotNull() {
            addCriterion("teacher_retire is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireEqualTo(BigDecimal value) {
            addCriterion("teacher_retire =", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireNotEqualTo(BigDecimal value) {
            addCriterion("teacher_retire <>", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireGreaterThan(BigDecimal value) {
            addCriterion("teacher_retire >", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_retire >=", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireLessThan(BigDecimal value) {
            addCriterion("teacher_retire <", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_retire <=", value, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireIn(List<BigDecimal> values) {
            addCriterion("teacher_retire in", values, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireNotIn(List<BigDecimal> values) {
            addCriterion("teacher_retire not in", values, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_retire between", value1, value2, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherRetireNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_retire not between", value1, value2, "teacherRetire");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalIsNull() {
            addCriterion("teacher_total is null");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalIsNotNull() {
            addCriterion("teacher_total is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalEqualTo(BigDecimal value) {
            addCriterion("teacher_total =", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalNotEqualTo(BigDecimal value) {
            addCriterion("teacher_total <>", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalGreaterThan(BigDecimal value) {
            addCriterion("teacher_total >", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_total >=", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalLessThan(BigDecimal value) {
            addCriterion("teacher_total <", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_total <=", value, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalIn(List<BigDecimal> values) {
            addCriterion("teacher_total in", values, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalNotIn(List<BigDecimal> values) {
            addCriterion("teacher_total not in", values, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_total between", value1, value2, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_total not between", value1, value2, "teacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherIsNull() {
            addCriterion("positive_teacher is null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherIsNotNull() {
            addCriterion("positive_teacher is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherEqualTo(BigDecimal value) {
            addCriterion("positive_teacher =", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherNotEqualTo(BigDecimal value) {
            addCriterion("positive_teacher <>", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherGreaterThan(BigDecimal value) {
            addCriterion("positive_teacher >", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher >=", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherLessThan(BigDecimal value) {
            addCriterion("positive_teacher <", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher <=", value, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherIn(List<BigDecimal> values) {
            addCriterion("positive_teacher in", values, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherNotIn(List<BigDecimal> values) {
            addCriterion("positive_teacher not in", values, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher between", value1, value2, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher not between", value1, value2, "positiveTeacher");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireIsNull() {
            addCriterion("positive_teacher_retire is null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireIsNotNull() {
            addCriterion("positive_teacher_retire is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_retire =", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireNotEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_retire <>", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireGreaterThan(BigDecimal value) {
            addCriterion("positive_teacher_retire >", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_retire >=", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireLessThan(BigDecimal value) {
            addCriterion("positive_teacher_retire <", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_retire <=", value, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireIn(List<BigDecimal> values) {
            addCriterion("positive_teacher_retire in", values, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireNotIn(List<BigDecimal> values) {
            addCriterion("positive_teacher_retire not in", values, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher_retire between", value1, value2, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherRetireNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher_retire not between", value1, value2, "positiveTeacherRetire");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalIsNull() {
            addCriterion("positive_teacher_total is null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalIsNotNull() {
            addCriterion("positive_teacher_total is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_total =", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalNotEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_total <>", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalGreaterThan(BigDecimal value) {
            addCriterion("positive_teacher_total >", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_total >=", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalLessThan(BigDecimal value) {
            addCriterion("positive_teacher_total <", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_teacher_total <=", value, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalIn(List<BigDecimal> values) {
            addCriterion("positive_teacher_total in", values, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalNotIn(List<BigDecimal> values) {
            addCriterion("positive_teacher_total not in", values, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher_total between", value1, value2, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andPositiveTeacherTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_teacher_total not between", value1, value2, "positiveTeacherTotal");
            return (Criteria) this;
        }

        public Criteria andBksBranchIsNull() {
            addCriterion("bks_branch is null");
            return (Criteria) this;
        }

        public Criteria andBksBranchIsNotNull() {
            addCriterion("bks_branch is not null");
            return (Criteria) this;
        }

        public Criteria andBksBranchEqualTo(BigDecimal value) {
            addCriterion("bks_branch =", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchNotEqualTo(BigDecimal value) {
            addCriterion("bks_branch <>", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchGreaterThan(BigDecimal value) {
            addCriterion("bks_branch >", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bks_branch >=", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchLessThan(BigDecimal value) {
            addCriterion("bks_branch <", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bks_branch <=", value, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchIn(List<BigDecimal> values) {
            addCriterion("bks_branch in", values, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchNotIn(List<BigDecimal> values) {
            addCriterion("bks_branch not in", values, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bks_branch between", value1, value2, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andBksBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bks_branch not between", value1, value2, "bksBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchIsNull() {
            addCriterion("ss_branch is null");
            return (Criteria) this;
        }

        public Criteria andSsBranchIsNotNull() {
            addCriterion("ss_branch is not null");
            return (Criteria) this;
        }

        public Criteria andSsBranchEqualTo(BigDecimal value) {
            addCriterion("ss_branch =", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchNotEqualTo(BigDecimal value) {
            addCriterion("ss_branch <>", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchGreaterThan(BigDecimal value) {
            addCriterion("ss_branch >", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ss_branch >=", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchLessThan(BigDecimal value) {
            addCriterion("ss_branch <", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ss_branch <=", value, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchIn(List<BigDecimal> values) {
            addCriterion("ss_branch in", values, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchNotIn(List<BigDecimal> values) {
            addCriterion("ss_branch not in", values, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ss_branch between", value1, value2, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andSsBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ss_branch not between", value1, value2, "ssBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchIsNull() {
            addCriterion("bs_branch is null");
            return (Criteria) this;
        }

        public Criteria andBsBranchIsNotNull() {
            addCriterion("bs_branch is not null");
            return (Criteria) this;
        }

        public Criteria andBsBranchEqualTo(BigDecimal value) {
            addCriterion("bs_branch =", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchNotEqualTo(BigDecimal value) {
            addCriterion("bs_branch <>", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchGreaterThan(BigDecimal value) {
            addCriterion("bs_branch >", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bs_branch >=", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchLessThan(BigDecimal value) {
            addCriterion("bs_branch <", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bs_branch <=", value, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchIn(List<BigDecimal> values) {
            addCriterion("bs_branch in", values, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchNotIn(List<BigDecimal> values) {
            addCriterion("bs_branch not in", values, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bs_branch between", value1, value2, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andBsBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bs_branch not between", value1, value2, "bsBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchIsNull() {
            addCriterion("sb_branch is null");
            return (Criteria) this;
        }

        public Criteria andSbBranchIsNotNull() {
            addCriterion("sb_branch is not null");
            return (Criteria) this;
        }

        public Criteria andSbBranchEqualTo(BigDecimal value) {
            addCriterion("sb_branch =", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchNotEqualTo(BigDecimal value) {
            addCriterion("sb_branch <>", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchGreaterThan(BigDecimal value) {
            addCriterion("sb_branch >", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sb_branch >=", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchLessThan(BigDecimal value) {
            addCriterion("sb_branch <", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sb_branch <=", value, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchIn(List<BigDecimal> values) {
            addCriterion("sb_branch in", values, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchNotIn(List<BigDecimal> values) {
            addCriterion("sb_branch not in", values, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sb_branch between", value1, value2, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andSbBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sb_branch not between", value1, value2, "sbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchIsNull() {
            addCriterion("bsb_branch is null");
            return (Criteria) this;
        }

        public Criteria andBsbBranchIsNotNull() {
            addCriterion("bsb_branch is not null");
            return (Criteria) this;
        }

        public Criteria andBsbBranchEqualTo(BigDecimal value) {
            addCriterion("bsb_branch =", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchNotEqualTo(BigDecimal value) {
            addCriterion("bsb_branch <>", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchGreaterThan(BigDecimal value) {
            addCriterion("bsb_branch >", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bsb_branch >=", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchLessThan(BigDecimal value) {
            addCriterion("bsb_branch <", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bsb_branch <=", value, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchIn(List<BigDecimal> values) {
            addCriterion("bsb_branch in", values, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchNotIn(List<BigDecimal> values) {
            addCriterion("bsb_branch not in", values, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bsb_branch between", value1, value2, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andBsbBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bsb_branch not between", value1, value2, "bsbBranch");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalIsNull() {
            addCriterion("student_branch_total is null");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalIsNotNull() {
            addCriterion("student_branch_total is not null");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalEqualTo(BigDecimal value) {
            addCriterion("student_branch_total =", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalNotEqualTo(BigDecimal value) {
            addCriterion("student_branch_total <>", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalGreaterThan(BigDecimal value) {
            addCriterion("student_branch_total >", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("student_branch_total >=", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalLessThan(BigDecimal value) {
            addCriterion("student_branch_total <", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("student_branch_total <=", value, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalIn(List<BigDecimal> values) {
            addCriterion("student_branch_total in", values, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalNotIn(List<BigDecimal> values) {
            addCriterion("student_branch_total not in", values, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_branch_total between", value1, value2, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andStudentBranchTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_branch_total not between", value1, value2, "studentBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchIsNull() {
            addCriterion("teacher_branch is null");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchIsNotNull() {
            addCriterion("teacher_branch is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchEqualTo(BigDecimal value) {
            addCriterion("teacher_branch =", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchNotEqualTo(BigDecimal value) {
            addCriterion("teacher_branch <>", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchGreaterThan(BigDecimal value) {
            addCriterion("teacher_branch >", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_branch >=", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchLessThan(BigDecimal value) {
            addCriterion("teacher_branch <", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_branch <=", value, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchIn(List<BigDecimal> values) {
            addCriterion("teacher_branch in", values, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchNotIn(List<BigDecimal> values) {
            addCriterion("teacher_branch not in", values, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_branch between", value1, value2, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_branch not between", value1, value2, "teacherBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchIsNull() {
            addCriterion("retire_branch is null");
            return (Criteria) this;
        }

        public Criteria andRetireBranchIsNotNull() {
            addCriterion("retire_branch is not null");
            return (Criteria) this;
        }

        public Criteria andRetireBranchEqualTo(BigDecimal value) {
            addCriterion("retire_branch =", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchNotEqualTo(BigDecimal value) {
            addCriterion("retire_branch <>", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchGreaterThan(BigDecimal value) {
            addCriterion("retire_branch >", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_branch >=", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchLessThan(BigDecimal value) {
            addCriterion("retire_branch <", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchLessThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_branch <=", value, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchIn(List<BigDecimal> values) {
            addCriterion("retire_branch in", values, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchNotIn(List<BigDecimal> values) {
            addCriterion("retire_branch not in", values, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_branch between", value1, value2, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andRetireBranchNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_branch not between", value1, value2, "retireBranch");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalIsNull() {
            addCriterion("teacher_branch_total is null");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalIsNotNull() {
            addCriterion("teacher_branch_total is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalEqualTo(BigDecimal value) {
            addCriterion("teacher_branch_total =", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalNotEqualTo(BigDecimal value) {
            addCriterion("teacher_branch_total <>", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalGreaterThan(BigDecimal value) {
            addCriterion("teacher_branch_total >", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_branch_total >=", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalLessThan(BigDecimal value) {
            addCriterion("teacher_branch_total <", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_branch_total <=", value, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalIn(List<BigDecimal> values) {
            addCriterion("teacher_branch_total in", values, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalNotIn(List<BigDecimal> values) {
            addCriterion("teacher_branch_total not in", values, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_branch_total between", value1, value2, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherBranchTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_branch_total not between", value1, value2, "teacherBranchTotal");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountIsNull() {
            addCriterion("teacher_apply_count is null");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountIsNotNull() {
            addCriterion("teacher_apply_count is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountEqualTo(BigDecimal value) {
            addCriterion("teacher_apply_count =", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountNotEqualTo(BigDecimal value) {
            addCriterion("teacher_apply_count <>", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountGreaterThan(BigDecimal value) {
            addCriterion("teacher_apply_count >", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_apply_count >=", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountLessThan(BigDecimal value) {
            addCriterion("teacher_apply_count <", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_apply_count <=", value, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountIn(List<BigDecimal> values) {
            addCriterion("teacher_apply_count in", values, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountNotIn(List<BigDecimal> values) {
            addCriterion("teacher_apply_count not in", values, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_apply_count between", value1, value2, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andTeacherApplyCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_apply_count not between", value1, value2, "teacherApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountIsNull() {
            addCriterion("student_apply_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountIsNotNull() {
            addCriterion("student_apply_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountEqualTo(BigDecimal value) {
            addCriterion("student_apply_count =", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountNotEqualTo(BigDecimal value) {
            addCriterion("student_apply_count <>", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountGreaterThan(BigDecimal value) {
            addCriterion("student_apply_count >", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("student_apply_count >=", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountLessThan(BigDecimal value) {
            addCriterion("student_apply_count <", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("student_apply_count <=", value, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountIn(List<BigDecimal> values) {
            addCriterion("student_apply_count in", values, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountNotIn(List<BigDecimal> values) {
            addCriterion("student_apply_count not in", values, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_apply_count between", value1, value2, "studentApplyCount");
            return (Criteria) this;
        }

        public Criteria andStudentApplyCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_apply_count not between", value1, value2, "studentApplyCount");
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