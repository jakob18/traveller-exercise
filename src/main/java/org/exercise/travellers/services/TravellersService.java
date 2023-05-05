package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.entities.Traveller;

public interface TravellersService {

    /**
     * Should support search by email or mobile or document
     * When Traveller has multiple documents assigned, Traveller can be retrieved only by the active one
     * Deactivated Travellers can’t be retrieved
     *
     * @param searchValue - the value to be searched on the database, can be email, mobile or document
     * @return the Traveller Dto
     */
    Traveller getTraveller(String searchValue);

    /**
     * Create Traveller, where the following data should be accepted:
     * First name, last name, date of birth, email, mobile number
     * Traveller’s document, which can be one of the types: passport or id card or driving license.
     * Unique document is identified by unique combination of document type, document number and document issuing country.
     * Traveller can have multiple documents assigned, but only one is active at a time.
     * One unique document can be assigned to only one Traveller.
     * Email, Mobile Number and Document are unique and can be assigned to only one Traveller
     *
     * @param createTravellerDto the object to be created on the DB
     * @return the object created
     */
    Traveller addTraveller(CreateTravellerDto createTravellerDto);

    /**
     * Accepts the same data as in case of Create Traveller operation
     * Deactivated Travellers can’t be updated
     *
     * @param updateTravellerDto the object to be updated on the DB
     * @return the object updated
     */
    Traveller updateTraveller(CreateTravellerDto updateTravellerDto);

    /**
     * Operation should disable the Traveller, so it can’t be retrieved through any API operation, but the data will stay in Database.
     *
     * @param id the id to be disabled
     */
    void deleteTraveller(String id);

}
