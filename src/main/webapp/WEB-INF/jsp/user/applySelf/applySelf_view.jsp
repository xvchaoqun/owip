<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/27
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row applySelf">
  <div class="preview">
    <div class="center">
      <button class="closeView btn btn-default btn-block" style="margin-bottom:10px;font-size: 10px">返回</button>
    </div>
            <table class="table table-bordered table-striped">
              <tbody>
              <tr>
                <td rowspan="3" class="bg-right">申请人情况</td>
                <td class="bg-right">姓名</td>
                <td class="bg-left">${sysUser.realname}</td>
                <td class="bg-right">工作证号</td>
                <td class="bg-left">${sysUser.code}</td>
                <td class="bg-right">性别</td>
                <td class="bg-left">${GENDER_MAP.get(sysUser.gender)}</td>
              </tr>
              <tr>
                <td class="bg-right">身份证号</td>
                <td colspan="3" class="bg-left">${sysUser.idcard}</td>
                <td class="bg-right">联系电话</td>
                <td class="bg-left">${sysUser.mobile}</td>
              </tr>
              <tr>
                <td class="bg-right">工作单位及现任职务</td>
                <td colspan="3" class="bg-left">${unitMap.get(cadre.unitId).name}&nbsp;&nbsp;${cadre.title}</td>
                <td class="bg-right">行政级别</td>
                <td class="bg-left">${adminLevelMap.get(cadre.typeId).name} </td>
              </tr>
              <tr>
                <td rowspan="9" class="bg-right">申请因私出国（境）情况</td>
                <td rowspan="2" class="bg-right">出行时间</td>
                <td colspan="5" class="bg-left">
                  <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
                    <input type="checkbox" class="big chkBox" ${type.key==applySelf.type?"checked":""} disabled> ${type.value}
                    &nbsp;&nbsp;&nbsp;&nbsp;
                  </c:forEach>
                </td>
              </tr>
              <tr>
                <td colspan="5" class="bg-left">${cm:formatDate(applySelf.startDate, "yyyy年 MM月 dd日")}  至  ${cm:formatDate(applySelf.endDate, "yyyy年 MM月 dd日")}</td>
              </tr>
              <tr>
                <td class="bg-right">前往国家或地区</td>
                <td colspan="5" class="bg-left">${applySelf.toCountry}</td>
              </tr>
              <tr>
                <td class="bg-right">出国（境）事由
                </td>
                <td colspan="5" class="bg-left">${fn:replace(applySelf.reason, "+++", ",")}</td>
              </tr>
              <tr>
                <td class="bg-right">同行人员</td>
                <td colspan="5" class="bg-left">${fn:replace(applySelf.peerStaff, "+++", ",")}</td>
              </tr>
              <tr>
                <td class="bg-right">费用来源</td>
                <td colspan="5" class="bg-left">${applySelf.costSource}</td>
              </tr>
              <tr>
                <td class="bg-right">所需证件</td>
                <td colspan="5" class="bg-left">

                  <c:forEach items="${fn:split(applySelf.needPassports, ',')}" var="typeId" varStatus="vs">
                    ${cm:getMetaType("mc_passport_type", typeId).name}
                    ${!vs.last?", ":""}
                  </c:forEach>
                </td>
              </tr>
              <tr>
                <td class="bg-right">其他说明材料</td>
                <td colspan="5" class="bg-left">
                  <c:forEach items="${files}" var="file">
                    <a href="${ctx}/applySelf_download?id=${file.id}" target="_blank">${file.fileName}</a>
                  </c:forEach>
                </td>
              </tr>
              <tr>
                <td class="bg-right">申请人签字</td>
                <td colspan="5" class="bg-left">申请人：${sysUser.realname} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  申请日期：${cm:formatDate(applySelf.applyDate, "yyyy年 MM月 dd日")}</td>
              </tr>
              <c:forEach items="${approvalResultMap}" var="result">
                <c:if test="${result.value.value!=-1}">
                  <c:set var="approvalLog" value="${cm:getApprovalLog(applySelf.id, result.key)}"/>
                  <tr>
                    <td colspan="7" style="text-align: center">
                      <c:if test="${result.key==-1}">组织部初审</c:if>
                      <c:if test="${result.key==0}">组织部终审</c:if>
                      <c:if test="${result.key>0}">
                        <c:set var="approvalType" value="${approverTypeMap.get(result.key)}"/>
                        ${approvalType.name}
                      </c:if>意见
                    </td>
                  </tr>
                  <tr>
                    <td colspan="7"   class="bg-left">
                      <c:if test="${not empty approvalLog}">
                        <c:set var="sysUser" value="${cm:getUserById(approvalLog.userId)}"/>
                        意见：${approvalLog.remark}<br/>
                        审批时间：${cm:formatDate(approvalLog.createTime,'yyyy-MM-dd')}<br/>
                        <c:if test="${!justView}">审批人：${sysUser.realname}<br/></c:if>
                      </c:if>
                      审批状态：${result.value.value==null?"未审批":(result.value.value==0?"未通过":"通过")}
                    </td>
                  </tr>
                </c:if>
              </c:forEach>
              </tbody>
            </table>

            <div class="widget-box transparent">
              <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                  <i class="ace-icon fa fa-info-circle"></i>
                  ${currentYear}年度所有的因私出国（境）申请记录
                </h4>
                <div class="widget-toolbar">
                  <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                  </a>
                </div>
              </div>

              <div class="widget-body">
                <div class="widget-main no-padding">
                  <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                      <th>序号</th>
                      <th>申请日期</th>
                      <th>出行时间</th>
                      <th>回国时间</th>
                      <th>出行天数</th>
                      <th>前往国家或地区</th>
                      <th>因私出国（境）事由</th>
                      <th>审批情况</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                      <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                      <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                      <tr>
                        <td>S${applySelf.id}</td>
                        <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                        <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
                        <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                        <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
                        <td>${applySelf.toCountry}</td>
                        <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                        <td>
                          <c:set var="firstApprovalLog" value="${cm:getApprovalLog(applySelf.id, -1)}"/>
                          <c:set var="lastApprovalLog" value="${cm:getApprovalLog(applySelf.id, 0)}"/>
                          <c:if test="${empty firstApprovalLog || (firstApprovalLog.status && empty lastApprovalLog)}">
                            待审批
                          </c:if>
                          <c:if test="${(firstApprovalLog!=null && !firstApprovalLog.status)||
                          (lastApprovalLog!=null && !lastApprovalLog.status)}">
                            未通过审批
                          </c:if>
                          <c:if test="${lastApprovalLog.status}">
                            通过审批
                          </c:if>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                </div></div></div>
    <div class="center">
      <button class="closeView btn btn-default btn-block" style="margin-top:10px;font-size: 10px">返回</button>
    </div>
          </div>

  <div class="info">
    <c:if test="${param.type=='aproval'}">
    <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
        <div style="font-size: 30px; padding-bottom: 10px">组织部初审意见：</div>
        <div style="font-size: 20px; padding-bottom: 10px">
          <input type="checkbox" class="big" id="agree"/> 符合申请条件，同意申请<br/>
          <input type="checkbox" class="big" id="disagree"/> 不符合申请条件，不同意申请
        </div>
        <div>
          <textarea id="reason" disabled placeholder="不同意申请原因" type="text" name="reason" rows="4" cols="60"></textarea>
        </div>
      <div>
        <button id="submitBtn" disabled class="btn btn-info btn-block" style="margin-top:20px;font-size: 20px">提交领导审批/退回申请</button>
      </div>
    </div>
    </c:if>

  </div>

</div>
<c:if test="${param.type=='aproval'}">
<script>
  $("#agree").click(function(){
      if($("#agree").prop("checked")) {
        $("#disagree").prop("checked", false);
        $("#reason").hide();
        $("#submitBtn").removeAttr("disabled").html("提交领导审批");
      }else{
        $("#reason").attr("disabled", "disabled").show();
        $("#submitBtn").attr("disabled", "disabled").html("提交领导审批/退回申请");
      }
  });
  $("#disagree").click(function(){
    if($("#disagree").prop("checked")) {
      $("#agree").prop("checked", false);
      $("#reason").removeAttr("disabled").show();
      $("#submitBtn").removeAttr("disabled").html("退回申请");
    }else{
      $("#reason").attr("disabled", "disabled").show();
      $("#submitBtn").attr("disabled", "disabled").html("提交领导审批/退回申请");
    }
  });
  $("#submitBtn").click(function(){
    if($("#agree").prop("checked")){
      $.post("${ctx}/applySelf_approval",{status:1, applySelfId:"${param.id}",
        approvalTypeId:"${param.approvalTypeId}", remark:""},function(ret){
          if(ret.success){
            SysMsg.success('操作成功。', '成功',function(){
              page_reload();
            });
          }
      });
    }else  if($("#disagree").prop("checked")){
      var reason = $("#reason").val().trim();
      if(reason==''){
        SysMsg.info("请填写原因",'',function(){
          $("#reason").focus();
        });
        return;
      }
      $.post("${ctx}/applySelf_approval",{status:0, applySelfId:"${param.id}",
        approvalTypeId:"${param.approvalTypeId}", remark:reason},function(ret){
        if(ret.success){
          page_reload();
          SysMsg.success('操作成功。', '成功');
        }
      });
    }
  });
</script>
  </c:if>
