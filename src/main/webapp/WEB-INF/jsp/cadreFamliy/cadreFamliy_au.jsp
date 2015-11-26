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
                        <input required class="form-control date-picker" name="_birthday" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreFamliy.birthday,'yyyy-MM-dd')}" />
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
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreFamliy!=null}">确定</c:if><c:if test="${cadreFamliy==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>