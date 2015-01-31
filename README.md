# A4M36JEE - Fix it! app #

## Příprava projektu: ##

* příprava projektu je relevantní pro aplikační server **WildFly-8.1.0.Final**

### Nastavení databáze ###

* nejdřív spustíme server ve standalone módu

  ``./bin/standalone.sh``

* připojíme se na cli konzoli

  ``./bin/jboss-cli.sh --connect``

* přidáme postgresql .jar aby ho měl WF k dispozici (cestu v resources nahradíme cestou kde máme stažený .jar)

  ``module add --name=org.postgresql --resources=~/Downloads/postgresql-9.1-903.jdbc4.jar --dependencies=javax.api,javax.transaction.api``

* přidáme jdbc driver

  ``/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-xa-datasource-class-name=org.postgresql.xa.PGXADataSource)``
  
* pokud proběhl cli script správně, ve *standalone.xml* se nám objeví nově přidaný driver:

  ```xml
  <driver name="postgresql" module="org.postgresql">
    <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
  </driver>
  ```

* nakonec musíme přidat vlastní datasource. Nějak se mi to v WF 8.1 nepodařilo nastavit přes cli tak to přidáme rovnou do *standalone.xml*. Přijde to do ``<datasources>`` a samozřejmě si případně jednotlivé atributy změníme. Před úpravou *standalone.xml* je žádoucí server vypnout.

  ```xml
  <xa-datasource jndi-name="java:jboss/datasources/AppXADS" pool-name="AppXADS" enabled="true">
    <xa-datasource-property name="ServerName">
        localhost
    </xa-datasource-property>
    <xa-datasource-property name="PortNumber">
        5432
    </xa-datasource-property>
    <xa-datasource-property name="DatabaseName">
        fixit
    </xa-datasource-property>
    <driver>postgresql</driver>
    <xa-pool>
        <min-pool-size>10</min-pool-size>
        <max-pool-size>25</max-pool-size>
        <prefill>true</prefill>
    </xa-pool>
    <security>
        <user-name>postgres</user-name>
        <password>postgres</password>
    </security>
  </xa-datasource>
  ```


### Nastavení security-domain ###

* do *standalone.xml* přidáme na příslušné místo

  ```xml
  <security-domain name="fixitapp-jaas-realm">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/AppXADS"/>
            <module-option name="principalsQuery" value="select password from person where username=?"/>
            <module-option name="rolesQuery" value="select role, 'Roles' from person where username=?"/>
            <module-option name="hashAlgorithm" value="SHA-256"/>
            <module-option name="hashEncoding" value="base64"/>
        </login-module>
    </authentication>
  </security-domain>
  ```
  
## Příprava projektu pro běh v clusteru: ##

* nastavení probíhá zcela totožně, jako je popsané v návodu výše, jediný rozdíl je, že nekonfigurujeme *standalone.xml* ale **standalone-ha.xml**

* v našem případě (již máme přidaný modul postgre) tedy pouze do *standalone-ha.xml* přidáme na příslušná místa *postgre driver*, náš *xa-datasource* a naší *security-domain*

* nyní si vytvoříme druhou instanci *wildfly* serveru (fyzicky druhou kopii). Konfigurační *standalone-ha.xml* do nové instance zkopírujeme z prvního serveru

* .war naší nasazované aplikace zkopírujeme na oba servery
  ```
  cp target/fixapp.war WF-1/standalone/deployments
  cp target/fixapp.war WF-2/standalone/deployments
  ```
  
* zbývá oba server spustit
  ```
  WF-1/bin/standalone.sh -c standalone-ha.xml -Djboss.node.name=`whoami`
  WF-2/bin/standalone.sh -c standalone-ha.xml -Djboss.socket.binding.port-offset=100 -Djboss.node.name=`whoami`2
  ```

* defaultně nyní aplikace poběží na portech *8080* a *8180*

  