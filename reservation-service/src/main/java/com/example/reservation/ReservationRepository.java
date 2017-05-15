package com.example.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * Created by esuarezv on 15/05/2017.
 */
@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @RestResource(path = "by-name")
    public Collection<Reservation> findByReservationName(@Param("rn") String rn);
}
