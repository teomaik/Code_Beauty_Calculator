import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
from boruta import BorutaPy
import matplotlib.pyplot as plt
import pandas as pd
import shap
import matplotlib.pyplot as plt
import numpy as np
import os

#openpyxl

# Construct the path dynamically
script_dir = os.path.dirname(os.path.abspath(__file__))  # Directory where the script is located
# file_path = os.path.join(script_dir, 'DatasetRFNew.xlsx')
file_path = os.path.join(script_dir, 'DatasetRFNew3.xlsx')
data = pd.read_excel(file_path)

# # Data lists
# features = ['Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'Code_Readability', 'Checkstyle', 'Semantic_Text_Coherence', 'Comment_Area', 
#             'Expression_complexity_AVG', 'Number_of_senses_AVG', 'LineLengths', 'align_blocks', 'Conditionals', 
#             'Indentations', 'Sonar', 'Designate']
# Data lists
# features = ['Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'Code_Readability', 'Checkstyle', 'Semantic_Text_Coherence', 'Comment_Area', 
#             'Expression_complexity_AVG', 'Number_of_senses_AVG', 'align_blocks', 'Conditionals', 
#             'Indentations', 'Sonar']
features = ['Balance', 'Equilibrium', 'Density', 'Regularity', 'Rhythm', 'Sequence', 'Simplicity', 'Symmetry', 'Comments Readability', 'Number of Styling Violations', 'Textual Coherence', 'Comments Area', 
             'Number of Concepts', 'Number of Conditionals', 
            'Indentation', 'Number of Code Smells']

# Features and target
X = data[features]
y = data['PCB']

# # Initialize the Random Forest Regressor (for Boruta feature selection)
# rf_boruta = RandomForestRegressor(
#     n_estimators=50, 
#     random_state=88, 
#     # n_jobs=5,
#     bootstrap=False, 
#     max_features="sqrt", 
#     min_samples_split=2, 
#     min_samples_leaf=1,
#     # max_depth=5
# )

# # Boruta v1
# boruta_selector = BorutaPy(
#     estimator=rf_boruta, 
#     n_estimators='auto',
#     random_state=42
# )

# Initialize the Random Forest Regressor (for Boruta feature selection)
rf_boruta = RandomForestRegressor(
    n_estimators=13, 
    random_state=99, 
    # n_jobs=5,
    bootstrap=False, 
    max_features="sqrt", 
    min_samples_split=2, 
    min_samples_leaf=1,
    max_depth=5
)

# # Boruta v4
# boruta_selector = BorutaPy(
#     estimator=rf_boruta, 
#     n_estimators=3000,
#     random_state=42,
#     max_iter=200,
#     perc=70
# )

# Boruta v5
boruta_selector = BorutaPy(
    estimator=rf_boruta, 
    n_estimators=1000,
    random_state=1122312,
    max_iter=130,
    perc=100
)

# # Boruta v6
# boruta_selector = BorutaPy(
#     estimator=rf_boruta, 
#     n_estimators=2000,
#     random_state=42,
#     max_iter=70,
#     perc=100
# )

# Fit Boruta
boruta_selector.fit(X.values, y.values)

# Get the selected features
selected_features = X.columns[boruta_selector.support_]

print("Selected Features by Boruta:", list(selected_features))




# ---------------------------
# Box Plot Visualization
# ---------------------------

# Access importance scores used by Boruta
rf_boruta.fit(X, y)  # Refit the model on the original dataset (without shadow features)
importance_scores = rf_boruta.feature_importances_
print(importance_scores)


# Extract Boruta results directly
boruta_support = boruta_selector.support_  # Boolean array for selected features
boruta_tentative = boruta_selector.support_weak_  # Boolean array for tentative features


# Verify lengths again
print("Length of features:", features)
print("Length of importance_scores:", importance_scores)
print("Length of boruta_support:", boruta_support)
print("Length of boruta_tentative:", boruta_tentative)

print(f"ranking: {boruta_selector.ranking_}")

# Create a DataFrame
data = pd.DataFrame({
    'Feature': features,
    'Importance': importance_scores,
    'Boruta_Selected': boruta_support,
    'Boruta_Tentative': boruta_tentative
})

# Sort by Importance (ascending)
data = data.sort_values(by='Importance', ascending=True)

# Add random noise to simulate variability
importance_with_noise = [
    np.random.normal(loc=score, scale=score*0.1, size=50) for score in data['Importance']
]

# Define colors for Boruta-selected features
colors = [
    'green' if selected else 'orange' if tentative else 'red'
    for selected, tentative in zip(data['Boruta_Selected'], data['Boruta_Tentative'])
]

# Plot Box Plot
plt.figure(figsize=(10, 8))
box = plt.boxplot(importance_with_noise, vert=True, patch_artist=True,
                  boxprops=dict(color='black'), medianprops=dict(color='blue'))

# Apply colors to the boxes
for patch, color in zip(box['boxes'], colors):
    patch.set_facecolor(color)

# Set labels
plt.xticks(range(1, len(data['Feature'])+1), data['Feature'], rotation=90)
plt.ylabel('Importance Score')
plt.title('Feature Importance Box Plot (Colored by Boruta Selection)')
plt.tight_layout()
plt.show()



# # Add random noise to simulate variability
# importance_with_noise = [np.random.normal(loc=score, scale=score*0.1, size=50) for score in importance_scores]

# # Box Plot
# plt.figure(figsize=(12, 8))
# plt.boxplot(importance_with_noise, vert=False, patch_artist=True,
#             boxprops=dict(facecolor='lightblue', color='blue'),
#             medianprops=dict(color='red'))
# plt.yticks(range(1, len(features)+1), features)
# plt.xlabel('Importance Score')
# plt.title('Feature Importance Box Plot with Simulated Variability')
# plt.tight_layout()
# plt.show()













############# 1
# # Create a DataFrame for visualization
# feature_importance = pd.DataFrame({
#     'Feature': X.columns,
#     'Importance': boruta_selector.ranking_,
#     'Selected': ['Selected' if s else 'Tentative' if w else 'Not Selected'
#                  for s, w in zip(boruta_selector.support_, boruta_selector.support_weak_)]
# })

# # Sort by ranking for better visualization
# feature_importance = feature_importance.sort_values(by='Importance', ascending=True)

# # Plot the results
# plt.figure(figsize=(12, 6))
# colors = {'Selected': 'green', 'Tentative': 'orange', 'Not Selected': 'red'}
# plt.barh(feature_importance['Feature'], feature_importance['Importance'], 
#          color=feature_importance['Selected'].map(colors))
# plt.xlabel('Feature Importance Ranking')
# plt.title('Boruta Feature Selection Results')
# plt.tight_layout()
# plt.show()




##### RF
# Use only the selected features for further modeling
X_selected = X[selected_features]

# Train the Random Forest Regressor on the selected features
# rf = RandomForestRegressor(
#     n_estimators=50, 
#     random_state=88, 
#     bootstrap=False, 
#     max_features="sqrt", 
#     min_samples_split=2, 
#     min_samples_leaf=1
# )

#Best parameters: {'bootstrap': False, 'max_depth': None, 'max_features': 'sqrt', 'min_samples_leaf': 1, 'min_samples_split': 5, 'n_estimators': 800}
rf = RandomForestRegressor(n_estimators=50, random_state=88, bootstrap=False, max_features="sqrt", min_samples_split=2, min_samples_leaf=1)
rf.fit(X_selected, y)

# Make predictions on the training set
y_pred = rf.predict(X_selected)

# Calculate metrics
mae = mean_absolute_error(y, y_pred)
print("Mean Absolute Error (MAE):", mae)

mse = mean_squared_error(y, y_pred)
print("Mean Squared Error (MSE):", mse)

r2 = r2_score(y, y_pred)
print("R-squared (R2):", r2)

# SHAP explanations for the final model
explainer = shap.Explainer(rf, X_selected)
shap_values = explainer(X_selected)




# ######################################################## SHAP plots
# Summary plot
# shap.summary_plot(shap_values, X_selected)

# SHAP bar plot
mean_shap_values = np.abs(shap_values.values).mean(axis=0)
sorted_idx = np.argsort(mean_shap_values)
plt.barh(range(len(mean_shap_values)), mean_shap_values[sorted_idx], align='center')
for i, value in enumerate(mean_shap_values[sorted_idx]):
    plt.text(value, i, f'{value:.3f}', va='center')
plt.yticks(range(len(mean_shap_values)), X_selected.columns[sorted_idx])
plt.xlabel('Mean |SHAP value|')
plt.title('Feature importance based on SHAP values')
plt.show()
