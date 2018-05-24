<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box" style="width: 1220px">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title smaller" style="font-weight: bolder">申请办理因私出国（境）证件</h4>
        <%--<div class="widget-toolbar">
            <label>
                <small class="green">
                    <b>Horizontal</b>
                </small>

                <input id="id-check-horizontal" type="checkbox" class="ace ace-switch ace-switch-6">
                <span class="lbl middle"></span>
            </label>
        </div>--%>
    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="row center"  style="text-align: center;margin: auto; ">
                <c:forEach items="${cm:getMetaTypes('mc_passport_type')}" var="passportType" varStatus="vs">
                    <div style="float: left; ${!vs.last?'padding-right: 100px;':''} ${vs.first?'padding-left: 200px;':''}">
                        <img src="/extend/img/${passportType.value.code}.jpg" width="200">
                        <div style="padding-top: 20px;padding-bottom: 20px;">
                            <input type="checkbox" class="big" name="classId" value="${passportType.key}">
                        ${passportType.value.name}</div>
                    </div>
                </c:forEach>

                </div>
        </div>
    </div>
    <div class="modal-footer center">
        <input id="next" type="button" class="btn btn-success" value="&nbsp;&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;&nbsp;"/>
        <input type="button" class="hideView btn btn-default" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"/>
    </div>
</div>

<script>
    $("input[type=checkbox]").click(function(){
        if($(this).prop("checked")){
            $("input[type=checkbox]").not(this).prop("checked", false);
        }
    });

    $("#next").click(function(){
        var classId = $('input[name=classId]:checked').val();
        if(classId==undefined || classId==''){
            SysMsg.info("请选择证件名称");
            return;
        }
        $("#apply-content").load("${ctx}/user/abroad/passportApply_confirm?classId="+classId + "&cadreId=${param.cadreId}&auth=${param.auth}");
    });
    $('form [data-rel="select2"]').select2();
</script>