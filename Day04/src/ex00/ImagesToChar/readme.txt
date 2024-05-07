# For Windows

# go in to work directory - ImagesToChar

#1 make target directory
mkdir target

#2 this command compiles .java files into .class files, put them into target directory
javac src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java -d target

#3 run program
java -classpath target src.java.edu.school21.printer.app.Program . 0 $PWD/images/it.bmp

#-----------------------------------------------------------------------------------------------------------------------
# For Mac

# go in to work directory - ImagesToChar

#1 make target directory
mkdir target

#2 this command compiles .java files into .class files, put them into target directory
javac src/java/edu/school21/printer/*/*.java -d target

#3
java -classpath target src.java.edu.school21.printer.app.Program . 0 images/it.bmp
