<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="smaller">
            ${dispatch!=null?"修改":"添加"}发文
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
    <form class="form-horizontal" action="${ctx}/dispatch_au" id="modalForm" method="post" enctype="multipart/form-data">
        <div class="row">
        <input type="hidden" name="id" value="${dispatch.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input required class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${year}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文类型</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                            name="dispatchTypeId" data-placeholder="请选择发文类型">
                        <option value="${dispatchType.id}">${dispatchType.name}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="code" value="${dispatch.code}">
                        <span class="label-inline"> * 留空自动生成</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党委常委会日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input class="form-control date-picker" name="_meetingTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.meetingTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input required class="form-control date-picker" name="_pubTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.pubTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input required class="form-control date-picker" name="_workTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任命人数</label>
            <div class="col-xs-6">
                <input required class="form-control digits" type="text" name="appointCount" value="${dispatch.appointCount}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">免职人数</label>
            <div class="col-xs-6">
                <input required class="form-control digits" type="text" name="dismissCount" value="${dispatch.dismissCount}">
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免文件</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_file" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">上会ppt</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_ppt" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited"name="remark">${dispatch.remark}</textarea>
				</div>
			</div>
        </div>
        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
                <button class="btn btn-info btn-sm" type="submit">
                    <i class="ace-icon fa fa-check "></i>
                    ${dispatch!=null?"修改":"添加"}
                </button>

                &nbsp; &nbsp; &nbsp;
                <button class="btn btn-default btn-sm" type="reset">
                    <i class="ace-icon fa fa-undo"></i>
                    重置
                </button>
            </div>
        </div>
    </form>
        </div>
    </div>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

    $('textarea.limited').inputlimiter();
    $('#modalForm input[type=file]').ace_file_input({
        no_file:'请选择文件 ...',
        btn_choose:'选择',
        btn_change:'更改',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });
    register_dispatchType_select($('#modalForm select[name=dispatchTypeId]'), $("#modalForm input[name=year]"));
    register_date($('.date-picker'));

    $("#modalForm").validate({
        rules: {
            code: {
                digits: true
            }
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        alert("添加成功")
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>