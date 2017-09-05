<!DOCTYPE html>
<html lang="en">
<head>
<#include 'includes/head.ftl'>
    <title>LINK - Library</title>
</head>
<body>
<#include "includes/nav.ftl">
<div class="container">
    <br/>
    <h3>Components library</h3>
<#include 'includes/messages.ftl'>
    <table class="table table-sm table-hover">
    <tbody>
    <#list components as name, component>
    <tr>
        <td>${name}</td>
        <td>${component.description!}</td>
    </tr>
    </tbody>
    </#list>
    </table>
</div>
<#include 'includes/foot.ftl'>
</body>
</html>