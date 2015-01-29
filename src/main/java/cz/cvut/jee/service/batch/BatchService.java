package cz.cvut.jee.service.batch;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.Properties;

/**
 * Service to plan run times of batch jobs.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Singleton
@RunAs("SYSTEM")
public class BatchService {

    /**
     * will perform processInvalidIncidentsJob job
     */
    @Schedule(hour = "02", minute = "15")
    @RolesAllowed({"SUPER_ADMIN", "SYSTEM"})
    public void processInvalidIncidentsJob() {
        BatchRuntime.getJobOperator().start("processInvalidIncidentsJob", new Properties());
    }

}
