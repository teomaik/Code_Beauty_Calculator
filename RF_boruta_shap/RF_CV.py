import pandas as pd
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
import shap
import matplotlib.pyplot as plt
import os


# Construct the path dynamically
script_dir = os.path.dirname(os.path.abspath(__file__))  # Directory where the script is located
file_path = os.path.join(script_dir, 'DatasetRFNew2.xlsx')
data = pd.read_excel(file_path)


# Features and target
# X = data[['Balance', 'Equilibrium', 'Overall Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'DCM']]
# X = data[['Simplicity', 'Regularity', 'Balance', 'Overall Density']]
# X = data[['Simplicity', 'Regularity', 'Balance']]
# X = data[['Simplicity', 'Rhythm']]
# X = data[['Regularity', 'Simplicity', 'Comment_Area', 'LineLengths', 'Conditionals', 'Indentations', 'Loops', 'Sonar']]
X = data[['Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'Code_Readability', 'Checkstyle', 'Semantic_Text_Coherence', 'Comment_Area', 'Expression_complexity_AVG', 'Number_of_senses_AVG', 'align_blocks', 'Conditionals', 'Indentations', 'Loops', 'Sonar', 'Designate']]
# X = data[['Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'Code_Readability', 'Checkstyle', 'Semantic_Text_Coherence', 'Comment_Area', 'Expression_complexity_AVG', 'Number_of_senses_AVG', 'LineLengths', 'align_blocks', 'Conditionals', 'Indentations', 'Loops', 'Sonar', 'Designate']]
y = data['PCB']

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=88)

# Define the parameter grid
param_grid = {
    # 'n_estimators': [5, 10, 20, 30, 50, 100, 200, 300, 400, 500, 600, 700, 800],
    'n_estimators': [1, 2, 3, 5, 7, 10, 13, 15, 18],
    'max_features': ['auto', 'sqrt', 'log2'],
    'max_depth': [None, 10, 20, 30, 40, 50],
    'min_samples_split': [2, 5, 10],
    'min_samples_leaf': [1, 2, 4],
    'bootstrap': [True, False]
}

# Instantiate the Random Forest Regressor
rf = RandomForestRegressor(random_state=42)

# Instantiate the grid search model
grid_search = GridSearchCV(estimator=rf, param_grid=param_grid, cv=5, n_jobs=-1, verbose=2)

# Fit the grid search to the data
grid_search.fit(X_train, y_train)

# Get the best parameters
best_params = grid_search.best_params_
print("Best parameters:", best_params)

# Train the final model with the best parameters
rf_best = RandomForestRegressor(**best_params, random_state=42)
rf_best.fit(X_train, y_train)

# Make predictions on the test set
y_pred = rf_best.predict(X_test)

# Calculate Mean Absolute Error
mae = mean_absolute_error(y_test, y_pred)
print("Mean Absolute Error (MAE):", mae)

# Calculate Mean Squared Error
mse = mean_squared_error(y_test, y_pred)
print("Mean Squared Error (MSE):", mse)

# Calculate R-squared
r2 = r2_score(y_test, y_pred)
print("R-squared (R2):", r2)

# Use SHAP to explain the model's predictions
explainer = shap.Explainer(rf_best, X_train)
shap_values = explainer(X_test)

# SHAP summary plot
shap.summary_plot(shap_values, X_test)

# SHAP bar plot
shap.summary_plot(shap_values, X_test, plot_type="bar")
