package client;

import Exceptions.CommandAlreadyExistsException;
import aplicattion.MainApp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Client {
    private CommandReader reader;
    private Receiver receiver;
    private Sender sender;
    private SocketAddress address = new InetSocketAddress("localhost", 12501);

    public static void main(String[] args) {
    }

    public void launch() {
        try {
            DatagramChannel channel = DatagramChannel.open();
            sender = new Sender(channel, address);
            reader = new CommandReader(sender);
            receiver = new Receiver(channel, address);
            receiver.start();
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | CommandAlreadyExistsException e) {
            PrintConsole.printerror("Произошла непредвиденная ошибка");
        }
    }
    public void executeCommand(String line){
        receiver.setReady(false);
        reader.readFXMLCommand(line);
    }
    public boolean isReceiverReady() {
        return receiver.getReady();
    }
    public Receiver getReceiver(){
        return receiver;
    }
}
