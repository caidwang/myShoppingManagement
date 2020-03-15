<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign In</title>
</head>
<body>
    <h1 style="text-align: center">欢迎使用商超系统, 请登录</h1>
    <div style="align-content: center; margin-left: 15%; margin-right: 15%;">
        <form action="/signIn" method="post">
            <p>用户名: <input type="text" name="username" /></p>
            <#if wrongPassword ><span style="color: crimson;">用户名与密码不符, 请重试</span></#if>
            <p>密码: <input type="password" name="password" /></p>
            <input type="submit" value="登录" />
        </form>
    </div>
</body>
</html>