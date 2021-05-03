package Exceptions;

import java.io.IOException;

public class WrongSpaceMarineException extends IOException {
    public WrongSpaceMarineException(String line) {
        System.err.println("Данные внутри " + line + " в SpaceMarine не соответствуют требованиям! Исправьте, пожалуйста, данное поле");
    }
}
