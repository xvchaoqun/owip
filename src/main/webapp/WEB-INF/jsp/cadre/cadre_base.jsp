<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">

		<table class="table table-bordered table-striped">
			<tbody>
			<tr>
				<td rowspan="6" colspan="2" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
					照片
				</td>
				<td style="background-color: #f9f9f9;text-align: right">
					工作证号
				</td>
				<td style="min-width: 150px;background-color: #fff;">
					${GENDER_MALE_MAP.get(cadre.gender)}
				</td>

				<td style="background-color: #f9f9f9;text-align: right">
					姓名
				</td>
				<td  style="min-width: 150px;background-color: #fff;">
					${cadre.nation}
				</td>
			</tr>
			<tr>
				<td>性别</td>
				<td >
					${cadre.code}
				</td>
				<td>
					民族
				</td>
				<td>
					${cadre.nativePlace}
				</td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td >
					${cadre.code}
				</td>
				<td>
					年龄
				</td>
				<td>
					${cadre.nativePlace}
				</td>
			</tr>
			<tr>
				<td>政治面貌</td>
				<td >
					${cadre.code}
				</td>
				<td>
					加入党团时间
				</td>
				<td>
					${cadre.nativePlace}
				</td>
			</tr>
			<tr>
				<td>国家/地区</td>
				<td >
					${cadre.code}
				</td>
				<td>
					所在党组织
				</td>
				<td>
					${cadre.nativePlace}
				</td>
			</tr>
			<tr>
				<td>证件类型</td>
				<td >
					${cadre.code}
				</td>
				<td>
					证件号码
				</td>
				<td>
					${cadre.nativePlace}
				</td>
			</tr>

			<tr>
				<td>
					籍贯
				</td>
				<td style="min-width: 100px">
					${cadre.grade}
				</td>
				<td>出生地</td>
				<td>
					${cadre.eduType}
				</td>
				<td>
					户籍地
				</td>
				<td >
					${cadre.eduLevel}
				</td>
			</tr>
			<tr>
				<td>熟悉专业有何专长</td>
				<td >
					${cadre.enrolYear}
				</td>
				<td>
					健康状况
				</td>
				<td colspan="3">
					${cadre.isFullTime}
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