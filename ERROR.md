## Help me StackOverflow! You're my only hope!

### Error with SparkFiles 

```log
18/08/21 15:14:16 WARN myLogger: SparkFiles.get return type: String
18/08/21 15:14:16 WARN myLogger: ADWC FULL CONNECTION STRING: jdbc:oracle:thin:@crab_low?TNS_ADMIN=/data/var/tmp/spark-909196b8-d74d-4fcc-9c50-a31e5a048066/userFiles-ffe3ad07-e026-4b82-9a54-e918e844facd
18/08/21 15:14:16 INFO myLogger: Database Connection done
Exception in thread "main" java.sql.SQLRecoverableException: IO Error: Invalid connection string format, a valid format is: "host:port:sid" 
	at oracle.jdbc.driver.T4CConnection.logon(T4CConnection.java:743)
	at oracle.jdbc.driver.PhysicalConnection.connect(PhysicalConnection.java:666)
	at oracle.jdbc.driver.T4CDriverExtension.getConnection(T4CDriverExtension.java:32)
	at oracle.jdbc.driver.OracleDriver.connect(OracleDriver.java:566)
	at oracle.jdbc.pool.OracleDataSource.getPhysicalConnection(OracleDataSource.java:317)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:241)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:184)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main$.main(main.scala:59)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main.main(main.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:745)
	at org.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:187)
	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:212)
	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:126)
	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)
Caused by: oracle.net.ns.NetException: Invalid connection string format, a valid format is: "host:port:sid" 
	at oracle.net.resolver.AddrResolution.resolveSimple(AddrResolution.java:554)
	at oracle.net.resolver.AddrResolution.resolveAndExecute(AddrResolution.java:483)
	at oracle.net.ns.NSProtocol.establishConnection(NSProtocol.java:595)
	at oracle.net.ns.NSProtocol.connect(NSProtocol.java:230)
	at oracle.jdbc.driver.T4CConnection.connect(T4CConnection.java:1452)
	at oracle.jdbc.driver.T4CConnection.logon(T4CConnection.java:496)
	... 17 more
```



### Original problem

When `hdfs://` is included 

```log
Exception in thread "main" java.sql.SQLException: encountered a problem with the Secret Store. Check the wallet location for the presence of an open wallet (cwallet.sso) and ensure that this wallet contains the correct credentials using the mkstore utility: java.io.IOException: Unsupported WRL typehdfs:///user/spark/wallet_LUIGI
	at oracle.jdbc.driver.PhysicalConnection.getSecretStoreCredentials(PhysicalConnection.java:1466)
	at oracle.jdbc.driver.PhysicalConnection.parseUrl(PhysicalConnection.java:1214)
	at oracle.jdbc.driver.PhysicalConnection.readConnectionProperties(PhysicalConnection.java:965)
	at oracle.jdbc.driver.PhysicalConnection.<init>(PhysicalConnection.java:624)
	at oracle.jdbc.driver.T4CConnection.<init>(T4CConnection.java:398)
	at oracle.jdbc.driver.T4CDriverExtension.getConnection(T4CDriverExtension.java:31)
	at oracle.jdbc.driver.OracleDriver.connect(OracleDriver.java:566)
	at oracle.jdbc.pool.OracleDataSource.getPhysicalConnection(OracleDataSource.java:317)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:241)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:184)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.database$.write(database.scala:29)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main$.main(main.scala:32)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main.main(main.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:745)
	at org.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:187)
	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:212)
	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:126)
	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)
```

When `hdfs://` is not included

```log
Exception in thread "main" java.sql.SQLRecoverableException: IO Error: Unknown host specified 
	at oracle.jdbc.driver.T4CConnection.logon(T4CConnection.java:743)
	at oracle.jdbc.driver.PhysicalConnection.connect(PhysicalConnection.java:666)
	at oracle.jdbc.driver.T4CDriverExtension.getConnection(T4CDriverExtension.java:32)
	at oracle.jdbc.driver.OracleDriver.connect(OracleDriver.java:566)
	at oracle.jdbc.pool.OracleDataSource.getPhysicalConnection(OracleDataSource.java:317)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:241)
	at oracle.jdbc.pool.OracleDataSource.getConnection(OracleDataSource.java:184)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.database$.write(database.scala:29)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main$.main(main.scala:32)
	at com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main.main(main.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:745)
	at org.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:187)
	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:212)
	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:126)
	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)
Caused by: oracle.net.ns.NetException: Unknown host specified 
	at oracle.net.resolver.HostnameNamingAdapter.resolve(HostnameNamingAdapter.java:207)
	at oracle.net.resolver.NameResolver.resolveName(NameResolver.java:131)
	at oracle.net.resolver.AddrResolution.resolveAndExecute(AddrResolution.java:476)
	at oracle.net.ns.NSProtocol.establishConnection(NSProtocol.java:595)
	at oracle.net.ns.NSProtocol.connect(NSProtocol.java:230)
	at oracle.jdbc.driver.T4CConnection.connect(T4CConnection.java:1452)
	at oracle.jdbc.driver.T4CConnection.logon(T4CConnection.java:496)
	... 18 more
```


