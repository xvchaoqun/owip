<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreFamily!=null}">编辑</c:if><c:if test="${cadreFamily==null}">添加</c:if>家庭成员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreFamily_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreFamily.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">称谓</label>
				<div class="col-xs-3">
                    <select required data-rel="select2" name="title" data-placeholder="请选择" data-width="125">
                        <option></option>
                        <c:forEach var="familyTitle" items="${CADRE_FAMILY_TITLE_MAP}">
                            <option value="${familyTitle.key}">${familyTitle.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modal select[name=title]").val("${cadreFamily.title}");
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-3">
                        <input required class="form-control" type="text" name="realname" value="${cadreFamily.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出生年月</label>
				<div class="col-xs-3">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_birthday" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy-mm" value="${cm:formatDate(cadreFamily.birthday,'yyyy-MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">

                    <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择" data-width="125">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_political_status"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=politicalStatus]").val(${cadreFamily.politicalStatus});
                    </script>

				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工作单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreFamily.unit}">
				</div>
			</div>
            <div class="form-group">
                <span class="col-xs-offset-3 col-xs-6" id="hint">

                </span>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreFamily!=null}">确定</c:if><c:if test="${cadreFamily==null}">添加</c:if>"/>
</div>

<script>

    $.register.date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreFamily").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
                        </c:if>
                        </c:if>
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
                hint = "子女已参加工作的，请填写工作单位、部门及职务。子女未参加工作的，请填写就读学校、学院、专业和年级”。示例：“${_sysConfig.schoolName}**学院**专业硕士研究生二年级";
                break;
        }
        $("#hint").html(hint);
    }).change();

    $('[data-rel="tooltip"]').tooltip();
</script>