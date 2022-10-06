package cat.udl.eps.softarch.demo.domain;


import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Data
//@EqualsAndHashCode(callSuper = true)
//@Table(name = "Request")

public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Length(min = 2, max = 24)
    private String name;

    @NotEmpty
    @DecimalMin(value = "0")
    private BigDecimal price;

    @NotEmpty
    @Length(min = 10, max = 200)
    private String description;


    @NotEmpty
   // @NotBlank
    // @ForeignKey
    private String requester;



}
