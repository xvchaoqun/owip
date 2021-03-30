<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty param.partyIds}" var="isNoticeParty"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${isNoticeParty?"未报送党委":"未报送党支部"}</h3>
</div>
<div class="modal-body rownumbers" id="noticeDiv">
    <div style="max-height: 400px; overflow-y: scroll">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th class="center">
                <label class="pos-rel">
                    <input type="checkbox" class="ace checkAll" name="checkAll">
                    <span class="lbl"></span>
                </label>
            </th>
            <th nowrap>${isNoticeParty?"二级党组织名称":"党支部名称"}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${isNoticeParty?partyList:branchList}" var="entry" varStatus="st">
            <tr>
                <td class="center" width="50">
                    <label class="pos-rel">
                        <input type="checkbox" name="_id"
                               value="${entry.id}"
                               class="ace"/>
                        <span class="lbl"></span>
                        <span class="lbl"></span>
                    </label>
                </td>
                <td nowrap>${entry.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${(fn:length(partyList)==0&&isNoticeParty)&&(fn:length(branchList)==0&&!isNoticeParty)}">
        <div class="well well-lg center">
            <h4 class="green lighter">暂无记录</h4>
        </div>
    </c:if>
    </div>
    <div class="space-4"></div>
    <div class="space-4"></div>
    <div class="form-group">
        <label class="col-xs-2 control-label" style="padding-left: 20px!important;padding-top: 12px!important;"> 提醒内容</label>
        <div class="col-xs-9">
            <textarea id="notice" class="form-control" rows="5" type="text" name="notice">${tpl.content}</textarea>
        </div>
    </div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" disabled class="btn btn-primary" value="发送提醒"/>
</div>
<script>

    var _ids = new Array();// 需要提醒的分党委id或者党支部id

    function isPermitSend(){
        if (_ids.length >0){
            $("#selectBtn").prop("disabled", false);
        }else {
            $("#selectBtn").prop("disabled", true);
        }
    }

    $(document).on("change", "#noticeDiv input[name='_id']", function(){

        $("input[name='_id']", "#noticeDiv").each(function () {
            var id = parseInt($(this).val());
            if (this.checked) { //被选中的复选框
                if (_ids.toString() == "") {
                    _ids.push(id);
                } else {  //判断id数组中是否含有以前存入的元素，没有则添加
                    if ($.inArray(id, _ids)<0) {
                        _ids.push(id);
                    }
                }
            } else {  //未被选中的复选框
                $('#noticeDiv input[name=checkAll]').prop('checked', false);
                if ($.inArray(id, _ids)>=0) {
                    _ids.remove(id);
                    //ids.splice($.inArray(id, ids), id.length);
                    //console.log(ids)
                }
                $(this).closest("tr").removeClass("active");
            }
        });
        isPermitSend();
    });
    $('#noticeDiv input[name=checkAll]').on('change',function () {
        if($(this).is(':checked')){
            $('#noticeDiv input[name=_id]').prop('checked',true);
            _ids=$.map($('#noticeDiv input[name=_id]'), function (input) {
                return parseInt($(input).val());
            });
        }else {
            $('#noticeDiv input[name=_id]').prop('checked',false);
            _ids=[];
        }
        isPermitSend();
    });

    $("#modal #selectBtn").click(function(){
        $.post("${ctx}/pm/pm3Guide_notice",
            {
                isNoticeParty:${isNoticeParty},
                _ids:_ids,
                id:${param.id},
                notice:$("#notice").val()
            },function(ret){
                if(ret.success){
                    SysMsg.success('发送提醒成功！');
                }
                $("#modal").modal('hide');
            })
        });
</script>