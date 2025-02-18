package br.edu.utfpr.agronomia.DTO;

import lombok.Getter;

/**
 * Classe que representa uma mensagem de resposta para os clientes HTTP.
 */
@Getter
public class MessageDTO {
    private String message;

    private MessageDTO(String message) {
        this.message = message;
    }

    /**
     * Cria um objeto Res contendo uma mensagem.
     *
     * @param message
     * @return Res
     */
    public static MessageDTO build(String message) {
        return new MessageDTO(message);
    }

    /**
     * Cria um objeto Res contendo uma mensagem.
     *
     * @param message
     * @return Res
     */
    public static MessageDTO b(String message) {
        return build(message);
    }
}
