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
            var tcpSocket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            var tcpLocal = new IPEndPoint(IPAddress.Any, port);
            tcpSocket.Bind(tcpLocal);

            var udpSocket = new Socket(SocketType.Dgram, ProtocolType.Udp);
            var udpLocal = new IPEndPoint(IPAddress.Any, port);
            udpSocket.Bind(udpLocal);

            Form1.updateLog($"Server has started on {port} Waiting for a connection");

            try
            {
                tcpSocket.Listen(1);
                Task tcpTask = Task.Run(async () =>
                {
                    while (true)
                    {
                        string tcpData = await TcpListenerAsync(tcpSocket);
                        Form1.updateLog($"TCP Received Message is {tcpData}");
                        CallController(tcpData);
                    }
                });
                Task udpTask = Task.Run(async () =>
                {
                    while (true)
                    {
                        string udpData = await UdpListenerAsync(udpSocket);
                        Form1.updateLog($"UDP Received Message is {udpData}");
                        CallController(udpData);
                    }
                });

                await Task.WhenAll(tcpTask, udpTask);
                
            }
            catch (Exception e)
            {
                Form1.updateLog($"サーバーの起動に失敗しました\n{e}");
            }
            finally
            {
                tcpSocket.Close();
                udpSocket.Close();
            }
            
        }

        private static async Task<string> TcpListenerAsync(Socket socket)
        {
            var client = await socket.AcceptAsync();
            byte[] buffer = new byte[512];
            var length = await client.ReceiveAsync(buffer, SocketFlags.None);
            string data = Encoding.UTF8.GetString(buffer, 0, length);

            byte[] message = Encoding.UTF8.GetBytes("ok");
            client.Send(message);

            client.Close();

            return data;
        }

        private static async Task<string> UdpListenerAsync(Socket socket)
        {
            byte[] buffer = new byte[512];
            EndPoint remote = new IPEndPoint(IPAddress.Any, port);
            var length = await socket.ReceiveAsync(buffer, SocketFlags.None);
            string data = Encoding.UTF8.GetString(buffer);

            return data;
        }

        private static void CallController(string message)
        {
            string[] commands = message.Split(",");

            if (commands[0] == "connect") 
            {
                SendTcp(commands[1], "server connected");
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

        public static void SendTcp(string ip, string message)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPAddress broadcast = IPAddress.Parse(ip);

            byte[] sendbuf = Encoding.ASCII.GetBytes(message);
            IPEndPoint ep = new IPEndPoint(broadcast, 11000);

            s.Connect(ep);
            s.Send(sendbuf);
            s.Close();
        }

        public static void SendUdp(string ip, string message)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);

            IPAddress broadcast = IPAddress.Parse(ip);

            byte[] sendbuf = Encoding.ASCII.GetBytes(message);
            IPEndPoint ep = new IPEndPoint(broadcast, 11000);

            s.SendTo(sendbuf, ep);
            s.Close();
        }
    }
}