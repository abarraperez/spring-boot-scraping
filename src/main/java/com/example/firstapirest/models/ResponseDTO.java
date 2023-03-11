package com.example.firstapirest.models;

import lombok.Data;

@Data // con esta anotacion creamos los getters y setters automaticamente gracias a la dependencia lombok
public class ResponseDTO {
    String title;
    String url;
}

