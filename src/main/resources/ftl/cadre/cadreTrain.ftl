<#list cadreTrains as cadreTrain>
<p>${cadreTrain.startTime?string("yyyy.MM")}${cadreTrain.endTime???string("-","-至今")}${(cadreTrain.endTime?string("yyyy.MM"))!}，参加${cadreTrain.content!}，${cadreTrain.unit!}主办</p>
</#list>