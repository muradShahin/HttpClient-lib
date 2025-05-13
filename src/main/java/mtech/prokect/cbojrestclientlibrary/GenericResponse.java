package mtech.prokect.cbojrestclientlibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    private HttpStatus status;
    private String message;
    private Object response;


}