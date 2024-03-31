using System.Net.Sockets;
using System.Net;
using System;
using System.Text;

namespace SmartMouse
{
    class Server
    {
        private static int port = 11000;

        public static string getIP()
        {
            string result = "";
            string hostname = Dns.GetHostName();
            IPAddress[] adrList = Dns.GetHostAddresses(hostname);
            foreach (IPAddress address in adrList)
            {
                if (address.AddressFamily == AddressFamily.InterNetwork)
                    result += address.ToString() + "\n";
            }

            return result;
        }

        public static async void Start()
        {
            var udpSocket = new Socket(SocketType.Dgram, ProtocolType.Udp);
            var udpLocal = new IPEndPoint(IPAddress.Any, port);
            udpSocket.Bind(udpLocal);

            Form1.updateLog($"UDP Server has started on {port} Waiting for a connection");

            try
            { 
                await Task.Run(async () =>
                {
                    while (true)
                    {
                        byte[] udpBuffer = new byte[512];
                        EndPoint udpRemote = new IPEndPoint(IPAddress.Any, port);
                        var udpLength = udpSocket.ReceiveFrom(udpBuffer, ref udpRemote);
                        string updData = Encoding.UTF8.GetString(udpBuffer);
                        Form1.updateLog($"Received Message is {updData}");
                        CallController(updData);
                    }
                });
                
            }
            catch (Exception e)
            {
                Form1.updateLog($"サーバーの起動に失敗しました\n{e}");
            }
            finally
            {
                udpSocket.Close();
            }
            
        }

        private static void CallController(string message)
        {
            string[] commands = message.Split(",");

            if (commands[0] == "connect") 
            {
                Send(commands[1], "server connected");
            }
            else if (commands[0] == "move")
            {
                int inputX, inputY;
                try
                {
                    inputX = int.Parse(commands[1]);
                    inputY = int.Parse(commands[2]);
                    Controller.Mouse.Move(inputX, inputY);
                }
                catch {}
            }

        }

        public static void Send(string ip, string message)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);

            IPAddress broadcast = IPAddress.Parse(ip);

            byte[] sendbuf = Encoding.ASCII.GetBytes(message);
            IPEndPoint ep = new IPEndPoint(broadcast, 11000);

            s.SendTo(sendbuf, ep);
        }
    }
}