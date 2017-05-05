package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@EnableBinding(ReservationChannels.class)
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

	@Bean
	CommandLineRunner runner (ReservationRepository rr) {
		return string -> {
			Stream.of("Eduardo", "Karla", "Robi", "Goldi", "Bambi")
					.forEach(name -> rr.save(new Reservation(name)));

			rr.findAll().forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

interface ReservationChannels {
	@Input
	SubscribableChannel input();
}

@MessageEndpoint
class ReservationProcessor {
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationProcessor(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@ServiceActivator(inputChannel = "input")
	public void onNewReservation(String reservationName) {
		this.reservationRepository.save(new Reservation(reservationName));
	}
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@RestResource(path = "by-name")
	public Collection<Reservation> findByReservationName(@Param("rn") String rn);
}

@Entity
class Reservation {

	@Id
	@GeneratedValue
	private Long id;
	private String reservationName;

	public Reservation() {
	}

	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", reservationName='" + reservationName + '\'' +
				'}';
	}
}

@RefreshScope
@RestController
class MessageRestController {
	@Value("${message:default message here}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		return this.message;
	}
}