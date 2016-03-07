<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
        <iframe id="myframe" src="/report/passportApply?id=${passportApply.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </div>
    <div class="info">
    <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_NOT_PASS}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">证件应交回日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <input ${passportApply.status==PASSPORT_APPLY_STATUS_PASS?"disabled":""} required class="form-control date-picker" name="_expectDate" type="text"
                                   data-date-format="yyyy年mm月dd日" value="${cm:formatDate(passportApply.expectDate,'yyyy年mm月dd日')}" />
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
            </form>
            <div>
            <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_PASS}">
                <button id="agree" class="btn btn-success btn-block" style="margin-top:20px;font-size: 20px">已备案，予以批准</button>
            </c:if>
                <button id="agree_msg" class="btn btn-info btn-block" style="margin-top:20px;font-size: 20px">短信通知</button>
            </div>
        </div>
    </c:if>
        <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_PASS}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">原因</label>
                    <div class="col-xs-6">
                        <textarea ${passportApply.status==PASSPORT_APPLY_STATUS_NOT_PASS?"disabled":""}
                                class="form-control limited" type="text" name="remark" rows="3">${passportApply.remark}</textarea>
                    </div>
                </div>
            </form>
            <div>
            <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_NOT_PASS}">
                <button id="disagree" class="btn btn-danger btn-block" style="margin-top:20px;font-size: 20px">不符合条件，不予批准</button>
                </c:if>
                <button id="disagree_msg"class="btn btn-info btn-block" style="margin-top:20px;font-size: 20px">短信通知</button>
            </div>
        </div>
        </c:if>
        <div class="center" style="margin-top: 40px">
    <c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_PASS}">
            <button id="print" class="btn btn-info2 btn-block" style="font-size: 30px">打印审批表</button>
    </c:if>
            <button class="closeView reload btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<c:set var="cadre" value="${cadreMap.get(passportApply.cadreId)}"/>
<c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
<c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passportApply.classId)}"/>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>

    $("#print").click(function(){ // 兼容谷歌
        //$("#myframe").jqprint();
        var myframe = document.getElementById("myframe");
        myframe.focus();
        myframe.contentWindow.print();
    });

    $("#agree").click(function(){
        var _expectDate = $("input[name=_expectDate]").val();
        if( _expectDate == ''){
            SysMsg.info("请填写应交回日期");
            return false;
        }
        $.post("${ctx}/passportApply_agree",{id:"${param.id}", _expectDate:_expectDate },function(ret){
            if(ret.success){
                SysMsg.success('审批成功', '提示', function(){

                    $("#item-content").load("${ctx}/passportApply_check?id=${param.id}&_="+new Date().getTime());
                });

            }
        });
    });
    $("#agree_msg").click(function(){
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确定发送',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-default'
                }
            },
            message: '<p style="padding:30px;font-size:20px;text-indent: 2em; ">${sysUser.realname}同志，您好！您提交的办理${passportType.name}的申请，组织部已备案。' +
            '请登录系统打印申请表，并到党委/校长办公室机要室（房间号？）找郭宁老师盖章。' +
            '领取新证件之后，请尽快交到组织部（主楼A306）集中保管。谢谢！</p>',
            callback: function(result) {
                if(result) {
                    SysMsg.success('通知成功', '提示', function(){
                        //page_reload();
                    });
                }
            },
            title: "短信通知"
        });
    });
    $("#disagree").click(function(){
        var remark = $("textarea[name=remark]").val().trim();
        if( remark == ''){
            SysMsg.info("请填写原因");
            return false;
        }
        $.post("${ctx}/passportApply_disagree",{id:"${param.id}", remark:remark },function(ret){
            if(ret.success){
                SysMsg.success('提交成功', '提示', function(){
                    $("#item-content").load("${ctx}/passportApply_check?id=${param.id}&_="+new Date().getTime());
                });
            }
        });
    });

    $("#disagree_msg").click(function(){
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确定发送',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-default'
                }
            },
            message: '<p style="padding:30px;font-size:20px;text-indent: 2em; ">${sysUser.realname}同志，您好！您提交的办理${passportType.name}的申请，因不符合要求未予审批。请登录系统查看具体原因，谢谢！</p>',
            callback: function(result) {
                if(result) {
                    SysMsg.success('通知成功', '提示', function(){
                        //page_reload();
                    });
                }
            },
            title: "短信通知"
        });
    });

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    $('textarea.limited').inputlimiter();
</script>