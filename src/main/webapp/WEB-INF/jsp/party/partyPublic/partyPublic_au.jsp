<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="bolder" style="cursor: auto;padding-left: 20px;font-size:larger">
            生成党员公示文件
        </span>
    </div>
    <div class="widget-body" style="max-width: 1225px">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8 row">
                <form class="form-horizontal" action="${ctx}/partyPublic_au" autocomplete="off"
                      disableautocomplete id="modalForm" method="post"
                      enctype="multipart/form-data">
                    <div style="width: 600px;float: left;margin-right: 25px">
                        <div class="widget-box" style="height: 530px;">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    公示基本信息
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main" style="padding-bottom: 0px">
                                    <div class="row">
                                        <input type="hidden" name="id" value="${partyPublic.id}">
                                        <input type="hidden" name="isPublish" value="${partyPublic.isPublish?1:0}">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label"><span class="star">*</span> 类别</label>
                                            <div class="col-xs-7">
                                                <div class="input-group">
                                                    <c:forEach items="<%=OwConstants.OW_PARTY_PUBLIC_TYPE_MAP%>" var="_type" varStatus="vs">
                                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                        <input required type="radio" name="type" id="type${_type.key}" value="${_type.key}">
                                                        <label for="type${_type.key}">
                                                            ${_type.value}
                                                        </label>
                                                    </div>
                                                     ${vs.last?'':'&nbsp;&nbsp;'}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label" style="white-space: nowrap"><span class="star">*</span> 公示党委</label>
                                            <div class="col-xs-8">
                                                 <select required class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1&notBranchAdmin=1" data-width="390"
                                                            name="partyId" data-placeholder="请选择">
                                                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                 </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">公示日期</label>
                                            <div class="col-xs-8 label-text">
                                                <c:set var="pubDate" value="${empty partyPublic?_today_dot:cm:formatDate(partyPublic.pubDate,'yyyy.MM.dd')}"/>
                                                <shiro:hasRole name="${ROLE_ODADMIN}">
                                                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                                                    <input required class="form-control" name="pubDate" type="text"
                                                            value="${pubDate}" />
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                </div>
                                                    <script>$.register.date($('.input-group.date'));</script>
                                                </shiro:hasRole>
                                                <shiro:lacksRole name="${ROLE_ODADMIN}">
                                                    ${pubDate}
                                                </shiro:lacksRole>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label"><span class="star">*</span> 院党委电话</label>
                                            <div class="col-xs-8">
                                                <input required class="form-control" type="text" name="phone" value="${partyPublic.phone}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label"><span class="star">*</span> 信箱号码</label>
                                            <div class="col-xs-8">
                                                <input required class="form-control" type="text" name="mailbox" value="${partyPublic.mailbox}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label"><span class="star">*</span> 电子邮箱</label>
                                            <div class="col-xs-8">
                                                <input required class="form-control" type="text" name="email" value="${partyPublic.email}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">备注</label>
                                            <div class="col-xs-8">
                                             <textarea class="form-control limited" rows="2"
                                                       name="remark">${partyPublic.remark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12" style="padding: 0 15px">
                                            <div style="font-weight: bolder">使用说明：</div>
                                            <ol style="padding-left: 20px;">
                                                <li>请先选择公示类别、公示党委</li>
                                                <li>院党委联系电话、信箱号码、电子邮箱默认与党委的基本信息相同，可在此修改（不会同步修改党委的基本信息），请确认信息准确无误</li>
                                                <li class="text-danger">点击“选择公示对象”按钮，选择后的公示对象可进行拖动排序</li>
                                                <li class="text-danger">点击“暂存”按钮可进行保存（不发布），点击“发布公示”按钮可直接发布公示</li>
                                            </ol>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 600px;float: left;">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    公示对象列表
                                    <div class="buttons pull-right" style="right: 15px">
                                        <button id="selectUsersBtn" style="margin-bottom: 0"
                                                class="btn btn-warning btn-sm" type="button"
                                                onclick="_selectUsers()">
                                            <i class="fa fa-plus-circle"></i> 选择公示对象
                                        </button>
                                    </div>
                                    <span class="tip">已选<span
                                                class="count">${fn:length(votes)}</span>人，请确认准确无误。</span>
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main" style="padding:5px">
                                    <table id="jqGrid2" data-width-reduce="20"
                                           class="table-striped"></table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style="clear: both"></div>
                    <div class="clearfix form-actions center" style="margin-top: 5px">
                        <c:if test="${cm:hasRole(ROLE_ODADMIN) || !partyPublic.isPublish}">
                        <button class="submitBtn btn btn-primary" type="button"
                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                data-publish="${partyPublic.isPublish?1:0}">
                            <i class="ace-icon fa ${partyPublic!=null?"fa-edit":"fa-save"} bigger-110"></i>
                            ${partyPublic!=null?"修改":"保存"}<c:if test="${!partyPublic.isPublish}">（暂存）</c:if>
                        </button>
                         </c:if>
                        <c:if test="${!partyPublic.isPublish}">
                        &nbsp;
                        <button class="submitBtn btn btn-success" type="button"
                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                data-publish="1">
                            <i class="ace-icon fa fa-check-square-o bigger-110"></i>
                            发布公示
                        </button>
                        </c:if>
                        <c:if test="${partyPublic.isPublish}">
                        &nbsp;
                        <button class="btn btn-success" type="button" disabled>
                            <i class="ace-icon fa fa-check-square-o bigger-110"></i>
                            公示已发布
                        </button>
                        </c:if>
                    </div>
                </form>
            </div>
            <!-- /.widget-main -->
        </div>
        <!-- /.widget-body -->
    </div>
    <!-- /.widget-box -->
</div>
<style>
    .widget-box .tip {
        margin-left: 30px;
        font-size: 16px;
        color: darkgreen;
    }
    .widget-box .tip .count{
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }
</style>
<script>
    <c:if test="${not empty partyPublic.id}">
	$("#modalForm input[name=type][value=${partyPublic.type}]").prop("checked", true);
	</c:if>

    var $partyId = $("#modalForm select[name=partyId]");
    $.register.del_select($partyId);

    $partyId.change(function () {

        var party = _cMap.partyMap[$(this).val()];
        //console.log(party)
        $("#modalForm input[name=email]").val($.trim(party.email));
        $("#modalForm input[name=phone]").val($.trim(party.phone));
        $("#modalForm input[name=mailbox]").val($.trim(party.mailbox));

        $("#jqGrid2").jqGrid("clearGridData");
        var $jqGrid = $("#jqGrid2");
        var $count = $jqGrid.closest(".widget-box").find(".tip .count");
        $count.html("0");
    });

    function _selectUsers() {
        event.stopPropagation();
        var $type = $("#modalForm input[name=type]:checked");
        var type = $.trim($type.val());
        //console.log(type)
        if (type=='') {
            $.tip({
                $target: $("#modalForm input[name=type]").closest(".input-group"),
                msg: "请选择类别。"
            });
        }
        var partyId = $.trim($partyId.val());
        if (partyId == '') {
            $.tip({
                $target: $partyId.closest("div").find(".select2-container"),
                at: 'bottom center', my: 'top center', type: 'info',
                msg: "请选择公示党委。"
            });
        }
        if (type=='' || partyId == '') {
            return;
        }

        var selectUserIds = $("#jqGrid2").jqGrid("getDataIDs");
        $.loadModal("${ctx}/partyPublic_users?type={0}&partyId={1}&selectUserIds={2}&publicId=${partyPublic.id}"
            .format(type, partyId, selectUserIds.join(",")), 900);
    }

    var votes = ${cm:toJSONArray(votes)};
    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 438,
        width: 580,
        datatype: "local",
        rowNum: votes.length,
        data: votes,
        //党委常委会日期、 类别、 工作证号、姓名、 原任职务、 职务
        colModel: [
            {
                label: '移除', name: '_remove', width: 90, formatter: function (cellvalue, options, rowObject) {
                //console.log(options)
                return '<button class="delRowBtn btn btn-danger btn-xs" type="button" data-id="{0}"><i class="fa fa-minus-circle"></i> 移除</button>'
                        .format(rowObject.userId)
            }
            },
            {label: '姓名', name: 'user.realname'},
            {label: '工作证号', name: 'user.code', width: 120},
            {
                label: '所在支部', name: '_branch', align:'left',  width: 450, formatter:function(cellvalue, options, rowObject){
                    if(rowObject.branchId>0){
                        var branch = _cMap.branchMap[rowObject.branchId];
                        return '<span class="{0}">{1}</span>'
                        .format(branch.isDeleted ? "delete" : "", branch.name);
                    }else{
                        var party = _cMap.partyMap[rowObject.partyId];
                        return '<span class="{0}">{1}</span>'
                        .format(party.isDeleted ? "delete" : "", party.name);
                    }
            }}, {hidden: true, key: true, name: 'userId'}
        ]
    })
    $("#jqGrid2").jqGrid('sortableRows')

    $(document).on("click", ".delRowBtn", function () {
        var $jqGrid = $("#jqGrid2");
        var $count = $jqGrid.closest(".widget-box").find(".tip .count");
        $jqGrid.delRowData($(this).data("id"));
        //console.log($jqGrid.jqGrid("getDataIDs").length)
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    })

    function _preview() {

        var voteUserIds = $("#jqGrid2").jqGrid("getDataIDs");
        if (voteUserIds.length == 0) {
            $.tip({
                $target: $("#selectUsersBtn"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择公示对象。"
            });
        }
        if(!$('#modalForm').valid() || voteUserIds.length == 0){
            return;
        }

        $.loadModal("${ctx}/sc/scPublic_process?export=0&voteUserIds=" + voteUserIds + "&" + $("#modalForm").serialize(), 700);
    }

    $(".submitBtn").click(function () {

        var voteUserIds = $("#jqGrid2").jqGrid("getDataIDs");
        if (voteUserIds.length == 0) {
            $.tip({
                $target: $("#selectUsersBtn"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择公示对象。"
            });
        }
        $("#modalForm input[name=isPublish]").val($(this).data("publish"))
        $("#modalForm").submit();
        return false;
    });

    $("#modalForm").validate({
        submitHandler: function (form) {
            var publish = $("#modalForm input[name=isPublish]").val();
            var $btn = $(".submitBtn[data-pubish='"+publish+"']").button('loading');

            var voteUserIds = $("#jqGrid2").jqGrid("getDataIDs");
            if(voteUserIds.length == 0){
                return;
            }

            $(form).ajaxSubmit({
                data: {userIds: voteUserIds},
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>