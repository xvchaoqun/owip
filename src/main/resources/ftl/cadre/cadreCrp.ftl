<#list crpRecords as crpRecord>
<p>${(crpRecord.startDate?string("yyyy.MM"))!}${crpRecord.realEndDate???string("—","—至今&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")}${(crpRecord.realEndDate?string("yyyy.MM"))!}&nbsp;&nbsp;${crpRecord.post!}<#--<#if crpRecord.note?? && crpRecord.note?trim!=''>（${crpRecord.note?trim}）</#if>--></p>
</#list>
