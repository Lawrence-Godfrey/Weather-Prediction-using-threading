JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Vector.java \
	CloudData.java \
	main.java \
	Classify.java \
	main_threaded.java \
	generator.java 

MAIN = main

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm *.class

