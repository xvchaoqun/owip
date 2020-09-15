<#list cadreTrains as cadreTrain>
<p>${(cadreTrain.startTime?string("yyyy.MM"))!}<#if cadreTrain.month gt 0>${cadreTrain.endTime???string("—","—至今")}${(cadreTrain.endTime?string("yyyy.MM"))!}</#if>，参加${cadreTrain.content!}<#if cadreTrain.unit?? && cadreTrain.unit?trim!=''>，${cadreTrain.unit!}主办</#if></p>
</#list>