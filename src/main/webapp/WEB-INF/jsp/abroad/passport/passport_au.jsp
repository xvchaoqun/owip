<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="isHandle" value="${not empty param.applyId || not empty param.taiwanRecordId}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        <c:if test="${param.op=='back'}">证件找回</c:if>
        <c:if test="${param.op!='back'}">
            <c:if test="${isHandle}">新办理的证件集中保管</c:if>
            <c:if test="${!isHandle}">
                <c:if test="${passport!=null}">编辑</c:if><c:if test="${passport==null}">添加</c:if>
                ${type==ABROAD_PASSPORT_TYPE_LOST?"丢失":""}证件信息
            </c:if>
        </c:if>
    </h3>
</div>
<div class="modal-body">
    <c:if test="${isDuplicate}">
        <div class="alert alert-warning">
            该干部的${passport.passportClass.name}已存在，请先处理。
        </div>
    </c:if>
    <form class="form-horizontal" action="${ctx}/abroad/passport_au" id="modalForm" method="post"
          enctype="multipart/form-data">
        <input type="hidden" name="id" value="${passport.id}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="taiwanRecordId" value="${param.taiwanRecordId}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="op" value="${param.op}">
        <c:if test="${isHandle}">
            <input type="hidden" name="cadreId" value="${passport.cadre.id}">
        </c:if>

        <div class="form-group">
            <label class="col-xs-3 control-label">干部</label>

            <div class="col-xs-6">
                <select required ${isHandle?"disabled":""} data-rel="select2-ajax"
                        data-ajax-url="${ctx}/cadre_selects?type=0" data-width="273"
                        name="cadreId" data-placeholder="请选择干部">
                    <option value="${passport.cadre.id}">${passport.user.realname}-${passport.user.code}</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">证件名称</label>

            <div class="col-xs-6">
                <c:if test="${isHandle}">
                    <input type="hidden" name="classId" value="${passport.classId}">
                </c:if>
                <select required ${isHandle?"disabled":""} data-rel="select2" data-width="273"
                        name="classId" data-placeholder="请选择证件名称">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_passport_type"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=classId]").val(${passport.classId});
                </script>
                <div style="position: absolute;left: 290px;top: 0px;">
                    <a href="javascript:;" id="checkBtn"
                       class="btn btn-success btn-sm">
                        <i class="fa fa-search"></i> 检测是否新办证件
                    </a>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件号码</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="code" value="${passport.code}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">发证机关</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="authority"
                       value="${empty passport.id?"公安部出入境管理局":passport.authority}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">发证日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker" name="_issueDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">有效期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker" name="_expiryDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>

        <c:if test="${param.op=='back' || type==ABROAD_PASSPORT_TYPE_KEEP ||
            (type==ABROAD_PASSPORT_TYPE_LOST && passport.lostType==ABROAD_PASSPORT_LOST_TYPE_TRANSFER)}">
            <div class="form-group">
                <label class="col-xs-3 control-label">集中保管日期</label>

                <div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_keepDate" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${param.op=='back'?_today:cm:formatDate(passport.keepDate,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${param.op=='back' || type!=ABROAD_PASSPORT_TYPE_LOST}">
            <div class="form-group">
                <label class="col-xs-3 control-label">存放保险柜</label>

                <div class="col-xs-6">
                    <select required data-rel="select2" name="safeBoxId" data-width="273" data-placeholder="保险柜">
                        <option></option>
                        <c:forEach items="${safeBoxMap}" var="safeBox">
                            <option value="${safeBox.key}">${safeBox.value.code}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=safeBoxId]").val(${passport.safeBoxId});
                    </script>
                </div>
            </div>
        </c:if>
        <c:if test="${param.op!='back' && type==ABROAD_PASSPORT_TYPE_LOST}">
            <div class="form-group">
                <label class="col-xs-3 control-label">登记丢失日期</label>

                <div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_lostTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${passport==null?_today:cm:formatDate(passport.lostTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">丢失证明</label>

                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_lostProof"/>
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
<c:if test="${!isDuplicate}">
    <input type="submit" class="btn btn-primary" value="${param.op=='back'?'找回':(passport!=null?'确定':'添加')}"/>
    </c:if>
</div>

<script>
    $("#modalForm #checkBtn").click(function () {

        var $this = $(this);
        var cadreId = $("#modalForm select[name=cadreId]").val();
        var classId = $("#modalForm select[name=classId]").val();
        if (cadreId == '') {
            $.tip({$form: $("#modalForm"), field: "cadreId", msg: "请选择干部", my: 'bottom center', at: 'top center'});
            return;
        }
        if (classId == '') {
            $.tip({$form: $("#modalForm"), field: "classId", msg: "请选择证件", my: 'bottom center', at: 'top center'});
            return;
        }
        $.post("${ctx}/abroad/passport_check", {cadreId: cadreId, classId: classId}, function (ret) {

            if (ret.success) {

                var msg = "<span class='text-success'><i class='fa fa-check'></i> 该干部还未办理此证件<span>";
                switch(ret.result){
                    case 1:
                        msg = "<span class='text-danger'><i class='fa fa-times'></i> 该干部已经存在此证件。<span>";
                        break;
                    case 2:
                        msg = "<span class='text-danger'><i class='fa fa-times'></i> 该干部已经申请办理了此证件，但还未办理证件交回。<span>";
                        break;
                    case 3:
                        msg = "<span class='text-danger'><i class='fa fa-times'></i> 该干部已新申请办理此证件。<span>";
                        break;
                }
                $.tip({
                    $target: $this, $container: $("#pageContent"),
                    msg: msg, my: 'bottom center', at: 'top center'
                })
            }
        });
    })

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#modalForm").validate({
        submitHandler: function (form) {

           $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        var hideInProgress = true;
                        $("#modal").modal('hide').on('hidden.bs.modal', function() {
                            hideInProgress = false;
                            if(ret.id > 0){
                                $.loadModal("${ctx}/shortMsg_view?type=passportKeep&id="+ ret.id);
                            }
                            $(this).off('hidden.bs.modal');
                        });
                        $("#jqGrid").trigger("reloadGrid");
                        <%--<c:if test="${not empty param.applyId}">
                        page_reload();
                        </c:if>--%>
                        //});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'))
    $.register.user_select($('[data-rel="select2-ajax"]'));

    $.fileInput($('#modalForm input[type=file]'))
</script>