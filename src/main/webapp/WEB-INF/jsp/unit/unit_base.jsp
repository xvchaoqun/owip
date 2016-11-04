<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-info-circle"></i>
			基本信息(${unit.status==1?"正在运转单位":"历史单位"})
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
				<table class="table table-unhover table-bordered table-striped">
					<tbody>
					<tr>

						<td>
							单位名称
						</td>
						<td style="min-width: 80px">
							${unit.name}
						</td>
						<td>
							单位编号
						</td>
						<td  style="min-width: 80px">
							${unit.code}
						</td>
						<td>
							单位网址
						</td>
						<td style="min-width: 80px">
							<c:if test="${not empty unit.url}">
								<a href="${unit.url}" target="_blank">${unit.url}</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>单位类型</td>
						<td >
							${unitTypeMap.get(unit.typeId).name}
						</td>
						<td>
							成立时间
						</td>
						<td>
							${cm:formatDate(unit.workTime, "yyyy-MM-dd")}
						</td>
						<td >
							备注
						</td>
						<td>
							${unit.remark}
						</td>
					</tr>
					</tbody>
				</table>


</div></div></div>

<div class="widget-box transparent">

	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-circle-o-notch fa-spin"></i>
			正在运转单位
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-unhover table-actived table-striped table-bordered table-hover">
				<thead>
				<tr>
					<th>单位编号</th>
					<th>单位名称</th>
					<th>单位类型</th>
					<th>成立时间</th>
					<th>备注</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${runUnits}" var="unit" varStatus="st">
					<tr>
						<td >${unit.code}</td>
						<td >
							<a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
									${unit.name}
							</a>
						</td>
						<td  class="hidden-480 hidden-xs">${unitTypeMap.get(unit.typeId).name}</td>
						<td  class="hidden-480 hidden-xs">${cm:formatDate(unit.workTime, "yyyy-MM-dd")}</td>

						<td  class="hidden-480 hidden-xs">${unit.remark}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-history"></i>
			历史单位
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-unhover table-actived table-striped table-bordered table-hover">
				<thead>
				<tr>
					<th>单位编号</th>
					<th>单位名称</th>
					<th>单位类型</th>
					<th>成立时间</th>
					<th>备注</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${historyUnits}" var="unit" varStatus="st">
					<tr>
						<td >${unit.code}</td>
						<td >
							<a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
									${unit.name}
							</a>
						</td>
						<td  class="hidden-480 hidden-xs">${unitTypeMap.get(unit.typeId).name}</td>
						<td  class="hidden-480 hidden-xs">${cm:formatDate(unit.workTime, "yyyy-MM-dd")}</td>

						<td  class="hidden-480 hidden-xs">${unit.remark}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>