package com.aesl.geoloc.repository;


import com.aesl.geoloc.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
