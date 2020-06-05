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
                ${member.idcard}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                ${MEMBER_SOURCE_MAP.get(member.source)}
              </td>
            </tr>
          </table>
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
          <div class="profile-info-name td"> 学籍状态</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.xjStatus}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 所在年级</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.grade}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 培养类型</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.eduType}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 培养层次</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.eduLevel}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 培养方式</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.eduWay}
            </span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 招生年度</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.enrolYear}
            </span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 学生类别 </div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.studentType}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 教育类别</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${member.eduCategory}
            </span>
          </div>
        </div>
      </div>
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name td"> 实际入学年月</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.actualEnrolTime,'yyyy-MM')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 预计毕业年月</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.expectGraduateTime,'yyyy-MM')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 实际毕业年月</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(member.actualGraduateTime,'yyyy-MM')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 延期毕业年限</div>
          <div class="profile-info-value td">
            <span class="editable">${member.delayYear}</span>
          </div>
        </div>
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
          <div class="profile-info-name"> 提交书面申请书时间</div>
          <div class="profile-info-value">
            <span class="editable">${cm:formatDate(member.applyTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 确定为入党积极分子时间</div>
          <div class="profile-info-value">
            <span class="editable">${cm:formatDate(member.activeTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 确定为发展对象时间</div>
          <div class="profile-info-value">
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
