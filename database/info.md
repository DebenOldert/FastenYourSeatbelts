<h1>How to access the database from a remote client</h1>
<h2>Step 1</h2>
```mysql -u root -p``` and fill in your password as requested (you defined it during the installation of mysql-server)
<h2>Step 2</h2>
```use mysql;```
<h2>Step 3</h2>
```update user set Host="%" where Host="127.0.0.1";```
<h2>Step 4</h2>
```exit ```
<h2>Step 5</h2>
```/etc/init.d/mysql restart```
<h2>Step 6</h2>
Open your sql client
<h2>Step 7</h2>
Create a new connection:
```ip: <ip of your raspberry>```
```port: 3306```
```user: root```
```password: <password for root>```
<h2>Step 8</h2>
Enjoy the database!
