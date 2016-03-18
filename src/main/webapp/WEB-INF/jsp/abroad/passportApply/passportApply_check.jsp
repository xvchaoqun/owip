<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
        <iframe id="myframe" src="${ctx}/report/passportApply?id=${passportApply.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
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
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <div style="min-width: 400px;">
                            <table  id="logTable" style="min-width: 500px;" class="table table-bordered">
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
                                        <td nowrap>${passportTypeMap.get(passport.classId).name}</td>
                                        <td nowrap>${passport.code}</td>
                                        <td nowrap>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                        <td nowrap>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                        <td nowrap>${passport.isLent?"借出":"-"}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div></div></div>
        </div>
    <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_NOT_PASS}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">证件应交回日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <input ${passportApply.status==PASSPORT_APPLY_STATUS_PASS?"disabled":""} required class="form-control date-picker" name="_expectDate" type="text"
                                   data-date-format="yyyy年mm月dd日" value="${cm:formatDate(passportApply.expectDate,'yyyy年MM月dd日')}" />
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
            <button id="print" class="btn btn-info btn-block" style="font-size: 30px">打印审批表</button>
    </c:if>
            <button class="closeView btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<c:set var="cadre" value="${cadreMap.get(passportApply.cadreId)}"/>
<c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
<c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passportApply.classId)}"/>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>
    stickheader($("#logTable"));
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

                    //$("#item-content").load("${ctx}/passportApply_check?id=${param.id}&_="+new Date().getTime());
                    location.href="${ctx}/passportApply?status=1";
                });

            }
        });
    });
    $("#agree_msg").click(function(){
        loadModal("${ctx}/shortMsg_view?id=${passportApply.id}&type=passportApplyPass");
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
                    //$("#item-content").load("${ctx}/passportApply_check?id=${param.id}&_="+new Date().getTime());
                    location.href="${ctx}/passportApply?status=2";
                });
            }
        });
    });

    $("#disagree_msg").click(function(){
        loadModal("${ctx}/shortMsg_view?id=${passportApply.id}&type=passportApplyUnPass");
    });

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    $('textarea.limited').inputlimiter();
</script>