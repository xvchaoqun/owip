<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>关联岗位信息</h3>
</div>
<div class="modal-body" style="overflow-x: scroll;">
<jsp:include page="/WEB-INF/jsp/unit/unitPostGroup/unitPost_table.jsp"/>
</div>
<div class="modal-footer">
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <div class="note">注：此处可选择一条或多条岗位信息</div>
    </shiro:lacksPermission>
    </shiro:hasPermission>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <c:if test="${type=='edit'}">
        <input type="button" onclick="addPost()" class="btn btn-primary" value="保存"/>
    </c:if>
    <c:if test="${type!='edit'}">
        <button class="popupBtn btn btn-success"
                data-url="${ctx}/unitPostGroup_addPost?id=${param.id}&type=edit"
                data-width="1000"><i class="fa fa-reply"></i>
            重新编辑</button>
    </c:if>
    </shiro:lacksPermission>
    </shiro:hasPermission>

</div>
</div>

<script>

    var idArray =new Array();
    if(${ids!=null}){
        var ids = ${cm:toJSONObject(ids)};
        idArray = ids.split(",");
    }
    //console.log(idArray);
    function addPost(){
        $.post("${ctx}/unitPostGroup_addPost",{id:'${param.id}',ids:idArray},function(ret){
            if(ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
        });
    }
</script>