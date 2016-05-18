<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Sadbeast - Login</title>
    <link href="//fonts.googleapis.com/css?family=Droid+Serif" rel="stylesheet" type="text/css">
    <link href="/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="/css/skeleton.css" rel="stylesheet" type="text/css">
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="four columns">
            <p></p>
        </div>
        <div class="four columns">
            <h4>Login to SadBeast</h4>
            <#if errors??>
                <p style="color:#a94442">${msg.getString("login.error")}</p>
            </#if>
            <form method="post" action="/authenticate">
                <label for="username">Username</label>
                <input class="u-full-width" type="text" name="username" id="username" value="${username}" autofocus/>
                <label for="password">Password</label>
                <input class="u-full-width" type="password" name="password" id="password"/>
                <input class="button-primary u-pull-right" type="submit" value="Login"/>
                <span><a href="/">Back to SadBeast</a></span>
            </form>
        </div>
    </div>
</div>
</body>
</html>