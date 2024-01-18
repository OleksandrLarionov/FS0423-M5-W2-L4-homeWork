package larionov.API.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PayloadBlogPost {
    private Long authorId;
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private double tempoDiLettura;

}
