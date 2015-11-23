<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">

		<table class="table table-bordered table-striped">
			<tbody>
			<tr>
				<td>
					任职单位
				</td>
				<td style="min-width: 80px">
					${cadre.realname}
				</td>
				<td>
					单位属性
				</td>
				<td style="min-width: 80px">
					${GENDER_MALE_MAP.get(cadre.gender)}
				</td>

				<td>
					是否双肩挑
				</td>
				<td  style="min-width: 80px">
					${cadre.nation}
				</td>

			</tr>
			<tr>
				<td >
					现任职务
				</td>
				<td style="min-width: 120px">
					${cadre.idcard}
				</td>
				<td >
					任现职时间
				</td>
				<td>
					${MEMBER_SOURCE_MAP.get(cadre.source)}
				</td>
				<td>
					现职务始任时间
				</td>
				<td>
					${cadre.syncSource}
				</td>
			</tr>
			<tr>
				<td>
					行政级别
				</td>
				<td>
					${cadre.grade}
				</td>
				<td>任现职级时间</td>
				<td >
					${cadre.eduType}
				</td>
				<td>
					任现职级年限
				</td>
				<td>
					${cadre.eduLevel}
				</td>
			</tr>
			<tr>

				<td>兼职单位1</td>
				<td >
					${cadre.enrolYear}
				</td>
				<td>
					兼任职务1
				</td>
				<td>
					${cadre.isFullTime}
				</td>
				<td >
					任兼职时间1
				</td>
				<td>
					${cadre.type}
				</td>
			</tr>
			<tr>

				<td>兼职单位2</td>
				<td >
					${cadre.actualEnrolTime}
				</td>
				<td>
					兼任职务2
				</td>
				<td>
					${cadre.expectGraduateTime}
				</td>
				<td >
					任兼职时间2
				</td>
				<td>
					${cadre.actualGraduateTime}
				</td>
			</tr>
			<tr>

				<td>党委委员</td>
				<td >
					${cadre.actualEnrolTime}
				</td>
				<td>
					纪委委员
				</td>
				<td colspan="3">
					${cadre.expectGraduateTime}
				</td>
			</tr>
			<tr>

				<td>担任其他职务</td>
				<td colspan="5">
					${cadre.actualEnrolTime}
				</td>
			</tr>
			<tr>

				<td>基层工作经历</td>
				<td colspan="5">
					${cadre.actualEnrolTime}
				</td>
			</tr>
			<tr>

				<td>挂职借调经历</td>
				<td colspan="5">
					${cadre.actualEnrolTime}
				</td>
			</tr>
			</tbody>
		</table>
    </form>

<style>
	#cadre-box .table-striped > tbody > tr:nth-of-type(odd) {
		background-color:inherit;
	}
	#cadre-box .table tbody tr:hover td, .table tbody tr:hover th {
		background-color:transparent;
	}
	#cadre-box .table-striped > tbody > tr > td:nth-of-type(odd) {
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