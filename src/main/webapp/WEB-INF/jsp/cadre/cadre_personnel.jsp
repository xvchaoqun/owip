<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">

		<table class="table table-bordered table-striped">
			<tbody>
			<tr>
				<td>
					所在单位
				</td>
				<td style="min-width: 80px">
					${cadre.realname}
				</td>
				<td>
					所在子单位1
				</td>
				<td style="min-width: 80px">
					${GENDER_MALE_MAP.get(cadre.gender)}
				</td>

				<td>
					所在子单位2
				</td>
				<td  style="min-width: 80px">
					${cadre.nation}
				</td>
			</tr>
			<tr>
				<td>编制类别</td>
				<td >
					${cadre.code}
				</td>
				<td>
					人员分类
				</td>
				<td>
					${cadre.nativePlace}
				</td>
				<td >
					人员身份
				</td>
				<td>
					${MEMBER_SOURCE_MAP.get(cadre.source)}
				</td>
			</tr>
			<tr>
				<td>
					人事转否
				</td>
				<td>
					${cadre.grade}
				</td>
				<td>人事转入时间</td>
				<td >
					${cadre.eduType}
				</td>
				<td>
					人员状态
				</td>
				<td>
					${cadre.eduLevel}
				</td>
			</tr>
			<tr>

				<td>岗位类别</td>
				<td >
					${cadre.enrolYear}
				</td>
				<td>
					岗位子类别
				</td>
				<td>
					${cadre.isFullTime}
				</td>
				<td >
					在岗情况
				</td>
				<td>
					${cadre.isFullTime}
				</td>
			</tr>
			<tr>

				<td>参加工作时间</td>
				<td >
					${cadre.actualEnrolTime}
				</td>
				<td>
					到校时间
				</td>
				<td>
					${cadre.expectGraduateTime}
				</td>
				<td >
					转正定级时间
				</td>
				<td>
					${cadre.actualGraduateTime}
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