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
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_BACK" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_BACK%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>

<c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-trash"></i>
        </button>
        <strong>
            <i class="ace-icon fa fa-trash"></i>
            返回修改
        </strong>
        <c:if test="${not empty memberTransfer.reason}">
            :${memberTransfer.reason}
        </c:if>

        <br>
    </div>
</c:if>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 校内组织关系转接申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberTransfer_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberTransfer.id}">
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
                                <input disabled class="form-control digits"
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
                            <label class="col-xs-3 control-label">政治面貌</label>
                            <div class="col-xs-6">

                                <input disabled class="form-control" type="text" value="${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">身份证号</label>
                            <div class="col-xs-6">
                                <input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
                            </div>
                        </div>

                    </div>
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">转出组织机构</label>
                            <div class="col-xs-6 ">
                                <textarea disabled class="form-control">${fromParty.name}<c:if test="${not empty fromBranch}">-${fromBranch.name}</c:if>
                                </textarea>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">转入分党委</label>
                            <div class="col-xs-6">
                                <select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                        name="toPartyId" data-placeholder="请选择" >
                                    <option value="${toParty.id}">${toParty.name}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" style="${(empty toBranch)?'display: none':''}" id="toBranchDiv">
                            <label class="col-xs-3 control-label">转入党支部</label>
                            <div class="col-xs-6">
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                        name="toBranchId" data-placeholder="请选择">
                                    <option value="${toBranch.id}">${toBranch.name}</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            register_party_branch_select($("#modalForm"), "toBranchDiv",
                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}',
                                    "${toParty.id}", "${toParty.classId}" , "toPartyId", "toBranchId");
                        </script>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">转出单位联系电话</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="fromPhone" value="${memberTransfer.fromPhone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">转出单位传真</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="fromFax" value="${memberTransfer.fromFax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">党费缴纳至年月</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_payTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.payTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">介绍信有效期天数</label>
                            <div class="col-xs-6">
                                <input required class="form-control digits" type="text" name="validDays" value="${memberTransfer.validDays}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">转出办理时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_fromHandleTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </div></div>
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

            if(!$("#branchDiv").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    bootbox.alert('请选择支部。');
                    return;
                }
            }
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
