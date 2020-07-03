package domain.cet;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class CetUpperTrainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetUpperTrainExample() {
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

        public Criteria andSpecialTypeIsNull() {
            addCriterion("special_type is null");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeIsNotNull() {
            addCriterion("special_type is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeEqualTo(Byte value) {
            addCriterion("special_type =", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotEqualTo(Byte value) {
            addCriterion("special_type <>", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeGreaterThan(Byte value) {
            addCriterion("special_type >", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("special_type >=", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeLessThan(Byte value) {
            addCriterion("special_type <", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeLessThanOrEqualTo(Byte value) {
            addCriterion("special_type <=", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeIn(List<Byte> values) {
            addCriterion("special_type in", values, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotIn(List<Byte> values) {
            addCriterion("special_type not in", values, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeBetween(Byte value1, Byte value2) {
            addCriterion("special_type between", value1, value2, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("special_type not between", value1, value2, "specialType");
            return (Criteria) this;
        }

        public Criteria andUnitAdmin(Set<Integer> adminUnitIdSet) {
            String sql = "";
            if(adminUnitIdSet.size()>0){
                sql += String.format("unit_id in(%s)", StringUtils.join(adminUnitIdSet, ","));
            }
            addCriterion("(" + sql + ")");
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

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNull() {
            addCriterion("is_online is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNotNull() {
            addCriterion("is_online is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineEqualTo(Boolean value) {
            addCriterion("is_online =", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotEqualTo(Boolean value) {
            addCriterion("is_online <>", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThan(Boolean value) {
            addCriterion("is_online >", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_online >=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThan(Boolean value) {
            addCriterion("is_online <", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThanOrEqualTo(Boolean value) {
            addCriterion("is_online <=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIn(List<Boolean> values) {
            addCriterion("is_online in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotIn(List<Boolean> values) {
            addCriterion("is_online not in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online between", value1, value2, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online not between", value1, value2, "isOnline");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
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

        public Criteria andTraineeTypeIdIsNull() {
            addCriterion("trainee_type_id is null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdIsNotNull() {
            addCriterion("trainee_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdEqualTo(Integer value) {
            addCriterion("trainee_type_id =", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotEqualTo(Integer value) {
            addCriterion("trainee_type_id <>", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdGreaterThan(Integer value) {
            addCriterion("trainee_type_id >", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trainee_type_id >=", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdLessThan(Integer value) {
            addCriterion("trainee_type_id <", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("trainee_type_id <=", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdIn(List<Integer> values) {
            addCriterion("trainee_type_id in", values, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotIn(List<Integer> values) {
            addCriterion("trainee_type_id not in", values, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("trainee_type_id between", value1, value2, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trainee_type_id not between", value1, value2, "traineeTypeId");
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

        public Criteria andIdentityIsNull() {
            addCriterion("identity is null");
            return (Criteria) this;
        }

        public Criteria andIdentityIsNotNull() {
            addCriterion("identity is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityEqualTo(String value) {
            addCriterion("identity =", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotEqualTo(String value) {
            addCriterion("identity <>", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThan(String value) {
            addCriterion("identity >", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("identity >=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThan(String value) {
            addCriterion("identity <", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThanOrEqualTo(String value) {
            addCriterion("identity <=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLike(Integer[] values) {
            List searchSqlList = new ArrayList<>();
            for (Integer value : values) {
                searchSqlList.add("identity like '%," + value + ",%'");
            }
            addCriterion("(" + StringUtils.join(searchSqlList, " and ") + ")");
            return (Criteria) this;
        }

        public Criteria andIdentityNotLike(String value) {
            addCriterion("identity not like", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityIn(List<String> values) {
            addCriterion("identity in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotIn(List<String> values) {
            addCriterion("identity not in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityBetween(String value1, String value2) {
            addCriterion("identity between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotBetween(String value1, String value2) {
            addCriterion("identity not between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNull() {
            addCriterion("post_type is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNotNull() {
            addCriterion("post_type is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeEqualTo(Integer value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(Integer value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(Integer value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(Integer value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<Integer> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<Integer> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type not between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andOrganizerIsNull() {
            addCriterion("organizer is null");
            return (Criteria) this;
        }

        public Criteria andOrganizerIsNotNull() {
            addCriterion("organizer is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizerEqualTo(Integer value) {
            addCriterion("organizer =", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerNotEqualTo(Integer value) {
            addCriterion("organizer <>", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerGreaterThan(Integer value) {
            addCriterion("organizer >", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerGreaterThanOrEqualTo(Integer value) {
            addCriterion("organizer >=", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerLessThan(Integer value) {
            addCriterion("organizer <", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerLessThanOrEqualTo(Integer value) {
            addCriterion("organizer <=", value, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerIn(List<Integer> values) {
            addCriterion("organizer in", values, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerNotIn(List<Integer> values) {
            addCriterion("organizer not in", values, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerBetween(Integer value1, Integer value2) {
            addCriterion("organizer between", value1, value2, "organizer");
            return (Criteria) this;
        }

        public Criteria andOrganizerNotBetween(Integer value1, Integer value2) {
            addCriterion("organizer not between", value1, value2, "organizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerIsNull() {
            addCriterion("other_organizer is null");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerIsNotNull() {
            addCriterion("other_organizer is not null");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerEqualTo(String value) {
            addCriterion("other_organizer =", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerNotEqualTo(String value) {
            addCriterion("other_organizer <>", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerGreaterThan(String value) {
            addCriterion("other_organizer >", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerGreaterThanOrEqualTo(String value) {
            addCriterion("other_organizer >=", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerLessThan(String value) {
            addCriterion("other_organizer <", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerLessThanOrEqualTo(String value) {
            addCriterion("other_organizer <=", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerLike(String value) {
            addCriterion("other_organizer like", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerNotLike(String value) {
            addCriterion("other_organizer not like", value, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerIn(List<String> values) {
            addCriterion("other_organizer in", values, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerNotIn(List<String> values) {
            addCriterion("other_organizer not in", values, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerBetween(String value1, String value2) {
            addCriterion("other_organizer between", value1, value2, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andOtherOrganizerNotBetween(String value1, String value2) {
            addCriterion("other_organizer not between", value1, value2, "otherOrganizer");
            return (Criteria) this;
        }

        public Criteria andTrainTypeIsNull() {
            addCriterion("train_type is null");
            return (Criteria) this;
        }

        public Criteria andTrainTypeIsNotNull() {
            addCriterion("train_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrainTypeEqualTo(Integer value) {
            addCriterion("train_type =", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeNotEqualTo(Integer value) {
            addCriterion("train_type <>", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeGreaterThan(Integer value) {
            addCriterion("train_type >", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_type >=", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeLessThan(Integer value) {
            addCriterion("train_type <", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeLessThanOrEqualTo(Integer value) {
            addCriterion("train_type <=", value, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeIn(List<Integer> values) {
            addCriterion("train_type in", values, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeNotIn(List<Integer> values) {
            addCriterion("train_type not in", values, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeBetween(Integer value1, Integer value2) {
            addCriterion("train_type between", value1, value2, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("train_type not between", value1, value2, "trainType");
            return (Criteria) this;
        }

        public Criteria andTrainNameIsNull() {
            addCriterion("train_name is null");
            return (Criteria) this;
        }

        public Criteria andTrainNameIsNotNull() {
            addCriterion("train_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrainNameEqualTo(String value) {
            addCriterion("train_name =", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameNotEqualTo(String value) {
            addCriterion("train_name <>", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameGreaterThan(String value) {
            addCriterion("train_name >", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameGreaterThanOrEqualTo(String value) {
            addCriterion("train_name >=", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameLessThan(String value) {
            addCriterion("train_name <", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameLessThanOrEqualTo(String value) {
            addCriterion("train_name <=", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameLike(String value) {
            addCriterion("train_name like", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameNotLike(String value) {
            addCriterion("train_name not like", value, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameIn(List<String> values) {
            addCriterion("train_name in", values, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameNotIn(List<String> values) {
            addCriterion("train_name not in", values, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameBetween(String value1, String value2) {
            addCriterion("train_name between", value1, value2, "trainName");
            return (Criteria) this;
        }

        public Criteria andTrainNameNotBetween(String value1, String value2) {
            addCriterion("train_name not between", value1, value2, "trainName");
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

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andAgencyIsNull() {
            addCriterion("agency is null");
            return (Criteria) this;
        }

        public Criteria andAgencyIsNotNull() {
            addCriterion("agency is not null");
            return (Criteria) this;
        }

        public Criteria andAgencyEqualTo(String value) {
            addCriterion("agency =", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyNotEqualTo(String value) {
            addCriterion("agency <>", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyGreaterThan(String value) {
            addCriterion("agency >", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyGreaterThanOrEqualTo(String value) {
            addCriterion("agency >=", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyLessThan(String value) {
            addCriterion("agency <", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyLessThanOrEqualTo(String value) {
            addCriterion("agency <=", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyLike(String value) {
            addCriterion("agency like", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyNotLike(String value) {
            addCriterion("agency not like", value, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyIn(List<String> values) {
            addCriterion("agency in", values, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyNotIn(List<String> values) {
            addCriterion("agency not in", values, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyBetween(String value1, String value2) {
            addCriterion("agency between", value1, value2, "agency");
            return (Criteria) this;
        }

        public Criteria andAgencyNotBetween(String value1, String value2) {
            addCriterion("agency not between", value1, value2, "agency");
            return (Criteria) this;
        }

        public Criteria andWordNoteIsNull() {
            addCriterion("word_note is null");
            return (Criteria) this;
        }

        public Criteria andWordNoteIsNotNull() {
            addCriterion("word_note is not null");
            return (Criteria) this;
        }

        public Criteria andWordNoteEqualTo(String value) {
            addCriterion("word_note =", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteNotEqualTo(String value) {
            addCriterion("word_note <>", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteGreaterThan(String value) {
            addCriterion("word_note >", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteGreaterThanOrEqualTo(String value) {
            addCriterion("word_note >=", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteLessThan(String value) {
            addCriterion("word_note <", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteLessThanOrEqualTo(String value) {
            addCriterion("word_note <=", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteLike(String value) {
            addCriterion("word_note like", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteNotLike(String value) {
            addCriterion("word_note not like", value, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteIn(List<String> values) {
            addCriterion("word_note in", values, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteNotIn(List<String> values) {
            addCriterion("word_note not in", values, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteBetween(String value1, String value2) {
            addCriterion("word_note between", value1, value2, "wordNote");
            return (Criteria) this;
        }

        public Criteria andWordNoteNotBetween(String value1, String value2) {
            addCriterion("word_note not between", value1, value2, "wordNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteIsNull() {
            addCriterion("pdf_note is null");
            return (Criteria) this;
        }

        public Criteria andPdfNoteIsNotNull() {
            addCriterion("pdf_note is not null");
            return (Criteria) this;
        }

        public Criteria andPdfNoteEqualTo(String value) {
            addCriterion("pdf_note =", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteNotEqualTo(String value) {
            addCriterion("pdf_note <>", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteGreaterThan(String value) {
            addCriterion("pdf_note >", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteGreaterThanOrEqualTo(String value) {
            addCriterion("pdf_note >=", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteLessThan(String value) {
            addCriterion("pdf_note <", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteLessThanOrEqualTo(String value) {
            addCriterion("pdf_note <=", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteLike(String value) {
            addCriterion("pdf_note like", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteNotLike(String value) {
            addCriterion("pdf_note not like", value, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteIn(List<String> values) {
            addCriterion("pdf_note in", values, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteNotIn(List<String> values) {
            addCriterion("pdf_note not in", values, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteBetween(String value1, String value2) {
            addCriterion("pdf_note between", value1, value2, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andPdfNoteNotBetween(String value1, String value2) {
            addCriterion("pdf_note not between", value1, value2, "pdfNote");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(String value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(String value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(String value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(String value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(String value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(String value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLike(String value) {
            addCriterion("score like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotLike(String value) {
            addCriterion("score not like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<String> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<String> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(String value1, String value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(String value1, String value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andAddTypeIsNull() {
            addCriterion("add_type is null");
            return (Criteria) this;
        }

        public Criteria andAddTypeIsNotNull() {
            addCriterion("add_type is not null");
            return (Criteria) this;
        }

        public Criteria andAddTypeEqualTo(Byte value) {
            addCriterion("add_type =", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeNotEqualTo(Byte value) {
            addCriterion("add_type <>", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeGreaterThan(Byte value) {
            addCriterion("add_type >", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("add_type >=", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeLessThan(Byte value) {
            addCriterion("add_type <", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeLessThanOrEqualTo(Byte value) {
            addCriterion("add_type <=", value, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeIn(List<Byte> values) {
            addCriterion("add_type in", values, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeNotIn(List<Byte> values) {
            addCriterion("add_type not in", values, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeBetween(Byte value1, Byte value2) {
            addCriterion("add_type between", value1, value2, "addType");
            return (Criteria) this;
        }

        public Criteria andAddTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("add_type not between", value1, value2, "addType");
            return (Criteria) this;
        }

        public Criteria andAddUserIdIsNull() {
            addCriterion("add_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAddUserIdIsNotNull() {
            addCriterion("add_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddUserIdEqualTo(Integer value) {
            addCriterion("add_user_id =", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdNotEqualTo(Integer value) {
            addCriterion("add_user_id <>", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdGreaterThan(Integer value) {
            addCriterion("add_user_id >", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_user_id >=", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdLessThan(Integer value) {
            addCriterion("add_user_id <", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("add_user_id <=", value, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdIn(List<Integer> values) {
            addCriterion("add_user_id in", values, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdNotIn(List<Integer> values) {
            addCriterion("add_user_id not in", values, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdBetween(Integer value1, Integer value2) {
            addCriterion("add_user_id between", value1, value2, "addUserId");
            return (Criteria) this;
        }

        public Criteria andAddUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("add_user_id not between", value1, value2, "addUserId");
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

        public Criteria andBackReasonIsNull() {
            addCriterion("back_reason is null");
            return (Criteria) this;
        }

        public Criteria andBackReasonIsNotNull() {
            addCriterion("back_reason is not null");
            return (Criteria) this;
        }

        public Criteria andBackReasonEqualTo(String value) {
            addCriterion("back_reason =", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotEqualTo(String value) {
            addCriterion("back_reason <>", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonGreaterThan(String value) {
            addCriterion("back_reason >", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonGreaterThanOrEqualTo(String value) {
            addCriterion("back_reason >=", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLessThan(String value) {
            addCriterion("back_reason <", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLessThanOrEqualTo(String value) {
            addCriterion("back_reason <=", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLike(String value) {
            addCriterion("back_reason like", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotLike(String value) {
            addCriterion("back_reason not like", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonIn(List<String> values) {
            addCriterion("back_reason in", values, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotIn(List<String> values) {
            addCriterion("back_reason not in", values, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonBetween(String value1, String value2) {
            addCriterion("back_reason between", value1, value2, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotBetween(String value1, String value2) {
            addCriterion("back_reason not between", value1, value2, "backReason");
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