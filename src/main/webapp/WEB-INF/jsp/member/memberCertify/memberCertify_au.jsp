<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${memberCertify!=null?'编辑':'添加'}组织关系证明</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member/memberCertify_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberCertify.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 姓名</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号"  data-width="252">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 年份</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 100px">
                    <input required class="form-control date-picker"
                           name="year"
                           type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2"
                           value="${empty memberCertify.year?_thisYear:memberCertify.year}"/>
                    <span class="input-group-addon"> <i
                            class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 政治面貌</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"
                        data-width="252">
                    <option></option>
                    <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="entity">
                        <option value="${entity.key}">${entity.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=politicalStatus]").val(${memberCertify.politicalStatus});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 转出单位</label>
            <div class="col-xs-6">
                <input required class="form-control" style="width: 252px" type="text" name="fromUnit" value="${memberCertify.fromUnit}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 转入单位抬头</label>
            <div class="col-xs-6">
                <input required class="form-control" style="width: 252px" type="text" name="toTitle" value="${memberCertify.toTitle}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 转入单位</label>
            <div class="col-xs-6">
                <input required class="form-control" style="width: 252px" type="text" name="toUnit" value="${memberCertify.toUnit}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 证明信日期</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 252px">
                    <input required class="form-control date-picker" name="certifyDate" type="text"
                           data-date-format="yyyy.mm.dd" value="${empty memberCertify?_today_dot:cm:formatDate(memberCertify.certifyDate,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty memberCertify?'确定':'添加'}</button>
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>