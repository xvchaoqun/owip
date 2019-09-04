package domain.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShortMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShortMsgExample() {
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

        public Criteria andWxMsgTypeIsNull() {
            addCriterion("wx_msg_type is null");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeIsNotNull() {
            addCriterion("wx_msg_type is not null");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeEqualTo(Byte value) {
            addCriterion("wx_msg_type =", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotEqualTo(Byte value) {
            addCriterion("wx_msg_type <>", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeGreaterThan(Byte value) {
            addCriterion("wx_msg_type >", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("wx_msg_type >=", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeLessThan(Byte value) {
            addCriterion("wx_msg_type <", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeLessThanOrEqualTo(Byte value) {
            addCriterion("wx_msg_type <=", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeIn(List<Byte> values) {
            addCriterion("wx_msg_type in", values, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotIn(List<Byte> values) {
            addCriterion("wx_msg_type not in", values, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeBetween(Byte value1, Byte value2) {
            addCriterion("wx_msg_type between", value1, value2, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("wx_msg_type not between", value1, value2, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxTitleIsNull() {
            addCriterion("wx_title is null");
            return (Criteria) this;
        }

        public Criteria andWxTitleIsNotNull() {
            addCriterion("wx_title is not null");
            return (Criteria) this;
        }

        public Criteria andWxTitleEqualTo(String value) {
            addCriterion("wx_title =", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotEqualTo(String value) {
            addCriterion("wx_title <>", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleGreaterThan(String value) {
            addCriterion("wx_title >", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleGreaterThanOrEqualTo(String value) {
            addCriterion("wx_title >=", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLessThan(String value) {
            addCriterion("wx_title <", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLessThanOrEqualTo(String value) {
            addCriterion("wx_title <=", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLike(String value) {
            addCriterion("wx_title like", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotLike(String value) {
            addCriterion("wx_title not like", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleIn(List<String> values) {
            addCriterion("wx_title in", values, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotIn(List<String> values) {
            addCriterion("wx_title not in", values, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleBetween(String value1, String value2) {
            addCriterion("wx_title between", value1, value2, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotBetween(String value1, String value2) {
            addCriterion("wx_title not between", value1, value2, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxUrlIsNull() {
            addCriterion("wx_url is null");
            return (Criteria) this;
        }

        public Criteria andWxUrlIsNotNull() {
            addCriterion("wx_url is not null");
            return (Criteria) this;
        }

        public Criteria andWxUrlEqualTo(String value) {
            addCriterion("wx_url =", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotEqualTo(String value) {
            addCriterion("wx_url <>", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlGreaterThan(String value) {
            addCriterion("wx_url >", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlGreaterThanOrEqualTo(String value) {
            addCriterion("wx_url >=", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLessThan(String value) {
            addCriterion("wx_url <", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLessThanOrEqualTo(String value) {
            addCriterion("wx_url <=", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLike(String value) {
            addCriterion("wx_url like", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotLike(String value) {
            addCriterion("wx_url not like", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlIn(List<String> values) {
            addCriterion("wx_url in", values, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotIn(List<String> values) {
            addCriterion("wx_url not in", values, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlBetween(String value1, String value2) {
            addCriterion("wx_url between", value1, value2, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotBetween(String value1, String value2) {
            addCriterion("wx_url not between", value1, value2, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxPicIsNull() {
            addCriterion("wx_pic is null");
            return (Criteria) this;
        }

        public Criteria andWxPicIsNotNull() {
            addCriterion("wx_pic is not null");
            return (Criteria) this;
        }

        public Criteria andWxPicEqualTo(String value) {
            addCriterion("wx_pic =", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotEqualTo(String value) {
            addCriterion("wx_pic <>", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicGreaterThan(String value) {
            addCriterion("wx_pic >", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicGreaterThanOrEqualTo(String value) {
            addCriterion("wx_pic >=", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLessThan(String value) {
            addCriterion("wx_pic <", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLessThanOrEqualTo(String value) {
            addCriterion("wx_pic <=", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLike(String value) {
            addCriterion("wx_pic like", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotLike(String value) {
            addCriterion("wx_pic not like", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicIn(List<String> values) {
            addCriterion("wx_pic in", values, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotIn(List<String> values) {
            addCriterion("wx_pic not in", values, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicBetween(String value1, String value2) {
            addCriterion("wx_pic between", value1, value2, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotBetween(String value1, String value2) {
            addCriterion("wx_pic not between", value1, value2, "wxPic");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNull() {
            addCriterion("sender_id is null");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNotNull() {
            addCriterion("sender_id is not null");
            return (Criteria) this;
        }

        public Criteria andSenderIdEqualTo(Integer value) {
            addCriterion("sender_id =", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotEqualTo(Integer value) {
            addCriterion("sender_id <>", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThan(Integer value) {
            addCriterion("sender_id >", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_id >=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThan(Integer value) {
            addCriterion("sender_id <", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThanOrEqualTo(Integer value) {
            addCriterion("sender_id <=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdIn(List<Integer> values) {
            addCriterion("sender_id in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotIn(List<Integer> values) {
            addCriterion("sender_id not in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdBetween(Integer value1, Integer value2) {
            addCriterion("sender_id between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_id not between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNull() {
            addCriterion("receiver_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNotNull() {
            addCriterion("receiver_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdEqualTo(Integer value) {
            addCriterion("receiver_id =", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotEqualTo(Integer value) {
            addCriterion("receiver_id <>", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThan(Integer value) {
            addCriterion("receiver_id >", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("receiver_id >=", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThan(Integer value) {
            addCriterion("receiver_id <", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThanOrEqualTo(Integer value) {
            addCriterion("receiver_id <=", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIn(List<Integer> values) {
            addCriterion("receiver_id in", values, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotIn(List<Integer> values) {
            addCriterion("receiver_id not in", values, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdBetween(Integer value1, Integer value2) {
            addCriterion("receiver_id between", value1, value2, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotBetween(Integer value1, Integer value2) {
            addCriterion("receiver_id not between", value1, value2, "receiverId");
            return (Criteria) this;
        }

        public Criteria andRelateIdIsNull() {
            addCriterion("relate_id is null");
            return (Criteria) this;
        }

        public Criteria andRelateIdIsNotNull() {
            addCriterion("relate_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelateIdEqualTo(Integer value) {
            addCriterion("relate_id =", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotEqualTo(Integer value) {
            addCriterion("relate_id <>", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdGreaterThan(Integer value) {
            addCriterion("relate_id >", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("relate_id >=", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdLessThan(Integer value) {
            addCriterion("relate_id <", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdLessThanOrEqualTo(Integer value) {
            addCriterion("relate_id <=", value, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdIn(List<Integer> values) {
            addCriterion("relate_id in", values, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotIn(List<Integer> values) {
            addCriterion("relate_id not in", values, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdBetween(Integer value1, Integer value2) {
            addCriterion("relate_id between", value1, value2, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("relate_id not between", value1, value2, "relateId");
            return (Criteria) this;
        }

        public Criteria andRelateSnIsNull() {
            addCriterion("relate_sn is null");
            return (Criteria) this;
        }

        public Criteria andRelateSnIsNotNull() {
            addCriterion("relate_sn is not null");
            return (Criteria) this;
        }

        public Criteria andRelateSnEqualTo(String value) {
            addCriterion("relate_sn =", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnNotEqualTo(String value) {
            addCriterion("relate_sn <>", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnGreaterThan(String value) {
            addCriterion("relate_sn >", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnGreaterThanOrEqualTo(String value) {
            addCriterion("relate_sn >=", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnLessThan(String value) {
            addCriterion("relate_sn <", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnLessThanOrEqualTo(String value) {
            addCriterion("relate_sn <=", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnLike(String value) {
            addCriterion("relate_sn like", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnNotLike(String value) {
            addCriterion("relate_sn not like", value, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnIn(List<String> values) {
            addCriterion("relate_sn in", values, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnNotIn(List<String> values) {
            addCriterion("relate_sn not in", values, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnBetween(String value1, String value2) {
            addCriterion("relate_sn between", value1, value2, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateSnNotBetween(String value1, String value2) {
            addCriterion("relate_sn not between", value1, value2, "relateSn");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIsNull() {
            addCriterion("relate_type is null");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIsNotNull() {
            addCriterion("relate_type is not null");
            return (Criteria) this;
        }

        public Criteria andRelateTypeEqualTo(Byte value) {
            addCriterion("relate_type =", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotEqualTo(Byte value) {
            addCriterion("relate_type <>", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeGreaterThan(Byte value) {
            addCriterion("relate_type >", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("relate_type >=", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeLessThan(Byte value) {
            addCriterion("relate_type <", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("relate_type <=", value, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeIn(List<Byte> values) {
            addCriterion("relate_type in", values, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotIn(List<Byte> values) {
            addCriterion("relate_type not in", values, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeBetween(Byte value1, Byte value2) {
            addCriterion("relate_type between", value1, value2, "relateType");
            return (Criteria) this;
        }

        public Criteria andRelateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("relate_type not between", value1, value2, "relateType");
            return (Criteria) this;
        }

        public Criteria andTypeStrIsNull() {
            addCriterion("type_str is null");
            return (Criteria) this;
        }

        public Criteria andTypeStrIsNotNull() {
            addCriterion("type_str is not null");
            return (Criteria) this;
        }

        public Criteria andTypeStrEqualTo(String value) {
            addCriterion("type_str =", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrNotEqualTo(String value) {
            addCriterion("type_str <>", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrGreaterThan(String value) {
            addCriterion("type_str >", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrGreaterThanOrEqualTo(String value) {
            addCriterion("type_str >=", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrLessThan(String value) {
            addCriterion("type_str <", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrLessThanOrEqualTo(String value) {
            addCriterion("type_str <=", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrLike(String value) {
            addCriterion("type_str like", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrNotLike(String value) {
            addCriterion("type_str not like", value, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrIn(List<String> values) {
            addCriterion("type_str in", values, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrNotIn(List<String> values) {
            addCriterion("type_str not in", values, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrBetween(String value1, String value2) {
            addCriterion("type_str between", value1, value2, "typeStr");
            return (Criteria) this;
        }

        public Criteria andTypeStrNotBetween(String value1, String value2) {
            addCriterion("type_str not between", value1, value2, "typeStr");
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andRepeatTimesIsNull() {
            addCriterion("repeat_times is null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesIsNotNull() {
            addCriterion("repeat_times is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesEqualTo(String value) {
            addCriterion("repeat_times =", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotEqualTo(String value) {
            addCriterion("repeat_times <>", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesGreaterThan(String value) {
            addCriterion("repeat_times >", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesGreaterThanOrEqualTo(String value) {
            addCriterion("repeat_times >=", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesLessThan(String value) {
            addCriterion("repeat_times <", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesLessThanOrEqualTo(String value) {
            addCriterion("repeat_times <=", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesLike(String value) {
            addCriterion("repeat_times like", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotLike(String value) {
            addCriterion("repeat_times not like", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesIn(List<String> values) {
            addCriterion("repeat_times in", values, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotIn(List<String> values) {
            addCriterion("repeat_times not in", values, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesBetween(String value1, String value2) {
            addCriterion("repeat_times between", value1, value2, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotBetween(String value1, String value2) {
            addCriterion("repeat_times not between", value1, value2, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
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

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRetIsNull() {
            addCriterion("ret is null");
            return (Criteria) this;
        }

        public Criteria andRetIsNotNull() {
            addCriterion("ret is not null");
            return (Criteria) this;
        }

        public Criteria andRetEqualTo(String value) {
            addCriterion("ret =", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotEqualTo(String value) {
            addCriterion("ret <>", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetGreaterThan(String value) {
            addCriterion("ret >", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetGreaterThanOrEqualTo(String value) {
            addCriterion("ret >=", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLessThan(String value) {
            addCriterion("ret <", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLessThanOrEqualTo(String value) {
            addCriterion("ret <=", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLike(String value) {
            addCriterion("ret like", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotLike(String value) {
            addCriterion("ret not like", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetIn(List<String> values) {
            addCriterion("ret in", values, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotIn(List<String> values) {
            addCriterion("ret not in", values, "ret");
            return (Criteria) this;
        }

        public Criteria andRetBetween(String value1, String value2) {
            addCriterion("ret between", value1, value2, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotBetween(String value1, String value2) {
            addCriterion("ret not between", value1, value2, "ret");
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