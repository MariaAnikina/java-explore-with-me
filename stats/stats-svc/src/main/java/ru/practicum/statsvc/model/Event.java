package ru.practicum.statsvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "statistics")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 128)
	private String app;
	@Column(length = 128)
	private String uri;
	@Column(length = 40)
	private String ip;
	private LocalDateTime timestamp;
}