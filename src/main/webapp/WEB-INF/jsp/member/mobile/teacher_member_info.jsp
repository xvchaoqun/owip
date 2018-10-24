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
                ${memberTeacher.idcard}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <a href='tel:${memberTeacher.extPhone}'>${memberTeacher.extPhone}</a>
              </td>
            </tr>
          </table>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 联系邮箱</div>
          <div class="profile-info-value">
            <span class="editable">${memberTeacher.email}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 籍贯 </div>

          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.nativePlace}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 最高学历</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.education}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 最高学位</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.degree}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 学位授予日期</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${cm:formatDate(memberTeacher.degreeTime, "yyyy-MM-dd")}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 所学专业</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.major}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 毕业学校</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.school}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 毕业学校类型</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.schoolType}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 到校日期 </div>
          <div class="profile-info-value">
            <span class="editable">
              ${cm:formatDate(memberTeacher.arriveTime, "yyyy-MM-dd")}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 编制类别</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${memberTeacher.authorizedType}
            </span>
          </div>
        </div>
      </div>
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name"> 人员分类</div>
          <div class="profile-info-value">
            <span class="editable">${memberTeacher.staffType}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人员状态</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.staffStatus}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 在岗情况</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.onJob}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 岗位类别</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.postClass}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 主岗等级</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.mainPostLevel}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 专业技术职务</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.proPost}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 专技岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.proPostLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 职称级别</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.titleLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 管理岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.manageLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 工勤岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.officeLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政职务</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任职级别</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.postLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人才类型</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.talentType}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 婚姻状况</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.maritalStatus}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 居住地址</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.address}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 是否退休</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.isRetire?"是":"否"}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 退休时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.retireTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 是否离休</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.isHonorRetire?"是":"否"}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 人才/荣誉称号</div>
          <div class="profile-info-value">
            <span class="editable">${memberTeacher.talentTitle}</span>
          </div>
        </div>
      </div>
    </div>
    <div id="dropdown2" class="tab-pane">
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name"> 所属组织机构</div>
          <div class="profile-info-value">
            <span class="editable">${cm:displayParty(memberTeacher.partyId, memberTeacher.branchId)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 党籍状态</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_POLITICAL_STATUS_MAP.get(memberTeacher.politicalStatus)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 状态</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_STATUS_MAP.get(memberTeacher.status)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 党内职务</div>
          <div class="profile-info-value td">
            <span class="editable">${memberTeacher.partyPost}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 入党时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.growTime,'yyyy-MM-dd')}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 转正时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.positiveTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 进入系统方式</div>
          <div class="profile-info-value td">
            <span class="editable">${MEMBER_SOURCE_MAP.get(memberTeacher.memberSource)}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 提交书面申请书时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.applyTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 确定为入党积极分子时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.activeTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 确定为发展对象时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(memberTeacher.candidateTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 党内奖励</div>
          <div class="profile-info-value">
            <span class="editable">${memberTeacher.partyReward}</span>
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
