echo "set the class path"
echo "assumes the build was executed with maven copy-dependencies"
export BASE_CP=jobs4u.app.backoffice.console/target/jobs4u.app.backoffice.console-0.1.0.jar:jobs4u.app.backoffice.console/target/dependency/*;

java -cp $BASE_CP jobs4u.base.app.backoffice.console.BaseBackoffice

# NOTE:
# export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64/"