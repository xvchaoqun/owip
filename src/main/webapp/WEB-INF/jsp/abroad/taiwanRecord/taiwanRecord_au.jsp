<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${taiwanRecord!=null}">编辑</c:if><c:if test="${taiwanRecord==null}">添加</c:if>因公赴台备案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/taiwanRecord_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${taiwanRecord.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label">备案时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control" name="recordDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${taiwanRecord==null?today:cm:formatDate(taiwanRecord.recordDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">干部</label>
            <c:if test="${not empty taiwanRecord.cadre}">
                <div class="col-xs-6 label-text">
                ${taiwanRecord.user.realname}-${taiwanRecord.user.code}
                </div>
            </c:if>
            <c:if test="${empty taiwanRecord.cadre}">
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?abroad=1" data-width="273"
                        name="cadreId" data-placeholder="请选择">
                    <option></option>
                </select>
            </div>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">离境时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="startDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(taiwanRecord.startDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">回国时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="endDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(taiwanRecord.endDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">出访活动类别</label>
            <div class="col-xs-6 choice input-group">
                <input name="_reason" type="checkbox" value="学术会议"> 学术会议&nbsp;
                <input name="_reason" type="checkbox" value="考察访问"> 考察访问&nbsp;
                <input name="_reason" type="checkbox" value="合作研究"> 合作研究&nbsp;
                <input name="_reason" type="checkbox" value="进修"> 进修&nbsp;
                <input name="_reason" type="checkbox" value="其他"> 其他
                <input name="_reason_other" type="text">
                <input name="reason" type="hidden"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea  class="form-control limited" name="remark" maxlength="100">${taiwanRecord.remark}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">现持有台湾通行证号码</label>
            <div class="col-xs-6 label-text">
                <span id="passportCode">${empty taiwanRecord.passportCode?"无":taiwanRecord.passportCode}</span>
                <input type="hidden" class="form-control" type="text" name="passportCode" value="${taiwanRecord.passportCode}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">办理新证件方式</label>
            <div class="col-xs-6">
                <select name="handleType" data-width="255"
                        data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=handleType]").val('${taiwanRecord.handleType}');
                </script>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${taiwanRecord!=null}">确定</c:if><c:if test="${taiwanRecord==null}">添加</c:if>"/>
</div>
<style>
    #modalForm input[type=checkbox]{
        width: 20px;
        height: 20px;
        _vertical-align: -1px;/*针对IE6使用hack*/
        vertical-align: -3px;
    }
    .choice{
        font-size: 16px;
    }
</style>
<script>
    var reason;
    <c:forEach var="reason" items="${fn:split(taiwanRecord.reason,'+++')}">
    reason = '${reason}';
    if(reason.startWith("其他:")){
        $("input[name=_reason][value='其他']").prop("checked", true);
        $("input[name=_reason_other]").val(reason.split(":")[1]);
    }else{
        $("input[name=_reason][value='${reason}']").prop("checked", true);
    }
    </c:forEach>
    <c:if test="${not empty taiwanRecord && empty taiwanRecord.passportCode}">
        <c:if test="${empty taiwanRecord.handleDate}">
        $("#modalForm select[name=handleType]").select2();
        </c:if>
        <c:if test="${not empty taiwanRecord.handleDate}">
        $("#modalForm select[name=handleType]").select2({theme: "default"}).trigger("change").prop("disabled", true);
        </c:if>
    </c:if>
    <c:if test="${not empty taiwanRecord.passportCode}">
    $("#modalForm select[name=handleType]").select2({theme: "default"}).val(null).trigger("change").prop("disabled", true);
    </c:if>
    <c:if test="${empty taiwanRecord}">
    var $selectCadre = $.register.user_select($('#modal select[name=cadreId]'));
    $selectCadre.on("change",function(){
        var twPassportCode = $(this).select2("data")[0]['twPassportCode'] || '';

        if (twPassportCode != '') {
            $('#modalForm #passportCode').html(twPassportCode);
            $('#modalForm input[name=passportCode]').val(twPassportCode);
            $("#modalForm select[name=handleType]").select2({theme: "default"}).val(null).trigger("change").prop("disabled", true);
        } else {
            $('#modalForm #passportCode').html('无');
            $('#modalForm input[name=passportCode]').val('');
            $("#modalForm select[name=handleType]").select2({theme: "classic"}).prop("disabled", false);
        }
    }).change();
    </c:if>
    $.register.date($('#modalForm input[name=recordDate]'))
    $.register.date($('.date-picker'))
    $("#modalForm").validate({
        submitHandler: function (form) {

            // 出访活动类别
            var $_reason = $("#modalForm input[name=_reason][value='其他']");
            var _reason_other = $("input[name=_reason_other]").val().trim();
            if($_reason.is(":checked")){
                if(_reason_other==''){
                    $.tip({$form:$(form), field:"_reason_other", msg:"请输入其他出访活动类别"})
                    /*SysMsg.info("请输入其他出访活动类别", '', function(){
                        $("#modalForm input[name=_reason_other]").val('').focus();
                    });*/
                    return;
                }
            }
            var reasons = [];
            $.each($("#modalForm input[name=_reason]:checked"), function(){
                if($(this).val()=='其他'){
                    reasons.push("其他:"+_reason_other);
                }else
                    reasons.push($(this).val());
            });
            if(reasons.length==0){
                $.tip({$form:$(form), field:"reason", msg:"请选择出访活动类别"})
                return;
            }
            $("#modalForm input[name=reason]").val(reasons.join("+++"));

            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
    $('#modalForm [data-rel="select2"]').select2();
</script>