#!/bin/bash
set -e # Immidiately fail in case of any error

kotlinc src/generate.kt -include-runtime -d generate.jar
randSeed=0
varNum=${1:-35} # Read from first command line argument OR use 35 as default value
mkdir -p variants # do the same as check with if

separator="______________________________________"

while [[ "$randSeed" != "$varNum" ]]
do
    first_task="1 $randSeed 2 4 2 1 1 * +"
    second_task="1 $randSeed 3 4 4 2 0 | & << >>"
    third_task="1 $randSeed 2 4 7 2 0 >> << | & * +"
    variant_file="variants/var${randSeed}.docx"
    answer_file="variants/answer${randSeed}.docx"

    echo "Generating ${randSeed}th variant" 
    echo variant_${randSeed} > "$variant_file"
    echo variant_${randSeed} > "$answer_file"
    
    for i in "$first_task" "$second_task" "$third_task"
    do
        echo | tee -a "$variant_file" >> "$answer_file"
        echo "$i" >> "$answer_file"
        echo >> "$answer_file"
        java -jar generate.jar "$i" | tee -a "$variant_file" >> "$answer_file"
        gcc func.c -o func
        echo >> "$answer_file"
        ./func >> "$answer_file"
        if [[ "$i" != "$third_task" ]]
        then
            echo "$separator"| tee -a "$variant_file" >> "$answer_file"
        fi
    done
    
    let "randSeed += 1"
done
