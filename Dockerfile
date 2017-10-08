FROM jboss/wildfly
MAINTAINER Ingo Weichsel
ADD target/fractalservice.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
