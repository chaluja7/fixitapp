package cz.cvut.jee.entity;

import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.entity.enums.PersonRole;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

/**
 * @author jakubchalupa
 * @since 27.12.14
 */
@Transactional(TransactionMode.ROLLBACK)
@RunWith(Arquillian.class)
public class SomeTest {

    @PersistenceContext(name = "test")
    EntityManager em;

    @Deployment
    public static Archive<?> getDeployment() {

        File[] libs = Maven.resolver().
                loadPomFromFile("pom.xml").
                resolve("postgresql:postgresql", "joda-time:joda-time", "org.jadira.usertype:usertype.core").
                withTransitivity().
                asFile();

        return ShrinkWrap
                .create(WebArchive.class)
                .addAsWebInfResource("web.xml")
                .addPackage(Package.getPackage("cz.cvut.jee.entity"))
                .addClass(PersonRole.class)
                .addClass(IncidentState.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(libs)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testSomething() {
        Person person = new Person();
        person.setUsername("neco@aa.cz");
        person.setName("Pepa");
        person.setSurname("Novak");
        person.setPassword("xxxyyy");

        em.persist(person);
        Person p = em.find(Person.class, person.getId());


        Assert.assertEquals(person.getUsername(), p.getUsername());
        Assert.assertEquals(person.getName(), p.getName());
    }

}
