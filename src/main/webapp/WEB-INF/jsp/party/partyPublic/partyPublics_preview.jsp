<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>预览已发布公示列表</h3>
</div>
<div class="modal-body" id="listDiv" style="max-height: 300px">

</div>
<div class="modal-footer">
    <div style="color: red; font-size: 16px; font-weight: bolder;text-align: left;padding-bottom: 20px;">
        注：
        <div>
            1、公示列表显示的是已发布的最新20条公示。<br/>
            2、公示列表存在10分钟的缓存。<br/>
            3、刚发布的公示如果想立刻生效，请点击“立即刷新”按钮
        </div>
    </div>
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 立即刷新
    </button>
</div>
<script>
var writes=[];
document.write=[].push.bind(writes);
</script>
<script type="text/javascript" src="/public/partyPublics"></script>
<script>
 $("#listDiv").html(writes.join(" "));
</script>
<script>
    $("#submitBtn").click(function () {
        var $btn = $("#submitBtn").button('loading');
        $.post("${ctx}/partyPublics_refresh", function (ret) {
            $btn.button('reset');
            if(ret.success){
                $.tip({
                    $target: $("#submitBtn"),
                    at: 'top center', my: 'bottom center', type: 'success',
                    msg: "刷新成功。"
                });

                writes=[]
                document.write=[].push.bind(writes);
                $.when(
                    $.getScript( "/public/partyPublics" ),
                    $.Deferred(function( deferred ){
                        $( deferred.resolve );
                    })
                ).done(function(){
                    //console.log(writes)
                    $("#listDiv").html(writes.join(" "));
                });

                //$("#modal #list").load("${ctx}/public/partyPublics")
            }
        })
        return false;
    });
</script>