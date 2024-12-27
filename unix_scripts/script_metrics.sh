while IFS= read -r filename; do
  # Replace '/' with '_' in the file path to make it unique and valid for filenames
  sanitized_name=$(echo "$filename" | sed 's/[/.]/_/g')

  # Run the command and redirect the output to a uniquely named file
  java -cp rsm.jar it.unimol.readability.metric.runnable.ExtractMetrics "$filename" > "${sanitized_name}_metrics.txt"
done < filelist.txt
