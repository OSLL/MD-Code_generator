#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
randSeed=0
varNum=35
if ! [ -d variants ]; then
    mkdir variants
fi
while [[ $randSeed != $varNum ]]
do
    echo variant_${randSeed} > variants/var${randSeed}.docx
    echo variant_${randSeed} > variants/answer${randSeed}.docx
    
    echo | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    echo "1 $randSeed 2 4 2 1 1 * - +" >> variants/answer${randSeed}.docx
    echo >> variants/answer${randSeed}.docx
    java -jar generate.jar "1 $randSeed 2 4 2 1 1 * - +" | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    gcc func.c -o func
    echo >> variants/answer${randSeed}.docx
    ./func >> variants/answer${randSeed}.docx
    echo "______________________________________"| tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    
    echo | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    echo "1 $randSeed 4 8 4 2 0 | & * - +" >> variants/answer${randSeed}.docx
    echo >> variants/answer${randSeed}.docx
    java -jar generate.jar "1 $randSeed 4 8 4 2 0 | & * - +" | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    gcc func.c -o func
    echo >> variants/answer${randSeed}.docx
    ./func >> variants/answer${randSeed}.docx
    echo "______________________________________"| tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    
    echo | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    echo "1 $randSeed 6 12 7 3 0 >> << | & * - +" >> variants/answer${randSeed}.docx
    echo >> variants/answer${randSeed}.docx
    java -jar generate.jar "1 $randSeed 6 12 7 3 0 >> << | & * - +" | tee -a variants/var${randSeed}.docx >> variants/answer${randSeed}.docx
    gcc func.c -o func
    echo >> variants/answer${randSeed}.docx
    ./func >> variants/answer${randSeed}.docx
    
    let "randSeed += 1"
done