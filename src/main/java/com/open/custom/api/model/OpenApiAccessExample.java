package com.open.custom.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenApiAccessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpenApiAccessExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andApiMethodIsNull() {
            addCriterion("API_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andApiMethodIsNotNull() {
            addCriterion("API_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andApiMethodEqualTo(String value) {
            addCriterion("API_METHOD =", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodNotEqualTo(String value) {
            addCriterion("API_METHOD <>", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodGreaterThan(String value) {
            addCriterion("API_METHOD >", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodGreaterThanOrEqualTo(String value) {
            addCriterion("API_METHOD >=", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodLessThan(String value) {
            addCriterion("API_METHOD <", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodLessThanOrEqualTo(String value) {
            addCriterion("API_METHOD <=", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodLike(String value) {
            addCriterion("API_METHOD like", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodNotLike(String value) {
            addCriterion("API_METHOD not like", value, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodIn(List<String> values) {
            addCriterion("API_METHOD in", values, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodNotIn(List<String> values) {
            addCriterion("API_METHOD not in", values, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodBetween(String value1, String value2) {
            addCriterion("API_METHOD between", value1, value2, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodNotBetween(String value1, String value2) {
            addCriterion("API_METHOD not between", value1, value2, "apiMethod");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassIsNull() {
            addCriterion("API_METHOD_CLASS is null");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassIsNotNull() {
            addCriterion("API_METHOD_CLASS is not null");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassEqualTo(String value) {
            addCriterion("API_METHOD_CLASS =", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassNotEqualTo(String value) {
            addCriterion("API_METHOD_CLASS <>", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassGreaterThan(String value) {
            addCriterion("API_METHOD_CLASS >", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassGreaterThanOrEqualTo(String value) {
            addCriterion("API_METHOD_CLASS >=", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassLessThan(String value) {
            addCriterion("API_METHOD_CLASS <", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassLessThanOrEqualTo(String value) {
            addCriterion("API_METHOD_CLASS <=", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassLike(String value) {
            addCriterion("API_METHOD_CLASS like", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassNotLike(String value) {
            addCriterion("API_METHOD_CLASS not like", value, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassIn(List<String> values) {
            addCriterion("API_METHOD_CLASS in", values, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassNotIn(List<String> values) {
            addCriterion("API_METHOD_CLASS not in", values, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassBetween(String value1, String value2) {
            addCriterion("API_METHOD_CLASS between", value1, value2, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andApiMethodClassNotBetween(String value1, String value2) {
            addCriterion("API_METHOD_CLASS not between", value1, value2, "apiMethodClass");
            return (Criteria) this;
        }

        public Criteria andAccessDateIsNull() {
            addCriterion("ACCESS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAccessDateIsNotNull() {
            addCriterion("ACCESS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAccessDateEqualTo(Date value) {
            addCriterion("ACCESS_DATE =", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotEqualTo(Date value) {
            addCriterion("ACCESS_DATE <>", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateGreaterThan(Date value) {
            addCriterion("ACCESS_DATE >", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ACCESS_DATE >=", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateLessThan(Date value) {
            addCriterion("ACCESS_DATE <", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateLessThanOrEqualTo(Date value) {
            addCriterion("ACCESS_DATE <=", value, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateIn(List<Date> values) {
            addCriterion("ACCESS_DATE in", values, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotIn(List<Date> values) {
            addCriterion("ACCESS_DATE not in", values, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateBetween(Date value1, Date value2) {
            addCriterion("ACCESS_DATE between", value1, value2, "accessDate");
            return (Criteria) this;
        }

        public Criteria andAccessDateNotBetween(Date value1, Date value2) {
            addCriterion("ACCESS_DATE not between", value1, value2, "accessDate");
            return (Criteria) this;
        }

        public Criteria andDuraTimeIsNull() {
            addCriterion("DURA_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDuraTimeIsNotNull() {
            addCriterion("DURA_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDuraTimeEqualTo(Integer value) {
            addCriterion("DURA_TIME =", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeNotEqualTo(Integer value) {
            addCriterion("DURA_TIME <>", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeGreaterThan(Integer value) {
            addCriterion("DURA_TIME >", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("DURA_TIME >=", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeLessThan(Integer value) {
            addCriterion("DURA_TIME <", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeLessThanOrEqualTo(Integer value) {
            addCriterion("DURA_TIME <=", value, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeIn(List<Integer> values) {
            addCriterion("DURA_TIME in", values, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeNotIn(List<Integer> values) {
            addCriterion("DURA_TIME not in", values, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeBetween(Integer value1, Integer value2) {
            addCriterion("DURA_TIME between", value1, value2, "duraTime");
            return (Criteria) this;
        }

        public Criteria andDuraTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("DURA_TIME not between", value1, value2, "duraTime");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNull() {
            addCriterion("CLIENT_IP is null");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNotNull() {
            addCriterion("CLIENT_IP is not null");
            return (Criteria) this;
        }

        public Criteria andClientIpEqualTo(String value) {
            addCriterion("CLIENT_IP =", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotEqualTo(String value) {
            addCriterion("CLIENT_IP <>", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThan(String value) {
            addCriterion("CLIENT_IP >", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThanOrEqualTo(String value) {
            addCriterion("CLIENT_IP >=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThan(String value) {
            addCriterion("CLIENT_IP <", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThanOrEqualTo(String value) {
            addCriterion("CLIENT_IP <=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLike(String value) {
            addCriterion("CLIENT_IP like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotLike(String value) {
            addCriterion("CLIENT_IP not like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpIn(List<String> values) {
            addCriterion("CLIENT_IP in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotIn(List<String> values) {
            addCriterion("CLIENT_IP not in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpBetween(String value1, String value2) {
            addCriterion("CLIENT_IP between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotBetween(String value1, String value2) {
            addCriterion("CLIENT_IP not between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andHttpMethodIsNull() {
            addCriterion("HTTP_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andHttpMethodIsNotNull() {
            addCriterion("HTTP_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andHttpMethodEqualTo(String value) {
            addCriterion("HTTP_METHOD =", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodNotEqualTo(String value) {
            addCriterion("HTTP_METHOD <>", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodGreaterThan(String value) {
            addCriterion("HTTP_METHOD >", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodGreaterThanOrEqualTo(String value) {
            addCriterion("HTTP_METHOD >=", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodLessThan(String value) {
            addCriterion("HTTP_METHOD <", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodLessThanOrEqualTo(String value) {
            addCriterion("HTTP_METHOD <=", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodLike(String value) {
            addCriterion("HTTP_METHOD like", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodNotLike(String value) {
            addCriterion("HTTP_METHOD not like", value, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodIn(List<String> values) {
            addCriterion("HTTP_METHOD in", values, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodNotIn(List<String> values) {
            addCriterion("HTTP_METHOD not in", values, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodBetween(String value1, String value2) {
            addCriterion("HTTP_METHOD between", value1, value2, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andHttpMethodNotBetween(String value1, String value2) {
            addCriterion("HTTP_METHOD not between", value1, value2, "httpMethod");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNull() {
            addCriterion("APP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNotNull() {
            addCriterion("APP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAppCodeEqualTo(String value) {
            addCriterion("APP_CODE =", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotEqualTo(String value) {
            addCriterion("APP_CODE <>", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThan(String value) {
            addCriterion("APP_CODE >", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThanOrEqualTo(String value) {
            addCriterion("APP_CODE >=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThan(String value) {
            addCriterion("APP_CODE <", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThanOrEqualTo(String value) {
            addCriterion("APP_CODE <=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLike(String value) {
            addCriterion("APP_CODE like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotLike(String value) {
            addCriterion("APP_CODE not like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeIn(List<String> values) {
            addCriterion("APP_CODE in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotIn(List<String> values) {
            addCriterion("APP_CODE not in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeBetween(String value1, String value2) {
            addCriterion("APP_CODE between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotBetween(String value1, String value2) {
            addCriterion("APP_CODE not between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("USER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("USER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("USER_CODE =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("USER_CODE <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("USER_CODE >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("USER_CODE >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("USER_CODE <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("USER_CODE <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("USER_CODE like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("USER_CODE not like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("USER_CODE in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("USER_CODE not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("USER_CODE between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("USER_CODE not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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