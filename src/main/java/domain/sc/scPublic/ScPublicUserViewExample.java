package domain.sc.scPublic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScPublicUserViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScPublicUserViewExample() {
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

        public Criteria andPublicIdIsNull() {
            addCriterion("public_id is null");
            return (Criteria) this;
        }

        public Criteria andPublicIdIsNotNull() {
            addCriterion("public_id is not null");
            return (Criteria) this;
        }

        public Criteria andPublicIdEqualTo(Integer value) {
            addCriterion("public_id =", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdNotEqualTo(Integer value) {
            addCriterion("public_id <>", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdGreaterThan(Integer value) {
            addCriterion("public_id >", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("public_id >=", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdLessThan(Integer value) {
            addCriterion("public_id <", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdLessThanOrEqualTo(Integer value) {
            addCriterion("public_id <=", value, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdIn(List<Integer> values) {
            addCriterion("public_id in", values, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdNotIn(List<Integer> values) {
            addCriterion("public_id not in", values, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdBetween(Integer value1, Integer value2) {
            addCriterion("public_id between", value1, value2, "publicId");
            return (Criteria) this;
        }

        public Criteria andPublicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("public_id not between", value1, value2, "publicId");
            return (Criteria) this;
        }

        public Criteria andVoteIdIsNull() {
            addCriterion("vote_id is null");
            return (Criteria) this;
        }

        public Criteria andVoteIdIsNotNull() {
            addCriterion("vote_id is not null");
            return (Criteria) this;
        }

        public Criteria andVoteIdEqualTo(Integer value) {
            addCriterion("vote_id =", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotEqualTo(Integer value) {
            addCriterion("vote_id <>", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdGreaterThan(Integer value) {
            addCriterion("vote_id >", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_id >=", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdLessThan(Integer value) {
            addCriterion("vote_id <", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdLessThanOrEqualTo(Integer value) {
            addCriterion("vote_id <=", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdIn(List<Integer> values) {
            addCriterion("vote_id in", values, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotIn(List<Integer> values) {
            addCriterion("vote_id not in", values, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdBetween(Integer value1, Integer value2) {
            addCriterion("vote_id between", value1, value2, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_id not between", value1, value2, "voteId");
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

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(Integer value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(Integer value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(Integer value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(Integer value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<Integer> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<Integer> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
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

        public Criteria andCommitteeIdIsNull() {
            addCriterion("committee_id is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdIsNotNull() {
            addCriterion("committee_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdEqualTo(Integer value) {
            addCriterion("committee_id =", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotEqualTo(Integer value) {
            addCriterion("committee_id <>", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdGreaterThan(Integer value) {
            addCriterion("committee_id >", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("committee_id >=", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdLessThan(Integer value) {
            addCriterion("committee_id <", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdLessThanOrEqualTo(Integer value) {
            addCriterion("committee_id <=", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdIn(List<Integer> values) {
            addCriterion("committee_id in", values, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotIn(List<Integer> values) {
            addCriterion("committee_id not in", values, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdBetween(Integer value1, Integer value2) {
            addCriterion("committee_id between", value1, value2, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("committee_id not between", value1, value2, "committeeId");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublishDateEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date =", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <>", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThan(Date value) {
            addCriterionForJDBCDate("publish_date >", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date >=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThan(Date value) {
            addCriterionForJDBCDate("publish_date <", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date not in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date not between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateIsNull() {
            addCriterion("public_start_date is null");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateIsNotNull() {
            addCriterion("public_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("public_start_date =", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("public_start_date <>", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("public_start_date >", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_start_date >=", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateLessThan(Date value) {
            addCriterionForJDBCDate("public_start_date <", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_start_date <=", value, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("public_start_date in", values, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("public_start_date not in", values, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_start_date between", value1, value2, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_start_date not between", value1, value2, "publicStartDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateIsNull() {
            addCriterion("public_end_date is null");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateIsNotNull() {
            addCriterion("public_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("public_end_date =", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("public_end_date <>", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("public_end_date >", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_end_date >=", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateLessThan(Date value) {
            addCriterionForJDBCDate("public_end_date <", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_end_date <=", value, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("public_end_date in", values, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("public_end_date not in", values, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_end_date between", value1, value2, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPublicEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_end_date not between", value1, value2, "publicEndDate");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathIsNull() {
            addCriterion("pdf_file_path is null");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathIsNotNull() {
            addCriterion("pdf_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathEqualTo(String value) {
            addCriterion("pdf_file_path =", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathNotEqualTo(String value) {
            addCriterion("pdf_file_path <>", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathGreaterThan(String value) {
            addCriterion("pdf_file_path >", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("pdf_file_path >=", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathLessThan(String value) {
            addCriterion("pdf_file_path <", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathLessThanOrEqualTo(String value) {
            addCriterion("pdf_file_path <=", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathLike(String value) {
            addCriterion("pdf_file_path like", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathNotLike(String value) {
            addCriterion("pdf_file_path not like", value, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathIn(List<String> values) {
            addCriterion("pdf_file_path in", values, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathNotIn(List<String> values) {
            addCriterion("pdf_file_path not in", values, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathBetween(String value1, String value2) {
            addCriterion("pdf_file_path between", value1, value2, "pdfFilePath");
            return (Criteria) this;
        }

        public Criteria andPdfFilePathNotBetween(String value1, String value2) {
            addCriterion("pdf_file_path not between", value1, value2, "pdfFilePath");
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

        public Criteria andRecordUserIdIsNull() {
            addCriterion("record_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNotNull() {
            addCriterion("record_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdEqualTo(Integer value) {
            addCriterion("record_user_id =", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotEqualTo(Integer value) {
            addCriterion("record_user_id <>", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThan(Integer value) {
            addCriterion("record_user_id >", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_user_id >=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThan(Integer value) {
            addCriterion("record_user_id <", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_user_id <=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIn(List<Integer> values) {
            addCriterion("record_user_id in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotIn(List<Integer> values) {
            addCriterion("record_user_id not in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id not between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIsNull() {
            addCriterion("original_post is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIsNotNull() {
            addCriterion("original_post is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostEqualTo(String value) {
            addCriterion("original_post =", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotEqualTo(String value) {
            addCriterion("original_post <>", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThan(String value) {
            addCriterion("original_post >", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThanOrEqualTo(String value) {
            addCriterion("original_post >=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThan(String value) {
            addCriterion("original_post <", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThanOrEqualTo(String value) {
            addCriterion("original_post <=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLike(String value) {
            addCriterion("original_post like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotLike(String value) {
            addCriterion("original_post not like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIn(List<String> values) {
            addCriterion("original_post in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotIn(List<String> values) {
            addCriterion("original_post not in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostBetween(String value1, String value2) {
            addCriterion("original_post between", value1, value2, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotBetween(String value1, String value2) {
            addCriterion("original_post not between", value1, value2, "originalPost");
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