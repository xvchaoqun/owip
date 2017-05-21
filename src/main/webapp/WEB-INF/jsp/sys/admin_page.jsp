<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">

        <div><button class="btn btn-success confirm" data-url="${ctx}/cache/clear" data-msg="确定清空系统缓存？" >清空缓存</button></div>
        <br/>
        <div><button class="btn btn-success confirm" data-url="${ctx}/cache/flush_metadata_JSON" data-msg="重新生成元数据资源文件？" >重新生成元数据资源文件（metadata.js）</button></div>
        <br/>
        <div><button class="btn btn-success confirm" data-url="${ctx}/cache/flush_location_JSON" data-msg="重新生成省地市资源文件？" >重新生成省地市资源文件（location.js）</button></div>

    </div>
</div>

<script>

</script>