#!/bin/bash

# Get the current working directory
cwd=$(pwd)

# Loop through all directories in the current working directory
for folder in */; do
    # Check if it is a directory
    if [ -d "$folder" ]; then
        echo "Processing folder: $folder"
        
        # Change into the directory
        cd "$folder" || exit

        # Execute the sequence of commands
        cp ~/dev/rsm.jar .
        cp ~/dev/script_metrics.sh .
        mkdir -p _results_metrics
        find ./**/ -name "*.java" > filelist.txt
        dos2unix script_metrics.sh
        chmod +x script_metrics.sh
        ./script_metrics.sh
        mv *_java_metrics.txt ./_results_metrics/

        # Go back to the parent directory (cwd)
        cd "$cwd" || exit
    fi
done

echo "Processing complete for all folders!"
