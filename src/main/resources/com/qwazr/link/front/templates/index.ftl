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
    <h1 class="text-center">Welcome to Qwazr LINK</h1>
    <form method="post">
        <div class="form-group">
            <label for="linkscript">Enter a JavaScript</label>
            <textarea class="form-control" id="linkscript" name="linkscript" rows="20">${linkscript!?html}</textarea>
        </div>
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