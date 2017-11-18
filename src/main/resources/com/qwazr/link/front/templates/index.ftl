<!DOCTYPE html>
<html lang="en">
<head>
<#include 'includes/head.ftl'>
    <title>Qwazr LINK</title>
</head>
<body>
<#include "includes/nav.ftl">
<div class="container">
    <br/>
    <form method="post">
        <div class="form-group">
            <label for="linkscript">Script Editor</label>
            <textarea class="form-control" id="linkscript" name="linkscript" rows="18">${linkscript!?html}</textarea>
        </div>
    <#--<div id="editor">${linkscript!?html}</div>-->
        <button type="submit" class="btn btn-primary">Execute</button>
    </form>
<#include 'includes/messages.ftl'>
<#if scriptout??>
    <hr/>
    <div>
        <pre><code>${scriptout?html}</code></pre>
    </div>
</#if>
</div>
<#include 'includes/foot.ftl'>
</body>
</html>