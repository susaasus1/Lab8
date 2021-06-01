package client;

import Answers.Answer;
import Answers.TypeAnswer;
import Data.SpaceMarine;
import Data.SpaceMarines;
import aplicattion.MainApp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

public class Receiver extends Thread {
    private final DatagramChannel channel;
    private final SocketAddress serverAddress;
    private final ByteBuffer buffer;
    private SpaceMarines spaceMarines;
    private String line;
    private boolean ready;

    public Receiver(DatagramChannel channel, SocketAddress serverAddress) {
        this.channel = channel;
        this.serverAddress = serverAddress;
        this.buffer = ByteBuffer.allocate(16384);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                buffer.clear();
                channel.connect(serverAddress);
                channel.receive(buffer);
                buffer.flip();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Answer answer = (Answer) objectInputStream.readObject();

                if (answer.getType()==TypeAnswer.DATA){
                    MainApp.space=(LinkedList<SpaceMarine>) answer.getAnswer();
                }else {
                    if (answer.getAnswer().equals("BigData")) {
                        PrintConsole.println("Слишком большой объем данных. Ожидаемое количество пакетов:");
                        buffer.clear();
                        channel.receive(buffer);
                        buffer.flip();
                        byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        Answer countAnswer = (Answer) objectInputStream.readObject();

                        String bigAnswer;
                        for (int i = 0; i < Integer.parseInt(countAnswer.getAnswer().toString()); i++) {
                            buffer.clear();
                            channel.receive(buffer);
                            buffer.flip();
                            byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                            objectInputStream = new ObjectInputStream(byteArrayInputStream);
                            Answer newAnswer = (Answer) objectInputStream.readObject();
                            PrintConsole.print(newAnswer.getAnswer().toString());
                        }
                    } else {
                        MainApp.answerLine = answer.getAnswer().toString();
                        ready = true;
                    }
                }
                objectInputStream.close();
                byteArrayInputStream.close();
                buffer.clear();
                channel.disconnect();
            } catch (PortUnreachableException | IllegalStateException e) {
                MainApp.answerLine="Сервер не доступен";
                ready=true;
                try {
                    channel.disconnect();
                } catch (IOException ex) {
                    MainApp.answerLine="Не удалось получить ответ";
                    ready=true;
                    ex.getMessage();
                    ex.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                PrintConsole.printerror("Произошла непредвиденная ошибка");
            }
        }
    }

    @Override
    public synchronized void start() {
        this.setDaemon(true);
        super.start();
    }
    public boolean getReady() {
        return this.ready;
    }
    public void setReady(boolean b) {
        this.ready = b;
    }

}

