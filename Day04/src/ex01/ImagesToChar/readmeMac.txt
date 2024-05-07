##########################################################################################
# You may run this app by using the following command `chmod 777 readmeMac.txt; ./readmeMac.txt`
WHITE=.
BLACK=O
##########################################################################################
#1 remove old target folder
rm -rf target

#2 create target directory
mkdir target

#3 change class files directory
javac src/java/edu/school21/printer/*/*.java -d target

#4 copy sources
cp -R src/resources target

#5 create jar
jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

#6 change access to jar file
chmod 777 target/images-to-chars-printer.jar

#7 run program
java -jar target/images-to-chars-printer.jar ${WHITE} ${BLACK}
# or
#7.1 if you want manual run program
#java -jar target/images-to-chars-printer.jar . 0
