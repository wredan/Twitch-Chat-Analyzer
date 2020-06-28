#!/bin/bash
echo "~~~~~~~~~~~~~~~~~~~~~"	
echo " Spark Consumer Menu"
echo "~~~~~~~~~~~~~~~~~~~~~"
PS3='Choose your Spark Consumer (number): '
options=("Python" "Scala" "Quit")
select opt in "${options[@]}"
do
    case $opt in
        "Python")
            spark_choice="Spark/Python"
            break
            ;;
        "Scala")
            spark_choice="Spark/Scala"
            break
            ;;
        "Quit")
            exit
            ;;
        *) echo "invalid option $REPLY";;
    esac
done

cd ${spark_choice}
bin/spark-start.sh