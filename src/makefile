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

doc : Vector.class CloudData.class main.class Classify.class main_threaded.class generator.class
	javadoc Vector.java CloudData.java main.java Classify.java main_threaded.java generator.java

clean:
	rm *.class

cleandocs:
	rm *.html *.js *.css
