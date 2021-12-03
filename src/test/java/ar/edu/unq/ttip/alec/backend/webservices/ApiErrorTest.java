package ar.edu.unq.ttip.alec.backend.webservices;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class ApiErrorTest {

        private String status;
        private String message;
        private List errors;

        public ApiErrorTest(){}
}
