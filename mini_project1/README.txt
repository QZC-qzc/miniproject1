1. For part 1, use the command "sudo docker -f Dockerfile ." to build the image, then use
the command "sudo docker images" to find the image ID that we created, after that. using 
the command "sudo docker run -d -t [image ID]" to build the container, then using the command
"sudo docker ps" to find the container ID, after that, using command "sudo docker exec
-it [container ID] /bin/bash" to log in to the container. The final step using command 
"bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar 
wordcount input/ output/" to run the wordcount example.

2. For part 2, use the java file named test.java to create a jar file, then upload the jar file into 
VM, using the jar file to do the test.

3. For part 3, q1 represents the first question, using the java file in this folder to create a jar file,
then upload the jar file into VM, using the jar file to do the test. Then repeat the same thing 
for question 2, question 3, and question 4.