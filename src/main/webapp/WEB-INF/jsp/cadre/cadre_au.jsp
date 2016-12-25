<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadre!=null}">编辑</c:if><c:if test="${cadre==null}">添加</c:if>
        <c:if test="${status==CADRE_STATUS_NOW}">现任干部</c:if>
        <c:if test="${status==CADRE_STATUS_LEAVE}">离任中层干部</c:if>
        <c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">离任校领导干部</c:if>
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <input type="hidden" name="status" value="${status}">
			<div class="form-group">
				<label class="col-xs-4 control-label">账号<c:if test="${cadre==null}">(不在干部库中)</c:if></label>
				<div class="col-xs-6 ${cadre!=null?'label-text':''}">
                    <c:if test="${cadre==null}">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                    </c:if>
                    <c:if test="${cadre!=null}">
                        <input type="hidden" name="userId" value="${sysUser.id}">
                        ${sysUser.realname}-${sysUser.code}
                    </c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select  data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=typeId]").val(${cadre.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select  data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postId]").val(${cadre.postId});
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-4 control-label">所属单位</label>
                <div class="col-xs-8">
                    <select  class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                        <option></option>
                        <c:forEach items="${unitMap}" var="unit">
                            <option value="${unit.key}">${unit.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=unitId]").val('${cadre.unitId}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">职务</label>
                <div class="col-xs-6">
                    <input  class="form-control" type="text" name="post" value="${cadre.post}">
                </div>
            </div>
    <c:if test="${cadre.id!=null && (status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
    <div class="form-group">
        <label class="col-xs-4 control-label">离任文件</label>
        <div class="col-xs-8 label-text">
            <div id="tree3"></div>
            <input type="hidden" name="dispatchCadreId">
        </div>
    </div>
    </c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label"><c:if test="${status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">离任后</c:if>所在单位及职务</label>
				<div class="col-xs-6">
                        <input  class="form-control" type="text" name="title" value="${cadre.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="remark" rows="5">${cadre.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadre!=null}">确定</c:if><c:if test="${cadre==null}">添加</c:if>"/>
</div>

<script>
    <c:if test="${cadre.id!=null && (status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
    var treeNode = ${tree};
    if(treeNode.children.length==0){
        $("#tree3").html("没有发文");
    }else{
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 2,
            children: treeNode,
            onSelect: function(select, node) {
                //node.expand(node.data.isFolder && node.isSelected());
            },
            onCustomRender: function(node) {
                if(!node.data.isFolder)
                    return "<span class='dynatree-title'>"+node.data.title
                            +"</span>&nbsp;&nbsp;<button class='openUrl btn btn-xs btn-default' data-url='${ctx}/swf/preview?type=url&path="+node.data.tooltip+"'>查看</button>"
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    }
    </c:if>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            <c:if test="${cadre.id!=null && (status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
            if(treeNode.children.length>0) {
                var selectIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                    return node.data.key;
                });
                if (selectIds.length > 1) {
                    SysMsg.warning("只能选择一个发文");
                    return;
                }
                $("#modal input[name=dispatchCadreId]").val(selectIds[0]);
            }
            </c:if>
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('[data-rel="select2-ajax"]'));
</script>