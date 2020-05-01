<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>销售员管理</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>


<div class="container">
    <div class="col-lg-8 col-lg-offset-2" style="margin-top: 40px">

        <a href="/backend" class="col-lg-3">返回</a>
        <h1 style="text-align: center">销售员详情</h1>

    </div>

    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <form name="saleman" method="post" action="/backend/submitSaleMan<#if username??>?username=${username}</#if>">

            <@spring.bind "saleman.sname" />
            <label for="username">用户名</label>
            <input type="text" name="sname" id="username" class="form-control" placeholder="输入用户名"
                   <#if username??>value="${saleman.sname}</#if>" required autofocus>
            <@spring.showErrors " " />
            <br>
            <@spring.bind "saleman.spassword" />
            <label for="password">用户密码</label>
            <input type="text" name="spassword" id="password" class="form-control" placeholder="输入用户密码"
                   <#if username?? >value="${saleman.spassword}"</#if> required>
            <@spring.showErrors " " />
            <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
            <@spring.formHiddenInput "saleman.sid", 'name="sid" value="${saleman.sid}"'/>
        </form>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>