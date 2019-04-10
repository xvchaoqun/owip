<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_STATUS_BACK" value="<%=MemberConstants.MEMBER_STAY_STATUS_BACK%>"/>
<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>

<div class="row" style="width: 900px">

    <c:if test="${memberStay.type==hasSubmitType && memberStay.status<=MEMBER_STAY_STATUS_BACK}">
        <div class="alert alert-danger">
            <button type="button" class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
            </button>
            <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if
                test="${not empty memberStay.reason}">: ${memberStay.reason}</c:if>
            <br>
        </div>
    </c:if>
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-paw blue"></i> ${MEMBER_STAY_TYPE_MAP.get(type)}申请组织关系暂留
                <c:if test="${param.auth=='admin'}">
                <a href="javascript:;" class="hideView btn btn-xs btn-success pull-right"
                   style="margin-top: 10px;margin-left: 10px;">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
                </c:if>
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal" action="${ctx}/user/memberStay_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                    <input type="hidden" name="id" value="${memberStay.id}">
                    <input type="hidden" name="userId" value="${param.userId}">
                    <input type="hidden" name="type" value="${param.type}">

                    <div class="row">
                        <c:if test="${type==MEMBER_STAY_TYPE_ABROAD}">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>人员类别</label>

                                    <div class="col-xs-6">
                                        <select required data-rel="select2" name="userType" data-placeholder="请选择">
                                            <option></option>
                                            <jsp:include page="/metaTypes?__code=mc_abroad_user_type"/>
                                        </select>
                                        <script type="text/javascript">
                                            $("#modalForm select[name=userType]").val(${memberStay.userType});
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>手机</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="20" type="text" name="mobile"
                                               value="${memberStay.mobile}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>家庭电话</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="20" type="text" name="phone"
                                               value="${memberStay.phone}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>微信</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" type="text" maxlength="20" name="weixin"
                                               value="${memberStay.weixin}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>电子邮箱</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control email" maxlength="50" type="text"
                                               name="email"
                                               value="${memberStay.email}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>QQ号</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="20" type="text" name="qq"
                                               value="${memberStay.qq}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>申请保留组织关系起始时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="saveStartTime"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.saveStartTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>申请保留组织关系截止时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="saveEndTime"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.saveEndTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>党费交纳截止时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker"
                                                   name="payTime" type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.payTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label">${empty memberStay.letter?'<span class="star">*</span>':''}接收函/邀请函<c:if
                                            test="${not empty memberStay.letter}">
                                        <a class="various" title="接收函/邀请函"
                                           data-path="${cm:encodeURI(memberStay.letter)}"
                                           data-fancybox-type="image"
                                           href="${ctx}/pic?path=${memberStay.letter}">(查看)</a>
                                    </c:if>
                                    </label>

                                    <div class="col-xs-6 uploader">
                                        <input ${empty memberStay.letter?'required':''} class="form-control" type="file"
                                                                                        name="_letter"/>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>去往国家</label>

                                    <div class="col-xs-6">
                                        <select required name="country" data-rel="select2" data-placeholder="请选择">
                                            <option></option>
                                            <c:forEach var="entity" items="${countryMap}">
                                                <option value="${entity.value.cninfo}">${entity.value.cninfo}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#modalForm select[name=country]").val("${memberStay.country}");
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label">出国原因</label>

                                    <div class="col-xs-6 choice">
                                        <input name="_reason" type="checkbox" value="留学"> 留学&nbsp;
                                        <input name="_reason" type="checkbox" value="访学"> 访学&nbsp;
                                        <input name="_reason" type="checkbox" value="工作"> 工作&nbsp;
                                        <br/>
                                        <input name="_reason" type="checkbox" value="其他"> 其他
                                        <input name="_reason_other" type="text" maxlength="50" size="18">
                                        <input name="stayReason" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>国内通讯地址</label>

                                    <div class="col-xs-6">
                                    <input required class="form-control" name="inAddress"
                                              maxlength="100" value="${memberStay.inAddress}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>国外通讯地址</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" name="outAddress"
                                               maxlength="100" value="${memberStay.outAddress}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>留学方式</label>

                                    <div class="col-xs-6">
                                        <select required data-rel="select2" name="abroadType" data-placeholder="请选择"
                                                data-width="100%">
                                            <option></option>
                                            <c:forEach items="<%=MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_MAP%>" var="_type">
                                                <option value="${_type.key}">${_type.value}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#modalForm select[name=abroadType]").val(${memberStay.abroadType});
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>留学学校或工作单位</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="50" type="text" name="school"
                                               value="${memberStay.school}">
                                    </div>
                                </div>


                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>出国起始时间</label>

                                    <div class="col-xs-6" style="z-index: 10;">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="startTime"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.startTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>出国截止时间</label>

                                    <div class="col-xs-6" style="z-index: 10;">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="endTime" type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.endTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>预计回国时间</label>

                                    <div class="col-xs-6" style="z-index: 10;">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="overDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.overDate,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>

                                <fieldset>
                                    <legend>国内第一联系人</legend>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text" name="name1"
                                                   value="${memberStay.name1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>与本人关系</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="relate1"
                                                   value="${memberStay.relate1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>单位</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="100" type="text"
                                                   name="unit1"
                                                   value="${memberStay.unit1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>职务</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="50" type="text" name="post1"
                                                   value="${memberStay.post1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>办公电话</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="phone1"
                                                   value="${memberStay.phone1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>手机号</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="mobile1"
                                                   value="${memberStay.mobile1}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>电子邮箱</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control email" maxlength="150" type="text"
                                                   name="email1" value="${memberStay.email1}">
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset style="margin-bottom: 10px">
                                    <legend>国内第二联系人</legend>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text" name="name2"
                                                   value="${memberStay.name2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>与本人关系</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="relate2"
                                                   value="${memberStay.relate2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>单位</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="100" type="text"
                                                   name="unit2"
                                                   value="${memberStay.unit2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>职务</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="50" type="text" name="post2"
                                                   value="${memberStay.post2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>办公电话</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="phone2"
                                                   value="${memberStay.phone2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>手机号</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control" maxlength="20" type="text"
                                                   name="mobile2"
                                                   value="${memberStay.mobile2}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label"><span class="star">*</span>电子邮箱</label>

                                        <div class="col-xs-6">
                                            <input required class="form-control email" maxlength="50" type="text"
                                                   name="email2" value="${memberStay.email2}">
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </c:if>
                        <c:if test="${type==MEMBER_STAY_TYPE_INTERNAL}">

                            <div class="col-xs-5">
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>人员类别</label>

                                    <div class="col-xs-7">
                                        <select required data-rel="select2" name="userType" data-placeholder="请选择">
                                            <option></option>
                                            <jsp:include page="/metaTypes?__code=mc_abroad_user_type"/>
                                        </select>
                                        <script type="text/javascript">
                                            $("#modalForm select[name=userType]").val(${memberStay.userType});
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>手机</label>

                                    <div class="col-xs-7">
                                        <input required class="form-control" maxlength="20" type="text" name="mobile"
                                               value="${memberStay.mobile}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>家庭电话</label>

                                    <div class="col-xs-7">
                                        <input required class="form-control" maxlength="20" type="text" name="phone"
                                               value="${memberStay.phone}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>微信</label>

                                    <div class="col-xs-7">
                                        <input required class="form-control" type="text" maxlength="20" name="weixin"
                                               value="${memberStay.weixin}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>电子邮箱</label>

                                    <div class="col-xs-7">
                                        <input required class="form-control email" maxlength="50" type="text"
                                               name="email"
                                               value="${memberStay.email}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>QQ号</label>

                                    <div class="col-xs-7">
                                        <input required class="form-control" maxlength="20" type="text" name="qq"
                                               value="${memberStay.qq}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>暂留原因</label>
                                    <div class="col-xs-7">
                                    <textarea required class="form-control limited" rows="3" name="stayReason"
                                              maxlength="200">${memberStay.stayReason}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-6 control-label">${empty memberStay.letter?'<span class="star">*</span>':''}户档暂留证明<c:if
                                            test="${not empty memberStay.letter}">
                                        <a class="various" title="户档暂留证明"
                                           data-path="${cm:encodeURI(memberStay.letter)}"
                                           data-fancybox-type="image"
                                           href="${ctx}/pic?path=${memberStay.letter}">(查看)</a>
                                    </c:if>
                                    </label>

                                    <div class="col-xs-6 uploader">
                                        <input ${empty memberStay.letter?'required':''} class="form-control" type="file"
                                                                                        name="_letter"/>

                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>申请保留组织关系起始时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="saveStartTime"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.saveStartTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>申请保留组织关系截止时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="saveEndTime"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.saveEndTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>党费交纳截止时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker"
                                                   name="payTime" type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.payTime,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>预计转出时间</label>

                                    <div class="col-xs-6">
                                        <div class="input-group" style="z-index: 1030">
                                            <input required class="form-control date-picker" name="overDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm"
                                                   data-date-min-view-mode="1"
                                                   value="${cm:formatDate(memberStay.overDate,'yyyy-MM')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>联系人姓名</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="20" type="text" name="name1"
                                               value="${memberStay.name1}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-6 control-label"><span class="star">*</span>联系人手机号</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" maxlength="20" type="text"
                                               name="mobile1"
                                               value="${memberStay.mobile1}">
                                    </div>
                                </div>
                            </div>
                        </c:if>

                    </div>

                    <div class="clearfix form-actions center">

                        <c:if test="${!canSubmit}">
                                已经提交了${MEMBER_STAY_TYPE_MAP.get(hasSubmitType)}组织关系暂留申请
                        </c:if>
                        <c:if test="${canSubmit}">
                            <button class="btn btn-info" id="submitBtn" type="button" data-loading-text="提交中..."
                                    autocomplete="off">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                提交
                            </button>
                        </c:if>
                        &nbsp;&nbsp;
                        <c:if test="${param.auth=='admin'}">
                            <a href="javascript:;" class="hideView btn btn-default">
                                <i class="ace-icon fa fa-reply"></i> 返回</a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<style>
    fieldset {
        border: 1px solid #c0c0c0 !important;
        text-align: center;
    }

    legend {
        width: auto !important;
        border-bottom: none !important;
        margin-bottom: 11px !important;
    }
</style>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>
    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
    <c:if test="${type==MEMBER_STAY_TYPE_ABROAD}">
    <c:forEach var="reason" items="${fn:split(memberStay.stayReason,'+++')}">
    $("input[name=_reason][value='${reason}']").prop("checked", true);
    <c:if test="${fn:startsWith(reason, '其他:')}">
    $("#modalForm input[name=_reason][value='其他']").prop("checked", true);
    $("input[name=_reason_other]").val('${fn:substringAfter(reason, '其他:')}');
    </c:if>
    </c:forEach>
    </c:if>

    $.fileInput($('#modalForm input[name=_letter]'),{
        no_file: '请选择jpg或png图片文件...',
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    })

    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function () {
        var $btn = $(this).button('loading');
        $("#modalForm").submit();
        setTimeout(function () {
            $btn.button('reset');
        }, 1000);
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            <c:if test="${type==MEMBER_STAY_TYPE_ABROAD}">
            // 出国原因
            var $_reason = $("#modalForm input[name=_reason][value='其他']");
            var _reason_other = $("input[name=_reason_other]").val().trim();
            if ($_reason.is(":checked")) {
                if (_reason_other == '') {
                    SysMsg.info("请输入其他出国原因", '', function () {
                        $("#modalForm input[name=_reason_other]").val('').focus();
                    });
                    return;
                }
            }
            var reasons = [];
            $.each($("#modalForm input[name=_reason]:checked"), function () {
                if ($(this).val() == '其他') {
                    reasons.push("其他:" + _reason_other);
                } else
                    reasons.push($(this).val());
            });
            if (reasons.length == 0) {
                SysMsg.info("请选择出国原因");
                return;
            }
            $("#modalForm input[name=stayReason]").val(reasons.join("+++"));
            </c:if>

            $(form).ajaxSubmit({
                success: function (ret) {
                    $("#submitBtn").button("reset");
                    if (ret.success) {
                        bootbox.alert('提交成功。', function () {
                            $.hashchange();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>