package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetProjectExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andTraineeTypeIdsIsNull() {
            addCriterion("trainee_type_ids is null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsIsNotNull() {
            addCriterion("trainee_type_ids is not null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsEqualTo(String value) {
            addCriterion("trainee_type_ids =", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsNotEqualTo(String value) {
            addCriterion("trainee_type_ids <>", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsGreaterThan(String value) {
            addCriterion("trainee_type_ids >", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsGreaterThanOrEqualTo(String value) {
            addCriterion("trainee_type_ids >=", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsLessThan(String value) {
            addCriterion("trainee_type_ids <", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsLessThanOrEqualTo(String value) {
            addCriterion("trainee_type_ids <=", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsLike(String value) {
            addCriterion("trainee_type_ids like", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsNotLike(String value) {
            addCriterion("trainee_type_ids not like", value, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsIn(List<String> values) {
            addCriterion("trainee_type_ids in", values, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsNotIn(List<String> values) {
            addCriterion("trainee_type_ids not in", values, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsBetween(String value1, String value2) {
            addCriterion("trainee_type_ids between", value1, value2, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdsNotBetween(String value1, String value2) {
            addCriterion("trainee_type_ids not between", value1, value2, "traineeTypeIds");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeIsNull() {
            addCriterion("other_trainee_type is null");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeIsNotNull() {
            addCriterion("other_trainee_type is not null");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeEqualTo(String value) {
            addCriterion("other_trainee_type =", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeNotEqualTo(String value) {
            addCriterion("other_trainee_type <>", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeGreaterThan(String value) {
            addCriterion("other_trainee_type >", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("other_trainee_type >=", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeLessThan(String value) {
            addCriterion("other_trainee_type <", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeLessThanOrEqualTo(String value) {
            addCriterion("other_trainee_type <=", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeLike(String value) {
            addCriterion("other_trainee_type like", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeNotLike(String value) {
            addCriterion("other_trainee_type not like", value, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeIn(List<String> values) {
            addCriterion("other_trainee_type in", values, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeNotIn(List<String> values) {
            addCriterion("other_trainee_type not in", values, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeBetween(String value1, String value2) {
            addCriterion("other_trainee_type between", value1, value2, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andOtherTraineeTypeNotBetween(String value1, String value2) {
            addCriterion("other_trainee_type not between", value1, value2, "otherTraineeType");
            return (Criteria) this;
        }

        public Criteria andObjCountIsNull() {
            addCriterion("obj_count is null");
            return (Criteria) this;
        }

        public Criteria andObjCountIsNotNull() {
            addCriterion("obj_count is not null");
            return (Criteria) this;
        }

        public Criteria andObjCountEqualTo(Integer value) {
            addCriterion("obj_count =", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountNotEqualTo(Integer value) {
            addCriterion("obj_count <>", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountGreaterThan(Integer value) {
            addCriterion("obj_count >", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_count >=", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountLessThan(Integer value) {
            addCriterion("obj_count <", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountLessThanOrEqualTo(Integer value) {
            addCriterion("obj_count <=", value, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountIn(List<Integer> values) {
            addCriterion("obj_count in", values, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountNotIn(List<Integer> values) {
            addCriterion("obj_count not in", values, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountBetween(Integer value1, Integer value2) {
            addCriterion("obj_count between", value1, value2, "objCount");
            return (Criteria) this;
        }

        public Criteria andObjCountNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_count not between", value1, value2, "objCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountIsNull() {
            addCriterion("quit_count is null");
            return (Criteria) this;
        }

        public Criteria andQuitCountIsNotNull() {
            addCriterion("quit_count is not null");
            return (Criteria) this;
        }

        public Criteria andQuitCountEqualTo(Integer value) {
            addCriterion("quit_count =", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountNotEqualTo(Integer value) {
            addCriterion("quit_count <>", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountGreaterThan(Integer value) {
            addCriterion("quit_count >", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("quit_count >=", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountLessThan(Integer value) {
            addCriterion("quit_count <", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountLessThanOrEqualTo(Integer value) {
            addCriterion("quit_count <=", value, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountIn(List<Integer> values) {
            addCriterion("quit_count in", values, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountNotIn(List<Integer> values) {
            addCriterion("quit_count not in", values, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountBetween(Integer value1, Integer value2) {
            addCriterion("quit_count between", value1, value2, "quitCount");
            return (Criteria) this;
        }

        public Criteria andQuitCountNotBetween(Integer value1, Integer value2) {
            addCriterion("quit_count not between", value1, value2, "quitCount");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNull() {
            addCriterion("open_time is null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNotNull() {
            addCriterion("open_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeEqualTo(Date value) {
            addCriterion("open_time =", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotEqualTo(Date value) {
            addCriterion("open_time <>", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThan(Date value) {
            addCriterion("open_time >", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("open_time >=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThan(Date value) {
            addCriterion("open_time <", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThanOrEqualTo(Date value) {
            addCriterion("open_time <=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIn(List<Date> values) {
            addCriterion("open_time in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotIn(List<Date> values) {
            addCriterion("open_time not in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeBetween(Date value1, Date value2) {
            addCriterion("open_time between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotBetween(Date value1, Date value2) {
            addCriterion("open_time not between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenAddressIsNull() {
            addCriterion("open_address is null");
            return (Criteria) this;
        }

        public Criteria andOpenAddressIsNotNull() {
            addCriterion("open_address is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAddressEqualTo(String value) {
            addCriterion("open_address =", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressNotEqualTo(String value) {
            addCriterion("open_address <>", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressGreaterThan(String value) {
            addCriterion("open_address >", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressGreaterThanOrEqualTo(String value) {
            addCriterion("open_address >=", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressLessThan(String value) {
            addCriterion("open_address <", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressLessThanOrEqualTo(String value) {
            addCriterion("open_address <=", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressLike(String value) {
            addCriterion("open_address like", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressNotLike(String value) {
            addCriterion("open_address not like", value, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressIn(List<String> values) {
            addCriterion("open_address in", values, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressNotIn(List<String> values) {
            addCriterion("open_address not in", values, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressBetween(String value1, String value2) {
            addCriterion("open_address between", value1, value2, "openAddress");
            return (Criteria) this;
        }

        public Criteria andOpenAddressNotBetween(String value1, String value2) {
            addCriterion("open_address not between", value1, value2, "openAddress");
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

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Boolean value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Boolean value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Boolean value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Boolean value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Boolean value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Boolean> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Boolean> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdIsNull() {
            addCriterion("project_type_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdIsNotNull() {
            addCriterion("project_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdEqualTo(Integer value) {
            addCriterion("project_type_id =", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdNotEqualTo(Integer value) {
            addCriterion("project_type_id <>", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdGreaterThan(Integer value) {
            addCriterion("project_type_id >", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type_id >=", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdLessThan(Integer value) {
            addCriterion("project_type_id <", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_type_id <=", value, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdIn(List<Integer> values) {
            addCriterion("project_type_id in", values, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdNotIn(List<Integer> values) {
            addCriterion("project_type_id not in", values, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("project_type_id between", value1, value2, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type_id not between", value1, value2, "projectTypeId");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
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

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(BigDecimal value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(BigDecimal value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(BigDecimal value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(BigDecimal value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<BigDecimal> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<BigDecimal> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodIsNull() {
            addCriterion("require_period is null");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodIsNotNull() {
            addCriterion("require_period is not null");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodEqualTo(BigDecimal value) {
            addCriterion("require_period =", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodNotEqualTo(BigDecimal value) {
            addCriterion("require_period <>", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodGreaterThan(BigDecimal value) {
            addCriterion("require_period >", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("require_period >=", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodLessThan(BigDecimal value) {
            addCriterion("require_period <", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("require_period <=", value, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodIn(List<BigDecimal> values) {
            addCriterion("require_period in", values, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodNotIn(List<BigDecimal> values) {
            addCriterion("require_period not in", values, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("require_period between", value1, value2, "requirePeriod");
            return (Criteria) this;
        }

        public Criteria andRequirePeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("require_period not between", value1, value2, "requirePeriod");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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