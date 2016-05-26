<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
    <c:if test="${param.type==MEMBER_INOUT_TYPE_INSIDE}">
        <c:set var="url" value="${ctx}/report/member_in_bj?ids[]=${param['ids[]']}"/>
    </c:if>
        <c:if test="${param.type==MEMBER_INOUT_TYPE_OUTSIDE}">
            <c:set var="url" value="${ctx}/report/member_out_bj?ids[]=${param['ids[]']}"/>
        </c:if>
        <iframe id="myframe" src="${url}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </div>
    <div class="info">
        <div class="center" style="margin-top: 40px">
            <button id="print" class="btn btn-info btn-block" style="font-size: 30px">打印</button>
            <button class="closeView reload btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>
    $("#print").click(function(){
        <c:if test="${param.type==MEMBER_INOUT_TYPE_INSIDE}">
        printWindow("${ctx}/report/member_in_bj?ids[]=${param['ids[]']}");
        </c:if>
        <c:if test="${param.type==MEMBER_INOUT_TYPE_OUTSIDE}">
        printWindow("${ctx}/report/member_out_bj?ids[]=${param['ids[]']}&type=1");
        </c:if>
    });
</script>