package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
	@NotBlank(message = "Идентификатор сервиса не может быть пустым")
	private String app;
	@NotBlank(message = "URI не может быть пустым")
	private String uri;
	@NotBlank(message = "IP-адрес не может быть пустым")
	private String ip;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@PastOrPresent(message = "Дата и время запроса не могут быть в будущем")
	private LocalDateTime timestamp;
}