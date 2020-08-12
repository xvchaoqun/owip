<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ABROAD_PASSPORT_TYPE_KEEP" value="<%=AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW%>"/>
<div style="width: 900px">
    <h3><c:if test="${passportDraw!=null}">修改</c:if><c:if test="${passportDraw==null}">添加</c:if>证件使用记录</h3>
    <hr/>
    <form class="form-horizontal" action="${ctx}/abroad/passportDraw_au" enctype="multipart/form-data"
          autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${passportDraw.id}">
        <div class="row">
            <div class="col-xs-6">
                <c:if test="${passportDraw==null}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"> 证件持有人</label>
                        <div class="col-xs-6">
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/cadre_selects"
                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                <option></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label"> 选择证件</label>
                        <div class="col-xs-6">
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/abroad/passport_selects"
                                    name="passportId" data-placeholder="请输入证件号码">
                                <option></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>证件号码</label>
                        <div class="col-xs-6">
                            <input required class="form-control" type="text" name="passportCode">
                            <span class="help-block">注：请填写证件库中的证件号码</span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${passportDraw!=null}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"> 证件持有人</label>
                        <div class="col-xs-6 label-text">
                                ${passportDraw.user.realname}
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label"> 证件号码</label>
                        <div class="col-xs-6 label-text">
                            <span class="${passportDraw.passport.type!=ABROAD_PASSPORT_TYPE_KEEP?'delete':''}">${passportDraw.passport.code}</span>（${passportDraw.passport.passportClass.name}）
                        </div>
                    </div>
                </c:if>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>使用类别</label>
                    <div class="col-xs-6">
                        <select required data-rel="select2" name="type" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP%>" var="entity">
                                <option value="${entity.key}">${entity.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=type]").val(${passportDraw.type});
                        </script>
                    </div>
                </div>
                <div class="form-group reason">
                </div>
                <div id="dateDiv">

                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">应归还时间</label>
                    <div class="col-xs-6">
                        <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                            <input class="form-control" name="returnDate" type="text"
                                   value="${cm:formatDate(passportDraw.returnDate,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                 <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>归还状态</label>
                    <div class="col-xs-8">
                        <select required data-rel="select2" name="drawStatus" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP}" var="entity" varStatus="vs">
                                <option value="${entity.key}">${entity.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=drawStatus]").val('${passportDraw.drawStatus}');
                        </script>
                        <span class="help-block">
                            注：如果选择“已领取”，则系统会根据“应归还时间”进行催促交证件
                        </span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">实际归还时间</label>
                    <div class="col-xs-6">
                        <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                            <input class="form-control" name="realReturnDate" type="text"
                                   value="${cm:formatDate(passportDraw.realReturnDate,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group useType">
                    <label class="col-xs-4 control-label">用途</label>
                    <div class="col-xs-6">
                        <select data-rel="select2" name="useType" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_USE_TYPE_MAP%>" var="entity">
                                <option value="${entity.key}">${entity.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=useType]").val(${passportDraw.useType});
                        </script>
                    </div>
                </div>


            </div>
            <div class="col-xs-6">
                <div class="form-group sign">
                    <label class="col-xs-4 control-label">是否签注</label>
                    <div class="col-xs-6">
                        <input type="checkbox"
                               name="needSign" ${(passportDraw!=null && passportDraw.needSign)?"checked":""}/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">审批日期</label>
                    <div class="col-xs-6">
                        <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                            <input class="form-control" name="approveTime" type="text"
                                   value="${cm:formatDate(passportDraw.approveTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">审批人</label>
                    <div class="col-xs-6">
                        <select data-rel="select2-ajax"
                                data-width="245"
                                data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${approvalUser.id}">${approvalUser.realname}-${approvalUser.code}</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label">领取日期</label>
                    <div class="col-xs-6">
                        <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                            <input class="form-control" name="drawTime" type="text"
                                   value="${cm:formatDate(passportDraw.drawTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">领取证件办理人</label>
                    <div class="col-xs-6">
                        <select data-rel="select2-ajax"
                                data-width="245"
                                data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                name="drawUserId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${drawUser.id}">${drawUser.realname}-${drawUser.code}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group self">
                    <label class="col-xs-4 control-label">出行时间范围</label>
                    <div class="col-xs-6">
                        <select data-rel="select2" name="dateType" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="<%=AbroadConstants.ABROAD_APPLY_SELF_DATE_TYPE_MAP%>" var="entity">
                                <option value="${entity.key}">${entity.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=dateType]").val(${applySelf.type});
                        </script>
                    </div>
                </div>
                <div class="form-group self">
                    <label class="col-xs-4 control-label">出行国家或地区</label>
                    <div class="col-xs-6">
                        <input type="text" name="toCountry" id="form-field-tags" value="${applySelf.toCountry}"
                               placeholder="输入后选择国家或按回车 ..." style="width: 450px"/>
                    </div>
                </div>
                <div class="form-group self">
                    <label class="col-xs-4 control-label">同行人员</label>
                    <div class="col-xs-6 choice">
                        <input name="_peerStaff" type="checkbox" value="配偶"> 配偶&nbsp;
                        <input name="_peerStaff" type="checkbox" value="子女"> 子女&nbsp;
                        <input name="_peerStaff" type="checkbox" value="无"> 无&nbsp;
                        <input name="_peerStaff" type="checkbox" value="其他"> 其他
                        <input name="_peerStaff_other" type="text" style="margin-top: 5px">
                        <input name="peerStaff" type="hidden">
                    </div>
                </div>
                <div class="form-group self">
                    <label class="col-xs-4 control-label">费用来源</label>
                    <div class="col-xs-6">
                        <input name="_costSource" type="radio" value="自费"> 自费&nbsp;
                        <input name="_costSource" type="radio" value="其他来源"> 其他来源
                        <input name="_costSource_other" type="text" style="margin-top: 5px">
                        <input name="costSource" type="hidden">
                    </div>
                </div>
                <div class="form-group tw">
                    <label class="col-xs-4 control-label">批件</label>
                    <div class="col-xs-7">
                        <div class="files">
                            <input class="form-control" type="file" name="_files"/>
                        </div>
                        <button type="button" onclick="addFile()" class="btn btn-default btn-xs"><i
                                class="fa fa-plus"></i></button>
                    </div>
                </div>
                <div class="form-group refuseReturnPassport">
                    <label class="col-xs-4 control-label">附件（pdf格式）</label>
                    <div class="col-xs-7">
                        <input class="form-control" type="file" name="_attachment"/>
                        <span class="help-block">注：此处会覆盖已上传的附件</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>
                    <div class="col-xs-7">
                        <textarea class="form-control limited" type="text" name="remark"
                                  rows="2">${passportDraw.remark}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="button"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                id="submitBtn">
            <i class="ace-icon fa fa-check bigger-110"></i>
            ${passportDraw!=null?"确定":"添加"}
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
    </div>
</div>
<style>
    #modalForm input[type=radio], #modalForm input[type=checkbox] {
        width: 16px;
        height: 16px;
        _vertical-align: -1px; /*针对IE6使用hack*/
        vertical-align: -5px;
    }

    #modalForm .tags {
        min-height: 50px;
    }

    #modalForm .form-group {
        padding-bottom: 5px;
        padding-top: 0px !important;
    }

</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script type="text/template" id="reason_tpl">
    {{if(type==''){}}
    <label class="col-xs-4 control-label">事由</label>
    <div class="col-xs-6 label-text">
        请选择使用类别
    </div>
    {{}}}
    {{if(type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>){}}
    <label class="col-xs-4 control-label">事由</label>
    <div class="col-xs-7 choice">
        <c:forEach items="${cm:getMetaTypes('mc_abroad_reason')}" var="r">
            <input name="_reason" type="checkbox" value="${r.value.name}"> ${r.value.name}&nbsp;
        </c:forEach>
        <input name="_reason" type="checkbox" value="其他"> 其他
        <input name="_reason_other" type="text" style="margin-top: 5px">
        <input name="reason" type="hidden"/>
    </div>
    {{}}}
    {{if(type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER%>){}}
    <label class="col-xs-4 control-label"><span class="star">*</span>申请事由</label>
    <div class="col-xs-7">
        <textarea required class="form-control limited" type="text" name="reason">${passportDraw.reason}</textarea>
    </div>
    {{}}}
    {{if(type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW%>
    || type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF%>
    || type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF%>){}}
    <label class="col-xs-4 control-label">因公事由</label>
    <div class="col-xs-7 choice">
        <c:forEach items="${cm:getMetaTypes('mc_abroad_public_reason')}" var="r">
            <input name="_reason" type="checkbox" value="${r.value.name}"> ${r.value.name}&nbsp;
        </c:forEach>
        <input name="_reason" type="checkbox" value="其他"> 其他
        <input name="_reason_other" type="text" style="margin-top: 5px">
        <input name="reason" type="hidden"/>
    </div>
    {{}}}
</script>
<script type="text/template" id="date_tpl">
    {{if(type==<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>){}}
    <div class="form-group">
        <label class="col-xs-4 control-label">实际出行时间</label>
        <div class="col-xs-6">
            <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                <input class="form-control" name="realStartDate" type="text"
                       value="${cm:formatDate(passportDraw.realStartDate,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-4 control-label">实际回国时间</label>
        <div class="col-xs-6">
            <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                <input class="form-control" name="realEndDate" type="text"
                       value="${cm:formatDate(passportDraw.realEndDate,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    {{}}}
    {{if(type!=<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>){}}
    <div class="form-group">
        <label class="col-xs-4 control-label"><span class="star">*</span>使用开始日期</label>
        <div class="col-xs-6">
            <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                <input required class="form-control" name="startDate" type="text"
                       value="${cm:formatDate(passportDraw.startDate,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-4 control-label">使用结束日期</label>
        <div class="col-xs-6">
            <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px;">
                <input class="form-control" name="endDate" type="text"
                       value="${cm:formatDate(passportDraw.endDate,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    {{}}}
</script>
<script>

    var selectCadre = $.register.user_select($('#modalForm select[name=cadreId]'));
    selectCadre.on("change", function () {
        var cadreId = $(this).val();
        $('#modalForm select[name=passportId]').data("ajax-url", "${ctx}/abroad/passport_selects?cadreId=" + cadreId);

        var selectPassport = $.register.ajax_select($('#modalForm select[name=passportId]'));
        selectPassport.on("change", function () {
            var code = $(this).select2("data")[0]['text'] || '';
            $('#modalForm input[name=passportCode]').val(code)
        });
        selectPassport.val(null).trigger('change')
    });

    $.fileInput($('input[type=file][name=_attachment]'), {
        allowExt: ['pdf']
    })

    $("#modalForm select[name=type]").change(function () {
        var type = $(this).val();
        if (type == '') {
            $(".form-group.self, .form-group.tw").show();
        } else {
            if (type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>') {
                $(".form-group.self").show();
                $(".form-group.useType").show();
                $(".form-group.realDate").show();
                $(".form-group.date").hide();
            } else {
                $(".form-group.self").hide();
                $(".form-group.useType").hide();
                $(".form-group.realDate").hide();
                $(".form-group.date").show();
            }
            if (type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER%>') {
                $(".form-group.sign").hide();
            } else {
                $(".form-group.sign").show();
            }
            if (type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW%>'
                || type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF%>'
                || type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF%>') {
                $(".form-group.tw").show();
            } else {
                $(".form-group.tw").hide();
            }
        }

        $(".form-group.reason").html(_.template($("#reason_tpl").html())({type: type}));
        $("#dateDiv").html(_.template($("#date_tpl").html())({type: type}));
        $.register.date($('.input-group.date'));
    }).change();

    $("#modalForm select[name=drawStatus]").change(function () {
        var drawStatus = $(this).val();
        if(drawStatus=='${ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW}'){
            $("#modalForm input[name=returnDate]").attr("required", "required");
            $("#modalForm input[name=returnDate]").closest('.form-group')
                .find('.control-label').html('<span class="star">*</span>应归还时间')
            $("#modalForm input[name=drawTime]").attr("required", "required");
            $("#modalForm input[name=drawTime]").closest('.form-group')
                .find('.control-label').html('<span class="star">*</span>领取时间')

        }else{
            $("#modalForm input[name=returnDate]").removeAttr("required");
            $("#modalForm input[name=returnDate]").closest('.form-group')
                .find('.control-label').html('应归还时间')
            $("#modalForm input[name=drawTime]").removeAttr("required");
            $("#modalForm input[name=drawTime]").closest('.form-group')
                .find('.control-label').html('领取时间')
        }
    }).change();

    $.fileInput($('input[type=file][name="_files"]'))

    function addFile() {
        var _file = $('<input class="form-control" type="file" name="_files" />');
        $(".files").append(_file);
        $.fileInput(_file)
        return false;
    }

    var reason;
    <c:forEach var="reason" items="${fn:split(reasons,'+++')}">
    reason = '${reason}';
    if (reason.startWith("其他:")) {
        $("input[name=_reason][value='其他']").prop("checked", true);
        $("input[name=_reason_other]").val(reason.split(":")[1]);
    } else {
        $("input[name=_reason][value='${reason}']").prop("checked", true);
    }
    </c:forEach>
    var peerStaff;
    <c:forEach var="peerStaff" items="${fn:split(applySelf.peerStaff,'+++')}">
    peerStaff = '${peerStaff}';
    if (peerStaff.startWith("其他:")) {
        $("input[name=_peerStaff][value='其他']").prop("checked", true);
        $("input[name=_peerStaff_other]").val(peerStaff.split(":")[1]);
    } else {
        $("input[name=_peerStaff][value='${peerStaff}']").prop("checked", true);
    }
    </c:forEach>
    var costSource = '${applySelf.costSource}';
    if (costSource == '自费')
        $("input[name=_costSource][value='自费']").prop("checked", true);
    else if (costSource.startWith("其他来源:")) {
        $("input[name=_costSource][value='其他来源']").prop("checked", true);
        $("input[name=_costSource_other]").val(costSource.split(":")[1]);
    }

    var tag_input = $('#form-field-tags');
    try {
        tag_input.tag(
            {
                placeholder: tag_input.attr('placeholder'),
                //enable typeahead by specifying the source array
                source: '${countryList}'
            }
        )
    } catch (e) {
        //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
        tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        //autosize($('#form-field-tags'));
    }

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });

    $("#modalForm").validate({
        submitHandler: function (form) {

            var type = $("#modalForm select[name=type]").val();

            if (type != '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER%>') {
                // 事由
                var $_reason = $("#modalForm input[name=_reason][value='其他']");
                var _reason_other = $("input[name=_reason_other]").val().trim();
                if ($_reason.is(":checked")) {
                    if (_reason_other == '') {
                        SysMsg.info("请输入其他事由", '', function () {
                            $("#modalForm input[name=_reason_other]").val('').focus();
                        });
                        return;
                    }
                }
                var reasons = [];
                $.each($("#modalForm input[name=_reason]:checked"), function () {
                    if ($(this).val() == '其他') {
                        reasons.push("其他:" + _reason_other);
                    } else
                        reasons.push($(this).val());
                });
                $("#modalForm input[name=reason]").val(reasons.join("+++"));
            }
            if (type == '<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>') {
                // 同行人员
                var $_peerStaff = $("#modalForm input[name=_peerStaff][value='其他']");
                var _peerStaff_other = $("#modalForm input[name=_peerStaff_other]").val().trim();
                if ($_peerStaff.is(":checked")) {
                    if (_peerStaff_other == '') {
                        SysMsg.info("请输入其他同行人员", '', function () {
                            $("#modalForm input[name=_peerStaff_other]").val('').focus();
                        });
                        return;
                    }
                }
                var peerStaffs = [];
                $.each($("#modalForm input[name=_peerStaff]:checked"), function () {
                    if ($(this).val() == '其他') {
                        peerStaffs.push("其他:" + _peerStaff_other);
                    } else
                        peerStaffs.push($(this).val());
                });
                $("#modalForm input[name=peerStaff]").val(peerStaffs.join("+++"));

                // 费用来源
                var $_costSource = $("#modalForm input[name=_costSource][value='其他来源']");
                var _costSource_other = $("#modalForm input[name=_costSource_other]").val().trim();
                if ($_costSource.is(":checked")) {
                    if (_costSource_other == '') {
                        SysMsg.info("请输入其他费用来源", '', function () {
                            $("#modalForm input[name=_costSource_other]").val('').focus();
                        });
                        return;
                    }
                }
                var costSources = [];
                $.each($("#modalForm input[name=_costSource]:checked"), function () {
                    if ($(this).val() == '其他来源') {
                        costSources.push("其他来源:" + _costSource_other);
                    } else
                        costSources.push($(this).val());
                });
                $("#modalForm input[name=costSource]").val(costSources.join("+++"));
            }

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm input[name=needSign]").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.input-group.date'));
    $('textarea.limited').inputlimiter();

    <c:if test="${passportDraw.drawStatus==ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN}">
    $("#modalForm select[name=drawStatus]").select2({theme: "default"}).prop("disabled", true);
    </c:if>
</script>