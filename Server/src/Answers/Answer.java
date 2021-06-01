package Answers;


import java.io.Serializable;

public abstract class Answer implements Serializable {
    private static final long serialVersionUID = -7768203184167854883L;
    protected Object answer;
    public TypeAnswer ty;

    public Answer(Object answer) {
        this.answer = answer;
    }

    public Object getAnswer(){ return answer; }

    public void setTy(TypeAnswer ty) {
        this.ty = ty;
    }

    public TypeAnswer getType(){
        return ty;
    }
    public abstract void logAnswer();
    public abstract void printAnswer();
}
