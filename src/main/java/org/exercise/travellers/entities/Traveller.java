package org.exercise.travellers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "traveller")
public class Traveller implements Serializable {

	public static final String SPEC_IS_ACTIVE = "isActive";

	@Serial
	private static final long serialVersionUID = -7880395848287219784L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native")
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "DATE_OF_BIRTH", nullable = false)
	private Date birthDate;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "MOBILE_NUMBER", nullable = false)
	private int mobileNumber;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@OneToMany(mappedBy = "traveller", fetch = FetchType.EAGER)
	private List<TravellerDocument> travellerDocuments = new ArrayList<>();

	public TravellerDocument getActiveDocument() {
		return travellerDocuments.stream()
				.filter(TravellerDocument::isActive)
				.findFirst()
				.orElse(null);
	}

}
