<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="page-header">
            <h1>
                <i class="fa fa-soundcloud"></i> 缓存管理
            </h1>
        </div>
        <div class="buttons">
            <button class="btn btn-warning btn-sm" onclick="_clearSysBaseCache(this)"
                     data-loading-text="<i class='fa fa-spinner fa-spin '></i> 清除中，请稍后"
                    data-msg="确定清除系统基础数据缓存？"><i class="fa fa-eraser"></i> 清除基础数据缓存
            </button>
            <button class="btn btn-danger confirm btn-sm pull-right" data-url="${ctx}/cache/clear" data-callback="_reload"
                    data-msg="确定清空全部的系统缓存？<br/>（会踢出所有用户，请谨慎操作！）"><i class="fa fa-chain-broken"></i> 清空全部缓存
            </button>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="page-header">
            <h1>
                <i class="fa fa-soundcloud"></i> 清除指定缓存
            </h1>
        </div>
        <div style="width: 600px">
            <form class="form-horizontal" action="${ctx}/cache/clear" autocomplete="off" disableautocomplete id="cacheForm"
                  method="post">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>缓存名称</label>
                    <div class="col-xs-6">
                        <input required class="form-control" type="text" name="name">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">缓存key</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="key">
                        <span class="help-block">留空清除所有key</span>
                    </div>
                </div>
            </form>
            <div class="modal-footer center">
                <div style="text-align: left">
                    校园账号表结构缓存：ColumnBeans#tablename
                </div>
                <button type="button" id="cacheBtn"
                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 操作中..."
                        class="btn btn-primary"><i class="fa fa-check"></i> 确定清除</button>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="page-header">
            <h1>
                <i class="fa fa-files-o"></i> 文件缓存管理
            </h1>
        </div>
        <div class="buttons">
            <button class="btn btn-success btn-sm" onclick="_reloadMetaData(this)"
                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 刷新中，请稍后">
                <i class="fa fa-refresh"></i> 刷新元数据资源文件（metadata.js）
            </button>

            <button class="runBtn btn btn-success btn-sm" data-url="${ctx}/cache/flush_location_JSON"
                    data-callback="_reload">
                <i class="fa fa-refresh"></i> 刷新省地市资源文件（location.js）
            </button>
        </div>
    </div>

    <div class="col-xs-12">
        <div class="space-4"></div>
        <div class="space-4"></div>
        <div class="page-header">
            <h1>
                <i class="fa fa-database"></i> 数据库备份
            </h1>
        </div>
        <div class="buttons">
            <button ${cm:isSuperAccount(_user.username)?'':'disabled'}
                    class="downloadBtn btn btn-success btn-sm" data-url="${ctx}/system/db_backup">
                <i class="fa fa-download"></i> 数据库备份
            </button>
        </div>
    </div>
</div>
<script>
    $("#cacheBtn").click(function () {
        $("#cacheForm").submit();
        return false;
    });
    $("#cacheForm").validate({
        submitHandler: function (form) {

             var $btn = $("#cacheBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.tip({
                            $target: $("#cacheBtn"),
                            at: 'top center', my: 'bottom center', type: 'success',
                            msg: "操作成功。"
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    function _reload() {
        toastr.success('操作成功。', '成功');
    }

    function _reloadMetaData(btn) {
        var $btn = $(btn).button('loading');
        $.reloadMetaData(function () {
            toastr.success('操作成功。', '成功');
            $btn.button('reset');
        });
    }

    function _clearSysBaseCache(btn) {

        var $btn = $(btn).button('loading');
        $.getJSON("${ctx}/cache/clear?clearBase=1", function(ret){
            if(ret.success){
                $.reloadMetaData(function () {
                    toastr.success('操作成功。', '成功');
                    $btn.button('reset');
                });
            }else{
                $btn.button('reset');
            }
        })
    }
</script>
