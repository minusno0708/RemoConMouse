using System.Net.Sockets;
using System.Net;
using System;

namespace Server
{
    class Server
    {
        public static void Start()
        {
            string ip = "127.0.0.1";
            int port = 8080;
            TcpListener server = new TcpListener(IPAddress.Parse(ip), port);
            server.Start();
            Console.WriteLine("Server has started on {0}:{1}.{2} Waiting for a connection", ip, port, Environment.NewLine);

            TcpClient client = new TcpClient();
            Console.WriteLine("A client connected.");
        }
    }
}