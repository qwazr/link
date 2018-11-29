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
  <#include 'includes/messages.ftl'>
  <form method="post">
    <div class="input-group mb-3">
      <input name="scriptPath" type="text" class="form-control" placeholder="Script path"
             aria-label="Script path" value="${scriptPath!?html}">
      <div class="input-group-append">
        <button class="btn btn-primary" type="submit">Execute</button>
      </div>
    </div>
  </form>
  <#if scripts?has_content>
    <table class="table">
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Start</th>
      <th>End</th>
      <th>State</th>
      <th>Output</th>
    </tr>
    <#list scripts as key, status>
      <tr>
      <td>${key?html}</td>
      <td>${status.name!?html}</td>
      <td><#if status.startTime??>${status.startTime!?datetime}</#if></td>
      <td><#if status.endTime??>${status.endTime!?datetime}</#if></td>
      <td>${status.state!?html}</td>
      <td>
      <div class="btn-group" role="group" aria-label="Script output">
    <a target="_blank" class="btn btn-sm btn-success" href="/out?id=${key?html}">std</a>
    <a target="_blank" class="btn btn-sm btn-danger" href="/err?id=${key?html}">err</a>
      </div>
      </td>
      </tr>
    </#list>
    </table>
  </#if>
</div>
<#include 'includes/foot.ftl'>
</body>
</html>
