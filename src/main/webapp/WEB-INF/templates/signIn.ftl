<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign In</title>
</head>
<body>
<h1 style="text-align: center">欢迎使用商超系统, 请登录</h1>
<div style="align-content: center; margin-left: 15%; margin-right: 15%;">
    <form action="/signIn" method="post">
        <p>用户名:
            <input type="text"
                   name="sname"/></p>
        <p>密码:
            <input type="password" name="spassword"/></p>
        <#if msg?? ><span style="color: crimson;">${msg}</span><br/></#if>
        <input type="submit" value="登录"/>
    </form>
</div>
</body>
</html>