#!/bin/bash
runChallenge(){
    echo -e "\n~~~~~ Welcome to Mario's Advent of Code ~~~~~~~~~~~~~~~~~\n"
    echo "* * * * * * * * * * * * * * * * * * * * * * * * * * * * *"
    echo " * * * * * * * * * * * * * * * * * * * * * * * * * * * * "
    echo "* * * * * * * * * * * * * * * * * * * * * * * * * * * * *"
    echo -e "\n~~~~~ Compiling ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    cd ./day-$1
    echo -e "\nCompiling day-$1-part-$2.kt to .jar\n"
    kotlinc day-$1-part-$2.kt -include-runtime -d day-$1-part-$2.jar &
    spinner $!
    echo -e "\033[2K\r~~~~~ Output ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
    java -jar day-$1-part-$2.jar
    echo -e "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
}

spinner(){
    spin[0]="~~"
    spin[1]="~~~"
    spin[2]="~~~~~"
    spin[3]="~~~~~~~~"
    spin[4]="~~~~~~~~~~~~"
    spin[5]="~~~~~~~~~~~~~~~~~"
    spin[6]="~~~~~~~~~~~~~~~~~~~~~~~"
    spin[7]="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    spin[8]="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    spin[9]="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    spin[10]="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    
    
    while kill -0 $1 2> /dev/null; do
        for i in "${spin[@]}"
        do
            echo -ne "\033[1K\r$i"
            sleep 0.1
        done
    done
}

runChallenge $1 $2