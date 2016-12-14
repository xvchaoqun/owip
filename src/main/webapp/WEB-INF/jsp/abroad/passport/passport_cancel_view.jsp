<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px">

            <div class="buttons">
                <a href="javascript:;" class="closeView btn btn-sm btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
<c:if test="${not empty passport.cancelPic}">
                <button id="print_proof" class="btn btn-info btn-sm" style="margin-left: 50px"><i class="fa fa-print"></i>  打印证明</button>
                <a href="${ctx}/${param.type=='user'?'user/':''}passport_cancelPic_download?id=${passport.id}" target="_blank"
                   class="btn btn-primary btn-sm"><i class="fa fa-download"></i> 下载</a>
    </c:if>
                <shiro:hasAnyRoles name="admin,cadreAdmin">
                    <button id="updateProof" class="btn btn-warning btn-sm"><i class="fa fa-upload"></i>  重新上传</button>
                </shiro:hasAnyRoles>
            </div>
        </div>
    </ul>

    <c:if test="${not empty passport.cancelPic}">
    <div class="tab-content">
        <div id="home4" class="tab-pane in active">
            <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px;width: 635px; text-align: center">
                <img src="${ctx}/pic?path=${cm:encodeURI(passport.cancelPic)}" style="max-width: 595px"/>
            </div>
            </div>
        </div>
    </div>
    </c:if>
<script>
    $("#print_proof").click(function () {
        printWindow('${ctx}/pic?path=${cm:encodeURI(passport.cancelPic)}');
    });
    $("#updateProof").click(function(){
        loadModal("${ctx}/updateCancelPic?id=${passport.id}")
    });
</script>