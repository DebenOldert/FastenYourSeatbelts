#! bin/bash
#IP generator
VERSION="V1.2.4"
#Generator to find all ip's in a range
#Copy Right: Deben Oldert
#Remove existing users file
echo "IP generator"
echo "Copy right: Deben Oldert"
echo "Version: $VERSION"
rm -f /users; touch /users
#Ask the ip range
echo "Enter your start ip"
read firstip
echo "Enter your end ip"
read secondip
#Split the ip strings into array's
IFS='.' read -a first <<< "$firstip"
IFS='.' read -a second <<< "$secondip"
#Set i for the counter
i=0
#Start looping
for (( a=${first[0]}; a<=${second[0]}; a++ ))
do
   for (( b=${first[1]}; b<=${second[1]}; b++ ))
	do
   		for (( c=${first[2]}; c<=${second[2]}; c++ ))
		do
		#Set max ip value to 255 if needed
			if [ $c -lt ${second[2]} ]
				then
				DMAX=255
				else
				DMAX=${second[3]}
			fi
			#Set min ip value to 0 if needed
			if [ $c -gt ${first[2]} ]
				then
				DMIN=0
				else
				DMIN=${first[3]}
			fi
  		 	for (( d=$DMIN; d<=$DMAX; d++ ))
			do
   				#echo "$a.$b.$c.$d"
   				#Put new line with ip in users file
   				echo -e "$a.$b.$c.$d\r\n" >> /users
   				#Ad 1 for the counter
   				i=$((i+1))
			done
		done
	done
done
#Say it!
echo "Found $i ip addresses in this range"
