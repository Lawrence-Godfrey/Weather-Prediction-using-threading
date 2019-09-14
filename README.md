## Weather Prediction

#### java program to to do computations on large three dimensional arrays of weather data 
#### Where the first dimension represents points in time while the two following dimensions represent the x and y coordinates of the data in space. 
#### Does simple computation to predict type of cloud to form from the wind data. 

 - main.java runs the computation and records the time it takes. takes a input file name and output file name as parameters. eg java main.java input.txt output.txt 
 - main_threaded runs same computation but uses a threaded version for computation
 - makefile for compiling. eg. make. make clean. make doc. make clean. make cleandoc


##### Threaded version uses ForkJoinPool library and divide and conquer algorithm. 
