<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
    <form class="form-horizontal" action="${ctx}/crsPost_templateContent" id="templateContentForm" method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
        <input type="hidden" name="type" value="${param.type}">
        <c:if test="${param.type!=CRS_TEMPLATE_TYPE_POST_DUTY}">
        <div class="form-group">
            <div class="col-xs-6">
                <select data-rel="select2" name="templateId" data-placeholder="请选择通用模板">
                    <option></option>
                    <c:forEach items="${templateMap}" var="entry">
                    <option value="${entry.key}">${entry.value.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <div class="col-xs-6">
                <input type="hidden" name="content">
                <textarea id="contentId">
                ${param.type==CRS_TEMPLATE_TYPE_BASE?crsPost.requirement:""}
                ${param.type==CRS_TEMPLATE_TYPE_POST?crsPost.qualification:""}
                ${param.type==CRS_TEMPLATE_TYPE_MEETINGNOTICE?crsPost.meetingNotice:""}
                ${param.type==CRS_TEMPLATE_TYPE_POST_DUTY?crsPost.postDuty:""}
                </textarea>
            </div>
        </div>
    </form>

<div class="modal-footer center">
    <c:if test="${not empty crsPost.meetingNotice}">
    <a href="javascript:;" onclick="_stepContentReload()" class="btn btn-default">取消</a>
    </c:if>
    <input type="button" id="noticeSubmitBtn" class="btn btn-primary" value="确定"/>
</div>
<script>
    <c:set var="keWidth" value="743px"/>
    <c:if test="${param.type==CRS_TEMPLATE_TYPE_MEETINGNOTICE}">
    <c:set var="keWidth" value="473px"/>
    </c:if>
    var templateMap = ${cm:toJSONObject(templateMap)}
    var ke = KindEditor.create('#contentId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '400px',
        width: '${keWidth}',
        minWidth: 400
    });
    $("#templateContentForm select[name=templateId]").on("change",function(){
        var templateId = $(this).val();
        if(templateId>0) {
            ke.html(KindEditor.unescape(templateMap[templateId].content));
            ke.readonly();
        }else{
            ke.readonly(false);
        }
    });

    $("#noticeSubmitBtn").click(function(){$("#templateContentForm").submit(); return false;})
    $("#templateContentForm").validate({
        submitHandler: function (form) {
            $("#templateContentForm input[name=content]").val(ke.html());
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $('#templateContentForm [data-rel="select2"]').select2();
</script>