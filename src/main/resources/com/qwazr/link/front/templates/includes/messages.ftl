<#list session.messages.list as message>
<div class="alert alert-${message.type}" role="alert">
    <#if message.close>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </#if>
    <#if message.title?has_content><h4 class="alert-heading">${message.title}</h4></#if>
    <#if message.content?has_content><p class="mb-0">${message.content}</p></#if>
</div>
</#list>