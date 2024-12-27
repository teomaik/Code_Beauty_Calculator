import os
import csv

def consolidate(directory_path):

    # Get the directory name for the output file name
    dirname = os.path.basename(os.path.normpath(directory_path))
    output_file = f"{dirname}_metrics_summary.csv"

    # Initialize a list to store results
    results = []

    # Iterate through all files in the directory
    for filename in os.listdir(directory_path):
        if filename.endswith("_java_metrics.txt"):
            file_path = os.path.join(directory_path, filename)

            # Initialize values to None
            conditionals = None
            indentations = None
            comments_area = None

            # Read the file line by line
            with open(file_path, 'r') as file:
                for line in file:
                    if "Dorn DFT Conditionals:" in line:
                        conditionals = line.split(":")[1].strip()
                    elif "Dorn DFT Indentations:" in line:
                        indentations = line.split(":")[1].strip()
                    elif "Dorn Areas Comments:" in line:
                        comments_area = line.split(":")[1].strip()

            # Format the filename
            formatted_filename = filename.replace("_java_metrics.txt", "").replace("_", "/")
            formatted_filename = formatted_filename.replace("//", "/")

            # Append the results if all values were found
            if conditionals and indentations and comments_area:
                results.append({
                    "filename": formatted_filename,
                    "Comments Area": comments_area,
                    "Number of Conditionals": conditionals,
                    "Indentation": indentations,
                })

    # Write results to a CSV file
    with open(output_file, mode="w", newline="") as csv_file:
        fieldnames = ["filename", "Comments Area", "Number of Conditionals", "Indentation"]
        writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
        
        # Write the header
        writer.writeheader()
        
        # Write the data
        for result in results:
            writer.writerow(result)

    print(f"CSV file '{output_file}' has been created with the extracted data.")


if __name__ == "__main__":
    folder_path = input("Enter the folder path to search for .java files: ")
    # folder_path = "C:\Users\Maik\Downloads\exampleeee"

    consolidate(folder_path)
