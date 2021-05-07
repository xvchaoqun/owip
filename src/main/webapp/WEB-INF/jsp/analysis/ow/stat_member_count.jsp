<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_member_other" style="width:750px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                党员数量统计
            </h5>
            <shiro:lacksPermission name="statOw:showPart">
                <div class="widget-toolbar no-border">
                <div class="inline dropdown-hover">
                    <button class="btn btn-xs btn-info">
                        ${empty type?"详细情况":(type==1)?"性别":(type==2)?"民族":"基本情况"}
                        <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="${empty type?'active':''}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${empty type?'':'invisible'}">
                                    &nbsp;</i>
                                详细情况
                            </a>
                        </li>
                        <li data-type="3"  class="${type!=3?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=3?'invisible':''}">
                                    &nbsp;</i>
                                基本情况
                            </a>
                        </li>
                        <li data-type="1"  class="${type!=1?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=1?'invisible':''}">
                                    &nbsp;</i>
                                性别
                            </a>
                        </li>
                        <li data-type="2"  class="${type!=2?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=2?'invisible':''}">
                                    &nbsp;</i>
                                民族
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            </shiro:lacksPermission>
        </div>
        <div class="widget-body">
            <c:if test="${not empty type}">
                <div class="widget-main">
                    <div id="memberOther-placeholder" style="height: 252px"></div>
                </div>
            </c:if>
            <c:if test="${empty type}">
                <div class="widget-main">
                    <div id="memberCount-placeholder" style="min-height: 252px;">
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script>
    $("#stat_member_other ul li").click(function () {

        var url = "${ctx}/${isPartyAdmin?'stat_party_member_count':'stat_member_count'}";

        $.get(url, {type: $(this).data('type'),partyId: "${partyId}"}, function (html) {
            $("#stat_member_other").replaceWith(html);
        });
    });

    <c:if test="${type == 3}">
    var $div = $('#memberOther-placeholder');
    </c:if>
    <c:if test="${empty type}">
    var $div = $('#memberCount-placeholder');
    </c:if>
    <c:if test="${not empty type && type != 3}">
    var $div = $('#memberOther-placeholder');
    </c:if>

    var url = "${ctx}/${isPartyAdmin?'stat_party_member_count_data':'stat_member_count_data'}";

    (function(displayDiv, type,partyId,branchId){
        var countChart = echarts.init(displayDiv);
        countChart.showLoading({text: '正在加载数据'});

        $.get(url, {type:type,partyId:partyId,branchId:branchId}, function (ret) {
            var otherMap = ret.otherMap;
            var statPoliticalStatusMap= ret.statPoliticalStatusMap;
            var statGrowMap= ret.statGrowMap;
            var statPositiveMap= ret.statPositiveMap;
            var statMap= ret.statMap;

            if(type == 3){
                var legendTitle = [];
                var countData = [];
                var count=statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}];
                function addData(){

                    <c:forEach items="${USER_TYPE_MAP}" var="userType">
                        if (statMap!=undefined && statMap[${userType.key}]>0){
                            countData.push({name: '${userType.value}党员('+statMap[${userType.key}]+')',
                                value: statMap[${userType.key}], userType:${userType.key}});
                            legendTitle.push('${userType.value}党员('+statMap[${userType.key}]+')');
                        }
                    </c:forEach>
                }
                addData();
                var option = {
                    title: {
                        text: '（党员总数：'+count+'）',
                        left: 'right'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}:{d}%'
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        left:-5,
                        top: -5,
                        bottom: 5,
                        data: legendTitle
                    },
                    series: [
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data:countData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur:10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data: countData,
                            itemStyle:{            //饼图图形上的文本标签
                                normal: {
                                    label: {
                                        show: true,
                                        position: 'inner',
                                        fontSize: 12,
                                        align: "left",
                                        textStyle: {
                                            fontWeight: 300,
                                            fontSize: 12,   //文字的字体大小
                                            color:'white'
                                        },
                                        formatter: '{d}%'
                                    },
                                    labelLine:{
                                        show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                    }
                                }
                            }
                        }
                    ]
                };
            }
            if($.trim(type)==''){
                var memberData = [];
                var legendTitle = [];
                var countData = [];
                var count =statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}];
                function addData1(){

                    if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]!=0){
                        memberData.push({
                            name: '正式党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]+')',
                            value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}],
                            politicalStatus:${MEMBER_POLITICAL_STATUS_POSITIVE}
                        });
                        legendTitle.push('正式党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]+')');
                    }
                    if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]!=0){
                        memberData.push({
                            name: '预备党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]+')',
                            value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}],
                            politicalStatus:${MEMBER_POLITICAL_STATUS_GROW}
                        });
                        legendTitle.push('预备党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]+')');
                    }

                    <c:forEach items="${USER_TYPE_MAP}" var="userType">
                        if (statPositiveMap[${userType.key}]!=0){
                            countData.push({name: '${userType.value}正式党员('+statPositiveMap[${userType.key}]+')',
                                value: statPositiveMap[${userType.key}], userType:${userType.key},
                                politicalStatus:${MEMBER_POLITICAL_STATUS_POSITIVE}});
                            legendTitle.push('${userType.value}正式党员('+statPositiveMap[${userType.key}]+')');
                        }
                    </c:forEach>
                    <c:forEach items="${USER_TYPE_MAP}" var="userType">
                        if (statGrowMap[${userType.key}]!=0){
                            countData.push({name: '${userType.value}预备党员('+statGrowMap[${userType.key}]+')',
                                value: statGrowMap[${userType.key}], userType:${userType.key},
                                politicalStatus:${MEMBER_POLITICAL_STATUS_GROW}});
                            legendTitle.push('${userType.value}预备党员('+statGrowMap[${userType.key}]+')');
                        }
                    </c:forEach>
                }
                addData1();
                var option = {
                    title: {
                        text: '（党员总数：'+count+'）',
                        left: 'right'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}:{d}%'
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        left:-5,
                        top: -5,
                        bottom: 5,
                        data: legendTitle
                    },
                    series: [
                        {
                            name: '党员数量',
                            type: 'pie',
                            selectedMode: 'single',
                            radius: [0, '45%'],
                            center: ['63%', '53%'],
                            startAngle:240,
                            label: {
                                color: 'white',
                                position: 'inner'
                            },
                            labelLine: {
                                show: false
                            },
                            data: memberData
                        },
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: ['55%', '70%'],
                            center: ['63%', '53%'],
                            startAngle:240,
                            data: countData
                        }
                    ]
                };
            }
            if($.trim(type)!='' && type != 3){

                var otherLabel= [];
                var otherData = [];
                $.each(otherMap, function (key, value) {

                    var item = key + '(' + value + ')';
                    otherLabel.push(item);
                    otherData.push({
                        name: item,
                        value: value,
                        _nation:type==2?key:'',
                        _gender:type==1?key:''
                    });
                });

                var option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}:{d}%'
                    },
                    legend: {
                        orient: 'vertical',
                        left: -5,
                        top: 5,
                        bottom: 5,
                        data: otherLabel
                    },
                    series: [
                        {
                            name: '党员基本情况',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data:otherData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur:10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '党员基本情况',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data: otherData,
                            itemStyle:{            //饼图图形上的文本标签
                                normal: {
                                    label: {
                                        show: true,
                                        position: 'inner',
                                        fontSize: 12,
                                        align: "left",
                                        textStyle: {
                                            fontWeight: 300,
                                            fontSize: 12,   //文字的字体大小
                                            color:'white'
                                        },
                                        formatter: '{d}%'
                                    },
                                    labelLine:{
                                        show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                    }
                                }
                            }
                        }
                    ]
                };
            }

            countChart.setOption(option);
            countChart.hideLoading();

            <shiro:hasPermission name="member:list">
            countChart.on('click', function (params) {
                //console.log(params.data);
                var url = "#${ctx}/member?cls=-1&partyId=${partyId}&branchId=${branchId}&userType={0}&politicalStatus={1}&_nation={2}&_gender={3}"
                    .format($.trim(params.data.userType), $.trim(params.data.politicalStatus),
                        $.trim(params.data._nation), $.trim(params.data._gender));
                window.open(url, "_blank");
            });
            </shiro:hasPermission>
        });
    })($div[0], '${type}','${partyId}','${branchId}');
</script>