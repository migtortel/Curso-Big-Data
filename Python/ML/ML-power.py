import numpy as np
import pandas as pd

d_type = {
    'Name': 'str',
    'Sex': 'str',
    'Event': 'str',
    'Equipment': 'str',
    'Age': 'float',
    'AgeClass': 'str',
    'Division': 'str',
    'BodyweightKg': 'float',
    'WeightClassKg': 'str',
    'Place': 'str',
    'Tested': 'str',
    'Country': 'str',
    'State': 'str',
    'Federation': 'str',
    'Sanctioned': 'str'
}

df = pd.read_csv("openipf-2024-06-29.csv", dtype=d_type, parse_dates=['Date'], low_memory=False)

#print(df.dtypes)
print(type(df[0]))
print(df.head())
df['Name'] = pd.to