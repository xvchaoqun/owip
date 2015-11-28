<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/memberStudent_au" id="modalForm" method="post">

		<table class="table table-bordered table-striped">
			<tbody>
			<tr>
				<td>
					姓名
				</td>
				<td style="min-width: 80px">
					${memberStudent.realname}
				</td>
				<td>
					性别
				</td>
				<td style="min-width: 80px">
					${GENDER_MALE_MAP.get(memberStudent.gender)}
				</td>

				<td>
					民族
				</td>
				<td  style="min-width: 80px">
					${memberStudent.nation}
				</td>
				<td >
					身份证号
				</td>
				<td style="min-width: 120px">
					${memberStudent.idcard}
				</td>
			</tr>
			<tr>
				<td>学生证号</td>
				<td >
					${memberStudent.code}
				</td>
				<td>
					籍贯
				</td>
				<td>
					${memberStudent.nativePlace}
				</td>
				<td >
					来源
				</td>
				<td>
					${MEMBER_SOURCE_MAP.get(memberStudent.source)}
				</td>
				<td>
					同步来源
				</td>
				<td>
					${memberStudent.syncSource}
				</td>
			</tr>
			<tr>
				<td>
					年级
				</td>
				<td>
					${memberStudent.grade}
				</td>
				<td>培养类型</td>
				<td >
					${memberStudent.eduType}
				</td>
				<td>
					培养层次
				</td>
				<td>
					${memberStudent.eduLevel}
				</td>
				<td >
					培养方式
				</td>
				<td>
					${memberStudent.eduWay}
				</td>
			</tr>
			<tr>

				<td>招生年度</td>
				<td >
					${memberStudent.enrolYear}
				</td>
				<td>
					是否全日制
				</td>
				<td>
					${memberStudent.isFullTime}
				</td>
				<td >
					学生类别
				</td>
				<td>
					${memberStudent.type}
				</td>
				<td>教育类别</td>
				<td >
					${memberStudent.eduCategory}
				</td>
			</tr>
			<tr>

				<td>实际入学年月</td>
				<td >
					${memberStudent.actualEnrolTime}
				</td>
				<td>
					预计毕业年月
				</td>
				<td>
					${memberStudent.expectGraduateTime}
				</td>
				<td >
					实际毕业年月
				</td>
				<td>
					${memberStudent.actualGraduateTime}
				</td>
				<td>
					延期毕业年限
				</td>
				<td>
					${memberStudent.delayYear}
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
            delay: 300,
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