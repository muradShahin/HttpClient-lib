package mtech.prokect.cbojrestclientlibrary;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Service
public class HttpClientService {


    private final static Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    private final RestTemplate restTemplate;

    public HttpClientService() {

        this.restTemplate = new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMinutes(1))
                .build();
    }

    public ResponseEntity<?> httpClient(String url, HttpMethod method, HttpHeaders headers, String request) {

        HttpEntity<String> httpEntity = new HttpEntity<String>(request, headers);

        ResponseEntity<Object> response = null;

        try {

            response = restTemplate.exchange(url, method, httpEntity, Object.class);
            GenericResponse genericRes = responseMapper(response);
            logger.info("backend response : " + response.getStatusCode());
            return new ResponseEntity<GenericResponse>(genericRes, response.getStatusCode());

        } catch (HttpClientErrorException e) {
            logger.info("HTTP Exception=" + response);

            GenericResponse res = new GenericResponse();
            res.setResponse(response);
            res.setMessage(e.getMessage());
            res.setStatus((HttpStatus) e.getStatusCode());
            return new ResponseEntity<GenericResponse>(res, e.getStatusCode());
        }


    }

    private GenericResponse responseMapper(ResponseEntity<Object> response) {

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResponse(response.getBody());
        genericResponse.setMessage("");
        genericResponse.setStatus((HttpStatus) response.getStatusCode());
        return genericResponse;
    }
}
