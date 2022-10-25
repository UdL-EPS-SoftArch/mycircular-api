package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.time.ZonedDateTime;

@Entity
@Data

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"authorId", "aboutId"})
})

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
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "authorId")
    @NotNull
    private User author;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "aboutId")
    @NotNull
    private User about;
}
