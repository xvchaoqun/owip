<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${crMeeting!=null?'编辑':'添加'}招聘会信息</h3>
</div>
<div class="modal-body overflow-visible">
    <form class="form-horizontal" action="${ctx}/crMeeting_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crMeeting.id}">
        <input type="hidden" name="infoId" value="${infoId}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘会日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input required class="form-control" autocomplete="off" name="meetingDate" type="text"
							   value="${empty crInfo.meetingDate?_today_dot:(cm:formatDate(crMeeting.meetingDate,'yyyy.MM.dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			 <div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>招聘岗位</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <select required class="multiselect" multiple="" name="postIds" data-width="273" data-placeholder="请选择">
                            <c:forEach items="${crPosts}" var="post">
								<option value="${post.id}">${post.name}</option>
							</c:forEach>
                        </select>
                        <script type="text/javascript">
                            $.register.multiselect($('#modalForm select[name=postIds]'), '${crMeeting.postIds}'.split(","));
                        </script>
                    </div>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">招聘会人数要求</label>
				<div class="col-xs-6">
					<input class="form-control digits" type="text" name="requireNum" value="${crMeeting.requireNum}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${crMeeting.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty crMeeting?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>