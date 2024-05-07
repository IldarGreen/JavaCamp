#1 make target directory
mkdir target

#3 compiles files, and put them into target directory
javac -cp ".:./lib/JColor-5.5.1.jar:./lib/jcommander-1.82.jar" -d ./target/ src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

#4 copy sources and lib
cp -r src/resources target/.; cp -r lib target/.

#5 unpacks jar files to target folder
cd target ; jar xf lib/JColor-5.5.1.jar com ; jar xf lib/jcommander-1.82.jar com ; cd ..

#6 create jar
jar -cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

#7 run program
java -jar target/images-to-chars-printer.jar --white=RED --black=YELLOW
