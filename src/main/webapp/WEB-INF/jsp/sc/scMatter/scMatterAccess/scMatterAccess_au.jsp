<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    调阅函预览
                    <div style="position: absolute; left:125px;top:8px;">
                        <form action="${ctx}/sc/scMatterAccess_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传调阅函
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/swf/preview?type=html&path=${scMatterAccess.accessFile}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        ${scMatterAccess!=null?"修改":"添加"}调阅信息
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scMatterAccess_au" id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scMatterAccess.id}">
                                <input type="hidden" name="accessFile" value="${scMatterAccess.accessFile}">

                                <div class="form-group">
                                    <label class="col-xs-4 control-label">年份</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" placeholder="请选择年份"
                                                   name="year"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${scMatterAccess.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">调阅日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="accessDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scMatterAccess.accessDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">调阅单位</label>

                                    <div class="col-xs-6">
                                        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                                name="unitId" data-placeholder="请选择"  data-width="240">
                                            <option value="${unit.id}">${unit.name}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">调阅类型</label>

                                    <div class="col-xs-6">
                                        <select required data-rel="select2"
                                                name="isCopy" data-placeholder="请选择" data-width="240">
                                            <option></option>
                                            <option value="0">原件</option>
                                            <option value="1">复印件</option>
                                        </select>
                                        <script>
                                            <c:if test="${not empty scMatterAccess.isCopy}">
                                            $("#modalForm select[name=isCopy]").val('${scMatterAccess.isCopy?1:0}')
                                            </c:if>
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">调阅用途</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="text" name="purpose"
                                               value="${scMatterAccess.purpose}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">调阅对象及调阅明细</label>
                                    <div class="col-xs-8 label-text">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sc/scMatterUser_selects"
                                                name="userId" data-placeholder="请选择调阅对象">
                                            <option></option>
                                        </select>
                                        <button type="button" class="btn btn-success btn-sm"
                                                onclick="_matterItems()">
                                            <i class="fa fa-search"></i> 填报记录
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div style="padding: 10px;">
                                            <div id="itemList">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scMatterAccess.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scMatterAccess!=null?"修改":"添加"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td colspan="6">已选择的调阅对象及调阅明细</td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>工号</td>
            <td>年度</td>
            <td>填报类型</td>
            <td>封面填表日期</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        {{_.each(items, function(item, idx){ }}
        <tr data-id="{{=item.id}}">
            <td>{{=item.realname}}</td>
            <td>{{=item.code}}</td>
            <td>{{=item.year}}</td>
            <td>{{=item.type}}</td>
            <td>{{=item.fillTime}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
                <a href="javasciprt:;" class="up">上移</a>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>

    var selectedItems = [];
    var _selectedItems = ${cm:toJSONArrayWithFilter(itemList, "matterItemId,code,realname,year,type,fillTime")};
    $.each(_selectedItems, function (i, item) {
        item.id = item.matterItemId;
        item.type = (item.type==1)?'年度集中填报':'个别填报';
        item.fillTime = (item.fillTime!=undefined)?item.fillTime.substr(0,10):'';
        //console.log(item)
        selectedItems.push(item);
    })

    function _displayItems(){
        $("#itemList").html(_.template($("#itemListTpl").html())({items: selectedItems}));
    }
    _displayItems();

    function _matterItems() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择填报对象。"
            });
            return;
        }
        $.loadModal("${ctx}/sc/scMatterAccess_selectItems?userId="+userId, 900);
    }

    $(document).off("click", "#itemList .del")
            .on('click', "#itemList .del", function () {
                var $tr = $(this).closest("tr");
                var id = $tr.data("id");
                //console.log("userId=" + userId)
                $.each(selectedItems, function (i, item) {
                    if (item.id == id) {
                        selectedItems.splice(i, 1);
                        return false;
                    }
                })
                $(this).closest("tr").remove();
            });
    $(document).off("click", "#itemList .up")
            .on('click', "#itemList .up", function () {
                var $tr = $(this).parents("tr");
                if ($tr.index() == 0){
                    //alert("首行数据不可上移");
                }else{
                    //$tr.fadeOut().fadeIn();
                    $tr.prev().before($tr);
                }
            });
    /*$(document).off("click", "#itemList .down")
            .on('click', "#itemList .down", function () {
                var $tr = $(this).parents("tr");
                if ($tr.index() != $("#itemList tbody tr").length - 1) {
                    //$tr.fadeOut().fadeIn();
                    $tr.next().after($tr);
                }
            });*/
    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.file));
                        $("#modalForm input[name=accessFile]").val(ret.file);
                    } else {
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var matterItemIds = $.map($("#itemList tbody tr"), function (tr) {
                return $(tr).data("id");
            });
            if ($.trim($("#modalForm input[name=accessFile]").val()) == "") {
                SysMsg.warning("请上传调阅函");
                return;
            }
            $(form).ajaxSubmit({
                data: {matterItemIds: matterItemIds},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>