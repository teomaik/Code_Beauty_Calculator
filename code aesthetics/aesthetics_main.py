import os
import csv
from simplicity import calculate_simplicity
from symmetry import calculate_symmetry
from equilibrium import calculate_equilibrium
from rhythm import calculate_rhythm
from regularity import calculate_regularity
from sequence import calculate_sequence
from density import calculate_density
from balance import calculate_balance

# C:\Users\Maik\Desktop\_Dev\JSS\scripts\CodeBeauty\our_code_files-Copy

def process_file(file_path):
    # # try:
    #     with open(file_path, 'r') as file:
    #         lines = file.readlines()
        try:
            # Attempt to open the file with utf-8 encoding
            with open(file_path, 'r', encoding='utf-8') as file:
                lines = file.readlines()
        except UnicodeDecodeError:
            # Fallback to a different encoding if utf-8 fails
            with open(file_path, 'r', encoding='latin1') as file:
                lines = file.readlines()

        # Remove empty lines at the start and end of the file
        while lines and not lines[0].strip():
            lines.pop(0)
        while lines and not lines[-1].strip():
            lines.pop()

        # Calculate table dimensions
        num_lines = len(lines)
        max_line_length = max(
            sum(4 if char == '\t' else 1 for char in line.rstrip('\n')) for line in lines
        )

        # Initialize a table with dimensions based on max line and file lines
        table_rows = num_lines
        table_cols = max_line_length
        table = [[' ' for _ in range(table_cols)] for _ in range(table_rows)]

        # Populate the table line by line
        for row_idx, line in enumerate(lines):
            col_idx = 0
            for char in line.rstrip('\n'):
                if char == '\t':
                    col_idx += 4  # Tab character adds 4 spaces
                else:
                    if row_idx < table_rows and col_idx < table_cols:
                        table[row_idx][col_idx] = char
                    col_idx += 1

        # Convert the table into a binary representation
        binary_table = [[1 if cell.strip() else 0 for cell in row] for row in table]

        # for row in binary_table:
        #         print(row)

        # print(f"\nFile: {file_path}")

        simplicity = calculate_simplicity(binary_table)
        # print(f"\nSimplicity for file: {simplicity}")

        regularity = calculate_regularity(binary_table)
        # print(f"\nRegularity for file: {regularity}")

        symmetry = calculate_symmetry(binary_table)
        # print(f"\nSymmetry for file: {symmetry}")

        equilibrium = calculate_equilibrium(binary_table)
        # print(f"\nEquilibrium for file: {equilibrium}")

        rhythm = calculate_rhythm(binary_table)
        # print(f"\nRhythm for file: {rhythm}")

        sequence = calculate_sequence(binary_table)
        # print(f"\nSequence for file: {sequence}")
        
        density = calculate_density(binary_table)
        # print(f"\nDensity for file: {density}")

        balance = calculate_balance(binary_table)
        # print(f"\nBalance for file: {balance}")


        # Prepare the data for the CSV
        file_name = os.path.relpath(file_path, start=folder_path)  # Get the relative file path
        file_name.replace("\\", "/")
        return [
            file_name, 
            balance, 
            equilibrium, 
            density, 
            regularity, 
            rhythm, 
            sequence, 
            simplicity,
            symmetry
        ]
    # except Exception as e:
    #     print(f"Error processing file {file_path}: {e}")


def process_java_files_in_directory(directory_path, output_csv_path):
    if not os.path.isdir(directory_path):
        # print("Error: The specified path is not a valid directory.")
        return

    # # Walk through the directory and process each .java file
    # for root, dirs, files in os.walk(directory_path):
    #     for file in files:
    #         if file.endswith(".java"):
    #             file_path = os.path.join(root, file)
    #             process_file(file_path)
    with open(output_csv_path, mode='w', newline='') as csvfile:
        fieldnames = ['File Name', 'Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry']
        writer = csv.writer(csvfile)
        
        # Write header
        writer.writerow(fieldnames)

        # Walk through the directory and process each .java file
        for root, dirs, files in os.walk(directory_path):
            for file in files:
                if file.endswith(".java"):
                    file_path = os.path.join(root, file)
                    file_metrics = process_file(file_path)
                    # Write the data for this file into the CSV
                    writer.writerow(file_metrics)
                    print(f"Processed and added data for: {file_path}")


if __name__ == "__main__":
    folder_path = input("Enter the folder path to search for .java files: ")
    # folder_path = "C:\Users\Maik\Downloads\exampleeee"

    folder_name = os.path.basename(folder_path)
    script_dir = os.path.dirname(os.path.abspath(__file__))  # Get the directory where the script is located
    output_csv_path = os.path.join(script_dir, f"{folder_name}.csv")

    process_java_files_in_directory(folder_path, output_csv_path)
    print("CSV file has been written successfully.")

