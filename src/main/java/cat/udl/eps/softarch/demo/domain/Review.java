package cat.udl.eps.softarch.demo.domain;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date when = new Date();

    @NotNull(message = "You must to provide a number of stars in your review")
    @Min(0)
    @Max(5)
    private Integer stars;

    @Length(max = 256)
    private String message;

    @ManyToOne
    private User author;

    @ManyToOne
    private User about;
}
