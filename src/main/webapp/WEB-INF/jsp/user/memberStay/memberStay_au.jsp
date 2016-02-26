<%@ page import="sys.constants.SystemConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:if test="${memberStay.status==MEMBER_STAY_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong>
            <i class="ace-icon fa fa-times"></i>
            返回修改
        </strong>
        <c:if test="${not empty memberStay.reason}">
            :${memberStay.reason}
        </c:if>

        <br>
    </div>
</c:if>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 组织关系暂留申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberStay_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberStay.id}">
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"> ${(userBean.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
                            <div class="col-sm-6">
                                <input disabled type="text" value="${userBean.code}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">姓名</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" name="realname" value="${userBean.realname}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">性别</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" value="${GENDER_MAP.get(userBean.gender)}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">年龄</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control"
                                       type="text" name="age" value="${cm:intervalYearsUntilNow(userBean.birth)}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">民族</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" name="nation" value="${userBean.nation}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">身份证</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">政治面貌</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" value="${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">入党时间</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" name="_growTime" type="text"
                                       value="${cm:formatDate(userBean.growTime,'yyyy-MM-dd')}" />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">

                        <div class="form-group">
                            <label class="col-xs-5 control-label">留学国别</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="country" value="${memberStay.country}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">出国时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_abroadTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.abroadTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">预计回国时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_returnTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.returnTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">党费缴纳至年月</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_payTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.payTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-5 control-label">手机号码</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="mobile" value="${memberStay.mobile}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">电子邮箱</label>
                            <div class="col-xs-6">
                                <input required class="form-control email" type="text" name="email" value="${memberStay.email}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">国内联系人姓名</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="contactName" value="${memberStay.contactName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">国内联系人手机号码</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="contactMobile" value="${memberStay.contactMobile}">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix form-actions center">
                    <button class="btn btn-info" type="submit">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>

    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        bootbox.alert('提交成功。',function(){
                            _reload();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
