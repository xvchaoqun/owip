<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty param.partyIds}" var="isNoticeParty"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${isNoticeParty?"未报送党委":"未报送党支部"}</h3>
</div>
<div class="modal-body rownumbers">
    <div class="space-4"></div>
    <div style="max-height: 400px; overflow-y: scroll">
        <c:if test="${isNoticeParty}">
            <c:forEach items="${partyList}" var="party">
                <div>${party.name}</div>
            </c:forEach>
        </c:if>
        <c:if test="${!isNoticeParty}">
            <c:forEach items="${branchList}" var="branch">
                <div>${branch.name}</div>
            </c:forEach>
        </c:if>
    </div>
    <div class="space-4"></div>
    <div class="form-group">
        <label class="col-xs-2 control-label" style="padding-left: 20px!important;padding-top: 12px!important;"> 提醒内容</label>
        <div class="col-xs-9">
            <textarea id="notice" class="form-control" rows="5" type="text" name="notice">${tpl.content}</textarea>
        </div>
    </div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="发送提醒"/>
</div>
<script>
    $("#modal #selectBtn").click(function(){
        $.post("${ctx}/pm/pm3Guide_notice",
            {
                partyIds:'${param.partyIds}',
                id:${param.id},
                notice:$("#notice").val()
            },function(ret){
                if(ret.success){
                    SysMsg.success('发送提醒成功！');
                }
                $("#modal").modal('hide');
            })
        });
</script>