package org.guerror.dataTest;

import org.guerror.dataTest.entity.Flight;
import org.guerror.dataTest.repository.FlightRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CrudTest {
    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void shouldfPerformCRUDOperations(){
        final Flight flight = new Flight();
        flight.setOrigin("London");
        flight.setDestination("New York");
        flight.setScheduledAt(LocalDateTime.now());
        flightRepository.save(flight);
        assertThat(flightRepository.findAll()).hasSize(1).first().isEqualToComparingFieldByField(flight);
        flightRepository.deleteById(flight.getId());
        assertThat(flightRepository.count()).isZero();

    }


}
