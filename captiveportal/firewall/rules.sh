#Captive portal iptables rule script
#Copyright Deben Oldert
#This script handles the rules for the captive portal
#It is multifuncional
#Initialize the new rules (startup)
#Delete a rule for an ip to grand internet access

#define version of the script
version="V2.1.13"
#load IPTables
IPTABLES=/sbin/iptables
#When it needs to initialize the rules, do this
if [ "$1" == "initialize" ]
then
#First stop host apd so people can't connect
sudo service hostapd stop
#Flush all existing rules
$IPTABLES -F
$IPTABLES -X
#Allow forwarding
echo 1 > /proc/sys/net/ipv4/ip_forward
#Boot up Wifi adapter
echo "Booting up WiFi adapter..."
sudo ifup wlan0
#Add some basic rules to iptables
while read rule
do
        #Execute each rule in the file
        $($rule)
done < /portal/firewall_Rules
#Each ip from the list will be redirected to the captive portal
echo "These IP's will be redirected to the Captive Portal"
#define 1 (ip counter) and set it to zero
i=0
#Read all ips from ip file and make rule per ip
#Read each line as $ip
while read ip
do
        #Shits getting serious now
        sudo $IPTABLES -t nat -A PREROUTING -s "$ip" -p tcp -j DNAT --to-destination 10.111.11.5:80
        #Print the ip
        echo $ip
        #Increment 1 on i so we can see how many ip where added
        ((i++))
#Not the last line in file? Lets do it again!
done < /portal/users
#Show the amount of ip's
echo "Added $i IP's to rule list"
echo "Checking if site is up (might take a while)"
#Set 1 to one, for another counter
i=1
        #Lets check if our portal is online
        #The 3 here is for the amount of times you want to check (default is 3)
        while [ $i -le 3 ]
        do
                #Check if the site is online 
                if curl -s --head http://localhost/Portal/ | head -n 1 | grep "200" >/dev/null 2>&1;
                then
                        #If the site is up, do this:
                        echo "Site is up!"
                        echo "Starting hostapd"
                        #Start hostapd so people can connect
                        sudo service hostapd start
                        #Set i to 4 to break the loop
                        i=4
                        echo "Everything is up and running!"
                else
                        #If site is still down do this
                        echo "Still checking if site is up ($i/3)"
                        #Give Tomcat some time to think
                        sleep 30s
                        #Increment i with 1
                        ((i++))
                        #If i = 4, The site is still not up, Tell whats wrong!
                        if [ "$i" == "4" ]
                        then
                                echo "Site is still not online!"
                                echo "Exiting the program (maybe reboot)"
                        fi
                fi
        done
#Show some information
echo "Captive Portal rule script"
echo $version
echo "Copy right: Deben Oldert"
fi
#If action is permitting acces to internet, delete rule to portal
if [ "$1" == "grand" ]
then
        #check if $2 (ip adress) not empty
        if [[ ! -z "$2" ]]
        then
                #Delete the rule
                sudo -u root $IPTABLES -t nat -D PREROUTING -s $2 -p tcp -j DNAT --to-destination 10.11$
                #Tell the system its fine
                echo "SUCCES"
        else
                #Missing the ip parameter
                echo "FAIL"
        fi
fi
#Uncomment for debugging
#$IPTABLES -t nat -L PREROUTING
