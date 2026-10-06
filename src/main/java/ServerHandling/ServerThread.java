package ServerHandling;

import ClientProfiles.ClientHandling;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.util.List;

import java.net.Socket;

public class ServerThread implements Runnable
{
    //Chat Type ENUM
    enum CHAT_TYPE
    {
        GLOBAL,
        PRIVATE,
        GROUP
    }

    //Global Variables
    private Socket clientSocket;
    private DataOutputStream serverToClient;
    private DataInputStream clientToServer;
    private List<ServerThread> connections;

    //User-Specific Variables
    private ClientHandling user;
    protected String userString = "";
    protected String[] userArgs;

    public ServerThread(Socket client, List<ServerThread> connections)
    {
        this.connections = connections;
        this.clientSocket = client;
    }

    @Override
    public void run()
    {
        try {
            this.serverToClient = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            this.clientToServer = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            this.user = new ClientHandling(clientToServer.readUTF());

            System.out.println("User " + this.user.getName() + " has successfully connected");
            //broadcastMessage(this.user.getName() + " has entered " + CHAT_TYPE.GLOBAL + " chat! Welcome them in!");

            serverToClient.writeUTF("Welcome to the chat");
            serverToClient.flush();

            while (!userString.equalsIgnoreCase("Quit"))
            {
                userString = clientToServer.readUTF();
                System.out.println(this.user.getName() + ": " + userString);

                serverToClient.writeUTF("Reflecting the message of " + userString);
                serverToClient.flush();

                if (userString.startsWith("/nick "))
                {
                    userArgs = userString.split(" ", 2);
                    if (userArgs.length == 2)
                    {
                        serverToClient.writeUTF("Are you wanting to switch from " + this.user.getName() + " to " + userArgs[1] + " ?");
                        serverToClient.flush();

                        userString = clientToServer.readUTF();
                        if (userString.equalsIgnoreCase("yes"))
                        {
                            broadcastMessage(this.user.getName() + " has changed their name to " + userArgs[1]);
                            this.user.setName(userArgs[1]);

                        }
                    }
                }


            }







        } catch (IOException e) {
            System.out.println("Failed to initialize user");
        }

    }

    public void broadcastMessage(String msg) throws IOException {
        for (ServerThread x : connections)
        {
            serverToClient.writeUTF("[" + CHAT_TYPE.GLOBAL + "] " + this.user.getName() + ": " + msg);
        }
    }

    public void privateMessage(String msg, String destUser) throws IOException {
        for (ServerThread x : connections)
        {
            if (x.user.getName().equalsIgnoreCase(destUser))
            {
                serverToClient.writeUTF("[" + CHAT_TYPE.PRIVATE + "]" + this.user.getName() + ": " + msg);
                serverToClient.flush();
            }
        }
    }

    public void groupMessage(String msg, String destGroup)
    {

    }
}
