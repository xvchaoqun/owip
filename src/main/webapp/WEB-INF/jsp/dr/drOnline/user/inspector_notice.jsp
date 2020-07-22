<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header" align="center">
    <h3>推荐说明</h3>
</div>
<div style="width:70%; margin:0 auto; padding-top: 10px">
    <div class="modal-body" style="text-align: left;word-wrap:break-word">
        ${cls==0?param.mobileNotice:param.notice}
    </div>
</div>
<div class="modal-footer">
    <button class="btn btn-success" data-dismiss="modal" aria-hidden="true"><i class="fa fa-check"></i> 确认</button>
</div>
<script type="text/javascript">
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>