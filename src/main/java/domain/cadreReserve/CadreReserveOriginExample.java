package domain.cadreReserve;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreReserveOriginExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreReserveOriginExample() {
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

        public Criteria andWayIsNull() {
            addCriterion("way is null");
            return (Criteria) this;
        }

        public Criteria andWayIsNotNull() {
            addCriterion("way is not null");
            return (Criteria) this;
        }

        public Criteria andWayEqualTo(Byte value) {
            addCriterion("way =", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotEqualTo(Byte value) {
            addCriterion("way <>", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThan(Byte value) {
            addCriterion("way >", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThanOrEqualTo(Byte value) {
            addCriterion("way >=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThan(Byte value) {
            addCriterion("way <", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThanOrEqualTo(Byte value) {
            addCriterion("way <=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayIn(List<Byte> values) {
            addCriterion("way in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotIn(List<Byte> values) {
            addCriterion("way not in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayBetween(Byte value1, Byte value2) {
            addCriterion("way between", value1, value2, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotBetween(Byte value1, Byte value2) {
            addCriterion("way not between", value1, value2, "way");
            return (Criteria) this;
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

        public Criteria andReserveTypeIsNull() {
            addCriterion("reserve_type is null");
            return (Criteria) this;
        }

        public Criteria andReserveTypeIsNotNull() {
            addCriterion("reserve_type is not null");
            return (Criteria) this;
        }

        public Criteria andReserveTypeEqualTo(Integer value) {
            addCriterion("reserve_type =", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeNotEqualTo(Integer value) {
            addCriterion("reserve_type <>", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeGreaterThan(Integer value) {
            addCriterion("reserve_type >", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reserve_type >=", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeLessThan(Integer value) {
            addCriterion("reserve_type <", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeLessThanOrEqualTo(Integer value) {
            addCriterion("reserve_type <=", value, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeIn(List<Integer> values) {
            addCriterion("reserve_type in", values, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeNotIn(List<Integer> values) {
            addCriterion("reserve_type not in", values, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeBetween(Integer value1, Integer value2) {
            addCriterion("reserve_type between", value1, value2, "reserveType");
            return (Criteria) this;
        }

        public Criteria andReserveTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("reserve_type not between", value1, value2, "reserveType");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitIsNull() {
            addCriterion("recommend_unit is null");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitIsNotNull() {
            addCriterion("recommend_unit is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitEqualTo(String value) {
            addCriterion("recommend_unit =", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitNotEqualTo(String value) {
            addCriterion("recommend_unit <>", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitGreaterThan(String value) {
            addCriterion("recommend_unit >", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_unit >=", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitLessThan(String value) {
            addCriterion("recommend_unit <", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitLessThanOrEqualTo(String value) {
            addCriterion("recommend_unit <=", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitLike(String value) {
            addCriterion("recommend_unit like", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitNotLike(String value) {
            addCriterion("recommend_unit not like", value, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitIn(List<String> values) {
            addCriterion("recommend_unit in", values, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitNotIn(List<String> values) {
            addCriterion("recommend_unit not in", values, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitBetween(String value1, String value2) {
            addCriterion("recommend_unit between", value1, value2, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendUnitNotBetween(String value1, String value2) {
            addCriterion("recommend_unit not between", value1, value2, "recommendUnit");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIsNull() {
            addCriterion("recommend_date is null");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIsNotNull() {
            addCriterion("recommend_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendDateEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date =", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date <>", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateGreaterThan(Date value) {
            addCriterionForJDBCDate("recommend_date >", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date >=", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateLessThan(Date value) {
            addCriterionForJDBCDate("recommend_date <", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("recommend_date <=", value, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateIn(List<Date> values) {
            addCriterionForJDBCDate("recommend_date in", values, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("recommend_date not in", values, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("recommend_date between", value1, value2, "recommendDate");
            return (Criteria) this;
        }

        public Criteria andRecommendDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("recommend_date not between", value1, value2, "recommendDate");
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

        public Criteria andObjIdIsNull() {
            addCriterion("obj_id is null");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjIdEqualTo(Integer value) {
            addCriterion("obj_id =", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotEqualTo(Integer value) {
            addCriterion("obj_id <>", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThan(Integer value) {
            addCriterion("obj_id >", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_id >=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThan(Integer value) {
            addCriterion("obj_id <", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThanOrEqualTo(Integer value) {
            addCriterion("obj_id <=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdIn(List<Integer> values) {
            addCriterion("obj_id in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotIn(List<Integer> values) {
            addCriterion("obj_id not in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdBetween(Integer value1, Integer value2) {
            addCriterion("obj_id between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_id not between", value1, value2, "objId");
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

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
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