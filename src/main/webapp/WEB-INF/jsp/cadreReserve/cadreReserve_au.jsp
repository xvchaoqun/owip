<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=CadreConstants.CADRE_RESERVE_TYPE_MAP%>" var="CADRE_RESERVE_TYPE_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReserve!=null}">编辑</c:if><c:if test="${cadreReserve==null}">添加</c:if>${CADRE_RESERVE_TYPE_MAP.get(reserveType)}
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReserve_au" id="modalForm" method="post">
        <input type="hidden" name="reserveId" value="${cadreReserve.id}">
        <input type="hidden" name="reserveType" value="${reserveType}"/>
        <div class="form-group">
            <label class="col-xs-4 control-label">类别</label>
            <div class="col-xs-6">
                <input  name="_isCadre"type="radio" value="0" class="big" ${CADRE_STATUS_SET.get(cadre.status)==null?'checked':''}> 非干部&nbsp;
                <input name="_isCadre" type="radio" value="1" class="big" ${CADRE_STATUS_SET.get(cadre.status)!=null?'checked':''}> 干部
            </div>
        </div>
        <div id="_isCadreDiv" style="${CADRE_STATUS_SET.get(cadre.status)==null?'display: none':''}">
            <div class="form-group">
                <label class="col-xs-4 control-label">选择干部</label>
                <div class="col-xs-6">
                    <select ${CADRE_STATUS_SET.get(cadre.status)!=null?'required':''} data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                        <c:if test="${CADRE_STATUS_SET.get(cadre.status)!=null}">
                        <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                        </c:if>
                    </select>
                </div>
            </div>
        </div>
        <div id="_notCadreDiv" style="${CADRE_STATUS_SET.get(cadre.status)!=null?'display: none':''}">
			<div class="form-group">
				<label class="col-xs-4 control-label">账号</label>
				<div class="col-xs-6">
                    <select ${CADRE_STATUS_SET.get(cadre.status)==null?'required':''} data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                    </select>
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
			<div class="form-group">
				<label class="col-xs-4 control-label">所在单位及职务</label>
				<div class="col-xs-6">
                        <input  class="form-control" type="text" name="title" value="${cadre.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="reserveRemark" rows="5">${cadreReserve.remark}</textarea>
				</div>
			</div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreReserve!=null}">确定</c:if><c:if test="${cadreReserve==null}">添加</c:if>"/>
</div>

<script>

    $("input[name=_isCadre]").click(function(){
        var val = $(this).val();
        if(val==0){
            $("select[name=cadreId]").removeAttr("required");
            $("select[name=userId]").attr("required", "required");
            $("#_isCadreDiv").hide();
            $("#_notCadreDiv").show();
        }
        if(val==1){
            $("select[name=userId]").removeAttr("required");
            $("select[name=cadreId]").attr("required", "required");
            $("#_isCadreDiv").show();
            $("#_notCadreDiv").hide();
        }
    });

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>