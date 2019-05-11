<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>启动缴费</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMonth_start" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="monthId" value="${pmdMonth.id}">

        <div class="form-group">
            <label class="col-xs-5 control-label">党费收缴月份</label>

            <div class="col-xs-6 label-text">
                ${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label">启动日期</label>

            <div class="col-xs-6 label-text">
                ${_today}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label">参与缴费党委</label>
            <div class="col-xs-6 label-text">
                <c:set var="partyCount" value="${fn:length(partyIdSet)}"/>
                ${partyCount}个
            </div>

        </div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">
        <c:if test="${partyCount==0}">
        请先设置本月参与缴费的党委
        </c:if>
        <c:if test="${partyCount>0}">
            注：
            <div>
            1、启动后，参与缴费的党委不可重新编辑，请确认准确无误后再启动。<br/>
            2、启动需要同步所选${_p_partyName}的党员数据，需要的时间较长(3分钟左右)，请耐心等待处理完成
            </div>
        </c:if>
    </div>
    <div style="margin-bottom: 10px;display: none" id="timer">耗时：<span></span></div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
    ${partyCount==0?"disabled":""}
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 同步数据中，请稍后"
            data-success-text="开启成功" autocomplete="off"> 确定开启
    </button>
</div>
<script src="${ctx}/extend/js/timer.jquery.min.js"/>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $("#timer").show();
            $("#timer span").timer({format:'%m分%s秒'});
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $btn.button("success").addClass("btn-success");
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        $.hashchange();
                    }
                    $('#timer span').timer('remove');
                    $("#timer").hide();
                    $btn.button('reset');
                }
            });
        }
    });
</script>