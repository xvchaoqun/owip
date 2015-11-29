<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="closeView btn btn-mini btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/unit_base?id=${param.id}">基本信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitTransfer_page?unitId=${param.id}">单位历程相关文件</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitAdminGroup_page?unitId=${param.id}">行政班子</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitCadreTransferGroup_page?unitId=${param.id}">干部任免信息</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                        <c:import url="/unit_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
<script>
    $("#view-box .nav-tabs li a").click(function(){
        $this = $(this);
        var $container = $("#view-box .tab-content");
        $container.showLoading({'afterShow':
                function() {
                    setTimeout( function(){

                        $container.hideLoading();
                    }, 2000 );
                }}) ;
        if($(this).data("url")!='') {
            $container.load($(this).data("url"), function () {
                $container.hideLoading();
                $("#view-box .nav-tabs li").removeClass("active");
                $this.closest("li").addClass("active");
            });
        }else{
            $container.hideLoading();
            toastr.warning("暂缓开通该功能");
        }
    });
</script>