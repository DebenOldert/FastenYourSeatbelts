<h1>HOW TO USE</h1>
Place the rules.sh and the users files in the same folder (I recommend a folder named ```portal``` in the root folder "/portal/")<br>
You can initiate the script by typing: ```bash /portal/rules.sh initialize```<br>
You can grand an ip access to the internet by typing: ```bash /portal/rules.sh grand XXX.XXX.XXX.XXX``` (The ip adrress)<br>
If you want the script to run at system stratup edit the /etc/rc.local and add <strong>before</strong> "exit 0" this code:<br>
```bash /portal/rules.sh initialize```
