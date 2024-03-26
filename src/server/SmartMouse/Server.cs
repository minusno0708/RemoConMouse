using System.Net.Sockets;
using System.Net;
using System;
using SmartMouse;

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
            SmartMouse.Form1.updateLog($"Server has started on {ip}:{port}.{Environment.NewLine} Waiting for a connection");

            TcpClient client = new TcpClient();
            SmartMouse.Form1.updateLog("A client connected.");
        }
    }
}