package com.vitor.usuario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String nome;
    private String email;
    private Integer idade;
    private Double altura;

    @Override
    public String toString() {
        return "\n" + nome + "\n" + email + "\n" + idade + "\n" + altura;
    }
}
