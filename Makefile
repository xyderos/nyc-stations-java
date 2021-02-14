COMPILER := javac
FLAGS := -Xlint
build: *.java
	javac $(FLAGS) *.java

clean:
	rm *.class
