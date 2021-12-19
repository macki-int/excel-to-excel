#Excel to Excel

Generating random measurement data
with getting config from excel file and exporting to new excel file

###CONFIG EXCEL FILE

The sheet must have to columns in order:
1. *Name* of the sensor chain
2. *Number* of sensors in one chain
3. Date of *starting* the measurement
4. Date of *ending* the measurement

**NOTHING MORE**

###OUTPUT EXCEL FILE
The output sheet must have to columns in order:
1. Measurement *data* (mesurement_time)
2. *Name* of the sensor chain (name)
3. *Id sensor* in the chain (id_name)
4. *Angel x* (angle_x)
5. *Angel y* (angle_y)
6. *Temperature* (temp)

Accuracy of measurement to the three decimal places