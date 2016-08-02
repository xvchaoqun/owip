<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- inline scripts related to this page -->
<script src="assets/js/flot/jquery.flot.js"></script>
<script src="assets/js/flot/jquery.flot.pie.js"></script>
<script src="assets/js/flot/jquery.flot.resize.js"></script>
<script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
     <div class="row">
        <div class="col-xs-12">
          <!-- PAGE CONTENT BEGINS -->
          <%--<div class="alert alert-block alert-success">
            <button type="button" class="close" data-dismiss="alert">
              <i class="ace-icon fa fa-trash"></i>
            </button>

            <i class="ace-icon fa fa-check green"></i>
            欢迎使用组织工作管理与服务一体化平台
          </div>--%>

          <div class="row">
            <div class="col-sm-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-hourglass-end orange"></i>
                    待处理事项
                  </h4>
                  <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                      <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                  </div>
                </div>
                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div class="col-sm-12">

                      <div class="infobox infobox-green">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-comments"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><a href="${ctx}/memberApply_layout?stage=${APPLY_STAGE_DRAW}">${growOdCheckCount}</a> <span style="font-size: 10pt;">未处理</span></span>
                          <div class="infobox-content">领取志愿书审批</div>
                        </div>
                      </div>

                      <div class="infobox infobox-blue">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-calendar-minus-o"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><a href="${ctx}/memberOut?cls=6">${memberOutCount}</a> <span style="font-size: 10pt;">待审</span></span>

                          <div class="infobox-content">组织关系转出审批</div>
                        </div>
                      </div>
                      <div class="infobox infobox-blue2">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-exclamation-triangle"></i>
                        </div>

                        <div class="infobox-data">
                          <span class="infobox-data-number"><a href="${ctx}/memberIn?cls=1">${memberInCount}</a> <span style="font-size: 10pt;">人</span> </span>

                          <div class="infobox-content">
                            组织关系转入申请
                          </div>
                        </div>
                      </div>

                      <div class="infobox infobox-red">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-plane"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><a href="${ctx}/graduateAbroad?cls=3">${graduateAbroadCount}</a> <span style="font-size: 10pt;">申请</span></span>
                          <div class="infobox-content">党员出国申请暂留</div>
                        </div>
                      </div>
                    </div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->

            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->

          <div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-star green"></i>
                    党员基本信息统计
                  </h4>
                  <div class="widget-toolbar">
                      <a href="#" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>
                </div>
                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div class="row">
                      <c:import url="/stat_member_count"/>
                      <c:import url="/stat_member_age"/>
                      <c:import url="/stat_member_apply"/>
                    </div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>
          <div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-signal"></i>
                    党员人数分布（前二十）
                  </h4>
                  <div class="widget-toolbar">
                      <a href="#" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>
                </div>

                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <c:import url="/stat_member_party"/>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>
          <%--<div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-signal"></i>
                    发展党员趋势图
                  </h4>

                  <!--<div class="widget-toolbar">
                      <a href="#" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>-->
                </div>

                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div id="trend-charts"></div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>--%>

          <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

<script type="text/javascript">
</script>