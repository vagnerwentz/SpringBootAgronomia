package br.edu.utfpr.agronomia.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String id;
    private String content;
    private Date createdAt;
}
