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
            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/clear" data-callback="_reload"
                    data-msg="确定清空系统缓存？">清空缓存
            </button>

            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/flush_metadata_JSON"  data-callback="_reload"
                    data-msg="重新生成元数据资源文件？">
                刷新元数据资源文件（metadata.js）
            </button>

            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/flush_location_JSON"  data-callback="_reload"
                    data-msg="重新生成省地市资源文件？">
                刷新省地市资源文件（location.js）
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
                <i class="fa fa-download"></i>  数据库备份</button>
        </div>
    </div>
</div>
<script>
    function _reload() {
        toastr.success('操作成功。', '成功');
    }
</script>
