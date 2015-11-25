<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star orange"></i>
			基本信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
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
					${GENDER_MALE_MAP.get(cadreView.gender)}
				</td>

				<td style="background-color: #f9f9f9;text-align: right">
					姓名
				</td>
				<td  style="min-width: 150px;background-color: #fff;">
					${cadreView.nation}
				</td>
			</tr>
			<tr>
				<td>性别</td>
				<td >
					${cadreView.code}
				</td>
				<td>
					民族
				</td>
				<td>
					${cadreView.nativePlace}
				</td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td >
					${cadreView.code}
				</td>
				<td>
					年龄
				</td>
				<td>
					${cadreView.nativePlace}
				</td>
			</tr>
			<tr>
				<td>政治面貌</td>
				<td >
					${cadreView.code}
				</td>
				<td>
					加入党团时间
				</td>
				<td>
					${cadreView.nativePlace}
				</td>
			</tr>
			<tr>
				<td>国家/地区</td>
				<td >
					${cadreView.code}
				</td>
				<td>
					所在党组织
				</td>
				<td>
					${cadreView.nativePlace}
				</td>
			</tr>
			<tr>
				<td>证件类型</td>
				<td >
					${cadreView.code}
				</td>
				<td>
					证件号码
				</td>
				<td>
					${cadreView.nativePlace}
				</td>
			</tr>

			<tr>
				<td>
					籍贯
				</td>
				<td style="min-width: 100px">
					${cadreView.grade}
				</td>
				<td>出生地</td>
				<td>
					${cadreView.eduType}
				</td>
				<td>
					户籍地
				</td>
				<td >
					${cadreView.eduLevel}
				</td>
			</tr>
			<tr>
				<td>熟悉专业有何专长</td>
				<td >
					${cadreView.enrolYear}
				</td>
				<td>
					健康状况
				</td>
				<td colspan="3">
					${cadreView.isFullTime}
				</td>
			</tr>
			</tbody>
		</table>
    </form>
</div></div></div>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star orange"></i>
			人事信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-bordered table-striped">
				<tbody>
				<tr>
					<td>
						所在单位
					</td>
					<td style="min-width: 80px">
						${cadreView.realname}
					</td>
					<td>
						所在子单位1
					</td>
					<td style="min-width: 80px">
						${GENDER_MALE_MAP.get(cadreView.gender)}
					</td>

					<td>
						所在子单位2
					</td>
					<td  style="min-width: 80px">
						${cadreView.nation}
					</td>
				</tr>
				<tr>
					<td>编制类别</td>
					<td >
						${cadreView.code}
					</td>
					<td>
						人员分类
					</td>
					<td>
						${cadreView.nativePlace}
					</td>
					<td >
						人员身份
					</td>
					<td>
						${MEMBER_SOURCE_MAP.get(cadreView.source)}
					</td>
				</tr>
				<tr>
					<td>
						人事转否
					</td>
					<td>
						${cadreView.grade}
					</td>
					<td>人事转入时间</td>
					<td >
						${cadreView.eduType}
					</td>
					<td>
						人员状态
					</td>
					<td>
						${cadreView.eduLevel}
					</td>
				</tr>
				<tr>

					<td>岗位类别</td>
					<td >
						${cadreView.enrolYear}
					</td>
					<td>
						岗位子类别
					</td>
					<td>
						${cadreView.isFullTime}
					</td>
					<td >
						在岗情况
					</td>
					<td>
						${cadreView.isFullTime}
					</td>
				</tr>
				<tr>

					<td>参加工作时间</td>
					<td >
						${cadreView.actualEnrolTime}
					</td>
					<td>
						到校时间
					</td>
					<td>
						${cadreView.expectGraduateTime}
					</td>
					<td >
						转正定级时间
					</td>
					<td>
						${cadreView.actualGraduateTime}
					</td>
				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star orange"></i>
			任职信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-bordered table-striped">
				<tbody>
				<tr>
					<td>
						任职单位
					</td>
					<td style="min-width: 80px">
						${cadreView.realname}
					</td>
					<td>
						单位属性
					</td>
					<td style="min-width: 80px">
						${GENDER_MALE_MAP.get(cadreView.gender)}
					</td>

					<td>
						是否双肩挑
					</td>
					<td  style="min-width: 80px">
						${cadreView.nation}
					</td>

				</tr>
				<tr>
					<td >
						现任职务
					</td>
					<td style="min-width: 120px">
						${cadreView.idcard}
					</td>
					<td >
						任现职时间
					</td>
					<td>
						${MEMBER_SOURCE_MAP.get(cadreView.source)}
					</td>
					<td>
						现职务始任时间
					</td>
					<td>
						${cadreView.syncSource}
					</td>
				</tr>
				<tr>
					<td>
						行政级别
					</td>
					<td>
						${cadreView.grade}
					</td>
					<td>任现职级时间</td>
					<td >
						${cadreView.eduType}
					</td>
					<td>
						任现职级年限
					</td>
					<td>
						${cadreView.eduLevel}
					</td>
				</tr>
				<tr>

					<td>兼职单位1</td>
					<td >
						${cadreView.enrolYear}
					</td>
					<td>
						兼任职务1
					</td>
					<td>
						${cadreView.isFullTime}
					</td>
					<td >
						任兼职时间1
					</td>
					<td>
						${cadreView.type}
					</td>
				</tr>
				<tr>

					<td>兼职单位2</td>
					<td >
						${cadreView.actualEnrolTime}
					</td>
					<td>
						兼任职务2
					</td>
					<td>
						${cadreView.expectGraduateTime}
					</td>
					<td >
						任兼职时间2
					</td>
					<td>
						${cadreView.actualGraduateTime}
					</td>
				</tr>
				<tr>

					<td>党委委员</td>
					<td >
						${cadreView.actualEnrolTime}
					</td>
					<td>
						纪委委员
					</td>
					<td colspan="3">
						${cadreView.expectGraduateTime}
					</td>
				</tr>
				<tr>

					<td>担任其他职务</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				<tr>

					<td>基层工作经历</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				<tr>

					<td>挂职借调经历</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star orange"></i>
			职称信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
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
						${GENDER_MALE_MAP.get(cadreView.gender)}
					</td>

					<td class="bg-right">
						专业技术职务评定时间
					</td>
					<td  style="min-width: 80px"  class="bg-white">
						${cadreView.nation}
					</td>
				</tr>
				<tr>
					<td  class="bg-right">专业技术职务等级</td>
					<td >
						${cadreView.code}
					</td>
					<td  class="bg-right">
						专业技术职务分级时间
					</td>
					<td>
						${cadreView.nativePlace}
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
						${cadreView.isFullTime}
					</td>
					<td class="bg-right">
						管理岗位分级
					</td>
					<td  class="bg-white">
						${cadreView.type}
					</td>
				</tr>
				<tr>


					<td colspan="4" class="bg-right">
						时间
					</td>
					<td>
						${cadreView.delayYear}
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
						${cadreView.isFullTime}
					</td>
					<td  class="bg-right">
						工勤岗位分级时间
					</td>
					<td  class="bg-white">
						${cadreView.type}
					</td>
				</tr>
				<tr>
					<td colspan="5" style="background-color:#fff;text-align: left ">
						工勤岗位备注：
					</td>

				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star orange"></i>
			学历学位信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-bordered table-striped">
				<tbody>
				<tr>
					<td>
						最高学历
					</td>
					<td style="min-width: 80px">
						${cadreView.realname}
					</td>
					<td>
						学习方式
					</td>
					<td style="min-width: 80px">
						${GENDER_MALE_MAP.get(cadreView.gender)}
					</td>

					<td>
						毕业学校
					</td>
					<td style="min-width: 80px">
						${GENDER_MALE_MAP.get(cadreView.gender)}
					</td>
				</tr>
				<tr>
					<td>毕业学院</td>
					<td >
						${cadreView.code}
					</td>
					<td>
						所学专业
					</td>
					<td>
						${cadreView.nativePlace}
					</td>
					<td >
						学校类型
					</td>
					<td>
						${MEMBER_SOURCE_MAP.get(cadreView.source)}
					</td>
				</tr>
				<tr>
					<td>
						最高学位
					</td>
					<td>
						${cadreView.grade}
					</td>
					<td>学习方式</td>
					<td >
						${cadreView.eduType}
					</td>
					<td>
						毕业学校
					</td>
					<td>
						${cadreView.eduLevel}
					</td>
				</tr>
				<tr>

					<td>毕业学院</td>
					<td >
						${cadreView.enrolYear}
					</td>
					<td>
						所学专业
					</td>
					<td>
						${cadreView.isFullTime}
					</td>
					<td >
						学校类型
					</td>
					<td>
						${cadreView.type}
					</td>
				</tr>

				</tbody>
			</table>
		</div></div></div>
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
	#cadre-box  .widget-main.no-padding .table{

		border: 1px solid #E5E5E5
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