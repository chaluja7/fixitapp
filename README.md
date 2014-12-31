A4M36JEE - Fix it! app
======================================

Project preparation:
--------------------

--NASTAVENÍ DATABÁZE
--nejdřív se připojím na jboss, server musí běžet:
./bin/jboss-cli.sh --connect

--musím přidat postgresql aby ho měl WF k dispozici, cestu v resources nahraď kde máš stažený .jar
module add --name=org.postgresql --resources=~/Downloads/postgresql-9.1-903.jdbc4.jar --dependencies=javax.api,javax.transaction.api

--přidej jdbc driver
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-xa-datasource-class-name=org.postgresql.xa.PGXADataSource)

--nakonec musíš přidat vlastní datasource. nějak to nejde v WF 8.1 přes cli tak to hoď rovnou do standalone.xml. Přijde to do <datasources>
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

--a databáze jede :)

--Zbývá nastavit security-domain
--do standalone.xml přidáme na příslušné místo
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