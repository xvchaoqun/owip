<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="tabbable">
  <ul class="nav nav-tabs" id="myTab3">
    <li class="active">
      <a data-toggle="tab" href="#dropdown1">
        <i class="fa fa-paw blue"></i> 基本信息
      </a>
    </li>
    <li>
      <a data-toggle="tab" href="#dropdown2">
        <i class="fa fa-star red"></i> 党籍信息
      </a>
    </li>
  </ul>
  <div class="tab-content" style="padding:16px 0px 0px">
    <div id="dropdown1" class="tab-pane in active">
      <div class="profile-user-info profile-user-info-striped" style="border:0px;">
        <div class="profile-info-row">
          <table class="table table-bordered table-center avatar" style="margin-bottom: 0px;">
            <tr>
              <td rowspan="6" class="avatar">
                <img src="${ctx}/m/avatar?path=${cm:encodeURI(uv.avatar)}&_t=<%=new Date().getTime()%>" class="avatar">
              </td>
            </tr>
            <tr>
              <td>
                ${uv.realname}
              </td>
              <td>
                ${GENDER_MAP.get(uv.gender)}
              </td>
            </tr>
            <tr>
              <td>
                ${cm:formatDate(uv.birth,'yyyy-MM-dd')}
              </td>
              <td>
                ${uv.nation}
              </td>
            </tr>
            <tr>
              <td colspan="2">${uv.code}</td>
            </tr>
            <tr>
              <td colspan="2">
                <t:mask src="${member.idcard}" type="idCard"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <a href='tel:${member.mobile}'><t:mask src="${member.mobile}" type="mobile"/></a>
              </td>
            </tr>
          </table>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 联系邮箱</div>
          <div class="profile-info-value">
            <span class="editable"><t:mask src="${member.email}" type="email"/></span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 籍贯 </div>

          <div class="profile-info-value td">
            <span class="editable">
              ${member.nativePlace}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 最高学历</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.education}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 最高学位</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.degree}
            </span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 学位授予日期</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${cm:formatDate(member.degreeTime, "yyyy-MM-dd")}
            </span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 所学专业</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.major}
            </span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 毕业学校</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.school}
            </span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 毕业学校类型</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.schoolType}
            </span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name"> 到校日期 </div>
          <div class="profile-info-value">
            <span class="editable">
              ${cm:formatDate(member.arriveTime, "yyyy-MM-dd")}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 编制类别</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.authorizedType}
            </span>
          </div>
        </div>
      </div>
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name"> 人员类别</div>
          <div class="profile-info-value">
            <span class="editable">${member.staffType}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人员状态</div>
          <div class="profile-info-value td">
            <span class="editable">${member.staffStatus}</span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 在岗情况</div>
          <div class="profile-info-value td">
            <span class="editable">${member.onJob}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 岗位类别</div>
          <div class="profile-info-value td">
            <span class="editable">${member.postClass}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 主岗等级</div>
          <div class="profile-info-value td">
            <span class="editable">${member.mainPostLevel}</span>
          </div>
        </div>--%>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 专业技术职务</div>
          <div class="profile-info-value td">
            <span class="editable">${member.proPost}</span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 职称级别</div>
          <div class="profile-info-value td">
            <span class="editable">${member.proPostLevel}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 职称级别</div>
          <div class="profile-info-value td">
            <span class="editable">${member.titleLevel}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 管理岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${member.manageLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 工勤岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${member.officeLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政职务</div>
          <div class="profile-info-value td">
            <span class="editable">${member.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任职级别</div>
          <div class="profile-info-value td">
            <span class="editable">${member.postLevel}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 人才类型</div>
          <div class="profile-info-value td">
            <span class="editable">${member.talentType}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 婚姻状况</div>
          <div class="profile-info-value td">
            <span class="editable">${member.maritalStatus}</span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 联系地址</div>
          <div class="profile-info-value td">
            <span class="editable">${member.address}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 是否退休</div>
          <div class="profile-info-value td">
            <span class="editable">${member.isRetire?"是":"否"}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 退休时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.retireTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 是否离休</div>
          <div class="profile-info-value td">
            <span class="editable">${member.isHonorRetire?"是":"否"}</span>
          </div>
        </div>--%>
        <%--<div class="profile-info-row">
          <div class="profile-info-name"> 人才/荣誉称号</div>
          <div class="profile-info-value">
            <span class="editable">${member.talentTitle}</span>
          </div>
        </div>--%>
      </div>
    </div>
    <div id="dropdown2" class="tab-pane">
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name"> 所在党组织</div>
          <div class="profile-info-value">
            <span class="editable">${cm:displayParty(member.partyId, member.branchId)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 党籍状态</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 状态</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_STATUS_MAP.get(member.status)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 党内职务</div>
          <div class="profile-info-value td">
            <span class="editable">${member.partyPost}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 入党时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.growTime,'yyyy-MM-dd')}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 转正时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 进入系统方式</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_SOURCE_MAP.get(member.source)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 提交书面申请书时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.applyTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 确定为入党积极分子时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.activeTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 确定为发展对象时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.candidateTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 党内奖励</div>
          <div class="profile-info-value">
            <span class="editable">${member.partyReward}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 其他奖励</div>
          <div class="profile-info-value">
            <span class="editable"></span>
          </div>
        </div>
      </div>
    </div>
    </div>
  </div>
</div>
