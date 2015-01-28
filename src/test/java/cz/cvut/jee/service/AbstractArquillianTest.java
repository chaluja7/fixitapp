package cz.cvut.jee.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;

import java.io.File;

/**
 * Abstract arquillian test class - base for concrete test classes.
 *
 * @author jakubchalupa
 * @since 27.12.14
 */
@Transactional((TransactionMode.ROLLBACK))
public abstract class AbstractArquillianTest extends Arquillian {

    @Deployment
    public static Archive<?> getDeployment() {


        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("postgresql:postgresql"
                        , "joda-time:joda-time"
                        , "org.jadira.usertype:usertype.core")
                .withTransitivity()
                .asFile();

        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, "cz.cvut.jee")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsLibraries(libs)
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-web.xml"), "jboss-web.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-ejb3.xml"), "jboss-ejb3.xml");

    }

    /**
     * will be perform before "SUPER_ADMIN" group annotated test method
     * @throws Exception
     */
    @BeforeGroups(groups = "SUPER_ADMIN")
    protected void loginSuperAdmin() throws Exception {
        login("spravce@eos.cz", "a");
    }

    /**
     * will be perform before "REGION_ADMIN" group annotated test method
     * @throws Exception
     */
    @BeforeGroups(groups = "REGION_ADMIN")
    protected void loginRegionAdmin() throws Exception {
        login("region@eos.cz", "a");
    }

    /**
     * will be perform before "OFFICER" group annotated test method
     * @throws Exception
     */
    @BeforeGroups(groups = "OFFICER")
    protected void loginOfficer() throws Exception {
        login("officer@eos.cz", "a");
    }

    /**
     * will be perform after group annotated test method
     * @throws Exception
     */
    @AfterGroups(groups = {"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    protected void logout() throws Exception {
        final SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
        securityClient.logout();
    }

    /**
     * will perform programmatic login
     * @param username username
     * @param password password
     * @throws Exception
     */
    protected void login(String username, String password) throws Exception {
        final SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
        securityClient.setSimple(username, password);
        securityClient.login();
    }

}
