<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<div class="row passport_apply">
    <div class="preview">
        <img data-src="${ctx}/report/passportApply?id=${passportApply.id}&_=<%=new Date().getTime()%>"
             src="${ctx}/img/loading.gif"
             onload="lzld(this)" <%-- width="595" height="842"--%>/>
    </div>
    <div class="info">
        <div style="border: 1px dashed #aaaaaa;padding: 20px">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-info-circle"></i>
                        当前拥有的证件列表
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding" style="overflow:auto;">
                            <table style="min-width: 500px;" class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>证件名称</th>
                                    <th>证件号码</th>
                                    <th>发证日期</th>
                                    <th>有效期</th>
                                    <th>是否借出</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${passports}" var="passport" varStatus="st">
                                    <tr>
                                        <td nowrap>${cm:getMetaType(passport.classId).name}</td>
                                        <td nowrap>${passport.code}</td>
                                        <td nowrap>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                        <td nowrap>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                        <td nowrap>${passport.isLent?"借出":"-"}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                    </div></div></div>
        </div>
    <c:if test="${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>证件应交回日期</label>
                    <div class="col-xs-6">
                        <div class="input-group date" data-date-format="yyyy年mm月dd日">
                            <input ${passportApply.status==ABROAD_PASSPORT_APPLY_STATUS_PASS?"disabled":""} required class="form-control" name="_expectDate" type="text"
                                    value="${cm:formatDate(passportApply.expectDate,'yyyy年MM月dd日')}" />
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
            </form>
            <div>
            <c:if test="${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_PASS}">
                <button id="agree" class="btn btn-success btn-block" style="margin-top:20px;font-size: 20px">已备案，予以批准</button>
            </c:if>
                <button id="agree_msg" class="btn btn-info btn-block"
                        style="margin-top:20px;font-size: 20px;
                        display: ${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_PASS?'none':'block'}">短信通知</button>
            </div>
        </div>
    </c:if>
        <c:if test="${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_PASS}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">原因</label>
                    <div class="col-xs-6">
                        <textarea ${passportApply.status==ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS?"disabled":""}
                                class="form-control limited" type="text" name="remark" rows="3">${passportApply.remark}</textarea>
                    </div>
                </div>
            </form>
            <div>
            <c:if test="${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS}">
                <button id="disagree" class="btn btn-danger btn-block" style="margin-top:20px;font-size: 20px">不符合条件，不予批准</button>
                </c:if>
                <button id="disagree_msg"class="btn btn-info btn-block"
                        style="margin-top:20px;font-size: 20px;
                        display: ${passportApply.status!=ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS?'none':'block'}">短信通知</button>
            </div>
        </div>
        </c:if>
        <div class="center" style="margin-top: 40px">
    <c:if test="${passportApply.status==ABROAD_PASSPORT_APPLY_STATUS_PASS}">
            <button id="print" class="btn btn-info btn-block" style="font-size: 30px">打印审批表</button>
    </c:if>
            <button class="hideView btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<c:set var="cadre" value="${cm:getCadreById(passportApply.cadreId)}"/>
<c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
<c:set var="passportType" value="${cm:getMetaType(passportApply.classId)}"/>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>
    $("#print").click(function(){
        $.print("${ctx}/report/passportApply?id=${passportApply.id}&format=pdf");
    });
  /*  $("#print").click(function(){ // 兼容谷歌
        //$("#myframe").jqprint();
        var myframe = document.getElementById("myframe");
        myframe.focus();
        myframe.contentWindow.print();
    });
*/
    $("#agree").click(function(){
        var _expectDate = $("input[name=_expectDate]").val();
        if( _expectDate == ''){
            SysMsg.info("请填写应交回日期");
            return false;
        }
        $.post("${ctx}/abroad/passportApply_agree",{id:"${param.id}", _expectDate:_expectDate },function(ret){
            if(ret.success){
                //SysMsg.success('审批成功', '提示', function(){

                    $("#body-content-view").load("${ctx}/abroad/passportApply_check?id=${param.id}&_="+new Date().getTime());
                //});
                //$("#agree_msg").show().click();

            }
        });
    });
    $("#agree_msg").click(function(){
        $.loadModal("${ctx}/abroad/shortMsg_view?id=${passportApply.id}&type=passportApplyPass");
    });
    $("#disagree").click(function(){
        var remark = $("textarea[name=remark]").val().trim();
        if( remark == ''){
            SysMsg.info("请填写原因");
            return false;
        }
        $.post("${ctx}/abroad/passportApply_disagree",{id:"${param.id}", remark:remark },function(ret){
            if(ret.success){
                //SysMsg.success('提交成功', '提示', function(){
                    //$("#body-content-view").load("${ctx}/abroad/passportApply_check?id=${param.id}&_="+new Date().getTime());
                    $.hashchange("status=2", "#${ctx}/abroad/passportApply");
                //});
            }
        });
    });

    $("#disagree_msg").click(function(){
        $.loadModal("${ctx}/abroad/shortMsg_view?id=${passportApply.id}&type=passportApplyUnPass");
    });

    $.register.date($('.input-group.date'), {startDate:'${_today}'});

    $('textarea.limited').inputlimiter();
</script>