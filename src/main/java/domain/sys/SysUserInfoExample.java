package domain.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SysUserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysUserInfoExample() {
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

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNull() {
            addCriterion("user_status is null");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNotNull() {
            addCriterion("user_status is not null");
            return (Criteria) this;
        }

        public Criteria andUserStatusEqualTo(String value) {
            addCriterion("user_status =", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotEqualTo(String value) {
            addCriterion("user_status <>", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThan(String value) {
            addCriterion("user_status >", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThanOrEqualTo(String value) {
            addCriterion("user_status >=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThan(String value) {
            addCriterion("user_status <", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThanOrEqualTo(String value) {
            addCriterion("user_status <=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLike(String value) {
            addCriterion("user_status like", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotLike(String value) {
            addCriterion("user_status not like", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusIn(List<String> values) {
            addCriterion("user_status in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotIn(List<String> values) {
            addCriterion("user_status not in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusBetween(String value1, String value2) {
            addCriterion("user_status between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotBetween(String value1, String value2) {
            addCriterion("user_status not between", value1, value2, "userStatus");
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

        public Criteria andIdcardTypeIsNull() {
            addCriterion("idcard_type is null");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeIsNotNull() {
            addCriterion("idcard_type is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeEqualTo(String value) {
            addCriterion("idcard_type =", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeNotEqualTo(String value) {
            addCriterion("idcard_type <>", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeGreaterThan(String value) {
            addCriterion("idcard_type >", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("idcard_type >=", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeLessThan(String value) {
            addCriterion("idcard_type <", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeLessThanOrEqualTo(String value) {
            addCriterion("idcard_type <=", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeLike(String value) {
            addCriterion("idcard_type like", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeNotLike(String value) {
            addCriterion("idcard_type not like", value, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeIn(List<String> values) {
            addCriterion("idcard_type in", values, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeNotIn(List<String> values) {
            addCriterion("idcard_type not in", values, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeBetween(String value1, String value2) {
            addCriterion("idcard_type between", value1, value2, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardTypeNotBetween(String value1, String value2) {
            addCriterion("idcard_type not between", value1, value2, "idcardType");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNull() {
            addCriterion("avatar is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("avatar is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("avatar =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("avatar <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("avatar >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("avatar >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("avatar <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("avatar <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("avatar like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("avatar not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("avatar in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("avatar not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("avatar between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("avatar not between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeIsNull() {
            addCriterion("avatar_upload_time is null");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeIsNotNull() {
            addCriterion("avatar_upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeEqualTo(Date value) {
            addCriterion("avatar_upload_time =", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeNotEqualTo(Date value) {
            addCriterion("avatar_upload_time <>", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeGreaterThan(Date value) {
            addCriterion("avatar_upload_time >", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("avatar_upload_time >=", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeLessThan(Date value) {
            addCriterion("avatar_upload_time <", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("avatar_upload_time <=", value, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeIn(List<Date> values) {
            addCriterion("avatar_upload_time in", values, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeNotIn(List<Date> values) {
            addCriterion("avatar_upload_time not in", values, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeBetween(Date value1, Date value2) {
            addCriterion("avatar_upload_time between", value1, value2, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andAvatarUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("avatar_upload_time not between", value1, value2, "avatarUploadTime");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIsNull() {
            addCriterion("homeplace is null");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIsNotNull() {
            addCriterion("homeplace is not null");
            return (Criteria) this;
        }

        public Criteria andHomeplaceEqualTo(String value) {
            addCriterion("homeplace =", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotEqualTo(String value) {
            addCriterion("homeplace <>", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceGreaterThan(String value) {
            addCriterion("homeplace >", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceGreaterThanOrEqualTo(String value) {
            addCriterion("homeplace >=", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLessThan(String value) {
            addCriterion("homeplace <", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLessThanOrEqualTo(String value) {
            addCriterion("homeplace <=", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLike(String value) {
            addCriterion("homeplace like", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotLike(String value) {
            addCriterion("homeplace not like", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIn(List<String> values) {
            addCriterion("homeplace in", values, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotIn(List<String> values) {
            addCriterion("homeplace not in", values, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceBetween(String value1, String value2) {
            addCriterion("homeplace between", value1, value2, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotBetween(String value1, String value2) {
            addCriterion("homeplace not between", value1, value2, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHouseholdIsNull() {
            addCriterion("household is null");
            return (Criteria) this;
        }

        public Criteria andHouseholdIsNotNull() {
            addCriterion("household is not null");
            return (Criteria) this;
        }

        public Criteria andHouseholdEqualTo(String value) {
            addCriterion("household =", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdNotEqualTo(String value) {
            addCriterion("household <>", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdGreaterThan(String value) {
            addCriterion("household >", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdGreaterThanOrEqualTo(String value) {
            addCriterion("household >=", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdLessThan(String value) {
            addCriterion("household <", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdLessThanOrEqualTo(String value) {
            addCriterion("household <=", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdLike(String value) {
            addCriterion("household like", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdNotLike(String value) {
            addCriterion("household not like", value, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdIn(List<String> values) {
            addCriterion("household in", values, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdNotIn(List<String> values) {
            addCriterion("household not in", values, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdBetween(String value1, String value2) {
            addCriterion("household between", value1, value2, "household");
            return (Criteria) this;
        }

        public Criteria andHouseholdNotBetween(String value1, String value2) {
            addCriterion("household not between", value1, value2, "household");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIsNull() {
            addCriterion("specialty is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIsNotNull() {
            addCriterion("specialty is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEqualTo(String value) {
            addCriterion("specialty =", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotEqualTo(String value) {
            addCriterion("specialty <>", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyGreaterThan(String value) {
            addCriterion("specialty >", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyGreaterThanOrEqualTo(String value) {
            addCriterion("specialty >=", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLessThan(String value) {
            addCriterion("specialty <", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLessThanOrEqualTo(String value) {
            addCriterion("specialty <=", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLike(String value) {
            addCriterion("specialty like", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotLike(String value) {
            addCriterion("specialty not like", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIn(List<String> values) {
            addCriterion("specialty in", values, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotIn(List<String> values) {
            addCriterion("specialty not in", values, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyBetween(String value1, String value2) {
            addCriterion("specialty between", value1, value2, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotBetween(String value1, String value2) {
            addCriterion("specialty not between", value1, value2, "specialty");
            return (Criteria) this;
        }

        public Criteria andHealthIsNull() {
            addCriterion("health is null");
            return (Criteria) this;
        }

        public Criteria andHealthIsNotNull() {
            addCriterion("health is not null");
            return (Criteria) this;
        }

        public Criteria andHealthEqualTo(Integer value) {
            addCriterion("health =", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthNotEqualTo(Integer value) {
            addCriterion("health <>", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthGreaterThan(Integer value) {
            addCriterion("health >", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthGreaterThanOrEqualTo(Integer value) {
            addCriterion("health >=", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthLessThan(Integer value) {
            addCriterion("health <", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthLessThanOrEqualTo(Integer value) {
            addCriterion("health <=", value, "health");
            return (Criteria) this;
        }

        public Criteria andHealthIn(List<Integer> values) {
            addCriterion("health in", values, "health");
            return (Criteria) this;
        }

        public Criteria andHealthNotIn(List<Integer> values) {
            addCriterion("health not in", values, "health");
            return (Criteria) this;
        }

        public Criteria andHealthBetween(Integer value1, Integer value2) {
            addCriterion("health between", value1, value2, "health");
            return (Criteria) this;
        }

        public Criteria andHealthNotBetween(Integer value1, Integer value2) {
            addCriterion("health not between", value1, value2, "health");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileIsNull() {
            addCriterion("msg_mobile is null");
            return (Criteria) this;
        }

        public Criteria andMsgMobileIsNotNull() {
            addCriterion("msg_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMsgMobileEqualTo(String value) {
            addCriterion("msg_mobile =", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileNotEqualTo(String value) {
            addCriterion("msg_mobile <>", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileGreaterThan(String value) {
            addCriterion("msg_mobile >", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileGreaterThanOrEqualTo(String value) {
            addCriterion("msg_mobile >=", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileLessThan(String value) {
            addCriterion("msg_mobile <", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileLessThanOrEqualTo(String value) {
            addCriterion("msg_mobile <=", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileLike(String value) {
            addCriterion("msg_mobile like", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileNotLike(String value) {
            addCriterion("msg_mobile not like", value, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileIn(List<String> values) {
            addCriterion("msg_mobile in", values, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileNotIn(List<String> values) {
            addCriterion("msg_mobile not in", values, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileBetween(String value1, String value2) {
            addCriterion("msg_mobile between", value1, value2, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andMsgMobileNotBetween(String value1, String value2) {
            addCriterion("msg_mobile not between", value1, value2, "msgMobile");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgIsNull() {
            addCriterion("not_send_msg is null");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgIsNotNull() {
            addCriterion("not_send_msg is not null");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgEqualTo(Boolean value) {
            addCriterion("not_send_msg =", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgNotEqualTo(Boolean value) {
            addCriterion("not_send_msg <>", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgGreaterThan(Boolean value) {
            addCriterion("not_send_msg >", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("not_send_msg >=", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgLessThan(Boolean value) {
            addCriterion("not_send_msg <", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgLessThanOrEqualTo(Boolean value) {
            addCriterion("not_send_msg <=", value, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgIn(List<Boolean> values) {
            addCriterion("not_send_msg in", values, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgNotIn(List<Boolean> values) {
            addCriterion("not_send_msg not in", values, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgBetween(Boolean value1, Boolean value2) {
            addCriterion("not_send_msg between", value1, value2, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andNotSendMsgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("not_send_msg not between", value1, value2, "notSendMsg");
            return (Criteria) this;
        }

        public Criteria andFileNumberIsNull() {
            addCriterion("file_number is null");
            return (Criteria) this;
        }

        public Criteria andFileNumberIsNotNull() {
            addCriterion("file_number is not null");
            return (Criteria) this;
        }

        public Criteria andFileNumberEqualTo(String value) {
            addCriterion("file_number =", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberNotEqualTo(String value) {
            addCriterion("file_number <>", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberGreaterThan(String value) {
            addCriterion("file_number >", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberGreaterThanOrEqualTo(String value) {
            addCriterion("file_number >=", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberLessThan(String value) {
            addCriterion("file_number <", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberLessThanOrEqualTo(String value) {
            addCriterion("file_number <=", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberLike(String value) {
            addCriterion("file_number like", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberNotLike(String value) {
            addCriterion("file_number not like", value, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberIn(List<String> values) {
            addCriterion("file_number in", values, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberNotIn(List<String> values) {
            addCriterion("file_number not in", values, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberBetween(String value1, String value2) {
            addCriterion("file_number between", value1, value2, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andFileNumberNotBetween(String value1, String value2) {
            addCriterion("file_number not between", value1, value2, "fileNumber");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNull() {
            addCriterion("unit_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNotNull() {
            addCriterion("unit_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeEqualTo(String value) {
            addCriterion("unit_code =", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotEqualTo(String value) {
            addCriterion("unit_code <>", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThan(String value) {
            addCriterion("unit_code >", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unit_code >=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThan(String value) {
            addCriterion("unit_code <", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThanOrEqualTo(String value) {
            addCriterion("unit_code <=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLike(String value) {
            addCriterion("unit_code like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotLike(String value) {
            addCriterion("unit_code not like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIn(List<String> values) {
            addCriterion("unit_code in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotIn(List<String> values) {
            addCriterion("unit_code not in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeBetween(String value1, String value2) {
            addCriterion("unit_code between", value1, value2, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotBetween(String value1, String value2) {
            addCriterion("unit_code not between", value1, value2, "unitCode");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNull() {
            addCriterion("home_phone is null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNotNull() {
            addCriterion("home_phone is not null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneEqualTo(String value) {
            addCriterion("home_phone =", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotEqualTo(String value) {
            addCriterion("home_phone <>", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThan(String value) {
            addCriterion("home_phone >", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("home_phone >=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThan(String value) {
            addCriterion("home_phone <", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThanOrEqualTo(String value) {
            addCriterion("home_phone <=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLike(String value) {
            addCriterion("home_phone like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotLike(String value) {
            addCriterion("home_phone not like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIn(List<String> values) {
            addCriterion("home_phone in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotIn(List<String> values) {
            addCriterion("home_phone not in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneBetween(String value1, String value2) {
            addCriterion("home_phone between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotBetween(String value1, String value2) {
            addCriterion("home_phone not between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andMailingAddressIsNull() {
            addCriterion("mailing_address is null");
            return (Criteria) this;
        }

        public Criteria andMailingAddressIsNotNull() {
            addCriterion("mailing_address is not null");
            return (Criteria) this;
        }

        public Criteria andMailingAddressEqualTo(String value) {
            addCriterion("mailing_address =", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressNotEqualTo(String value) {
            addCriterion("mailing_address <>", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressGreaterThan(String value) {
            addCriterion("mailing_address >", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("mailing_address >=", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressLessThan(String value) {
            addCriterion("mailing_address <", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressLessThanOrEqualTo(String value) {
            addCriterion("mailing_address <=", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressLike(String value) {
            addCriterion("mailing_address like", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressNotLike(String value) {
            addCriterion("mailing_address not like", value, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressIn(List<String> values) {
            addCriterion("mailing_address in", values, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressNotIn(List<String> values) {
            addCriterion("mailing_address not in", values, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressBetween(String value1, String value2) {
            addCriterion("mailing_address between", value1, value2, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMailingAddressNotBetween(String value1, String value2) {
            addCriterion("mailing_address not between", value1, value2, "mailingAddress");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
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

        public Criteria andSyncIsNull() {
            addCriterion("sync is null");
            return (Criteria) this;
        }

        public Criteria andSyncIsNotNull() {
            addCriterion("sync is not null");
            return (Criteria) this;
        }

        public Criteria andSyncEqualTo(Integer value) {
            addCriterion("sync =", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotEqualTo(Integer value) {
            addCriterion("sync <>", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncGreaterThan(Integer value) {
            addCriterion("sync >", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncGreaterThanOrEqualTo(Integer value) {
            addCriterion("sync >=", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncLessThan(Integer value) {
            addCriterion("sync <", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncLessThanOrEqualTo(Integer value) {
            addCriterion("sync <=", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncIn(List<Integer> values) {
            addCriterion("sync in", values, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotIn(List<Integer> values) {
            addCriterion("sync not in", values, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncBetween(Integer value1, Integer value2) {
            addCriterion("sync between", value1, value2, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotBetween(Integer value1, Integer value2) {
            addCriterion("sync not between", value1, value2, "sync");
            return (Criteria) this;
        }

        public Criteria andResIdsAddIsNull() {
            addCriterion("res_ids_add is null");
            return (Criteria) this;
        }

        public Criteria andResIdsAddIsNotNull() {
            addCriterion("res_ids_add is not null");
            return (Criteria) this;
        }

        public Criteria andResIdsAddEqualTo(String value) {
            addCriterion("res_ids_add =", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddNotEqualTo(String value) {
            addCriterion("res_ids_add <>", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddGreaterThan(String value) {
            addCriterion("res_ids_add >", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddGreaterThanOrEqualTo(String value) {
            addCriterion("res_ids_add >=", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddLessThan(String value) {
            addCriterion("res_ids_add <", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddLessThanOrEqualTo(String value) {
            addCriterion("res_ids_add <=", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddLike(String value) {
            addCriterion("res_ids_add like", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddNotLike(String value) {
            addCriterion("res_ids_add not like", value, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddIn(List<String> values) {
            addCriterion("res_ids_add in", values, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddNotIn(List<String> values) {
            addCriterion("res_ids_add not in", values, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddBetween(String value1, String value2) {
            addCriterion("res_ids_add between", value1, value2, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsAddNotBetween(String value1, String value2) {
            addCriterion("res_ids_add not between", value1, value2, "resIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddIsNull() {
            addCriterion("m_res_ids_add is null");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddIsNotNull() {
            addCriterion("m_res_ids_add is not null");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddEqualTo(String value) {
            addCriterion("m_res_ids_add =", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddNotEqualTo(String value) {
            addCriterion("m_res_ids_add <>", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddGreaterThan(String value) {
            addCriterion("m_res_ids_add >", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddGreaterThanOrEqualTo(String value) {
            addCriterion("m_res_ids_add >=", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddLessThan(String value) {
            addCriterion("m_res_ids_add <", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddLessThanOrEqualTo(String value) {
            addCriterion("m_res_ids_add <=", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddLike(String value) {
            addCriterion("m_res_ids_add like", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddNotLike(String value) {
            addCriterion("m_res_ids_add not like", value, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddIn(List<String> values) {
            addCriterion("m_res_ids_add in", values, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddNotIn(List<String> values) {
            addCriterion("m_res_ids_add not in", values, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddBetween(String value1, String value2) {
            addCriterion("m_res_ids_add between", value1, value2, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andMResIdsAddNotBetween(String value1, String value2) {
            addCriterion("m_res_ids_add not between", value1, value2, "mResIdsAdd");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusIsNull() {
            addCriterion("res_ids_minus is null");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusIsNotNull() {
            addCriterion("res_ids_minus is not null");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusEqualTo(String value) {
            addCriterion("res_ids_minus =", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusNotEqualTo(String value) {
            addCriterion("res_ids_minus <>", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusGreaterThan(String value) {
            addCriterion("res_ids_minus >", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusGreaterThanOrEqualTo(String value) {
            addCriterion("res_ids_minus >=", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusLessThan(String value) {
            addCriterion("res_ids_minus <", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusLessThanOrEqualTo(String value) {
            addCriterion("res_ids_minus <=", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusLike(String value) {
            addCriterion("res_ids_minus like", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusNotLike(String value) {
            addCriterion("res_ids_minus not like", value, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusIn(List<String> values) {
            addCriterion("res_ids_minus in", values, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusNotIn(List<String> values) {
            addCriterion("res_ids_minus not in", values, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusBetween(String value1, String value2) {
            addCriterion("res_ids_minus between", value1, value2, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andResIdsMinusNotBetween(String value1, String value2) {
            addCriterion("res_ids_minus not between", value1, value2, "resIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusIsNull() {
            addCriterion("m_res_ids_minus is null");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusIsNotNull() {
            addCriterion("m_res_ids_minus is not null");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusEqualTo(String value) {
            addCriterion("m_res_ids_minus =", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusNotEqualTo(String value) {
            addCriterion("m_res_ids_minus <>", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusGreaterThan(String value) {
            addCriterion("m_res_ids_minus >", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusGreaterThanOrEqualTo(String value) {
            addCriterion("m_res_ids_minus >=", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusLessThan(String value) {
            addCriterion("m_res_ids_minus <", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusLessThanOrEqualTo(String value) {
            addCriterion("m_res_ids_minus <=", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusLike(String value) {
            addCriterion("m_res_ids_minus like", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusNotLike(String value) {
            addCriterion("m_res_ids_minus not like", value, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusIn(List<String> values) {
            addCriterion("m_res_ids_minus in", values, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusNotIn(List<String> values) {
            addCriterion("m_res_ids_minus not in", values, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusBetween(String value1, String value2) {
            addCriterion("m_res_ids_minus between", value1, value2, "mResIdsMinus");
            return (Criteria) this;
        }

        public Criteria andMResIdsMinusNotBetween(String value1, String value2) {
            addCriterion("m_res_ids_minus not between", value1, value2, "mResIdsMinus");
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