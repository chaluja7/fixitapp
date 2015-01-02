# A4M36JEE - Fix it! app #

## Příprava projektu: ##

### Nastavení databáze ###

* nejdřív se připoj na wildfy (server musí běžet)

  ``./bin/jboss-cli.sh --connect``

* musíš přidat postgresql .jar aby ho měl WF k dispozici (cestu v resources nahraď kde máš stažený .jar)

  ``module add --name=org.postgresql --resources=~/Downloads/postgresql-9.1-903.jdbc4.jar --dependencies=javax.api,javax.transaction.api``

* přidej jdbc driver

  ``/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-xa-datasource-class-name=org.postgresql.xa.PGXADataSource)``

* nakonec musíš přidat vlastní datasource. Nějak se mi to v WF 8.1 nepodařilo nastavit přes cli tak to hoď rovnou do standalone.xml. Přijde to do ``<datasources>`` a samozřejmě si případně jednotlivé atributy změň. Před úpravou standalone.xml je žádoucí server vypnout.

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

* do standalone.xml přidáme na příslušné místo

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
  
* samotné přidání security-domain ale nestačí. Ještě ji potřebujeme přiřadit jako defaultní pro EJB, abychom mohli na jednotlivých beanách  používat @RolesAllowed anotace. Nalezneme tedy tag

  ```xml
  <subsystem xmlns="urn:jboss:domain:ejb3:2.0">
  ```
  
* uvnitř nalezneme setování defaultní security domény a nahradíme jej naší nově vytvořenou doménou  

  ```xml
  <default-security-domain value="fixitapp-jaas-realm"/>
  ```
  