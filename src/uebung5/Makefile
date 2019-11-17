all: quadtree.jar
clean: 
	rm *.class *.jar
quadtree.jar:	Interval2D.java Interval.java QuadTree.java
	javac *.java 
	jar -cf quadtree.jar *.class 
run:	quadtree.jar
ifndef M
	$(error M (number of queries) is not set)
endif
ifndef N
	$(error N (number of points to generate the tree) is not set)
endif
	java -cp quadtree.jar QuadTree $(M) $(N)
 
