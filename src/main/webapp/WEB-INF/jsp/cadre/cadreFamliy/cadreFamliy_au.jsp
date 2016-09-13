<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CADRE_FAMLIY_TITLE_MAP" value="<%=SystemConstants.CADRE_FAMLIY_TITLE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreFamliy!=null}">编辑</c:if><c:if test="${cadreFamliy==null}">添加</c:if>家庭成员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreFamliy_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreFamliy.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">称谓</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="title" data-placeholder="请选择">
                        <option></option>
                        <c:forEach var="famliyTitle" items="${CADRE_FAMLIY_TITLE_MAP}">
                            <option value="${famliyTitle.key}">${famliyTitle.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modal select[name=title]").val("${cadreFamliy.title}");
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${cadreFamliy.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出生年月</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_birthday" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy-mm" value="${cm:formatDate(cadreFamliy.birthday,'yyyy-MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">

                    <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_political_status"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=politicalStatus]").val(${cadreFamliy.politicalStatus});
                    </script>

				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工作单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreFamliy.unit}">
				</div>
			</div>
            <div class="form-group">
                <span class="col-xs-offset-3 col-xs-6" id="hint">

                </span>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreFamliy!=null}">确定</c:if><c:if test="${cadreFamliy==null}">添加</c:if>"/>
</div>

<script>

    register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreFamliy").trigger("reloadGrid");
                    }
                }
            });
        }
    });

    $('#modalForm [data-rel="select2"]').select2();
    $('#modalForm select[name=title]').change(function(){
        //alert($(this).val())
        var hint = "";
        switch (parseInt($(this).val())) {
            case 1:
            case 2:// 父亲或母亲
                hint = "请填写父亲、母亲现工作的单位、部门及职务。如果父亲或目前已退休或者已去世，请填写退休前的单位、部门及职务。退休请注明（已退休），去世请注明（已去世）”。示例：“**省**市教育局**科**科长（已退休）";
                break;
            case 3:
                hint = "请填写配偶工作的单位、部门及职务。";
                break;
            case 4:
            case 5:
                hint = "子女已参加工作的，请填写工作单位、部门及职务。子女未参加工作的，请填写就读学校、学院、专业和年级”。示例：“<fmt:message key="site.school" bundle="${spring}"/>**学院**专业硕士研究生二年级";
                break;
        }
        $("#hint").html(hint);
    }).change();

    $('[data-rel="tooltip"]').tooltip();
</script>