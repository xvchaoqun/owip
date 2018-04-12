<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

  <ul class="preview title nav nav-tabs tab-color-blue background-blue"
      style="/*padding-right: 20px;*/margin-bottom: 10px!important;">
      <div class="buttons" style="margin-bottom: 8px;margin-left: 10px; ">
        <a href="javascript:;" class="closeView btn btn-xs btn-success">
          <i class="ace-icon fa fa-backward"></i>
          返回
        </a>
        <c:if test="${param.status==0}">
          <c:if test="${param.type!='admin' ||(param.type=='admin' && applySelf.flowNode<=0)}">
            <div class="pull-right" style="margin-right: 10px;">
        <button id="agree" style="margin-left: 10px; margin-right: 10px;"
           class="btn btn-primary btn-xs"><i class="fa fa-check"></i> 同意申请</button>
        <button id="disagree" class="btn btn-danger btn-xs">
          <i class="fa fa-times"></i>  不同意申请</button>
            </div>
        </c:if>

        <c:if test="${param.type=='admin' && applySelf.flowNode>0}">
          <c:set var="approverType" value="${approverTypeMap.get(applySelf.flowNode)}"/>
          <button disabled class="btn btn-default btn-xs pull-right">当前：${approverType.name}审批</button>
        </c:if>
        </c:if>

        <c:if test="${param.status==1 && param.type=='admin'}">
          <button id="msgBtn" style="margin-left: 10px; margin-right: 10px;"
                  class="btn btn-primary btn-xs"><i class="fa fa-info-circle"></i> 短信提醒</button>
        </c:if>
      </div>
  </ul>
<div class="tabbable">
  <ul class="nav nav-tabs" id="myTab">
    <li class="active">
      <a data-toggle="tab" href="#detail">
        <i class="green ace-icon fa fa-hand-o-down  bigger-120"></i>
        详情
      </a>
    </li>
    <li>
      <a data-toggle="tab" href="#logs">
        <i class="green ace-icon fa fa-history  bigger-120"></i>
        本年度因私出国境记录
        <c:set var="yearCount" value="${fn:length(applySelfs)}"/>
        <c:if test="${yearCount>0}">
        <span class="badge badge-danger">${yearCount}</span>
        </c:if>
      </a>
    </li>
  </ul>

  <div class="tab-content">
    <div id="detail" class="tab-pane fade in active">

      <div class="tabbable">
        <ul class="nav nav-tabs" id="myTab3">
          <li class="active">
            <a data-toggle="tab" href="#home3">
              <i class="pink ace-icon fa fa-user"></i>
              申请人
            </a>
          </li>

          <li>
            <a data-toggle="tab" href="#profile3">
              <i class="blue ace-icon fa fa-info-circle"></i>
              申请情况
            </a>
          </li>

          <li>
            <a data-toggle="tab" href="#dropdown13">
              <i class="ace-icon fa fa-check-circle"></i>
              审批情况
            </a>
          </li>
        </ul>

        <div class="tab-content">
          <div id="home3" class="tab-pane in active">

            <div class="profile-user-info profile-user-info-striped">
              <div class="profile-info-row">
                <div class="profile-info-name td"> 姓名 </div>

                <div class="profile-info-value td">
                  <span class="editable">${sysUser.realname}</span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name td"> 工作证号 </div>
                <div class="profile-info-value td">
                  <span class="editable">${sysUser.code}</span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name td"> 性别 </div>

                <div class="profile-info-value td">
                  <span class="editable">${GENDER_MAP.get(sysUser.gender)}</span>
                </div>
              </div>

              <%--<div class="profile-info-row">
                <div class="profile-info-name td"> 身份证号 </div>

                <div class="profile-info-value td">
                  <span class="editable">${sysUser.idcard}</span>
                </div>
              </div>--%>

              <div class="profile-info-row">
                <div class="profile-info-name td"> 联系电话 </div>

                <div class="profile-info-value td">
                  <span class="editable">
                  <a href='tel:${sysUser.mobile}'>${sysUser.mobile}</a>
                  </span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name"> 工作单位及职务</div>

                <div class="profile-info-value">
                  <span class="editable">${cadre.title}</span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name td"> 行政级别 </div>

                <div class="profile-info-value td">
                  <span class="editable" >${adminLevelMap.get(cadre.typeId).name}</span>
                </div>
              </div>
            </div>

          </div>

          <div id="profile3" class="tab-pane">

            <div class="profile-user-info profile-user-info-striped">
              <div class="profile-info-row">
                <div class="profile-info-name td"> 申请日期 </div>
                <div class="profile-info-value td">
                  <span class="editable">${cm:formatDate(applySelf.applyDate, "yyyy年 MM月 dd日")}</span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name td"> 出发时间 </div>

                <div class="profile-info-value td">
                  <span class="editable">
                  ${cm:formatDate(applySelf.startDate, "yyyy年 MM月 dd日")}
                  </span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name td"> 回国时间 </div>

                <div class="profile-info-value td">
                  <span class="editable">
                   ${cm:formatDate(applySelf.endDate, "yyyy年 MM月 dd日")}
                  </span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name td"> 同行人员 </div>

                <div class="profile-info-value td">
                  <span class="editable">${fn:replace(applySelf.peerStaff, "+++", ",")}</span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name td"> 前往国家或地区 </div>
                <div class="profile-info-value td">
                  <span class="editable">${applySelf.toCountry}</span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name"> 出国（境）事由 </div>

                <div class="profile-info-value">
                  <span class="editable">${fn:replace(applySelf.reason, "+++", ",")}</span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name"> 费用来源 </div>

                <div class="profile-info-value">
                  <span class="editable">${applySelf.costSource}</span>
                </div>
              </div>

              <div class="profile-info-row">
                <div class="profile-info-name"> 所需证件 </div>

                <div class="profile-info-value">
                  <span class="editable">
                    <c:forEach items="${fn:split(applySelf.needPassports, ',')}" var="typeId" varStatus="vs">
                      ${cm:getMetaType(typeId).name}
                      ${!vs.last?", ":""}
                    </c:forEach>
                  </span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name"> 备注 </div>

                <div class="profile-info-value">
                  <span class="editable">
                    ${applySelf.remark}
                  </span>
                </div>
              </div>
              <div class="profile-info-row">
                <div class="profile-info-name"> 其他说明材料 </div>

                <div class="profile-info-value">
                  <span class="editable" >
                    <c:if test="${fn:length(files)==0}">无</c:if>
                    <c:forEach items="${files}" var="file">
                      <a href="${ctx}/abroad/applySelf_download?id=${file.id}" target="_blank">${file.fileName}</a>
                    </c:forEach>
                  </span>
                </div>
              </div>

            </div>
          </div>

          <div id="dropdown13" class="tab-pane">
            <div class="profile-user-info profile-user-info-striped">
            <c:forEach items="${approvalResultMap}" var="result">
              <c:if test="${result.value.value!=-1}">
                <c:set var="approvalLog" value="${cm:getApprovalLog(applySelf.id, result.key)}"/>
                <div class="profile-info-row">
                  <div class="profile-info-name">
                    <c:if test="${result.key==-1}">组织部初审</c:if>
                    <c:if test="${result.key==0}">组织部终审</c:if>
                    <c:if test="${result.key>0}">
                      <c:set var="approvalType" value="${approverTypeMap.get(result.key)}"/>
                      ${approvalType.name}
                    </c:if>意见
                  </div>

                  <div class="profile-info-value">
                  <span class="editable" >
                    审批结果：${result.value.value==null?"未审批":(result.value.value==0?"未通过":"通过")}
                    <br/>
                    <c:if test="${not empty approvalLog}">
                      <c:set var="sysUser" value="${cm:getUserById(approvalLog.userId)}"/>
                      审批意见：${approvalLog.remark}<br/>
                      <c:if test="${!justView}">审批人：${sysUser.realname}<br/></c:if>
                      审批时间：${cm:formatDate(approvalLog.createTime,'yyyy-MM-dd')}
                    </c:if>
                  </span>
                  </div>
                </div>
              </c:if>
            </c:forEach>
              </div>
          </div>
        </div>
      </div>



    </div>

    <div id="logs" class="tab-pane fade">
      <div class="message-list-container">
        <div class="message-list">
          <c:if test="${fn:length(applySelfs)==0}">
            <div class="none">本年度无因私出国（境）记录</div>
          </c:if>
          <c:forEach items="${applySelfs}" var="applySelf">
            <c:set var="cadre" value="${cm:getCadreById(applySelf.cadreId)}"/>
            <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
            <div class="popView message-item"  data-url="${ctx}/m/abroad/applySelf_detail?id=${applySelf.id}">
              <i class="message-star ace-icon fa ${status==1?'fa-star orange2':'fa-star-o light-green'}"></i>
              <span class="sender">
                ${cm:formatDate(applySelf.startDate,'MM.dd')}~${cm:formatDate(applySelf.endDate,'MM.dd')}，
                                        <c:set var="reasons" value="${fn:split(applySelf.reason, '+++')}"/>
                                        ${reasons[0]}${fn:length(reasons)>1?'等':''}，
                                         <c:set var="toCountrys" value="${fn:split(applySelf.toCountry, ',')}"/>
                                        ${toCountrys[0]}${fn:length(toCountrys)>1?'等':''}
              </span>
              <span class="time">${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</span>

            </div>
          </c:forEach>
        </div>
      </div>
    </div>

  </div>
</div>
<script>
  <c:if test="${param.status==0}">
  $("#agree").click(function(){
    $.loadModal("${ctx}/m/abroad/applySelf_approval?id=${applySelf.id}&pass=1&type=${param.type}");
  });
  $("#disagree").click(function(){
    $.loadModal("${ctx}/m/abroad/applySelf_approval?id=${applySelf.id}&pass=0&type=${param.type}");
  });
</c:if>
<c:if test="${param.status==1}">
  $("#msgBtn").click(function(){
    $.loadModal("${ctx}/m/abroad/shortMsg_view?id=${applySelf.id}&type=applySelf");
  });
</c:if>
</script>

