<h1>How to deploy?</h1>
Just download the latest precompiled .WAR file from the war_exports directory.<br>
<h2>How the portal works</h2>
HTTP request<br>
||>>By just typing in any website<br>
User redirected to the captive portal<br>
||>>By javascript<br>
User fills in ticket number and lastname<br>
||>>By a HTML form<br>
Forms goes to proccess.java<br>
________||<br>
________||>>lastname and ticketnumber return 1 row? (lastname and ticketnumber combination is unique)<br>
________||    Then continue<br>
________||    else Tell the user the lastname and ticketnumber combination was not correct<br>
________||<br>
________||<br>
________||>> Update Database (tell the database the user is using internet)<br>
________||    Succes? return continue<br>
________||    Failure? Tell the user someting went wrong<br>
________||<br>
________||>>Grand IP adress<br>
________||    Succes? continue<br>
________||    else update Database (tell the database the user is not using internet)<br>
________||    Tell the user someting went wrong<br>
________||<br>
________||>>Everyting OK?<br>
________||    Then show the user the loading.html page and The user is free to use the internet<br>
________||    else Tell the user someting went wrong<br>
<br>
<br>
This is how our captive portal in very global lines works.
