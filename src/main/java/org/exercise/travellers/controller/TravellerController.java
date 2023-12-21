package org.exercise.travellers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.services.TravellersService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/api/traveller")
@RequiredArgsConstructor
@Slf4j
public class TravellerController {

	private final TravellersService travellersService;

	// This was the first approach because the exercise didn't specify if it should be one get for all types or different gets
	@GetMapping()
	public TravellerDto getTraveller(@RequestParam String searchValue) {
		log.info("Search for the traveller with: " + searchValue);
		return TravellerParser.toDto(travellersService.getTraveller(searchValue));
	}

	// Better implementation different get for each case
	@GetMapping("/by_email")
	public TravellerDto getTravellerByEmail(@RequestParam String email) {
		log.info("Search for the traveller by the email: " + email);
		return TravellerParser.toDto(travellersService.getTravellerByEmail(email));
	}

	@GetMapping("/by_mobile")
	public TravellerDto getTravellerByMobile(@RequestParam int mobile) {
		log.info("Search for the traveller by the mobile: " + mobile);
		return TravellerParser.toDto(travellersService.getTravellerByMobile(mobile));
	}

	@GetMapping("/by_document")
	public TravellerDto getTravellerByDocument(@Valid @RequestParam("documentType") DocumentTypeEnum documentTypeEnum, @Valid @RequestParam("documentNumber") String documentNumber, @Valid @RequestParam("issuingCountry") String issuingCountry) {
		log.info("Search for the traveller by the Document. Document Type: {} - Document Number: {} - Issuing Country: {}", documentTypeEnum, documentNumber, issuingCountry);
		return TravellerParser.toDto(travellersService.getTravellerByDocument(documentTypeEnum, documentNumber, issuingCountry));
	}

	@PostMapping
	public TravellerDto addTraveller(@Valid @RequestBody CreateTravellerDto createTravellerDto) {
		log.info("Add traveller with: " + createTravellerDto.getFirstName());
		return TravellerParser.toDto(travellersService.addTraveller(createTravellerDto));
	}

	@PutMapping
	public TravellerDto updateTraveller(@Valid @RequestBody TravellerDto updateTravellerDto) {
		log.info("Update traveller with: " + updateTravellerDto.firstName());
		return TravellerParser.toDto(travellersService.updateTraveller(updateTravellerDto));
	}

	@DeleteMapping("/{id}")
	public void deleteTraveller(@PathVariable Long id) {
		log.info("Deleting traveller with id: " + id);
		travellersService.deleteTraveller(id);
	}
}
