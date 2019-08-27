<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_DELETE%>" var="UNIT_POST_STATUS_DELETE"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMotion!=null}">编辑</c:if><c:if test="${scMotion==null}">添加</c:if>动议</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMotion_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${scMotion.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
            <div class="col-xs-6">
                <div class="input-group date"
                     data-date-format="yyyy" data-date-min-view-mode="2"
                     style="width: 100px;">
                    <input required autocomplete="off" class="form-control" placeholder="请选择年份"
                           name="year" type="text"
                           value="${empty scMotion.year?_thisYear:scMotion.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>动议主体</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="way"
                        data-placeholder="请选择" data-width="273">
                    <option></option>
                    <c:forEach items="<%=ScConstants.SC_MOTION_WAY_MAP%>" var="way">
                        <option value="${way.key}">${way.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=way]").val(${scMotion.way});
                </script>
            </div>
        </div>
        <div class="form-group" id="wayOtherDiv">
            <label class="col-xs-3 control-label"><span class="star">*</span>其他动议主体</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="wayOther" value="${scMotion.wayOther}">
            </div>
        </div>
        <div class="form-group groupTopic" style="display: none">
            <label class="col-xs-3 control-label"><span class="star">*</span>动议记录</label>
            <div class="col-xs-6">
                <select required name="groupTopicId" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/sc/scGroupTopic_selects?type=${cm:getMetaTypeByCode("mt_sgt_motion").id}"
                        data-width="273"
                        data-placeholder="请选择">
                    <option value="${scGroupTopic.id}">
                        [${cm:formatDate(scGroupTopic.holdDate, "yyyy.MM.dd")}]${scGroupTopic.name}</option>
                </select>
                <script>
                    $.register.ajax_select($("#modalForm select[name=groupTopicId]"))
                </script>
            </div>
        </div>
        <div class="form-group committeeTopic" style="display: none">
            <label class="col-xs-3 control-label"><span class="star">*</span>动议记录</label>
            <div class="col-xs-6 committeeTopic">
                <select required name="committeeTopicId" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/sc/scCommitteeTopic_selects"
                        data-width="273"
                        data-placeholder="请选择">
                    <option value="${scCommitteeTopic.id}">
                        [${cm:formatDate(scCommitteeTopic.holdDate, "yyyy.MM.dd")}]${scCommitteeTopic.name}</option>
                </select>
                <script>
                    $.register.ajax_select($("#modalForm select[name=committeeTopicId]"))
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>动议时间</label>
            <div class="col-xs-6" style="width: 160px">
                <div class="input-group date" data-date-format="yyyy.mm.dd">
                    <input required class="form-control" autocomplete="off" name="holdDate" type="text"
                           value="${empty scMotion.holdDate?_today_dot:(cm:formatDate(scMotion.holdDate,'yyyy.MM.dd'))}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>拟调整岗位</label>
            <div class="col-xs-6">
                <select required name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
                        data-width="273"
                        data-placeholder="请选择">
                    <option value="${unitPost.id}" delete="${unitPost.status==UNIT_POST_STATUS_DELETE}">${unitPost.code}-${unitPost.name}</option>
                </select>
                <script>
                    $.register.del_select($("#modalForm select[name=unitPostId]"))
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>选任方式</label>
            <div class="col-xs-6">
                <select required data-rel="select2" data-width="273"
                        name="scType" data-placeholder="请选择">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_sc_type"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=scType]").val(${scMotion.scType});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">工作方案</label>
            <div class="col-xs-6">
                <textarea id="contentId">${scMotion.content}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" style="width: 320px" name="remark">${scMotion.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer"><a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i> ${scMotion!=null?'确定':'添加'}</button>
</div>
<script>
    var contentKe = KindEditor.create('#contentId', {
        items: ["wordpaste", "source", "clearstyle", "|", "fullscreen"],
        height: '130px',
        width: '320px',
        minWidth: 320,
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {content: contentKe.html()},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $.register.date($('.input-group.date'));

    $("#modalForm select[name=committeeTopicId]").change(function () {
        var data = $(this).select2("data")[0];
        if (data.holdDate != undefined) {
            $("#modalForm input[name=holdDate]")
                .closest(".input-group.date")
                .datepicker('update', data.holdDate);
        }
    });

    $("#modalForm select[name=groupTopicId]").change(function () {
        var data = $(this).select2("data")[0];
        if (data.holdDate != undefined) {
            $("#modalForm input[name=holdDate]")
                .closest(".input-group.date")
                .datepicker('update', data.holdDate);
            var unitPost = data.unitPost;
            if (unitPost != undefined) {
                $("#modalForm select[name=unitPostId]").html('<option value="{0}" delete="{2}">{1}</option>'
                    .format(unitPost.id, unitPost.code + "-" + unitPost.name, unitPost.status == '${UNIT_POST_STATUS_DELETE}'))
                    .trigger("change");
            }
            $("#modalForm select[name=scType]").val(data.scType).trigger("change");
            if (!$.isBlank(data.content)) {
                contentKe.html(data.content)
            }
        }
    });

    function wayChange() {

        var way = $("#modalForm select[name=way]").val();
        if (way == '<%=ScConstants.SC_MOTION_WAY_OTHER%>') {
            $("#wayOtherDiv").show();
            $("#modalForm input[name=wayOther]").attr("required", "required");
            $(".groupTopic").hide().find("select").val(null).trigger('change').prop("required", false);
            $(".committeeTopic").hide().find("select").val(null).trigger('change').prop("required", false);
        } else {
            $("#wayOtherDiv").hide();
            $("#modalForm input[name=wayOther]").removeAttr("required");
            if (way == '<%=ScConstants.SC_MOTION_WAY_GROUP%>') {
                $(".groupTopic").show().find("select").prop("required", true);
                $(".committeeTopic").hide().find("select").prop("required", false);
                <c:if test="${empty committeeTopic}">
                $(".committeeTopic").val(null).trigger('change');
                </c:if>
            } else if (way == '<%=ScConstants.SC_MOTION_WAY_COMMITTEE%>') {
                $(".groupTopic").hide().find("select").prop("required", false);
                $(".committeeTopic").show().find("select").prop("required", true);
                <c:if test="${empty groupTopic}">
                $(".groupTopic").val(null).trigger('change');
                </c:if>
            } else {
                $(".groupTopic").hide().find("select").val(null).trigger('change').prop("required", false);
                $(".committeeTopic").hide().find("select").val(null).trigger('change').prop("required", false);
            }
        }
    }

    $("#modalForm select[name=way]").change(function () {
        wayChange();
    });
    wayChange();
</script>