#!/bin/python

import pandas as pd
import sqlite3

# Path to your Excel file
excel_file = "ARAVIND_EXPENSES.xlsx"

# Load all sheets into a dictionary of DataFrames
sheets = pd.read_excel(excel_file, sheet_name=None)

# Create SQLite database connection (this will create a .db file)
conn = sqlite3.connect("ARAVIND_EXPENSES.sqlite")

for sheet_name, df in sheets.items():
  # Clean column names (remove extra spaces, etc.)
  df.columns = df.columns.str.strip()

  # Ensure correct column order
  expected_cols = ["Date", "Category", "ItemPurchase", "Amount"]
  df = df[expected_cols]

  # Save each sheet as a separate table in SQLite
  table_name = sheet_name.replace(" ", "_")  # valid SQL table name
  df.to_sql(table_name, conn, if_exists="replace", index=False)

conn.close()
print("SQLite database 'purchases.db' created successfully.")

