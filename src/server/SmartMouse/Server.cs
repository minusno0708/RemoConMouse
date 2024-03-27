using System.Net.Sockets;
using System.Net;
using System;
using System.Text;

namespace SmartMouse
{
    class Server
    {
        private static int port = 11000;

        public static async void Start()
        {
            UdpClient listener = new UdpClient(port);
            IPEndPoint groupEP = new IPEndPoint(IPAddress.Any, port);
            Form1.updateLog($"UDP Server has started on {port} Waiting for a connection");

            try
            { 
                await Task.Run(async () =>
                {
                    while (true)
                    {
                        byte[] bytes = listener.Receive(ref groupEP);
                        Form1.updateLog($"Recieved Message is {Encoding.ASCII.GetString(bytes, 0, bytes.Length)}");
                        CallController(Encoding.ASCII.GetString(bytes, 0, bytes.Length));
                    }
                });
                
            }
            catch (Exception e)
            {
                Form1.updateLog($"サーバーの起動に失敗しました\n{e}");
            }
            finally
            {
                listener.Close();
            }
            
        }

        private static void CallController(string message)
        {
            string[] commands = message.Split(".");
            
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

        private static void OpenPort(int port)
        {

        }

        public static void Send(string message)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);

            IPAddress broadcast = IPAddress.Parse("192.168.11.64");

            byte[] sendbuf = Encoding.ASCII.GetBytes(message);
            IPEndPoint ep = new IPEndPoint(broadcast, 11000);

            s.SendTo(sendbuf, ep);
        }
    }
}