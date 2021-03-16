<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-block alert-warning" style="padding:5px;margin-bottom: 5px">
                    <i class="ace-icon fa fa-smile-o green"></i>
                    欢迎使用${_plantform_name}，请点击屏幕<span class="text text-danger bolder">左上角按钮 <span style="background-color: #61a8d1;
    text-align: center;color: white;padding: 0 5px;"><i class="fa fa-bars"></i></span></span> 弹出系统菜单。
                </div>
                    <shiro:hasPermission name="stat:cadre">
                        <div class="alert alert-block alert-success" style="margin-bottom: 10px;padding:5px">
                            <c:import url="/m/stat_cadre_count"/>
                        </div>
                        <div class="alert alert-block alert-success" style="margin-bottom: 10px;padding:5px">
                            <c:import url="/m/stat_cadreAge_count"/>
                        </div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="stat:ow">
                        <div class="alert alert-block alert-success" style="margin-bottom: 10px;padding:5px">
                        <c:import url="/m/stat_member_count"/>
                        </div>
                    </shiro:hasPermission>
                       <%-- <div id="cadreCountDiv" style="height: 180px"></div>--%>
                        <%--  <shiro:hasPermission name="m:applySelfList:*">
                          <div class="ahref infobox infobox-blue2" data-url="${ctx}/m/abroad/applySelfList?status=0">
                              <div class="infobox-icon">
                                  <i class="ace-icon fa fa-history"></i>
                              </div>
                              <div class="infobox-data">
                                  <span class="infobox-data-number">${notApprovalCount} <span style="font-size: 10pt;">待审批</span></span>
                                  <div class="infobox-content">因私出国（境）申请</div>

                              </div>
                          </div>
                          <div class="ahref infobox infobox-green" data-url="${ctx}/m/abroad/applySelfList?status=1">
                              <div class="infobox-icon">
                                  <i class="ace-icon fa fa-check-square-o"></i>
                              </div>
                              <div class="infobox-data">
                                  <span class="infobox-data-number">${hasApprovalCount}  <span style="font-size: 10pt;">已审批</span></span>
                                  <div class="infobox-content">因私出国（境）申请</div>
                              </div>
                          </div>
                          </shiro:hasPermission>
                          <shiro:hasPermission name="m:applySelf:*">
                          <div class="ahref infobox infobox-blue2" data-url="${ctx}/m/abroad/applySelf?status=0">
                              <div class="infobox-icon">
                                  <i class="ace-icon fa fa-history"></i>
                              </div>
                              <div class="infobox-data">
                                  <span class="infobox-data-number">${notApprovalCount} <span style="font-size: 10pt;">未完成审批</span></span>
                                  <div class="infobox-content">因私出国（境）申请</div>

                              </div>
                          </div>
                          <div class="ahref infobox infobox-green" data-url="${ctx}/m/abroad/applySelf?status=1">
                              <div class="infobox-icon">
                                  <i class="ace-icon fa fa-check-square-o"></i>
                              </div>
                              <div class="infobox-data">
                                  <span class="infobox-data-number">${hasApprovalCount}  <span style="font-size: 10pt;">已完成审批</span></span>
                                  <div class="infobox-content">因私出国（境）申请</div>
                              </div>
                          </div>
                          </shiro:hasPermission>--%>
                    </div>
                    <!-- /.col -->

        <!-- /.row -->
        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
</div>
