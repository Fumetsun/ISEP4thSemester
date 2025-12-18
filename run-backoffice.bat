REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=jobs4u.app.backoffice.console\target\jobs4u.app.backoffice.console-0.1.0.jar;jobs4u.app.backoffice.console\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% jobs4u.base.app.backoffice.console.BaseBackoffice
