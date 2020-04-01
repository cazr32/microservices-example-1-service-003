FROM tomcat:8.5.53

ADD **/*.war /usr/local/tomcat/webapps/
#ADD **/*.war /usr/docker/SampleProject2/tomcat/

EXPOSE 8080

#CMD ["catalina.sh", "run"]