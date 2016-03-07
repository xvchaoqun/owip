<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
        <iframe src="/report/passportApply?classId=${param.classId}&userId=${_user.id}&id=${param.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </div>
    <div class="info">
        <div class="alert alert-warning" style="font-size: 20px">
            请仔细核对申办人姓名、身份证号、所在单位和职务。若信息有误，请联系组织部。<br/>
            联系人：徐蕾、龙海明<br/>
            联系电话：58808302、58806879。
        </div>
        <c:if test="${param.type!='view'}">
        <div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
            <input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
        </div>
        <div class="center" style="margin-top: 20px">
        <button id="submit" class="btn btn-success btn-block" style="font-size: 30px">提交申请</button>
        </div>
        <div class="center" style="margin-top: 40px">
            <button id="back" class="btn btn-default btn-block" style="font-size: 30px">返回选择证件</button>
        </div>
        </c:if>
        <c:if test="${param.type=='view'}">
            <div class="center" style="margin-top: 40px">
                <button class="closeView btn btn-default btn-block" style="font-size: 30px">返回</button>
            </div>
            </c:if>
    </div>
</div>

<script>
    $("#back").click(function(){
        $("#apply-content").load("${ctx}/user/passportApply_select");
    });


    $("#submit").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }
        $.post("${ctx}/user/passportApply_au",{classId:"${param.classId}"},function(ret){
            if(ret.success){
                SysMsg.success('您的申请已提交，组织部备案之后会短信提醒您，' +
                '然后请再次登录系统下载审批表并到党委/校长办公室机要室（房间号？）' +
                '找郭宁老师盖章。谢谢！', '提示', function(){
                    page_reload();
                });

            }
        });
    });

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    register_user_select($('[data-rel="select2-ajax"]'));
    $('textarea.limited').inputlimiter();
</script>