package org.guerror.dataTest;

import org.guerror.dataTest.entity.Flight;
import org.guerror.dataTest.repository.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DerivedQueriesTests {

    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldFindFlightFromLondon(){
        final Flight flight1 = createFlight("London");
        final Flight flight2 = createFlight("London");
        final Flight flight3 = createFlight("Salta");
        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightToLondon = flightRepository.findByOrigin("London");

        assertThat(flightToLondon).hasSize(2);
        assertThat(flightToLondon.get(0)).isEqualToComparingFieldByField(flight1);
        assertThat(flightToLondon.get(1)).isEqualToComparingFieldByField(flight2);

    }

    @Test
    public void shouldFindFlightFromLondontoMadrid(){
        final Flight flight1 = createFlight("LONDON","Madrid");

        flightRepository.save(flight1);

        List<Flight> flightToLondon = flightRepository.findByOriginIgnoreCase("London");

        assertThat(flightToLondon).hasSize(1);
        assertThat(flightToLondon.get(0)).isEqualToComparingFieldByField(flight1);


    }

    @Test
    public void shouldFindFlightFromArray(){
        final Flight flight1 = createFlight("London","Madrid");
        final Flight flight2 = createFlight("Madrid","Madrid");
        final Flight flight3 = createFlight("Salta", "London");
        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightToLondon = flightRepository.findByOriginIn("London","Madrid");

        assertThat(flightToLondon).hasSize(2);
        assertThat(flightToLondon.get(0)).isEqualToComparingFieldByField(flight1);
        assertThat(flightToLondon.get(1)).isEqualToComparingFieldByField(flight2);

    }

    @Test
    public void shouldFindFlightFromOriginIgnoreCase(){
        final Flight flight1 = createFlight("London","Madrid");
        final Flight flight2 = createFlight("Madrid","Madrid");
        final Flight flight3 = createFlight("Salta", "London");
        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightToLondon = flightRepository.findByOriginIn("London","Madrid");

        assertThat(flightToLondon).hasSize(2);
        assertThat(flightToLondon.get(0)).isEqualToComparingFieldByField(flight1);
        assertThat(flightToLondon.get(1)).isEqualToComparingFieldByField(flight2);

    }



    private Flight createFlight(String origin){
        final Flight a = new Flight();
        a.setOrigin(origin);
        a.setDestination(origin);
        a.setScheduledAt(LocalDateTime.now());
        return a;
    }

    private Flight createFlight(String origin, String destination){
        final Flight a = new Flight();
        a.setOrigin(origin);
        a.setDestination(destination);
        a.setScheduledAt(LocalDateTime.now());
        return a;
    }


}
