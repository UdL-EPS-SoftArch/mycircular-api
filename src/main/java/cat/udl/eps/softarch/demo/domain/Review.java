package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Review {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private ZonedDateTime when = ZonedDateTime.now();

    @NotNull(message = "Must provide a valid number of stars in your review")
    @Min(1)
    @Max(5)
    private Integer stars;

    @Length(max = 256)
    private String message;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private User about;
}
