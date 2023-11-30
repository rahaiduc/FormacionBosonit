package com.trip.repository;

import com.trip.domain.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje,Integer> {
}
