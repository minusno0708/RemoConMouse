using System.Net.Sockets;
using System.Net;
using System;
using System.Text;

namespace SmartMouse
{
    class Server
    {

        public static async void Start()
        {
            int port = 11000;
            UdpClient listener = new UdpClient(port);
            IPEndPoint groupEP = new IPEndPoint(IPAddress.Any, port);
            Form1.updateLog($"UDP Server has started on {port} Waiting for a connection");

            try
            { 
                await Task.Run(async () =>
                {
                    while (true)
                    {
                        Form1.updateLog("Waiting for broadcast");
                        byte[] bytes = listener.Receive(ref groupEP);
                        Form1.updateLog($"Received broadcast from {groupEP} :");
                        Form1.updateLog($"Recieved Message is {Encoding.ASCII.GetString(bytes, 0, bytes.Length)}");
                        CallController(Encoding.ASCII.GetString(bytes, 0, bytes.Length));
                    }
                });
                
            }
            catch
            {
                Form1.updateLog("サーバーの起動に失敗しました");
            }
            finally
            {
                listener.Close();
            }
            
        }

        private static void CallController(string message)
        {
            string[] commands = message.Split("-");
            
            if (commands[0] == "move")
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

        public static void Send(string message)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);

            IPAddress broadcast = IPAddress.Parse("127.0.0.1");

            byte[] sendbuf = Encoding.ASCII.GetBytes(message);
            IPEndPoint ep = new IPEndPoint(broadcast, 11000);

            s.SendTo(sendbuf, ep);
        }
    }
}