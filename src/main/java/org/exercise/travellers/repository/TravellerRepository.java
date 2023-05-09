package org.exercise.travellers.repository;

import org.exercise.travellers.entities.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TravellerRepository extends JpaRepository<Traveller, Long>, JpaSpecificationExecutor<Traveller> {

    Optional<Traveller> findByEmailAndIsActiveTrue(String email);

    Optional<Traveller> findByMobileNumberAndIsActiveTrue(int mobile);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(int mobile);

    Optional<Traveller> findByIdAndIsActiveTrue(long id);

}
