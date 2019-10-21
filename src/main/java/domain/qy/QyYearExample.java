package domain.qy;

import java.util.ArrayList;
import java.util.List;

public class QyYearExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QyYearExample() {
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

        public Criteria andPlanPdfIsNull() {
            addCriterion("plan_pdf is null");
            return (Criteria) this;
        }

        public Criteria andPlanPdfIsNotNull() {
            addCriterion("plan_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andPlanPdfEqualTo(String value) {
            addCriterion("plan_pdf =", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNotEqualTo(String value) {
            addCriterion("plan_pdf <>", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfGreaterThan(String value) {
            addCriterion("plan_pdf >", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfGreaterThanOrEqualTo(String value) {
            addCriterion("plan_pdf >=", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfLessThan(String value) {
            addCriterion("plan_pdf <", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfLessThanOrEqualTo(String value) {
            addCriterion("plan_pdf <=", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfLike(String value) {
            addCriterion("plan_pdf like", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNotLike(String value) {
            addCriterion("plan_pdf not like", value, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfIn(List<String> values) {
            addCriterion("plan_pdf in", values, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNotIn(List<String> values) {
            addCriterion("plan_pdf not in", values, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfBetween(String value1, String value2) {
            addCriterion("plan_pdf between", value1, value2, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNotBetween(String value1, String value2) {
            addCriterion("plan_pdf not between", value1, value2, "planPdf");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameIsNull() {
            addCriterion("plan_pdf_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameIsNotNull() {
            addCriterion("plan_pdf_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameEqualTo(String value) {
            addCriterion("plan_pdf_name =", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameNotEqualTo(String value) {
            addCriterion("plan_pdf_name <>", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameGreaterThan(String value) {
            addCriterion("plan_pdf_name >", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_pdf_name >=", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameLessThan(String value) {
            addCriterion("plan_pdf_name <", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameLessThanOrEqualTo(String value) {
            addCriterion("plan_pdf_name <=", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameLike(String value) {
            addCriterion("plan_pdf_name like", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameNotLike(String value) {
            addCriterion("plan_pdf_name not like", value, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameIn(List<String> values) {
            addCriterion("plan_pdf_name in", values, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameNotIn(List<String> values) {
            addCriterion("plan_pdf_name not in", values, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameBetween(String value1, String value2) {
            addCriterion("plan_pdf_name between", value1, value2, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanPdfNameNotBetween(String value1, String value2) {
            addCriterion("plan_pdf_name not between", value1, value2, "planPdfName");
            return (Criteria) this;
        }

        public Criteria andPlanWordIsNull() {
            addCriterion("plan_word is null");
            return (Criteria) this;
        }

        public Criteria andPlanWordIsNotNull() {
            addCriterion("plan_word is not null");
            return (Criteria) this;
        }

        public Criteria andPlanWordEqualTo(String value) {
            addCriterion("plan_word =", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordNotEqualTo(String value) {
            addCriterion("plan_word <>", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordGreaterThan(String value) {
            addCriterion("plan_word >", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordGreaterThanOrEqualTo(String value) {
            addCriterion("plan_word >=", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordLessThan(String value) {
            addCriterion("plan_word <", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordLessThanOrEqualTo(String value) {
            addCriterion("plan_word <=", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordLike(String value) {
            addCriterion("plan_word like", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordNotLike(String value) {
            addCriterion("plan_word not like", value, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordIn(List<String> values) {
            addCriterion("plan_word in", values, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordNotIn(List<String> values) {
            addCriterion("plan_word not in", values, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordBetween(String value1, String value2) {
            addCriterion("plan_word between", value1, value2, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordNotBetween(String value1, String value2) {
            addCriterion("plan_word not between", value1, value2, "planWord");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameIsNull() {
            addCriterion("plan_word_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameIsNotNull() {
            addCriterion("plan_word_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameEqualTo(String value) {
            addCriterion("plan_word_name =", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameNotEqualTo(String value) {
            addCriterion("plan_word_name <>", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameGreaterThan(String value) {
            addCriterion("plan_word_name >", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_word_name >=", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameLessThan(String value) {
            addCriterion("plan_word_name <", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameLessThanOrEqualTo(String value) {
            addCriterion("plan_word_name <=", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameLike(String value) {
            addCriterion("plan_word_name like", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameNotLike(String value) {
            addCriterion("plan_word_name not like", value, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameIn(List<String> values) {
            addCriterion("plan_word_name in", values, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameNotIn(List<String> values) {
            addCriterion("plan_word_name not in", values, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameBetween(String value1, String value2) {
            addCriterion("plan_word_name between", value1, value2, "planWordName");
            return (Criteria) this;
        }

        public Criteria andPlanWordNameNotBetween(String value1, String value2) {
            addCriterion("plan_word_name not between", value1, value2, "planWordName");
            return (Criteria) this;
        }

        public Criteria andResultPdfIsNull() {
            addCriterion("result_pdf is null");
            return (Criteria) this;
        }

        public Criteria andResultPdfIsNotNull() {
            addCriterion("result_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andResultPdfEqualTo(String value) {
            addCriterion("result_pdf =", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfNotEqualTo(String value) {
            addCriterion("result_pdf <>", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfGreaterThan(String value) {
            addCriterion("result_pdf >", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfGreaterThanOrEqualTo(String value) {
            addCriterion("result_pdf >=", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfLessThan(String value) {
            addCriterion("result_pdf <", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfLessThanOrEqualTo(String value) {
            addCriterion("result_pdf <=", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfLike(String value) {
            addCriterion("result_pdf like", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfNotLike(String value) {
            addCriterion("result_pdf not like", value, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfIn(List<String> values) {
            addCriterion("result_pdf in", values, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfNotIn(List<String> values) {
            addCriterion("result_pdf not in", values, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfBetween(String value1, String value2) {
            addCriterion("result_pdf between", value1, value2, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfNotBetween(String value1, String value2) {
            addCriterion("result_pdf not between", value1, value2, "resultPdf");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameIsNull() {
            addCriterion("result_pdf_name is null");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameIsNotNull() {
            addCriterion("result_pdf_name is not null");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameEqualTo(String value) {
            addCriterion("result_pdf_name =", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameNotEqualTo(String value) {
            addCriterion("result_pdf_name <>", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameGreaterThan(String value) {
            addCriterion("result_pdf_name >", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameGreaterThanOrEqualTo(String value) {
            addCriterion("result_pdf_name >=", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameLessThan(String value) {
            addCriterion("result_pdf_name <", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameLessThanOrEqualTo(String value) {
            addCriterion("result_pdf_name <=", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameLike(String value) {
            addCriterion("result_pdf_name like", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameNotLike(String value) {
            addCriterion("result_pdf_name not like", value, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameIn(List<String> values) {
            addCriterion("result_pdf_name in", values, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameNotIn(List<String> values) {
            addCriterion("result_pdf_name not in", values, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameBetween(String value1, String value2) {
            addCriterion("result_pdf_name between", value1, value2, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultPdfNameNotBetween(String value1, String value2) {
            addCriterion("result_pdf_name not between", value1, value2, "resultPdfName");
            return (Criteria) this;
        }

        public Criteria andResultWordIsNull() {
            addCriterion("result_word is null");
            return (Criteria) this;
        }

        public Criteria andResultWordIsNotNull() {
            addCriterion("result_word is not null");
            return (Criteria) this;
        }

        public Criteria andResultWordEqualTo(String value) {
            addCriterion("result_word =", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordNotEqualTo(String value) {
            addCriterion("result_word <>", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordGreaterThan(String value) {
            addCriterion("result_word >", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordGreaterThanOrEqualTo(String value) {
            addCriterion("result_word >=", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordLessThan(String value) {
            addCriterion("result_word <", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordLessThanOrEqualTo(String value) {
            addCriterion("result_word <=", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordLike(String value) {
            addCriterion("result_word like", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordNotLike(String value) {
            addCriterion("result_word not like", value, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordIn(List<String> values) {
            addCriterion("result_word in", values, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordNotIn(List<String> values) {
            addCriterion("result_word not in", values, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordBetween(String value1, String value2) {
            addCriterion("result_word between", value1, value2, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordNotBetween(String value1, String value2) {
            addCriterion("result_word not between", value1, value2, "resultWord");
            return (Criteria) this;
        }

        public Criteria andResultWordNameIsNull() {
            addCriterion("result_word_name is null");
            return (Criteria) this;
        }

        public Criteria andResultWordNameIsNotNull() {
            addCriterion("result_word_name is not null");
            return (Criteria) this;
        }

        public Criteria andResultWordNameEqualTo(String value) {
            addCriterion("result_word_name =", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameNotEqualTo(String value) {
            addCriterion("result_word_name <>", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameGreaterThan(String value) {
            addCriterion("result_word_name >", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameGreaterThanOrEqualTo(String value) {
            addCriterion("result_word_name >=", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameLessThan(String value) {
            addCriterion("result_word_name <", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameLessThanOrEqualTo(String value) {
            addCriterion("result_word_name <=", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameLike(String value) {
            addCriterion("result_word_name like", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameNotLike(String value) {
            addCriterion("result_word_name not like", value, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameIn(List<String> values) {
            addCriterion("result_word_name in", values, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameNotIn(List<String> values) {
            addCriterion("result_word_name not in", values, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameBetween(String value1, String value2) {
            addCriterion("result_word_name between", value1, value2, "resultWordName");
            return (Criteria) this;
        }

        public Criteria andResultWordNameNotBetween(String value1, String value2) {
            addCriterion("result_word_name not between", value1, value2, "resultWordName");
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