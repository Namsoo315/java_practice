package com.codeit.mvc.domain;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
// xml
@JacksonXmlRootElement(localName = "user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String roles;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "devLang")
    private List<String> devLang;
}
// lombok 사용
