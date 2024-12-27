import pandas as pd
import os
from scipy.stats import spearmanr

# Load the Excel file
script_dir = os.path.dirname(os.path.abspath(__file__))  # Directory where the script is located
file_path = os.path.join(script_dir, 'DatasetRFNew3.xlsx')
data = pd.read_excel(file_path)

# Display the loaded data to verify it
print("Loaded Data:")
print(data.head())

# Define the metrics
metrics_x = ["LOC", "CC", "MPC", "LCOM"]
metrics_y = ["Regularity", "Simplicity", "Indentation", "Number of Conditionals", "Comments Area", "Number of Code Smells"]

# Check for missing columns
missing_x = [col for col in metrics_x if col not in data.columns]
missing_y = [col for col in metrics_y if col not in data.columns]

if missing_x or missing_y:
    print(f"Missing columns in metrics_x: {missing_x}")
    print(f"Missing columns in metrics_y: {missing_y}")
    raise ValueError("Ensure all required columns are present in the Excel file.")

# Prepare a dictionary to store results
results = []

# Calculate Spearman's rho and p-value for each pair
for x in metrics_x:
    for y in metrics_y:
        rho, p_value = spearmanr(data[x], data[y], nan_policy='omit')
        results.append({
            'Metric X': x,
            'Metric Y': y,
            'Spearman Rho': rho,
            'P-Value': p_value
        })

# Convert results to a DataFrame for better readability
results_df = pd.DataFrame(results)

# Save results to a CSV file (optional)
results_df.to_csv("spearman_correlation_results.csv", index=False)

# Print results
print("Spearman Correlation Results:")
print(results_df)