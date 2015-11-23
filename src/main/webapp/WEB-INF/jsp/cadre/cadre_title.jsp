<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">

		<table class="table table-bordered">
			<tbody>
			<tr>
				<td rowspan="2"  class="bg-right" style="text-align: center;vertical-align: middle">
					专技岗位
				</td>
				<td class="bg-right">
					专业技术职务
				</td>
				<td style="min-width: 80px" class="bg-white">
					${GENDER_MALE_MAP.get(cadre.gender)}
				</td>

				<td class="bg-right">
					专业技术职务评定时间
				</td>
				<td  style="min-width: 80px"  class="bg-white">
					${cadre.nation}
				</td>
			</tr>
			<tr>
				<td  class="bg-right">专业技术职务等级</td>
				<td >
					${cadre.code}
				</td>
				<td  class="bg-right">
					专业技术职务分级时间
				</td>
				<td>
					${cadre.nativePlace}
				</td>

			</tr>
			<tr>
				<td colspan="5" style="background-color:#fff;text-align: left ">
					专技岗位备注：
				</td>

			</tr>
			<tr>

				<td  class="bg-right" style="text-align: center">管理岗位</td>

				<td class="bg-right">
					管理岗位等级
				</td>
				<td  class="bg-white">
					${cadre.isFullTime}
				</td>
				<td class="bg-right">
					管理岗位分级
				</td>
				<td  class="bg-white">
					${cadre.type}
				</td>
			</tr>
			<tr>


				<td colspan="4" class="bg-right">
					时间
				</td>
				<td>
					${cadre.delayYear}
				</td>
			</tr>
			<tr>
				<td colspan="5" style="background-color:#fff;text-align: left ">
					管理岗位备注：
				</td>

			</tr>
			<tr>

				<td  class="bg-right" style="text-align: center">工勤岗位</td>

				<td class="bg-right">
					工勤岗位等级
				</td>
				<td  class="bg-white">
					${cadre.isFullTime}
				</td>
				<td  class="bg-right">
					工勤岗位分级时间
				</td>
				<td  class="bg-white">
					${cadre.type}
				</td>
			</tr>
			<tr>
				<td colspan="5" style="background-color:#fff;text-align: left ">
					工勤岗位备注：
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
	#cadre-box .table-striped > tbody > tr > td:nth-of-type(odd) , .bg-right{
		background-color: #f9f9f9;
		text-align: right;
	}
	#cadre-box .bg-white{
		background-color:#fff;!important;
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