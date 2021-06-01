package Answers;

import javax.sound.midi.Receiver;
import java.io.Serializable;
import java.util.logging.Logger;

public class ErrorAnswer extends Answer implements Serializable {
    private static final long serialVersionUID = -7768203184167854883L;
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    public TypeAnswer ty;

    public ErrorAnswer(Object answer) {
        super(answer);
    }

    @Override
    public void logAnswer() {
        logger.severe(answer.toString());
    }

    @Override
    public void printAnswer() {
        System.err.println(answer.toString());
    }
    public void setType(TypeAnswer type){
        this.ty=type;
    }
    public TypeAnswer getType(){
        return ty;
    }
}
