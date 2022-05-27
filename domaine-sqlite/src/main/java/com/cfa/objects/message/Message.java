package com.cfa.objects.message;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Message implements Serializable {

    private String message;

}
