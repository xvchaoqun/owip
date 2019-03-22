<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党费减免</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMember_selectReduceNorm" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="num" value='${fn:length(fn:split(param["ids[]"],","))}'/>
        <c:if test="${num==1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${pmdMember.user.realname}
            </div>
        </div>
        </c:if>
        <c:if test="${num>1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">已选择党员数量</label>
            <div class="col-xs-6 label-text">
                 ${num}人
            </div>
        </div>
        </c:if>

        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>党费减免标准</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="normId"
                        data-width="270"
                        data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${pmdNorms}" var="pmdNorm">
                        <option value="${pmdNorm.id}"
                                data-amount="${pmdNorm.pmdNormValue.amount}"
                                data-set-type="${pmdNorm.setType}">${pmdNorm.name}</option>
                    </c:forEach>
                </select>
                <span id="freeNote" style="display:none;font-weight: bolder;" class="text-danger">免交操作不可逆，请仔细核实后再提交！</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">额度（单位：人民币）</label>
            <div class="col-xs-6">
                <input class="form-control float"
                       type="text" name="amount"
                       data-rule-min="0.01" maxlength="10"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
            <textarea class="form-control limited" type="text"
                      name="remark" maxlength="100"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("select[name=normId]", "#modalForm").change(function(){
        var $selected = $(this).find(':selected');
        var setType = $selected.data("set-type");
        var amount = $selected.data("amount");
        //console.log(setType)
        if(setType=="${PMD_NORM_SET_TYPE_FREE}"){
            amount = 0;
            $("#freeNote").show();
        }else{
            $("#freeNote").hide();
        }
        if(setType=="${PMD_NORM_SET_TYPE_SET}"){
            $("input[name=amount]", "#modalForm").val('')
                    .prop("disabled", false)
                    .attr("required", "required");
        }else{
            $("input[name=amount]", "#modalForm").val(amount)
                    .prop("disabled", true).removeAttr("required");
        }
    })
    $("#modalForm select[name=normId]").val('${pmdMember.normId}').trigger("change");

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('textarea.limited').inputlimiter();
</script>