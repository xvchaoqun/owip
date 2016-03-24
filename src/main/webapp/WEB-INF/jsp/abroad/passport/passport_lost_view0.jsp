<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row passport_apply">
    <div class="preview">
        <img src="${ctx}/img?path=${passport.lostProof}" style="max-width: 595px"/>
    </div>
    <div class="info">
        <div class="center" style="margin-top: 40px">
            <button id="print_proof" class="btn btn-info btn-block" style="font-size: 30px">打印证明</button>
            <a href="${ctx}/${param.type=='user'?'user/':''}passport_lostProof_download?id=${passport.id}" target="_blank"
               class="btn btn-primary btn-block" style="font-size: 30px">下载</a>
            <shiro:hasAnyRoles name="admin,cadreAdmin">
                <button id="updateLostProof" class="btn btn-warning btn-block" style="font-size: 30px">重新上传</button>
            </shiro:hasAnyRoles>
            <button class="closeView reload btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回
            </button>
        </div>
    </div>
</div>
<script>
    $("#print_proof").click(function () {
        printWindow('${ctx}/img?path=${fn:replace(passport.lostProof, "\\","\\/"  )}');
    });
    $("#updateLostProof").click(function(){
        loadModal("${ctx}/updateLostProof?id=${passport.id}")
    });
</script>