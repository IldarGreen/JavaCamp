#1 make target directory
mkdir target

#2 this command compiles .java files into .class files, put them into target directory
javac src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java -d target

#3 copy sources
cp -R src/resources target

#4 create jar
jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

#5 run program
java -jar target/images-to-chars-printer.jar . 0
