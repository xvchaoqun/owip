<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/memberStudent_au" id="modalForm" method="post">

		<table class="table table-bordered table-striped">
			<tbody>
			<tr>
				<td>政治面貌</td>
				<td>
					${MEMBER_POLITICAL_STATUS_MAP.get(memberStudent.politicalStatus)}
				</td>
				<td>
					所属组织机构
				</td>

				<td colspan="5">
					${partyMap.get(memberStudent.partyId).name}
						<c:if test="${not empty memberStudent.branchId}">
							-${branchMap.get(memberStudent.branchId).name}
						</c:if>
				</td>

			</tr>
			<tr>
				<td>
					入党时间
				</td>
				<td>
					${cm:formatDate(memberStudent.growTime,'yyyy-MM-dd')}
				</td>
				<td>
					转正时间
				</td>
				<td  colspan="5">
					${cm:formatDate(memberStudent.positiveTime,'yyyy-MM-dd')}
				</td>
			</tr>
			<tr>
				<td>提交书面申请书时间</td>
				<td >
					${cm:formatDate(memberStudent.applyTime,'yyyy-MM-dd')}
				</td>
				<td>
					确定为入党积极分子时间
				</td>

				<td>
					${cm:formatDate(memberStudent.activeTime,'yyyy-MM-dd')}
				</td>
				<td>
					确定为发展对象时间
				</td>
				<td  colspan="3">
					${cm:formatDate(memberStudent.candidateTime,'yyyy-MM-dd')}
				</td>
			</tr>

			</tbody>
		</table>
    </form>
<style>
	.table-striped > tbody > tr:nth-of-type(odd) {
		background-color:inherit;
	}
	.table tbody tr:hover td, .table tbody tr:hover th {
		background-color:transparent;
	}
	.table-striped > tbody > tr > td:nth-of-type(odd) {
		background-color: #f9f9f9;
		text-align: right;
	}
</style>
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